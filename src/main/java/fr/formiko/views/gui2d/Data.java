package fr.formiko.views.gui2d;

import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Pheromone;
import fr.formiko.formiko.Point;
import fr.formiko.usuel.Folder;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.Img;
import fr.formiko.usuel.images.Pixel;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.structures.ImageTree;
import fr.formiko.usuel.types.str;

import java.awt.Color;
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
  private BufferedImage tIF[];
  // private BufferedImage tII[][];
  private BufferedImage tG[][];
  private BufferedImage tF[][];
  // private BufferedImage antColor[];
  private BufferedImage antLeg[];
  private BufferedImage map;
  //ini (this var sould not be modify in an other place than here.)
  private BufferedImage imgNullIni;
  private BufferedImage selectionneeIni; private BufferedImage fereIni;
  private BufferedImage cNuageuseIni,cSombreIni;
  private BufferedImage bIni[];
  private BufferedImage tICarteIni[];
  private BufferedImage tIFIni[];
  // private BufferedImage tIIIni[][];
  private BufferedImage tGIni[][];
  private BufferedImage tFIni[][];
  // private BufferedImage antColorIni[];
  private BufferedImage antLegIni[];
  //image data
  private ImageTree imageTree;
  private ImageTree imageTreeIni;
  //PanneauAction
  private BufferedImage tImage [];
  //PanneauZoom
  private Image [] tIBZoom;
  private boolean initialisationFX;
  //PanneauChargement
  private BufferedImage imageChargement;
  //PanneauActionInf / Sup
  private Image backgroundPAI;
  private Image backgroundPAS;

  //PanneauMenu
  private Color buttonColor = new Color(81, 252, 0, 100);
  private Color buttonActivateColor = Color.YELLOW;
  private Color buttonFocusColor = new Color(248, 152, 29, 100);
  private Color buttonDisableColor = new Color(56, 56, 56, 100);

  private Color tButtonColor[];

  private Color buttonColorWithoutAlpha = new Color(81, 252, 0);
  private Color buttonDisableColorWithoutAlpha = new Color(56, 56, 56);
  private Color buttonFocusColorWithoutAlpha = new Color(248, 152, 29);
  private Color butonBorderColor = new Color(19, 161, 14);

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *Main constructor.
  *@version 1.18
  */
  public void Data(){
    initialisationFX=false;
  }
  // GET SET -------------------------------------------------------------------
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
  // public BufferedImage [][] getTII(){return tII;}
  public BufferedImage [][] getTG(){return tG;}
  public BufferedImage [][] getTF(){return tF;}
  public BufferedImage getMap(){return map;}
  //PanneauAction
  public BufferedImage [] getTImage(){return tImage;}
  //PanneauChargement
  public BufferedImage getImageChargement(){return imageChargement;}
  //imageIni
  public void setImageIniForNewGame(boolean b){imageIniForNewGame=b;}
  //PanneauActionInf / Sup
  public Image getBackgroundPAI(){return backgroundPAI;}
  public Image getBackgroundPAS(){return backgroundPAS;}
  public Color getButtonColor(){return buttonColor;}
  public Color getButtonDisableColor(){return buttonDisableColor;}
  public Color getButtonFocusColor(){return buttonFocusColor;}
  public Color getButtonActivateColor(){return buttonActivateColor;}

  public Color getButtonColorWithoutAlpha(){return buttonColorWithoutAlpha;}
  public Color getButtonDisableColorWithoutAlpha(){return buttonDisableColorWithoutAlpha;}
  public Color getButtonFocusColorWithoutAlpha(){return buttonFocusColorWithoutAlpha;}
  public Color getButonBorderColor() {return butonBorderColor;}
  public Color getButtonColor(int colorId){
    if(tButtonColor==null){
      iniTButtonColor();
    }
    return tButtonColor[colorId];
  }
  // FUNCTIONS -----------------------------------------------------------------
  public void iniTButtonColor(){
    tButtonColor = new Color[4];
    tButtonColor[0]=new Color(81, 252, 0);
    tButtonColor[1]=Color.YELLOW;
    tButtonColor[2]=new Color(248, 152, 29);
    tButtonColor[3]=new Color(56, 56, 56);
  }
  /**
  *{@summary Return the Image that fit to a Creature.}
  *@param c the Creature to represent.
  *@version 2.6
  */
  public BufferedImage getCreatureImage(Creature c){
    return imageTree.getCreatureImage(c);
  }
  /**
  *{@summary Return an array with all images use to draw an ant.}<br>
  *This image are sorted by order to daw.
  *<ul>
  *<li>Legs
  *<li>Body
  *<li>Color of the thorax
  *<li>Wings
  *</ul>
  *@version 2.1
  */
  public BufferedImage [] getAntImage(Fourmi f){
    int idEspece = f.getEspece().getId();
    int stade = f.getStade();
    Pheromone ph = f.getPheromone();
    int imageNumber = 9;
    int widthImage = tIF[idEspece].getWidth();
    int heightImage = tIF[idEspece].getHeight();
    BufferedImage tBi [] = new BufferedImage[imageNumber];
    Point tp [] = new Point[imageNumber];
    int tRotation [] = new int[imageNumber];
    Img imgColor = null;
    double diffX=0;
    double diffY=0;
    int k=0;
    if(stade==0){
      diffX = 2;// difference between fixation point & start of the image.
      diffY = 2;
      //to have full images.
      // widthImage*=1.2;
      // heightImage*=1.2;
      // diffX*=1.2;
      // diffY*=1.2;
      //Basic point & rotation
      tp[0] = new Point(52,55);
      tp[1] = new Point(56,46);
      tp[2] = new Point(53,38);
      // for (int i=0; i<3; i++) {
      //   tp[i].setX((int)(tp[i].getX()*1.1));
      //   tp[i].setY((int)(tp[i].getY()*1.1));
      // }
      tRotation[0]=-17;
      tRotation[1]=-45;
      tRotation[2]=-75;
      //op° for modify basic point & rotation.
      for (int i=3; i<6; i++) { //The 3 left legs will be flip, so we just need to copy opperation as the other 1.
        tRotation[i]=tRotation[i-3];
        tp[i] = tp[i-3].clone();
      }
      for (int i=0; i<6; i++) {
        if(tp[i]!=null){
          tp[i] = new Point((int)((tp[i].getX()-diffX)*widthImage)/100, (int)((tp[i].getY()-diffY)*widthImage)/100);
        }
      }
      try {
        Point rotate = Panneau.getView().getPc().getMovingObjectRotation(f.getId());
        for (int i=0; i<6; i+=2) {
          tRotation[i] += rotate.getX();
        }
        for (int i=1; i<6; i+=2) {
          tRotation[i] += -rotate.getX();
        }
      }catch (Exception e) {}

      //get images
      for (int i=0; i<6; i++) {
        try{tBi[k++] = antLeg[idEspece];}catch (Exception e) {antLeg[k++] = tIF[0];}
      }
      //TODO #246 use a diferent image depending of stade.
      try {tBi[k++] = tIF[idEspece];} catch (Exception e) {tBi[k++] = tIF[0];}
      // if(Main.getOp().getAntColorLevel()>0){
      //   try {imgColor=new Img(antColor[idEspece]);} catch (Exception e) {imgColor=new Img(antColor[0]);}
      // }
    }else{
      k=6;
      tBi[k++]=getTF()[0][3+stade];
    }
    if(imgColor!=null){
      tBi[k++]=image.changeColor(imgColor, ph);
    }
    if(stade==0){
      //TODO #246 add wings for queen.
      for(int i=0; i<imageNumber; i++){
        if(i<6){
          tBi[i] = image.translateImage(tBi[i], widthImage/2, heightImage/2, widthImage, heightImage);
        }
        // else{
        //   tBi[i] = image.translateImage(tBi[i], 0, 0, widthImage, heightImage);
        // }
        if(tRotation[i]!=0){
          // System.out.println((diffX+(widthImage/2))+" "+ (diffY+(heightImage/2)) +"     "+ tRotation[i]);
          tBi[i] = image.rotateImage2(tBi[i], tRotation[i], (int)(diffX+(widthImage/2)), (int)(diffY+(heightImage/2)));
        }
        if(tp[i]!=null){
          tBi[i] = image.translateImage(tBi[i], tp[i].getX()-(widthImage/2), tp[i].getY()-(heightImage/2), widthImage, heightImage);
        }
      }
      // for(int i=0; i<imageNumber; i++){
      //   if(i<6){
      //     tBi[i] = image.translateImage(tBi[i], (int)(widthImage/2.4), (int)(heightImage/2.4), widthImage, heightImage);
      //   }
      //   if(tRotation[i]!=0){
      //     // System.out.println((diffX+(widthImage/2))+" "+ (diffY+(heightImage/2)) +"     "+ tRotation[i]);
      //     tBi[i] = image.rotateImage2(tBi[i], tRotation[i], diffX+((int)(widthImage/2.4)), diffY+((int)(heightImage/2.4)));
      //   }
      //   if(tp[i]!=null){
      //     tBi[i] = image.translateImage(tBi[i], tp[i].getX()-((int)(widthImage/2.4)), tp[i].getY()-((int)(heightImage/2.4)), widthImage, heightImage);
      //   }
      // }
      for (int i=3; i<6; i++) {
        tBi[i] = image.flipImage(tBi[i], false);
      }
    }
    return tBi;
  }

  //public class Controleur{
    //iniImage etc
    /**
    *{@summary Load image in map resolution.}<br>
    *If the original image have'nt been load, it will call chargerImagesIni.<br>
    *The images defined here have the rigth dimention for being used on the map.<br>
    *@version 2.6
    */
    public void chargerImages(){
      debug.débogage("chargement des images a la bonne taille.");
      chargerImagesIni();
      Main.startCh();
      int tailleFourmi = (tailleDUneCase*4)/5;
      erreur.info("Update Image to size "+tailleDUneCase);//@a
      imgNull = image.resize(imgNullIni,tailleDUneCase);
      selectionnee = image.resize(selectionneeIni,tailleDUneCase);
      tICarte=getScaledInstance(tICarteIni, tailleDUneCase);
      // tIF=getScaledInstance(tIFIni, tailleFourmi);
      // tII=getScaledInstance(tIIIni, tailleFourmi,2);//les insectes
      // tF=getScaledInstance(tFIni, tailleFourmi,1);//les Fourmis au différent stade.
      // antColor=getScaledInstance(antColorIni, tailleFourmi,0);//les Fourmis au différent stade.
      // antLeg=getScaledInstance(antLegIni, tailleFourmi/2,0);//les Fourmis au différent stade.
      imageTree = ImageTree.getScaledInstanceFromTree(imageTreeIni, tailleFourmi);
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
        // tIIIni = chargerTX("I");
        tFIni = chargerTX("F",3,(byte)0,-3);
        // iniAntColorIni();
        antLegIni = image.getImages("FLeg",image.getNbrImages("FLeg"),(byte)0);
        imageTreeIni = ImageTree.folderToTree(Main.getFolder().getFolderStable()+Main.getFolder().getFolderImages()+"Creature/");
        // antFAFIni = image.getImages("FAF",image.getNbrImages("FAF"),(byte)0);
        // antFASIni = image.getImages("FAS",image.getNbrImages("FAS"),(byte)0);
        tGIni = chargerTX("seed");
        fereIni = image.getImage("antnest");//.getScaledInstance(tailleDUneCaseBase/2, tailleDUneCaseBase/2,scale);
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
        //TODO #246 & #247 add various image for every species.
        tIFIni = new BufferedImage[1];
        tIFIni[0] = image.getImage("F0");
      }
      imageIniForNewGame=true;
    }
    /***
    *{@summary Load antColorIni.}
    *antColorIni will be set to max in alpha if it need.
    *@version 2.2
    */
    // private void iniAntColorIni(){
    //   antColorIni = image.getImages("FCol",image.getNbrImages("FCol"),(byte)0);
    //   int len = antColorIni.length;
    //   if(Main.getOp().getAntColorLevel()>1){
    //     for (int i=0; i<len; i++) {
    //       Img img = new Img(antColorIni[i]);
    //       img.supprimerLaTransparencePartielle(1);
    //       img.actualiserImage();
    //       antColorIni[i] = img.getImage();
    //     }
    //   }
    // }
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
    @param name Name of de group. Every image will start by this name.
    *@version 1.18
    */
    public BufferedImage [][] chargerTX(String name, int x, byte y, int début){
      return image.getImagess(name,x,(byte)début);
    }
    public BufferedImage [][] chargerTX(String name, int x, byte y){ return chargerTX(name,x,y,0);}
    public BufferedImage [][] chargerTX(String name, int x){ return chargerTX(name,x,(byte)0);}
    public BufferedImage [][] chargerTX(String name){return chargerTX(name,image.getNbrImages(name));}

    /**
    *Create a background image from tI1 and tI2 images.
    *@version 1.42
    */
    public void iniBackgroundMapImage(){
      if(!Main.getView().getActionGameOn()){return;}
      Main.startCh();
      // if(Panneau.getView().getPc()==null){erreur.erreur("Map panel is null");}
      Panneau.getView().getPc().updateSize();
      Img img = new Img(Panneau.getView().getPc().getWidth(),Panneau.getView().getPc().getHeight());
      //if(img.)
      Img img2 = null;
      int xCase = Panneau.getView().getPc().getXCase();
      int yCase = Panneau.getView().getPc().getYCase();
      try {
        // if(getTailleDUneCase()<1){erreur.erreur("Case size is <1");}
        for (int i=0;i<xCase ;i++ ) {
          for (int j=0;j<yCase ;j++ ) {
            int xT = i*getTailleDUneCase(); int yT = j*getTailleDUneCase();
            Case c=null;
            try {
              c = Main.getGc().getCCase(i+Panneau.getView().getPc().getPosX(),j+Panneau.getView().getPc().getPosY()).getContent();
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
    *Return a scaled BufferedImage
    *@version 2.1
    */
    public BufferedImage getScaledInstance(BufferedImage bi, int dim, int b){
      BufferedImage r = null;
      if(b==0){//par défaut
        r=image.resize(bi,dim);
      }
      // else if(b==1){//pour les fourmis.
      //   int idEspece = 0;
      //   // int stade = i-3;
      //   r=image.resize(bi,image.taille(idEspece, stade,dim));
      // }else if(b==2){//pour les insectes
      //   // int idEspece = i+100;
      //   int stade = 0;
      //   r=image.resize(bi,image.taille(idEspece, stade,dim));
      // }
      return r;
    }public BufferedImage getScaledInstance(BufferedImage bi, int dim){return getScaledInstance(bi,dim,0);}
    /**
    *Return a scaled BufferedImage []
    *@version 1.18
    */
    public BufferedImage [] getScaledInstance(BufferedImage ti[], int dim, int b){
      int lenr = 0;
      if(ti!=null){
        lenr=ti.length;
      }
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
          //TODO #421 do the same into the imageTreeIni.
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
      loadBackgroundPAI();
      loadBackgroundPAS();
    }
  }
  /**
  *Load backgroundPAI
  *@version 1.46
  */
  private void loadBackgroundPAI(){
    backgroundPAI = image.getImage("backgroundPAI");
    backgroundPAI = backgroundPAI.getScaledInstance(Main.getDimX(), Panneau.getView().getPa().getHeight(),Image.SCALE_SMOOTH);
  }
  /**
  *Load backgroundPAS
  *@version 1.46
  */
  private void loadBackgroundPAS(){
    backgroundPAS = image.getImage("backgroundPAS");
    backgroundPAS = backgroundPAS.getScaledInstance(Panneau.getView().getPa().getHeight(), Panneau.getView().getPa().getHeight(),Image.SCALE_SMOOTH);
  }
  /**
  *Load images for PanneauAction without background
  *@version 1.18
  */
  private void chargerTImage(){
    int tailleBouton = Panneau.getView().getPa().getTailleBouton();
    tImage = image.getImages("desc");
    for (int i=0;i<10 ;i++ ) {
      tImage[i] = image.resize(tImage[i],tailleBouton);
    }
  }
  /**
  *Load images for PanneauAction with background
  *@version 1.18
  */
  private void chargerTImageAvecFond(Pixel pi){
    int tailleBouton = Panneau.getView().getPa().getTailleBouton();
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
  *{@summary Turn the arrow for PanneauZoom.}<br>
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
  *{@summary Load a loading image for PanneauChargement.}<br>
  *@version 1.32
  */
  public boolean loadImageChargement(){
    String mapName = Main.getMap().getMapName();
    mapName = str.sToSMaj(mapName);
    imageChargement=null;
    if(mapName!=null && !mapName.equals("")){
      imageChargement=image.getImage("loading"+mapName,false);
      if(imageChargement!=null){
        imageChargement=image.resize(imageChargement,Main.getDimX(),Main.getDimY());
      }
    }
    //if it haven't been load yet we try to load any image name chargementi.png or .jpj.
    if(imageChargement==null){
      int x = allea.getAlléa(image.getNbrImages("loading"));
      imageChargement=image.getImage("loading"+x);
      imageChargement=image.resize(imageChargement,Main.getDimX(),Main.getDimY());
      return true;
    }
    return false;
  }
}
    // //début test
    // BufferedImage before = Main.getData().getB()[iconeId];
    // int w = before.getWidth();
    // int h = before.getHeight();
    // BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    // AffineTransform at = new AffineTransform();
    // at.scale(2.0, 2.0);
    // AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    // after = scaleOp.filter(before, after);
    // //fin test
