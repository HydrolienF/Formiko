package fr.formiko.fusual.images;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usual.Folder;
import fr.formiko.usual.ProgressionNull;
import fr.formiko.usual.fichier;
import fr.formiko.usual.images.Images;
import fr.formiko.usual.images.Img;
import fr.formiko.usual.maths.allea;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

public class FImagesTest extends TestCaseMuet {

  // FUNCTIONS -----------------------------------------------------------------
  @BeforeAll
  public static void iniMain(){
    Folder.setFolder(new Folder(new ProgressionNull()));
  }
  private void ini(){
    Folder folder = new Folder(new ProgressionNull());
    folder.ini();
    Folder.setFolder(folder);
  }
  //getImage
  @Test
  public void testGetImage(){
    ini();
    Image nul = Images.getImage("null");
    Image i0 = Images.getImage("vbfizefzeg.png");
    //faute de savoir comment comparer les images sans prendre en compte leur adresse mémoire on supose que la taille est assez propre a chaque image.
    assertTrue(i0!=null);
    assertEquals(nul.getWidth(null),i0.getWidth(null));
    Image i1 = Images.getImage("b0.png");
    Image i2 = Images.getImage("b0");
    Image i3 = Images.getImage("b0",Folder.getFolder().getFolderStable()+Folder.getFolder().getFolderImages());//si on cherche dans le bon répertoire.
    Image i4 = Images.getImage("b0","./"); //si on cherche dans un mauvais répertoire.
    assertEquals(i1.getWidth(null),i2.getWidth(null));
    assertEquals(i1.getWidth(null),i3.getWidth(null));
    assertEquals(null,i4);
  }
}
