package fr.formiko.formiko;

import fr.formiko.formiko.interfaces.TourFourmiNonIa;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.exceptions.EmptyListException;
import fr.formiko.usuel.exceptions.ListItemNotFoundException;
import fr.formiko.usuel.exceptions.NullItemException;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.Liste;

import java.io.Serializable;
import java.util.Iterator;
// extends Liste<Creature>
public class GCreature implements Serializable{//, Iterator{
  protected CCreature début;
  protected CCreature fin;
  //TODO #82 replace début & fin by a protected Liste<Creature>.
  private byte loopSafety;
  // CONSTRUCTEUR -----------------------------------------------------------------
  public GCreature(CCreature cc){
    début = cc; fin = cc;
  }
  public GCreature(Creature c){
    this(new CCreature(c));
  }
  public GCreature(){
    this((CCreature) null);
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
  // GET SET -----------------------------------------------------------------------
  public CCreature getHead(){return début;}
  public CCreature getTail(){return fin;}
  public Creature getFirst(){return getHead().getContent();}
  public void setDébut(CCreature cc){début = cc;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    if (début==null){ return "";}
    return début.toString();
  }public String gcToString(){return gcToString();}
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
  *@version 2.1
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
  *@param stade The specific stade to fined.
  *@version 2.1
  */
  public GCreature getGcStade(int stade){
    GCreature gcr = new GCreature();
    for (Creature c : toList()) {
      if(c.getStade() == stade){
        gcr.add(c);
      }
    }
    return gcr;
  }
  public GCreature getGcType(int typeF){
    GCreature gcr = new GCreature();
    for (Creature c : toList()) {
      if (c instanceof Fourmi && ((Fourmi)c).getTypeF()==typeF){
        gcr.add((Fourmi)c);
      }
    }
    return gcr;
  }
  public GCreature getCouvain(){ // on renvoie d'habord les plus proches de la transformation en Fourmi adulte.
    GCreature gcr = new GCreature();
    for (Creature c : toList()) {
      if(c.getStade() != 0){
        gcr.add(c);
      }
    }
    return gcr;
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
      if (fTest.getPropreté() < 90) {
        gcr.remove(gcr.getHead().getContent());
      }else{
        break;
      }
    }if (gcr.getHead() == null){ return new GCreature();}
    gcr.getHead().getCouvainsSale(); // on filtre les propre dans la suite de la liste.
    return gcr;
  }
  // a add :
  // public GCreature getGcSiMemeFere(Fourmiliere fere){}
  /**
  *{@summary return the Creature that have this id.}<br>
  *@param id the id of the creature
  *@version 2.1
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
  *@version 2.1
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
  *@version 2.1
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
  private Fourmi getFourmiParFereE(Fourmiliere f)throws EmptyListException{
    if (début==null){ throw new EmptyListException("GCreature","trouver la créature par fere");}
    return début.getFourmiParFere(f);
  }
  public Fourmi getFourmiParFere(Fourmiliere f){
    try {
      return getFourmiParFereE(f);
    }catch (Exception e) {
      return null;
    }
  }
  /**
  *{@summary return the Creatures that are ally with c.}<br>
  *@param c the tested creature.
  *@param
  *@version 2.1
  */
  private GCreature filtreAlliés(Creature c2, int differenceTolerated){
    GCreature gcr = new GCreature();
    for (Creature c : toList()) {
      if (c2.getPheromone().equals(c.getPheromone(),differenceTolerated)){
        gcr.add(c);
      }
    }
    return gcr;
  }
  /**
  *{@summary return the Creatures that are ally with c.}<br>
  *@param c the tested creature.
  *@version 2.1
  */
  public GCreature filtreAlliés(Creature c){
    int x=0; if(c.getEspece()!=null && c.getEspece().getPolycalique()){x=5;}// en théorie 4 suffisent.
    return filtreAlliés(c,x);
  }
  /**
  *{@summary delete Creature that can't eat more.}<br>
  *@version 2.1
  */
  public GCreature filtreFaimMax(){
    GCreature gcr = new GCreature();
    for (Creature c : toList()) {
      if (c.getNourriture()<c.getNourritureMax()){
        gcr.add(c);
      }
    }
    return gcr;
  }
  /**
  *{@summary delete Creature that can't be cleaner.}<br>
  *@version 2.1
  */
  public GCreature filtrePropreteMax(){
    GCreature gcr = new GCreature();
    for (Creature c : toList()) {
      if (c.getProprete()<100){
        gcr.add(c);
      }
    }
    return gcr;
  }
  /**
  *{@summary delete Creature that didn't whant food.}<br>
  *@version 2.1
  */
  public GCreature filtreWantFood(){
    GCreature gcr = new GCreature();
    for (Creature c : toList()) {
      if (c.wantFood()){
        gcr.add(c);
      }
    }
    return gcr;
  }
  /**
  *{@summary delete Creature that didn't whant clean.}<br>
  *@version 2.1
  */
  public GCreature filtreWantClean(){
    GCreature gcr = new GCreature();
    for (Creature c : toList()) {
      if (c.wantClean()){
        gcr.add(c);
      }
    }
    return gcr;
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
  *@version 2.1
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
  *@version 1.31
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
  *@version 2.5
  */
  private void jouerE() throws EmptyListException{
    if(début == null){
      throw new EmptyListException("GCreature","jouer");
    }else{
      for (Creature c : toList()) {
        if((Main.getPartie()!=null && !Main.getPartie().getContinuerLeJeu()) || Main.getRetournerAuMenu()){return;}
        if(c instanceof Fourmi){
          Fourmi fActuel = (Fourmi)c;
          if(Main.getPartie().getAntIdToPlay()!=-1){
            // erreur.info("test de "+Main.getPartie().getAntIdToPlay()+" & "+c.getId()+" sur les "+length()+" fourmis ("+toList().toStringId()+")");
            //if player have clic on this ant.
            if(Main.getPartie().getAntIdToPlay()==c.getId()){
              Main.getPartie().setAntIdToPlay(-1);
              loopSafety=0;
              if(fActuel.getAction()>0){
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
  *@version 1.33
  */
  public void jouer(){
    try{
      jouerE();
    }catch (EmptyListException e) {erreur.alerte("1 player can't play");}
  }
  /**
  *reset action before the turn of all the ant.
  *@version 2.1
  */
  private void preTourE() throws EmptyListException{
    if(début == null){
      throw new EmptyListException("GCreature","preTour");
    }else{
      for (Creature c : toList() ) {
        if(c instanceof Fourmi){
          Fourmi fActuel = (Fourmi)c;
          fActuel.preTour();
        }else{
          erreur.erreur("Impossible de faire preTour comme une fourmi la créature "+c.getId()+" qui n'en est pas une.");
        }
      }
    }
  }
  /**
  *{@summary reset action before the turn of all the ant.}
  *@version 1.30
  */
  public void preTour(){
    try{
      preTourE();
    }catch (EmptyListException e) {}
  }
  /**
  *{@summary Update Black and cloud Case.}
  *@version 2.1
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
    //le but est d'habord que personne ne passe en dessous des x de propretée minimum lié au niveau de difficulté.
    //on met tout ce qui sont en dessous de 50 dans l'ordre d'age/stade.
    //et on ajoute ceux qui sont après dans l'ordre de saleté.
  }
  public int [] toTId(){
    if (début==null){ return new int[0];}
    return début.toTId();
  }
  /**
  *{@summary Transform a GCreature in Liste&lt;Creature&gt;.}
  *@version 1.38
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
  *@version 2.1
  */
  public boolean haveDoneAllActionAviable(){
    for (Creature c : toList()) {
      if(c.getAction()>0){return false;}
    }
    return true;
  }
  /**
  *{@summary Return true if all ant are in autoMode.}
  *@version 2.5
  */
  public boolean isAllInAutoMode(){
    for (Creature c : toList()) {
      if(c instanceof Fourmi && !((Fourmi)c).isAutoMode()){return false;}
    }
    return true;
  }

  /**
  *{@summary Force all the GCreature Creature to end there turn.}<br>
  *Ant that still haven't end there turn will have action set to 0 &#38; tour to update age, cleaning etc.
  *@version 1.x
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
