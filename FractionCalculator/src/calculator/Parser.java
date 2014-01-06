package calculator;

import java.util.*;
import java.math.BigInteger;

public class Parser {
	public static Fraction parseFraction(String input, boolean fractionType) {
		if(fractionType) {
			int wholePart = 0;
			int numerator = 0;
			int denominator = 1;
			if(!input.contains("/"))
				wholePart = Integer.parseInt(input.substring(0, input.length()));
			else if(input.contains("_") && input.contains("/")) {
				wholePart = Integer.parseInt(input.substring(0, input.indexOf('_')));
				numerator = Integer.parseInt(input.substring(input.indexOf('_') + 1, input.indexOf('/')));
				numerator = wholePart < 0 && numerator > 0 ? -numerator : numerator;
				numerator = input.charAt(0) == '-' && wholePart == 0 ? -numerator : numerator;
				denominator = Integer.parseInt(input.substring(input.indexOf('/') + 1, input.length()));
			}
			else if(!input.contains("_") && input.contains("/")) {
				numerator = Integer.parseInt(input.substring(0, input.indexOf('/')));
				denominator = Integer.parseInt(input.substring(input.indexOf('/') + 1, input.length()));
			}
			Fraction newFraction = new IntFraction(true, wholePart, numerator, denominator);
			newFraction.simplify();
			return newFraction;
		}
		else {
			BigInteger wholePart = BigInteger.ZERO;
			BigInteger numerator = BigInteger.ZERO;
			BigInteger denominator = BigInteger.ONE;
			if(!input.contains("/"))
				wholePart = new BigInteger(input.substring(0, input.length()));
			else if(input.contains("_") && input.contains("/")) {
				wholePart = new BigInteger(input.substring(0, input.indexOf('_')));
				numerator = new BigInteger(input.substring(input.indexOf('_') + 1, input.indexOf('/')));
				numerator = !wholePart.equals(wholePart.abs()) && numerator.equals(numerator.abs()) ? numerator.negate() : numerator;
				numerator = input.charAt(0) == '-' && wholePart.equals(BigInteger.ZERO) ? numerator.negate() : numerator;
				denominator = new BigInteger(input.substring(input.indexOf('/') + 1, input.length()));
			}
			else if(!input.contains("_") && input.contains("/")) {
				numerator = new BigInteger(input.substring(0, input.indexOf('/')));
				denominator = new BigInteger(input.substring(input.indexOf('/') + 1, input.length()));
			}
			Fraction newFraction = new BigFraction(true, wholePart, numerator, denominator);
			newFraction.simplify();
			return newFraction;
		}
	}
	
	public static Fraction parseExpression(List<Fraction> fracArray, List<String> opString) {
		Fraction newFraction = null;
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
				if(!isOperator(input.charAt(i)) || validate(i, listAdd, input.charAt(i)))
					listAdd += input.charAt(i);
			else if(isOperator(input.charAt(i))) {
				returnString.add(listAdd);
				listAdd = "";
				listAdd += input.charAt(i);
				returnString.add(listAdd);
				listAdd = "";
			}
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
	
	public static boolean validate(int index, String listAdd, char addChar) {
		int isParenthesis = 0;
		if(listAdd.length() > 0)
			isParenthesis = listAdd.charAt(0) == '(' ? 1 : 0;
		if(isOperator(addChar) && listAdd.length() > 0 + isParenthesis)
			if(addChar != '/')
				return false;
		if(!listAdd.contains("_") && addChar == '/' && listAdd.length() > 0)
			return false;
		if(listAdd.contains("/"))
			return false;
		
		return true;
	}
	
	public static int containsComparison(String input) {
		int returnNum = 10;
		String[] comparison = new String[5];
		comparison[0] = ">";
		comparison[1] = "<";
		comparison[2] = ">=";
		comparison[3] = "<=";
		comparison[4] = "=";
		for(int i = 0; i < comparison.length; i++) {
			if(input.contains(comparison[i])) {
				returnNum = i;
				if(returnNum == 2 || returnNum == 3)
					break;
			}
		}
		
		return returnNum;
	}
	
	public static boolean isOperator(char input) {
		String operator = "+-*/";
		for(int i = 0; i < operator.length(); i++)
			if(input == operator.charAt(i)) 
				return true;
		return false;
	}
}
