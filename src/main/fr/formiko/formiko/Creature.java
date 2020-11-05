package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;
import fr.formiko.formiko.interfaces.*;
import fr.formiko.usuel.math.allea;
import fr.formiko.usuel.math.math;
import fr.formiko.usuel.conversiondetype.str;

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
  protected boolean femelle;
  protected byte tolerencePheromone;
  protected byte stade; // -2 = oeuf, -1 = larve, 0 =  imago L'utilisation de la variable stade permet de faire manger les larves et pas les oeux. Les diférent stade on également des image distinctes.
  protected int nourritureFournie;
  protected Espece e;
  //private int pv; //Point de vie
  //private int pa; //Point de dégats
  // point de force.
  // CONSTRUCTEUR -----------------------------------------------------------------
  //Principal
  public Creature (CCase p, int age, int ageMax, byte actionMax, Pheromone ph, int nourriture, int nourritureMax){
    super(p);
    this.age = age; this.ageMax= ageMax; this.estMort = false;
    this.action = actionMax; this.actionMax = actionMax;
    this.ph = ph;
    this.nourriture = nourriture; this.nourritureMax =nourritureMax;
    femelle = allea.getBAllea();
    tolerencePheromone=0;
    nourritureFournie=1;
    this.déplacement = new DeplacementNull(); this.chasse = new ChasseNull(); this.pondre = new PondreNull(); this.trophallaxie = new TrophallaxieNull(); this.collecte = new CollecteNull();this.evoluer=new EvoluerNull();this.mourir=new MourirNull();this.netoyer=new NetoyerNull();
  }
  //Auxiliaire
  public Creature (CCase p,int age, int ageMax, byte actionMax){ // Les fourmis utilise ce contructeur.
    this(p,age,ageMax, actionMax, new Pheromone(-128,-128,-128), 10, 100);
  } public Creature(CCase p, int age, int ageMax, int actionMax){ this(p,age,ageMax,(byte) actionMax);}
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
  public void setCCase(CCase p){
    //debug.débogage("Un déplacement de la créature "+ id +" a lieux de la case "+this.getCCase().getContenu().description()+ " a la case "+p.getContenu().description());
    //if (debug.getAffLesEtapesDeRésolution()==true) Main.getGc().afficheCarte();
    this.p.getContenu().getGc().retirer(this);
    this.p = p;
    p.getContenu().getGc().ajouter(this);
    //if (debug.getAffLesEtapesDeRésolution()==true) Main.getGc().afficheCarte();
  }
  public Pheromone getPheromone(){ return ph;}
  public Pheromone getPh(){ return getPheromone();}
  public void setPheromone(Pheromone ph){this.ph = ph; }
  public void setPh(Pheromone ph){ setPheromone(ph);}
  public void setPheromone(byte a, byte b, byte c){ph = new Pheromone(a,b,c);}
  public boolean getEstMort(){ return estMort;}
  public void setEstMort(boolean b){estMort=b;actionMax=0;action=0;}
  public byte getPropreté(){return getProprete();} public byte getProprete(){return propreté;}
  public void setPropreté(int x){setPropreté(str.iToBy(x));}
  public void setPropreté(byte x){ propreté = x; if(x<100){x=100;}}
  public boolean getFemelle(){return femelle;}
  public void setFemelle(boolean b){femelle=b;}
  public byte getStade(){ return stade;}
  public void setStade(byte s){ stade = s;} public void setStade(int x){setStade(str.iToBy(x));}
  public boolean estFourmi(){return (this instanceof Fourmi);}
  public abstract byte getType();//réclame une implémentation de getType.
  public byte getTolerencePheromone(){return tolerencePheromone;}
  public void setTolerencePheromone(byte x){tolerencePheromone=x;}
  public abstract boolean getVole();
  public Espece getEspece(){ return e;}
  public void setEspece(Espece e){ this.e = e;}
  public void setEspece(int e){ setEspece(Main.getEspeceParId(e));}
  public String getNom(){return g.get("creature");}

  //raccourci des action d'interface
  public void ceDeplacer(boolean bIa){déplacement.unMouvement(this,bIa);}
  public void ceDeplacer(CCase p){déplacement.unMouvement(this,p);}
  public void ceDeplacer(int direction){déplacement.unMouvement(this,direction);}
  public void ceDeplacerPlusieurCase(CCase cc){déplacement.plusieurMouvement(this,cc);}
  public void pondre(){pondre.unePonte(this);}
  public void chasse(){chasse.chasse(this);}
  public void chasser(int direction){chasse.chasser(this, direction);}
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
  //public void manger (graine pour certaine fourmi, champnons pour d'autre et herbe pour les insectes.)
  // Fonctions propre -----------------------------------------------------------
  @Override
  public abstract String toString();//on réclame une méthode toString().
  public abstract void afficheToi();
  //public boolean equals(Creature c){return getId()==c.getId();} // c'est ObjetAId qui compare l'id.

  /**
  *{@summary is this consider as an enemy of c ? <br>}
  *@param c A Creature that whant to know if we are ally.
  */
  public boolean getEstAllié(Creature c){ // en théorie la fourmi f reconnait plus ou moins en fonction de ses caractéristique les autre créature.
    if(this.getPheromone().equals(c.getPheromone(),c.getTolerencePheromone())){return true;}
    return false;
  }
  /**
  *{@summary is this consider as an enemy of c ? <br>}
  *@param c A Creature that whant to know if we are enemy.
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
  */
  public void mourirOuPas(int x){
    if(x==2 && age>ageMax){mourir(x);return;}
    if(x==3 && nourriture < 0){mourir(x);return;}
  }
}
