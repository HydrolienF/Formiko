package fr.formiko.usuel.liste;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import javax.swing.JComboBox;
import fr.formiko.usuel.math.math;
import java.io.Serializable;

public class GString implements Serializable{
  private CString début, fin;
  // CONSTRUCTEUR -----------------------------------------------------------------
  public GString(){}

  // GET SET -----------------------------------------------------------------------
  public CString getDébut(){ return début;}
  public CString getFin(){ return fin;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    if(début==null){return "";}
    return début.toString();
  }
  public int length(){
    if(début==null){ return 0;}
    return début.length();
  }
  public JComboBox<String> getComboBox(int x){
    if (début==null){ return new JComboBox<String>();}
    return début.getComboBox(x);
  }
  public JComboBox<String> getComboBox(){ return getComboBox(0);}
  public String getElementX(int x){
    if (début==null){ return null;}
    return début.getElementX(x);
  }
  public String getClé(String s){
    if (début==null){ return null;}
    return début.getClé(s);
  }
  public void ajouter(String s){ // On ajoute a la fin par défaut.
    CString c = new CString(s);
    if (fin == null){ // si la liste est complètement vide.
      fin = c;
      début = c;
    } else {
      fin.setSuivant(c);
      c.setPrécédent(fin);
      fin = c;
    }
  }
  public void ajouter(GString gs){
    if(gs==null){ return;}
    if(this.getDébut()==null){début = gs.getDébut();return;}
    //on lie l'anciène fin au début de gs.
    this.fin.setSuivant(gs.getDébut());
    this.fin.getSuivant().setPrécédent(fin);
    // on change la fin actuelle.
    this.fin = gs.getFin();
  }
  public void add(String s){ ajouter(s);}
  public void add(GString s){ ajouter(s);}
  public String concatène(){
    if (début == null){ // 0
      return "";
    } else if (début.getSuivant() == null) { // 1
      return début.getContenu();
    } else {
      return début.concatène(); // plus d'1
    }
  }
  public void addParMorceaux(String s,int x, boolean b){
    int k=0; int lens = s.length();
    if(!b){
      while(k<lens){
        add(s.substring(k,math.min(k+x,lens))); //ajout de la sous chaine a partir de k et pendant x. Sauf la dernière qui s'arrète a lens et pas a k+x.
        k=k+x;
      }
    }else{//on évite de couper des mots.
      while(k<lens){
        String sub = s.substring(k,lens);//ce qu'on a pas encore affiché.
        String t[] = sub.split(" ");
        String s2="";
        int i=0; int lent=t.length;
        while(i<lent && (s2.length()+t[i].length()<x || s2.length()==0)){//tant qu'on dépace pas le nombre de char autorisé on ajoute des mots. On ajoute quand meme un mot si il est trop long pour la chaine.
          if(s2.length()>0){s2=s2+" ";}
          s2=s2+t[i];
          i++;
        }
        add(s2);
        k=k+s2.length()+1;
      }
    }
  }
  public void addParMorceaux(String s, int x){addParMorceaux(s,x,false);}
  public String concatèneCompacte(){
    if (début == null){ // 0
      return "";
    } else if (début.getSuivant() == null) { // 1
      return début.getContenu();
    } else {
      return début.concatèneCompacte(); // plus d'1
    }
  }
  public void afficheToi(){
    if(début==null){
      System.out.println(g.get("GString",1,"Le GString est vide"));
    }else{
      début.afficheTout();
    }
  }
  public void filtreDoublon(){//permet de filtré les doublon dans un GString
    if (début==null){ return;}
    GString gs = début.filtreDoublon();
    début = gs.getDébut();
    fin = gs.getFin();
  }
  // renvoie true si est seulement si s est une des String du GString
  public boolean contient(String s){
    if (début==null){ return false;}
    return début.contient(s);
  }
  public void classer(){
    if (début==null){ return;}
    //début.classer(); a poursuivre
  }
  public GString transformerScore(){
    if (début==null){ return null;}
    return début.transformerScore();
  }
  public int compterLigneDifferenteDe(GString gs2){
    if (début==null){ return 0;}
    return début.compterLigneDifferenteDe(gs2);
  }
  public void supprimerLesLignesCommunesAvec(GString gs2){
    if (début==null){ return;}
    début.supprimerLesLignesCommunesAvec(gs2,this);//on met aussi ce GString pour pouvoir retirer une CString.
  }
  public boolean supprimer(String s){
    if (début==null){ return false;}
    if(début.getContenu().equals(s)){//on teste la 1a CString car après on vérifira seulement la suivante. (puis la suivante et ainsi de suite).
      début = début.getSuivant(); return true;
    }
    boolean b = début.supprimer(s);
    actualiserFin();
    //if(!b){System.out.println("la suppression de la ligne qui suis n'as pas pu etre éffectuée"+s);}
    return b;
  }
  /*public boolean supprimer(String s, int max){
    if (début==null){ return 0;}
    if(début.getContenu().equals(s)){//on teste la 1a CString car après on vérifira seulement la suivante. (puis la suivante et ainsi de suite).
      début = début.getSuivant(); return true;
    }
    int b = début.supprimer(s);
    actualiserFin();
    if(!b){System.out.println("la suppression de la ligne qui suis n'as pas pu etre éffectuée"+s);}
    return b;
  }*/
  public void actualiserFin(){ //remet fin a ce place.
    fin = début;
    while(fin.getSuivant()!=null){//tant que ce n'est pas le dernier éléments de la chaine.
      fin = fin.getSuivant();
    }
  }
}
