package xxx.batch.fw.errorhandle;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xxx.batch.fw.ExecuteResult;
import xxx.batch.fw.mail.MailBean;
import xxx.batch.fw.mail.MailSender;
import xxx.batch.fw.mail.MailSenderImpl;
import xxx.batch.fw.util.PropertyUtil;

public class FileMoveErrorHandler implements ErrorHandler {

    Logger log = LoggerFactory.getLogger(FileMoveErrorHandler.class);

    private File targetFile = null;
    private ExecuteResult result = null;

    public FileMoveErrorHandler(File targetFile, ExecuteResult result) {
        this.targetFile = targetFile;
        this.result = result;
    }

    /**
     * エラーハンドリングを行う。
     */
    public void handle() {
        Exception e = result.getException();
        if (e != null) {
            log.error(result.getMessage(), e);
        } else {
            log.error(result.getMessage());
        }

        // 対象のファイルをエラーディレクトリに移動
        String errorDirBase = PropertyUtil.getProperty("error.moveDir");
        if (errorDirBase != null && !errorDirBase.endsWith("/")) {
            errorDirBase = errorDirBase + "/";
        }
        String fileName = targetFile.getName();
        targetFile.renameTo(new File(errorDirBase + fileName));

        // メール送信
        MailBean bean = new MailBean();
        bean.setErrorCode(result.getStatus().toString());
        bean.setErrorMessage(result.getMessage());
        MailSender sender = new MailSenderImpl();
        sender.send(bean);
    }

}
