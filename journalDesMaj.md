**Le journalDesMaj est le lieu où toutes les informations de modification du projet sont indiquée**

#Version 0.2.x : x
Un seul grand fichier contenait toutes les fonctions utile au déroulement du programme.

#Version 0.3.1 : 30/01/2020
Utilisation d'une carte a 2 dimensions sous la forme d'un tableau de tableau d'entier.
Regroupement des Fourmi dans 1 fourmilière.
Les Fourmi possèdent des coordonnées, une id, une Grille.
Les fourmilière possèdent des coordonnées et une id.
Un mode de déplacement aléatoire a été mis en place. La vérification des sorties de carte est fonctionnelle.
Actuellement aucun système de sauvegarde n'as été mis en place.
Un mode de débogage a été mis en place. Il faudrait que celui ci soit activable est désactivable pendant l’exécution du programme

#Version 0.4.0 : 04/02/2020
La carte fonctionne a condition d'être carrée (sinon problème de limite yM et xM).
Le mode de déplacement par getInt fonctionne.

#Version 0.4.1 : 05/02/2020
Création d'une class Partie et d'une class Joueur de façon a pouvoir lancé une nouvelle partie de façon simple dans le fichier Main.java
Les joueurs possèdent un id, une fourmilière un pseudo et un boolean ia.
Je veux remplacer le tableau de Fourmiliere par un tableau de joueur.

#Version 0.4.2 : 05/02/2020
on peu désormais changé un pseudo, afficheTout( Joueur tableau [] )
Il manque, pour l'instant, une class point et la possibilité de lancé une partie avec des caractéristiques défini.

#Version 0.4.3 : 07/02/2020
L'affichage et le fonctionnement du jeu marche avec les joueur Ia ou non.

#Version 0.5.0 : 07/02/2020
Le package tableau est désormais très complet pour les tableau de String.
Le package erreur permet désormais d'affiché des erreurs et des alertes.
la Grille s'affiche maintenant avec les Fourmiliere sous la forme "F"+id

#Version 0.5.1 : 07/02/2020
La Fourmi possède une durée de vie et un compteur age.
La possibilité d'élimination de Fourmi est en cours de développement

#Version 0.5.2 : 07/02/2020
Les fourmis peuvent désormais mourir et meurent lorsque leur age atteint leur ageMax.

#Version 0.5.3 : 10/02/2020
L'objet Point a été rajouté avec toutes ses fonctions de base associée ainsi que sa fonction estDansCarte.
Cela a permis une simplification du système de validation des déplacements. Désormais on crer le point d'arrivé si la fourmi peut ce déplacer. Et si celui ci est correcte on bouge la Fourmi.

#Version 0.5.4 : 10/02/2020
les Fourmiliere ont désormais un Point.
L'affichage refonctionne.
fix d'un bogue, les fourmis des ia sont désormais obligé de bouger et peuvent ce déplacer dans toutes les directions (9 compris).
Les pseudos par défaut des joueurs sont a revoir.

#Version 0.6.0 : 10/02/2020
Essaie d'implémentation du mode de démarrage "Ancien jardin" sans succès. Dans la version 7 la fonctionnalité les fourmi commence avec un age Aléatoire sera faite a partir d'un seul blocs. Après la création de toutes les Fourmi.

#Version 0.7.0 : 10/02/2020
Les insectes feront prochainement leur apparition.
Les fourmi commence désormais a un Age aléatoire et elle sont entre 1 et nbrDeFourmiMax.
Suppression d'un bogue majeur qui avait lieu a la mort d'une fourmi, le tableau étant réduit d'une case, on testait une case de trop.

#Version 0.7.1 : 11/02/2020
Les joueurs humains commence a 1 fourmi dans le mode de Jeux l'ancien jardin.
Comme une seule Grille est utiliser, il a été considéré préférable que chaque fourmi n'ai pas d’abribus Grille.

#Version 0.7.2 : 11/02/2020
Les insecte, sources de nourriture sont désormais présent avec pour attributs un point et une quantité de nourritureFournie a leur mort. Ils peuvent etre ajouter et sont afficher en texte et dans le tableau !

#Version 0.7.3 : 11/02/2020
Les insecte apparaissent désormais aléatoirement sur la carte juqu'à ce qu'il soit trop nombreux.

#Version 0.7.4 : 12/02/2020
L'énorme méthode d'affichage de la carte a été décomposé en 3 sous fonctions de récupération des Fourmiliere puis Fourmi puis Insecte (Ces 3 fonctions sont sous une seule fonction qui récupère dans un tableau de String tt les éléments de la case)

#Version 0.7.5 : 12/02/2020
Les insectes sont désormais mangeable par les fourmis, si la fourmi est sur la meme case que l'insecte.

#Version 0.7.6 : 12/02/2020
Les fourmis sont désormais capable de ce déplacer vers un des insectes voisins si il y en a.

#Version 0.8.0 : 13/02/2020
Les fourmi dispose de 3 actions possible par tour.
Ces actions peuvent être un déplacement ou une action tuer un insecte.

#Version 0.8.1 : 13/02/2020
Les insecte peuvent désormais être mort a l’apparition (insecte volant), ou vif (avec un temps de vie défini).
Ils peuvent également ce déplacer aléatoirement.
Les insectes n'apparaissent plus sur des cases ou sont déjà présente des créatures a l'exeption du premier tour, lorsque la liste d'insecte n'existe pas encore.
De nouveaux getteur permettent d'obtenir la Grille g, le tableau de Joueur listeJoueur et la liste d'insecte GInsecte de la partie dans Main.java

#Version 0.9.0 : 13/02/2020
Les insecte et les fourmis ayant beaucoup de point commun, il a été choisi de les faire hérité de la classe ObjetSurCarteAId.

#Version 0.9.1 : 13/02/2020
Les insectes et les fourmis sont désormais des Creature, celle ci ayant :
- 1 id, (l'id des fourmi est commun a celui des insectes.)
- 1 age et un ageMax (potentiellement déja atteint pour les insectes)
- 1 boolean estMort qui vaut vrais si l'age a atteint ou dépasser l'ageMax.
- Toutes les get / set néssésaire pour gérer ses variables.

De nouveau type de Fourmi sont en projet Reine, Ouvrière, Soldate toutes héritantes de Fourmi mais avec des caractéristiques propre.
Elle pourront toutes faire partie du même tableau dans Fourmiliere !

#Version 0.9.2 : 13/02/2020
Ajout du type Reine hérité du type Fourmi.

#Version 0.10.0 : 13/02/2020
la déclaration du tableau de Joueur et de chaque fourmilière associée ne nécessite plus de grille.

#Version 0.10.1 : 13/02/2020
GInsecte et Fourmiliere hérite de GCreature, qui possède un tableau de Creature et quelque méthode élémentaire comme retirerUneCreature( Creature c);

#Version 0.10.2 : 13/02/2020
Ajout de 2 méthode a GCreature : ajouterUneCreature et retirerUneCreature.
Ses 2 méthodes sont sécurisé pour éviter les sortie si l'insecte n'est pas qu'une et une seule fois dans le tableau.
Ou si l'insecte est déja dans un tableau ou on voudrait l'ajouter.

#Version 0.11.0 : 14/02/2020
Essaie de passage des méthodes de déplacement dans une interface.
Trop dur a déboguer.

#Version 0.12.0 : 14/02/2020
Déplacement des méthodes de déplacement de Fourmi dans DeplacementDUneFourmi.
Passage de 80% des méthodes dans DeplacementDUneFourmi en private.
Utilisation de ce nouveaux modes de déplacement dans tourFourmi().

#Version 0.12.1 : 14/02/2020
Le dernier Insecte du groupe d'insecte peu a nouveau disparaître.

#Version 0.12.2 : 14/02/2020
Les insecte on leur propre tourInsecte() (dans leur class).
Une fourmi a été indiqué hors de la carte !
Correction de estDansCarte() (dans la class Point) afin d'évier que ce problème ne ce reproduise.

#Version 0.12.3 : 14/02/2020
Les insectes bénéficie pour l'instant du même mode de déplacement que les fourmis.

#Version 0.12.4 : 15/02/2020
Ajout d'un mode de ponte pour les Fourmi de type Reine.
Ce mode de ponte nécessite de la nourriture pour pondre et une Reine placé a sa fourmilière.
Les déplacements de la Reine chez les ia sont limité a :
Lorsque la fourmilière est trop peu peuplé la reine chasse aussi ainsi que lorsque elle meure de faim.

#Version 0.13.0 : 16/02/2020
La fonction d'affichage des menu est désormais dans menu.java.

#Version 0.13.1 : 16/02/2020
Le joueur peu désormais choisir si il décide de chasser sur sa case et si il veut pondre.
Les actions coute un mouvement seulement a la fin de la réalisation de l'action, dans le fichier ou ce situe l'action.

#Version 0.13.2 : 16/02/2020
le package tableau contient désormais toutes les méthodes utiles pour les tableau d'int.

#Version 0.13.3 : 16/02/2020
L'ia choisi elle aussi ses actions comme le joueur.
Le choix des ia reste assez imparfait.
La Reine pond et ne fait rien.
Les autres fourmis chassent et ce déplace pour chasser, c'est à dire qu'elles cherchent les insectes et ce déplace en conséquence.

#Version 0.13.4 : 16/02/2020
tentative d'utilisation de throws et trow dans le package readInt.
Cette utilisation devra plutôt être faite pour les fonctions ou un disfonctionnement ne peu pas ce corrigé ou demander a être manuellement corrigé (getInt()), lorsque l'erreur soulevée est fatale.
Moderniation du package read :
correction de faute de frappe et élimination d'éléments utilisé plusieurs fois de façon identique.
Précision des messages d'erreur et utilisation des méthodes du package erreur.
Création d'un système d'arrêt forcé qui renvoie ou en était le programme au moment de l'erreur fatale.

#Version 0.14.1 : 16/02/2020
Nettoyage des packages. Certain sont désormais dans inutilisé.

#Version 0.14.2 : 16/02/2020
Création de l'objet Message qui permet l'affichage des évènement du jeux (mort de fourmi, chasse, naissance, etc)

#Version 0.14.3 : 16/02/2020
Les 3 messages de base sont désormais utilisé par le programme.

#Version 0.14.4 : 16/02/2020
Découverte des type Liste Chainée, toutes les méthodes utile qui sont prédéfinie avec ses listes sont déjà définie pour mes tableaux.
Je ne changerait donc pas pour l'instant de méthode de stockage de donnée.

#Version 0.14.5 : 16/02/2020
Récupération d'une class Chrono qui permet de chronométrer le temps d'éxecution d'un programme.
Essaie de cette class pour l’exécution de 100 tours, avec 10 ia et 10 fourmi par ia. (lancementNouvellePartie(0,10,10, false,false, 100,50,50);) carte 50*50
Avec le mode débogage : 10449 ms (= milliseconde)
Sans :  1920 ms
sans l'affichage des détails des Créature : 1889 ms
sans affichage : 320 ms
Conclusion : Ce sont les méthodes d'affichage qui demande le plus de temps d’exécution.
Ajout d'une variable niveauDeDétailDeLAffichage de type byte (=1 octet).

#Version 0.14.6 : 16/02/2020
tentative d'utilisation de la généricité pour faires des méthodes de tableau éfficace avec tout les types.

#Version 0.15.0 : 18/02/2020
Malheureusment la généricité ne permet pas de traité des tableau ayant 1 seul type d'objet sans les transformer en tableau multitype.
les méthodes des tableaux resterons donc propre a chaque type.

#Version 0.15.1 : 18/02/2020
Utilisation d'une méthode de copie d'un fichier (créer par openClassRoom).
Ajout d'une sécurité en cas de tentative de copie d'un fichier dans un fichier déjà existant.

#Version 0.15.2 : 18/02/2020
Création de l'exception FichierDejaPresentException.
Utilisation de cette exception dans le package fichier, affin de vérifier qu'aucun fichier ne sera écraser lors d'1 copie.

#Version 0.16.0 : 19/02/2020
Le package lireUnFichier permet désormais la lecture d'un fichier ligne par ligne. L’exécution de ce package peu générer des erreur qui sont traité en interne.

#Version 0.16.1 : 19/02/2020
getIntDeLaLigne et getValeurDeLaLigne permet d'obtenir des données a partir d'une ligne de texte de type nomDeLaVariable : valeur
Ses 2 méthodes permettent de vérifier la compatibilité d'une sauvegarde avec la version du jeux.
Elles permettrons bientôt de charger une sauvegarde entière.

#Version 0.16.2 : 20/02/2020
2 nouvelle méthodes permettent d'obtenir les infos contenues dans une ligne représentant un tableau sous la forme 0 0,0 1,0 2;1 0, 1 1;2 0;2 1 etc
Ces méthodes sont désormais dans le package decoderUnFichier.

#Version 0.16.3 : 20/02/2020
Les méthodes de conversion de tableau et de tableau de tableau, de String a int sont fonctionnelles.

#Version 0.16.4 : 20/02/2020
Modernisation de l'affichage de la carte de la Grille. Celui ci est désormais basé sur une méthode static commune a toutes les Grilles.

#Version 0.16.5 : 20/02/2020
ajout de 2 constructeur, pour Creature et pour Fourmi, affin de pouvoir créer une fourmi et une créature avec toutes les variables prédéfinie.
Finalement tout sera dans le constructeur de Fourmi.

#Version 0.16.5 : 20/02/2020
On peu désormais créer une Fourmi a partir de ses 9 variables (7 + le x et le y de son Point).

#Version 0.16.6 : 20/02/2020
On peu désormais charger les joueurs en entier !

#Version 0.16.7 : 20/02/2020
La liste d'insecte ce rempli comme il faut.
Le système de charge d'une sauvegarde est FONCTIONNEL !

#Version 0.17.0 : 26/02/2020
Ajout des opérations sur les tableau de byte.
Utilisation du type byte pour les choix a réaliser par les Fourmi, ia et joueur.

#Version 0.17.1 : 26/02/2020
On peu désormais charger une partie depuis le menu principal.
Les sauvegardes ce nomme toutes seules si aucun nom n'est préciser ou si le nom préciser est déjà une sauvegarde.

#Version 0.17.1 : 26/02/2020
essaie de déplacement de la grosse méthode lireUneSauvegarde dans un package sans succès. Car il est Impossible d'appeler des fonctions de Main dans un package.

#Version 0.17.2 : 26/02/2020
Les sauvegardes prennent en compte le nombre de tour restant.

#Version 0.17.3 : 26/02/2020
Les sauvegarde prennent en compte les boolean.

#Version 0.18.0 : 28/02/2020
La listeJoueur est devenue un objet GJoueur gj.
L'affichage et la sauvegarde sont a nouveau fonctionnel.

#Version 0.18.1 : 28/02/2020
correction d'un bogue qui bloquait la possibilité d'avoir des fourmilière avec un nombre d'éléments variable.

#Version 0.18.1 : 28/02/2020
Les actions des fourmi choisie par les joueurs ne font plus de boucle infini.
Plus de boucle infini dans les tours de fourmi.

#Version 0.18.2 : 28/02/2020
Pour une raison inconnue la méthodes de chois des actions des fourmis ne marche plus avec les ia.
C'est l'occasion de changer le fonctionnement de ce système. Désormais les fourmis ont un mode.
Les Reines on 3 par défaut et les Fourmi 0.

#Version 0.18.3 : 28/02/2020
Le nouveau système de mode, beaucoup plus simple, est en place.
Il y a encore un problème lors de l'entrée de valeur dans les champ textuel.
Le jeu fonctionne très bien avec des ia. les ouvrière chasse et les reines attende.
Sauf que les fourmis n’attrape jamais de proie ;(

#Version 0.18.4 : 28/02/2020
Les fourmi en mode 0 chasse a nouveau mais il y a une boucle infinie dans le système de chasse ;(.
La boucle infinie arrive lors de l'affichage d'un message ligne 85 de Fourmi.java.

#Version 0.18.5 : 04/03/2020
la fonction problématique getFourmiliere a été fixée.

#Version 0.18.6 .getSuivant(): 04/03/2020
Le Système de chasse est désormais dans sa propre interface !
La chasse semble marcher pour les ia comme pour les joueurs.
Il y a des soucis de pointeur nul si le joueur tente de faire pondre une ouvrière.

#Version 0.18.7 : 04/03/2020
Réorganisation des constructeurs en principal et auxiliaire. Tout les auxiliaires pointant vers le principal.
ajout d'1 class ChasseNull qui affiche une erreur si une créature sans méthode de chasse cherche a chasser.

#Version 0.18.8 : 04/03/2020
ajout des autres class Null avec succès.
Le constructeur des insectes est maintenant organiser avec un Principal et des auxiliaires.

#Version 0.18.9 : 04/03/2020
Ajout de la fonctionnalité trophallaxie. Pour l'instant la class TrophallaxieFourmi ne marche pas.
Normalisation des fonctionnalités sous la forme FonctionaliteOject. ex : DeplacementFourmi

#Version 0.19.0 : 11/03/2020
Les chargement de sauvegarde fonctionne a nouveau.
Ajout des fonctions ObjetToString pour coder les variables dans le fichier txt.

#Version 0.19.1 : 11/03/2020
Les 2 1a ligne fonctionnes.
Certaines méthodes de ObjetToString fonctionne : version, nombre de tour et grille.
Ce serait mieux d'avoir un objet LString et CString.

#Version 0.19.2 : 11/03/2020
Création des objet LString et CString.
Ajout des get set et de la méthode ajoute();
La sauvegarde ce fait désormais avec la liste chaînée !

#Version 0.19.3 : 11/03/2020
Début de la méthode de sauvegarde des joueurs.
Fonctionne pour les Fourmiliere et les Joueurs (manque les fourmis).

#Version 0.19.4 : 11/03/2020
La sauvegarde peut etre créer (sans la liste d'insecte qui est encore a l'états actuel un tableau.)
A faire : Créer des listes chainées simple (pour insecte) et double (pour fourmi), avec pour ObjetAId (qui donne ObjetSurCarteAId puis Creature)
A faire : transformer GInsecte et GCreature pour avoir des listes chainées, simplement ou doublement chainées.

#Version 0.20.0 : 16/03/2020
Les class GInsecte et CInsecte semble fonctionnelle a l’exception de l'affichage de la carte.

#Version 0.20.1 : 22/03/2020
la création d'un GInsecte fonctionne désormais.

#Version 0.20.2 : 22/03/2020
Le constructeur Insecte vérifie que les élément de Main dont-il peut avoir besoin on été créer (cad ne sont pas null).
L'affichage ne boucle plus et fonctionne.

#Version 0.20.3 : 22/03/2020
La class GInsecte a désormait un getFin et des méthodes qui utilise les Points

#Version 0.20.4 : 22/03/2020
Les joueurs humains sont en mesure de chasser mais pas les ia, qui ne fond rien de leur tours...

#Version 0.21.0 : 22/03/2020
Les ia chasse en fait très bien. Seule les reines reste passivent.
On peut effectuer une sauvegarde en cours de partie !

#Version 0.21.1 : 30/03/2020
Modification des méthode chasse et chasser dans ChasseFourmi de façon a ce que les Insectes qui fournissent le plus de nourriture soit chassé d'abord.
Ajout d'un système de difficulté qui actuellement n'agit que sur l'intéligence en chasse.

#Version 0.21.2 : 30/03/2020
tentative d'ajout d'une class permettant l'affichage des méthodes et des class mères.
Si chaque fourmi avait un lien vers son joueur ce serait beaucoup plus simple de retrouver estIa, le joueur lui même ou la fourmilière.

#Version 0.21.3 : 31/03/2020
ajout d'une possibilité de retour sur les menuNouvellePartie et menuChargerUnePartie.
la date de création de la partie et de création de sa sauvegarde sont désormais dans les fichiers de sauvegarde.

#Version 0.22.0 : 31/03/2020
Le système de sauvegarde crée désormais un fichier avec La date dans le nom sous la forme jj-mm-aa hh:mm:ss.

#Version 0.22.1 : 31/03/2020
Ajout d'un système de sauvegarde plus basique au cas ou le système de sauvegarde normal soit défaillant.
Ce système permet la création d'un fichier "sauvegardeEnCasDErreur.txt" dans le bon répertoire et permet a la sauvegarde de s'effectuée quand même.

#Version 0.22.2 : 31/03/2020
Ajout d'un lien de chaque fourmi vers ça fourmilière.
Ajout d'un lien de chaque Fourmiliere vers le joueur qui la possède.

#Version 0.22.3 : 31/03/2020
Elimiation de la méthode Main.getFourmiliere désormais trop gourmande en oppération par rapport a la méthode f.getFourmiliere();
Organisation des constructeur Joueur en 1 principal et 2 auxiliaires.

#Version 0.22.4 : 3/04/2020
Remplacement du tableau gf de Fourmiliere par une liste chainées GCreature.
Modification de beaucoup les fonctions utilisant des fourmis et réécritures de certaine fonctions.

#Version 0.22.5 : 3/04/2020
Actuellement seules les fonctions pour faire jouer les fourmis dans GCreature et plus bas sont incomplète.
LA méthodes ajouter fin produit l'erreur NullPointerException au lacement du jeux.

#Version 0.22.6 : 3/04/2020
La création des éléments et leur affichage de liste fonctionne.
En revanche la méthode getFourmiSurLaCase et la méthode getFourmiliereSurLaCase ne fonctionne pas puisque pas encore modifié !

#Version 0.22.7 : 7/04/2020
ajout d'une méthode pour retirer une créature d'un groupe de Creature (utile pour les futures Case et les actuelle Fourmiliere).
ajout de contructeur et de get set pour les méthodes Case et CCase.

#Version 0.22.8 : 7/04/2020
ajout de la class GCase.
On peut désormais afficher une ligne de GCase et Créer une ligne de GCase.

#Version 0.22.9 : 7/04/2020
Le système de création de la carte (vide) fonctionne !
Les Fourmiliere possèdent désormais une CCase associée.

#Version 0.22.10 : 7/04/2020
On peu désormais afficher toutes les cases sous forme de liste avec 1 ou 0 Fourmiliere et une liste de Creature. Pour l'instant seule les Fourmi on été testé.
Lors de la Création d'une Fourmi et d'une Fourmiliere. celle ci est placé sur la carte.
La nouvelle méthodes d'affichage de la carte est fonctionnelle.

#Version 0.22.11 : 10/04/2020
le fichier Main ne fait plus référence a Grille.java
Les insecte n'utilise plus Grille.
Les joueurs apparaissent a nouveau dans l'ordre.

#Version 0.22.12 : 10/04/2020
La création de Fourmi et de joueur entraîne un placement d'élément a la case 0,0 uniquement.
Les insectes ce place comme il faut.
Les déplacement ne sont pas encore adapté au changement de la version 0.22.*

#Version 0.22.12 : 10/04/2020
Les placements des fourmilière sont désormais aléatoire.
Une carte carré n'est plus nécessaire !
Les éléments sont désormais visible sur la carte (il manque encore la légende).

#Version 0.22.13 : 14/04/2020
La légende fonctionne.
fréquement le contenu d'une CCase est null !

#Version 0.23.0 : 15/04/2020
la fonction getCCase était en cause du bogue de case null. L'erreur a été corrigé.
les déplacement des Fourmi sont a nouveau possible.
Le jeu fonctionne a nouveau ! Sauf sauvegarde et charge d'une sauvegarde.

#Version 0.24.0 : 18/04/2020
Les créatures n'hérite plus d'un point mais une CCase de la part de ObjetSurCarteAId.
Cela permet entre autre de tester facilement si la case a d'autre occupant.
Les déplacements ne marche tjr pas.

#Version 0.24.1 : 19/04/2020
Les déplacement sont corrigés.

#Version 0.24.2 : 19/04/2020
La légende existe désormais a nouveau. (a partir d'une GString)
Le système de ponte fonctionne.
Les ia ce déplace mais la carte ne change pas et les reine ia boucle dans leur action.
Les fourmilières peuvent apparaître sur des fourmilière déjà existante et les faire disparaitre.

#Version 0.24.3 : 19/04/2020
Le bug de déplacement lié a l'inversion des déplacements vers 4 ou vers 6 a été corrigé.
La carte s'actualise bien en directe en case de débogage. (En différé sinon.)
Le problème actuel est que certaine créature ne sont pas a leur place sur la carte.
Parfois ce bug entraine un NullPointerException. parfois il ne se passe rien.
Le problème est probablement identifié ! Les fourmis ce retire mal sans que cela provoque d'erreur. ET ce retrouve donc en plusieur fois sur la carte !
Seule les fourmis en fin de chaine on ce soucis.

#Version 0.24.4 : 19/04/2020
les fourmis de base arrive a toutes bougée et la carte suis leur déplacement.
En revanche les insectes ne sont pas visible sur la carte.

#Version 0.24.5 : 19/04/2020
Le jeux fonctionne a nouveau ! Les ia peuvent pondre et chasser(elles trouvent des proix).
Et la carte suis tt ce qui ce passe !
L'affichage n'est pas encore parfait (encore bcp de débug).

#Version 0.25.0 : 24/04/2020
Les cases testé au hasard pour le placement d'1 Insecte ne sont jamais vide. Les cases qui devrait l'être on toujours le même insecte.
Pour l'instant la fonctionnalité de placement des insecte sur une case vide a été retirée.
ajout d'1 fonction pour retirer les joueurs.
ajout d'un message en cas de victoire d'1 des joueurs, c'est a dire si celui ci ce retrouve seul survivant ou si le partie ce termine.

#Version 0.25.1 : 24/04/2020
Pour une simplification du codage, toutes les listes chainées ont des méthodes get ou set pour les valeurs communes : précédent, suivant, contenu.

#Version 0.26.0 : 24/04/2020
Les fourmi dont l'age est négatif passe bien leur tour tout en grandissant et en perdant de la nourriture.
La reine peu pondre des oeuf qui n'apparaissent qu'1 seule fois dans la fourmilière et sur la carte !

#Version 0.26.1 : 24/04/2020
la méthode de déplacement vers la fourmilière a été implémenté et est utilisée pour toutes les fourmis en mode 3.

#Version 0.26.2 : 25/04/2020
Les ia sont bien capable de rentrer a leur fourmilière.
Ajout de l'utilisation de cette habilité en cas de Chasse fructueuse. (passage en mode 1.)
L’objectif est de permettre la chasse pour nourrir la reine.

#Version 0.26.3 : 25/04/2020
Les fourmis qui chasse rentre désormais toutes seule si elle sont très chargé en nourriture.
Il manque juste de système de trophallaxie pour rendre le jeux fonctionnel sur la durée. (+ les nourrice ce serait cool).

#Version 0.27.0 : 25/04/2020
tentative de placement de tout les fichier .java dans des packages.
Celle si semble avoir fonctionné sans aucun soucis.

#Version 0.27.1 : 25/04/2020
La TrophallaxieFourmi fonctionne ! Les ia, l'utilise. Lorsque les proie sont abomdante, la reine peut pondre 3 oeux par tour.

#Version 0.27.2 : 25/04/2020
le système de fin de partie est désormais fonctionnel. Celui ci stop le jeu lorsqu'un joueur gagne et demmande si l'on veux continuer la partie quand même.
Les insectes pourrait être amélioré de façon conséquente.

#Version 0.28.0 : 28/04/2020
ajout d'une variable stade allant de -2 oeuf a 0 fourmi permettant de choisir si la fourmi a ou non la possibilité de chasser, ce déplacer etc.
le type n'est désormais plus une chaine de char mais un byte.

#Version 0.28.1 : 28/04/2020
Ajout de la class Espece qui permet de définir les caractéristique de base des Fourmi de cette espèce.
Les fourmis ce vois désormais attribué un ageMax et une nourritureMax dépendant de leur espèce et variant de + ou - 10%

#Version 0.28.2 : 28/04/2020
La Reine prend désormais soin de ne pas laisser mourrir les oeux, les larves et les nymphes.
Mais celle ci devrait ne ce soucié que des larves !
Pour éviter qu'elle ne reste bloqué sur un oeuf il faut faire des filtres qui permettetn d'obtenir seulement les fourmis nourrissable. (C'est a dire des filtre en fonction du type et en fonction de la fourmilière de la fourmi)
De façon générale il faudrait que les fourmis ai le réflexe de Trophallaxer avec les moins bien nourrie.

#Version 0.28.2 : 28/04/2020
La class Espece prend en compte les 4 stades d'évolution de la fourmi

#Version 0.28.3 : 28/04/2020
Les ia sont réglés pour que leur Reine garde de la nourriture pour nourrir les oeux a éclore.
Le reste de la nourriture servent a pondre.

#Version 0.28.4 : 30/04/2020
Les ouvrières aide la Reine si une partie du couvain est délaissé.
on peu désormais trié le couvain et le couvainSale.
Il faudrait rajouter un filtre estBienSurLaCase(CCase cc)
La propreté n'est pas encore bien prise en compte.

#Version 0.29.0 : 05/05/2020
Révision du mode 3 des fourmis Ia de façon a ce que ce mode prenne en compte les nourrices et pas juste les Reine.
Désormais ce mode est traité par la fonction nourrirEtNétoyer qui elle meme utilise pondreF, nétoyer et nourrir.

#Version 0.29.1 : 05/05/2020
Le jeu est fonctionnel avec la nouvelle façon de mourir, par infection de bactérie si on est sale.
La Reine est capable de géré seule un bon nombre de Creature du couvain mais elle est vite dépassé.

#Version 0.29.2 : 05/05/2020
Les ouvrières porte secours a la Reine si celle ci n'arrive pas a s'occuper du couvain toutes seule. La Reine évite égallement de trop pondre (si le pourcentageDeCouvain > 70).
Résolution d'un problème important, les fusions de GCreature ne prenait pas en compte le fait que le groupe a ajouter était potentiellement vide.
Les fourmis font parfois des trophallaxies a des créatures Null. // A résoudre.

#Version 0.30.0 : 06/05/2020
Ajout de la class Phéromone. Celle ci permettra l'identification des fourmis.
Les nourrices ne sont plus débordée.
Les utilisation de getFere().getGc() on été remplacer dans Fourmi.java par getCCase().getContenu().getGc().filtreAlliés() (lorsque c'était nécessaire.)
Mais cella n'as pas permi d'évité le soucis de NullPointerException lors de Trophalaxie.
Le mode 1 des fourmis est désormais compris dans le mode 3.
Le problème causé par la trophallaxie du mode 1 semble résolu.

#Version 0.30.1 : 06/05/2020
Si une fourmi n'as plus d'action a effectuer en mode 3, elle repasse en mode chasse (ou elle attend si c'est la Reine).
Si la Reine a beaucoup de nourriture elle ne prend plus en compte le %age max du couvain.

#Version 0.30.2 : 10/05/2020
Test pour valider la méthode equals de Phéromone.
Après correction les Fourmi d'une même Fourmiliere ce reconnaisse equals en Phéromone a 0, 1 ou 2 de différenceTolléré.

#Version 0.30.3 : 10/05/2020
Le nombre d'action des Reines est désormais géré dans l'espèce.
Les modes auto chasse et nourrirEtNétoyer sont désormais activable par les joueurs.
Ces 2 modes auto sont géré comme pour les Ia et peuvent permettre au joueur de moins microgéré ses tours.
Idéalement il faudrait pourvoir maintenir le mode auto choisi et surtout pourvoir le désactiver.

#Version 0.30.4 : 15/05/2020
La trophallaxie fonctionne pour les joueurs.
Ajout de condition de vérification des variable dans TrophallaxieFourmi de façon a évité les erreurs si le joueur donne des nombres incoérent pour l'id et la nourritureDonnée.
Toutes les Créature possède désormais une variable nourriture et une variable nourritureMax.
Cela permettra des trophallaxies entre créature et non pas seulement entre Fourmi.

#Version 0.30.5 : 15/05/2020
Correction d'un bogue qui faisait jouer les insecte autant de fois qu'il y avait d'insecte tout les tour a la place d'une seule fois.
Les insectes peuvent désormais ce nourrire avant de chercher a ce déplacer.

#Version 0.30.6 : 15/05/2020
Création de la classe Personalité. Celle ci comporte 4 variable vitale qui servirons au Fourmi :
rapidité, force, armure et agressivité.
Ces 4 variable ne devrais pas beaucoup changé durant la vie d'une Fourmi.
Tout comme les 2 variable phéromonale qui permette de reconnaître une Fourmi de la même Fourmiliere et une fourmis de la même super collonie de Fourmi.

#Version 0.30.7 : 15/05/2020
Les Case on de la nourriture pour insecte (une certaine quantité en stock et un maximum alléatoire).
Les insectes consomme bien la nourriture sur les cases !

#Version 0.31.0 : 16/05/2020
Ajout de coût des actions en point d'action dans Espece.
Les action coûte désormais un montant propre a l'Espece de point d'action.

#Version 0.31.1 : 17/05/2020
Séparation nette dans Espece des variable commune a tout les individu de l'Espece et des autres.
Les points d'actions étant plus nombreux, désormais le nombre d'action varie aussi.
La réinitialisation des actions n'est plus la même, maintenant si une Fourmi a utilisé trop de point au tour d'avant elle perd a ce tour ce qu'elle avait pris au tour précédent. (testé et aprouvé).
Les actions sont désormais des byte. (limité a 63 pour les ouvrières et 127 pour les Reines.)

#Version 0.31.2 : 17/05/2020
Le constructeur complet d'Espece est en place ainsi que sont équivalent avec des int (convertit en suite en byte).
une fois que la ligne 34 de Espece marchera, on pourra Créer une Espece a partir d'une longue liste de 20 élément (string ou int), regroupé dans une String séparer par des ';'.

#Version 0.31.3 : 18/05/2020
Main ne contient plus une Espece attribué a toute les fourmis mais un GEspece créé a partir du fichier Espece.txt.

#Version 0.31.4 : 18/05/2020
Les informations des espèces comporte désormais la taille est sont accessible via la commande libreoffice --calc data/Espece.csv &
Pour échapper un caractère (, ou ; concidéré comme des séparateurs de case) il suffit de tapper \ avant.
Un tableau Espece.csv très complet est en court de remplissage dans data.
Le racio tour/jour est actuellement de 24 tour par jour.(1 tour = 1 heure).
Les temps de développement des fourmis ce veulent réaliste. Un mode rapide pourra être ajouter.

#Version 0.31.5 : 19/05/2020
Le tableau des Individu a ses entêtes chargé.
Les constructeur de Espece sont condenser en 1 seule et les vérification sont géré dans la class GEspece.
Il y a une ligne paramètres par défaut dans Espece.cvs de façon a ce que les cases vide soit comblé par des paramètres par défaut avant la création de l'Espece.
La création et l'affichage des espece est a nouveau fonctionnel.
Ajout d'une méthode de concaténation compacte des String contenue dans un GString.

#Version 0.31.6 : 19/05/2020
Chaque Espece a un groupe d'individu qui est rempli lors de la lecture du fichier Individu.cvs
La description des individus est complète.
Les individu peuvent être reine, male, minor, médias, major, soldate ou inconue.
Une action impossible dans le tableau des Individus s'écrira -1.

#Version 0.31.6 : 19/05/2020
toutes les utilisation de e.getEspece() poite désormais jusqu'a l'endrois voulu.
Le jeu marche a nouveau.
La class Reine a été retiré.

#Version 0.31.7 : 19/05/2020
ajout d'un constructeur Fourmi avec les paramètres minimaux en connaissant l'Espece.
Les fourmi qui évolue en imago ne sont capable de réaliser les 4 actions possible actuellement que si leur Individu dans leur Espèce en est capable.
Installation d'un système rudimentaire de choix de l'Element pondu (Le joueur écrit le type (et celui ci ce corrige tt seul si une erreur pouvait advenir.)).

#Version 0.32.0 : 20/05/2020
Création d'un constructeur Creature suffisamment complet pour créer une Fourmi issu d'une espece.
Les Fourmiliere tire aléatoirement une Espece du tableau tableauDesEspecesAutorisée contenue dans main. Celle ci sert a choisir la Reine de leur fourmilière.
Les Fourmi créer au début sont d’abord créer comme des oeuf puis modifié au stade -1 puis évolué. Cela permet d'actualiser leur caractéristiques comme si elle venait d'éclore.
Le jeu fonctionne avec 3 ia qui sont autonome. L'affichage du déroulement du jeux par la Console est top !

#Version 0.33.0 : 20/05/2020
Suppression de beaucoup de texte en commentaire désormais inutile comme celui des constructeur de Fourmi.
Passage de 3623 à 3340 ligne des fichier .java faisant parti du package principal.

#Version 0.33.1 : 22/05/2020
Ajout de la class graine ayant pour but de nourrir les fourmis granivore.
Ajout de la class GGraine et CGraine (presque identique a GInsecte), et ajout d'un GGraine a chaque Case.
Ajout d'une variable type (de type byte) pour les cases. Le type infura sur la gérération de nourritureInsecteParTour et la génération de Graine. (+ le visuel).

#Version 0.33.1 : 22/05/2020
Ajout d'une méthode de choix de la graine sur la case.
Les fourmi ia ne connaisse la nourritureFournie des graines que si la difficulté est au moins normale (2). Cela n'impacte pas les joueurs humains.

#Version 0.33.2 : 22/05/2020
Le jeu devrait etre fonctionnel avec les graines. Les fourmis granivore les collectes et les ramènes a la fourmilière.
Mais les graines ne sont pas posé par les fourmis.
Normalement les graines sont bien posé.
Tout les un petit temps, pour une raison inconue le jeu s'arrète dans une boucle infinie.
Le bogue ce produit losque d'une fourmi collecte des graines a 1 case de sa fourmilière.
Le problème est probablement lié au fait que certaine graine ce retrouve a plusieurs endroit a la fois.

C'est bien la collecte de graine qui pose tout ses problèmes. Reste a trouver comment les résoudres.

#Version 0.33.3 : 24/05/2020
La méthode de Suppression des graines ne marchait pas lorsqu'on faisait graine g.equals(int i), méthode qui n'éatit défini nule par mais bon...
Le bogue a été identifier précisément, lors de la recherche de graine, le GGraine de la case sur laquelle était la fourmi était modifié.
Les fourmis ne sont plus capable de prendre des graines a distance.
Les graines sont bien supprimer sur les cases.
Les sousis de duplications de graine semble avoir été résolue depuis que le GGraine utilisée pour la recherche ne fonctionne plus qu"avec des copies.

#Version 0.33.4 : 24/05/2020
Grace a des try catch les bordures ne pose plus de problème a la détection d'Insecte ou de Graine.
Les fourmis ia pense a ceNétoyer toutes seules quelque soit leur mode.

Il faut qu'elle pense a piocher dans le grenier quand elle on faim.

#Version 0.33.4 : 27/05/2020
Modification de la méthode getGrainePlusDeNourritureFournie (et getGrainePlusDeNourritureFournieSansDureté) de façon a ce que les graines ouverte ne soit pas prise en compte quitte a renvoyer null.
Une vérification est effectuée avant de renvoyer la meilleure graine. Si celle ci ne rempli pas un des critères on renvoie null et on supose que c'est parce ce que la gMax est la 1a graine du GGraine.

#Version 0.34.0 : 27/05/2020
ajout de méthodes d'interaction avec le jeu en cours dans triche.java
Ajout de setter qui n'avait pas encore d'utilités.
Les class Fourmi, Insecte, Creature et Joueur on leur setteur utilisable depuis triche.java

#Version 0.34.1 : 27/05/2020
Ajout d'un fichier contenant le texte en français et d'une Fonction (chargerLesTraductions) de chargement de celui ci dans une Map<String,String>
Ajout des premier texte traduit en français puis en esperanto.

#Version 0.34.2 : 29/05/2020
les textes s'affiche par défaut avec une majuscule en début de phrase mais peuvent aussi ne pas en avoir.
Les fichier read et Main sont traduit en 3 langues.

#Version 0.34.3 : 29/05/2020
les erreurs des tableaux sont synchronisées et géré par le nouveau package g qui redirige vers Main.
Le package read redirige ses get vers g.java
Les éléments de débogage ne seront pas traduit pour l'instant.
Les fichiers ou il reste des donnée a traduire non prioritaire sont les suivants : ecrireUnFichier, fichier, FichierDejaPresentException, math
Tout les autre .java de package on été transformer.

#Version 0.35.0 : 01/06/2020
Ajout d'Exception personnalisée.
Le fichier GCreature est censé gérer toutes les exceptions de type liste vide et élément introuvable dans la liste.
Cela rajoute pas mal de ligne au fichier. Je ne suis pas sûr de l’utilité de cette méthode. Elle permet tout de même de faire des erreur.erreur géréral, et donc plus facile a traduire.
Pour éviter d'avoir a entourer d'un try catch toutes les fonctions qui utilise une liste potentiellement vide just en desous de la fonction fonctionX throws ... ce trouve la fonction fonctionXE (){...

#Version 0.35.1 : 01/06/2020
Ajout des textes dans fr.txt jusqu'a DeplacementFourmi.

#Version 0.35.2 : 01/06/2020
Ajout de fonction permettant la création d'une fenetre a partir de paramètre variable.
Actuellement le nombre de bouton et le nombre de case de la carte en x et en y peuvent etre choisi.

#Version 0.35.3 : 01/06/2020
ajout d'un groupe de bouton pour les menu. Celui ci charge les textes depuis le fichier de langue.
Début de l'interface graphique. les actions des menu sont désormais déclenchable depuis la fenetre.

#Version 0.35.4 : 03/06/2020
Ajout d'une image d'arrière plan qui ce redimentionne si la fenetre change de taille.
La méthode de dessin des cases fonctionne avec la taille des cases !

#Version 0.36.0 : 03/06/2020
Le texte des bouton s'actuallise correctement.
Les panneau secondaires couvre toute la surface du panel principal.

#Version 0.36.1 : 04/06/2020
Le nombre de case afficher dépend désormais de la taille des cases. Celui ci est ajuster de façon a ce que toutes les cases dont au moins 1 pixel serait visible soit afficher.
Si le nombres de case en x ou en y est inférieur a ce que l'on devrait pouvoir afficher, on ce contente d'afficher gc.getNbrX ou Y case.
Le quadrillage noir est désormais adapté aux cases quelques soit leurs nombre et leur taille entre 50 et 500.
L'affichage est centré sur la 1a case affichée. celle ci est par défaut 0,0 mais peu changer.
La fenetre prend les dimensions de l'écran par défaut.

#Version 0.36.2 : 04/06/2020
La méthode d'affichage des cases permet d'afficher de l'herbe sur les cases.
Si une ou plusieurs fourmis sont sur une case. L'une d'entre elle est affichée sur la carte, avec la couleur associer a sa fourmilière.

#Version 0.36.3 : 04/06/2020
La grille et les images sont redimensionnable avec des boutons.
Les redimensionnements sont actuellement *1,5 ou *0,66

#Version 0.36.4 : 04/06/2020
Il est possible de déplacer la carte de bas en haut et de droite a gauche.

#Version 0.36.5 : 04/06/2020
Les boutons du PanneauZoom sont positionné dans une grilles de 3 sur 3.
Le panneau Zoom est un carré de côté 3*tailleD1Bouton.
Le PanneauZoom est placé en haut a droite de l'écran.

#Version 0.37.0 : 07/06/2020
Ajout de nouveau graphisme (coccinelle, sable et terre).
adaptation des tableaux contenant les images au graphisme.

#Version 0.37.1 : 08/06/2020
Le panneau jeu est désormais séparer entre l'interface de la carte et celle des boutons.
Lors du redimensionnement de la fenêtre le PanneauJeu change de taille, ce qui permet au PanneauZoom de ce placer ou il faut la plus part du temps (si on lui en laisse le temps).
Echec de la tentative de zoom sur le PanneauCarte plutôt que sur la carte en elle même.

#Version 0.38.0 : 08/06/2020
Le bouton center fonctionne.
Ajout des premières carte "jardin"(100*80) et potDeFeurs(20*20).

#Version 0.38.1 : 09/06/2020
la coccinelle a désormais des ombres et ai plus réaliste.
Ajout du scarabée.

#Version 0.38.2 : 09/06/2020
ajout de la class Img.
La class Img permet un affichage simple de l'image et crée 4 tableau contenant les informations de transparence et 3 niveau de couleur RGB, tout cela sous forme de tableau de byte.

#Version 0.38.3 : 09/06/2020
Les modifications des couleur des images (Fourmis) ne fonctionne que partiellement.
Le programme compte 4002 pixel a changé mais il n’effectue que 1717 modifications.
un i était a la place d'un j. Le programme fonctionne !
La fonction changerPixel(Pixel a, Pixel b) fonctionne.
tant que la sauvegarde d'image ne fonctionne pas, la modification de pixel sur image n'est pas si utile que ça.

#Version 0.38.3 : 09/06/2020
Les boutons du PanneauZoom sont désormais représenté par des images de +, -, centrer et des flèches.

#Version 0.39.0 : 10/06/2020
Les insectes s'affiche désormais sur la carte.
Les dimensions minimum de la carte on été réduite a 1 sur 1.

#Version 0.39.1 : 10/06/2020
Les images des boutons n'étant utiliser qu'a leur création. le tableau d'Image n'est plus commun au PanneauZoom.
Ajout de 3 bouton d'actions.

#Version 0.39.2 : 10/06/2020
les mouvements des Fourmis sont visible sur la carte en direct tant qu'on repaint() la fenêtre a chaque fois que nécessaire.
la partie ce déroule Normalement avec l'affichage.

#Version 0.39.3 : 10/06/2020
Les fourmis non granivore ne cherche plus a ouvrir les graines.
les insecte apparaissent de façon plus réaliste.

#Version 0.40.0 : 11/06/2020
Le PanneauAction contient désormais les actions disponible pour les fourmis. Le panneau jeu est capable de transmettre l'action choisi au fichier Fourmi.java
Les descriptions des boutons de PanneauZoom et de PanneauAction sont dans le fichier fr.txt

#Version 0.40.1 : 11/06/2020
La première étape des boutons d'actions des fourmis s’effectue désormais sur l'interface graphique.
Le temps d'attente avant de vérifier si une nouvelle action est disponible est de 0,01 seconde.

#Version 0.40.2 : 11/06/2020
Les trophallaxies sont bloquée si il n'y a pas au moins 2 créatures sur la case.
La liste des créatures de la case est affichée lors d'une tentative de trophallaxie.

#Version 0.41.0 : 12/06/2020
Grace a la class Panneau et a la class Bouton on peu transmettre plein d'informations lors du clic de souris.
Le système de synchronisation de java est sûrement plus complet que le mien.

#Version 0.41.1 : 12/06/2020
Commande a utiliser pour transformer en .jar tout le programme :
jar cvfm formiko.jar manifest.txt graphisme/*.class formiko/* usuel/*
Commande a utiliser pour lancer le jeu : (ou double clic dans le gestionnaire de fichier.)
java -jar formiko.jar

#Version 0.41.2 : 12/06/2020
faire apparaître un Panneau de bouton construit a partir d'un tableau d'entier semble bien compliqué pour l'instant.

#Version 0.41.3 : 13/06/2020
Le PanneauTInt s'affiche comme souhaitez, mais seulement lors du survol du bouton.
Le PanneauTInt s'affiche comme il faut si le tableaux d'entier est spécifier au lancement du jeux.

#Version 0.41.4 : 13/06/2020
Le PanneauTInt s'affiche lorsque cela lui est demander et avec tout les chiffres du tableau.
La créature en fin de liste n'était pas trouvée avec la méthode getCreatureParId(). C'est résolu.
maintenant notre soucis reste que les boutons avec les nombres n'affiche et ne renvoie pas ce qu'il faut.
Les bouton affiche ce qu'il faut et renvoie le numéro sur le bouton.
Les Trophallaxies marche par bouton ! (sauf la nourriture a choisir.)
Les PanneauTInt sensé être effacé ne ce réafiche plus lorsqu'on passe sur la zone ou il étaits.

#Version 0.41.5 : 13/06/2020
Les fonction utilisé dans Fourmi pour obtenir des valeurs on été déplacer a la fin du fichier pour plus de lisibilité.
On peu afficher plusieurs panneaux de choix d'id de Fourmi mais le panneau de choix de la quantité de nourriture ne fonctionne pas.

#Version 0.42.0 : 14/06/2020
Pondre fonctionne désormais avec un choix.
La Trophallaxie fonctionne, le temps d'apparition des boutons est juste un peu long.
Tous les boutons des Fourmis sont fonctionnel.

#Version 0.42.1 : 14/06/2020
Les boutons d'actions des fourmis possèdent désormais leur propre description.

#Version 0.42.2 : 14/06/2020
Le panneau d'action des fourmi devrais n'être dessiner que lorsqu'1 fourmi est sélectionné, mais pour une raison inconnue. Il ne ce dessine pas dutout avant le 1a repaint() et seulement losqu'on passe au dessus sinon...
pj.validate permet que le 1a affichage fonctionne. mais après il faut constamment passer au dessus des boutons pour les voir.
C'est probablement du au fait que la carte est afficher au dessus des boutons du PanneauAction.
hypothèse de la ligne du dessus validée.

#Version 0.43.0 : 15/06/2020
Séparation de la carte et du reste des boutons de façon a ce que les interfaces de bouton soit toujours au dessus de la carte.
Les descriptions sont chargées correctement. elle ont juste du mal a disparaître.

#Version 0.43.1 : 15/06/2020
Ajout de la méthode getFourmiParId() dans GCase avec pour objectif de permettre d'avoir une méthode de nettoyage des fourmis pour les joueurs.
La possibilité de nettoyer est associée au bouton 7.

#Version 0.43.2 : 15/06/2020
Les actions des fourmis ne s'affiche que si la fourmi est en capacité de réalisé l'action voulue.
Mais pour l'instant le PanneauAction s'affiche en double lorsqu'une nouvelle fourmi joue.

#Version 0.43.3 : 15/06/2020
Les actions des fourmi s'affiche si l'action est faisable par l'individu et que la fourmi est en capacité de la faire.

#Version 0.44.0 : 15/06/2020
L'interface de choix de pseudo est fonctionnelle.

#Version 0.44.1 : 16/06/2020
commande a utiliser pour avoir la taille du projet :
wc *.java package/* fenetre/* > statprojet.txt

#Version 0.44.2 : 16/06/2020
Les changements de couleur des fourmis sont fonctionnelles. Main les fourmi créer ne sont pas réajuster a la taille qu'il faudrait.
Les fourmi charge la couleur associée a leurs phéromones si celle ci est bien défini et blanc sinon.
Les fourmis sont de la bonne taille et bien visible sur la carte !
Les images des boutons s'adapte a la taille des boutons dans PanneauZoom et PanneauAction.

#Version 0.44.3 : 16/06/2020
les actions des fourmis incluent désormais casser une graine et manger une graine ainsi que passer le tour et supprimer la fourmi.
Les actions sont a nouveau connecter au bouton.
Le mode chasse de l'insecte sur la case ne s'affiche que si il y a un insecte sur la case.

#Version 0.45.0 : 16/06/2020
Les boutons d'actions on désormais 3 tailles 160 80 et 40pixels. Il ont également chacun une image.
Les fourmilière s'affiche sur la carte.
Faire un affichage différencié des cases et des insectes permettrait de fluidifier l'affichage a chaque tour.

#Version 0.45.1 : 17/06/2020
La taille des boutons et la langue sont modifiable dans le fichier Option.txt.
l'utilisation du cpu varie entre 1 est 12% en moyenne et la mémoire utilisée est d'environs 0.3 Go.

#Version 0.45.2 : 17/06/2020
Test au chronomètre effectué :

Pour 5 joueurs aillant 5 fourmi chaqu'un :
~10% cpu 0,35go de ram
initialisation des fichier=~100
fenêtre chargé=~1000
chargement des joueurs=~10

Pour 100 joueurs aillant 1000 fourmis chaqu'un :
~25% cpu 0,5go de ram
initialisation des fichier=~100
fenêtre chargé=~1000
chargement des joueurs=~70

#Version 0.45.3 : 17/06/2020
Un nombre trop élevé de créature déclenche une erreur stack OverFlow.
Les insecte mort ne joue plus dans le nombre maximum d'insecte.

#Version 0.45.4 : 17/06/2020
Les fourmis ia ne nétoie pas assez le couvains.
type==1 ne générai pas assez de nourrice. Le type par défaut est 3 !
Les fourmi choisissent leur mode au début de leur tour et n'en change que si le mode 3 n'est plus utile.

#Version 0.45.5 : 17/06/2020
Les fourmis gère leur mode en simultané avec le reste de la fourmilière. Losrqu'il y a peu de travail. Seule la reine travail en temps que nourrice et sinon toutes les fourmis du début de la liste passe en mode nourrice aussi.

Une fourmi infectée par une bactérie a causé une exeption ElementListeIntrouvableExeption.
Le bloc try catch qui y était a été retiré.

#Version 0.46.0 : 18/06/2020
rm -f data/langue/*
cp ../Formiko45/data/langue/*.txt ./data/langue/
Avec ces 2 commandes on réactualise les traductions au niveau d'avant.
Avec trad.copieTrads(); on peut reformater les fichiers de traductions selon le format du fichier fr.txt, tout en conservant les données présent dans les fichiers de traductions.
le fichier trad.java est dans outils pour évité d'afficher son message d'erreur a chaque javacF

#Version 0.46.1 : 19/06/2020
Le joueur peut toujours enclencher un mode automatique.
Les if permettant de retirer un bouton d'action de fourmi ne sont testé que si l'action est encore présente dans le tableau d'action.

#Version 0.46.2 : 19/06/2020
Ajout du PanneauInfo qui affiche les info de la fourmi fActuelle (la fourmi sélectionnée par le joueur).
Le panneau info ne s'affiche pas bien mais il s'affiche a l’endroit voulu.
Il semble avoir la taille voulue...

#Version 0.46.3 : 20/06/2020
Le déroulement normal des actions des fourmis ne fonctionne que si le panneau info est absent.

#Version 0.46.4 : 20/06/2020
le PanneauInfo s'affiche correctement a l’exception de sa position dans le PanneauBouton.
Les fourmis ont une méthode de description basé sur un tableau.
le package tableau a une méthode de compression des tableaux en une String.

#Version 0.46.5 : 20/06/2020
Le texte a traduire n'as plus de majuscule par défaut. La plus part des phrase on été réadaptées.
Ajout des info de description des fourmis dans les fichier de traduction.

#Version 0.47.0 : 21/06/2020
Les traductions en espéranto sont quasiment finie.
Si un joueur gagne, on lui demande si il veux continuer dans la fenêtre graphique.

#Version 0.47.1 : 21/06/2020
Les clé ne menant a rien compte dans la table de traduction. Il faut donc utiliser une autre méthode pour compter les traduction effectuée / les traduction totale.

#Version 0.47.2 : 22/06/2020
La méthode getPourcentageTraduit est fonctionnelle :
%age eo : 90
%age en : 23
%age esp : 0
%age de : 0

#Version 0.47.3 : 22/06/2020
La validation pour quitter le jeu marche désormais. Ainsi que la saisie du pseudo.
Parfois la fenêtre répond plus sans raison apparente... Corrigé. l'affichage des graines causais une boucle infinie.

#Version 0.47.4 : 22/06/2020
Il n'est pas possible d'actualiser seulement une partie du panneau jeu sous peine d'avoir une zone de l'écran ou d'ancien éléments subsiste.
La déformation de la taille de la fenêtre n'as plus d'impact sur la nettée de l'image de fond.

#Version 0.47.5 : 22/06/2020
Le PanneauInfo est réactualisé a chaque fois que la fourmi sélectionnée a tenté de faire une action.
Cela permet entre autre de voir son nombre d'action évoluer.

#Version 0.47.6 : 22/06/2020
%age eo : 100
%age en : 100
%age esp : 0
%age de : 0

#Version 0.48.0 : 23/06/2020
Le dossier temporaire est utilisé pour stocker les images de fourmi créé durant la partie. Celui ci est vidée a chaque fin normale de partie.

#Version 0.48.1 : 23/06/2020
La fonction tourner de Img fonctionne !
Les flèches du panneau zoom sont tourner dans le bon sens.

#Version 0.48.2 : 24/06/2020
Les variables fourmiOrientéAprèsDéplacement, mouvementRapide et déplacementInstantané, présente dans le fichier d'options, permettent de complexifier l'affichage ou alors de fluidifier les tours.

#Version 0.48.3 : 24/06/2020
Le PanneauTInt n'est plus qu'une extention du PanneauTX.
Le PanneauTBoolean a vue le jour.

#Version 0.48.4 : 24/06/2020
Les fourmis ont désormais une couleur non seulement lié a leur phéromone mais aussi logique et facile a retrouver. Les niveaux de couleur varie de -128 à 127.

#Version 0.48.5 : 24/06/2020
Le chargement des images ce rééfectue juste avant de lancer le jeux.
Cela permet aux fourmis de prendre leur couleur associée.

#Version 0.48.6 : 24/06/2020
Pour une raison inconnue et particulièrement énervante les bouton OUI et NON du PanneauTBoolean ne s'affiche pas !
Alors que tout marche bien avec un PanneauTInt avec 0 et 1.

#Version 0.48.7 : 24/06/2020
commande pour copier tout ce qu'il faut pour que le jeux fonctionne :
jar cfm formiko.jar manifest.txt graphisme/*.class formiko/* usuel/* outils/*
cp formiko.jar ../../../MEGAsync/.
cp -r data/ ../../../MEGAsync/.

//jar cmef manifest classPrincipale formiko.jar *.class
jar tf // liste le contenu d'un jar

Pour l'instant il est nécessaire d'avoir le fichier data a côté.
le fichier exe.sh effectue tout ce qu'il faut pour créer le .jar et le dossier data. Puis les changer en .zip et les placer dans le dossier voulu.
Commande a réalisé : ./exe.sh

#Version 0.49.0 : 24/06/2020
//jarsigner -keystore monStore -signedjar FormikoS.jar Formiko.jar signature
le fichier exe.sh demande de signer le .jar
L'annoattion si dessous est sencé indiquer que la fonction doit etre remplacer pour suivre les nottations java. Si j'ai bien compris, cette nottations permet d'évité les message d'erreur tant que la fonction qui suis n'est jamais utilisée.
@Deprecated(since = "4.5", forRemoval = true)

#Version 0.49.1 : 24/06/2020
Après de multiple test sur une plateforme Windows. Le .jar s’exécute avec un jdk libre trouver sur internet.
Le jkd standard n'est plus développé depuis la version 8 de java.
Les accents s'affichent parfaitement dans les fichier .txt mais sont parfois déformé dans le jeu.
Les graphismes des boutons sont particulièrement bogué.
MAIS le PanneauInfo ce place bien a l'initialisation de la fourmi...

#Version 0.49.2 : 25/06/2020
tree permet d'afficher l’arborescence de fichier d'une chouette façon.
Si jlink est nécessaire a la sortie d'une app fonctionnelle alors c'est parti !
1a test avec jlink fonctionnel avec un fichier .java nécessitant 1 import.

#Version 0.49.3 : 25/06/2020
Création du sous package impor avec pour objectif de lire dans chaque fichier java tout les import utilisé dans le projet.
Le fichier contenu contient tout le texte des fichiers .java du projet avec
cat *.java fenetre/* package/* outils/* > contenu.txt
Et c'est une réussite. Les imports sont bien listé grâce a cette technique.

#Version 0.49.4 : 25/06/2020
Le package GString peu désormais filter ces doublons
cela permet de n'afficher les package utilisé 1 seule fois.
La création du fichier est désormais faisable avec impor.java

#Version 0.49.5 : 25/06/2020
les requires ne sont pas connecter de façon logique avec les imports. Tentative de création d'un projet eclipse pour que les requires soit cappable de ce créer tout seul.

#Version 0.49.6 : 25/06/2020
A coup de f5 les fichiers du projet fonctionne sur Eclipse.
Eclipce est très fort pour pointé du doit des erreurs qui n'en sont pas vraiment. C'est pas grave si math est un package et un .java ...
Le projet eclipse ce lance et les éléments nom graphique s'affiche dans la console.
MAIS eclipse est incapable de récupérer les packages des bibliothèques java.

#Version 0.49.6 : 25/06/2020
Il semblerait que les modules soit beaucoup plus global que les packages.
Ainsi java.desktop regroupe javax.swing.* et java.awt.*
Tout ce trouve facilement sur la doc oracle !



javac module-info.java
//executé le programme avec la commande java pour testé :
java --module-path ./. --module moduleF/formiko.Main
//créer le .jar
jar cfm formiko.jar manifest.txt graphisme/*.class formiko/* usuel/* outils/* module-info.class

jlink --module-path $JAVA_HOME/jmods:build/modules \
--add-modules moduleF \
--output serviceapp

//afficher les dépendances.
// juste les modules
jdeps --module-path ./. -s --module moduleF
//toutes les sous liens aussi.
jdeps --module-path ./. --module moduleF

Il manque encore l'app java.

jlink --module-path /jmods --add-modules moduleF --output out

#Version 0.50.0 : 26/06/2020
Tentative de jlink

#Version 0.51.0 : 28/06/2020
./exe.sh permet de créer le .jar et de le déplacer avec une copie de data dans MEGAsync et de tout zipper ensemble.

#Version 0.51.1 : 28/06/2020
Tentative d'ajout d'une écoute de la carte pour pouvoir déplacer une fourmi avec un clic droite
ET sélectionnée une fourmi avec un clic gauche.
Le panneau info est sûrement mal placer car il ne ce redessine jamais.

#Version 0.51.2 : 28/06/2020
La fourmi sélectionnée s'affiche bien même si la case en haut a gauche n'est pas la case 0,0.
Meme des composants simple ne s'affiche pas dans le PanneauInfo.

#Version 0.51.2 : 28/06/2020
Dans une fenetre vide, en temps que contentPane, le PanneauInfo s'affiche correctement.
Sauf que pi.setBounds n'as absolument aucun effet. Le PanneauInfo ce place la ou il veut plutot qu'en x,y.

#Version 0.51.3 : 29/06/2020
La taille du PanneauInfo est fixable sans problème. Les éléments dessiner s'y affiche tous comme il faut.
Mais la position du panneau est complètement bogué. quelque soit la taille de la fenetre il ce place toujours en x=100 et y=20 grossomodo.

#Version 0.51.4 : 29/06/2020
Un JPanel a en fait déja 2 variables x et y de défini pour les pixels de placement realivement au coin de la fentre !

#Version 0.51.5 : 29/06/2020
La description de la Fourmi a été légèrement modifié. Elle est désormais récupérer dans un GString plus maléable.
Les GString on désormais une fonction length.

#Version 0.52.0 : 29/06/2020
Les variable de la partie sont désormais répartie dans l'objet Partie.
L'objet Partie contient une Carte.
Les racourci du fichier Main sont classer par groupe.

#Version 0.52.1 : 29/06/2020
L'écran de victoire ne s'affiche pas si la partie est lancé avec 1 seul joueur.
La fenêtre s'actualise lorsque la fourmi sélectionné change.

#Version 0.52.2 : 29/06/2020
Création de la liste de Message.
Chaque joueur a une liste de message (vide si le joueur et une ia.)

#Version 0.52.3 : 29/06/2020
Ajout d'une méthode d'affichage des x derniers messages adressé au joueur. X étant défini dans options.
La méthode d'affichage des Message provoque 1 NullPointerException.
Pour résoudre cette exception qui pourrait venir du lien de la fourmi vers sa fourmilière, j'ai réactualisé les liens après que la création de toutes les fere ai été effectué, sans succès.
L'exception venait de la fActuelle. Il est résolu.
Le PanneauInfo 2 ne s'affiche pas... Même si on a bien un gs avec plus d'1 ligne.

#Version 0.52.4 : 30/06/2020
Ajout d'une méthode getJoueurHumain dans une liste de joueur.
ajout de plusieurs fonction permettant au message destiné a -1 d'atteindre tout les joueurs.
Le joueur a bien une liste composé de tout les messages qui lui sont adressé directement ou qui sont adressé a tt le monde.

#Version 0.52.5 : 30/06/2020
le texte de pij ne s'affiche jamais bien... Pourtant aucune méthode static ne devrais le déréglé et on a bien vue que le GString est plein de String.

#Version 0.52.6 : 30/06/2020
Les 4 types de graines s'affiche sur la carte.
L'affichage des graines passe par une méthode qui compte le nombre de fichier s'appelant graineX.png

#Version 0.52.6 : 30/06/2020
L'affichage des boutons du PanneauAction passe par la nouvelles méthodes de chargement de tableau d'Image.
Après une 10aine de test le temps de compter les 11 fichiers images pour connaître la taille du tableau d'image a créer est négligeable.
Le chargement de pa prend entre 15 et 20 seconde lors de la 1a initialisation que l'on indique le nombre dimage ou non.
Le fichier image indique que la durée de chargement des 11 fichiers prend soit 0 soit 1 ms. un temps tout a fait acceptable.

#Version 0.52.7 : 30/06/2020
création du fichier s.sh pour réactualisé statprojet.txt.
Le projet a 3,6 mots par ligne en moyenne. C'est le même ratio que celui du projet a la version 30 et 0,07 de moins que la version 40. Je pensait que la compaction du codes, des get set par exemple. Augmenterai le nombre de mots par ligne.
Grace a l'objet Partie.java, Main a perdu 150 lignes.
Lors du chargement d'une carte a partir d'un fichier la méthode setTypes est appeler pour permettre a la carte d'avoir autre chose que des case terre/herbe.
La méthode setTypes produit des cases hostiles (3 cad sable et -qqchose cad les cases inaccessible). Cela permet de rendre la carte plus réaliste.

#Version 0.52.8 : 30/06/2020
chargerLesOptionsDe0 permet de créer un fichier d'option avec des paramètres par défaut, idéalement la langue est choisi dans le menu d’initialisation du jeu.
Lors de l'appel a chargerLesOptionsDe0, la taille des boutons est fixé en fonction de la largeur de l'écran.

#Version 0.52.9 : 30/06/2020
la fonction g.getM permet d'obtenir la valeur associer avec une majuscule en début de String.
Les fourmis tue les insectes avant de les dépecer.
Les fourmis ne prennent pas plus de nourriture sur un insecte qu'elle ne peuvent en prendre.
Le message "La fourmi "+id+" n'as plus d'action nourrirEtNétoyer a faire" n'est envoyer qu'au joueurs concerné.

#Version 0.52.10 : 30/06/2020
Suppression d'anciens fichier de tableau inutile dans data

#Version 0.53.0 : 30/06/2020
La méthode getJoueurActuel permet d'obtenir facilement le joueur dont c'est le tour. Si la fourmi qui joue est null. le joueur qui joue est null, mais cela ne provoque pas de NullPointerException dû au fait qu'on chercherai fNull.getJoueur.
L'affichage d'une case est nuageux si le joueur qui joue ne l'as jamais découverte et grisé si aucune de ses fourmi n'est a proximitée.
Les affichage des cases peuvent aussi être fixé a toujours visible.
Si aucun joueur n'est sélectionnée la carte est nuageuse.

#Version 0.53.1 : 30/06/2020
Une fonction permet de récupérer les cases à 1 de distance de chaque fourmi.
Les casesNuageuses et les casesSombres sont sensé s'initialiser puis s'actualiser lors de l'initialisation de chaque joueur dans Partie.java (pour les joueurs humain uniquement).
Les case N et S sont censé s'actualiser a chaque fin d'action de Fourmi, si la fourmi appartient bien a un joueur.
Un affichage a la console a permis de vérifier le bon fonctionnement des méthodes d'actualisation des cases SN
Il faut encore réaliser les graphismes des 2 cases et s'arranger pour que l'affichage correspondent.

#Version 0.54.0 : 01/07/2020
Une fonction de Img permet de supprimer la transparence.
Une fonction de Img permet d'augmenter ou de réduire la valeur de la transparence. un pixel déjà transparent ne sera pas impacté.

#Version 0.54.1 : 01/07/2020
grace a un boolean qui indique si les fourmis colorée on été initialisée, la création de nouveau fichier de fourmi colorée ne ce fait qu'1 fois.

#Version 0.54.2 : 01/07/2020
la carte s'assombrit a la fin de chaque tour. C'est pas plus mal. (c'est le temps que les ia/autre joueur jouent).
Les images de /temporaire/ ne sont créée qu'1 seule fois dans la partie.
PanneauZoom gère les flèches et la Partie lance initialiserFX lorsque tout les joueurs on été créés.

#Version 0.54.3 : 01/07/2020
La grille est désormais désactivable.

#Version 0.55.0 : 01/07/2020
sauvegarde des versions précédente.
la méthode ombrer de Img devrais permettre de créer une ombre sur les bords d'une zone colorée.
Pour cela il faudrait une fonctions pour regrouper dans un tableau la liste des pixels dans un rayon de x Pixel. (+1 fonction pour compter le nombre de pixel a dans un tableau.)

#Version 0.55.1 : 01/07/2020
Les images tournée de 1, 2 ou 3 quart de tour sont sauvegarder sous la forme temporaire/+nom+nombre+[b,d,g]+.png

#Version 0.55.2 : 01/07/2020
  Le clic sur le bouton fermer de la fenetre ne provoque plus de fermeture immédiate. a la place une boite de dialogue (showConfirmDialog) ce pointe..
Et si l'utilisateur clic sur oui, Main.quitter() est lancé. (ce qui vide les fichiers d'image temporaire.)
Sinon retour au jeu et reprise normale du jeu !
Une option permet d'outrepassé la validation de fermeture de fenêtre.

#Version 0.55.3 : 02/07/2020
La fermeture de la fenêtre ne contient plus que des textes traduits.
Les options forcerQuitter, dessinerGrille, nbrMessageAfficher sont désormais réglable dans le fichier Option.txt

#Version 0.55.4 : 02/07/2020
Les Objet ObjetSurCarteAId on un byte direction compris entre 1 et 9 (5 exclu).

#Version 0.55.5 : 02/07/2020
Les insectes sont désormais normalisé sous la même forme que les fourmis et les graines : I0, I1 etc.

#Version 0.55.6 : 02/07/2020
La texture des caseNuages est appliquée. le résultat n'est pas trop moche.
Les insecte, les graines et les Fourmi sont orientée !
Le jeu fonctionne aussi si les élements ne sont pas orienté.

#Version 0.55.7 : 02/07/2020
Les déplacements qui ne l'était pas encore sont désormais traduit en direction.
Les déplacements qui implique une direction (cad tous) change l’orientation de l'ObjetSurCarteAId.

#Version 0.55.8 : 02/07/2020
Les insectes mort sont marqué d'une tête de mort rouge sur la carte.
10 % des nouveaux insectes qui apparaissent chaque tours sont morts.

#Version 0.55.9 : 02/07/2020
Tant que la partie n'est pas déclarée en cours, la carte ne s'affiche pas.
Des messages de chargement s'affiche tant que les chargements ne sont pas fini.

#Version 0.55.10 : 02/07/2020
Les images des fourmis s'enregistre comme il faut dans temporaire/ mais un nombre de joueur élevé provoque des problèmes d'affichage : soit les fourmis ne s'affiche pas, soit elle s'affiche avec la couleur d'une autre fourmis.
Une partie du problème venait d'une sécurité qui au lieu d’être le nombre de fourmi était 4 tt le temps.

#Version 0.55.11 : 03/07/2020
Les fourmis qui ne s'affichait pas était celle qui regardait par le haut. Au lieu d'ignoré la fourmi 0 on ignorait les fourmi h lors du redimensionnement des images.

#Version 0.56.0 : 03/07/2020
Les boutons ont désormais un fond coloré si le Pixel pi dans Main est non null.
Les boutons n'ont plus la bordures par défaut.

#Version 0.56.1 : 03/07/2020
La taille des bordures des boutons fait désormais partie des options.

#Version 0.56.2 : 03/07/2020
Les méthode getEstAllié et getEstEnnemi permette de distinguer les fourmis allié, ennemi et neutres.
Pour l'instant les insecte sont tous considéré comme neutre, mais les fourmi non reconnue a 30 de différence le sont.
Les pastilles donne une indication visuel des liens entre la fourmi qui joue et les autres créature de la carte.

#Version 0.56.3 : 03/07/2020
les graines s'affiche avec leur pastille ouverte ou fermé.

#Version 0.56.4 : 03/07/2020
Une options de la carte permet d'afficher des cases jamais découverte en "nuageux" et les case non visible actuellement en sombre.

#Version 0.57.0 : 04/07/2020
Les boutons des menu et les autres boutons textuel on chacun leur taille de police + leur police provenant du fichier d'option.

#Version 0.57.1 : 04/07/2020
Les boutons du panneau menu sont bien placer au centre du menu.
Le jeu ce lance lorsqu'on clic sur nouvelle partie mais la fenêtre ne change pas.

#Version 0.57.1 : 04/07/2020
Si une graine pose un soucis d'affichage les autres éléments de la case sont quand même afficher.

#Version 0.60.0 07/07/2020
Après plusieurs jours d’essais infructueux, le PanneauMenu est connecté avec l'interface de jeu !
Le soucis qui m’empêchait de faire ça venait du fait que j’essayai toujours de lancer une fonction de Main dans le PanneauMenu alors qu'il faut plutot changer un booléen pour indiquer a Main qu'on peu récupérer des info puis lancer une partie.

#Version 0.61.0 07/07/2020
Le contructeur de joueur et de fourmilière n'utilise plus par défaut la carte de Main pour placer ses fourmis. La carte pouvant etre défini dans PanneauMenu ça vaut mieurx pour éviter les erreurs de placemennt des fourmilières.

#Version 0.61.1 07/07/2020
Le nombre de joueurs, d'ia et le nombre de tour peuvent être définie dans les options de partie grâce a partie.setElément(). Ses variables sont en suite utiliser pour créer les joueurs et les ia lorsque la partie a été définie dans Main. (sinon ca bug).
Les messages de chargement sont fonctionnels même si en pratique, seuls le chargement des graphismes est suffisamment long pour être visible.

#Version 0.61.2 07/07/2020
g.java renvoie le code du message si la ligne existe dans la fichier de traduction mais qu'il n'y a rien après les ":".

#Version 0.61.3 07/07/2020
Les opérations d'initialisation de la partie et d'affichage des graphismes peuvent s'afficher avec leur temps d’exécution avec débutCh() et finCh(nomDeLAction)

#Version 0.61.4 07/07/2020
une bonne partie des Sytem.out.println on été retirer pour rendre l'affichage des temps de chargement plus clair. Pas mal sont encore cacher qqpart.
grep -r -i -l '//@a' ./ permet de retrouver tout les fichiers qui contienne une ligne a retirer facillement

#Version 0.61.5 07/07/2020
un setAffLesEtapesDeRésolution() a été retiré.
Les fichiers inutilisé on été vidé. cf la version 60 pour les retrouver.

#Version 0.61.6 07/07/2020
La taille de police1 a maintenant un effet sur toutes les Desc par défaut.
La police et le font colorée d'1 Desc peuvent être modifier via des setteurs.

#Version 0.61.7 07/07/2020
La page PanneauNouvellePartie est en cours de création.
Tout les éléments qui doivent dépendre de la taille de la police en dépendent.

#Version 0.61.8 07/07/2020
Le Panneau EtiquetteJoueur permet d'afficher un pseudo et une JComboBox (liste défilante), avec pour options Joueur, Ia ou fermé.
Sélectionné qqchose dans la liste défilante change la valeur des 2 booléen qui définissent si l'EtiquetteJoueur doit etre prise en compte (pour créer un joueur ou 1 ia.) ou pas.
Une liste des EtiquetteJoueur doit permette d'afficher l'ensemble des joueurs et des ia au lancement d'une partie.
Lorsque une étiquette est changé a ia, sont pseudo devient un pseudo aléatoire.
Si elle est changer a joueur le pseudo est "".
Les paramètres par défaut d'une partie sont 1 joueur et 3 ia.

#Version 0.61.9 07/07/2020
Les étiquettes peuvent être ajoutée ou retirée facilement.
L'EtiquetteJoueur a un champ de pseudo modifiable

#Version 0.62.0 08/07/2020
Le PanneauNouvellePartie contient désormais la liste de tt les joueurs grâce a 1 PanneauGEtiquetteJoueur.
Les étiquettes que l'on ferme disparaissent.
Si la dernière étiquette devient non fermé une autre est ajouté à sa suite.

#Version 0.62.1 08/07/2020
Le nombre de tour est récupérer dans l'interface graphique.
Les noms des fichiers de la carte sont récupérés et ajoutés a la liste des cartes disponible (traduit si la traduction existe).

#Version 0.62.2 08/07/2020
GEtiquetteJoueur peu ce définir avec int x. Par défaut il comporte 1 joueur x Ia puis 1 champ vide.
L'ajout de this.setLayout(null); a permit a quelque soucis d'affichage d'être résolu.

#Version 0.62.2 08/07/2020
La méthodes iToS ne prend en compte que les chiffres au dessus de 4 char a convertir.
Ainsi les nombres écrit "34 567" sont bien converti.

#Version 0.62.2 08/07/2020
Le nombre de tour et la carte sont bien impacté par les choix dans l'écran de nouvelle partie.

#Version 0.62.3 08/07/2020
Les joueurs sont bien ceux choisi dans la page de nouvelle partie ! (avec Pseudo et boolean ia correcte.)
La carte est bien la carte sélectionné dans la JComboBox.
Le jeux fonctionne correctement.

#Version 0.62.4 08/07/2020
Les fourmis sont bien affiché quelque soit le mode de lancement de la partie.

#Version 0.62.5 08/07/2020
Les Phéromone peuvent donnée la Color qui leur est associé.
Les Phéromone peuvent donner leurs code couleur associer sous forme de String.
Les Phéromone peuvent être contruit avec un code couleur.
Si un code couleur n'est pas correcte, il est remplacé par un code aléatoire et une alerte est déclenché.

#Version 0.62.5 08/07/2020
La couleur est désormais une variable choisissable pour chaque joueur et ia.

#Version 0.62.5 08/07/2020
Un message d'erreur faisait boguer les cases avec des insectes. C'est résolu.

#Version 0.62.6 09/07/2020
Les EtiquetteJoueur sont présenté sous la forme d'un rectangle au bord arrondi (avec bordure).
Le texte devient blanc si la couleur est trop foncé pour que le noir soit lisible.
Le pseudo par défaut des joueurs est g.get("joueur")+" "+id de l'étiquette.

#Version 0.62.7 09/07/2020
Les couleurs ne devrais pas changer lors du passage de joueur a ia. (En pratique le if associé entraine d'autre problème d'affichage.)
Si il y a plusieurs joueur dans la partie, un message indique au joueur suivant que c'est le début de son tour.

#Version 0.63.0 09/07/2020
Commande pour avoir plein d'info sur la commande java.
java -XshowSettings:properties
Commande pour lancer le programme depuis le module.
java --module-path ./. --module moduleF/formiko.Main

jlink --module-path /jmods --add-modules moduleF --output out

Chemin pour jmods
usr/lib/jvm/java-11-openjdk-amd64/jmods
Peut-être que prendre la mini app java développé sur Windows (en version linux) pourrait suffire pour faire fonctionner le projet sur linux.
Ça n'as pas marcher...

#Version 0.65.0 10/07/2020
Le choix de la difficulté peut s’effectuer sur le PanneauNouvellePartie.

#Version 0.65.1 10/07/2020
Le joueur peut choisir si la carte est dévoilé ou pas au début de la partie.
Le joueur peut choisir si les créatures ne seront visible que lorsque ses fourmis sont proche ou tout le temps.

#Version 0.65.2 10/07/2020
La taille de la desc dans PanneauBouton est plus ou moins grande en fonction de la longueur du texte a afficher.



#Version 0.66.0 15/08/2020
Reprise du développement du jeu après plus d'un mois d'absence.
salir() prend désormais en compte la difficulté. par défaut de seuil dangereux et de 50.
Avec une difficulté faible les joueurs humains ont - de 50 et les ia + de 50 et vis versa avec une difficulté élevée.

#Version 0.66.1 15/08/2020
La page de lancement de partie affiche les 6 difficultés disponibles.
Le niveau de difficulté est récupéré exactement comme il est définie sur la page de lancement de partie.

#Version 0.66.2 15/08/2020
Tout les endroit ou getDifficulté() est utiliser ont été modifier pour correspondre au nouveau niveau de difficulté. (càd ChasseFourmi, CollecteFourmi et Fourmi).
En normal les ia chasse les insectes les plus intéressants sur la case ou elle sont.
En difficile les ia chasse les insectes les plus intéressants qu'elle voie.
Idem pour la collecte.

#Version 0.66.3 15/08/2020
la commande suivante permet de regrouper tout le texte du projet pour faire une recherche groupé.
cat *.java package/* fenetre/* > projet.txt

#Version 0.66.4 15/08/2020
Le temps depuis le lancement de l'appli est affiché a la fermeture.

#Version 0.66.5 16/08/2020
Les fourmis non-imago sont affiché correctement sur la carte.
Le couvains ne meurt plus si sont age atteint le maximum. Normalement il évolue juste avant.

#Version 0.66.6 16/08/2020
Les messages de mort des fourmis s'affiche systématiquement. 7 message de mort existe en français avec un code de 0 a 10 pour les morts sans etre tué. et de 10 a 20 pour les mort causés par un tueur.
Les mots clé contiennent des accents et sont désormais un peu mieux trié en français.

#Version 0.66.7 16/08/2020
Les fichiers de trad sont actualiser par rapport a celui en français et les sujet qui subisse l'action peuvent être signalez en esperanto par g.get("n"), ce qui sera remplacé par la chaine vide dans toutes les autres langues.

#Version 0.67.0 16/08/2020
Les temps en temps qu'oeuf, larve et nymphe dépende de la difficulté, il varie actuellement entre 1/1,4 et 1,6 fois le temps normale.
Les œufs sont modifié dans PondreReine et les autres stade dans Fourmi.évoluer.
La partie a une variable vitesseDeJeu par défaut a 1.
Cette vitesse est directement connecté au temps de vie (ou de croissance) des 4 stades de la vie des fourmis.

#Version 0.67.1 18/08/2020
Un entier compris entre 0 et gs.length() doit permettre de changé l'élément par défaut d'une JComboBox dans le fichier GString.
Grâce a ses fonctions la difficulté par défaut est désormais "normale".

#Version 0.67.1 18/08/2020
Une EtiquetteChoix permet de créer facilement une JComboBox et une légende associer a partir d'un GString et de la clé pour g.get() de la légende.

#Version 0.67.2 18/08/2020
La vitesse de jeu peut être choisis dans la page PanneauNouvellePartie du plus rapide au plus lent.
La vitesse de jeu varie entre 0,25 et 4.
La vitesse de jeu est exprimé sous la forme de nom d'insecte plus ou moins rapide.

#Version 0.67.3 18/08/2020
pendant le chargement de la partie seulement PanneauChargement est visible.
Pendant le chargement de la partie les messages de chargements arrive sur la page de chargement.

#Version 0.67.4 18/08/2020
Le mode plein écran s'active et ce désactive dans le fichier Options.txt.

#Version 0.68.0 19/08/2020
Les menus textuel inutilisé de Main.java on été retiré.
Tout les Panneau devrons ce caler sur les dimensions du PanneauPrincipal.

#Version 0.68.1 20/08/2020
Le fichier d'options n'est pas compris dans le .zip, cela permet au jeu de trouver tout seul la dimension idéale de la police et des boutons.
La taille des boutons du menu dépend a 100% des dimensions du PanneauPrincipal.

#Version 0.68.2 20/08/2020
Les sous Panneau de PanneauPrincipal on toujours la taille de PanneauPrincipal ou une taille plus petite qui leur est propre.
La taille des boutons en jeu dépend de la taille de l'écran lors du lancement de la 1a partie. Elle peut ensuite être modifiée.

#Version 0.68.3 20/08/2020
Les cases assombrit et nuagie sont désormais bien affiché même si la case en au a gauche n'est pas la case 0,0.

#Version 0.68.4 20/08/2020
Les zoom et dézoom sont un peu moins fort *3/4 ou *4/3.

#Version 0.68.5 20/08/2020
Les évolutions de fourmis utiliser pour créer des fourmis adultes au tour 1 ne sont plus afficher par la console.

#Version 0.68.6 20/08/2020
Le jeu ne cherche plus a affiché les fourmi 0 ET l'affichage des fourmis est correcte.

#Version 0.69.0 21/08/2020
javafx fonctionne avec un suite de commande ou l'on utilise le répertoire en question :
le chemin vers les documents :
export PATH_TO_FX=~/Documents/javafx-sdk-15/lib
la commande javac :
javac --module-path $PATH_TO_FX --add-modules javafx.controls HelloFX.java
la commande pour lancer le programme
java --module-path $PATH_TO_FX --add-modules javafx.controls HelloFX

#Version 0.69.1 21/08/2020
La fermeture de la Fenetre s'accompagne d'un bloc try cach au cas ou quelque chose ce déroulerai mal.

#Version 0.69.2 21/08/2020
L'écoute des touches fonctionne dans Fenetre grâce a une sous class Touches

#Version 0.69.3 21/08/2020
Les actions ont toutes une touche prédéfinie enregistré sous forme lisible si c'est un char de l'alphabet (maj et min)(+0a9) et les char courant.
la class chargerLesTouches permet de récupérer une HashMap<String,Integer>.
Cette HashMap permet de passer une action intelligible en français comme Main.key("chasse") a la touche qui y correspond.

#Version 0.69.4 21/08/2020
pas mal de Panneau ne sauvegarde plus leur panneau supérieur et préfère utiliser les Main.getPx

#Version 0.70.0 21/08/2020
Modification légère de KeyThread et de Touches.

#Version 0.71.0 22/08/2020
permet de vérifier que le moduleF fonctionne.
java --module-path ./. --module moduleF/formiko.Main
jlink --module-path ./. --add-modules moduleF --output out
jlink --add-modules java.desktop --output out


L'affichage de HelloWorld fonctionne avec l'app rudimentaire créer avec :
jlink --add-modules java.base --output out
lancement de HelloWorld avec le micro java.
out/bin/java HelloWorld

sudo apt install openjdk-14-jre-headless


peut-etre que pour pouvoir utilisé une bibliothèques de java il faut le .sdk et pas le .jmods !


#Version 0.74.0 27/08/2020
Changement de sessions ubuntu pour la version 20.04, version de javac 13.0.3, jlink ne marche toujours pas pour autre chose que java.base avec la même erreur.
Le PanneauAction est défini vide au lancement de la partie.
Le chargements des images des boutons ce fait désormais a partir d'1 seule méthode synchronized.
L'option chargement pendant les menu a été implémenté.

#Version 0.74.1 27/08/2020
Les méthodes de lancement et de stop des chrono pointe vers une méthode plus générale.
Si le chrono n'est pas préciser on prend celui du thread principal.
Le premier Th extends de Thread fonctionne !

#Version 0.74.2 27/08/2020
Garder les graphismes tourné autre que les F permet de réduire de moitié le temps nécessaire au chargement des graphismes avant le lancement de la partie.
Si cette opération est toute fois faite, elle se déroule en 1 seconde pendant que le joueur est dans les menus.

#Version 0.75.0 27/08/2020
plusieurs fonction ont été déplacer vers Th affin d'être exécutée en arrière plan.
la class ini permet de réaliser les actions demander par Th.

#Version 0.75.1 27/08/2020
Les Options chargement pendant les menu, garder les fichier tourné et attendre après le chargement de la carte on été ajoutée.
Les 2 première augmente les performance au lancement du jeu et de la partie, lorsqu'elles sont activées.
La 3a lance l'affichage d'1 bouton a la fin du chargement, celui ci valide le lancement du jeu.
Le bouton lancer le jeu fonctionne.

#Version 0.75.2 29/08/2020
Le bouton fermer de la fenêtre devrait fonctionner et demander une validation suplémentaire (si l'options est coché dans les réglages) avant de fermer la fenêtre.

#Version 0.75.3 29/08/2020
ajout du package GInt et CInt
GInt possède un contructeur qui prend en argument une Fourmiliere et qui compte le nombre de chaque stade de fourmi proésent dans la fere (+le nombre de Reine).
ajout du package GGInt et CCint.
GGInt possède une méthode d'affichage.

#Version 0.75.4 29/08/2020
ajout d'un if dans la méthode getPropretéPerdu de Espèce pour éviter un message d'erreur lors de l'appel a fluctuer().

#Version 0.75.5 29/08/2020
ajout d'un méthode pour compté les fourmis de chaque joueur a la fin de chaque tour.
Le score de chaque joueur peux être calculé de la façon suivante : 20 par adulte, 50 pour la reine, 9 pour une nymphe, 6 pour une larve, 3 pour un oeuf et -1 pour chaque fourmi morte.
Le score des joueurs et le nombre de fourmi est afficher a la fin de chaque fin de tour dans Fourmiliere.

#Version 0.75.6 29/08/2020
Les statistiques de score et de nombre de fourmi par stade peuvent être affichée dans data/score.csv avec des entêtes pour chaque colonne.
Autrement résumé :
Un nouvel outils permet de mémoriser le nombre de fourmi (a chaque stade), de chaque joueur. Ses information sont utiliser pour calculer les scores de chaque joueurs.

#Version 0.75.7 29/08/2020
correction d'un légé bug sur le PanneauZoom.
La carte ne passe plus par un écran nuageux si il y a 1 ou 0 joueur humain.

#Version 0.75.8 29/08/2020
La vitesse de jeu a un impact, auparavent une erreur de conversion de type faisait que fourmi.getMultiplicateurDeDiff(), n'avait aucun impact.

#Version 0.75.9 29/08/2020
La difficulté a un impact sur la vitesse de croissance du couvain.

#Version 0.75.10 29/08/2020
Les icones s'affiche même si aucune fourmi n'est la fActuelle, ce qui en théorie n'arrive que lorsque aucun joueur humain n'est dans la partie.

#Version 0.75.11 29/08/2020
En difficulté très difficile et en vitesse taon les ia arrivent a nétoyer leur larves puis leur nymphe jusqu'a ce qu'elle soit autonome.
Un rééquilibrage du netoyage semble néssésaire.

#Version 0.75.12 29/08/2020
La reine ne pond plus si il y a déja plus de 2 éléments du couvain et qu'elle est seule.
Elle ne pond pas nonplus si un élément du couvain est sale.

#Version 0.75.13 29/08/2020
Les larves et nymphe des ia meurent de faim autant que de bactérie.

#Version 0.76.0 29/08/2020
Au bout de 20 a 119 jours les graines qui ne sont pas dans des fourmilières pourrissent.

#Version 0.76.1 29/08/2020
Lorsqu'1 seul joueur humain joue, les icones des graines et des insectes s'affiche comme perçu par la première fourmi de sa fourmilière si il en a une.
Si le joueur a une seule fourmi cela permet d'achevez de masquer les conséquenses d'un changement de tour.

#Version 0.76.2 02/09/2020
un nouveau filtre de GCréature permet de fitrer celon le type.

#Version 0.76.3 02/09/2020
aNourrir a été réorganisée de façon a ce que les fourmi donne leur nourriture a la reine et celle ci distibue au imago a la fere qui en manquerait puis au nymphe puis au larve puis au oeuf.
Les ia sont capable de nourrire leur couvain puis de le surveillé jusqu'a éclosion !

#Version 0.76.4 02/09/2020
Si la reine est déja pleine en nourriture, les autres fourmis peuvent donné directement au oeuf.
Le message de chargement n'affiche le temps que si affLesPerformances==true.

#Version 0.76.5 02/09/2020
Les virgules de séparation de nombre sont dans le fichier de trduction comme les . de fin de frase ? et ! qui ont dans certaine langue pas d'espace avant le "?" ou "!"

#Version 0.76.6 02/09/2020
Les imago ia ne ce base plus que sur le besoin de soin exprimé par la reine pour le reste du couvain mais aussi sur leur stock de nourriture. Si ils arrivent a plus de 90%

#Version 0.76.7 02/09/2020
les fourmis nourrissent la reine tant que celle ci a de la place pour stoquer de la nourriture.

#Version 0.76.7 02/09/2020
Les fourmis ce dévelloppe lentement au début pour être sur de s'en sortir puis de plus en plus vite.
une fourmilière seule sur la petite carte atteint sont point d'équilibre vers le tour 117 et fluctue encuite entre 80 et 250 individu.

#Version 0.77.0 02/09/2020
La couleur d'une fourmilière peu être récupéré grace a getPh. On prend par défaut les Ph de la Reine, sinon ceux de la 1a créature du Gc sinon du gris.

#Version 0.77.1 02/09/2020
Les fourmilières sont facilement repérable grâce a un rond de la couleur de la fere sur la case en question.

#Version 0.77.2 02/09/2020
Les reines commencent avec plus ou moins de nourriture en fonction de la difficulté.
Le projet fait plus de 11 000 lignes rien qu'avec les .java !

#Version 0.77.3 02/09/2020
La class Temps contient les valeurs de 1a ouverture du jeu, denière ouverture du jeu et temps écoulé en jeu.
La class Temps peu ce construire a partir du fichier data/Temps.txt

#Version 0.77.4 02/09/2020
msToHMS permet de passer d'un long en ms a une String sous la forme 1 h 33 min 56 s
msToHMS peut être traduit en plein de langue avec T.h T.s etc.
Les fonctions de temps sont désormais dans Temps au cas ou Windows concidèrerai que ce sont des doublons.

#Version 0.77.5 02/09/2020
Le fichier Temps.java est pleinement fonctionnel.

#Version 0.78.0 03/09/2020
Les touches pressé on un impacte si le dernière éléments cliquer a bien "addKeyListener(new Touches());".

#Version 0.78.1 03/09/2020
une fonction permet de savoir si un int est dans un tableau d'int.
le tableau utiliser pour afficher le PanneauAction est conservé dans cellui ci de façon a ce que la fonction getEstBoutonActif renvoie true si le bouton est affiché et donc le racourci clavier autorisé.

#Version 0.78.2 03/09/2020
setActionF ne fonctionne que si l'action est faisable (donc visible a l'écran).
Les touches sont fonctionnellent sur les actions de la fourmi !

#Version 0.78.3 03/09/2020
Les insectes tué par les fourmis ne peuvent plus bouger.

#Version 0.78.4 03/09/2020
ajout de la carte désert.
Les noms des cartes sont traduit en français.

#Version 0.78.4 03/09/2020
Les insectes qui apparaissent dépende de la case sur laquelle il apparaissent.

#Version 0.78.5 03/09/2020
GString possède une fonction getClé qui renvoie la clé correspondant a la String donné en arguments si il y en a une (avec ou sans maj au début) et sinon null.
getClé permet d'afficher les clé des cartes traduitent tout en créant une partie avec le nom du fichier originale.(tant que la langue ne change pas entre l'affichage de la ComboBox et le lancement de la partie).

#Version 0.78.6 03/09/2020
Une vérification d'1ms permet de valider que tout les dociers et tout les fichiers important sont la.
En cas d'absence de fichier important le jeu essaie de combler les trous et si cela n'est pas possible le jeu refuse de ce lancer.
Les fourmilières qui n'ont plus de fourmi passe leur tour de jeux automatiquement.

#Version 0.78.6 03/09/2020
La reine ne peu plus pondre si peutPondre n'est pas validée, cad exactement les memes conditions qui bloque ou pas la ponte (estALaFere et nourriture>20).

#Version 0.79.0 03/09/2020
Les touches raccourci fonctionne la plus part du temps.
Les raccourci s'affiche lors du survole d'un bouton ssi il a un raccourci.

#Version 0.79.1 03/09/2020
La "victoire" défaite a été ajoutée.

#Version 0.79.2 04/09/2020
La méthode ajouterDébut de GJoueur est maintenant fonctionnelle également dans le cas ou début==fin.

#Version 0.79.3 04/09/2020
Le nombre de joueur est défini avant le lancement de la partie quelque soit la façon d'y arrivé.
Cela permet de ne plus vérifier les conditions de victoire s'il n'y a que des ia.

#Version 0.79.4 04/09/2020
Le système de classement des joueurs est fonctionel a l'exeptions de petit soucis de duplucation.

#Version 0.79.5 04/09/2020
La fourmilière a une variabe nbrFourmisMorte qui augmente de 1 a chaque mort.
Le score d'une fourmilière décend de 1 par fourmi morte.

#Version 0.79.6 04/09/2020
un nouveau Panneau, PanneauFinPartie existe.
il s'affiche sur le PanneauJeu a condition que pc et pb soit non visible.(car il est ajouter après.)

#Version 0.79.7 04/09/2020
L'écran de fin s'affiche a la fin d'une partie. Il indique le message de victoire et le score des joueurs par ordre décroissant.

#Version 0.80.0 04/09/2020
Les fourmilières ne risque plus d'être généré exactement au même endrois.
La CCase de la fere est enregistré après que le changement ai été effetué si il était néssésaire.

#Version 0.80.1 04/09/2020
Le nombre de joueur dans une partie ne peu pas dépasser le nombre de case. Pour évité d'entrer dans une boucle infini, une erreur fatale est déclanchée.

#Version 0.80.2 04/09/2020
L'affichage des EtiquetteJoueur fonctionne mieux en actualisant la page ou le PanneauGEtiquetteJoueur losque c'est néssésaire.

#Version 0.80.3 04/09/2020
L'affichage de la carte est complètement nuageux entre les tours des joueurs humains.

#Version 0.80.4 04/09/2020
La limite du numéro d'insecte a chercher dans le tableau d'insecte lors de l'affichage d'un insecte n'est plus dépendante d'une variable propre a PanneauCarte mais dépend de la dimention du tableau.

#Version 0.80.5 04/09/2020
Les options non disponible des menus s'affiche avec un bientôt en plus.

#Version 0.80.6 04/09/2020
Les textes des boutons sont aligné a gauche et plus ou moins centré en hauteur.

#Version 0.80.7 04/09/2020
Un raccourci est acuellement utiliser dans PondreReine pour passer l'étape de choix du type de l'oeuf avec 3 de choisi par défaut.

#Version 0.81.0 05/09/2020
Test Windows :
les caractères spéciaux des fichier .txt sont bien lue.
Les caractères spéciaux dans des String en revanche ne sont pas bien écrit dans le fichier Options.txt par exemple.
Les textes des EtiquetteJoueur s'actualise autant qu'il faut pour que le texte soir bien écrit.
Les boutons des action s'affiche mal au rafraichisement de la fenetre puis a nouveau bien lors d'un survole.
La description des boutons vois sont arrière plan s'actualiser avec une actualisation de retard.

#Version 0.81.1 05/09/2020
Un build java personnalisée permet de lancer le jeu sur Windows dans C:\Users\lili5\OneDrive\Documents\test jlink 6
Ce build a été réalisé avec :
jlink --module-path "C:\Program Files (x86)\Java\jdk-14.0.1\jmods" --add-modules java.desktop --output java
Le jeu ce lance avec :
java/bin/java.exe -jar Formiko.jar

#Version 0.81.2 05/09/2020
le jeu ce lance en double cliquant sur un .bat qui contient :
@echo off
echo lancement du jeu sur Windows.
start java/bin/java.exe -jar .\Formiko.jar

#Version 0.81.3 05/09/2020
ajout d'un fichier README.md qui contient toutes les informations importantes.

#Version 0.82.0 05/09/2020
La commande si dessous permet de faire un équivalent de zip.
Compress-Archive -Path dossier -DestinationPath nom.zip

#Version 0.82.1 05/09/2020
Un .sh permet de lancer le jeu avec un petit message dans le terminale linux.
Il ne manque que l'image java associé.

#Version 0.82.2 06/09/2020
version mac et linux avec image fonctionnel.
la phase de zip ne marche pas encore

#Version 0.83.0 07/09/2020
Plus de switch case dans le fichier triche.java

#Version 0.83.1 07/09/2020
Les commandes sont lue mais pas appliquée.
es commandes sur joueurs ou la commande test fonctionne.
Il y avait une boucle infini dans CJoueur.
la commande aff fonction sans soucis.
Les commandes qui utilisent des GCréature semble causer des soucis dans les GCases utilisé dans le jeu.

#Version 0.83.2 07/09/2020
L'action setPropreté ne peu pas donner une valeur qui ne soit pas dans l'intervale 0,100.
L'action ceNétoyer ne ce déclanche pas si la fourmi est déja a 100 en propreté.
Le bouton nétoyer ne s'affiche plus pour une créature seule déja propre.

#Version 0.83.3 07/09/2020
Les codes triche n'impacte plus les GCreature des joueurs.

#Version 0.83.4 07/09/2020
Les codes triches peuvent être décris avec la commande aide.
Les textes des codes triche sont complet en français.

#Version 0.83.5 07/09/2020
Les codes triches peuvent être tappé dans la console.

#Version 0.83.6 07/09/2020
Etats des traductions :
eo : 69%
en : 54%
esp : 0%
de : 0%

#Version 0.84.0 07/09/2020
Etats des traductions :
eo : 68%
en : 82%
esp : 0%
de : 0%
Les fichiers de traductions ont été remis en forme.

#Version 0.84.1 07/09/2020
Ajout d'une class ThGraphisme qui raffraichi la fenetre toutes les 20 ms.
L'actualisation ralentie trop le jeu.
Essaie d'utlisisation de la méthode revalidate a la place de repaint.

#Version 0.84.2 08/09/2020
powershell.exe au début d'une commande .bat permet d'executé une commande powershell. (comme le zippage ou la supression de répertoire.)
la généricité pour que les type des tableaux n'ai pas besoin d'être définie ne marche pas facilement...

#Version 0.84.3 08/09/2020
Système de sortie du jeu fonctionnel.

#Version 0.84.4 08/09/2020
Le PanneauFinPartie s'affiche au dessus des PanneauCarte et PanneauBouton.

#Version 0.84.5 08/09/2020
getNbrDeJoueurVivant permet de compté le nombre de joueur qui on au moins 1 fourmi dans un GJoueur.
Suppression d'un chargement d'image tourné de fourmi de trop.

#Version 0.84.6 08/09/2020
Un bouton continuer le jeu sur le PanneauFinPartie permet de continuer la partie.
lors de l'affichage du PanneauFinPartie le PanneauBouton est non de façon a ce que le joueur prène en concidération le PanneauFinPartie.
Si on clic sur continuer la partie le PanneauFinPartie ne s'affichera plus (les conditions de victoire ne seront plus vérifiée.).

#Version 0.85.0 08/09/2020
ThTriche légèrement modifié.

#Version 0.85.1 08/09/2020
Le bouton retour au menu permet de retourner dans la void main, mais le PanneauMenu afficher n'est pas pleinement fonctionel.

#Version 0.85.2 08/09/2020
La fonction addParMorceaux d'une GString permet d'ajouter une longue string par section de x char a chaque fois (tout en évitant au maximum de couper les mots).
Les conseils sont affiché sur l'écran de chargement grace a la fonction addParMorceaux.

#Version 0.85.3 08/09/2020
pour ne pas s'embèter avec la taille du PanneauInfo conseil lors du setBound on fait setBound(x,y,conseil.getWidth(),conseil.getHeight());

#Version 0.86.0 09/09/2020
Les fichiers en sortie de ./exe.sh sont directement synchronisées vers MEGA et vers windows en vue de subire les dernières transformations avant la sortie.

#Version 0.86.1 09/09/2020
Ajout d'une méthode qui transforme un GCase en une grande image avec les différentes case d'herbe, de mousse et de sable.
La texture de mousse est dans le jeu

#Version 0.87.0 13/09/2020
Ajout d'une chenille verte et d'un cloporte.
Ajout des noms des insectes en fr.

#Version 0.87.1 13/09/2020
Ajout d'un fichier Insecte.csv avec les chances d'appartition des insectes sur les cases en fonction de leur type.

#Version 0.87.2 13/09/2020
2 méthode de conversion permettent d'éffectuer la convertion d'un tableau d'int en tableau de String et vis versa.

#Version 0.87.3 13/09/2020
Les informations du fichier IEspece.csv sont chargé et enregistré lors de l'initialisation du jeu.

#Version 0.87.4 13/09/2020
ajout des commandes triche affCase et affGj.
Les textures de graines et d'insectes peuvent être plus nombreux sans qu'on ai besoin de modifier le nombre qu'il faudra charger en temps qu'image car ce nombre est calculé en fonction du nom des fichiers.

#Version 0.87.5 13/09/2020
le fichier IEspece.csv permet d'attribué un %age de chance d'appartion d'un insecte celon le type de la case. (ex : plus de cloporte dans la mousse, plus de vers dans le sable et de façon général moins de scarabée).

#Version 0.87.6 13/09/2020
Le fichier de triche permet d'ajouter un insecte ou une fourmi en indiquant les coordonées et le type pour l'insecte et seulement la fourmilière pour la fourmi.

#Version 0.87.7 13/09/2020
Les paramètres d'une partie par défaut n'ont plus case sombre et case nuageuse d'activé.

#Version 0.88.0 15/09/2020
Modification du pluriel en eo + char ! ? etc.
Les é accentué des noms de .java on été retiré

#Version 0.88.1 17/09/2020
Une correction était erroné dans chargerTIF. le GEtiquetteJoueur était conposé d'un élément vide a la fin. Pour évité les soucis on avait fait -1 mais dans les paramètres par défaut de lancement de partie le nombre de joueur était correcte. Le soucis a été réglé.

#Version 0.89.0 18/09/2020
Ajout de méthode toString.

#Version 0.89.1 18/09/2020
Un GCase peut ce println.
Idem pour : Fourmi, Fourmiliere, Case, Insecte, Creature

#Version 0.89.2 18/09/2020
getDirAllea dans Fourmi est légèrement plus simple.

#Version 0.89.3 18/09/2020
L'interface Trophalaxie.java contient Trophallaxer(Creature c).
Ajout de trophallaxer(Creature c) dans TrophallaxieFourmi (action du joueur pour trophalaxer).

#Version 0.90.0 19/09/2020
Ajout d'un interface Evoluer ainsi que sont implémentation Fourmi et Null.

#Version 0.90.1 19/09/2020
getDirAllea est désormais dans ObjetSurCarteAId puisque c'est la bas qu'est géré la direction.

#Version 0.90.2 19/09/2020
Les get set de Creature ainsi que les raccoucis d'accès vers les interfaces sont plus compacte
Les test de morts et de dépassements de nourriture sont fait dans tt les cas ou c'est utile. Ils ont leur propre petite fonction de test.

#Version 0.90.3 19/09/2020
60 lignes on été retirer dans chaqu'un des fichier Fourmi et Créature.

#Version 0.90.4 19/09/2020
les interfaces ont leur propre package.

#Version 0.91.0 19/09/2020
La commande suivante permet de remplacer "package " par "package src." 1 fois dans le fichier Main.java.
sed -i 's/package /package src./' Main.java
avec un g après le dernier / on demande a remplacer autant de fois que nécessaire l'information.

#Version 0.91.1 19/09/2020
Passage du fichier journalDesMaj.txt a journalDesMaj.md
Cela permet l'affichage en couleur des lignes concernant la version (qui désormais commence par #)
Cela permet également de voir en plusieur ligne les longues phrases.

#Version 0.91.2 19/09/2020
Tout les package sont redirigé vers le dossier fr.formiko d'abord.

#Version 0.91.3 19/09/2020
Ajout de la notation //@OSpour indiquer que l'action est différente en fonction de l'OS.

#Version 0.91.4 19/09/2020
Réorganisation des fichier tel que tt les packages contiènent fr.formiko au début. C'est plus simple de faire le .jar a partir de la. et Comme sa il sont bien isolé des .java.
Les fichier 'EtiquetteJoueur$ItemStatex.class' correspondent au sous class présente dans la class EtiquetteJoueur.

#Version 0.91.5 19/09/2020
L'os du joueur est identifié grace a *Os.java.*
Cette information permet de rajouter un traitement spéciale sur windows pour des opérations qui ne fonctionne pas comme sur linux.

#Version 0.91.5 19/09/2020
Sur la version Windows la mise en plein écran fonctionne aussi !

#Version 0.92.0 19/09/2020
modifications des paramètres de sortie du .jar.

#Version 0.92.0 19/09/2020
GIEspece a une méthode toString.

#Version 0.93.0 19/09/2020
Ajout de l'api de traduction de google utile pour partagé l'avancement du jeu sur Discord.

#Version 0.93.1 20/09/2020
Nouveau système de bot discord. Ce système permet la traduction en anglais, français et esperanto des messages concernant l'avancement du jeu.

#Version 0.94.0 20/09/2020
Réorganisation des salons du Discord de façon a ce que seul les salons associés a la langue de l'utilisateur soit affiché.

#Version 0.94.1 20/09/2020
Les requètes google trad peuvent retourner des appostrophes en format chelou.

#Version 0.94.2 21/09/2020
Le fichier qui traduit et qui ajoute dans journalDesMaj n'as plus besoin du numéro de sous version car celui ci est calculé automatiquement.

#Version 0.94.3 21/09/2020
Les fichiers de trad peuvent contenir des "[auto]" en fin de liste sans que cela n'impacte l'affichage réèle de la traduction.

#Version 0.94.4 21/09/2020
La vérification de ligne a traduire "estLigneDeTrad" est plus restrictive dans le fichier chargerLesTraductions.java

#Version 0.94.5 21/09/2020
La clé google :
export GOOGLE_APPLICATION_CREDENTIALS=~/Formiko/traduction/My\ First\ Project-58890c16071b.json

#Version 0.94.6 21/09/2020
L'affichage des %age traduit inclu un "(dont x% traduit automatiquement)" si une partie est traduit automatiquement.

#Version 0.94.7 21/09/2020
le char ø permet d'indiquer une chaine vide mais une traduction finie.

#Version 0.94.8 21/09/2020
D'éventuel espace ajouter par google traduction en début de String traduite sont retiré.

#Version 0.95.0 21/09/2020
Les trad sont actualiser, le nombre de ":", n'est plus pris en compte pour une ligne de trad sauf si il est <1.

#Version 0.95.1 21/09/2020
D'autre langues peuvent être ajouter dans le tableau de langue de chargerLesTraductions, les fichier seront créé, complèté sans traduction puis entièrement traduit.
Le fichier data/langue/langue.csv contient tt les codes google trad et le nom dans la langue en question de la langue.

#Version 0.95.2 21/09/2020
Au 1a text non recconu, on passe a la langue suivante plutot que de demander tout les éléments d'une langue qui n'est pas présente sur google trad.

#Version 0.96.0 21/09/2020
Les chars spéciaux de google trad &quot; et &39; sont remplacer par leur équivalent lisible en java.

#Version 0.96.1 21/09/2020
Toutes les langues accepté par google trad on été ajoutée.
du -hs * permet de connaitre la tailles des répertoires. Les fichiers de trad fond 2,7 mo au total = une image en hd.

#Version 0.96.2 21/09/2020
La commande cdF permet de ce déplacer dans la dernière version de Formiko.

#Version 0.97.0 22/09/2020
107 langues sont désormais supporté dans le jeu ! Certaines langues ne sont pas traduite manuellement, les traductions peuvent être inexacte. @traducteur si vous parlez l'une de ses langues + le français ou l'anglais, votre aide est bienvenue.

#Version 0.97.1 22/09/2020
La commande si dessous permet d'afficher les fichiers et dociers assé volumineux d'un répertoire.
du -a | grep [1-9][1-9][1-9]
taille des répertoires les plus lourd en ko :
java 75968
imageFormiko 8444
langue 2720
Formiko.jar 244
le reste de data 124

#Version 0.97.2 22/09/2020
Les insectes passe 10% de leur temps de vie maximum en temps que cadavre après leur mort.

#Version 0.97.3 22/09/2020
Une nouvelle erreur, erreurType servira essentiellement pour les interfaces d'action ou on passe d'une créature a un surtype.

#Version 0.97.4 22/09/2020
Les insectes et les fourmi meurent grace a l'interface Mourir. Chacun a sont implémentation de l'interface.

#Version 0.97.5 22/09/2020
les infos de débogage ne sont plus afficher null part.
Les insectes ne meurt finalement pas vraiment pour cause de bug que je ne trouve pas.

#Version 0.97.6 22/09/2020
Ajout d'un github pour synchronisées les choses a faire.

#Version 0.98.0 23/09/2020
Réorganisation des get set de *Point.java* et *Fourmiliere.java* et remplacemenent de getPoint par toString.

#Version 0.98.1 23/09/2020
Les packages usuel et graphisme on désormais un répertoire a leur nom.
Ajout d'un arrière plan transparent sur le PanneauAction dans l'espoir que ça résolve le soucis de Windows.

#Version 0.98.2 24/09/2020
Les .java du package formiko sont dans un dossier a ce nom.

#Version 0.98.3 24/09/2020
Le PanneauSup écoute et intercepte tout les clics.

#Version 0.98.4 24/09/2020
Le PanneauSup n'est ajouter qu'au moment ou le PanneauChargement est retiré.
Il capte 100% des clics et des survoles. Les panneaux inférieur n'ont donc qu'un role graphique.
On peu réduire la zone d'écoute en réduissant la taille de PanneauSup.
Le PanneauAction s'appelle avec getPa() alors que partie demande getPartie().

#Version 0.98.5 24/09/2020
Les zones de bouton PanneauZoom et PanneauAction ne sont pas comprise dans la zone d'écoute de PanneauSup.

#Version 0.98.6 24/09/2020
Le PanneauSup est capable d'annalyser un clic et de savoir quelle case est cliquée
Ajout d'1 méthode estFourmi qui renvoie true ssi la Creature est une fourmi. Cette méthode utilise le fait que getClass de c telle que  Creature c = new Fourmi() est une Fourmi.

#Version 0.98.6 24/09/2020
la méthode getFourmiParFere permet de récupérer la 1a fourmi de la même fourmilière si il y en a une dans le gc.
Cette méthode est utilisé sur les cases de la carte par PanneauSup.

#Version 0.98.6 24/09/2020
On peu changer de Fourmi pendant sont tour en cliquant sur la fourmi !

#Version 0.98.7 24/09/2020
ajout d'un do while dans Fourmiliere pour s'assurer que toutes les fourmis d'1 joueur humain ont jouée.

#Version 0.98.8 24/09/2020
Le viellissement et l'affamissement d'une Fourmi ce fait après la boucle de tour. Cela permet d'éviter de faire viellir une fourmi plusieur fois par tour si l'ordre est perturbé.

#Version 0.98.9 24/09/2020
Si une fourmi est cliquée sur la carte, elle fini sont tour et laisse la fourmi suivante jouer.

#Version 0.98.10 24/09/2020
Le panneau d'info de la fourmi s'actualise correctement.
le PanneauAction est retié lorsqu'on change de fourmi.
On ce base sur getDimX et getDimY plustot que les dim de la fenetre car elle est plus grande que l'écran !

#Version 0.98.11 24/09/2020
cliquer sur une fourmi nous permet de la jouée avant les autres (a conditions qu'elle soit a nous évidement) !

#Version 0.98.12 24/09/2020
un clic gauche sur la carte entraine un déplacement de la Fourmi qui joue d'autant de case que possible dans la direction de la case. (avec une nouvelle méthode de *DeplacementFourmi.java*)

#Version 0.98.13 24/09/2020
Une méthode de PanneauSup écoute les déplacements de la sourie et détecte les changements de cases.

#Version 0.98.14 24/09/2020
Survoler une case permet d'afficher les infos qui lui y sont lié.

#Version 0.98.15 24/09/2020
Le panneau tInt apparait en bas a droite en aussi gros que le panneau zoom.

#Version 0.99.0 25/09/2020
La selection d'une fourmi n'est autorisé que si celle ci a encore des actions de disponible.

#Version 0.99.1 25/09/2020
Ajout de nouveau truc a faire lorsque on doit jouer une fourmi en particulier.
On remet la fourmi a joué a -1 a la fin de son tour pour éviter la boucle infini.

#Version 0.99.2 25/09/2020
Un revalidate semble nécéssaire a la fin de addPA(), sans ça il ne s'affiche pas avant le prochain repaint.
Ajout d'un repaint juste après.

#Version 0.99.3 25/09/2020
Le fichier exe.sh trouve la version de sortie tout seul.

#Version 0.99.4 25/09/2020
Avec ./actualiserJDM.sh on peu ajouter la ligne de trad/out.txt dans jdm (avec la version) sans faire les requètes de traduction.

#Version 0.99.5 25/09/2020
Le PanneauFinPartie désactive PanneauSup (et le réactive si il est remove).

#Version 0.99.6 25/09/2020
Il est probable que les insectes qui mourrait en buggant mourrait 2 fois.
Les Insectes ne meurt plus 2 fois et ne provoque plus d'erreur.

#Version 0.100.0 25/09/2020
Lien GitHub ajouté pour les issues https://github.com/HydrolienF/Formiko. Si vous avez des sugestions d'ajout, souhaitez signaler un bug ou voulez découvir les ajout programmé, allez voir !

#Version 0.100.1 25/09/2020
Ajout de la difficulté dans le calcul du score (5 par défaut, 3 au min 5 au max).

#Version 0.100.2 25/09/2020
Les insectes et les fourmis peuvent être placés de 3 façons différentes en fonction de l'option positionCase.
Soit au centre dans tout les cas. Soit dans les coins tant que c'est possible. Soit alléatoirement tant qu'elles ne sorte pas de la case.
Le déplacement positionnelle (dans les coins) est privilégié par défaut.
Le déplacement alléatoire change un peu trop souvant. Il faudrait que chaque élément sur case ce souviène de sont déplacement en x et en y et qu'il ne change que lors des déplacements.

#Version 0.100.3 25/09/2020
Des getClass() remplace les try catch qui servait a choisir le type de Créature. (Ou pas parce que ca marche pas.)

#Version 0.100.4 25/09/2020
les élément xTemp et yTemp sont private a PanneauCarte et change dans une fonction destiné a les calculer.

#Version 0.100.5 26/09/2020
les options par défaut inclue des boutons plus grand.

#Version 0.100.6 26/09/2020
Les bouton d'action de fourmi sont fixé sur windows !

#Version 0.100.7 26/09/2020
Si l'OS est windows les jlabel ce redessine null avant de s'afficher. Apparement ça a aucun effet alors on revient a la version d'avant.

#Version 0.101.0 26/09/2020
Les fourmi des joueur humains ne perde plus pluS de déplacement que nésséssaire en clic droit.

#Version 0.101.1 26/09/2020
Le PanneauInfo est retiré (try) avant d'être ajouter ce qui lui permet d'être bien recontruit.

#Version 0.101.2 26/09/2020
Le score des ia n'est pas impacté par la difficulté.

#Version 0.101.3 26/09/2020
Une fourmi peut ce suicidé.

#Version 0.101.4 26/09/2020
L'objet Question permet de créer un JOptionPane avec les choix oui ou non prédéfini.
Il ce contruit avec 2 clé a traduire. Il est utilisé dans Fenetre et dans Fourmi.

#Version 0.101.5 26/09/2020
Fenetre n'as plus d'import inutile.

#Version 0.101.6 26/09/2020
Plein de package de Main était inutile, ils ont été retiré.

#Version 0.101.7 26/09/2020
Les graines sont prisent en compte dans le placement organisé sur les cases.

#Version 0.101.8 26/09/2020
Le github envoie tout les changement vers un salon discord français.

#Version 0.102.0 28/09/2020
Le chargement des éléments de la cartes ce fait en 2 fois.D'abord on charge les éléments graphique avec la résolution maximale qu'il dervait avoir dans le jeu.
Puis on initialise les éléments graphique qui servirons pour de vrais a partir des éléments précédents.
A chaque zoom ou dézoom seule la 2a phase est effectuée.

#Version 0.102.1 28/09/2020
L'actualisation des cases sombres et nuageuse ce fait dans déplacement, et advient a chaque fois qu'une fourmi d'un joueur humain effectue un mouvement.

#Version 0.102.2 28/09/2020
Suppression de package inutile dans *PanneauJeu.java*

#Version 0.102.3 28/09/2020
Si l'écoute de PanneauSup porte sur une case null, on ne cherche pas a dessiner la légende, on actualise juste la description a "".
Et on set cc2 a null de façon a ce que la description soit actualisée même si on revient sur la même case.

#Version 0.102.4 28/09/2020
Les différentes options du PanneauEchap sont traduite en français. Les liens des actions des boutons sont dans PanneauJeu.

#Version 0.102.5 29/09/2020
Le panneau échap s'affiche avec un bouton.

#Version 0.102.6 29/09/2020
revalidate(); après avoir construit le PanneauEchap permet de l'afficher.

#Version 0.102.7 29/09/2020
Le PanneauEchap peut être retiré en cliquant sur le bouton associé ou avec l'action 15.

#Version 0.102.8 29/09/2020
PanneauEchap a un get estContruit qui sert a addapté la réponse a le pression de la touche échap.

#Version 0.102.9 29/09/2020
Le PanneauEchap peut s'afficher et ce désafiché facilement avec un Override de setVisible.

#Version 0.102.10 29/09/2020
Le bouton quitter Formiko fonctionne.

#Version 0.103.0 29/09/2020
La fonction getTailleElementGraphique permet d'obtenir la taille idéale pour la Fenetre du joueur, (en fonction de getDimX et getDimY) avec x la valeur pour une résolution 1920x1080.

#Version 0.103.1 29/09/2020
Les dimensions du PanneauAction sont dépendante seulement de getTailleElementGraphique & du nombre de bouton qui le compose.
Celle ci sont fixé a la création du PanneauAction puis ne change plus.

#Version 0.103.2 29/09/2020
La position de desc dépend de la taille de PanneauAction, cela permet de pouvoir changer celle ci sans avoir a changer la position de desc.
La desc s'affiche aussi a gauche et aussi en bas que possible.

#Version 0.103.3 29/09/2020
La taille des boutons du PanneauAction dépend de getTailleElementGraphique.

#Version 0.103.4 29/09/2020
L'espace laisser libre entre chaque bouton de PanneauAction est désormais différent de 0 (=10) et dépend de getTailleElementGraphique.

#Version 0.103.5 29/09/2020
L'espace réservé pour chaque bouton (et donc la taille finale de PanneauAction) dépend de seuelemtn 2 variable static tailBouton et bordure.

#Version 0.103.6 29/09/2020
L'image de fond de la zone des bontons est chargé et affiché.

#Version 0.103.7 29/09/2020
La dimention des cases au lancement du jeu est getTailleElementGraphique(100) de façon a ce que les cartes en 16 9 s'affiche dans l'espcace qui leur est réservé.

#Version 0.103.8 29/09/2020
Les boutons du PanneauAction n'ont plus de bordure grâce à setBordure(boolean). Il ont à la place une bordure verte dessiné grâce à PanneauActionSup.

#Version 0.103.9 29/09/2020
Les boutons on un fond vert grace a fillRect dans PanneauAction.

#Version 0.103.10 30/09/2020
Le PanneauActionInf permet d'afficher l'arrière plan de la carte sur toute la longueur de la fenetre.

#Version 0.103.11 30/09/2020
removePA n'est plus utilisé dans Fourmi, on ce contente de désaficher les éléments.
En revanche lorsqu'on les ajoute a nouveau, on les retire juste avant de les ajouter au cas ou ça encombrerai la mémoire pour rien.

#Version 0.103.12 30/09/2020
La carte est adapté au zoom en fonction du nombre de case (sauf si l'écran est trandart et la case aussi parce que dans ce cas la c'est déja bon.)

#Version 0.103.13 30/09/2020
Ajout de la carte tuto.

#Version 0.104.0 30/09/2020
Début du tuto, les premier élément textuel sont dans fr.txt.
fere.setCc change les lien vers la fourmilière des 2 cases concerné de façon a ce qu'on ai pas a ce préocuper de changer autre chose.

#Version 0.104.1 30/09/2020
Le boolean appartionInsecte de Partie permet de bloquer ou d'autoriser l'appartition normale d'insecte.
Il a un effet également sur le nombre d'insecte présent initialement sur la carte.

#Version 0.104.2 30/09/2020
L'appartionGraine peut elle aussi etre réglé dans Partie.

#Version 0.104.3 30/09/2020
La Fourmi et la Fourmiliere du tuto sont placer en 0,1, l'insecte a chasser est défini vivant mais sans possibilité de ce déplacer (actionMax=0). il est en position 1,1.

#Version 0.104.4 30/09/2020
Les texte du tuto sont complet et correcte en orthographe en français.
Le script du tuto en .formiko et complet mais certaines actions ne possèdent pas d'équivalent dans Triche.java.

#Version 0.104.5 30/09/2020
La commande triche new Graine permet d'ajouter une graine sur la carte.

#Version 0.104.6 30/09/2020
La commande triche evoluer a été ajouté.
Les imago ne peuvent plus évoluer vers le stade suivant (sinon il redeviennent des oeuf mais avac des actions a faire et ca plante.)

#Version 0.104.7 30/09/2020
Le nombre de commande est calculé automatiquement par triche.

#Version 0.104.8 30/09/2020
On peut afficher un texte avec sa clé grâce a la commande print.

#Version 0.104.9 30/09/2020
L'élément graphique PanneauDialogue peut recevoir une String, la coupé en morceau grace a une méthode de GString et l'afficher en autant de lignes qu'il faut avec un PanneauInfo.
On peu ajouter un PanneauDialogue dans PanneauJeu.

#Version 0.104.10 30/09/2020
Le PanneauDialogue s'affiche en haut sur tout la dimension de l'écran.

#Version 0.104.11 30/09/2020
La description afficher avec PanneauDialogue est bien centré en haut a gauche.
Les descriptions on désormais un fond vert pomme par défaut.

#Version 0.104.12 30/09/2020
Le texte de PanneauDialogue ne s'affiche plus qu'au dessus de la carte. Comme la taille doit etre mal réglé, il apparait centré et juste de la taille qu'il faut pour que tout le texte y soit, magique !

#Version 0.104.13 01/10/2020
Si Main.getPartie est null, les Panneau qui ne s'affiche que lorsque partie != null ne bug pas grace a un try catch.
Le PanneauChargement s'affiche même si partie n'est pas défini dans Main.

#Version 0.104.14 01/10/2020
Grace a ```if(pi!=null){remove(pi);}``` dans PanneauDialogue.initialiser on peu facilement réinitialiser le Panneau avec un nouveau texte.

#Version 0.104.15 01/10/2020
Le texte du panneau info n'est plus contrains en taille plus qu'il ne faut.

#Version 0.104.16 01/10/2020
*script.java* permet d'executé un.formiko en executant chaque commande a la suite, potentiellement en attendant avant la suivante si on le lui indique.
triche indique a script d'attendre après chaque commande "print"

#Version 0.104.17 01/10/2020
un Thread ThScript permet de lancer un script sans bloquer les autres actions de la Fenetre.

#Version 0.104.18 01/10/2020
Le PanneauSup vérifie avant de chercher la case sur laquelle on a cliquer, si il n'y a pas le PanneauDialogue la où on a cliqué. Si panneau dialogue exite et renvoie true c'est qu'on lui a cliquer dessus, on ne fait donc rien dans PanneauSup, sinon rien ne change dans PanneauSup.

#Version 0.104.19 01/10/2020
Le passage d'une boite de dialogue a une autre s'effectue sans problème.

#Version 0.104.20 01/10/2020
Les commande ne sont plus dans les fichiers de langues pour éviter les problèmes de traduction. Comme ca n'importe quelle fichier .formiko pourra être lue.

#Version 0.104.21 01/10/2020
Il n'y a plus de char accentué dans cmd.txt pour rendre les commandes plus internationale.

#Version 0.104.22 01/10/2020
La partie est concidéré comme fini si au lancement du jeu il n'y a qu'un seul joueur.

#Version 0.104.23 01/10/2020
Les textes en français du fichier joueur sont désormais traduit.

#Version 0.104.24 01/10/2020
La commande *quit* a été ajoutée. Elle permet de fermer le jeu normalement.

#Version 0.105.0 01/10/2020
iniTLangue est lancé juste avant de charger les options, ce qui permet de reconnaitre la langue indiqueé dans le fichier des options, c'est quand même mieux !

#Version 0.105.1 01/10/2020
On peut utiliser "print " pour retirer le PanneauDialogue (et le PanneauDialogueInf).

#Version 0.105.2 01/10/2020
La plus part des actions du script correspondant au tutoriel sont réalisable dans le jeu.
Mais, il manque encore certaines actions importantes (attendre que le joueur face une action par exemple) pour que le tuto sois pleinement opérationel.
La partie graphique du tuto est terminée. Les messages du tuto s'affichent en haut de la fenêtre et un clic sur le message permet de passer au message suivant.

#Version 0.105.3 02/10/2020
Point peut etre comparé a une String semblabe a : 1,-6 ou -67.709 ou 3;5
La comparaison permet de vérifié qu'une créature est bien au coordonées donnée.

#Version 0.105.4 02/10/2020
On peut désormais attendre qu'une Creature face quelque chose comme ce déplacer vers un point dans un script.

#Version 0.105.5 02/10/2020
Si getCreatureParId renvoi qqchose de null, cela déclance une exeptions qui affiche une erreur.

#Version 0.105.6 02/10/2020
Les insectes sont également pris en compte parmi les créatures lors de la recherche par id dans triche.

#Version 0.105.7 02/10/2020
Le tuto fonctionne jusqu'a la nymphe.

#Version 0.106.0 04/10/2020
Ajout d'un fichier Sound et d'un fichier Musique ainsi que sont Thead associer ThMusique. Ses 3 fichier permettre de jouer de la musique (en .wav) pendant le jeu.

#Version 0.106.1 04/10/2020
Le fichier d'options est initialisé avec la langue par défaut de l'ustilisateur !

#Version 0.106.2 04/10/2020
La taille de Desc est maintenant 1.2 fois la taille du texte pour permettre les majuscules accentué et l'affichage normale de text dans des langues ou il y a des point ou accent au dessous ou au dessus des lettres.

#Version 0.106.3 04/10/2020
Les affichages du tuto ne bloque plus forcément la commande suivante. Le dernier affichage dans une liste de commande doit avoir " true" a la fin de la ligne pour passer a la commande suivante sans attendre.

#Version 0.106.4 04/10/2020
Ajout de 7 musiques dans le dossier data/musique.

#Version 0.106.5 04/10/2020
Les musiques sont lue a la suite de façon alléatoire.

#Version 0.106.6 04/10/2020
Le PanneauZoom est retiré avec une commande triche au début du tuto.

#Version 0.106.7 04/10/2020
Les erreurs lié au clic droit ou gauche sur des zones de la carte ou il n'y a pas de Case sont catch et ne s'affiche plus. (Ou alors les actions associé  ne sont faite que si CCase ou Case est non null.)

#Version 0.106.8 05/10/2020
Le PanneauAction est modifié par tuto.formiko de façon a ce que seule l'action a faire soit disponible, malheureusement l'affichage ne ce fait jamais complètement bien.
L'action de setPa x marche parfaitement en commande triche, visiblement le fait que l'action soit exécuté dans un autre Thread pose problème.

#Version 0.106.9 06/10/2020
L'affichage d'un seul bouton de PanneauAction fonctione depuis que l'oppération est réalisé dans le thread principal.

#Version 0.106.10 06/10/2020
Le choix de la cible est automatique en trophallaxie si seulement 2 Creature sont sur la case.
La tropahallaxie est addapté pour faire pop une boite de dialogue a liste déffilante.

#Version 0.106.11 06/10/2020
Les boites de dialogue de TrophallaxieFourmi foncionne.
La trophallaxie s'effectue désormais par BoiteListeDefilante.

#Version 0.106.12 06/10/2020
Le nettoyage d'une fourmi s'effectue aussi par BoiteListeDefilante.

#Version 0.107.0 06/10/2020
Le niveau de propreté est indiqué dans la BoiteListeDefilante correspondant a nettoyer des fourmis.

#Version 0.107.1 06/10/2020
La quatité de nourriture possèdé par chaque fourmi potentiellement cible est indiqué dans la BoiteListeDefilante.
Le stade de la Fourmi est également précisé, ssi c'est bien une fourmi qui reçoit la nourriture.

#Version 0.107.2 06/10/2020
Modification de s.sh pour que s'affiche les tailles des répertoires.

#Version 0.107.3 06/10/2020
Le tuto est fonctionnel.

#Version 0.107.4 06/10/2020
Les fichier .formiko peut conteni des commentaires a condition que celui commence par //.

#Version 0.107.5 07/10/2020
Le panneauSup agrandi sont champ d'écoute lorsqu'un print est indiqué, malheureusement cet agrandissement d'écoute n'est pris en compte qu'au print suivant a cause des histoires de thread.

#Version 0.107.6 07/10/2020
Sortie d'une nouvelle version du jeu avec un tutoriel et l'ajout de musique prévue ce dimanche !

#Version 0.107.7 07/10/2020
Si le fichier des Options est manquant, on concidère que c'est la 1a partie du joeur, et donc on ne lui affiche pas de message d'erreur concernant la reconstruction de fichiers et de dociers de son arborécence, et on lui passe les menus.

#Version 0.107.8 07/10/2020
La musique du menu est remplacé par une musique aléatoire lorsque qu'une partie est lancée.

#Version 0.107.9 07/10/2020
Gestion des avertissements de java :
La JComboBox de BoiteListeDefilante est de type String.
La FichierDejaPresentException a une serialVersionUID réalisé avec la commande serialver fr.formiko.usuel.exception.FichierDejaPresentException
La commande ThMusique.stop a rété retié dans Main au profi d'une méthode personnalisée qui coupe la musique qui elle meme coupe le son.

#Version 0.107.10 07/10/2020
La trophallaxie dépend de la nourritureMax - la nourriture de la cible.

#Version 0.107.11 08/10/2020
Les .jpg sont aussi pris en compte dans le nombre d'image en nomDeLImageX.qqchose.
Les .jpg sont chargé si il n'existe pas de .png du même nom.

#Version 0.107.12 08/10/2020
L'image de chargement de la carte désert est fixé dans chargerCarte.

#Version 0.108.0 08/10/2020
la fonction netoyer des fourmis est désormais une interface a part entière.

#Version 0.108.1 08/10/2020
getByteDeLaLigne a été ajouter a decoderUnFichier. Cette fonction fixe la valeur a min ou max si la valeur était trop grande pour rentré dans un byte.

#Version 0.108.2 08/10/2020
Les options musique et son true ou false ainsi qu'un %age de volume on été ajouté.
La musique est désactivable.

#Version 0.108.3 09/10/2020
Le tuto n'est plus étiqueté "bientot".

#Version 0.108.4 09/10/2020
passage des verbes conjugé au verbe non conjugé dans tableau.java.
Les Fourmi déja 100/100 propre ne pourrons plus être nétoyé.

#Version 0.108.5 09/10/2020
Les fourmis déjà propre ne s'affiche dans la BoiteListeDefilante et le bouton associé a l'action n'apparais pas si le nombreDeCreatureANetoyer est égale a 0.

#Version 0.108.6 09/10/2020
Les nom commun a toutes les langues (nom latin ou nom propre), ne sont pas traduit automatiquement.

#Version 0.108.7 09/10/2020
Une fourmi qui est déja complètement nourrie ne s'affichera pas dans la liste des cibles pour Trophalaxie.

#Version 0.108.8 09/10/2020
Ajout d'un raccourci toString a la liste GString.

#Version 0.108.9 09/10/2020
Les options peuvent être sauvegardées.(Les boolean sont sous forme textuel mais sont bien identifié par la méthode de chargement des Options. Les tailles de bouton sont brute tant que les boutons sont pas trop grand ça pose pas de problème)

#Version 0.108.10 09/10/2020
Un petit bug résolu sur les false dans le fichier d'options.

#Version 0.108.11 10/10/2020
Puisque la fonction java pour colorer le fond d'un bouton ne marche pas voici la mienne : setCFond(Color).

#Version 0.108.12 10/10/2020
Les boutons du PanneauEchap on un fond coloré vert standard pour etre facilement repérable.

#Version 0.108.13 10/10/2020
La taille du panneau a droite est faite pour renter tous juste dans l'espace libre a droite.

#Version 0.108.14 10/10/2020
La couleur du texte des bouton est plus noir et moins gris. Ca permet de mieux distinguer le texte de la fourmi sur l'image d'arrière plan.

#Version 0.108.15 10/10/2020
En théorie les tailles et les placements du bouton du PanneauEchap sont correcte, en pratique ca ne s'affiche pas.

#Version 0.108.16 10/10/2020
en rajoutant le "Graphics g" dans public void paintComponent(Graphics g) les boutons s'affiche bien !

#Version 0.108.17 10/10/2020
La taille des bouton en Y est celle de Main.getOp().getTaillePolice1() fois 1,4 pour prendre en compte la bordure.

#Version 0.108.18 10/10/2020
Le PanneauEchap s'affiche centré avec un rectangle bleu dépendant du nombre de bouton et des boutons a bordure vert standard.

#Version 0.108.19 10/10/2020
Les descriptions des boutons null cad qui commence par "bouton.desc." ne s'affiche plus.

#Version 0.108.20 10/10/2020
la méthode setText de Desc erst Override de façon a ce que chaque texte de bouton passe par les teste de setTexte.

#Version 0.108.21 10/10/2020
Les Options du PanneauEchap qui ne sont pas encore disponible sont annoté bientôt.
Le tuto n'est plus annoté bientôt.

#Version 1.0.0 10/10/2020
Sortie du jeu avec une petite version de java qui marche pour linux et mac.

#Version 1.0.1 11/10/2020
Plus d'affichage de "boolean ni true ni false"

#Version 1.0.2 11/10/2020
Les dernier fichiers d'image accentué ne le sont plus pour évité les soucis de zip et unzip un peu null qui ne reconnaise pas bien les char spéciaux.
Les graphismes sont a nouveau pleinement chargé.

#Version 1.0.3 11/10/2020
Le fichier .sh qui lance le jeu sur linux est capable de rendre accessible toute l'arborécence de document mais il doit être accessible.

#Version 1.0.4 11/10/2020
La musique est temporairement désactivée.

#Version 1.0.5 11/10/2020
Soucis dans les fichier d'image réglé normalement.

#Version 1.0.6 11/10/2020
La musique n'est plsu tiré alléatoirement si elle est désactivé.

#Version 1.0.7 11/10/2020
Certain fichier important de data ne sont pas bien transmis.

#Version 1.0.8 11/10/2020
ø

#Version 1.0.9 11/10/2020
ø

#Version 1.0.10 11/10/2020
On force tuto a valoir première partie dans Main pour compenser le fait qu'il soit modifier par qqchose.

#Version 1.0.11 11/10/2020
Le tuto ce lance bien a pa première partie.

#Version 1.0.12 11/10/2020
Ajout des fichier de musiques.

#Version 1.0.13 11/10/2020
La 1a version stable du jeu est sortie ! (ainsi qu'une version avec de la musique).
Profitez du tuto pour apprendre a jouer !
@everyone

#Version 1.0.14 13/10/2020
Le clic est effectif lorsque l'on relâche le clic de souris. Cela permet d'évité qu'un clic soit trop facilement identifié comme un cliqué glissé.

#Version 1.0.15 13/10/2020
La commande si dessous permet de retirer facilement une meme ligne dans tout les fichier d'un dossier.
Main supprimer "ligneAretiré qui contient des espaces" fichierOuDossierOuLOnRetireLaLigne ByteIndiquantCommentOnretirLaLigne

#Version 1.0.16 13/10/2020
il est possible de forcé l'affichage des info de déboggage en ajoutant -d après la commande de lancement du jeu. (Main -d)

#Version 1.0.17 13/10/2020
réarangement des packages de Main.

#Version 1.0.18 13/10/2020
les options en "-" doivent etre placer dans les 1a arguments. Il peut y en avoir autant qu'on veut tant qu'elle sont toute au début.

#Version 1.1.0 13/10/2020
Certain fichier présent dans le dossier formiko on été déplacer vers le bon package.
Les fichier de formiko et de usuel sont a jour sur les packages.

#Version 1.1.1 13/10/2020
outils et interface sont aussi a jour. interface a besoin d'importer les packages de formiko.

#Version 1.1.2 13/10/2020
Le package junit a été ajouter a la racines du projet avec pour objectif de pouvoir faire des test avec.
8 test on été écrit pour math.max.

#Version 1.1.3 13/10/2020
import org.junit.Test; permet d'utiliser l'annotation @Test

#Version 1.1.4 15/10/2020
Les test du fichier math sont assez complet.
Les erreurs peuvent etre rendue muette pour les test avec erreur.setMuet(true).

#Version 1.1.5 15/10/2020
Grace au test de allea.fluctuer je me suis rendu compte que iToBy ne modifiait pas les valeurs en cas d'erreur. Maintenant en plus d'afficher l'erreur il fixe au maximum ou au minimum l'int a convertir.

#Version 1.1.6 15/10/2020
la convertion String to Int de *str.java* est plus réstrictive et ne tollère que les chiffres, les - et les espace.

#Version 1.1.7 17/10/2020
Le fichiers Main est pleinement décrit en javadoc. Enfin seulement les truc utiles, les get set étant transparent il ne seront probablement pas commenté. (+100 lignes de commentaires)
Certaines vielles fonctions (qui ne servent plus depuis très longtemps) de Main on été passé en commentaire.

#Version 1.1.8 17/10/2020
Toutes les interfaces sont bien dans leur package.
Les variable liées au interface de Creature sont désormais public pour pouvoir être utilisé dans EvoluerFourmi.

#Version 1.1.9 17/10/2020
Toutes les interfaces et leurs implémentations comportent une description au minimum de la class.
ChasseFourmi est plus détaillé.
On pourrait rajouter plus de détail pour toutes les implémentations non null.

#Version 1.1.10 17/10/2020
La méthode de chasse de la fourmi comporte 2 sous partie private et utilise la méthode de morts de l'insecte.(a testé)

#Version 1.1.11 17/10/2020
Le fichier math est pleinement décrits.

#Version 1.1.12 17/10/2020
str est pleinement commenté, certaine vielle fonction sont partie.

#Version 1.1.13 17/10/2020
coderUnFichier est passer au fichier inutile.

#Version 1.1.14 17/10/2020
visiblement les anciennes conversion avec str sont encore très utilisé alors on les laisses pour le bon fonctionnement de decoderUnFichier.

#Version 1.1.15 17/10/2020
Tous les Objets des package formiko, (usuel) et interface sont Serializable mais seul des éléments très simple accèpte d'être serializé... (ça marche pour ObjetSurCarteAId mais pas Insecte ou Fourmi.)

#Version 1.2.0 18/10/2020
Visiblement le seule truc qui coince avec la sauvegarde de partie est l'interface Evoluer.

#Version 1.2.1 18/10/2020
Les Creatures sont male ou femmelle alléatoirement (1 chance sur 2) sauf les fourmis qui sont femmelle sauf si de type 1.
Les fourmis Override les get set de femmelle pour le traité sous l'angle du type (male, reine etc).

#Version 1.2.2 18/10/2020
Plutot que d'utiliser une version compatible pour savoir si une sauvegarde de Partie est compatible on vas utiliser un serialVersionUID qui sera incémenté de 1 a chaque changement de version.

#Version 1.2.3 18/10/2020
Le fichier sauvegarderUnePartie est testé et commenté ainsi que la nouvelle méthode de str.

#Version 1.2.4 18/10/2020
L'options sauvegarder une partie (puis pourvoir la charger) est en bonne voie. Il reste encore un petit soucis d'éléments qui refuse de ce sauvegarder pour une raison encore inconnue. J'espère que ce sera bientôt résolu.

#Version 1.2.5 18/10/2020
L'interface Mourir contient désormais une méthode supprimerDeLaCarte qui permet de ne pas passer par l'étape transformer en cadavre des Insectes.
ChasseFourmi utilise supprimerDeLaCarte.

#Version 1.2.6 18/10/2020
Les interfaces de fourmi utilise instanceof Fourmi plustot que des try catch.

#Version 1.2.7 18/10/2020
Les types d'insectes sont désormais fixé pour toutes les créatures pour permettre des tests plus général. Le but est de pouvoir utiliser cette information pour pouvoir définir un régime alimentaire spéciale composé d'un type d'insecte particulié, plusieur ou bien des fourmis (+ potentiellemnt des insectes).
la méthode de Fourmi getType est devenu getTypeF (idem pour setType). estReine est désormais utilisé pour tout les cas ou elle est utile.

#Version 1.2.8 18/10/2020
Une creature renverra -1 comme type par défaut.
Une fourmi renvera -2.
Cela permet de les distinguers des autres Insectes.

#Version 1.2.9 18/10/2020
Le problème non-sérializable de Evoluer est résolue. Il était dû au fait qu'une vielle version de Evoluer était dans les .class dans le Package formiko, il y avait donc 2 class avec le même identifiant de sérialization. Comme quoi parfois il faut juste pensé a "raffraichir" l'arboressence des .class

#Version 1.2.10 18/10/2020
Les setters de Creature utilise str.iToBy si ils ont besoin d'éviter les dépassements sur une conversion de type.

#Version 1.2.11 19/10/2020
Les Créatures on une tolerencePheromone de 0, les fourmi de 5
Les tolerencePheromone sont utliser pour ce reconaitre allié et *6 pour neutre.

#Version 1.2.12 19/10/2020
Réorganisation de image.java de façon a ce que les fichier image soit recherché et créer si il existe plutôt que créer dans un try catch.
De façon général on cherche a prévenir plutot que de gérir pour l'ensemble des fichiers qui contiennent des try catch.

#Version 1.2.13 19/10/2020
Les try catch qui portait sur une conversion de type, essenciellement de Creature a Fourmi ou de Creature a Insecte on été remplacer par des if(c instanceof FourmiOuInsecte).

#Version 1.2.14 19/10/2020
jdm, le corps des issues, les info de débogage et les commentaires sauvage du code sont en français.
Les labels, les titres des issues, les milestones et les commentaires destiné a la javadoc sont en englais.

#Version 1.2.15 19/10/2020
Ajout d'un test, PartieTest qui permet de vérifier que les sous éléments de partie se décrivent bien.

#Version 1.2.16 21/10/2020
Le stade anciennement seulement défini pour les Fourmis est désormais commun a toute les créatures. Les insectes pouvant donc aussi passer par les stades oeuf (-3), larve (-2), et nymphe (-1).

#Version 1.2.17 21/10/2020
GEspece, CEspece et Espece ainsi que GInsecte et GJoueur on désormais une méthode toSTring.

#Version 1.2.18 21/10/2020
GEspece est dans la partie mais en static car GEspece est initialisé avant la partie.

#Version 1.2.19 21/10/2020
Le boolean polycalique du tableau Espece.csv a un impacte sur le jeu. Seule les Espece polycalique reconnaissent les Fourmiliere très semblable phéromonalement.

#Version 1.2.20 21/10/2020
Certaines espèce sont désormais annoté herbivore ou miellativore.

#Version 1.2.21 21/10/2020
Ajout d'un champ vole dans Espece.csv

#Version 1.2.22 21/10/2020
Lorsque une fourmi grandit si elle est Reine ou Male, on lui donne des ailes.
Ses ailes seront automatiquement coupé au 1a oeuf pondu.

#Version 1.2.23 21/10/2020
la nourritureFournie est désormais une varaible commune a toutes les créatures. La nourriture fourmi est celle de l'espece +ou- 10%.

#Version 1.2.24 21/10/2020
Les quantités de nourriture fourmi sont fixée par insectes et par état.

#Version 1.3.0 21/10/2020
Les fourmi granivore on été réactivée pour les Ia. Tester leur fonctionnement est nésséssaire.

#Version 1.3.1 21/10/2020
Les graines sont visiblement bien collecté par les fourmis granivores.

#Version 1.3.2 21/10/2020
La taille réaliste est désormais défini dans les Options. Elle varie entre 0 et 100%

#Version 1.3.3 21/10/2020
La tailles des 3 stades du couvain des fourmis on des tailles réalistes. Les tailles des insectes dépendent de leur espèce.

#Version 1.3.4 22/10/2020
le fichier image.java abrite la méthode taille et des désormais complètement documenté.
La fonction image.getNbrDeFichierCommencantPar a été définiivement remplasser par son alternative getNbrImages qui prend en compte les .jpg également.

#Version 1.3.5 22/10/2020
Il n'est plus nésséssaire de préciser temporaire/nomDeLImage. La méthode de recherche des images vas vérifié toutes seul dans les 2 répertoires d'image usuel.

#Version 1.3.6 22/10/2020
Les test de *image.java* on permi de mettre en évidance des failles dans le contage des fichier d'image dès lors que x était différent de 0 ou 1 (et que les fichiers n'existait pas).

#Version 1.3.7 23/10/2020
Une image .png qui aurait été sauvegarder dans un fichier avec une autre extention n'est pas accèpté par readImage.

#Version 1.3.8 23/10/2020
Toutes les fonctions de image.java sont testé a l'exeption des getImages et getImagess.

#Version 1.3.9 24/10/2020
Le fichier str contient une méthode sToDirectoryName testé et commentée.

#Version 1.3.10 24/10/2020
Le fichier image contient un String REPTEXTUREPACK qui indique le chemin vers des images de remplacement ou en plus. Si REPTEXTUREPACK==null rien ne change si en revanche il est non null est contient des images avec les meme nom ou qui continu une suite comme chargementx elles seront prises en compte lors du compte d'image et lors du chargement d'image.

#Version 1.3.11 24/10/2020
Le fichier *image.java* est complètement testé.

#Version 1.3.12 24/10/2020
Les flèches sont sauvergadé tourné en meme temps que les autre images, donc plus de soucis de flèches pas chargé alors que tout les autres graphismes le sont.

#Version 1.3.13 24/10/2020
Le fichier d'images est désormais image et plus imageFormiko. Cela a permit de testé que toutes les méthodes de chargement ou de compte d'image était bien connecté au variable static REP et REP2.

#Version 1.3.14 24/10/2020
Les fourmilières sont indiqué par un rond de taille dépendant de la taille de l'écran grace a Main.getTailleElementGraphique.
Les rond des fourmilière deviène des carrés plein si la taille de la case est trop petite pour qu'un rong y soit dessiné.

#Version 1.3.15 24/10/2020
ajout du dossier ressourcesPack. si celui ci contient des images elle seront charger en priorité.

#Version 1.3.16 24/10/2020
La réperation et la vérifiaction de l'arbo ce base sur les noms de fichier présent dans image.

#Version 1.3.17 24/10/2020
On peut forcer la réninitialisation des graphismes avec Main -rg
Les options a - peuvent réclamé une initialisation qui ne sera pas refaite plus tard dans ce cas.

#Version 1.4.0 26/10/2020
Les fichiers .java sont rangé dans leur catégories respective mais 5 liens symbolique permet d'y accèder facillement.

#Version 1.4.1 26/10/2020
Les commandes comme javacf utilise le nouveau chemain src/main/... pour fonctionner. Cela permet de compiler facilement les fichier .java contenu dans les sous dossiers mais cela oblige a compilé les fichiers de outils qui contiènent des "unchecked or unsafe operations".

#Version 1.4.2 26/10/2020
Les test sont compilé dans les mêmes package que leur fichier a testé mais dans la branche src/test.

#Version 1.4.3 26/10/2020
Réexecuté ./exe.sh écrase l'ancien build de la même version si celui si existe. Cela permet de réexécuté ./exe.sh sur la meme version sans avoir d'erreur.

#Version 1.4.4 26/10/2020
La musique est désactivé et retirée de data pour l'instant.

#Version 1.4.5 26/10/2020
Le PanneauChargement est passé rapidement après le tuto.

#Version 1.4.6 26/10/2020
Les langues sont a nouveau a jours par rapport au français.
Main refonctionne.

#Version 1.4.7 26/10/2020
La commande formiko permet de lancer le jeu sur linux. Elle doit etre placer dans usr/bin/, dans un fichier formiko qui contient :
"cd ~/chemin; ./formiko.sh"
Il faudrait pouvoir executé les commandes suivant au 1a lancement du jeu.
chmod 700 formiko.sh
chmod 777 formiko
sudo mv formiko ~/../../usr/bin/.

#Version 1.4.8 27/10/2020
Les commandes de bashrc on été réparé de façon a ce que seul les fichiers de test soit compilé dans la partie test de build.

#Version 1.4.9 27/10/2020
Les allias de bashrc sont dans leur propre fichier de façon a ce qu'un .sh puisse les utiliseé avec une petite ligne de commande.

#Version 1.4.10 27/10/2020
Les fichiers *usuel/test.java* et testJunit.sh permette d'effectué tout les test présent dans l'abroressence de fichier. Si certain test ne sont pas encore fonctionnel on peu les désactivé en les retirant de testJunit.txt.

#Version 1.4.11 27/10/2020
Les fichiers qui contenaint des get set qui ne revoyait pas juste une valeur contenu dans la class et ne lançait pas un raccoucis on été déplacer dans fonction propre après les méthodes toString length et equals.

#Version 1.5.0 29/10/2020
Les lignes d'un fichier de traduction non reconu comme ligne de traduction ne seront pas modifié.

#Version 1.5.1 29/10/2020
Le soucis de traduction des mots d'un seul char était en fait un soucis de chargement des langues dans la map. Il a été résolu.

#Version 1.5.2 31/10/2020
toString des insectes est traduit.

#Version 1.5.3 31/10/2020
*g.java* est commenté et contient désormais une méthode getOu qui renvoie la traduction des 2 éléments (ex le/la) sauf si elle est identique, dans ce cas il n'en affiche qu'une des 2 (ex en "eo" getOu("le",la) return la).

#Version 1.5.4 31/10/2020
Un message d'erreur s'affiche si une commande triche n'est pas reconnue (pas seulement si elle échoue).

#Version 1.5.5 31/10/2020
Les cases contenant une fourmilière ne détaille pas les fourmis contenue par la fourlilière grace a toString(boolean b).

#Version 1.5.6 31/10/2020
La langue peu désormais etre changer en jeu par commande triche. L'effet est imédiat sur tout les éléments textuel qui sont rechargé.

#Version 1.5.7 31/10/2020
Les créature ne peuvent plus dépasser 100 en propreté, des test sur les modifications de variables des Creature serait bien utiles.

#Version 1.5.8 31/10/2020
Les test sur image.clearPartielTemporaire ne sont plus impacté par d'éventuel fichier temporaire présent suite a un arrêt brutale du jeu.

#Version 1.5.9 31/10/2020
estLigneDeTrad a été perfectionné grace au test.

#Version 1.6.0 31/10/2020
le github du projet est a jour et j'ai mieux compris comment fonctionnait l'envoie de modification git : d'abord il faut avoir charger toutes les informations dans le fichier courant avec git pull, ensuite on peu afficher l'état des choses avec git status, pour envoyer qqhose on doit l'enregister comme modification avec git rm ou git add, pour soumettre la modification on fait git commit et une fois que toutes les modifs sont faite on envoie a la version en ligne avec git push.

#Version 1.7.0 01/11/2020
Ajout de la prise en charge de "" et String null dans les méthodes de str.

#Version 1.7.1 01/11/2020
Ajout de str.sToSMaj pour renvoyer la même chaine de char a l'exception du 1a char en majuscule si possible.

#Version 1.7.2 01/11/2020
Les tests de chargerLesTraductions sont complets. Il ont permi de remplacer certaine section de code par de nouvelle plus compacte, plus sûr ou présente dans d'autre package usuel comme str.

#Version 1.7.3 02/11/2020
Les fichiers utilisant getVersion.sh marche aussi si il sont dans un dossier suppémentait a cause de git.

#Version 1.7.4 02/11/2020
Le jeu peut être relancé avec le bouton associé du PanneauEchap. Pour l'instant relancer le jeu réinitialise tout ce qui concerne la fenetre + les idCPt de Fourmiliere, Joueur GIEspece et ObjetAId.

#Version 1.7.5 02/11/2020
La class Main est découpé en plusieur étape qui même an lancement :
- main contient le décodage de la commande tappé et une boucle while qui lance le jeu tant qu'on ne quitte pas.
- Launch ce charge de toutes les autres étapes cad :
  - préinitialisé
  - construit la Fenetre et l'arboressence graphique
  - construit le menu
  - enclanche l'attente de lancement d'une partie
  - lance la partie après le PanneauChargement
  - lance le jeu dans Partie (Partie renvoie pa.jeu renvoie true si il faut recommencer toutes ses étapes.)

#Version 1.7.6 02/11/2020
Les fichiers de trad contiène bien test:testXX avec xx l'indentifiant de la langue (et pas test:testFr[auto]).

#Version 1.7.7 02/11/2020
Les sauvegardes sont faisable grace au bouton du PanneauEchap.
Les sauvegarde récupère un nom avec getSaveName. (méthode qui reste a pefectionné.)

#Version 1.7.8 03/11/2020
Ajout des méthodes getAlliéSurLaCase et getAlliéSurLaCaseSansThis a Fourmi.java, celle si servent pour Fourmi, TrophallaxieFourmi et NetoyerFourmi.

#Version 1.7.9 03/11/2020
En faisant l'initialisation dans Main on s'assure que les ini des Cpt sont bien a 1 (ou a 0 si il commence a 0.).
Sans quoi les test et les tuto supposant que l'on commence a 1 ne marcherons plus.

#Version 1.7.10 03/11/2020
Un test permet de vérifier la justesse du système de cellection de fourmi allié sur la case lors de trophalaxie. Les trophalaxie sont autorisé de Fourmi a Creature et n'implique en Creature cible que des Creature proche phéromonalement de la donneuse et qui ont faim.

#Version 1.8.0 05/11/2020
Ajout d'une feuille css.

#Version 1.8.1 05/11/2020
ajout de 2 lien vers la javadoc et l'ancien site.

#Version 1.8.2 05/11/2020
ajout de 2 iframe, la futur vidéo bande anonce et le lien discord.

#Version 1.9.0 09/11/2020
Les textures d'herbe et de mousse on été modifié par de vrais images.

#Version 1.9.1 09/11/2020
Résolution de 2 bug, 1 de langue l'autre de PanneauEchap qui s'affichaient pendant le menu.

#Version 1.10.0 12/11/2020
Les langues traduite automatiquement ne contiènent plus de phrase en francais qui aurait réussi a ce glisser.

#Version 1.10.1 12/11/2020
Suite a un soucis sur des traductions qui contenait ':' on ne demande plus un seul : mais aux moins 1 pour est considéré comme une ligne de trad.

#Version 1.10.2 12/11/2020
on peu désormais retirer des lignes des 4 bordures d'un tableau avec tableau.rogner. (Méthode abondament testé).

#Version 1.10.3 12/11/2020
tableau contient certaine méthode equals et copier qui peuvent etre utile.

#Version 1.10.4 12/11/2020
test ajouter sur les images carrée.

#Version 1.11.0 13/11/2020
corection d'un bug dans chargerLesOptions pour les écrans autre que 1920x1080

#Version 1.11.1 14/11/2020
Le ThTriche ne fait effet que si la ligne est non nul.

#Version 1.11.2 14/11/2020
Correction d'un bug d'affichage des boites de dialogues de TrophallaxieFourmi.

#Version 1.11.3 14/11/2020
L'initialisation des cases sombre/nuageuse ne pose plus de soucis.

#Version 1.11.4 14/11/2020
Le fichier Img est complètement commenté.

#Version 1.11.5 14/11/2020
La BufferedImage est désormais nommé bi par soucis de clareté du code, img étant souvent utiliser pour 1 Img.

#Version 1.11.6 14/11/2020
Effectuer l'action img.tourner ne renvoie rien mais tourne l'image. Cette modifiaction a été prise en compte dans le jeu et dans les tests.

#Version 1.11.7 14/11/2020
Les méthodes clone et equals ont été ajoutée a Img.

#Version 1.11.8 14/11/2020
Les images non carré peuvent être tourné.

#Version 1.11.9 14/11/2020
ajout des options -p et -g pour affLesPerformances et affG

#Version 1.11.10 14/11/2020
Grace a la commande "Main rbt name1 [name2] [name3] [etc]"" Les images du jeu peuvent être facilement rogner (Suppression des bord transparent).
Cela devrait rendre le jeu encore plus fluide (pas besoin d'afficher les pixels transparent des bordures lors du dessin de l'image).
De plus les tailles des insectes pourront être choisies indépendament de la taille de l'insecte sur l'image.

#Version 1.11.11 14/11/2020
L'ajout de ", source_language="fr"" dans le fichier trad.py permet d'imposer le langage source et donc de ne pas traduire les mots en esperanto !

#Version 1.12.0 14/11/2020
L'initialisation du PanneauJeu ce déclanche automatiquement sans Thread si c'est la première partie du joueur.

#Version 1.12.1 14/11/2020
un soucis de Creature non reconnu dans Trophalaxie Fourmi affichera juste une erreur et finira l'action.

#Version 1.12.2 14/11/2020
Les fourmis dont la nourriture est >= a nourritureMax ne peuvent plus bénéficié d'un trophallaxie

#Version 1.12.3 14/11/2020
Les fourmi qui n'ont plus de nourriture ne peuvent plus en donner.

#Version 1.13.0 15/11/2020
Un GString possède une méthode equals testé.

#Version 1.13.1 15/11/2020
La récupération des noms des fichiers d'1 répertoire est validé par test.

#Version 1.13.2 15/11/2020
La commande Main stats permet d'afficher le pourcentage de code commenté ! (#130)
/!\ tout les commentaires sont pris en compte mais seul les fonction longue et les class doivent etre commentée.
Les fonctions courtes comme les get/set ou des raccourcis sont concidéré comme pas a commenter.

#Version 1.13.3 15/11/2020
GString a eux quelque ajout de gestion de cas extrème pour gs.add(GString).

#Version 1.13.4 15/11/2020
Les colones de stats.txt sont alligné. (On rajoute des espace pour que "34%" soit aussi long que "100%")

#Version 1.13.5 15/11/2020
Le total fonctionne pour stats.java avec une autre méthode.

#Version 1.13.6 16/11/2020
Les commentaires de variable s'écrivent /*** pour ne pas etre compter dans les commentaires de fonction.

#Version 1.13.7 16/11/2020
Ajout d'informations complémentaire dans stats.txt avec compterFctEnDetail.

#Version 1.13.8 16/11/2020
Les 3 fonctions qui analyse les fichier .java sont testée.

#Version 1.13.9 17/11/2020
Les fonctions qui on en début de ligne (20 1a char ou 1a moitié) ...//... ou .../*... ne seront pas prise en compte dans les fonctions. (et donc ne demanderont pas a être commenté.)

#Version 1.13.10 17/11/2020
Fin des commentaires de interfaces/

#Version 1.13.11 17/11/2020
révision de toString de insectes pour du 100% traduit et utilisation du symoble tete de mort.

#Version 1.13.12 17/11/2020
Commentaire de *Insectes.java* fini.

#Version 1.13.13 17/11/2020
On demande 110 fichiers de langues. (105 avec les info de langues + cmd.txt et quelque autre.)

#Version 1.13.14 17/11/2020
Ajout de bcp de commentaire sur Creature et arbo

#Version 1.14.1 18/11/2020
Correction d'un bug mineur sur le temps (désormait en ms en param de timeToHMS).

#Version 1.14.1 18/11/2020
Ajout de PanneauChoixPartie.java

#Version 1.14.2 18/11/2020
le chargement de partie fonctionne, manque plus qu'une joli page de chargement fonctionnelle.

#Version 1.14.3 19/11/2020
Ajout des méthode afficher et contient pour des tableaux de T.

#Version 1.14.4 19/11/2020
EtiquetteChoix a une taille basée sur getTailleElementGraphiqueX ou Y plutot que sur son contenant. Cela permet d'utiliser cet élement dans PanneauChoixPartie.

#Version 1.14.5 19/11/2020
Les 2 pages de menu qui peuvent lancer une partie on désormais un élément commun, PanneauLanceurPartie qui contient 1 bouton de lancement.

#Version 1.14.6 19/11/2020
La sauvegarde puis le chargement d'une partie sauvegarder fonctionne !

#Version 1.14.7 19/11/2020
Les sauvegardes ont toutes un identifiant unique qui commence a 1 au 1a lancement du jeu.
Cet identifiant est sauvegardé dans le fichier data/.save
Le fichier data/.save pourra servir a sauvegardé d'autre valeur importante pour le jeu.

#Version 1.15.0 20/11/2020
Le fichier exe.sh centralise les informations qui était dans exe.bat.
Cela permet de n'executé qu'1 fichier pour créer les 4 fichiers de realise.
Pour qu'on ait toutes les info les fichiers de jlink et les launchers on été ajouté au projet git.

#Version 1.15.1 21/11/2020
Les 5 commandes principale sont désormais sous forme de .sh de façon a pouvoir les modifier facilement sans rendre incompatible les fichiers d'une version perécédente.
./main.sh et ./javaj.sh prennent autant d'argument que c'est utile.
Dans le cas de main il sont transmit en 1 bloc a java. Dans le case de javaj on execute la commande de test pour chaque argument.

#Version 1.15.2 22/11/2020
Le fichier test associé a sauvegarderUnePartie fonctionne.

#Version 1.15.3 22/11/2020
Les test peuvent facilement effectué une initialisation sans afficher d'information de débogage quelqu'onque avec les 2 ligne suivante :
debug.setDPG(false);
Main.initialisation();

#Version 1.15.4 22/11/2020
Le fichier testJunit.sh ce sert désormais de la prise d'agruments multiple de javaj.sh (code plus compacte pour le même résultat)

#Version 1.16.0 22/11/2020
Les jlink sont dans un .zip dans le projet git et ils sont décompressé a chaque sortie de nouvelle version.

#Version 1.16.1 22/11/2020
Les fichiers .zip (pour l'instant seulement jlink.zip) devrait être stocker a distance dans git large storage.

#Version 1.16.2 22/11/2020
le fichier destiné au test junit*.jar est désormais dans le répertoire courant de façon a pouvoir servir sur d'autre machine.

#Version 1.16.3 23/11/2020
les true & false qui s'affichait on été retiré.
les fourmis sont concidéré propre pour propreté>=100.

#Version 1.17.0 23/11/2020
tentative de transformation des méthodes de jeux qui implique un long délai d'attente pour que le retour au menu puisse marcher n'imporque quand. (a poursuivre) Pour l'instant j'ai pas réussi a refaire marché les fourmis.

#Version 1.18.0 24/11/2020
Les images de PanneauCarte sont dans Data.java.

#Version 1.18.1 24/11/2020
Les images de Data.java ne sont plus redimentionné au chargement. Elle le seront de tout façon juste après lors de l'execution de chargerImages().

#Version 1.18.2 24/11/2020
Les messages de performances ne sont afficher que si le temps que ça prend est long. (>20ms)

#Version 1.18.3 24/11/2020
On économise 1 seconde a chaque rechargement de partie grace au fait que les images ne sont plus rechargées.

#Version 1.18.4 24/11/2020
Le PanneauZoom apelle la fonction qui recharge la carte.

#Version 1.18.5 24/11/2020
Le PanneauCarte refuse les redimentionnements. On utilise la méthode actualiserSize plutot pour lui demander de choisir lui meme sa taille.
Cela permet d'évité de faire une BufferedImage plus grande que la carte.

#Version 1.18.6 24/11/2020
La méthode de redimentionnements du PanneauCarte est appeler dans celui ci si setTailleDUneCase est modifié (et que la valeur de x change).
Ca nous évite de devoir le spécifier dans PanneauZoom et la ou on en aurait besoin.

#Version 1.18.7 24/11/2020
Les images de PanneauZoom, PanneauCarte et PanneauAction sont toutes stoqué dans action et ne sont plus recharger a chaque relancement de partie.

#Version 1.18.8 24/11/2020
L'arrière plan de la carte (les cases) est redessiné seulement si la carte change de taille (si les case change de taille).
Cela permet de dessiner plus vite l'ensemble de la fenetre mais entraine un temps plus long de chargement des éléments graphiques.

#Version 1.18.9 25/11/2020
La méthode actualiserImage ne passe plus par une couleur et a la place :
on transforme les donnée des tableaux en 1 int qui représente les niveaux de couleur. (Exactement comme color ferait sauf que ca demande probablement un peu moins de temps de ne pas avoir a passer par le constructeur.)
Le temps gagné semble assez négligeable mais on vas garder cette façon de faire.

#Version 1.18.10 25/11/2020
tentative de résolution du problème des pannel transparent en appelant super.paintComponent.

#Version 1.19.0 25/11/2020
Un clic glissé sur la carte prendra effet a la fin du clic. Cela permet d'évité de détecté des clics maintenu qui n'en sont pas vraiment.

#Version 1.21.0 26/11/2020
la javadoc utilise le juint.jar du répertoire actuel.

#Version 1.21.1 26/11/2020
La javadoc est actualisé a chaque nouvelle release & les test sont effectués.

#Version 1.21.2 26/11/2020
La javadoc n'affiche plus que les warrning puis les erreurs.

#Version 1.21.3 05/12/2020
Ajout d'un //class ---------------------------------------------------------------

#Version 1.21.4 18/12/2020
Le fichier fichier.java est en anglais.

#Version 1.22.0 21/12/2020
le fichier html contenant les versions de jeu est modifié lors du ./exe.sh (sortie d'une nouvelle version)

#Version 1.22.1 21/12/2020
Les versions stable et dernière version peuvent etre modifié grâce au fichier *download.php* & *preDownload.php*.
Elles sont modifié via le fichier setVersion.sh

#Version 1.22.2 21/12/2020
On peu désormais indiquer seulement la dernière version au fichier ./setVersion.sh

#Version 1.22.3 21/12/2020
Le fichier setVersion fait les 3 étapes git sur le fichier download.html pour que le site web soit actualisé.

#Version 1.23.0 23/12/2020
Les boutons sont a nouveau utilisable.

#Version 1.23.1 23/12/2020
Un bug bloque les boutons après l'apparition et la disparition du panneauEchap.

#Version 1.23.2 23/12/2020
Les actions des boutons ne se déclanche que si le PanneauEchap est invisible.

#Version 1.23.3 23/12/2020
La desc ne change que si le PanneauEchap n'est pas visible.
Le PanneauEchap fige complètement l'affichage en arrière plan.

#Version 1.23.4 23/12/2020
Le Chrono de Main s'initialise si il est null. (Cela permet de lancer des débutCh sans avoir préinitialisé le Chrono.)

#Version 1.23.5 23/12/2020
Les traduction auto ne sont plus effetué depuis la commande main. Les champ de tradutions sont tout de même instancié vide.

#Version 1.24.0 23/12/2020
Fix du ./jar.sh

#Version 1.24.1 23/12/2020
La javadoc n'as plus d'error.

#Version 1.24.2 24/12/2020
L'inteface *Tour.java* fait jouer un tour a un Insecte, une Fourmi, une Reine, une CreatureMorte, une CreatureSansAction.
Insecte, CreatureMorte et CreatureSansAction sont implémenté.

#Version 1.24.3 24/12/2020
Le tour des insectes est pars défaut TourInsecte si il sont vivant, mais, si ils meurent il change pour TourCreatureMorte.

#Version 1.24.4 24/12/2020
Les fourmis n'ont un tour fourmi qui leur est spécifique que lorsqu'elles atteigne le stade imago.

#Version 1.25.0
try

#Version 1.26.0 18/01/2021
Ajout de méthode de compte de pixel dans image.java

#Version 1.26.1 18/01/2021
./exe.sh nome les versions last & lastStable comme il faut dans le fichier download.html.

#Version 1.27.0 18/01/2021
Le zoom et dézoom remarche correctement avec une image d'arrière plan de la carte qui n'est pas redessiné a chaque fois.

#Version 1.28.0 22/01/2021
Les insectes ont une implémentation de l'interface Tour très simple mais qui permet 3 comportements, fuire, manger et ce reproduire (pour l'instant seul manger est implémenté).

#Version 1.28.1 22/01/2021
Les insectes utilise par défaut l'insterface herbivore pour ce nourrir.

#Version 1.28.2 22/01/2021
Commentaire fait a 25% !

#Version 1.29.0 25/01/2021
Tour fourmi contient de nouvelles fonctions plus simple dont le but est de réécrire complètement la façon dont une Fourmi joue a la façon des Insectes.

#Version 1.29.1 26/01/2021
Les filtres ne filtre plus le 1a éléments du GCreature.

#Version 1.29.2 26/01/2021
getReine peut etre lancer dans un groupe de Creature qui contient autre chose que des fourmis et renvoie la 1a reine du groupe.

#Version 1.29.3 26/01/2021
want food est testé et n'as plus de problème si getNourriture()==getNourritureMax()

#Version 1.29.4 26/01/2021
Tour Fourmi a toutes les fonctions dont il avait besoin. (en cours de test)

#Version 1.29.5 26/01/2021
J'ai bricolé un package java qui fait les getter et les setter pour qu'il me les faces comme j'aime en tappant ctrl alg g

#Version 1.30.0 28/01/2021
Migration vers maven. Ca débloque plusieur soucis sur :
- les tests
- la couverture de test
Ca doit permettre de vérifié facilement des trucs en plus que le fichier exe.sh
Ca permet de suivre a 100% l'arboressence de fichier standard de maven.

#Version 1.30.1 28/01/2021
Les raccourcis d'accès sont réparer.

#Version 1.30.2 28/01/2021
Les .sh sont tous fonctionnels.

#Version 1.30.3 28/01/2021
Les tests qui ne marchait plus sur maven on été désactivé de façon a ce que les autres soit executé directement. Les test qui ne fonctionne plus utilise tous une lecture de fichier.

#Version 1.30.4 28/01/2021
assertThrows marche grace a Junit 5

#Version 1.30.5 29/01/2021
les tests remarche tous avec maven.

#Version 1.30.6 29/01/2021
les actions de preTour (surtout redonner des actions) sont déclancher pour toutes les fourmis d'une fourmilière avant le début du tour de la 1 fourmi.

#Version 1.30.7 29/01/2021
Les symboles scientifique pour male et femelle sont basé sur getFemelle et donc s'adapte bien pour les fourmis (qui ont un getFemelle différent que les autres créatures.)

#Version 1.30.8 31/01/2021
Individu récupère le stade de la fourmi pour pouvoir choisir de sont codé si getNourritureConso renvoie nourritureConso ou 0 (si c'est un oeuf).

#Version 1.30.9 31/01/2021
Les fourmi utilise getIndividu plutot que in (l'individu défini a la création de la fourmi.)
Cela permet qu'un changement d'espece ou de typeF change aussi l'individu de la fourmi.

#Version 1.30.10 31/01/2021
Les exceptions personnalisés ne déclanchent plus d'erreur fatale (et peuvent donc être catch).

#Version 1.30.11 31/01/2021
Le tour fourmi non ia très proche de l'ancien tourF fonctionne.

#Version 1.30.12 31/01/2021
La méthode eat des Creature lance chasser et pas chasse. Ce qui permet au fourmi de ce déplacer dans la même direction lorsqu'elles veulent manger (au lieu de ne pas bouger).

#Version 1.30.13 31/01/2021
Les reine ia n'ont pas encore leur implémentations de tour.

#Version 1.31.0 03/02/2021
On peu executer une commande linux depuis les actions github. On peu également faire executer un fichier .sh mais les modifications ne sont pas durable sur l'architecture de fichier.

#Version 1.31.1 09/02/2021
les fonction equals peuvent etre utiliser avec des Object potentiellement null et vérifie si l'objet et non null & si il est du bon type.

#Version 1.31.2 11/02/2021
Le tour de la reine est testé sur plusieur cas semblable de tour de façon a vérifié que l'action suivante est aussi enclanché si la reine a les actions qu'il faut.

#Version 1.31.3 13/02/2021
Les reines ia sont capable de mener 1 oeuf jusqu'au stade imago en moins de 100 tours. On vérifi bientôt ça avec des tests.

#Version 1.31.4 13/02/2021
Le joueur n'as plus de bouton chasser si la méthode canHuntMore de chasse.java renvoie false (ne peu plus chasser).

#Version 1.31.5 14/02/2021
Les images sont redimentioné même si carrée.

#Version 1.31.6 14/02/2021
test long pour vérifier le bon fonctionnement d'1 Partie (pour les ia).

#Version 1.31.7 14/02/2021
Réoragnisation des packages de facon a ce que liste est 1 s.

#Version 1.33.0 17/02/2021
tentative de faire un .deb

#Version 1.33.1 17/02/2021
les dernière release auto ne contiènent pas la partie jlink. On vas fixé ca.
