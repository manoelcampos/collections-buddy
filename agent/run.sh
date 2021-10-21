#!/bin/bash
clear
mvn -f ../pom.xml clean install | grep -v INFO

AGENT="target/collections-advisor-agent.jar"
java -javaagent:"$AGENT" -jar "$AGENT"
