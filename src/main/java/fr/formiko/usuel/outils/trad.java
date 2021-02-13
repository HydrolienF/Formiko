package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.chargerLesTraductions;
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.liste.CString;
import fr.formiko.usuel.types.str;
import java.util.Map;
import java.util.HashMap;

public class trad {
  private static String sep = ":";
  // Fonctions propre -----------------------------------------------------------
  public static void copieTrads(){
    String tLangue[] = chargerLesTraductions.getTLangue();
    int lentl = tLangue.length;
    // on récupère les traductions déja effectuée.
    Map<String,String> trad [];
    try {
      //en faite 1 des 2 avertissements est retiré avec SuppressWarnings mais l'autre est concidéré comme une erreur.
      //@SuppressWarnings("unchecked")
      trad = new Map[lentl];//un tableau de map
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
      if(chargerLesTraductions.estLigneDeTrad(s) && !str.contient(s,"[]",2)){//si c'est une ligne de trad qui ne correspond pas a un nom propre.
        if(str.contient(s,"test:",0)){gs.ajouter("test:test"+str.sToSMaj(se));}
        else{gs.ajouter(ligneTradBase(s,map));}//modifié
      }else{
        gs.ajouter(s);//pas modifié.
      }
    }
    //int k = (k1*100)/(k1+k2);
    //System.out.println("traduction de "+se+" effectuée a "+k+"%");
    ecrireUnFichier.ecrireUnFichier(gs,"data/langue/"+se+".txt");
  }
  public static String ligneTradBase(String s, Map<String,String> map){
    String s2 = debutDeLigne(s);
    boolean changé=false;
    for (String s4 : map.keySet()) { // s4 vaut les clés.
      if (s4.equals(s2)){ //si on reconnait la clé dans la map.
        s2 = s2+sep+map.get(s4);
        changé=true; break;
      }//k1++;
    }
    //dans ce cas on n'enregistre pas la valeur de la traduction :
    if(!changé){s2 = s2 +sep;
    }
    //Si la ligne ce termine par [], on ne le modifie pas car c'est un nom propre.
    return s2;
  }
  public static String debutDeLigne(String s){
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
