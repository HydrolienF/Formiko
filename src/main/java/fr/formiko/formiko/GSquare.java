package fr.formiko.formiko;

import fr.formiko.usual.Point;
import fr.formiko.usual.decoderUnFichier;
import fr.formiko.usual.erreur;
import fr.formiko.usual.images.Img;
import fr.formiko.usual.images.Images;
import fr.formiko.usual.maths.allea;
import fr.formiko.usual.structures.listes.Liste;
import fr.formiko.usual.types.str;

import java.io.Serializable;

/**
*{@summary A Groups of squares with most of the map items.}<br>
*@author Hydrolien
*@lastEditedVersion 2.24
*/
public class GSquare implements Serializable {
  private CSquare actuelle;
  private CSquare ccases[][];
  private int width;
  private int height;
  // CONSTRUCTORS --------------------------------------------------------------
  public GSquare(int width, int height){
    if(width < 0 || height < 0){ erreur.erreur("Impossible de créer une carte si petite","la carte la plus petite possible a été créée."); width = 1; height = 1;}
    this.width=width;
    this.height=height;

    ccases = new CSquare[width][height];
    for (int x=0; x<width; x++) {
      for (int y=0; y<height; y++) {
        ccases[x][y]=new CSquare(x,y,this);
      }
    }
  }
  public GSquare(int taille){
    this(taille, taille);
  }
  public GSquare(){} //only for test
  // GET SET -------------------------------------------------------------------
  public CSquare getHead(){ return getCSquare(0,0);}
  public Square getFirst(){ return getHead().getContent();}
  public String getDim(){ return getWidth()+";"+getHeight();}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    String s = "";
    for (int x=0; x<getWidth(); x++) {
      for (int y=0; y<getHeight(); y++) {
        s+=ccases[x][y];
      }
      s+='\n';
    }
    return s;
  }
  public CSquare getCSquare(int x, int y){
    if(x<0 || y<0 || x>=getWidth() || y>=getHeight()){return null;}
    return ccases[x][y];
  }
  public CSquare getCSquare(Point p){
    return getCSquare(p.getX(),p.getY());
  }
  public int getWidth(){return width;}
  public int getHeight(){return height;}
  public int length(){ return getWidth()*getHeight();}
  public CSquare getCSquareAlléa(){
    if (getHead()== null){ erreur.erreurGXVide("GSquare"); return null;}
    return getCSquare(allea.getAlléa(this.getWidth()), allea.getAlléa(this.getHeight()));
  }
  /**
  *{@summary Set types for all squares from String[] as a csv file.}<br>
  *@lastEditedVersion 2.24
  */
  public void setTypes(String t[]){
    for (int y=0; y<getHeight(); y++) {
      String t2 [] = decoderUnFichier.getTableauString(t[y],',');
      for (int x=0; x<getWidth(); x++) {
        getCSquare(x,y).getContent().setType(str.sToI(t2[x]));
      }
    }
  }
  /**
  *{@summary Play a turn for all squares.}<br>
  *@lastEditedVersion 2.24
  */
  public void tourSquares(){
    if(!Main.getPartie().getAppartionGraine()){return;}
    for (int x=0; x<getWidth(); x++) {
      for (int y=0; y<getHeight(); y++) {
        getCSquare(x,y).getContent().actualisationGraine(getCSquare(x,y));
      }
    }
  }
  /**
  *{@summary Check that it's a correct x.}<br>
  *If it's not it will be set at max or min value.
  *@lastEditedVersion 2.24
  */
  private int asX(int x){
    if(x<0){return 0;}
    else if(x>=getWidth()){return getWidth()-1;}
    else{return x;}
  }
  /**
  *{@summary Check that it's a correct y.}<br>
  *If it's not it will be set at max or min value.
  *@lastEditedVersion 2.24
  */
  private int asY(int y){
    if(y<0){return 0;}
    else if(y>=getHeight()){return getHeight()-1;}
    else{return y;}
  }
  /**
  *{@summary Give all Square in a rectangle as a list.}<br>
  *@param x1 x of the 1a Square
  *@param y1 y of the 1a Square
  *@param x2 x of the 2a Square
  *@param y2 y of the 2a Square
  *@lastEditedVersion 2.24
  */
  public Liste<Square> getSquaresBetween(int x1, int y1, int x2, int y2){
    if(x1>x2){
      int x=x2;
      x2=x1;
      x1=x;
    }
    if(y1>y2){
      int y=y2;
      y2=y1;
      y1=y;
    }
    x1=asX(x1);
    y1=asY(y1);
    x2=asX(x2);
    y2=asY(y2);
    Liste<Square> gc = new Liste<Square>();
    for (int x=x1; x<=x2; x++) {
      for (int y=y1; y<=y2; y++) {
        gc.add(getCSquare(x,y).getContent());
      }
    }
    return gc;
  }
  /**
  *{@summary Give all Square in a rectangle as a list.}<br>
  *@param from 1a corner of the rectangle
  *@param to 2a corner of the rectangle
  *@lastEditedVersion 2.24
  */
  public Liste<Square> getSquaresBetween(CSquare from, CSquare to){
    return getSquaresBetween(from.getX(), from.getY(), to.getX(), to.getY());
  }
  /**
  *{@summary Give all Square in a rectangle as a list.}<br>
  *@param center of the rectangle
  *@param radius distance with the wall of the rectangle
  *@lastEditedVersion 2.24
  */
  public Liste<Square> getSquaresBetween(CSquare center, int radius){
    if(radius<0){return new Liste<Square>();}
    return getSquaresBetween(center.getX()-radius, center.getY()-radius, center.getX()+radius, center.getY()+radius);
  }
}
