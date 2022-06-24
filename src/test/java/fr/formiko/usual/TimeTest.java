package fr.formiko.usual;


import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.usual.Time;
import fr.formiko.usual.tableau;
import fr.formiko.tests.TestCaseMuet;

public class TimeTest extends TestCaseMuet{

  // FUNCTIONS -----------------------------------------------------------------
  @Test
  public void testMsToTimeLongArray(){
    //0
    assertEquals("0 0 0 0 0",tableau.tableauToString(Time.msToTimeLongArray(0,true)));
    //error
    assertEquals("0 0 0 0 -1",tableau.tableauToString(Time.msToTimeLongArray(-1,true)));
    assertEquals("0 0 0 0 -1",tableau.tableauToString(Time.msToTimeLongArray(-678,true)));
    //<1000
    assertEquals("0 0 0 0 200",tableau.tableauToString(Time.msToTimeLongArray(200,true)));
    assertEquals("0 0 0 0 999",tableau.tableauToString(Time.msToTimeLongArray(999,true)));
    //s
    assertEquals("0 0 0 3 200",tableau.tableauToString(Time.msToTimeLongArray(3200,true)));
    assertEquals("0 0 0 1 999",tableau.tableauToString(Time.msToTimeLongArray(1999,true)));
    //min
    assertEquals("0 0 2 1 999",tableau.tableauToString(Time.msToTimeLongArray(60000*2+1999,true)));
    //h
    assertEquals("0 5 2 10 120",tableau.tableauToString(Time.msToTimeLongArray((60000*60*5)+(60000*2)+10120,true)));
    //d
    assertEquals("1 5 2 10 120",tableau.tableauToString(Time.msToTimeLongArray((60000*60*(5+24))+(60000*2)+10120,true)));
    //d but we only want h.
    assertEquals("0 30 2 10 120",tableau.tableauToString(Time.msToTimeLongArray((60000*60*(30))+(60000*2)+10120,false)));
  }
  @Test
  public void testMsToTime(){
    Os.setOs(new Os());
    Main.initialisation();
    Main.setLanguage(1);
    //0
    assertEquals("0ms",Time.msToTime(0));
    //1
    assertEquals("1ms",Time.msToTime(1));
    //1001
    assertEquals("1,001s",Time.msToTime(1001));
    //plein
    assertEquals("11j 13h",Time.msToTime(1000000000));

    Main.setLanguage(2);
    System.out.println(chargerLesTraductions.getRep());
    assertEquals("1.001s",Time.msToTime(1001));
    assertEquals("1.1s",Time.msToTime(1100));

    //test d'arrondit
    assertEquals("1s",Time.msToTime(1100,1,true));
    assertEquals("1s",Time.msToTime(1999,1,true));

    assertEquals("11d",Time.msToTime(1000000000,1,true));

    Main.setLanguage(1);
    //test avec différent nombre d'unité / dayOn true or false
    assertEquals("277h",Time.msToTime(1000000000,1,false));
    assertEquals("",Time.msToTime(1000000000,0,false));
    assertEquals("277h 46min 40s",Time.msToTime(1000000000,5,false));
    assertEquals("277h 46min 40,001s",Time.msToTime(1000000001,5,false));
    assertEquals("277h 46min 40s",Time.msToTime(1000000001,3,false));
    assertEquals("11j 13h 46min",Time.msToTime(1000000001,3,true));
    assertEquals("277h 46min",Time.msToTime(1000000001,2,false));
    assertEquals("277h",Time.msToTime(1000000001,1,false));
  }

}
