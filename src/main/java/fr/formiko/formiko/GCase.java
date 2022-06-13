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
  private CCase début;
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
        ccases[x][y]=new CCase(x,y);
      }
    }

    // début = new CCase(new Case(0,0));
    début = getCCase(0,0);
    addDroite(width-1, getHead());
    int d = 1; CCase début2;
    while (d < height){
      // début2 = new CCase(new Case(0,d));
      début2 = getCCase(0,d);
      d++;
      addDroite(width-1, début2);
      fusionnnerLigne(début2);
    }
  }
  public GCase(int taille){
    this(taille, taille);
  }
  public GCase(){} //only for test
  // GET SET -------------------------------------------------------------------
  public CCase getHead(){ return début;}
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
  public void addDroite(int x, CCase débutDeLaLigne){
    debug.débogage("Création d'une ligne");
    int k = 1; CCase temp; actuelle = débutDeLaLigne;
    while (x>0){
      temp = getCCase(k,actuelle.getContent().getPoint().getY()); k++;
      actuelle.setRigth(temp);
      temp.setLeft(actuelle);
      actuelle = temp;
      x--;
    }
  }
  public void fusionnnerLigne(CCase débutDeLaLigne2){
    CCase débutDeLaLigne = getHead();
    while (débutDeLaLigne.getDown() != null){
      débutDeLaLigne = débutDeLaLigne.getDown();
    }
    CCase tempHaut = débutDeLaLigne;
    CCase tempBas = débutDeLaLigne2;
    while(tempHaut != null && tempBas != null){
      tempHaut.setDown(tempBas);
      tempBas.setUp(tempHaut);
      tempHaut = tempHaut.getRigth();
      tempBas = tempBas.getRigth();
    }
  }
  public void tourCases(){
    if (getHead()==null){
      erreur.erreur("La carte est vide");
    }else{
      getHead().tourCases();
    }
  }
  // public void add(Case c){
  //   if (getHead()==null){début = new CCase(c);return;}
  //   getHead().add(c);
  // }
}
