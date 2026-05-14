@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-17.0.4.1
set PATH=%JAVA_HOME%\bin;%PATH%
cd /d C:\Users\Administrator\WorkBuddy\20260501080626\bedrock_enhancement
call gradlew.bat build 2>&1
