package fr.formiko.usual;

import fr.formiko.usual.ReadFile;
import fr.formiko.usual.ecrireUnFichier;
import fr.formiko.usual.structures.listes.GString;

import java.io.File;

public class modificationDeFichier {

  // FUNCTIONS -----------------------------------------------------------------
  public static void retirerLesLignesR(String s, String fichier, byte by){
    int x = retirerLesLignes(s,fichier, by);
    erreur.println(x+" lignes ont été supprimées");
  }


  public static int retirerLesLignes(String s, String fichier, int nbrARetire, byte dmft){
    File f = new File(fichier);
    if(!f.exists()){
      erreur.erreur("Le fichier spécifié n'existe pas : "+fichier);
      return 0;
    }
    //si c'est un dossier on transmet l'info a tout ses sous fichier et dossier jusqu'a ce que tout les fichier de l'arboressence ai été traité.
    if(f.isDirectory()){
      String tf [] = f.list();
      int k=0;int lentf = tf.length;
      for (int i=0; i<lentf ;i++ ) {
        k=k+retirerLesLignes(s,fichier +"/"+ tf[i],nbrARetire,dmft);
      }
      return k;
    }else if(f.isFile()){//si c'est un fichier on le traite normalement
      return retirerLesLignesDuFichier(s,fichier,nbrARetire, dmft);
    }else{
      erreur.erreur("Le fichier n'est ni identifié comme un fichier ni comme un dossier :"+fichier);
    }
    return 0;
  }
  public static int retirerLesLignes(String s, String fichier, byte x){
    return retirerLesLignes(s,fichier,0, x);//0 = pas de limite
  }

  //fonctionnement interne.
  private static int retirerLesLignesDuFichier(String s, String f, int nbrARetire, byte dmft){
    if(nbrARetire <0){erreur.erreur("Impossible de remove un nombre négatif de ligne d'un fichier");return 0;}
    GString gs = ReadFile.readFileGs(f);
    if(nbrARetire==0){nbrARetire=gs.length();}//si c'est 0 on retirera autant de lignes que néssésaire
    int k=0;
    boolean b;
    do {
      //b=gs.supprimer(s,dmft);k++; //TODO donner un impacte a débutMilieuxFinTotal. 0=on cherche au début 1 =on cherche partout, 2= on cherche a la fin, 3 ca doit correspondre exatement a la ligne.
      b=gs.remove(s);k++;
    } while (b && k<nbrARetire);//tant que la denière suppression a marché et que on doit encore supprimer des lignes.
    if(!b){k--;}//si la dernière suppression n'as rien supprimer on ne la compte pas comme une ligne supprimé.
    //on enregistre sous le nême nom le fichier sans les lignes qui on été retiré.
    ecrireUnFichier.ecrireUnFichier(gs,f);
    return k; //a la fin on a retiré k ligne.
  }
}
