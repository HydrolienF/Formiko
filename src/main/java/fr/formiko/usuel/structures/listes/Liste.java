package fr.formiko.usuel.structures.listes;

import fr.formiko.formiko.ObjetSurCarteAId;
import fr.formiko.usuel.structures.Node;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;

/**
*{@summary Custom Linked Liste class using Generics.}<br>
*@lastEditedVersion 1.52
*@author Hydrolien
*/
public class Liste<T> implements Iterable<T>, Serializable, List<T> {
  protected ListeNode<T> head, tail;
  // CONSTRUCTORS --------------------------------------------------------------
  public Liste(){}
  // GET SET -------------------------------------------------------------------
  public ListeNode<T> getHead(){return head;}
  private void setHead(ListeNode<T> node){head=node;}
  public ListeNode<T> getTail(){return tail;}
  private void setTail(ListeNode<T> node){tail=node;}
  public T getFirst(){if(getHead()!=null){return getHead().getContent();}else{return null;}}
  public T getLast(){if(getTail()!=null){return getTail().getContent();}else{return null;}}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary add new Element at tail of the linked list}<br>
  *content content of the element to add.
  *@lastEditedVersion 1.31
  */
  public void addTail(T content){
    if(content==null){return;}
    //if(contains(content)){return;}
    ListeNode<T> node = new ListeNode<>(content, null);
    if (head == null)
      tail = head = node;
    else {
      tail.setNext(node);
      tail = node;
    }
  }
  /**
  *{@summary add new Element at head of the linked list}<br>
  *content content of the element to add.
  *@lastEditedVersion 1.52
  */
  public void addHead(T content){
    if(content==null){return;}
    //if(contains(content)){return;}
    ListeNode<T> node = new ListeNode<>(content, null);
    if (head == null)
      tail = head = node;
    else {
      node.setNext(head);
      head = node;
    }
  }
  /**
  *{@summary Update tail element.}<br>
  *@lastEditedVersion 1.41
  */
  public void updateTail(){
    ListeNode<T> node = getHead();
    while(node.getNext()!=null){
      node=node.getNext();
    }
    tail=node;
  }
  /**
  *{@summary add new Element at the default place. At the tail of the linked list}<br>
  *@lastEditedVersion 1.31
  */
  public boolean add(T content){
    addTail(content);
    return true;
  }
  @Override
  public void add(int index, T content){
    if(index<0){throw new IndexOutOfBoundsException();}
    if(index==0){
      addHead(content);
    }else{
      if(!getHead().add(index, content)){
        throw new IndexOutOfBoundsException();
      }
    }
    //TODO
    // addTail(content);
    // return true;
  }
  /**
  *{@summary add an other linked list at tail of the linked list}<br>
  *@lastEditedVersion 1.31
  */
  public void addList(Liste<T> list){
    if(list == null || list.getHead() == null){ return;}
    if (getTail() == null){
      head = list.getHead();
      tail = list.getTail();
    }else {
      tail.setNext(list.getHead());
      tail = list.getTail();
    }
  }
  public void add(Liste<T> list){addList(list);}
  /**
  *{@summary Push at the top of the heap (at head).}<br>
  *@lastEditedVersion 2.6
  */
  public void push(T t){
    addHead(t);
  }
  /**
  *{@summary Push at the top of the heap list as Head.}<br>
  *@lastEditedVersion 2.6
  */
  public void push(Liste<T> list){
    if(list == null || list.getHead() == null){ return;}
    // list.addList(this);
    // head=list.getHead();
    // tail=list.getTail();
    if (getTail() == null){
      head = list.getHead();
      tail = list.getTail();
    }else {
      list.getTail().setNext(getHead());
      head = list.getHead();
      // tail.setNext(list.getHead());
      // tail = list.getTail();
    }
  }
  /**
  *{@summary Pop (return the item &#38; remove it) of the top of the heap (at Head).}<br>
  *@lastEditedVersion 2.6
  */
  public T pop(){
    T t = getFirst();
    removeItem(0);
    return t;
  }
  /**
  *{@summary Return true is list is empty.}<br>
  *It's a better function than doing list.length()==0.
  *@lastEditedVersion 2.5
  */
  public boolean isEmpty(){
    return getHead()==null;
  }
  /**
  *{@summary Return the number of element.}<br>
  *@lastEditedVersion 2.1
  */
  public int size(){
    int cpt = 0;
    for (T t : this) {
      cpt++;
    }
    return cpt;
  }public int length(){return size();}
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@lastEditedVersion 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof Liste)){return false;}
    Liste gs = (Liste)o;
    if(getHead()==null && gs.getHead()==null){return true;}
    if(getHead()==null || gs.getHead()==null){return false;}
    return getHead().equals(gs.getHead());
  }
  /**
  *{@summary Return true if this contains T content.}<br>
  *@lastEditedVersion 2.1
  */
  @SuppressWarnings("unchecked")
  public boolean contains(Object o){
    T content=(T)o;
    for (T t : this ) {
      if(t.equals(content)){return true;}
    }
    return false;
  }
  /**
  *{@summary Return a long string with all the list value split by ' '.}<br>
  *@lastEditedVersion 1.31
  */
  @Override
  public String toString(){
    String r = "";
    for (T t : this ) {
      r+= t.toString();
      r+= " ";
    }
    return r;
  }
  public String toStringId(){
    String r = "";
    for (T t : this ) {
      try {
        r+= ((ObjetSurCarteAId)t).getId();
      }catch (Exception e) {}
      r+= " ";
    }
    return r;
  }
  /**
  *{@summary Return a long string with all the list value split by '\n'.}<br>
  *@lastEditedVersion 1.41
  */
  public String toStringLong(){
    String r = "";
    for (T t : this ) {
      r+= t.toString();
      r+= "\n";
    }
    return r;
  }
  /**
  *{@summary Return a new Liste with only item that match with predicate.}<br>
  *@param predicate a predicate that is used for the filter
  *@return a new Liste&lb;T&rb; filtered
  *@lastEditedVersion 1.41
  */
  public Liste<T> filter(Predicate<T> predicate){
    Liste<T> newList = new Liste<T>();
    for (T e : this) {
      if(predicate.test(e)){
        newList.add(e);
      }
    }
    return newList;
  }
  /**
  *{@summary return the xa item}<br>
  *@lastEditedVersion 2.1
  */
  @Override
  public T get(int index){
    if(index<0){return null;}
    for (T item : this ) {
      if(index==0){return item;}
      index--;
    }
    return null;
  }
  public T getItem(int index){return get(index);}

  /**
  *{@summary copy only different item.}<br>
  *@lastEditedVersion 1.41
  */
  public boolean removeDuplicateItem(){
    Liste<T> newList = new Liste<T>();
    boolean flag=false;
    for (T t : this ) {
      if (!newList.contains(t)){
        newList.add(t);
      }else{
        flag=true;
      }
    }
    head = newList.getHead();
    tail = newList.getTail();
    return flag;
  }
  /**
  *{@summary Delete the xa element}<br>
  *@param i the number of the element to remove.
  *@return true if it have been remove
  *@lastEditedVersion 1.52
  */
  public boolean removeItem(int i){
    if(getHead()==null || i<0){return false;}
    if(i==0){head=getHead().getNext(); return true;}
    return getHead().removeItem(i);
  }
  @Override
  public T remove(int i){
    removeItem(i);
    return null;
  }
  /**
  *{@summary Delete the 1a t element}<br>
  *@param o the element to remove.
  *@return true if it have been remove
  *@lastEditedVersion 1.52
  */
  @Override
  @SuppressWarnings("unchecked")
  public boolean remove(Object o){
    try {
      T t = (T)o;
      if(getHead()==null || t==null){return false;}
      if(getHead().getContent().equals(t)){head=getHead().getNext(); return true;}
      return getHead().remove(t);
    }catch (Exception e) {
      return false;
    }
  }
  @Override
  public Liste<T> subList(int a, int b){
    throw new UnsupportedOperationException();
  }
  @Override
  public ListIterator<T> listIterator(){
    throw new UnsupportedOperationException();
  }
  @Override
  public ListIterator<T> listIterator(int index){
    throw new UnsupportedOperationException();
  }
  @Override
  public int indexOf(Object o){
    //TODO
    throw new UnsupportedOperationException();
  }
  @Override
  public int lastIndexOf(Object o){
    //TODO
    throw new UnsupportedOperationException();
  }
  @Override
  public T set(int index, T t){
    //TODO
    throw new UnsupportedOperationException();
  }
  @Override
  public boolean retainAll​(Collection<?> c){
    throw new UnsupportedOperationException();
  }
  @Override
  public boolean containsAll​(Collection<?> c){
    throw new UnsupportedOperationException();
  }
  @Override
  public <T> T[] toArray(T[] a){
    throw new UnsupportedOperationException();
  }
  @Override
  public Object[] toArray(){
    Object array [] = new Object[length()];
    int k=0;
    for (T t : this) {
      array[k]=t;
      k++;
    }
    return array;
  }
  @Override
  @SuppressWarnings("unchecked")
  public boolean addAll​(Collection<? extends T> c){
    boolean flag = true;
    for (Object o : c) {
      try {
        if(!add((T)o)){
          flag=false;
        }
      }catch (Exception e) {
        flag=false;
      }
    }
    return flag;
  }
  @Override
  @SuppressWarnings("unchecked")
  public boolean addAll​(int index, Collection<? extends T> c){
    boolean flag = true;
    for (Object o : c) {
      try {
        add(index, (T)o);
      }catch (Exception e) {
        flag=false;
      }
    }
    return flag;
  }
  @Override
  @SuppressWarnings("unchecked")
  public boolean removeAll​(Collection<?> c){
    boolean flag = true;
    for (Object o : c) {
      try {
        if(!remove((T)o)){
          flag=false;
        }
      }catch (Exception e) {
        flag=false;
      }
    }
    return flag;
  }
  @Override
  public void clear(){
    head = null;
    tail = null;
  }
  @Override
  public void sort​(Comparator<? super T> c){
    Liste<T> newList = new Liste<T>();
    for (T t : this) {
      newList.addSorted(t,c);
    }
    head = newList.getHead();
    tail = newList.getTail();
  }
  public boolean addSorted(T t, Comparator<? super T> c){
    if(getHead()==null){
      return add(t);
    }else if(c.compare(t,getHead().getContent())>0){
      ListeNode<T> newNode = new ListeNode<T>(t);
      newNode.setNext(getHead());
      setHead(newNode);
      return true;
    }else{
      ListeNode<T> node = getHead();
      ListeNode<T> newNode = new ListeNode<T>(t);
      while(node.getNext()!=null){
        if(c.compare(t,node.getNext().getContent())>0){
          //insert between node & node.next
          newNode.setNext(node.getNext());
          node.setNext(newNode);
          return true;
        }
        node=node.getNext();
      }
    }
    // if(c.compare(t,getTail().getContent())>0){
      ListeNode<T> newNode = new ListeNode<T>(t);
      getTail().setNext(newNode);
      setTail(newNode);
      return true;
    // }
    // return false;
  }
  /**
  *{@summary return the coresponding Iterator}<br>
  *@lastEditedVersion 1.31
  */
  public Iterator<T> iterator(){
    return new ListeIterator<T>(this);
  }
}
/**
*{@summary Iterator of the Linked Liste}<br>
*@lastEditedVersion 1.31
*@author Hydrolien
*/
class ListeIterator<T> implements Iterator<T>, Serializable {
  private ListeNode<T> current;

  /**
  *{@summary Initialize pointer to head of the list for iteration.}<br>
  *@lastEditedVersion 1.31
  */
  public ListeIterator(Liste<T> list){
    current = list.getHead();
  }

  public boolean hasNext(){return current != null;}

  /**
  *{@summary Return current content and update pointer.}<br>
  *@lastEditedVersion 1.31
  */
  public T next(){
    T content = current.getContent();
    current = current.getNext();
    return content;
  }
}
/**
*{@summary Constituent ListeNode of the Linked Liste}<br>
*@lastEditedVersion 1.41
*@author Hydrolien
*/
class ListeNode<T> extends Node implements Serializable {
  private T content;
  private ListeNode<T> next;
  /**
  *{@summary Main constructor for ListeNode.}<br>
  *@lastEditedVersion 1.31
  */
  public ListeNode(T content, ListeNode<T> next){
      this.content = content;
      this.next = next;
  }
  /**
  *{@summary Secondary constructor for ListeNode with null next item.}<br>
  *@lastEditedVersion 2.1
  */
  public ListeNode(T content){
    this(content, null);
  }
  // GET SET -------------------------------------------------------------------
  // @Override
  public ListeNode<T> getNext(){return next;}
  public void setNext(ListeNode<T> next){this.next = next;}
  public T getContent(){return content;}
  public void setContent(T content){this.content=content;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Equals function that also test next.}<br>
  *@lastEditedVersion 1.41
  */
  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object o){
    if(o==null){return false;}
    if(o instanceof ListeNode){
      try {
        ListeNode<T> node = (ListeNode<T>)o;
        if(getContent().equals(node.getContent())){
          if(getNext()==null && node.getNext()==null){return true;}
          if(getNext()==null || node.getNext()==null){return false;}
          return getNext().equals(node.getNext());
        }
      }catch (Exception e) {}
    }
    return false;
  }
  /**
  *{@summary Delete the xa element}<br>
  *@param i the number of the element to remove.
  *@return true if it have been remove
  *@lastEditedVersion 1.52
  */
  public boolean removeItem(int i){
    if(getNext() == null){return false;}
    if(i==0){
      next = getNext().getNext();//go over the element.
      return true;
    }
    return getNext().removeItem(i-1);
  }

  /**
  *{@summary Delete the 1a t element}<br>
  *@param t the element to remove.
  *@return true if it have been remove
  *@lastEditedVersion 1.41
  */
  public boolean remove(T t){
    if(getNext() == null){return false;}
    if(getNext().getContent().equals(t)){
      next = getNext().getNext();//go over the element.
      return true;
    }
    return getNext().remove(t);
  }
  /**
  *{@summary Add the t element}<br>
  *@param t the element to add.
  *@return true if it have been add.
  *@lastEditedVersion 2.1
  */
  public boolean add(int index, T t){
    if(index==1){
      ListeNode<T> node = new ListeNode<T>(t);
      node.setNext(getNext());
      setNext(node);
      return true;
    }
    if(getNext() == null){return false;}
    return getNext().add(index--, t);
  }
}
