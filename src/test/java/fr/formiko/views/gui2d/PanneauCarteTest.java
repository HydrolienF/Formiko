package fr.formiko.views.gui2d;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.tests.TestCaseMuet;
import fr.formiko.views.gui2d.PanneauCarte;

public class PanneauCarteTest extends TestCaseMuet{
  @Test
  public void testNbrPrintableCase(){
    PanneauCarte pc = new PanneauCarte();
    pc.setBounds(0,0,1000,200);
    Main.setData(new Data());
    Main.getData().setTailleDUneCase(100);
    assertEquals(11,pc.nbrPrintableCase(true));
    assertEquals(3,pc.nbrPrintableCase(false));
    pc.setBounds(0,0,999,196);
    assertEquals(10,pc.nbrPrintableCase(true));
    assertEquals(2,pc.nbrPrintableCase(false));
    pc.setBounds(0,0,999,1096);
    assertEquals(10,pc.nbrPrintableCase(true));
    assertEquals(11,pc.nbrPrintableCase(false));

    Main.getData().setTailleDUneCase(12);
    pc.setBounds(0,0,999,700);
    assertEquals((999/12)+1,pc.nbrPrintableCase(true));
    assertEquals((700/12)+1,pc.nbrPrintableCase(false));
  }
  // @Test
  // public void testGcSortedByImageSize(){
  //   Main.getOp().setTailleRealiste(0);
  //   GCreature gc = new GCreature();
  //   gc.add(new Fourmi());
  //   Insecte i0 = new Insecte(null);
  //   i1.setType(0);
  //   Insecte i1 = new Insecte(null);
  //   i1.setType(1);
  //   Insecte i2 = new Insecte(null);
  //   i1.setType(2);
  //   gc.add();
  //
  // }
}
