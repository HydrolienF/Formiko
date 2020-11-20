#command to javac test file.
#exemple of use : ./javacft.sh
javac -d build/test/ -cp ~/Formiko/junit-4.13.1.jar:.:build/main $(find src/test -name *.java)
