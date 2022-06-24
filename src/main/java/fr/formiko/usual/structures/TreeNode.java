package fr.formiko.usual.structures;

import fr.formiko.usual.images.Images;
import fr.formiko.usual.structures.listes.Liste;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

/**
*{@summary TreeNode used by tree.}<br>
*It have a parent, children &#38; content.
*@lastEditedVersion 2.6
*@author Hydrolien
*/
public class TreeNode<T> extends Node {
  private T content;
  private final TreeNode<T> parent;
  private Liste<TreeNode<T>> children;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}
  *@lastEditedVersion 2.6
  */
  public TreeNode(TreeNode<T> parent){
    this.parent=parent;
    children = new Liste<TreeNode<T>>();
  }
  /**
  *{@summary Secondary constructor used only by the Tree constructor.}
  *@lastEditedVersion 2.6
  */
  public TreeNode(){
    this(null);
  }

  // GET SET -------------------------------------------------------------------
  public TreeNode<T> getParent(){return parent;}
  @Override
  public TreeNode<T> getChildren(int index){return children.get(index);}
  public Liste<TreeNode<T>> getChildren(){return children;}
  public int getChildrenSize(){return children.size();}
  public T getContent(){return content;}
  public void setContent(T content){this.content=content;}

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Compact toString function.}
  *@lastEditedVersion 2.6
  */
  public String toString(int id){
    String s = ""+id;
    if(getContent()!=null){
      // s+=getContent().toString();
      s+="I";
    }
    if(children.isEmpty()){return s;}
    s+="(";
    int k=0;
    for (TreeNode<T> node : children) {
      if(k>0){s+=",";}
      s+=node.toString(k);
      k++;
    }
    s+=")";
    return s;
  }
  public String toString(){return toString(0);}
  /**
  *{@summary Add a child to the children list.}
  *@param childContent the content of the child.
  *@lastEditedVersion 2.6
  */
  public void addChildren(T childContent){
    TreeNode<T> node = new TreeNode<T>(this);
    node.setContent(childContent);
    children.add(node);
  }
  /**
  *{@summary Add a child to the children list.}
  *@lastEditedVersion 2.6
  */
  public void addChildren(){
    children.add(new TreeNode<T>(this));
  }
  /**
  *{@summary Add x child to the children list.}<br>
  *@param x the number of children to add
  *@lastEditedVersion 2.6
  */
  public void addChildren(int x){
    for (int i=0;i<x ;i++ ) {
      addChildren();
    }
  }
  /**
  *{@summary Add a folder (and it's sub folder) at this TreeNode.}<br>
  *It sort file name to be sur to get it in the same order in Windows &#38; Linux.
  *(Linux don't give us file in alphabetic order)<br>
  *@param file file to Transform into TreeNode.
  *@lastEditedVersion 2.6
  */
  @SuppressWarnings("unchecked")
  void addFileAsNode(File file) { //private but aviable on all Tree.java
    if(file.isDirectory()) {
      File allF [] = file.listFiles();
      if (allF != null) {
        Liste<File> list = new Liste<File>();
        Comparator<File> comparator = (File f1, File f2) -> (f2.getName().compareTo(f1.getName()));
        for (File subFile : allF) {
          list.addSorted(subFile, comparator);
        }
        for (File subFile : list) {
        // for (File subFile : allF) {
          if(subFile.isDirectory()){
            TreeNode<T> node = new TreeNode<T>(this);
            children.add(node);
            node.addFileAsNode(subFile);
          } else if (Images.isImage(subFile)) { //  && subFile.getName().contains("full") && T instanceof BufferedImage
            setContent((T)Images.readImage(subFile));
          }
        }
      }
    }
  }
  /**
  *{@summary Copy the structure of a given TreeNode into this.}
  *content of node will not be copy.
  *@param nodeIn TreeNode to copy.
  *@lastEditedVersion 2.6
  */
  void copyStructure(TreeNode<T> nodeIn) { //private but aviable on all Tree.java
    addChildren(nodeIn.getChildrenSize());
    int k=0;
    for (TreeNode<T> subNode : getChildren()) {
      subNode.copyStructure(nodeIn.getChildren(k));
      k++;
    }
  }
}
