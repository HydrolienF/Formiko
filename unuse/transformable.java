import java.util.function.UnaryOperator;
class transformable {
  public static void main(String[] args) {
    String s = "stR";
    String s2 = s;
    s=s.transform(String::toLowerCase);
    System.out.println(s);
    System.out.println(s2);
  }
}
interface Transformable<T> {
  T getElement();
  void transform(UnaryOperator<T> trans);
}
