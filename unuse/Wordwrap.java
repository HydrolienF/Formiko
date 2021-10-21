import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

public class Wordwrap extends JFrame {

    public Wordwrap() {
        String s = "I have a long string which doesn't fit to the JPanel i am putting it. text is logger than the JPanel width. I can not put \n to the string to break the string, in fact i don't have the control over the length and content of the string. It user inputted string. What i want to do is when i am putting the text on JPanel i want any text that doesn't fit in to the JPanel to flow in to the next Line.";

        JTextPane textPanel = new JTextPane();
        textPanel.setText(s);
        textPanel.setPreferredSize(new Dimension(500, 100));

        JPanel p = new JPanel();
        p.add(textPanel);
        getContentPane().add(p);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        pack();
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                new Wordwrap();
            }
        });
    }
}
