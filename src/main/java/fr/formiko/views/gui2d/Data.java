package fr.formiko.views.gui2d;

import fr.formiko.formiko.Case;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.Img;
import fr.formiko.usuel.images.Pixel;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.types.str;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
*{@summary Contain all data (images) that will be used by the graphic interface.}
*@author Hydrolien
*@version 1.18
*/
public class Data {
  private int tailleDUneCase; // entre 10 et 500.
  //private final int tailleDUneCaseBase = 500;
  //private int scale = Image.SCALE_SMOOTH;
  private boolean imageIni;
  private boolean imageIniForNewGame;
  //image
  private BufferedImage imgNull;
  private BufferedImage selectionnee; private BufferedImage fere;
  private BufferedImage cNuageuse,cSombre;
  private BufferedImage b[];
  private BufferedImage tICarte[];
  private BufferedImage tIF[][];
  private BufferedImage tII[][];
  private BufferedImage tG[][];
  private BufferedImage tF[][];
  private BufferedImage map;
  //ini (this var sould not be modify in an other place than here.)
  private BufferedImage imgNullIni;
  private BufferedImage selectionneeIni; private BufferedImage fereIni;
  private BufferedImage cNuageuseIni,cSombreIni;
  private BufferedImage bIni[];
  private BufferedImage tICarteIni[];
  private BufferedImage tIFIni[][];
  private BufferedImage tIIIni[][];
  private BufferedImage tGIni[][];
  private BufferedImage tFIni[][];
  //PanneauAction
  private BufferedImage tImage [];
  //PanneauZoom
  private Image [] tIBZoom;
  private boolean initialisationFX;
  //PanneauChargement
  private BufferedImage imageChargement;

  // CONSTRUCTEUR ---------------------------------------------------------------
  /**
  *Main constructor.
  *@version 1.18
  */
  public void Data(){
    initialisationFX=false;
  }
  // GET SET --------------------------------------------------------------------
  //ini
  public int getTailleDUneCase(){return tailleDUneCase;}
  public void setTailleDUneCase(int x){tailleDUneCase=x;}
  public BufferedImage getImgNull(){return imgNull;}
  public BufferedImage getSelectionnee(){return selectionnee;}
  public BufferedImage getFere(){return fere;}
  public BufferedImage getCNuageuse(){return cNuageuse;}
  public BufferedImage getCSombre(){return cSombre;}
  public BufferedImage [] getB(){return b;}
  public BufferedImage [] getTICarte(){return tICarte;}
  public BufferedImage [][] getTIF(){return tIF;}
  public BufferedImage [][] getTII(){return tII;}
  public BufferedImage [][] getTG(){return tG;}
  public BufferedImage [][] getTF(){return tF;}
  public BufferedImage getMap(){return map;}
  //PanneauAction
  public BufferedImage [] getTImage(){return tImage;}
  //PanneauChargement
  public BufferedImage getImageChargement(){return imageChargement;}
  //imageIni
  public void setImageIniForNewGame(boolean b){imageIniForNewGame=b;}
  // Fonctions propre -----------------------------------------------------------

  //public class Controleur{
    //iniImage etc
    /**
    *{@summary Load image in map resolution.}<br>
    *If the original image have'nt been load, it will call chargerImagesIni.<br>
    *The images defined here have the rigth dimention for being used on the map.<br>
    *@version 1.18
    */
    public void chargerImages(){
      debug.débogage("chargement des images a la bonne taille.");
      chargerImagesIni();
      Main.startCh();
      int tailleFourmi = (tailleDUneCase*4)/5;
      imgNull = image.resize(imgNullIni,tailleDUneCase);
      selectionnee = image.resize(selectionneeIni,tailleDUneCase);
      tICarte=getScaledInstance(tICarteIni, tailleDUneCase);
      tIF=getScaledInstance(tIFIni, tailleFourmi);
      tII=getScaledInstance(tIIIni, tailleFourmi,2);//les insectes
      tF=getScaledInstance(tFIni, tailleFourmi,1);//les Fourmis au différent stade.
      tG=getScaledInstance(tGIni, tailleFourmi);
      fere = image.resize(fereIni,tailleDUneCase/2);
      cNuageuse = image.resize(cNuageuseIni,tailleDUneCase);
      cSombre = image.resize(cSombreIni,tailleDUneCase);
      int lenb = bIni.length;
      b=getScaledInstance(bIni,tailleDUneCase/2);
      Main.endCh("chargerImages");
    }
    /**
    *{@summary Load image in full resolution.}<br>
    *Image need to be load in full resolution 1 time only. If it have alredy be done the function will do nothing.
    *@version 1.33
    */
    public void chargerImagesIni(){
      if(!imageIni){
        Main.startCh();
        imgNullIni = image.getImage("null");//.getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
        selectionneeIni = image.getImage("selectionnee");//.getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
        chargerTI();
        tIIIni = chargerTX("I");
        tFIni = chargerTX("fourmi",3);
        tGIni = chargerTX("graine");
        fereIni = image.getImage("fourmiliere");//.getScaledInstance(tailleDUneCaseBase/2, tailleDUneCaseBase/2,scale);
        cNuageuseIni = image.getImage("cNuageuse");//.getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
        cSombreIni = image.getImage("cSombre");//.getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
        bIni = image.getImages("b"); int lenb = bIni.length;
        /*for (int i=0;i<lenb ;i++ ) {
          bIni[i]=bIni[i].getScaledInstance(tailleDUneCaseBase/2, tailleDUneCaseBase/2,scale);
        }*/
        Main.endCh("chargerImagesIni");
      }
      imageIni=true;
      if(!imageIniForNewGame){
        tIFIni = chargerTX("F",Main.getNbrDeJoueur(),(byte)0,1);
      }
      imageIniForNewGame=true;
    }
    /**
    *Load Case image
    *@version 1.18
    */
    public void chargerTI(){
      tICarteIni = new BufferedImage [3];
      tICarteIni[0]=image.getImage("herbe");
      tICarteIni[1]=image.getImage("mousse");
      tICarteIni[2]=image.getImage("sable");
    }
    /**
    *{@summary Load a group of BufferedImage that starts with a similar name.}<br>
    *see image.getImagess() for more informations.
    @param nom Name of de group. Every image will start by this name.
    *@version 1.18
    */
    public BufferedImage [][] chargerTX(String nom, int x, byte y, int début){
      return image.getImagess(nom,x,(byte)début);
    }
    public BufferedImage [][] chargerTX(String nom, int x, byte y){ return chargerTX(nom,x,y,0);}
    public BufferedImage [][] chargerTX(String sn, int x){ return chargerTX(sn,x,(byte)0);}
    public BufferedImage [][] chargerTX(String sn){return chargerTX(sn,image.getNbrImages(sn));}

    /**
    *Create a background image from tI1 and tI2 images.
    *@version 1.42
    */
    public void iniBackgroundMapImage(){
      if(!Main.getView().getActionGameOn()){return;}
      Main.startCh();
      // if(Main.getPc()==null){erreur.erreur("Map panel is null");}
      Main.getPc().actualiserSize();
      Img img = new Img(Main.getPc().getWidth(),Main.getPc().getHeight());
      //if(img.)
      Img img2 = null;
      int xCase = Main.getPc().getXCase();
      int yCase = Main.getPc().getYCase();
      try {
        // if(getTailleDUneCase()<1){erreur.erreur("Case size is <1");}
        for (int i=0;i<xCase ;i++ ) {
          for (int j=0;j<yCase ;j++ ) {
            int xT = i*getTailleDUneCase(); int yT = j*getTailleDUneCase();
            Case c=null;
            try {
              c = Main.getGc().getCCase(i+Main.getPc().getPosX(),j+Main.getPc().getPosY()).getContenu();
            }catch (Exception e) {erreur.erreur("case is null");}
            try {
              img2 = new Img(tICarte[c.getType()-1]);
            }catch (Exception e) {
              img2 = new Img(imgNull);
              erreur.alerte("A map image can't be load");
            }
            try {
              img.add(xT,yT,img2);
            }catch (Exception e) {
              erreur.erreur("img.add() fail");
            }
          }
        }
        try {
          img.actualiserImage();
          map = img.getBi();
        }catch (Exception e) {
          erreur.erreur("Map image can't be update");
          map=null;
        }
      }catch (Exception e) {
        erreur.erreur("iniBackgroundMapImage fail");
        map=null;
      }
      Main.endCh("iniBackgroundMapImage");
    }

    //getScaledInstance.
    /**
    *Return a scaled BufferedImage []
    *@version 1.18
    */
    public BufferedImage [] getScaledInstance(BufferedImage ti[],int dim, int b){
      int lenr = ti.length;
      BufferedImage r [] = new BufferedImage[lenr];
      for (int i=0;i<lenr ;i++ ) {
        if(b==0){//par défaut
          r[i]=image.resize(ti[i],dim);
        }else if(b==1){//pour les fourmis.
          int idEspece = 0;
          int stade = i-3;
          r[i]=image.resize(ti[i],image.taille(idEspece, stade,dim));
        }else if(b==2){//pour les insectes
          int idEspece = i+100;
          int stade = 0;
          r[i]=image.resize(ti[i],image.taille(idEspece, stade,dim));
        }
      }
      return r;
    }public BufferedImage [] getScaledInstance(BufferedImage ti[],int dim){return getScaledInstance(ti,dim,0);}
    /**
    *Return a scaled BufferedImage [][]
    *@version 1.18
    */
    public BufferedImage [][] getScaledInstance(BufferedImage ti[][],int dim, int b){
      int lenr = ti.length;
      BufferedImage r [][] = new BufferedImage[lenr][];
      for (int i=0;i<lenr ;i++ ) {
        r[i]=getScaledInstance(ti[i],dim,b);
      }
      return r;
    }public BufferedImage [][] getScaledInstance(BufferedImage ti[][],int dim){return getScaledInstance(ti,dim,0);}

  //}
  //PanneauAction
  /**
  *Load graphics for PanneauAction
  *@version 1.18
  */
  public synchronized void chargerTIPanneauAction(){
    if (tImage==null){
      if(Main.getPiFond()!=null){
        chargerTImageAvecFond(Main.getPiFond());
      }else{
        chargerTImage();
      }
      PanneauActionInf.chargerFond();
      PanneauActionSup.chargerFond();
    }
  }
  /**
  *Load images for PanneauAction without background
  *@version 1.18
  */
  public void chargerTImage(){
    int tailleBouton = Main.getPa().getTailleBouton();
    tImage = image.getImages("desc");
    for (int i=0;i<10 ;i++ ) {
      tImage[i] = image.resize(tImage[i],tailleBouton);
    }
  }
  /**
  *Load images for PanneauAction with background
  *@version 1.18
  */
  public void chargerTImageAvecFond(Pixel pi){
    int tailleBouton = Main.getPa().getTailleBouton();
    for (int k=0;k<10 ;k++) {
      Img img = new Img("desc"+k);
      img.changerPixelTransparent(pi);
      img.sauvegarder("temporaire/desc"+k+".png");
    }
    tImage = image.getImages("temporaire/desc");
    for (int i=0;i<10 ;i++ ) {
      tImage[i] = image.resize(tImage[i],tailleBouton);
    }
  }
  //PanneauZoom
  /**
  *Load images for PanneauZoom
  *@version 1.18
  */
  public Image [] chargerTIBZoom(){
    tIBZoom = new Image[9];
    if(!initialisationFX && !Main.getGarderLesGraphismesTourné()){tournerLesFleches();}
    int tailleBouton=Main.getbuttonSizeZoom();
    tIBZoom[0] = image.getImage("moins").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIBZoom[1] = image.getImage("fleche").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIBZoom[2] = image.getImage("plus").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIBZoom[3] = image.getImage("fleche1").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    //tourner +90°
    tIBZoom[4] = image.getImage("centrer").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIBZoom[5] = image.getImage("fleche2").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    //tourner -90°
    tIBZoom[6] = image.getImage("fleche3").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    //tourner +180°
    tIBZoom[7] = image.getImage("centrer").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIBZoom[8] = image.getImage("centrer").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    return tIBZoom;
  }
  /**
  *Turn the arrow for PanneauZoom
  *@version 1.18
  */
  public void tournerLesFleches(String nom){
    initialisationFX=true;
    Img f = new Img(nom);
    Img f1 = f.clone();
    f1.tourner(1);
    Img f2 = f.clone();
    f2.tourner(3);
    Img f3 = f.clone();
    f3.tourner(2);
    f2.sauvegarder("fleche2.png");
    f1.sauvegarder("fleche1.png");
    f3.sauvegarder("fleche3.png");
  }public void tournerLesFleches(){ tournerLesFleches("fleche");}

  /**
  *{@summary Loard a loading image for PanneauChargement.}
  *@version 1.32
  */
  public boolean loadImageChargement(){
    String mapName = Main.getMap().getMapName();
    mapName = str.sToSMaj(mapName);
    imageChargement=null;
    if(mapName!=null && !mapName.equals("")){
      imageChargement=image.getImage("chargement"+mapName,false);
      if(imageChargement!=null){
        imageChargement=image.resize(imageChargement,Main.getDimX(),Main.getDimY());
      }
    }
    //if it haven't been load yet we try to load any image name chargementi.png or .jpj.
    if(imageChargement==null){
      int x = allea.getAlléa(image.getNbrImages("chargement"));
      imageChargement=image.getImage("chargement"+x);
      imageChargement=image.resize(imageChargement,Main.getDimX(),Main.getDimY());
      return true;
    }
    return false;
  }
}
