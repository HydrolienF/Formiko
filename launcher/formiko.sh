echo game launch on Linux; chmod 700 java/bin/java;./java/bin/java -XX:+UseZGC -XX:MinHeapSize=512m -XX:SoftMaxHeapSize=2G -XX:MaxRAMPercentage=90 -jar Formiko.jar $@;
