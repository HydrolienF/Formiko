package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;
import fr.formiko.formiko.interfaces.*;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.type.str;
/**
*{@summary The class that extends every living thing on the game.<br>}
*Most of the common var between Fourmi and Insecte can be found here.<br>
*Creature have a lot of interfaces. They can be used to do every action that a creature can do. If a creature can not do an action as lay (pondre) and try to do it, an error message will appear. This actions can all be call by a short cut here (to be able to do creature.action() and not ActionFourmi.action(creature)).<br>
*@author Hydrolien
*@version 1.13
*/
public abstract class Creature extends ObjetSurCarteAId implements Serializable{
  // action
  public Deplacement déplacement;
  public Chasse chasse;
  public Pondre pondre;
  public Trophallaxie trophallaxie;
  public Collecte collecte;
  public Evoluer evoluer;
  public Mourir mourir;
  public Netoyer netoyer;
  public Tour tour;
  // variable
  // CCase p
  protected int nourriture;
  protected int nourritureMax;
  protected int age;
  protected int ageMax;
  protected boolean estMort;
  protected byte action;
  protected byte actionMax;
  protected Pheromone ph;
  protected byte propreté;
  protected byte tolerencePheromone;
  protected byte stade; // -2 = oeuf, -1 = larve, 0 =  imago L'utilisation de la variable stade permet de faire manger les larves et pas les oeux. Les diférent stade on également des image distinctes.
  protected int nourritureFournie;
  protected Espece e;
  //TODO #73
  //protected int typeEated [];
  //private int pv; //Point de vie
  //private int pa; //Point de dégats
  // point de force.
  // CONSTRUCTEUR -----------------------------------------------------------------
  /**
  *{@summary Main constructor for Creature.<br>}
  *All args are Creature var.
  *@version 1.13
  */
  public Creature (CCase p, int age, int ageMax, byte actionMax, Pheromone ph, int nourriture, int nourritureMax){
    super(p);
    this.age = age; this.ageMax= ageMax; this.estMort = false;
    this.action = actionMax; this.actionMax = actionMax;
    this.ph = ph;
    this.nourriture = nourriture; this.nourritureMax =nourritureMax;
    tolerencePheromone=0;
    nourritureFournie=1;
    this.déplacement = new DeplacementNull(); this.chasse = new ChasseNull(); this.pondre = new PondreNull(); this.trophallaxie = new TrophallaxieNull(); this.collecte = new CollecteNull();this.evoluer=new EvoluerNull();this.mourir=new MourirNull();this.netoyer=new NetoyerNull();
    this.tour = new TourCreatureSansAction();
  }
  /**
  *{@summary constructor for Creature.<br>}
  *Here we only know some var, but the main constructor will take care of them.
  *@version 1.13
  */
  public Creature (CCase p,int age, int ageMax, byte actionMax){ // Les fourmis utilise ce contructeur.
    this(p,age,ageMax, actionMax, new Pheromone(-128,-128,-128), 10, 100);
  } public Creature(CCase p, int age, int ageMax, int actionMax){ this(p,age,ageMax,(byte) actionMax);}
  /**
  *{@summary constructor for Creature.<br>}
  *Here we only know some var, but the main constructor will take care of them.
  *@version 1.13
  */
  public Creature (CCase p,int age, int ageMax){
    this(p,age,ageMax,(byte) 50); // 50 action par défaut.
  }
  public Creature (CCase p,int ageMax){this(p,0,ageMax);}
  public Creature (CCase p){this(p,100);}
  public Creature (){this((CCase) null);}
  // GET SET -----------------------------------------------------------------------
  //Nourriture
  public int getNourriture(){return nourriture;}
  public int getNourritureMax(){return nourritureMax;}
  public void setNourritureMoins1(){nourriture--; mourirOuPas(3);}
  public void setNourriture(int x){nourriture = x;diminuerOuPasNourriture();mourirOuPas(3);}
  public void setNourritureMax(int x){ nourritureMax = x;diminuerOuPasNourriture();}
  public void ajouteNourriture(int x){nourriture += x;diminuerOuPasNourriture();mourirOuPas(3);}
  public void diminuerOuPasNourriture(){if (nourriture>nourritureMax) {nourriture = nourritureMax;}}
  public int getNourritureFournie(){return nourritureFournie;}
  public void setNourritureFournie(int x){nourritureFournie=x;}
  //Age
  public int getAge(){return age;}
  public int getAgeMax(){return ageMax;}
  public void setAgeMax(int x){ageMax = x;}
  public void setAgePlus1(){age++; mourirOuPas(2);}
  public void setAge(int x){age=x;mourirOuPas(2);}
  public void ajouteAge(int x){age+=x; mourirOuPas(2);}
  public int getAction(){return action;}
  public void setAction(byte x){action = x;}
  public void setAction(int x){setAction(str.iToBy(x));}
  public void setActionMoins(int x){setAction(getAction() - x);}
  public byte getActionMax(){return actionMax;}
  public void setActionMax(byte x){actionMax =x;}
  /**
  *move the Creature from a case to an other (used by Deplacement interfaces).
  *@version 1.13
  */
  public void setCCase(CCase p){
    this.p.getContenu().getGc().retirer(this);
    this.p = p;
    p.getContenu().getGc().ajouter(this);
  }
  public void setCc(CCase cc){setCCase(cc);}
  public Pheromone getPheromone(){ return ph;}
  public Pheromone getPh(){ return getPheromone();}
  public void setPheromone(Pheromone ph){this.ph = ph; }
  public void setPh(Pheromone ph){ setPheromone(ph);}
  public void setPheromone(byte a, byte b, byte c){ph = new Pheromone(a,b,c);}
  public boolean getEstMort(){ return estMort;}
  public void setEstMort(boolean b){estMort=b;actionMax=0;action=0;}
  public byte getPropreté(){return getProprete();} public byte getProprete(){return propreté;}
  public void setPropreté(int x){setProprete(x);}
  public void setProprete(int x){setPropreté(str.iToBy(x));}
  public void setPropreté(byte x){ propreté = x; if(x<100){x=100;}}
  public abstract boolean getFemelle();
  public abstract void setFemelle(boolean b);
  public byte getStade(){ return stade;}
  public void setStade(byte s){ stade = s;} public void setStade(int x){setStade(str.iToBy(x));}
  public boolean estFourmi(){return (this instanceof Fourmi);}
  public abstract byte getType();//réclame une implémentation de getType.
  public byte getTolerencePheromone(){return tolerencePheromone;}
  public void setTolerencePheromone(byte x){tolerencePheromone=x;}
  public abstract boolean getVole();
  public abstract boolean wantFood();
  public abstract boolean wantClean();
  public Espece getEspece(){ return e;}
  public void setEspece(Espece e){ this.e = e;}
  public void setEspece(int e){ setEspece(Main.getEspeceParId(e));}
  public String getNom(){return g.get("creature");}

  //raccourci des actions d'interface
  public void ceDeplacer(boolean bIa){déplacement.unMouvement(this,bIa);}
  public void ceDeplacer(CCase p){déplacement.unMouvement(this,p);}
  public void ceDeplacer(int direction){déplacement.unMouvement(this,direction);}
  public void ceDeplacerPlusieurCase(CCase cc){déplacement.plusieurMouvement(this,cc);}
  public void pondre(){pondre.unePonte(this);}
  public boolean chasse(){return chasse.chasse(this);}
  public boolean chasser(int direction){return chasse.chasser(this, direction);}
  public void trophallaxie(int id, int nourritureDonnée){trophallaxie.trophallaxie(this, id, nourritureDonnée);}
  public void trophallaxie(Creature c, int nourritureDonnée){trophallaxie.trophallaxie(this,c, nourritureDonnée);}
  public void trophallaxer(){trophallaxie.trophallaxer(this);}
  public void collecter(int direction){collecte.collecter((Fourmi) this, (byte) direction);}
  public void evoluer(){evoluer.evoluer(this);}
  public void mourir(){mourir(0);}//mourrir sans raison spécifié.
  public void mourir(int r){mourir.mourir(this,r);}
  public void supprimerDeLaCarte(){ mourir.supprimerDeLaCarte(this);}
  public void netoyer(){netoyer.netoyer(this);}
  public void netoyer(Creature c){netoyer.netoyer(this,c);}
  public void ceNetoyer(){netoyer.netoyer(this,this);}
  public boolean netoyerIa(){return netoyer.netoyerIa(this);}
  public void tour(){tour.unTour(this);}
  //public void preTour(){tour.preTour(this);}
  //public void manger (graine pour certaine fourmi, champnons pour d'autre et herbe pour les insectes.)
  // Fonctions propre -----------------------------------------------------------
  @Override
  public String toString(){
    String r = "";
    r+= g.getOu("le","la")+" "+getNom();
    r+=" ";
    r+=getId();r+=" ";
    if (this.getFemelle()){r+= "♀";}
    else {r+= "♂";}r+=" ";
    if (estMort){r+= "(☠︎)";}
    else {r+= "("+(ageMax-age)+" "+g.get("avant")+" ☠︎)";}r+=" ";
    r+=p.desc();r+=", ";
    r+=g.get("stade")+" "+getStringStade()+", ";
    r+=g.get("nourriture")+" "+nourriture+"/"+nourritureMax+" (nf:"+nourritureFournie+")"+", ";
    r+=g.get("age")+" "+age+"/"+ageMax+", ";
    r+=g.get("action")+" "+action+"/"+actionMax+", ";
    r+=g.get("propreté")+" "+propreté+"/"+"100"+", ";
    r+=g.get("phéromone")+" "+ph.toString()+", ";
    r+=g.get("tolerencePheromone")+" "+tolerencePheromone+", ";
    r+=g.get("espèce")+" "+e.getNom();
    return r;
  }
  /**
  *return stade as a string in the good language.
  *@version 1.29
  */
  public String getStringStade(){
    if (stade==0){ return g.get("imago");}
    if (stade==-3){ return g.get("oeuf");}
    if (stade==-2){ return g.get("larve");}
    if (stade==-1){ return g.get("nymphe");}
    return g.get("stade")+" "+g.get("inconnu")+" ("+stade+")";
  }
  //public abstract void afficheToi();
  //public boolean equals(Creature c){return getId()==c.getId();} // c'est ObjetAId qui compare l'id.

  /**
  *{@summary is this consider as an enemy of c ? <br>}
  *@param c A Creature that whant to know if we are ally.
  *@version 1.13
  */
  public boolean getEstAllié(Creature c){ // en théorie la fourmi f reconnait plus ou moins en fonction de ses caractéristique les autre créature.
    if(this.getPheromone().equals(c.getPheromone(),c.getTolerencePheromone())){return true;}
    return false;
  }
  /**
  *{@summary is this consider as an enemy of c ? <br>}
  *@param c A Creature that whant to know if we are enemy.
  *@version 1.13
  */
  //est ce que c nous concidère comme ennemis.
  public boolean getEstEnnemi(Creature c){
    if(this.getPheromone().equals(c.getPheromone(),c.getTolerencePheromone())){ return false;} //c me voie comme un allié aucune raison de ce méfier de moi
    //TODO chercher si dans ma liste des proies de this on trouve c.getType(); si je mange c, c doit ce méfier de moi.
    //if(this.getGiProie().contient(c.getType())){ return true;}
    if(!this.getPheromone().equals(c.getPheromone(),math.min(127,c.getTolerencePheromone()*6))){ return true;} // c est une fourmi non alliés, et nous n'avons pas de lien de parenté.
    return false; //sinon a priori on est neutre.
  }
  /**
   *{@summary find all allied Creature on the same Case.<br>}
   *@version 1.7
   */
  public GCreature getAlliéSurLaCase(){
    //if(!e.getPolycalique()){return new GCreature(this);} //pris en compte par la diff phéromonale tolléré
    return getCCase().getContenu().getGc().filtreAlliés(this);
  }
  /**
   *{@summary find all allied Creature on the same Case and remove this form the GCreature.<br>}
   *@version 1.7
   */
  public GCreature getAlliéSurLaCaseSansThis(){
    //if(!e.getPolycalique()){return new GCreature();}//pris en compte par la diff phéromonale tolléré
    GCreature gc = getAlliéSurLaCase();
    gc.retirer(this);
    return gc;
  }
  /**
  *{@summary check if this should died of reason x. <br>}
  *@param x Reason to died or not.
  *@version 1.20
  */
  public void mourirOuPas(int x){
    if(x==2 && age>ageMax){mourir(x);return;}
    if(x==3 && nourriture < 0){mourir(x);return;}
  }
  /**
  *{@summary check if this is hungry.<br>}
  *@param percentageOfHungryness 0=are you starving to death, 40=are you hungry, 90=can you eat something more.<br>
  *return true if actual %age of food is 	&#60; than percentageOfHungryness.<br>
  *@version 1.28
  */
  public boolean isHungry(int percentageOfHungryness){
    if(percentageOfHungryness>100){percentageOfHungryness=100;}
    else if(percentageOfHungryness<0){percentageOfHungryness=0;}
    if((getNourriture()*100)/getNourritureMax()<percentageOfHungryness){
      return true;
    }
    return false;
  }

  //function shared by all Creature. -------------------------------------------
  /**
  *{@summary Eat with the interface Chasse.<br>}
  *It will stop eating only if action &#60;&#61; 0 or is not hungry or chasse have returned false (creature haven't eat the last time he try).<br>
  *return true if the Creature have eat.
  *@version 1.30
  */
  public void eat(int percentageOfHungryness){
    int direction=getDirAllea();
    while(getAction()>0 && isHungry(percentageOfHungryness) && chasser(direction)){}
  }

  /**
  *{@summary Run away if a predator is next to you.<br>}
  *@version 1.28
  */
  public void runAway(){ //TODO
    //while(c.getAction()>0  && c.fuire()){
    return;
  }

  /**
  *Actualise Creature c before the turn.
  *@version 1.28
  */
  public void preTour(){
    setAction(math.min(getAction(),0) + getActionMax());//If we have used more action that what we had, we have less this turn.
    if((evoluer instanceof EvoluerNull) && (getStade()!=0 && getAge()>=getAgeMax())){ evoluer();}
  }
}
