package fr.formiko.usuel.image;
import fr.formiko.usuel.image.*;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.awt.Image;
import java.awt.Color;
import fr.formiko.usuel.image.image;
//import javafx.embed.swing.SwingFXUtils;
import fr.formiko.usuel.math.math;


public class Img {
  private Random rand = new Random();
  private final BufferedImage img;
  private final int width, height;
  private byte [][] rouge;
  private byte [][] vert;
  private byte [][] bleu;
  private byte [][] alpha;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Img(Image i){
    if (i==null){ erreur.erreur("impossible de créer une Img a partir d'une Image null","Img.Img",true);}
    img = (BufferedImage) i;
    width = img.getWidth();
    height = img.getHeight();
    debug.débogage("Initialisation des 4 tableaux.");
    setRouge(); setVert(); setBleu(); setAlpha();
  }
  public Img(String nom){
    this(image.getImage(nom));
  }
  /*public Img(byte [][] rouge,byte [][] vert, byte [][] bleu) {
    width = rouge.length;
    height = rouge[0].length;
    img = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
    for (int i = 0 ; i < width; i++)
      for (int j = 0; j < height; j++)
        img.setRGB(i,j,(rouge[i][j]<<16)|(vert[i][j]<<8)|(bleu[i][j]));
  }
  public Img(byte [][] gray) {
    width = gray.length;
    height = gray[0].length;
    img = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
    for (int i = 0 ; i < width; i++)
      for (int j = 0; j < height; j++)
        img.setRGB(i,j,(gray[i][j]<<16)|(gray[i][j]<<8)|(gray[i][j]));
  }*/
  public Img(int width,int height){
    this.width=width; this.height=height;
    img = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_ARGB);
    alpha = new byte[width][height];
    rouge = new byte[width][height];
    vert = new byte[width][height];
    bleu = new byte[width][height];
    /*for (int i=0;i<width ;i++) {
      for(int j=0;j<height;j++){
        try {
          setARVB(i,j,128);//tt mettre a blanc
        }catch (Exception e) {
          erreur.erreur("l'indice "+i+" "+j+"n'est pas correcte");
        }
      }
    }*/
  }
  // GET SET --------------------------------------------------------------------
  public BufferedImage getImg(){ return img;}
  public byte[][] getAlpha() { // transparence.
    if (alpha != null){ return alpha;}
    else { setAlpha(); return alpha;}
  }public byte [][] getA(){return getAlpha();}
  public byte getA(int i, int j){ return alpha[i][j];}
  public void setAlpha(){
    alpha = new byte[width][height];
    for (int i = 0 ; i < width; i++)
      for (int j = 0; j < height; j++)
        alpha[i][j] = (byte)(((img.getRGB(i,j)>>24)&255) -128);
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
        rouge[i][j] = (byte)(((img.getRGB(i,j)>>16)&255) -128);
  }public void setRouge(int i,int j, byte x){ rouge[i][j]=x;}
  public void setR(int i,int j, byte x){setRouge(i,i,x);}

  public byte[][] getVert() {
    if (vert != null) return vert;
    else setVert(); return vert;
  }public byte [][] getG(){ return getVert();}
  public byte [][] getV(){ return getVert();}
  public byte getV(int i, int j){ return vert[i][j];}
  public void setVert(){
    vert = new byte[width][height];
    for (int i = 0 ; i < width; i++)
      for (int j = 0; j < height; j++)
        vert[i][j] = (byte)(((img.getRGB(i,j)>>8)&255) -128);
  }public void setVert(int i,int j, byte x){ vert[i][j]=x;}
  public void setV(int i,int j, byte x){setVert(i,i,x);}

  public byte[][] getBleu() {
    if (bleu != null) return bleu;
    else setBleu(); return bleu;
  }public byte [][] getB(){ return getBleu();}
  public byte getB(int i, int j){ return bleu[i][j];}
  public void setBleu(){
    bleu = new byte[width][height];
    for (int i = 0 ; i < width; i++)
      for (int j = 0; j < height; j++)
        bleu[i][j] = (byte)((img.getRGB(i,j)&255) -128);
  }public void setBleu(int i,int j, byte x){ bleu[i][j]=x;}
  public void setB(int i,int j, byte x){setBleu(i,i,x);}

  public byte[][] getGray() {
    byte [][] gray = new byte[width][height];
    for (int i = 0 ; i < width; i++)
      for (int j = 0; j < height; j++)
        gray[i][j] = (byte)(((img.getRGB(i,j)&255)  + ((img.getRGB(i,j)>>8)&255)*10  + ((img.getRGB(i,j)>>16)&255)*3 ) / 14);
    return gray;
  }
  /*public Image getImage(){
    sauvegarde("img.png");
    return image.getImage("img.png");
  }*/
  public void sauvegarder(String nom){
    try {
      ImageIO.write(img,"png",new File("data/imageFormiko/"+nom));
    }catch (Exception e) {
      erreur.erreur("Echec de la sauvegarde d'image","img.sauvegarde");
    }
  }public void sauvegarde(String s){ sauvegarder(s);}
  public void setRouge(byte [][] x){rouge=x;}
  public void setVert(byte [][] x){vert=x;}
  public void setBleu(byte [][] x){bleu=x;}
  public void setAlpha(byte [][] x){alpha=x;}
  public int getWidth(){return img.getWidth();}
  public int getHeight(){return img.getHeight();}
  public void setARVB(int i, int j, int x){setARVB(i,j,(byte)x);}
  public void setARVB(int i, int j, byte x){setA(i,j,x);setR(i,j,x);setV(i,j,x);setB(i,j,x);}
  public Pixel getPixel(int i, int j){ return new Pixel(getR(i,j),getV(i,j),getB(i,j),getA(i,j));}
  // Fonctions propre -----------------------------------------------------------
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
  public void add(int x, int y, Img ie){
    //on rajoute les niveau de couleurs
    int xTemp = ie.getWidth();
    int yTemp = ie.getHeight();
    for (int i=0; i<xTemp; i++){
      for (int j=0; j<yTemp; j++){
        //on remplace le pixel de l'image par celui de i au meme endrois
        rouge[i+x][j+y]=ie.getR(i,j);
        vert[i+x][j+y]=ie.getV(i,j);
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
  public void draw() {
    ImageIcon icon = new ImageIcon(img);
    //Image icon = (Image)(img);
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

  public void save(String filename) throws IOException {
    File file = new File(filename);
    ImageIO.write(img, "png", file);
  }

  public int [] compterNiveauDeRouge(){
    int xr [] = new int [256];
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        int x = rouge[i][j]+128;
        xr[x]++;
      }
    }return xr;
  }
  public int [] compterNiveauDeVert(){
    int xr [] = new int [256];
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        int x = vert[i][j]+128;
        xr[x]++;
      }
    }return xr;
  }
  public int [] compterNiveauDeBleu(){
    int xr [] = new int [256];
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        int x = bleu[i][j]+128;
        xr[x]++;
      }
    }return xr;
  }
  public int [] compterNiveauDeAlpha(){
    int xr [] = new int [256];
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        int x = alpha[i][j]+128;
        xr[x]++;
      }
    }return xr;
  }
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
  public void changerPixel(Pixel a, Pixel b){
    //debug.débogage("changement des pixels voulus");
    //debug.débogage(compterPixel(a)+" pixel devrais être modifié.");
    int x = 0;
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        if(a.getR()==rouge[i][j] && a.getG()==vert[i][j] && a.getB()==bleu[i][j] && a.getA()==alpha[i][j]){
          rouge[i][j]=b.getR();
          vert[i][j]=b.getV();
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
  public void changerPixelTransparent(Pixel b){
    //debug.débogage("changement des pixels voulus");
    //debug.débogage(compterPixel(a)+" pixel devrais être modifié.");
    int x = 0;
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        if(alpha[i][j]==-128){
          rouge[i][j]=b.getR();
          vert[i][j]=b.getV();
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
  public void actualiserImage(){
    Color rose = new Color(255,150,255,100);
    int pixelActualisé = 0;
    for (int i = 0 ; i < width; i++){
      for (int j = 0; j < height; j++){
        Color cl = new Color(rouge[i][j]+128,vert[i][j]+128,bleu[i][j]+128,alpha[i][j]+128);
        img.setRGB(i,j,cl.getRGB());
        int x = ((rouge[i][j]<<16)|(vert[i][j]<<8)|(bleu[i][j]));
        //if(x!=0){System.out.println(x+ " "+rouge[i][j]+" "+vert[i][j]+" "+bleu[i][j]); }
        pixelActualisé++;
      }
    }
    debug.débogage(pixelActualisé+" pixels ont été actualisé.");
  }
  public Img tourner(byte x){ // on tourne de 90° a chaque fois.
    x=(byte)(x+4); // pour pouvoir utiliser des angles négatifs.
    if(width!=height){ return null;}
    int taille = width; int ta= taille-1;
    byte [][] rougeT = new byte[taille][taille];
    byte [][] vertT = new byte[taille][taille];
    byte [][] bleuT = new byte[taille][taille];
    byte [][] alphaT = new byte[taille][taille];
    if (x%4==1){
      for (int i=0;i<taille;i++) {
        for (int j=0;j<taille;j++) {
          rougeT[i][j]=rouge[j][ta-i];
          vertT[i][j]=vert[j][ta-i];
          bleuT[i][j]=bleu[j][ta-i];
          alphaT[i][j]=alpha[j][ta-i];
        }
      }
    }else if(x%4==2){
      for (int i=0;i<taille;i++) {
        for (int j=0;j<taille;j++) {
          rougeT[i][j]=rouge[ta-i][ta-j];
          vertT[i][j]=vert[ta-i][ta-j];
          bleuT[i][j]=bleu[ta-i][ta-j];
          alphaT[i][j]=alpha[ta-i][ta-j];
        }
      }
    }else if(x%4==3){ // -1 ou 3.
      for (int i=0;i<taille;i++) {
        for (int j=0;j<taille;j++) {
          rougeT[i][j]=rouge[ta-j][i];
          vertT[i][j]=vert[ta-j][i];
          bleuT[i][j]=bleu[ta-j][i];
          alphaT[i][j]=alpha[ta-j][i];
        }
      }
    }
    Img ir = new Img(width,height);
    ir.setRouge(rougeT);
    ir.setVert(vertT);
    ir.setBleu(bleuT);
    ir.setAlpha(alphaT);
    ir.actualiserImage();
    return ir;
  }public Img tourner(int x){ return tourner((byte) x);}
  public Img tourner(){ return tourner(1);}
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
  public void supprimerLaTransparencePartielle(int x){ //x est le niveau de transparence de 0 a 255.
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
    actualiserImage();
  }public void ombrer(Pixel a){ ombrer(a,10);}
  public int getNbrDePixel(Pixel a, int x){
    int r = 0;
    //la il faudrait arriver a compter les pixels a dans un rayon de x.
    return r;
  }public int getNbrDePixel(Pixel a){ return getNbrDePixel(a,10);}
}