#command to javac main file.
#exemple of use : ./javacf.sh
javac -d build/main/ -cp ~/Formiko/junit-4.13.1.jar:. $(find src/main -name *.java)
