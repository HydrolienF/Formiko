package fr.formiko.usuel;

//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.types.*;
import fr.formiko.usuel.types.str;

public class decoderUnFichier {
  // Fonctions propre -----------------------------------------------------------
  // fonction simple
  public static String getStringDeLaLigne(String s){
    int i=0; int lens = s.length();
    String sr = "";
    while (i < lens && s.charAt(i) != ':'){ // On cherche les 2 points
      i++;
    }
    i++; //on saute les :
    if (i >= lens) { return "-1";} // pas de : dans la chaine.
    if (i < lens && s.charAt(i) == ' '){ // on saute l'espace.
      i++;
    }
    while (i < lens){
      sr = sr + s.charAt(i);
      i++;
      //debug.débogage("sr = \"" + sr + "\"");
    }
    debug.débogage("sr = \"" + sr + "\"");
    return sr;
  }
  public static int getIntDeLaLigne(String s){
    String sr = getStringDeLaLigne(s);
    return str.sToI(sr); //print une erreur si la convertion échoue.
  }
  public static byte getByteDeLaLigne(String s){
    int x = getIntDeLaLigne(s);
    if(x>127){x=127;erreur.alerte("La valeur de la ligne : "+s+" ne convient pas comme byte.","decoderUnFichier");}
    else if(x<-128){x=-128;erreur.alerte("La valeur de la ligne : "+s+" ne convient pas comme byte.","decoderUnFichier");}
    return (byte)x;
  }
  public static boolean getBooleanDeLaLigne(String x){
    String s = "";int lenx = x.length();
    if(lenx>5){s = x.substring(lenx-4,lenx);}
    if(s.equals("true")){return true;}
    if(lenx>6){s = x.substring(lenx-5,lenx);}
    if(s.equals("false")){return false;}
    return getBooleanDeLaLigne(getIntDeLaLigne(x));
  }
  public static boolean getBooleanDeLaLigne(int x){
    return str.iToB(x);
  }

  public static Boolean getBoolean(String sa){
    String s = "";
    for (int i=0;i<sa.length() ;i++ ) {
      if (sa.charAt(i) != ' '){ s = s + sa.charAt(i);}
    }
    if (s.length() != 1){
      erreur.alerte(g.get("decoderUnFichier",1,"la chaîne de caractère")+" \"" + s + "\" "+g.get("decoderUnFichier",3,"n'est pas un booléen"),"decoderUnFichier.getBoolean");
    }else {
      if (s.charAt(0) == '1'){
        return true;
      }else if (s.charAt(0) == '0'){
        return false;
      }else{
        erreur.alerte(g.get("decoderUnFichier",4,"le caractère")+" \"" + s.charAt(0) + "\" "+g.get("decoderUnFichier",3,"n'est pas un booléen")+" (1 ou 0)","decoderUnFichier.getBoolean");
      }
    }
    return null;
  }

  // fonction a tableau.

  public static String [] getTableauString(String s, char séparateur){
    int nombreDeSéparateur = getNbrDeCharDansStr(s,séparateur);
    String tr []= new String [nombreDeSéparateur +1]; int k=0;
    int lens = s.length(); int i=0;
    String sActuel = "";
    boolean ouvert = false;
    while (i < lens){
      char c = s.charAt(i);
      //debug.débogage("test du char : "+c);
      if(c=='"' && (i==0 || s.charAt(i-1) != '\\')){ // si on a un gillemet non échappé :
        if(ouvert){ ouvert = false;}
        else{ ouvert = true;}
      }
      if (c == séparateur && !ouvert){//&& (i==0 || s.charAt(i-1) != '\\') // si c'est un séparateur
        //debug.débogage("On ajoute \"" + sActuel + "\" au tableau");
        tr [k] = sActuel; k++;
        sActuel = "";
      } else { // Si on est pas encore arrivé au séparateur.
        if (s.charAt(i) != '\\' && s.charAt(i) != '"'){ // on ne recopie pas le char d'échappement ou un guillemet.
          sActuel = sActuel + str.str(c);
        }
      }
      i++;
    }
    //debug.débogage("On ajoute \"" + sActuel + "\" au tableau");
    tr [k] = sActuel;
    return tr;
  }

  public static String [] getTableauStringDeLaLigne(String ligne){
    String s = getStringDeLaLigne(ligne);
    return getTableauString(s,',');
  }


  public static String [][] getTableauTableauStringDeLaLigne(String ligne){
    String s = getStringDeLaLigne(ligne); int lens = s.length(); int i=0;
    // s = un texte,un 2a texte,autre chose;le début du 2a tableau,la fin;etc
    int nombreDePointVirgule = getNbrDeCharDansStr(ligne,';');
    String tr [][] = new String [nombreDePointVirgule+1][]; int k=0;
    String sActuel = "";
    i=0;
    while (i < lens){
      char c = s.charAt(i);
      if (c == ';'){ // si c'est un séparateur
        //debug.débogage("On ajoute \"" + sActuel + "\" au tableau de tableau de tableau");
        // Ici il faut add un tableau a un tableau de tableau.
        tr[k] = getTableauString(sActuel,','); k++;
        sActuel = "";
      } else { // Si on est pas encore arrivé au séparateur.
        sActuel = sActuel + str.str(c);
      }
      i++;
    }
    //debug.débogage("On ajoute \"" + sActuel + "\" au tableau de tableau");
    tr[k] = getTableauString(sActuel,',');
    //tr = tableau.ajouteX(tr,sActuel); // la dernière String du tableau n'as pas de virgule après elle.
    return tr;
  }

  public static int [] getTableauIntDeLaLigne(String ligne){
    return ent.ent(getTableauStringDeLaLigne(ligne));
  }
  public static int [][] getTableauTableauIntDeLaLigne(String ligne){
    return ent.ent(getTableauTableauStringDeLaLigne(ligne));
  }

  public static int getNbrDeCharDansStr(String s, char c){
    int lens = s.length(); int kr =0;
    for (int i=0;i<lens ;i++ ) {
      if (s.charAt(i) == c) { kr++;}
    }
    return kr;
  }

  // OBJET

  public static String [] getObjet(String s){
    return getTableauString(s,'-');
  }

  // FOURMI



}
