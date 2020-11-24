package fr.formiko.graphisme;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.awt.Image;
import fr.formiko.usuel.image.image;

/**
*{@summary Have all data (images) that will be used by the graphic interface.}
*@author Hydrolien
*@version 1.13
*/
public class Data {
  private int tailleDUneCase; // entre 10 et 500.
  private final int tailleDUneCaseBase = 500;
  private int scale = Image.SCALE_SMOOTH;
  private boolean imageIni;
  //image
  private Image imgNull;
  private Image selectionnee; private Image fere;
  private Image cNuageuse,cSombre;
  private Image b[];
  private Image tI1[];
  private Image tI2[];
  private Image tIF[][];
  private Image tII[][];
  private Image tG[][];
  private Image tF[][];
  //ini (this var sould not be modify in an other place than here.)
  private Image imgNullIni;
  private Image selectionneeIni; private Image fereIni;
  private Image cNuageuseIni,cSombreIni;
  private Image bIni[];
  private Image tI1Ini[];
  private Image tI2Ini[];
  private Image tIFIni[][];
  private Image tIIIni[][];
  private Image tGIni[][];
  private Image tFIni[][];

  // CONSTRUCTEUR ---------------------------------------------------------------

  // GET SET --------------------------------------------------------------------
  //ini
  public int getTailleDUneCase(){return tailleDUneCase;}
  public void setTailleDUneCase(int x){tailleDUneCase=x;}
  public Image getImgNull(){return imgNull;}
  public Image getSelectionnee(){return selectionnee;}
  public Image getFere(){return fere;}
  public Image getCNuageuse(){return cNuageuse;}
  public Image getCSombre(){return cSombre;}
  public Image [] getB(){return b;}
  public Image [] getTI1(){return tI1;}
  public Image [] getTI2(){return tI2;}
  public Image [][] getTIF(){return tIF;}
  public Image [][] getTII(){return tII;}
  public Image [][] getTG(){return tG;}
  public Image [][] getTF(){return tF;}
  // Fonctions propre -----------------------------------------------------------

  //public class Controleur{
    //iniImage etc
    public void chargerImages(){
      debug.débogage("chargement des images a la bonne taille.");
      if(!imageIni){
        chargerImagesIni();
        Main.débutCh();
      }
      int tailleFourmi = (tailleDUneCase*4)/5;
      imgNull = imgNullIni.getScaledInstance(tailleDUneCase, tailleDUneCase,scale);
      selectionnee = selectionneeIni.getScaledInstance(tailleDUneCase, tailleDUneCase,scale);
      tI1=getScaledInstance(tI1Ini, tailleDUneCase);
      tI2=getScaledInstance(tI2Ini, tailleDUneCase);
      tIF=getScaledInstance(tIFIni, tailleFourmi);
      tII=getScaledInstance(tIIIni, tailleFourmi,2);//les insectes
      tF=getScaledInstance(tFIni, tailleFourmi,1);//les Fourmis au différent stade.
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
        selectionneeIni = image.getImage("selectionnee").getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
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
      tI2Ini[0]=image.getImage("herbe").getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
      tI2Ini[1]=image.getImage("mousse").getScaledInstance(tailleDUneCaseBase, tailleDUneCaseBase,scale);
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
    public Image [][] chargerTX(String sn){return chargerTX(sn,image.getNbrImages(sn));}


    public Image [] getScaledInstance(Image ti[],int dim, int b){
      int lenr = ti.length;
      Image r [] = new Image[lenr];
      for (int i=0;i<lenr ;i++ ) {
        if(b==0){//par défaut
          r[i]=ti[i].getScaledInstance(dim, dim,scale);
        }else if(b==1){//pour les fourmis.
          int idEspece = 0;
          int stade = i-3;
          r[i]=ti[i].getScaledInstance(image.taille(idEspece, stade,dim), image.taille(idEspece, stade,dim),scale);
        }else if(b==2){//pour les insectes
          int idEspece = i+100;
          int stade = 0;
          r[i]=ti[i].getScaledInstance(image.taille(idEspece, stade,dim), image.taille(idEspece, stade,dim),scale);
        }
      }
      return r;
    }public Image [] getScaledInstance(Image ti[],int dim){return getScaledInstance(ti,dim,0);}
    public Image [][] getScaledInstance(Image ti[][],int dim, int b){
      int lenr = ti.length;
      Image r [][] = new Image[lenr][];
      for (int i=0;i<lenr ;i++ ) {
        r[i]=getScaledInstance(ti[i],dim,b);
      }
      return r;
    }public Image [][] getScaledInstance(Image ti[][],int dim){return getScaledInstance(ti,dim,0);}

  //}

}
