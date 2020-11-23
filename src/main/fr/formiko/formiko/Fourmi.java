package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;
import fr.formiko.usuel.math.allea;
import fr.formiko.usuel.math.math;
import fr.formiko.usuel.read;
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.usuel.menu;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.Message;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.liste.GString;
import fr.formiko.formiko.interfaces.*;
import fr.formiko.graphisme.Question;

public class Fourmi extends Creature implements Serializable{
  protected byte typeF; // 0  Reine 1 = Male 2 = Minor 3 Medium 4 = Major 5 = soldate (etc).
  protected byte mode; // Par défaut la fourmi chasse (0)
  // Elle peut aussi défendre la fourmilière (1) ou aider a la création de nouvelles fourmis (3)
  protected Fourmiliere fere;
  protected Individu in;
  protected ObjetSurCarteAId transporté;
  protected byte duretéMax;
  private static byte uneSeuleAction=-1;
  private static boolean bUneSeuleAction=false;
  private static boolean bActualiserTaille=false;
  protected boolean ailesCoupees=true;

  // CONSTRUCTEUR -----------------------------------------------------------------
  //Principal
  public Fourmi(Fourmiliere fere, Espece e, byte ty){ // arrivé d'un oeuf.
    // on devrais fixer l'age max en fonction de la difficulté la aussi
    super(fere.getCc(),0,e.getGIndividu().getIndividuParType(ty).getAgeMax(0),0);
    typeF = ty; this.e = e; this.fere = fere; stade = (byte)-3; propreté = (byte) 100;
    in = e.getGIndividu().getIndividuParType(ty);
    Fourmi reine = fere.getGc().getReine();
    if (reine != null){ e = reine.getEspece(); ph = new Pheromone(reine.getPheromone());}
    else if (fere.getGc().getDébut() != null){ ph = new Pheromone(fere.getGc().getDébut().getContenu().getPheromone());}
    else{ ph = new Pheromone();}
    // a modifier a partir des individus quand dureté sera un paramètre.
    if(e.getGranivore()){
      duretéMax = 50;
    }else{
      duretéMax = 0;
    }
    if (getTypeF()==4){ duretéMax = 100;}
    setNourritureFournie(e.getNourritureFournie(getStade()));
    fere.getCc().getContenu().getGc().ajouter(this);
    evoluer = new EvoluerFourmi();
    mourir = new MourirFourmi();
    if(e.getPolycalique()){tolerencePheromone=5;}//si c'est une espèce capable de s'endendre avec les fourmilières de la même famille.
  }public Fourmi(Fourmiliere fere, Espece e, int ty){ this(fere,e,(byte)ty);}
  public Fourmi(Fourmiliere fere, Espece e, byte ty,byte stade){
    this(fere,e,ty);
    this.stade = (byte)(stade-1); evoluer(); //On simule le fait que la fourmi vien d'éclore.
    nourriture = 30; // on lui donne un peu de nourriture pour évité qu'elle ne meurt des le début.
  }
  public Fourmi(Fourmiliere fere, Espece e, byte ty, byte st, Pheromone ph){
    this(fere,e,ty,st);
    this.ph =ph;
  }
  public Fourmi(){}//a ne pas utiliser sauf pour les test de class.
  // GET SET -----------------------------------------------------------------------
  public byte getTypeF(){return typeF;}
  public void setTypeF(byte s){typeF = s;}
  public byte getMode(){return mode;}
  public void setMode(byte x){mode = x;}public void setMode(int x){setMode((byte)x);}
  public void setFourmiliere(Fourmiliere gf){fere = gf;}public void setFere(Fourmiliere fere){setFourmiliere(fere);}
  public Fourmiliere getFourmiliere(){return fere;} public Fourmiliere getFere(){ return getFourmiliere();}
  public Joueur getJoueur(){ if(getFere()==null){ return null;}return getFere().getJoueur();}
  public ObjetSurCarteAId getTransporté(){ return transporté;}
  public void setTransporté(ObjetSurCarteAId o){ transporté = o;}
  public byte getDuretéMax(){ return duretéMax;}
  public void setDuretéMax(byte x){ duretéMax=x; }
  public int getX(){return getCCase().getContenu().getX();}
  public int getY(){return getCCase().getContenu().getY();}
  public void setNourritureMoinsConsomNourriture(){ setNourriture(getNourriture()-in.getNourritureConso());}
  public Individu getIndividu(){ return in;}
  public boolean getTropDeNourriture(){if(getNourriture()*1.1>getNourritureMax()){ return true;} return false;}
  public boolean peutPondre(){return this.getNourriture()>20 && this.estALaFere();}
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
  public static void setUneSeuleAction(int x){uneSeuleAction=(byte)x;}public static void setUneSeuleAction(){setUneSeuleAction(-1);}
  public static void setBUneSeuleAction(boolean b){bUneSeuleAction=b;}
  public static void setBActualiserTaille(boolean b){bActualiserTaille=b;}
  @Override
  public String getNom(){return g.get("fourmi");}
  //racourci
  public Fourmi getReine(){ return this.getFere().getGc().getReine();}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){return tableau.tableauToString(descriptionTableau());}
  public void afficheToi (){System.out.println(description());}
  public boolean estReine(){return getTypeF()==0;}
  public String description(){
    return toString();
  }
  public String getStringStade(){
    if (stade==0){ return g.get("imago");}
    if (stade==-3){ return g.get("oeuf");}
    if (stade==-2){ return g.get("larve");}
    if (stade==-1){ return g.get("nymphe");}
    return "stade inconnu ("+stade+")";
  }
  public int getAgeMaxIndividu(int a, int b){ // b vas de -3 oeuf a 0 imago
    Individu in2;
    if(a!=100){
      in2 = e.getIndividuParType(a);
    }else{
      in2 = in;
    }
    if(in2==null){erreur.erreur("L'individu de stade "+a+" n'as pas été trouvé.");in2 = in;}
    return (int)((double)(in2.getAgeMax(b+3)*getMultiplicateurDeDiff()));
  }
  public int getAgeMaxIndividu(){
    return getAgeMaxIndividu(100,0); //0 = imago.
  }
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
  public boolean estALaFere(){
    if (this.getCCase().equals(this.getFourmiliere().getCCase())){ return true;}
    return false;
  }

  public byte getModeReine(){
    if(fere.getGc().getNbrOuvrière() > 0){ return 3;}
    debug.débogage("Choix spéciale");
    //on n'éffectue un choix spéciale que s'il n'y a aucune ouvrière pour aider.
    int nbrDeTour = getAgeMaxIndividu(3,-3) + getAgeMaxIndividu(3,-2) + getAgeMaxIndividu(3,-1);// l'age d'un individu de type 3 (cad ouvrière) et dans les 3 1a stades.
    //10 + la nourriture consommé par tour * nbrDeTour
    int nourritureTotalPossédé = nourriture;
    try { // on retire les tour déja écoulé.
      Fourmi fPetite = (Fourmi) fere.getGc().getCouvain().getDébut().getContenu();
      nbrDeTour = nbrDeTour - fPetite.getAge();
      if(fPetite.getStade()>=-2){
        nbrDeTour = nbrDeTour - getAgeMaxIndividu(3,-3);
      }
      if(fPetite.getStade()>=-1){
        nbrDeTour = nbrDeTour - getAgeMaxIndividu(3,-2);
      }
      nourritureTotalPossédé = nourritureTotalPossédé + fPetite.getNourriture();
    }catch (Exception e) {}
    int nourritureSouhaité = 40 + (in.getNourritureConso()+e.getIndividuParType(0).getNourritureConso())*nbrDeTour;
    if(nourritureSouhaité>nourritureMax){ return 3;} // au cas ou la reine ne pourrait pas stocker autant qu'il faut.
    //si la reine a plus de nourriture qu'il n'en faut pour surveillé un oeuf on part s'en occupé.
    if(nourritureSouhaité<nourritureTotalPossédé){ return 3;}
    debug.débogage("la reine de la fere "+fere.getId()+" n'as pas encore assez de nourriture : "+nourriture+"/"+nourritureSouhaité);
    return 0;
  }
  public byte getChoixBouton(){
    byte choix = (byte) Main.getPj().getActionF();
    while (choix==-1) {
      choix = (byte) Main.getPj().getActionF();
      Temps.pause(10);
      if (bUneSeuleAction){
        Main.getPb().removePa();
        Main.getPb().addPa(Main.getFActuelle().getTActionFourmi());
        bUneSeuleAction=false;
      }
      if(bActualiserTaille){
        Main.getPs().actualiserTailleMax();
        Main.getPs().revalidate();
        Main.repaint();
        bActualiserTaille=false;
      }
      /*else if(uneSeuleAction==-1){
        Main.getPb().removePa();
        Main.getPb().addPa(Main.getFActuelle().getTActionFourmi());
      }*/
    }choix++;
    return choix;
  }
  public byte getChoixJoueur(){
    int [] t = getTActionFourmi();
    Main.getPb().removePa();
    Main.getPb().addPa(t);
    // on attend une action de la fenetre.
    debug.débogage("lancement de l'attente du bouton");
    byte choix = getChoixBouton();
    debug.débogage("action de Fourmi lancé "+choix);
    Main.getPj().setActionF(-1);
    return choix;
  }
  public int [] getTActionFourmi(){
    if(uneSeuleAction!=-1){
      if(uneSeuleAction==20){return new int[0];}
      int t []= new int [1];
      t[0]=(int)uneSeuleAction;
      return t;
    }
    int t []=new int [11];
    for (int i=0;i<11 ;i++ ) {
      t[i]=i;
    }
    GCreature gcCase = this.getCCase().getContenu().getGc();
    if(in.getCoutDéplacement() == -1){ t=tableau.retirerX(t,0);}
    if(in.getCoutChasse() == -1 || gcCase.getGi().length()==0){ t=tableau.retirerX(t,1);}
    if(in.getCoutPondre() == -1 || !peutPondre()){ t=tableau.retirerX(t,2);}
    if(in.getCoutTrophallaxie() == -1 || gcCase.filtreAlliés(this).filtreFaimMax().length() < 2 || this.getNourriture()<1){ t=tableau.retirerX(t,3);}
    if(in.getCoutNétoyer() == -1 ||(netoyer.getNombreDeCreatureANetoyer(this))==0){ t=tableau.retirerX(t,4);}
    if(!e.getGranivore()){
      t=tableau.retirerX(t,5);
      t=tableau.retirerX(t,6);
    }
    try {//on retire l'anciène méthode de déplacement.
      t=tableau.retirerX(t,0);
    }catch (Exception e) {}
    return t;
  }
  public String [] descriptionTableau(){
    String tr [] = new String [13];
    String idTrans = "Rien"; if(transporté != null){ idTrans = ""+transporté.getId();}
    int k=0;
    tr[k]=g.get("la")+" "+getNom()+" "+getId();k++;
    tr[k]=g.get("coordonnées")+" : "+p.desc();k++;
    tr[k]=g.get("type")+" : "+in.getStringType();k++;
    tr[k]=g.get("stade")+" : "+getStringStade();k++;
    tr[k]=g.get("age")+" : " + age+ "/"+ageMax;k++;
    tr[k]=g.get("nourriture")+" : " + nourriture+ "/"+nourritureMax;k++;
    tr[k]=g.get("action")+" : "+action+"/"+actionMax;k++;
    tr[k]=g.get("propreté")+" : "+propreté+"/100";k++;
    tr[k]=g.get("fourmilière")+" : "+fere.getId();k++;
    tr[k]=g.get("mode")+" : "+mode;k++;
    tr[k]=g.get("Pheromone")+" : "+ this.getPheromone().toString();k++;
    tr[k]=g.get("transporté")+" : "+idTrans;k++;
    tr[k]=g.get("espèce")+" : "+this.getEspece().getNom();k++;
    return tr;
  }
  public GString descriptionGString(){
    GString gs = new GString();
    gs.add(g.get("type")+" : "+in.getStringType());
    gs.add(g.get("stade")+" : "+getStringStade());
    gs.add(g.get("age")+" : " + age+ "/"+ageMax);
    gs.add(g.get("nourriture")+" : " + nourriture+ "/"+nourritureMax);
    gs.add(g.get("action")+" : "+action+"/"+actionMax);
    gs.add(g.get("propreté")+" : "+propreté+"/100");
    //gs.add(g.get("fourmilière")+" : "+fere.getId());
    //gs.add(g.get("mode")+" : "+mode);
    //gs.add(g.get("Pheromone")+" : "+ ph.description());
    if(transporté != null){ gs.add(g.get("transporté")+" : "+""+transporté.getId());}
    gs.add(g.get("espèce")+" : "+e.getNom());
    return gs;
  }

  
  public void tourFourmi(){
    int idf = Main.getPs().getIdFourmiAjoué();
    if(idf!=-1 && getId()!=idf){mode=-1;Main.getPb().setVisiblePa(false);Main.getPb().removePi();return;}//si 1 fourmi spéciale est sencé joué et que ce n'est pas celle la.
    boolean estIa = fere.getJoueur().getIa();
    // débloquage des modes auto :
    if (!estIa){ mode = -1;}
    else if(estReine()){mode = getModeReine();}
    else if(getTropDeNourriture()){mode=3;}
    else{mode = fere.getModeDéfaut();if(nourriture<5*in.getNourritureConso()){ mode=0;}}//choixMode();}
    if (stade == 0){ // les fourmis qui ne sont pas encore née ne font rien
      // Un tour de jeu d'une Fourmi
      int direction=getDirAllea();
      byte choix=0; int j=0; int k=0;
      while ( this.getAction() > 0 && k<actionMax+2) { // tant que la fourmie a encore des actions :
        if(k==actionMax+1){
          erreur.erreur("La fourmi "+ getId()+ " est en train de boucler dans ses actions","Fourmi.tourFourmi");
        }
        debug.débogage("Nouveau tour");
        String m = "";
        if (!estIa && mode == -1){
          Main.getPj().setFActuelle(this);
          Main.getPb().addPI();
          Main.getPb().addPIJ();
          //ICI on a une action d'attente.
          choix = (byte)(getChoixJoueur()-1);
          doPlayerAction(choix);
        } else if (estIa){ // si c'est une ia
          if(propreté < 70){ ceNetoyer();}
          //debug.débogage("choix mode");
          m=faireActionAuto(mode,m);
        }
        k++;
        //new Message("La fourmi " +this.getId() +" a choisi de " + m + "."+" mode : "+mode);
        //if(!Main.getMouvementRapide() && estIa){Main.repaint();} // ici on pause le jeu si les mouvement rapide sont désactivé et que la fourmi est une ia.
      }
    }else{ // les fourmis non adulte.
      if (age>=ageMax){ evoluer();}
    }
    if(!estIa && action<=0){Main.getPs().setIdFourmiAjoué(-1);}
  }
  /**
  *make an action chose by the player.
  *@version 1.17
  */
  public void doPlayerAction(int choix){
    if(choix==-2){return;}
    faire(choix);
    faireActionAuto(mode,"");//si l'action est une action de mode auto.
    Main.getPb().removePi(); Main.getPb().removePij();
  }



  /**
  *launch an action for this.
  *@version 1.17
  */
  public String faireActionAuto(int mode, String m){
    // Les modes auto
    if (mode == 0){
      m = "chasser / ce déplacer pour chasser (Ou Récolter des graines)";
      collecterOuChasser(direction, fere.getJoueur().getIa());
    }else if(mode == 1){
      if (!estALaFere()) {rentrer();}
      else {setAction(0);}
      m = "défendre la fourmilière.";
    }else if(mode == 3){
      nourrirEtNétoyer(); m = "Nourrir et Nétoyer";
    }
    return m;
  }




  public void finTour(){
    debug.débogage("Fin du tour de la Fourmi");
    // Un tour ça coute en age et en nourriture;
    if (stade == 0){this.setAction(math.min(getAction(),0) + getActionMax());} // on peu avoir consommé trop d'action au tour précédent mais pas en avoir garder.
    this.setAgePlus1(); this.salir();
    if (stade == 0 || stade == -1 || stade == -2) {this.setNourritureMoinsConsomNourriture();}
    // if contition de température appartient a l'intervale idéale (et que stade = -1, -2 ou -3) : re setAgePlus1();
    if (!fere.getJoueur().getIa()) {
      Main.getPj().setFActuelle(null);
      Main.getPb().setVisiblePa(false);
    }
    Main.getPs().setIdFourmiAjoué(-1);
  }

  public String faire(int choix){
    boolean estIa = fere.getJoueur().getIa();
    String m = switch(choix){
      case 0 :
        ceDeplacer(estIa);
        yield "ceDeplacer";
      case 1 :
        chasse();
        yield "chasse";
      case 2 :
        pondre();
        yield "pondre";
      case 3 :
        trophallaxer();
        yield "trophallaxer";
      case 4:
        netoyer();
        yield "ce netoyer ou nétoyer une autre fourmi";
      case 5:
        mangerGraine();
        yield "mangerGraine";
      case 6 :
        casserGraine();
        yield "casserGraine";
      case 7 :
        setMode(0);
        yield "setMode0";
      case 8 :
        setMode(3);
        yield "setMode1";
      case 9 :
        setAction(0);
        yield "ne rien faire";
      case 10 :
        Question q = new Question("supprimerFourmi.1","supprimerFourmi.2");
        if(q.getChoix()){
          mourir(4);
          yield "supprimer la fourmi";
        }
        yield "ne pas supprimer la fourmi";
      default :
        yield "";
      };
    return m;
  }

  public void collecterOuChasser(int direction, boolean estIa){
    if(this.getEspece().getInsectivore()){
      this.chasser(direction);
    }else if(this.getEspece().getGranivore()){
      debug.débogage("Lancement mode 3 graine.");
      if (transporté == null){
        if (!estALaFere()) {this.collecter(direction);}
        else{this.ceDeplacer(estIa);}
      }else{
        if (!estALaFere()) {rentrer();debug.débogage("rentez");}
        else{
          debug.débogage("Dépo de la graine "+transporté.getId());
          fere.déposer((Graine) transporté);
          this.setTransporté(null);
        }
      }
    }
  }
  public void nourrirEtNétoyer(){
    if (!estALaFere()){ rentrer(); return;}
    boolean aJoué = false;
    if (getTypeF()==4 || (fere.getGc().length() < 50 && this.getEspece().getGranivore())){aJoué = casserGraine();}
    if (!aJoué && this.getEspece().getGranivore()){ aJoué = mangerGraine();}
    if ((estReine() && fere.getJoueur().getIa()) && !aJoué){ aJoué = pondreOuPas();}
    if (!aJoué){ aJoué = nourrir();}
    if (!aJoué){ aJoué = netoyerIa();}
    if (!aJoué && fere.getJoueur().getIa()){
      new Message("La fourmi "+id+" n'as plus d'action nourrirEtNétoyer a faire",this.getFere().getJoueur().getId());
      fere.setModeDéfaut(0); // les prochaines fourmi pourront juste s'occuper de chasser.
      if (estReine() && fere.getGc().getCouvain().length()>=1){ // si c'est la reine et qu'elle a 1 oeuf elle ne doit pas s'enéloigner.
        //if(estALaFere()){chasser(5);}//elle chasse dans un rayon de 1 case.
        //aJoué = nétoyerTtLeMonde();
        //if (!aJoué) {
          setAction(0);
        //}
      } // elle attend qu'on ai besoin d'aide.
      else if(nourriture < 10){ mode=0;} // elle retourne chasser
      else{setAction(0);}
    }else if(!aJoué){//si c'est un joueur.
      setAction(0);
    }
  }
  public boolean mangerGraine(){
    //if(fere.getGGraine().getDébut()==null){return false;}
    Graine g = fere.getGGraine().getGraineOuverte();
    if(this.getNourriture() < this.getNourritureMax()/2 && g!=null){
      nourriture = nourriture + g.getNourritureFournie();
      fere.getGGraine().retirerGraine(g); return true;
    }return false;
  }
  public boolean pondreOuPas(){
    if (this.getFere().getGc().getCouvainSale()!=null && getAlliéSurLaCase().getNbrOuvrière()==0){ return false;}//si personne n'aide et que le couvain et sale.
    if (fere.getGc().getNbrOuvrière()==0 && this.getFere().getGc().getCouvain().length()>=1){ return false;} // pas plus d'un oeuf au début.
    if (nourriture > 50 + this.getFere().getGc().length()*5 || nourriture*2 > nourritureMax){ // soit la reine a au mois la moitié de sa nourriture max soit on cherche a avoir au moins 50 de nourriture plus 5 par fourmi déja présente. Une fourmilière déja bien établie n'as pas besoin de prendre de risque.
      if (getAlliéSurLaCase().getCouvain() != null){
        int pourcentageDeCouvain = (100*getAlliéSurLaCase().getCouvain().length()) / this.getFere().length();
        if (pourcentageDeCouvain > 75 && nourriture > nourritureMax/2){ return false;} // si le nombre d'élément du couvain est vraiment important il faut évité de pondre plus sinon il y aura des morts pendant le dévelloppement du couvain.
      }
      this.pondre(); return true;
    } return false;
  }
  public boolean nourrir(){
    if (this.getNourriture()<30){ return false;}
    Fourmi f = aNourrir();
    if (f!=null && (f.equals(getReine()) || f.getNourriture() < 5*math.max(f.getIndividu().getNourritureConso(),1) )){//|| f.equals(getReine()))) {
      int nourritureDonnée = this.getNourriture()-5*in.getNourritureConso();
      trophallaxie((Creature) f,nourritureDonnée);
      return true;
    }
    return false;

  }
  public boolean casserGraine(){
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

  }
  public Fourmi aNourrir(){
    Fourmi f3 = getReine();
    Fourmi f=null;
    //soit on donne a la reine.
    if (f3 != null && (!f3.equals(this))){ // si la Reine existe, n'est pas la créatures qui joue ou que la reine a déja plus de nourriture qu'il ne lui en faut.
      return f3;
    }else{//soit la reine donne au nécéciteux, plus la fourmi est proche du stade imago plus elle est prioritaire.
      //f = getAlliéSurLaCase().getGcStade(0).getPlusAffamée(); // Les adultes sont prioritaire pour recevoir de la nourriture.
      int i = -1;
      while(((f==null || f.getNourriture() > 5*f.getIndividu().getNourritureConso())) && i>-4){ //si la créature tien plus de 5 tour seule on en cherche une autre.
        f = getAlliéSurLaCase().getGcStade(i).getPlusAffamée();
        i--;
      }
    }
    return f;
  }
  public byte choixMode(){
    if (estReine() && nourriture > 30){
      return 3; //sauf si elle est morte de faim ou toute seule.
    }
    // condition pour etre nourrice à ce tour : //&& type==1 ne générai pas assez de nourrice. Le type par défaut est 3 !
    if (nourriture > 10 && estALaFere() && this.getFere().getGc().getCouvainSale() != null){
      return 3;
    }
    if (nourriture*2 > nourritureMax || (nourriture<10 && e.getGranivore())){ // si la fourmi est au moins a moitié pleine.
      return 3; // ancien choix pour mode 1.
    }
    return 0;
  }
  public void rentrer(){
    int directionDeLaFourmilière = this.getCCase().getDirection(this.getFourmiliere().getCCase());
    this.ceDeplacer(directionDeLaFourmilière);
  }
  public void salir(){
    double chanceDeMort = allea.getRand()*getSeuilDeRisqueDInfection(); // on tire le nombre min pour survivre a ce tour.
    if (getPropreté()<allea.getRand()*50){mourir(1);}
    setPropreté(getPropreté() - e.getPropretéPerdu(stade));
  }
}
