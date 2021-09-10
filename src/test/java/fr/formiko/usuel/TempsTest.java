package fr.formiko.usuel;


import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.tests.TestCaseMuet;

public class TempsTest extends TestCaseMuet{

  // FUNCTIONS -----------------------------------------------------------------
  @Test
  public void testMsToTimeLongArray(){
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
    //d but we only want h.
    assertEquals("0 30 2 10 120",tableau.tableauToString(Temps.msToTimeLongArray((60000*60*(30))+(60000*2)+10120,false)));
  }
  @Test
  public void testMsToTime(){
    Main.initialisation();
    Main.setLangue(1);
    //0
    assertEquals("0ms",Temps.msToTime(0));
    //1
    assertEquals("1ms",Temps.msToTime(1));
    //1001
    assertEquals("1,001s",Temps.msToTime(1001));
    //plein
    assertEquals("11j 13h",Temps.msToTime(1000000000));

    Main.setLangue(2);
    assertEquals("1.001s",Temps.msToTime(1001));
    assertEquals("1.1s",Temps.msToTime(1100));

    //test d'arrondit
    assertEquals("1s",Temps.msToTime(1100,1,true));
    assertEquals("1s",Temps.msToTime(1999,1,true));

    assertEquals("11d",Temps.msToTime(1000000000,1,true));

    Main.setLangue(1);
    //test avec différent nombre d'unité / dayOn true or false
    assertEquals("277h",Temps.msToTime(1000000000,1,false));
    assertEquals("",Temps.msToTime(1000000000,0,false));
    assertEquals("277h 46min 40s",Temps.msToTime(1000000000,5,false));
    assertEquals("277h 46min 40,001s",Temps.msToTime(1000000001,5,false));
    assertEquals("277h 46min 40s",Temps.msToTime(1000000001,3,false));
    assertEquals("11j 13h 46min",Temps.msToTime(1000000001,3,true));
    assertEquals("277h 46min",Temps.msToTime(1000000001,2,false));
    assertEquals("277h",Temps.msToTime(1000000001,1,false));
  }

}
