package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.Point;

import java.io.Serializable;
/**
*{@summary Square objects use to represent the map.}<br>
*@version 1.39
*@author Hydrolien
*/
public class Case implements Serializable{
  private Point p;
  private byte type; //0 = herbe ...
  private Fourmiliere fere;
  private GCreature gc;
  private GGraine gg;
  private GBlade gb;
  private byte foodInsecte;
  private byte foodInsecteMax;
  private byte foodInsecteParTour;

  // CONSTRUCTORS --------------------------------------------------------------
  public Case(Point p, Fourmiliere fere, GCreature gc, byte foodInsecte, byte foodInsecteMax, byte nt){
    this.p =p;
    this.fere = fere;
    this.gc = gc;
    if(this.gc == null){ this.gc = new GCreature();}
    this.foodInsecte = 0;
    this.gb = new GBlade();
    addFoodInsecte(foodInsecte);
    this.foodInsecteMax = foodInsecteMax;
    foodInsecteParTour = nt;
    gg = new GGraine(); type = 1;
  }
  public Case(Point p, Fourmiliere fere, GCreature gc){
    this(p,fere,gc,(byte) allea.getAlléa(3),(byte)(allea.getAlléa(100)+2),(byte) allea.getAlléa(3));
    // si la food de départ n'est pas réduite :
    setFoodInsecte((byte) allea.getAlléa(foodInsecteMax));
  }
  public Case(Point p){this(p,null,new GCreature());}
  public Case(int x, int y){this(new Point(x,y));}
  // GET SET -------------------------------------------------------------------
  public Point getP(){return p;}
  public Point getPoint(){ return getP();}
  public void setP(Point p){this.p = p;}
  public int getX(){ return p.getX();}
  public int getY(){ return p.getY();}
  public Fourmiliere getFere(){return fere;}
  public void setFere(Fourmiliere fere){this.fere = fere;}
  public GCreature getGc(){return gc;}
  public void setGc(GCreature gc){this.gc = gc;}
  public GInsecte getGi(){return gc.getGi();}
  public byte getFoodInsecte(){return foodInsecte;}
  /**
  *{@summary Update to x foodInsecte.}
  * It also update GBlade if needed.
  *@param x new foodInsecte
  *@version 2.16
  */
  public void setFoodInsecte(byte x){
    if(x>foodInsecte){
      addFoodInsecte((byte)(x-foodInsecte));
    }else if(x<foodInsecte){
      removeFoodInsecte((byte)(-x+foodInsecte));
    }
  }
  public void setFoodInsecte(int x){setFoodInsecte((byte)x);}
  /**
  *{@summary Add x foodInsecte.}
  * It also update GBlade if needed.
  *@param x foodInsecte to add
  *@version 2.16
  */
  public void addFoodInsecte(byte x){
    if(x<1){
      if(x<0){
        erreur.alerte("Trying to add "+x+" foodInsecte on Case "+p);
      }
      return;
    }
    int x2 = foodInsecte+x;
    if(x2>getFoodInsecteMax()){
      erreur.alerte("Trying to add more foodInsecte than max on Case "+p);
      x2=getFoodInsecteMax();
    }
    gb.addBlades(x2 - foodInsecte);
    foodInsecte=(byte)x2;
  }
  public void addFoodInsecte(int x){addFoodInsecte((byte)x);}
  /**
  *{@summary remove x foodInsecte.}
  * It also update GBlade if needed.
  *@param x foodInsecte to remove
  *@version 2.16
  */
  public void removeFoodInsecte(byte x){
    if(x<1){
      if(x<0){
        erreur.alerte("Trying to remove "+x+" foodInsecte on Case "+p);
      }
      return;
    }
    int x2 = foodInsecte-x;
    if(x2<0){
      // System.out.println("Trying to remove more foodInsecte than aviable on Case "+p);
      erreur.alerte("Trying to remove more foodInsecte than aviable on Case "+p);
      x2=0;
    }
    gb.removeBlades(foodInsecte-x2);
    foodInsecte=(byte)x2;
  }
  public void removeFoodInsecte(int x){removeFoodInsecte((byte)x);}
  public byte getFoodInsecteMax(){return foodInsecteMax;}
  public void setFoodInsecteMax(byte x){foodInsecteMax =x;}
  public byte getFoodInsecteParTour(){return foodInsecteParTour;}
  public void setFoodInsecteParTour(byte x){foodInsecteParTour=x;}
  public GGraine getGGraineCopier(){return gg.copierGGraine();}
  public GGraine getGGraine(){return gg;}
  public GGraine getGg(){ return getGGraine();}
  public GBlade getGb(){ return gb;}
  public byte getType(){ return type;}
  public void setType(byte x){type = x; if(type==3 || type<0){setFoodInsecteMax((byte)0); setFoodInsecteParTour((byte)0);}}
  public void setType(int x){setType((byte)x);}
  public boolean canReachCase(){return getType()>-1;}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    boolean caseSombre = false;
    if(Main.getPartie()!=null && Main.getPartie().getPlayingJoueur()!=null){
      if(Main.getPartie().getCasesNuageuses() && Main.getPartie().getPlayingJoueur().getCaseNuageuse(p.getX(),p.getY())){
        return "";
      }else if(Main.getPartie().getCasesSombres() && Main.getPartie().getPlayingJoueur().getCaseSombre(p.getX(),p.getY())){
        caseSombre=true;
      }
    }
    String s = g.get("case")+" : ("+g.get("foodInsecte")+" :"+foodInsecte+"/"+foodInsecteMax+" (+"+foodInsecteParTour+"))";
    s=s+ p.toString();s=s+"\n";
    if (fere != null){
      s=s+g.get("fourmilière")+" :";s=s+"\n";
      s=s+fere.toString(false);s=s+"\n";
    }
    if (!caseSombre) {
      if (gc != null && gc.getHead() != null){
        s=s+g.get("creatures")+" : "; s=s+"\n";
        s=s+gc.toString();s=s+"\n";
      }
      if (gg != null && gg.getHead() != null){
        s=s+g.get("graines")+" : ";s=s+"\n";
        s=s+gg.toString();s=s+"\n";
      }
    }
    return s;
  }
  public String desc(){return p.toString();}
  public int getNbrDElementSurCase(){
    int xr = 0;
    if (fere != null){ xr=1;}
    return xr + gc.length() + gg.length();
  }
  public int length(){
    return getNbrDElementSurCase();
  }
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@version 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof Case)){return false;}
    Case c = (Case)o;
    if(c.length() != this.length()){ return false;}
    if(!this.getPoint().equals(c.getPoint())){ return false;}
    return true;
  }
  public boolean estCaseVide(){
    if (getNbrDElementSurCase() == 0){ return true;}
    return false;
  }
  public String description(){
    return p.toString();
  }
  /**
  *{@summary Update foodInsecte.}
  * It also update GBlade if needed.
  *@version 2.16
  */
  public void updateFoodInsecte(){
    int toAdd = getFoodInsecteParTour();
    if(foodInsecte+toAdd > foodInsecteMax){
      toAdd=foodInsecteMax-foodInsecte;
    }
    addFoodInsecte((byte)toAdd);
  }
  public void actualisationGraine(CCase p){
    //TODO ici un %age dépendant du type de la Case et de la saison serait bienvenue. (multiplié par l'abondance des graines.)
    int x  = allea.getAlléa(50);
    if(x==0 && this.getFere()==null){ new Graine(p);} // si on a de la chance et que il n'y a pas de fere sur la case.
    gg.tour();
  }
}
