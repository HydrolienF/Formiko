#Generate the javadoc for the project in docs/javadoc

javadoc -author -private -d docs/javadoc/ -cp ./junit-4.13.1.jar:. $(find src/main -name *.java)
