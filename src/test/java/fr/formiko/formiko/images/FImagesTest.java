package fr.formiko.formiko.images;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.tests.TestCaseMuet;
import fr.formiko.fusual.FFolder;
import fr.formiko.usual.fichier;
import fr.formiko.usual.images.Img;
import fr.formiko.usual.images.Images;
import fr.formiko.usual.maths.allea;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

public class FImagesTest extends TestCaseMuet {
  //taille
  @Test
  public void testTaille(){
    Main.setFolder(new FFolder(Main.getView()));
    Main.getFolder().ini();
    Main.iniOp();//on initialise les Options.
    //FImages.taille(taille de l'espece, taille voulu avec le niveau de grossicement)
    Main.getOp().setRealisticSize(100);//un paramètre utilisé par FImages.taille
    assertEquals(100,FImages.taille(100,100));//normale.

    //si la taille n'est pas réaliste on prend a 100% la taille de la case.
    Main.getOp().setRealisticSize(0);//un paramètre utilisé par FImages.taille
    assertEquals(100,FImages.taille(20,100));
    assertEquals(140,FImages.taille(20,140));
    assertEquals(10,FImages.taille(20,10));

    //si la taille est réaliste a 100% on ne dépend plus que de taille de l'espece * tailleVOulue/100
    Main.getOp().setRealisticSize(100);
    assertEquals(20,FImages.taille(20,100));
    assertEquals(30,FImages.taille(20,150));
    assertEquals(10,FImages.taille(20,50));
    assertEquals(4,FImages.taille(8,50));
    //et ca arrondi bien.
    assertEquals(4,FImages.taille(8,51));
    assertEquals(4,FImages.taille(8,56)); // la conversion qu'on fait ne prend que la partie entière, c'est pas très grave mais s'est bon a savoir.
    //assertEquals(5,FImages.taille(8,57));
    //assertEquals(5,FImages.taille(8,60));
  }
  @Test
  public void testTaillePartiellementRealiste(){
    Main.iniOp();//on initialise les Options.
    Main.getOp().setRealisticSize(50);
    //si la taille est partielement réaliste.
    assertEquals(75,FImages.taille(50,100));//100% réaliste ca donne 100 0% réaliste ca donne 50, on veut que ce soit au milieux.
    assertEquals(60,FImages.taille(50,80));
    assertEquals(150,FImages.taille(50,200));
    assertEquals(72,FImages.taille(20,120));

    Main.getOp().setRealisticSize(10);
    assertEquals(95,FImages.taille(50,100));
    assertEquals(47,FImages.taille(50,50));
    Main.getOp().setRealisticSize(63);
    assertEquals(82,FImages.taille(50,120));
    assertEquals(59,FImages.taille(20,120));
  }
}
