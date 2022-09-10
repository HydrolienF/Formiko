package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usual.Info;
import fr.formiko.usual.Point;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.maths.allea;
import fr.formiko.usual.maths.math;
import fr.formiko.usual.structures.listes.Liste;

import java.io.Serializable;
/**
*{@summary Square objects use to represent the map.}<br>
*@lastEditedVersion 1.39
*@author Hydrolien
*/
public class Square implements Serializable{
  private Point p;
  /** 1=grass, 2=moss, 3=sand */
  private byte type;
  private Fourmiliere fere;
  private GCreature gc;
  private GGraine gg;
  private transient GBlade gb;
  private byte foodInsecte;
  private byte foodInsecteMax;
  private byte foodInsecteParTour;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor with all needed args.}<br>
  *@lastEditedVersion 2.30
  */
  public Square(Point p, Fourmiliere fere, GCreature gc, byte foodInsecte, byte foodInsecteMax, byte nt){
    this.p =p;
    this.fere = fere;
    this.gc = gc;
    if(this.gc == null){ this.gc = new GCreature();}
    this.foodInsecte = 0;
    this.foodInsecteMax = foodInsecteMax;
    foodInsecteParTour = nt;
    gg = new GGraine();
    setType(1);
  }
  /**
  *{@summary Secondary constructor.}<br>
  *@lastEditedVersion 2.30
  */
  public Square(Point p, Fourmiliere fere, GCreature gc){
    this(p,fere,gc,(byte) allea.getAllea(3),(byte)(allea.getAllea(100)+2),(byte) allea.getAllea(3));
    // si la food de départ n'est pas réduite :
    // new Info().setContent("ini food insecte to a random value from 0 to "+foodInsecteMax).print();
    setFoodInsecte((byte) allea.getAllea(foodInsecteMax));
  }
  /***
  *{@summary Secondary constructor.}<br>
  *@lastEditedVersion 2.30
  */
  public Square(Point p){this(p,null,new GCreature());}
  /***
  *{@summary Secondary constructor.}<br>
  *@lastEditedVersion 2.30
  */
  public Square(int x, int y){this(new Point(x,y));}
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
  *@lastEditedVersion 2.16
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
  *@lastEditedVersion 2.16
  */
  public void addFoodInsecte(byte x){
    if(x<1){
      if(x<0){
        new Info().setType(1).setClassDepth(1).setContent("Trying to add "+x+" foodInsecte on Square "+p).print();
      }
      return;
    }
    int x2 = foodInsecte+x;
    if(x2>getFoodInsecteMax()){
      erreur.alerte("Trying to add more foodInsecte than max on Square "+p);
      x2=getFoodInsecteMax();
    }
    addBlades(x2 - foodInsecte);
    foodInsecte=(byte)x2;
  }
  public void addFoodInsecte(int x){addFoodInsecte((byte)x);}
  /**
  *{@summary remove x foodInsecte.}
  * It also update GBlade if needed.
  *@param x foodInsecte to remove
  *@lastEditedVersion 2.16
  */
  public void removeFoodInsecte(byte x){
    if(x<1){
      if(x<0){
        erreur.alerte("Trying to remove "+x+" foodInsecte on Square "+p);
      }
      return;
    }
    int x2 = foodInsecte-x;
    if(x2<0){
      erreur.alerte("Trying to remove more foodInsecte than aviable on Square "+p);
      x2=0;
    }
    removeBlades(foodInsecte-x2);
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
  public GBlade getGb(){if(gb==null){iniGBlade();} return gb;}
  public byte getType(){ return type;}
  /**
  *{@summary Update type &#38; GBlade depending of type.}
  *@param type the type to set
  *@lastEditedVersion 2.16
  */
  public void setType(byte type){
    this.type = type;
    int abondanceHerbe;
    if(Main.getPartie()!=null && Main.getPartie().getCarte()!=null){
      abondanceHerbe = Main.getPartie().getCarte().getAbondanceHerbe();
    }else{
      abondanceHerbe = 8;
    }
    int foodInsecteParTourTemp;
    switch(type){
      case 1: //grass
      case 2: //moss
      foodInsecteParTourTemp=1+allea.getAllea(1+abondanceHerbe);
      break;
      case 3: //sand
      foodInsecteParTourTemp=allea.getAllea(1+abondanceHerbe/5);
      break;
      default:
      foodInsecteParTourTemp=0;
      break;
    }
    setFoodInsecteMax((byte)math.min(foodInsecteParTourTemp*20,127));
    setFoodInsecteParTour((byte)math.min(foodInsecteParTourTemp,127));
    gb = new GBlade(type);
    if(getFoodInsecte()>0){
      // new Info().setContent("remove "+getFoodInsecte()).print();
      removeFoodInsecte(getFoodInsecte()); // if there was already some food insect
    }
    if(getFoodInsecteMax()>0){
      addFoodInsecte(allea.getAllea(getFoodInsecteMax()));
    }
  }
  public void setType(int x){setType((byte)x);}
  public boolean canReachSquare(){return getType()>-1;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Return a string that describe this.}<br>
  *@lastEditedVersion 2.18
  */
  public String toString(){
    boolean caseSombre = false;
    if(Main.getPartie()!=null && Main.getPartie().getPlayingJoueur()!=null){
      if(Main.getPartie().getSquaresNuageuses() && Main.getPartie().getPlayingJoueur().getSquareNuageuse(p.getX(),p.getY())){
        return "";
      }else if(Main.getPartie().getSquaresSombres() && Main.getPartie().getPlayingJoueur().getSquareSombre(p.getX(),p.getY())){
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
  /**
  *{@summary Return a short string that describe this.}<br>
  *@lastEditedVersion 2.18
  */
  public String toStringShort(){
    boolean caseSombre = false;
    if(Main.getPartie()!=null && Main.getPartie().getPlayingJoueur()!=null){
      if(Main.getPartie().getSquaresNuageuses() && Main.getPartie().getPlayingJoueur().getSquareNuageuse(p.getX(),p.getY())){
        return "";
      }else if(Main.getPartie().getSquaresSombres() && Main.getPartie().getPlayingJoueur().getSquareSombre(p.getX(),p.getY())){
        caseSombre=true;
      }
    }
    String s = g.get("case")+" : ("+g.get("foodInsecte")+" :"+foodInsecte+"/"+foodInsecteMax+" (+"+foodInsecteParTour+"))";
    s=s+ p.toString();s=s+"\n";
    if (fere != null){
      // s=s+g.get("fourmilière")+" :";s=s+"\n";
      s=s+fere.toStringShort();s=s+"\n";
    }
    if (!caseSombre) {
      GCreature gc = getSortedGc();
      if (gc != null && gc.getHead() != null){
        s=s+g.get("creatures")+" : "; s=s+"\n";
        for (Creature c : gc) {
          s+=c.toStringShort()+"\n";
        }
      }
      if (gg != null && gg.getHead() != null){
        s=s+g.get("graines")+" : ";s=s+"\n";
        for (Graine g : gg) {
          s+=g.toStringShort()+"\n";
        }
      }
    }
    return s;
  }
  public String desc(){return p.toString();}
  /**
  *{@return The number of element on this.}
  *@lastEditedVersion 1.x
  */
  public int getNbrDElementSurSquare(){
    int xr = 0;
    if (fere != null){ xr=1;}
    return xr + gc.length() + gg.length();
  }
  /**
  *{@return The number of element on this.}
  *@lastEditedVersion 1.x
  */
  public int length(){
    return getNbrDElementSurSquare();
  }
  /**
  *{@return The GCreature sorted by friendly level with an ant.}
  *@param f the ant to compare
  *@lastEditedVersion 2.18
  */
  public GCreature getSortedGc(Fourmi f){
    if(f==null){return gc;}
    else{
      GCreature gcout = new GCreature();
      for (Creature c : getGc()) {
        gcout.addSorted(c, (c1, c2) -> c1.friendlyLevel(f) - c2.friendlyLevel(f));
      }
      return gcout;
    }
  }
  /**
  *{@return The GCreature sorted by friendly level with an ant.}
  *Used ant for sorting is playing ant.
  *@lastEditedVersion 2.30
  */
  public GCreature getSortedGc(){
    return getSortedGc(Main.getPlayingAnt());
  }
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@lastEditedVersion 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof Square)){return false;}
    Square c = (Square)o;
    if(c.length() != this.length()){ return false;}
    if(!this.getPoint().equals(c.getPoint())){ return false;}
    return true;
  }
  /**
  *{@summary Return true if is an empty Square.}
  *@lastEditedVersion 2.30
  */
  public boolean estSquareVide(){
    if (getNbrDElementSurSquare() == 0){ return true;}
    return false;
  }
  /**
  *{@summary Return a short description.}
  *@lastEditedVersion 2.30
  */
  public String description(){
    return p.toString();
  }
  /**
  *{@summary Update foodInsecte.}
  * It also update GBlade if needed.
  *@lastEditedVersion 2.16
  */
  public void updateFoodInsecte(){
    int toAdd = getFoodInsecteParTour();
    if(foodInsecte+toAdd > foodInsecteMax){
      toAdd=foodInsecteMax-foodInsecte;
    }
    if(toAdd>127){toAdd=127;}
    addFoodInsecte((byte)toAdd);
  }
  /**
  *{@summary Create a new seed on p depending of a random number.}
  *@lastEditedVersion 2.30
  */
  public void actualisationGraine(CSquare p){
    //TODO ici un %age dépendant du type de la Square et de la saison serait bienvenue. (multiplié par l'abondance des graines.)
    int x  = allea.getAllea(50);
    if(x==0 && this.getFere()==null){ new Graine(p);} // si on a de la chance et que il n'y a pas de fere sur la case.
    gg.tour();
  }
  /**
  *{@summary Initialize GBlade back.}<br>
  *@lastEditedVersion 2.22
  */
  private void iniGBlade(){
    gb = new GBlade(type);
    gb.addBlades(getFoodInsecte());
  }
  /**
  *{@summary Initialize GBlade back.}<br>
  *@lastEditedVersion 2.22
  */
  private void addBlades(int nbrBlades){
    if(gb==null){iniGBlade();return;}
    gb.addBlades(nbrBlades);
  }
  /**
  *{@summary Initialize GBlade back.}<br>
  *@lastEditedVersion 2.22
  */
  private void removeBlades(int nbrBlades){
    if(gb==null){iniGBlade();return;}
    gb.removeBlades(nbrBlades);
  }
  /**
  *{@summary Give a score about herbivore interest for this square.}<br>
  *It use 2 parameters, foodInsecte &#38; number of other insect already in it.
  *@lastEditedVersion 2.24
  */
  public int interestForHerbivore(){
    return getFoodInsecte()-(3*getGc().length());
  }
}