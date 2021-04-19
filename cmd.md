**liste des commandes utiles**

grep -r -i -l '//@a' ./src/main/
permet de retrouver tout les fichiers qui contienne une ligne a retirer facillement

./s.sh
actualise les donnée de nombre de lignes, mots et char des fichiers .java.

./actualiserJDM.sh
actualise le jdm en y ajoutant ce que contient trad/out.txt

./exe.sh
lance la finition du projet et la déplace dans un fichier synchronisé sur MEGA.nz

cat *.java package/* fenetre/* > projet.txt
Permet de regrouper tout le texte du projet.

jpackage --input in -d out --name Formiko --main-jar Formiko.jar --main-class fr.formiko.formiko.Main --app-version 1.32.4 --description "Formiko, a game about ant." --icon icon.ico --resource-dir in/data --runtime-image jlink/jWindows --win-shortcut --win-dir-chooser --win-menu --win-console
Permet de créer un .exe sur Windows #98
jpackage --input in -d out --name Formiko --main-jar Formiko.jar --main-class fr.formiko.formiko.Main --app-version 1.33.3 --description "Formiko, a game about ant. Linux distribution" --icon icon.ico --resource-dir in/data --runtime-image jlink/JLinux

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

git checkout -b nomDeLaBrancheCible idDuCommit698F824BIQF754

git branch 14 develop
Ouvre une nouvelle branch qui ne "suis" pas la branche principale (master) mais qui "suis" la branche develop. En pratique une modification sur develop ne met pas a jour 14.

ctrl alg g (sur atom) lance la construction de tout les setter possible en un bloc compacte comme j'aime.

ctrl maj f (sur atom) permet de chercher et de remplacer dans l'ensemble du projet.

ctrl alt o (atom) trie les imports d'un fichier.
