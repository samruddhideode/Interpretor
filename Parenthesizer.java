package calc;

import java.util.*;
import java.util.regex.*;
import java.io.*;

public class Parenthesizer {

    public static ArrayList occurences(String substring, StringBuffer exp) {
        // returns a list of positions of all occurences for a given operator
        ArrayList<Integer> trigo = new ArrayList<>();
        int i = 0;
        boolean deleted = false;
        int n = exp.length();
        while (i != n) {
            int start = exp.indexOf(substring);
            if (start != -1) {
                if (deleted == true)
                    trigo.add(start + 3);
                else
                    trigo.add(start);
                exp.delete(start, start + 3);
                deleted = true;
            }
            i++;
        }
        return (trigo);

    }

    public static ArrayList occurences_operators(char op, StringBuffer exp) {
        // returns a list of positions of all occurences for a given operator
        ArrayList<Integer> trigo = new ArrayList<>();
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == op) {
                trigo.add(i);
            }
        }
        return (trigo);
    }

    public static ArrayList change_position(ArrayList<Integer> operators, int cnt) {
        // after insertion of opening and closing brackets, positions of the succesive
        // operators increment by 2
        ArrayList<Integer> chnged_operators = new ArrayList<>();
        for (int i = 0; i < operators.size(); i++) {
            chnged_operators.add(i, operators.get(i) + cnt);
        }
        return (chnged_operators);
    }
    // ------------------------------------------------------------------------------------------------

    public static int opening_pos(StringBuffer exp, ArrayList<Integer> pos) {
        // returns the position of opening bracket (for arithmetic operations)

        int op_position = pos.get(0);
        int opening = op_position;
        char prev = exp.charAt(opening - 1);
        if (Character.isDigit(prev) || Character.isAlphabetic(prev)) {
            // while prev character is a digit
            while (opening != 0) {
                if (Character.isDigit(exp.charAt(opening - 1)) || Character.isAlphabetic(exp.charAt(opening - 1)))
                    opening = opening - 1;
                else
                    break;
            }
            return opening;
        }

        else if (prev == ')') {
            Stack<Character> st = new Stack<>();
            while (opening != 0) {
                if (prev == ')')
                    st.push(')');
                if (prev == '(')
                    st.pop();
                if (st.isEmpty())
                    break;
                opening--;
                prev = exp.charAt(opening - 1);
            }
            return (opening);
        }
        return (opening);
    }

    public static int closing_pos(StringBuffer exp, ArrayList<Integer> pos) {
        // returns the position of closing bracket (for arithmetic operations)

        int op_position = pos.get(0);
        op_position++; // since we hv inserted (, op pos increments
        int closing = op_position;
        char next = exp.charAt(closing + 1);

        if (Character.isDigit(next) || Character.isAlphabetic(next)) {
            // while next character is a digit or an alphabet
            while (closing != exp.length() - 1) {
                if (Character.isDigit(exp.charAt(closing + 1)) || Character.isAlphabetic(exp.charAt(closing + 1)))
                    closing = closing + 1;
                else
                    break;
            }
            return (closing + 1);
        }

        else if (next == '(') {
            Stack<Character> st = new Stack<>();
            while (closing != exp.length() - 1) {
                if (next == '(')
                    st.push('(');
                if (next == ')')
                    st.pop();
                if (st.isEmpty())
                    break;
                closing++;
                next = exp.charAt(closing + 1);
            }
            return (closing + 1);
        }
        return (closing + 1);

    }

    public static void add_parenthesis(StringBuffer exp, ArrayList<Integer> pos, int cnt) {
        // code to add parenthesis around arithmetic operators (^,/,*,+,-)
        int opening;
        int closing;
        int n = pos.size();
        if (n == 0)
            return;
        else {
            opening = opening_pos(exp, pos);
            exp.insert(opening, "(");
            closing = closing_pos(exp, pos);
            exp.insert(closing, ")");
            pos.remove(0);
            pos = change_position(pos, cnt + 2);
            add_parenthesis(exp, pos, 0);
        }
    }

    public static StringBuffer check_for_operator(StringBuffer exp) {
        // looks for an operator in the expression and the its occurences
        // adds parenthesis for that operator

        char op = '^';
        ArrayList<Integer> exponential = occurences_operators(op, new StringBuffer(exp));
        add_parenthesis(exp, exponential, 0);
        // ---------------------------------------------------------------------
        op = '/';
        ArrayList<Integer> div = occurences_operators(op, new StringBuffer(exp));
        add_parenthesis(exp, div, 0);
        // ---------------------------------------------------------------------
        op = '*';
        ArrayList<Integer> mult = occurences_operators(op, new StringBuffer(exp));
        add_parenthesis(exp, mult, 0);
        // ---------------------------------------------------------------------
        op = '-';
        ArrayList<Integer> minus = occurences_operators(op, new StringBuffer(exp));
        add_parenthesis(exp, minus, 0);
        // ---------------------------------------------------------------------
        op = '+';
        ArrayList<Integer> plus = occurences_operators(op, new StringBuffer(exp));
        add_parenthesis(exp, plus, 0);
        return (exp);
    }

    // ---------------------------------------------------------------------------------------------
    // handles log(10)(20) functions
    public static void insert_log_closing(StringBuffer exp, ArrayList<Integer> log) {
        int closing = log.get(0) + 4;
        Stack<Character> st = new Stack<>();
        st.push('(');
        for (int i = 0; i < 2; i++) {
            closing++;
            while (closing != exp.length()) {
                if (exp.charAt(closing) == '(')
                    st.push('(');
                if (exp.charAt(closing) == ')')
                    st.pop();
                if (st.isEmpty())
                    break;
                closing++;
            }
        }
        exp.insert(closing, ')');
        log.remove(0);
    }

    public static void insert_opening(StringBuffer exp, ArrayList log, int cnt) {
        // code to insert opening and closing brackets
        int n = log.size();
        if (n == 0) // base condition
            return;
        else {
            exp = exp.insert((int) log.get(0), '(');
            insert_log_closing(exp, log); // gets position of the closing bracket
            log.remove(0);
            log = change_position(log, cnt + 2);
            insert_opening(exp, log, cnt + 2);
        }
    }

    public static void check_for_log(StringBuffer exp) {
        ArrayList<Integer> log = occurences("log", new StringBuffer(exp));
        int cnt = 0;
        while (!log.isEmpty()) {
            // inserts opening bracket
            exp.insert(log.get(0), "(");
            insert_log_closing(exp, log);
            change_position(log, cnt + 2);
        }
        insert_opening(exp, log, 0);
    }

    // ---------------------------------------------------------------------------------------------
    // handle sin cos tan and other user defined functions except for log
    public static void insert_func_brackets(StringBuffer exp, ArrayList<Integer> func, int cnt) {
        // code to insert opening and closing brackets
        while (func.size() != 0) {
            int opening = func.get(0);
            int closing = func.get(1) + 1;
            exp = exp.insert(opening, "(");
            exp = exp.insert(closing, ")");
            func.remove(0);
            func.remove(0);
            cnt = cnt + 2;
            func = change_position(func, cnt);
        }

    }

    public static void check_for_func(StringBuffer exp) {
        ArrayList<Integer> func = new ArrayList<>(); // list of occurences of functions
        Pattern p = Pattern.compile("\\w+\\((.*?)\\)");
        Matcher m = p.matcher(exp);
        while (m.find()) {
            String str = exp.substring(m.start(), m.end());
            if (!str.contains("log")) {
                func.add(m.start());
                func.add(m.end());
            }
        }
        insert_func_brackets(exp, func, 0);
    }

    // -----------------------------------------------------------------------------------------
    // preliminary checks
    public static void check_for_minus(StringBuffer exp) {
        // string startswith minus
        if (exp.charAt(0) == '-') {
            exp = exp.insert(0, '(');
            int closing = 1;
            char next = exp.charAt(closing + 1);

            if (Character.isDigit(next) || Character.isAlphabetic(next)) {
                // while next character is a digit or an alphabet
                while (closing != exp.length() - 1) {
                    if (Character.isDigit(exp.charAt(closing + 1)) || Character.isAlphabetic(exp.charAt(closing + 1)))
                        closing++;
                    else
                        break;
                }
            }
            closing++;
            exp = exp.insert(closing, ")");
        }
    }

    public static boolean is_operator(char ch) {
        if (ch == '^' || ch == '/' || ch == '*' || ch == '+' || ch == '-')
            return true;
        return false;
    }

    public static boolean only_char(StringBuffer exp) {
        // if expression has only constants or variables, no operator, insert
        // parenthesis around it
        boolean no_op = true;
        int i = 0;

        while (i != exp.length()) {
            if (is_operator(exp.charAt(i))) {
                no_op = false;
            }
            i++;
        }
        if (no_op) {
            exp.insert(0, '(');
            exp.insert(exp.length(), ')');
        }
        return (no_op);
    }

    String parenthesize(String expr) {
        
        StringBuffer exp;
        expr = expr.replaceAll("\\s+", "");
        exp = new StringBuffer(expr);

        boolean no_operator;
        // for string without any operators
        no_operator = only_char(exp);

        if (!no_operator) {
            // starts with negative sign
            check_for_minus(exp);
            // check for trigonometric and user defined functions
            check_for_func(exp);
            //System.out.println("After trigo: " + exp);  DEBUG
            // check for log
            check_for_log(exp);
            //System.out.println("After log: " + exp);    DEBUG
            // check for operators
            check_for_operator(exp);
        }
        return exp.toString();
    }
}