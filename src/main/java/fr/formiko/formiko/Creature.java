package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.interfaces.*;
import fr.formiko.usual.exceptions.NotNullLocationException;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.maths.allea;
import fr.formiko.usual.maths.math;
import fr.formiko.usual.types.str;

import java.io.Serializable;
/**
*{@summary The class that extends every living thing on the game.}<br>
*Most of the common var between Fourmi and Insecte can be found here.<br>
*Creature have a lot of interfaces. They can be used to do every action that a creature can do. If a creature can not do an action as lay (pondre) and try to do it, an error message will appear. This actions can all be call by a short cut here (to be able to do creature.action() and not ActionFourmi.action(creature)).<br>
*@author Hydrolien
*@lastEditedVersion 1.13
*/
public abstract class Creature extends ObjetSurCarteAId implements Serializable{
  // action done with Decorator pattern
  public Deplacement déplacement;
  public Chasse chasse;
  public Pondre pondre;
  public Trophallaxie trophallaxie;
  public Evoluer evoluer;
  public Mourir mourir;
  public Netoyer netoyer;
  public Tour tour;
  // public Collecte collecte;
  // variable
  protected int food;
  protected int maxFood;
  protected int givenFood;
  protected int age;
  protected int maxAge;
  protected boolean isDead;
  protected byte action;
  protected byte maxAction;
  protected Pheromone ph;
  protected byte health;
  protected byte pheromoneTolerence;
  /** -3=egg, -2=larva, -1=nymph, 0=imago */
  protected byte stade;
  protected Espece e;
  protected ObjetSurCarteAId transported;
  private int lastTurnEnd=-1;

  //TODO #73
  //protected int typeEated [];
  //private int hp; //Heal point
  //private int dp; //Damage points
  // point de force.
  // CONSTRUCTORS ----------------------------------------------------------------
  /**
  *{@summary Main constructor for Creature.}<br>
  *All args are Creature var.
  *@lastEditedVersion 1.13
  */
  public Creature (CSquare ccase, int age, int maxAge, byte maxAction, Pheromone ph, int food, int maxFood){
    super(ccase);
    this.age = age; this.maxAge= maxAge; this.isDead = false;
    this.action = maxAction; this.maxAction = maxAction;
    this.ph = ph;
    this.food = food; this.maxFood =maxFood;
    pheromoneTolerence=0;
    givenFood=1;
    this.déplacement = new DeplacementNull(); this.chasse = new ChasseNull(); this.pondre = new PondreNull(); this.trophallaxie = new TrophallaxieNull(); this.evoluer=new EvoluerNull();this.mourir=new MourirNull();this.netoyer=new NetoyerNull();
    this.tour = new TourCreatureSansAction();
  }
  /**
  *{@summary constructor for Creature.}<br>
  *Here we only know some var, but the main constructor will take care of them.
  *@lastEditedVersion 1.13
  */
  public Creature (CSquare ccase,int age, int maxAge, byte maxAction){ // Les fourmis utilise ce contructeur.
    this(ccase,age,maxAge, maxAction, new Pheromone(), 10, 100);
  } public Creature(CSquare ccase, int age, int maxAge, int maxAction){ this(ccase,age,maxAge,(byte) maxAction);}
  /**
  *{@summary constructor for Creature.}<br>
  *Here we only know some var, but the main constructor will take care of them.
  *@lastEditedVersion 1.13
  */
  public Creature (CSquare ccase,int age, int maxAge){
    this(ccase,age,maxAge,(byte) 50); // 50 action par défaut.
  }
  public Creature (CSquare ccase,int maxAge){this(ccase,0,maxAge);}
  public Creature (CSquare ccase){this(ccase,100);}
  public Creature (){this((CSquare) null);}
  // GET SET ----------------------------------------------------------------------
  //Food
  public int getFood(){return food;}
  public int getMaxFood(){return maxFood;}
  public void setFoodMoins1(){food--; mourirOuPas(3);}
  public void setFood(int x){food = x;diminuerOuPasFood();mourirOuPas(3);}
  public void setMaxFood(int x){ maxFood = x;diminuerOuPasFood();}
  public void ajouteFood(int x){addFood(x);}
  public void addFood(int x){food += x;diminuerOuPasFood();mourirOuPas(3);}
  public void diminuerOuPasFood(){if (food>maxFood) {food = maxFood;}}
  public int getGivenFood(){return givenFood;}
  public void setGivenFood(int x){givenFood=x;}
  //Age
  public int getAge(){return age;}
  public int getMaxAge(){return maxAge;}
  public void setMaxAge(int x){maxAge = x;}
  public void setAgePlus1(){age++; mourirOuPas(2);}
  public void setAge(int x){age=x;mourirOuPas(2);}
  public void ajouteAge(int x){age+=x; mourirOuPas(2);}
  public int getAction(){return action;}
  private void setAction(byte x){action = x;}
  public void setAction(int x){setAction(str.iToBy(x));}
  public void setActionMoins(int x){setAction(getAction() - x);}
  public void setActionTo0(){if(getAction()>0){setAction(0);}}
  public byte getMaxAction(){return maxAction;}
  public void setMaxAction(byte x){maxAction =x;}
  /**
  *{@summary Move the Creature from a case to an other.}<br>
  *It is used by Deplacement interfaces.<br>
  *It wil try to remove from old CSquare and add to new CSquare.<br>
  *@lastEditedVersion 1.40
  */
  @Override
  public void setCSquare(CSquare newCSquare){
    if(this.ccase!=null){
      this.ccase.getContent().getGc().remove(this);
    }
    this.ccase = newCSquare;
    if(newCSquare!=null){
      newCSquare.getContent().getGc().add(this);
    }
  }
  //public void setCSquare(int x, int y){setCSquare(Main.getGc().getCSquare(x,y));}
  //public void setCc(CSquare cc){setCSquare(cc);}
  public Pheromone getPheromone(){ return ph;}
  public Pheromone getPh(){ return getPheromone();}
  public void setPheromone(Pheromone ph){this.ph = ph; }
  public void setPh(Pheromone ph){ setPheromone(ph);}
  public void setPheromone(byte a, byte b, byte c){ph = new Pheromone(a,b,c);}
  public void setPheromone(int a, int b, int c){setPheromone((byte)a,(byte)b,(byte)c);}
  public boolean getIsDead(){ return isDead;}
  public boolean isDead(){ return getIsDead();}
  public void setIsDead(boolean b){isDead=b;maxAction=0;action=0;}
  public byte getHealth(){return health;}
  public byte getMaxHealth(){return 100;}
  public void setHealth(int x){setHealth(str.iToBy(x));}
  public void setHealth(byte x){ health = x; if(x<100){x=100;}}
  public abstract boolean getFemelle();
  public abstract void setFemelle(boolean b);
  public abstract String getSex();
  public byte getStade(){ return stade;}
  public void setStade(byte s){ stade = s;} public void setStade(int x){setStade(str.iToBy(x));}
  public boolean estFourmi(){return (this instanceof Fourmi);}
  public abstract byte getType();//réclame une implémentation de getType.
  public byte getPheromoneTolerence(){return pheromoneTolerence;}
  public void setPheromoneTolerence(byte x){pheromoneTolerence=x;}
  /***
  *{@summary return true if Creature have wings.}
  *@lastEditedVersion 2.10
  */
  public abstract boolean getHaveWings();
  public boolean isFlying(){return false;}
  public abstract boolean wantFood();
  public abstract boolean wantClean();
  public Espece getEspece(){ return e;}
  public void setEspece(Espece e){ this.e = e;}
  public void setEspece(int e){ setEspece(Main.getEspeceById(e));}
  public String getName(){return g.get("creature");}
  public int getLastTurnEnd(){return lastTurnEnd;}
  public void setLastTurnEnd(int x){lastTurnEnd=x;}
  /***
  *{@summary Return true if is own by an AI.}<br>
  *@lastEditedVersion 1.40
  */
  public boolean isAI(){return true;}
  public boolean getIa(){return isAI();}
  public ObjetSurCarteAId getTransported(){ return transported;}
  /**
  *{@summary Set as transported item o.}<br>
  *If item is no null it will be remove from the CSquare.<br>
  *If item is not null &#38; it already have an item it will throw an exception.<br>
  *@lastEditedVersion 1.40
  */
  public void setTransported(ObjetSurCarteAId o){
    if(o!=null && getTransported()!=null){throw new NotNullLocationException();}
    transported = o;
    if(o!=null){o.setCSquare(null);}
  }
  /**
  *{@summary Drop the transported item.}<br>
  *Transported item is now on the CSquare of the Creature &#38; not anymore transported by Creature.
  *@lastEditedVersion 2.29
  */
  public void dropTransported(){
    getTransported().setCSquare(getCSquare());
    setTransported(null);
  }

  public int getSize(){if(getEspece()!=null){return getEspece().getSize(getStade());}return 1;}

  //raccourci des actions d'interface
  public void ceDeplacer(boolean bIa){déplacement.unMouvement(this,bIa);}
  public void ceDeplacer(CSquare ccase){déplacement.unMouvement(this,ccase);}
  public void ceDeplacer(Square ca){déplacement.unMouvement(this,ca);}
  public void ceDeplacer(int direction){déplacement.unMouvement(this,direction);}
  public void ceDeplacerPlusieurSquare(CSquare cc){déplacement.plusieurMouvement(this,cc);}
  public void pondre(){pondre.unePonte(this);}
  public boolean canLay(){return pondre.canLay(this);}
  public boolean chasse(){return chasse.chasse(this);}
  public boolean chasser(int direction){return chasse.chasser(this, direction);}
  public void trophallaxie(int id, int foodDonnée){trophallaxie.trophallaxie(this, id, foodDonnée);}
  public void trophallaxie(Creature c, int foodDonnée){trophallaxie.trophallaxie(this,c, foodDonnée);}
  public void trophallaxer(){trophallaxie.trophallaxer(this);}
  // public void collecter(int direction){collecte.collecter((Fourmi) this, (byte) direction);}
  public void evoluer(){evoluer.evoluer(this);}
  public void mourir(){mourir(0);}//mourrir sans raison spécifié.
  public void mourir(int r){mourir.mourir(this,r);}
  public void supprimerDeLaCarte(){ mourir.supprimerDeLaCarte(this);}
  public void netoyer(){netoyer.netoyer(this);}
  public void netoyer(Creature c){netoyer.netoyer(this,c);}
  public void ceNetoyer(){netoyer.netoyer(this,this);}
  public boolean netoyerIa(){return netoyer.netoyerIa(this);}
  public void tour(){tour.unTour(this);}
  public void endTurn(){tour.endTurn(this);}
  //public void preTurn(){tour.preTurn(this);}
  //public void manger (graine pour certaine fourmi, champnons pour d'autre et herbe pour les insectes.)
  /**
  *{@summary Return the state of the Creature concerning food.}
  *@return an int from 0 to 3 (0=OK, 1=medium, 2=bad, 3=critical)
  *@lastEditedVersion 2.8
  */
  public int getStateFood(){
    if(getFood()<0.1*getMaxFood()){return 3;}
    else if(getFood()<0.2*getMaxFood()){return 2;}
    else if(getFood()<0.4*getMaxFood()){return 1;}
    else {return 0;}
  }
  /**
  *{@summary Return the state of the Creature concerning action.}
  *@return an int from 0 to 3 (0=OK, 1=medium, 2=bad, 3=critical)
  *@lastEditedVersion 2.8
  */
  public int getStateAction(){
    if(getAction()==getMaxAction()){return 0;}
    else if(getAction()<=0){return 3;}
    else{return 1;}
  }
  /**
  *{@summary Return the state of the Creature concerning age.}
  *@return an int from 0 to 3 (0=OK, 1=medium, 2=bad, 3=critical)
  *@lastEditedVersion 2.8
  */
  public int getStateAge(){
    if(getAge()>=getMaxAge()*0.9){return 2;}
    return 1;
  }
  /**
  *{@summary Return the state of the Creature concerning health.}
  *Creature that don't Override getStateHealth() will always be at 0.
  *@return an int from 0 to 3 (0=OK, 1=medium, 2=bad, 3=critical)
  *@lastEditedVersion 2.8
  */
  public int getStateHealth(){
    return 0;
  }
  public int getMovingCost(){return 10;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Return a description of the creature.}
  *@lastEditedVersion 2.7
  */
  @Override
  public String toString(){
    String r = "";
    r+= g.getOr("le","la")+" "+getName();
    r+=" ";
    r+=getId();r+=" ";
    if (this.getFemelle()){r+= "♀";}
    else {r+= "♂";}r+=" ";
    if (isDead){r+= "(☠︎)";}
    else {r+= "("+(maxAge-age)+" "+g.get("avant")+" ☠︎)";}r+=" ";
    r+=ccase.desc();r+=", ";
    r+=g.get("stade")+" "+getStringStade()+", ";
    r+=g.get("food")+" "+food+"/"+maxFood+" (nf:"+givenFood+")"+", ";
    r+=g.get("age")+" "+age+"/"+maxAge+", ";
    r+=g.get("action")+" "+action+"/"+maxAction+", ";
    r+=g.get("health")+" "+health+"/"+"100"+", ";
    r+=g.get("phéromone")+" "+ph.toString()+", ";
    r+=g.get("pheromoneTolerence")+" "+pheromoneTolerence+", ";
    try {
      r+=g.get("espèce")+" "+e.getName();
    }catch (Exception e) {}
    return r;
  }
  /**
  *{@summary Return a short string that describe this.}<br>
  *@lastEditedVersion 2.18
  */
  public String toStringShort(){
    String r = "";
    r+=str.toMaj(getName());
    r+=" ";
    r+=getId();r+=" ";
    if (this.getFemelle()){r+= "♀";}
    else {r+= "♂";}
    if (isDead){r+= " (☠︎)";}
    else{r+=", "+g.get("age")+" "+age+"/"+maxAge+", ";}
    r+=ccase.desc();r+=", ";
    r+=getStringStade()+", ";
    r+=g.get("food")+" "+food+"/"+maxFood+" (✝:"+givenFood;
    if(this instanceof Insecte){r+=" +"+((Insecte)this).foodEatable;}
    r+=")"+", ";
    r+=g.get("action")+" "+action+"/"+maxAction+", ";
    r+=g.get("health")+" "+health+"/"+"100"+", ";
    r+=g.get("phéromone")+" "+ph.toHex()+" (±"+pheromoneTolerence+")"+", ";
    try {
      r+=e.getName();
    }catch (Exception e) {}
    return r;
  }
  /**
  *return stade as a string in the good language.
  *@lastEditedVersion 1.29
  */
  public String getStringStade(){
    if (stade==0){ return g.get("imago");}
    if (stade==-3){ return g.get("oeuf");}
    if (stade==-2){ return g.get("larve");}
    if (stade==-1){ return g.get("nymphe");}
    return g.get("stade")+" "+g.get("inconnu")+" ("+stade+")";
  }
  //... equals(Creature c) // c'est ObjetAId qui compare l'id.

  /**
  *{@summary Is this consider as an ally of c ?}<br>
  *@param c A Creature that whant to know if we are ally.
  *@lastEditedVersion 1.13
  */
  public boolean getEstAllié(Creature c){ // en théorie la fourmi f reconnait plus ou moins en fonction de ses caractéristique les autre créature.
    if(this.getPheromone().equals(c.getPheromone(),c.getPheromoneTolerence())){return true;}
    return false;
  }
  /**
  *{@summary Is this consider as an enemy of c ?}<br>
  *@param c A Creature that whant to know if we are enemy.
  *@lastEditedVersion 1.13
  */
  //est ce que c nous concidère comme ennemis.
  //TODO Neutral insect should not be seen as Enemy.
  public boolean getEstEnnemi(Creature c){
    if(this.getPheromone().equals(c.getPheromone(),c.getPheromoneTolerence())){ return false;} //c me voie comme un allié aucune raison de ce méfier de moi
    //TODO chercher si dans ma liste des proies de this on trouve c.getType(); si je mange c, c doit ce méfier de moi.
    //if(this.getGiProie().contient(c.getType())){ return true;}
    if(!this.getPheromone().equals(c.getPheromone(),math.min(127,c.getPheromoneTolerence()*6))){ return true;} // c est une fourmi non alliés, et nous n'avons pas de lien de parenté.
    return false; //sinon a priori on est neutre.
  }
  /**
  *{@summary Is this consider as neutral for c ?}<br>
  *@param c A Creature that whant to know if we are neutral.
  *@lastEditedVersion 1.13
  */
  public boolean getIsNeutral(Creature c){
    return !getEstAllié(c) && !getEstEnnemi(c);
  }
  /**
  *{@summary Return a friendly level. Higer is more frienly.}<br>
  *@param c A Creature to test friendly level.
  *@lastEditedVersion 2.18
  */
  public int friendlyLevel(Creature c){
    if(c.getEstAllié(this)){return 1;}
    else if(c.getEstEnnemi(this)){return -1;}
    return 0;
  }
  /**
   *{@summary find all allied Creature on the same Square.}<br>
   *@lastEditedVersion 1.7
   */
  public GCreature getAlliéSurLaSquare(){
    //if(!e.getPolycalique()){return new GCreature(this);} //pris en compte par la diff phéromonale tolléré
    return getCSquare().getContent().getGc().filterAlliés(this);
  }
  /**
   *{@summary find all allied Creature on the same Square and remove this form the GCreature.}<br>
   *@lastEditedVersion 1.7
   */
  public GCreature getAlliéSurLaSquareSansThis(){
    //if(!e.getPolycalique()){return new GCreature();}//pris en compte par la diff phéromonale tolléré
    GCreature gc = getAlliéSurLaSquare();
    try {
      gc.remove(this);
    }catch (Exception e) {}
    return gc;
  }
  /**
  *{@summary check if this should died of reason x. }<br>
  *@param x Reason to died or not.
  *@lastEditedVersion 1.20
  */
  public void mourirOuPas(int x){
    if(x==2 && age>maxAge){mourir(x);return;}
    if(x==3 && food < 0){mourir(x);return;}
  }
  /**
  *{@summary check if this is hungry.}<br>
  *@param percentageOfHungryness 0=are you starving to death, 40=are you hungry, 90=can you eat something more.<br>
  *return true if actual %age of food is 	&#60; than percentageOfHungryness.<br>
  *@lastEditedVersion 1.28
  */
  public boolean isHungry(int percentageOfHungryness){
    if(percentageOfHungryness>100){percentageOfHungryness=100;}
    else if(percentageOfHungryness<0){percentageOfHungryness=0;}
    if((getFood()*100)/getMaxFood()<percentageOfHungryness){
      return true;
    }
    return false;
  }

  //function shared by all Creature. -------------------------------------------
  /**
  *{@summary Eat with the interface Chasse.}<br>
  *It will stop eating only if action &#60;&#61; 0
  *or is not hungry
  *or it transport something
  *or chasse have returned false (creature haven't eat the last time he try).<br>
  *If transported thing can be eat, it will be done.
  *Else transported thing will be drop at anthill.
  *@lastEditedVersion 2.29
  */
  // TOTEST
  public void eat(int percentageOfHungryness){
    int direction=getDirAllea();
    while(getAction()>0
        && isHungry(percentageOfHungryness)
        && getTransported()==null
        && chasser(direction)){}
    if(getTransported()!=null){
      if(this instanceof Fourmi){
        Fourmi f = (Fourmi)this;
        if(chasse.canEatSeed()){chasse.eatSeed();}//eat seed
        else if(f.estALaFere()){chasser(direction);}//drop seed here
        else {ceDeplacer(f.getFere().getCSquare());}//go fere to drop seed & maybe find a better one
      }
      if(getAction()>0 && isHungry(percentageOfHungryness)){
        eat(percentageOfHungryness);
      }
    }
  }

  /**
  *{@summary Run away if a predator is next to you.}<br>
  *@lastEditedVersion 1.28
  */
  public void runAway(){ //TODO
    //an ant do now run away if in his anthill.
    //while(c.getAction()>0  && c.fuire()){
    return;
  }

  /**
  *Actualise Creature c before the turn.
  *@lastEditedVersion 1.28
  */
  public void preTurn(){
    setAction(math.min(getAction(),0) + getMaxAction());//If we have used more action that what we had, we have less this turn.
    if((evoluer instanceof EvoluerNull) && (getStade()!=0 && getAge()>=getMaxAge())){ evoluer();}
  }
}
