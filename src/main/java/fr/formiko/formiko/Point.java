package fr.formiko.formiko;

import fr.formiko.usuel.erreur;
import fr.formiko.usuel.types.str;

import java.io.Serializable;

/**
*{@summary Point is a basic geometic shape.}<br>
*In Formiko, it is used to store an x and an y in a single object.<br>
*@author Hydrolien
*@version 1.30
*/
public class Point implements Serializable{
  protected int x,y; // pas de byte pour pourvoir utliser de très grande carte !
  // CONSTRUCTEUR -----------------------------------------------------------------
  /**
  *{@summary A simple contructor.}<br>
  *@version 1.30
  */
  public Point (int x,int y){
    this.x = x;
    this.y = y;
  }
  /**
  *{@summary A simple contructor.}<br>
  *A Point can be create from a string that look like this : <br>
  *<ul>
  *<li>3,5
  *<li>78 -90
  *<li>+467;+100
  *</ul>
  *@version 1.30
  */
  public Point(String s){//sous la forme -51,34
    x=-1; y=-1;
    try {
      String t [] = s.split(";",2);
      if(t.length<2){
        t = s.split(",",2);
        if(t.length<2){
          //t = s.split(".",2); //this line make test fail
          //t = s.split("."); //this line do not break actual test but can't split "0.0"
          t = s.split(" ",2);
        }
      }
      x=str.sToI(t[0]);
      y=str.sToI(t[1]);
    }catch (Exception e) {
      erreur.alerte("1 Point can't be create","Point.Point");
    }
  }

  // GET SET -----------------------------------------------------------------------
  public int getX(){return x;}
  public int getY(){return y;}
  public void setX(int x){this.x = x;}
  public void setY(int y){this.y = y;}
  public void ajouteAX(int a){this.x = this.x + a;}
  public void ajouteAY(int a){this.y = this.y + a;}

  // Fonctions propre -----------------------------------------------------------
  /**
  *{@summary A simple toString function.}<br>
  *@version 1.30
  */
  public String toString(){
    return "("+x+","+y+")";
  }
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@version 1.31
  */
  @Override
  public boolean equals(Object obj){
    if(obj==null || !(obj instanceof Point)){return false;}
    Point p = (Point)obj;
    if (x != p.x) { return false;}
    if (y != p.y) { return false;}
    return true;
  }
  /**
  *{@summary Equals methode for point.}<br>
  *A Point can be compare to a string that look like this : <br>
  *<ul>
  *<li>3,5
  *<li>78 -90
  *<li>+467;+100
  *</ul>
  *@version 1.30
  */
  public boolean equals(String s){//sous la forme -51,34
    if(s==null){return false;}
    return equals(new Point(s));
  }
}
