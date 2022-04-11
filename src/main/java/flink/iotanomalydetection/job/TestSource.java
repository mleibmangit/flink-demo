package flink.iotanomalydetection.job;

import flink.iotanomalydetection.model.DeviceTemperatureMeasurement;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestSource extends RichSourceFunction<DeviceTemperatureMeasurement> {

    private static final Logger log = LoggerFactory.getLogger(TestSource.class);
    private static final transient ScheduledExecutorService executor = Executors.newScheduledThreadPool(200);

    private volatile boolean running = true;
    private final double[] temperatures = new double[]{1.0, 2.0, 3.0, 6.0, 9.0};
    private final Random random = new Random();

    @Override
    public void run(SourceContext<DeviceTemperatureMeasurement> sourceContext) throws Exception {

        Runnable runnable = () -> {

            for (int i = 0; i < 2000; i++) {
                double temperature = temperatures[random.nextInt(temperatures.length)];
                sourceContext.collect(new DeviceTemperatureMeasurement(String.valueOf(i), temperature, LocalDateTime.now()));
            }
        };

        executor.scheduleAtFixedRate(runnable, 0, 60, TimeUnit.MILLISECONDS);

        while (running) {
            Thread.sleep(600);
        }
    }

    @Override
    public void cancel() {
        running = false;
        executor.shutdown();
        log.info("TestSource cancelled");
    }
}