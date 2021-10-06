**liste des commandes utiles**

grep -r -i -l '//@a' ./src/main/
permet de retrouver tout les fichiers qui contienne une ligne a retirer facillement

./exe.sh
lance la finition du projet et crer 3 .zip.

cat *.java package/* fenetre/* > projet.txt
Permet de regrouper tout le texte du projet.

jlink --module-path " jmods,out" --add-modules "java.desktop,java.base,jdk.crypto.ec" --output java
Permet de créer notre petit java.

jpackage --input in -d out --name Formiko --main-jar Formiko.jar --main-class fr.formiko.formiko.Main --app-version $(cat version.md) --description "Formiko, a game about ant." --icon tools/icon.ico --runtime-image jlink/jWindows --win-shortcut --win-menu --win-console --license-file LICENSE.md --vendor Hydrolien -t msi
cscript add-change.js
--verbose
Permet de créer un .exe sur Windows #98
jpackage --input in -d out --name Formiko --main-jar Formiko.jar --main-class fr.formiko.formiko.Main --app-version $(cat version.md) --description "Formiko, a game about ant. Linux distribution" tools/icon.ico --runtime-image jlink/JLinux

serialver fr.formiko.usuel.souspackage.nomDuFichier
permet de recevoir une serialVersionUID spécifique a la class et de la modifier si elle est identique a une autre class.

javac -cp ~/Formiko/junit-4.13.1.jar:.:Formiko.jar test/usuel/* test/formiko/*
Permet de compiler en spécifiant un classPath (-cp) suplémentaire de celui de base. (ici cp est ~/Formiko/junit-4.13.1.jar)

java -cp junit-4.13.1.jar:. junit.textui.TestRunner NomDuTest
lance le test NumDuTest

javap nom.class
Ca affiche toutes les info de type et de méthode de la classe.

atom $(grep -r -i -l 'try' ./src/main/)
Ouvre tout les fichiers qui contiène 'try' parmi les .java des dossier spécifier.

mvn package
Lance le module de package qui génère un .jar

git checkout -b nomDeLaBrancheCible idDuCommit698F824BIQF754

git branch 14 develop
Ouvre une nouvelle branch qui ne "suis" pas la branche principale (master) mais qui "suis" la branche develop. En pratique une modification sur develop ne met pas a jour 14.

ctrl alg g (sur Atom) lance la construction de tout les setter possible en un bloc compacte comme j'aime.

ctrl maj f (sur Atom) permet de chercher et de remplacer dans l'ensemble du projet.

ctrl alt o (Atom) trie les imports d'un fichier. (grace a java-import-wiz)

ctrl alt a (Atom) avec un nom d'objet Java met dans le ctrl-c la ligne d'importe qu'il faut si elle existe dans le projet ou dans Java (avant Java 9) (Grace a java-importer)

mvn compile exec:java -Dargs="tws ../HydrolienF.github.io/docs/ -p ../HydrolienF.github.io/docs/data/language"
Lance la traduction des pages du site sur mon pc.

git rev-list --count 2.6
renvoie le nombre de commit sur la branche 2.6
En contant les commit de la branche 2.6 et la branch 2.7, on peu savoir combien on été ajouté dans la 2.7 !

git symbolic-ref HEAD --short
git rev-parse --abbrev-ref HEAD
Les 2 renvoie uniquement la branche git courante.

En c pour vérifier si il y a une erreur mémoire, compiler avec :
-fsanitize=adress

Atom package :
french-menu
java-generator (to generate setter / getter)
java-import-wiz (organize import)
java-importer (copy name of the class to import)

atom-ide-ui
ide-java
formiko.fr

Réglage princpaux a refaire
2 espace pour 1 tabulation en python.
