export HADOOP_CLASSPATH=target/lab3-1.0-SNAPSHOT.jar
rm -r output
mvn package
hadoop fs -rm -r output
hadoop org.wendex.AirportApp L_AIRPORT_ID.csv 664600583_T_ONTIME_sample.csv output
hadoop fs -copyToLocal output

