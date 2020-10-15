package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.conversiondetype.str;

public class Point {
  protected int x,y; // pas de byte pour pourvoir utliser de très grande carte !
  // CONSTRUCTEUR -----------------------------------------------------------------
  public Point (int x,int y){
    this.x = x;
    this.y = y;
  }
  public Point(String s){//sous la forme -51,34
      String t [] = s.split(".");
      if(t.length<2){
        t = s.split(",");
        if(t.length<2){
          t = s.split(";");
        }
      }
    x=str.sToI(t[0]);
    y=str.sToI(t[1]);
  }

// GET SET -----------------------------------------------------------------------
  public int getX(){return x;}
  public int getY(){return y;}
  public void setX(int x){this.x = x;}
  public void setY(int y){this.y = y;}
  public void ajouteAX(int a){this.x = this.x + a;}
  public void ajouteAY(int a){this.y = this.y + a;}

// Fonctions propre -----------------------------------------------------------
  public String toString(){
    return "("+x+","+y+")";
  }
  public void afficheToi(){
    System.out.println("("+x+","+y+")");
  }
  public String description(){ return toString();}
  public boolean equals(Point p){
    if (x != p.x) { return false;}
    if (y != p.y) { return false;}
    return true;
  }
  public boolean equals(String s){//sous la forme -51,34
    try {
      String t [] = s.split(".");
      if(t.length<2){
        t = s.split(",");
        if(t.length<2){
          t = s.split(";");
        }
      }
      if(str.sToI(t[0])!=x){return false;}
      if(str.sToI(t[1])!=y){return false;}
      return true;
    }catch (Exception e) {
      return false;
    }
  }
}