package calculator;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FractionCalculator {
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		InputGenerator inputGen = null;
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader reader = null;
		String input = "";
		System.out.println("Fraction Calculator");
		while(!input.equalsIgnoreCase("quit")) {
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
				int cyclesLimit = 5;
				inputGen.generate(number, cyclesLimit);
				String testInput = "";
				for(int i = 0; i < number; i++) {
					try {
						testInput = reader.readLine();
						System.out.println(testInput);
						calculate(testInput);
					} 
					catch (IOException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			else
				calculate(input);
		}
		System.out.println("Program finished.");
	}
	
	public static void calculate(String input) {
		List<String> parseList = Parser.split(input);
		List<String> opList = new ArrayList<String>();
		List<IntFraction> fracList = new ArrayList<IntFraction>();
		for(int i = 0; i < parseList.size(); i++) {
			if(parseList.get(i).length() == 1 && Parser.isOperator(parseList.get(i).charAt(0))) {
				opList.add(parseList.get(i));
				parseList.remove(i);
			}
		}
		for(int i = 0; i < parseList.size(); i++)
			fracList.add(Parser.parseFraction(parseList.get(i)));
		
		IntFraction solution = Parser.parseExpression(fracList, opList);
		System.out.println("Answer: " + solution);
	}
}
