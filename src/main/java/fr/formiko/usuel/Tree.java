package fr.formiko.usuel;

import fr.formiko.usuel.images.image;
import fr.formiko.usuel.listes.Liste;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;

/**
*{@summary Custom Tree class using Generics.}<br>
*@version 2.6
*@author Hydrolien
*/
public class Tree<T> implements Serializable {
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
  public static Tree folderToTree(String fileName) {
    return folderToTree(new File(fileName));
  }

  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary Node used by tree.}<br>
  *It have a parent, children &#38; data.
  *@version 2.6
  *@author Hydrolien
  */
  public static class Node<T> {
    private T data;
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
    public T getData(){return data;}
    public void setData(T data){this.data=data;}
    public int getChildrenSize(){return children.size();}

    // FUNCTIONS -----------------------------------------------------------------
    /**
    *{@summary Compact toString function.}
    *@version 2.6
    */
    public String toString(int id){
      String s = ""+id;
      if(getData()!=null){
        System.out.println("data!=null");//@a
        s+=getData().toString();
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
    *@param childData the data of the child.
    *@version 2.6
    */
    public void addChildren(T childData){
      Node<T> node = new Node<T>(this);
      node.setData(childData);
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
    private void addFileAsNode(File file) {
      if(file.isDirectory()) {
        //add all the sub fodler.
        File allF [] = file.listFiles();
        if (allF != null) {
            for (File subFile : allF) {
              // addChildren();
              if(subFile.isDirectory()){
                Node<T> node = new Node<T>(this);
                children.add(node);
                node.addFileAsNode(subFile);
              } else if (image.isImage(subFile)) { //  && subFile.getName().contains("full") && T instanceof BufferedImage
                try {
                  System.out.println("settting data");
                  setData((T)image.readImage(subFile));
                  System.out.println("data  set");
                }catch (Exception e) {}
              }
            }
        }
      }
    }
  }
}
