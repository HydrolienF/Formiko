package fr.formiko.formiko;

import fr.formiko.usuel.Point;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.images.Img;
// import fr.formiko.usuel.images.ThImage;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.allea;

import java.io.Serializable;

public class GCase implements Serializable{
  private CCase actuelle;
  private CCase ccases[][];
  private int width;
  private int height;
  // CONSTRUCTORS --------------------------------------------------------------
  public GCase(int width, int height){
    if(width < 0 || height < 0){ erreur.erreur("Impossible de créer une carte si petite","la carte la plus petite possible a été créée."); width = 1; height = 1;}
    this.width=width;
    this.height=height;

    ccases = new CCase[width][height];
    for (int x=0; x<width; x++) {
      for (int y=0; y<height; y++) {
        ccases[x][y]=new CCase(x,y,this);
      }
    }
  }
  public GCase(int taille){
    this(taille, taille);
  }
  public GCase(){} //only for test
  // GET SET -------------------------------------------------------------------
  public CCase getHead(){ return getCCase(0,0);}
  public Case getFirst(){ return getHead().getContent();}
  public String getDim(){ return getWidth()+";"+getHeight();}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    String s = "";
    for (int x=0; x<width; x++) {
      for (int y=0; y<height; y++) {
        s+=ccases[x][y];
      }
      s+='\n';
    }
    return s;
  }
  public CCase getCCase(int x, int y){
    if(x<0 || y<0 || x>=getWidth() || y>=getHeight()){return null;}
    return ccases[x][y];
  }
  public CCase getCCase(Point p){
    return getCCase(p.getX(),p.getY());
  }
  public int getWidth(){return width;}
  public int getHeight(){return height;}
  public int length(){ return getWidth()*getHeight();}
  public CCase getCCaseAlléa(){
    if (getHead()== null){ erreur.erreurGXVide("GCase"); return null;}
    return getCCase(allea.getAlléa(this.getWidth()), allea.getAlléa(this.getHeight()));
  }
  public void setTypes(String t[]){
    if (getHead()== null){ erreur.erreurGXVide("GCase"); return;}
    getHead().setTypes(t);
  }
  public void tourCases(){
    if (getHead()==null){
      erreur.erreur("La carte est vide");
    }else{
      getHead().tourCases();
    }
  }
}
