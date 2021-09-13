package fr.formiko.usuel;

import org.junit.jupiter.api.Test;

import fr.formiko.usuel.Tree;
import fr.formiko.usuel.tests.TestCaseMuet;


public class TreeTest extends TestCaseMuet{

  // FUNCTIONS -----------------------------------------------------------------
  @Test
  public void testTree(){
    Tree<String> tree = new Tree<String>();
    assertTrue(tree.getRoot()!=null);
    assertTrue(tree.getRoot().getChildren(0)==null);
  }
  @Test
  public void testTree2(){
    Tree<String> tree = new Tree<String>();
    tree.getRoot().addChildren();
    tree.getRoot().addChildren("str");
    tree.getRoot().addChildren("test");
    assertEquals(null,tree.getRoot().getChildren(0).getData());
    assertEquals("str",tree.getRoot().getChildren(1).getData());
    assertEquals("test",tree.getRoot().getChildren(2).getData());
    assertEquals(null,tree.getRoot().getChildren(3));
    assertEquals(null,tree.getRoot().getChildren(-1));
  }
  @Test
  public void testTree3(){
    Tree<String> tree = new Tree<String>();
    tree.getRoot().addChildren();
    tree.getRoot().getChildren(0).addChildren();
    tree.getRoot().getChildren(0).getChildren(0).addChildren();
    tree.getRoot().getChildren(0).getChildren(0).getChildren(0).addChildren();
    tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getChildren(0).addChildren();
    tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildren(0).addChildren();
    tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildren(0).addChildren();
    assertEquals(1,tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildrenSize());
    assertEquals(2,tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildrenSize());
    tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildren(0).addChildren();
    assertEquals(3,tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildrenSize());
    tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildren(2).setData("a string");
    assertEquals("a string",tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildren(2).getData());
  }
  @Test
  public void testTree4(){
    Tree<String> tree = new Tree<String>();
    tree.getRoot().addChildren("1");
    tree.getRoot().getChildren(0).addChildren("2");
    tree.getRoot().getChildren(0).getChildren(0).addChildren("3");
    assertEquals(null,tree.getRoot().getData());
    assertEquals("1",tree.getRoot().getChildren(0).getData());
    assertEquals("2",tree.getRoot().getChildren(0).getChildren(0).getData());
    assertEquals("3",tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getData());
    assertEquals("2",tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getParent().getData());
    assertEquals("2",tree.getRoot().getChildren(0).getParent().getChildren(0).getChildren(0).getData());
    assertEquals("1",tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getParent().getParent().getData());
  }
}
