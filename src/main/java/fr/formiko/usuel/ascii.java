package fr.formiko.usuel;

import fr.formiko.usuel.types.str;

public class ascii{
  public static char asciiToA(int x){
    char c = (char) x;
    return c;
  }
  public static int aToAscii(char a){
    int r = (int) a;
    return r;
  }
  public static String getNuméroationEnAbcd(int i){
    String sr ="";
    i--;
    if (i<=25){
       sr = str.str(asciiToA(65+i));
    }else if (i<= 26*26){
      int q = i/26;
      int r = i%26;
      sr = str.str(asciiToA(64+q)) + str.str(asciiToA(64+r+1));
    /*}else{ // A continué si on souhaite affiché des liste de plus de 26*26 éléments.
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
  *@version 2.11
  */
  public static String stringToAscii(String s){
    String sr="";
    for (char c : s.toCharArray()) {
      sr+=aToAscii(c)+" ";
    }
    sr+="/n";
    return sr;
  }
}
