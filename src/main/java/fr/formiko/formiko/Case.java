package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Info;
import fr.formiko.usuel.Point;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.structures.listes.Liste;

import java.io.Serializable;
/**
*{@summary Square objects use to represent the map.}<br>
*@lastEditedVersion 1.39
*@author Hydrolien
*/
public class Case implements Serializable{
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
  public Case(Point p, Fourmiliere fere, GCreature gc, byte foodInsecte, byte foodInsecteMax, byte nt){
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
  public Case(Point p, Fourmiliere fere, GCreature gc){
    this(p,fere,gc,(byte) allea.getAllea(3),(byte)(allea.getAllea(100)+2),(byte) allea.getAllea(3));
    // si la food de départ n'est pas réduite :
    // new Info().setContent("ini food insecte to a random value from 0 to "+foodInsecteMax).print();
    setFoodInsecte((byte) allea.getAllea(foodInsecteMax));
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
        new Info().setType(1).setClassDepth(1).setContent("Trying to add "+x+" foodInsecte on Case "+p).print();
      }
      return;
    }
    int x2 = foodInsecte+x;
    if(x2>getFoodInsecteMax()){
      erreur.alerte("Trying to add more foodInsecte than max on Case "+p);
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
  public boolean canReachCase(){return getType()>-1;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Return a string that describe this.}<br>
  *@lastEditedVersion 2.18
  */
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
  /**
  *{@summary Return a short string that describe this.}<br>
  *@lastEditedVersion 2.18
  */
  public String toStringShort(){
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
      // s=s+g.get("fourmilière")+" :";s=s+"\n";
      s=s+fere.toStringShort();s=s+"\n";
    }
    if (!caseSombre) {
      GCreature gc = getSortedGc();
      if (gc != null && gc.getHead() != null){
        s=s+g.get("creatures")+" : "; s=s+"\n";
        for (Creature c : gc.toList()) {
          s+=c.toStringShort()+"\n";
        }
      }
      if (gg != null && gg.getHead() != null){
        s=s+g.get("graines")+" : ";s=s+"\n";
        for (Graine g : gg.toList()) {
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
  public int getNbrDElementSurCase(){
    int xr = 0;
    if (fere != null){ xr=1;}
    return xr + gc.length() + gg.length();
  }
  /**
  *{@return The number of element on this.}
  *@lastEditedVersion 1.x
  */
  public int length(){
    return getNbrDElementSurCase();
  }
  /**
  *{@return The GCreature sorted by friendly level with an ant.}
  *@param f the ant to compare
  *@lastEditedVersion 2.18
  */
  public GCreature getSortedGc(Fourmi f){
    if(f==null){return gc;}
    else{
      Liste<Creature> list = new Liste<Creature>();
      for (Creature c : getGc().toList()) {
        list.addSorted(c, (c1, c2) -> c1.friendlyLevel(f) - c2.friendlyLevel(f));
      }
      GCreature gcr = new GCreature();
      for (Creature c : list) {
        // if(!c.equals(f)){
          gcr.addFin(c);
        // }
      }
      return gcr;
    }
  }
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
  public void actualisationGraine(CCase p){
    //TODO ici un %age dépendant du type de la Case et de la saison serait bienvenue. (multiplié par l'abondance des graines.)
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
}
