#create a jar file.
#exemple of use : ./jar.sh Formiko
# cd target/classes/.;jar cfm $1.jar ../../manifest.txt *;mv $1.jar ../../.;cd ../..
mvn versions:set -DnewVersion="$(cat version.md)"; mvn package
mv target/Formiko-$(cat version.md)-jar-with-dependencies.jar Formiko.jar
