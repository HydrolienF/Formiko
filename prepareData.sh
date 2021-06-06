echo "data"
# cp -r data/ out/.
mvn -ntp compile exec:java -Dargs="updateDataVersion"
cp version.json data/.
zip -qr out/data.zip data/
