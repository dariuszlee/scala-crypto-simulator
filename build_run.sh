#!/bin/bash
HOME=$(pwd)
SRC=$HOME/src
BIN=$HOME/bin
DEPENDENCIES=$BIN:

function BuildDependencies() {
	find $HOME/target | grep '.jar$' | awk '{ print "DEPENDENCIES+="$1":" }' > $HOME/dependencies.sh
	source $HOME/dependencies.sh
}

## OPTIONS START ##
while getopts "c:" opt; do
	case "$opt" in
		c)
			RUN_CLASS=$OPTARG
	esac
done

if [[ -z $RUN_CLASS ]];then
	RUN_CLASS=MainSimulator
fi
## END ##

## BUILD AND RUN ##
BuildDependencies
cd $BIN
scalac $SRC/*.scala -cp $DEPENDENCIES
if [[ ! $? -eq 0 ]]; then
	exit 1
fi

cd $HOME
scala -cp $DEPENDENCIES $RUN_CLASS
## END ##
