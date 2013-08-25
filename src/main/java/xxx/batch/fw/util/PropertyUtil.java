package xxx.batch.fw.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 外部設定ファイルの読み込み、取り出しを行うクラス。
 */
public class PropertyUtil {

    private static Logger log = LoggerFactory.getLogger(PropertyUtil.class);

    private static final String RESOURCE_NAME = "conf.properties";

    private static Properties props = null;

    static {
        load();
    }

    /**
     * プロパティファイルからの読み出しを行う。 ファイル変更後に再度読み出したい場合は本メソッドを再実行する。
     */
    public static void load() {
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(RESOURCE_NAME);
        synchronized (PropertyUtil.class) {
            props = new Properties();
            try {
                props.load(is);
            } catch (IOException e) {
                log.error("プロパティファイルの読み取り失敗。"
                        + RESOURCE_NAME + " のクラスパスを確認してください。:", e);
                return;
            }
        }
    }

    /**
     * プロパティを取得する。
     * 
     * @param key プロパティキー
     * @return プロパティ値
     */
    public static String getProperty(String key) {
        synchronized (PropertyUtil.class) {
            return props.getProperty(key);
        }
    }

    /**
     * プロパティオブジェクトを返却する。
     * 
     * @return プロパティ
     */
    public static Properties getProperties() {
        return props;
    }
}
