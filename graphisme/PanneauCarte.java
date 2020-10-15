package fr.formiko.graphisme;
import fr.formiko.graphisme.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import fr.formiko.formiko.*;
import fr.formiko.usuel.image.image;
import fr.formiko.usuel.math.math;
import fr.formiko.usuel.math.allea;
import java.awt.geom.AffineTransform;
import fr.formiko.usuel.image.Img;
import fr.formiko.usuel.image.Pixel;
import fr.formiko.usuel.debug;
import java.awt.BasicStroke;
//yen a un peu trop la desous
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanneauCarte extends Panneau implements MouseListener{
  private int tailleDUneCase; // entre 10 et 500.
  private int tailleDUneCaseBase = 500;
  //private int espaceRéservéBas = 1; // permet de réservé un nombre de case défini d'espace.
  private int xCase; // nombre de case en X
  private int yCase; // nombre de case en Y
  private int posX; // position de la 1a case.
  private int posY;
  private int xTemp,yTemp;
  private boolean imageIni;
  private int scale = Image.SCALE_SMOOTH;
  //image
  private Image imgNull;
  private Image tI1[];
  private Image tI2[];
  private Image tIF[][];
  private Image tII[][];
  private Image selectionnée; private Image fere;
  private Image tG[][];
  private Image tF[][];
  private Graphics g;
  private Image cNuageuse,cSombre;
  private Image b[];
  //ini
  private Image imgNullIni;
  private Image tI1Ini[];
  private Image tI2Ini[];
  private Image tIFIni[][];
  private Image tIIIni[][];
  private Image selectionnéeIni; private Image fereIni;
  private Image tGIni[][];
  private Image tFIni[][];
  private Graphics gIni;
  private Image cNuageuseIni,cSombreIni;
  private Image bIni[];

  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauCarte(){}
  public void construire(){
    tailleDUneCase = Main.getTailleElementGraphique(100);
    posX = 0; posY = 0;
    GCase gc = Main.getGc();
    xCase = gc.getNbrX();
    yCase = gc.getNbrY();
    int xT = tailleDUneCase*xCase;
    int yT = tailleDUneCase*yCase;
    this.setSize(xT,yT);
  }
  // GET SET --------------------------------------------------------------------
  public int getTailleDUneCase(){return tailleDUneCase;}
  public void setTailleDUneCase(int x){tailleDUneCase = x;}
  public int getXCase(){ return xCase;}
  public void setXCase(int x){xCase = x;}
  public int getYCase(){ return yCase;}
  public void setYCase(int y){yCase = y;}
  public int getPosX(){ return posX;}
  public void setPosX(int x){posX=x; }
  public int getPosY(){ return posY;}
  public void setPosY(int x){posY=x; }
  public void setLigne(Graphics2D g){
    BasicStroke ligne = new BasicStroke(Main.getDimLigne());
    g.setStroke(ligne);
    g.setColor(Color.BLACK);
  }
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g2){
    //Main.débutCh();
    Graphics2D g = (Graphics2D)g2;
    setLigne(g);
    try {
      if(!Main.getPartie().getEnCours()){return;}
    }catch (Exception e) {
      erreur.alerte("la partie est null");
    }
    try {
      GCase gc = Main.getGc();
      int x = Main.getPp().getWidth();
      int y = Main.getPp().getHeight();
      this.setSize(x,y);
      xCase = math.min((x/tailleDUneCase)+1,gc.getNbrX()-posX);
      yCase = math.min((y/tailleDUneCase)+1,gc.getNbrY()-posY);
      debug.débogage("Dimention du PanneauCarte en case : x="+xCase+" y="+yCase);
      debug.débogage("taille réèle du panneau de la carte : x="+this.getWidth()+", y="+this.getHeight());
      //dessin des cases :
      for (int i=0;i<xCase ;i++ ) {
        for (int j=0;j<yCase ;j++ ) {
          peintImagePourCase(gc,i,j,g);
        }
      }
      dessinerGrille(g);
      //dessin de la Fourmi selectionnée :
      if(Main.getFActuelle()!=null){
        Case c = Main.getFActuelle().getCCase().getContenu();
        g.drawImage(selectionnée,(c.getX()-posX)*tailleDUneCase,(c.getY()-posY)*tailleDUneCase,this);
      }
    }catch (Exception e) {
      erreur.erreur("Quelque chose d'imprévue est arrivé lors de l'affichage de PanneauCarte");
    }
    //Main.finCh("repaintDeLaCarte");
  }

  public void dessinerGrille(Graphics g){
    if(Main.getDessinerGrille()){
      for (int i=0;i<xCase+1 ;i++ ) {
        int xT = tailleDUneCase*i;
        g.drawLine(xT,0,xT,tailleDUneCase*yCase);
      }
      for (int i=0;i<yCase+1 ;i++ ) {
        int xT = tailleDUneCase*i;
        g.drawLine(0,xT,tailleDUneCase*xCase,xT);
      }
    }
  }
  public void repaintParciel(Case c){
    peintImagePourCase(c,(Graphics2D)g);
  }
  public void peintImagePourCase(Case c,Graphics2D g){
    int x = c.getX(); int y = c.getY();
    peintImagePourCase(c,x,y,g);
  }
  public void peintImagePourCase(GCase gc, int x, int y,Graphics2D g){
    Case c = gc.getCCase(x+posX,y+posY).getContenu();
    peintImagePourCase(c,x,y,g);
  }
  public boolean peintCaseNuageuse(int x, int y,Graphics g,int xT, int yT){
    Joueur jo = Main.getPj().getJoueurActuel();
    if(Main.getPartie().getCarte().getCasesNuageuses()==true){ //si il y a des cases nuageuses
      try {
        if(Main.getPartie().getGj().getNbrDeJoueurHumain()==1){//si il ya moins de 2 joueurs, on peu afficher les cases que le joueur voie.
          jo = Main.getPartie().getGj().getJoueurHumain().getDébut().getContenu();
        }
        if (jo!=null){//si on a un joueur sélectionné.
          if ((x+posX)>=0 && (y+posY)>=0 && jo.getCaseNuageuse(x+posX,y+posY)){//si la case est invisible (nuageuse.)
            g.drawImage(cNuageuse,xT,yT,this);
            return true;//on ne dessine rien par dessus.
          }
        }else{//si pas de joueur selcetionné toute les cases sont nuageuse.
          g.drawImage(cNuageuse,xT,yT,this);
          return true;//on ne dessine rien par dessus.
        }
      }catch (Exception e) {
        erreur.erreur("Une case possiblement nuageuse n'as pas pue atre affiché","PanneauCarte");
      }
    }
    return false;
  }


  public void peintImagePourCase(Case c, int x, int y,Graphics2D g){
    Joueur jo = Main.getPj().getJoueurActuel();
    Fourmi fi = Main.getFActuelle();
    if(fi==null){
      try {
        fi=(Fourmi)jo.getFere().getGc().getDébut().getContenu();
      }catch (Exception e) {}
    }
    int xT = x*tailleDUneCase; int yT = y*tailleDUneCase;
    int xT2 = (x-posX)*tailleDUneCase; int yT2 = (y-posY)*tailleDUneCase;
    if(peintCaseNuageuse(x,y,g,xT,yT)){ return;}//si la case est nuageuse, on n'affichera rien d'autre dessus.
    byte ty = c.getType();
    CCreature ccrea = c.getGc().getDébut();
    CGraine ccg = c.getGg().getDébut();
    int lenTIF = tIF[0].length+1;
    try {
      //le fond
      Image iTemp;
      if (ty==1) {
        g.drawImage(tI2[0],xT,yT,this);
        //iTemp = tI2[0];
      }else if (ty==2){
        g.drawImage(tI2[1],xT,yT,this);
        //iTemp = tI2[1];
      }else if (ty==3){
        g.drawImage(tI1[1],xT,yT,this);
        //iTemp = tI1[1];
      }else{
        g.drawImage(imgNull,xT,yT,this);
        //iTemp = imgNull;
      }
      /*try {
        iTemp = iTemp.getScaledInstance(tailleDUneCase, tailleDUneCase,scale);
        g.drawImage(iTemp,xT,yT,this);
      }catch (Exception e) {
        erreur.erreur("impossible de dessiner la case avec l'image redimentionnée.");
      }*/
      int tC10 = tailleDUneCase/10;int tC4 = tailleDUneCase/4;int tC2 = tailleDUneCase/2;
      // la fourmilière
      if (c.getFere()!=null){
        g.drawImage(fere,xT+tC4,yT+tC4,this);
        drawRond(xT,yT,tailleDUneCase,g,c.getFere());
        //affichage d'un rond de la couleur de la fere.
      }
      if(jo!=null && Main.getPartie().getCarte().getCasesSombres() && jo.getCaseSombre(x+posX,y+posY)){
        g.drawImage(cSombre,xT,yT,this); // si les créatures sur la case ne sont pas visible.
      }else{
        // les graines
        int k=0;
        if(Main.getAffGraine()){
          while (ccg!=null){
            calculerXYTemp(xT,yT,k,c);k++;
            int dir = getDir((ObjetSurCarteAId)ccg.getContenu());
            try {
              g.drawImage(tG[dir][ccg.getContenu().getType()],xTemp,yTemp,this);
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
              g.drawImage(tIF[dir][math.min(f.getFourmiliere().getId()-1,lenTIF)],xTemp,yTemp,this);
            }else if(f.getStade()==-1){
              g.drawImage(tF[dir][2],xTemp,yTemp,this);
            }else if(f.getStade()==-2){
              g.drawImage(tF[dir][1],xTemp,yTemp,this);
            }else{ //stade == -3
              g.drawImage(tF[dir][0],xTemp,yTemp,this);
            }
            insecte=false;
          }catch (Exception e) {
            try {
              Insecte i = (Insecte)(ccrea.getContenu());
              g.drawImage(tII[dir][math.min(i.getType(),tII[dir].length)],xTemp,yTemp,this);
            }catch (Exception e2) {erreur.erreur("impossible de dessiner l'image de la case : "+x+" "+y);}
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
      erreur.erreur("impossible de dessiner l'image de la case : "+x+" "+y);
    }
  }
  public void calculerXYTemp(int xT, int yT, int k, Case c){
    int deplacementEnX=0;
    int deplacementEnY=0;
    //deplacement centré.
    int espaceLibre = tailleDUneCase/5;
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
  public void drawRond(int x, int y, int r, Graphics2D g, Fourmiliere fere){
    g.setColor(fere.getPh().getColor());
    int tailleDuCercle = (20*Main.getDimY())/1080;
    BasicStroke line = new BasicStroke(tailleDuCercle);
    g.setStroke(line);
    g.drawOval(x+tailleDuCercle/2,y+tailleDuCercle/2,r-tailleDuCercle,r-tailleDuCercle);
    setLigne(g);//retour au paramètres par défaut.
  }
  public void drawIcone(Graphics g, int x, int xT, int yT, int tC2){
    if (!Main.getDessinerIcone()){ return;}
    g.drawImage(b[x],xT+tC2,yT,this);
  }
  public int getDir(ObjetSurCarteAId obj){
    if (!Main.getElementSurCarteOrientéAprèsDéplacement()){return 0;}// si la direction de l'objet n'est pas prise en compte on cherche dans le tableau 0.
    int x = obj.getDirection();
    if(x==1 || x==2){ return 0;}
    if(x==3 || x==6){ return 1;}
    if(x==9 || x==8){ return 2;}
    return 3;
  }

  public void chargerImages(){
    if(!imageIni){
      chargerImagesIni();
      Main.débutCh();
    }
    int tailleFourmi = (tailleDUneCase*4)/5;
    imgNull = imgNullIni.getScaledInstance(tailleDUneCase, tailleDUneCase,scale);
    selectionnée = selectionnéeIni.getScaledInstance(tailleDUneCase, tailleDUneCase,scale);
    tI1=getScaledInstance(tI1Ini, tailleDUneCase);
    tI2=getScaledInstance(tI2Ini, tailleDUneCase);
    tIF=getScaledInstance(tIFIni, tailleFourmi);
    tII=getScaledInstance(tIIIni, tailleFourmi);
    tF=getScaledInstance(tFIni, tailleFourmi);
    tG=getScaledInstance(tGIni, tailleFourmi);
    fere = fereIni.getScaledInstance(tailleDUneCase/2, tailleDUneCase/2,scale);
    cNuageuse = cNuageuseIni.getScaledInstance(tailleDUneCase, tailleDUneCase,scale);
    cSombre = cSombreIni.getScaledInstance(tailleDUneCase, tailleDUneCase,scale);
    int lenb = bIni.length;
    b=getScaledInstance(bIni,tailleDUneCase/2);
    Main.finCh("chargerImages");
  }
  public void chargerImagesIni(){
    if(!imageIni){
      Main.débutCh();
      imgNullIni = image.getImage("null").getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
      selectionnéeIni = image.getImage("selectionnee").getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
      chargerTI();
      chargerTIF(Main.getNbrDeJoueur());
      chargerTII();
      chargerTF();
      chargerTG();
      fereIni = image.getImage("fourmiliere").getScaledInstance(tailleDUneCaseBase/2, tailleDUneCaseBase/2,scale);
      cNuageuseIni = image.getImage("cNuageuse").getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
      cSombreIni = image.getImage("cSombre").getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
      bIni = image.getImages("b"); int lenb = bIni.length;
      for (int i=0;i<lenb ;i++ ) {
        bIni[i]=bIni[i].getScaledInstance(tailleDUneCaseBase/2, tailleDUneCaseBase/2,scale);
      }
      Main.finCh("chargerImagesIni");
    }
    imageIni=true;
  }

  public void chargerTI(){
    tI1Ini = new Image [2]; tI1 = new Image [2];
    tI1Ini[0]=image.getImage("terre").getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
    tI1Ini[1]=image.getImage("sable").getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
    tI2Ini = new Image [2]; tI2 = new Image [2];
    tI2Ini[0]=image.getImage("herbe.png").getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
    tI2Ini[1]=image.getImage("mousse.png").getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
  }
  public void chargerTIF(int nbrDeJoueur){
    //if (!initialisationFX){initialisationFX=initialiserFX(nbrDeJoueur);}
    tIFIni = chargerTX("F",nbrDeJoueur,(byte)0,1);
  }public void chargerTIF(){ chargerTIF(12);}
  public void chargerTII(){
    tIIIni = chargerTX("I");
  }
  public void chargerTG(){
    tGIni = chargerTX("graine");
  }
  public void chargerTF(){
    tFIni = chargerTX("fourmi",3);
  }
  public Image [][] chargerTX(String nom, int x, byte y, int début){
    Image tTemp [][] = image.getImagess(nom,x,(byte)début);
    //mise a l'échelle.
    int tailleFourmi = (tailleDUneCaseBase*4)/5;
    int len1 = 4; int len2 = tTemp[0].length;
    for (int j=0;j<len1 && tTemp[j]!=null; j++) {
      for (int i=y;i<len2 ;i++ ) {
        tTemp[j][i] = tTemp[j][i].getScaledInstance(tailleFourmi, tailleFourmi,scale);
      }
    }
    return tTemp;
  }
  public Image [][] chargerTX(String nom, int x, byte y){ return chargerTX(nom,x,y,0);}
  public Image [][] chargerTX(String sn, int x){ return chargerTX(sn,x,(byte)0);}
  public Image [][] chargerTX(String sn){return chargerTX(sn,image.getNbrDeFichierCommencantPar(sn));}

  public Image [] getScaledInstance(Image ti[],int dim){
    int lenr = ti.length;
    Image r [] = new Image[lenr];
    for (int i=0;i<lenr ;i++ ) {
      r[i]=ti[i].getScaledInstance(dim, dim,scale);
    }
    return r;
  }
  public Image [][] getScaledInstance(Image ti[][],int dim){
    int lenr = ti.length;
    Image r [][] = new Image[lenr][];
    for (int i=0;i<lenr ;i++ ) {
      r[i]=getScaledInstance(ti[i],dim);
    }
    return r;
  }


  //sourie
  public void mouseClicked(MouseEvent e){
    debug.débogage("Clic détecté sur la carte");
    if (e.getButton() == MouseEvent.BUTTON1){System.out.println("bouton gauche");}

    else if (e.getButton() == MouseEvent.BUTTON2){System.out.println("bouton du milieu");}

    else if (e.getButton() == MouseEvent.BUTTON3){System.out.println("bouton droit");}
  }

  //Méthode appelée lors du survol de la souris
  public void mouseEntered(MouseEvent e) {
    //setDesc(g.get("case.desc."+));
    int x = e.getX();
    int y = e.getY();
    setDesc("");
    debug.débogage("x = "+x+" y = "+y);
  }

  //Méthode appelée lorsque la souris sort de la zone du bouton
  public void mouseExited(MouseEvent event) {
    setDesc("");
  }

  //Méthode appelée lorsque l'on presse le bouton gauche de la souris
  public void mousePressed(MouseEvent event) { }

  //Méthode appelée lorsque l'on relâche le clic de souris
  public void mouseReleased(MouseEvent event) { }

  public void setDesc(String s){
    if(Main.getPp().getPj()==null){ erreur.erreur("pj null");}
    try {
      Main.getPp().getPj().getPb().setDesc(s);
    }catch (Exception e) {
      erreur.alerte("Impossible de setDesc pour la carte.");
    }
  }
}
