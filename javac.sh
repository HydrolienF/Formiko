#command to javac main file.
#exemple of use : ./javac.sh
#javac -d build/main/ -cp junit-4.13.1.jar:. $(find src/main -name *.java)
mvn -ntp compile
