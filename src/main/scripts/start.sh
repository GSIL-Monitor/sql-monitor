#!/bin/bash
BinDir=`dirname $0`
ProjectHome="$BinDir/../"

cd $ProjectHome
JVM_OPTS="-server -XX:MaxPermSize=256M -Xms3078M -Xmx3078M -XX:+CMSClassUnloadingEnabled"
JVM_EXTRA_OPTS="-XX:+UseParallelGC -XX:+UseParallelOldGC -XX:-OptimizeStringConcat"
OOM_OPTS="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/sql-monitor-heap-dump.hprof"
CP="lib/*:conf/"
MAIN="mogujie.sql.monitor.Main"

java $JVM_OPTS $JVM_EXTRA_OPTS $OOM_OPTS -XX:OnOutOfMemoryError='kill -9 %p' -Djava.io.tmpdir=/tmp  -cp $CP $MAIN > /dev/null 2>&1  &
echo "$!" > /var/run/sql-monitor.pid
