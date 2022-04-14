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
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(400);

    private volatile boolean running = true;
    private final double[] temperatures = new double[]{1.0, 2.0, 3.0, 6.0, 9.0};
    private final Random random = new Random();

    @Override
    public void run(SourceContext<DeviceTemperatureMeasurement> sourceContext) throws Exception {

        for (int i = 0; i < 2000; i++) {

            int finalDeviceId = i;

            Runnable runnable = () -> {
                double temperature = temperatures[random.nextInt(temperatures.length)];
                DeviceTemperatureMeasurement deviceTemperatureMeasurement = new DeviceTemperatureMeasurement(String.valueOf(finalDeviceId), temperature, LocalDateTime.now());
                //log.info("Created measurement: {}", deviceTemperatureMeasurement);
                sourceContext.collect(deviceTemperatureMeasurement);
            };

            executor.scheduleAtFixedRate(runnable, 0, 600, TimeUnit.MILLISECONDS);
        }

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