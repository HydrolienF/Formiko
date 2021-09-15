package fr.formiko.usuel;

import fr.formiko.usuel.images.image;
import fr.formiko.usuel.listes.Liste;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;

/**
*{@summary Custom Tree class using Generics.}<br>
*@version 2.6
*@author Hydrolien
*/
public class Tree<T> implements Serializable, Iterable<T>{
  private Node<T> root;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}
  *@version 2.6
  */
  public Tree() {
    root = new Node<T>();
  }

  // GET SET -------------------------------------------------------------------
  public Node<T> getRoot(){return root;}

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Compact toString function.}
  *@version 2.6
  */
  public String toString(){
    return getRoot().toString();
  }
  /**
  *{@summary Transform a folder tree into a Java Tree.}
  *@param file file to Transform
  *@version 2.6
  */
  public static Tree<BufferedImage> folderToTree(File file) {
    Tree<BufferedImage> tree = new Tree<BufferedImage>();
    if (file.exists() && file.isDirectory()) {
      tree.getRoot().addFileAsNode(file);
    }
    return tree;
  }
  /**
  *{@summary Transform a folder tree into a Java Tree.}
  *@param fileName name of the file to Transform
  *@version 2.6
  */
  public static Tree<BufferedImage> folderToTree(String fileName) {
    return folderToTree(new File(fileName));
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
    // private int childNumber;
    private Node<T> current;
    private Liste<Node<T>> heapNextChild;

    /**
    *{@summary Initialize pointer to head of the list for iteration.}<br>
    *@version 2.6
    */
    public TreeIterator(Tree<T> tree){
      current = tree.getRoot();
      // childNumber=-1;
    }

    public boolean hasNext(){return current != null;}

    /**
    *{@summary Return current content and update pointer.}<br>
    *It save Children in a heap to be sur to check every Node.<br>
    *@version 2.6
    */
    public T next(){
      T content = current.getContent();
      // if(childNumber==-1){
      //   content = current.getContent();
      //   childNumber++;
      // }else if(childNumber>-1 && childNumber<getChildrenSize()){
      //   content = getChildren(childNumber).getContent();
      //   childNumber++;
      // }else{
      //   current = current.getNext();
      //   childNumber=-1;
      // }
      if (current.getChildrenSize()>0){
        heapNextChild.add(current.getChildren());
      }
      current=heapNextChild.pop();
      return content;
    }
  }
}
/**
*{@summary Node used by tree.}<br>
*It have a parent, children &#38; content.
*@version 2.6
*@author Hydrolien
*/
class Node<T> {
  private T content;
  private final Node<T> parent;
  private Liste<Node<T>> children;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}
  *@version 2.6
  */
  public Node(Node<T> parent){
    this.parent=parent;
    children = new Liste<Node<T>>();
  }
  /**
  *{@summary Secondary constructor used only by the Tree constructor.}
  *@version 2.6
  */
  public Node(){
    this(null);
  }
  // GET SET -------------------------------------------------------------------
  public Node<T> getParent(){return parent;}
  public Node<T> getChildren(int index){return children.get(index);}
  public Liste<Node<T>> getChildren(){return children;}
  public T getContent(){return content;}
  public void setContent(T content){this.content=content;}
  public int getChildrenSize(){return children.size();}

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
    for (Node<T> node : children) {
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
    Node<T> node = new Node<T>(this);
    node.setContent(childContent);
    children.add(node);
  }
  /**
  *{@summary Add a child to the children list.}
  *@version 2.6
  */
  public void addChildren(){
    children.add(new Node<T>(this));
  }
  /**
  *{@summary Add a folder (and it's sub folder) at this Node.}
  *@param file file to Transform into Node.
  *@version 2.6
  */
  @SuppressWarnings("unchecked")
  void addFileAsNode(File file) { //private by aviable on all Tree.java
    if(file.isDirectory()) {
      File allF [] = file.listFiles();
      if (allF != null) {
        for (File subFile : allF) {
          if(subFile.isDirectory()){
            Node<T> node = new Node<T>(this);
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
}
