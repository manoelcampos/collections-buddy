# Java Collections Advisor Metrics

This project currently has a single class which is used to store metrics for method calls on java.util.Collection classes.

Since the agent needs to store static data into such a class that will be used by the application using the agent, that class must live in a dedicated jar which is loaded in the Bootstrap class loader.
This way, classes in such a jar are visible for both agent and the application it's instrumenting.

If such classes are loaded in different classloaders
and store internal data, despite they will be the same classes loaded
twice, it's as if they were different classes.
Therefore, static attributes will have different values on these classes
living in different classloaders.

The metrics jar is loaded using ByteBuddy's `instrumentation.appendToBootstrapClassLoaderSearch()` method.

## References

- https://github.com/raphw/byte-buddy/issues/597
- https://github.com/raphw/byte-buddy/issues/1082
