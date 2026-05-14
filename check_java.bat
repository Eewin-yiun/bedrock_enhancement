@echo off
set CLASSPATH=%~dp0gradle\wrapper\gradle-wrapper.jar
"C:\Program Files\Java\jdk-22\bin\java.exe" -Xmx64m -Xms64m -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain properties -Dorg.gradle.java.home=C:/Program Files/Java/jdk-22 2>&1 | findstr /i "java.home jdk"
echo.
echo Actual JAVA_HOME env: %JAVA_HOME%
"C:\Program Files\Java\jdk-22\bin\java.exe" -version
