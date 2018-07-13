#!/bin/bash

HOME=$(pwd)
SRC=$HOME/src
BIN=$HOME/bin

cd $BIN
scalac $SRC/*.scala
