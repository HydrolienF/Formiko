package fr.formiko.usuel.liste;
import javax.swing.JComboBox;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

public class CString {
  private CString suivant, précédent;
  private String s;
  // CONSTRUCTEUR -----------------------------------------------------------------
  public CString(String s){
    this.s = s;
  }
  // GET SET -----------------------------------------------------------------------
  public CString getSuivant(){return suivant;}
  public void setSuivant(CString cs){suivant = cs;}
  public CString getPrécédent(){return précédent;}
  public void setPrécédent(CString cs){précédent = cs;}
  public String getContenu(){ return s;}
  public void setContenu(String x){s=x;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    return concatène();
  }
  public int length(){
    if(suivant==null){ return 1;}
    return 1+suivant.length();
  }
  public String concatène(){
    if (suivant == null){ return s;}
    return s + "\n" + suivant.concatène();
  }
  public String concatèneCompacte(){
    if (suivant == null){ return s;}
    return s + ", " + suivant.concatèneCompacte();
  }
  public void afficheTout(){
    afficheToi();
    if(suivant != null){ suivant.afficheTout();}
  }
  public void afficheToi(){
    System.out.println(s);
  }
  public GString filtreDoublon(){
    GString gsr = new GString();
    CString csTemp = this;
    while(csTemp != null){
      String s = csTemp.getContenu();
      if (!gsr.contient(s)){
        gsr.ajouter(s);
      }
      csTemp = csTemp.getSuivant();
    }
    return gsr;
  }
  // renvoie true si est seulement si s est une des String du GString
  public boolean contient(String s){
    CString csTemp = this;
    while(csTemp != null){
      if(csTemp.getContenu().equals(s)){ return true;}
      csTemp = csTemp.getSuivant();
    }return false;
  }
  /*public void classer(){
    GString gsr = new GString();

    return gsr;
  }*/
  public JComboBox<String> getComboBox(int x){
    JComboBox<String> cb = new JComboBox<String>();
    CString cs = this;
    while(cs!=null){
      cb.addItem(cs.getContenu());
      cs=cs.getSuivant();
    }
    if(x!=0){
      try {
        cb.setSelectedItem(this.getElementX(x));
      }catch (Exception e) {
        erreur.erreur("Impossible de mettre l'élément n°"+x+" par défaut.","CString.getComboBox");
      }
    }
    return cb;
  }
  public String getElementX(int x){
    if(x==0){ return getContenu();}
    if(suivant!=null){ return suivant.getElementX(x-1);}
    if(x<0){ erreur.erreur("Impossible de récupéré un élément négatif","CString.getElementX");return null;}
    erreur.erreur("Impossible de récupérer un éléments plus grand que le nombre de CString de la GString","CString.getElementX");
    return null;
  }
  public GString transformerScore(){
    /*on cherche a passer de :
    1,0,0,0,0
    1,1,0,0,1

    1,2,0,0,0
    1,3,0,0,0

    a :
    1,0,0,0,0,,1,2,0,0,0
    1,2,0,0,1,,1,3,0,0,0
    */
    //phase 1 on cré un tableau avec tout les éléments de début.
    //String t [] = {"Reine..."}
    String t [] = getContenu().split("\n");int lent = t.length;
    CString cs = suivant;
    //phase 2 on ajouter a la fin de chaque ligne les éléments de chaque CString.
    while(cs!=null){
      String tTemp [] = cs.getContenu().split("\n");
      for (int i=0;i<lent ;i++ ) {//on ajoute tout les nouveau éléments
        t[i]=t[i]+",,"+tTemp[i];
      }
      cs = cs.getSuivant();
    }
    //phase 3 on passe du tableau au GString.
    GString gsr = new GString();
    String s = g.getM("reine")+","+g.getM("imago")+","+g.getM("nymphe")+","+g.getM("larve")+","+g.getM("oeuf")+",,";
    int lengj = length();
    String s2 = "";
    for(int i=0;i<lengj;i++){
      s2=s2+s;
    }
    gsr.ajouter(s2);
    for (int i=0;i<lent ;i++ ) {
      gsr.ajouter(t[i]);
    }
    return gsr;
  }
  public String getClé(String s){
    CString cs = this;
    while(cs!=null){
      if(g.get(cs.getContenu()).equals(s)){ return cs.getContenu();}
      if(g.getM(cs.getContenu()).equals(s)){ return cs.getContenu();}
      cs = cs.getSuivant();
    }
    return null;
  }
  public int compterLigneDifferenteDe(GString gs2){
    int x=0;
    if(!gs2.contient(getContenu())){x=1;}//on compte 1 ligne différente.
    if(suivant==null){//si c'est la dernière ligne qu'on traite.
      return x;
    }//sinon on demande a la Cellule suivante de ce vérifier aussi.
    return x+suivant.compterLigneDifferenteDe(gs2);
  }
  public void supprimerLesLignesCommunesAvec(GString gs2, GString gs1){
    if(gs2.contient(getContenu())){
      gs2.supprimer(getContenu());
      gs1.supprimer(getContenu());//pour évité les soucis de "c'est la 1a CString on passe par le GString."
    }//on supprime la ligne en question dans les 2 fichiers.
    if(suivant==null){//si c'est la dernière ligne qu'on traite.
      return;
    }//sinon on demande a la Cellule suivante de ce vérifier aussi.
    suivant.supprimerLesLignesCommunesAvec(gs2,gs1);
  }
  public boolean supprimer(String s){//supprime la 1a occurence de s.
    if(suivant == null){return false;}
    if(suivant.getContenu().equals(s)){
      suivant = suivant.getSuivant();//on saute une Cellule.
      return true;
    }
    return suivant.supprimer(s);
  }
}