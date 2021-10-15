#!/bin/bash
clear
AGENT="target/data-structure-analysis-agent.jar"
mvn clean package

java -javaagent:"$AGENT" -jar "$AGENT"

