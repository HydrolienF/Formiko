@echo off
echo game launch on Windows
"java/bin/java.exe" -XX:+UseZGC -XX:MinHeapSize=512m -XX:SoftMaxHeapSize=2G -XX:MaxRAMPercentage=90 -jar Formiko.jar
