package fr.formiko.usuel.image;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.formiko.Main;

public class image{
  public static Image getImage(String nom){
    Image imgr = null;
    // si le .png ou .jpg etc n'as pas été précisé, on ajoute .png a la fin.
    if (str.nbrDeX(nom,'.')==0){ nom = nom + ".png";}
    try {
      imgr = ImageIO.read(new File("data/imageFormiko/"+nom));
    } catch (IOException e) {
      try {
        imgr = ImageIO.read(new File("data/imageFormiko/"+nom.substring(0,nom.length()-4)+".jpg"));
      }catch (IOException e3) {
        erreur.erreurChargementImage(nom);
        try {
          imgr = ImageIO.read(new File("data/imageFormiko/null.png"));
        }catch (IOException e2) {
          erreur.erreurChargementImage("null.png");
        }
      }
    }
    return imgr;
  }
  public static Image [] getImages(String nom,char lettre, int nbr, byte x){
    Image tr [] = new Image [nbr];int k=x;
    for (int i=0;i<nbr ;i++ ) {
      String s = nom+k+lettre;
      if(lettre==' '){ s = nom+k;}
      tr[i] = getImage(s);k++;
    }
    return tr;
  }
  public static Image [] getImages(String nom, int nbr, byte x){ return getImages(nom,' ',nbr,x);}
  public static Image[] getImages(String nom, byte x){
    int nbr = getNbrImages(nom, x);
    return getImages(nom, nbr);
  }
  public static int getNbrImages(String nom, byte x){
    int nbr = x;
    File f = new File("data/imageFormiko/"+nom+nbr+".png");
    File f2 = new File("data/imageFormiko/"+nom+nbr+".jpg");
    while(f.exists() || f2.exists()){
      f = new File("data/imageFormiko/"+nom+nbr+".png");
      f2 = new File("data/imageFormiko/"+nom+nbr+".jpg");
      nbr++;
    }nbr--; //on compense le cran de trop.
    return nbr;
  }public static int getNbrImages(String n){return getNbrImages(n,(byte)0);}
  public static Image[] getImages(String nom, int nbr){ return getImages(nom,nbr,(byte)0);}
  public static Image[] getImages(String nom){ return getImages(nom,(byte)0);}
  public static Image[][] getImagess(String nom, int nbr, byte x){
    Image tr [][]= new Image[4][];
    nom = "temporaire/"+nom;
    tr[0] = getImages(nom,'h',nbr,x);
    if(Main.getElementSurCarteOrientéAprèsDéplacement()){
      tr[1] = getImages(nom,'d',nbr,x);
      tr[2] = getImages(nom,'b',nbr,x);
      tr[3] = getImages(nom,'g',nbr,x);
    }
    return tr; // tr[1] et plus est null si l'orrientation n'est pas prise en compte.
  }
  public static Image[][] getImagess(String nom, int nbr){ return getImagess(nom,nbr,(byte)0);}
  public static Image[][] getImagess(String nom){ return getImagess(nom,(byte)0);}
  public static Image[][] getImagess(String nom, byte x){ return getImagess(nom,getNbrImages(nom),x);}
  public static void clearTemporaire(){
    String chemin = "data/imageFormiko/temporaire/";
    File docier = new File(chemin);
    File[] fichiers = docier.listFiles();
    for (File f : fichiers ) {
      f.delete();
    }
  }
  public static void clearPartielTemporaire(){
    String chemin = "data/imageFormiko/temporaire/";
    File docier = new File(chemin);
    File[] fichiers = docier.listFiles();
    for (File f : fichiers ) {
      if(f.getName().contains("F")){//si c'est une fourmi.
        f.delete();
      }
    }
  }
  public static int getNbrDeFichierCommencantPar(String nom){
    String chemin = "data/imageFormiko/temporaire/";
    //on veut le nombre de fichier Ix.qqchose
    int i=0;
    File f = new File("data/imageFormiko/"+nom+i+".png");
    while(f.exists()){
      f = new File("data/imageFormiko/"+nom+i+".png");
      i++;
    }
    //on a le 1a i pour lequel il n'y a plus de fichier nomi.png
    return i-1;
  }
  public static Image [] getScaledInstance(Image img [],int a, int b, int c){
    int len = img.length;
    for (int i=0;i<len ;i++ ) {
      img[i].getScaledInstance(a,b,c);
    }
    return img;
  }
}