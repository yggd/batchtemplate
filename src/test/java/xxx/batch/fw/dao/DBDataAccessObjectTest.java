package xxx.batch.fw.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import xxx.batch.fw.dao.DBDataAccessObject;

public class DBDataAccessObjectTest {

    /**
     * 一回インサートされたら一意制約違反で異常終了するため注意。
     */
    @Test
    public void test() {
        DBDataAccessObject dao = new DBDataAccessObject();
        assertTrue(dao.save(8, "ああああ"));
    }

}
