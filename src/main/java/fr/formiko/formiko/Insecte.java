package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.interfaces.*;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.maths.math;

import java.io.Serializable;

/**
*{@summary Insecte class}<br>
*This class is used to have all the insecte on the map. Insecte are usualy eated by ant but some will eat ant.<br>
*Specials insectes extends this class.<br>
*Almost all the var can be found in Creature.java<br>
*@author Hydrolien
*@version 1.13
*/
public class Insecte extends Creature implements Serializable{
  protected boolean femelle;
  protected byte nourritureMangeable;
  protected static GIEspece gie;
  // CONSTRUCTEUR -----------------------------------------------------------------
  /**
  *{@summary Main constructor for Insecte.}<br>
  *All args are Insecte var.
  *@version 1.13
  */
  public Insecte (CCase p, int age, int ageMax, int actionMax){
    // Soit l'insecte est terrestre et vien de naitre, soit il est volant et il est mort.
    super(p,age,ageMax, actionMax);
    if (action == 0){
      estMort = true;
      age = ageMax;
      tour = new TourCreatureMorte();
    }else{
      tour = new TourInsecte();
    }
    this.nourritureMangeable =(byte) (allea.getAllea(3)+2);// de 2 a 5.
    this.déplacement = new DeplacementFourmi();
    this.chasse = new ChasseHerbivore();
    p.getContent().getGc().add(this);
    setType(getRandomTypeInsectOnTheCase());
    stade = (byte)0; // doit apparaitre en -3 pour etre un oeuf.
    mourir = new MourirInsecte();
    setNourritureFournie(e.getNourritureFournie(getStade()));
    femelle = allea.getBAllea();
    debug.débogage("L'insecte "+ this.id + " a été  créée");
  }
  /**
  *{@summary constructor for Insecte.}<br>
  *Here we only know the location of the insecte, random value will be add for ageMax and actionMax.
  *@version 1.13
  */
  public Insecte (CCase p){
    this(p, 0,10 + allea.getAlléa(101), allea.getAlléa(21));//action entre 0 et 20.
  }
  /**
  *{@summary constructor for Insecte.}<br>
  *Here know nothing, the location of the insecte will be shoose randomly on the actual GCase of Main. Random value will be add for ageMax and actionMax.
  *@version 1.13
  */
  public Insecte (){ //on place l'insecte alléatoirement. //  en théorie soit il nait et il a la case de ca mere, soit il vient d'autre par et dans ce cas il apparait sur une case en bordure de carte.
    this(Main.getGc().getCCaseAlléa());
  }

  // GET SET -----------------------------------------------------------------------
  public boolean getFemelle(){return femelle;}
  public void setFemelle(boolean b){femelle=b;}
  public byte getNourritureMangeable(){ return nourritureMangeable;}
  public void setNourritureMangeable(byte x){ nourritureMangeable=x;} public void setNourritureMangeable(int x){ setNourritureMangeable((byte)x);}
  @Override
  public byte getType(){ return (byte)(getEspece().getId()-100);}
  /**
  *{@summary set type &#38; Espece.}
  *@version 2.6
  */
  public void setType(byte x){
    super.setEspece(Main.getEspeceParId(100+x));
    if(e==null){erreur.erreur("Une espece d'insecte n'as pas pu etre chargé : "+(100+getType()));}
  }
  public void setType(int x){setType((byte)x);}
  public byte getRandomTypeInsectOnTheCase(){return gie.getRandomTypeInsectOnTheCase(getCCase().getContent().getType());}
  /**
  *{@summary set type &#38; Espece.}
  *@version 2.6
  */
  @Override
  public void setEspece(Espece ex){
    super.setEspece(ex);
  }
  @Override
  public boolean getVole(){if(getStade()!=0){return false;}return e.getVole();}//si c'est un imago ca dépend de l'espece.
  public static void setGie(){ gie=new GIEspece();}//initialise le fichier/
  @Override
  public String getNom(){return g.get("I"+getType());}
  // Fonctions propre -----------------------------------------------------------
  /**
  *{@summary Print all information about the Insecte.}<br>
  *@version 1.13
  */
  @Override
  public String toString(){
    String sr = super.toString();sr+=", ";
    sr+= " "+g.get("nourritureMangeable")+" "+nourritureMangeable;
    return sr;
  }

  /**
  *{@summary return true if this whant some food.}
  *Insecte alway whant food from ant exept if they can eat it.
  *@version 1.29
  */
  public boolean wantFood(){
    return getNourriture()<getNourritureMax();
  }
  /**
  *{@summary return true if this whant to be clean.}
  *@version 1.29
  */
  public boolean wantClean(){
    if(propreté>99){return false;}
    return true;
  }

}
