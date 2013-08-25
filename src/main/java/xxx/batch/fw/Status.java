package xxx.batch.fw;

/**
 * ステータスコードを一括管理する列挙体。
 */
public enum Status {
    NORMAL, // ステータス正常
    
    ABORT_EXECUTE, // DB登録処理中断
    
    FILE_REMOVE_FAIL, // ファイル削除失敗
    OTHER_FAIL // 原因不明による失敗
}
