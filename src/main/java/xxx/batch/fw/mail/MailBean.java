package xxx.batch.fw.mail;

/**
 * メールテンプレートに埋め込まれる、各種可変情報を保持する。
 * 
 */
public class MailBean {

    private String errorCode = null;
    private String errorMessage = null;

    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
}
