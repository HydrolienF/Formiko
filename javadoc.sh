#Generate the javadoc for the project in docs/javadoc

javadoc -quiet -author -private -d docs/javadoc/ $(find src/main/java -name "*.java" | grep -v fr/formiko/usuel/tests/)
