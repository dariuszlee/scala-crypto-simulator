#!/bin/bash

HOME=$(pwd)
SRC=$HOME/src
BIN=$HOME/bin

if [[ ! -d $BIN ]];then
	mkdir $BIN
fi

cd $BIN
scalac $SRC/*.scala
