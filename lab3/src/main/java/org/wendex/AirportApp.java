package org.wendex;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class AirportApp {
    @org.jetbrains.annotations.Nullable
    private static Integer safeParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
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
                   int id = safeParseInt(strs[0]);
                   return new Tuple2<>(id, strs[1]);
                }).filter(t -> t._1 >= 0);
    }
}