package org.wendex;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class AirportApp {
    private static Integer safeParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static final int AIRPORT_ID_INDEX = 0;
    private static final int AIRPORT_NAME_INDEX = 1;
    private static final int FLIGHT_ORIGIN_ID_INDEX = 11;
    private static final int FLIGHT_DEST_ID_INDEX = 14;
    private static final int FLIGHT_DELAY_INDEX = 18;
    private static final int FLIGHT_CANCELLED_INDEX = 19;

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> AirportRDD = sc.textFile("L_AIRPORT_ID.csv");
        JavaRDD<String> FlightRDD = sc.textFile("664600583_T_ONTIME_sample.csv");

        JavaPairRDD<Integer, String> AirportsNames = sc.textFile("L_AIRPORT_ID.csv")
                .mapToPair(s -> {
                   String[] strs = CsvTools.read(s);
                   return new Tuple2<>(safeParseInt(strs[AIRPORT_ID_INDEX]), strs[AIRPORT_NAME_INDEX]);
                }).filter(t -> t._1 != null);

        JavaPairRDD<Tuple2<Integer, Integer>, Tuple2<Double, Boolean>> Flights = sc
                .textFile("664600583_T_ONTIME_sample.csv")
                .mapToPair(s -> {
                    String[] strs = CsvTools.read(s);
                    Integer origin = safeParseInt(strs[FLIGHT_ORIGIN_ID_INDEX]);
                    Integer dest = safeParseInt(strs[FLIGHT_DEST_ID_INDEX]);
                    Double delay = Double.parseDouble(strs[FLIGHT_DELAY_INDEX]);
                    Double cancelled = Double.parseDouble(strs[FLIGHT_CANCELLED_INDEX]);
                    if (origin == null) {
                        return new Tuple2<>(null, null);
                    }
                    return new Tuple2<>(new Tuple2<>(origin, dest), new Tuple2<>(delay, cancelled > 0));
                }).filter(t -> t._1 != null)
                .
    }
}