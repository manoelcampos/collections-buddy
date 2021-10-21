#!/bin/bash
clear
mvn -f ../pom.xml clean install

AGENT="target/collections-advisor-agent.jar"
java -javaagent:"$AGENT" -jar "$AGENT"
