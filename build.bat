@echo off

@REM delete the build directory and out.jar
rmdir /s /q build
if exist out.jar del out.jar

@REM create the build directory
mkdir build
echo Created build directory

@REM compile the java files
set classpath=
for /d %%i in (lib\*) do set classpath=%%i\lib\*;%classpath%

javac -classpath %classpath% -d build src/*.java
echo Compiled java files

@REM create manifest file if it doesn't exist
if not exist manifest.txt echo Main-Class: Main > manifest.txt

@REM create the jar file
jar cfm out.jar manifest.txt -C build .
echo Created out.jar

@REM run the jar file and pass all arguments
set modulepath=
for /d %%i in (lib\*) do set modulepath=%%i/lib;%modulepath%
java --module-path %modulepath% --add-modules javafx.controls,javafx.fxml -jar out.jar %*