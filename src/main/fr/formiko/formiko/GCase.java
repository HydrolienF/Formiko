package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.math.allea;
import fr.formiko.usuel.image.Img;
import fr.formiko.usuel.image.image;
import java.awt.Image;
import fr.formiko.usuel.image.ThImage;
import fr.formiko.usuel.Temps;
import java.io.Serializable;

public class GCase implements Serializable{
  public CCase début;
  public CCase actuelle;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public GCase(int horizontale, int verticale){
    if(horizontale < 0 || verticale < 0){ erreur.erreur("Impossible de créer une carte si petite","GCase.GCase","la carte la plus petite possible a été créée."); horizontale = 1; verticale = 1;}

    début = new CCase(new Case(0,0));
    ajouterDroite(horizontale-1, début);
    int d = 1; CCase début2;
    while (d < verticale){
      début2 = new CCase(new Case(0,d)); d++;
      ajouterDroite(horizontale-1, début2);
      fusionnnerLigne(début2);
    }
  }
  public GCase(int taille){
    this(taille, taille);
  }
  public GCase(){}
  // GET SET --------------------------------------------------------------------
  public CCase getDébut(){ return début;}
  public String getDim(){ return getWidth()+";"+getHeight();}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    if (début==null){erreur.erreur("La carte est vide","Gcase.afficheToi");return "";}
    return début.toString();
  }
  public CCase getCCase(int x, int y){
    if(début==null){
      return null;
    }else{
      if (x==0 && y==0){
        return début;
      }
    }
    return début.getCCase(x,y);
  }
  public CCase getCCase(Point p){
    return getCCase(p.getX(),p.getY());
  }
  public int getNbrX(){
    if (début== null){ return 0;}
    return début.getNbrX();
  }public int getWidth(){return getNbrX();}
  public int getNbrY(){
    if (début== null){ return 0;}
    return début.getNbrY();
  }public int getHeight(){return getNbrY();}
  public int getNbrDeCase(){
    return getNbrX()*getNbrY();
  }public int length(){ return getNbrDeCase();}
  public CCase getCCaseAlléa(){
    if (début== null){ erreur.erreurGXVide("GCase"); return null;}
    return getCCase(allea.getAlléa(this.getNbrX()), allea.getAlléa(this.getNbrY()));
  }
  public void setTypes(String t[]){
    if (début== null){ erreur.erreurGXVide("GCase"); return;}
    début.setTypes(t);
  }
  public Img getImg(){
    Img herbe = new Img(image.getImage("herbe"));
    Img mousse = new Img(image.getImage("mousse"));
    Img sab = new Img(image.getImage("sable"));
    Img terre = new Img(image.getImage("terre"));
    mousse.changerPixelTransparent(terre.getPixel(0,0));
    Img sable = new Img(500,500);
    sable.changerPixel(sable.getPixel(0,0),sab.getPixel(0,0));//tt les pixel deviènnent des pixel de la couleur de sable.
    int leni=getWidth(); int lenj=getHeight();
    Img ie = new Img(leni*500,lenj*500);
    System.out.println(ie.getWidth()+"   "+ie.getHeight());
    Img ti[] = new Img[4];
    ti[1]=herbe;ti[2]=mousse;ti[3]=sable;
    debug.débogage("add de "+(leni*lenj)+" éléments");
    //ThImage tth[] = new ThImage[leni];
    for(int i=0;i<leni;i++){
      //tth[i] = new ThImage(this,ie,i,ti,lenj,leni);
      //tth[i].start();
      for (int j=0;j<lenj;j++ ) {
        System.out.println("éléments n°"+(i*lenj+j)+"/"+(leni*lenj));
        ie.add(i*500,j*500,ti[this.getCCase(i,j).getContenu().getType()]);
      }
    }
    ie.actualiserImage();
    return ie;
  }
  public void afficheCarte(){
    if (début==null){
      erreur.erreur("La carte est vide","Gcase.afficheCarte");
    }else{
      début.afficheCarteTout(1);
      début.affLégende();
    }
  }
  public void ajouterDroite(int x, CCase débutDeLaLigne){
    debug.débogage("Création d'une ligne");
    int k = 1; CCase temp; actuelle = débutDeLaLigne;
    while (x>0){
      temp = new CCase(new Case(k,actuelle.getContenu().getPoint().getY())); k++;
      actuelle.setDroite(temp);
      temp.setGauche(actuelle);
      actuelle = temp;
      x--;
    }
  }
  public void fusionnnerLigne(CCase débutDeLaLigne2){
    CCase débutDeLaLigne = début;
    while (débutDeLaLigne.getBas() != null){
      débutDeLaLigne = débutDeLaLigne.getBas();
    }
    CCase tempHaut = débutDeLaLigne;
    CCase tempBas = débutDeLaLigne2;
    while(tempHaut != null && tempBas != null){
      tempHaut.setBas(tempBas);
      tempBas.setHaut(tempHaut);
      tempHaut = tempHaut.getDroite();
      tempBas = tempBas.getDroite();
    }
  }
  public void tourCases(){
    if (début==null){
      erreur.erreur("La carte est vide","Gcase.tourCases");
    }else{
      début.tourCases();
    }
  }
  public void ajouter(Case c){
    if (début==null){début = new CCase(c);return;}
    début.ajouter(c);
  }
}
