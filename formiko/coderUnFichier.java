package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.conversiondetype.str;

public class coderUnFichier {
  // x,y,id, age, ageMax, estMort, action, actionMax
  private static String c = "-";
  // la méthode générale :
  // creerUneSauvegarde()

  // toutes les sous méthodes :
  public static String pointToString(Point p){
    return str.str(p.getX() + c + p.getY());
  }
  public static String objetSurCarteAIdToString(ObjetSurCarteAId o){
    return o.getId() + c + pointToString(o.getCCase().getContenu().getPoint());
  }
  public static String creatureToString(Creature f){
    //0-0-98-87-100-1-1
    String s = objetSurCarteAIdToString(f);
    s+= c+ f.getAge() + c + f.getAgeMax() +c+ f.getAction() +c+ f.getActionMax();
    return s;
  }
  public static String fourmiToString(Fourmi f){
    //0-0-16-200-Reine-27-1000-3-3
    String s  = creatureToString(f);
    s += c+ f.getNourriture() + c + f.getNourritureMax() + c + f.getType() +c+ f.getMode(); // A continuer
    return s;
  }
  public static String insecteToString(Insecte f){
    String s  = creatureToString(f);
    s+= c+f.getNourritureFournie();
    return s;
  }
  public static String joueurToString(Joueur j){
    return j.getId() + c + j.getPseudo() + c + j.getIa();
  }
  public static String fourmiliereToString(Fourmiliere gf){
    return gf.getId() + c + pointToString(gf.getP());
  }


  public static String codeTour(){
    return str.str(Main.getTour() + c + Main.getNbrDeTour());
  }
  public static String codeJoueur(Joueur j){
    String s = joueurToString(j);
    s += "\n" + fourmiliereToString(j.getFourmiliere());
    int taille = j.getFourmiliere().length();
    for (int i=0;i<taille ;i++ ) {
    }
    return s;
  }
}