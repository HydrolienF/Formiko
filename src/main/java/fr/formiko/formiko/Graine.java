package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.allea;

import java.io.Serializable;

// TODO Les graines apparaissent surtout en automne (80%) (saison encore a créer.)
public class Graine extends ObjetSurCarteAId implements Serializable{
  private int nourritureFournie;
  private byte dureté; // il faut de bonne mandibule pour pouvoir ouvrir des graines de grande dureté, mais celle si contiène souvent plus de nourriture !
  private boolean ouverte; // une graine ouverte est mangeable.
  private byte type;
  private byte tempsAvantDecomposition;

  // CONSTRUCTORS --------------------------------------------------------------
  public Graine(CCase ccase, int nourritureFournie, byte dureté){
    super(ccase); ouverte = false;
    this.nourritureFournie = nourritureFournie; this.dureté = dureté;
    type = (byte) allea.getAlléa(4);//0,1 ou 2.
    tempsAvantDecomposition = (byte)(20 + allea.getAlléa(100));// de 19 a 119
    if(ccase!=null){ccase.getGg().addGraine(this);}
  }
  public Graine(CCase ccase){
    this(ccase,allea.getAlléa(400)+10,(byte) 0);
    setDureté(getNourritureFournie()/10 + allea.getAlléa(80)); // de 1 a 41 + de 0 a 80.
  }
  public Graine(){
    this(Main.getGc().getCCaseAlléa());
  }
  // GET SET -------------------------------------------------------------------
  public int getNourritureFournie(){ return nourritureFournie;}
  public void setNourritureFourie(int x){ nourritureFournie = x;}
  public byte getDureté(){ return dureté;}
  public void setDureté(byte x){ dureté = x;}
  public void setDureté(int x){ if(x<-128 || x>127){ erreur.erreur("byte inoptencible depuis "+x);return;}setDureté((byte)x);}
  public boolean getOuverte(){ return ouverte;}
  public void setOuverte(boolean b){ouverte = b;}
  /**
  *{@summary Move the Graine from a case to an other.}<br>
  *It is used by Deplacement interfaces.<br>
  *It wil try to remove from old CCase and add to new CCase.<br>
  *@version 1.40
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
  public byte getTempsAvantDecomposition(){ return tempsAvantDecomposition;}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    String adjOuverte; if(ouverte){ adjOuverte = "est ouverte"; }else{ adjOuverte = "est fermée";}
    String s = "Graine "+id+", nourritureFournie : "+nourritureFournie+", dureté : "+dureté+", "+adjOuverte;
    return s;
  }
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@version 1.31
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
    Main.getGc().getCCase(ccase.getContent().getX(),ccase.getContent().getY()).getGg().retirerGraine(this);//on retire la graine de sa liste.
    this.setCCase(null);
  }
  public void casser(){
    ouverte=true;
  }
  public void tour(){
    if(getCCase().getContent().getFere()==null){//si la graine n'est pas dans une fourmiliere :
      tempsAvantDecomposition--;
      if(tempsAvantDecomposition<0){mourrir();}//la graine pourrie.
    }
  }
}
