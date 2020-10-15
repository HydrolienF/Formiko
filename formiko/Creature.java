package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.formiko.interfaces.*;

public abstract class Creature extends ObjetSurCarteAId {
  // action
  protected Deplacement déplacement;
  protected Chasse chasse;
  protected Pondre pondre;
  protected Trophallaxie trophallaxie;
  protected Collecte collecte;
  protected Evoluer evoluer;
  protected Mourir mourir;
  protected Netoyer netoyer;
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
  //Age
  public int getAge(){return age;}
  public int getAgeMax(){return ageMax;}
  public void setAgeMax(int x){ageMax = x;}
  public void setAgePlus1(){age++; mourirOuPas(2);}
  public void setAge(int x){age=x;mourirOuPas(2);}
  public void ajouteAge(int x){age+=x; mourirOuPas(2);}
  public int getAction(){return action;}
  public void setAction(byte x){action = x;}
  public void setAction(int x){ if (x<-128 || x>127){ erreur.erreur("Impossible de set un nombre d'action inférieur a -128 ou supérieur a 127","Creature.setAction");
    }else{setAction((byte)x);}
  }
  public void setActionMoins(int x){setAction(getAction() - x);}
  public byte getActionMax(){return actionMax;}
  public void setActionMax(byte x){actionMax =x;}
  public void setCCase(CCase p){
    debug.débogage("Un déplacement de la créature "+ id +" a lieux de la case "+this.getCCase().getContenu().description()+ " a la case "+p.getContenu().description());
    if (debug.getAffLesEtapesDeRésolution()==true) Main.getGc().afficheCarte();
    this.p.getContenu().getGc().retirer(this);
    this.p = p;
    p.getContenu().getGc().ajouter(this);
    if (debug.getAffLesEtapesDeRésolution()==true) Main.getGc().afficheCarte();
  }
  public Pheromone getPheromone(){ return ph;}
  public Pheromone getPh(){ return getPheromone();}
  public void setPheromone(Pheromone ph){this.ph = ph; }
  public void setPh(Pheromone ph){ setPheromone(ph);}
  public void setPheromone(byte a, byte b, byte c){this.ph = new Pheromone(a,b,c);}
  public boolean getEstMort(){ return estMort;}
  public void setEstMort(boolean b){this.estMort=b;actionMax=0;action=0;}

  public byte getPropreté(){return getProprete();} public byte getProprete(){return propreté;}
  public void setPropreté(int x){
    if (x > 127 || x < -128){ erreur.erreur("La variable propretée doit être un byte et ne peu pas être : "+x);}
    if(x>100){x=100;}
    else if(x<0){x=0;}
    setPropreté((byte)x);
  }public void setPropreté(byte x){ propreté = x;}

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
  public void mourir(){mourir(0);}
  public void mourir(int r){mourir.mourir(this,r);}
  public void netoyer(){netoyer.netoyer(this);}
  public void netoyer(Creature c){netoyer.netoyer(this,c);}
  public void ceNetoyer(){netoyer.netoyer(this,this);}
  public boolean netoyerIa(){return netoyer.netoyerIa(this);}
  //public void manger (graine pour certaine fourmi, champnons pour d'autre et herbe pour les insectes.)
  public boolean estFourmi(){return this.getClass().equals(new Fourmi().getClass());}
  // Fonctions propre -----------------------------------------------------------
  @Override
  public abstract String toString();//on réclame une méthode toString().
  public abstract void afficheToi();
  //public boolean equals(Creature c){return getId()==c.getId();}

  public void mourirOuPas(int x){
    if(x==2 && age>ageMax){mourir(x);return;}
    if(x==3 && nourriture < 0){mourir(x);return;}
  }
  public boolean getEstAllié(Fourmi f){ // en théorie la fourmi f reconnait plus ou moins en focntion de ses caractéristique les autre créature.
    if(this.getPheromone().equals(f.getPheromone(),5)){
      return true;
    }return false;
  }
  public boolean getEstEnnemi(Fourmi f){
    if(this.getPheromone().equals(f.getPheromone(),30)){
      /*try {
        Insecte i = (Insecte) this;
        return false; // pour l'instant on concidère que tout les insectes sont passif.
      }catch (Exception e) {*/
        return false;
      //}
    }return true;
  }
}