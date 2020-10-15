package fr.formiko.usuel.son;
import fr.formiko.usuel.son.Musique;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class ThMusique extends Thread {
  private Musique m;//la musique en cours.
  private boolean boucler = true;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public ThMusique(String s){
    m = new Musique(s);
  }
  public ThMusique(){
    m=new Musique();
  }
  // GET SET --------------------------------------------------------------------
  public synchronized Musique getM(){return m;}
  public synchronized void setM(Musique mu){m=mu;}
  public synchronized void setM(){setM(new Musique());}
  public synchronized void stopThm(){boucler=false; stopM();}
  // Fonctions propre -----------------------------------------------------------
  @Override
  public void run(){
    try {
      if(m==null){setM(Musique.getMusiqueAlleatoire());}
      do {
        m.play();
        setM(Musique.getMusiqueAlleatoire());
      } while (boucler);
    }catch (Exception e) {
      erreur.alerte("la musique n'as pas pu etre lancé.");
    }
  }
  public void stopM(){
    m.stop();
  }
}