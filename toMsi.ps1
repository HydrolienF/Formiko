#set-executionpolicy remotesigned to alow powershell script

mvn -ntp versions:set -DnewVersion="$(cat version.md)"
rm src/main/resources/res/version.json
cp version.json src/main/resources/res/
mvn -ntp compile
mvn -ntp package
mv target/Formiko-$(cat version.md)-jar-with-dependencies.jar Formiko.jar

mkdir in
mv Formiko.jar in/
cp LICENSE.md in/
cp README.md in/
cp version.md in/
cp version.json in/
jpackage --input in -d out --name Formiko --main-jar Formiko.jar --main-class fr.formiko.formiko.Main --app-version $(cat version.md) --description "Formiko, a game about ant." --icon tools/icon.ico --runtime-image jlink/jWindows --win-shortcut --win-menu --win-console --license-file LICENSE.md --vendor Hydrolien -t msi
rm -r in

$version = cat .\version.md
cscript addLaunchToMsi.js $version
