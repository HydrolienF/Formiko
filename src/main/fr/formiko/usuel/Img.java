package fr.formiko.usuel.image;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
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
import fr.formiko.usuel.math.math;
import fr.formiko.usuel.conversiondetype.str;


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
    if(width < 0 || height < 0){erreur.erreur("Impossible d'initialiser une image avec des dimentions négative : "+width+","+height,"Img.Img","taille set a 0"); width=0; height=0;}
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
  public void setRouge(byte [][] x){rouge=x;}
  public void setVert(byte [][] x){vert=x;}
  public void setBleu(byte [][] x){bleu=x;}
  public void setAlpha(byte [][] x){alpha=x;}
  public int getWidth(){return img.getWidth();}
  public int getHeight(){return img.getHeight();}
  public void setARVB(int i, int j, int x){setARVB(i,j,(byte)x);}
  public void setARVB(int i, int j, byte x){setA(i,j,x);setR(i,j,x);setV(i,j,x);setB(i,j,x);}
  public Pixel getPixel(int i, int j){ return new Pixel(getR(i,j),getV(i,j),getB(i,j),getA(i,j));}
  public int getNbrDePixel(Pixel a, int x){
    int r = 0;
    //la il faudrait arriver a compter les pixels a dans un rayon de x.
    return r;
  }public int getNbrDePixel(Pixel a){ return getNbrDePixel(a,10);}
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
  public void sauvegarder(String nom){
    sauvegarder(image.REP2,nom);
  }public void sauvegarde(String s){ sauvegarder(s);}
  /**
  *{@summary save the Img as a .png image with a correct name.<br>}
  *If Main.getOx() value is defined some char will be tolerate or not depending of the OS.
  *@param rep the directory were to save the image.
  *@param filename the name of the Image file. (without .png).
  */
  public void sauvegarder(String rep, String filename){
    if(str.contient(filename,"temporaire/",0)){
      filename = filename.substring(11,filename.length());
    }
    filename = str.filtreCharInterdit(filename);
    debug.débogage("save de "+filename+" dans "+rep);
    try {
      save(rep+filename);
    }catch (Exception e) {
      erreur.erreur("Echec de la sauvegarde d'image pour : "+rep+filename,"img.sauvegarde");
    }
  }
  /**
  *{@summary try to save the Img.<br>}
  */
  public void save(String filename) throws IOException {
    File file = new File(filename);
    ImageIO.write(img, "png", file);
  }
  /**
  *{@summary draw the Img.<br>}
  */
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
  /**
  *{@summary use to refresh the BufferedImage before draw it or save it.<br>}
  */
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
  /**
  *{@summary rotate the Img.<br>}
  *@param x How much do we need to rotate : 1=90° 2=180° -1 or 3 = -90°
  *@return a new Img rotated.
  */
  public Img tourner(byte x){ // on tourne de 90° a chaque fois.
    x=(byte)(x+4); // pour pouvoir utiliser des angles négatifs.
    //if(width!=height){ return null;}
    int ta= width-1;
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
          rougeT[i][j]=rouge[width-1-i][width-1-j];
          vertT[i][j]=vert[width-1-i][width-1-j];
          bleuT[i][j]=bleu[width-1-i][width-1-j];
          alphaT[i][j]=alpha[width-1-i][width-1-j];
        }
      }
    }else if(x%4==3){ // -1 ou 3.
      for (int i=0;i<width;i++) {
        for (int j=0;j<height;j++) {
          rougeT[i][j]=rouge[width-1-j][i];
          vertT[i][j]=vert[width-1-j][i];
          bleuT[i][j]=bleu[width-1-j][i];
          alphaT[i][j]=alpha[width-1-j][i];
        }
      }
    }else{
      return this;
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
  }public void ombrer(Pixel a){ ombrer(a,10);}
  /**
  *{@summary trim the Img.<br>}
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
  *{@summary trim the Img to cut transparent border.<br>}
  */
  public void rognerBordTransparent(){
    int t[] = compterBordTransparent();
    rogner(t[0],t[1],t[2],t[3]);
  }
  /**
  *{@summary get how much line are composed of transparents pixels.<br>}
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

    Img imgTemp = tourner();
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
}
