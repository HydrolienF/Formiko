import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URI;

class HttpLink {
  public static void main(String[] args) {
    try {
      // "https://discord.gg/vqvfGzf"
      // "https://www.formiko.fr"
        Desktop.getDesktop().browse(new URI("https://www.formiko.fr"));
    } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
    }
  }
}
