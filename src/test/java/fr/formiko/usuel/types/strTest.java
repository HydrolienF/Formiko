package fr.formiko.usuel.types;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Os;
import fr.formiko.tests.TestCaseMuet;

public class strTest extends TestCaseMuet{

  // FUNCTIONS -----------------------------------------------------------------
  @Test
  public void testIsVersionOver(){
    assertTrue(str.isVersionOver("1.0.0","0.0.0"));
    assertTrue(str.isVersionOver("0.1.0","0.0.0"));
    assertTrue(str.isVersionOver("0.0.1","0.0.0"));
    assertTrue(str.isVersionOver("1.2.3","0.0.0"));
    assertTrue(str.isVersionOver("2.10.1","2.10.0"));
    assertTrue(!str.isVersionOver("0.0.0","1.0.0"));
    assertTrue(!str.isVersionOver("0.0.0","1.4.67"));
    assertTrue(!str.isVersionOver("0.0.0","1000000.0.0"));
    assertTrue(!str.isVersionOver("1.0.0","1.0.1"));
    assertTrue(!str.isVersionOver("1.0.0","1.0.0"));
  }
  @Test
  public void testIsVersionOver2(){
    assertTrue(!str.isVersionOver("1.0.0.0.0.0","1.0.0.0.0.0"));
    assertTrue(!str.isVersionOver("1","1"));
    assertTrue(str.isVersionOver("4","1"));
    assertTrue(!str.isVersionOver("10","2.7")); //0.10 < 2.7
    assertTrue(!str.isVersionOver("1","1.0"));
    assertTrue(str.isVersionOver("1.0","4"));
    assertTrue(str.isVersionOver("1.0.0.0.0.0","1.0.0.0.0"));
  }
  @Test
  public void testIsVersionOver3(){
    assertThrows(IllegalArgumentException.class, () -> str.isVersionOver("A","1"));
    assertThrows(IllegalArgumentException.class, () -> str.isVersionOver("4","4.0.£"));
    assertThrows(IllegalArgumentException.class, () -> str.isVersionOver("人1.1","1.1"));
  }
  @Test
  public void testNbrDeX(){
    String s = "est un E iueé";//la maj et le 'é' ne doivent pas compter
    assertEquals(2,str.nbrDeX(s,'e'));
    s = "一个小姜饼人一";
    assertEquals(2,str.nbrDeX(s,'一'));
    assertEquals(0,str.nbrDeX(s,' '));
  }
  @Test
  public void testContient(){
    assertTrue(str.contient("une grande chaine de char","chaine"));
    assertTrue(str.contient("une grande chaine de char","e c"));
    assertTrue(str.contient("une grande chaine de char","chaine",1));
    assertTrue(!str.contient("une grande chaine de char","chaine",0));
    assertTrue(!str.contient("une grande chaine de char","chaine",2));
    assertTrue(!str.contient("une grande chaine de char","chaine",3));
    assertTrue(!str.contient("une grande chaine de char","cp",1));

    assertTrue(str.contient("une grande chaine de char","u",0));
    assertTrue(!str.contient("une grande chaine de char","U",0));
    assertTrue(str.contient("une grande chaine de char","une",0));
    assertTrue(!str.contient("une grande chaine de char","Une",0));

    assertTrue(str.contient("une grande chaine de char","r",2));
    assertTrue(!str.contient("une grande chaine de char","R",2));
    assertTrue(str.contient("une grande chaine de char"," char",2));
    assertTrue(!str.contient("une grande chaine de char","  char",2));

    assertTrue(str.contient("une grande chaine de char","une grande chaine de char",3));
    assertTrue(!str.contient("une grande chaine de char","une grande chaine de chart",3));

    //des trucs simple d'erreur :
    assertTrue(!str.contient("ch","chaine"));
    assertTrue(str.contient("ch",""));
    assertTrue(!str.contient(null,"chaine"));
    assertTrue(!str.contient("ch",null));
    assertTrue(!str.contient("chaine x","chaine",-1));
    assertTrue(!str.contient("chaine x","chaine",5));
    assertTrue(!str.contient("chaine x","chaine",-2));
  }
  @Test
  public void testSToI(){
    assertEquals(2,str.sToI("2"));
    assertEquals(0,str.sToI("0"));
    assertEquals(2200,str.sToI("2200"));
    assertEquals(-3,str.sToI("-3"));
    assertEquals(2678,str.sToI("2678 "));
    assertEquals(2678,str.sToI("2 678"));
    assertEquals(2678,str.sToI("    2 678"));

    String s = null;
    assertEquals(-1,str.sToI(s));
    assertEquals(-1,str.sToI(""));
    assertEquals(-1,str.sToI("8tte"));
    assertEquals(-1,str.sToI("8 e"));
    assertEquals(-1,str.sToI("--8"));
    assertEquals(-1,str.sToI("-8-45"));
  }
  @Test
  public void testSToLThrows(){
    try {
      assertEquals(2,str.sToLThrows("2"));
      assertEquals(21,str.sToLThrows("2   1"));
      assertThrows(Exception.class, () -> {str.sToLThrows("t4");});
    }catch (Exception e) {assertTrue(false);}
  }
  @Test
  public void testfilterForbiddenChar(){
    String s = null;
    char t [] = {'é'};
    assertEquals(null,str.filterForbiddenChar(s,t));
    s = "wexrtcyuié&'-\\^é&('-è')'*$\"";
    assertEquals("wexrtcyui&'-\\^&('-è')'*$\"",str.filterForbiddenChar(s,t));
    char t2 [] = {'\"'};
    assertEquals("wexrtcyuié&'-\\^é&('-è')'*$",str.filterForbiddenChar(s,t2));
    char t3 [] = {};
    assertEquals("wexrtcyuié&'-\\^é&('-è')'*$\"",str.filterForbiddenChar(s,t3));
    char t4 [] = {'w','x'};
    assertEquals("ertcyuié&'-\\^é&('-è')'*$\"",str.filterForbiddenChar(s,t4));
  }
  @Test
  public void testfilterForbiddenCharInFile(){
    Main.setOs(new Os());
    String s = "unNomDeFichier";
    Main.getOs().setId((byte)0);
    assertEquals("unNomDeFichier",str.filterForbiddenChar(s));
    Main.getOs().setId((byte)1);
    assertEquals("unNomDeFichier",str.filterForbiddenChar(s));
    Main.getOs().setId((byte)2);
    assertEquals("unNomDeFichier",str.filterForbiddenChar(s));
    Main.getOs().setId((byte)-1);
    assertEquals("unNomDeFichier",str.filterForbiddenChar(s));

    s = "unNomDeFichier?";
    //os don't have an impact anymore.
    //Main.getOs().setId((byte)0);
    //assertEquals("unNomDeFichier?",str.filterForbiddenChar(s));
    Main.getOs().setId((byte)1);
    assertEquals("unNomDeFichier",str.filterForbiddenChar(s));

    s = "unNomDe/Fichier";
    Main.getOs().setId((byte)0);
    assertEquals("unNomDeFichier",str.filterForbiddenChar(s));
    Main.getOs().setId((byte)1);
    assertEquals("unNomDeFichier",str.filterForbiddenChar(s));

    s = "unNomDe/F*^ich|er";
    //Main.getOs().setId((byte)0);
    //assertEquals("unNomDeF*^ich|er",str.filterForbiddenChar(s));
    Main.getOs().setId((byte)1);
    assertEquals("unNomDeF^icher",str.filterForbiddenChar(s));

  }

  @Test
  public void testSToDirectoryName(){
    String s = null;
    assertEquals(null,str.sToDirectoryName(s));
    s = "";
    assertEquals("",str.sToDirectoryName(s));
    s = "u";
    assertEquals("u/",str.sToDirectoryName(s));
    s = "unNomDeFichier";
    assertEquals("unNomDeFichier/",str.sToDirectoryName(s));
    s = "unNomDeFichier/";
    assertEquals("unNomDeFichier/",str.sToDirectoryName(s));
    s = "unNomD/eFichier";
    assertEquals("unNomD/eFichier/",str.sToDirectoryName(s));
    s = "unNomD/eFichier/";
    assertEquals("unNomD/eFichier/",str.sToDirectoryName(s));
    s = "unNomD/*?eFichier";
    assertEquals("unNomD/eFichier/",str.sToDirectoryName(s));
    s = "un!!??NomD/eFichier";
    assertEquals("un!!NomD/eFichier/",str.sToDirectoryName(s));
  }

  @Test
  public void testSToSMaj(){
    String s = null;
    assertEquals(null,str.sToSMaj(s));
    assertEquals("",str.sToSMaj(""));
    assertEquals("M",str.sToSMaj("M"));
    assertEquals("M",str.sToSMaj("m"));
    assertEquals(" un mot",str.sToSMaj(" un mot"));
    assertEquals("Un mot",str.sToSMaj("un mot"));
    assertEquals(" un mot",str.sToSMaj(" un mot"));
    assertEquals("择",str.sToSMaj("择"));
    assertEquals("Ĉ",str.sToSMaj("ĉ"));
    assertEquals("Ĉ",str.sToSMaj("Ĉ"));
  }

  @Test
  public void testSToSMin(){
    String s = null;
    assertEquals(null,str.sToSMin(s));
    assertEquals("",str.sToSMin(""));
    assertEquals("m",str.sToSMin("M"));
    assertEquals("m",str.sToSMin("m"));
    assertEquals(" un mot",str.sToSMin(" un mot"));
    assertEquals("un mot",str.sToSMin("Un mot"));
    assertEquals("un mot",str.sToSMin("un mot"));
    assertEquals("择",str.sToSMin("择"));
    assertEquals("ĉ",str.sToSMin("ĉ"));
    assertEquals("ĉ",str.sToSMin("Ĉ"));
  }

  @Test
  public void testIsMaj(){
    assertTrue(!str.isMaj(""));
    assertTrue(!str.isMaj(" "));
    assertTrue(!str.isMaj("7"));
    assertTrue(!str.isMaj("a"));
    assertTrue(!str.isMaj("a O"));
    assertTrue(!str.isMaj("pOOOOOOOOOOOOOOOOOOOOOOO000"));
    assertTrue(str.isMaj("A"));
    assertTrue(str.isMaj("Z"));
    assertTrue(str.isMaj("O"));
    assertTrue(str.isMaj("Ok"));
    assertTrue(str.isMaj("OK c'est bon"));
    assertTrue(!str.isMaj("Ĉ"));//only A-Z without accent char are ok.
  }
}
