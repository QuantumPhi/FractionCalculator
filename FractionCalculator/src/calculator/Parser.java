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
		IntFraction newFraction = null;
		List<String> precedence = new ArrayList<String>();
		List<Integer> precLoc = new ArrayList<Integer>();
		int precSpace = 0;
		for(int i = 0; i < opString.size(); i++) {
			if(opString.get(i).equals("*") || opString.get(i).equals("/")) {
				precedence.add(opString.get(i));
				precLoc.add(i - precSpace);
				precSpace++;
				opString.remove(i);
			}
		}
		for(int i = 0; i < precedence.size(); i++) {
			newFraction = precedence.get(i).equals("*") ? fracArray.get(precLoc.get(i)).multiply(fracArray.get(precLoc.get(i) + 1)) : fracArray.get(precLoc.get(i)).divide(fracArray.get(precLoc.get(i) + 1));
			fracArray.remove(precLoc.get(i)); fracArray.remove(precLoc.get(i)); fracArray.add(precLoc.get(i), newFraction);
		}
		
		for(int i = 0; i < opString.size(); i++) {
			newFraction = opString.get(0).equals("+") ? fracArray.get(0).add(fracArray.get(1)) : fracArray.get(0).subtract(fracArray.get(1));
			fracArray.remove(0); fracArray.remove(0); fracArray.add(0, newFraction);
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
		returnString.add(listAdd);
		
		return returnString;
	}
	
	public static boolean isOperator(char input) {
		String operator = "+-*/";
		for(int i = 0; i < operator.length(); i++)
			if(input == operator.charAt(i)) 
				return true;
		return false;
	}
}
