package fr.formiko.usuel;

import org.junit.jupiter.api.Test;

import fr.formiko.usuel.Tree;
import fr.formiko.usuel.tests.TestCaseMuet;

import java.io.File;


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
  @Test
  public void testFolderToTree(){
    int x = getId();
    File dir = new File("testDir"+x+"/testDir2/testDir3/");
    dir.mkdirs();
    dir = new File("testDir"+x+"/testDir2/testDir3bis/");
    dir.mkdirs();
    File dirTested = new File("testDir"+x);
    Tree<?> tree = Tree.folderToTree(dirTested);
    assertEquals("0(0(0,1))",tree.toString());
    dir = new File("testDir"+x+"/testDir2/testDir3ter/");
    dir.mkdirs();
    tree = Tree.folderToTree(dirTested);
    assertEquals("0(0(0,1,2))",tree.toString());
    dir = new File("testDir"+x+"/testDir2bis/");
    dir.mkdirs();
    tree = Tree.folderToTree(dirTested);
    assertEquals("0(0(0,1,2),1)",tree.toString());
    assertTrue(fichier.deleteDirectory(dirTested));
  }
  @Test
  public void testFolderToTree2(){
    int x = getId();
    File dir = new File("testDir"+x+"/testDir2/testDir3/");
    dir.mkdirs();
    dir = new File("testDir"+x+"/testDir2/testDir3bis/");
    dir.mkdirs();
    File dirTested = new File("testDir"+x);
    Tree<?> tree = Tree.folderToTree("testDir"+x);
    assertEquals("0(0(0,1))",tree.toString());
    assertTrue(fichier.deleteDirectory(dirTested));
  }
  @Test
  public void testFolderToTree3(){
    int x = getId();
    File dirTested = new File("testDir"+x);
    fichier.deleteDirectory(dirTested);
    File dir = new File("testDir"+x+"/testDir2/testDir3/");
    dir.mkdirs();
    dir = new File("testDir"+x+"/testDir2/testDir3bis/");
    dir.mkdirs();
    try {
      File file = new File("testDir"+x+"/testDir2/testDir3bis/bka.txt");
      file.createNewFile();
    }catch (Exception e) {
      assertTrue(false);
    }
    Tree<?> tree = Tree.folderToTree("testDir"+x);
    assertEquals("0(0(0,1))",tree.toString());
    assertTrue(fichier.deleteDirectory(dirTested));
  }
  @Test
  public void testFolderToTree4(){
    int x = getId();
    File dirTested = new File("testDir"+x);
    fichier.deleteDirectory(dirTested);
    File dir = new File("testDir"+x+"/testDir2/testDir3/");
    dir.mkdirs();
    dir = new File("testDir"+x+"/testDir2/testDir3bis/");
    dir.mkdirs();
    try {
      File file = new File("testDir"+x+"/testDir2/testDir3bis/bka.txt");
      file.createNewFile();
      file = new File("testDir"+x+"/testDir2/testDir3bis/imag.png");
      file.createNewFile();
    }catch (Exception e) {
      assertTrue(false);
    }
    Tree<?> tree = Tree.folderToTree("testDir"+x);
    System.out.println("containt image");//@a
    System.out.println();System.out.println();
    assertEquals("0(0(0,1))",tree.toString());
    assertTrue(fichier.deleteDirectory(dirTested));
  }

}
