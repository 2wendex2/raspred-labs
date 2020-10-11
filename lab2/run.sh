export HADOOP_CLASSPATH=target/lab2-1.0-SNAPSHOT.jar
rm -r output
mvn package
hadoop fs -rm -r output
hadoop fs -rm -r L_AIRPORT_ID.csv
hadoop fs -copyFromLocal L_AIRPORT_ID.csv
hadoop org.wendex.AirportApp L_AIRPORT_ID.csv output
hadoop fs -copyToLocal output

