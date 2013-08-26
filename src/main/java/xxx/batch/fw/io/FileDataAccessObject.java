package xxx.batch.fw.io;

/**
 * ファイル読み取りDAO。
 *
 */
public interface FileDataAccessObject<T> {
    T readNext();
}
