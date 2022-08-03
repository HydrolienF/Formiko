package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.maths.allea;

import java.io.Serializable;

// TODO Les graines apparaissent surtout en automne (80%) (saison encore a créer.)
public class Graine extends ObjetSurCarteAId implements Serializable{
  private int givenFood;
  private byte hardness; // il faut de bonne mandibule pour pouvoir ouvrir des graines de grande hardness, mais celle si contiène souvent plus de food !
  private boolean ouverte; // une graine ouverte est mangeable.
  private byte type;
  private byte tempsAvantDecomposition;

  // CONSTRUCTORS --------------------------------------------------------------
  public Graine(CCase ccase, int givenFood, byte hardness){
    super(ccase); ouverte = false;
    this.givenFood = givenFood; this.hardness = hardness;
    type = (byte) allea.getAlléa(4);//0,1 ou 2.
    tempsAvantDecomposition = (byte)(20 + allea.getAlléa(100));// de 19 a 119
    if(ccase!=null){ccase.getGg().add(this);}
  }
  public Graine(CCase ccase){
    this(ccase,allea.getAlléa(400)+10,(byte) 0);
    setHardness(getGivenFood()/10 + allea.getAlléa(80)); // de 1 a 41 + de 0 a 80.
  }
  public Graine(){
    this(Main.getGc().getCCaseAlléa());
  }
  // GET SET -------------------------------------------------------------------
  public int getGivenFood(){ return givenFood;}
  public void setGivenFood(int x){ givenFood = x;}
  public byte getHardness(){ return hardness;}
  public void setHardness(byte x){ hardness = x;}
  public void setHardness(int x){ if(x<-128 || x>127){ erreur.erreur("byte inoptencible depuis "+x);return;}setHardness((byte)x);}
  public boolean getOuverte(){ return isOpen();}
  public boolean isOpen(){ return ouverte;}
  public void setOpen(boolean b){ouverte=b;}
  public void setOuverte(boolean b){setOpen(b);}
  public void casser(){setOuverte(true);}
  /**
  *{@summary Return true if it can be open by given Creature.}<br>o
  *@lastEditedVersion 2.29
  */
  public boolean canBeOpenBy(Creature c){
    return (!isOpen() && c instanceof Fourmi && ((Fourmi)c).getHardnessMax()>getHardness());
  }
  /**
  *{@summary Move the Graine from a case to an other.}<br>o
  *It is used by Deplacement interfaces.<br>
  *It wil try to remove from old CCase and add to new CCase.<br>
  *@lastEditedVersion 1.40
  */
  public void setCCase(CCase newCCase){
    if(this.ccase!=null){
      this.ccase.getContent().getGg().remove(this);
    }
    this.ccase = newCCase;
    if(newCCase!=null){
      newCCase.getContent().getGg().add(this);
    }
  }
  public byte getType(){ return type;}
  public byte getTimeAvantDecomposition(){ return tempsAvantDecomposition;}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    String adjOuverte; if(ouverte){ adjOuverte = "est ouverte"; }else{ adjOuverte = "est fermée";}
    String s = "Graine "+id+", givenFood : "+givenFood+", hardness : "+hardness+", "+adjOuverte;
    return s;
  }
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@lastEditedVersion 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof Graine)){return false;}
    Graine g = (Graine)o;
    if (getId() == g.getId()){ return true;}
    return false;
  }
  public void mourrir(){
    debug.débogage("Lancement de la mort de la graine.");
    Main.getGc().getCCase(ccase.getContent().getX(),ccase.getContent().getY()).getGg().remove(this);//on retire la graine de sa liste.
    this.setCCase(null);
  }
  public void tour(){
    if(getCCase().getContent().getFere()==null){//si la graine n'est pas dans une fourmiliere :
      tempsAvantDecomposition--;
      if(tempsAvantDecomposition<0){mourrir();}//la graine pourrie.
    }
  }
}
