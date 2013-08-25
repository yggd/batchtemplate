package xxx.batch.fw;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xxx.batch.fw.task.ExecuteTask;

/**
 * バッチの周期実行を規定し、エントリポイントとなるクラス。
 * 
 */
public class Scheduler {

    private static Logger log = LoggerFactory.getLogger(Scheduler.class);

    public static void main(String[] args) {
        log.info("main-thread, start.");

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        Runnable runnable = new Runnable() {
            // TODO タスクの外だし。
            private ExecuteTask task = new ExecuteTask();
            public void run() {
                task.execute();
            }
        };
        // TODO 複数タスクあるなら遅延実行を行う。
        scheduler.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);

        log.info("main-thread, end.");
    }
}
