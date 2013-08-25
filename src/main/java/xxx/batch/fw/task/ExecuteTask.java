package xxx.batch.fw.task;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xxx.batch.fw.ExecuteResult;
import xxx.batch.fw.ExecuteTemplate;
import xxx.batch.fw.Status;
import xxx.batch.fw.errorhandle.FileMoveErrorHandler;
import xxx.batch.fw.util.PropertyUtil;

/**
 * バッチ周期実行1サイクルの具体的な処理を実行する。
 */
public class ExecuteTask extends ExecuteTemplate {

    private static Logger log = LoggerFactory.getLogger(ExecuteTask.class);

    private static String targetFilePath = null;

    private File targetFile = null;

    static {
        targetFilePath = PropertyUtil.getProperty("file.target.path");
    }

    /**
     * 対象ファイルが存在することを確認する。
     */
    @Override
    public boolean targetExist() {
        // TODO 対象ファイルはプロパティから。
        File f = new File(targetFilePath);
        if (f.exists() && f.isFile() && f.canRead()) {
            this.targetFile = f;
            log.info("start cycle, targetFile:" + f.getName());
            return true;
        }
        return false;
    }

    /**
     * 対象ファイルの存在、形式確認。
     * 
     * @return 処理結果
     */
    @Override
    public ExecuteResult checkTarget() {
        // TODO Auto-generated method stub
        return new ExecuteResult();
    }

    /**
     * ファイルの内容確認チェック。
     * @return 処理結果
     */
    @Override
    public ExecuteResult validateTarget() {
        // TODO Auto-generated method stub
        return new ExecuteResult();
    }

    /**
     * DB登録処理。
     * @return 処理結果
     */
    @Override
    public ExecuteResult registerToDB() {
        // TODO Auto-generated method stub
        return new ExecuteResult();
    }

    /**
     * 正常終了時、ファイル削除を行う。
     * @return 処理結果
     */
    @Override
    public ExecuteResult removeTarget() {
        // 対象のファイル削除を行う。
        if (targetFile != null) {
            if (targetFile.delete()) {
                log.info("end cycle, remove file:" + targetFile.getName());
            } else {
                ExecuteResult result = new ExecuteResult(
                        Status.FILE_REMOVE_FAIL,
                        "ファイル削除に失敗:" + targetFile.getName());
                return result;
            }
        }
        return new ExecuteResult();
    }

    /**
     * エラーハンドリングを行う。
     */
    @Override
    public void handleError(ExecuteResult result) {
        new FileMoveErrorHandler(targetFile, result).handle();
    }

}
