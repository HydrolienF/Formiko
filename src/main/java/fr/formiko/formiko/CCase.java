package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Point;
import fr.formiko.usuel.Ascii;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.structures.listes.Liste;
import fr.formiko.usuel.types.str;

import java.io.Serializable;

public class CCase implements Serializable{
  private CCase haut, bas, droite, gauche;
  private Case contenu;

  // CONSTRUCTORS --------------------------------------------------------------
  public CCase(Case contenu){
    this.contenu = contenu;
    // this(contenu, null, null, null, null);
    // if(contenu==null){
    //   erreur.erreur("Le contenu est déclaré vide !",true);
    // }
  }
  public CCase(int x, int y){
    this(new Case(x,y));
  }
  // GET SET -------------------------------------------------------------------
  public CCase getHaut(){return haut;}
  public void setHaut(CCase c){haut = c;}
  public CCase getBas(){return bas;}
  public void setBas(CCase c){bas = c;}
  public CCase getDroite(){return droite;}
  public void setDroite(CCase c){droite = c;}
  public CCase getGauche(){return gauche;}
  public void setGauche(CCase c){gauche = c;}
  public Case getContent(){return contenu;}
  public void setContenu(Case c){contenu = c;}
  public GGraine getGg(){return contenu.getGg();}
  public int getX(){return getContent().getX();}
  public int getY(){return getContent().getY();}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    return getContent().toString();
    // if(getBas()==null){//la sortie
    //   return toStringLigne();
    // }//passage a la ligne suivante
    // return toStringLigne()+"\n"+getBas().toString();
  }
  public String desc(){ return contenu.desc();}
  // public String toStringLigne(){
  //   if(getDroite()==null){//si c'est le dernier de la ligne
  //     return getContent().toString();
  //   }//passage a la case suivante.
  //   return getContent().toString()+"\n"+getDroite().toStringLigne();
  // }
  /**
  *{@summary Standard equals function.}<br>
  *Null &#38; other class type proof.<br>
  *@param o o is the Object to test. It can be null or something else than this class.
  *@lastEditedVersion 1.31
  */
  @Override
  public boolean equals(Object o){ // on ne peu pas tt vérifié facilement alors on ce contente de vérifié les co X Y du point et le nbr de connection.
    if(o==null || !(o instanceof CCase)){return false;}
    CCase cc = (CCase)o;
    if (cc.nbrDeCaseVoisine() != this.nbrDeCaseVoisine()){ return false;}
    return cc.getContent().equals(this.getContent());
  }
  public GGraine getGg(int x){
    if (x != 1){
      erreur.erreurPasEncoreImplemente();
    }
    GGraine gir = contenu.getGGraineCopier(); // ici on ne veut pas modifier le groupe originale alors on en fait une copie.
    //if (nbrDeCaseVoisine() != 4){ debug.débogage("La détection des graine ne marche pas car trop proche de la bordure de la carte");return gir;}
    try {
      gir.add(getDroite().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.add(getHaut().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.add(getBas().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.add(getGauche().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.add(getGauche().getHaut().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.add(getGauche().getBas().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.add(getDroite().getHaut().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.add(getDroite().getBas().getContent().getGGraineCopier());
    }catch (Exception e) {}
    return gir;
  }
  public Liste<Case> getGca(int x){
    if (x != 1){
      erreur.erreurPasEncoreImplemente();
    }
    // ici on ne veut pas modifier le groupe originale alors on en fait une copie.
    Liste<Case> gir = new Liste<Case>();
    gir.add(this.getContent());
    try {
      gir.add(getDroite().getContent());
    }catch (Exception e) {}
    try {
      gir.add(getHaut().getContent());
    }catch (Exception e) {}
    try {
      gir.add(getBas().getContent());
    }catch (Exception e) {}
    try {
      gir.add(getGauche().getContent());
    }catch (Exception e) {}
    try {
      gir.add(getGauche().getHaut().getContent());
    }catch (Exception e) {}
    try {
      gir.add(getGauche().getBas().getContent());
    }catch (Exception e) {}
    try {
      gir.add(getDroite().getHaut().getContent());
    }catch (Exception e) {}
    try {
      gir.add(getDroite().getBas().getContent());
    }catch (Exception e) {}
    return gir;
  }
  /**
  *{@summary return a direction by using this &#38; an other CCase to reach.}
  *@lastEditedVersion 2.11
  */
  public int getDirection(CCase to){
    if (to==null){ return 5;}
    int x = this.getContent().getX() - to.getContent().getX();
    int y = this.getContent().getY() - to.getContent().getY();
    return getDirectionFromXY(x,y);
  }
  /**
  *{@summary return a direction by using this &#38; a Case to reach.}
  *@lastEditedVersion 2.11
  */
  public int getDirection(Case to){
    if (to==null){ return 5;}
    int x = this.getContent().getX() - to.getX();
    int y = this.getContent().getY() - to.getY();
    return getDirectionFromXY(x,y);
  }
  /**
  *{@summary return a direction by using 2 Point.}
  *@lastEditedVersion 2.11
  */
  public static int getDirection(Point from, Point to){
    int x = from.getX() - to.getX();
    int y = from.getY() - to.getY();
    return getDirectionFromXY(x,y);
  }
  /**
  *{@summary return a direction by using difference in x &#38; in y.}
  *@lastEditedVersion 2.11
  */
  private static int getDirectionFromXY(int x, int y){
    //int xabs = valAbs(x); int yabs = valAbs(y); on pourrait utiliser ces données pour aller parfois juste en x parfois juste en y lorsque le trajet n'est pas conplètement en diagonale. (cad lorsque xabs == yabs)
    // x est négatif si le point ou l'on veux ce rendre est plus a droite.
    // y est négatif si le point ou l'on veux ce rendre est plus bas.
    if (x < 0){
      if (y < 0){
        return 9;
      }else if (y > 0){
        return 3;
      }else{
        return 6;
      }
    }else if(x > 0){
      if (y < 0){
        return 7;
      }else if (y > 0){
        return 1;
      }else{
        return 4;
      }
    }else{ //x==0
      if (y < 0){
        return 8;
      }else if (y > 0){
        return 2;
      }else{
        return 5;
      }
    }
  }
  public void setTypes(String t[]){
    CCase cc = this;int lent = t.length;
    for (int i=0;i<lent  && cc != null ;i++ ) {
      cc.setTypesLigne(t[i]);
      cc = cc.getBas();
    }
  }
  public void setTypesLigne(String s){
    String t [] = decoderUnFichier.getTableauString(s,',');
    CCase cc = this;int lent = t.length;
    for (int i=0;i<lent  && cc != null ;i++ ) {
      cc.getContent().setType(str.sToI(t[i]));
      cc = cc.getDroite();
    }
  }
  public String getPoint(){
    return contenu.description();
  }
  public GInsecte getGi(){return contenu.getGi();}
  public GInsecte getGi(int x){
    if (x != 1){
      erreur.erreurPasEncoreImplemente();
    }
    GInsecte gir = getGi();
    try {
      gir.addList(getDroite().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(getHaut().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(getBas().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(getGauche().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(getGauche().getHaut().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(getGauche().getBas().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(getDroite().getHaut().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(getDroite().getBas().getGi());
    }catch (Exception e) {}
    return gir;
  }


  public byte nbrDeCaseVoisine(){
    byte xr = 0;
    if (getHaut()!= null){xr++;}
    if (getBas()!= null){xr++;}
    if (getDroite()!= null){xr++;}
    if (getGauche()!= null){xr++;}
    return xr;
  }
  public void afficheTout(){
    this.afficheLigne();
    if(getBas() != null){
      getBas().afficheTout();
    }
  }
  public void afficheLigne(){
    erreur.println(contenu);
    if (getDroite() != null){
      getDroite().afficheLigne();
    }
  }

  public void tourCases(){
    tourLigneCases();
    if(getBas() != null){
      getBas().tourCases();
    }
  }
  public void tourLigneCases(){
    contenu.updateFoodInsecte();
    if(Main.getPartie().getAppartionGraine()){
      contenu.actualisationGraine(this);
    }
    if (getDroite() != null){
      getDroite().tourLigneCases();
    }
  }
  public void add(Case c){
    CCase cc = this;
    while (cc.getDroite() != null){
      cc = cc.getDroite();
    }
    cc.setDroite(new CCase(c));
  }
}
