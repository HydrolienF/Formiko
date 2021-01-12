package fr.formiko.usuel.image;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.image.Img;
import fr.formiko.usuel.test.TestCaseMuet;
import org.junit.Test;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.image.Pixel;

public class ImgTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  //equals() work if tableau.equals(t,t2) work.
  @Test
  public void testClone(){
    Img img = new Img(3,3);
    byte t [][] = new byte[3][3];
    int j=0;
    for (byte t2[] : t) {
      for (int i=0;i<t2.length ;i++ ) {
        t2[i]=(byte)(i+j);
      } j+=3;
    }
    img.setAlpha(t);//on rempli alpha rouge, vert, bleu avec 4 tableau.
    img.setRouge(tableau.copier(t));
    img.setVert(tableau.copier(t));
    img.setBleu(tableau.copier(t));
    assertTrue(img.iniWH());
    Img img2 = img.clone();
    assertTrue(img.equals(img2));
    img2.setAlpha(2,2,(byte)-5);
    assertTrue(!img.equals(img2));
  }
  @Test
  public void testTourner(){
    Img img = new Img(3,3);
    byte t [][] = new byte[3][3];
    int j=0;
    for (byte t2[] : t) {
      for (int i=0;i<t2.length ;i++ ) {
        t2[i]=(byte)(i+j);
      } j+=3;
    }
    /*
    0 1 2
    3 4 5
    6 7 8
    */
    img.setAlpha(t);//on rempli alpha rouge, vert, bleu avec 4 tableau.
    img.setRouge(tableau.copier(t));
    img.setVert(tableau.copier(t));
    img.setBleu(tableau.copier(t));
    //tourner pour de faut.
    Img img2 = img.clone();
    img2.tourner(0);
    assertTrue(tableau.equals(img.getAlpha(),img2.getAlpha()));
    img2 = img.clone();
    img2.tourner(4);
    assertTrue(tableau.equals(img.getAlpha(),img2.getAlpha()));
    //tourner de 90°
    Img img3 = img.clone();
    img3.tourner(1);
    byte t3 [][] = new byte[3][3];
    t3[0][0]=2;t3[0][1]=5;t3[0][2]=8;
    t3[1][0]=1;t3[1][1]=4;t3[1][2]=7;
    t3[2][0]=0;t3[2][1]=3;t3[2][2]=6;
    assertTrue(tableau.equals(t3,img3.getAlpha()));
    //tourner de 180°
    Img img4 = img.clone();
    img4.tourner(2);
    img3.tourner(1);
    byte t4 [][] = new byte[3][3];
    t4[0][0]=8;t4[0][1]=7;t4[0][2]=6;
    t4[1][0]=5;t4[1][1]=4;t4[1][2]=3;
    t4[2][0]=2;t4[2][1]=1;t4[2][2]=0;
    assertTrue(tableau.equals(t4,img4.getAlpha()));
    assertTrue(tableau.equals(t4,img3.getAlpha()));
    //tourner de -90°
    Img img5 = img.clone();
    img5.tourner(3);
    img3.tourner(1);
    t[0][0]=6;t[0][1]=3;t[0][2]=0;
    t[1][0]=7;t[1][1]=4;t[1][2]=1;
    t[2][0]=8;t[2][1]=5;t[2][2]=2;
    assertTrue(tableau.equals(t,img5.getAlpha()));
    assertTrue(tableau.equals(t,img3.getAlpha()));
  }
  @Test
  public void testTourner2(){
    Img img = new Img(2,3);
    byte t [][] = new byte[2][3];
    int j=0;
    for (byte t2[] : t) {
      for (int i=0;i<t2.length ;i++ ) {
        t2[i]=(byte)(i+j);
      } j+=3;
    }
    /*
    0 1 2
    3 4 5
    */
    img.setAlpha(t);//on rempli alpha rouge, vert, bleu avec 4 tableau.
    img.setRouge(tableau.copier(t));
    img.setVert(tableau.copier(t));
    img.setBleu(tableau.copier(t));
    //tourner pour de faut.
    Img img2 = img.clone();
    img2.tourner(0);
    assertTrue(tableau.equals(img.getAlpha(),img2.getAlpha()));
    img2 = img.clone();
    img2.tourner(4);
    assertTrue(tableau.equals(img.getAlpha(),img2.getAlpha()));
    //tourner de 90°
    Img img3 = img.clone();
    img3.tourner(1);
    byte t3 [][] = new byte[3][2];
    t3[0][0]=2;t3[0][1]=5;
    t3[1][0]=1;t3[1][1]=4;
    t3[2][0]=0;t3[2][1]=3;
    assertTrue(tableau.equals(t3,img3.getAlpha()));
    //tourner de 180°
    Img img4 = img.clone();
    img4.tourner(2);
    img3.tourner(1);
    byte t4 [][] = new byte[2][3];
    t4[0][0]=5;t4[0][1]=4;t4[0][2]=3;
    t4[1][0]=2;t4[1][1]=1;t4[1][2]=0;
    assertTrue(tableau.equals(t4,img4.getAlpha()));
    assertTrue(tableau.equals(t4,img3.getAlpha()));
    //tourner de -90°
    Img img5 = img.clone();
    img5.tourner(3);
    img3.tourner(1);
    byte t5 [][] = new byte[3][2];
    t5[0][0]=3;t5[0][1]=0;
    t5[1][0]=4;t5[1][1]=1;
    t5[2][0]=5;t5[2][1]=2;
    assertTrue(tableau.equals(t5,img5.getAlpha()));
    assertTrue(tableau.equals(t5,img3.getAlpha()));
  }
  @Test
  public void testCompterBordTransparent(){
    //image de 3 sur 3 pixel avec 1 pixel coloré au centre :
    Img img = new Img(3,3);
    byte t [][] = new byte[3][3];
    for (byte t2[] : t) {
      for (int i=0;i<t2.length ;i++ ) {
        t2[i]=-128;
      }
    }
    img.setAlpha(t);
    int rs [] = new int [4];
    rs[0]=3;rs[1]=3;rs[2]=3;rs[3]=3;
    int tTest [] = img.compterBordTransparent();
    assertTrue(tableau.equals(rs,tTest));
    t[1][1]=-100;//plus transparent
    rs[0]=1;rs[1]=1;rs[2]=1;rs[3]=1;
    tTest = img.compterBordTransparent();
    assertTrue(tableau.equals(rs,tTest));
    //un autre pixel transparent
    t[1][1]=-128;//plus transparent
    t[2][0]=30;
    rs[0]=2;rs[1]=0;rs[2]=0;rs[3]=2;
    tTest = img.compterBordTransparent();
    assertTrue(tableau.equals(rs,tTest));
    //2 pixel qui fond la ligne du bas.
    t[2][2]=0;
    rs[0]=2;rs[1]=0;rs[2]=0;rs[3]=0;
    tTest = img.compterBordTransparent();
    assertTrue(tableau.equals(rs,tTest));
    //2 pixel qui fond toute l'image
    for (byte t2[] : t) {
      for (int i=0;i<t2.length ;i++ ) {
        t2[i]=-128;
      }
    }
    t[0][2]=0;
    t[2][0]=0;
    rs[0]=0;rs[1]=0;rs[2]=0;rs[3]=0;
    tTest = img.compterBordTransparent();
    assertTrue(tableau.equals(rs,tTest));
  }
  public void testCompterBordTransparent2(){
    //image de 3 sur 4 pixel avec 1 pixel coloré au centre :
    Img img = new Img(3,4);
    byte t [][] = new byte[3][4];
    for (byte t2[] : t) {
      for (int i=0;i<t2.length ;i++ ) {
        t2[i]=-128;
      }
    }
    int tTest [] = null;
    int rs [] = new int [4];
    t[0][2]=0;
    t[2][0]=0;
    rs[0]=2;rs[1]=0;rs[2]=0;rs[3]=1;
    //TODO s'arranger pour pouvoir tourner une image devrais changer ca.
    //tTest = img.compterBordTransparent();
    //assertTrue(tableau.equals(rs,tTest));
  }
  public void testCompterPixel(){
    Img img = new Img(2,2);
    Pixel p=new Pixel(128,128,128,128);
    assertEquals(4,img.compterPixel(p));
    p=new Pixel(128,128,128,127);
    assertEquals(0,img.compterPixel(p));
    p=new Pixel(0,128,128,128);
    assertEquals(0,img.compterPixel(p));
    byte tb [][] = new byte[2][2];
    tb[0][0]=(byte)1;tb[0][1]=(byte)1;tb[1][0]=(byte)1;tb[1][1]=(byte)2;
    img.setRouge(tb);
    p=new Pixel(129,128,128,128);
    assertEquals(3,img.compterPixel(p));
    p=new Pixel(130,128,128,128);
    assertEquals(1,img.compterPixel(p));
  }
  public void testCompterChaquePixel(){

  }
}
