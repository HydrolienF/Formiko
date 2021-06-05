#File used to make 4 new realise (witout java or with java in Windows, Linux & mac)
#echo "javadoc"
#./javadoc.sh
mvn -ntp compile
./run.sh cleanFolder .
echo "to .jar"
./jar.sh Formiko
#jarsigner -keystore monStore -signedjar FormikoTemp.jar Formiko.jar signature
#nom = name + version
echo "choose name"
#nom=$(./v2.sh)
nom=Formiko$(cat version.md)
nomL=$nom"Linux"
nomM=$nom"Mac"
nomW=$nom"Windows"
if [[ -e out/ ]]; then
  echo "clear out"
  rm out/ -fr
fi
mkdir out/
# rm -fr out/$nom
# rm -fr out/$nomW
# rm -fr out/$nomL
# rm -fr out/$nomM
# If data need to be upload.
echo "data"
# cp -r data/ out/.
zip -qr data.zip data

mkdir out/$nom
echo "cp .jar, README.md, LICENSE.md & version.md"
mv Formiko.jar out/$nom/.
cp README.md out/$nom/.
cp LICENSE.md out/$nom/.
cp version.md out/$nom/.

mkdir out/$nomW
mkdir out/$nomL
mkdir out/$nomM
cp -r out/$nom/* out/$nomW/.
cp -r out/$nom/* out/$nomL/.
cp -r out/$nom/* out/$nomM/.
mkdir out/$nomW/java/
mkdir out/$nomL/java/
mkdir out/$nomM/java/

echo "";
echo "download jlink/";
./run.sh download "https://github.com/HydrolienF/JRE/releases/download/1.0.1/jlink.zip" jlink.zip
./run.sh unzip jlink.zip jlink/
echo "cp jlink & launcher"
#unzip -qq jlink.zip
cp -r jlink/jWindows/* out/$nomW/java/
cp -r jlink/jLinux/* out/$nomL/java/
cp -r jlink/jMac/* out/$nomM/java/
#rm -fr jlink/
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

cd ..

echo "-----------------------"
ls -l out/
echo "-----------------------"

#echo "setVersion for the web site"
#cd ~/Formiko/HydrolienF.github.io/docs
#./authentification.sh
#./setVersion.sh \"$(echo `expr substr $nom 8 20`)\"
