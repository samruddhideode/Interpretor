package calc;
import java.io.*;
import java.util.Scanner;

class FileHandler {
	
	String fileHandle(String exp, HashTable hash) {
	try {
		String d = "";
		String line = "";
		String fname = exp.substring(exp.indexOf("(")+1,exp.indexOf(")"));
		Calculator c = new Calculator(hash); //new calculator object to handle processing of file
		
		Scanner sc = new Scanner(new File(fname));
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			//System.out.println(line);  DEBUG
			d = c.calculate(line);
			if (!Evaluator.isNum(d) && !d.isBlank())
				break;
			}
			return "Line : " + line +", " + d ;
		}
		catch(FileNotFoundException ex) {
			return ex.getMessage();
		}
		catch (Exception o) {
			return new InvalidExpressionException().getMessage();
		}
	}
}

class FunctionHandler{
	String handle(String s, HashTable h) {
		try {
		String original = s;
		s = s.strip();
		s = s.substring(4); 
		if (s.charAt(0) != ' ')
			throw new InvalidSyntaxException(original);
		s = s.strip();
		int i = s.indexOf('(');
		if (s.isEmpty() || s.substring(0,i).strip().contains(" ") )
			throw new InvalidIdentifierException("' '");
		String f_name = s.substring(0,i).strip();
		if (!Evaluator.isAlpha(f_name))
			throw new InvalidIdentifierException(f_name);
		
		s = s.replace(" ", "");
		int j = s.indexOf(')');
		String var_name = s.substring(i+1,j);
		if (! var_name.equals("x"))
			throw new IncorrectParamException(var_name);
		
		String exp = s.substring(j+2); 
		
		if (h.search(f_name) != null && h.search(f_name) instanceof Function)
			throw new FunctionReDefinitionException(f_name);
		else if (h.search(f_name) != null)
			throw new IdentifierConflictException(var_name);
		Function f = new calc.Function(f_name,exp);
		h.insert(f);
		return "";
		}
		catch (CalculatorException e){
			return e.getMessage();
		}
		catch (Exception o) {
			return new InvalidExpressionException().getMessage();
		}
	}
}

class AssignmentHandler{
	String assign(String s, HashTable h) {
		try {
			s = s.strip();
			int i = s.indexOf('=');
			if ( s.isEmpty() || s.substring(0,i).strip().contains(" "))
				throw new InvalidIdentifierException("' '");
			String var_name = s.substring(0,i).strip();
			if (!Evaluator.isAlpha(var_name))
				throw new InvalidIdentifierException(var_name);
			s = s.replace(" ", "");
			i = s.indexOf('=');
			s = s.substring(i+1);
			//System.out.println("|"+ s + "|");   DEBUG
			double value = new Evaluator(s,h).evaluate();
			//System.out.println("Assigned " + value);   DEBUG
			UserDefined var = h.search(var_name);
			if (var == null)
			{
				var = new Variable(var_name,value);
				h.insert(var);
			}
			else if (var instanceof Variable)
				((Variable)var).value = value;
			else
				throw new IdentifierConflictException(var_name);
			return "";
		}
		catch (CalculatorException e) {
			return e.getMessage();
		}
		catch (Exception o) {
			return new InvalidExpressionException().getMessage();
		}
	}
	
}

class ExpressionHandler {
	String evaluate(String exp, HashTable h) {
		try {
			Evaluator ev = new Evaluator(exp,h);
			return ev.evaluate().toString();
		}
		catch (NullPointerException e) {
			return new InvalidExpressionException().getMessage();
		}
		catch (CalculatorException e) {
			return e.getMessage();
		}
		catch (Exception o) {
			return new InvalidExpressionException().getMessage();
		}
	}
}
