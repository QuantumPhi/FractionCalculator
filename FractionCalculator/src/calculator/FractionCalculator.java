package calculator;

import java.util.*;

public class FractionCalculator {
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		String input = "";
		List<String> fracList = new ArrayList<String>();
		List<String> operatorList = new ArrayList<String>();
		List<IntFraction> intFrac = new ArrayList<IntFraction>();
		while(!input.equals("quit")) {
			fracList.clear();
			operatorList.clear();
			intFrac.clear();
			System.out.print("Enter expression to evaluate: ");
			input = console.nextLine();
			String addFrac = "";
			String opCheck = "";
			for(int i = 0; i < input.length(); i++) {
				if(i > 0 && isOperator(input.charAt(i)) && checkFraction(addFrac) && addFrac.length() > 1) {
					opCheck += input.charAt(i);
					operatorList.add(opCheck);
					opCheck = "";
					fracList.add(addFrac);
					addFrac = "";
				}
				else if(input.charAt(i) != ' ')
					addFrac += input.charAt(i);
			}
			fracList.add(addFrac);
			//TODO: Add iterator
			for(int i = 0; i < fracList.size(); i++)
				intFrac.add(Parser.parseFraction(fracList.get(i)));
			IntFraction solution = Parser.parseExpression(intFrac, operatorList);
			System.out.println(solution);
		}
	}
	
	public static boolean isOperator(char inputChar) {
		String operator = "+-*/";
		for(int i = 0; i < operator.length(); i++)
			if(inputChar == operator.charAt(i))
				return true;
		return false;
	}
	
	public static boolean checkFraction(String fraction) {
		if(fraction.contains("_") && !fraction.contains("/"))
			return false;
		return true;
	}
}
