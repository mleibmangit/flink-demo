package flink.iotanomalydetection.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.List;

@ToString
@Setter
@Getter
public class DeviceTemperatureStandardDeviation {
    private String deviceId;
    private int count;
    private double average;
    //private double standardDeviation;
    //private List<Double> temperatures = new ArrayList<>();
    @ToString.Exclude
    private DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();

    public void update(DeviceTemperatureMeasurement deviceTemperatureMeasurement) {

        //temperatures.add(deviceTemperatureMeasurement.getTemperature());

        count++;

        double addedAverage = (deviceTemperatureMeasurement.getTemperature() - average) / count;
        average = average + addedAverage;

        /*double addedStandardDeviation = (deviceTemperatureMeasurement.getTemperature() - newAverage)
                * (deviceTemperatureMeasurement.getTemperature() - average);

        average = newAverage;
        standardDeviation = standardDeviation + addedStandardDeviation;*/

       descriptiveStatistics.addValue(deviceTemperatureMeasurement.getTemperature());
    }

    public double getStandardDeviation(){
        return descriptiveStatistics.getStandardDeviation();
    }

    /*
    	constructor() {
		this.count = 0
		this._mean = 0
		this._dSquared = 0
	}

	update(newValue) {
		this.count++

		const meanDifferential = (newValue - this._mean) / this.count

		const newMean = this._mean + meanDifferential

		const dSquaredIncrement =
			(newValue - newMean) * (newValue - this._mean)

		const newDSquared = this._dSquared + dSquaredIncrement

		this._mean = newMean

		this._dSquared = newDSquared
	}

	get mean() {
		this.validate()
		return this._mean
	}

	get dSquared() {
		this.validate()
		return this._dSquared
	}

	get populationVariance() {
		return this.dSquared / this.count
	}

	get populationStdev() {
		return Math.sqrt(this.populationVariance)
	}

	get sampleVariance() {
		return this.count > 1 ? this.dSquared / (this.count - 1) : 0
	}

	get sampleStdev() {
		return Math.sqrt(this.sampleVariance)
	}

	validate() {
		if (this.count == 0) {
			throw new StatsError('Mean is undefined')
		}
	}
     */
}