package calculator;

import java.util.*;

public class Parser {
	public static IntFraction parseFraction(String input) {
		boolean positive = true;
		int wholePart = 0;
		int numerator = 0;
		int denominator = 1;
		int startPos = 0;
		if(input.charAt(startPos) == '-')
			startPos++;
		for(int i = startPos; i < input.length(); i++) {
			if(input.contains("_")) {
				wholePart = Integer.parseInt(input.substring(startPos, input.indexOf('_')));
				i = input.indexOf('_') + 1;
			}
			else if(!input.contains("/")) {
				wholePart = Integer.parseInt(input.substring(startPos, input.length()));
				break;
			}
		}
		IntFraction newFraction = new IntFraction(positive, wholePart, numerator, denominator);
		return newFraction;
	}
	
	public static IntFraction parseExpression(List<IntFraction> fracArray, List<String> opString) {
		List<Integer> opLoc = new ArrayList<Integer>();
		List<String> orderOp = new ArrayList<String>();
		int space = 0;
		for(int i = 0; i < opString.size(); i++) {
			if(opString.get(i).equals("*") || opString.get(i).equals("/")) {
				orderOp.add(opString.get(i));
				opLoc.add(i - space);
				space++;
				opString.remove(i);
			}
		}
		
		int i = 0;
		while(i < orderOp.size()) {
			if(orderOp.get(i).equals("*")) {
				IntFraction newFraction = fracArray.get(opLoc.get(i)).multiply(fracArray.get(opLoc.get(i) + 1));
				fracArray.remove(opLoc.get(i).intValue()); fracArray.remove(opLoc.get(i).intValue()); fracArray.add(opLoc.get(i).intValue(), newFraction);
			}
			else if(orderOp.get(i).equals("/")) {
				IntFraction newFraction = fracArray.get(opLoc.get(i)).divide(fracArray.get(opLoc.get(i) + 1));
				fracArray.remove(i); fracArray.remove(i); fracArray.add(i, newFraction);
			}
			i++;
		}
		
		int j = 0;
		while(j < opString.size()) {
			if(opString.get(j).equals("+")) {
				IntFraction newFraction = fracArray.get(0).add(fracArray.get(1));
				fracArray.remove(0); fracArray.remove(0); fracArray.add(0, newFraction);
			}
			if(opString.get(j).equals("-")) {
				IntFraction newFraction = fracArray.get(0).subtract(fracArray.get(1));
				fracArray.remove(0); fracArray.remove(0); fracArray.add(0, newFraction);
			}
			j++;
		}
		
		return fracArray.get(0);
	}
	
	public static List<String> split(String input) {
		List<String> returnString = new ArrayList<String>();
		String listAdd = "";
		for(int i = 0; i < input.length(); i++) {
			if(input.charAt(i) != ' ')
				listAdd += input.charAt(i);
			else {
				returnString.add(listAdd);
				listAdd = "";
			}
		}
		
		return returnString;
	}
	
	public static boolean isOperator(char inputChar) {
		String operator = "+-*/";
		for(int i = 0; i < operator.length(); i++)
			if(inputChar == operator.charAt(i))
				return true;
		return false;
	}
}
