#!/bin/bash

OS=`uname`
IP="" # store IP
case ${OS} in
   Linux)
        if [ -f /sbin/ifconfig ]
        then
            IP=`/sbin/ifconfig  | grep 'inet'| grep -v '127.0.0.1' |grep -v 'inet6'| awk '{ print $2}' | head -n 1|cut -d: -f2`
        else
        # some linux use /bin/ifconfig (e.g. gentoo)
            IP=`/bin/ifconfig  | grep 'inet addr:'| grep -v '127.0.0.1' |grep -v 'inet6'| awk '{ print $2}' | head -n 1`
        fi
        ;;
   FreeBSD|OpenBSD|Darwin) IP=`ifconfig | grep -E '^en[0-9]:' -A 4 | grep -E 'inet.[0-9]' | grep -v '127.0.0.1' | awk '{ print $2}' | head -n 1` ;;
   SunOS) IP=`ifconfig -a | grep inet | grep -v '127.0.0.1' | awk '{ print $2} ' | head -n 1` ;;
   *) IP="Unknown";;
esac

LIB=${BASEDIR}/lib
LOG_DIR=${BASEDIR}/logs
test -d $LOG_DIR || mkdir -p $LOG_DIR

#gc log dir
GC_LOG_DIR=$LOG_DIR/gclogs
#gc log file name subfix
nowday=`date +%Y%m%d_%H%M%S`
#create gclogs if necessary
test -d $GC_LOG_DIR || mkdir -p $GC_LOG_DIR

CLASSPATH=${BASEDIR}
CLASSPATH=${CLASSPATH}:${LIB}/*

GC_OPTS="-verbosegc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -Xloggc:${GC_LOG_DIR}/gc.log.${nowday}  -XX:+UseG1GC "
JAVA_OPTS="-server -d64 -Dfile.encoding=UTF8"

if [ -z "$JAVA_HOME" ]; then
  JAVA="java"
else
  JAVA="$JAVA_HOME/bin/java"
fi

MAIN_CLASS="com.zsc.springboot.Bootstrap"