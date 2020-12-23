package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.test.TestCaseMuet;
import org.junit.Test;
import fr.formiko.usuel.tableau;

public class TempsTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testmsToTimeLongArray(){
    //0
    assertEquals("0 0 0 0 0",tableau.tableauToString(Temps.msToTimeLongArray(0,true)));
    //error
    assertEquals("0 0 0 0 -1",tableau.tableauToString(Temps.msToTimeLongArray(-1,true)));
    assertEquals("0 0 0 0 -1",tableau.tableauToString(Temps.msToTimeLongArray(-678,true)));
    //<1000
    assertEquals("0 0 0 0 200",tableau.tableauToString(Temps.msToTimeLongArray(200,true)));
    assertEquals("0 0 0 0 999",tableau.tableauToString(Temps.msToTimeLongArray(999,true)));
    //s
    assertEquals("0 0 0 3 200",tableau.tableauToString(Temps.msToTimeLongArray(3200,true)));
    assertEquals("0 0 0 1 999",tableau.tableauToString(Temps.msToTimeLongArray(1999,true)));
    //min
    assertEquals("0 0 2 1 999",tableau.tableauToString(Temps.msToTimeLongArray(60000*2+1999,true)));
    //h
    assertEquals("0 5 2 10 120",tableau.tableauToString(Temps.msToTimeLongArray((60000*60*5)+(60000*2)+10120,true)));
    //d
    assertEquals("1 5 2 10 120",tableau.tableauToString(Temps.msToTimeLongArray((60000*60*(5+24))+(60000*2)+10120,true)));
  }

}
