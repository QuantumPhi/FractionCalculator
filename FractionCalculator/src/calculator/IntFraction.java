package calculator;

import java.math.BigInteger;

public class IntFraction implements Fraction {
	private int numerator;
	private int denominator;

	public BigInteger getNumerator() {
		BigInteger returnNum = new BigInteger(String.valueOf(this.numerator));
		return returnNum;
	}
	public BigInteger getDenominator() {
		BigInteger returnDenom = new BigInteger(String.valueOf(this.denominator));
		return returnDenom;
	}

	public IntFraction(boolean positive, int numerator, int denominator) {
		this.numerator = positive ? numerator : -numerator;
		this.denominator = denominator;
	}
	
	public IntFraction(boolean positive, int wholePart, int numerator, int denominator) {
		this.numerator = positive ? numerator + wholePart * denominator : -(numerator + wholePart * denominator);
		this.denominator = denominator;
	}

	
	public Fraction add(Fraction other) {
		Fraction newFraction = null;
		int totalNum = this.numerator * other.getDenominator().intValue() + other.getNumerator().intValue() * this.denominator;
		int totalDenom = this.denominator * other.getDenominator().intValue();
		newFraction = new IntFraction(true, totalNum, totalDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public Fraction subtract(Fraction other) {
		Fraction newFraction = null;
		int totalNum = this.numerator * other.getDenominator().intValue() - other.getNumerator().intValue() * this.denominator;
		int totalDenom = this.denominator * other.getDenominator().intValue();
		newFraction = new IntFraction(true, totalNum, totalDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public Fraction multiply(Fraction other) {
		int totalNum = this.numerator * other.getNumerator().intValue();
		int setDenom = this.denominator * other.getDenominator().intValue();
		Fraction newFraction = new IntFraction(true, totalNum, setDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public Fraction divide(Fraction other) {
		int totalNum = this.numerator * other.getDenominator().intValue();
		int setDenom = this.denominator * other.getNumerator().intValue();
		Fraction newFraction = new IntFraction(true, totalNum, setDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public int compare(Fraction other) {
		if(this.numerator * other.getDenominator().intValue() == other.getNumerator().intValue() * this.denominator)
			return 0;
		int returnValue = this.numerator * other.getDenominator().intValue() > other.getNumerator().intValue() * this.denominator ? 1 : -1;
		return returnValue;
	}
	
	public void simplify() {
		int factorNum = Math.abs(this.numerator);
		int factorDenom = this.denominator;
		while(factorNum != 0) {
			int temp;
			temp = factorNum;
			factorNum = factorDenom % factorNum;
			factorDenom = temp;
		}
		this.numerator /= factorDenom;
		this.denominator /= factorDenom;
		if(this.numerator < 0 && this.denominator < 0) {
			this.numerator *= -1;
			this.denominator *= -1;
		}
		if(this.denominator < 0 && this.numerator > 0) {
			this.numerator *= -1;
			this.denominator = Math.abs(this.denominator);
		}
	}
	
	public String toString() {
		String returnString = "";
		int wholePart = 0;
		int setNum = 0;
		if(Math.abs(this.numerator) >= Math.abs(this.denominator)) {
			setNum = this.numerator % this.denominator;
			wholePart = (this.numerator - setNum) / this.denominator;
		}
		if(setNum == 0)
			returnString += wholePart;
		else {
			returnString = wholePart == 0 ? returnString : returnString + wholePart + "_";
			returnString = wholePart == 0 ? returnString + this.numerator + "/" : returnString + Math.abs(setNum) + "/";
			returnString += this.denominator;
		}
		
		return returnString;
	}
}
