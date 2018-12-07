#!/bin/bash

function usage()
{
    echo "migration 1.0"
    echo ""
    echo "./start_server.sh"
    echo ""
}


while [ "$1" != "" ]; do
    PARAM=`echo $1 | awk -F= '{print $1}'`
    VALUE=`echo $1 | awk -F= '{print $2}'`
    case ${PARAM} in
        -h | --help)
            usage
            exit
            ;;
        *)
            echo "ERROR: unknown parameter \"$PARAM\""
            usage
            exit 1
            ;;
    esac
    shift
done


BIN=$(dirname $(readlink -f $0))
BASEDIR=$(dirname $BIN)

echo $BASEDIR

export BASEDIR

cd ${BASEDIR}/bin

source ./common.sh

cd ${BASEDIR}

CLASSPATH=${BASEDIR}/conf/:${CLASSPATH}

PID=($(ps -ef | grep ${MAIN_CLASS}| grep -v grep| awk '{print $2}'))
if [ ${#PID[@]} -gt 0 ];then 
        echo "program  already started! exit."
        exit 0
fi


RPC_HEAP_OPTS="-Xmx2g -Xms1g "


echo "starting process..."
echo ${MAIN_CLASS}
echo ${CLASSPATH}
echo ${GC_OPTS}
echo ${JAVA_OPTS}
echo ${JAVA}

nohup ${JAVA} ${JAVA_OPTS}  ${RPC_HEAP_OPTS} ${GC_OPTS} -classpath ${CLASSPATH} -Dfile.encoding=UTF-8 -Dlog.home=${LOG_DIR} -Djava.security.egd=file:/dev/./urandom   ${MAIN_CLASS} >> ${LOG_DIR}/stdout.log 2>&1 &