package fr.formiko.usuel.maths;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.tests.TestCaseMuet;

public class mathTest extends TestCaseMuet{

  // FUNCTIONS -----------------------------------------------------------------
  @Test
  public void testMaxInt(){
    assertEquals(8,math.max(1,8));
    assertEquals(1,math.max(1,-9));
    assertEquals(1,math.max(1,1));
  }
  @Test
  public void testMaxInt2(){
    assertEquals(8,math.max(1,8,3));
    assertEquals(1,math.max(1,-9,0,-4,-6774,-9));
    assertEquals(1,math.max(1,1,1,1,1,1,1,1,1));
  }
  @Test
  public void testMaxInt3(){
    int t [] = {1,2,-1,3,8};
    assertEquals(8,math.max(t));
    t = new int[2];
    t[0]=1; t[1]=-0;
    assertEquals(1,math.max(t));
    t = new int[2];
    t[0]=1; t[1]=1;
    assertEquals(1,math.max(t));
  }
  @Test
  public void testMaxIntT(){
    int t [] = {0,5,190,-45};
    assertEquals(math.max(t),190);
    int t2 [] = {-2,-5,-190,-45};
    assertEquals(math.max(t2),-2);
  }
  @Test
  public void testMaxDouble(){
    assertEquals(math.max(1.9,8.2),8.2);
    assertEquals(math.max(1.0,-9.0),1.0);
  }
  @Test
  public void testMaxDoubleT(){
    double t3 [] = {0.0,5.23,-45.34};
    assertEquals(math.max(t3),5.23);
    double t4 [] = {-2.1,-2.100001,-190.190,-45.6};
    assertEquals(math.max(t4),-2.1);
  }

  @Test
  public void testMinInt(){
    assertEquals(math.min(1,8),1);
    assertEquals(math.min(1,-9),-9);
    assertEquals(math.min(1,1),1);
  }
  @Test
  public void testMinIntT(){
    int t [] = {0,5,190,-45};
    assertEquals(math.min(t),-45);
    int t2 [] = {-2,-5,-190,-45};
    assertEquals(math.min(t2),-190);
  }
  @Test
  public void testMinDouble(){
    assertEquals(math.min(1.9,8.2),1.9);
    assertEquals(math.min(1.0,-9.0),-9.0);
  }
  @Test
  public void testMinDoubleT(){
    double t3 [] = {0.0,5.23,-45.34};
    assertEquals(math.min(t3),-45.34);
    double t4 [] = {-2.1,-2.100001,-190.190,-45.6};
    assertEquals(math.min(t4),-190.19);
  }


  @Test
  public void testValAbsInt(){
    assertEquals(6,math.valAbs(-6));
    assertEquals(6,math.valAbs(6));
    assertEquals(0,math.valAbs(0));
  }
  @Test
  public void testValAbsDouble(){
    assertEquals(6.3,math.valAbs(-6.3));
    assertEquals(6.245,math.valAbs(6.245));
    assertEquals(0.0,math.valAbs(0.0));
  }

  @Test
  public void testSommeDe0AXSwitch(){
    assertEquals(1+2+3,math.sommeDe0AXSwitch(3));
    assertEquals(0,math.sommeDe0AXSwitch(-6));
    assertEquals(0,math.sommeDe0AXSwitch(0));
  }
  @Test
  public void testSommeDe0AX(){
    assertEquals(1+2+3,math.sommeDe0AX(3));
    assertEquals(0,math.sommeDe0AX(-6));
    assertEquals(0,math.sommeDe0AX(0));
  }

  @Test
  public void testFactorielle(){
    assertEquals(-1,math.factorielle(-7));
    assertEquals(0,math.factorielle(0));
    assertEquals(1,math.factorielle(1));
    assertEquals(1*2*3*4,math.factorielle(4));
  }

  @Test
  public void testKParmiN(){
    //au bout d'un moment on aura des erreur du a la taille max des long.
    assertEquals(-1,math.kParmiN(-3,6));
    assertEquals(0,math.kParmiN(0,0));
    assertEquals(-1,math.kParmiN(1,0));
    assertEquals(35,math.kParmiN(4,7));
  }
}
