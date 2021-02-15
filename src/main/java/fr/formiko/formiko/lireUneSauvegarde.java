package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.types.*;

public class lireUneSauvegarde {
  public static void lireUneSauvegarde(String nomDuFichier){
    erreur.erreur("Le système de lecture des sauvegardes n'est pas a jour !","lireUneSauvegarde.lireUneSauvegarde",true);
    /*
    // On récupère les donnes du fichier sous la forme d'un tableau de Str. 1 par ligne.
    String donnéeSauvegardée [] = lireUnFichier.lireUnFichier(nomDuFichier);
    if (donnéeSauvegardée.length == 0) {
      erreur.erreur("La sauvegarde est vide !","Main.lireUneSauvegarde",true);
    }
    if (donnéeSauvegardée.length < 2) {
      erreur.erreur("La sauvegarde est incomplète !","Main.lireUneSauvegarde",true);
    }
    int k = 0;
    int version = decoderUnFichier.getIntDeLaLigne(donnéeSauvegardée[k]); k++;
    if (version == Main.getVersionActuelle()){
      // toutes la récupération des données du jeux.
      int nbrDeTour = decoderUnFichier.getIntDeLaLigne(donnéeSauvegardée[k]); k++;
      // Création de la carte
      int g1 [][] = decoderUnFichier.getTableauTableauIntDeLaLigne(donnéeSauvegardée[k]); k++;
      //Main.setG(new Grille (g1));

      GInsecte gi;
      Joueur listeJoueur [];
      // Création du tableau de joueur
      int nombreDeJoueur = decoderUnFichier.getIntDeLaLigne(donnéeSauvegardée[k]); k++;
      int joueur = 0; //System.out.println(k);//k = 4
      GJoueur gj = new GJoueur();
      while (joueur < nombreDeJoueur){
        debug.débogage("k = "+k);
        // Création du joueurs
        String joueurJ [] = decoderUnFichier.getObjet(donnéeSauvegardée[k]); k++;
        boolean estIa = decoderUnFichier.getBoolean(joueurJ[1]);
        // Création de la fourmilière de chaque joueurs
        Fourmiliere gf = new Fourmiliere(null); // null doit etre un joueur.
        String tFourmiliere [] = decoderUnFichier.getObjet(donnéeSauvegardée[k]); System.out.println("k = "+k);k++;
        gf.setP(new Point (ent.ent(tFourmiliere[0]), ent.ent(tFourmiliere[1])));
        int nbrDeFourmi = ent.ent(tFourmiliere[2]);
        int i;
        for (i=0; i<nbrDeFourmi;i++){
          // Création de chaque Fourmi
          String ts [] = decoderUnFichier.getObjet(donnéeSauvegardée[k+i]);
          if (ts.length !=9){
            erreur.erreur("La fourmi ne possède pas le bon nombre de variable","Main.lireUneSauvegarde",true);
          }
          Fourmi fm = new Fourmi (new Point(ent.ent(ts[0]),  ent.ent(ts[1])),  ent.ent(ts[2]),  ent.ent(ts[3]),  ts[4], ent.ent(ts[5]), ent.ent(ts[6]), ent.ent(ts[7]), ent.ent(ts[8]) );
          gf = new Fourmiliere (gf,fm);
        }
        gj.ajouter(new Joueur (gf, joueurJ [0], estIa ));
        debug.débogage("ajout d'1 joueur");
        k=k+i; // On a passer i Foumis
        gf.afficheToi();
        joueur++;
      }
      // Création du tableau d'insecte
       gi = new GInsecte ();
       System.out.println(k);
       int nombreDInsecte = decoderUnFichier.getIntDeLaLigne(donnéeSauvegardée[k]); k++;
       for (int i=0; i<nombreDInsecte;i++){
        // Création de chaque insecte
        String ts [] = decoderUnFichier.getObjet(donnéeSauvegardée[k+i]);
        if (ts.length !=6){
          erreur.erreur("L'Insecte ne possède pas le bon nombre de variable","Main.lireUneSauvegarde",true);
        }
        Insecte cr = new Insecte (new Point(ent.ent(ts[0]),  ent.ent(ts[1])),  ent.ent(ts[2]),  ent.ent(ts[3]),  ent.ent(ts[4]),  ent.ent(ts[5]));
        gi.ajouterUneCreature(cr);
        debug.débogage("Ajout d'1 insecte");
      }

      // On initialise toutes les valeurs des créatures et on lance le jeu.
      Main.setGi(gi);
      debug.débogage("Ajout dans Main de " + gj.length() +" joueurs");
      gj.afficheToi();
      Main.setGj(gj);
      Main.setNbrDeTour(nbrDeTour); // A modifier
      Main.jeu();
    } else {
      erreur.erreur("La sauvegarde n'est pas compatible avec la version du jeu.","Main.lireUneSauvegarde");
    }*/
  }
}
