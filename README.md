# Java Collections Advisor [![Build Status](https://github.com/manoelcampos/collections-buddy/actions/workflows/maven.yml/badge.svg)](https://github.com/manoelcampos/collections-buddy/actions/workflows/maven.yml)

## Introduction

## Build and Running

Run `com.manoelcampos.collectionsadvisor.AgentEntry` class or
execute the following commands inside the root dir:

```bash
mvn clean package
java -jar target/collections-advisor-agent.jar   
```

## References

- [ByteBuddy Tutorial](https://bytebuddy.net/#/tutorial)
- [Runtime Code Generation with Byte Buddy](https://blogs.oracle.com/javamagazine/post/runtime-code-generation-with-byte-buddy)
- [Fixing Bugs in Running Java Code with Dynamic Attach](https://www.sitepoint.com/fixing-bugs-in-running-java-code-with-dynamic-attach/)
- [How to Make Java More Dynamic with Runtime Java Code Generation (1/2)](https://www.jrebel.com/blog/runtime-java-code-generation-guide)
- [Using Byte Buddy for Annotation Driven Java (2/2)](https://www.jrebel.com/blog/using-byte-buddy-for-annotation-driven-java)
- [Rafael Winterhalter - The definitive guide to Java agents (JPoint 2019)](https://youtu.be/OF3YFGZcQkg)
