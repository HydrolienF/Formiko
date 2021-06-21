package fr.formiko.formiko;

import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.exceptions.EmptyListException;
import fr.formiko.usuel.exceptions.ListItemNotFoundException;
import fr.formiko.usuel.exceptions.NullItemException;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.List;

import java.io.Serializable;
import java.util.Iterator;

public class GCreature implements Serializable{//, Iterator{
  protected CCreature début;
  protected CCreature fin;
  //TODO #82 replace début & fin by a protected List<Creature>.
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
  public CCreature gethead(){return début;}
  public CCreature getTail(){return fin;}
  public void setDébut(CCreature cc){début = cc;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    if (début==null){ return "";}
    return début.toString();
  }public String gcToString(){return gcToString();}
  public int length(){
    if (début==null){
      return 0;
    }else if(début.getContenu().equals(fin.getContenu())){
      return 1;
    }else {
      return début.length();
    }
  }
  public Fourmi getReine(){
    if (début==null){return null;}
    if (gethead().getContenu() instanceof Fourmi){
      Fourmi f1 = (Fourmi) gethead().getContenu();
      if (f1.estReine()){//si c'est la reine
        return f1;
      }
    }
    return début.getReine();
  }
  public Fourmi getPlusAffamée(){
    if (début==null){return null;}
    return début.getPlusAffamée();
  }
  public GCreature getGcStade(int x){
    if (début==null){return new GCreature();}
    return début.getGcStade(x);
  }
  public GCreature getGcType(int x){
    if (début==null){return new GCreature();}
    return début.getGcType(x);
  }
  public GCreature getCouvain(){ // on renvoie d'habord les plus proches de la transformation en Fourmi adulte.
    GCreature gcr = getGcStade(-1);
    gcr.add(getGcStade(-2));
    gcr.add(getGcStade(-3));
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
    while (gcr.gethead() != null){
      Fourmi fTest = (Fourmi) gcr.gethead().getContenu();
      if (fTest.getPropreté() < 90) {
        gcr.retirer(gcr.gethead().getContenu());
      }else{
        break;
      }
    }if (gcr.gethead() == null){ return new GCreature();}
    gcr.gethead().getCouvainsSale(); // on filtre les propre dans la suite de la liste.
    return gcr;
  }
  // a add :
  // public GCreature getGcSiMemeFere(Fourmiliere fere){}
  private Creature getCreatureParIdE(int id)throws EmptyListException{
    if (début==null){ throw new EmptyListException("GCreature","trouver la créature "+id);}
    if (début.getContenu().getId()==id){
      return début.getContenu();
    }else {
      return début.getCreatureParId(id);
    }
  }
  public Creature getCreatureParId(int id){
    try {
      return getCreatureParIdE(id);
    }catch (EmptyListException e) {return null;}
  }
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
  private GCreature filtreAlliés(Creature c, int différenceTolléré){
    if (début==null || c==null){ return new GCreature();}
    return début.filtreAlliés(c,différenceTolléré);
  }
  public GCreature filtreAlliés(Creature c){
    int x=0; if(c.getEspece()!=null && c.getEspece().getPolycalique()){x=5;}// en théorie 4 suffisent.
    return filtreAlliés(c,x);
  }
  /**
  *{@summary delete Creature that can't eat more.}<br>
  *@version 1.29
  */
  public GCreature filtreFaimMax(){
    if (début==null){ return new GCreature();}
    return début.filtreFaimMax();
  }
  /**
  *{@summary delete Creature that can't be cleaner.}<br>
  *@version 1.29
  */
  public GCreature filtrePropreteMax(){
    if (début==null){ return new GCreature();}
    return début.filtrePropreteMax();
  }
  /**
  *{@summary delete Creature that didn't whant food.}<br>
  *@version 1.29
  */
  public GCreature filtreWantFood(){
    if (début==null){ return new GCreature();}
    return début.filtreWantFood();
  }
  /**
  *{@summary delete Creature that didn't whant clean.}<br>
  *@version 1.29
  */
  public GCreature filtreWantClean(){
    if (début==null){ return new GCreature();}
    return début.filtreWantClean();
  }
  public void setLienFere(Fourmiliere fere){
    if(début==null){ return;}
    début.setLienFere(fere);
  }
  public int getNbrGcStade(int x){ return getGcStade(x).length();}
  public int getNbrImago(){ return getNbrGcStade(0);}
  public int getNbrReine(){ return getGcStade(0).getGcType(0).length();}
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
    if(début!=null){return ((Fourmi)début.getContenu()).getEspece();}
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
      Creature c = cc.getContenu();
      if(c instanceof Insecte){gi.add((Insecte) cc.getContenu());}
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
    if(gc == null || gc.gethead() == null){ return;}
    if (fin == null){
      début = gc.gethead();
      fin = gc.getTail();
    }else {
      fin.setSuivant(gc.gethead());
      gc.gethead().setPrécédent(fin);
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
    if(début == null){ throw new EmptyListException("GCreature","retirer la Creature "+c.getId());}//erreur.erreur("Aucune créature n'as pu être retirer car GCreature est vide","GCreature.retirer",true); return;}
    if(début.getContenu().equals(c)){
      if(fin.getContenu().equals(c)){
        début = null; fin = null; // on retire la seule créature
      }else{
        début = début.getSuivant(); // on retire la 1a créature.
      }
      return;
    }
    début.retirer(c);
    // On remet fin a la dennière case.
    fin = début;
    while(fin.getSuivant() != null){
      fin = fin.getSuivant();
    }
  }
  public void retirer(Creature c){remove(c);}
  public void delete(Creature c){retirer(c);}
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
  *Play as an ant.
  *@version 1.33
  */
  private void jouerE() throws EmptyListException{
    if(début == null){
      throw new EmptyListException("GCreature","jouer");
    }else{
      début.jouer();
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
  *@version 1.30
  */
  private void preTourE() throws EmptyListException{
    if(début == null){
      throw new EmptyListException("GCreature","preTour");
    }else{
      début.preTour();
    }
  }
  /**
  *reset action before the turn of all the ant.
  *@version 1.30
  */
  public void preTour(){
    try{
      preTourE();
    }catch (EmptyListException e) {}
  }
  public void actualiserCaseSN(){
    if(début == null){ return;}
    début.actualiserCaseSN();
  }
  public void setPheromone(Pheromone ph){
    if (début==null){ return;}
    début.setPheromone(ph);
  }
  public void classerPourNetoyage(){
    if (début==null){ return;}
    début.classerPourNétoyage();
  }
  public int [] toTId(){
    if (début==null){ return new int[0];}
    return début.toTId();
  }
  /**
  *{@summary Transform a GCreature in List&lt;Creature&gt;.}
  *@version 1.38
  */
  public List<Creature> toList(){
    if (début==null){
      List<Creature> lc = new List<Creature>();
      return lc;
    }
    return début.toList();
  }
  //functions that use iterator
  public boolean aFiniDeJouer(){
    List<Creature> lc = toList();
    for (Creature c : lc ) {
      if(c.getAction()>0){return false;}
    }
    return true;
  }
  /**
  *{@summary Force all the GCreature Creature to end there turn.}<br>
  *Ant that still haven't end there turn will have action set to 0 &#38; tour to update age, cleaning etc.
  */
  public boolean setAction0AndEndTurn(){
    List<Creature> lc = toList();
    for (Creature c : lc ) {
      if(c.getAction()>0){
        c.setAction(0);
        c.tour();//force to do finTour() without any action ant will do nothing.
      }
    }
    return true;
  }
}
