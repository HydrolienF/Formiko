package fr.formiko.formiko.images;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Pheromone;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.fichier;
import fr.formiko.usual.g;
import fr.formiko.usual.images.Images;
import fr.formiko.usual.images.Img;
import fr.formiko.usual.maths.math;
import fr.formiko.usual.types.str;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Function;
import javax.imageio.ImageIO;

/**
*{@summary Images function especialy for Formiko.}<br>
*@lastEditedVersion 2.16
*@author Hydrolien
*/
public class FImages extends Images {
  //taille d'une image de Creature.
  /***
  *{@summary Return size of a Creature image.}<br>
  *@param c Creature that we need size
  *@param taille taille used if it was 0% realistic
  *@lastEditedVersion 1.3
  */
  // public static int taille(Creature c, int taille){
  //   int a;
  //   if(c instanceof Fourmi){
  //     a = c.getIndividu().getSize();
  //   }else{
  //     a = c.getEspece().getSize(stade);
  //   }
  //   //standard a is 100
  //   return taille(a,taille);
  // }
  /**
  *{@summary Return size of a Creature image.}<br>
  *If ant is not found it will just return taille.
  *@param idEspece id of the Species (size depend of Species)
  *@param typeF typeF also infulence size of the Ant
  *@param taille taille used if it was 0% realistic
  *return Size with a part of realistic
  *@lastEditedVersion 2.16
  */
  public static int tailleFourmi(int idEspece, int typeF, int taille){
    if(Main.getEspeceById(idEspece)!=null && Main.getEspeceById(idEspece).getIndividuByType(typeF)!=null){
      int a = Main.getEspeceById(idEspece).getIndividuByType(typeF).getSize();
      return taille(a,taille);
    }else{
      erreur.alerte("Ant specie "+idEspece+" have no Individu for "+typeF);
      return taille;
      // return -1.
    }
  }
  /**
  *{@summary Return size of a Creature image.}<br>
  *@param idEspece id of the Species (size depend of Species)<br>
  *@param stade stade also infulence size of the Creature
  *@param taille taille used if it was 0% realistic
  *@lastEditedVersion 1.3
  */
  public static int taille(int idEspece, int stade, int taille){
    int a = Main.getEspeceById(idEspece).getSize(stade);//standard a is 100
    return taille(a,taille);
  }
  /**
  *{@summary Return size of a Creature image.}<br>
  *@param a size assumed if it was 100% realistic
  *@param taille size used if it was 0% realistic
  *@lastEditedVersion 1.3
  */
  public static int taille(int a, int taille){
    double tailleR = Main.getFop().getByte("realisticSize")/100.0;
    double db = ((a*taille*tailleR)/100) + (taille)*(1-tailleR);//en pixel on fait *4.
    return (int)db;
  }
  /**
  *{@summary Change the image color depending of ant Pheromone.}<br>
  *@param imgColor the image to change.
  *@param ph the Pheromone to get color from.
  *@lastEditedVersion 2.6
  */
  public static BufferedImage changeColor(Img imgColor, Pheromone ph){
    int w = imgColor.getWidth();
    int h = imgColor.getHeight();
    imgColor.setRouge(fullOf(w,h,ph.getR()));
    imgColor.setVert(fullOf(w,h,ph.getG()));
    imgColor.setBleu(fullOf(w,h,ph.getB()));
    imgColor.actualiserImage();
    return imgColor.getImage();
  }
}
