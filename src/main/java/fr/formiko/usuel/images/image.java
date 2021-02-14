package fr.formiko.usuel.images;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import fr.formiko.usuel.types.str;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.fichier;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 *{@summary image class that contain a lot of tools to use images. <br>}
 *@author Hydrolien
 *@version 1.4
 */
public class image{
  /**
   *{@summary The Images directory.<br>}
   *@version 1.3
   */
  public static final String REP = "data/image/";
  /**
   *{@summary The Images directory. Used to put temporary Images.<br>}
   *@version 1.3
   */
  public static final String REP2 = "data/image/temporaire/";
  /**
   *{@summary The Images directory for extra texture.<br>}
   *You can add new image that will be used as the game texture (as chargement(Max+1)).
   *Or You can replace some images by puting an image with the same name than an actual image in REP or REP2.
   *@version 1.3
   */
  public static String REPTEXTUREPACK = null;
  // GET SET --------------------------------------------------------------------
  public static void setREPTEXTUREPACK(String s){
    if(s!=null){s = str.sToDirectoryName(s);}
    REPTEXTUREPACK=s;
  }
  public static String getREP(){ return REP;}
  // Fonctions propre -----------------------------------------------------------
  /**
   *{@summary Try to read an Image file<br>}
   *Image are File who end with ".png" or ".jpg".<br>
   *@param f File that sould contain the Image.
   *@return Image on the file or null if something went wrong.
   *@version 1.3
   */
  public static BufferedImage readImage(File f){
    if(!isImage(f)){erreur.erreur("L'Image sencé être dans le fichier suivant n'as pas été reconnue en temps qu'image. "+f.toString());return null;}
    try {
      return ImageIO.read(f);
    }catch (IOException e) {
      //on n'affiche plus systématiquement l'erreur car on a parfois besoin d'essayer d'ouvrir une image et de ressevoir null pour réésayer dans un autre répertoire.
      //erreur.erreur("L'Image sencé être dans le fichier suivant n'as pas été correctement trouvé et chargée. "+f.toString());
      return null;
    }
  }public static BufferedImage readImage(String s){return readImage(new File(s));}
  /**
   *{@summary get an Image in 1 of the 3 usuals images directories.<br>}
   *It will 1a search on REPTEXTUREPACK, then in REP and finaly in REP2.
   *Image are File who end with ".png" or ".jpg".<br>
   *.png or .jpg do not need to be precised on the name.<br>
   *@param nom Name of the file without REP part.
   *@version 1.3
   */
  public static BufferedImage getImage(String nom){
    BufferedImage imgr = null;
    if(REPTEXTUREPACK!=null){imgr = getImage(nom,REPTEXTUREPACK);}
    if(imgr==null){imgr = getImage(nom,REP);}
    if(imgr==null){imgr = getImage(nom,REP2);}//si on ne l'as pas trouvé dans le 1a répertoire on vas chercher dans le 2a.
    if(imgr==null){imgr = readImage(new File(REP+"null.png"));}
    return imgr;
  }
  /**
   *{@summary get an Image in a directory.<br>}
   *Image are File who end with ".png" or ".jpg".<br>
   *.png or .jpg do not need to be precised on the name.<br>
   *@param nom Name of the file without REP part.
   *@param repTemp directory were to search.
   *@version 1.3
   */
  public static BufferedImage getImage(String nom, String repTemp){
    BufferedImage imgr = null;
    repTemp = str.sToDirectoryName(repTemp);
    // si le .png ou .jpg etc n'as pas été précisé, on teste les 2 terminaison (.png d'habord).
    if(str.contient(nom,".png",2) || str.contient(nom,".jpg",2)){//si on a déja un .png ou un .jpd a la fin du nom.
      imgr = readImage(new File(repTemp+nom));
    }else{//sinon il nous faut ajouter l'un ou l'autre.
      String nomTemp = nom + ".png";
      File f = new File(repTemp+nomTemp);
      if(f.exists()){
        imgr = readImage(f);
      }else{
        nomTemp = nom + ".jpg";
        f = new File(repTemp+nomTemp);
        if(f.exists()){
          imgr = readImage(f);
        }else{
          return null;
        }
      }
    }
    return imgr;
  }
  /**
   *{@summary get an array [] of Image.<br>}
   *Image are File who end with ".png" or ".jpg"
   *@param nom Name of the file without REP part and number and .png or .jpg part of it.
   *@param nbr Number of image that we want.
   *@param lettre A letter that can be after the number on rotated image file. ' ' or 'ø' if there is no letter after the number.
   *@param x Needed only if x!=0. x is the 1a number of the numbering.
   *@version 1.3
   */
  public static BufferedImage [] getImages(String nom,char lettre, int nbr, byte x){
    BufferedImage tr [] = new BufferedImage [nbr];int k=x;
    for (int i=0;i<nbr ;i++ ) {
      String s = nom+k+lettre;
      if(lettre==' ' || lettre=='ø'){ s = nom+k;}
      tr[i] = getImage(s);k++;
    }
    return tr;
  }
  public static BufferedImage [] getImages(String nom, int nbr, byte x){ return getImages(nom,' ',nbr,x);}
  public static BufferedImage[] getImages(String nom, byte x){return getImages(nom, getNbrImages(nom, x));}
  /**
   *{@summary Fined the last existing number of image from x.<br>}
   *Image are File who end with ".png" or ".jpg"
   *@param nom Name of the file without REP part and number and .png or .jpg part of it.
   *@param x Needed only if x!=0. x is the 1a number of the numbering.
   *@version 1.3
   */
  public static int getNbrImages(String nom, byte x){
    int t [] = new int [5];
    t[0] = getNbrImages(nom,REP,x);
    t[1] = getNbrImages(nom,REP2,x);
    t[2] = getNbrImages(nom,REPTEXTUREPACK,x);
    t[3] = getNbrImages(nom,REPTEXTUREPACK,(byte)t[0]);//il peu y avoir la suite des images des répertoires classique.
    t[4] = getNbrImages(nom,REPTEXTUREPACK,(byte)t[1]);
    return math.max(t);
  }public static int getNbrImages(String n){return getNbrImages(n,(byte)0);}
  /**
   *{@summary Fined the last existing number of image from x on a directory<br>}
   *Image are File who end with ".png" or ".jpg"
   *@param nom Name of the file without REP part and number and .png or .jpg part of it.
   *@param rep Directory name.
   *@param x Needed only if x!=0. x is the 1a number of the numbering.
   *@version 1.3
   */
  public static int getNbrImages(String nom, String rep, byte x){
    int nbr = x;
    File f = new File(rep+nom+nbr+".png");
    File f2 = new File(rep+nom+nbr+".jpg");
    while(f.exists() || f2.exists()){
      f = new File(rep+nom+nbr+".png");
      f2 = new File(rep+nom+nbr+".jpg");
      nbr++;
    }nbr--; //on compense le cran de trop.
    if(nbr==x-1){return 0;}//si on est jamais entré dans la boucle.
    return math.max(nbr,0);
  }public static int getNbrImages(String n, String rep){return getNbrImages(n,rep,(byte)0);}
  public static BufferedImage[] getImages(String nom, int nbr){ return getImages(nom,nbr,(byte)0);}
  public static BufferedImage[] getImages(String nom){ return getImages(nom,(byte)0);}
  /**
   *{@summary get an array [][] of Image.<br>}
   *Image are File who end with ".png" or ".jpg"<br>
   *This metode will always return a new Image [4][nbr] to have the 4 rotated images.
   *@param nom Name of the file without REP part and number and .png or .jpg part of it.
   *@param nbr Number of image that we want.
   *@param x Needed only if x!=0. x is the 1a number of the numbering.
   *@version 1.3
   */
  public static BufferedImage[][] getImagess(String nom, int nbr, byte x){
    BufferedImage tr [][]= new BufferedImage[4][];
    //nom = "temporaire/"+nom;
    tr[0] = getImages(nom,'h',nbr,x);
    if(Main.getElementSurCarteOrientéAprèsDéplacement()){
      tr[1] = getImages(nom,'d',nbr,x);
      tr[2] = getImages(nom,'b',nbr,x);
      tr[3] = getImages(nom,'g',nbr,x);
    }
    return tr; // tr[1] et plus est null si l'orrientation n'est pas prise en compte.
  }

  public static BufferedImage[][] getImagess(String nom, int nbr){ return getImagess(nom,nbr,(byte)0);}
  public static BufferedImage[][] getImagess(String nom){ return getImagess(nom,(byte)0);}
  public static BufferedImage[][] getImagess(String nom, byte x){ return getImagess(nom,getNbrImages(nom),x);}
  /**
   *{@summary transforme to Scaled instance a Image []<br>}
   *@param width the width to which to scale the image.
   *@param heigth the height to which to scale the image.
   *@param hints flags to indicate the type of algorithm to use for image resampling.
   *@version 1.3
   */
  public static BufferedImage [] getScaledInstance(BufferedImage img [],int width, int heigth, int hints){
    int len = img.length;
    for (int i=0;i<len ;i++ ) {
      img[i].getScaledInstance(width,heigth,hints);
    }
    return img;
  }
  /**
   *{@summary delete every file in REP2.<br>}
   *@version 1.3
   */
  public static void clearTemporaire(){
    File docier = new File(REP2);
    File[] fichiers = docier.listFiles();
    for (File f : fichiers ) {
      if(isImage(f)){f.delete();}
    }
  }
  /**
   *{@summary delete every FSomething file in REP2.<br>}
   *@version 1.3
   */
  public static void clearPartielTemporaire(){
    File docier = new File(REP2);
    File[] fichiers = docier.listFiles();
    for (File f : fichiers ) {
      if(str.contient(f.getName(),"F",0)){//si c'est une fourmi. (= si le fichier commence par "F")
        if(isImage(f)){f.delete();}
      }
    }
  }
  //taille d'une image de Creature.
  /**
   *{@summary Return size of a Creature image.<br>}
   *@param idEspece id of the Species (size depend of Species)<br>
   *@param stade stade also infulence size of the Creature
   *@param taille taille used if it was 0% realistic.
   *@version 1.3
   */
  public static int taille(int idEspece, int stade,int taille){
    int a = Main.getEspeceParId(idEspece).getTaille(stade);//la taille en fonction du stade (100 en moyenne.)
    return taille(a,taille);
  }
  /**
   *{@summary Return size of a Creature image.<br>}
   *@param a size assumed if it was 100% realistic.
   *@param taille size used if it was 0% realistic.
   *@version 1.3
   */
  public static int taille(int a,int taille){
    double tailleR = Main.getOp().getTailleRealiste()/100.0;
    double db = ((a*taille*tailleR)/100) + (taille)*(1-tailleR);//en pixel on fait *4.
    return (int)db;
  }
  public static boolean isImage(File f){//2 possibilité, le fichier ce termine par .pnj ou par .jpg.
    if(f==null){return false;}
    if(str.contient(f.getName(),".png",2)){return true;}
    if(str.contient(f.getName(),".jpg",2)){return true;}
    return false;
  }
  public static boolean deleteDirectory(File f){ return fichier.deleteDirectory(f);}

  public BufferedImage rognerImage(BufferedImage i){
    Img img = new Img(i);
    img.rognerBordTransparent();
    img.actualiserImage();
    img.sauvegarder("tempFromImage");
    try {
      File f = new File(REP+"tempFromImage.png");
      if(isImage(f)){
        BufferedImage i2 = getImage("tempFromImage");
        return i2;
      }else{
        erreur.erreur("impossible de charger l'image correctement","image.rognerImage");
      }
    }catch (Exception e) {
      erreur.erreur("impossible de charger l'image correctement","image.rognerImage");
    }
    return i;
  }
  /**
  *{@summary A fonction to getScaledInstance and return a BufferedImage.} <br>
  *cf https://stackoverflow.com/questions/9417356/bufferedimage-resize/9417836#9417836
  *@param bi The Image to resize.
  *@param newW The new width.
  *@param newH The new height.
  *@version 1.31
  */
  public static BufferedImage resize(BufferedImage bi, int newW, int newH) {
    Image tmp = bi.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2d = dimg.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();

    return dimg;
  }
  /**
  *{@summary A fonction to getScaledInstance and return a BufferedImage.}<br>
  *This function use resize(bufferedImage, width, height).<br>
  *This function resize the biger side to newHW &#38; the other size to keep the proportions.<br>
  *@param bi The Image to resize.
  *@param newHW The new height or width (biger side).
  *@version 1.31
  */
  public static BufferedImage resize(BufferedImage bi, int newHW){
    int newW = 0;
    int newH = 0;
    if(bi.getWidth()>bi.getHeight()){
      newW = newHW;
      newH = newHW*bi.getHeight()/bi.getWidth(); //a smaler size.
    }else{
      newH = newHW;
      newW = newHW*bi.getWidth()/bi.getHeight(); //a smaler size.
    }
    double racioWH = bi.getWidth()/bi.getHeight();
    return resize(bi,newW,newH);
  }
}
