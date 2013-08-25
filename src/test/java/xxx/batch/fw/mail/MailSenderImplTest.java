package xxx.batch.fw.mail;

import org.junit.Test;

import xxx.batch.fw.mail.MailBean;
import xxx.batch.fw.mail.MailSender;
import xxx.batch.fw.mail.MailSenderImpl;

public class MailSenderImplTest {

    @Test
    public void test() {
        MailSender sender = new MailSenderImpl();
        MailBean bean = new MailBean();
        bean.setErrorCode("仮のエラーコード");
        bean.setErrorMessage("仮のエラーメッセージ");
        // Assertできないので、目視でメール確認
        sender.send(bean);
    }

}
