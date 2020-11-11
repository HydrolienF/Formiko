#rm -rf ../../MEGAsync/.
javac -d build/main/ -cp ~/Formiko/junit-4.13.1.jar:. $(find src/main -name *.java)
#jar cfm Formiko.jar manifest.txt fr/formiko/*
#jar cfm Formiko.jar manifest.txt $(find src/main -name *.java)
#jar cfm Formiko.jar manifest.txt build/main/fr/formiko/*
cd build/main/.;jar cfm Formiko.jar ../../manifest.txt fr/formiko/*;mv Formiko.jar ../../.;cd ../..
#jarsigner -keystore monStore -signedjar FormikoTemp.jar Formiko.jar signature
mv Formiko.jar ../../Formiko/.
cp -r data/ ../../Formiko/.
cp README.md ../../Formiko/.
#nom = name + version
nom=$(./v2.sh)
cd ../../
cd Formiko/
rm -fr data/Options.txt
rm -f data/score*
rm -f data/.~*
rm -f data/Temps.txt
rm -fr data/sauvegarde/*
rm -fr data/image/temporaire/*
rm -fr data/image/ressourcesPack/*
rm -fr $nom
mkdir $nom
mv data $nom/.
mv Formiko.jar $nom/.
mv README.md $nom/.
#zip part will be done in a second time.
#zip Formiko.zip Formiko.jar data/ -r
#zip -r $nom.zip $nom/
#rm -fr $nom/
