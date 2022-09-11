package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.maths.allea;

import java.io.Serializable;

public class ObjetSurCarteAId extends ObjetAId implements Serializable{
  protected CSquare ccase;
  protected byte direction;

  // CONSTRUCTORS ----------------------------------------------------------------
  //Principal
  public ObjetSurCarteAId(CSquare ccase){
    super();
    this.ccase = ccase;
    direction = getDirAllea();
  }
  //Auxiliaire
  public ObjetSurCarteAId(){
    this((CSquare) null);
  }
  // GET SET ----------------------------------------------------------------------
  public CSquare getCSquare(){return this.ccase;}
  public Square getSquare(){return getCSquare().getContent();}
  //setSquare est Override par Creature & Graine.
  public void setCSquare(CSquare cc){ this.ccase = cc;}
  public void setCSquare(int x, int y){setCSquare(Main.getGc().getCSquare(x,y));}
  public void setCc(CSquare cc){setCSquare(cc);}
  public byte getDirection(){ return direction;}
  public void setDirection(int x){direction = (byte)x;}
  // FUNCTIONS -----------------------------------------------------------------
  public byte getDirAllea(){
    if(!Main.getFop().getBoolean("orientedObjectOnMap")){return 2;}
    int direction = allea.getAlléa(7)+1;//on a 8 possibilité de 1 a 8.
    if (direction>=5){direction++;}
    return (byte)direction;
  }
}
