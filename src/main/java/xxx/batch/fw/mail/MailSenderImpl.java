package xxx.batch.fw.mail;

import java.util.Properties;
import java.util.Set;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xxx.batch.fw.util.PropertyUtil;

public class MailSenderImpl implements MailSender {

    Logger log = LoggerFactory.getLogger(MailSenderImpl.class);

    /**
     * メールを送信する。
     * 
     * @param bean メール本文に埋め込まれる可変情報
     */
    public void send(MailBean bean) {
        // SMTP settings.
        Session session = Session.getDefaultInstance(getSMTPProps());
        MimeMessage msg = new MimeMessage(session);

        try {
            // From-To setting
            msg.setFrom(new InternetAddress(
                    PropertyUtil.getProperty("mail.contents.from")));
            msg.setSender(new InternetAddress(
                    PropertyUtil.getProperty("mail.contents.sender")));
            msg.setRecipient(RecipientType.TO, new InternetAddress(
                    PropertyUtil.getProperty("mail.contents.recipients")));

            // Contents.
            String mailEncoding = PropertyUtil.getProperty("mail.contents.encoding");
            msg.setSubject(
                    PropertyUtil.getProperty("mail.contents.title"),
                    mailEncoding);
            MailTemplate mailTemplate = new MailTemplate();
            String text = mailTemplate.mergeText(bean);

            msg.setText(text, mailEncoding);

            // send.
            Transport.send(msg);
        } catch (AddressException e) {
            log.error("アドレス不正", e);
        } catch (MessagingException e) {
            log.error("送信障害", e);
        }
    }

    protected Properties getSMTPProps() {
        Properties props = new Properties();
        Properties utilProps = PropertyUtil.getProperties();
        Set<String> keySet = utilProps.stringPropertyNames();
        for (String key : keySet) {
            if (key.startsWith("mailProp")) {
                String javamailPropKey = key.substring("mailProp.".length());
                props.setProperty(javamailPropKey, utilProps.getProperty(key));
            }
        }
        return props;
    }
}
