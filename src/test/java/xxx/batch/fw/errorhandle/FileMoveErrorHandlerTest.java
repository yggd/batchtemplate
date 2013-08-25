package xxx.batch.fw.errorhandle;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;

import org.junit.Test;

import xxx.batch.fw.ExecuteResult;
import xxx.batch.fw.Status;
import xxx.batch.fw.util.PropertyUtil;

public class FileMoveErrorHandlerTest {

    @Test
    public void test() throws Exception {
        Date d = new Date();
        String dummyFile = "dummy_" + d.getTime();
        File targetFile = new File(dummyFile);
        assertTrue(targetFile.createNewFile());
        
        ExecuteResult result = new ExecuteResult(Status.OTHER_FAIL, new Exception("エラーメッセージ"));
        ErrorHandler handler = new FileMoveErrorHandler(targetFile, result);
        handler.handle();
        
        File movedFile = new File(
                PropertyUtil.getProperty("error.moveDir") + "/" + dummyFile
                );
        assertTrue(movedFile.exists());
    }

}
