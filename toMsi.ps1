#set-executionpolicy remotesigned to alow powershell script

# Start of common part with toX
mvn -ntp versions:set -DnewVersion="$(cat version.md)"
mvn -ntp compile
mvn -ntp package
mv target/Formiko-$(cat version.md)-jar-with-dependencies.jar Formiko.jar

# no -f here
# rm -r in
mkdir in
mv Formiko.jar in/
cp LICENSE.md in/
cp README.md in/
cp version.md in/
cp version.json in/
# End of common part with toX

jpackage --java-options "-XX:SoftMaxHeapSize=2G -XX:MaxRAMPercentage=90" --input in -d out --name Formiko --main-jar Formiko.jar --main-class fr.formiko.formiko.Main --app-version $(cat version.md) --description "Formiko, a game about ant." --icon tools/icon.ico --runtime-image jlink/jWindows --win-shortcut --win-menu --win-console --license-file LICENSE.md --vendor Hydrolien -t msi --win-upgrade-uuid "1468751e-078f-4fab-80e4-fa15e9b364b3"
rm -r in

$version = cat .\version.md
cscript addLaunchToMsi.js $version
