package fr.formiko.usuel.son;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import fr.formiko.usuel.math.allea;
import fr.formiko.usuel.son.*;

public class Musique {
  private String nom;
  private static String rep = "data/musique/";
  private Sound son;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Musique(String s){
    if(!Main.getOp().getBMusique()){return;}//si la musique est désactivée.
    if(s.length()<4 || !s.substring(s.length()-4,s.length()).equals(".wav")){//si le .wav n'est pas dans le nom.
      s = s+".wav";
    }
    nom = s.substring(0,s.length()-4);
    son = new Sound(rep+s);
  }
  public Musique(){
    this(getMusiqueAlleatoireString());
  }
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    return nom;
  }
  public static synchronized Musique getMusiqueAlleatoire(){
    return new Musique(getMusiqueAlleatoireString());
  }
  public static String getMusiqueAlleatoireString(){
    if(!Main.getOp().getBMusique()){return "";}//si la musique est désactivée.
    File f = new File(rep);
    String liste [] = f.list();
    if(liste.length == 0){return null;}
    // TODO Opération sur liste pour retirer tour les fichier qui ne finisse pas en .wav
    int x = allea.getAlléa(liste.length);
    return liste[x];
  }
  public synchronized void play(){
    if(!Main.getOp().getBMusique()){return;}//si la musique est désactivée.
    InputStream stream = new ByteArrayInputStream(son.getSamples());
    son.play(stream);
  }
  public void stop(){
    if(!Main.getOp().getBMusique()){return;}//si la musique est désactivée.
    son.stop();
  }
}
