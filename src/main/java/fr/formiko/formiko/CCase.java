package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Point;
import fr.formiko.usuel.Ascii;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.*;
import fr.formiko.usuel.types.str;

import java.io.Serializable;

public class CCase implements Serializable{
  private CCase haut, bas, droite, gauche;
  private Case contenu;

  // CONSTRUCTORS --------------------------------------------------------------
  public CCase(Case contenu, CCase haut, CCase bas, CCase droite, CCase gauche){
    this.contenu = contenu;
    this.haut = haut; this.bas = bas; this.droite = droite; this.gauche = gauche;
  }
  public CCase(Case contenu){
    this(contenu, null, null, null, null);
    if(contenu==null){
      erreur.erreur("Le contenu est déclaré vide !",true);
    }
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
      if(bas==null){//la sortie
        return toStringLigne();
      }//passage a la ligne suivante
      return toStringLigne()+"\n"+bas.toString();
  }
  public String desc(){ return contenu.desc();}
  public String toStringLigne(){
    if(droite==null){//si c'est le dernier de la ligne
      return getContent().toString();
    }//passage a la case suivante.
    return getContent().toString()+"\n"+droite.toStringLigne();
  }
  /**
  *{@summary Standard equals function.}<br>
  *Null &#38; other class type proof.<br>
  *@param o o is the Object to test. It can be null or something else than this class.
  *@lastEditedVersion 1.31
  */
  @Override
  public boolean equals(Object o){ // on ne peu pas tt férifié facilement alors on ce contente de vérifié les co X Y du point et le nbr de connection.
    if(o==null || !(o instanceof CCase)){return false;}
    CCase cc = (CCase)o;
    if (cc.nbrDeCaseVoisine() != this.nbrDeCaseVoisine()){ return false;}
    return cc.getContent().equals(this.getContent());
  }
  /**
  *{@summary Get CCase with position x,y.}<br>
  *Null proof.<br>
  *@param x The x of the searched CCase.
  *@param y The y of the searched CCase.
  *@lastEditedVersion 1.39
  */
  public CCase getCCase(int x, int y){
    // Si le x n'est pas encore bon.
    if (contenu.getP().getX() != x ){
      if (contenu.getP().getX() < x){
        if(droite==null){ return null;}
        return droite.getCCase(x,y);
      }else{
        if(gauche==null){ return null;}
        return gauche.getCCase(x,y);
      }
    }
    // Si le y n'est pas encore bon.
    if (contenu.getP().getY() != y ){
      if (contenu.getP().getY() < y){
        if(bas==null){ return null;}
        return bas.getCCase(x,y);
      }else{
        if(haut==null){ return null;}
        return haut.getCCase(x,y);
      }
    }
    return this;
  }
  public GGraine getGg(int x){
    if (x != 1){
      erreur.erreurPasEncoreImplemente();
    }
    GGraine gir = contenu.getGGraineCopier(); // ici on ne veut pas modifier le groupe originale alors on en fait une copie.
    //if (nbrDeCaseVoisine() != 4){ debug.débogage("La détection des graine ne marche pas car trop proche de la bordure de la carte");return gir;}
    try {
      gir.addGg(droite.getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.addGg(haut.getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.addGg(bas.getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.addGg(gauche.getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.addGg(gauche.getHaut().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.addGg(gauche.getBas().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.addGg(droite.getHaut().getContent().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.addGg(droite.getBas().getContent().getGGraineCopier());
    }catch (Exception e) {}
    return gir;
  }
  public GCase getGca(int x){
    if (x != 1){
      erreur.erreurPasEncoreImplemente();
    }
    GCase gir = new GCase(); // ici on ne veut pas modifier le groupe originale alors on en fait une copie.
    gir.add(this.getContent());
    try {
      gir.add(droite.getContent());
    }catch (Exception e) {}
    try {
      gir.add(haut.getContent());
    }catch (Exception e) {}
    try {
      gir.add(bas.getContent());
    }catch (Exception e) {}
    try {
      gir.add(gauche.getContent());
    }catch (Exception e) {}
    try {
      gir.add(gauche.getHaut().getContent());
    }catch (Exception e) {}
    try {
      gir.add(gauche.getBas().getContent());
    }catch (Exception e) {}
    try {
      gir.add(droite.getHaut().getContent());
    }catch (Exception e) {}
    try {
      gir.add(droite.getBas().getContent());
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
  public int getNbrX(){
    if (droite ==  null){ return 1;}
    return 1 + droite.getNbrX();
  }
  public int getNbrY(){
    if (bas ==  null){ return 1;}
    return 1 + bas.getNbrY();
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
      gir.addList(droite.getGi());
    }catch (Exception e) {}
    try {
      gir.addList(haut.getGi());
    }catch (Exception e) {}
    try {
      gir.addList(bas.getGi());
    }catch (Exception e) {}
    try {
      gir.addList(gauche.getGi());
    }catch (Exception e) {}
    try {
      gir.addList(gauche.getHaut().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(gauche.getBas().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(droite.getHaut().getGi());
    }catch (Exception e) {}
    try {
      gir.addList(droite.getBas().getGi());
    }catch (Exception e) {}
    return gir;
  }


  public byte nbrDeCaseVoisine(){
    byte xr = 0;
    if (haut!= null){xr++;}
    if (bas!= null){xr++;}
    if (droite!= null){xr++;}
    if (gauche!= null){xr++;}
    return xr;
  }
  public void afficheTout(){
    this.afficheLigne();
    if(bas != null){
      bas.afficheTout();
    }
  }
  public void afficheLigne(){
    erreur.println(contenu);
    if (droite != null){
      droite.afficheLigne();
    }
  }

  public void tourCases(){
    tourLigneCases();
    if(bas != null){
      bas.tourCases();
    }
  }
  public void tourLigneCases(){
    contenu.updateFoodInsecte();
    if(Main.getPartie().getAppartionGraine()){
      contenu.actualisationGraine(this);
    }
    if (droite != null){
      droite.tourLigneCases();
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
