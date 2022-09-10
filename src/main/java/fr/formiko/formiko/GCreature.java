package fr.formiko.formiko;

import fr.formiko.formiko.interfaces.TourFourmiNonIa;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.exceptions.EmptyListException;
import fr.formiko.usual.exceptions.ListItemNotFoundException;
import fr.formiko.usual.exceptions.NullItemException;
import fr.formiko.usual.g;
import fr.formiko.usual.structures.listes.Liste;

import java.io.Serializable;
import java.lang.Cloneable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
*{@summary List of creatures.}
*lastEditedVersion 2.23
*@author Hydrolien
*/
public class GCreature extends Liste<Creature> implements Serializable, Cloneable {//, Iterator{
  private byte loopSafety;
  // CONSTRUCTORS ----------------------------------------------------------------
  /**
  *{@summary Main constructor.}
  *@lastEditedVersion 2.23
  */
  public GCreature(){
    super();
  }
  /**
  *{@summary Constructor that create an empty GCreature and then fill it as a Fourmiliere.}
  *@lastEditedVersion 2.23
  */
  public GCreature(int nbrDeCreature, Fourmiliere fere, Espece e, CSquare cc){
    this();
    debug.débogage("Création d'un groupe de Fourmi avec au moins 1 fourmis.");
    Fourmi reine = new Fourmi(fere,e, (byte) 0,(byte) 0);
    add(reine);
    for (int i =1 ;i < nbrDeCreature ;i++ ) {
      Fourmi f = new Fourmi(fere,e,(byte) 3,(byte) 0,reine.getPheromone());
      add(f);
    }
  }
  // GET SET ----------------------------------------------------------------------
  /**
  *{summary Return the 1a queen of the anthill}
  *,or null if there is no qeen.
  *@lastEditedVersion 2.1
  */
  public Fourmi getReine(){
    for (Creature c : this) {
      if (c instanceof Fourmi && ((Fourmi)c).estReine()){
        return ((Fourmi)c);
      }
    }
    return null;
  }
  /**
  *{summary Return all the Creature at a specific stade.}
  *@param stade the specific stade to fined
  *@lastEditedVersion 2.23
  */
  public GCreature getGcStade(int stade){
    return toGc(filter(c -> c.getStade()==stade));
  }
  /**
  *{summary Return all the Ant at a specific typeF.}
  *@param typeF the specific typeF to fined
  *@lastEditedVersion 2.23
  */
  public GCreature getGcType(int typeF){
    return toGc(filter(c -> c instanceof Fourmi && ((Fourmi)c).getTypeF()==typeF));
  }
  /**
  *{summary Return all the Creature at an other stade than 0.}
  *@lastEditedVersion 2.23
  */
  public GCreature getBrood(){ // on renvoie d'habord les plus proches de la transformation en Fourmi adulte.
    return toGc(filter(c -> c.getStade()!=0));
  }
  public Creature getBroodSaleE() throws EmptyListException {
    if(isEmpty()){throw new EmptyListException("GCreature","trouver la créature sale du brood");}
    return filter(c -> c.getStade()!=0 && c.getHealth()<90).getFirst();
  }
  public Creature getBroodSale(){
    try {
      return getBroodSaleE();
    }catch (EmptyListException e){return null;}
  }
  public GCreature getBroodsSale(){
    return toGc(filter(c -> c.getStade()!=0 && c.getHealth()<90));
  }
  // a add :
  // public GCreature getGcSiMemeFere(Fourmiliere fere){}
  /**
  *{@summary return the Creature that have this id.}<br>
  *@param id the id of the creature
  *@lastEditedVersion 2.1
  */
  private Creature getCreatureByIdE(int id) throws EmptyListException {
    if(isEmpty()){ throw new EmptyListException("GCreature","trouver la créature "+id);}
    for (Creature c : this ) {
      if(c.getId()==id){return c;}
    }
    return null;
  }
  /**
  *{@summary return the Creature that have this id.}<br>
  *@param id the id of the creature
  *@lastEditedVersion 2.1
  */
  public Creature getCreatureById(int id){
    try {
      return getCreatureByIdE(id);
    }catch (EmptyListException e) {return null;}
  }
  /**
  *{@summary return the Fourmi that have this id.}<br>
  *If the creature isn't an Ant it return null &#38; print an error.
  *@param id the id of the Fourmi.
  *@lastEditedVersion 2.1
  */
  public Fourmi getFourmiById(int id){
    Creature c = getCreatureById(id);
    if(c instanceof Fourmi){
      return (Fourmi)c;
    }else{
      erreur.erreur("La creature selectionné n'as pas put etre transformer en fourmi.");
      return null;
    }
  }
  private Fourmi getFourmiParFereE(Fourmiliere fere) throws EmptyListException {
    if(getHead()==null){throw new EmptyListException("GCreature","trouver la créature par fere");}
    if(fere==null){throw new NullPointerException();}
    Liste<Fourmi> lc = new Liste<Fourmi>();
    Comparator<Fourmi> idComparator = (Fourmi p1, Fourmi p2) -> (int)(p2.getId() - p1.getId());
    for (Creature c : this) {
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
    }catch (EmptyListException | NullPointerException e) {
      return null;
    }
  }
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Return a list with all this class functions.}
  *@lastEditedVersion 2.23
  */
  private static GCreature toGc(Liste<Creature> l){
    GCreature gc = new GCreature();
    gc.setHead(l.getHead());
    gc.setTail(l.getTail());
    return gc;
  }
  /**
  *{@summary Return the Creatures that are ally with c.}<br>
  *@param cTested the tested creature
  *@param differenceTolerated the Pheromone difference that is tolerated
  *@lastEditedVersion 2.23
  */
  private GCreature filterAlliés(Creature cTested, int differenceTolerated){
    return toGc(filter(c -> cTested.getPheromone().equals(c.getPheromone(),differenceTolerated)));
  }
  /**
  *{@summary return the Creatures that are ally with c.}<br>
  *@param c the tested creature.
  *@lastEditedVersion 2.23
  */
  public GCreature filterAlliés(Creature c){
    int x=0; if(c.getEspece()!=null && c.getEspece().getPolycalique()){x=5;}// en théorie 4 suffisent.
    return filterAlliés(c,c.getPheromoneTolerence());
  }
  /**
  *{@summary delete Creature that can't eat more.}<br>
  *@lastEditedVersion 2.23
  */
  public GCreature filterFaimMax(){
    return toGc(filter(c -> c.getFood()<c.getMaxFood()));
  }
  /**
  *{@summary delete Creature that can't be cleaner.}<br>
  *@lastEditedVersion 2.23
  */
  public GCreature filterHealthMax(){
    return toGc(filter(c -> c.getHealth()<c.getMaxHealth()));
  }
  /**
  *{@summary delete Creature that didn't whant food.}<br>
  *@lastEditedVersion 2.23
  */
  public GCreature filterWantFood(){
    return toGc(filter(c -> c.wantFood()));
  }
  /**
  *{@summary delete Creature that didn't whant clean.}<br>
  *@lastEditedVersion 2.23
  */
  public GCreature filterWantClean(){
    return toGc(filter(c -> c.wantClean()));
  }
  public void setLienFere(Fourmiliere fere){
    GCreature gcr = new GCreature();
    for (Creature c : this) {
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
  *@lastEditedVersion 2.23
  */
  public int getNbrOuvriere(){
    return filter(c -> c.getStade()==0 && (((Fourmi)c).getTypeF()==3 || ((Fourmi)c).getTypeF()==4 || ((Fourmi)c).getTypeF()==5)).length();
  }
  /**
  *{@summary Return species of this GCreature as if it was the gc of an anthill.}
  *@lastEditedVersion 2.23
  */
  public Espece getEspece(){
    Fourmi c = this.getReine();
    if(c!=null){return c.getEspece();}
    if(getFirst()!=null){return ((Fourmi)getFirst()).getEspece();}
    return null;
  }
  /**
  *{@summary Return an array of id.}
  *@lastEditedVersion 2.23
  */
  public int [] gcToTInt(){
    int lentr=length();
    int tr[]=new int [lentr];int k=0;
    for (Creature c : this) {
      tr[k]=c.getId();k++;
    }
    return tr;
  }
  public GCreature copier(){
    GCreature gc=new GCreature();
    gc.add(this);
    return gc;
  }
  /**
  *{@summary Return all Insecte from this.}
  *@lastEditedVersion 2.23
  */
  public GInsecte getGi(){
    GInsecte gi = new GInsecte();
    for (Creature c : this) {
      if(c instanceof Insecte){gi.add((Insecte)c);}
    }
    return gi;
  }
  /**
  *{@summary Play as an ant.}
  *If antIdToPlay have been set, we will play this ant first.
  *@lastEditedVersion 2.5
  */
  private void jouerE() throws EmptyListException{
    if(isEmpty()){
      throw new EmptyListException("GCreature","jouer");
    }else{
      for (Creature c : this) {
        if((Main.getPartie()!=null && !Main.getPartie().getContinuerLeJeu()) || Main.getRetournerAuMenu()){return;}
        if(c instanceof Fourmi){
          Fourmi fActuel = (Fourmi)c;
          if(fActuel.getJoueur().getIsTurnEnded()){
            fActuel.setActionTo0();
            continue;
          }
          if(Main.getPartie().getAntIdToPlay()!=-1 && !fActuel.getIa()){
            // erreur.info("test de "+Main.getPartie().getAntIdToPlay()+" & "+c.getId()+" sur les "+length()+" fourmis ("+toStringId()+")");
            //if player have clic on this ant.
            if(Main.getPartie().getAntIdToPlay()==c.getId()){
              Main.getPartie().setAntIdToPlay(-1);
              loopSafety=0;
              if(fActuel.getAction()>0){
                if(!fActuel.isAI() && Main.getOp().getFollowAntAtStartTurn()){
                  Main.getView().centerOverSquare(fActuel.getCSquare().getContent());
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
                Main.getView().centerOverSquare(fActuel.getCSquare().getContent());
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
    if(isEmpty()){
      throw new EmptyListException("GCreature","preTurn");
    }else{
      for (Creature c : this) {
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
  *{@summary Update Black and cloud Square.}
  *@lastEditedVersion 2.1
  */
  public void updateSquareSN(){
    for (Creature c : this ) {
      if(c instanceof Fourmi){
        Fourmi f = (Fourmi)(c);
        Joueur j = f.getJoueur();
        for (Square ca : f.getCSquare().getGca(1)) {
          int x = ca.getX(); int y = ca.getY();
          j.setSquareSombre(x,y,false);
          j.setSquareNuageuse(x,y,false);
        }
      }
    }
  }
  /**
  *{@summary Sort this depending of health.}
  *@lastEditedVersion 2.23
  */
  public void classerPourNetoyage(Fourmi f){
    sort((Creature c1, Creature c2) -> c2.getHealth()-c1.getHealth());
    // pour l'instant on tri juste en fct de health
    //TODO
    //le but est d'habord que personne ne passe en dessous des x de health minimum lié au niveau de difficulté.
    //on met tout ceux qui sont en dessous de 50 dans l'ordre d'age/stade.
    //et on ajoute ceux qui sont après dans l'ordre de saleté.
  }
  public int [] toTId(){
    int tr []= new int[this.length()];
    int k=0;
    for (Creature c : this) {
      tr[k]=c.getId();k++;
    }
    return tr;
  }
  /**
  *{@summary Return true if all Creature have played to there last action.}
  *Action can be under 0.
  *@lastEditedVersion 2.1
  */
  public boolean haveDoneAllActionAviable(){
    for (Creature c : this) {
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
    for (Creature c : this) {
      if(c instanceof Fourmi && !((Fourmi)c).isAutoMode() && ((Fourmi)c).getStade()==0){return false;}
    }
    return true;
  }
  /**
  *{@summary Return true if all ant are in autoMode.}
  *@lastEditedVersion 2.5
  */
  public boolean isAllInAutoModeOrHaveDoneAllAction(){
    for (Creature c : this) {
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
    for (Creature c : this) {
      if(c.getAction()>0){
        c.setAction(0);
        c.tour();//force to do finTour() without any action ant will do nothing.
      }
    }
    return true;
  }
}
