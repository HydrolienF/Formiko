#File used to make 4 new realise (witout java or with java in Windows, Linux & mac)
echo "javac"
./javac.sh
echo "test"
./javact.sh
./testJunit.sh
echo "javadoc"
./javadoc.sh
echo "to .jar"
./jar.sh Formiko
#jarsigner -keystore monStore -signedjar FormikoTemp.jar Formiko.jar signature
#nom = name + version
echo "choose name"
nom=$(./v2.sh)
nomL=$nom"Linux"
nomM=$nom"Mac"
nomW=$nom"Windows"
if [[ -e out/ ]]; then
  echo "clear out"
  rm out/ -fr
fi
mkdir out/
rm -fr out/$nom
rm -fr out/$nomW
rm -fr out/$nomL
rm -fr out/$nomM

mkdir out/$nom
echo "cp data, README & .jar"
mv Formiko.jar out/$nom/.
cp -r data/ out/$nom/.
cp README.md out/$nom/.
cd out/$nom
#suppress all file that will be recreate on the computer of the user.
echo "Suppress unuse fill from data"
rm -fr data/Options.txt
rm -f data/score*
rm -f data/.~*
rm -f data/.save
rm -f data/Temps.txt
rm -fr data/sauvegarde/*
rm -fr data/image/temporaire/*
rm -fr data/image/ressourcesPack/*

cd ../..
mkdir out/$nomW
mkdir out/$nomL
mkdir out/$nomM
cp -r out/$nom/* out/$nomW/.
cp -r out/$nom/* out/$nomL/.
cp -r out/$nom/* out/$nomM/.
mkdir out/$nomW/java/
mkdir out/$nomL/java/
mkdir out/$nomM/java/

echo "cp jlink & launcher"
unzip -qq jlink.zip
cp -r jlink/jWindows/* out/$nomW/java/
cp -r jlink/jLinux/* out/$nomL/java/
cp -r jlink/jMac/* out/$nomM/java/
rm -fr jlink/
cp launcher/formiko.bat out/$nomW/.
cp launcher/formiko.sh out/$nomL/.
cp launcher/formiko.sh out/$nomM/.

echo "zip part"
cd out/
zip -qr $nom.zip $nom
zip -qr $nomW.zip $nomW
zip -qr $nomL.zip $nomL
zip -qr $nomM.zip $nomM
#zip Formiko.zip Formiko.jar data/ -r
echo "delete directory"
rm -fr $nom
rm -fr $nomW
rm -fr $nomL
rm -fr $nomM

echo "setVersion for the web site"
cd ~/Formiko/HydrolienF.github.io/docs
./authentification.sh
./setVersion.sh \"$(echo `expr substr $nom 8 20`)\"
