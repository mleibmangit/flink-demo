package flink.iotanomalydetection.job;

import flink.iotanomalydetection.model.DeviceTemperatureStandardDeviation;
import flink.iotanomalydetection.model.DeviceTemperatureMeasurement;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AverageTemperatureAggregator
        implements AggregateFunction<DeviceTemperatureMeasurement, DeviceTemperatureStandardDeviation,
        Tuple2<DeviceTemperatureStandardDeviation, Double>> {

    private static final Logger log = LoggerFactory.getLogger(AverageTemperatureAggregator.class);

    @Override
    public DeviceTemperatureStandardDeviation createAccumulator() {
        return new DeviceTemperatureStandardDeviation();
    }

    @Override
    public DeviceTemperatureStandardDeviation add(DeviceTemperatureMeasurement deviceTemperatureMeasurement,
                                                  DeviceTemperatureStandardDeviation deviceTemperatureStandardDeviation) {
        //log.info("add new DeviceTemperatureMeasurement to window: {}", deviceTemperatureMeasurement);
        deviceTemperatureStandardDeviation.setDeviceId(deviceTemperatureMeasurement.getDeviceId());
        deviceTemperatureStandardDeviation.update(deviceTemperatureMeasurement);
        return deviceTemperatureStandardDeviation;
    }

    @Override
    public Tuple2<DeviceTemperatureStandardDeviation, Double> getResult(DeviceTemperatureStandardDeviation deviceTemperatureStandardDeviation) {
        //log.info("window closed with result: {}", deviceTemperatureStandardDeviation);
        return new Tuple2<>(deviceTemperatureStandardDeviation, deviceTemperatureStandardDeviation.getStandardDeviation());
    }

    @Override
    public DeviceTemperatureStandardDeviation merge(DeviceTemperatureStandardDeviation deviceTemperatureAverage, DeviceTemperatureStandardDeviation acc1) {
        log.info("merge");
        return deviceTemperatureAverage;
    }
}