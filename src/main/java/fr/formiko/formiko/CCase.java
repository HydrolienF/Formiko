package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usual.Point;
import fr.formiko.usual.erreur;
import fr.formiko.usual.structures.listes.Liste;

import java.io.Serializable;

public class CCase implements Serializable{
  private Case contenu;
  private GCase gc;

  // CONSTRUCTORS --------------------------------------------------------------
  public CCase(Case contenu, GCase gc){
    if(contenu==null){erreur.erreur("content is empty !");}
    if(gc==null){erreur.erreur("gc is empty !");}
    this.contenu=contenu;
    this.gc=gc;
  }
  public CCase(int x, int y, GCase gc){
    this(new Case(x,y), gc);
  }
  public CCase(Case contenu){
    this(contenu,null);
  }
  public CCase(int x, int y){
    this(x, y, null);
  }
  // GET SET -------------------------------------------------------------------
  public CCase getUp(){return getGc().getCCase(getX(),getY()-1);}
  public CCase getDown(){return getGc().getCCase(getX(),getY()+1);}
  public CCase getRigth(){return getGc().getCCase(getX()+1,getY());}
  public CCase getLeft(){return getGc().getCCase(getX()-1,getY());}
  public Case getContent(){return contenu;}
  public void setContenu(Case c){contenu = c;}
  public GGraine getGg(){return contenu.getGg();}
  public int getX(){return getContent().getX();}
  public int getY(){return getContent().getY();}
  public GCase getGc() {return gc;}
	// public void setGc(GCase gc) {this.gc=gc;}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    return getContent().toString();
  }
  public String desc(){ return contenu.desc();}
  /**
  *{@summary Standard equals function.}<br>
  *Null &#38; other class type proof.<br>
  *@param o o is the Object to test. It can be null or something else than this class.
  *@lastEditedVersion 1.31
  */
  @Override
  public boolean equals(Object o){ // on ne peu pas tt vérifié facilement alors on ce contente de vérifié les co X Y du point et le nbr de connection.
    if(o==null || !(o instanceof CCase)){return false;}
    CCase cc = (CCase)o;
    if (cc.nbrDeCaseVoisine() != this.nbrDeCaseVoisine()){ return false;}
    return cc.getContent().equals(this.getContent());
  }
  public GGraine getGg(int radius){
    GGraine gir = new GGraine();
    for (Case c : getGc().getCasesBetween(this, radius)) {
      gir.add(c.getGg());
    }
    return gir;
  }
  public Liste<Case> getGca(int radius){
    return getGc().getCasesBetween(this, radius);
  }
  /**
  *{@summary return a direction by using this &#38; an other CCase to reach.}
  *@lastEditedVersion 2.11
  */
  public int getDirection(CCase to){
    if (to==null){ return 5;}
    int x = this.getContent().getX() - to.getContent().getX();
    int y = this.getContent().getY() - to.getContent().getY();
    return getDirectionFromXY(x,y);
  }
  /**
  *{@summary return a direction by using this &#38; a Case to reach.}
  *@lastEditedVersion 2.11
  */
  public int getDirection(Case to){
    if (to==null){ return 5;}
    int x = this.getContent().getX() - to.getX();
    int y = this.getContent().getY() - to.getY();
    return getDirectionFromXY(x,y);
  }
  /**
  *{@summary return a direction by using 2 Point.}
  *@lastEditedVersion 2.11
  */
  public static int getDirection(Point from, Point to){
    int x = from.getX() - to.getX();
    int y = from.getY() - to.getY();
    return getDirectionFromXY(x,y);
  }
  /**
  *{@summary return a direction by using difference in x &#38; in y.}
  *@lastEditedVersion 2.11
  */
  private static int getDirectionFromXY(int x, int y){
    //int xabs = valAbs(x); int yabs = valAbs(y); on pourrait utiliser ces données pour aller parfois juste en x parfois juste en y lorsque le trajet n'est pas conplètement en diagonale. (cad lorsque xabs == yabs)
    // x est négatif si le point ou l'on veux ce rendre est plus a droite.
    // y est négatif si le point ou l'on veux ce rendre est plus bas.
    if (x < 0){
      if (y < 0){
        return 9;
      }else if (y > 0){
        return 3;
      }else{
        return 6;
      }
    }else if(x > 0){
      if (y < 0){
        return 7;
      }else if (y > 0){
        return 1;
      }else{
        return 4;
      }
    }else{ //x==0
      if (y < 0){
        return 8;
      }else if (y > 0){
        return 2;
      }else{
        return 5;
      }
    }
  }
  public String getPoint(){
    return contenu.description();
  }
  public GInsecte getGi(){return getGi(0);}
  public GInsecte getGi(int radius){
    GInsecte gir = new GInsecte();
    for (Case c : getGc().getCasesBetween(this, radius)) {
      gir.add(c.getGi());
    }
    return gir;
  }

  public byte nbrDeCaseVoisine(){
    if(getGc()==null){return 0;}
    byte xr = 0;
    if (getUp()!= null){xr++;}
    if (getDown()!= null){xr++;}
    if (getRigth()!= null){xr++;}
    if (getLeft()!= null){xr++;}
    return xr;
  }
}
