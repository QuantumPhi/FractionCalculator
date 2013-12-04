package calculator;

import java.util.*;

public class FractionCalculator {
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		InputGenerator generator;
		String input = "";
		System.out.println("Fraction Calculator");
		while(!input.equals("quit")) {
			input = console.nextLine();
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
			System.out.println(solution);
		}
		InputGenerator inputGen = new InputGenerator();
		inputGen.generate(50, 5);
		System.out.println("Program finished.");
	}
}
