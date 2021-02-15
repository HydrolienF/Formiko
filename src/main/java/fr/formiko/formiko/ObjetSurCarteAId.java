package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.allea;

import java.io.Serializable;

public class ObjetSurCarteAId extends ObjetAId implements Serializable{
  protected CCase p;
  protected byte direction;

  // CONSTRUCTEUR -----------------------------------------------------------------
  //Principal
  public ObjetSurCarteAId(CCase p){
    super();
    this.p = p;
    direction = getDirAllea();
  }
  //Auxiliaire
  public ObjetSurCarteAId(){
    this((CCase) null);
  }
  // GET SET -----------------------------------------------------------------------
  public CCase getCCase(){return this.p;}
  //setCase utilise des Creature pour fonctionner.
  public byte getDirection(){ return direction;}
  public void setDirection(int x){direction = (byte)x;}
  // Fonctions propre -----------------------------------------------------------
  public byte getDirAllea(){
    if(!Main.getElementSurCarteOrientéAprèsDéplacement()){return 2;}
    int direction = allea.getAlléa(7)+1;//on a 8 possibilité de 1 a 8.
    if (direction>=5){direction++;}
    return (byte)direction;
  }
}
