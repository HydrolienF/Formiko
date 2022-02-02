package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Ascii;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.allea;

import java.awt.Color;
import java.io.Serializable;

public class Pheromone implements Serializable{
  private byte r;
  private byte v;
  private byte b;
  // CONSTRUCTORS --------------------------------------------------------------
  public Pheromone(byte r, byte v, byte b){
    this.r = r; this.v = v; this.b = b;
  }
  public Pheromone(int r, int v, int b){
    this((byte)r,(byte)v,(byte)b);
  }
  public Pheromone(Pheromone fo, byte x){
    this(allea.fluctuerBrute(fo.getR(),x),allea.fluctuerBrute(fo.getG(),x),allea.fluctuerBrute(fo.getB(),x));
  }
  public Pheromone(Pheromone fo){
    this(fo,(byte)2); // par défaut les Pheromones fluctuent de 2 maximum.(6 pour une Reine fille).
  }
  public Pheromone(String s){
    this(getXFromS(s.substring(0,2))-128,getXFromS(s.substring(2,4))-128,getXFromS(s.substring(4,6))-128);
  }
  public Pheromone(){
    this(allea.getAlléa(256)-128,allea.getAlléa(256)-128,allea.getAlléa(256)-128);
  }
  // GET SET -------------------------------------------------------------------
  public byte getR(){ return r;}
  public byte getG(){ return v;}
  public byte getB(){ return b;}
  public void setR(byte x){r=x;}
  public void setG(byte x){r=v;}
  public void setB(byte x){r=b;}
  public byte getRc(){ return (byte)(r+128);}
  public byte getVc(){ return (byte)(v+128);}
  public byte getBc(){ return (byte)(b+128);}
  public Color getColor(){ return new Color(r+128,v+128,b+128);}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Return a string that describe this.}<br>
  *@lastEditedVersion 2.18
  */
  public String toString(){
    return r+" "+v+" "+b;
  }
  /**
  *{@summary Return the hexadecimal code that describe this.}<br>
  *@lastEditedVersion 2.18
  */
  public String toHex(){
    return toHex(r+128)+toHex(v+128)+toHex(b+128);
  }
  /**
  *{@summary Return the hexadecimal code of an int.}<br>
  *@lastEditedVersion 2.18
  */
  private String toHex(int x){
    String s = Integer.toHexString(x).toUpperCase();
    if(s.length()==1){
      s="0"+s;
    }
    return s;
  }
  public void afficheToi(){
    System.out.println(this);
  }
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@lastEditedVersion 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof Pheromone)){return false;}
    Pheromone ph = (Pheromone)o;
    return equals(ph,1);
  }
  @Override
  public int hashCode(){return getR()+getG()*1000+getB()*1000000;}
  /**
  *{@summary special equals function.}
  *@lastEditedVersion 1.31
  */
  public boolean equals(Pheromone ph, int ndifferenceTollere){
    if(ndifferenceTollere<1){return false;}
    // si 7 < 3 - 5
    // si 7 > 3 + 5
    if (this.getR() <= ph.getR() - ndifferenceTollere){ return false;}
    if (this.getR() >= ph.getR() + ndifferenceTollere){ return false;}
    if (this.getB() <= ph.getB() - ndifferenceTollere){ return false;}
    if (this.getB() >= ph.getB() + ndifferenceTollere){ return false;}
    if (this.getG() <= ph.getG() - ndifferenceTollere){ return false;}
    if (this.getG() >= ph.getG() + ndifferenceTollere){ return false;}
    return true;
  }
  public static int getXFromS(String s){
    if (s.length()!=2){ erreur.alerte("un code couleur n'as pas pu être transformer "+s+".  Il sera remplacer par un code alléatoire"); return allea.getAlléa(256);}
    return b16ToB10(s.charAt(0))*16 + b16ToB10(s.charAt(1));
  }
  public String phToS(){
    String sr ="";
    sr=sr+b10ToB16(r+128);
    sr=sr+b10ToB16(v+128);
    sr=sr+b10ToB16(b+128);
    return sr;
  }
  public Color phToColor(){
    return new Color(r+128,v+128,b+128,255);
  }
  public static Pheromone colorToPh(Color col){
    return new Pheromone(col.getRed()-128,col.getGreen()-128,col.getBlue()-128);
  }


  public static String b10ToB16(int x){
    int y = x/16;
    x = x - y*16;
    String sr = nbrToChiffre(y) + nbrToChiffre(x);
    return sr;
  }
  public static String nbrToChiffre(int x){ //10 devient ascci(41) (cad A).
    if (x<10){return x+"";}
    int y = x-10;
    return Ascii.asciiToA(y+65)+"";
  }
  public static int b16ToB10(char c){
    if (c>=65 && c<=90){ return c-55;}
    if (c>=48 && c<=57){ return c-48;}
    erreur.alerte("un code couleur n'as pas pu être transformer "+c+".  Il sera remplacer par un code alléatoire.");
    return allea.getAlléa(16);
  }
  public static Pheromone sToPh(String s){
    Pheromone ph;
    try {
      ph=new Pheromone(s);
    }catch (Exception e) {
      erreur.alerte("Le code "+s+" n'as pas pu être changer en Pheromone","Pheromone est choisi aléatoirement.");
      ph = new Pheromone();
    }
    return ph;
  }
}
