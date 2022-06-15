package fr.formiko.usuel.images;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Pheromone;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.fichier;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.types.str;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Function;
import javax.imageio.ImageIO;

/**
 *{@summary image class that contain a lot of tools to use images. }<br>
 *@author Hydrolien
 *@lastEditedVersion 1.4
 */
public class image{

  // GET SET -------------------------------------------------------------------
  /***
  *{@summary The Images directory for extra texture.}<br>
  *You can add new image that will be used as the game texture (as chargement(Max+1)).
  *Or You can replace some images by puting an image with the same name than an actual image in getREP() or REP_TEMPORARY.
  *@lastEditedVersion 1.33
  */
  public static String getREPTEXTUREPACK(){return Main.getFolder().getFolderResourcesPacks()+Main.getFolder().getFolderImages();}
  public static void setREPTEXTUREPACK(String s){Main.getFolder().setFolderResourcesPacks(s);}
  /***
   *{@summary The Images directory.}<br>
   *@lastEditedVersion 1.33
   */
  public static String getREP(){ return Main.getFolder().getFolderStable()+Main.getFolder().getFolderImages();}
  public static String getREPTEMPORARY(){ return Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderImages(); }
  // FUNCTIONS -----------------------------------------------------------------
  /**
   *{@summary Try to read an Image file}<br>
   *Image are File who end with ".png" or ".jpg".<br>
   *@param f File that sould contain the Image.
   *@return Image on the file or null if something went wrong.
   *@lastEditedVersion 1.3
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
   *{@summary get an Image in 1 of the 3 usuals images directories.}<br>
   *It will 1a search on getREPTEXTUREPACK(), then in getREP() and finaly in REP_TEMPORARY.
   *Image are File who end with ".png" or ".jpg".<br>
   *.png or .jpg do not need to be precised on the name.<br>
   *@param nom Name of the file without getREP() part.
   *@param returnImageNull If true it will return an error image insted of null.
   *@lastEditedVersion 1.32
   */
  public static BufferedImage getImage(String nom, boolean returnImageNull){
    BufferedImage imgr = null;
    if(getREPTEXTUREPACK()!=null){imgr = getImage(nom,getREPTEXTUREPACK());}
    if(imgr==null){imgr = getImage(nom,getREP());}
    if(imgr==null){imgr = getImage(nom,getREPTEMPORARY());}//si on ne l'as pas trouvé dans le 1a répertoire on vas chercher dans le 2a.
    if(imgr==null && returnImageNull){imgr = readImage(new File(getREP()+"null.png"));}
    return imgr;
  }
  public static BufferedImage getImage(String nom){return getImage(nom,true);}
  /**
   *{@summary get an Image in a directory.}<br>
   *Image are File who end with ".png" or ".jpg".<br>
   *.png or .jpg do not need to be precised on the name.<br>
   *@param nom Name of the file without getREP() part.
   *@param repTemp directory were to search.
   *@lastEditedVersion 1.3
   */
  public static BufferedImage getImage(String nom, String repTemp){
    BufferedImage imgr = null;
    repTemp = str.sToDirectoryName(repTemp);
    // si le .png ou .jpg etc n'as pas été précisé, on teste les 2 terminaison (.png d'habord).
    if(str.contient(nom,".png",2) || str.contient(nom,".jpg",2)){//si on a déja un .png ou un .jpd a la fin du nom.
      imgr = readImage(new File(repTemp+nom));
    }else{//sinon il nous faut add l'un ou l'autre.
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
   *{@summary get an array [] of Image.}<br>
   *Image are File who end with ".png" or ".jpg"
   *@param nom Name of the file without getREP() part and number and .png or .jpg part of it.
   *@param nbr Number of image that we want.
   *@param lettre A letter that can be after the number on rotated image file. ' ' or 'ø' if there is no letter after the number.
   *@param k Needed only if k!=0. k is the 1a number of the numbering.
   *@lastEditedVersion 1.3
   */
  public static BufferedImage [] getImages(String nom, char lettre, int nbr, byte k){
    debug.débogage("getImagess "+nom+" from "+k+" to "+(nbr+k));
    BufferedImage tr [] = new BufferedImage [nbr];
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
   *{@summary Fined the last existing number of image from x.}<br>
   *Image are File who end with ".png" or ".jpg"
   *@param nom Name of the file without getREP() part and number and .png or .jpg part of it.
   *@param x Needed only if x!=0. x is the 1a number of the numbering.
   *@lastEditedVersion 1.3
   */
  public static int getNbrImages(String nom, byte x){
    int t [] = new int [5];
    t[0] = getNbrImages(nom,getREP(),x);
    t[1] = getNbrImages(nom,getREPTEMPORARY(),x);
    t[2] = getNbrImages(nom,getREPTEXTUREPACK(),x);
    t[3] = getNbrImages(nom,getREPTEXTUREPACK(),(byte)t[0]);//il peu y avoir la suite des images des répertoires classique.
    t[4] = getNbrImages(nom,getREPTEXTUREPACK(),(byte)t[1]);
    return math.max(t);
  }public static int getNbrImages(String n){return getNbrImages(n,(byte)0);}
  /**
   *{@summary Fined the last existing number of image from x on a directory}<br>
   *Image are File who end with ".png" or ".jpg"
   *@param nom Name of the file without repStable part and number and .png or .jpg part of it.
   *@param repStable Directory name.
   *@param x Needed only if x!=0. x is the 1a number of the numbering.
   *@lastEditedVersion 1.3
   */
  public static int getNbrImages(String nom, String repStable, byte x){
    int nbr = x;
    File f = new File(repStable+nom+nbr+".png");
    File f2 = new File(repStable+nom+nbr+".jpg");
    while(f.exists() || f2.exists()){
      f = new File(repStable+nom+nbr+".png");
      f2 = new File(repStable+nom+nbr+".jpg");
      nbr++;
    }nbr--; //on compense le cran de trop.
    if(nbr==x-1){return 0;}//si on est jamais entré dans la boucle.
    return math.max(nbr,0);
  }public static int getNbrImages(String n, String repStable){return getNbrImages(n,repStable,(byte)0);}
  public static BufferedImage[] getImages(String nom, int nbr){ return getImages(nom,nbr,(byte)0);}
  public static BufferedImage[] getImages(String nom){ return getImages(nom,(byte)0);}
  /**
   *{@summary get an array [][] of Image.}<br>
   *Image are File who end with ".png" or ".jpg"<br>
   *This metode will always return a new Image [4][nbr] to have the 4 rotated images.
   *@param nom Name of the file without getREP() part and number and .png or .jpg part of it.
   *@param nbr Number of image that we want.
   *@param x Needed only if x!=0. x is the 1a number of the numbering.
   *@lastEditedVersion 1.3
   */
  public static BufferedImage[][] getImagess(String nom, int nbr, byte x){
    BufferedImage tr [][]= new BufferedImage[4][];
    //nom = "temporaire/"+nom;
    tr[0] = getImages(nom,'h',nbr,x);
    // if(Main.getOrientedObjectOnMap()){
    //   tr[1] = getImages(nom,'d',nbr,x);
    //   tr[2] = getImages(nom,'b',nbr,x);
    //   tr[3] = getImages(nom,'g',nbr,x);
    // }
    return tr; // tr[1] et plus est null si l'orrientation n'est pas prise en compte.
  }

  public static BufferedImage[][] getImagess(String nom, int nbr){ return getImagess(nom,nbr,(byte)0);}
  public static BufferedImage[][] getImagess(String nom){ return getImagess(nom,(byte)0);}
  public static BufferedImage[][] getImagess(String nom, byte x){ return getImagess(nom,getNbrImages(nom),x);}
  /**
   *{@summary transforme to Scaled instance a Image []}<br>
   *@param width the width to which to scale the image.
   *@param height the height to which to scale the image.
   *@param hints flags to indicate the type of algorithm to use for image resampling.
   *@lastEditedVersion 1.3
   */
  public static BufferedImage [] getScaledInstance(BufferedImage img [],int width, int height, int hints){
    int len = img.length;
    for (int i=0;i<len ;i++ ) {
      img[i].getScaledInstance(width,height,hints);
    }
    return img;
  }
  /**
   *{@summary delete every file in REP_TEMPORARY.}<br>
   *@lastEditedVersion 1.3
   */
  public static void clearTemporaire(){
    File folder = new File(getREPTEMPORARY());
    if(folder !=null && folder.isDirectory()){
      File[] fichiers = folder.listFiles();
      if(fichiers!=null){
        for (File f : fichiers ) {
          if(isImage(f)){f.delete();}
        }
      }
    }
  }
  /**
   *{@summary delete every FSomething file in REP_TEMPORARY.}<br>
   *@lastEditedVersion 1.3
   */
  public static void clearPartielTemporaire(){
    //TODO #248 remove all this function that isn't need anymore.
    File folder = new File(getREPTEMPORARY());
    if(folder.exists() && folder.isDirectory()){
      File[] fichiers = new File[0];
      fichiers = folder.listFiles();
      if(fichiers != null){
        for (File f : fichiers ) {
          if(str.contient(f.getName(),"F0&",0)){
            if(isImage(f)){f.delete();}
          }
        }
      }
    }
  }
  //taille d'une image de Creature.
  /***
  *{@summary Return size of a Creature image.}<br>
  *@param c Creature that we need size
  *@param taille taille used if it was 0% realistic
  *@lastEditedVersion 1.3
  */
  // public static int taille(Creature c, int taille){
  //   int a;
  //   if(c instanceof Fourmi){
  //     a = c.getIndividu().getSize();
  //   }else{
  //     a = c.getEspece().getSize(stade);
  //   }
  //   //standard a is 100
  //   return taille(a,taille);
  // }
  /**
  *{@summary Return size of a Creature image.}<br>
  *If ant is not found it will just return taille.
  *@param idEspece id of the Species (size depend of Species)
  *@param typeF typeF also infulence size of the Ant
  *@param taille taille used if it was 0% realistic
  *return Size with a part of realistic
  *@lastEditedVersion 2.16
  */
  public static int tailleFourmi(int idEspece, int typeF, int taille){
    if(Main.getEspeceById(idEspece)!=null && Main.getEspeceById(idEspece).getIndividuByType(typeF)!=null){
      int a = Main.getEspeceById(idEspece).getIndividuByType(typeF).getSize();
      return taille(a,taille);
    }else{
      erreur.alerte("Ant specie "+idEspece+" have no Individu for "+typeF);
      return taille;
      // return -1.
    }
  }
  /**
  *{@summary Return size of a Creature image.}<br>
  *@param idEspece id of the Species (size depend of Species)<br>
  *@param stade stade also infulence size of the Creature
  *@param taille taille used if it was 0% realistic
  *@lastEditedVersion 1.3
  */
  public static int taille(int idEspece, int stade, int taille){
    int a = Main.getEspeceById(idEspece).getSize(stade);//standard a is 100
    return taille(a,taille);
  }
  /**
  *{@summary Return size of a Creature image.}<br>
  *@param a size assumed if it was 100% realistic
  *@param taille size used if it was 0% realistic
  *@lastEditedVersion 1.3
  */
  public static int taille(int a, int taille){
    double tailleR = Main.getOp().getRealisticSize()/100.0;
    double db = ((a*taille*tailleR)/100) + (taille)*(1-tailleR);//en pixel on fait *4.
    return (int)db;
  }
  /**
  *{@summary True is file is an Image.}<br>
  *An image end with .png or .jpg
  *@lastEditedVersion 2.5
  */
  public static boolean isImage(File f){
    if(f==null){return false;}
    if(f.getName().endsWith(".png")){return true;}
    if(f.getName().endsWith(".jpg")){return true;}
    return false;
  }
  /*public BufferedImage rognerImage(BufferedImage i){
    Img img = new Img(i);
    img.rognerBordTransparent();
    img.actualiserImage();
    img.sauvegarder("tempFromImage");
    try {
      File f = new File(getREP()+"tempFromImage.png");
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
  }*/
  /**
  *{@summary A fonction to getScaledInstance and return a BufferedImage.} <br>
  *If images is alredy resize to the exacte dimention it only return the same image.<br>
  *cf https://stackoverflow.com/questions/9417356/bufferedimage-resize/9417836#9417836<br>
  *@param bi The Image to resize.
  *@param newW The new width.
  *@param newH The new height.
  *@lastEditedVersion 1.31
  */
  public static BufferedImage resize(BufferedImage bi, int newW, int newH) {
    if(bi==null){return null;}
    if(newW==bi.getWidth() && newH==bi.getHeight()){return bi;}
    if(newW<1){newW=1;}
    if(newH<1){newH=1;}
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
  *@param in The Image to resize.
  *@param newHW The new height or width (biger side).
  *@param 1.31
  */
  public static BufferedImage resize(BufferedImage in, int newHW){
    if(in==null){return null;}
    int newW;
    int newH;
    if(in.getWidth()>in.getHeight()){
      newW = newHW;
      newH = newHW*in.getHeight()/in.getWidth(); //a smaler size.
    }else{
      newH = newHW;
      newW = newHW*in.getWidth()/in.getHeight(); //a smaler size.
    }
    return resize(in,newW,newH);
  }
  /**
  *{@summary A fonction to rotate &#38; center a BufferedImage.}<br>
  *@param bi The Image to rotate &#38; center
  *@param direction The direction to rotate. Direction are multiplied by 45°
  *@lastEditedVersion 2.16
  */
  public static BufferedImage rotateAndCenterImage(BufferedImage bi, int direction){
    bi = rotateImage(bi,direction);
    return trimTransparentBorder(bi);
    // Img img = new Img(bi);
    // img.rognerBordTransparent();
    // img.actualiserImage();
    // return img.toBufferedImage();
  }
  /**
  *{@summary A fonction to trim a BufferedImage.}<br>
  *This function use countTransparentBorders(bufferedImage).<br>
  *@param in The Image to trim
  *@lastEditedVersion 2.15
  */
  public static BufferedImage trimTransparentBorder(BufferedImage in){
    int t[] = countTransparentBorders(in);
    // erreur.info("trimed : "+t[0]+" "+t[1]+" "+t[2]+" "+t[3]);
    return in.getSubimage(t[0], t[1], in.getWidth()-t[0]-t[2], in.getHeight()-t[1]-t[3]);
  }
  /**
  *{@summary A fonction to count transparent borders of a BufferedImage.}<br>
  *A border is transparent if alpha=0 on all the pixel line.
  *@param in The Image to count transparent borders
  *@lastEditedVersion 2.16
  */
  public static int [] countTransparentBorders(BufferedImage in){
    int t[]={0,0,0,0};
    if(in==null){return t;}
    int lenI = in.getHeight();
    int lenJ = in.getWidth();
    t[0]=countTransparentBorder(in, lenJ, lenI, false, false);
    t[1]=countTransparentBorder(in, lenI, lenJ, true, false);
    t[2]=countTransparentBorder(in, lenJ, lenI, false, true);
    t[3]=countTransparentBorder(in, lenI, lenJ, true, true);
    return t;
  }
  /**
  *{@summary A fonction to count a transparent border of a BufferedImage.}<br>
  *A border is transparent if alpha=0 on all the pixel line.
  *@param in The Image to count transparent borders
  *@param lenI width or height depending of revers
  *@param lenJ width or height depending of revers
  *@param revers True: revers width &#38; height
  *@param fromTheEnd True: start from the end of the image (width or height)
  *@lastEditedVersion 2.15
  */
  public static int countTransparentBorder(BufferedImage in, int lenI, int lenJ, boolean revers, boolean fromTheEnd){
    int r=0;
    for (int i=0; i<lenI; i++) {
      for (int j=0; j<lenJ; j++) {
        int x,y;
        if(revers){
          if(fromTheEnd){x=lenJ-j-1; y=lenI-i-1;}
          else{x=j; y=i;}
        }else{
          if(fromTheEnd){x=lenI-i-1; y=lenJ-j-1;}
          else{x=i; y=j;}
        }
        if(((in.getRGB(x,y)>>24)&255)!=0){ //if it's not a full alpha pixel, we stop.
          return r;
        }
      }
      r++;
    }
    return r;
  }
  /**
  *{@summary A fonction to rotate a BufferedImage.}<br>
  *@param before The Image to rotate
  *@param direction The direction to rotate. Direction are multiplied by 45°
  *@lastEditedVersion 2.1
  */
  public static BufferedImage rotateImage(BufferedImage before, int direction) {
    if(before==null){return null;}
    direction = (direction+8)%8;
    if(direction==0){return before;}
    int w = before.getWidth();
    int h = before.getHeight();
    int max = Math.max(w,h);
    int min = Math.min(w,h);
    // BufferedImage after = new BufferedImage(newSize, newSize, BufferedImage.TYPE_INT_ARGB);
    BufferedImage after = null;//new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    AffineTransform at = new AffineTransform();
    if(direction%2==1){ // 1, 3, 5, 7
      int halfDiagonal = (int)(Math.sqrt(w*w+h*h)/2.0);
      at.translate(-halfDiagonal + max/2, -halfDiagonal + max/2);
      at.rotate(direction * Math.PI / 4.0,halfDiagonal,halfDiagonal);
      // first - center image at the origin so rotate works OK
      at.translate(halfDiagonal - w/2, halfDiagonal - h/2);
    }else if((direction+2)%4==0){ //2 & 6
      int halfDiagonal = (int)(Math.sqrt(w*w+h*h)/2.0);
      at.translate(-halfDiagonal + h/2, -halfDiagonal + w/2);
      at.rotate(direction * Math.PI / 4.0,halfDiagonal,halfDiagonal);
      at.translate(halfDiagonal - w/2, halfDiagonal - h/2);
    }else{ //4
      at.translate(w/2,h/2);
      at.rotate(direction * Math.PI / 4.0);
      at.translate(-w/2,-h/2);
    }
    AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
    // AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    after = scaleOp.filter(before, null);
    // erreur.info("from a "+w+"x"+h+" image to a "+after.getWidth()+"x"+after.getHeight()+" image.");
    return after;
  }
  /**
  *{@summary A fonction to rotate a BufferedImage.}<br>
  *@param before The Image to rotate
  *@param angle The angle to rotate
  *@lastEditedVersion 2.20
  */
  public static BufferedImage rotateImage(BufferedImage before, double angle) {
    if(before==null){return null;}
    int w = before.getWidth();
    int h = before.getHeight();
    AffineTransform at = new AffineTransform();
    at.translate(w/2,h/2);
    at.rotate(angle);
    at.translate(-w/2,-h/2);
    AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
    return scaleOp.filter(before, null);
  }
  /**
  *{@summary A fonction to translate a BufferedImage.}<br>
  *@param before The Image to translate.
  *@param xOffset offset in x.
  *@param yOffset offset in y.
  *@lastEditedVersion 2.1
  */
  public static BufferedImage translateImage(BufferedImage before, int xOffset, int yOffset, int width, int height){
    if(xOffset==0 && yOffset==0){return before;}
    AffineTransform at = new AffineTransform();
    at.translate(xOffset, yOffset);
    AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    return op.filter(before, new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
  }
  /**
  *{@summary A fonction to rotate a BufferedImage.}<br>
  *@param before The Image to rotate.
  *@param direction The direction to rotate. Direction are multiplied by 1°.
  *@lastEditedVersion 2.1
  */
  public static BufferedImage rotateImage2(BufferedImage before, int direction, int pivotX, int pivotY){
    direction = (direction+360)%360;
    if(direction==0){return before;}
    int w = before.getWidth();
    int h = before.getHeight();
    int max = Math.max(w,h);
    int min = Math.min(w,h);
    // BufferedImage after = new BufferedImage(newSize, newSize, BufferedImage.TYPE_INT_ARGB);
    BufferedImage after = null;//new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    AffineTransform at = new AffineTransform();
    // int halfDiagonal = (int)(Math.sqrt(w*w+h*h)/2.0);
    // at.translate(-halfDiagonal + max/2, -halfDiagonal + max/2);
    at.rotate(direction * Math.PI / 180, pivotX, pivotY);
    // at.translate(halfDiagonal - w/2, halfDiagonal - h/2);
    AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
    // AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    after = scaleOp.filter(before, null);
    // erreur.info("from a "+w+"x"+h+" image to a "+after.getWidth()+"x"+after.getHeight()+" image.");
    return after;
  }
  /**
  *{@summary A tool to flip a BufferedImage.}<br>
  *@param before The Image to flip.
  *@param vertically True, the image will be flip vertically, false it will be flip horizontally.
  *@lastEditedVersion 2.1
  */
  public static BufferedImage flipImage(BufferedImage before, boolean vertically){
    AffineTransform tx;
    AffineTransformOp op;
    if(vertically){
      tx = AffineTransform.getScaleInstance(1, -1);
      tx.translate(0, -before.getHeight(null));
      op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    }else{
      tx = AffineTransform.getScaleInstance(-1, 1);
      tx.translate(-before.getWidth(null), 0);
      op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    }
    return op.filter(before, null);
  }
  /**
  *{@summary Change the image color depending of ant Pheromone.}<br>
  *@param imgColor the image to change.
  *@param ph the Pheromone to get color from.
  *@lastEditedVersion 2.6
  */
  public static BufferedImage changeColor(Img imgColor, Pheromone ph){
    int w = imgColor.getWidth();
    int h = imgColor.getHeight();
    imgColor.setRouge(fullOf(w,h,ph.getR()));
    imgColor.setVert(fullOf(w,h,ph.getG()));
    imgColor.setBleu(fullOf(w,h,ph.getB()));
    imgColor.actualiserImage();
    return imgColor.getImage();
  }
  /**
  *{@summary Full the color of an array.}<br>
  *@lastEditedVersion 2.1
  */
  public static byte [][] fullOf(int x, int y, byte b){
    byte r [][] = new byte[x][y];
    for (int i=0; i<x; i++) {
      for (int j=0; j<y; j++) {
        r[i][j]=b;
      }
    }
    return r;
  }

  //Map
  /**
  *{@summary Return a new HashMap&lt;String, BufferedImage&gt;.}<br>
  *@param folder folder that contain all images
  *@lastEditedVersion 2.7
  */
  public static HashMap<String, BufferedImage> getImagesAsMap(File folder){
    HashMap<String, BufferedImage> map = new HashMap<String, BufferedImage>();
    if (folder.exists() && folder.isDirectory()) {
      for (File f : folder.listFiles()) {
        String s = f.getName();
        if((str.contient(s,".png",2) || str.contient(s,".jpg",2)) && s.length()>4){s=s.substring(0,s.length()-4);}
        map.put(s, readImage(f));
      }
    }
    return map;
  }
  /**
  *{@summary Return a new HashMap&lt;String, BufferedImage&gt;.}<br>
  *@param folderName the name of the folder that contain all images
  *@lastEditedVersion 2.7
  */
  public static HashMap<String, BufferedImage> getImagesAsMap(String folderName){
    return getImagesAsMap(new File(folderName));
  }
  /**
  *{@summary Rezise a HashMap&lt;String, BufferedImage&gt;.}<br>
  *@param mapIn Map that contains images to rezise
  *@lastEditedVersion 2.7
  */
  public static HashMap<String, BufferedImage> getScaledInstanceFromMap(HashMap<String, BufferedImage> mapIn, int size){
    if(mapIn==null){return null;}
    HashMap<String, BufferedImage> mapOut = new HashMap<String, BufferedImage>();
    for (HashMap.Entry<String, BufferedImage> entry : mapIn.entrySet()) {
      BufferedImage bi = entry.getValue();
      bi = resize(bi,size);
      mapOut.put(entry.getKey(), bi);
      // erreur.println("put "+entry.getKey());
    }
    return mapOut;
  }

  /**
  *{@summary Print a BufferedImage color.}
  *@param bi the bufferedImage to print.
  *@lastEditedVersion 2.16
  */
  public static void printBufferedImageColor(BufferedImage bi){
    for (int i=0; i<bi.getWidth(); i++) {
      for (int j=0; j<bi.getHeight(); j++) {
        erreur.print(bi.getRGB(i,j)+" ");
      }
      erreur.println("");
    }
    erreur.println();
  }
  /**
  *{@summary Replace a specified rect by given color.}
  *@param bi the bufferedImage to use
  *@param col the color to draw
  *@param x x of the rectangle
  *@param y y of the rectangle
  *@param width width of the rectangle
  *@param height height of the rectangle
  *@lastEditedVersion 2.16
  */
  public static void replaceRectColor(BufferedImage bi, Color col, int x, int y, int width, int height){
    int colInt = col.getRGB();
    int lenI = width+x;
    int lenJ = height+y;
    for (int i=x; i<lenI; i++) {
      for (int j=y; j<lenJ; j++) {
        bi.setRGB(i,j, colInt);
      }
    }
  }
  /**
  *{@summary Replace color by black &#38; white shade.}
  *@param bi the bufferedImage to use
  *@lastEditedVersion 2.16
  */
  public static BufferedImage toBlackAndWhite(BufferedImage bi){
    BufferedImage gray = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
    Graphics2D g = gray.createGraphics();
    g.drawImage(bi, 0, 0, null);
    return gray;
  }
  /**
  *{@summary Edit all pixels of a BufferedImage.}
  *@param bi the bufferedImage to edit
  *@param fct the function to apply
  *@lastEditedVersion 2.16
  */
  public static void editAllPixels(BufferedImage bi, Function<Integer, Integer> fct) {
    for (int i=0; i<bi.getWidth(); i++) {
      for (int j=0; j<bi.getHeight(); j++) {
        bi.setRGB(i,j,fct.apply(bi.getRGB(i,j)));
      }
    }
  }
}
