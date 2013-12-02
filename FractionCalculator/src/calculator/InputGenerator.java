package calculator;

import java.io.*;
import java.util.*;

public class InputGenerator {
	public void generate(int number) {
		try {
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter("TestInput"));
			for(int i = 0; i < number; i++) {
				Random generator = new Random();
				String addString = "";
				String operatorString = "+-*/";
				int j = 0;
				int cycles = generator.nextInt(10);
				while(j < cycles) {
					boolean positive = generator.nextBoolean();
					int wholeNumber = generator.nextInt(100);
					if(wholeNumber > 49) {
						int wholePart = generator.nextInt();
						int numerator = generator.nextInt();
						int denominator = generator.nextInt();
						IntFraction newFraction = new IntFraction(positive, wholePart, numerator, denominator);
						addString += newFraction.toString();
					}
					else {
						int writeNumber = generator.nextInt();
						if(!positive)
							writeNumber = -writeNumber;
						addString += writeNumber;
					}
					
					int operatorIndex = generator.nextInt(4);
					if(i < number - 1)
						addString += " " + operatorString.charAt(operatorIndex) + " ";
					fileWriter.write(addString);
				}
			}
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
