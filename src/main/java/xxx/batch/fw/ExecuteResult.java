package xxx.batch.fw;

/**
 * 実行結果のステータスコードと詳細情報を保持するクラス
 *
 */
public class ExecuteResult {

    private Status status;
    private String message;
    private Exception exception;

    /**
     * コンストラクタ。
     * 正常ステータスを返却するときに使用する。
     */
    public ExecuteResult() {
        this(Status.NORMAL, "normal end.");
    }

    /**
     * コンストラクタ。
     * ステータスコードと詳細情報を指定する。
     * 
     * @param status ステータスコード
     * @param message 詳細情報
     */
    public ExecuteResult(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * コンストラクタ。
     * 例外発生に伴う異常終了時に使用する。
     * 
     * @param status ステータスコード
     * @param e 発生例外
     */
    public ExecuteResult(Status status, Exception e) {
        this(status, e.getMessage());
        this.exception = e;
    }

    /**
     * ステータスコードを取得する。
     * 
     * @return ステータスコード
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 詳細情報を取得する。
     * 
     * @return 詳細情報
     */
    public String getMessage() {
        return message;
    }

    /**
     * 発生例外を取得する。
     * 
     * @return 発生例外
     */
    public Exception getException() {
        return exception;
    }
}
