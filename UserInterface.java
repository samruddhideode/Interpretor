/*
 * DSA II Mini Project - Scientific Calculator
   Team members:
	2328 - Shruti Datar
	2329 - Samruddhi Deode
	2332 - Nisha Deshmukh
	2336 - Yamini Dongaonkar
   Problem Statement : 
   	Creating a scientific calculator to solve arithmetic,
    trigonometric and logarithmic expressions;
    to store and process variables and functions.
 */

package calc;
import java.util.Scanner;

public class UserInterface {
	
	public static void main(String[] args) {
		
		HashTable hash = new HashTable();
		Calculator c = new Calculator(hash);
		Scanner sc = new Scanner(System.in);
		String ans = "";
		System.out.println("Welcome!");
		while(true) {
			System.out.print(">>> ");
			String exp = sc.nextLine();
			if(exp.equalsIgnoreCase("Exit")) {
				System.out.println("Thank you!");
				break;
			}
			ans = c.calculate(exp);
			if (!ans.isBlank())
				System.out.println(ans);
		}
	}
}

class Calculator {
	HashTable hash;
	Calculator(HashTable h){
		hash = h;
	}
	public String calculate(String exp) {
		String e= "";
		FileHandler fh = new FileHandler();
		AssignmentHandler ah = new AssignmentHandler();
		FunctionHandler funchand = new FunctionHandler();
		ExpressionHandler exphand = new ExpressionHandler();
		
		if(exp.contains("import")) {
			e = fh.fileHandle(exp,hash);
		}
		else if(exp.contains("=") && !exp.contains("Func")) {
			//call assignment handler
			e = ah.assign(exp,hash);
		}
		else if(exp.contains("Func") ) { 
			//call function handler
			e = funchand.handle(exp, hash);
		}
		else if(exp.isBlank())
			e = "";
		else { //call expression handler
			e = exphand.evaluate(exp,hash);
		}
		return e;
	}
}
