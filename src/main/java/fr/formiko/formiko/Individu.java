package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.tableau;

import java.io.Serializable;

public class Individu implements Serializable{
  protected Espece e; // on la relie avec un id dans le tableau .csv
  protected byte type; // 0  Reine 1 = Male 2 = Minor 3 Medium 4 = Major 5 = soldate (etc).
  protected boolean sexué; // false = pas sexué
  protected boolean sexe; // false = femmelle; true = Male;
  protected byte nétoyage;
  protected byte maxAction; // dans certaine espèces les larves travaille en créant du fil de soie.
  protected int taille;
  protected String couleur;
  protected byte poidMax; //(en gramme) si le poid est supérieur a 10% la fourmi peine et ses déplacements coute plus chere.
  protected byte tempsDeRepos; // en heure (ou en 24a de tour).
  protected int foodConso;

  protected int tMaxAge[] = new int[4]; // sauf l'age adulte qui vari.
  protected int tMaxFood[]= new int[4]; // sauf la maxFood adulte.
  protected byte tCoutAction[] = new byte[6];
  // CONSTRUCTORS --------------------------------------------------------------
  public Individu(int idEsp, byte ty, boolean se,boolean se2, byte né, byte ac, int ta, String co, byte po, byte te, int tag0, int tag1, int tag2, int tag3, int nm0, int nm1, int nm2, int nm3,byte ca0,byte ca1,byte ca2,byte ca3,byte ca4,byte ca5,int nc){
    e = Main.getGEspece().getEspeceParId(idEsp);
    type = ty; sexué = se; sexe = se2; nétoyage = né; maxAction = ac; taille =ta; couleur = co; poidMax =po; tempsDeRepos = te;
    tMaxAge[0] = tag0; tMaxAge[1] = tag1; tMaxAge[2] = tag2; tMaxAge[3] = tag3;
    tMaxFood[0] = nm0; tMaxFood[1] = nm1; tMaxFood[2] = nm2; tMaxFood[3] = nm3;
    tCoutAction[0] = ca0; tCoutAction[1] = ca1; tCoutAction[2] = ca2; tCoutAction[3] = ca3; tCoutAction[4] = ca4; tCoutAction[5] = ca5;
    foodConso=nc;
    debug.débogage("Fin de la création d'un individu");
  }
  // GET SET -------------------------------------------------------------------
  public Espece getEspece(){ return e;}
  public byte getType(){ return type;}
  public byte getMaxAction(){ return maxAction;}
  public int getTaille(){ return taille;}

  public int getMaxAge(int i){ return tMaxAge[i]/2;} //to make it shorter
  public int getMaxAge(){ return getMaxAge(3);}
  public int getMaxFood(int i){ return tMaxFood[i];}
  public int getMaxFood(){ return getMaxFood(3);}

  public byte getMovingCost(){ return tCoutAction[0];}
  public byte getCoutChasse(){ return tCoutAction[1];}
  public byte getCoutPondre(){ return tCoutAction[2];}
  public byte getCoutNétoyer(){ return tCoutAction[3];}
  public byte getCoutTrophallaxie(){ return tCoutAction[4];}
  public byte getNétoyage(){return nétoyage;}
  public String getStringType(){
    String ind = "";
    if (type== 0){ ind = g.get("reine");}
    else if (type== 1){ ind = g.get("male");}
    else if (type== 2){ ind = g.get("minor");}
    else if (type== 3){ ind = g.get("médias");}
    else if (type== 4){ ind = g.get("major");}
    else if (type== 5){ ind = g.get("soldate");}
    else{ ind = g.get("individu")+" "+type;}
    return ind;
  }
  public int getFoodConso(int stade){
    if(stade==-3){ return 0;}
    return foodConso;
  }
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    GString adj = new GString();//♂ ♀
    if(sexué){ String sex = "♀"; if(sexe){ sex = "♂";} adj.add(g.get("sexué")+" ("+sex+")");}
    else{adj.add(g.get("assexué"));}
    adj.add(couleur);
    String ind = getStringType();
    String s = "";
    s+=ind +" "+ adj.toString();s+="\n";
    s+=g.get("nétoyage")+" : "+nétoyage;s+="\n";
    s+=g.get("maxAction")+" : "+maxAction;s+="\n";
    s+=g.get("taille")+" : "+taille;s+="\n";
    s+=g.get("poidsSupportable")+" : "+poidMax;s+="\n";
    //System.out.println("Temps de repos néssésaire (par tour)")+" : "+tempsDeRepos);
    s+=g.get("tMaxAge")+" : ";
    s+=tableau.tableauToString(tMaxAge);s+="\n";
    s+=g.get("tMaxFood")+" : ";
    s+=tableau.tableauToString(tMaxFood);s+="\n";
    s+=g.get("tCoutAction")+" : ";
    s+=tableau.tableauToString(tCoutAction);s+="\n";
    return s;
  }
}
