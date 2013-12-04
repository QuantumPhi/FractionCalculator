package calculator;

import java.io.*;
import java.util.*;

public class InputGenerator {
	public static void main(String[] args) {
		InputGenerator inputGen = new InputGenerator();
		inputGen.generate(50, 5);
		System.out.println("Program finished");
	}
	
	public void generate(int number, int cyclesLimit) {
		try {
			//Modify as necessary
			File file = new File("Z:/git//FractionCalculator//FractionCalculator//src//calculator//TextFile.txt");
			PrintWriter writer = new PrintWriter(file);
			for(int i = 0; i < number; i++) {
				Random generator = new Random();
				String addString;
				String operatorString = "+-*/";
				int cycles = 0;
				int j = 0;
				String writeString = "";
				while(cycles < 2)
					cycles = generator.nextInt(cyclesLimit + 1);
				while(j < cycles) {
					addString = "";
					boolean positive = generator.nextBoolean();
					boolean wholeNumber = generator.nextBoolean();
					if(!wholeNumber) {
						int wholePart = generator.nextInt(100);
						int numerator = generator.nextInt(100);
						int denominator = 0;
						while(denominator == 0) 
							denominator = generator.nextInt(100);
						IntFraction newFraction = new IntFraction(positive, wholePart, numerator, denominator);
						addString += newFraction.toString();
					}
					else {
						int writeNumber = generator.nextInt(100);
						if(!positive)
							writeNumber = -writeNumber;
						addString += writeNumber;
					}
					
					int operatorIndex = generator.nextInt(4);
					if(j < cycles - 1)
						addString += " " + operatorString.charAt(operatorIndex) + " ";
					writeString += addString;
					j++;
				}
				//TODO: Fix write
				writer.println(writeString);
				writeString = "";
			}
			writer.close();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
