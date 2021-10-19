#!/bin/bash
clear
AGENT="target/collections-advisor-agent.jar"
mvn clean package

java -javaagent:"$AGENT" -jar "$AGENT"

