package fr.formiko.views.gui2d;

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

  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauCarte(){}
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
  public void setLigne(Graphics2D g){
    BasicStroke ligne = new BasicStroke(Main.getDimLigne());
    g.setStroke(ligne);
    g.setColor(Color.BLACK);
  }
  /**
  *setSize sould never be used. Use actualiserSize insted.
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
    actualiserSize();//actualise la taille du PanneauCarte a la bonne dimention.
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
    Graphics2D g = (Graphics2D)g2;
    setLigne(g);
    try {
      GCase gc = Main.getGc();
      actualiserSize();
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
      for (int i=0;i<xCase+1 ;i++ ) {
        int xT = Main.getData().getTailleDUneCase()*i;
        g.drawLine(xT,0,xT,Main.getData().getTailleDUneCase()*yCase);
      }
      for (int i=0;i<yCase+1 ;i++ ) {
        int xT = Main.getData().getTailleDUneCase()*i;
        g.drawLine(0,xT,Main.getData().getTailleDUneCase()*xCase,xT);
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
    Case c = gc.getCCase(x+posX,y+posY).getContenu();
    peintImagePourCase(c,x,y,g);
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
          if ((x+posX)>=0 && (y+posY)>=0 && jo.getCaseNuageuse(x+posX,y+posY)){//si la case est invisible (nuageuse.)
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
      g.drawImage(Main.getData().getSelectionnee(),(c.getX()-posX)*Main.getData().getTailleDUneCase(),(c.getY()-posY)*Main.getData().getTailleDUneCase(),this);
    }
  }

  /**
  *{@summary Draw a Case.}<br>
  *@version 1.x
  */
  public void peintImagePourCase(Case c, int x, int y,Graphics2D g){
    Joueur jo = Main.getPlayingJoueur();
    Fourmi fi = Main.getPlayingAnt();
    if(fi==null){
      try {
        fi=(Fourmi)jo.getFere().getGc().getDébut().getContenu();
      }catch (Exception e) {}
    }
    int xT = x*Main.getData().getTailleDUneCase(); int yT = y*Main.getData().getTailleDUneCase();
    int xT2 = (x-posX)*Main.getData().getTailleDUneCase(); int yT2 = (y-posY)*Main.getData().getTailleDUneCase();
    if(peintCaseNuageuse(x,y,g,xT,yT)){ return;}//si la case est nuageuse, on n'affichera rien d'autre dessus.
    byte ty = c.getType();
    CCreature ccrea = c.getGc().getDébut();
    CGraine ccg = c.getGg().getDébut();
    int lenTIF = Main.getData().getTIF()[0].length+1;
    try {
      int tC10 = Main.getData().getTailleDUneCase()/10;int tC4 = Main.getData().getTailleDUneCase()/4;int tC2 = Main.getData().getTailleDUneCase()/2;
      // la fourmilière
      if (c.getFere()!=null && (drawAllFere || c.getFere().getId()==idCurentFere)){
        g.drawImage(Main.getData().getFere(),xT+tC4,yT+tC4,this);
        int tailleDuCercle = Main.getTailleElementGraphique(20);
        drawRondOuRect(xT,yT,Main.getData().getTailleDUneCase(),g,c.getFere(),tailleDuCercle);
        //affichage d'un rond de la couleur de la fere.
      }
      if(jo!=null && Main.getPartie().getCarte().getCasesSombres() && jo.getCaseSombre(x+posX,y+posY)){
        g.drawImage(Main.getData().getCSombre(),xT,yT,this); // si les créatures sur la case ne sont pas visible.
      }else{
        // les graines
        int k=0;
        if(Main.getAffGraine()){
          while (ccg!=null){
            calculerXYTemp(xT,yT,k,c);k++;
            int dir = getDir((ObjetSurCarteAId)ccg.getContenu());
            try {
              g.drawImage(Main.getData().getTG()[dir][ccg.getContenu().getType()],xTemp,yTemp,this);
            }catch (Exception e) {}
            if(ccg.getContenu().getOuverte()){drawIcone(g,5,xT,yT,tC2);}
            else if(fi==null || ccg.getContenu().getDureté()<=fi.getDuretéMax()){drawIcone(g,4,xT,yT,tC2);}
            else {drawIcone(g,6,xT,yT,tC2);}
            ccg = ccg.getSuivant();
          }
        }
        // les créatures.
        while (ccrea !=null) {
          Creature cr = ccrea.getContenu();
          int dir = getDir((ObjetSurCarteAId)cr);
          boolean insecte = true;
          calculerXYTemp(xT,yT,k,c);k++;
          try {
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
          }catch (Exception e) {
            try {
              Insecte i = (Insecte)(ccrea.getContenu());
              g.drawImage(Main.getData().getTII()[dir][math.min(i.getType(),Main.getData().getTII()[dir].length)],xTemp,yTemp,this);
            }catch (Exception e2) {erreur.erreur("impossible de dessiner l'image de la case : "+x+" "+y);
              System.out.println(ccrea.getContenu());//@a
            }
          }
          //les icone
          if(cr.getEstMort()){drawIcone(g,3,xT,yT,tC2);}
          else if(fi!=null && cr.getEstAllié(fi)){drawIcone(g,0,xT,yT,tC2);}
          else if(fi!=null && cr.getEstEnnemi(fi) && !insecte){drawIcone(g,2,xT,yT,tC2);}
          else {drawIcone(g,1,xT,yT,tC2);}
          ccrea=ccrea.getSuivant();
        }
      }
    }catch (Exception e) {
      erreur.erreur("impossible de dessiner l'image de la Case : "+x+" "+y);
    }
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
  *@version 1.x
  */
  public void drawIcone(Graphics g, int iconeId, int xT, int yT, int xOffset){
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

  public void actualiserSize(){
    GCase gc = Main.getGc();
    xCase = gc.getNbrX();
    yCase = gc.getNbrY();
    int xTemp = Main.getData().getTailleDUneCase()*xCase;
    int yTemp = Main.getData().getTailleDUneCase()*yCase;
    super.setSize(xTemp,yTemp);
  }

  public void setDesc(String s){
    if(Main.getPp().getPj()==null){ erreur.erreur("pj null");}
    try {
      Main.getPp().getPj().getPb().setDesc(s);
    }catch (Exception e) {
      erreur.alerte("Impossible de setDesc pour la carte.");
    }
  }
}
