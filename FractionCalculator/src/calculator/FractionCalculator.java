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
					int numBits = 53;
					inputGen.generate(number, cyclesLimit, numberSize, numBits, FRACTION_TYPE);
					String testInput = "";
					for(int i = 0; i < number; i++) {
						try {
							testInput = reader.readLine();
							System.out.println(testInput);
							Fraction solution = calculate(testInput);
							System.out.println("Answer: " + solution);
						} 
						catch (IOException e) {
							System.out.println(e.getMessage());
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
				else {
					Fraction solution = calculate(input);
					System.out.println("Answer: " + solution);
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
					parenthesisList.add(parseList.get(i).substring(1, parseList.get(i).length()));
					parseList.remove(i);
					while(!parseList.get(i).contains(")")) {
						parenthesisList.add(parseList.get(i));
						parseList.remove(i);
					}
					parenthesisList.add(parseList.get(i).substring(0, parseList.get(i).length() - 1));
					parseList.remove(i);
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
		return solution;
	}
}
