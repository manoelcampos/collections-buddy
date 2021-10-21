# Java Collections Advisor [![Build Status](https://github.com/manoelcampos/collections-buddy/actions/workflows/maven.yml/badge.svg)](https://github.com/manoelcampos/collections-buddy/actions/workflows/maven.yml)

## 1. Introduction

## 2. Build and Running

In order to just try the agent you have two options:

- run the `com.manoelcampos.collectionsadvisor.AgentEntry` class from the [agent](agent) dir in your IDE;
- or execute the [agent/run.sh](agent/run.sh) script.

### 2.1. Running an independent app

If you are building your own app and want to load the agent during application startup, check the [sample-app](sample-app) project. Execute the [sample-app/run.sh](sample-app/run.sh) script to build and run the app using the agent.

The sample app has no agent code and the instrumentation happens in runtime.

## 3. Results after the agent instrument your app code

## 4. References

- [ByteBuddy Tutorial](https://bytebuddy.net/#/tutorial)
- [Runtime Code Generation with Byte Buddy](https://blogs.oracle.com/javamagazine/post/runtime-code-generation-with-byte-buddy)
- [Fixing Bugs in Running Java Code with Dynamic Attach](https://www.sitepoint.com/fixing-bugs-in-running-java-code-with-dynamic-attach/)
- [How to Make Java More Dynamic with Runtime Java Code Generation (1/2)](https://www.jrebel.com/blog/runtime-java-code-generation-guide)
- [Using Byte Buddy for Annotation Driven Java (2/2)](https://www.jrebel.com/blog/using-byte-buddy-for-annotation-driven-java)
- [Rafael Winterhalter - The definitive guide to Java agents (JPoint 2019)](https://youtu.be/OF3YFGZcQkg)
