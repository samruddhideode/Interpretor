package calc;

import java.util.LinkedList;

public class Tokenizer {
	
	//returns a list of tokens
	LinkedList<String> tokenize(String expr) throws Exception { 
		LinkedList<String> tokens = new LinkedList<String>();
		expr = new Parenthesizer().parenthesize(expr);
		String token = "";
		int i = 0;
		char c;
		while (i < expr.length())
		{
			c = expr.charAt(i);
			if (c == '(' || c == ')' || isOp(c))
			{
				token = c + "";
				i ++;
			}
			else if (isNum(c))
			{
				int j = i;
				while(j < expr.length() && isNum(expr.charAt(j)))
					j ++;
				
				token = expr.substring(i,j);
				i = j;
			}
			else if (isAlpha(c))
			{
				int j = i;
				while(j < expr.length() && isAlpha(expr.charAt(j)))
					j ++;
				
				token = expr.substring(i,j);
				i = j;
			}
			else if (c == ' ')
			{
				i ++;
				continue;
			}
			else 
				throw new UnknownSymbolException("" + c);
			tokens.add(token);
		}
		//System.out.println(tokens);   DEBUG
		return tokens;
	}
	
	boolean isNum(char c) {
		return ('0' <= c && c <= '9') || (c == '.');
	}
	
	boolean isAlpha(char c) {
		return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
	}
	
	boolean isOp(char c) {
		String ops = "+-*/^";
		return ops.contains(c+"");
	}
}
