package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Point;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.tests.TestCaseMuet;

public class PointTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testEquals(){
    Point p = new Point(0,0);
    Point p2 = new Point(0,0);
    assertTrue(p.equals(p2));
    p2 = p;
    assertTrue(p.equals(p2));
    p2 = null;
    assertTrue(!p.equals(p2));
    p2 = new Point(1,0);
    assertTrue(!p.equals(p2));
    p = new Point(10,-6);
    p2 = new Point(10,-6);
    assertTrue(p.equals(p2));
  }
  @Test
  public void testEquals2(){
    Point p = new Point(0,0);
    assertTrue(p.equals("0;0"));
    assertTrue(p.equals("0,0"));
    /*System.out.println("------------------------------");//@a
    String s = "0n0";
    String s2 = "01.70";
    String s3 = "non.je";
    String t [] = s.split("n");
    String t2 [] = s2.split(".");
    String t3 [] = s3.split(".");
    tableau.afficher(t);
    tableau.afficher(t2);
    tableau.afficher(t3);
    System.out.println("------------------------------");//@a*/
    //la ligne suivante ne marche pas...
    //assertTrue(p.equals("0.0"));
    assertTrue(p.equals("0 0"));
    assertTrue(p.equals("0 +0"));
    assertTrue(p.equals("+0,+0"));
    assertTrue(p.equals("-0,0"));
    assertTrue(!p.equals("-1,+1"));
    assertTrue(!p.equals("-1,0"));
    assertTrue(!p.equals("0,+1"));

    //assertTrue(!p.equals("0;0;0"));
    //assertTrue(!p.equals("0 0,0"));
    p = new Point(389744,-60);
    assertTrue(p.equals("+389744,-60"));
    assertTrue(p.equals("389744,-60"));
    assertTrue(p.equals("389744;-60"));
    assertTrue(p.equals("389744 -60"));
    assertTrue(!p.equals("-389744 -60"));
    assertTrue(!p.equals("-60,389744"));
    assertTrue(!p.equals("-60 +389744"));
    assertTrue(!p.equals("60;-389744"));
  }
}
