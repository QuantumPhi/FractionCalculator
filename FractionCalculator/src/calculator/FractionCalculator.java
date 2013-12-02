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
			if(input.equals("test")) {
				generator = new InputGenerator();
				generator.generate(50);
			}
		}
	}
	
	public static boolean isOperator(char inputChar) {
		String operator = "+-*/";
		for(int i = 0; i < operator.length(); i++)
			if(inputChar == operator.charAt(i))
				return true;
		return false;
	}
}
