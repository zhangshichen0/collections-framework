#!/bin/bash

BIN=$(dirname $(readlink -f $0))
BASEDIR=$(dirname $BIN)

function killpid()
{
  if [ ! -z "$*" ] ; then
	echo "kill $*"
    kill $*
  fi
}

function forceKillPid(){
   if [ ! -z "$*" ] ; then
      echo "force kill $*"
      kill -9 $*
    fi
}

export BASEDIR

cd ${BASEDIR}/bin

source ./common.sh

PID=($(ps -ef | grep ${MAIN_CLASS}| grep -v grep| awk '{print $2}'))
TIMEOUT=10
countkill=0
while [ ${#PID[@]} -gt 0 ] && [ $TIMEOUT -gt 0 ]
    do
      if [ $countkill -eq 0 ];then
          echo "Stop program   ${MAIN_CLASS} . PID: ${PID[@]}"
          killpid ${PID[@]}
          countkill=1
      fi
      PID=($(ps -ef | grep ${MAIN_CLASS}| grep -v grep| awk '{print $2}'))
      sleep 1
      TIMEOUT=$(($TIMEOUT-1))
      if [ $TIMEOUT -eq 0 ];then
                echo " Stop program ${MAIN_CLASS}  timeout !  Force killing!. PID: ${PID[@]}"
                forceKillPid ${PID[@]}
          exit 0
      fi
      echo TIMEOUT $TIMEOUT
   done

exit 0