@echo off
set CLASSPATH=%~dp0gradle\wrapper\gradle-wrapper.jar
java -Xmx64m -Xms64m -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
