package fr.formiko.views.gui2d;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.*;
import fr.formiko.usuel.structures.listes.Liste;
import fr.formiko.tests.TestCaseMuet;
import fr.formiko.views.gui2d.PanneauCarte;

public class PanneauCarteTest extends TestCaseMuet{
  private Joueur j;
  private void ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(1,2),0,0,1,false,false),1);
    Main.setPartie(p);
    // p.setAppartionInsecte(false);
    // p.setAppartionGraine(false);
    j = new Joueur(new Fourmiliere(p.getGc().getCCase(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j);
    // assertTrue(p.getGc().getCCase(0,0).getContent().getFere().equals(j.getFere()));
    // assertTrue(p.getGc().getCCase(0,1).getContent().getFere()==null);
    // p.getGj().add(j);
    // return f;
  }

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
  @Test
  public void testGcSortedByImageSize(){
    ini();
    Main.getOp().setTailleRealiste(0);
    GCreature gc = new GCreature();
    Insecte i0 = new Insecte();
    i0.setType(0);
    Insecte i1 = new Insecte();
    i1.setType(1);
    Insecte i2 = new Insecte();
    i2.setType(2);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 3, (byte) 0);
    Fourmi f2 = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 3, (byte) -3);
    gc.add(i0);
    gc.add(i1);
    gc.add(f);
    gc.add(i2);
    gc.add(f2);
    int t [] = {1,2,4,3,5};
    int k=0;
    for (Creature c : gc.toList()) {
      assertEquals(t[k], c.getId());
      k++;
    }
    Liste<Creature> lc = PanneauCarte.gcSortedByImageSize(gc);
    k=0;
    for (Creature c : lc) {
      assertEquals(t[k], c.getId());
      k++;
    }
    Main.getOp().setTailleRealiste(1);
    lc = PanneauCarte.gcSortedByImageSize(gc);
    k=0;
    int t2 [] = {2,1,4,5,3}; //scarabé, cocinelle, fourmi imago, fourmi oeuf, puceron.
    for (Creature c : lc) {
      assertEquals(t2[k], c.getId());
      k++;
    }
  }
}
