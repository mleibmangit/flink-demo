package flink.iotanomalydetection.job;

import com.google.common.collect.EvictingQueue;
import flink.iotanomalydetection.model.DeviceTemperatureMeasurement;
import flink.iotanomalydetection.model.DeviceTemperatureStandardDeviation;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceTemperatureMeasurementAggregatorV2 extends KeyedProcessFunction<String, DeviceTemperatureMeasurement, DeviceTemperatureStandardDeviation> {

    private static final Logger log = LoggerFactory.getLogger(DeviceTemperatureMeasurementAggregatorV2.class);
    public static final int LAST_MEASUREMENTS_COUNT = 100;

    private transient ValueState<EvictingQueue<DeviceTemperatureMeasurement>> state;

    @Override
    public void open(Configuration parameters) {
        ValueStateDescriptor<EvictingQueue<DeviceTemperatureMeasurement>> sateDescriptor = new ValueStateDescriptor<>(
                "state",
                TypeInformation.of(new TypeHint<EvictingQueue<DeviceTemperatureMeasurement>>() {
                }));
        state = getRuntimeContext().getState(sateDescriptor);
    }

    @Override
    public void processElement(
            DeviceTemperatureMeasurement deviceTemperatureMeasurement,
            Context context,
            Collector<DeviceTemperatureStandardDeviation> collector) throws Exception {

        //log.info("Got new deviceTemperatureMeasurement: {}",deviceTemperatureMeasurement);
        EvictingQueue<DeviceTemperatureMeasurement> deviceTemperatureMeasurements = state.value();

        if (deviceTemperatureMeasurements == null) {
            deviceTemperatureMeasurements = EvictingQueue.create(LAST_MEASUREMENTS_COUNT);
        }

        if (deviceTemperatureMeasurements.size() < LAST_MEASUREMENTS_COUNT) {
            deviceTemperatureMeasurements.add(deviceTemperatureMeasurement);
            state.update(deviceTemperatureMeasurements);
            return;
        }

        DeviceTemperatureStandardDeviation deviceTemperatureStandardDeviation = new DeviceTemperatureStandardDeviation();

        for (DeviceTemperatureMeasurement measurement : deviceTemperatureMeasurements) {
            deviceTemperatureStandardDeviation.setDeviceId(deviceTemperatureMeasurement.getDeviceId());
            deviceTemperatureStandardDeviation.update(measurement);
        }

        deviceTemperatureMeasurements.add(deviceTemperatureMeasurement);
        state.update(deviceTemperatureMeasurements);
        collector.collect(deviceTemperatureStandardDeviation);
    }
}