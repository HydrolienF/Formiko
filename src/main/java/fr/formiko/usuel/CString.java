package fr.formiko.usuel.liste;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.79.5
import javax.swing.JComboBox;
import java.io.Serializable;
import fr.formiko.usuel.type.str;
import fr.formiko.usuel.maths.math;

public class CString implements Serializable{
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
  public boolean equals(CString cs){
    if(!cs.getContenu().equals(getContenu())){return false;}
    if(cs.getSuivant()==null && getSuivant()==null){return true;}
    if(cs.getSuivant()==null || getSuivant()==null){return false;}
    return getSuivant().equals(cs.getSuivant());
  }
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
  public String getClé(String s){
    CString cs = this;
    while(cs!=null){
      if(g.get(cs.getContenu()).equals(s)){ return cs.getContenu();}
      if(g.getM(cs.getContenu()).equals(s)){ return cs.getContenu();}
      cs = cs.getSuivant();
    }
    return null;
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
  /**
  *{@summary count how much fonction and class (short or long) a GString have.}
  *@version 1.13
  */
  public GInt compterFct(){
    int c=0; int l=0;
    CString csTemp = this;
    while(csTemp != null){
      String ligne = csTemp.getContenu();
      if(str.contient(ligne,"{") && !csTemp.estCom() && (str.contient(ligne, "class") || str.contient(ligne, "interface") || str.contient(ligne,"public") || str.contient(ligne,"private") || str.contient(ligne,"protected"))){
        if(str.contient(ligne,"}")){
          c++;
        }else{
          l++;
        }
      }
      csTemp = csTemp.getSuivant();
    }
    GInt r = new GInt();
    r.add(c);r.add(l);
    return r;
  }
  /**
  *{@summary count how much javadoc commentary a GString have.}
  *@version 1.13
  */
  public int compterComJavadoc(){
    int c=0;
    CString csTemp = this;
    while(csTemp != null){
      String ligne = csTemp.getContenu();
      if(str.contient(ligne,"/**") && !str.contient(ligne,"/***")){
        c++;
      }
      csTemp = csTemp.getSuivant();
    }
    return c;
  }
  /**
  *{@summary count how much class and long fonction (public, ø, protected, private) a GString have.}
  *@version 1.13
  */
  public GInt compterFctEnDetail(){
    int c=0; int pu=0; int po=0; int pr=0;
    CString csTemp = this;
    while(csTemp != null){
      String ligne = csTemp.getContenu();
      if(!str.contient(ligne,"}") && str.contient(ligne,"{") && !csTemp.estCom()) {
        //if(!str.contient(ligne,"if(") && !str.contient(ligne,"if (") && !str.contient(ligne,"while") && !str.contient(ligne,"for(") && !str.contient(ligne,"for (") && !str.contient(ligne,"switch")){
        if(str.contient(ligne, "class") || str.contient(ligne, "interface")){
          c++;
        }else if (str.contient(ligne, "(") && str.contient(ligne, ")")){
          if(str.contient(ligne, "public")){
            pu++;
          }else if(str.contient(ligne, "protected")){
            po++;
          }else if(str.contient(ligne, "private")){
            pr++;
          }
        }
      }
      csTemp = csTemp.getSuivant();
    }
    GInt gi = new GInt();
    gi.add(c);gi.add(pu);gi.add(po);gi.add(pr);
    return gi;
  }
  /**
  *{@summary Return true if the line is a commentary.}
  *@version 1.13
  */
  public boolean estCom(){
    String ligne = getContenu();
    if(str.contient(ligne.substring(0,math.min(ligne.length()/2,20)),"/*")){return true;}
    if(str.contient(ligne.substring(0,math.min(ligne.length()/2,20)),"//")){return true;}
    return false;
  }
}
