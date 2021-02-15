import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import fr.formiko.test.usuel.math.mathTest;

public class TestRunner {
public static void main(String[] args) {
  System.out.println("lancement du main de TestRunner");//@a
  Result result = JUnitCore.runClasses(mathTest.class);

  for (Failure failure : result.getFailures()) {
    System.out.println(failure.toString());
  }

  System.out.println(result.wasSuccessful());
  }
}
