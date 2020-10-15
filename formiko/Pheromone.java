package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.math.allea;
import java.awt.Color;
import fr.formiko.usuel.ascii;

public class Pheromone {
  private byte r;
  private byte v;
  private byte b;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Pheromone(byte r, byte v, byte b){
    this.r = r; this.v = v; this.b = b;
  }
  public Pheromone(int r, int v, int b){
    this((byte)r,(byte)v,(byte)b);
  }
  public Pheromone(Pheromone fo, byte x){
    this(allea.fluctuerBrute(fo.getR(),x),allea.fluctuerBrute(fo.getV(),x),allea.fluctuerBrute(fo.getB(),x));
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
  // GET SET --------------------------------------------------------------------
  public byte getR(){ return r;}
  public byte getV(){ return v;}
  public byte getB(){ return b;}
  public void setR(byte x){r=x;}
  public void setV(byte x){r=v;}
  public void setB(byte x){r=b;}
  public byte getRc(){ return (byte)(r+128);}
  public byte getVc(){ return (byte)(v+128);}
  public byte getBc(){ return (byte)(b+128);}
  public Color getColor(){ return new Color(r+128,v+128,b+128);}
  // Fonctions propre -----------------------------------------------------------
  public String description(){
    return r+" "+v+" "+b;
  }
  public void afficheToi(){
    System.out.println(description());
  }
  public boolean equals(Pheromone ph, int différenceTolléré){
    // si 7 < 3 - 5
    // si 7 > 3 + 5
    if (this.getR() <= ph.getR() - différenceTolléré){ return false;}
    if (this.getR() >= ph.getR() + différenceTolléré){ return false;}
    if (this.getB() <= ph.getB() - différenceTolléré){ return false;}
    if (this.getB() >= ph.getB() + différenceTolléré){ return false;}
    if (this.getV() <= ph.getV() - différenceTolléré){ return false;}
    if (this.getV() >= ph.getV() + différenceTolléré){ return false;}
    return true;
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


  public static String b10ToB16(int x){
    int y = x/16;
    x = x - y*16;
    String sr = nbrToChiffre(y) + nbrToChiffre(x);
    return sr;
  }
  public static String nbrToChiffre(int x){ //10 devient ascci(41) (cad A).
    if (x<10){return x+"";}
    int y = x-10;
    return ascii.asciiToA(y+65)+"";
  }
  public static int getXFromS(String s){
    if (s.length()!=2){ erreur.alerte("un code couleur n'as pas pu être transformer "+s+".  Il sera remplacer par un code alléatoire. "); return allea.getAlléa(256);}
    return b16ToB10(s.charAt(0))*16 + b16ToB10(s.charAt(1));
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
      erreur.erreur("Le code "+s+" n'as pas pu être changer en Pheromone");
      ph = new Pheromone();
    }
    return ph;
  }
}