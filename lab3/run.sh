export HADOOP_CLASSPATH=target/lab3-1.0-SNAPSHOT.jar
rm output.csv
hadoop fs -rm -r output.csv
mvn package
spark-submit --class org.wendex.AirportApp --master yarn-client --num-executors 3 target/lab3-1.0-SNAPSHOT.jar
hadoop fs -copyToLocal output.csv
