package xxx.batch.fw.io;

public class CsvFileDAO implements FileDataAccessObject<CsvRecordBean> {

    @Override
    public CsvRecordBean readNext() {
        // TODO Auto-generated method stub
        return new CsvRecordBean();
    }

}
