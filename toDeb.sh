# sudo apt-get install ./formiko_2.19.39-1_amd64.deb
# sudo apt remove formiko
# cd /opt/formiko/bin/
# ./Formiko
./updateVersion.sh > version.md
mvn -ntp versions:set -DnewVersion="$(cat version.md)"
# generate jar
# mv Formiko.jar in/
cp LICENSE.md in/
cp README.md in/
cp version.md in/
cp version.json in/
jpackage --input in -d out --name Formiko --main-jar Formiko.jar --main-class fr.formiko.formiko.Main --app-version $(cat version.md) --description "Formiko, a game about ant." --icon tools/icon.png --runtime-image jlink/jLinux --license-file LICENSE.md --vendor Hydrolien -t deb --linux-package-name formiko --linux-shortcut
