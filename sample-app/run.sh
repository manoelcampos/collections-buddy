#!/bin/bash
clear
AGENT_DIR="../agent"
AGENT_NAME="collections-advisor-agent"
AGENT_JAR="$AGENT_DIR/target/$AGENT_NAME.jar"

mvn -f "../metrics/pom.xml" clean install
mvn -f "$AGENT_DIR/pom.xml" clean install
mvn clean package

# You can indicate the package in which to track calls to java.util.Collection objects
# by executing, for instance:
# java -javaagent:$AGENT_JAR=com.manoelcampos -jar target/sample.jar
# This way, only Collections inside classes from package com.manoelcampos will be tracked.
java -javaagent:$AGENT_JAR -jar target/sample-app.jar
