package fr.formiko.usual;

import org.junit.jupiter.api.Test;

import fr.formiko.usual.Time;
import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usual.tableau;

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
}
