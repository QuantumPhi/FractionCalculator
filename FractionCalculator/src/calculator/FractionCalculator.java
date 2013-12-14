package calculator;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class FractionCalculator {
	public static boolean FRACTION_TYPE = true;
	
	public static void framework(String input, JTextPane pane, JTextField field) {
		pane.setText("");
		InputGenerator inputGen = null;
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader reader = null;
		try {
			if(input.equalsIgnoreCase("test")) {
				try {
					fstream = new FileInputStream("TextInput");
				} 
				catch (FileNotFoundException e) {
					pane.setText(e.toString());
				}
				in = new DataInputStream(fstream);
				reader = new BufferedReader(new InputStreamReader(in));
				inputGen = new InputGenerator();
				int number = 50;
				int cyclesLimit = 10;
				int numberSize = 10;
				int numBits = 64;
				inputGen.generate(number, cyclesLimit, numberSize, numBits, FRACTION_TYPE);
				String testInput = "";
				for(int i = 0; i < number; i++) {
					try {
						testInput = reader.readLine();
						pane.setText(pane.getText() + testInput);
						Fraction solution = calculate(testInput);
						pane.setText(pane.getText() + "\nAnswer: " + solution + "\n");
					} 
					catch (Exception e) {
						if(!e.getClass().getSimpleName().equals("ArithmeticException"))
							pane.setText(e.toString());
						else {
							pane.setText("\n" + "Answer: Invalid" + "\n");
							continue;
						}
					}
				}
				reader.close();
			}
			else if(input.equalsIgnoreCase("IntFraction")) {
				FRACTION_TYPE = true;
			}
			else if(input.equalsIgnoreCase("BigFraction")) {
				FRACTION_TYPE = false;
			}
			else if(Parser.containsComparison(input) != 10) {
				int comparisonType = Parser.containsComparison(input);
				List<Fraction> solution = new ArrayList<Fraction>();
				pane.setText(input);
				compare(comparisonType, input, solution, pane);
			}
			else {
				try {
					Fraction solution = calculate(input);
					pane.setText(input);
					pane.setText(pane.getText() + "\nAnswer: " + solution + "\n");
				}
				catch(Exception e) {
					if(e.getClass().getSimpleName().equals("ArithmeticException")) {
						pane.setText(input);
						pane.setText(pane.getText() + "\nAnswer: Invalid\n");
					}
					else
						throw e;
				}
			}
		}
		catch(Exception e) {
			pane.setText(e.toString());
		}
	}
	
	public static Fraction calculate(String input) {
		List<String> parseList = Parser.split(input);
		List<String> opList = new ArrayList<String>();
		List<Fraction> fracList = new ArrayList<Fraction>();
		List<String> parenthesisList = new ArrayList<String>();
		int parenthesisLoc;
		for(int i = 0; i < parseList.size(); i++) {
			if(parseList.get(i).contains("(")) {
				parenthesisLoc = i;
				if(parseList.get(i).contains(")")) {
					parenthesisList.add(parseList.get(i).substring(1, parseList.get(i).length() - 1));
					parseList.remove(i);
				}
				else {
					int parenthesisCount = 1;
					int currentParenthesis = 0;
					currentParenthesis = parseList.get(i).contains(")") ? currentParenthesis + 1 : currentParenthesis;
					if(parenthesisCount == currentParenthesis)
						parenthesisList.add(parseList.get(i).substring(1, parseList.get(i).length() - 1));
					else {
						parenthesisList.add(parseList.get(i).substring(1, parseList.get(i).length()));
						parseList.remove(i);
					}
					while(parenthesisCount > currentParenthesis && i < parseList.size()) {
						parenthesisCount = parseList.get(i).contains("(") ? parenthesisCount + 1 : parenthesisCount;
						currentParenthesis = parseList.get(i).contains(")") ? currentParenthesis + 1 : currentParenthesis;
						if(parseList.get(i).length() > 1 && (currentParenthesis == parenthesisCount || parseList.get(i).charAt(parseList.get(i).length() - 1) == ')' && parseList.get(i).charAt(parseList.get(i).length() - 2) == ')'))
							parenthesisList.add(parseList.get(i).substring(0, parseList.get(i).length() - 1));
						else
							parenthesisList.add(parseList.get(i));
						parseList.remove(i);
					}
				}
				Fraction newFraction = calculate(Parser.revSplit(parenthesisList));
				parseList.add(parenthesisLoc, newFraction.toString());
				parenthesisList.clear();
			}
			else if(parseList.get(i).length() == 1 && Parser.isOperator(parseList.get(i).charAt(0))) {
				opList.add(parseList.get(i));
				parseList.remove(i);
				i--;
			}
		}
		for(int i = 0; i < parseList.size(); i++)
			fracList.add(Parser.parseFraction(parseList.get(i), FRACTION_TYPE));
		
		Fraction solution = Parser.parseExpression(fracList, opList);
		if(solution.getDenominator().intValue() == 0)
			throw new java.lang.ArithmeticException();
		return solution;
	}
	
	public static void compare(int comparisonType, String input, List<Fraction> solution, JTextPane pane) {
		try {
			if(comparisonType == 0) {
				solution.add(calculate(input.substring(0, input.indexOf(">"))));
				solution.add(calculate(input.substring(input.indexOf(">") + 1, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison == 1)
					pane.setText(pane.getText() + "\nAnswer: true\n");
				else
					pane.setText(pane.getText() + "\nAnswer: false\n");
			}
			else if(comparisonType == 1) {
				solution.add(calculate(input.substring(0, input.indexOf("<"))));
				solution.add(calculate(input.substring(input.indexOf("<") + 1, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison == -1)
					pane.setText(pane.getText() + "\nAnswer: true\n");
				else
					pane.setText(pane.getText() + "\nAnswer: false\n");
			}
			else if(comparisonType == 2) {
				solution.add(calculate(input.substring(0, input.indexOf(">="))));
				solution.add(calculate(input.substring(input.indexOf(">=") + 2, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison >= 0)
					pane.setText(pane.getText() + "\nAnswer: true\n");
				else
					pane.setText(pane.getText() + "\nAnswer: false\n");
			}
			else if(comparisonType == 3) {
				solution.add(calculate(input.substring(0, input.indexOf("<="))));
				solution.add(calculate(input.substring(input.indexOf("<=") + 2, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison <= 0)
					pane.setText(pane.getText() + "\nAnswer: true\n");
				else
					pane.setText(pane.getText() + "\nAnswer: false\n");
			}
			else if(comparisonType == 4) {
				solution.add(calculate(input.substring(0, input.indexOf("="))));
				solution.add(calculate(input.substring(input.indexOf("=") + 1, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison == 0)
					pane.setText(pane.getText() + "\nAnswer: true\n");
				else
					pane.setText(pane.getText() + "\nAnswer: false\n");
			}
		}
		catch(ArithmeticException e) {
			pane.setText("Answer: Invalid");
		}
	}
}
