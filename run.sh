#!/bin/bash
clear
AGENT="target/collections-buddy-agent.jar"
mvn clean package

java -javaagent:"$AGENT" -jar "$AGENT"

