package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.conversiondetype.str;

public class ascii{
  /*public static int ascii(char c){
    int x = Character.toInt(c);
    return x;
  }*/
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
}
