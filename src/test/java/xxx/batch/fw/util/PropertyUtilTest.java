package xxx.batch.fw.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertyUtilTest {

    @Test
    public void test() {
        assertEquals("hogehogeぷー", PropertyUtil.getProperty("db.driver.name"));
    }

}
