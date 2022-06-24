package fr.formiko.usual.structures;

import fr.formiko.usual.images.Images;
import fr.formiko.usual.structures.listes.Liste;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

/**
*{@summary Custom Tree class using Generics.}<br>
*@lastEditedVersion 2.6
*@author Hydrolien
*/
public class Tree<T> implements Serializable, Iterable<T>{
  private TreeNode<T> root;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}
  *@lastEditedVersion 2.6
  */
  public Tree() {
    root = new TreeNode<T>();
  }

  // GET SET -------------------------------------------------------------------
  public TreeNode<T> getRoot(){return root;}

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Compact toString function.}
  *@lastEditedVersion 2.6
  */
  public String toString(){
    return getRoot().toString();
  }
  /**
  *{@summary Copy the structure of a given Tree.}
  *content of node will not be copy.
  *@lastEditedVersion 2.25
  */
  public Tree<T> copyStructure(Tree<T> treeOut) {
    treeOut.getRoot().copyStructure(this.getRoot());
    return treeOut;
  }
  /**
  *{@summary Copy the structure of a given Tree.}
  *content of node will not be copy.
  *@lastEditedVersion 2.25
  */
  public Tree<T> copyStructure() {
    return copyStructure(new Tree<T>());
  }
  /**
  *{@summary Transform a folder tree into a Java Tree<T>.}
  *@param file file to Transform
  *@lastEditedVersion 2.25
  */
  public void folderToTree(File file) {
    if (file.exists() && file.isDirectory()) {
      getRoot().addFileAsNode(file);
    }
  }
  /**
  *{@summary Transform a folder tree into a Java Tree<T>.}
  *@param fileName name of the file to Transform
  *@lastEditedVersion 2.25
  */
  public void folderToTree(String fileName) {
    folderToTree(new File(fileName));
  }
  /**
  *{@summary return the coresponding Iterator}<br>
  *@lastEditedVersion 2.6
  */
  public Iterator<T> iterator(){
    return new TreeIterator<T>(this);
  }

  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary Iterator of the Tree}<br>
  *@lastEditedVersion 2.6
  *@author Hydrolien
  */
  class TreeIterator<T> implements Iterator<T>, Serializable {
    private TreeNode<T> current;
    private Liste<TreeNode<T>> heapNextChild;

    /**
    *{@summary Initialize pointer to head of the list for iteration.}<br>
    *@lastEditedVersion 2.6
    */
    public TreeIterator(Tree<T> tree){
      current = tree.getRoot();
      heapNextChild = new Liste<TreeNode<T>>();
    }

    public boolean hasNext(){return current != null;}

    /**
    *{@summary Return current content and update pointer.}<br>
    *It save Children in a heap to be sur to check every TreeNode.<br>
    *@lastEditedVersion 2.6
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
