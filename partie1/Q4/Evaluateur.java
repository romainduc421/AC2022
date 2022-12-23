import java.util.Stack;

public class Evaluateur {

  /**
   * evalue le programme modulo p
   * @param program : liste d'instructions
   * @param p : entier du modulo
   * @return
   */
  public static int evaluateur(String[] program, int p) {
    // Initialisation de la pile et de la variable de résultat
    Stack<Integer> stack = new Stack<>();
    int result = 0;

    // Pour chaque instruction du programme
    for (String instruction : program) {
      // Si l'instruction est un nombre, on le push dans la pile
      if (instruction.matches("\\d+")) {
        stack.push(Integer.parseInt(instruction) % p);
      }
      // Si l'instruction est une opération, on la réalise en utilisant les deux derniers éléments de la pile
      else if (instruction.matches("[+\\-*]")) {
        int x = stack.pop();
        int y = stack.pop();
        if (instruction.equals("+")) {
          stack.push((x + y)%p);
        } else if (instruction.equals("-")) {
          stack.push((x - y)%p);
        } else if (instruction.equals("*")) {
          stack.push(multiply(x, y, p));
        }
      }
    }

    // Si il reste un élément dans la pile, c'est le résultat de l'évaluation
    if (!stack.isEmpty()) {
      result = stack.pop();
    }

    return result;
  }

  // fonction d'aide dans le sujet
  /**
   * Compute (x*y) % p
   * @param x premiere operande
   * @param y deuxieme operande
   * @param p entier du modulo
   * @return
   */
  private static int multiply(int x, int y, int p) {
    int result = 0;
    while (y > 0) {
      //if y est impair, ajouter 'x' au résultat
      if ((y & 1) == 1) result = (result + x) % p;
      y >>= 1;
      x = (2 * x) % p;
    }
    return result;
  }

  /**
   * fonction detectant l'overflow
   * @param x
   * @param y
   * @return
   */
  private static boolean multiplyOverflow(int x, int y){
    return Integer.MAX_VALUE/x < y;
  }

  public static void main(String[] args){
    String[] prgm2 = new String[] {
      "46341", "4", "+", "4", "*"
    };
    String[] prgm = new String[]{
      "23170", "23171", "+", "46341","*"
    };
    String[] program = new String[]{
      "3", "4", "*", "5", "+"
    };

    System.out.println("46341^2 Overflow : "+ multiplyOverflow(46341, 46341));
    System.out.println("46340^2 Overflow : " + multiplyOverflow(46340, 46340));

    int p = 7;
    int result = evaluateur(program, p); //17 mod 7 = 3
    System.out.println(result);

    result = evaluateur(prgm, p);
    System.out.println(result); //1 car 2147488281 mod 7 = 1

    result = evaluateur(prgm2, p);
    System.out.println(result); //6 car 185380 mod 7 = 6
  }
}

