export HADOOP_CLASSPATH=target/lab1-1.0-SNAPSHOT.jar
rm -r output
mvn package
hadoop fs -rm -r output
hadoop fs -rm -r war.txt
hadoop fs -copyFromLocal war.txt
hadoop org.wendex.WordCountApp war.txt output
hadoop fs -copyToLocal output

