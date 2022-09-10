package fr.formiko.views.gui2d;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import fr.formiko.formiko.*;
import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usual.structures.listes.Liste;
import fr.formiko.views.gui2d.FPanelCarte;

public class FPanelCarteTest extends TestCaseMuet{
  private Joueur j;
  private void ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GSquare(1,2),0,0,1,false,false),1);
    Main.setPartie(p);
    // p.setAppartionInsecte(false);
    // p.setAppartionGraine(false);
    j = new Joueur(new Fourmiliere(p.getGc().getCSquare(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j);
    // assertTrue(p.getGc().getCSquare(0,0).getContent().getFere().equals(j.getFere()));
    // assertTrue(p.getGc().getCSquare(0,1).getContent().getFere()==null);
    // p.getGj().add(j);
    // return f;
  }

  @Test
  public void testNbrPrintableSquare(){
    FPanelCarte pc = new FPanelCarte();
    pc.setBounds(0,0,1000,200);
    Main.setData(new Data());
    Main.getData().setTailleDUneSquare(100);
    assertEquals(11,pc.nbrPrintableSquare(true));
    assertEquals(3,pc.nbrPrintableSquare(false));
    pc.setBounds(0,0,999,196);
    assertEquals(10,pc.nbrPrintableSquare(true));
    assertEquals(2,pc.nbrPrintableSquare(false));
    pc.setBounds(0,0,999,1096);
    assertEquals(10,pc.nbrPrintableSquare(true));
    assertEquals(11,pc.nbrPrintableSquare(false));

    Main.getData().setTailleDUneSquare(12);
    pc.setBounds(0,0,999,700);
    assertEquals((999/12)+1,pc.nbrPrintableSquare(true));
    assertEquals((700/12)+1,pc.nbrPrintableSquare(false));
  }
  @Test
  // @Disabled("Fail when releasing new data")
  public void testGcSortedByImageSize(){
    ini();
    Main.getOp().setRealisticSize(0);
    GCreature gc = new GCreature();
    Insecte i0 = new Insecte();
    i0.setType(0);
    Insecte i1 = new Insecte();
    i1.setType(1);
    Insecte i2 = new Insecte();
    i2.setType(2);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    Fourmi f2 = new Fourmi(j.getFere(),Main.getEspeceById(0), (byte) 3, (byte) -3);
    gc.add(i0);
    gc.add(i1);
    gc.add(f);
    gc.add(i2);
    gc.add(f2);
    int t [] = {1,2,4,3,5};
    int k=0;
    for (Creature c : gc) {
      assertEquals(t[k], c.getId());
      k++;
    }
    Liste<Creature> lc = FPanelCarte.gcSortedByImageSize(gc);
    k=0;
    for (Creature c : lc) {
      assertEquals(t[k], c.getId());
      k++;
    }
    Main.getOp().setRealisticSize(1);
    lc = FPanelCarte.gcSortedByImageSize(gc);
    k=0;
    int t2 [] = {2,1,4,5,3}; //scarabé, cocinelle, fourmi imago, fourmi oeuf, puceron.
    for (Creature c : lc) {
      assertEquals(t2[k], c.getId());
      k++;
    }
  }
  @Test
  public void testIsSquareVisible(){
    Square c = new Square(0,0);
    assertTrue(FPanelCarte.isSquareVisible(c, 0, 0, 1, 1, 1));
    c = new Square(2,2);
    assertFalse(FPanelCarte.isSquareVisible(c, 0, 0, 1, 1, 1));
    c = new Square(1,1);
    assertTrue(FPanelCarte.isSquareVisible(c, 0, 0, 1, 1, 1));
    c = new Square(1,1);
    assertFalse(FPanelCarte.isSquareVisible(c, -1, -1, 0, 0, 1));
  }
  @Test
  public void testIsSquareVisible2(){
    Square c = new Square(0,0);
    assertTrue(FPanelCarte.isSquareVisible(c, 1, 1, 3, 3, 1));
    assertFalse(FPanelCarte.isSquareVisible(c, 2, 2, 3, 3, 1));
  }
  @Test
  public void testIsSquareVisible3(){
    Square c = new Square(0,0);
    assertTrue(FPanelCarte.isSquareVisible(c, 0, -10, 400, 300, 10));
    assertTrue(FPanelCarte.isSquareVisible(c, 10, 10, 400, 300, 10));
    assertFalse(FPanelCarte.isSquareVisible(c, 11, 10, 400, 300, 10));
    assertFalse(FPanelCarte.isSquareVisible(c, 10, 11, 400, 300, 10));
  }
}
