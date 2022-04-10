package flink.iotanomalydetection.job;

import flink.iotanomalydetection.model.DeviceTemperatureMeasurement;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.time.LocalDateTime;
import java.util.Random;

public class TestSource extends RichSourceFunction<DeviceTemperatureMeasurement> {

    private volatile boolean running = true;

    private String[] deviceIds = new String[] { "1", "2" };
    private double[] temperatures = new double[] { 1.0, 2.0, 3.0 };
    private Random random = new Random();

    @Override
    public void run(SourceContext<DeviceTemperatureMeasurement> sourceContext) throws Exception {
        while (running) {
            String deviceId = deviceIds[random.nextInt(deviceIds.length)];
            double temperature =  temperatures[random.nextInt(temperatures.length)];
            //Double value = random.nextDouble() * 10;
            sourceContext.collect(new DeviceTemperatureMeasurement(deviceId, temperature, LocalDateTime.now()));
            Thread.sleep(500);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}
