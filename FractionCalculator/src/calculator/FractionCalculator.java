package calculator;

import java.awt.Color;
import java.io.*;
import java.util.*;

import javax.swing.*;

public class FractionCalculator {
	public static boolean FRACTION_TYPE = true;
	
	public static void framework(String input, JTextPane pane, JTextField field) {
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
				inputGen.generate(pane, number, cyclesLimit, numberSize, numBits, FRACTION_TYPE);
				String testInput = "";
				for(int i = 0; i < number; i++) {
					try {
						testInput = reader.readLine();
						FracCalcFrame.appendToPane(pane, testInput, Color.BLACK);
						if(Parser.containsComparison(testInput) != 10) {
							int comparisonType = Parser.containsComparison(testInput);
							List<Fraction> solutionCompare = new ArrayList<Fraction>();
							compare(comparisonType, testInput, solutionCompare, pane);
						}
						else {
							Fraction solution = calculate(testInput);
							FracCalcFrame.appendToPane(pane, "\nAnswer: " + solution + "\n", Color.BLUE);
						}
					} 
					catch (Exception e) {
						if(e.getClass().getSimpleName().equals("ArithmeticException")) {
							FracCalcFrame.appendToPane(pane, "\nAnswer: Invalid\n", Color.RED);
							continue;
						}
						else
							throw e;
					}
				}
				reader.close();
			}
			else if(Parser.containsComparison(input) != 10) {
				int comparisonType = Parser.containsComparison(input);
				List<Fraction> solution = new ArrayList<Fraction>();
				FracCalcFrame.appendToPane(pane, input, Color.BLACK);;
				compare(comparisonType, input, solution, pane);
			}
			else {
				FracCalcFrame.appendToPane(pane, input, Color.BLACK);
				Fraction solution = calculate(input);
				if(solution == null)
					FracCalcFrame.appendToPane(pane, "\nAnswer: " + solution + "\n", Color.RED);
				else
					FracCalcFrame.appendToPane(pane, "\nAnswer: " + solution + "\n", Color.BLUE);
			}
		}
		catch(Exception e) {
			pane.setText("");
			if(e.getClass().getSimpleName().equals("NumberFormatException")) {
				FracCalcFrame.appendToPane(pane, input, Color.BLACK);
				String errorString = e.getMessage().substring(e.getMessage().indexOf('\"') + 1, e.getMessage().length() - 1);
				FracCalcFrame.appendToPane(pane, "\n" + e.toString(), Color.RED);
				FracCalcFrame.highlightText(pane, errorString, Color.YELLOW);
			}
			else if(e.getClass().getSimpleName().equals("NullPointerException"))
				FracCalcFrame.appendToPane(pane, "TextInput file initialized!", Color.BLUE);
			else {
				pane.setText(input + "\n");
				FracCalcFrame.appendToPane(pane, e.toString(), Color.RED);
			}
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
					int parenthesisCount = get(parseList.get(i), '(');
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
		
		Fraction solution;
		try {
			solution = Parser.parseExpression(fracList, opList);
		}
		catch (ArithmeticException e) { solution = null; }
		return solution;
	}
	
	public static void compare(int comparisonType, String input, List<Fraction> solution, JTextPane pane) {
		try {
			if(comparisonType == 0) {
				solution.add(calculate(input.substring(0, input.indexOf(">"))));
				solution.add(calculate(input.substring(input.indexOf(">") + 1, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison == 1)
					FracCalcFrame.appendToPane(pane, "\nAnswer: true\n", Color.BLUE);
				else
					FracCalcFrame.appendToPane(pane, "\nAnswer: false\n", Color.BLUE);
			}
			else if(comparisonType == 1) {
				solution.add(calculate(input.substring(0, input.indexOf("<"))));
				solution.add(calculate(input.substring(input.indexOf("<") + 1, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison == -1)
					FracCalcFrame.appendToPane(pane, "\nAnswer: true\n", Color.BLUE);
				else
					FracCalcFrame.appendToPane(pane, "\nAnswer: false\n", Color.BLUE);
			}
			else if(comparisonType == 2) {
				solution.add(calculate(input.substring(0, input.indexOf(">="))));
				solution.add(calculate(input.substring(input.indexOf(">=") + 2, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison >= 0)
					FracCalcFrame.appendToPane(pane, "\nAnswer: true\n", Color.BLUE);
				else
					FracCalcFrame.appendToPane(pane, "\nAnswer: false\n", Color.BLUE);
			}
			else if(comparisonType == 3) {
				solution.add(calculate(input.substring(0, input.indexOf("<="))));
				solution.add(calculate(input.substring(input.indexOf("<=") + 2, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison <= 0)
					FracCalcFrame.appendToPane(pane, "\nAnswer: true\n", Color.BLUE);
				else
					FracCalcFrame.appendToPane(pane, "\nAnswer: false\n", Color.BLUE);
			}
			else if(comparisonType == 4) {
				solution.add(calculate(input.substring(0, input.indexOf("="))));
				solution.add(calculate(input.substring(input.indexOf("=") + 1, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison == 0)
					FracCalcFrame.appendToPane(pane, "\nAnswer: true\n", Color.BLUE);
				else
					FracCalcFrame.appendToPane(pane, "\nAnswer: false\n", Color.BLUE);
			}
		}
		catch(ArithmeticException e) {
			pane.setText(pane.getText() + "\nAnswer: Invalid\n");
		}
	}
	
	public static int get(String input, char findChar) {
		int returnInt = 0;
		for(int i = 0; i < input.length(); i++) 
			returnInt = input.charAt(i) == findChar ? returnInt + 1 : returnInt;
		return returnInt;
	}
}
