package xxx.batch.fw.errorhandle;

/**
 * DB登録時に処理を中断せざるを得ない事態が発生した場合の例外。
 * 本例外が投げられた場合処理を直ちに中断する。
 */
public class AbortException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1353244318095841864L;

    private Exception cause = null;
    private String message = null;

    /**
     * コンストラクタ。
     * 原因例外が存在せず、何らかの処理中断の必要が生じた場合に使用する。
     * 
     * @param message 処理中断理由
     */
    public AbortException(String message) {
        this.message = message;
    }

    /**
     * コンストラクタ。
     * 原因例外が存在した場合に使用する。
     * 
     * @param cause 原因例外
     * @param message 処理中断理由
     */
    public AbortException(Exception cause, String message) {
        this.cause = cause;
        this.message = message;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
