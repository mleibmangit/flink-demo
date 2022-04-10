package flink.iotanomalydetection;

import flink.iotanomalydetection.job.FlinkStreamingJob;

public class Main {

    public static void main(String[] args) throws Exception {
        FlinkStreamingJob flinkStreamingJob = new FlinkStreamingJob();
        flinkStreamingJob.runJob();
    }
}
