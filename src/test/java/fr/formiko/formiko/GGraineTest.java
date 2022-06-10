package fr.formiko.usuel.structures.listes;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.GGraine;
import fr.formiko.formiko.Graine;
import fr.formiko.tests.TestCaseMuet;

public class GGraineTest extends TestCaseMuet {
  @Test
  public void testFilterAndGetMost(){
    GGraine gg = new GGraine();
    gg.addTail(new Graine(null, 100, (byte)1));
    gg.addTail(new Graine(null, 90, (byte)10));
    gg.addTail(new Graine(null, 40, (byte)0));
    gg.addTail(new Graine(null, 120, (byte)0));
    gg.addTail(new Graine(null, 150, (byte)0));
    for (Graine g : gg.toList()) {
      g.casser();
    }
    gg.toList().get(4).setOuverte(false);
    System.out.println(gg);
    Graine max=gg.toList().filter(g -> g.getOuverte()).getMost((Graine g1, Graine g2) -> g2.getGivenFood() - g1.getGivenFood());
    assertEquals(gg.toList().get(3),max);
  }
  @Test
  public void testFilterAndGetMost2(){
    GGraine gg = new GGraine();
    Graine max=gg.toList().filter(g -> g.getOuverte()).getMost((Graine g1, Graine g2) -> g2.getGivenFood() - g1.getGivenFood());
    assertEquals(null,max);

    gg.add(new Graine(null, 100, (byte)1));
    max=gg.toList().filter(g -> g.getOuverte()).getMost((Graine g1, Graine g2) -> g2.getGivenFood() - g1.getGivenFood());
    assertEquals(null,max);

    gg.toList().get(0).setOuverte(true);
    max=gg.toList().filter(g -> g.getOuverte()).getMost((Graine g1, Graine g2) -> g2.getGivenFood() - g1.getGivenFood());
    assertEquals(gg.toList().get(0),max);
  }
}
