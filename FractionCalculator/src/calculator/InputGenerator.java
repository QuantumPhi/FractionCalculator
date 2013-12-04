package calculator;

import java.io.*;
import java.util.*;

public class InputGenerator {
	public void generate(int number, int cyclesLimit) {
		for(int i = 0; i < number; i++) {
			Random generator = new Random();
			String addString;
			String operatorString = "+-*/";
			int cycles = 0;
			int j = 0;
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
				System.out.print(addString);
				j++;
			}
			System.out.println();
		}
	}
}
