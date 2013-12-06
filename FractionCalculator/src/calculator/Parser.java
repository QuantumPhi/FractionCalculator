package calculator;

import java.util.*;

public class Parser {
	public static IntFraction parseFraction(String input) {
		int wholePart = 0;
		int numerator = 0;
		int denominator = 1;
		if(!input.contains("/"))
			wholePart = Integer.parseInt(input.substring(0, input.length()));
		else if(input.contains("_") && input.contains("/")) {
			wholePart = Integer.parseInt(input.substring(0, input.indexOf('_')));
			numerator = Integer.parseInt(input.substring(input.indexOf('_') + 1, input.indexOf('/')));
			denominator = Integer.parseInt(input.substring(input.indexOf('/') + 1, input.length()));
		}
		else if(!input.contains("_") && input.contains("/")) {
			numerator = Integer.parseInt(input.substring(0, input.indexOf('/')));
			denominator = Integer.parseInt(input.substring(input.indexOf('/') + 1, input.length()));
		}
		IntFraction newFraction = new IntFraction(true, wholePart, numerator, denominator);
		return newFraction;
	}
	
	public static IntFraction parseExpression(List<IntFraction> fracArray, List<String> opString) {
		IntFraction newFraction = null;
		List<String> precedence = new ArrayList<String>();
		List<Integer> precLoc = new ArrayList<Integer>();
		for(int i = 0; i < opString.size(); i++) {
			if(opString.get(i).equals("*") || opString.get(i).equals("/")) {
				precedence.add(opString.get(i));
				precLoc.add(i);
				opString.remove(i);
				i--;
			}
		}
		for(int i = 0; i < precedence.size(); i++) {
			newFraction = precedence.get(i).equals("*") ? fracArray.get(precLoc.get(i).intValue()).multiply(fracArray.get(precLoc.get(i).intValue() + 1)) : fracArray.get(precLoc.get(i).intValue()).divide(fracArray.get(precLoc.get(i).intValue() + 1));
			fracArray.remove(precLoc.get(i).intValue()); fracArray.remove(precLoc.get(i).intValue()); fracArray.add(precLoc.get(i).intValue(), newFraction);
		}
		
		for(int i = 0; i < opString.size(); i++) {
			newFraction = opString.get(i).equals("+") ? fracArray.get(0).add(fracArray.get(1)) : fracArray.get(0).subtract(fracArray.get(1));
			fracArray.remove(0); fracArray.remove(0); fracArray.add(0, newFraction);
		}
		
		if(fracArray.size() == 1 && opString.size() == 0) {
			fracArray.get(0).simplify();
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
	
	public static String revSplit(List<String> input) {
		String returnString = "";
		for(int i = 0; i < input.size(); i++)
			returnString = i < input.size() - 1 ? returnString + input.get(i) + " " : returnString + input.get(i);
		
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
