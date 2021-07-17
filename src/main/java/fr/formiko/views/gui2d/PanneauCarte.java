package fr.formiko.views.gui2d;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.CCreature;
import fr.formiko.formiko.CGraine;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Graine;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Joueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.ObjetSurCarteAId;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.Img;
import fr.formiko.usuel.images.Pixel;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.maths.math;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
  // private SubPanel subPanel;

  // CONSTRUCTEUR ---------------------------------------------------------------
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
  }
  // GET SET --------------------------------------------------------------------
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
      Case c = gc.getCCase(x,y).getContenu();
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
          jo = Main.getPartie().getGj().getJoueurHumain().getDébut().getContenu();
        }
        if (jo!=null){//si on a un joueur sélectionné.
          if (x>=0 && y>=0 && jo.getCaseNuageuse(x,y)){//si la case est invisible (nuageuse.)
            g.drawImage(Main.getData().getCNuageuse(),xT,yT,this);
            return true;//on ne dessine rien par dessus.
          }
        }else{//si pas de joueur selcetionné toute les cases sont nuageuse.
          g.drawImage(Main.getData().getCNuageuse(),xT,yT,this);
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
  *@version 1.x
  */
  private void drawPlayingAnt(Graphics g){
    if(Main.getPlayingAnt()!=null){
      Case c = Main.getPlayingAnt().getCCase().getContenu();
      g.drawImage(Main.getData().getSelectionnee(),(c.getX())*Main.getData().getTailleDUneCase(),(c.getY())*Main.getData().getTailleDUneCase(),this);
    }
  }

  /**
  *{@summary Draw a Case.}<br>
  *@version 1.46
  */
  public void peintImagePourCase(Case c, int x, int y,Graphics2D g){
    if(!isCaseVisible(c)){return;}
    Joueur jo = Main.getPlayingJoueur();
    Fourmi fi = Main.getPlayingAnt();
    int xT = x*getTailleDUneCase(); int yT = y*getTailleDUneCase();
    int xT2 = (x)*getTailleDUneCase(); int yT2 = (y)*getTailleDUneCase();
    if(peintCaseNuageuse(x,y,g,xT,yT)){ return;}//si la case est nuageuse, on n'affichera rien d'autre dessus.
    byte ty = c.getType();
    CCreature ccrea = c.getGc().getDébut();
    CGraine ccg = c.getGg().getDébut();
    int lenTIF = Main.getData().getTIF()[0].length+1;
    try {
      int tC10 = Main.getData().getTailleDUneCase()/10;int tC4 = Main.getData().getTailleDUneCase()/4;int tC2 = Main.getData().getTailleDUneCase()/2;
      // anthill
      if (c.getFere()!=null){
        g.drawImage(Main.getData().getFere(),xT+tC4,yT+tC4,this);
        if (needToDrawAnthillColor(c, x, y)) {
          int tailleDuCercle = Main.getTailleElementGraphique(20);
          drawRondOuRect(xT,yT,Main.getData().getTailleDUneCase(),g,c.getFere(),tailleDuCercle);
        }
      }
      if(isSombre(x,y)){
        g.drawImage(Main.getData().getCSombre(),xT,yT,this); // si les créatures sur la case ne sont pas visible.
      }else{
        // les graines
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
        if(seedPrinted){
          while (ccg!=null){
            calculerXYTemp(xT,yT,k,c);k++;
            int dir = getDir((ObjetSurCarteAId)ccg.getContenu());
            try {
              g.drawImage(Main.getData().getTG()[dir][ccg.getContenu().getType()],xTemp,yTemp,this);
            }catch (Exception e) {}
            if(ccg.getContenu().getOuverte()){drawIcone(g,5,xT,yT,tC2,kIcon++,cptIcon);}
            else if(fi==null || ccg.getContenu().getDureté()<=fi.getDuretéMax()){drawIcone(g,4,xT,yT,tC2,kIcon++,cptIcon);}
            else {drawIcone(g,6,xT,yT,tC2,kIcon++,cptIcon);}
            ccg = ccg.getSuivant();
          }
        }
        // les créatures.
        while (ccrea !=null) {
          Creature cr = ccrea.getContenu();
          int dir = getDir((ObjetSurCarteAId)cr);
          boolean insecte = true;
          calculerXYTemp(xT,yT,k,c);k++;
          if(cr instanceof Fourmi){
            //System.out.println(ccrea.getContenu().getClass().equals(new Fourmi().getClass()));
            Fourmi f = ((Fourmi)ccrea.getContenu());
            if(f.getStade()==0){
              g.drawImage(Main.getData().getTIF()[dir][math.min(f.getFourmiliere().getId()-1,lenTIF)],xTemp,yTemp,this);
            }else if(f.getStade()==-1){
              g.drawImage(Main.getData().getTF()[dir][2],xTemp,yTemp,this);
            }else if(f.getStade()==-2){
              g.drawImage(Main.getData().getTF()[dir][1],xTemp,yTemp,this);
            }else{ //stade == -3
              g.drawImage(Main.getData().getTF()[dir][0],xTemp,yTemp,this);
            }
            insecte=false;
          }else if(cr instanceof Insecte){
            Insecte i = (Insecte)(ccrea.getContenu());
            g.drawImage(Main.getData().getTII()[dir][math.min(i.getType(),Main.getData().getTII()[dir].length)],xTemp,yTemp,this);
          }
          //les icone
          try {
            if(cr.getEstMort()){drawIcone(g,3,xT,yT,tC2,kIcon++,cptIcon);}
            else if(fi!=null && cr.getEstAllié(fi)){drawIcone(g,0,xT,yT,tC2,kIcon++,cptIcon);}
            else if(fi!=null && cr.getEstEnnemi(fi) && !insecte){drawIcone(g,2,xT,yT,tC2,kIcon++,cptIcon);}
            else {drawIcone(g,1,xT,yT,tC2,kIcon++,cptIcon);}
          }catch (Exception e) {
            erreur.erreur("impossible de dessiner l'icone de la Case : "+x+" "+y);
          }
          ccrea=ccrea.getSuivant();
        }
      }
    }catch (Exception e) {
      erreur.erreur("impossible de dessiner l'image de la Case : "+x+" "+y);
    }
  }
  /**
  *{@summary return true if case in x,y is sombre.}<br>
  *@version 1.46
  */
  private boolean isSombre(int x, int y){
    Joueur jo = Main.getPlayingJoueur();
    return jo!=null && Main.getPartie().getCarte().getCasesSombres() && jo.getCaseSombre(x,y);
  }
  /**
  *{@summary return true if we need to draw the color of the anthill.}<br>
  *@version 1.46
  */
  private boolean needToDrawAnthillColor(Case c, int x, int y){
    if (drawAllFere) { return true;}
    if(c.getFere().getId()==idCurentFere && !isSombre(x,y)){return true;}
    return (lookedCCase!=null && lookedCCase.getContenu() !=null && lookedCCase.getContenu().equals(c));
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
    if(x==1 || x==2){ return 0;}
    if(x==3 || x==6){ return 1;}
    if(x==9 || x==8){ return 2;}
    return 3;
  }

  public void updateSize(){
    GCase gc = Main.getGc();
    xCase = gc.getNbrX();
    yCase = gc.getNbrY();
    int xTemp = Main.getData().getTailleDUneCase()*xCase;
    int yTemp = Main.getData().getTailleDUneCase()*yCase;
    super.setSize(xTemp,yTemp);
  }

  public void setDesc(String s){
    if(getView().getPp().getPj()==null){ erreur.erreur("pj null");}
    try {
      getView().getPp().getPj().getPb().setDesc(s);
    }catch (Exception e) {
      erreur.alerte("Impossible de setDesc pour la carte.");
    }
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
