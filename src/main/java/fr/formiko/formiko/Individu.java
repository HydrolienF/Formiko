package fr.formiko.formiko;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.structures.listes.GString;
import fr.formiko.usual.tableau;

import java.io.Serializable;

public class Individu implements Serializable {
  protected int idSpecies; // on la relie avec un id dans le tableau .csv
  protected byte type; // 0  Reine 1 = Male 2 = Minor 3 Medium 4 = Major 5 = soldate (etc).
  protected boolean sexual; // false = pas sexual
  protected boolean sexe; // false = femmelle; true = Male;
  protected byte cleaning;
  protected byte maxAction; // dans certaine espèces les larves travaille en créant du fil de soie.
  protected int size;
  protected String color;
  protected byte maxMassMovable; //(en gramme) si le poid est supérieur a 10% la fourmi peine et ses déplacements coute plus chere.
  protected byte sleepTime; // en heure (ou en 24a de tour).
  protected int foodConsume;

  protected int tMaxAge[] = new int[4]; // sauf l'age adulte qui vari.
  protected int tMaxFood[]= new int[4]; // sauf la maxFood adulte.
  protected byte tActionCost[] = new byte[6];
  // CONSTRUCTORS --------------------------------------------------------------
  public Individu(int idSpecies, byte ty, boolean se,boolean se2, byte né, byte ac, int ta, String co, byte po, byte te, int tag0, int tag1, int tag2, int tag3, int nm0, int nm1, int nm2, int nm3,byte ca0,byte ca1,byte ca2,byte ca3,byte ca4,byte ca5,int nc){
    this.idSpecies=idSpecies;
    type = ty; sexual = se; sexe = se2; cleaning = né; maxAction = ac; size =ta; color = co; maxMassMovable =po; sleepTime = te;
    tMaxAge[0] = tag0; tMaxAge[1] = tag1; tMaxAge[2] = tag2; tMaxAge[3] = tag3;
    tMaxFood[0] = nm0; tMaxFood[1] = nm1; tMaxFood[2] = nm2; tMaxFood[3] = nm3;
    tActionCost[0] = ca0; tActionCost[1] = ca1; tActionCost[2] = ca2; tActionCost[3] = ca3; tActionCost[4] = ca4; tActionCost[5] = ca5;
    foodConsume=nc;
    debug.débogage("Fin de la création d'un individu");
  }
  // GET SET -------------------------------------------------------------------
  @JsonIgnore
  public Espece getEspece(){ return Main.getGEspece().getEspeceById(idSpecies);}
  public int getIdSpecies(){return idSpecies;}
  public byte getType(){ return type;}
  public byte getMaxAction(){ return maxAction;}
  public int getSize(){ return size;}

  public int getMaxAge(int i){ return tMaxAge[i]/2;} //to make it shorter
  public int getMaxAge(){ return getMaxAge(3);}
  public int getMaxFood(int i){ return tMaxFood[i];}
  public int getMaxFood(){ return getMaxFood(3);}

  public byte getMovingCost(){ return tActionCost[0];}
  public byte getCoutChasse(){ return tActionCost[1];}
  public byte getCoutPondre(){ return tActionCost[2];}
  public byte getCoutNétoyer(){ return tActionCost[3];}
  public byte getCoutTrophallaxie(){ return tActionCost[4];}
  public byte getNétoyage(){return cleaning;}
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
    return foodConsume;
  }
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    GString adj = new GString();//♂ ♀
    if(sexual){ String sex = "♀"; if(sexe){ sex = "♂";} adj.add(g.get("sexual")+" ("+sex+")");}
    else{adj.add(g.get("assexual"));}
    adj.add(color);
    String ind = getStringType();
    String s = "";
    s+=ind +" "+ adj.toString();s+="\n";
    s+=g.get("cleaning")+" : "+cleaning;s+="\n";
    s+=g.get("maxAction")+" : "+maxAction;s+="\n";
    s+=g.get("size")+" : "+size;s+="\n";
    s+=g.get("poidsSupportable")+" : "+maxMassMovable;s+="\n";
    //erreur.println("Time de repos néssésaire (par tour)")+" : "+sleepTime);
    s+=g.get("tMaxAge")+" : ";
    s+=tableau.tableauToString(tMaxAge);s+="\n";
    s+=g.get("tMaxFood")+" : ";
    s+=tableau.tableauToString(tMaxFood);s+="\n";
    s+=g.get("tActionCost")+" : ";
    s+=tableau.tableauToString(tActionCost);s+="\n";
    return s;
  }
}
