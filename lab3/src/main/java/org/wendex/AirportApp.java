package org.wendex;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class AirportApp {
    private static int safeParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> AirportRDD = sc.textFile("L_AIRPORT_ID.csv");
        JavaRDD<String> FlightRDD = sc.textFile("664600583_T_ONTIME_sample.csv");

        JavaPairRDD<Integer, String> AirportsNames = sc.textFile("L_AIRPORT_ID.csv")
                .mapToPair(s -> {
                   String[] strs = CsvTools.read(s);
                   return Tuple2<>
                });
    }
}