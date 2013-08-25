package xxx.batch.fw;

import xxx.batch.fw.errorhandle.AbortException;

/**
 * バッチ処理一連の流れを表すテンプレートクラス。
 * 周期実行対象の1サイクルの処理を規定する。
 */
public abstract class ExecuteTemplate {

    /**
     * 処理対象となるファイルが存在するかを判定するメソッド。
     * 対象ファイルが存在する場合は後続のファイル整合性チェックが実行され、
     * 存在しない場合はバッチ実行が終了する。
     * 
     * @return ファイルが存在する場合はtrueを返却する事。
     */
    public abstract boolean targetExist();

    /**
     * ファイルの形式を調べる。
     * ここでの調査対象はファイルの内容調査（バリデーション）ではなく、
     * 外部接続クライアントに起因するチェックとして、
     * ファイルの正当性(チェックサム／サイズ)を調査する。
     * 形式エラー発生時、つまりStatus.NORMAL以外のステータスが返却された
     * 場合は、ログ記録およびメール送信を行う。
     * 
     * @return 対象ファイルが正しい形式の場合は、Status.NORMALが指定された処理結果を返す事。
     */
    public abstract ExecuteResult checkTarget();

    /**
     * ファイルの内容調査(バリデーション)を行う。
     * 
     * @return 対象ファイルのバリデーションが正常である場合、Status.NORMALが指定された処理結果を返す事。
     */
    public abstract ExecuteResult validateTarget();

    /**
     * ファイルの内容を読み取り、DB登録を行う。
     * 
     * @return 処理結果
     */
    public abstract ExecuteResult registerToDB();

    /**
     * DB登録正常終了時、対象ファイルの削除を行う。
     * 
     * @return 処理結果
     */
    public abstract ExecuteResult removeTarget();

    /**
     * エラーハンドリング。
     * 結果の内容に応じ、エラー処理を行う。
     * 
     * @param result 処理結果
     */
    public abstract void handleError(ExecuteResult result);

    /**
     * バッチを実行する。
     * バッチの正常処理1サイクル分の流れを規定するメソッド。
     */
    public void execute() {
        // TODO テンプレート化したことで流れが見づらくなった。
        // 説明資料等が必要な気がする。

        if (!targetExist()) {
            // 対象ファイルが存在しない場合は終了
            return;
        }

        // ファイルの形式チェックを行う。
        ExecuteResult result = checkTarget();
        if (result == null || !Status.NORMAL.equals(result.getStatus())) {
            handleError(result);
            return;
        }

        // ファイルの内容チェックを行う。
        result = validateTarget();
        if (result == null || !Status.NORMAL.equals(result.getStatus())) {
            handleError(result);
            return;
        }

        // DB登録を行う。
        try {
            result = registerToDB();
        } catch (AbortException e) {
            ExecuteResult abortResult =
                    new ExecuteResult(Status.ABORT_EXECUTE, e.getMessage());
            handleError(abortResult);
            return;
        }
        
        // 対象ファイル削除を行う。
        result = removeTarget();
        if (result == null || !Status.NORMAL.equals(result.getStatus())) {
            handleError(result);
            return;
        }
    }
}
