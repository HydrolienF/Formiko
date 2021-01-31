package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.type.str;
import java.io.Serializable;

public class GIndividu implements Serializable{
  protected CIndividu début;
  protected CIndividu fin;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public GIndividu(){}
  // GET SET --------------------------------------------------------------------
  public CIndividu getDébut(){ return début;}
  public void setDébut(CIndividu ce){début = ce; }
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    if(début == null){ erreur.erreur("Impossible d'afficher une liste vide","GIndividu.afficheToi");return g.get("GIndividu")+" "+g.get("vide");}
    return g.get("GIndividu") +" : "+ début.toString();
  }
  public Individu getIndividuParType(byte x){
    if (début==null){ return null;}
    return début.getIndividuParType(x);
  }public Individu getIndividuParType(int x){ return getIndividuParType((byte)x);}
  public int [] getTypeDIndividu(){
    if (début==null){ return null;}
    return début.getTypeDIndividu();
  }
  public void ajouter(Individu e){
    if(début == null){
      début = new CIndividu(e);
      fin =  début;
    }else{
      CIndividu temp = new CIndividu(e);
      fin.setSuivant(temp);
      fin = temp;
    }
  }
  public int length(){
    if(début == null){ return 0;}
    return début.length();
  }

  public static void chargerLesIndividus(){
    String td [] = new String [0];
    try {
      td = lireUnFichier.lireUnFichier("data/Individu.csv");
    }catch (Exception e) {
      erreur.erreur("Le fichier des Individu n'as pas pu être localisé. il devrais y avoir un docier data et celui ci devrait contenir un fichier Individu.txt","GIndividu.chargerLesIndividu",true);
    } int lentd = td.length;
    if (lentd < 3){ erreur.erreur("Le fichier des Individu devrais contenir au moins 4 lignes dont 1 Individu","GIndividu.chargerLesIndividu",true);}
    String tDéfaut [] = decoderUnFichier.getTableauString(td[2],',');
    for (int i=3;i<lentd; i++) {
      Individu iu = créerUnIndividu(td[i],tDéfaut); // on le cré.
      iu.getEspece().getGIndividu().ajouter(iu); // on l'ajoute a son espèce.
    }
  }
  public static Individu créerUnIndividu(String s, String [] tDéfaut){
    debug.débogage("Création d'un nouvel Individu");
    // test ici
    String t [] = decoderUnFichier.getTableauString(s,',');
    tableau.boucherLesCasesVide(t,tDéfaut); // on remplace d'éventuelle case vide par les paramètres par défaut.
    int idEsp = str.sToI(t[0]); //int id = str.sToI(t[0]);
    byte ty=str.sToBy(t[1]); boolean se=str.sToB(t[2]); boolean se2=str.sToB(t[3]); byte né=str.sToBy(t[4]);  byte ac=str.sToBy(t[5]); byte ta=str.sToBy(t[6]);
    String co=t[7]; byte po=str.sToBy(t[8]); byte te=str.sToBy(t[9]); int tag0=str.sToI(t[10]);  int tag1=str.sToI(t[11]);  int tag2=str.sToI(t[12]);  int tag3=str.sToI(t[13]);
    int nm0=str.sToI(t[14]);  int nm1=str.sToI(t[15]);  int nm2=str.sToI(t[16]);  int nm3=str.sToI(t[17]); byte ca0=str.sToBy(t[18]); byte ca1=str.sToBy(t[19]); byte ca2=str.sToBy(t[20]);
    byte ca3=str.sToBy(t[21]);byte ca4=str.sToBy(t[22]);byte ca5=str.sToBy(t[23]); int nc = str.sToI(t[24]);
    Individu iur = new Individu(idEsp, ty, se, se2, né, ac, ta, co, po, te, tag0, tag1, tag2, tag3, nm0, nm1, nm2, nm3, ca0,ca1,ca2,ca3,ca4,ca5,nc);
    return iur;
  }
}
