import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class SwingWorkerDemo extends JFrame {

    private JTextArea textArea;
    private JTextField textField;
    private JProgressBar progressBar;

    class MonSwingWorker extends SwingWorker<Integer, String> {

        public MonSwingWorker() {
            /* On ajoute un écouteur de barre de progression. */
            addPropertyChangeListener(new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    if ("progress".equals(evt.getPropertyName())) {
                        progressBar.setValue((Integer) evt.getNewValue());
                    }
                }
            });
        }

        @Override
        public Integer doInBackground() {
            File userDir = new File(System.getProperty("user.dir"));
            return getNombreDeFichiers(userDir, 0, 100);
        }

        /* Compte le nombre de fichiers du répertoire utilisateur. */
        private int getNombreDeFichiers(File dir, double progressStart, double progressEnd) {
            File[] files = dir.listFiles();
            int nb = 0;
            if (files.length > 0) {
                /* Le calcul de l'avancement du traitement n'a que peu d'importance pour l'exemple. */
                double step = (progressEnd - progressStart) / files.length;

                for (int i = 0; i < files.length; i++) {
                    File f = files[i];
                    double progress = progressStart + i * step;

                    /* Transmet la nouvelle progression. */
                    setProgress(Math.min((int) progress, 100));

                    /*
                     * Ajout d'un temps d'attente pour observer les changements à l'échelle
                     * "humaine".
                     */
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (f.isDirectory()) {
                        /* Publication du répertoire trouvé. */
                        publish("Exploration du répertoire " + f.getAbsolutePath() + "...");
                        nb += getNombreDeFichiers(f, progress, progress + step);
                    } else {
                        /* Publication du fichier trouvé. */
                        publish(f.getAbsolutePath());
                        nb++;
                    }
                }
            }
            return nb;
        }

        @Override
        protected void process(List<String> strings) {
            /* Affichage des publications reçues dans le textarea. */
            for (String s : strings)
                textArea.append(s + '\n');
        }

        @Override
        protected void done() {
            try {
                /* Le traitement est terminé. */
                setProgress(100);
                /*
                 * À la fin du traitement, affichage du nombre de fichiers parcourus dans le
                 * textfield.
                 */
                textField.setText(String.valueOf(get()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public SwingWorkerDemo() {
        /* Construction de l'interface graphique. */
        super("SwingWorkerDemo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        textArea = new JTextArea(12, 40);
        textArea.setEnabled(false);
        textField = new JTextField(5);
        textField.setEnabled(false);
        progressBar = new JProgressBar();
        JPanel content = new JPanel(new BorderLayout());
        content.add(new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        JPanel south = new JPanel(new BorderLayout());
        south.add(progressBar);
        south.add(textField, BorderLayout.EAST);
        content.add(south, BorderLayout.SOUTH);
        setContentPane(content);
        pack();
        setLocation(100, 100);
        setVisible(true);
    }

    public static void main(String... args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                /* Démarrage de l'interface graphique et du SwingWorker. */
                SwingWorkerDemo demo = new SwingWorkerDemo();
                MonSwingWorker swingWorker = demo.new MonSwingWorker();
                swingWorker.execute();
            }
        });
    }

}
