/*
 * Bonus Challenge (Optional): Mini Interpreter
Build a simple interpreter to evaluate let variable declarations 
and if conditions from input strings.
 */

import java.util.*;

public class MiniInterpreter {
    private final Map<String, Integer> variables = new HashMap<>();

    public void execute(String line) {
        line = line.trim();
        if (line.startsWith("let ")) {
            // let x = 5
            String[] parts = line.substring(4).split("=");
            String var = parts[0].trim();
            int val = evalExpr(parts[1].trim());
            variables.put(var, val);
        } else if (line.startsWith("if ")) {
            // if x > 3 then y = 10 else y = 20
            int thenIdx = line.indexOf("then");
            int elseIdx = line.indexOf("else");
            String cond = line.substring(3, thenIdx).trim();
            String thenPart = line.substring(thenIdx + 4, elseIdx).trim();
            String elsePart = line.substring(elseIdx + 4).trim();
            boolean condResult = evalCondition(cond);
            execute(condResult ? thenPart : elsePart);
        } else if (line.startsWith("print ")) {
            // print y
            String var = line.substring(6).trim();
            System.out.println(variables.getOrDefault(var, null));
        } else if (line.contains("=")) {
            // y = 10
            String[] parts = line.split("=");
            String var = parts[0].trim();
            int val = evalExpr(parts[1].trim());
            variables.put(var, val);
        }
    }

    // Evaluate simple integer expressions or variable names
    private int evalExpr(String expr) {
        expr = expr.trim();
        if (variables.containsKey(expr)) return variables.get(expr);
        return Integer.parseInt(expr);
    }

    // Evaluate simple conditions like x > 3, y == 10, etc.
    private boolean evalCondition(String cond) {
        cond = cond.trim();
        if (cond.contains(">=")) {
            String[] p = cond.split(">=");
            return evalExpr(p[0]) >= evalExpr(p[1]);
        } else if (cond.contains("<=")) {
            String[] p = cond.split("<=");
            return evalExpr(p[0]) <= evalExpr(p[1]);
        } else if (cond.contains(">")) {
            String[] p = cond.split(">");
            return evalExpr(p[0]) > evalExpr(p[1]);
        } else if (cond.contains("<")) {
            String[] p = cond.split("<");
            return evalExpr(p[0]) < evalExpr(p[1]);
        } else if (cond.contains("==")) {
            String[] p = cond.split("==");
            return evalExpr(p[0]) == evalExpr(p[1]);
        } else if (cond.contains("!=")) {
            String[] p = cond.split("!=");
            return evalExpr(p[0]) != evalExpr(p[1]);
        }
        throw new IllegalArgumentException("Invalid condition: " + cond);
    }

    // Example usage and test cases
    public static void main(String[] args) {
        MiniInterpreter interpreter = new MiniInterpreter();
        interpreter.execute("let x = 5");
        interpreter.execute("if x > 3 then y = 10 else y = 20");
        interpreter.execute("print y"); // Output: 10

        interpreter.execute("let a = 2");
        interpreter.execute("if a == 2 then b = 100 else b = 200");
        interpreter.execute("print b"); // Output: 100

        interpreter.execute("if x < 3 then z = 1 else z = 2");
        interpreter.execute("print z"); // Output: 2
    }
}

/*
Sample Output:
10
100
2
*/