package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.test.TestCaseMuet;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

public class tableauTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testRogner(){
    byte t[][] = new byte[3][3];
    byte k=0;
    for (int i=0;i<t.length ;i++ ) {
      for (int j=0;j<t[0].length ;j++ ) {
        t[i][j]=k;k++;
      }
    }
    /* On travail sur le tableau suivant.
    0 1 2
    3 4 5
    6 7 8
    */
    //sans transformation
    byte t1 [][] = tableau.rogner(t,0,0,0,0);
    byte liste [] = new byte[9];
    for (int i=0;i<9 ;i++ ) { liste[i]=(byte)i;}
    assertTrue(tableau.contientLesElementDeX(t1,liste));
    //la 1a ligne
    t1 = tableau.rogner(t,1,0,0,0);
    liste = new byte[6];
    for (int i=3;i<9 ;i++ ) { liste[i-3]=(byte)i;}
    assertTrue(tableau.contientLesElementDeX(t1,liste));
    //les 2 1a ligne
    t1 = tableau.rogner(t,2,0,0,0);
    liste = new byte[3];
    for (int i=6;i<9 ;i++ ) { liste[i-6]=(byte)i;}
    assertTrue(tableau.contientLesElementDeX(t1,liste));
    //les 3 1a ligne
    t1 = tableau.rogner(t,3,0,0,0);
    assertTrue(t1!=null);
    assertTrue(t1.length==0);
    //les 2 1a ligne et la denière
    t1 = tableau.rogner(t,3,0,0,0);
    assertTrue(t1!=null);
    assertTrue(t1.length==0);
    //la 1a ligne et la dernière
    t1 = tableau.rogner(t,1,0,1,0);
    liste = new byte[3];
    for (int i=3;i<6 ;i++ ) { liste[i-3]=(byte)i;}
    assertTrue(tableau.contientLesElementDeX(t1,liste));
    //b
    t1 = tableau.rogner(t,0,1,0,0);
    liste = new byte[6];
    liste[0]=1;liste[1]=2;liste[2]=4;liste[3]=5;liste[4]=7;liste[5]=8;
    assertTrue(tableau.contientLesElementDeX(t1,liste));
    //d
    t1 = tableau.rogner(t,0,0,0,1);
    liste = new byte[6];
    liste[0]=1;liste[1]=0;liste[2]=4;liste[3]=3;liste[4]=7;liste[5]=6;
    assertTrue(tableau.contientLesElementDeX(t1,liste));
    //
    t1 = tableau.rogner(t,1,1,0,0);
    liste = new byte[4];
    liste[0]=4;liste[1]=5;liste[2]=7;liste[3]=8;
    assertTrue(tableau.contientLesElementDeX(t1,liste));
    //
    t1 = tableau.rogner(t,1,1,1,0);
    liste = new byte[2];
    liste[0]=4;liste[1]=5;
    assertTrue(tableau.contientLesElementDeX(t1,liste));
    //
    t1 = tableau.rogner(t,1,0,1,1);
    liste = new byte[2];
    liste[0]=4;liste[1]=3;
    assertTrue(tableau.contientLesElementDeX(t1,liste));
    //
    t1 = tableau.rogner(t,1,0,1,2);
    liste = new byte[1];
    liste[0]=3;
    assertTrue(tableau.contientLesElementDeX(t1,liste));
    //
    t1 = tableau.rogner(t,1,1,1,1);
    liste = new byte[1];
    liste[0]=4;
    assertTrue(tableau.contientLesElementDeX(t1,liste));
  }
  @Before
  public void testContientLesElementDeX(){
    byte liste [] = {1,5,-7};
    byte t [][] =  new byte[2][2];
    t[0][0]=1;t[0][1]=-7;
    t[1][0]=6;t[1][1]=5;
    assertTrue(tableau.contientLesElementDeX(t,liste));
    t[1][1]=1;
    assertTrue(!tableau.contientLesElementDeX(t,liste));
  }
  public void testContient(){
    byte t[]= new byte[2]; t[0]=1; t[1]=-7;
    assertTrue(tableau.contient(t,(byte)1));
    assertTrue(tableau.contient(t,(byte)-7));
    assertTrue(!tableau.contient(t,(byte)7));
    assertTrue(!tableau.contient(t,(byte)0));
  }
  public void testContient2(){
    byte t[][]= new byte[2][2];
    t[0][0]=1;t[0][1]=7;
    t[1][0]=6;t[1][1]=5;
    assertTrue(tableau.contient(t,(byte)1));
    assertTrue(tableau.contient(t,(byte)7));
    assertTrue(tableau.contient(t,(byte)6));
    assertTrue(tableau.contient(t,(byte)5));
    assertTrue(!tableau.contient(t,(byte)-7));
    assertTrue(!tableau.contient(t,(byte)0));
    assertTrue(!tableau.contient(t,(byte)-5));
    assertTrue(!tableau.contient(t,(byte)37));
  }
  public void testEquals(){
    byte t [] = new byte[3];t[0]=0;t[1]=1;t[2]=-89;
    byte t2 [] = new byte[3];t2[0]=0;t2[1]=1;t2[2]=-89;
    assertTrue(tableau.equals(t,t2));
    t2[0]=1;
    assertTrue(!tableau.equals(t,t2));
    t2[0]=0;
    assertTrue(tableau.equals(t,t2));
  }
  public void testEquals2(){
    byte t [][] = new byte[2][2];
    t[0][0]=0;t[0][1]=1;
    t[1][0]=-1;t[1][1]=4;
    byte t2 [][] = new byte[2][2];
    t2[0][0]=0;t2[0][1]=1;
    t2[1][0]=-1;t2[1][1]=4;
    byte t3 [][] = new byte[3][2];
    t3[0][0]=0;t3[0][1]=1;
    t3[1][0]=-1;t3[1][1]=4;
    assertTrue(tableau.equals(t,t2));
    assertTrue(!tableau.equals(t,t3));
    t2[1][1]=0;
    assertTrue(!tableau.equals(t,t2));

  }
  public void testCopier(){
    byte t [] = new byte[2];
    t[0]=0;t[1]=1;
    byte t2 []= tableau.copier(t);
    assertTrue(!t.equals(t2));
    assertTrue(tableau.equals(t,t2));
    byte t3 []= new byte[1];
    assertTrue(!t.equals(t3));
    assertTrue(!tableau.equals(t,t3));
    byte t4 []= new byte[2]; t4[0]=0;t4[1]=1;
    assertTrue(!t.equals(t4));
    assertTrue(tableau.equals(t,t4));
    t4[1]=2;
    assertTrue(!t.equals(t4));
    assertTrue(!tableau.equals(t,t4));
  }
}
