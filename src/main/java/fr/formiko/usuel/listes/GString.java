package fr.formiko.usuel.listes;

import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.types.str;

import java.io.Serializable;
import javax.swing.JComboBox;

/**
*{@summary Custom String Linked Liste class.}<br>
*It have a lot of useful function that use fore() loop.<br>
*@version 1.41
*@author Hydrolien
*/
public class GString extends Liste<String> implements Serializable {
  // CONSTRUCTEUR -----------------------------------------------------------------
  /**
  *{@summary Constructor from an array.}<br>
  *@version 1.41
  */
  public GString(String t[]){
    for (String s : t ) {
      add(s);
    }
  }
  public GString(){}

  // GET SET -----------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  //equals is in Liste
  /**
  *{@summary Return a JComboBox with all the String in it.}<br>
  *@param x the default select item.
  *@version 1.41
  */
  public JComboBox<String> getComboBox(int x){
    JComboBox<String> cb = new JComboBox<String>();
    for (String s : this ) {
      cb.addItem(s);
    }
    if(x!=0){
      try {
        cb.setSelectedItem(getItem(x));
      }catch (Exception e) {
        erreur.erreur("Impossible de mettre l'élément n°"+x+" par défaut.");
      }
    }
    return cb;
  }
  /***
  *{@summary Return a JComboBox with all the String in it.}<br>
  *1a item is selected.
  *@version 1.41
  */
  public JComboBox<String> getComboBox(){ return getComboBox(0);}
  /**
  *{@summary Return the translation key for s.}<br>
  *If it fail it return null.<br>
  *@param s the string give by the key.
  *@version 1.41
  */
  public String getKey(String s){
    for (String key : this ) {
      if(g.get(key).equalsIgnoreCase(s)){return key;}
    }
    return null;
  }
  /**
  *{@summary Add item peace by peace.}<br>
  *@param s the String to split.
  *@param maxLen the max length for a line.
  *@param doNotCutWord if true word will not be cut. (If a word is too long for being in 1 line it will be cut.)
  *@version 1.41
  */
  public void addParMorceaux(String s, int maxLen, boolean doNotCutWord){
    int k=0; int lens = s.length();
    if(!doNotCutWord){
      while(k<lens){
        add(s.substring(k,math.min(k+maxLen,lens))); //ajout de la sous chaine a partir de k et pendant maxLen. Sauf la dernière qui s'arrète a lens et pas a k+maxLen.
        k=k+maxLen;
      }
    }else{//on évite de couper des mots.
      while(k<lens){
        String sub = s.substring(k,lens);//ce qu'on a pas encore affiché.
        String t[] = sub.split(" ");
        String s2="";
        int i=0; int lent=t.length;
        while(i<lent && (s2.length()+t[i].length()<maxLen || s2.length()==0)){//tant qu'on dépace pas le nombre de char autorisé on ajoute des mots. On ajoute quand meme un mot si il est trop long pour la chaine.
          if(s2.length()>0){s2=s2+" ";}
          s2=s2+t[i];
          i++;
        }
        add(s2);
        k=k+s2.length()+1;
      }
    }
  }
  /***
  *{@summary Add item peace by peace.}<br>
  *@param s the String to split.
  *@param maxLen the max length for a line.
  *@version 1.41
  */
  public void addParMorceaux(String s, int maxLen){addParMorceaux(s,maxLen,false);}
  /**
  *{@summary Print this.}<br>
  *If is empty it will print a special mail.
  *@version 1.41
  */
  public void afficheToi(){
    if(getHead()==null){
      System.out.println(g.get("GString",1,"Le GString est vide"));
    }else{
      //getHead().afficheTout();
      System.out.println(toStringLong());
    }
  }
  // public void classer(){
  //   if (getHead()==null){ return;}
  //   //getHead().classer(); a poursuivre
  // }
  /**
  *{@summary Do a transformation for score.}<br>
  *@version 1.41
  */
  public GString transformerScore(){
    if (getHead()==null){ return null;}
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
      String t [] = getHead().getContent().split("\n");int lent = t.length;
      //phase 2 on add a la fin de chaque line les éléments de chaque CString.
      for (String s : this ) {
        String tTemp [] = s.split("\n");
        for (int i=0;i<lent ;i++ ) {//on ajoute tout les nouveau éléments
          t[i]=t[i]+",,"+tTemp[i];
        }
      }
      //phase 3 on passe du tableau au GString.
      GString gsr = new GString();
      String s = g.getM("reine")+","+g.getM("imago")+","+g.getM("nymphe")+","+g.getM("larve")+","+g.getM("oeuf")+",,";
      int lengj = length();
      String s2 = "";
      for(int i=0;i<lengj;i++){
        s2=s2+s;
      }
      gsr.add(s2);
      for (int i=0;i<lent ;i++ ) {
        gsr.add(t[i]);
      }
      return gsr;
  }
  /**
  *{@summary Count different line between this &#38; gs2.}<br>
  *@version 1.41
  */
  public int compterlineDifferenteDe(GString gs2){
    int cpt=0;
    for (String line : this ) {
      if(!gs2.contains(line)){cpt++;}//on compte 1 line différente.
    }
    return cpt;
  }
  /**
  *{@summary Remove different line between this &#38; gs2.}<br>
  *@version 1.41
  */
  public void supprimerLesLignesCommunesAvec(GString gs2){
    for (String line : this ) {
      if(gs2.contains(line)){
        gs2.remove(line);
        remove(line);//pour évité les soucis de "c'est la 1a CString on passe par le GString."
      }//on supprime la line en question dans les 2 fichiers.
    }
  }
  /*public boolean supprimer(String s, int max){
    if (getHead()==null){ return 0;}
    if(getHead().getContent().equals(s)){//on teste la 1a CString car après on vérifira seulement la suivante. (puis la suivante et ainsi de suite).
      getHead() = getHead().getNext(); return true;
    }
    int b = getHead().supprimer(s);
    actualiserFin();
    if(!b){System.out.println("la suppression de la line qui suis n'as pas pu etre éffectuée"+s);}
    return b;
  }*/
  /**
  *{@summary count how much fonction and class (short or long) a GString have.}
  *@version 1.41
  */
  public GInt compterFct(){
    if (getHead()==null){ GInt gi = new GInt(); gi.add(0);gi.add(0); return gi;}
    int shortFct=0; int longFct=0;
    for (String line : this ) {
      if(str.contient(line,"{") && !estCom(line) && (str.contient(line, "class") || str.contient(line, "interface") || str.contient(line,"public") || str.contient(line,"private") || str.contient(line,"protected"))){
        if(str.contient(line,"}")){
          shortFct++;
        }else{
          longFct++;
        }
      }
    }
    GInt r = new GInt();
    r.add(shortFct);r.add(longFct);
    return r;
  }
  /**
  *{@summary count how much javadoc commentary a GString have.}
  *@version 1.41
  */
  public int compterComJavadoc(){
    int c=0;
    for (String line : this ) {
      if(str.contient(line,"/**") && !str.contient(line,"/***")){
        c++;
      }
    }
    return c;
  }
  /**
  *{@summary count how much javadoc commentary and fonction and class (short or long) a GString have.}
  *@version 1.13
  */
  public GInt compterFctEtComJavadoc(){
    GInt gi = compterFct();
    gi.add(compterComJavadoc());
    return gi;
  }
  /**
  *{@summary count how much class and long fonction (public, ø, protected, private) a GString have.}
  *@version 1.41
  */
  public GInt compterFctEnDetail(){
    if (getHead()==null){ GInt gi = new GInt(); gi.add(0);gi.add(0);gi.add(0);gi.add(0);gi.add(0); return gi;}
    int c=0; int pu=0; int po=0; int pr=0;
    for (String line : this ) {
      if(!str.contient(line,"}") && str.contient(line,"{") && !estCom(line)) {
        //if(!str.contient(line,"if(") && !str.contient(line,"if (") && !str.contient(line,"while") && !str.contient(line,"for(") && !str.contient(line,"for (") && !str.contient(line,"switch")){
        if(str.contient(line, "class") || str.contient(line, "interface")){
          c++;
        }else if (str.contient(line, "(") && str.contient(line, ")")){
          if(str.contient(line, "public")){
            pu++;
          }else if(str.contient(line, "protected")){
            po++;
          }else if(str.contient(line, "private")){
            pr++;
          }
        }
      }
    }
    GInt gi = new GInt();
    gi.add(c);gi.add(pu);gi.add(po);gi.add(pr);
    return gi;
  }
  /**
  *{@summary Return true if is a java commentary line.}<br>
  *Line can have a comment at the end of the line &#38; be concidered as a non-comment line if comment start after char 20 &#38; midle of the line.<br>
  *@version 1.41
  */
  private boolean estCom(String line){
    if(str.contient(line.substring(0,math.min(line.length()/2,20)),"/*")){return true;}
    if(str.contient(line.substring(0,math.min(line.length()/2,20)),"//")){return true;}
    return false;
  }
}
