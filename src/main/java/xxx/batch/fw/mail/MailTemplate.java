package xxx.batch.fw.mail;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import xxx.batch.fw.errorhandle.AbortException;
import xxx.batch.fw.util.PropertyUtil;

/**
 * メールテンプレートを読み込み、文字を埋め込む。
 */
public class MailTemplate {

    private static final String VELOCITY_PROP = "/velocity.properties";
    
    private String templateLocation = null;

    public MailTemplate() {
        this.templateLocation = PropertyUtil.getProperty("mail.template");
    }
    
    /**
     * メールテンプレートを読み取り、可変文字列を埋め込み後に本文を返却する。
     * 
     * @param mail 可変情報
     * @return 本文テキスト
     */
    public String mergeText(MailBean mail) {
        if (templateLocation == null) {
            throw new AbortException("メールテンプレートの場所が指定されていません。");
        }
        Velocity.init(getClass().getResource(VELOCITY_PROP).getPath());
        VelocityContext ctx = new VelocityContext();
        ctx.put("mail", mail);
        
        StringWriter writer = new StringWriter();
        Template template = Velocity.getTemplate(templateLocation,
                PropertyUtil.getProperty("mail.templateEncoding"));
        template.merge(ctx, writer);
        String text = writer.toString();
        return text;
    }
}
