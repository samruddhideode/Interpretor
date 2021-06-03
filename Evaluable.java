package calc;

import java.util.LinkedList;

//common interface for all functions, variables and numerics
interface Evaluable{
	Double evaluate() throws Exception;
}

//base class for all binary operators
abstract class BinOperator implements Evaluable {
	Evaluable left;
	Evaluable right;
	void connect(Evaluable le, Evaluable ri){
		right = ri;
		left = le;
	}
}
class Add extends BinOperator{
	
	public Double evaluate() throws Exception {
		return right.evaluate() + left.evaluate();
	}
}

class Subtract extends BinOperator{
	
	public Double evaluate() throws Exception {
		return left.evaluate() - right.evaluate();
	}
}

class Multiply extends BinOperator{
	
	public Double evaluate() throws Exception {
		return right.evaluate() * left.evaluate();
	}
}

class Divide extends BinOperator{
	
	public Double evaluate() throws Exception {
		return left.evaluate() / right.evaluate();
	}
}

class Exponentiate extends BinOperator{
	
	public Double evaluate() throws Exception {
		return Math.pow(left.evaluate(), right.evaluate());
	}
}

class Log implements Evaluable{
	Evaluable left;
	Evaluable right;
	Log(Evaluable le, Evaluable ri){
		right = ri;
		left = le;
	}
	public Double evaluate() throws Exception {
		return Math.log(right.evaluate())/Math.log(left.evaluate());
	}
}

class Sine implements Evaluable{
	Evaluable right;
	Sine(Evaluable ri){
		right = ri;
	}
	public Double evaluate() throws Exception {
		return Math.sin(right.evaluate());
	}
}

class Cosine implements Evaluable{
	Evaluable right;
	Cosine(Evaluable ri){
		right = ri;
	}
	public Double evaluate() throws Exception {
		return Math.cos(right.evaluate());
	}
}

class Tan implements Evaluable{
	Evaluable right;
	Tan(Evaluable ri){
		right = ri;
	}
	public Double evaluate() throws Exception {
		return Math.tan(right.evaluate());
	}
}

//base class for functions and variables
//to use common hash table for functions and variables
abstract class UserDefined implements Evaluable {
	String name;
}
class Function extends UserDefined{
	LinkedList<String> tokens;
	Evaluable arg = null;
	Function(String f_name,String exp) throws Exception{ 
		this.name = f_name;
		this.tokens = new Tokenizer().tokenize(exp);  //tokenizes the function's expression
		for (int i = 0; i < tokens.size(); i ++) {
			if (!valid(tokens.get(i)))
				throw new UnknownSymbolException(tokens.get(i));
		}
		if (tokens.isEmpty())
			throw new InvalidExpressionException();
	}
	
	//valid token
	boolean valid(String t) {
		return Evaluator.isNum(t) || Evaluator.isOp(t) || Evaluator.isTrig(t) || t.equals("log") || t.equals("x") || t.equals(")") || t.equals("(");
	}
	void set_arg(Evaluable e) {
		arg = e;
	}
	
	
	public Double evaluate() throws Exception{
		Double x = arg.evaluate();
		String x_tok = x.toString();
		for (int i = 0; i < tokens.size(); i ++)
		{
			if (tokens.get(i).equals("x"))
				tokens.set(i, x_tok);
		}
		
		return new Evaluator(tokens).evaluate();
	}
}

class Variable extends UserDefined{
	double value;
	Variable(String var_name,double val){
		name = var_name;
		this.value = val;
	}
	public Double evaluate() {
		return value;
	}
}

//for numeric values
class Numeric implements Evaluable{
	double value;
	
	Numeric(double val){
		value = val;
	}
	public Double evaluate() {
		return value;
	}
}

// -------------------------------------------------------------

