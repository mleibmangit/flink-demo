package flink.iotanomalydetection.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class DeviceTemperatureMeasurement {
    private final String deviceId;
    private final double temperature;
    private final LocalDateTime measurementTime;
}