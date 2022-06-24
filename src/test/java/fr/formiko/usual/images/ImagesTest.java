package fr.formiko.usual.images;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usual.Folder;
import fr.formiko.usual.ProgressionNull;
import fr.formiko.usual.fichier;
import fr.formiko.usual.images.Images;
import fr.formiko.usual.images.Img;
import fr.formiko.usual.maths.allea;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImagesTest extends TestCaseMuet {

  // FUNCTIONS -----------------------------------------------------------------
  @BeforeAll
  public static void iniMain(){
    // new imageTest();
    // Main.ini();
    Folder.setFolder(new Folder(new ProgressionNull()));
  }
  private void ini(){
    Folder folder = new Folder(new ProgressionNull());
    folder.ini();
    Folder.setFolder(folder);
  }
  @Test
  public void testReadImage(){
    File f = new File(Folder.getFolder().getFolderStable()+Folder.getFolder().getFolderImages()+"null.png");
    Image iNull = Images.readImage(f);
    assertTrue(iNull!=null);
    assertTrue(f.isFile());//il n'as pas été supprimé lors de la lecture

    //répertoire d'image.
    Folder folder = new Folder(new ProgressionNull());
    folder.ini(false);
    Folder.setFolder(folder);
    int x = allea.getAllea(1000)+100;
    Img img = new Img(1,1);
    img.sauvegarder("testImage"+x+".png");
    f = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages()+"testImage"+x+".png");
    Image i = Images.readImage(f);
    assertTrue(i!=null);
    assertTrue(f.isFile());//il n'as pas été supprimé lors de la lecture
    assertTrue(f.delete());//il ce supprime bien.

    //répertoire courant.
    x = allea.getAllea(1000)+100;
    img = new Img(1,1);
    img.sauvegarder("./","testImage"+x+".png");
    f = new File("testImage"+x+".png");
    i = Images.readImage(f);
    assertTrue(i!=null);
    assertTrue(f.isFile());//il n'as pas été supprimé lors de la lecture
    assertTrue(f.delete());//il ce supprime bien.

    x = allea.getAllea(1000)+100;
    img.sauvegarder("./","testImage"+x+".pkg");//nom d'image incorecte.
    f = new File("testImage"+x+".pkg");
    Image i2 = Images.readImage(f);
    assertTrue(i2==null);
    assertTrue(f.isFile());//il n'as pas été supprimé lors de la lecture
    assertTrue(f.delete());//il ce supprime bien.

    x = allea.getAllea(1000)+100;
    img.sauvegarder("testImage"+x+".jpg");//nom d'image incorecte.
    f = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages()+"testImage"+x+".jpg");

    Image i3 = Images.readImage(f);
    assertTrue(i3!=null);
    assertTrue(f.isFile());//il n'as pas été supprimé lors de la lecture
    assertTrue(f.delete());//il ce supprime bien.
  }
  //getImage
  @Test
  public void testGetImage(){
    ini();
    Image nul = Images.getImage("null");
    Image i0 = Images.getImage("vbfizefzeg.png");
    //faute de savoir comment comparé les images sans prendre en compte meur adresse mémoire on supose que la taille est assez propre a chaque image.
    assertTrue(i0!=null);
    assertEquals(nul.getWidth(null),i0.getWidth(null));
    Image i1 = Images.getImage("b0.png");
    Image i2 = Images.getImage("b0");
    Image i3 = Images.getImage("b0",Folder.getFolder().getFolderStable()+Folder.getFolder().getFolderImages());//si on cherche dans le bon répertoire.
    Image i4 = Images.getImage("b0","./"); //si on cherche dans un mauvais répertoire.
    assertEquals(i1.getWidth(null),i2.getWidth(null));
    assertEquals(i1.getWidth(null),i3.getWidth(null));
    assertEquals(null,i4);
  }

  //getImages
  @Test
  public void testGetImages(){
    Folder folder = new Folder(new ProgressionNull());
    folder.setFolderMain("imassertTrue(fichier.deleteDirectory(new File(folder.getFolderMain().substring(0,folder.getFolderMain().length()-5))));lsSPEC"+getId());
    folder.ini(false);
    Folder.setFolder(folder);
    Img img = new Img(1,1);
    img.sauvegarder(folder.getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+0+".png");
    img.sauvegarder(folder.getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+1+".png");
    img.sauvegarder(folder.getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+2+".png");
    assertEquals(3,Images.getImages("testImage").length);
    assertEquals(0,Images.getImages("testImag").length);
    assertEquals(0,Images.getImages("testImage.png").length);
    // image.setREPTEXTUREPACK("./");
    // assertEquals(3,Images.getImages("testDirIT/testImage").length);
    // assertEquals(0,Images.getImages("testImage").length);
    // image.setREPTEXTUREPACK(null);
    // assertEquals(0,Images.getImages("testDirIT/testImage").length);
    // assertEquals(0,Images.getImages("testImage").length);
    //assertEquals(3,Images.getImages("testDirIT/","testImage").length);
    //assertEquals(3,Images.getImages("testDirIT","testImage").length);
    //assertEquals(0,Images.getImages("dirX","testImage").length);
    assertTrue(fichier.deleteDirectory(new File(folder.getFolderMain().substring(0,folder.getFolderMain().length()-5))));
  }

  //getImagess
  //not used anymore.
  // @Test
  // public void testGetImagess(){
  //   Main.iniOp();
  //   Main.getOp().setOrientedObjectOnMap(true);
  //   Folder folder = new Folder(new ProgressionNull());
  //   folder.setFolderMain("testDirIT"+getId());
  //   folder.ini(false);
  //   Folder.setFolder(folder);
  //   Img img = new Img(3,1);
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+0+""+".png");
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+1+""+".png");
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+2+""+".png");
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+0+"h"+".png");
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+1+"h"+".png");
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+2+"h"+".png");
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+0+"d"+".png");
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+1+"d"+".png");
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+2+"d"+".png");
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+0+"b"+".png");
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+1+"b"+".png");
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+2+"b"+".png");
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+0+"g"+".png");
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+1+"g"+".png");
  //   img.sauvegarder(Folder.getFolder().getFolderResourcesPacks()+folder.getFolderImages(),"testImage"+2+"g"+".png");
  //   // image.setREPTEXTUREPACK("testDirIT2/");
  //   Image [][] t = Images.getImagess("testImage");
  //   int k=0;
  //   for(Image ti []: t){
  //     for (Image i : ti) {
  //       assertEquals(3,i.getWidth(null));
  //       assertEquals(1,i.getHeight(null));
  //       k++;
  //     }
  //   }
  //   assertEquals(12,k);
  //   Main.getOp().setOrientedObjectOnMap(false);
  //   t = Images.getImagess("testImage");
  //   k=0;
  //   for(Image ti []: t){
  //     if(t!=null){
  //       try { // on ne peu lire que la 1a colone.
  //         for (Image i : ti) {
  //           if(i!=null){
  //             assertEquals(3,i.getWidth(null));
  //             assertEquals(1,i.getHeight(null));
  //             k++;
  //           }
  //         }
  //       }catch (Exception e) {}
  //     }
  //   }
  //   assertEquals(3,k);
  //   image.setREPTEXTUREPACK(null);
  //   assertTrue(fichier.deleteDirectory(new File(folder.getFolderMain().substring(0,folder.getFolderMain().length()-5))));
  // }

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
    assertEquals(3,Images.getNbrImages("x",rep));
    //chercher "x" a partir de 4 doit donner 4.
    assertEquals(5,Images.getNbrImages("x",rep, (byte) 4));
    //chercher "x" a partir de -1 doit donner 0.
    assertEquals(0,Images.getNbrImages("x",rep,(byte) -1));
    //créer x-1.png
    try {
      f = new File(rep+"x-1.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    //chercher "x" a partir de -1 doit donner 2.
    assertEquals(3,Images.getNbrImages("x",rep,(byte) -1));
    //chercher "y" a partir de 0 ou 1 ou 10 doit donner 0.
    assertEquals(0,Images.getNbrImages("y",rep,(byte) -1));
    assertEquals(0,Images.getNbrImages("y",rep,(byte) 0));
    assertEquals(0,Images.getNbrImages("y",rep,(byte) 1));
    assertEquals(0,Images.getNbrImages("y",rep,(byte) 10));
    assertEquals(0,Images.getNbrImages("y",rep));
    assertEquals(0,Images.getNbrImages("yvub",rep,(byte) -1));
    assertEquals(0,Images.getNbrImages("padix",rep,(byte) 10));
    //après les test on supprime le répertoire.
    f = new File(rep);
    assertTrue(fichier.deleteDirectory(f));
  }
  @Test
  public void testGetNbrImagesREP3(){
    //on vérifie que d'éventuelles images complémentaire présente dans REPTEXTUREPACK seront bien chargée.
    Folder folder = new Folder(new ProgressionNull());
    folder.setFolderMain("testDirIT"+getId());
    folder.ini(false);
    Folder.setFolder(folder);
    File f = new File(Images.getREPTEXTUREPACK());
    fichier.deleteDirectory(f);
    assertTrue(f.mkdirs());
    try {
      f = new File(Images.getREPTEXTUREPACK()+"x0.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
      f = new File(Images.getREPTEXTUREPACK()+"x1.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
      f = new File(Images.getREPTEXTUREPACK()+"x2.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
      f = new File(Images.getREPTEXTUREPACK()+"x4.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
      f = new File(Images.getREPTEXTUREPACK()+"fourmi0.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
      f = new File(Images.getREPTEXTUREPACK()+"fourmi1.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
      f = new File(Images.getREPTEXTUREPACK()+"fourmi2.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
      f = new File(Images.getREPTEXTUREPACK()+"fourmi3.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    assertEquals(3,Images.getNbrImages("x"));
    assertEquals(4,Images.getNbrImages("fourmi"));
    try {
      f = new File(Images.getREPTEXTUREPACK()+"fourmi4.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    assertEquals(5,Images.getNbrImages("fourmi"));
    f = new File(Images.getREPTEXTUREPACK());
    assertTrue(fichier.deleteDirectory(new File(folder.getFolderMain().substring(0,folder.getFolderMain().length()-5))));
  }

  //clearTemporaire - clearPartielTemporaire
  @Test
  public void testClearTemporaire(){
    File f = null;
    Folder folder = new Folder(new ProgressionNull());
    folder.setFolderMain("imageTestFodler"+getId());
    folder.ini(false);
    Folder.setFolder(folder);
    File d = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages());
    assertTrue(d.exists());
    assertTrue(d.isDirectory());
    Images.clearTemporaire();//on vire déja toutes les images du fichier temporaire.
    int x = 0;//d.listFiles().length;
    try {
      f = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages()+"egfeFzg.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    Images.clearTemporaire();
    assertEquals(x,d.listFiles().length);
    assertTrue(!f.delete());
    try {
      f = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages()+"egfeFzg.p"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    Images.clearTemporaire();
    assertEquals(x+1,d.listFiles().length);
    assertTrue(f.delete());
    try {
      f = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages()+"a"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    Images.clearTemporaire();
    assertEquals(x+1,d.listFiles().length);
    assertTrue(f.delete());
    try {
      f = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages()+".jpg"); assertTrue(f.createNewFile());assertTrue(f.exists());
    }catch (Exception e) {assertTrue(false);}
    Images.clearTemporaire();
    assertEquals(x,d.listFiles().length);
    assertTrue(!f.delete());
    assertTrue(fichier.deleteDirectory(new File(folder.getFolderMain().substring(0,folder.getFolderMain().length()-5))));
  }
  // @Test
  // public void testClearPartielTemporaire(){
  //   Images.clearPartielTemporaire();
  //   File f = null;
  //   File d = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages());
  //   int x = d.listFiles().length;
  //   // ...F...
  //   try {
  //     f = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages()+"egfeFzig.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
  //   }catch (Exception e) {assertTrue(false);}
  //   Images.clearPartielTemporaire();
  //   assertEquals(x+1,d.listFiles().length);
  //   assertTrue(f.delete());
  //   //f...
  //   try {
  //     f = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages()+"fegfezig.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
  //   }catch (Exception e) {assertTrue(false);}
  //   Images.clearPartielTemporaire();
  //   assertEquals(x+1,d.listFiles().length);
  //   assertTrue(f.delete());
  //   //F...
  //   try {
  //     f = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages()+"Fvi.png"); assertTrue(f.createNewFile());assertTrue(f.exists());
  //   }catch (Exception e) {assertTrue(false);}
  //   Images.clearPartielTemporaire();
    //TODO #248 will be remove soon
    // assertEquals(x,d.listFiles().length);
    // assertTrue(!f.delete());
    // //F...
    // try {
    //   f = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages()+"F.jpg"); assertTrue(f.createNewFile());assertTrue(f.exists());
    // }catch (Exception e) {assertTrue(false);}
    // Images.clearPartielTemporaire();
    // // assertEquals(x,d.listFiles().length);
    // assertTrue(!f.delete());
    // //... . ...
    // try {
    //   f = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages()+"vehti.hze"); assertTrue(f.createNewFile());assertTrue(f.exists());
    // }catch (Exception e) {assertTrue(false);}
    // Images.clearPartielTemporaire();
    // // assertEquals(x+1,d.listFiles().length);
    // assertTrue(f.delete());
    // //F... . ...
    // try {
    //   f = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages()+"Fbn.hze"); assertTrue(f.createNewFile());assertTrue(f.exists());
    // }catch (Exception e) {assertTrue(false);}
    // Images.clearPartielTemporaire();
    // // assertEquals(x+1,d.listFiles().length);
    // assertTrue(f.delete());
    // //F . ...
    // try {
    //   f = new File(Folder.getFolder().getFolderTemporary()+Folder.getFolder().getFolderImages()+"F.hze"); assertTrue(f.createNewFile());assertTrue(f.exists());
    // }catch (Exception e) {assertTrue(false);}
    // Images.clearPartielTemporaire();
    // // assertEquals(x+1,d.listFiles().length);
  //   f.delete();
  // }


  //isImage
  @Test
  public void testIsImage(){
    File f = new File("fgz.png");
    assertTrue(Images.isImage(f));
    f = new File(".png");
    assertTrue(Images.isImage(f));
    f = new File("test tek.png");
    assertTrue(Images.isImage(f));
    f = new File("ctufzioazfèéèè^.png");
    assertTrue(Images.isImage(f));
    f = new File("è.jpg");
    assertTrue(Images.isImage(f));
    f = new File("$£¤.jpg");
    assertTrue(Images.isImage(f));

    f = new File("$£¤.jg");
    assertTrue(!Images.isImage(f));
    f = new File("gerh");
    assertTrue(!Images.isImage(f));
    f = new File("gerh.pnj.ĉu ne");
    assertTrue(!Images.isImage(f));
  }

  @Test
  public void testResize(){
    BufferedImage bi = new BufferedImage(8,8,BufferedImage.TYPE_INT_ARGB);
    BufferedImage bi2 = Images.resize(bi,4,4);
    assertEquals(4,bi2.getWidth());
    assertEquals(4,bi2.getHeight());
    bi = new BufferedImage(10,12,BufferedImage.TYPE_INT_ARGB);
    bi2 = Images.resize(bi,6,9);
    assertEquals(6,bi2.getWidth());
    assertEquals(9,bi2.getHeight());
    //uncorect value
    bi = new BufferedImage(10,12,BufferedImage.TYPE_INT_ARGB);
    bi2 = Images.resize(bi,-6,-9);
    assertEquals(1,bi2.getWidth());
    assertEquals(1,bi2.getHeight());
    bi = new BufferedImage(10,12,BufferedImage.TYPE_INT_ARGB);
    bi2 = Images.resize(bi,0,9);
    assertEquals(1,bi2.getWidth());
    assertEquals(9,bi2.getHeight());

    bi=null;
    bi2 = Images.resize(bi,5);
    assertEquals(null,bi2);
  }

  @Test
  public void testResize2(){
    //with int
    BufferedImage bi = new BufferedImage(8,4,BufferedImage.TYPE_INT_ARGB);
    BufferedImage bi2 = Images.resize(bi,4);
    assertEquals(4,bi2.getWidth());
    assertEquals(2,bi2.getHeight()); //and not 3.
    bi = new BufferedImage(4,12,BufferedImage.TYPE_INT_ARGB);
    bi2 = Images.resize(bi,6);
    assertEquals(2,bi2.getWidth());
    assertEquals(6,bi2.getHeight());
    //with non-int.
    //more than 2.
    bi = new BufferedImage(4,12,BufferedImage.TYPE_INT_ARGB);
    bi2 = Images.resize(bi,7);
    assertEquals(2,bi2.getWidth());
    assertEquals(7,bi2.getHeight());
    //less that 2.
    bi = new BufferedImage(4,12,BufferedImage.TYPE_INT_ARGB);
    bi2 = Images.resize(bi,5);
    assertEquals(1,bi2.getWidth());
    assertEquals(5,bi2.getHeight());

    bi=null;
    bi2 = Images.resize(bi,5);
    assertEquals(null,bi2);
  }
  @Test
  public void countTransparentBordersTest(){
    Color colFull = new Color(25,255,255,255);
    Color colEmpty = new Color(0,0,0,0);
    int t[]={0,0,0,0};
    assertArrayEquals(t, Images.countTransparentBorders(null));
    BufferedImage bi = new BufferedImage(3, 4, BufferedImage.TYPE_INT_ARGB);
    Graphics g = bi.getGraphics();
    g.setColor(colFull);
    g.fillRect(0,0,3,4);
    assertArrayEquals(t, Images.countTransparentBorders(bi));

    Images.replaceRectColor(bi,colEmpty,0,0,1,1);
    assertArrayEquals(t, Images.countTransparentBorders(bi));

    Images.replaceRectColor(bi,colEmpty,0,0,1,4);
    int t2[]={1,0,0,0};
    assertArrayEquals(t2, Images.countTransparentBorders(bi));

    g.setColor(colEmpty);
    Images.replaceRectColor(bi,colEmpty,0,0,3,4);
    int t3[]={3,4,3,4};
    assertArrayEquals(t3, Images.countTransparentBorders(bi));
  }

  @Test
  public void countTransparentBordersTest2(){
    Color colFull = new Color(25,255,255,255);
    Color colEmpty = new Color(0,0,0,0);
    int t[]={0,0,0,0};
    assertArrayEquals(t, Images.countTransparentBorders(null));
    BufferedImage bi = new BufferedImage(3, 4, BufferedImage.TYPE_INT_ARGB);
    Graphics g = bi.getGraphics();
    g.setColor(colFull);
    g.fillRect(0,0,3,4);

    Images.replaceRectColor(bi,colEmpty,1,0,1,4);
    int t2[]={0,0,0,0};
    assertArrayEquals(t2, Images.countTransparentBorders(bi));

    g.fillRect(0,0,3,4);
    Images.replaceRectColor(bi,colEmpty,2,0,1,4);
    Images.replaceRectColor(bi,colEmpty,0,0,1,3); // should not change anything
    int t3[]={0,0,1,0};
    assertArrayEquals(t3, Images.countTransparentBorders(bi));

    g.fillRect(0,0,3,4);
    Images.replaceRectColor(bi,colEmpty,2,0,1,4);
    Images.replaceRectColor(bi,colEmpty,0,0,1,3);
    Images.replaceRectColor(bi,colEmpty,1,0,1,1);
    int t4[]={0,1,1,0};
    assertArrayEquals(t4, Images.countTransparentBorders(bi));

    g.fillRect(0,0,3,4);
    Images.replaceRectColor(bi,colEmpty,2,0,1,4);
    Images.replaceRectColor(bi,colEmpty,0,0,1,3);
    Images.replaceRectColor(bi,colEmpty,1,0,1,1);
    Images.replaceRectColor(bi,colEmpty,1,2,1,1);
    int t5[]={0,1,1,0};
    assertArrayEquals(t5, Images.countTransparentBorders(bi));
  }



  @AfterAll
  public static void clean(){
    Folder folder = new Folder(new ProgressionNull());
    Folder.setFolder(folder);
    fichier.deleteDirectory("null");
  }
}
