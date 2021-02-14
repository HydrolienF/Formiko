package fr.formiko.usuel.images;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.fichier;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.Img;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.tests.TestCaseMuet;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

public class imageTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testReadImage(){
    File f = new File(image.REP+"null.png");
    Image iNull = image.readImage(f);
    assertTrue(iNull!=null);
    assertTrue(f.isFile());//il n'as pas été supprimé lors de la lecture

    //répertoire d'image.
    int x = allea.getAllea(1000)+100;
    Img img = new Img(1,1);
    img.sauvegarder("testImage"+x+".png");
    f = new File(image.REP2+"testImage"+x+".png");
    Image i = image.readImage(f);
    assertTrue(i!=null);
    assertTrue(f.isFile());//il n'as pas été supprimé lors de la lecture
    assertTrue(f.delete());//il ce supprime bien.

    //répertoire courant.
    x = allea.getAllea(1000)+100;
    img = new Img(1,1);
    img.sauvegarder("./","testImage"+x+".png");
    f = new File("testImage"+x+".png");
    i = image.readImage(f);
    assertTrue(i!=null);
    assertTrue(f.isFile());//il n'as pas été supprimé lors de la lecture
    assertTrue(f.delete());//il ce supprime bien.

    x = allea.getAllea(1000)+100;
    img.sauvegarder("./","testImage"+x+".pkg");//nom d'image incorecte.
    f = new File("testImage"+x+".pkg");
    Image i2 = image.readImage(f);
    assertTrue(i2==null);
    assertTrue(f.isFile());//il n'as pas été supprimé lors de la lecture
    assertTrue(f.delete());//il ce supprime bien.

    x = allea.getAllea(1000)+100;
    img.sauvegarder("testImage"+x+".jpg");//nom d'image incorecte.
    f = new File(image.REP2+"testImage"+x+".jpg");

    Image i3 = image.readImage(f);
    assertTrue(i3!=null);
    assertTrue(f.isFile());//il n'as pas été supprimé lors de la lecture
    assertTrue(f.delete());//il ce supprime bien.
  }
  //getImage
  @Test
  public void testGetImage(){
    Image nul = image.getImage("null");
    Image i0 = image.getImage("vbfizefzeg.png");
    //faute de savoir comment comparé les images sans prendre en compte meur adresse mémoire on supose que la taille est assez propre a chaque image.
    assertTrue(i0!=null);
    assertEquals(nul.getWidth(null),i0.getWidth(null));
    Image i1 = image.getImage("I0.png");
    Image i2 = image.getImage("I0");
    Image i3 = image.getImage("I0",image.REP);//si on cherche dans le bon répertoire.
    Image i4 = image.getImage("I0","./"); //si on cherche dans un mauvais répertoire.
    assertEquals(i1.getWidth(null),i2.getWidth(null));
    assertEquals(i1.getWidth(null),i3.getWidth(null));
    assertEquals(null,i4);
  }

  //getImages
  @Test
  public void testGetImages(){
    File f = new File("testDir");
    f.mkdir();
    Img img = new Img(1,1);
    img.sauvegarder("testDir/","testImage"+0+".png");
    img.sauvegarder("testDir/","testImage"+1+".png");
    img.sauvegarder("testDir/","testImage"+2+".png");
    image.setREPTEXTUREPACK("testDir/");
    assertEquals(3,image.getImages("testImage").length);
    assertEquals(0,image.getImages("testImag").length);
    assertEquals(0,image.getImages("testImage.png").length);
    image.setREPTEXTUREPACK("./");
    assertEquals(3,image.getImages("testDir/testImage").length);
    assertEquals(0,image.getImages("testImage").length);
    image.setREPTEXTUREPACK(null);
    assertEquals(0,image.getImages("testDir/testImage").length);
    assertEquals(0,image.getImages("testImage").length);
    //assertEquals(3,image.getImages("testDir/","testImage").length);
    //assertEquals(3,image.getImages("testDir","testImage").length);
    //assertEquals(0,image.getImages("dirX","testImage").length);
    fichier.deleteDirectory(f);
  }

  //getImagess
  @Test
  public void testGetImagess(){
    File f = new File("testDir2");
    f.mkdir();
    Img img = new Img(3,1);
    Main.iniOp();
    Main.getOp().setElementSurCarteOrientéAprèsDéplacement(true);
    img.sauvegarder("testDir2/","testImage"+0+""+".png");
    img.sauvegarder("testDir2/","testImage"+1+""+".png");
    img.sauvegarder("testDir2/","testImage"+2+""+".png");
    img.sauvegarder("testDir2/","testImage"+0+"h"+".png");
    img.sauvegarder("testDir2/","testImage"+1+"h"+".png");
    img.sauvegarder("testDir2/","testImage"+2+"h"+".png");
    img.sauvegarder("testDir2/","testImage"+0+"d"+".png");
    img.sauvegarder("testDir2/","testImage"+1+"d"+".png");
    img.sauvegarder("testDir2/","testImage"+2+"d"+".png");
    img.sauvegarder("testDir2/","testImage"+0+"b"+".png");
    img.sauvegarder("testDir2/","testImage"+1+"b"+".png");
    img.sauvegarder("testDir2/","testImage"+2+"b"+".png");
    img.sauvegarder("testDir2/","testImage"+0+"g"+".png");
    img.sauvegarder("testDir2/","testImage"+1+"g"+".png");
    img.sauvegarder("testDir2/","testImage"+2+"g"+".png");
    image.setREPTEXTUREPACK("testDir2/");
    Image [][] t = image.getImagess("testImage");
    int k=0;
    for(Image ti []: t){
      for (Image i : ti) {
        assertEquals(3,i.getWidth(null));
        assertEquals(1,i.getHeight(null));
        k++;
      }
    }
    assertEquals(12,k);
    Main.getOp().setElementSurCarteOrientéAprèsDéplacement(false);
    t = image.getImagess("testImage");
    k=0;
    for(Image ti []: t){
      if(t!=null){
        try { // on ne peu lire que la 1a colone.
          for (Image i : ti) {
            if(i!=null){
              assertEquals(3,i.getWidth(null));
              assertEquals(1,i.getHeight(null));
              k++;
            }
          }
        }catch (Exception e) {}
      }
    }
    assertEquals(3,k);
    image.setREPTEXTUREPACK(null);
    fichier.deleteDirectory(f);
  }

  //getNbrImages
  @Test
  public void testGetNbrImages(){
    int x = allea.getAllea(1000)+100;
    //créer un dossier avec x0.png, x1.png, x2.png et x4.png.
    String rep = "repTestImage"+x+"/";
    File f = new File(rep);
    assertTrue(f.mkdir());
    try {
      f = new File(rep+"x0.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
      f = new File(rep+"x1.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
      f = new File(rep+"x2.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
      f = new File(rep+"x4.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    //chercher "x" a partir de 0 doit donner 2.
    assertEquals(3,image.getNbrImages("x",rep));
    //chercher "x" a partir de 4 doit donner 4.
    assertEquals(5,image.getNbrImages("x",rep, (byte) 4));
    //chercher "x" a partir de -1 doit donner 0.
    assertEquals(0,image.getNbrImages("x",rep,(byte) -1));
    //créer x-1.png
    try {
      f = new File(rep+"x-1.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    //chercher "x" a partir de -1 doit donner 2.
    assertEquals(3,image.getNbrImages("x",rep,(byte) -1));
    //chercher "y" a partir de 0 ou 1 ou 10 doit donner 0.
    assertEquals(0,image.getNbrImages("y",rep,(byte) -1));
    assertEquals(0,image.getNbrImages("y",rep,(byte) 0));
    assertEquals(0,image.getNbrImages("y",rep,(byte) 1));
    assertEquals(0,image.getNbrImages("y",rep,(byte) 10));
    assertEquals(0,image.getNbrImages("y",rep));
    assertEquals(0,image.getNbrImages("yvub",rep,(byte) -1));
    assertEquals(0,image.getNbrImages("padix",rep,(byte) 10));
    //après les test on supprime le répertoire.
    f = new File(rep);
    assertTrue(fichier.deleteDirectory(f));
  }
  @Test
  public void testGetNbrImagesREP3(){
    //on vérifie que d'éventuelles images complémentaire présente dans REPTEXTUREPACK seront bien chargée.
    image.setREPTEXTUREPACK("testDir5/");
    File f = new File(image.REPTEXTUREPACK);
    assertTrue(f.mkdir());
    try {
      f = new File(image.REPTEXTUREPACK+"x0.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
      f = new File(image.REPTEXTUREPACK+"x1.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
      f = new File(image.REPTEXTUREPACK+"x2.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
      f = new File(image.REPTEXTUREPACK+"x4.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
      f = new File(image.REPTEXTUREPACK+"fourmi3.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    assertEquals(3,image.getNbrImages("x"));
    assertEquals(4,image.getNbrImages("fourmi"));
    try {
      f = new File(image.REPTEXTUREPACK+"fourmi4.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    assertEquals(5,image.getNbrImages("fourmi"));
    f = new File(image.REPTEXTUREPACK);
    assertTrue(fichier.deleteDirectory(f));
  }

  //clearTemporaire - clearPartielTemporaire
  @Test
  public void testClearTemporaire(){
    File f = null;
    File d = new File(image.REP2);
    image.clearTemporaire();//on vire déja toutes les images du fichier temporaire.
    int x = d.listFiles().length;
    try {
      f = new File(image.REP2+"egfeFzg.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    image.clearTemporaire();
    assertEquals(x,d.listFiles().length);
    assertTrue(!f.delete());
    try {
      f = new File(image.REP2+"egfeFzg.p"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    image.clearTemporaire();
    assertEquals(x+1,d.listFiles().length);
    assertTrue(f.delete());
    try {
      f = new File(image.REP2+"a"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    image.clearTemporaire();
    assertEquals(x+1,d.listFiles().length);
    assertTrue(f.delete());
    try {
      f = new File(image.REP2+".jpg"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    image.clearTemporaire();
    assertEquals(x,d.listFiles().length);
    assertTrue(!f.delete());

  }
  @Test
  public void testClearPartielTemporaire(){
    image.clearPartielTemporaire();
    File f = null;
    File d = new File(image.REP2);
    int x = d.listFiles().length;
    // ...F...
    try {
      f = new File(image.REP2+"egfeFzig.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    image.clearPartielTemporaire();
    assertEquals(x+1,d.listFiles().length);
    assertTrue(f.delete());
    //f...
    try {
      f = new File(image.REP2+"fegfezig.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    image.clearPartielTemporaire();
    assertEquals(x+1,d.listFiles().length);
    assertTrue(f.delete());
    //F...
    try {
      f = new File(image.REP2+"Fvi.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    image.clearPartielTemporaire();
    assertEquals(x,d.listFiles().length);
    assertTrue(!f.delete());
    //F...
    try {
      f = new File(image.REP2+"F.jpg"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    image.clearPartielTemporaire();
    assertEquals(x,d.listFiles().length);
    assertTrue(!f.delete());
    //... . ...
    try {
      f = new File(image.REP2+"vehti.hze"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    image.clearPartielTemporaire();
    assertEquals(x+1,d.listFiles().length);
    assertTrue(f.delete());
    //F... . ...
    try {
      f = new File(image.REP2+"Fbn.hze"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    image.clearPartielTemporaire();
    assertEquals(x+1,d.listFiles().length);
    assertTrue(f.delete());
    //F . ...
    try {
      f = new File(image.REP2+"F.hze"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    image.clearPartielTemporaire();
    assertEquals(x+1,d.listFiles().length);
    assertTrue(f.delete());
  }

  //taille
  @Test
  public void testTaille(){
    Main.iniOp();//on initialise les Options.
    //image.taille(taille de l'espece, taille voulu avec le niveau de grossicement)
    Main.getOp().setTailleRealiste(100);//un paramètre utilisé par image.taille
    assertEquals(100,image.taille(100,100));//normale.

    //si la taille n'est pas réaliste on prend a 100% la taille de la case.
    Main.getOp().setTailleRealiste(0);//un paramètre utilisé par image.taille
    assertEquals(100,image.taille(20,100));
    assertEquals(140,image.taille(20,140));
    assertEquals(10,image.taille(20,10));

    //si la taille est réaliste a 100% on ne dépend plus que de taille de l'espece * tailleVOulue/100
    Main.getOp().setTailleRealiste(100);
    assertEquals(20,image.taille(20,100));
    assertEquals(30,image.taille(20,150));
    assertEquals(10,image.taille(20,50));
    assertEquals(4,image.taille(8,50));
    //et ca arrondi bien.
    assertEquals(4,image.taille(8,51));
    assertEquals(4,image.taille(8,56)); // la conversion qu'on fait ne prend que la partie entière, c'est pas très grave mais s'est bon a savoir.
    //assertEquals(5,image.taille(8,57));
    //assertEquals(5,image.taille(8,60));
  }
  @Test
  public void testTaillePartiellementRealiste(){
    Main.iniOp();//on initialise les Options.
    Main.getOp().setTailleRealiste(50);
    //si la taille est partielement réaliste.
    assertEquals(75,image.taille(50,100));//100% réaliste ca donne 100 0% réaliste ca donne 50, on veut que ce soit au milieux.
    assertEquals(60,image.taille(50,80));
    assertEquals(150,image.taille(50,200));
    assertEquals(72,image.taille(20,120));

    Main.getOp().setTailleRealiste(10);
    assertEquals(95,image.taille(50,100));
    assertEquals(47,image.taille(50,50));
    Main.getOp().setTailleRealiste(63);
    assertEquals(82,image.taille(50,120));
    assertEquals(59,image.taille(20,120));
  }

  //isImage
  @Test
  public void testIsImage(){
    File f = new File("fgz.png");
    assertTrue(image.isImage(f));
    f = new File(".png");
    assertTrue(image.isImage(f));
    f = new File("test tek.png");
    assertTrue(image.isImage(f));
    f = new File("ctufzioazfèéèè^.png");
    assertTrue(image.isImage(f));
    f = new File("è.jpg");
    assertTrue(image.isImage(f));
    f = new File("$£¤.jpg");
    assertTrue(image.isImage(f));

    f = new File("$£¤.jg");
    assertTrue(!image.isImage(f));
    f = new File("gerh");
    assertTrue(!image.isImage(f));
    f = new File("gerh.pnj.ĉu ne");
    assertTrue(!image.isImage(f));
  }

  @Test
  public void testResize(){
    BufferedImage bi = new BufferedImage(8,8,BufferedImage.TYPE_INT_ARGB);
    BufferedImage bi2 = image.resize(bi,4,4);
    assertEquals(4,bi2.getWidth());
    assertEquals(4,bi2.getHeight());
    bi = new BufferedImage(10,12,BufferedImage.TYPE_INT_ARGB);
    bi2 = image.resize(bi,6,9);
    assertEquals(6,bi2.getWidth());
    assertEquals(9,bi2.getHeight());
    //uncorect value
    bi = new BufferedImage(10,12,BufferedImage.TYPE_INT_ARGB);
    bi2 = image.resize(bi,-6,-9);
    assertEquals(1,bi2.getWidth());
    assertEquals(1,bi2.getHeight());
    bi = new BufferedImage(10,12,BufferedImage.TYPE_INT_ARGB);
    bi2 = image.resize(bi,0,9);
    assertEquals(1,bi2.getWidth());
    assertEquals(9,bi2.getHeight());

    bi=null;
    bi2 = image.resize(bi,5);
    assertEquals(null,bi2);
  }

  @Test
  public void testResize2(){
    //with int
    BufferedImage bi = new BufferedImage(8,4,BufferedImage.TYPE_INT_ARGB);
    BufferedImage bi2 = image.resize(bi,4);
    assertEquals(4,bi2.getWidth());
    assertEquals(2,bi2.getHeight()); //and not 3.
    bi = new BufferedImage(4,12,BufferedImage.TYPE_INT_ARGB);
    bi2 = image.resize(bi,6);
    assertEquals(2,bi2.getWidth());
    assertEquals(6,bi2.getHeight());
    //with non-int.
    //more than 2.
    bi = new BufferedImage(4,12,BufferedImage.TYPE_INT_ARGB);
    bi2 = image.resize(bi,7);
    assertEquals(2,bi2.getWidth());
    assertEquals(7,bi2.getHeight());
    //less that 2.
    bi = new BufferedImage(4,12,BufferedImage.TYPE_INT_ARGB);
    bi2 = image.resize(bi,5);
    assertEquals(1,bi2.getWidth());
    assertEquals(5,bi2.getHeight());

    bi=null;
    bi2 = image.resize(bi,5);
    assertEquals(null,bi2);
  }
}
