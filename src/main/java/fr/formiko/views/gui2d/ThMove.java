package fr.formiko.views.gui2d;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.MapPath;
import fr.formiko.formiko.ObjetSurCarteAId;
import fr.formiko.formiko.interfaces.DeplacementFourmi;
import fr.formiko.usual.Point;
import fr.formiko.usual.Temps;
import fr.formiko.usual.erreur;
import fr.formiko.usual.structures.listes.Liste;

import java.lang.Thread;
import java.util.Comparator;

/**
*{@summary A simple Thread extends class to move ObjetSurCarteAId.}<br>
*It modify only rotation &#38; position so it won't break any game mechanic.
*@lastEditedVersion 2.4
*@author Hydrolien
*/
public class ThMove {
  private ObjetSurCarteAId o;
  private Point from;
  private Point to;
  private Point curent;
  private Point curent2;
  private Point rotate;
  private double vectX;
  private double vectY;
  private double vectRotate;
  private static Liste<ThMove> curentThList = new Liste<ThMove>();
  private static Liste<ThMove> queue = new Liste<ThMove>();
  private int id; private static int cptId=0;
  private boolean lock;
  private long time;
  private static Comparator<ThMove> comparator = (ThMove e1, ThMove e2) -> (int)(e2.getIdTh() - e1.getIdTh());
  private static ThMoveManager thMoveManager;
  private int k;
  private int kIni;
  private int numberOfTic;
  private double rotateAngle;
  /**
  *{@summary Create Thread for the ObjetSurCarteAId animation.}
  *@param o the Object to animate.
  *@param from CCase from where it move.
  *@param to CCase where it move.
  *@lastEditedVersion 2.4
  */
  public ThMove(ObjetSurCarteAId o, CCase from, CCase to){
    id=cptId++;
    this.o=o;
    Case c = from.getContent();
    this.from = FPanel.getView().getPc().getPointFromCase(c.getX(), c.getY(), false);
    c = to.getContent();
    this.to = FPanel.getView().getPc().getPointFromCase(c.getX(), c.getY(), false);
    curent2 = new Point(0,0);
    addToQueue(this);
    time = System.currentTimeMillis();
    // ThMove.updateQueue();
    if(thMoveManager==null){
      thMoveManager = new ThMoveManager();
      thMoveManager.start();
    }
  }
  private int getIdMovingObject(){return o.getId();}
  public int getIdTh(){return id;}
  /**
  *{@summary Initialize before launch start function.}
  *It Initialize Point and add to the 2 curent list.
  *@lastEditedVersion 2.4
  */
  private void iniBeforeStart(){
    curentThList.add(this);
    curent = new Point(-1,-1);
    rotate = new Point(0,0);
    try {
      FPanel.getView().getPc().addMovingObject(o.getId(), curent, rotate);
    }catch (NullPointerException e) {} //getPc return null if we move creature by script before graphics are ini.
    iniRun();
  }
  /**
  *{@summary add an item to the queue.}
  *Item are sorted in queue so that they will be launch in the creation order.
  *@lastEditedVersion 2.4
  */
  private static void addToQueue(ThMove th){
    synchronized (queue) {
      queue.addSorted(th, comparator);
    }
  }
  /***
  *{@summary Update the queue by launching all ThMove that can be launch.}
  *@lastEditedVersion 2.4
  */
  static void updateQueue(){
    if(queue==null){return;}
    // erreur.println(queue.size()+" in queue");
    try {
      synchronized (queue) {
        for (ThMove th : queue) {
          //if need to launch : launch
          if(FPanel.getView().getPc().getMovingObjectLocation(th.getIdMovingObject())==null){
            // erreur.info("test ok th "+th.getIdTh()+" for "+th.getIdMovingObject()+" after "+(System.currentTimeMillis()-th.time)+"ms");
            queue.remove(th);
            th.iniBeforeStart();
            // th.start();
            thMoveManager.add(th);
          }
        }
      }
    }catch (Exception e) {
      if(FPanel.getView().getActionGameOn()){
        erreur.alerte("someting whent wrong in updateQueue");
      }
    }
  }
  /**
  *{@summary Update the destination Point.}
  *@param to the new destination.
  *@lastEditedVersion 2.1
  */
  private void updateTo(Point to){
    curent2.setX(curent2.getX() + this.to.getX() - to.getX());
    curent2.setY(curent2.getY() + this.to.getY() - to.getY());
    this.to=to;
  }
  /**
  *{@summary Update all ThMove that need to be change if ant move a 2a time.}
  *It will update all ThMove that haven't been done &#38; that are about ObjetSurCarteAId with given id.
  *@param to new CCase were to go.
  *@param id id of the concerned ObjetSurCarteAId.
  *@lastEditedVersion 2.1
  */
  public static void updateTo(CCase to, int id){
    Case c = to.getContent();
    for (ThMove th : curentThList ) {
      if(th.getIdMovingObject()==id){
        th.updateTo(FPanel.getView().getPc().getPointFromCase(c.getX(), c.getY(), false));
      }
    }
    for (ThMove th: queue) {
      if(th.getIdMovingObject()==id){
        th.updateTo(FPanel.getView().getPc().getPointFromCase(c.getX(), c.getY(), false));
      }
    }
  }
  /**
  *{@summary Initialize the ObjetSurCarteAId animation.}
  *@lastEditedVersion 2.1O
  */
  // @Override
  public void iniRun(){
    o.setDirection(CCase.getDirection(from, to));
    int walkCycle = 2;
    k=120; //should be a mutiple of 2*walkCycle.
    if(Main.getOp().getQuickMovement()){k=20;}
    kIni=k;
    numberOfTic = k/(2*walkCycle);
    vectX = this.to.getX()-this.from.getX();
    vectY = this.to.getY()-this.from.getY();
    if(vectX==0 && vectY==0){return;}
    //patch to move 1 Case max.
    if(vectX>Main.getData().getTailleDUneCase()){
      vectX=Main.getData().getTailleDUneCase();
    }else if(vectX<-Main.getData().getTailleDUneCase()){
      vectX=-Main.getData().getTailleDUneCase();
    }
    if(vectY>Main.getData().getTailleDUneCase()){
      vectY=Main.getData().getTailleDUneCase();
    }else if(vectY<-Main.getData().getTailleDUneCase()){
      vectY=-Main.getData().getTailleDUneCase();
    }
    rotateAngle = 0;
    vectRotate=-40;
    vectRotate/=numberOfTic;
  }
  /**
  *{@summary Do a step of the ObjetSurCarteAId animation.}
  *Call end run if it's over.
  *@lastEditedVersion 2.10
  */
  public void oneStep(){
    curent.setX((int)((-k)*(vectX/kIni))+curent2.getX());
    curent.setY((int)((-k)*(vectY/kIni))+curent2.getY());
    if (o instanceof Fourmi && ((Fourmi)o).getStade()==0){
      if ((k+(numberOfTic/2))%numberOfTic==0) { //4 changement = 6 cycle de marche.
        vectRotate=-vectRotate;
      }
      rotateAngle+=vectRotate;
      rotate.setX((int)rotateAngle);
    }
    k--;
    if(k<1){endRun();}
  }
  /**
  *{@summary End the ObjetSurCarteAId animation by removing this from the 2 thread.}
  *@lastEditedVersion 2.10
  */
  public void endRun(){
    if(FPanel.getView().getPc()!=null){
      FPanel.getView().getPc().removeMovingObject(o.getId());
    }
    curentThList.remove(this);
    thMoveManager.remove(this);
  }
}
/**
*{@summary A simple Thread extends class to manage ThMove.}<br>
*It is used only if instantaneousMovement==false.<br>
*@lastEditedVersion 2.10
*@author Hydrolien
*/
class ThMoveManager extends Thread {
  private Liste<ThMove> list;
  /**
  *{@summary Main constructor that initialize list.}<br>
  *@lastEditedVersion 2.10
  */
  public ThMoveManager(){
    list = new Liste<ThMove>();
  }
  public void add(ThMove move){list.add(move);}
  public void remove(ThMove move){list.remove(move);}
  /**
  *{@summary Do all the moving animation.}
  *@lastEditedVersion 2.10
  */
  @Override
  public void run(){
    if(Main.getOp().getInstantaneousMovement()){return;}
    while(true){
      ThMove.updateQueue();
      for (ThMove move : list) {
        move.oneStep();
      }
      Temps.pause(10);
      // if(list.isEmpty()){
      //   try {
      //     wait();
      //   }catch (InterruptedException e) {}
      // }
    }
  }
}
