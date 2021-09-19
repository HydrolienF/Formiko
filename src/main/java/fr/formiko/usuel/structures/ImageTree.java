package fr.formiko.usuel.structures;

import fr.formiko.usuel.images.image;
import fr.formiko.usuel.structures.listes.Liste;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Insecte;
import fr.formiko.usuel.maths.math;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;

/**
*{@summary Custom Tree class using Generics.}<br>
*@version 2.6
*@author Hydrolien
*/
public class ImageTree extends Tree<BufferedImage> {
  /**
  *{@summary Copy the structure of a given Tree.}
  *content of node will not be copy.
  *@version 2.6
  */
  @Override
  public ImageTree copyStructure() {
    ImageTree treeOut = new ImageTree();
    treeOut.getRoot().copyStructure(this.getRoot());
    return treeOut;
  }
  /**
  *{@summary Return the Image that fit to a Creature.}
  *@param c the Creature to represent.
  *@version 2.6
  */
  public BufferedImage getCreatureImage(Creature c){
    treeNode<BufferedImage> node = getRoot();
    if(c instanceof Insecte) {
      node = node.getChildren(1);
    }else if(c instanceof Fourmi) {
      node = node.getChildren(0);
    }else{
      return null;
    }
    int x=0;
    if(c instanceof Insecte){x=100;}
    node = node.getChildren(c.getEspece().getId()-x).getChildren(math.valAbs(c.getStade()));
    if(c.getStade()==0){
      if(c instanceof Fourmi){
        return node.getChildren(((Fourmi)c).getTypeF()).getContent();
      }else{
        //TODO if ♀ ...
        return node.getChildren(0).getContent();
      }
    }else{
      return node.getContent();
    }
  }
  //static
  /**
  *{@summary Return the scaled instance of this tree.}
  *content of node will not be copy.
  *@version 2.6
  */
  public static ImageTree getScaledInstanceFromTree(ImageTree treeIn, int dim){
    ImageTree treeOut = treeIn.copyStructure();
    //insect
    Liste<treeNode<BufferedImage>> insectListIn = treeIn.getRoot().getChildren(1).getChildren();
    Liste<treeNode<BufferedImage>> insectListOut = treeOut.getRoot().getChildren(1).getChildren();
    int idSpecies = 0;
    for (treeNode<BufferedImage> nodeIn : insectListIn) {
      BufferedImage biIn,biOut;
      for (int i=0;i<2 ;i++ ) { //imago ♂ & ♀
        biIn = nodeIn.getChildren(0).getChildren(i).getContent();
        if(biIn!=null){
          biOut = image.resize(biIn,image.taille(idSpecies+100,0,dim));
          insectListOut.get(idSpecies).getChildren(0).getChildren(i).setContent(biOut);
        }
      }
      for (int i=1;i<3 ;i++ ) { // other stade
        biIn = nodeIn.getChildren(i).getContent();
        if(biIn!=null){
          biOut = image.resize(biIn,image.taille(idSpecies+100,-i,dim));
          insectListOut.get(idSpecies).getChildren(i).setContent(biOut);
        }
      }
      idSpecies++;
    }
    //ant
    idSpecies = 0;
    Liste<treeNode<BufferedImage>> antListIn = treeIn.getRoot().getChildren(0).getChildren();
    Liste<treeNode<BufferedImage>> antListOut = treeOut.getRoot().getChildren(0).getChildren();
    for (treeNode<BufferedImage> nodeIn : antListIn) {
      BufferedImage biIn,biOut;
      int len = nodeIn.getChildren(0).getChildrenSize();
      for (int i=0;i<len ;i++ ) { //imago ♂, ♀, minor, media, major, soldier etc
        biIn = nodeIn.getChildren(0).getChildren(i).getContent();
        if(biIn!=null){
          biOut = image.resize(biIn,image.tailleFourmi(idSpecies,i,dim));
          antListOut.get(idSpecies).getChildren(0).getChildren(i).setContent(biOut);
        }
      }
      for (int i=1;i<3 ;i++ ) { // other stade
        biIn = nodeIn.getChildren(i).getContent();
        if(biIn!=null){
          biOut = image.resize(biIn,image.taille(idSpecies,-i,dim));
          insectListOut.get(idSpecies).getChildren(i).setContent(biOut);
        }
      }
      idSpecies++;
    }

    System.out.println("treeIn = "+treeIn);//@a
    System.out.println("treeOut = "+treeOut);//@a
    return treeOut;
  }
  /**
  *{@summary Transform a folder tree into a Java ImageTree.}
  *@param file file to Transform
  *@version 2.6
  */
  public static ImageTree folderToTree(File file) {
    ImageTree tree = new ImageTree();
    if (file.exists() && file.isDirectory()) {
      tree.getRoot().addFileAsNode(file);
    }
    return tree;
  }
  /**
  *{@summary Transform a folder tree into a Java ImageTree.}
  *@param fileName name of the file to Transform
  *@version 2.6
  */
  public static ImageTree folderToTree(String fileName) {
    return folderToTree(new File(fileName));
  }
}
