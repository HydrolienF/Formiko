# cp -r data/ out/.
mvn -ntp compile exec:java -Dargs="updateDataVersion $(./needToReleaseData.sh) $(./needToReleaseMusic.sh)"
./run.sh cleanFolder .
cp version.json data/.
mkdir music
mv data/stable/musics/* musics/
mv "musics/Ride of the Valkyries - Wagner.mp3" data/stable/musics/.
zip -qr out/data.zip data/
zip -qr out/music.zip musics/
mv musics/* data/stable/musics/
