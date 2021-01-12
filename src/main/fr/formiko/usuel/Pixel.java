package fr.formiko.usuel.image;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.formiko.Pheromone;
import java.awt.Color;

public class Pixel {
  private byte r;
  private byte g;
  private byte b;
  private byte a;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Pixel(byte r, byte g, byte b, byte a){
    this.r=r; this.g=g; this.b=b; this.a=a;
  }
  public Pixel(int r, int g, int b, int a){
    this((byte)(r-128),(byte)(g-128),(byte)(b-128),(byte)(a-128));
  }
  public Pixel(byte r, byte g, byte b){
    this(r,g,b,127);
  }
  public Pixel(Pheromone ph){
    this(ph.getR(),ph.getV(),ph.getB());
  }
  // GET SET --------------------------------------------------------------------
  public byte getR(){return r;}
  public byte getG(){return g;} public byte getV(){return getG();}
  public byte getB(){return b;}
  public byte getA(){return a;}
  public void setR(byte x){ r=x;}
  public void setG(byte x){ g=x;} public void setV(byte x){ setG(x);}
  public void setB(byte x){ b=x;}
  public void setA(byte x){ a=x;}
  // Fonctions propre -----------------------------------------------------------
  public boolean equals(Pixel p){
    if(this.getR()!=p.getR()){ return false;}
    if(this.getG()!=p.getG()){ return false;}
    if(this.getB()!=p.getB()){ return false;}
    if(this.getA()!=p.getA()){ return false;}
    return true;
  }
  public void afficheToi(){
    System.out.println(r+" "+g+" "+b+" "+a);
  }
  public Color piToColor(){
    return new Color(r,g,b,a);
  }
}
