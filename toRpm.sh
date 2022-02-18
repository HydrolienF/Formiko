# install, remove
# sudo apt-get install ./formiko_($version)-1_amd64.deb
# sudo apt remove formiko
# print info
# dpkg-deb -I file.deb
# cd /opt/formiko/bin/
# ./Formiko

# ./updateVersion.sh > version.md
# Start of common part with toX
mvn -ntp versions:set -DnewVersion="$(cat version.md)"
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
# End of common part with toX

# TODO add a Github action that call that as toMsi.
# TODO sheck that it work on red hat OS.
jpackage --input in -d out --name Formiko --main-jar Formiko.jar --main-class fr.formiko.formiko.Main --app-version $(cat version.md) --description "Formiko, a game about ant." --icon tools/icon.png --runtime-image jlink/jLinux --license-file LICENSE.md --vendor Hydrolien -t rpm --linux-package-name formiko --linux-shortcut --linux-menu-group "Game;StrategyGame" --linux-app-category games
# TODO create toRpm.sh with same jpackage line exept "deb" option replace by --linux-app-category category string --linux-rpm-license-type type string
