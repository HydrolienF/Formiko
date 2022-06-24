package fr.formiko.usual.structures.listes;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usual.Point;
import fr.formiko.usual.fichier;
import fr.formiko.usual.structures.listes.Liste;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FListeTest extends TestCaseMuet {
  @Test
  public void testcontains3(){
    //Creature have an id to test equals()
    Main.ini();
    Main.setPartie(Partie.getDefautlPartie());
    Liste<Creature> l = new Liste<Creature>();
    assertTrue(!l.contains(new Point(0,0)));
    Insecte i = new Insecte();
    Fourmi f = new Fourmi();
    l.add(i);
    l.add(f);
    Insecte i2 = i;
    assertTrue(l.contains(i));
    assertTrue(l.contains(f));
    assertTrue(l.contains(i2));
    assertFalse(l.contains(new Fourmi()));
    assertFalse(l.contains(new Insecte()));
  }
}
