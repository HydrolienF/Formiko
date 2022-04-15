package fr.formiko.formiko;

import fr.formiko.formiko.interfaces.TourFourmiNonIa;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.exceptions.EmptyListException;
import fr.formiko.usuel.exceptions.ListItemNotFoundException;
import fr.formiko.usuel.exceptions.NullItemException;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.Liste;

import java.util.List;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

// extends Liste<Creature>
public class GCreature implements Serializable{//, Iterator{
  protected CCreature début;
  protected CCreature fin;
  //TODO #82 replace début & fin by a protected Liste<Creature>.
  private byte loopSafety;
  // CONSTRUCTORS ----------------------------------------------------------------
  public GCreature(CCreature cc){
    début = cc; fin = cc;
  }
  public GCreature(Creature c){
    this(new CCreature(c));
  }
  public GCreature(){
    this((CCreature) null);
  }
  public GCreature(List<Creature> list){
    this();
    for (Creature c : list) {
      add(c);
    }
  }

  public GCreature(int nbrDeCreature, Fourmiliere fere, Espece e, CCase cc){
    this();
    debug.débogage("Création d'un groupe de Fourmi avec au moins 1 fourmis.");
    Fourmi reine = new Fourmi(fere,e, (byte) 0,(byte) 0);
    //reine.setCCase(cc);
    addFin(reine);
    for (int i =1 ;i < nbrDeCreature ;i++ ) {
      Fourmi f = new Fourmi(fere,e,(byte) 3,(byte) 0,reine.getPheromone());
      //f.setCCase(cc);
      addFin(f);
    }
  }
  // GET SET ----------------------------------------------------------------------
  public CCreature getHead(){return début;}
  public CCreature getTail(){return fin;}
  public Creature getFirst(){return getHead().getContent();}
  public void setDébut(CCreature cc){début = cc;}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    if (début==null){ return "";}
    return début.toString();
  }
  public int length(){
    if (début==null){
      return 0;
    }else if(début.getContent().equals(fin.getContent())){
      return 1;
    }else {
      return début.length();
    }
  }
  /**
  *{summary Return the 1a queen of the anthill}
  *,or null if there is no qeen.
  *@lastEditedVersion 2.1
  */
  public Fourmi getReine(){
    for (Creature c : toList()) {
      if (c instanceof Fourmi && ((Fourmi)c).estReine()){
        return ((Fourmi)c);
      }
    }
    return null;
  }
  /**
  *{summary Return all the Creature at a specific stade.}
  *@param stade the specific stade to fined
  *@lastEditedVersion 2.10
  */
  public GCreature getGcStade(int stade){
    return new GCreature(toList().filter(c -> c.getStade()==stade));
  }
  /**
  *{summary Return all the Ant at a specific typeF.}
  *@param typeF the specific typeF to fined
  *@lastEditedVersion 2.10
  */
  public GCreature getGcType(int typeF){
    return new GCreature(toList().filter(c -> c instanceof Fourmi && ((Fourmi)c).getTypeF()==typeF));
  }
  /**
  *{summary Return all the Creature at an other stade than 0.}
  *@lastEditedVersion 2.10
  */
  public GCreature getCouvain(){ // on renvoie d'habord les plus proches de la transformation en Fourmi adulte.
    return new GCreature(toList().filter(c -> c.getStade()!=0));
  }
  public Creature getCouvainSaleE()throws EmptyListException{
    if (début==null){ throw new EmptyListException("GCreature","trouver la créature sale du couvain");}
    return début.getCouvainSale();
  }
  public Creature getCouvainSale(){
    try {
      return getCouvainSaleE();
    }catch (EmptyListException e){return null;}
  }
  public GCreature getCouvainsSale(){
    GCreature gcr = getCouvain();
    // on garde le premier sale :
    while (gcr.getHead() != null){
      Fourmi fTest = (Fourmi) gcr.getHead().getContent();
      if (fTest.getHealth() < 90) {
        gcr.remove(gcr.getHead().getContent());
      }else{
        break;
      }
    }if (gcr.getHead() == null){ return new GCreature();}
    gcr.getHead().getCouvainsSale(); // on filter les propre dans la suite de la liste.
    return gcr;
  }
  // a add :
  // public GCreature getGcSiMemeFere(Fourmiliere fere){}
  /**
  *{@summary return the Creature that have this id.}<br>
  *@param id the id of the creature
  *@lastEditedVersion 2.1
  */
  private Creature getCreatureParIdE(int id) throws EmptyListException {
    if (début==null){ throw new EmptyListException("GCreature","trouver la créature "+id);}
    for (Creature c : toList() ) {
      if(c.getId()==id){return c;}
    }
    return null;
  }
  /**
  *{@summary return the Creature that have this id.}<br>
  *@param id the id of the creature
  *@lastEditedVersion 2.1
  */
  public Creature getCreatureParId(int id){
    try {
      return getCreatureParIdE(id);
    }catch (EmptyListException e) {return null;}
  }
  /**
  *{@summary return the Fourmi that have this id.}<br>
  *If the creature isn't an Ant it return null &#38; print an error.
  *@param id the id of the Fourmi.
  *@lastEditedVersion 2.1
  */
  public Fourmi getFourmiParId(int id){
    Creature c = getCreatureParId(id);
    if(c instanceof Fourmi){
      return (Fourmi)c;
    }else{
      erreur.erreur("La creature selectionné n'as pas put etre transformer en fourmi.");
      return null;
    }
  }
  private Fourmi getFourmiParFereE(Fourmiliere fere)throws EmptyListException{
    if (getHead()==null){ throw new EmptyListException("GCreature","trouver la créature par fere");}
    if(fere==null){throw new NullPointerException();}
    Liste<Fourmi> lc = new Liste<Fourmi>();
    Comparator<Fourmi> idComparator = (Fourmi p1, Fourmi p2) -> (int)(p2.getId() - p1.getId());
    for (Creature c : toList()) {
      if(c instanceof Fourmi){ //an Ant
        if(((Fourmi)c).getFere().equals(fere)){ //Of the same fere
          lc.addSorted((Fourmi)c, idComparator);
        }
      }
    }
    //if playing ant is not from this fere.
    if(Main.getPlayingAnt()==null || !Main.getPlayingAnt().getFere().equals(fere)){return lc.getFirst();}
    //else
    int idPlayingAnt=Main.getPlayingAnt().getId();
    boolean next=false;
    for (Fourmi f : lc) { //if playing ant is in this (the GCreature) return next 1.
      if(next){return f;}
      if(f.getId()==idPlayingAnt){next=true;}
    }
    //else return the 1a.
    return lc.getFirst();
  }
  public Fourmi getFourmiParFere(Fourmiliere fere){
    try {
      return getFourmiParFereE(fere);
    }catch (Exception e) {
      return null;
    }
  }
  /**
  *{@summary Return the Creatures that are ally with c.}<br>
  *@param cTested the tested creature
  *@param differenceTolerated the Pheromone difference that is tolerated
  *@lastEditedVersion 2.10
  */
  private GCreature filterAlliés(Creature cTested, int differenceTolerated){
    return new GCreature(toList().filter(c -> cTested.getPheromone().equals(c.getPheromone(),differenceTolerated)));
  }
  /**
  *{@summary return the Creatures that are ally with c.}<br>
  *@param c the tested creature.
  *@lastEditedVersion 2.10
  */
  public GCreature filterAlliés(Creature c){
    int x=0; if(c.getEspece()!=null && c.getEspece().getPolycalique()){x=5;}// en théorie 4 suffisent.
    return filterAlliés(c,c.getPheromoneTolerence());
  }
  /**
  *{@summary delete Creature that can't eat more.}<br>
  *@lastEditedVersion 2.10
  */
  public GCreature filterFaimMax(){
    return new GCreature(toList().filter(c -> c.getFood()<c.getMaxFood()));
  }
  /**
  *{@summary delete Creature that can't be cleaner.}<br>
  *@lastEditedVersion 2.10
  */
  public GCreature filterHealthMax(){
    return new GCreature(toList().filter(c -> c.getHealth()<c.getMaxHealth()));
  }
  /**
  *{@summary delete Creature that didn't whant food.}<br>
  *@lastEditedVersion 2.10
  */
  public GCreature filterWantFood(){
    return new GCreature(toList().filter(c -> c.wantFood()));
  }
  /**
  *{@summary delete Creature that didn't whant clean.}<br>
  *@lastEditedVersion 2.1
  */
  public GCreature filterWantClean(){
    return new GCreature(toList().filter(c -> c.wantClean()));
  }
  public void setLienFere(Fourmiliere fere){
    GCreature gcr = new GCreature();
    for (Creature c : toList()) {
      if(c instanceof Fourmi){
        Fourmi fTest = (Fourmi)(c);
        fTest.setFere(fere);
      }
    }
  }
  public int getNbrGcStade(int x){ return getGcStade(x).length();}
  public int getNbrImago(){ return getNbrGcStade(0);}
  public int getNbrReine(){ return getGcStade(0).getGcType(0).length();}
  /**
  *{@summary Count worker imago.}
  *Worker imago are at stade 0 and type 3, 4 or 5.
  *@lastEditedVersion 2.1
  */
  public int getNbrOuvriere(){
    try {
      return getGcStade(0).getGcType(3).length() + getGcStade(0).getGcType(4).length() + getGcStade(0).getGcType(5).length();
    }catch (Exception e) {
      erreur.erreur("Impossible de prende en compte les type major et minor dans les ouvrières.");
      return getGcStade(0).getGcType(3).length();
    }
  }
  public Espece getEspece(){
    Fourmi c = this.getReine();
    if(c!=null){return c.getEspece();}
    if(début!=null){return ((Fourmi)début.getContent()).getEspece();}
    return null;
  }

  public int [] gcToTInt(){
    if (début==null){ return null;}
    return début.gcToTInt();
  }
  public GCreature copier(){
    if(début==null){ return new GCreature();}
    return début.copier();
  }
  public void actualiserFin(){
    CCreature cc = début;
    while(cc!=null){
      cc=cc.getSuivant();
    }
    fin = cc;
  }
  public GInsecte getGi(){
    GInsecte gi = new GInsecte();
    CCreature cc = début;
    while(cc != null){
      Creature c = cc.getContent();
      if(c instanceof Insecte){gi.add((Insecte) cc.getContent());}
      cc = cc.getSuivant();
    }
    return gi;
  }
  public void add(Creature c){
    addFin(c);
  }
  public void addFin(Creature c){
    if(c==null){throw new NullItemException();}
    CCreature cc = new CCreature(c);
    if (fin ==  null){
      début = cc;
      fin = cc;
    }else {
      fin.setSuivant(cc);
      cc.setPrécédent(fin);
      fin = cc;
    }
  }
  public void add(GCreature gc){
    if(gc == null || gc.getHead() == null){ return;}
    if (fin == null){
      début = gc.getHead();
      fin = gc.getTail();
    }else {
      fin.setSuivant(gc.getHead());
      gc.getHead().setPrécédent(fin);
      fin = gc.getTail();
    }
  }
  public void add(GInsecte gi ){
    GCreature gc = gi.toGCreature();
    add(gc);
  }
  /**
  *{@summary remove an item from the list.} <br>
  *if list is empty or element fail to be remove an Exception will be throw.<br>
  *@lastEditedVersion 1.31
  */
  public void remove(Creature c) {
    if(c==null){ throw new NullItemException();}
    if(début == null){ throw new EmptyListException("GCreature","remove la Creature "+c.getId());}//erreur.erreur("Aucune créature n'as pu être remove car GCreature est vide","GCreature.remove",true); return;}
    if(début.getContent().equals(c)){
      if(fin.getContent().equals(c)){
        début = null; fin = null; // on retire la seule créature
      }else{
        début = début.getSuivant(); // on retire la 1a créature.
      }
      return;
    }
    début.remove(c);
    fin = début;
    while(fin.getSuivant() != null){
      fin = fin.getSuivant();
    }
    // actualiserFin();
  }
  // public void remove(Creature c){remove(c);}
  public void delete(Creature c){remove(c);}
  public void afficheToiE() throws EmptyListException{
    if(début==null){
      throw new EmptyListException("GCreature","tout afficher");
    }else{
      début.afficheTout();
    }
  }
  public void afficheToi(){
    try {
      afficheToiE();
    }catch (EmptyListException e) {}
  }
  public void afficheToiRéduitE() throws EmptyListException{
    if(début==null){
      throw new EmptyListException("GCreature","tout afficher");
    }else{
      System.out.print(g.get("listeCreature")+" : ");
      début.afficheToutRéduit();
    }
  }
  public void afficheToiRéduit(){
    try {
      afficheToiRéduitE();
    }catch (EmptyListException e) {}
  }
  /**
  *{@summary Play as an ant.}
  *If antIdToPlay have been set, we will play this ant first.
  *@lastEditedVersion 2.5
  */
  private void jouerE() throws EmptyListException{
    if(début == null){
      throw new EmptyListException("GCreature","jouer");
    }else{
      for (Creature c : toList()) {
        if((Main.getPartie()!=null && !Main.getPartie().getContinuerLeJeu()) || Main.getRetournerAuMenu()){return;}
        if(c instanceof Fourmi){
          Fourmi fActuel = (Fourmi)c;
          if(fActuel.getJoueur().getIsTurnEnded()){
            fActuel.setActionTo0();
            continue;
          }
          if(Main.getPartie().getAntIdToPlay()!=-1 && !fActuel.getIa()){
            // erreur.info("test de "+Main.getPartie().getAntIdToPlay()+" & "+c.getId()+" sur les "+length()+" fourmis ("+toList().toStringId()+")");
            //if player have clic on this ant.
            if(Main.getPartie().getAntIdToPlay()==c.getId()){
              Main.getPartie().setAntIdToPlay(-1);
              loopSafety=0;
              if(fActuel.getAction()>0){
                if(!fActuel.isAI() && Main.getOp().getFollowAntAtStartTurn()){
                  Main.getView().centerOverCase(fActuel.getCCase().getContent());
                }
                fActuel.tour();
              }else{
                ((TourFourmiNonIa)fActuel.tour).allowToDisableAutoMode();
              }
            }else{
              loopSafety++;
              if(loopSafety<0){
                erreur.erreur("Fail to select ant by id 128 times","A random ant have been chosen");
                Main.getPartie().setAntIdToPlay(-1);
                loopSafety=0;
              }
            }
            //if player have clic on an other ant, go to next loop turn.
          }else{ //if player have press Enter or end an other Ant turn.
            if(fActuel.getAction()>0){
              if(!fActuel.isAI() && Main.getOp().getFollowAntAtStartTurn()){
                Main.getView().centerOverCase(fActuel.getCCase().getContent());
              }
              fActuel.tour();
            }
          }
        }else{
          erreur.erreur("Impossible de faire jouer comme une fourmi la créature "+c.getId()+" qui n'en est pas une.");
        }
      }
    }
  }
  /**
  *Play as an ant.
  *@lastEditedVersion 1.33
  */
  public void jouer(){
    try{
      jouerE();
    }catch (EmptyListException e) {erreur.alerte("1 player can't play");}
  }
  /**
  *reset action before the turn of all the ant.
  *@lastEditedVersion 2.1
  */
  private void preTurnE() throws EmptyListException{
    if(début == null){
      throw new EmptyListException("GCreature","preTurn");
    }else{
      for (Creature c : toList() ) {
        if(c instanceof Fourmi){
          Fourmi fActuel = (Fourmi)c;
          fActuel.preTurn();
        }else{
          erreur.erreur("Impossible de faire preTurn comme une fourmi la créature "+c.getId()+" qui n'en est pas une.");
        }
      }
    }
  }
  /**
  *{@summary reset action before the turn of all the ant.}
  *@lastEditedVersion 1.30
  */
  public void preTurn(){
    try{
      preTurnE();
    }catch (EmptyListException e) {}
  }
  /**
  *{@summary Update Black and cloud Case.}
  *@lastEditedVersion 2.1
  */
  public void updateCaseSN(){
    for (Creature c : toList() ) {
      if(c instanceof Fourmi){
        Fourmi f = (Fourmi)(c);
        Joueur j = f.getJoueur();
        GCase gc = f.getCCase().getGca(1); //ensemble des case vue par la créature.
        CCase cca = gc.getHead();
        while(cca!=null){
          int x = cca.getContent().getX(); int y = cca.getContent().getY();
          j.setCaseSombre(x,y,false);
          j.setCaseNuageuse(x,y,false);
          cca=cca.getDroite();
        }
      }
    }
  }

  public void classerPourNetoyage(){
    if (début==null){ return;}
    //TODO
    //le but est d'habord que personne ne passe en dessous des x de healthe minimum lié au niveau de difficulté.
    //on met tout ce qui sont en dessous de 50 dans l'ordre d'age/stade.
    //et on ajoute ceux qui sont après dans l'ordre de saleté.
  }
  public int [] toTId(){
    if (début==null){ return new int[0];}
    return début.toTId();
  }
  /**
  *{@summary Transform a GCreature in Liste&lt;Creature&gt;.}
  *@lastEditedVersion 1.38
  */
  public Liste<Creature> toList(){
    if (début==null){
      Liste<Creature> lc = new Liste<Creature>();
      return lc;
    }
    return début.toList();
  }
  /**
  *{@summary Return true if all Creature have played to there last action.}
  *Action can be under 0.
  *@lastEditedVersion 2.1
  */
  public boolean haveDoneAllActionAviable(){
    for (Creature c : toList()) {
      if(c.getAction()>0){return false;}
      // TODO #410 if(c.getAction()>0 && (!(c instanceof Fourmi) || ((Fourmi)c).isAutoMode())){return false;}
    }
    return true;
  }
  /**
  *{@summary Return true if all ant are in autoMode.}
  *@lastEditedVersion 2.5
  */
  public boolean isAllInAutoMode(){
    for (Creature c : toList()) {
      if(c instanceof Fourmi && !((Fourmi)c).isAutoMode() && ((Fourmi)c).getStade()==0){return false;}
    }
    return true;
  }
  /**
  *{@summary Return true if all ant are in autoMode.}
  *@lastEditedVersion 2.5
  */
  public boolean isAllInAutoModeOrHaveDoneAllAction(){
    for (Creature c : toList()) {
      if(c instanceof Fourmi && !((Fourmi)c).isAutoMode() && c.getAction()>0){return false;}
    }
    return true;
  }

  /**
  *{@summary Force all the GCreature Creature to end there turn.}<br>
  *Ant that still haven't end there turn will have action set to 0 &#38; tour to update age, cleaning etc.
  *@lastEditedVersion 1.x
  */
  public boolean setAction0AndEndTurn(){
    for (Creature c : toList()) {
      if(c.getAction()>0){
        c.setAction(0);
        c.tour();//force to do finTour() without any action ant will do nothing.
      }
    }
    return true;
  }
}
