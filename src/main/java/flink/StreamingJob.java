/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.MultipleParameterTool;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffleConsumer;
import org.apache.flink.util.Collector;
import org.apache.flink.util.Preconditions;
import org.apache.flink.streaming.api.datastream.DataStream;

import java.util.Properties;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="https://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJob {

	/*public static void main(String[] args) throws Exception {
		// set up the streaming execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		*//*
		 * Here, you can start creating your execution plan for Flink.
		 *
		 * Start with getting some data from the environment, like
		 * 	env.readTextFile(textPath);
		 *
		 * then, transform the resulting DataStream<String> using operations
		 * like
		 * 	.filter()
		 * 	.flatMap()
		 * 	.join()
		 * 	.coGroup()
		 *
		 * and many more.
		 * Have a look at the programming guide for the Java API:
		 *
		 * https://flink.apache.org/docs/latest/apis/streaming/index.html
		 *
		 *//*

		// execute program
		env.execute("Flink Streaming Java API Skeleton");
	}*/

	public static void main(String[] args) throws Exception {

		/*final MultipleParameterTool params = MultipleParameterTool.fromArgs(args);

		// set up the execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		// make parameters available in the web interface
		env.getConfig().setGlobalJobParameters(params);

		// get input data
		DataSet<String> text = null;
		if (params.has("input")) {
			// union all the inputs from text files
			*//*for (String input : params.getMultiParameterRequired("input")) {
				if (text == null) {
					text = env.readTextFile(input);
				} else {
					text = text.union(env.readTextFile(input));
				}
			}
			Preconditions.checkNotNull(text, "Input DataSet should not be null.");*//*
		} else {
			// get default test text data
			System.out.println("Executing WordCount example with default input data set.");
			System.out.println("Use --input to specify file input.");
			text = WordCountData.getDefaultTextLineDataSet(env);
		}

		DataSet<Tuple2<String, Integer>> counts =
				// split up the lines in pairs (2-tuples) containing: (word,1)
				text.flatMap(new Tokenizer())
						// group by the tuple field "0" and sum up tuple field "1"
						.groupBy(0)
						.sum(1);

		counts.print();
		env.execute("WordCount Example");*/
		// emit result
		/*if (params.has("output")) {
			counts.writeAsCsv(params.get("output"), "\n", " ");
			// execute program
			env.execute("WordCount Example");
		} else {
			System.out.println("Printing result to stdout. Use --output to specify output path.");
			counts.print();
		}*/
	}

	// *************************************************************************
	//     USER FUNCTIONS
	// *************************************************************************

	/**
	 * Implements the string tokenizer that splits sentences into words as a user-defined
	 * FlatMapFunction. The function takes a line (String) and splits it into multiple pairs in the
	 * form of "(word,1)" ({@code Tuple2<String, Integer>}).
	 */
	public static final class Tokenizer
			implements FlatMapFunction<String, Tuple2<String, Integer>> {

		@Override
		public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
			// normalize and split the line
			String[] tokens = value.toLowerCase().split("\\W+");

			// emit the pairs
			for (String token : tokens) {
				if (token.length() > 0) {
					out.collect(new Tuple2<>(token, 1));
				}
			}
		}
	}

	/*public static FlinkKafkaShuffleConsumer<String> createStringConsumerForTopic(
			String topic, String kafkaAddress, String kafkaGroup ) {

		Properties props = new Properties();
		props.setProperty("bootstrap.servers", kafkaAddress);
		props.setProperty("group.id",kafkaGroup);
		FlinkKafkaShuffleConsumer<String> consumer = new FlinkKafkaShuffleConsumer<>(
				topic, new SimpleStringSchema(), props);

		return consumer;
	}*/
}
