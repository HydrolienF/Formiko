package fr.formiko.usuel.structures;

import fr.formiko.usuel.images.image;
import fr.formiko.usuel.structures.listes.Liste;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

/**
*{@summary Custom Tree class using Generics.}<br>
*@version 2.6
*@author Hydrolien
*/
public class Tree<T> implements Serializable, Iterable<T>{
  private treeNode<T> root;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}
  *@version 2.6
  */
  public Tree() {
    root = new treeNode<T>();
  }

  // GET SET -------------------------------------------------------------------
  public treeNode<T> getRoot(){return root;}

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Compact toString function.}
  *@version 2.6
  */
  public String toString(){
    return getRoot().toString();
  }
  /**
  *{@summary Copy the structure of a given Tree.}
  *content of node will not be copy.
  *@version 2.6
  */
  public Tree<T> copyStructure() {
    Tree<T> treeOut = new Tree<T>();
    treeOut.getRoot().copyStructure(this.getRoot());
    return treeOut;
  }
  /**
  *{@summary return the coresponding Iterator}<br>
  *@version 2.6
  */
  public Iterator<T> iterator(){
    return new TreeIterator<T>(this);
  }

  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary Iterator of the Tree}<br>
  *@version 2.6
  *@author Hydrolien
  */
  class TreeIterator<T> implements Iterator<T>, Serializable {
    private treeNode<T> current;
    private Liste<treeNode<T>> heapNextChild;

    /**
    *{@summary Initialize pointer to head of the list for iteration.}<br>
    *@version 2.6
    */
    public TreeIterator(Tree<T> tree){
      current = tree.getRoot();
      heapNextChild = new Liste<treeNode<T>>();
    }

    public boolean hasNext(){return current != null;}

    /**
    *{@summary Return current content and update pointer.}<br>
    *It save Children in a heap to be sur to check every treeNode.<br>
    *@version 2.6
    */
    public T next(){
      T content = current.getContent();
      if (current.getChildrenSize()>0){
        heapNextChild.push(current.getChildren());
      }
      current=heapNextChild.pop();
      return content;
    }
  }
}
/**
*{@summary treeNode used by tree.}<br>
*It have a parent, children &#38; content.
*@version 2.6
*@author Hydrolien
*/
class treeNode<T> extends Node {
  private T content;
  private final treeNode<T> parent;
  private Liste<treeNode<T>> children;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}
  *@version 2.6
  */
  public treeNode(treeNode<T> parent){
    this.parent=parent;
    children = new Liste<treeNode<T>>();
  }
  /**
  *{@summary Secondary constructor used only by the Tree constructor.}
  *@version 2.6
  */
  public treeNode(){
    this(null);
  }

  // GET SET -------------------------------------------------------------------
  public treeNode<T> getParent(){return parent;}
  @Override
  public treeNode<T> getChildren(int index){return children.get(index);}
  public Liste<treeNode<T>> getChildren(){return children;}
  public int getChildrenSize(){return children.size();}
  public T getContent(){return content;}
  public void setContent(T content){this.content=content;}

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Compact toString function.}
  *@version 2.6
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
    for (treeNode<T> node : children) {
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
  *@version 2.6
  */
  public void addChildren(T childContent){
    treeNode<T> node = new treeNode<T>(this);
    node.setContent(childContent);
    children.add(node);
  }
  /**
  *{@summary Add a child to the children list.}
  *@version 2.6
  */
  public void addChildren(){
    children.add(new treeNode<T>(this));
  }
  /**
  *{@summary Add x child to the children list.}<br>
  *@param x the number of children to add
  *@version 2.6
  */
  public void addChildren(int x){
    for (int i=0;i<x ;i++ ) {
      addChildren();
    }
  }
  /**
  *{@summary Add a folder (and it's sub folder) at this treeNode.}
  *@param file file to Transform into treeNode.
  *@version 2.6
  */
  @SuppressWarnings("unchecked")
  void addFileAsNode(File file) { //private but aviable on all Tree.java
    if(file.isDirectory()) {
      File allF [] = file.listFiles();
      if (allF != null) {
        // Liste<File> list = new Liste<File>();
        // Comparator<File> comparator = (File f1, File f2) -> (f1.getName().compareTo(f2.getName()));
        // for (File subFile : allF) {
        //   list.addSorted(subFile, comparator);
        // }
        for (File subFile : allF) {
          if(subFile.isDirectory()){
            treeNode<T> node = new treeNode<T>(this);
            children.add(node);
            node.addFileAsNode(subFile);
          } else if (image.isImage(subFile)) { //  && subFile.getName().contains("full") && T instanceof BufferedImage
            try {
              setContent((T)image.readImage(subFile));
            }catch (Exception e) {}
          }
        }
      }
    }
  }
  /**
  *{@summary Copy the structure of a given treeNode into this.}
  *content of node will not be copy.
  *@param nodeIn treeNode to copy.
  *@version 2.6
  */
  void copyStructure(treeNode<T> nodeIn) { //private but aviable on all Tree.java
    addChildren(nodeIn.getChildrenSize());
    int k=0;
    for (treeNode<T> subNode : getChildren()) {
      subNode.copyStructure(nodeIn.getChildren(k));
      k++;
    }
  }
}
