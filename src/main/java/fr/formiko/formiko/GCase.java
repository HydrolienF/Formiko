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
    début = new CCase(new Case(0,0));
    addDroite(width-1, début);
    int d = 1; CCase début2;
    while (d < height){
      début2 = new CCase(new Case(0,d)); d++;
      addDroite(width-1, début2);
      fusionnnerLigne(début2);
    }
    ccases = new CCase[width][height];
    for (int x=0; x<width; x++) {
      for (int y=0; y<height; y++) {
        ccases[x][y]=new CCase(x,y);
      }
    }
  }
  public GCase(int taille){
    this(taille, taille);
  }
  public GCase(){
    this(0);
  }
  // GET SET -------------------------------------------------------------------
  public CCase getHead(){ return début;}
  public Case getFirst(){ return getHead().getContent();}
  public String getDim(){ return getWidth()+";"+getHeight();}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    if (début==null){erreur.erreur("La carte est vide");return "";}
    return début.toString();
  }
  public CCase getCCase(int x, int y){
    if(début==null){
      return null;
    }else{
      if (x==0 && y==0){
        return début;
      }
    }
    return début.getCCase(x,y);
  }
  public CCase getCCase(Point p){
    return getCCase(p.getX(),p.getY());
  }
  public int getNbrX(){
    if (début== null){ return 0;}
    return début.getNbrX();
  }public int getWidth(){return getNbrX();}
  public int getNbrY(){
    if (début== null){ return 0;}
    return début.getNbrY();
  }public int getHeight(){return getNbrY();}
  public int getNbrDeCase(){
    return getNbrX()*getNbrY();
  }public int length(){ return getNbrDeCase();}
  public CCase getCCaseAlléa(){
    if (début== null){ erreur.erreurGXVide("GCase"); return null;}
    return getCCase(allea.getAlléa(this.getNbrX()), allea.getAlléa(this.getNbrY()));
  }
  public void setTypes(String t[]){
    if (début== null){ erreur.erreurGXVide("GCase"); return;}
    début.setTypes(t);
  }
  public void addDroite(int x, CCase débutDeLaLigne){
    debug.débogage("Création d'une ligne");
    int k = 1; CCase temp; actuelle = débutDeLaLigne;
    while (x>0){
      temp = new CCase(new Case(k,actuelle.getContent().getPoint().getY())); k++;
      actuelle.setDroite(temp);
      temp.setGauche(actuelle);
      actuelle = temp;
      x--;
    }
  }
  public void fusionnnerLigne(CCase débutDeLaLigne2){
    CCase débutDeLaLigne = début;
    while (débutDeLaLigne.getBas() != null){
      débutDeLaLigne = débutDeLaLigne.getBas();
    }
    CCase tempHaut = débutDeLaLigne;
    CCase tempBas = débutDeLaLigne2;
    while(tempHaut != null && tempBas != null){
      tempHaut.setBas(tempBas);
      tempBas.setHaut(tempHaut);
      tempHaut = tempHaut.getDroite();
      tempBas = tempBas.getDroite();
    }
  }
  public void tourCases(){
    if (début==null){
      erreur.erreur("La carte est vide");
    }else{
      début.tourCases();
    }
  }
  public void add(Case c){
    if (début==null){début = new CCase(c);return;}
    début.add(c);
  }
}
