#!/bin/bash
clear
mvn -f ../pom.xml clean install | grep -v INFO

AGENT_DIR="../agent"
AGENT_NAME="collections-advisor-agent"
AGENT_JAR="$AGENT_DIR/target/$AGENT_NAME.jar"

# You can indicate the package in which to track calls to java.util.Collection objects
# by executing, for instance:
# java -javaagent:$AGENT_JAR=com.manoelcampos -jar target/sample.jar
# This way, only Collections inside classes from package com.manoelcampos will be tracked.
java -javaagent:$AGENT_JAR -jar target/sample-app.jar
