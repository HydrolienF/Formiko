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
  private Case contenu;
  private GCase gc; //TODO check that it don't cause more lag when saving / loading Partie.

  // CONSTRUCTORS --------------------------------------------------------------
  public CCase(Case contenu, GCase gc){
    this.contenu=contenu;
    this.gc=gc;
    if(contenu==null){
      erreur.erreur("Le contenu est déclaré vide !",true);
    }
  }
  public CCase(int x, int y, GCase gc){
    this(new Case(x,y), gc);
  }
  public CCase(Case contenu){
    this(contenu,null);
  }
  public CCase(int x, int y){
    this(x, y, null);
  }
  // GET SET -------------------------------------------------------------------
  public CCase getUp(){return getGc().getCCase(getX(),getY()-1);}
  public CCase getDown(){return getGc().getCCase(getX(),getY()+1);}
  public CCase getRigth(){return getGc().getCCase(getX()+1,getY());}
  public CCase getLeft(){return getGc().getCCase(getX()-1,getY());}
  public Case getContent(){return contenu;}
  public void setContenu(Case c){contenu = c;}
  public GGraine getGg(){return contenu.getGg();}
  public int getX(){return getContent().getX();}
  public int getY(){return getContent().getY();}
  public GCase getGc() {return gc;}
	// public void setGc(GCase gc) {this.gc=gc;}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    return getContent().toString();
  }
  public String desc(){ return contenu.desc();}
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
    try {
      gir.add(getRigth().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.add(getUp().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.add(getDown().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.add(getLeft().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.add(getLeft().getUp().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.add(getLeft().getDown().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.add(getRigth().getUp().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.add(getRigth().getDown().getContent().getGGraineCopier());
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
      gir.add(getRigth().getContent());
    }catch (Exception e) {}
    try {
      gir.add(getUp().getContent());
    }catch (Exception e) {}
    try {
      gir.add(getDown().getContent());
    }catch (Exception e) {}
    try {
      gir.add(getLeft().getContent());
    }catch (Exception e) {}
    try {
      gir.add(getLeft().getUp().getContent());
    }catch (Exception e) {}
    try {
      gir.add(getLeft().getDown().getContent());
    }catch (Exception e) {}
    try {
      gir.add(getRigth().getUp().getContent());
    }catch (Exception e) {}
    try {
      gir.add(getRigth().getDown().getContent());
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
      cc = cc.getDown();
    }
  }
  public void setTypesLigne(String s){
    String t [] = decoderUnFichier.getTableauString(s,',');
    CCase cc = this;int lent = t.length;
    for (int i=0;i<lent  && cc != null ;i++ ) {
      cc.getContent().setType(str.sToI(t[i]));
      cc = cc.getRigth();
    }
  }
  public String getPoint(){
    return contenu.description();
  }
  // TODO #503 create a function that give all CCase in a radius as a list.
  // GCase.getCCases(CCase from, CCase to)
  public GInsecte getGi(){return contenu.getGi();}
  public GInsecte getGi(int x){
    if (x != 1){
      erreur.erreurPasEncoreImplemente();
    }
    GInsecte gir = getGi();
    try {
      gir.addList(getRigth().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(getUp().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(getDown().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(getLeft().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(getLeft().getUp().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(getLeft().getDown().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(getRigth().getUp().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(getRigth().getDown().getGi());
    }catch (Exception e) {}
    return gir;
  }


  public byte nbrDeCaseVoisine(){
    if(getGc()==null){return 0;}
    byte xr = 0;
    if (getUp()!= null){xr++;}
    if (getDown()!= null){xr++;}
    if (getRigth()!= null){xr++;}
    if (getLeft()!= null){xr++;}
    return xr;
  }
  public void afficheTout(){
    this.afficheLigne();
    if(getDown() != null){
      getDown().afficheTout();
    }
  }
  public void afficheLigne(){
    erreur.println(contenu);
    if (getRigth() != null){
      getRigth().afficheLigne();
    }
  }

  public void tourCases(){
    tourLigneCases();
    if(getDown() != null){
      getDown().tourCases();
    }
  }
  public void tourLigneCases(){
    contenu.updateFoodInsecte();
    if(Main.getPartie().getAppartionGraine()){
      contenu.actualisationGraine(this);
    }
    if (getRigth() != null){
      getRigth().tourLigneCases();
    }
  }
}
