package fr.formiko.views.gui2d;

import fr.formiko.formiko.Square;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Graine;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Pheromone;
import fr.formiko.formiko.images.FImageTree;
import fr.formiko.formiko.images.FImages;
import fr.formiko.usual.Folder;
import fr.formiko.usual.Point;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.images.Img;
import fr.formiko.usual.images.Pixel;
import fr.formiko.usual.maths.allea;
import fr.formiko.usual.maths.math;
import fr.formiko.usual.types.str;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
*{@summary Contain all data (images) that will be used by the graphic interface.}
*@author Hydrolien
*@lastEditedVersion 1.18
*/
public class Data {
  private int tailleDUneSquare; // entre 10 et 500.
  //private final int tailleDUneSquareBase = 500;
  //private int scale = Image.SCALE_SMOOTH;
  private boolean imageIni;
  private boolean imageIniForNewGame;
  //image
  private BufferedImage imgNull;
  private BufferedImage fere;
  private BufferedImage cSombre;
  private BufferedImage b[];
  private BufferedImage tICarte[];
  private BufferedImage tIF[];
  // private BufferedImage tII[][];
  private BufferedImage tG[];
  private BufferedImage tF[][];
  // private BufferedImage antColor[];
  private BufferedImage antLeg[];
  private BufferedImage map;
  private BufferedImage cloudMap;
  private BufferedImage loopArrow;
  private BufferedImage pick;
  //ini (this var sould not be modify in an other place than here.)
  private BufferedImage imgNullIni;
  private BufferedImage fereIni;
  private BufferedImage cSombreIni;
  private BufferedImage bIni[];
  private BufferedImage tICarteIni[];
  private BufferedImage tIFIni[];
  // private BufferedImage tIIIni[][];
  private BufferedImage tGIni[];
  private BufferedImage tFIni[][];
  // private BufferedImage antColorIni[];
  // private BufferedImage antLegIni[];
  //image data
  private FImageTree imageTree;
  private FImageTree imageTreeIni;
  private HashMap<String, BufferedImage> iconMap;
  // private map<String,BufferedImage> iconMapIni;
  //FPanelAction
  private BufferedImage tImage [];
  //FPanelZoom
  private Image [] tIBZoom;
  private boolean initialisationFX;
  //FPanelChargement
  private BufferedImage loadingImage;
  //FPanelActionInf / Sup
  private Image backgroundPAI;
  private Image backgroundPAS;
  /** Contains all not resize image. */
  private HashMap<String, BufferedImage> otherImages;

  //FPanelMenu
  private Color buttonColor = new Color(81, 252, 0, 100);
  private Color buttonActivateColor = Color.YELLOW;
  private Color buttonFocusColor = new Color(248, 152, 29, 100);
  private Color buttonDisableColor = new Color(56, 56, 56, 100);

  private Color tButtonColor[];
  private int lenTButtonColor;

  private Color buttonColorWithoutAlpha = new Color(81, 252, 0);
  private Color buttonDisableColorWithoutAlpha = new Color(56, 56, 56);
  private Color buttonFocusColorWithoutAlpha = new Color(248, 152, 29);
  private Color butonBorderColor = new Color(19, 161, 14);

  private int cptTaskDone;
  private int numberOfTask;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *Main constructor.
  *@lastEditedVersion 1.18
  */
  public void Data(){
    initialisationFX=false;
    setTailleDUneSquare(1);
  }
  // GET SET -------------------------------------------------------------------
  //ini
  public int getTailleDUneSquare(){return tailleDUneSquare;}
  public int getTailleIcon(){return (int)(getTailleDUneSquare()/3);}
  public void setTailleDUneSquare(int x){tailleDUneSquare=x;}
  public BufferedImage getImgNull(){return imgNull;}
  public BufferedImage getFere(){return fere;}
  public BufferedImage getCSombre(){return cSombre;}
  public BufferedImage [] getB(){return b;}
  public BufferedImage [] getTICarte(){return tICarte;}
  // public BufferedImage [][] getTII(){return tII;}
  public BufferedImage [] getTG(){return tG;}
  public BufferedImage [][] getTF(){return tF;}
  public BufferedImage getMap(){return map;}
  public BufferedImage getCloudMap(){return cloudMap;}
  public BufferedImage getLoopArrow(){return loopArrow;}
  public BufferedImage getPick(){return pick;}
  //FPanelAction
  public BufferedImage [] getTImage(){return tImage;}
  //FPanelChargement
  public BufferedImage getImageChargement(){return loadingImage;}
  //imageIni
  public void setImageIniForNewGame(boolean b){imageIniForNewGame=b;}
  //FPanelActionInf / Sup
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
  /**
  *{@summary Return color used by the buttons.}
  *@lastEditedVersion 2.7
  */
  public Color getButtonColor(int colorId){
    if(tButtonColor==null){
      iniTButtonColor();
    }
    if(colorId<0 || colorId>=lenTButtonColor){
      erreur.alerte("Can't get buttonColor by id "+colorId+" in "+lenTButtonColor+" colors aviable");
      return null;
    }
    return tButtonColor[colorId];
  }
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Initialize color used by the buttons.}
  *@lastEditedVersion 2.7
  */
  public void iniTButtonColor(){
    lenTButtonColor=9;
    tButtonColor = new Color[lenTButtonColor];
    tButtonColor[0]=new Color(81, 252, 0);//green
    tButtonColor[1]=Color.YELLOW;
    tButtonColor[2]=new Color(248, 152, 29);//orange
    tButtonColor[3]=Color.RED;
    tButtonColor[4]=Color.WHITE;
    tButtonColor[5]=new Color(56, 56, 56);//grey
    tButtonColor[6]=Color.BLACK;
    tButtonColor[7]=Color.BLUE;
    tButtonColor[8]=new Color(0,0,0,0);//nothing.
  }
  /**
  *{@summary Return an image from otherImages.}
  *Image from otherImages can be acces by there name.
  *@lastEditedVersion 2.18
  */
  public BufferedImage getImage(String name){
    if(otherImages==null){return null;}
    return otherImages.get(name);
  }
  /**
  *{@summary Return the Image that fit to a Creature.}
  *@param c the Creature to represent.
  *@lastEditedVersion 2.6
  */
  public BufferedImage getCreatureImage(Creature c){
    if(imageTree==null){
      erreur.erreur("imageTree is null");
      return null;
    }
    return imageTree.getCreatureImage(c, Main.getOp().getAntColorLevel());
  }
  /**
  *{@summary Return the Image that fit to a Seed.}
  *@param s the Seed to represent
  *@lastEditedVersion 2.18
  */
  public BufferedImage getGraineImage(Graine s){
    if(s==null){return null;}
    else{
      return getTG()[s.getType()];
    }
  }
  /**
  *{@summary Return an IconImage.}
  *@param name the name of the icon, the name of the file without ".png"
  *@lastEditedVersion 2.6
  */
  public BufferedImage getIconImage(String name){
    if(iconMap==null){return null;}
    if(name==null){return null;}
    return iconMap.get(name);
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
  *@lastEditedVersion 2.1
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
        Point rotate = FPanel.getView().getPc().getMovingObjectRotation(f.getId());
        for (int i=0; i<6; i+=2) {
          tRotation[i] += rotate.getX();
        }
        for (int i=1; i<6; i+=2) {
          tRotation[i] += -rotate.getX();
        }
      }catch (Exception e) {}

      //get images
      // for (int i=0; i<6; i++) {
      //   try{tBi[k++] = antLeg[idEspece];}catch (Exception e) {antLeg[k++] = tIF[0];}
      // }
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
      tBi[k++]=FImages.changeColor(imgColor, ph);
    }
    if(stade==0){
      //TODO #246 add wings for queen.
      for(int i=0; i<imageNumber; i++){
        if(i<6){
          tBi[i] = FImages.translateImage(tBi[i], widthImage/2, heightImage/2, widthImage, heightImage);
        }
        // else{
        //   tBi[i] = FImages.translateImage(tBi[i], 0, 0, widthImage, heightImage);
        // }
        if(tRotation[i]!=0){
          // erreur.println((diffX+(widthImage/2))+" "+ (diffY+(heightImage/2)) +"     "+ tRotation[i]);
          tBi[i] = FImages.rotateImage2(tBi[i], tRotation[i], (int)(diffX+(widthImage/2)), (int)(diffY+(heightImage/2)));
        }
        if(tp[i]!=null){
          tBi[i] = FImages.translateImage(tBi[i], tp[i].getX()-(widthImage/2), tp[i].getY()-(heightImage/2), widthImage, heightImage);
        }
      }
      // for(int i=0; i<imageNumber; i++){
      //   if(i<6){
      //     tBi[i] = FImages.translateImage(tBi[i], (int)(widthImage/2.4), (int)(heightImage/2.4), widthImage, heightImage);
      //   }
      //   if(tRotation[i]!=0){
      //     // erreur.println((diffX+(widthImage/2))+" "+ (diffY+(heightImage/2)) +"     "+ tRotation[i]);
      //     tBi[i] = image.rotateImage2(tBi[i], tRotation[i], diffX+((int)(widthImage/2.4)), diffY+((int)(heightImage/2.4)));
      //   }
      //   if(tp[i]!=null){
      //     tBi[i] = FImages.translateImage(tBi[i], tp[i].getX()-((int)(widthImage/2.4)), tp[i].getY()-((int)(heightImage/2.4)), widthImage, heightImage);
      //   }
      // }
      for (int i=3; i<6; i++) {
        tBi[i] = FImages.flipImage(tBi[i], false);
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
    *This function use multithreading to be faster.<br>
    *@lastEditedVersion 2.30
    */
    public synchronized void chargerImages(){
      chargerImagesIni();
      Main.startCh();
      int tailleFourmi = (getTailleDUneSquare()*4)/5;
      erreur.info("Update Image to size "+getTailleDUneSquare(),8);

      // new way
      List<Task> taskList = new ArrayList<Task>();

      taskList.add(new Task(() -> imgNull = FImages.resize(imgNullIni,getTailleDUneSquare())));
      taskList.add(new Task(() -> tICarte=getScaledInstance(tICarteIni, getTailleDUneSquare())));
      taskList.add(new Task(() -> imageTree = FImageTree.getScaledInstanceFromTree(imageTreeIni, tailleFourmi)));
      taskList.add(new Task(() -> tG=getScaledInstance(tGIni, tailleFourmi)));
      taskList.add(new Task(() -> fere = FImages.resize(fereIni,getTailleDUneSquare()/2)));
      taskList.add(new Task(() -> cSombre = FImages.resize(cSombreIni,getTailleDUneSquare())));
      taskList.add(new Task(() -> b=getScaledInstance(bIni,getTailleIcon())));

      launchTaskList(taskList);

      // imgNull = FImages.resize(imgNullIni,getTailleDUneSquare());
      // tICarte=getScaledInstance(tICarteIni, getTailleDUneSquare());
      // imageTree = FImageTree.getScaledInstanceFromTree(imageTreeIni, tailleFourmi);
      // tG=getScaledInstance(tGIni, tailleFourmi);
      // fere = FImages.resize(fereIni,getTailleDUneSquare()/2);
      // cSombre = FImages.resize(cSombreIni,getTailleDUneSquare());
      // b=getScaledInstance(bIni,getTailleIcon());

      // tIF=getScaledInstance(tIFIni, tailleFourmi);
      // tII=getScaledInstance(tIIIni, tailleFourmi,2);//les insectes
      // tF=getScaledInstance(tFIni, tailleFourmi,1);//les Fourmis au différent stade.
      // antColor=getScaledInstance(antColorIni, tailleFourmi,0);//les Fourmis au différent stade.
      // antLeg=getScaledInstance(antLegIni, tailleFourmi/2,0);//les Fourmis au différent stade.

      Main.endCh("chargerImages");
    }
    /**
    *{@summary Load image in full resolution.}<br>
    *Image need to be load in full resolution 1 time only. If it have alredy be done the function will do nothing.<br>
    *This function use multithreading to be faster.<br>
    *@lastEditedVersion 2.30
    */
    public synchronized void chargerImagesIni(){
      if(!imageIni){
        erreur.info("load images from files");
        Main.startCh();

        // new way
        List<Task> taskList = new ArrayList<Task>();

        taskList.add(new Task(() -> {imageTreeIni = new FImageTree();
            imageTreeIni.folderToTree(Main.getFolder().getFolderStable()+Main.getFolder().getFolderImages()+"Creature/");}));
        taskList.add(new Task(() -> {iconMap = FImages.getImagesAsMap(Main.getFolder().getFolderStable()+Main.getFolder().getFolderImages()+"icon/");
            iconMap = FImages.getScaledInstanceFromMap(iconMap, Main.getTailleElementGraphiqueY(30));}));
        taskList.add(new Task(() -> {chargerTI();}));
        taskList.add(new Task(() -> tFIni = chargerTX("F",3,(byte)0,-3)));
        taskList.add(new Task(() -> tGIni = FImages.getImages("seed",FImages.getNbrImages("seed"),(byte)0)));
        taskList.add(new Task(() -> bIni = FImages.getImages("b")));
        taskList.add(new Task(() -> {iniOtherImages();}));
        taskList.add(new Task(() -> fereIni = FImages.getImage("antnest")));
        taskList.add(new Task(() -> cSombreIni = FImages.getImage("cSombre")));
        taskList.add(new Task(() -> imgNullIni = FImages.getImage("null")));
        // taskList.add(new Task(() -> ));

        launchTaskList(taskList);

        // not multithread way
        // imgNullIni = FImages.getImage("null");
        // chargerTI();
        // tFIni = chargerTX("F",3,(byte)0,-3);
        // imageTreeIni = new FImageTree();
        // imageTreeIni.folderToTree(Main.getFolder().getFolderStable()+Main.getFolder().getFolderImages()+"Creature/");
        // iconMap = FImages.getImagesAsMap(Main.getFolder().getFolderStable()+Main.getFolder().getFolderImages()+"icon/");
        // iconMap = FImages.getScaledInstanceFromMap(iconMap, Main.getTailleElementGraphiqueY(30));
        // tGIni = FImages.getImages("seed",FImages.getNbrImages("seed"),(byte)0);
        // fereIni = FImages.getImage("antnest");
        // cSombreIni = FImages.getImage("cSombre");
        // bIni = FImages.getImages("b");
        // iniOtherImages();

        Main.endCh("chargerImagesIni");

      }
      imageIni=true;
      if(!imageIniForNewGame){
        //TODO #246 & #247 add various image for every species.
        tIFIni = new BufferedImage[1];
        tIFIni[0] = FImages.getImage("F0");
      }
      imageIniForNewGame=true;
    }
    /**
    *{@summary Launch a task list.}<br>
    *This function use multithreading to be faster.<br>
    *@param taskList task list to launch
    *@lastEditedVersion 2.30
    */
    public void launchTaskList(List<Task> taskList){
      ExecutorService executor = Executors.newCachedThreadPool();
      List<Future<Object>> futureList = new ArrayList<Future<Object>>();
      cptTaskDone=0;
      numberOfTask = taskList.size();
      try {
        futureList = executor.invokeAll(taskList, 10, TimeUnit.SECONDS);
      }catch (InterruptedException | NullPointerException | RejectedExecutionException e) {
        erreur.erreur("Fail to load images from file in multithread because of "+e);
      }
      executor.shutdown();
      int cptTaskWorked=0;
      for(Future<Object> fut : futureList){
        if(fut.isDone()){
          cptTaskWorked++;
        }
      }

      if(cptTaskWorked!=numberOfTask){
        erreur.erreur((numberOfTask-cptTaskWorked)+" load image tasks fail to be done");
      }
    }

    /**
    *{@summary Update the number of task done to +1.}<br>
    *@lastEditedVersion 2.30
    */
    public void oneMoreTaskDone(){
      // TODO synchronized on something else than this (as a Progression for exemple).
      cptTaskDone++;
      // erreur.println("done : "+cptTaskDone+"/"+numberOfTask);
      Main.setMessageChargement(g.getM("chargementDesGraphismes")+" "+cptTaskDone+"/"+numberOfTask);
    }

    /**
    *{@summary Callable task.}<br>
    *It is used to be run with multithreading.<br>
    *@lastEditedVersion 2.30
    *@author Hydrolien
    */
    class Task implements Callable<Object> {
      private final Runnable r;
      /**
      *{@summary Main constructor with the task to run as a Runnable.}<br>
      *@param r task to run
      *@lastEditedVersion 2.30
      */
      public Task(Runnable r){
        this.r=r;
      }
      /**
      *{@summary Run the task &#38; update counter of task done.}<br>
      *@lastEditedVersion 2.30
      */
      @Override
      public Object call() throws Exception {
        r.run();
        oneMoreTaskDone();
        return null;
      }
    }

    /**
    *{@summary Initialize all images that don't need to be resize.}
    *Image from otherImages can be acces by there name.
    *@lastEditedVersion 2.21
    */
    public void iniOtherImages(){
      if(otherImages!=null){return;}
      otherImages =  new HashMap<String, BufferedImage>();
      File dir = new File(Main.getFolder().getFolderStable()+Main.getFolder().getFolderImages()+"other/");
      for (File f : dir.listFiles()) {
        otherImages.put(f.getName().substring(0,f.getName().length()-4),FImages.getImage("other/"+f.getName()));
      }
    }
    /**
    *{@summary Load the FPanelNouvellePartie images if they aren't load yet.}
    * If size is different, image will be reload with the greate size.
    *@param buttonSize size of the 2 image to load.
    *@lastEditedVersion 2.15
    */
    public void loadPnpImage(int buttonSize){
      if(loopArrow==null || loopArrow.getWidth()!=buttonSize){
        loopArrow = FImages.resize(FImages.getImage("loop arrow"), buttonSize);
        pick = FImages.resize(FImages.getImage("pick"), buttonSize);
      }
    }
    /***
    *{@summary Load antColorIni.}
    *antColorIni will be set to max in alpha if it need.
    *@lastEditedVersion 2.2
    */
    // private void iniAntColorIni(){
    //   antColorIni = FImages.getImages("FCol",FImages.getNbrImages("FCol"),(byte)0);
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
    *Load Square image
    *@lastEditedVersion 1.18
    */
    public void chargerTI(){
      tICarteIni = new BufferedImage [3];
      // tICarteIni[0]=FImages.getImage("herbe");
      // tICarteIni[1]=FImages.getImage("mousse");
      tICarteIni[0]=FImages.getImage("terre");
      tICarteIni[1]=FImages.getImage("terre");
      tICarteIni[2]=FImages.getImage("sable");
    }
    /**
    *{@summary Load a group of BufferedImage that starts with a similar name.}<br>
    *see FImages.getImagess() for more informations.
    @param name Name of de group. Every image will start by this name.
    *@lastEditedVersion 1.18
    */
    public BufferedImage [][] chargerTX(String name, int x, byte y, int début){
      return FImages.getImagess(name,x,(byte)début);
    }
    public BufferedImage [][] chargerTX(String name, int x, byte y){ return chargerTX(name,x,y,0);}
    public BufferedImage [][] chargerTX(String name, int x){ return chargerTX(name,x,(byte)0);}
    public BufferedImage [][] chargerTX(String name){return chargerTX(name,FImages.getNbrImages(name));}

    /**
    *Create a background image from tI1 and tI2 images.
    *@lastEditedVersion 1.42
    */
    public void iniBackgroundMapImage(){
      if(!Main.getView().getActionGameOn()){return;}
      Main.startCh();
      // if(FPanel.getView().getPc()==null){erreur.erreur("Map panel is null");}
      FPanel.getView().getPc().updateSize();
      Img img = new Img(FPanel.getView().getPc().getWidth(),FPanel.getView().getPc().getHeight());
      //if(img.)
      Img img2 = null;
      int xSquare = FPanel.getView().getPc().getXSquare();
      int ySquare = FPanel.getView().getPc().getYSquare();
      try {
        // if(getTailleDUneSquare()<1){erreur.erreur("Square size is <1");}
        for (int i=0;i<xSquare ;i++ ) {
          for (int j=0;j<ySquare ;j++ ) {
            int xT = i*getTailleDUneSquare(); int yT = j*getTailleDUneSquare();
            Square c=null;
            try {
              c = Main.getGc().getCSquare(i,j).getContent();
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
        erreur.println(e);
        e.printStackTrace();
        map=null;
      }
      Main.endCh("iniBackgroundMapImage");
      if(Main.getPartie()!=null && Main.getPartie().getCarte()!=null && Main.getPartie().getCarte().getSquaresNuageuses()==true){
        iniCloudMapImage();
      }
    }
    /**
    *Create a cloud image from loading image.
    *@lastEditedVersion 1.42
    */
    private void iniCloudMapImage(){
      if(!Main.getView().getActionGameOn()){return;}
      BufferedImage could = FImages.resize(FImages.getImage("cloud"), map.getWidth(), map.getHeight());
      cloudMap=FImages.resize(loadingImage, map.getWidth(), map.getHeight());
      for (int i=0; i<cloudMap.getWidth(); i++) {
        for (int j=0; j<cloudMap.getHeight(); j++) {
          int red = ((cloudMap.getRGB(i,j)>>16)&255);
          int green = ((cloudMap.getRGB(i,j)>>8)&255);
          int blue = (cloudMap.getRGB(i,j)&255);
          int redC = ((could.getRGB(i,j)>>16)&255);
          int greenC = ((could.getRGB(i,j)>>8)&255);
          int blueC = (could.getRGB(i,j)&255);
          // int added=60;
          // added+=allea.getAllea(20)-9;
          // red+=added;
          // green+=added;
          // blue+=added;
          red=(red+redC)/2;
          green=(green+greenC)/2;
          blue=(blue+blueC)/2;
          if(red>255){red=255;}
          if(green>255){green=255;}
          if(blue>255){blue=255;}
          int x = ((255<<24)|(red<<16)|(green<<8)|(blue));
          cloudMap.setRGB(i,j,x);
        }
      }
      cloudMap = FImages.toBlackAndWhite(cloudMap);
    }

    //getScaledInstance.
    /**
    *Return a scaled BufferedImage
    *@lastEditedVersion 2.1
    */
    public BufferedImage getScaledInstance(BufferedImage bi, int dim, int b){
      BufferedImage r = null;
      if(b==0){//par défaut
        r=FImages.resize(bi,dim);
      }
      // else if(b==1){//pour les fourmis.
      //   int idEspece = 0;
      //   // int stade = i-3;
      //   r=FImages.resize(bi,FImages.taille(idEspece, stade,dim));
      // }else if(b==2){//pour les insectes
      //   // int idEspece = i+100;
      //   int stade = 0;
      //   r=FImages.resize(bi,FImages.taille(idEspece, stade,dim));
      // }
      return r;
    }public BufferedImage getScaledInstance(BufferedImage bi, int dim){return getScaledInstance(bi,dim,0);}
    /**
    *Return a scaled BufferedImage []
    *@lastEditedVersion 1.18
    */
    public BufferedImage [] getScaledInstance(BufferedImage ti[], int dim, int b){
      int lenr = 0;
      if(ti!=null){
        lenr=ti.length;
      }
      BufferedImage r [] = new BufferedImage[lenr];
      for (int i=0;i<lenr ;i++ ) {
        if(b==0){//par défaut
          r[i]=FImages.resize(ti[i],dim);
        }else if(b==1){//pour les fourmis.
          int idEspece = 0;
          int stade = i-3;
          r[i]=FImages.resize(ti[i],FImages.taille(idEspece, stade,dim));
        }else if(b==2){//pour les insectes
          int idEspece = i+100;
          int stade = 0;
          //TODO #421 do the same into the imageTreeIni.
          r[i]=FImages.resize(ti[i],FImages.taille(idEspece, stade,dim));
        }
      }
      return r;
    }public BufferedImage [] getScaledInstance(BufferedImage ti[],int dim){return getScaledInstance(ti,dim,0);}
    /**
    *Return a scaled BufferedImage [][]
    *@lastEditedVersion 1.18
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
  //FPanelAction
  /**
  *Load graphics for FPanelAction
  *@lastEditedVersion 1.18
  */
  public synchronized void chargerTIFPanelAction(){
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
  *@lastEditedVersion 1.46
  */
  private void loadBackgroundPAI(){
    backgroundPAI = FImages.getImage("backgroundPAI");
    backgroundPAI = backgroundPAI.getScaledInstance(Main.getDimX(), FPanel.getView().getPa().getHeight(),Image.SCALE_SMOOTH);
  }
  /**
  *Load backgroundPAS
  *@lastEditedVersion 1.46
  */
  private void loadBackgroundPAS(){
    backgroundPAS = FImages.getImage("backgroundPAS");
    backgroundPAS = backgroundPAS.getScaledInstance(FPanel.getView().getPa().getHeight(), FPanel.getView().getPa().getHeight(),Image.SCALE_SMOOTH);
  }
  /**
  *Load images for FPanelAction without background
  *@lastEditedVersion 2.12
  */
  private void chargerTImage(){
    int tailleBouton = FPanel.getView().getPa().getbuttonSize();
    tImage = FImages.getImages("desc");
    int len = tImage.length;
    for (int i=0; i<len; i++) {
      tImage[i] = FImages.resize(tImage[i],tailleBouton);
    }
  }
  /**
  *Load images for FPanelAction with background
  *@lastEditedVersion 1.18
  */
  private void chargerTImageAvecFond(Pixel pi){
    int tailleBouton = FPanel.getView().getPa().getbuttonSize();
    for (int k=0;k<10 ;k++) {
      Img img = new Img("desc"+k);
      img.changerPixelTransparent(pi);
      img.sauvegarder("temporaire/desc"+k+".png");
    }
    tImage = FImages.getImages("temporaire/desc");
    for (int i=0;i<10 ;i++ ) {
      tImage[i] = FImages.resize(tImage[i],tailleBouton);
    }
  }
  //FPanelZoom
  /**
  *Load images for FPanelZoom
  *@lastEditedVersion 1.18
  */
  public Image [] chargerTIBZoom(){
    tIBZoom = new Image[9];
    if(!initialisationFX && !Main.getKeepFilesRotated()){tournerLesFleches();}
    int tailleBouton=Main.getbuttonSizeZoom();
    tIBZoom[0] = FImages.getImage("moins").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIBZoom[1] = FImages.getImage("fleche").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIBZoom[2] = FImages.getImage("plus").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIBZoom[3] = FImages.getImage("fleche1").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    //tourner +90°
    tIBZoom[4] = FImages.getImage("centrer").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIBZoom[5] = FImages.getImage("fleche2").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    //tourner -90°
    tIBZoom[6] = FImages.getImage("fleche3").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    //tourner +180°
    tIBZoom[7] = FImages.getImage("centrer").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIBZoom[8] = FImages.getImage("centrer").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    return tIBZoom;
  }
  /**
  *{@summary Turn the arrow for FPanelZoom.}<br>
  *@lastEditedVersion 1.18
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
  *{@summary Load a loading image for FPanelChargement.}<br>
  *@lastEditedVersion 1.32
  */
  public boolean loadImageChargement(){
    String mapName = Main.getMap().getMapName();
    mapName = str.sToSMaj(mapName);
    loadingImage=null;
    if(mapName!=null && !mapName.equals("")){
      loadingImage=FImages.getImage("loading"+mapName,false);
      if(loadingImage!=null){
        loadingImage=FImages.resize(loadingImage,Main.getDimX(),Main.getDimY());
      }
    }
    //if it haven't been load yet we try to load any image name chargementi.png or .jpj.
    if(loadingImage==null){
      int x = allea.getAlléa(FImages.getNbrImages("loading"));
      loadingImage=FImages.getImage("loading"+x);
      loadingImage=FImages.resize(loadingImage,Main.getDimX(),Main.getDimY());
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
