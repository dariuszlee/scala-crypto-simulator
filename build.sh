#!/bin/bash

HOME=$(pwd)
SRC=$HOME/src
BIN=$HOME/bin

# Dependencies
DEPENDENCIES=$BIN:
DEPENDENCIES+=$BIN/akka-actor_2.12-2.5.14.jar:
DEPENDENCIES+=$BIN/akka-http_2.12-10.1.3.jar:
DEPENDENCIES+=$BIN/akka-http-core_2.12-10.1.3.jar:
DEPENDENCIES+=$BIN/akka-http-testkit_2.12-10.1.3.jar:
DEPENDENCIES+=$BIN/akka-stream_2.12-2.5.14.jar:


if [[ ! -d $BIN ]];then
	mkdir $BIN
fi

cd $BIN
scalac $SRC/*.scala -cp $DEPENDENCIES
