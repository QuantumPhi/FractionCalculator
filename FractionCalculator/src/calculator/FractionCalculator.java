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
			List<String> fracList = Parser.split(input);
			for(int i = 0; i < fracList.size(); i++) {
				
			}
		}
		InputGenerator inputGen = new InputGenerator();
		inputGen.generate(50, 5);
		System.out.println("Program finished.");
	}
}
