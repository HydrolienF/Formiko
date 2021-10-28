package fr.formiko.usuel.structures;

import org.junit.jupiter.api.Test;

import fr.formiko.usuel.structures.Tree;
import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usuel.fichier;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class TreeTest extends TestCaseMuet {

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
    assertEquals(null,tree.getRoot().getChildren(0).getContent());
    assertEquals("str",tree.getRoot().getChildren(1).getContent());
    assertEquals("test",tree.getRoot().getChildren(2).getContent());
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
    tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildren(2).setContent("a string");
    assertEquals("a string",tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildren(0).getChildren(2).getContent());
  }
  @Test
  public void testTree4(){
    Tree<String> tree = new Tree<String>();
    tree.getRoot().addChildren("1");
    tree.getRoot().getChildren(0).addChildren("2");
    tree.getRoot().getChildren(0).getChildren(0).addChildren("3");
    assertEquals(null,tree.getRoot().getContent());
    assertEquals("1",tree.getRoot().getChildren(0).getContent());
    assertEquals("2",tree.getRoot().getChildren(0).getChildren(0).getContent());
    assertEquals("3",tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getContent());
    assertEquals("2",tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getParent().getContent());
    assertEquals("2",tree.getRoot().getChildren(0).getParent().getChildren(0).getChildren(0).getContent());
    assertEquals("1",tree.getRoot().getChildren(0).getChildren(0).getChildren(0).getParent().getParent().getContent());
  }

  @Test
  public void testFore(){
    Tree<String> tree = new Tree<String>();
    tree.getRoot().addChildren("1");
    tree.getRoot().addChildren("1b");
    tree.getRoot().addChildren("1t");
    tree.getRoot().getChildren(0).addChildren("2");
    tree.getRoot().getChildren(0).getChildren(0).addChildren("3");
    tree.getRoot().getChildren(0).addChildren("2b");
    tree.getRoot().getChildren(1).addChildren("2t");
    int k=0;
    for (String s : tree) {
      k++;
    }
    assertEquals(8,k);
  }
  @Test
  public void testFore2(){
    Tree<String> tree = new Tree<String>();
    tree.getRoot().addChildren("1");
    tree.getRoot().addChildren("1b");
    tree.getRoot().getChildren(0).addChildren("2");
    tree.getRoot().getChildren(0).getChildren(0).addChildren("3");
    tree.getRoot().getChildren(0).getChildren(0).getChildren(0).addChildren("4");
    int k=0;
    for (String s : tree) {
      k++;
    }
    assertEquals(6,k);
  }
  @Test
  public void testCopyStructure(){
    Tree<String> tree = new Tree<String>();
    assertEquals("0",tree.toString());
    Tree<String> tree2 = tree.copyStructure();
    assertEquals("0",tree2.toString());
  }
  @Test
  public void testCopyStructure2(){
    Tree<String> tree = new Tree<String>();
    tree.getRoot().addChildren();
    tree.getRoot().addChildren();
    tree.getRoot().getChildren(0).addChildren();
    tree.getRoot().getChildren(0).getChildren(0).addChildren();
    tree.getRoot().getChildren(0).getChildren(0).getChildren(0).addChildren();
    assertEquals("0(0(0(0(0))),1)",tree.toString());
    Tree<String> tree2 = tree.copyStructure();
    assertEquals("0(0(0(0(0))),1)",tree2.toString());
  }
  @Test
  public void testCopyStructure3(){
    Tree<String> tree = new Tree<String>();
    tree.getRoot().addChildren("1");
    tree.getRoot().addChildren("1b");
    tree.getRoot().getChildren(0).addChildren();
    tree.getRoot().getChildren(0).getChildren(0).addChildren("3");
    tree.getRoot().getChildren(0).getChildren(0).getChildren(0).addChildren();
    assertEquals("0(0I(0(0I(0))),1I)",tree.toString());
    Tree<String> tree2 = tree.copyStructure();
    assertEquals("0(0(0(0(0))),1)",tree2.toString());
  }

}
