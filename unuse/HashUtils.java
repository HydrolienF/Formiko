import java.awt.GraphicsEnvironment;

/**
 * Lister les polices de caractère installées sur le poste
 * http://www.fobec.com/java/984/lister-polices-caractere-installees-sur-poste.html
 * Axel 2010
 */
public class HashUtils {

    /**
     * Exemple
     * @param args
     */
    public static void main(String[] args) {
       GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontList = ge.getAvailableFontFamilyNames();
        System.out.println("Polices installées sur le poste:");
        System.out.println("---------------------------------");
        for (int i = 0; i < fontList.length; ++i) {
            System.out.println(fontList[i]);
        }
    }
}
