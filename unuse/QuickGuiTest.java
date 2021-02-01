import java.awt.event.*;
import javax.swing.*;

public class QuickGuiTest {

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                final JFrame frame = new JFrame("Test Frame");

                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                frame.setSize(600, 400);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        int result = JOptionPane.showConfirmDialog(frame, "Are you sure?");
                        if( result==JOptionPane.OK_OPTION){
                            // NOW we change it to dispose on close..
                            frame.setDefaultCloseOperation(
                                    JFrame.DISPOSE_ON_CLOSE);
                            frame.setVisible(false);
                            frame.dispose();
                        }
                    }
                });
                frame.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}
