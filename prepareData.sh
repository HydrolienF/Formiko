# cp -r data/ out/.
mvn -ntp compile exec:java -Dargs="updateDataVersion $(./needToReleaseData.sh) $(./needToReleaseMusic.sh)"
./run.sh cleanFolder .
cp version.json data/.
mkdir music
mv data/stable/music/* music/
zip -qr out/data.zip data/
zip -qr out/music.zip music/
mv music/* data/stable/music/
