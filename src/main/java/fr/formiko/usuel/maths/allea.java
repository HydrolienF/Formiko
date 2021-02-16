package fr.formiko.usuel.maths;

import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.types.str;

import java.util.Random;
/**
*{@summary Random Class.}<br>
*@author Hydrolien
*@version 1.0
*/
public class allea {
  private static Random rand = new Random();
  /**
  *{@summary get a random int in [0;x[.}<br>
  *@return a random boolean.
  *@version 1.0
  */
  public static int getAllea(int x){
    if (x <= 0) { erreur.erreur("On ne peu tirer un nombre alléatoire qu'entre 2 borne positive, "+x+" ne convient pas","allea.getAlléa","On revoie -1"); return -1;}
    int xr = rand.nextInt(x);
    debug.débogage(x + " a été tiré alléatoirement");
    return xr;
  }public static int getAlléa(int x){return getAllea(x);}
  public static double getRand(){return Math.random();}
  /**
  *{@summary get a random boolean.}<br>
  *@return a random boolean.
  *@version 1.20
  */
  public static boolean getBAllea(){
    if(getAllea(2)==0){return false;}//1 chance sur 2.
    return true;
  }
  /**
  *{@summary get a random int in [].}<br>
  *@return a random int.
  *@version 1.0
  */
  public static int getAlléaDansTableau(int t []){
		return t[getAllea(t.length)];
	}
 // plein de vieille fonctions qui viènent de la version 0.2.0
  public static int getAlléaDansTableauAvecTc(int t[], int tc[]){
    int t4 [] = remplirTableauGraceATc(t,tc);
    return getAlléaDansTableau(t4);
  }
  public static int [] getTableauAlléaDansTableauAvecTc (int lentr, int t[], int tc[]){
    int tr [] = new int [lentr];
    for (int i=0; i<lentr; i++){
      tr[i] = getAlléaDansTableauAvecTc(t,tc);
    }
    return tr;
  }
  public static int [][] getTableauTableauAlléaDansTableauAvecTc (int lentr, int t[], int tc[]){
    int tr [][] = new int [lentr][1];
    for (int i=0; i<lentr; i++){
      tr[i][0] = getAlléaDansTableauAvecTc(t,tc);
    }
    return tr;
  }
  public static int [][] getTableauTableauAlléaDansTableauAvecTc(int colonne, int ligne, int t[], int tc[]){
    int tr[][] = new int [colonne][ligne];
    for(int i=0; i<colonne; i++) {
      tr[i] = getTableauAlléaDansTableauAvecTc(ligne, t, tc);
    }
    return tr;
  }
  public static int [][][] getTableauTableauTableauAlléaDansTableauAvecTc(int colonne, int ligne, int t[], int tc[]){
    int tr[][][] = new int [colonne][ligne][1];
    for(int i=0; i<colonne; i++) {
      tr[i] = getTableauTableauAlléaDansTableauAvecTc(ligne, t, tc);
    }
    return tr;
  }
  public static int [] remplirTableauGraceATc(int t[], int tc[]){
    int lentr = 0; int lentc = tc.length; int lent = t.length;
    if (lentc != lent) { erreur.erreur(g.get("allea",1,"Le tableau t n'as pas autant de case que le tableau tc"),"remplirTableauGraceATc", g.get("allea",2,"On ne prend pas en compte tc")); return t; }
    for (int i=0; i<lentc; i++){
      lentr = lentr + tc[i];
    }
    int tr [] = new int [lentr];
    int cpt =0;
    int k=0;
    while (k < lentc && cpt<lentr) {
      for (int i=0; i<tc[k];i++){
        tr[cpt]=t[k];
        cpt++;
      }
      k++;
    }             // si tc = {4,3} et t = {0,1}
    return tr; // on renvoi tr = {0,0,0,0,1,1,1}
  }


  // fluctuer
  // en %age
  public static int fluctuer(int x, byte a){ // a varit de 1 a 100;
    if (a < 1 || a > 100 || x<1){ erreur.erreur(g.get("allea",3,"Impossible de faire fluctuer une variable d'autant"),"Espece.fluctuer",g.get("allea",4,"Pas de fluctuation pour cette variable")); return x;}
    return (x*(100-a)/100) + getAlléa(math.max(((x*a)/50),1));
  }
  public static int fluctuer(int x){ //de 10% par défaut
    return fluctuer(x, (byte) 10); // 90 % + de 0% a 20%
  }
  public static int fluctuer(int x, int a){
    return fluctuer(x, str.iToBy(a));
  }
  //brute
  public static byte fluctuerBrute(byte x, byte a){ // les risques de dépassement sont pris en compte.
    int xr = x - a + getAlléa(math.max(1,a*2));
    if (xr > 127){ return (byte) 127;}
    if (xr < -128){ return (byte) -128;}
    return (byte) xr;
  }
}
