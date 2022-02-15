# sudo apt-get install ./formiko_2.19.39-1_amd64.deb
# sudo apt-get install ./out/formiko_2.19.46-1_amd64.deb
# sudo apt remove formiko
# cd /opt/formiko/bin/
# ./Formiko
./updateVersion.sh > version.md
# Start of common part with toMsi
mvn -ntp versions:set -DnewVersion="$(cat version.md)"
rm src/main/resources/res/version.json
cp version.json src/main/resources/res/
mvn -ntp compile
mvn -ntp package
mv target/Formiko-$(cat version.md)-jar-with-dependencies.jar Formiko.jar

rm -fr in
mkdir in
mv Formiko.jar in/
cp LICENSE.md in/
cp README.md in/
cp version.md in/
cp version.json in/
# End of common part with toMsi
jpackage --input in -d out --name Formiko --main-jar Formiko.jar --main-class fr.formiko.formiko.Main --app-version $(cat version.md) --description "Formiko, a game about ant." --icon tools/icon.png --runtime-image jlink/jLinux --license-file LICENSE.md --vendor Hydrolien -t deb --linux-package-name formikoludo --linux-shortcut
