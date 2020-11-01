package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.conversiondetype.str;

public class print{
  public static void print(){
		System.out.println();
	}
  public static void print(String s){
		System.out.println(s);
	}
	public static void print(int s){
		System.out.println(s);
	}
	public static void print(double s){
		System.out.println(s);
	}
	public static void print(char s){
		System.out.println(s);
	}
  public static void print(int [] t){
    for(int i=0; i<t.length; i++){
      System.out.print(str.str(t[i]) + " ");
    }
    print();
	}
}
