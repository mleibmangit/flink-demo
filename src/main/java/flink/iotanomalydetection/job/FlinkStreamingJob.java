package flink.iotanomalydetection.job;

import flink.iotanomalydetection.model.DeviceTemperatureMeasurement;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

public class FlinkStreamingJob {

    public void runJob() throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<DeviceTemperatureMeasurement> deviceTemperatureMeasurements
                = env.addSource(new TestSource());

        deviceTemperatureMeasurements
                .keyBy(DeviceTemperatureMeasurement::getDeviceId)
                .window(SlidingProcessingTimeWindows.of(Time.seconds(60), Time.seconds(1)))
                .aggregate(new DeviceTemperatureMeasurementAggregator())
                .filter(t -> t.f0.getStandardDeviation() > 3)
                .print("ALERT, temperature anomaly detected");

        /*deviceTemperatureMeasurements
                .keyBy(DeviceTemperatureMeasurement::getDeviceId)
                .process(new DeviceTemperatureMeasurementAggregatorV2())
                .filter(standardDeviation -> standardDeviation.getStandardDeviation() > 3)
                .print("ALERT, temperature anomaly detected");*/

        env.execute();
    }
}