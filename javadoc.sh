#Generate the javadoc for the project in docs/javadoc

javadoc -doctitle "Docs of Formiko" -version -quiet -author -private -d docs/javadoc/ $(find src/main/java -name "*.java" | grep -v fr/formiko/usuel/tests/) -locale en_US
# -bottom
