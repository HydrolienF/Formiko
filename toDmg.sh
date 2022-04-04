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

# TOFIX shortcut don't seem's to work neether in menu, nor in terminal.
# TOFIX icon is not used on tab screen or in menu screen
jpackage --java-options "$(cat .mvn/jvm.config)" --input in -d out --name Formiko --main-jar Formiko.jar --main-class fr.formiko.formiko.Main --app-version $(cat version.md) --description "Formiko, a game about ant." --icon tools/icon.icns --runtime-image jlink/jMac --license-file LICENSE.md --vendor Hydrolien -t dmg --mac-package-name formiko --mac-package-identifier "1468751e-078f-4fab-80e4-fa15e9b364b3"
# --linux-package-deps can call vlc
