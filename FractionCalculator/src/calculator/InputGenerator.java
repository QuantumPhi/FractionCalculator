package calculator;

import java.io.*;
import java.util.*;

public class InputGenerator {
	public void generate(int number, int cyclesLimit, int numberSize) {
		try {
			File file = new File("TextInput");
			file.delete();
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			for(int i = 0; i < number; i++) {
				Random generator = new Random();
				String addString;
				String operatorString = "+-*/";
				int cycles = 0;
				int j = 0;
				String writeString = "";
				while(cycles < 1)
					cycles = generator.nextInt(cyclesLimit + 1);
				while(j < cycles) {
					addString = "";
					boolean positive = generator.nextBoolean();
					boolean wholeNumber = generator.nextBoolean();
					if(!wholeNumber) {
						int wholePart = generator.nextInt(numberSize + 1);
						int numerator = generator.nextInt(numberSize + 1);
						int denominator = generator.nextInt(numberSize + 1);
						while(denominator == 0) 
							denominator = generator.nextInt(numberSize + 1);
						while(writeString.length() >= 2 && numerator == 0 && wholePart == 0 && writeString.charAt(writeString.length() - 2) == '/' ) {
							wholePart = generator.nextInt(numberSize + 1);
							numerator = generator.nextInt(numberSize + 1);
						}
						IntFraction newFraction = new IntFraction(positive, wholePart, numerator, denominator);
						addString += newFraction.toString();
					}
					else {
						int writeNumber = generator.nextInt(numberSize + 1);
						while(writeString.length() >= 2 && writeNumber == 0 && writeString.charAt(writeString.length() - 2) == '/')
							writeNumber = generator.nextInt(numberSize + 1);
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
				if(i < number - 1)
					writer.append(writeString + System.getProperty("line.separator"));
				else
					writer.append(writeString);
				writeString = "";
			}
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
