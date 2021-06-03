package calc;

import java.util.LinkedList;

class Evaluator{
	int pos = 0; //keeps track of position of evaluation in makeTree() method
	LinkedList<String> tokens;
	HashTable hash;
	
	Evaluator(String exp, HashTable h) throws Exception{
		tokens = new Tokenizer().tokenize(exp);
		hash = h;
	}
	
	//constructor used only by function class
	Evaluator(LinkedList<String> t){
		tokens = t;
		hash = null;
	}
	
	//wrapper method
	Double evaluate() throws Exception {
		Evaluable ev = makeTree();
		if (pos == tokens.size())
			return ev.evaluate();
		else 
			throw new InvalidExpressionException();
	}
	
	private Evaluable makeTree() throws Exception {
		Evaluable left = null, right = null, temp = null;
		BinOperator op = null;
		String token = tokens.get(pos);
		//System.out.println(token);   DEBUG
		if (token.equals("("))
		{
			if (isTrig(tokens.get(pos + 1)))
			{
				pos += 2;
				left = trig_handler(tokens.get(pos - 1));
				//System.out.println("Recvd trig func");   DEBUG
			}
			else if (tokens.get(pos + 1).equals("log"))
			{
				pos += 2;
				left = log_handler();
			}
			//for function
			else if (isAlpha(tokens.get(pos + 1)) && tokens.get(pos + 2).equals("("))
			{
				pos += 2;
				left = func_handler(tokens.get(pos - 1));
			}
			//for unary -
			else if (tokens.get(pos + 1).equals("-"))
			{
				pos += 2;
				right = makeTree();
				op = new Subtract();
				left = new Numeric(0);
				
			}
			//for unary +
			else if (tokens.get(pos + 1).equals("+"))
			{
				pos += 2;
				right = makeTree();
				op = new Add();
				left = new Numeric(0);
				
			}
			else 
			{
				pos ++;
				left = makeTree();
				//System.out.println("Recvd left");  DEBUG
				temp =  makeTree();
				if (temp != null)
				{
					if (!(temp instanceof BinOperator))
						throw new InvalidExpressionException();
					else 
						op = (BinOperator) temp;
				}
				//System.out.println("Recvd op");  DEBUG
				right = makeTree();
				//System.out.println("Recvd right");  DEBUG
			}
			
			if (tokens.get(pos).equals(")"))
			{
				pos ++;
				if (op == null)
					return left;
				else
				{
					op.connect(left, right);
					return op;
				}
			}
		}
		//base cases of recursion
		if (isNum(token))
		{
			pos ++;
			//System.out.println("Returned num");  DEBUG
			return new Numeric(Double.parseDouble(token));
		}
		if (isAlpha(token))
		{
			pos ++;
			//System.out.println("Returned var");     DEBUG
			//System.out.println("Var" + hash.search(token).evaluate());   DEBUG
			UserDefined u = hash.search(token);
			if (u == null || u instanceof Function)
				throw new VariableNotFoundException(token);
			return u;
		}
		if (isOp(token))
		{
			op = makeOperator(token);
			pos++;
			//System.out.println("Returned op");  DEBUG
			return op;
		}
		if (token.equals(")"))
			return null;
		
		throw new InvalidExpressionException();
	}
	
	Evaluable trig_handler(String trig) throws Exception {
		Evaluable right = makeTree();
		//System.out.println("Recvd trig arg");   DEBUG
		if (trig.equals("sin"))
			return new Sine(right);
		else if (trig.equals("cos"))
			return new Cosine(right);
		else if (trig.equals("tan"))
			return new Tan(right);
		else 
			return null;
		
	}
	
	Evaluable func_handler (String fname) throws Exception {
		Evaluable arg = makeTree();
		//System.out.println("Recvd func arg"); DEBUG
		UserDefined u = hash.search(fname);
		if ( u == null || u instanceof Variable)
			throw new FunctionNotFoundException(fname);
		((Function)u).set_arg(arg);
		Double d = u.evaluate();
		return new Numeric(d);
		
	}
	
	Evaluable log_handler() throws Exception{
		Evaluable left = makeTree();
		Evaluable right = makeTree();
		return new Log(left,right);
	}
	
	BinOperator makeOperator(String s) {
		BinOperator e = null;
		char c = s.charAt(0);
		switch (c) {
		case '+' : 
			e =  new Add();
			break;
		case '-' :
			e = new Subtract();
			break;
		case '*' :
			e = new Multiply();
			break;
		case '/' : 
			e = new Divide();
			break;
		case '^' : 
			e = new Exponentiate();
			break;
		default : break;
		}
		return e;
	}
	
	static boolean isTrig(String token) {
		return token.equals("sin") || token.equals("cos") || token.equals("tan");
	}
	
	static boolean isNum(String s) {
		try {
			Double.parseDouble(s);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	static boolean isAlpha(String s) {
		return s.matches("[a-zA-Z]+");
	}
	
	static boolean isOp(String s) {
		return "+-*/^".contains(s);
	}
}
