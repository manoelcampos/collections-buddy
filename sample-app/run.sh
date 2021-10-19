#!/bin/bash
clear
AGENT_NAME="collections-advisor-agent"
AGENT_DIR=".."
AGENT_JAR="$AGENT_DIR/target/$AGENT_NAME.jar"
mvn -f "$AGENT_DIR/pom.xml" clean package
mvn clean package

# You can indicate the package in which to track calls to java.util.Collection objects
# by executing, for instance:
# java -javaagent:$AGENT_JAR=com.manoelcampos -jar target/sample.jar
# This way, only Collections inside classes from package com.manoelcampos will be tracked.
java -javaagent:$AGENT_JAR -jar target/sample-app.jar
