package fr.formiko.usuel.conversiondetype;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.ascii;

public class str{
  //bonus
  public static int nbrDeX(String s,char x){
    int xr=0;
    int lens = s.length();
    for (int i=0;i<lens ;i++ ) {
      if(s.charAt(i)==x){xr++;}
    }return xr;
  }
	//nouvelle partie :
	// convertion  :
  public static int sToI(String s){
		//System.out.print("\""+s+"\"");
    int lens = s.length();String s2="";
    if(lens>4){
      for(int i=0;i<lens;i++){
        int asci = ascii.aToAscii(s.charAt(i));
        if((asci>=48 && asci<=57) || asci==45){ // si c'est un - ou un chiffre on le garde.
          s2 = s2 + s.charAt(i);
        }
      }
      s=s2;
    }
    try {
      return Integer.parseInt(s);
    }catch (Exception e) {
      erreurConversion("String To int",s);
      return -1;
    }
  }
  public static long sToL(String s){
		//System.out.print("\""+s+"\"");
    int lens = s.length();String s2="";
    if(lens>4){
      for(int i=0;i<lens;i++){
        int asci = ascii.aToAscii(s.charAt(i));
        if((asci>=48 && asci<=57) || asci==45){ // si c'est un - ou un chiffre on le garde.
          s2 = s2 + s.charAt(i);
        }
      }
      s=s2;
    }
    try {
      return Long.parseLong(s);
    }catch (Exception e) {
      erreurConversion("String To long",s);
      return -1;
    }
  }
  public static String iToS(int x){
    return ""+x;
  }
  public static byte iToBy(int x){
    if(x>127 || x<-128){ erreurConversion("int To byte",x+"");}
    return (byte) x;
  }
  public static boolean iToB(int b){
    if (b==0){ return false;}
    if (b==1){ return true;}
    erreurConversion("int To Boolean",b+"");
    return true;
  }
  public static boolean sToB(String s){
    if("true".equals(s)){ return true;}
    if("false".equals(s)){ return false;}
    return iToB(sToI(s));
  }
  public static byte sToBy(String s){
    return iToBy(sToI(s));
  }
  public static void erreurConversion(String xToY, String s){
    erreur.erreur(g.get("str",1,"Impossible d'effectuer une des conversions") +" "+ xToY +" "+g.get("str",2,"correctement")+" : "+s,"str.");
  }
  //tableaux
  public static int[] sToI(String ts[]){
    int lents=ts.length;
    int tr[]=new int[lents];
    for (int i=0;i<lents ;i++ ) {
      tr[i]=sToI(ts[i]);
    }
    return tr;
  }
  public static String[] iToS(int ts[]){
    int lents=ts.length;
    String tr[]=new String[lents];
    for (int i=0;i<lents ;i++ ) {
      tr[i]=iToS(ts[i]);
    }
    return tr;
  }

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

  /*
	public static String [] str(int t[]){
		int lent = len(t);
		String tr [] = new String [lent];
		for(int i=0; i<lent; i++){
			tr[i]=str(t[i]);
		}
		return tr;
	}
	public static String [][] str(int t[][]){
		int lent = len(t);
		String tr[][] = new String [lent][];
		for(int i=0; i<lent; i++){
			tr[i]=str(t[i]);
		}
		return tr;
	}
	public static String [][][] str(int t[][][]){
		int lent = len(t);
		String tr[][][] = new String [lent][][];
		for(int i=0; i<lent; i++){
			tr[i]=str(t[i]);
		}
		return tr;
	}*/
}