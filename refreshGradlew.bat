@echo off
echo Cleaning up workspace.
./gradlew.bat clean
./gradlew.bat --refresh-dependencies
./gradlew.bat setupDecompWorkspace idea
echo finished
@echo on

