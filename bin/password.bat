@echo off
if "%OS%" == "Windows_NT" setlocal

if not defined JAVA_HOME goto no_java_home
if not defined VTPASS_HOME goto no_vtpass_home

set JAVA=%JAVA_HOME%\bin\java

set PASS_JAR=%VTPASS_HOME%\jars\vt-password-${project.version}.jar
set LIBDIR=%VTPASS_HOME%\lib

set CLASSPATH=%LIBDIR%\vt-dictionary-3.0.jar;%LIBDIR%\vt-crypt-2.1.3.jar;%PASS_JAR%

call "%JAVA%" -cp "%CLASSPATH%" edu.vt.middleware.password.PasswordCli %*
goto end

:no_vtpass_home
echo ERROR: VTPASS_HOME environment variable must be set to VT Password install path.
goto end

:no_java_home
echo ERROR: JAVA_HOME environment variable must be set to JRE/JDK install path.

:end
