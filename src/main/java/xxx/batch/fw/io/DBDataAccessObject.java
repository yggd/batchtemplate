package xxx.batch.fw.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xxx.batch.fw.errorhandle.AbortException;
import xxx.batch.fw.util.PropertyUtil;

public class DBDataAccessObject {

    private static Logger log = LoggerFactory.getLogger(DBDataAccessObject.class);

    private static String driverName = null;
    private static String url = null;
    private static String userName = null;
    private static String password = null;

    static {
        driverName = PropertyUtil.getProperty("db.drivername");
        url = PropertyUtil.getProperty("db.url");
        userName = PropertyUtil.getProperty("db.username");
        password = PropertyUtil.getProperty("db.password");
    }

    /**
     * テーブルにデータの保存を行う。
     * @return SQL実行に成功し、1件insertできたときにtrue
     */
    public boolean save(int num, String name) {
        String sql = "insert into test (num, name) values (?, ?)";
        try (
            Connection con = DriverManager.getConnection(url, userName, password);
            PreparedStatement ps = con.prepareStatement(sql);
        ){
            Class.forName(driverName).newInstance();
            con.setAutoCommit(false);
            // SQLバインド(TODO O/Rマッパー使うか悩みどころ)
            ps.setInt(1, num);
            ps.setString(2, name);
            int updateCount = ps.executeUpdate();
            if (updateCount == 1) {
                con.commit();
                return true;
            }
            con.rollback();
            return false;
        } catch (SQLException e) {
            log.error("SQL実行例外 エラーコード[" + e.getErrorCode() + "] SQLSTATE[" + e.getSQLState() + "]", e);
            return false;
        } catch (InstantiationException e) {
            throw new AbortException(e, "DBドライバのインスタンス化に失敗。");
        } catch (IllegalAccessException e) {
            throw new AbortException(e, "不正アクセス例外");
        } catch (ClassNotFoundException e) {
            throw new AbortException(e, "DBドライバクラスが見つからない。");
        }
    }
}
