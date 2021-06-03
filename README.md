# Interpretor
Problem Statement : Creating a scientific calculator to solve
arithmetic, trigonometric and logarithmic expressions; to store
and process variables and functions.
---
Functionalities:
1. Users can enter expressions and get the evaluated result.
2. Users can assign, save and update the value of a variable.
3. Users can define and call functions.
4. Users can import a file containing expressions and
declarations, and process its contents
Major Classes and Interfaces :-
1. Evaluable: This interface is implemented by all operators,
variables, functions and numeric constants.
It contains an evaluate method which returns a double value
after evaluation.
2. Evaluator: Tokenizes the expression string and creates an
expression tree to evaluate.
3. HashTable: Implements a hash table which maps variables to
their corresponding values and functions to their corresponding
expressions.
Some important functions in the code:
1. makeTree() : Scans a tokenized linked list, categorizes the
tokens into variable, function, operator or numeric value to
construct an expression tree and returns an evaluable object as
the root of the tree.
2. evaluate() : This function is implemented by all evaluable
classes like variable, function, numeric, and all operators. It
evaluates the node and returns double value.
3. generateHash() : It returns a unique hash key for a variable
using quadratic probing.4. tokenize() : Accepts expression string entered by users and
creates tokens based on operators, numeric values, variables,
etc. Returns a linked list of tokens.
Other Features of the project:-
1. Balanced parentheses are added to user input by
addParanthesis() function to facilitate easy evaluation.
2. Syntax errors are handled using the exception handling
feature of Java.
3. Other handled exceptions: Function redefinition,
Function/Variable/File not found, Use of unknown symbols.
4. User Interface: Prompts user to enter input using specific
keywords like Func, import, exit.
----
***Run UserInterface.java to test the working***
