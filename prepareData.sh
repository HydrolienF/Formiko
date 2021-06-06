# cp -r data/ out/.
mvn -ntp compile exec:java -Dargs="updateDataVersion"
./run.sh cleanFolder .
cp version.json data/.
zip -qr out/data.zip data/
