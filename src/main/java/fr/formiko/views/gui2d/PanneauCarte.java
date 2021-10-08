package fr.formiko.views.gui2d;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.CCreature;
import fr.formiko.formiko.CGraine;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.GCreature;
import fr.formiko.formiko.Graine;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Joueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.ObjetSurCarteAId;
import fr.formiko.formiko.Point;
import fr.formiko.formiko.Point;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.Img;
import fr.formiko.usuel.images.Pixel;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.structures.listes.Liste;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.maths.math;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
*{@summary Map panel.}<br>
*It can draw map.<br>
*Mouse listener part is in PanneauSup.java.<br>
*@author Hydrolien
*@version 1.42
*/
public class PanneauCarte extends Panneau {
  private int xCase; // nombre de case en X
  private int yCase; // nombre de case en Y
  private int posX; // position de la 1a case.
  private int posY;
  private int xTemp,yTemp;
  private int idCurentFere=-1;
  private static boolean drawAllFere;
  private CCase lookedCCase;
  private Map<Integer,Point> hashMapMovingObjectSurCarteAid;
  private Map<Integer,Point> hashMapMovingObjectSurCarteAidRotation;
  // private SubPanel subPanel;
  private static Comparator<Creature> imageSizeComparator = (Creature p1, Creature p2) -> (int)(p1.getEspece().getTaille(p1.getStade()) - p2.getEspece().getTaille(p2.getStade()));

  // CONSTRUCTORS --------------------------------------------------------------
  public PanneauCarte(){
    // subPanel = new SubPanel(this);
    // add(subPanel);
  }
  /**
  *{@summary Build methode.}<br>
  *@version 1.x
  */
  public void build(){
    Main.getData().setTailleDUneCase(Main.getTailleElementGraphique(100));
    posX = 0; posY = 0;
    GCase gc = new GCase(1,1);
    xCase = gc.getNbrX();
    yCase = gc.getNbrY();
    hashMapMovingObjectSurCarteAid = new HashMap<Integer,Point>();
    hashMapMovingObjectSurCarteAidRotation = new HashMap<Integer,Point>();
  }
  // GET SET -------------------------------------------------------------------
  public int getTailleDUneCase(){return Main.getData().getTailleDUneCase();}
  public void setTailleDUneCase(int x){if(x!=getTailleDUneCase()){Main.getData().setTailleDUneCase(x);actualiserCarte();}}
  public int getXCase(){ return xCase;}
  public void setXCase(int x){xCase = x;}
  public int getYCase(){ return yCase;}
  public void setYCase(int y){yCase = y;}
  public int getPosX(){ return posX;}
  public void setPosX(int x){posX=x; }
  public int getPosY(){ return posY;}
  public void setPosY(int x){posY=x; }
  public void setIdCurentFere(int x){idCurentFere=x;}
  public CCase getLookedCCase(){return lookedCCase;}
  public void setLookedCCase(CCase cc){lookedCCase=cc;}
  public void setLigne(Graphics2D g){
    BasicStroke ligne = new BasicStroke(Main.getDimLigne());
    g.setStroke(ligne);
    g.setColor(Color.BLACK);
  }
  /**
  *setSize sould never be used. Use updateSize insted.
  *@version 1.x
  */
  @Override
  public void setSize(int x, int y){
    erreur.alerte("Not alowed to setSize here ! (Nothing have been done.)");
  }
  /**
  *Do the 3 steps that are need to set PanneauCarte to a new size.
  *@version 1.x
  */
  public void actualiserCarte(){
    updateSize();//actualise la taille du PanneauCarte a la bonne dimention.
    //chargerImages();
    Main.getData().iniBackgroundMapImage(); //demande au donnée d'image de rechargé l'image qui représente l'arrière plan de la carte.
  }
  // Fonctions propre ----------------------------------------------------------
  /**
  *{@summary Main paint function for Map.}<br>
  *It print 1 by 1: map with only Case, grids, all map element Case by Case, the mark for all game as playingAnt mark.
  *@version 1.42
  */
  public void paintComponent(Graphics g2){
    if(!Main.getActionGameOn()){return;}
    try {
      if(!Main.getPartie().getEnCours()){return;}
    }catch (Exception e) {
      erreur.alerte("la partie est null");
    }
    Main.startCh();

    setLocation(-getPosX()*getTailleDUneCase(),-getPosY()*getTailleDUneCase());

    Graphics2D g = (Graphics2D)g2;
    setLigne(g);
    try {
      GCase gc = Main.getGc();
      updateSize();
      debug.débogage("Dimention du PanneauCarte en case : x="+xCase+" y="+yCase);
      debug.débogage("taille réèle du panneau de la carte : x="+this.getWidth()+", y="+this.getHeight());
      try {
        if(Main.getData().getMap()==null){Main.getData().iniBackgroundMapImage();}
        g.drawImage(Main.getData().getMap(),0,0,this);
      }catch (Exception e) {
        erreur.erreur("impossible d'afficher l'arrière plan de la carte");
      }
      dessinerGrille(g);
      for (int i=0;i<xCase ;i++ ) {
        for (int j=0;j<yCase ;j++ ) {
          peintImagePourCase(gc,i,j,g);
        }
      }
      drawPlayingAnt(g);
    }catch (Exception e) {
      erreur.erreur("Quelque chose d'imprévu est arrivé lors de l'affichage de PanneauCarte");
    }
    Main.endCh("repaintDeLaCarte");
  }
  /**
  *{@summary Draw grid.}<br>
  *@version 1.x
  */
  public void dessinerGrille(Graphics g){
    if(Main.getDessinerGrille()){
      int tailleCase = getTailleDUneCase();
      for (int i=0;i<xCase+1 ;i++ ) {
        int xT = tailleCase*i;
        g.drawLine(xT,0,xT,tailleCase*(yCase));
      }
      for (int i=0;i<yCase+1 ;i++ ) {
        int xT = tailleCase*i;
        g.drawLine(0,xT,tailleCase*xCase,xT);
      }
    }
  }
  /*public void repaintParciel(Case c){
    peintImagePourCase(c,(Graphics2D) this.getGraphics());
  }*/
  public void peintImagePourCase(Case c,Graphics2D g){
    int x = c.getX(); int y = c.getY();
    peintImagePourCase(c,x,y,g);
  }
  public void peintImagePourCase(GCase gc, int x, int y,Graphics2D g){
    try {
      Case c = gc.getCCase(x,y).getContent();
      peintImagePourCase(c,x,y,g);
    }catch (Exception e) {
      erreur.erreur("fail to draw "+x+" "+y);
    }
  }
  /**
  *{@summary Draw cloud Case.}<br>
  *@version 1.x
  */
  public boolean peintCaseNuageuse(int x, int y,Graphics g,int xT, int yT){
    Joueur jo = Main.getPlayingJoueur();
    if(Main.getPartie().getCarte().getCasesNuageuses()==true){ //si il y a des cases nuageuses
      try {
        if(Main.getPartie().getGj().getNbrDeJoueurHumain()==1){//si il ya moins de 2 joueurs, on peu afficher les cases que le joueur voie.
          jo = Main.getPartie().getGj().getJoueurHumain().getHead().getContent();
        }
        if (jo!=null){//si on a un joueur sélectionné.
          if (x>=0 && y>=0 && jo.getCaseNuageuse(x,y)){//si la case est invisible (nuageuse.)
            drawImage(g,Main.getData().getCNuageuse(),xT,yT);
            return true;//on ne dessine rien par dessus.
          }
        }else{//si pas de joueur selcetionné toute les cases sont nuageuse.
          drawImage(g,Main.getData().getCNuageuse(),xT,yT);
          return true;//on ne dessine rien par dessus.
        }
      }catch (Exception e) {
        erreur.erreur("Une case possiblement nuageuse n'as pas pue atre affiché");
      }
    }
    return false;
  }
  /**
  *{@summary Draw the mark for playingAnt.}<br>
  *@version 2.5
  */
  private void drawPlayingAnt(Graphics g){
    Fourmi playingAnt = Main.getPlayingAnt();
    if(playingAnt!=null && !playingAnt.getIa()){
      Case c = playingAnt.getCCase().getContent();
      drawImage(g,Main.getData().getSelectionnee(),(c.getX())*Main.getData().getTailleDUneCase(),(c.getY())*Main.getData().getTailleDUneCase());
    }
  }
  /**
  *{@summary Return the coordinates on screen for Case with x &#38; y.}<br>
  *@param x the x of the Case.
  *@param y the y of the Case.
  *@param centered True return the coordinates of the center of the case. False return left top coordinates.
  *@version 2.1
  */
  public Point getPointFromCase(int x, int y, boolean centered){
    Point p = new Point(x*getTailleDUneCase(),y*getTailleDUneCase());
    if(centered){
      p.setX(p.getX()+getTailleDUneCase()/2);
      p.setY(p.getY()+getTailleDUneCase()/2);
    }
    return p;
  }
  /**
  *{@summary Draw a Case.}<br>
  *@version 2.1
  */
  public void peintImagePourCase(Case c, int x, int y,Graphics2D g){
    if(!isCaseVisible(c)){return;}
    Joueur jo = Main.getPlayingJoueur();
    Fourmi fi = Main.getPlayingAnt();
    if(fi==null){
      try {
        fi = (Fourmi)Main.getPlayingJoueur().getFere().getGc().getFirst();
      }catch (NullPointerException e) {}
    }
    Point point = getPointFromCase(x,y,false);
    int xT = point.getX(); int yT = point.getY();
    int xT2 = (x)*getTailleDUneCase(); int yT2 = (y)*getTailleDUneCase();
    if(peintCaseNuageuse(x,y,g,xT,yT)){return;}//si la case est nuageuse, on n'affichera rien d'autre dessus.
    byte ty = c.getType();
    CCreature ccrea = c.getGc().getHead();
    CGraine ccg = c.getGg().getHead();
    try {
      int tC10 = Main.getData().getTailleDUneCase()/10;int tC4 = Main.getData().getTailleDUneCase()/4;int tC2 = Main.getData().getTailleDUneCase()/2;
      // anthill
      if (c.getFere()!=null){
        drawImage(g,Main.getData().getFere(),xT+tC4,yT+tC4);
        if (needToDrawAnthillColor(c, x, y)) {
          int tailleDuCercle = Main.getTailleElementGraphique(20);
          drawRondOuRect(xT,yT,Main.getData().getTailleDUneCase(),g,c.getFere(),tailleDuCercle);
        }
      }
      if(isSombre(x,y)){
        drawImage(g,Main.getData().getCSombre(),xT,yT); // si les créatures sur la case ne sont pas visible.
      }else{
        int k=0;
        boolean seedPrinted = Main.getAffGraine();
        boolean insectPrinted = true;
        int cptIcon = 0;
        int kIcon = 0;
        if(seedPrinted){
          cptIcon+=c.getGg().length();
        }
        if(insectPrinted){
          cptIcon+=c.getGc().length();
        }
        //seeds
        if(seedPrinted){
          while (ccg!=null){
            calculerXYTemp(xT,yT,k,c);k++;
            int dir = getDir((ObjetSurCarteAId)ccg.getContent());
            try {
              BufferedImage bi = Main.getData().getTG()[0][ccg.getContent().getType()];
              drawImageCentered(g,image.rotateImage(bi,dir),xT,yT);
            }catch (Exception e) {}
            if(ccg.getContent().getOuverte()){drawIcone(g,5,xT,yT,tC2,kIcon++,cptIcon);}
            else if(fi==null || ccg.getContent().getDureté()<=fi.getDuretéMax()){drawIcone(g,4,xT,yT,tC2,kIcon++,cptIcon);}
            else {drawIcone(g,6,xT,yT,tC2,kIcon++,cptIcon);}
            ccg = ccg.getSuivant();
          }
        }
        //creatures.
        Liste<Creature> gcToPrint = gcSortedByImageSize(c.getGc());
        for (Creature cr : gcToPrint) {
          Point p = hashMapMovingObjectSurCarteAid.get(cr.getId());
          int x2,y2;
          if(p!=null){
            x2=p.getX();
            y2=p.getY();
            if(x2==-1){continue;}//go to next loop turn.
          }else{
            x2 = y2 = 0;
          }
          int dir = getDir((ObjetSurCarteAId)cr);
          boolean insecte = true;
          calculerXYTemp(xT,yT,k,c);k++;
          if(cr instanceof Fourmi){
            Fourmi f = ((Fourmi)cr);
            try {
              BufferedImage bi = Main.getData().getCreatureImage(f);
              drawImageCentered(g,image.rotateImage(bi,dir),xT+x2,yT+y2);
              // Point tp [] = Main.getData().getAntImageLocation();
              // BufferedImage tBi [] = Main.getData().getAntImage(f);
              // int k2=0;
              // for (BufferedImage bi : tBi ) {
              //   if(bi!=null){
              //     drawImageCentered(g,image.rotateImage(bi,dir),xT+x2,yT+y2);
              //   }
              //   k2++;
              // }
            }catch (Exception e) {
              erreur.erreur("can't draw ant "+f.getId()+" at stade "+f.getStade());
            }
            insecte=false;
          }else if(cr instanceof Insecte && insectPrinted){
            Insecte i = (Insecte)(cr);
            try {
              BufferedImage bi = Main.getData().getCreatureImage(cr);
              // BufferedImage bi = Main.getData().getTII()[0][math.min(i.getType(),Main.getData().getTII()[0].length)];
              drawImageCentered(g,image.rotateImage(bi,dir),xT+x2,yT+y2);
            }catch (Exception e) {
              erreur.erreur("can't draw insect "+i.getId()+" with type "+i.getType());
            }
          }
          //icons
          try {
            if(cr.getEstMort()){drawIcone(g,3,xT,yT,tC2,kIcon++,cptIcon);}
            else if(fi!=null && cr.getEstAllié(fi)){drawIcone(g,0,xT,yT,tC2,kIcon++,cptIcon);}
            else if(fi!=null && cr.getEstEnnemi(fi) && !insecte){drawIcone(g,2,xT,yT,tC2,kIcon++,cptIcon);}
            else {drawIcone(g,1,xT,yT,tC2,kIcon++,cptIcon);}
          }catch (Exception e) {
            erreur.erreur("impossible de dessiner l'icone de la Case : "+x+" "+y);
          }
          // ccrea=ccrea.getSuivant();
        }
      }
    }catch (Exception e) {
      erreur.erreur("impossible de dessiner l'image de la Case : "+x+" "+y);
    }
  }
  /**
  *{@summary Return a Liste<Creature> sorted by image size.}<br>
  *It is used to print smaler creature on top, so that we can see every Creature.
  *@version 2.6
  */
  //public only for test
  public static Liste<Creature> gcSortedByImageSize(GCreature gc){
    Liste<Creature> listToPrint = gc.toList();
    if(Main.getOp().getTailleRealiste()==0){return listToPrint;}
    listToPrint.sort(imageSizeComparator);
    return listToPrint;
  }
  /**
  *{@summary Add a curent moving object.}
  *It modify location and rotation of the object.
  *location and rotation are save in 2 hashmap.
  *@param id the id of the object
  *@param location the modification of the location of the object as a Point
  *@param rotation the modification of the rotation in the x value of a Point
  *@version 2.4
  */
  public void addMovingObject(int id, Point location, Point rotation){
    hashMapMovingObjectSurCarteAid.put(id, location);
    hashMapMovingObjectSurCarteAidRotation.put(id, rotation);
  }
  public Point getMovingObjectLocation(int id){
    return hashMapMovingObjectSurCarteAid.get(id);
  }
  public Point getMovingObjectRotation(int id){
    return hashMapMovingObjectSurCarteAidRotation.get(id);
  }
  public void removeMovingObject(int id){
    hashMapMovingObjectSurCarteAid.remove(id);
    hashMapMovingObjectSurCarteAidRotation.remove(id);
  }
  /**
  *{@summary draw an image centered for a Case.}<br>
  *@param g Graphics element were to draw.
  *@param image the image to draw.
  *@param xT the x of the Case were to draw.
  *@param yT the y of the Case were to draw.
  *@version 2.1
  */
  private void drawImageCentered(Graphics2D g, BufferedImage image, int xT, int yT){
    if(image==null){
      // erreur.alerte("Can't draw a null image");
      // return;
      throw new NullPointerException();
    }
    int w = image.getWidth();
    int h = image.getHeight();
    int caseSize = getTailleDUneCase();
    int xI = xT+(caseSize-w)/2;
    int yI = yT+(caseSize-h)/2;
    drawImage(g,image,xI,yI);
  }
  /**
  *{@summary draw an image.}<br>
  *@param g Graphics element were to draw.
  *@param im the image to draw.
  *@param x the x ere to draw.
  *@param y the y were to draw.
  *@version 2.1
  */
  public void drawImage(Graphics gTemp, BufferedImage im, int x, int y){
    Graphics2D g = (Graphics2D)gTemp;
    g.drawImage(im,x,y,this);
    if(Main.getOp().getPaintHitBox()){
      g.setColor(Color.RED);
      g.setStroke(new BasicStroke(math.max(im.getWidth()/100,im.getHeight()/100,1)));
      g.drawRect(x,y,im.getWidth(),im.getHeight());
    }
  }
  /**
  *{@summary return true if case in x,y is sombre.}<br>
  *@version 1.46
  */
  private boolean isSombre(int x, int y){
    Joueur jo = Main.getPlayingJoueur();
    return jo==null || jo.getIa() || (Main.getPartie().getCarte().getCasesSombres() && jo.getCaseSombre(x,y));
  }
  /**
  *{@summary return true if we need to draw the color of the anthill.}<br>
  *@version 1.46
  */
  private boolean needToDrawAnthillColor(Case c, int x, int y){
    if (drawAllFere) { return true;}
    if(c.getFere().getId()==idCurentFere){return true;} // && !isSombre(x,y)
    return (lookedCCase!=null && lookedCCase.getContent() !=null && lookedCCase.getContent().equals(c));
  }
  /**
  *{@summary fonction that place ObjetSurCarteAId on the same Case.}<br>
  *It modify PanneauCarte value xTemp and yTemp.
  *@version 1.x
  */
  public void calculerXYTemp(int xT, int yT, int k, Case c){
    int deplacementEnX=0;
    int deplacementEnY=0;
    //deplacement centré.
    int espaceLibre = Main.getData().getTailleDUneCase()/5;
    if(Main.getPositionCase()==0 || (Main.getPositionCase()==2 && (k>3 || c.getGc().length()+c.getGg().length()==1))){//si on est en mode 2 et que la fourmi est seule ou qu'il y en a plus de 4.
      deplacementEnX=espaceLibre/2;
      deplacementEnY=espaceLibre/2;
      //deplacement alléatoire
    }else if(Main.getPositionCase()==1){
      deplacementEnX=allea.getAlléa(espaceLibre);
      deplacementEnY=allea.getAlléa(espaceLibre);
      //deplacement dans un angle.
    }else if(Main.getPositionCase()==2){
      //on ne modifie que si c'est pas 0.
      if(k==1){
        deplacementEnX=espaceLibre;
        deplacementEnY=espaceLibre;
      }else if(k==2){
        deplacementEnY=espaceLibre;
      }else if(k==3){
        deplacementEnX=espaceLibre;
      }//au milieu si on a déja quelqu'1 dans chaque coin.
    }
    xTemp = xT+deplacementEnX;
    yTemp = yT+deplacementEnY;
  }
  /**
  *{@summary Draw an anthill mark.}<br>
  *@version 1.x
  */
  public void drawRondOuRect(int x, int y, int tailleDUneCase, Graphics2D g, Fourmiliere fere, int tailleDuCercle){
    if(tailleDuCercle<tailleDUneCase/2){
      drawRond(x,y,tailleDUneCase,g,fere,tailleDuCercle);
    }else{
      g.setColor(fere.getPh().getColor());
      g.fillRect(x,y,tailleDUneCase,tailleDUneCase);
      setLigne(g);//retour au paramètres par défaut.
    }
  }
  /**
  *{@summary Draw a circle as an anthill mark.}<br>
  *@version 1.x
  */
  public void drawRond(int x, int y, int r, Graphics2D g, Fourmiliere fere, int tailleDuCercle){
    g.setColor(fere.getPh().getColor());
    BasicStroke line = new BasicStroke(tailleDuCercle);
    g.setStroke(line);
    g.drawOval(x+tailleDuCercle/2,y+tailleDuCercle/2,r-tailleDuCercle,r-tailleDuCercle);
    setLigne(g);//retour au paramètres par défaut.
  }
  /**
  *{@summary Draw an icone.}<br>
  *@param iconeId the icone id to load the good image.
  *@param xT x value to use.
  *@param yT y value to use.
  *@param xOffset offset in x.
  *@param k the number of the peace of circle.
  *@param maxIcon the total number of peace of circle.
  *@version 2.0
  */
  public void drawIcone(Graphics g, int iconeId, int xT, int yT, int xOffset, int k, int maxIcon){
    if (!Main.getDessinerIcone()){ return;}
    g.drawImage(Main.getData().getB()[iconeId],xT+xOffset,yT,this);
  }
  public int getDir(ObjetSurCarteAId obj){
    if (!Main.getElementSurCarteOrientéAprèsDéplacement()){return 0;}// si la direction de l'objet n'est pas prise en compte on cherche dans le tableau 0.
    int x = obj.getDirection();
    // return x;
    // if(x==1 || x==2){ return 0;}
    // if(x==3 || x==6){ return 1;}
    // if(x==9 || x==8){ return 2;}
    // return 3;
    if(x==2){return 0;}
    if(x==3){return 1;}
    if(x==6){return 2;}
    if(x==9){return 3;}
    if(x==8){return 4;}
    if(x==7){return 5;}
    if(x==4){return 6;}
    if(x==1){return 7;}
    return 0;
  }

  public void updateSize(){
    GCase gc = Main.getGc();
    xCase = gc.getNbrX();
    yCase = gc.getNbrY();
    int xTemp = Main.getData().getTailleDUneCase()*xCase;
    int yTemp = Main.getData().getTailleDUneCase()*yCase;
    super.setSize(xTemp,yTemp);
  }

  /**
  *{@summary Tool to save performances by drawing only visible Case.}<br>
  *@param c Case to check
  *return true if case is visible
  */
  public boolean isCaseVisible(Case c){
    if(c.getX()<getPosX() || c.getY()<getPosY()){return false;}
    if(c.getX()>nbrPrintableCase(true)-getPosX() || c.getY()>nbrPrintableCase(false)-getPosY()){return false;}
    return true;
  }
  /**
  *{@summary Tool to calculate the number of printable case on this Panel.}<br>
  *It depend of the size of the panel and of the size of the Case.<br>
  *@param inX true if we check x (width)
  *@return number of printable Case
  */
  public int nbrPrintableCase(boolean inX){
    if(inX){
      int xTemp = 0;
      try {
        xTemp = math.min(getWidth(),getView().getWidth());
      }catch (Exception e) {}
      if(xTemp==0){xTemp=getWidth();}
      return (int)(xTemp/getTailleDUneCase())+1;
    }else{
      int yTemp = 0;
      try {
        yTemp = math.min(getHeight(),getView().getHeight());
      }catch (Exception e) {}
      if(yTemp==0){yTemp=getHeight();}
      return (int)(yTemp/getTailleDUneCase())+1;
    }
  }
}
// class SubPanel extends Panneau{
//   PanneauCarte pc;
//   public SubPanel(PanneauCarte pc) {
//     this.pc = pc;
//   }
//   public void paintComponent(Graphics g2){
//     Graphics2D g = (Graphics2D)g2;
//     setLigne(g);
//     try {
//       GCase gc = Main.getGc();
//       updateSize();
//       debug.débogage("Dimention du PanneauCarte en case : x="+xCase+" y="+yCase);
//       debug.débogage("taille réèle du panneau de la carte : x="+this.getWidth()+", y="+this.getHeight());
//       try {
//         if(Main.getData().getMap()==null){Main.getData().iniBackgroundMapImage();}
//         g.drawImage(Main.getData().getMap(),0-posX*getTailleDUneCase(),0-posY*getTailleDUneCase(),this);
//       }catch (Exception e) {
//         erreur.erreur("impossible d'afficher l'arrière plan de la carte");
//       }
//       dessinerGrille(g);
//       for (int i=0;i<xCase ;i++ ) {
//         for (int j=0;j<yCase ;j++ ) {
//           peintImagePourCase(gc,i,j,g);
//         }
//       }
//       drawPlayingAnt(g);
//     }catch (Exception e) {
//       erreur.erreur("Quelque chose d'imprévu est arrivé lors de l'affichage de PanneauCarte");
//     }
//   }
// }
// class GcComparator<Creature> implements Comparator<Creature>{
//   public int compare(Creature c1, Creature c2) {
//     // return c1.getTaille() - c2.getTaille();
//     return 1;
//   }
// }
