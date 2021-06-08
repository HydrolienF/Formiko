package fr.formiko.usuel.types;

import fr.formiko.usuel.ascii;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.tableau;

/**
*{@summary Types conversions from String}<br>
*@author Hydrolien
*@version 1.1
*/
public class str{
  // Fonctions propre -----------------------------------------------------------
  /**
   *{@summary get the number of char x in a String}
   *@param s String were to search.
   *@param x char to search on s.
   *@return number of char x in s.
   *@version 1.1
   */
  public static int nbrDeX(String s,char x){
    int xr=0;
    int lens = s.length();
    for (int i=0;i<lens ;i++ ) {
      if(s.charAt(i)==x){xr++;}
    }return xr;
  }
  /**
   *{@summary is subS on the String}
   *@param s String were to search.
   *@param subS String to search on s.
   *@param x 0=s should starts with subS, 1=s should contain subS, 2=s should end with subsS, 3=s should be equals to subS.
   *@return true if it contain subS
   *@version 1.2
   */
  public static boolean contient(String s,String subS, byte x){
    //les cas d'erreur.
    if(s==null){return false;}
    if(subS==null){return false;}
    if(subS.equals("")){return true;}
    if(x<0 || x>4){return false;}
    if(s.length()<subS.length()){return false;}

    //if(x==3){return s.equals(subS);}
    if(x==1){return s.contains(subS);}//quelque part
    int lensubS = subS.length();
    if(x==0){
      s = s.substring(0,lensubS);//au début
    }else if(x==2){
      int lens = s.length();
      s = s.substring(lens-lensubS,lens);//a la fin
    }
    return s.equals(subS);
  }public static boolean contient(String s, String s2, int x){return contient(s,s2,iToBy(x));}
  public static boolean contient(String s,String s2){return contient(s,s2,1);}
  /**
   *{@summary add fin at the end off s, if s does not arlerdy end by fin.}<br>
   *@param s main String.
   *@param fin String to add on s.
   *@return s with fin at the end.
   *@version 1.2
   */
  public static String addALaFinSiNecessaire(String s, String fin){
    if(!contient(s,fin,2)){s+=fin;}
    return s;
  }

  /**
  *{@summary Delete forbidden char in the array t.}<br>
  *@param s the String were to delete forbidden char.
  *@param t the array were forbidden char are.
  *@version 1.3
  */
  public static String filtreCharInterdit(String s, char t[]){
    if(s==null){return null;}
    String r = "";
    int len = s.length();
    for (int i=0;i<len ;i++ ) {
      char c = s.charAt(i);
      if(!tableau.contient(t,c)){ r=r+c;}
    }
    return r;
  }
  /**
  *{@summary Delete forbidden char depending of the os.}<br>
  *if os is not define windows char will be deleted.
  *@param s the String were to delete forbidden char.
  *@version 1.3
  */
  public static String filtreCharInterdit(String s){
    if(s==null){return null;}
    char w [] = {'<', '>', ':', '\"', '/', '\\', '|', '?', '*'};
    //char ml [] = {':','/','\\'};
    //if(Main.getOs()==null || Main.getOs().isWindows()){
      return filtreCharInterdit(s,w);
    //}
    //return filtreCharInterdit(s,ml);
  }public static String sToFileName(String s){ return filtreCharInterdit(s);}
  /**
  *{@summary Transform a String to a directory name aviable on every os.}<br>
  *If last / is missing it will be add.
  *If there is a 1a / it will be delete.
  *If there is \ they will be transform by /.
  *@param s the String to transform to a directory name.
  *@version 1.38
  */
  public static String sToDirectoryName(String s){
    //TODO test
    if(s==null){return null;}
    if(s.equals("")){return "";}
    char w [] = {'<', '>', '\"', '|', '?', '*'};//, ':', '\\'
    s = s.replace('\\','/');
    s = filtreCharInterdit(s,w);
    if(s.charAt(0)=='/'){s=s.substring(1);}
    s = addALaFinSiNecessaire(s,"/");
    return s;
  }
  /**
  *{@summary Transform the first char of a String to the toUpperCase char.}<br>
  *if s is "" or null nothing will be done.
  *@param s the String to transform.
  *@version 1.7
  */
  public static String sToSMaj(String s){
    if(s==null){return null;}
    if(s.length()>1){
      return s.substring(0,1).toUpperCase()+s.substring(1); // 1a char en majuscule.
    }else if(s.length()==1){
      return s.substring(0,1).toUpperCase();
    }else{
      return s;
    }
  }
  /**
  *{@summary Transform the first char of a String to the toLowerCase char.}<br>
  *if s is "" or null nothing will be done.
  *@param s the String to transform.
  *@version 1.39
  */
  public static String sToSMin(String s){
    if(s==null){return null;}
    if(s.length()>1){
      return s.substring(0,1).toLowerCase()+s.substring(1);
    }else if(s.length()==1){
      return s.substring(0,1).toLowerCase();
    }else{
      return s;
    }
  }
  /**
  *{@summary Return true if 1a char is an english maj char.}<br>
  *only A to Z without accent char are ok.
  *@param s the String to test.
  *@version 1.39
  */
  public static boolean isMaj(String s){
    if(s==null || s.length()<1){return false;}
    if(s.charAt(0)>64 && s.charAt(0)<91){return true;}
    return false;
  }

	//nouvelle partie :
	// conversion  :
  /**
  *{@summary From String to int}
  *return -1 if conversion fail.
  *@version 1.39
  */
  public static int sToI(String s){
    return sToI(s,-1);
  }
  /**
  *{@summary From String to int}
  *return Default value if conversion fail.
  *@version 1.39
  */
  public static int sToI(String s, int iDefault){
    try {
      return (int) sToLThrows(s);
    }catch (Exception e) {
      erreurConversion("String To long",s);
      return iDefault;
    }
  }
  /**
  *{@summary From String to double}
  *return -1 if conversion fail.
  *@version 1.39
  */
  public static double sToD(String s){
    return sToD(s,-1.0);
  }
  /**
  *{@summary From String to double}
  *return Default value if conversion fail.
  *@version 1.39
  */
  public static double sToD(String s, Double dDefault){
    try {
      return Double.parseDouble(s);
    }catch (Exception e) {
      erreurConversion("String To Double",s);
      return dDefault;
    }
  }
  /**
  *{@summary From String to int}
  *Throw a Exception trows if conversion fail.
  *@version 1.1
  */
  public static long sToLThrows(String s) throws Exception {//on ne tolère que les espace les chiffres et les moins.
    int lens = s.length();String s2="";
    if(nbrDeX(s,' ')>0){
      for(int i=0;i<lens;i++){
        int asci = ascii.aToAscii(s.charAt(i));
        if((asci>=48 && asci<=57) || asci==45){// si c'est un - ou un chiffre on le garde.
          s2 = s2 + s.charAt(i);
        }else if(asci!=32){;throw new Exception();}//au 1 a char nom autorisé. //TODO change exeption to a special exeption.
      }
      s=s2;
    }
    return Long.parseLong(s);
  }
  /**
  *{@summary From String to long}
  *return -1 if conversion fail.
  *@version 1.1
  */
  public static long sToL(String s){
    try {
      return sToLThrows(s);
    }catch (Exception e) {
      erreurConversion("String To long",s);
      return -1;
    }
  }

  /**
  *{@summary From int to String}
  *@version 1.1
  */
  public static String iToS(int x){
    return ""+x;
  }
  /**
  *{@summary From int to byte}
  *return the max or the min if conversion fail.
  *@version 1.1
  */
  public static byte iToBy(int x){
    if(x>127){ erreurConversion("int To byte",x+"");x=127;}
    if(x<-128){ erreurConversion("int To byte",x+"");x=-128;}
    return (byte) x;
  }
  /**
  *{@summary From int to boolean}
  *return true if conversion fail.
  *@version 1.1
  */
  public static boolean iToB(int b){
    if (b==0){ return false;}
    if (b==1){ return true;}
    erreurConversion("int To Boolean",b+"");
    return true;
  }
  /**
  *{@summary From String to boolean}
  *boolean can be "true", "false" or "1", "0".
  *@version 1.1
  */
  public static boolean sToB(String s){
    if("true".equals(s)){ return true;}
    if("false".equals(s)){ return false;}
    return iToB(sToI(s));
  }
  /**
  *{@summary From String to byte}
  *@version 1.39
  */
  public static byte sToBy(String s, int iDefault){
    return iToBy(sToI(s));
  }
  /**
  *{@summary From String to byte}
  *@version 1.39
  */
  public static byte sToBy(String s){
    return sToBy(s,-1);
  }
  /**
  *{@summary special error for conversion}
  *return -1 if conversion fail.
  *@version 1.1
  */
  public static void erreurConversion(String xToY, String s){
    erreur.erreur(g.get("str",1,"Impossible d'effectuer une des conversions") +" "+ xToY +" "+g.get("str",2,"correctement")+" : "+s,5);
  }
  //tableaux
  /**
  *{@summary From int to String}
  *@version 1.1
  */
  public static int[] sToI(String ts[]){
    int lents=ts.length;
    int tr[]=new int[lents];
    for (int i=0;i<lents ;i++ ) {
      tr[i]=sToI(ts[i]);
    }
    return tr;
  }
  /**
  *{@summary From String to int}
  *file a case with -1 if conversion fail.
  *@version 1.1
  */
  public static String[] iToS(int ts[]){
    int lents=ts.length;
    String tr[]=new String[lents];
    for (int i=0;i<lents ;i++ ) {
      tr[i]=iToS(ts[i]);
    }
    return tr;
  }

	/*//anciène partie.
	public static String str(int s){
		return s+"";
	}*/
  //anciène partie.
  //Conversion de type.
  public static String str(int s){
    return String.valueOf(s);
  }
  public static String str(String s){
    return s;
  }
  public static String str(char c){
    return String.valueOf(c);
  }
  public static String str(double x){
    return String.valueOf(x);
  }
  public static String str(float x){
    return String.valueOf(x);
  }
}
