package xxx.batch.fw.mail;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

import xxx.batch.fw.mail.MailBean;
import xxx.batch.fw.mail.MailTemplate;

public class MailTemplateTest {

    @Test
    public void test() throws Exception {
        MailTemplate template = new MailTemplate();
        MailBean bean = new MailBean();
        bean.setErrorCode("###ERROR-CODE###");
        bean.setErrorMessage("###エラーメッセージ###");
        String text = template.mergeText(bean);
        try (
                Reader reader = new StringReader(text);
                BufferedReader bufReader = new BufferedReader(reader);
        ) {
            // 本文3行目の文字埋め込み確認
            bufReader.readLine();
            bufReader.readLine();
            String line3 = bufReader.readLine();
            assertEquals("[###ERROR-CODE###:###エラーメッセージ###]", line3);
        }
    }

}
