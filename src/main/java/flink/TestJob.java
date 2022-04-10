package flink;

public class TestJob {
  /*  private static final Logger log = LoggerFactory.getLogger(AggregationTestIT.class);

    @Test
    public void testPipeline() throws Exception {
        log.debug("running local pipeline()");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<MyEvent> events = env.addSource(new MySource());

        events.keyBy(myEvent -> myEvent.variant)
                .window(TumblingProcessingTimeWindows.of(Time.seconds(4)))
                .aggregate(new AverageAggregator())
                .print();

        env.execute();
    }

    *//**
     * Aggregation function for average.
     *//*
    public static class AverageAggregator implements AggregateFunction<MyEvent, DeviceTemperatureStandardDeviation, Tuple2<DeviceTemperatureStandardDeviation, Double>> {

        @Override
        public DeviceTemperatureStandardDeviation createAccumulator() {
            return new DeviceTemperatureStandardDeviation();
        }

        @Override
        public DeviceTemperatureStandardDeviation add(MyEvent myEvent, DeviceTemperatureStandardDeviation myAverage) {
            log.debug("add({},{})", myAverage.variant, myEvent);
            myAverage.variant = myEvent.variant;
            myAverage.count = myAverage.count + 1;
            myAverage.sum = myAverage.sum + myEvent.cev;
            return myAverage;
        }

        @Override
        public Tuple2<DeviceTemperatureStandardDeviation, Double> getResult(DeviceTemperatureStandardDeviation myAverage) {
            return new Tuple2<>(myAverage, myAverage.sum / myAverage.count);
        }

        @Override
        public DeviceTemperatureStandardDeviation merge(DeviceTemperatureStandardDeviation myAverage, DeviceTemperatureStandardDeviation acc1) {
            myAverage.sum = myAverage.sum + acc1.sum;
            myAverage.count = myAverage.count + acc1.count;
            return myAverage;
        }
    }

    *//**
     * Produce never ending stream of fake updates.
     *//*
    public static class MySource extends RichSourceFunction<MyEvent> {

        private boolean running = true;

        private String[] variants = new String[] { "var1", "var2" };
        private String[] products = new String[] { "prodfoo", "prodBAR", "prod-baz" };
        private Random random = new Random();

        @Override
        public void run(SourceContext<MyEvent> sourceContext) throws Exception {
            while (running) {
                String variant = variants[random.nextInt(variants.length)];
                String product = products[random.nextInt(products.length)];
                Double value = random.nextDouble() * 10;
                sourceContext.collect(new MyEvent(variant, product, value));
                Thread.sleep(500);
            }
        }

        @Override
        public void cancel() {
            running = false;
        }
    }

    *//**
     * Immutable update event.
     *//*
    public static class MyEvent {
        public final String variant;
        public final String product;
        public final Double cev;

        public MyEvent(String variant, String product, Double cev) {
            this.variant = variant;
            this.product = product;
            this.cev = cev;
        }

        @Override
        public String toString() {
            return "{" +
                    "'variant' : '" + variant + "\'" +
                    ", 'product' : '" + product + "\'" +
                    ", 'cev' : " + cev +
                    '}';
        }
    }

    public static class DeviceTemperatureStandardDeviation {

        public String variant;
        public Integer count = 0;
        public Double sum = 0d;

        @Override
        public String toString() {
            return "DeviceTemperatureStandardDeviation{" +
                    "variant='" + variant + '\'' +
                    ", count=" + count +
                    ", sum=" + sum +
                    '}';
        }
    }*/
}
