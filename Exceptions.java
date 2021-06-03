package calc;
//exceptions handled in this project
//base class is CalculatorExceptions
class CalculatorException extends Exception {
	CalculatorException(String s){
		super(s);
	}
}

class UnknownSymbolException extends CalculatorException{
	UnknownSymbolException(String c){
		super("Unknown Symbol : " + c);
	}
}

class InvalidIdentifierException extends CalculatorException {
	InvalidIdentifierException(String identifier){
		super("Invalid Identifier : " + identifier);
	}
}


class IncorrectParamException extends CalculatorException {
	IncorrectParamException(String var_name){
		super("Incorrect parameter " + var_name + ", please use 'x'.");
	}
}

class FunctionReDefinitionException extends CalculatorException {
	FunctionReDefinitionException(String f_name){
		super("Incorrect attempt to redefine function " + f_name);
	}
}

class InvalidSyntaxException extends CalculatorException{
	InvalidSyntaxException(String exp){
		super("Syntax Error in line : " + exp);
	}
}

class IdentifierConflictException extends CalculatorException {
	IdentifierConflictException(String name){
		super("Identifier " + name + " already bound to another object");
	}
}

class VariableNotFoundException extends CalculatorException {
	VariableNotFoundException(String v){
		super(v + " : No such variable.");
	}
}
class FunctionNotFoundException extends CalculatorException {
	FunctionNotFoundException(String f){
		super(f + " : No such function.");
	}
}
class InvalidExpressionException extends CalculatorException {
	InvalidExpressionException(){
		super("Syntax error.");
	}
}
