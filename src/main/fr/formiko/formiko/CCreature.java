package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.exeption.ElementListeIntrouvableException;
import java.io.Serializable;

public class CCreature implements Serializable{
  protected CCreature suivant, précédente;
  protected Creature contenu;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public CCreature(Creature c, CCreature suivant, CCreature précédente){
    contenu = c;
    this.suivant = suivant;
    this.précédente = précédente;
  }
  public CCreature(Creature c){
    this(c, null, null);
  }
  // GET SET --------------------------------------------------------------------
  public CCreature getSuivant(){return suivant;}
  public void setSuivant(CCreature cc){suivant = cc;}
  public CCreature getPrécédent(){return précédente;}
  public void setPrécédent(CCreature cc){précédente = cc;}
  public Creature getContenu(){return contenu;}
  public Creature getCreature(){return getContenu();}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    if (suivant == null){
      return contenu.toString()+"";
    }else{
      return contenu.toString()+ ", "+suivant.gcToString();
    }
  }public String gcToString(){return toString();}
  public int length(){
    if (suivant == null){
      return 1;
    }else{
      return 1 + suivant.length();
    }
  }
  public void setPheromone(Pheromone ph){
    CCreature cc = this;
    while(cc!=null){
      cc.getContenu().setPheromone(ph);
      cc=cc.getSuivant();
    }
  }
  public Fourmi getReine(){
    if (this.getSuivant()==null){ return null;}
    Fourmi f1 = (Fourmi) this.getSuivant().getContenu();
    if (f1.estReine()){//si c'est la reine
      return f1;
    }else{
      return this.getSuivant().getReine();
    }
  }
  public Fourmi getPlusAffamée(){
    Fourmi fr = (Fourmi) this.getContenu();
    CCreature ccTest = suivant;
    while (ccTest != null){
      Fourmi fTest = (Fourmi) ccTest.getContenu();
      if (fTest.getNourriture()<fr.getNourriture()){ fr = fTest;}
      ccTest = ccTest.getSuivant();
    }
    return fr;
  }
  public GCreature getGcStade(int stade){
    GCreature gcr = new GCreature();
    CCreature ccTest = this;
    while (ccTest != null){
      Creature cTest = ccTest.getContenu();
      Fourmi fTest = (Fourmi) cTest;
      if(fTest.getStade() == stade){
        gcr.ajouter(cTest);
      }
      ccTest = ccTest.getSuivant();
    }
    return gcr;
  }
  public GCreature getGcType(int type){
    GCreature gcr = new GCreature();
    CCreature ccTest = this;
    while (ccTest != null){
      Creature cTest = ccTest.getContenu();
      Fourmi fTest = (Fourmi) cTest;
      if(fTest.getTypeF() == type){
        gcr.ajouter(cTest);
      }
      ccTest = ccTest.getSuivant();
    }
    return gcr;
  }
  public Creature getCouvainSale(){
    if (suivant == null){ return null;}
    Fourmi f = (Fourmi) suivant.getContenu();
    if (f.getPropreté() < 75){
      return f;
    }else{
      return suivant.getCouvainSale();
    }
  }
  public void getCouvainsSale(){
    if (suivant == null){ return;}
    Fourmi f = (Fourmi) suivant.getContenu();
    if (f.getPropreté() > 75){
      suivant = suivant.getSuivant();
      this.getCouvainsSale();
    }else{
      suivant.getCouvainsSale();
    }
  }
  public Creature getCreatureParId(int id){
    if (this.getSuivant()==null){ return null;}
    if (suivant.getContenu().getId()==id){
      return suivant.getContenu();
    }else{
      return this.getSuivant().getCreatureParId(id);
    }
  }
  public Fourmi getFourmiParFere(Fourmiliere fere){
    if(getContenu().estFourmi()){
      if(((Fourmi)(getContenu())).getFere().equals(fere)){return (Fourmi)contenu;}
    }
    if (this.getSuivant()==null){ return null;}
    return suivant.getFourmiParFere(fere);
  }
  public GCreature filtreAlliés(Creature c,int différenceTolléré){
    GCreature gcr = new GCreature();
    CCreature ccTest = this;
    if (c.getPheromone().equals(ccTest.getContenu().getPheromone(),différenceTolléré)){
      gcr.ajouterFin(ccTest.getContenu());
    }
    ccTest = ccTest.getSuivant();
    while(ccTest != null){
      if (c.getPheromone().equals(ccTest.getContenu().getPheromone(),différenceTolléré)){
        gcr.ajouterFin(ccTest.getContenu());
      }
      ccTest = ccTest.getSuivant();
    }
    return gcr;
  }
  public void setLienFere(Fourmiliere fere){
    CCreature ccTest = this;
    while(ccTest != null){
      if(ccTest.getContenu() instanceof Fourmi){
        Fourmi fTest = (Fourmi)(ccTest.getContenu());
        fTest.setFere(fere);
      }
      ccTest = ccTest.getSuivant();
    }
  }
  public int [] gcToTInt(){
    int lentr =this.length();
    int tr[]=new int [lentr];int k=0;
    CCreature cc = this;
    while(k<lentr && cc!= null){
      tr[k]=cc.getContenu().getId();k++;
      cc=cc.getSuivant();
    }
    return tr;
  }
  public GCreature copier(){
    CCreature cc = this;
    GCreature gcr = new GCreature();
    while(cc!=null){
      gcr.ajouterFin(cc.getContenu());//on ajoute seulement le contenu a chaque fois.
      cc=cc.getSuivant();
    }
    return gcr;
  }
  public void retirer(Creature c)throws ElementListeIntrouvableException {
    if (suivant == null){ throw new ElementListeIntrouvableException("Creature",c.getId(),true);}
    if (suivant.getContenu().equals(c)){
      suivant = suivant.getSuivant(); return;
    }
    suivant.retirer(c);
  }
  public void afficheToi(){
    int x = 0;
    if(suivant!=null){ x++;}
    if(précédente!=null){ x++;}
    System.out.print(g.get("celluleA")+" "+ x+" "+g.get("lien")+" : ");
    contenu.afficheToi();
  }
  public void afficheToiRéduit(){
    int x = 0;
    if(suivant!=null){ x++;}
    if(précédente!=null){ x++;}
    System.out.print(contenu.getId());
  }
  public void afficheTout(){
    afficheToi();
    if (suivant!=null){
      try {
      suivant.afficheTout();
    }catch (Exception e) {
      System.out.println("impossible de retirer cet éléments pour cause de stack OverFlow");
      erreur.erreur("","",true);
    }
    }
  }
  public void afficheToutRéduit(){
    afficheToiRéduit();
    if (suivant!=null){
      suivant.afficheToutRéduit();
    }else{
      System.out.println();
    }
  }
  public void jouer(){
    Fourmi fActuel =  null;
    if(contenu instanceof Fourmi){
      fActuel = (Fourmi) contenu;
    }else{
      erreur.erreur("Impossible de faire jouer comme une fourmi la créature "+contenu.getId()+" qui n'en est pas une.","CCreature.jouer");
    }
    if(fActuel!=null){fActuel.tourFourmi();}
    if(suivant != null){
      suivant.jouer();
    }
  }
  public void finTour(){
    Fourmi fActuel =  null;
    if(contenu instanceof Fourmi){
      fActuel = (Fourmi) contenu;
    }else{
      erreur.erreur("Impossible de faire jouer comme une fourmi la créature "+contenu.getId()+" qui n'en est pas une.","CCreature.jouer");
    }
    if(fActuel!=null){fActuel.finTour();}
    if(suivant != null){
      suivant.finTour();
    }
  }
  public void actualiserCaseSN(){
    CCreature cc = this;
    while(cc!= null){
      if(cc.getContenu() instanceof Fourmi){
        Fourmi f = (Fourmi)(cc.getContenu());
        Joueur j = f.getJoueur();
        GCase gc = f.getCCase().getGca(1); //ensemble des case vue par la créature.
        CCase cca = gc.getDébut();
        while(cc!=null){
          int x = cca.getContenu().getX(); int y = cca.getContenu().getY();
          j.setCaseSombre(x,y,false);
          j.setCaseNuageuse(x,y,false);
          cca=cca.getDroite();
        }
      }
      cc = cc.getSuivant();
    }
  }
  public void classerPourNétoyage(){
    //TODO
    //le but est d'habord que personne ne passe en dessous des 50 de propretée.
    //on met tout ce qui sont en dessous de 50 dans l'ordre d'age/stade.
    //et on ajoute ceux qui sont après dans l'ordre de saleté.
  }
  public boolean aFiniDeJouer(){
    if(getContenu().getAction()>0){return false;}//si la Creature n'as pas fini.
    if(getSuivant()==null){return true;}//si c'était la dernière de la liste et que tt le monde a fini.
    return getSuivant().aFiniDeJouer();//si il reste d'autre Creature après.
  }
  public void setAction0(){
    contenu.setAction(0);
  }
}
