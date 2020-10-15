package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.chargerLesTraductions;
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.liste.CString;
import java.util.Map;
import java.util.HashMap;

public class trad {

  // Fonctions propre -----------------------------------------------------------
  public static void copieTrads(){
    String tLangue[] = chargerLesTraductions.getTLangue();
    int lentl = tLangue.length;
    // on récupère les traductions déja effectuée.
    Map<String,String> trad [];
    try {
      //en faite 1 des 2 avetissement est retiré avec SuppressWarnings mais l'autre est concidéré comme une erreur.
      //@SuppressWarnings("unchecked")
      trad = new Map<String,String>[lentl];//un tableau de map
      trad[0] = chargerLesTraductions.chargerLesTraductions(0);
      for (int i=2;i<lentl ;i++ ) {
        trad[i-1]=chargerLesTraductions.chargerLesTraductions(i);
      }
      int k=0;
      for (String s :tLangue ) {
        if(!s.equals("fr")){
          copieTradBase(s,trad[k]);k++;
        }
      }
    }catch (Exception e) {
      erreur.erreur("La mise au format standard des traductions a échouée.");
    }
  }
  public static void copieTradBase(String se, Map<String,String> map){
    String t [] = lireUnFichier.lireUnFichier("data/langue/fr.txt");
    GString gs = new GString();
    for (String s : t) {
      String s2 = getDébutDeLigne(s);
      String s3 = ":";
      boolean changé=false;
      for (String s4 : map.keySet()) { // s4 vaut les clés.
        if (s4.equals(s2)){ s2 = s2+s3+map.get(s4); changé=true; break;}//k1++;
      }
      if(!changé && s2.length()>0){s2 = s2 +s3;}
      //Si la ligne ce termine par [], on ne le modifie pas car c'est un nom propre.
      if(s.length()>2 && s.substring(s.length()-2).equals("[]")){
        gs.ajouter(s);
      }else{
        gs.ajouter(s2);
      }
    }
    //int k = (k1*100)/(k1+k2);
    //System.out.println("traduction de "+se+" effectuée a "+k+"%");
    ecrireUnFichier.ecrireUnFichier(gs,"data/langue/"+se+".txt");
  }
  public static String getDébutDeLigne(String s){
    int lens = s.length();
    String sr="";int i=0;char c = ' ';
    if (lens !=0 ){ c = s.charAt(i);}
    if(lens > 1 && c=='\\' && s.charAt(1)=='\\'){return s;}
    for (i=1;i<lens && c!=':';i++ ) {
      sr = sr+c;
      c = s.charAt(i);
    }
    return sr;
  }
}
