#create a jar file.
#exemple of use : ./jar.sh
# cd target/classes/.;jar cfm $1.jar ../../manifest.txt *;mv $1.jar ../../.;cd ../..
mvn -ntp versions:set -DnewVersion="$(cat version.md)"
# rm src/main/resources/version.json
# cp version.json src/main/resources/
mvn -ntp package
mv target/Formiko-$(cat version.md)-jar-with-dependencies.jar Formiko.jar
