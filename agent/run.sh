#!/bin/bash
clear
mvn -f "../metrics/pom.xml" clean install
mvn clean package

AGENT="target/collections-advisor-agent.jar"
java -javaagent:"$AGENT" -jar "$AGENT"
