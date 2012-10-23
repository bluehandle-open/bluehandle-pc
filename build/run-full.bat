@echo off
set JRE_HOME=D:\Java\jdk1.5.0_05
set PATH=%JRE_HOME%\bin
set CLASSPATH=.;%JRE_HOME%\lib;%JRE_HOME%\lib\rt.jar

java -jar -verbose:class bluetoothHandle.jar >> class.txt
pause