package fr.formiko.usuel.images;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Chrono;
import fr.formiko.usuel.chargerLesTraductions;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
*{@summary Img is a BufferedImage where you can edit pixel value, then save it on a local file or draw it.}<br>
*@author Hydrolien
*@version 1.11
*/
public class Img implements Cloneable{
  private Random rand = new Random();
  private BufferedImage bi;
  private int width, height;
  private byte [][] rouge;
  private byte [][] vert;
  private byte [][] bleu;
  private byte [][] alpha;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *Constructs a new Img with a BufferedImage.
  */
  public Img(BufferedImage i){
    if (i==null){ erreur.erreur("impossible de créer une Img a partir d'une Image null");}
    bi = i;
    width = bi.getWidth();
    height = bi.getHeight();
    debug.débogage("Initialisation des 4 tableaux.");Chrono.debutCh();
    setRouge(); setVert(); setBleu(); setAlpha();
    Chrono.endCh("4 tableaux de pixel initialiser");
  }
  /**
  *Constructs a new Img with a fileName.
  */
  public Img(String nom){
    this(image.getImage(nom));
  }
  /**
  *Constructs a new grey 50% alpha Img whose width and height are specified by the arguments of the same name.
  */
  public Img(int width,int height){
    if(width < 0 || height < 0){erreur.erreur("Impossible d'initialiser une image avec des dimentions négative : "+width+","+height,"taille set a 0"); width=0; height=0;}
    this.width=width; this.height=height;
    bi = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_ARGB);
    alpha = new byte[width][height];
    rouge = new byte[width][height];
    vert = new byte[width][height];
    bleu = new byte[width][height];
  }
  // GET SET -------------------------------------------------------------------
  public BufferedImage getBi(){ return bi;}
  public BufferedImage getImage(){return getBi();}
  public byte[][] getAlpha() { // transparence.
    if (alpha != null){ return alpha;}
    else { setAlpha(); return alpha;}
  }public byte [][] getA(){return getAlpha();}
  public byte getA(int i, int j){ return alpha[i][j];}
  public void setAlpha(){
    alpha = new byte[width][height];
    for (int i = 0 ; i < width; i++)
      for (int j = 0; j < height; j++)
        alpha[i][j] = (byte)(((bi.getRGB(i,j)>>24)&255) -128);
  }public void setAlpha(int i,int j, byte x){ alpha[i][j]=x;}
  public void setA(int i,int j, byte x){setAlpha(i,i,x);}

  public byte[][] getRouge() {
    if (rouge != null) return rouge;
    else setRouge(); return rouge;
  }public byte [][] getR(){ return getRouge();}
  public byte getR(int i, int j){ return rouge[i][j];}
  public void setRouge(){
    rouge = new byte[width][height];
    for (int i = 0 ; i < width; i++)
      for (int j = 0; j < height; j++)
        rouge[i][j] = (byte)(((bi.getRGB(i,j)>>16)&255) -128);
  }public void setRouge(int i,int j, byte x){ rouge[i][j]=x;}
  public void setR(int i,int j, byte x){setRouge(i,i,x);}

  public byte[][] getVert() {
    if (vert != null) return vert;
    else setVert(); return vert;
  }public byte [][] getG(){ return getVert();}
  public byte getG(int i, int j){ return vert[i][j];}
  public void setVert(){
    vert = new byte[width][height];
    for (int i = 0 ; i < width; i++)
      for (int j = 0; j < height; j++)
        vert[i][j] = (byte)(((bi.getRGB(i,j)>>8)&255) -128);
  }public void setVert(int i,int j, byte x){ vert[i][j]=x;}
  public void setG(int i,int j, byte x){setVert(i,i,x);}

  public byte[][] getBleu() {
    if (bleu != null) return bleu;
    else setBleu(); return bleu;
  }public byte [][] getB(){ return getBleu();}
  public byte getB(int i, int j){ return bleu[i][j];}
  public void setBleu(){
    bleu = new byte[width][height];
    for (int i = 0 ; i < width; i++)
      for (int j = 0; j < height; j++)
        bleu[i][j] = (byte)((bi.getRGB(i,j)&255) -128);
  }public void setBleu(int i,int j, byte x){ bleu[i][j]=x;}
  public void setB(int i,int j, byte x){setBleu(i,i,x);}

  public byte[][] getGray() {
    byte [][] gray = new byte[width][height];
    for (int i = 0 ; i < width; i++)
      for (int j = 0; j < height; j++)
        gray[i][j] = (byte)(((bi.getRGB(i,j)&255)  + ((bi.getRGB(i,j)>>8)&255)*10  + ((bi.getRGB(i,j)>>16)&255)*3 ) / 14);
    return gray;
  }
  /*public Image getImage(){
    sauvegarde("img.png");
    return image.getImage("img.png");
  }*/
  public void setRouge(byte [][] x){rouge=x;}
  public void setVert(byte [][] x){vert=x;}
  public void setBleu(byte [][] x){bleu=x;}
  public void setAlpha(byte [][] x){alpha=x;}
  public int getWidth(){return width;}
  public int getHeight(){return height;}
  public void setARVB(int i, int j, int x){setARVB(i,j,(byte)x);}
  public void setARVB(int i, int j, byte x){setA(i,j,x);setR(i,j,x);setG(i,j,x);setB(i,j,x);}
  public Pixel getPixel(int i, int j){ return new Pixel(getR(i,j),getG(i,j),getB(i,j),getA(i,j));}
  public int getNbrDePixel(Pixel a, int x){
    int r = 0;
    //la il faudrait arriver a compter les pixels a dans un rayon de x.
    return r;
  }public int getNbrDePixel(Pixel a){ return getNbrDePixel(a,10);}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@version 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof Img)){return false;}
    Img img2 = (Img)o;
    if(getWidth()!=img2.getWidth()){return false;}
    if(getHeight()!=img2.getHeight()){return false;}
    if(!tableau.equals(getRouge(),img2.getRouge())){return false;}
    if(!tableau.equals(getVert(),img2.getVert())){return false;}
    if(!tableau.equals(getBleu(),img2.getBleu())){return false;}
    if(!tableau.equals(getAlpha(),img2.getAlpha())){return false;}
    return true;
  }
  /**
  *Make a copie of the Img.
  */
  @Override
  public Img clone(){
    actualiserImage(); //on s'assure que la BufferedImage repésente bien l'image actuelle
    Img imgr = new Img(this.getBi());//puis on ce sert de celle la pour recréer une img.
    return imgr;
  }
  /**
  *Initialize width &#38; height.
  */
  public boolean iniWH(){
    try {
      width = rouge.length;
      height = rouge[0].length;
      return true;
    }catch (ArrayIndexOutOfBoundsException e) {
      return false;
    }
  }
  /**
  *{@summary Print the ARGB level.}<br>
  */
  public void afficherLesTableaux(){
    System.out.println("transparence :");
    tableau.afficher(this.compterNiveauDeAlpha());
    System.out.println("rouge");
    tableau.afficher(this.compterNiveauDeRouge());
    System.out.println("vert");
    tableau.afficher(this.compterNiveauDeVert());
    System.out.println("bleu");
    tableau.afficher(this.compterNiveauDeBleu());
  }
  /**
  *{@summary Add an Img on this.}<br>
  *It can be used to make the map image.
  */
  public void add(int x, int y, Img ie){
    //on rajoute les niveau de couleurs
    int xTemp = ie.getWidth();
    int yTemp = ie.getHeight();
    //on évite de sortir de la zone de l'image pour éviter les arrayOutOffBoundExceptions.
    xTemp = math.min(rouge.length,x+xTemp) -x;
    yTemp = math.min(rouge[0].length,y+yTemp) -y;
    for (int i=0; i<xTemp; i++){
      for (int j=0; j<yTemp; j++){
        //on remplace le pixel de l'image par celui de i au mêm e endrois
        rouge[i+x][j+y]=ie.getR(i,j);
        vert[i+x][j+y]=ie.getG(i,j);
        bleu[i+x][j+y]=ie.getB(i,j);
        alpha[i+x][j+y]=ie.getA(i,j);
      }
    }
    //actualiserImage(); Trop consomateur a faire a la fin
  }
  /*public void afficheToi(){
    System.out.println("ROUGE :");
    tableau.afficher(getRouge());
    System.out.println("VERT :");
    tableau.afficher(getVert());
    System.out.println("BLEU :");
    tableau.afficher(getBleu());
    System.out.println("TRANSPARENCE :");
    tableau.afficher(getAlpha());
  }*/
  /**
  *{@summary save the Img as a .png image with a correct name.}<br>
  *@param rep the directory were to save the image.
  *@param filename the name of the Image file. (without .png).
  */
  public void sauvegarder(String rep, String filename){
    if(str.contient(filename,"temporaire/",0)){
      filename = filename.substring(11,filename.length());
    }
    filename = str.filterForbiddenChar(filename);
    debug.débogage("save de "+filename+" dans "+rep);
    try {
      save(rep+filename);
    }catch (Exception e) {
      erreur.erreur("Echec de la sauvegarde d'image pour : "+rep+filename);
    }
  }public void sauvegarder(String nom){sauvegarder(Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderImages(),nom);}
  public void sauvegarde(String s){ sauvegarder(s);}
  /**
  *{@summary try to save the Img.}<br>
  */
  public void save(String filename) throws IOException {
    File file = new File(filename);
    ImageIO.write(bi, "png", file);
  }
  /**
  *{@summary draw the Img.}<br>
  */
  public void draw() {
    ImageIcon icon = new ImageIcon(bi);
    //Image icon = (Image)(bi);
    JLabel d = new JLabel(icon);
    JFrame frame = new JFrame();
    frame.getContentPane().add(new JPanel());
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(width,height);
    frame.setContentPane(d);
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension screenSize = tk.getScreenSize();
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;

    frame.setLocation(rand.nextInt(screenWidth/2), rand.nextInt(screenHeight/2));
    frame.setVisible(true);
  }public void dessiner(){ draw();}
  public void afficher(){ draw();}

  /**
  *{@summary Make an array with all red value for all pixel of the Img.}<br>
  */
  public int [] compterNiveauDeRouge(){
    int xr [] = new int [256];
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        int x = rouge[i][j]+128;
        xr[x]++;
      }
    }return xr;
  }
  /**
  *{@summary Make an array with all green value for all pixel of the Img.}<br>
  */
  public int [] compterNiveauDeVert(){
    int xr [] = new int [256];
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        int x = vert[i][j]+128;
        xr[x]++;
      }
    }return xr;
  }
  /**
  *{@summary Make an array with all blue value for all pixel of the Img.}<br>
  */
  public int [] compterNiveauDeBleu(){
    int xr [] = new int [256];
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        int x = bleu[i][j]+128;
        xr[x]++;
      }
    }return xr;
  }
  /**
  *{@summary Make an array with all alpha value for all pixel of the Img.}<br>
  */
  public int [] compterNiveauDeAlpha(){
    int xr [] = new int [256];
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        int x = alpha[i][j]+128;
        xr[x]++;
      }
    }return xr;
  }
  /**
  *{@summary Count how much pixel there is on the image.}<br>
  */
  public int compterPixel(Pixel a){
    int x=0;
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        if(a.getR()==rouge[i][j] && a.getG()==vert[i][i] && a.getB()==bleu[i][j] && a.getA()==alpha[i][j]){
          x++;
        }
      }
    }
    return x;
  }
  /**
  *{@summary Count how much of eatch pixel there is on the image.}<br>
  */
  public HashMap<Pixel, Integer> compterChaquePixel(){
    Chrono.debutCh();
    //faire une liste de tt les pixels différents
    //compter le nombre de pixel identique pour chaque pixel dans la liste.
    HashMap<Pixel, Integer> hm = new HashMap<Pixel, Integer>();
    //ou Parcourir chaque pixel de l'image, si il est dans la liste on compte +1, sinon on l'ajoute dans la liste.
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        boolean estDansLaListe=false;
        Pixel p2 = new Pixel(rouge[i][j],vert[i][j],bleu[i][j],alpha[i][j]);
        if(alpha[i][j]!=-128){
          for (Pixel p : hm.keySet()) {
            if(p.equals(p2)){
              hm.replace(p,hm.get(p)+1);
              estDansLaListe=true;
              break;
            }
          }
          if(!estDansLaListe){
            hm.put(p2,1);
          }
        }
      }
    }
    Chrono.endCh("compterChaquePixel");
    return hm;
  }
  /**
  *{@summary Count how much of eatch pixel there is on the image and create a .html page to store the data.}<br>
  */
  public void compterChaquePixelToHtml(){
    HashMap hm = compterChaquePixel();
    g.setMap(chargerLesTraductions.chargerLesNationsName());
    String sr = "";
    Chrono.debutCh();
    //for (var item : hm.entrySet()) {
    Object to[] = hm.keySet().toArray();
    Pixel tp[] = new Pixel[to.length];
    for (int i=0;i<to.length ;i++ ) {
      tp[i] = (Pixel)to[i];
    }
    for (int i=0;i<tp.length ; i++) {
      sr+="{label:'"+g.get(tp[i].toString())+"', y:"+hm.get(tp[i])+"},\n";
    }
    Chrono.endCh("compterChaquePixelToHtml");
    System.out.println(getResultAsHtmlDiv(sr));
  }
  /**
  *{@summary Replace pixel a by pixel b.}<br>
  *On Formiko it is used to create random color for ant and modify the image.
  */
  public void changerPixel(Pixel a, Pixel b){
    //debug.débogage("changement des pixels voulus");
    //debug.débogage(compterPixel(a)+" pixel devrais être modifié.");
    int x = 0;
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        if(a.getR()==rouge[i][j] && a.getG()==vert[i][j] && a.getB()==bleu[i][j] && a.getA()==alpha[i][j]){
          rouge[i][j]=b.getR();
          vert[i][j]=b.getG();
          bleu[i][j]=b.getB();
          alpha[i][j]=b.getA();
          x++;
        }
      }
    }
    int taille = width*height;
    int y = (x*100)/(taille);
    String s ="";
    if(y==0 && x > 0){s=">";}
    //debug.débogage(s+y+"% des pixels on été changés ("+x+"/"+taille+")");
    actualiserImage();
    //debug.débogage("actualisation de l'image conculante.");
  }
  /**
  *{@summary Replace max alpha pixel by an other color.}<br>
  */
  public void changerPixelTransparent(Pixel b){
    //debug.débogage("changement des pixels voulus");
    //debug.débogage(compterPixel(a)+" pixel devrais être modifié.");
    int x = 0;
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        if(alpha[i][j]==-128){
          rouge[i][j]=b.getR();
          vert[i][j]=b.getG();
          bleu[i][j]=b.getB();
          alpha[i][j]=b.getA();
          x++;
        }
      }
    }
    int taille = width*height;
    int y = (x*100)/(taille);
    String s ="";
    if(y==0 && x > 0){s=">";}
    actualiserImage();
  }
  /**
  *{@summary Use to refresh the BufferedImage before draw it or save it.}<br>
  */
  public void actualiserImage(){
    Chrono.debutCh();
    iniWH();
    //néssésaire si l'image n'as plus les mêmes dimentions.
    bi = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_ARGB);
    //int pixelActualisé = 0;
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        //Color cl = new Color(rouge[i][j]+128,vert[i][j]+128,bleu[i][j]+128,alpha[i][j]+128);
        int x = ((alpha[i][j]+128<<24)|(rouge[i][j]+128<<16)|(vert[i][j]+128<<8)|(bleu[i][j]+128)); //on transforme les donnée des tableaux en 1 int qui représente les niveaux de couleur. (Exactement comme color ferait sauf que ca demande probablement un peu moins de temps de ne pas avoir a passer par le constructeur.)
        //bi.setRGB(i,j,cl.getRGB());
        bi.setRGB(i,j,x);
        //if(x!=0){System.out.println(x+ " "+rouge[i][j]+" "+vert[i][j]+" "+bleu[i][j]); }
        //pixelActualisé++;
      }
    }
    Chrono.endCh("actualiserImage");
    //debug.débogage(pixelActualisé+" pixels ont été actualisé.");
  }
  /**
  *{@summary rotate the Img.}<br>
  *@param x How much do we need to rotate : 1=90° 2=180° -1 or 3 = -90°
  */
  public void tourner(byte x){ // on tourne de 90° a chaque fois.
    x=(byte)(x+4); // pour pouvoir utiliser des angles négatifs.
    //if(width!=height){ return null;}
    Img ir = new Img(width,height);
    if (x%2==1){ //si la largeur et la hauteur sont échangée.
      int wTemp = width;
      width = height;
      height = wTemp;
    }
    byte [][] rougeT = new byte[width][height];
    byte [][] vertT = new byte[width][height];
    byte [][] bleuT = new byte[width][height];
    byte [][] alphaT = new byte[width][height];
    //TODO remplacer les width par des height si nésséssaire. (Puis écrire une fonction test associée.)
    if (x%4==1){
      for (int i=0;i<width;i++) {
        for (int j=0;j<height;j++) {
          rougeT[i][j]=rouge[j][width-1-i];
          vertT[i][j]=vert[j][width-1-i];
          bleuT[i][j]=bleu[j][width-1-i];
          alphaT[i][j]=alpha[j][width-1-i];
        }
      }
    }else if(x%4==2){
      for (int i=0;i<width;i++) {
        for (int j=0;j<height;j++) {
          rougeT[i][j]=rouge[width-1-i][height-1-j];
          vertT[i][j]=vert[width-1-i][height-1-j];
          bleuT[i][j]=bleu[width-1-i][height-1-j];
          alphaT[i][j]=alpha[width-1-i][height-1-j];
        }
      }
    }else if(x%4==3){ // -1 ou 3.
      for (int i=0;i<width;i++) {
        for (int j=0;j<height;j++) {
          rougeT[i][j]=rouge[height-1-j][i];
          vertT[i][j]=vert[height-1-j][i];
          bleuT[i][j]=bleu[height-1-j][i];
          alphaT[i][j]=alpha[height-1-j][i];
        }
      }
    }else{//si sa me change pas.
      /*setRouge(tableau.copier(rouge));
      setVert(tableau.copier(vert));
      setBleu(tableau.copier(bleu));
      setAlpha(tableau.copier(alpha));*/
      //actualiserImage();
      return;
    }//si sa a changé.
    setRouge(rougeT);
    setVert(vertT);
    setBleu(bleuT);
    setAlpha(alphaT);
    actualiserImage();
    //return ir;
  }public void tourner(int x){ tourner((byte) x);}
  public void tourner(){ tourner(1);}
  /**
  *{@summary Add x in alpha (non-transparency) to the all image.}<br>
  *You can remove some alpha with an x&#60;0.
  */
  public void changerAlpha(int x){
    int taille = width;
    for (int i=0;i<taille;i++) {
      for (int j=0;j<taille;j++) {
        int alphaTemp = alpha[i][j]+x;
        if (alphaTemp < -128){ alphaTemp=-128;}
        if (alphaTemp > 127){ alphaTemp=127;}
        alpha[i][j]=(byte) alphaTemp;
      }
    }
    actualiserImage();
  }
  /**
  *{@summary Replace all non 100% or 0% transparent pixel by a 100% or a 0% transparent pixel.}<br>
  *On some image it can have some 99% transparent pixel, we may need to transforme them to 0% transparent pixel. (100 transparent pixel can be recolored more easyly.)
  *@param x alpha limit level between 0 and 255. If pixel alpha value is higer than x pixel will be at 255 alpha. Other wise it will be at 0 alpha.
  */
  public void supprimerLaTransparencePartielle(int x){
    x = x-128; // pour qu'il soit callé sur le pixel.
    if(x<-128 || x>127){ return;}
    //opération :
    //si alpha du pixel est suppérieur a x alpha = 255
    //sinon alpha = 0.
    int taille = width;
    for (int i=0;i<taille;i++) {
      for (int j=0;j<taille;j++) {
        if(alpha[i][j]<x){
          alpha[i][j]=-128;
        }else{
          alpha[i][j]=127;
        }
      }
    }
    actualiserImage();
  }public void supprimerLaTransparencePartielle(){ supprimerLaTransparencePartielle(127);}
  /**
  *{@summary draw a shadow on the border of a colored zone as ant alitrunk.}<br>
  */
  //TODO test & use it.
  public void ombrer(Pixel a, int x){
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        if(a.getR()==rouge[i][j] && a.getG()==vert[i][j] && a.getB()==bleu[i][j] && a.getA()==alpha[i][j]){
          //si le pixel correspond on le décolore jusqu'a 50%
          int piNoir = getNbrDePixel(new Pixel(0,0,0,255)); //get le nombre de pixel noir dans un petit rayon.
          double d = 1;
          d=math.min(2.0,(double)((piNoir/20) +1)); // d = 1 ou 1,05 ou 1,1 etx ou 2
          rouge[i][j]=(byte)(rouge[i][j]/d);
          vert[i][j]=(byte)(vert[i][j]/d);
          bleu[i][j]=(byte)(bleu[i][j]/d);
          alpha[i][j]=(byte)(alpha[i][j]/d);
        }
      }
    }
  }public void ombrer(Pixel a){ ombrer(a,10);}
  /**
  *{@summary trim the Img.}<br>
  *@param a How much do we need to trim in pixel before width
  *@param b How much do we need to trim in pixel before height
  *@param c How much do we need to trim in pixel after width
  *@param d How much do we need to trim in pixel after height
  */
  public void rogner(int a, int b, int c, int d){
    rouge = tableau.rogner(rouge,a,b,c,d);
    vert = tableau.rogner(vert,a,b,c,d);
    bleu = tableau.rogner(bleu,a,b,c,d);
    alpha = tableau.rogner(alpha,a,b,c,d);
  }
  /**
  *{@summary trim the Img to cut transparent border.}<br>
  */
  public void rognerBordTransparent(){
    int t[] = compterBordTransparent();
    rogner(t[0],t[1],t[2],t[3]);
  }
  /**
  *{@summary get how much line are composed of transparents pixels.}<br>
  */
  public int [] compterBordTransparent(){
    int t[]=new int[4];
    int a = 0;
    while(a<width && tableau.contientUniquement(alpha[a],(byte)-128)){//tant qu'il n'y a que des pixels transparent.
      a++;
    }
    int c=0;
    while(c<width && tableau.contientUniquement(alpha[width-c-1],(byte)-128)){//tant qu'il n'y a que des pixels transparent.
      c++;
    }

    Img imgTemp = clone();
    imgTemp.tourner();
    int d = 0;
    while(d<width && tableau.contientUniquement(imgTemp.getAlpha()[d],(byte)-128)){//tant qu'il n'y a que des pixels transparent.
      d++;
    }
    int b=0;
    while(b<width && tableau.contientUniquement(imgTemp.getAlpha()[width-b-1],(byte)-128)){//tant qu'il n'y a que des pixels transparent.
      b++;
    }
    t[0]=a;t[1]=b;t[2]=c;t[3]=d;
    return t;
  }

  public String getResultAsHtmlDiv(String sr) {
    String a =
            "<!DOCTYPE HTML>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<meta charset=\"utf-8\">" +
                  	"<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">" +
                    "<script src=\"https://canvasjs.com/assets/script/canvasjs.min.js\"></script>\n" +
                    "<script type=\"text/javascript\">\n" +
                    "\n" +
                    "window.onload = function () {\n" +
                    "\tvar chart = new CanvasJS.Chart(\"chartContainer\", {\n" +
                    "\t\ttitle:{\n" +
                    "\t\t\ttext: \""+"%age of claim"+"\"              \n" +
                    "\t\t},\n" +
                    "\t\tdata: [              \n" +
                    "\t\t{\n" +
                    "\t\t\t// Change type to \"doughnut\", \"line\", \"splineArea\", etc.\n" +
                    "\t\t\ttype: \"pie\",\n" +
                    "\t\t\tdataPoints: [\n" ;

    String c =
            "\t\t\t]\n" +
                    "\t\t}\n" +
                    "\t\t]\n" +
                    "\t});\n" +
                    "\tchart.render();\n" +
                    "}\n" +
                    "</script>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<img src=\"images/worldMapByHydrolien.jpg\" width=\"100%\" height=\"70%\">" +
                    "<div id=\"chartContainer\" style=\"height: 30%; width: 300;\"></div>\n" +
                    "</body>\n" +
                    "</html>";
    return a+sr+c;
  }
}
