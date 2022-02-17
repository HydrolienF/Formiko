# sudo apt-get install ./formiko_2.19.39-1_amd64.deb
# sudo apt-get install ./out/formiko_2.19.46-1_amd64.deb
# sudo apt remove formiko
# dpkg-deb -I file.deb print info about a .deb
# cd /opt/formiko/bin/
# ./Formiko
./updateVersion.sh > version.md
# Start of common part with toMsi
mvn -ntp versions:set -DnewVersion="$(cat version.md)"
# rm src/main/resources/res/version.json
# cp version.json src/main/resources/res/
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
# TODO add a Github action that call that as toMsi.
# TOFIX shortcut don't seem's to work neether in menu, nor in terminal.
# TOFIX icon is not used on tab screen or in menu screen
jpackage --input in -d out --name Formiko --main-jar Formiko.jar --main-class fr.formiko.formiko.Main --app-version $(cat version.md) --description "Formiko, a game about ant." --icon tools/icon.png --runtime-image jlink/jLinux --license-file LICENSE.md --vendor Hydrolien -t deb --linux-package-name formiko --linux-shortcut --linux-deb-maintainer hydrolien.f@gmail.com --linux-menu-group "Game;StrategyGame" --linux-app-category games
# TODO create toRmp.sh with same jpackage line exept "deb" option replace by --linux-app-category category string --linux-rpm-license-type type string
# --linux-package-deps can call vlc
