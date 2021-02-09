package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.ascii;
import fr.formiko.usuel.liste.*;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.type.str;
import java.io.Serializable;

public class CCase implements Serializable{
  private CCase haut, bas, droite, gauche;
  private Case contenu;
  private static int xi; private static GString légende;

  // CONSTRUCTEUR ---------------------------------------------------------------
  public CCase(Case contenu, CCase haut, CCase bas, CCase droite, CCase gauche){
    this.contenu = contenu;
    this.haut = haut; this.bas = bas; this.droite = droite; this.gauche = gauche;
  }
  public CCase(Case contenu){
    this(contenu, null, null, null, null);
    if(contenu==null){
      erreur.erreur("Le contenu est déclaré vide !","CCase.CCase",true);
    }
  }
  // GET SET --------------------------------------------------------------------
  public CCase getHaut(){return haut;}
  public void setHaut(CCase c){haut = c;}
  public CCase getBas(){return bas;}
  public void setBas(CCase c){bas = c;}
  public CCase getDroite(){return droite;}
  public void setDroite(CCase c){droite = c;}
  public CCase getGauche(){return gauche;}
  public void setGauche(CCase c){gauche = c;}
  public Case getContenu(){return contenu;}
  public void setContenu(Case c){contenu = c;}
  public GGraine getGg(){return contenu.getGg();}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
      if(bas==null){//la sortie
        return toStringLigne();
      }//passage a la ligne suivante
      return toStringLigne()+"\n"+bas.toString();
  }
  public String desc(){ return contenu.desc();}
  public String toStringLigne(){
    if(droite==null){//si c'est le dernier de la ligne
      return getContenu().toString();
    }//passage a la case suivante.
    return getContenu().toString()+"\n"+droite.toStringLigne();
  }
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@version 1.31
  */
  @Override
  public boolean equals(Object o){ // on ne peu pas tt férifié facilement alors on ce contente de vérifié les co X Y du point et le nbr de connection.
    if(o==null || !(o instanceof CCase)){return false;}
    CCase cc = (CCase)o;
    if (cc.nbrDeCaseVoisine() != this.nbrDeCaseVoisine()){ return false;}
    return cc.getContenu().equals(this.getContenu());
  }
  public CCase getCCase(int x, int y){
    // Si le x n'est pas encore bon.
    //debug.débogage("X : "+contenu.getP().getX()+" = "+x+" ?");
    if (contenu.getP().getX() != x ){
      if (contenu.getP().getX() < x){
        if(droite==null){ return nul(x,y);}
        return droite.getCCase(x,y);
      }else{
        if(gauche==null){ return nul(x,y);}
        return gauche.getCCase(x,y);
      }
    }
    // Si le y n'est pas encore bon.
    //debug.débogage("Y : "+contenu.getP().getY()+" = "+y+" ?");
    if (contenu.getP().getY() != y ){
      if (contenu.getP().getY() < y){
        if(bas==null){ return nul(x,y);}
        return bas.getCCase(x,y);
      }else{
        if(haut==null){ return nul(x,y);}
        return haut.getCCase(x,y);
      }
    }
    return this;
  }
  public GGraine getGg(int x){
    if (x != 1){
      erreur.erreurPasEncoreImplémenté("CCase.getGi");
    }
    GGraine gir = contenu.getGGraineCopier(); // ici on ne veut pas modifier le groupe originale alors on en fait une copie.
    //if (nbrDeCaseVoisine() != 4){ debug.débogage("La détection des graine ne marche pas car trop proche de la bordure de la carte");return gir;}
    try {
      gir.ajouterGg(droite.getContenu().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.ajouterGg(haut.getContenu().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.ajouterGg(bas.getContenu().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.ajouterGg(gauche.getContenu().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.ajouterGg(gauche.getHaut().getContenu().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.ajouterGg(gauche.getBas().getContenu().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.ajouterGg(droite.getHaut().getContenu().getGGraineCopier());
    }catch (Exception e) {}
    try {
      gir.ajouterGg(droite.getBas().getContenu().getGGraineCopier());
    }catch (Exception e) {}
    return gir;
  }
  public GCase getGca(int x){
    if (x != 1){
      erreur.erreurPasEncoreImplémenté("CCase.getGi");
    }
    GCase gir = new GCase(); // ici on ne veut pas modifier le groupe originale alors on en fait une copie.
    gir.ajouter(this.getContenu());
    try {
      gir.ajouter(droite.getContenu());
    }catch (Exception e) {}
    try {
      gir.ajouter(haut.getContenu());
    }catch (Exception e) {}
    try {
      gir.ajouter(bas.getContenu());
    }catch (Exception e) {}
    try {
      gir.ajouter(gauche.getContenu());
    }catch (Exception e) {}
    try {
      gir.ajouter(gauche.getHaut().getContenu());
    }catch (Exception e) {}
    try {
      gir.ajouter(gauche.getBas().getContenu());
    }catch (Exception e) {}
    try {
      gir.ajouter(droite.getHaut().getContenu());
    }catch (Exception e) {}
    try {
      gir.ajouter(droite.getBas().getContenu());
    }catch (Exception e) {}
    return gir;
  }
  public int getDirection(CCase cc){
    if (cc==null){ return 5;}
    int x = this.getContenu().getX() - cc.getContenu().getX();
    int y = this.getContenu().getY() - cc.getContenu().getY();
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
      cc.getContenu().setType(str.sToI(t[i]));
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
      erreur.erreurPasEncoreImplémenté("CCase.getGi");
    }
    GInsecte gir = getGi();
    //if (nbrDeCaseVoisine() != 4){ debug.débogage("La detection des insecte ne marche pas car trop proche de la bordure de la carte");return gir;}
    try {
      gir.ajouterGi(droite.getGi());
    }catch (Exception e) {}
    try {
      gir.ajouterGi(haut.getGi());
    }catch (Exception e) {}
    try {
      gir.ajouterGi(bas.getGi());
    }catch (Exception e) {}
    try {
      gir.ajouterGi(gauche.getGi());
    }catch (Exception e) {}
    try {
      gir.ajouterGi(gauche.getHaut().getGi());
    }catch (Exception e) {}
    try {
      gir.ajouterGi(gauche.getBas().getGi());
    }catch (Exception e) {}
    try {
      gir.ajouterGi(droite.getHaut().getGi());
    }catch (Exception e) {}
    try {
      gir.ajouterGi(droite.getBas().getGi());
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
  public void affLégende(){
    if (légende!=null){
      System.out.println(g.get("légende")+" : ");
      légende.afficheToi();
    }
  }
  public void afficheTout(){
    this.afficheLigne();
    if(bas != null){
      bas.afficheTout();
    }
  }
  public void afficheLigne(){
    contenu.afficheToi();
    if (droite != null){
      droite.afficheLigne();
    }
  }
  public void afficheCarteTout(int x){
    xi = x; légende = new GString();
    afficheCarteTout();
  }
  public void afficheCarteTout(){
    this.afficheCarteLigne();
    System.out.println();
    if(bas != null){
      bas.afficheCarteTout();
    }
  }
  public void afficheCarteLigne(){
    this.afficheToiRéduit();
    if(contenu.length() >= 2){xi++;}
    if (droite != null){
      droite.afficheCarteLigne();
    }
  }
  public void afficheToiRéduit(){
    int taille = 4;
    int nbrDElementSurCase = contenu.getNbrDElementSurCase();
    String sr = "";
    if (nbrDElementSurCase == 0){
      sr = "-";
    }else if(nbrDElementSurCase == 1){
      if(contenu.getFere() != null){
        sr = "F"+contenu.getFere().getId();
      }else if (contenu.getGc().getDébut() != null){
        sr = sr + contenu.getGc().getDébut().getContenu().getId();
      }else{
        sr = sr +"G"+contenu.getGg().getDébut().getContenu().getId();
      }
    }else{
      sr = ascii.getNuméroationEnAbcd(xi);
      // ajout dans la légende.
      String s = sr +":";
      if(contenu.getFere() != null){
        s = s + "F"+contenu.getFere().getId()+", ";
      }
      s = s + contenu.getGc().toString();
      s = s + contenu.getGg().toString();
      légende.ajouter(s);
    }
    while (sr.length()<taille){
      sr = sr + " ";
    }
    System.out.print(sr);
  }
  public void tourCases(){
    tourLigneCases();
    if(bas != null){
      bas.tourCases();
    }
  }
  public void tourLigneCases(){
    contenu.actualisationNourritureInsecte();
    if(Main.getPartie().getAppartionGraine()){
      contenu.actualisationGraine(this);
    }
    if (droite != null){
      droite.tourLigneCases();
    }
  }
  public void ajouter(Case c){
    CCase cc = this;
    while (cc.getDroite() != null){
      cc = cc.getDroite();
    }
    cc.setDroite(new CCase(c));
  }
  public CCase nul(int x, int y){
    //erreur.erreur("La case ("+x+";"+y+") est null !","CCase.nul");
    return null;
  }
}
