package calculator;

import java.io.*;
import java.util.*;

public class FractionCalculator {
	public static boolean FRACTION_TYPE = true;
	
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		InputGenerator inputGen = null;
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader reader = null;
		String input = "";
		System.out.println("Fraction Calculator");
		while(true) {
			try {
				input = console.nextLine();
				if(input.equalsIgnoreCase("test")) {
					try {
						fstream = new FileInputStream("TextInput");
					} 
					catch (FileNotFoundException e) {
						System.out.println(e.getMessage());
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
							System.out.println(testInput);
							Fraction solution = calculate(testInput);
							System.out.println("Answer: " + solution);
						} 
						catch (Exception e) {
							if(!e.getClass().getSimpleName().equals("ArithmeticException"))
								System.out.println(e);
							else {
								System.out.println("Answer: Invalid");
								continue;
							}
						}
					}
					reader.close();
				}
				else if(input.equalsIgnoreCase("Fraction")) {
					System.out.print("Fraction type? ");
					input = console.nextLine();
					if(input.equalsIgnoreCase("IntFraction"))
						FRACTION_TYPE = true;
					if(input.equalsIgnoreCase("BigFraction"))
						FRACTION_TYPE = false;
				}
				else if(Parser.containsComparison(input) != 10) {
					int comparisonType = Parser.containsComparison(input);
					List<Fraction> solution = new ArrayList<Fraction>();
					compare(comparisonType, input, solution);
				}
				else {
					try {
						Fraction solution = calculate(input);
						System.out.println("Answer: " + solution);
					}
					catch(Exception e) {
						if(e.getClass().getSimpleName().equals("ArithmeticException"))
							System.out.println("Answer: Invalid");
					}
				}
			}
			catch(Exception e) {
				if(input.equalsIgnoreCase("quit")) {
					System.out.println("Program Finished");
					console.close();
					break;
				}
				else {
					String reference = e.getMessage().substring(e.getMessage().indexOf('\"') + 1, e.getMessage().length() - 1);
					String messagePrint = "";
					for(int i = 0; i < input.indexOf(reference); i++)
						messagePrint += " ";
					messagePrint += "^";
					messagePrint += " ";
					messagePrint += e;
					System.out.println(messagePrint);
				}
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
	
	public static void compare(int comparisonType, String input, List<Fraction> solution) {
		try {
			if(comparisonType == 0) {
				solution.add(calculate(input.substring(0, input.indexOf(">"))));
				solution.add(calculate(input.substring(input.indexOf(">") + 1, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison == 1)
					System.out.println("Answer: true");
				else
					System.out.println("Answer: false");
			}
			else if(comparisonType == 1) {
				solution.add(calculate(input.substring(0, input.indexOf("<"))));
				solution.add(calculate(input.substring(input.indexOf("<") + 1, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison == -1)
					System.out.println("Answer: true");
				else
					System.out.println("Answer: false");
			}
			else if(comparisonType == 2) {
				solution.add(calculate(input.substring(0, input.indexOf(">="))));
				solution.add(calculate(input.substring(input.indexOf(">=") + 2, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison >= 0)
					System.out.println("Answer: true");
				else
					System.out.println("Answer: false");
			}
			else if(comparisonType == 3) {
				solution.add(calculate(input.substring(0, input.indexOf("<="))));
				solution.add(calculate(input.substring(input.indexOf("<=") + 2, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison <= 0)
					System.out.println("Answer: true");
				else
					System.out.println("Answer: false");
			}
			else if(comparisonType == 4) {
				solution.add(calculate(input.substring(0, input.indexOf("="))));
				solution.add(calculate(input.substring(input.indexOf("=") + 1, input.length())));
				int comparison = solution.get(0).compare(solution.get(1));
				if(comparison == 0)
					System.out.println("Answer: true");
				else
					System.out.println("Answer: false");
			}
		}
		catch(ArithmeticException e) {
			System.out.println("Answer: Invalid");
		}
	}
}
