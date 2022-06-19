package fr.formiko.usual.structures;

import fr.formiko.usual.erreur;
import fr.formiko.usual.images.Img;
import fr.formiko.usual.images.Images;
import fr.formiko.usual.maths.math;
import fr.formiko.usual.structures.listes.Liste;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;

/**
*{@summary Custom Tree class using Generics.}<br>
*@lastEditedVersion 2.25
*@author Hydrolien
*/
public class ImageTree extends Tree<BufferedImage> {
  /**
  *{@summary Simple builder.}
  *@lastEditedVersion 2.25
  */
  public ImageTree newImageTree(){
    return new ImageTree();
  }
  /**
  *{@summary Copy the structure of a given Tree.}
  *content of node will not be copy.
  *@lastEditedVersion 2.6
  */
  @Override
  public ImageTree copyStructure() {
    return (ImageTree)super.copyStructure(new ImageTree());
  }
}
