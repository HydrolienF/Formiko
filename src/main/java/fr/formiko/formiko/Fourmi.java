package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Message;
import fr.formiko.formiko.interfaces.*;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.menu;
import fr.formiko.usuel.read;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

import java.io.Serializable;

/**
*{@summary The ant class.}<br>
*Ant is the creature used by player.<br>
*Ant aren't different by extends of this class but but Espece &#38; Individu.<br>
*@author Hydrolien
*@version 1.30
*/
public class Fourmi extends Creature implements Serializable{
  /***
  *It can be 0: ♀, 1: ♂, 2: Minor, 3: Medium, 4:Major, 5:soldier 6+:other type.
  */
  protected byte typeF;
  protected byte mode; // Par défaut la fourmi chasse (0)
  // Elle peut aussi défendre la fourmilière (1) ou aider a la création de nouvelles fourmis (3)
  protected Fourmiliere fere;
  protected byte duretéMax;
  private static byte uneSeuleAction=-1;
  private static boolean bUneSeuleAction=false;
  private static boolean bActualiserTaille=false;
  protected boolean ailesCoupees=true;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *By default the ant is an egg of 0 years old &#38; 100/100 of cleaning
  *Pheromone are set depending of the queen of the anthill.
  *param fere The anthill of this.
  *param e The specie of this.
  *param ty The typeF of this. It can be 0: ♀, 1: ♂, 2: Minor, 3: Medium, 4:Major, 5:soldier 6+:other type.
  *@version 1.39
  */
  // /!\ Ant need to be add to the Fourmiliere after that.
  public Fourmi(Fourmiliere fere, Espece e, byte ty){ // arrivé d'un oeuf.
    // on devrais fixer l'age max en fonction de la difficulté la aussi
    super(fere.getCc(),0,e.getGIndividu().getIndividuParType(ty).getAgeMax(0),0);
    typeF = ty; this.e = e; this.fere = fere; stade = (byte)-3; propreté = (byte) 100;
    iniPheromone();
    // a modifier a partir des individus quand duretée sera un paramètre. OU alors on dit que duretéMax est fixe en fonction des individus. Genre les gros casse tout, les moyen jusqu'a 60 et les petit jusqu'a 20.
    duretéMax=0;
    setNourritureFournie(e.getNourritureFournie(getStade()));
    fere.getCc().getContenu().getGc().ajouter(this);
    evoluer = new EvoluerFourmi();
    mourir = new MourirFourmi();
    if(e.getPolycalique()){tolerencePheromone=5;}//si c'est une espèce capable de s'endendre avec les fourmilières de la même famille.
    iniTour();
  }
  /**
  *{@summary Secondary constructor.}<br>
  *param fere The anthill of this.
  *param e The specie of this.
  *param ty The typeF of this. It can be 0: ♀, 1: ♂, 2: Minor, 3: Medium, 4:Major, 5:soldier 6+:other type.
  *@version 1.39
  */
  public Fourmi(Fourmiliere fere, Espece e, int ty){ this(fere,e,(byte)ty);}
  /**
  *{@summary Secondary constructor.}<br>
  *param fere The anthill of this.
  *param e The specie of this.
  *param ty The typeF of this. It can be 0: ♀, 1: ♂, 2: Minor, 3: Medium, 4:Major, 5:soldier 6+:other type.
  *param stade The stade of the ant. It will call evoluer() to be sur that everything it update as if the ant have grow.
  *@version 1.39
  */
  public Fourmi(Fourmiliere fere, Espece e, byte ty, byte stade){
    this(fere,e,ty);
    this.stade = (byte)(stade-1); evoluer(); //On simule le fait que la fourmi vien d'éclore.
    nourriture = 50; // on lui donne un peu de nourriture pour évité qu'elle ne meurt des le début.
  }
  /**
  *{@summary Secondary constructor.}<br>
  *param fere The anthill of this.
  *param e The specie of this.
  *param ty The typeF of this. It can be 0: ♀, 1: ♂, 2: Minor, 3: Medium, 4:Major, 5:soldier 6+:other type.
  *param stade The stade of the ant. It will call evoluer() to be sur that everything it update as if the ant have grow up.
  *param ph The Pheromone of the ant.
  *@version 1.39
  */
  public Fourmi(Fourmiliere fere, Espece e, byte ty, byte st, Pheromone ph){
    this(fere,e,ty,st);
    this.ph =ph;
  }
  /***
  *{@summary Null constructor.}<br>
  *Use only for test.
  *@version 1.39
  */
  public Fourmi(){}//a ne pas utiliser sauf pour les test de class.
  // GET SET -----------------------------------------------------------------------
  public byte getTypeF(){return typeF;}
  public void setTypeF(byte s){typeF = s;}public void setTypeF(int x){setTypeF((byte)x);}
  public byte getMode(){return mode;}
  public void setMode(byte x){mode = x;}public void setMode(int x){setMode((byte)x);}
  public void setFourmiliere(Fourmiliere gf){fere = gf;}public void setFere(Fourmiliere fere){setFourmiliere(fere);}
  public Fourmiliere getFourmiliere(){return fere;} public Fourmiliere getFere(){ return getFourmiliere();}
  public Joueur getJoueur(){ if(getFere()==null){ return null;}return getFere().getJoueur();}
  public byte getDuretéMax(){ return duretéMax;}
  public void setDuretéMax(byte x){ duretéMax=x; }
  public int getX(){return getCCase().getContenu().getX();}
  public int getY(){return getCCase().getContenu().getY();}
  public void setNourritureMoinsConsomNourriture(){ setNourriture(getNourriture()-getNourritureConso());}
  public Individu getIndividu(){ return e.getIndividuParType(typeF);}
  public boolean getTropDeNourriture(){if(getNourriture()*1.1>getNourritureMax()){ return true;} return false;}
  @Override
  public boolean getFemelle(){ return typeF!=1;}// c'est une femmelle si ce n'est pas un male.
  @Override
  public void setFemelle(boolean b){erreur.alerte("Le sexe d'une fourmi ne peu pas être modifié, modifiez plutot son type.");}
  @Override
  public byte getType(){return -2;}//Les Fourmis sont toutes identifié comme -2.
  @Override
  public boolean getVole(){
    if(!e.getVole() || getTypeF() > 1){return false;}//si l'espece ne vole pas ou si le type n'est pas male ou reine.
    return !getAilesCoupees();//true si les ailes ne sont pas coupée.
  }
  public boolean getAilesCoupees(){return ailesCoupees;}
  public void setAilesCoupees(boolean b){ailesCoupees=b;}
  //static
  public static byte getUneSeuleAction(){return uneSeuleAction;}
  public static void setUneSeuleAction(int x){uneSeuleAction=(byte)x;}public static void setUneSeuleAction(){setUneSeuleAction(-1);}
  public static boolean getBUneSeuleAction(){return bUneSeuleAction;}
  public static void setBUneSeuleAction(boolean b){bUneSeuleAction=b;}
  public static void setBActualiserTaille(boolean b){bActualiserTaille=b;}
  @Override
  public String getNom(){return g.get("fourmi");}
  //racourci
  public Fourmi getReine(){ return getFere().getGc().getReine();}
  public byte getPropretéPerdu(){return e.getPropretéPerdu(stade);}
  public int getNourritureConso(){return getIndividu().getNourritureConso(getStade());}
  /**
  *{@summary Return true if is own by an AI.}<br>
  *If it have an anthill that have a player it will return getIa() value of the player.<br>
  *@version 1.40
  */
  @Override
  public boolean getIa(){
    try {return getFere().getJoueur().getIa();}
    catch (NullPointerException e) {return false;}
  }
  // Fonctions propre -----------------------------------------------------------
  public String toString(){return super.toString() +" "+ tableau.tableauToString(descriptionTableau());}
  public void afficheToi (){System.out.println(description());}
  public boolean estReine(){return getTypeF()==0;}
  public String description(){
    return toString();
  }
  /**
  *{@summary return the max age for an Individu depending of Espece 	&#38; stade.}
  *@param especeTempId Temporary Espece id.
  *@param stadeTemp Temporary stade.
  */
  public int getAgeMaxIndividu(int especeTempId, int stadeTemp){ // b vas de -3 oeuf a 0 imago
    Individu in2;
    if(especeTempId!=100){
      in2 = e.getIndividuParType(especeTempId);
    }else{
      in2 = getIndividu();
    }
    if(in2==null){erreur.erreur("L'individu de stade "+especeTempId+" n'as pas été trouvé.");in2 = getIndividu();}
    return (int)((double)(in2.getAgeMax(stadeTemp+3)*getMultiplicateurDeDiff()));
  }
  /**
  *{@summary return the max age for an Individu with stade=0 (imago) 	&#38; individu already define.}
  */
  public int getAgeMaxIndividu(){
    return getAgeMaxIndividu(100,0);
  }
  /**
  *{@summary return the difficulty multiplier.}<br>
  *Difficulty multiplier is in [0.2;3] <br>
  */
  public double getMultiplicateurDeDiff(){
    double vit = Main.getVitesseDeJeu();
    boolean ia = fere.getJoueur().getIa();
    double difé = (double)Main.getDifficulté();
    double diff=1;
    diff = 1+0.2*difé;// +0.2*difé sera négatif si la difficulté est négative.
    //on évite les dépassements.
    if(diff>3){diff=3;}
    else if(diff<0.2){diff=0.2;}
    double x = diff*vit;
    return x;
  }
  public int getSeuilDeRisqueDInfection(){ // dépend du boolean ia et de la difficulté de la partie.
    int x;
    if(fere.getJoueur().getIa()){
      x=50-(Main.getDifficulté()*10);
    }else{//les joueurs
      x=50+(Main.getDifficulté()*10);
    }
    if (x<10){ x=10;} if (x>70){ x=70;} // seuil a ne pas dépacer.
    return x;
  }
  /**
  *{@summary True if the ant is at its anthill.}<br>
  *@version 1.39
  */
  public boolean estALaFere(){
    try {
      if (this.getCCase().equals(this.getFourmiliere().getCCase())){ return true;}
    }catch (Exception e) {
      erreur.alerte("Impossible de savoir si la fourmi est a la fourmilière","Fourmi.estALaFere");
    }
    return false;
  }

  //public byte getModeReine(){return 0;}
  public String [] descriptionTableau(){
    String tr [] = new String [4];
    String idTrans = "Rien"; if(transported != null){ idTrans = ""+transported.getId();}
    int k=0;
    //tr[k]=g.get("la")+" "+getNom()+" "+getId();k++;
    //tr[k]=g.get("coordonnées")+" : "+p.desc();k++;
    tr[k]=g.get("type")+" : "+getIndividu().getStringType();k++;
    //tr[k]=g.get("stade")+" : "+getStringStade();k++;
    //tr[k]=g.get("age")+" : " + age+ "/"+ageMax;k++;
    //tr[k]=g.get("nourriture")+" : " + nourriture+ "/"+nourritureMax;k++;
    //tr[k]=g.get("action")+" : "+action+"/"+actionMax;k++;
    //tr[k]=g.get("propreté")+" : "+propreté+"/100";k++;
    tr[k]=g.get("fourmilière")+" : "+fere.getId();k++;
    tr[k]=g.get("mode")+" : "+mode;k++;
    //tr[k]=g.get("Pheromone")+" : "+ this.getPheromone().toString();k++;
    tr[k]=g.get("transported")+" : "+idTrans;k++;
    //tr[k]=g.get("espèce")+" : "+this.getEspece().getNom();k++;
    return tr;
  }
  public GString descriptionGString(){
    GString gs = new GString();
    gs.add(g.get("type")+" : "+getIndividu().getStringType());
    gs.add(g.get("stade")+" : "+getStringStade());
    gs.add(g.get("age")+" : " + age+ "/"+ageMax);
    gs.add(g.get("nourriture")+" : " + nourriture+ "/"+nourritureMax);
    gs.add(g.get("action")+" : "+action+"/"+actionMax);
    gs.add(g.get("propreté")+" : "+propreté+"/100");
    //gs.add(g.get("fourmilière")+" : "+fere.getId());
    //gs.add(g.get("mode")+" : "+mode);
    //gs.add(g.get("Pheromone")+" : "+ ph.description());
    if(transported != null){ gs.add(g.get("transported")+" : "+""+transported.getId());}
    gs.add(g.get("espèce")+" : "+e.getNom());
    return gs;
  }

  /*public boolean mangerGraine(){
    //if(fere.getGGraine().getDébut()==null){return false;}
    Graine g = fere.getGGraine().getGraineOuverte();
    if(this.getNourriture() < this.getNourritureMax()/2 && g!=null){
      nourriture = nourriture + g.getNourritureFournie();
      fere.getGGraine().retirerGraine(g); return true;
    }return false;
  }*/
  /*public boolean casserGraine(){
    debug.débogage("tentative de cassage de graine");
    try {
      debug.débogage("Etape 1");
      System.out.println(fere.getGg().getGrainePlusDeNourritureFournie(this));
      if (fere.getGg().getGrainePlusDeNourritureFournie(this).getDureté() < this.getDuretéMax()){
        debug.débogage("Etape 2");
        fere.getGg().getGrainePlusDeNourritureFournie(this).casser();return true;
      }return false;
    }catch (Exception e) {
      return false;
    }
  }*/
  /**
  *{@summary un-clean this.}
  *It also let this died if it is under the seuilDeRisqueDInfection 	&#38; that this have bad luck.
  *@version 1.29
  */
  public void salir(){
    double chanceDeMort = allea.getRand()*getSeuilDeRisqueDInfection(); // on tire le nombre min pour survivre a ce tour.
    if (getPropreté()<allea.getRand()*50){mourir(1);}
    setPropreté(getPropreté() - getPropretéPerdu());
  }
  /**
  *{@summary return true if this whant some food.}
  *This ask for food if it is hungry at 5% or if food is &#60; at what we need for 2 days.
  *@version 1.29
  */
  public boolean wantFood(){
    if(stade==-3){return false;}
    return isHungry(5) || (getNourriture() < math.min(getNourritureConso()*2,getNourritureMax()));
  }
  /**
  *{@summary return true if this whant to be clean.}
  *This want to le clean if in 2 turns it will be under the seuilDeRisqueDInfection.
  *@version 1.29
  */
  public boolean wantClean(){
    if(getProprete()>99){return false;}
    return getProprete() - (getPropretéPerdu()*2) <= getSeuilDeRisqueDInfection();
  }
  /**
  *{@summary initialize tour value for an ant.}<br>
  *If that's a non-ai player it will have TourFourmiNonIa else it will have TourReine or TourFourmi depending of getReine().
  *@version 1.31
  */
  public void iniTour(){
    if(getFere().getJoueur().getIa()){
      if(estReine()){
        tour = new TourReine();
      }else{
        tour = new TourFourmi();
      }
    }else{
      tour = new TourFourmiNonIa();
    }
  }
  /**
  *{@summary initialize ph value for an ant.}<br>
  *It take similar pheromone to the 1 of the queen.
  *If the queen is death it take ph from the 1a an of the anthill.
  *If there is not more and it take a random pheromone.
  *@version 1.31
  */
  public void iniPheromone(){
    Fourmi reine = getReine();
    if (reine != null){ e = reine.getEspece(); ph = new Pheromone(reine.getPheromone());}
    else if (fere.getGc().getDébut() != null){ ph = new Pheromone(fere.getGc().getDébut().getContenu().getPheromone());}
    else{ ph = new Pheromone();}
  }
}
