package calculator;

import java.util.*;

public class FractionCalculator {
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		String input = "";
		System.out.println("Fraction Calculator");
		while(!input.equals("quit")) {
			input = console.nextLine();
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
