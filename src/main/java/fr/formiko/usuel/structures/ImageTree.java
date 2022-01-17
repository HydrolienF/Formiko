package fr.formiko.usuel.structures;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.images.Img;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.structures.listes.Liste;

import java.awt.Graphics;
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
  *@version 2.14
  */
  public BufferedImage getCreatureImage(Creature c){
    try {
      TreeNode<BufferedImage> node = getRoot();
      if(c instanceof Insecte) {
        node = node.getChildren(1);
      }else if(c instanceof Fourmi) {
        node = node.getChildren(0);
      }else{
        return null;
      }
      int x=0;
      if(c instanceof Insecte){x=100;}
      // System.out.println("0 "+ (c.getEspece().getId()-x)+" "+math.valAbs(c.getStade()));
      node = node.getChildren(c.getEspece().getId()-x).getChildren(math.valAbs(c.getStade()));
      if(c.getStade()==0){
        if(c instanceof Fourmi){
          return createAntImageFromNode(c, node.getChildren(((Fourmi)c).getTypeF()));
          // return node.getChildren(((Fourmi)c).getTypeF()).getContent();
        }else{
          //TODO if ♀ ...
          return node.getChildren(0).getContent();
        }
      }else{
        return node.getContent();
      }
    }catch (Exception e) {
      erreur.erreur("fail to get Creature Image");
      return null;
    }
  }
  //static
  /**
  *{@summary Create an ant Image from the coresponding node, with the Creature color.}<br>
  *@param c Creature that will be used for color
  *@param node
  *@version 2.6
  */
  public static BufferedImage createAntImageFromNode(Creature c, TreeNode<BufferedImage> node){
    BufferedImage body = node.getContent();
    if(node.getChildrenSize()>0){
      if(Main.getOp().getAntColorLevel()>0){
        BufferedImage bi = new BufferedImage(body.getWidth(), body.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.drawImage(body, 0, 0, null);
        if(node.getChildren(0)!=null){
          BufferedImage color = node.getChildren(0).getContent();
          Img img = new Img(color);
          if(Main.getOp().getAntColorLevel()>1){
            img.supprimerLaTransparencePartielle(1);
          }
          color = image.changeColor(img, c.getPheromone());
          g.drawImage(color, 0, 0, null);
        }
        if(c.getHaveWings()){
          if(node.getChildren(1)!=null){
            BufferedImage wings = node.getChildren(1).getContent();
            g.drawImage(wings, 0, 0, null);
          }else{
            erreur.alerte("Can't find wings of an ant specie");
          }
        }
        g.dispose();
        body=bi;
      }
    }
    return body;
  }
  /**
  *{@summary Return the scaled instance of this tree.}
  *content of node that correspond to Creature image will be copy.
  *@version 2.10
  */
  public static ImageTree getScaledInstanceFromTree(ImageTree treeIn, int dim){
    if(treeIn.getRoot().getChildrenSize()==0){
      erreur.erreur("ImageTreeIni is empty");
      return null;
    }
    ImageTree treeOut = treeIn.copyStructure();
    //insect
    Liste<TreeNode<BufferedImage>> insectListIn = treeIn.getRoot().getChildren(1).getChildren();
    Liste<TreeNode<BufferedImage>> insectListOut = treeOut.getRoot().getChildren(1).getChildren();
    addScaledInsect(insectListIn, insectListOut, dim);
    //ant
    Liste<TreeNode<BufferedImage>> antListIn = treeIn.getRoot().getChildren(0).getChildren();
    Liste<TreeNode<BufferedImage>> antListOut = treeOut.getRoot().getChildren(0).getChildren();
    addScaledAnt(antListIn, antListOut, dim);
    return treeOut;
  }
  /**
  *{@summary Return the scaled instance of the Insect part.}
  *content of node that correspond to Creature image will be copy.
  *@version 2.10
  */
  private static void addScaledInsect(Liste<TreeNode<BufferedImage>> insectListIn, Liste<TreeNode<BufferedImage>> insectListOut, int dim){
    int idSpecies = 0;
    for (TreeNode<BufferedImage> nodeIn : insectListIn) {
      BufferedImage biIn,biOut;
      for (int i=0;i<2 ;i++ ) { //imago ♂ & ♀
        if(nodeIn.getChildren(0)!=null && nodeIn.getChildren(0).getChildren(i)!=null){
          if (nodeIn.getChildren(0).getChildren(i).getContent()!=null) {
            biIn = nodeIn.getChildren(0).getChildren(i).getContent();
            biOut = image.resize(biIn,image.taille(idSpecies+100,0,dim));
            insectListOut.get(idSpecies).getChildren(0).getChildren(i).setContent(biOut);
          } //else erreur.alerte("A branch of the tree is empty (imago)");
        }else{
          erreur.alerte("A branch of the tree is cut (imago)");
        }
      }
      for (int i=1;i<4 ;i++ ) { // other stade
        if(nodeIn.getChildren(i)!=null){
          if (nodeIn.getChildren(i).getContent()!=null) {
            biIn = nodeIn.getChildren(i).getContent();
            biOut = image.resize(biIn,image.taille(idSpecies+100,-i,dim));
            insectListOut.get(idSpecies).getChildren(i).setContent(biOut);
          } //else erreur.alerte("A branch of the tree is empty (other stade)");
        }else{
          erreur.alerte("A branch of the tree is cut (other stade)");
        }
      }
      idSpecies++;
    }
  }
  /**
  *{@summary Return the scaled instance of the Ant part.}
  *content of node that correspond to Creature image will be copy.
  *@version 2.10
  */
  private static void addScaledAnt(Liste<TreeNode<BufferedImage>> antListIn, Liste<TreeNode<BufferedImage>> antListOut, int dim){
    int idSpecies = 0;
    for (TreeNode<BufferedImage> nodeIn : antListIn) {
      BufferedImage biIn,biOut;
      int len = nodeIn.getChildren(0).getChildrenSize();
      for (int i=0; i<len; i++ ) { //imago ♂, ♀, minor, media, major, soldier etc
        //ant body
        if(nodeIn.getChildren(0)!=null && nodeIn.getChildren(0).getChildren(i)!=null){
          TreeNode<BufferedImage> currentNodeIn = nodeIn.getChildren(0).getChildren(i);
          TreeNode<BufferedImage> currentNodeOut = antListOut.get(idSpecies).getChildren(0).getChildren(i);
          biIn = currentNodeIn.getContent();
          if(biIn!=null){ //if there is an ant
            int size = image.tailleFourmi(idSpecies,i,dim);
            biOut = image.resize(biIn,size);
            currentNodeOut.setContent(biOut);
            addScaledAntColorPart(currentNodeIn, currentNodeOut, dim, size);
            addScaledAntOtherPart(currentNodeIn, currentNodeOut, dim, size);
          }
        }else{
          erreur.alerte("A branch of the tree is cut (Ant imago)");
        }
      }
      for (int i=1;i<4 ;i++ ) { // other stade
        if(nodeIn.getChildren(i)!=null && nodeIn.getChildren(i).getContent()!=null){
          biIn = nodeIn.getChildren(i).getContent();
          biOut = image.resize(biIn,image.taille(idSpecies,-i,dim));
          antListOut.get(idSpecies).getChildren(i).setContent(biOut);
        }else{
          erreur.alerte("A branch of the tree is cut (Ant other stade)");
        }
      }
      idSpecies++;
    }
  }
  /**
  *{@summary Return the scaled color of the Ant.}<br>
  *It can return null if color is disable in Options.
  *Color image will be edit to whithout transparency image if Ant color level is 1 in Options.
  *@version 2.10
  */
  private static void addScaledAntColorPart(TreeNode<BufferedImage> currentNodeIn, TreeNode<BufferedImage> currentNodeOut, int dim, int size){
    // if(Main.getOp().getAntColorLevel()==0){return;} //done during game time.
    BufferedImage biIn,biOut;
    if(currentNodeIn.getChildren(0)!=null){
      biIn = currentNodeIn.getChildren(0).getContent();
      //done during game time.
      // if(Main.getOp().getAntColorLevel()>1){
      //   Img img = new Img(biIn);
      //   img.supprimerLaTransparencePartielle(1);
      //   img.actualiserImage();
      //   biIn = img.getImage();
      // }
      if(biIn!=null){
        biOut = image.resize(biIn,size);
        currentNodeOut.getChildren(0).setContent(biOut);
      }
    }
  }
  /**
  *{@summary Return the scaled part of the body of the Ant.}<br>
  *@version 2.10
  */
  private static void addScaledAntOtherPart(TreeNode<BufferedImage> currentNodeIn, TreeNode<BufferedImage> currentNodeOut, int dim, int size){
    BufferedImage biIn,biOut;
    int k=1;
    while(currentNodeIn.getChildren(k)!=null){
      biIn = currentNodeIn.getChildren(k).getContent();
      if(biIn!=null){
        biOut = image.resize(biIn,size);
        currentNodeOut.getChildren(k).setContent(biOut);
      }
      k++;
    }
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
