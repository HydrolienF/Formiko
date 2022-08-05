package fr.formiko.views.gui2d;

import fr.formiko.formiko.Blade;
import fr.formiko.formiko.CCase;
import fr.formiko.formiko.GGraine;
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
import fr.formiko.formiko.MapPath;
import fr.formiko.formiko.ObjetSurCarteAId;
import java.awt.RenderingHints;
import fr.formiko.usual.Info;
import fr.formiko.usual.Point;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.images.Img;
import fr.formiko.usual.images.Pixel;
import fr.formiko.usual.images.Images;
import fr.formiko.usual.maths.allea;
import fr.formiko.usual.maths.math;
import fr.formiko.usual.structures.listes.Liste;

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
*Mouse listener part is in FPanelSup.java.<br>
*@author Hydrolien
*@lastEditedVersion 1.42
*/
public class FPanelCarte extends FPanel {
  private int xCase; // nombre de case en X
  private int yCase; // nombre de case en Y
  private int xTemp,yTemp;
  private int idCurentFere=-1;
  private CCase lookedCCase;
  private Map<Integer,Point> hashMapMovingObjectSurCarteAid;
  private Map<Integer,Point> hashMapMovingObjectSurCarteAidRotation;
  // private SubPanel subPanel;
  private static Comparator<Creature> imageSizeComparator = (Creature p1, Creature p2) -> (int)(p1.getEspece().getSize(p1.getStade()) - p2.getEspece().getSize(p2.getStade()));
  private BufferedImage iconImage;
  private BufferedImage bladeImage;
  private BufferedImage tBiState []=null;
  private int TRANSPARENCY = 180;

  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelCarte(){}
  /**
  *{@summary Build methode.}<br>
  *@lastEditedVersion 1.x
  */
  public void build(){
    // Main.getData().setTailleDUneCase(Main.getTailleElementGraphique(100));
    // Main.getData().setTailleDUneCase(Main.getTailleElementGraphique(1));
    GCase gc = new GCase(1,1);
    xCase = gc.getWidth();
    yCase = gc.getHeight();
    hashMapMovingObjectSurCarteAid = new HashMap<Integer,Point>();
    hashMapMovingObjectSurCarteAidRotation = new HashMap<Integer,Point>();
  }
  // GET SET -------------------------------------------------------------------
  public int getTailleDUneCase(){return Main.getData().getTailleDUneCase();}
  public int getTailleIcon(){return Main.getData().getTailleIcon();}
  /**
  *{@summary Update Case size.}<br>
  *@param x new value for Case size
  *@param updateMap if true update the map to
  *@lastEditedVersion 2.22
  */
  public void setTailleDUneCase(int x, boolean updateMap){
    if(x!=getTailleDUneCase()){Main.getData().setTailleDUneCase(x);
      if(updateMap){
        actualiserCarte();
      }
    }
  }
  /***
  *{@summary Update Case size &#38; map.}<br>
  *@param x new value for Case size
  *@lastEditedVersion 2.22
  */
  public void setTailleDUneCase(int x){setTailleDUneCase(x, true);}
  public int getXCase(){ return xCase;}
  public void setXCase(int x){xCase = x;}
  public int getYCase(){ return yCase;}
  public void setYCase(int y){yCase = y;}
  public void setIdCurentFere(int x){idCurentFere=x;}
  public CCase getLookedCCase(){return lookedCCase;}
  public void setLookedCCase(CCase cc){lookedCCase=cc;}
  public void setLigne(Graphics2D g){
    BasicStroke ligne = new BasicStroke(Main.getSizeOfMapLines());
    g.setStroke(ligne);
    g.setColor(Color.BLACK);
  }
  /**
  *setSize sould never be used. Use updateSize insted.
  *@lastEditedVersion 1.x
  */
  @Override
  public void setSize(int x, int y){
    erreur.alerte("Not alowed to setSize here ! (Nothing have been done.)");
  }
  /**
  *Do the 3 steps that are need to set FPanelCarte to a new size.
  *@lastEditedVersion 1.x
  */
  public void actualiserCarte(){
    updateSize();//actualise la taille du FPanelCarte a la bonne dimention.
    //chargerImages();
    Main.getData().iniBackgroundMapImage(); //demande au donnée d'image de rechargé l'image qui représente l'arrière plan de la carte.
  }
  public BufferedImage[] getTBIState(){if(tBiState==null){iniTBiState();} return tBiState;}
  // Fonctions propre ----------------------------------------------------------
  /**
  *{@summary Main paint function for Map.}<br>
  *It print 1 by 1: map with only Case, grids, all map element Case by Case, the mark for all game as playingAnt mark.
  *@lastEditedVersion 1.42
  */
  public void paintComponent(Graphics g2){
    if(!Main.getActionGameOn()){return;}
    try {
      if(!Main.getPartie().getEnCours()){return;}
    }catch (Exception e) {
      erreur.alerte("la partie est null");
    }
    Main.startCh();

    Graphics2D g = (Graphics2D)g2;
    setLigne(g);
    try {
      GCase gc = Main.getGc();
      updateSize();
      long time = System.currentTimeMillis();
      debug.débogage("Dimention du FPanelCarte en case : x="+xCase+" y="+yCase);
      debug.débogage("taille réèle du panneau de la carte : x="+this.getWidth()+", y="+this.getHeight());
      try {
        if(Main.getData().getMap()==null){Main.getData().iniBackgroundMapImage();}
        g.drawImage(Main.getData().getMap(),0,0,this);
      }catch (Exception e) {
        erreur.erreur("impossible d'afficher l'arrière plan de la carte");
      }
      dessinerGrille(g);
      iconImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
      if(debug.getPerformance()){
        // erreur.info("Time for grid & backgroundImage: "+(System.currentTimeMillis()-time),0);
      }
      time = System.currentTimeMillis();
      drawBlades(g, gc);
      if(debug.getPerformance()){
        // erreur.info("Time for draw blade: "+(System.currentTimeMillis()-time),0);
      }
      time = System.currentTimeMillis();
      for (int i=0; i<xCase; i++) {
        for (int j=0; j<yCase; j++) {
          peintImagePourCase(gc,i,j,g);
        }
      }
      if(debug.getPerformance()){
        // erreur.info("Time for refrech all Map Case: "+(System.currentTimeMillis()-time),0);
      }
      if (Main.getDrawRelationsIcons() || Main.getOp().getDrawStatesIconsLevel()<4){
        if(iconImage!=null){
          drawImage(g, iconImage, 0, 0);
        }
      }
      drawPlayingAnt(g);
      if(getView().getMoveMode()){
        drawMovingPath(g);
      }
    }catch (Exception e) {
      new Info("Quelque chose d'imprévu est arrivé lors de l'affichage de FPanelCarte").setClassDepth(10);
    }
    Main.endCh("repaintDeLaCarte");
  }
  /**
  *{@summary Draw grid.}<br>
  *@lastEditedVersion 1.x
  */
  public void dessinerGrille(Graphics g){
    if(Main.getDrawGrid()){
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
  public void drawBlades(Graphics g, GCase gc){
    if(getView().getBladeChanged()){
      getView().setBladeChanged(false);
      bladeImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics gBlade = bladeImage.getGraphics();
      // TODO if (op.getGraphicsLevel()>medium){
        Graphics2D g2d = (Graphics2D)gBlade;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //good for all drawLine, drawCircle etc.
      // }
      for (int i=0; i<xCase; i++) {
        for (int j=0; j<yCase; j++) {
          Point point = getPointFromCase(i,j,false);
          int xT = point.getX(); int yT = point.getY();
          if(Main.getOp().getDrawBlades()){
            for (Blade b : gc.getCCase(i,j).getContent().getGb()) {
              b.draw(gBlade, xT, yT);
            }
          }else{
            gBlade.setColor(new Color(0,180,0, math.min(255,gc.getCCase(i,j).getContent().getFoodInsecte()*2)));
            gBlade.fillRect(xT+1, yT+1, getTailleDUneCase()-2, getTailleDUneCase()-2);
          }
        }
      }
    }
    //TOFIX it don't draw all time greeen part.
    drawImage(g, bladeImage, 0, 0);
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
  *@lastEditedVersion 2.17
  */
  public boolean peintCaseNuageuse(int x, int y, Graphics g, int xT, int yT){
    if(needToPaintAll()){return false;}
    Joueur jo = Main.getPlayingJoueur();
    if(Main.getPartie().getCarte().getCasesNuageuses()==true){ //si il y a des cases nuageuses
      try {
        if(Main.getPartie().getGj().getNbrDeJoueurHumain()==1){//si il ya moins de 2 joueurs, on peu afficher les cases que le joueur voie.
          jo = Main.getPartie().getGj().getJoueurHumain().getFirst();
        }
        if (jo==null || (x>=0 && y>=0 && jo.getCaseNuageuse(x,y))){//0 playing player or caseNuageuse for the playing player.
          // drawImage(g,Main.getData().getCloudMap(),xT,yT);
          g.drawImage(Main.getData().getCloudMap(), xT, yT, xT+getTailleDUneCase(), yT+getTailleDUneCase(), xT, yT, xT+getTailleDUneCase(), yT+getTailleDUneCase(), null);
          return true;//nothing more to draw on this Case
        }
      }catch (Exception e) {
        erreur.erreur("Une case possiblement nuageuse n'as pas pue être affiché");
      }
    }
    return false;
  }
  /**
  *{@summary Draw the mark for playingAnt.}<br>
  *@lastEditedVersion 2.5
  */
  private void drawPlayingAnt(Graphics g){
    Fourmi playingAnt = Main.getPlayingAnt();
    if(playingAnt!=null && !playingAnt.getIa()){
      CCase cc = playingAnt.getCCase();
      drawCircle(g, cc, Main.getData().getButtonColor(0));
    }
  }
  /**
  *{@summary Draw the path from playingAnt to the looked case.}<br>
  *@lastEditedVersion 2.11
  */
  private void drawMovingPath(Graphics g){
    CCase to = getLookedCCase();
    CCase from = Main.getPlayingAnt().getCCase();
    if(from.equals(to)){return;}
    MapPath mp = new MapPath(from, to);
    mp.updateMovingCaseByTurn(Main.getPlayingAnt());
    Liste<Integer> li = mp.getMovingCaseByTurn();
    int k=li.pop();
    int turnCount=0;
    CCase last=null;
    boolean drawOnLast = false;
    for (CCase cc : mp.getList()) {
      if(k<1){
        if(!li.isEmpty()){
          turnCount++;
          k=li.pop();
          drawWhiteLine(g, last, cc, true, false);
          drawWhiteCircle(g, cc, turnCount);
        }
        drawOnLast=false;
      }else{
        if(cc.equals(to)){
          turnCount++;
          drawWhiteLine(g, last, cc, drawOnLast, false);
          drawWhiteCircle(g, cc, turnCount);
        }else{
          if(last!=null){
            drawWhiteLine(g, last, cc, drawOnLast, true);
          }
        }
        if(last!=null){
          drawOnLast=true;
        }
      }
      last=cc;
      k--;
    }
  }
  /**
  *{@summary Draw a white circle on giving Case.}<br>
  *@param g graphics where to draw
  *@param cc CCase where to draw
  *@lastEditedVersion 2.11
  */
  private void drawWhiteCircle(Graphics g, CCase cc, int toPrint){
    drawCircle(g, cc, Color.WHITE);
    int tc=getTailleDUneCase();
    g.setFont(g.getFont().deriveFont((float)(tc*0.8)));
    g.drawString(""+toPrint, (int)((cc.getX()+0.25)*tc),(int)((cc.getY()+0.75)*tc));
    // g.drawString(""+toPrint, cc.getX()*tc,cc.getY()*tc);
  }
  /**
  *{@summary Draw a colored circle on a giving Case.}<br>
  *@param g graphics where to draw
  *@param cc CCase where to draw
  *@param col Color to use
  *@lastEditedVersion 2.11
  */
  private void drawCircle(Graphics g, CCase cc, Color col){
    int tc=getTailleDUneCase();
    int x=cc.getX()*tc;
    int y=cc.getY()*tc;
    col = new Color(col.getRed(), col.getGreen(), col.getBlue(), TRANSPARENCY);
    if(g instanceof Graphics2D){
      Graphics2D g2d = (Graphics2D)g;
      g2d.setColor(col);
      BasicStroke line = new BasicStroke(math.min(Main.getTailleElementGraphique(15),tc/2));
      g2d.setStroke(line);
      g2d.drawOval(x,y,tc,tc);
    }
  }
  /**
  *{@summary Draw a white line on giving Case.}<br>
  *@param g graphics where to draw
  *@param from CCase where to start draw
  *@param to CCase where to end draw
  *@lastEditedVersion 2.11
  */
  private void drawWhiteLine(Graphics g, CCase from, CCase to, boolean drawOnFrom, boolean drawOnTo){
    drawLine(g, from, to, Color.WHITE, drawOnFrom, drawOnTo);
  }
  /**
  *{@summary Draw a colored line on a giving Case.}<br>
  *@param g graphics where to draw
  *@param from CCase where to start draw
  *@param to CCase where to end draw
  *@param col Color to use
  *@lastEditedVersion 2.11
  */
  private void drawLine(Graphics g, CCase from, CCase to, Color col, boolean drawOnFrom, boolean drawOnTo){
    int tc=getTailleDUneCase();
    int lineStroke = math.min(Main.getTailleElementGraphique(10),tc/4);
    int x1=(int)(((double)from.getX()+0.5)*tc);
    int y1=(int)(((double)from.getY()+0.5)*tc);
    int x2=(int)(((double)to.getX()+0.5)*tc);
    int y2=(int)(((double)to.getY()+0.5)*tc);
    if(!drawOnFrom){
      if(x2 < x1){x1-=(int)(0.5*tc);}
      else if(x2 > x1){x1+=(int)(0.5*tc);}
      if(y2 < y1){y1-=(int)(0.5*tc);}
      else if(y2 > y1){y1+=(int)(0.5*tc);}
    }
    if(!drawOnTo){
      if(x2 > x1){x2-=(int)(0.5*tc);}
      else if(x2 < x1){x2+=(int)(0.5*tc);}
      if(y2 > y1){y2-=(int)(0.5*tc);}
      else if(y2 < y1){y2+=(int)(0.5*tc);}
    }
    col = new Color(col.getRed(), col.getGreen(), col.getBlue(), TRANSPARENCY);
    if(g instanceof Graphics2D){
      Graphics2D g2d = (Graphics2D)g;
      g2d.setColor(col);
      BasicStroke line = new BasicStroke(lineStroke);
      g2d.setStroke(line);
      g2d.drawLine(x1,y1,x2,y2);
    }
  }
  /**
  *{@summary Draw a colored line on a giving Case.}<br>
  *@param g graphics where to draw
  *@param from CCase where to start draw
  *@param to CCase where to end draw
  *@param col Color to use
  *@lastEditedVersion 2.11
  */
  private void drawLine(Graphics g, CCase from, CCase to, Color col){
    drawLine(g,from,to,col,true,true);
  }
  /**
  *{@summary Return the coordinates on screen for Case with x &#38; y.}<br>
  *@param x the x of the Case.
  *@param y the y of the Case.
  *@param centered True return the coordinates of the center of the case. False return left top coordinates.
  *@lastEditedVersion 2.1
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
  *@lastEditedVersion 2.23
  */
  public void peintImagePourCase(Case c, int x, int y,Graphics2D g){
    if(!isCaseVisible(c)){return;}
    // Joueur jo = Main.getPlayingJoueur();
    Fourmi fi = Main.getPlayingAnt();
    Liste<BufferedImage> listIconsRelation = new Liste<BufferedImage>();
    Liste<BufferedImage> listIconsState = new Liste<BufferedImage>();
    if(fi==null && Main.getPlayingJoueur()!=null && !Main.getPlayingJoueur().getFere().getGc().isEmpty()){
      fi = (Fourmi)Main.getPlayingJoueur().getFere().getGc().getFirst();
    }
    if(Main.getPlayingJoueur()==null && Main.getPartie().getPartieFinie() && Main.getPartie().getWinner()!=null){
      fi=(Fourmi)Main.getPartie().getWinner().getFere().getGc().getFirst();
    }
    Point point = getPointFromCase(x,y,false);
    int xT = point.getX(); int yT = point.getY();
    int xT2 = (x)*getTailleDUneCase(); int yT2 = (y)*getTailleDUneCase();
    if(peintCaseNuageuse(x,y,g,xT,yT)){return;}//si la case est nuageuse, on n'affichera rien d'autre dessus.
    byte ty = c.getType();
    GGraine gg = c.getGg();
    Graphics gIcon=null;
    if(iconImage!=null){
      gIcon = iconImage.getGraphics();
    }
    try {
      int tC10 = getTailleDUneCase()/10;int tC4 = getTailleDUneCase()/4;int tC2 = getTailleDUneCase()/2;
      // anthill
      if (c.getFere()!=null){
        drawImage(g,Main.getData().getFere(),xT+tC4,yT+tC4);
        if (needToDrawAnthillColor(c, x, y)) {
          int tailleDuCercle = Main.getTailleElementGraphique(20);
          drawRondOuRect(xT,yT,getTailleDUneCase(),g,c.getFere(),tailleDuCercle);
        }
      }
      if(isSombre(x,y)){
        drawImage(g,Main.getData().getCSombre(),xT,yT); // si les créatures sur la case ne sont pas visible.
      }else{
        int k=0;
        //seeds
        if(Main.getOp().getDrawSeeds() && (!Main.getOp().getDrawOnlyEatable() || Main.getPlayingJoueur()==null || Main.getPlayingJoueur().getEspece().getGranivore())){
          for (Graine gr : gg) {
            calculerXYTemp(xT,yT,k,c);k++;
            int dir = getDir((ObjetSurCarteAId)gr);
            try {
              BufferedImage bi = Main.getData().getGraineImage(gr);
              drawImageCentered(g, Images.rotateImage(bi,dir),xT,yT);
            }catch (Exception e) {}
            if(gr.getOuverte()){listIconsRelation.add(getIconImage(5));}
            else if(fi==null || gr.getHardness()<=fi.getHardnessMax()){listIconsRelation.add(getIconImage(4));}
            else {listIconsRelation.add(getIconImage(6));}
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
          calculerXYTemp(xT,yT,k,c);k++;
          if(cr instanceof Fourmi && needToDraw(cr)){
            Fourmi f = ((Fourmi)cr);
            try {
              BufferedImage bi = Main.getData().getCreatureImage(f);
              drawImageCentered(g,Images.rotateAndCenterImage(bi, dir),xT+x2,yT+y2);
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
          }else if(cr instanceof Insecte && needToDraw(cr)){
            Insecte i = (Insecte)(cr);
            try {
              BufferedImage bi = Main.getData().getCreatureImage(cr);
              // BufferedImage bi = Main.getData().getTII()[0][math.min(i.getType(),Main.getData().getTII()[0].length)];
              drawImageCentered(g,Images.rotateAndCenterImage(bi,dir),xT+x2,yT+y2);
            }catch (Exception e) {
              erreur.erreur("can't draw insect "+i.getId()+" with type "+i.getType());
            }
          }
          //icons
          if(fi!=null){
            listIconsRelation.add(getIconImage(cr, fi));
            if(cr.getEstAllié(fi) && Main.getOp().getDrawStatesIconsLevel()<4){
              listIconsState.add(getStatesIconsImages(cr));
            }
            // drawIcon(g,getIconImage(cr, fi),xT,yT,tC2,kIcon++,cptIcon);
            // if (!getIa() && playingJoueur().equals(c.getJoueur()))
            // listIconsState.add()...
          }
        }
        //draw icons
        if (Main.getDrawRelationsIcons()){
          drawListIcons(gIcon, listIconsRelation, xT, yT, getTailleDUneCase()-getTailleIcon());
        }
        if (Main.getOp().getDrawStatesIconsLevel()<4) {
          drawListIcons(gIcon, listIconsState, xT, yT, 0);
        }
      }
    }catch (Exception e) {
      erreur.erreur("Fail to draw Case: "+x+" "+y+" because of "+e);
      // e.printStackTrace();
    }
  }
  /**
  *{@summary Return true if we need to draw Creature depending of the Options.}<br>
  *@lastEditedVersion 2.10
  */
  public static boolean needToDraw(Creature cr){
    if(cr==null){return false;}
    Fourmi playingAntOrPlayingPlayerAnt = Main.getPlayingAnt();
    if(playingAntOrPlayingPlayerAnt==null && Main.getPlayingJoueur()!=null){
      playingAntOrPlayingPlayerAnt=(Fourmi)Main.getPlayingJoueur().getFere().getGc().getFirst();
    }
    if(playingAntOrPlayingPlayerAnt==null){return true;}
    boolean friendlyInsectPrinted = Main.getOp().getDrawAllyCreatures();
    boolean neutralInsectPrinted = Main.getOp().getDrawNeutralCreatures();
    boolean enemyInsectPrinted = Main.getOp().getDrawEnemyCreatures();
    if(friendlyInsectPrinted && neutralInsectPrinted && enemyInsectPrinted && !Main.getOp().getDrawOnlyEatable()){return true;}
    else if(cr.getEstEnnemi(playingAntOrPlayingPlayerAnt) && enemyInsectPrinted){return true;}
    else if(cr.getEstAllié(playingAntOrPlayingPlayerAnt) && friendlyInsectPrinted){return true;}
    else if(cr.getIsNeutral(playingAntOrPlayingPlayerAnt) && neutralInsectPrinted && (!Main.getOp().getDrawOnlyEatable() || Main.getPlayingJoueur().getEspece().getInsectivore())){return true;}
    else{return false;}
  }
  /**
  *{@summary Return a Liste&lt;Creature&gt; sorted by image size.}<br>
  *It is used to print smaler creature on top, so that we can see every Creature.
  *@lastEditedVersion 2.6
  */
  //public only for test
  public static Liste<Creature> gcSortedByImageSize(GCreature gc){
    Liste<Creature> listToPrint = gc;
    if(Main.getOp().getRealisticSize()==0){return listToPrint;}
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
  *@lastEditedVersion 2.4
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
  *{@summary draw an image centered.}<br>
  *@param g Graphics element were to draw.
  *@param image the image to draw.
  *@param xT the x of the Case were to draw.
  *@param yT the y of the Case were to draw.
  *@param caseSize size of the Case
  *@lastEditedVersion 2.1
  */
  private void drawImageCentered(Graphics g, BufferedImage image, int xT, int yT, int caseSize){
    if(image==null){
      // erreur.alerte("Can't draw a null image");
      // return;
      throw new NullPointerException();
    }
    int w = image.getWidth();
    int h = image.getHeight();
    int xI = xT+(caseSize-w)/2;
    int yI = yT+(caseSize-h)/2;
    drawImage(g,image,xI,yI);
  }
  /**
  *{@summary draw an image centered for a Case.}<br>
  *@param g Graphics element were to draw.
  *@param image the image to draw.
  *@param xT the x of the Case were to draw.
  *@param yT the y of the Case were to draw.
  *@lastEditedVersion 2.1
  */
  private void drawImageCentered(Graphics g, BufferedImage image, int xT, int yT){
    drawImageCentered(g,image,xT,yT,getTailleDUneCase());
  }
  /**
  *{@summary draw an image.}<br>
  *@param gTemp Graphics element were to draw.
  *@param im the image to draw.
  *@param x the x ere to draw.
  *@param y the y were to draw.
  *@lastEditedVersion 2.1
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
  *@lastEditedVersion 1.46
  */
  private boolean isSombre(int x, int y){
    if(needToPaintAll()){return false;}
    Joueur jo = Main.getPlayingJoueur();
    return jo==null || jo.getIa() || (Main.getPartie().getCarte().getCasesSombres() && jo.getCaseSombre(x,y));
  }
  /**
  *{@summary return true if we need to draw the color of the anthill.}<br>
  *@lastEditedVersion 1.46
  */
  private boolean needToDrawAnthillColor(Case c, int x, int y){
    if (Main.getOp().getDrawAllAnthillColor()) { return true;}
    if(c.getFere().getId()==idCurentFere){return true;} // && !isSombre(x,y)
    return (getLookedCCase()!=null && getLookedCCase().getContent() !=null && getLookedCCase().getContent().equals(c));
  }
  /**
  *{@summary fonction that place ObjetSurCarteAId on the same Case.}<br>
  *It modify FPanelCarte value xTemp and yTemp.
  *@lastEditedVersion 1.x
  */
  public void calculerXYTemp(int xT, int yT, int k, Case c){
    int deplacementEnX=0;
    int deplacementEnY=0;
    //deplacement centré.
    int espaceLibre = getTailleDUneCase()/5;
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
  *@lastEditedVersion 1.x
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
  *@lastEditedVersion 1.x
  */
  public void drawRond(int x, int y, int r, Graphics2D g, Fourmiliere fere, int tailleDuCercle){
    g.setColor(fere.getPh().getColor());
    BasicStroke line = new BasicStroke(tailleDuCercle);
    g.setStroke(line);
    g.drawOval(x+tailleDuCercle/2,y+tailleDuCercle/2,r-tailleDuCercle,r-tailleDuCercle);
    setLigne(g);//retour au paramètres par défaut.
  }
  /**
  *{@summary Draw an icon.}<br>
  *@param g the Graphics where to draw
  *@param iconImage the icon image to draw
  *@param xT x value to use
  *@param yT y value to use
  *@param xOffset offset in x
  *@lastEditedVersion 2.8
  */
  public void drawIcon(Graphics g, BufferedImage iconImage, int xT, int yT, int xOffset){
    g.drawImage(iconImage,xT+xOffset,yT,this);
  }
  /**
  *{@summary Draw an icons list.}<br>
  *@param g the Graphics where to draw
  *@param list the list of icon image to draw
  *@param xT x value to use
  *@param yT y value to use
  *@param xOffset offset in x
  *@lastEditedVersion 2.8
  */
  public void drawListIcons(Graphics g, Liste<BufferedImage> list, int xT, int yT, int xOffset){
    int len = list.length();
    int k=0;
    if(len>1){
      int angle = (int)(360.0/(double)(len)+0.99); //to avoid to have smaler angle that don't fill 360°
      for (BufferedImage biIn : list ) {
        //TODO replace true by Main.getOp().getSimplerIcons()
        BufferedImage biOut = doSlice(angle, biIn, k, true);
        drawIcon(g,biOut,xT,yT,xOffset);
        k++;
      }
    }else if(len==1){
      drawIcon(g,list.getFirst(),xT,yT,xOffset);
    }
  }
  /**
  *{@summary Do a slice of angle from a BufferedImage.}<br>
  *@param angle the angle of the slice
  *@param biIn the BufferedImage in
  *@param k offset in angle (k * the angle)
  *@param replaceColorByImage if true we replace font color by the image in
  *@return a slice of the BufferedImage in
  *@lastEditedVersion 2.8
  */
  private BufferedImage doSlice(int angle, BufferedImage biIn, int k, boolean replaceColorByImage){
    BufferedImage biOut = new BufferedImage(biIn.getWidth(), biIn.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D gOut = (Graphics2D)biOut.getGraphics();
    int colorValue = biIn.getRGB(biIn.getWidth()/4,biIn.getHeight()*3/4);
    Color fontColor = new Color(colorValue);
    gOut.setColor(fontColor);
    gOut.fillArc(0,0,biOut.getWidth(),biOut.getHeight(),90+k*angle, angle);
    if(replaceColorByImage){
      int w = biIn.getWidth();
      int h = biIn.getHeight();
      for (int i=0; i<w; i++) {
        for (int j=0; j<h; j++) {
          if(biOut.getRGB(i,j)==colorValue){
            biOut.setRGB(i,j,biIn.getRGB(i,j));
          }
        }
      }
    }
    return biOut;
  }

  /**
  *{@summary Return an icon id.}<br>
  *@param cr the Creature that we represent by the icon
  *@param fi the Creature that whant to know how to see the other Creature
  *@return the icone id corresponding to the relation from the ant to the Creature.
  *@lastEditedVersion 2.7
  */
  public static int getIconId(Creature cr, Fourmi fi){
    if(cr==null){
      erreur.alerte("Can't get icon from a null Creature");
      return 0;
    }
    if(cr.getIsDead()){return 3;}
    else if(fi!=null && cr.getEstAllié(fi)){return 0;}
    else if(fi!=null && cr.getEstEnnemi(fi) && cr instanceof Fourmi){return 2;}
    else{return 1;}
  }
  /**
  *{@summary Return an icon image.}<br>
  *@param cr the Creature that we represent by the icon
  *@param fi the Creature that whant to know how to see the other Creature
  *@return the icone image corresponding to the relation from the ant to the Creature
  *@lastEditedVersion 2.7
  */
  public static BufferedImage getIconImage(Creature cr, Fourmi fi){
    if(Main.getData().getB()==null){return null;}
    return Main.getData().getB()[getIconId(cr,fi)];
  }
  /**
  *{@summary Return an icon image.}<br>
  *@param id the id of the icon
  *@return the icone image corresponding to the id
  *@lastEditedVersion 2.7
  */
  public BufferedImage getIconImage(int id){
    if(Main.getData().getB()==null){return null;}
    return Main.getData().getB()[id];
  }
  /**
  *{@summary Return the states icons images.}<br>
  *@param cr the Creature that we whant to print icons states
  *@lastEditedVersion 2.10
  */
  public Liste<BufferedImage> getStatesIconsImages(Creature cr){
    int minPrintState = Main.getOp().getDrawStatesIconsLevel(); // between 0 & 4 (3= only red state, 0=all).
    Liste<BufferedImage> list = new Liste<BufferedImage>();
    int state = cr.getStateFood();
    if(tBiState==null){iniTBiState();}
    if(state>=minPrintState){
      list.add(getStateIconImage(Main.getData().getButtonColor(state),tBiState[0]));
    }
    state = cr.getStateHealth();
    if(state>=minPrintState){
      list.add(getStateIconImage(Main.getData().getButtonColor(state),tBiState[1]));
    }
    if(cr.getAge() >= cr.getMaxAge()){
      state = 7;
      if(cr.getStade()==0){state = 3;}
      if(state%6>=minPrintState){ //state 7 = state 0 in priority.
        list.add(getStateIconImage(Main.getData().getButtonColor(state),tBiState[2]));
      }
    }
    return list;
  }
  /**
  *{@summary Return a state icon image.}<br>
  *A colored round containing an icon image.
  *@lastEditedVersion 2.10
  */
  public BufferedImage getStateIconImage(Color col, BufferedImage icon){
    int iconSize = getTailleIcon();
    if(iconSize<1){return null;}
    BufferedImage ir = new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_ARGB);
    Graphics g = ir.getGraphics();
    g.setColor(col);
    g.fillOval(0,0,iconSize, iconSize);
    if(icon!=null){
      drawImageCentered(g,icon,0,0,iconSize);
    }
    return ir;
  }

  public int getDir(ObjetSurCarteAId obj){
    if (!Main.getOrientedObjectOnMap()){return 0;}// si la direction de l'objet n'est pas prise en compte on cherche dans le tableau 0.
    int x = obj.getDirection();
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
    xCase = gc.getWidth();
    yCase = gc.getHeight();
    int xTemp = getTailleDUneCase()*xCase;
    int yTemp = getTailleDUneCase()*yCase;
    super.setSize(xTemp,yTemp);
    if(Main.getPlayingJoueur()!=null && !Main.getPlayingJoueur().isIa()){
      getView().getPs().setSize(xTemp, yTemp);
    }
    iniTBiState();
  }

  /**
  *{@summary Tool to save performances by drawing only visible Case.}<br>
  *@param c Case to check
  *return true if case is visible
  *@lastEditedVersion 2.13
  */
  public boolean isCaseVisible(Case c){
    FPanel p = getView().getPmmo();
    return isCaseVisible(c, -getX(), -getY(), p.getWidth()-getX(), p.getHeight()-getY(), getTailleDUneCase());
  }
  /**
  *{@summary Tool to save performances by drawing only visible Case.}<br>
  *@param c Case to check
  *return true if case is visible
  *@lastEditedVersion 2.13
  */
  public static boolean isCaseVisible(Case c, int minX, int minY, int maxX, int maxY, int caseSize){
    int x = c.getX()*caseSize;
    minX-=caseSize;
    if(x<minX || x>maxX){return false;}
    int y = c.getY()*caseSize;
    minY-=caseSize;
    if(y<minY || y>maxY){return false;}
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
  public void iniTBiState(){
    tBiState = new BufferedImage[3];
    tBiState[0] = Main.getData().getIconImage("food");
    tBiState[1] = Main.getData().getIconImage("health");
    tBiState[2] = Main.getData().getIconImage("age");
    int size = (int)(getTailleIcon()*0.8);
    for (int i=0; i<tBiState.length; i++) {
      tBiState[i] = Images.resize(tBiState[i],size,size);
    }
  }

  public boolean needToPaintAll(){
    return Main.getPartie()!=null && Main.getPartie().getPartieFinie() && getView().getPfp()!=null;
  }
}
