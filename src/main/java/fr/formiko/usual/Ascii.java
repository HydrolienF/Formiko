package fr.formiko.usual;

import fr.formiko.usual.types.str;

/**
*{@summary ASCII functions.}
*@lastEditedVersion 2.14
*/
public class Ascii {
  /**
  *{@summary Transform int into ascii char.}
  *@lastEditedVersion 2.14
  */
  public static char asciiToA(int x){
    char c = (char) x;
    return c;
  }
  /**
  *{@summary Transform char into ascii int.}
  *@lastEditedVersion 2.14
  */
  public static int aToAscii(char a){
    int r = (int) a;
    return r;
  }
  /**
  *{@summary Transform int into 1 or 2 letter that represent it.}
  *@lastEditedVersion 2.14
  */
  public static String intToLetterCode(int i){
    String sr ="";
    i--;
    if (i<=25){
       sr = str.str(asciiToA(65+i));
    }else if (i<= 26*26){
      int q = i/26;
      int r = i%26;
      sr = str.str(asciiToA(64+q)) + str.str(asciiToA(64+r+1));
    /*}else{ // To continue if we want to print list of more than 26*26 elements.
      int k=1; int i2 = i;
      while (i2>26){ // on compte combien de caractère il nous faut dans la légende "AAY".
        i2=i2/26; k++;
      }
      int t []= new int [k];
      for (int j=0;j<k ;j++ ) {
        int q = i/26;
        int r = i%26;
        t[j] =
      }*/
    }
    return  sr;
  }
  /**
  *{@summary Transform into an ascii String.}
  *@lastEditedVersion 2.14
  */
  public static String stringToAscii(String s){
    if(s==null){s="";}
    String sr="";
    boolean first=true;
    for (char c : s.toCharArray()) {
      if(!first){sr+=" ";}
      else{first=false;}
      sr+=aToAscii(c);
    }
    sr+="/n";
    return sr;
  }
}
