package calculator;

import java.math.BigInteger;

public class BigFraction implements Fraction {
	private BigInteger numerator;
	private BigInteger denominator;

	public BigInteger getNumerator() {
		BigInteger returnNum = new BigInteger(String.valueOf(this.numerator));
		return returnNum;
	}
	public BigInteger getDenominator() {
		BigInteger returnDenom = new BigInteger(String.valueOf(this.denominator));
		return returnDenom;
	}

	public BigFraction(boolean positive, BigInteger numerator, BigInteger denominator) {
		this.numerator = positive ? numerator : numerator.negate();
		this.denominator = denominator;
	}
	
	public BigFraction(boolean positive, BigInteger wholePart, BigInteger numerator, BigInteger denominator) {
		this.numerator = positive ? numerator.add(wholePart.multiply(denominator)) : numerator.add(wholePart.multiply(denominator)).negate();
		this.denominator = denominator;
	}

	
	public Fraction add(Fraction other) {
		Fraction newFraction = null;
		BigInteger totalNum = this.numerator.multiply(other.getDenominator()).add(other.getNumerator().multiply(this.denominator));
		BigInteger totalDenom = this.denominator.multiply(other.getDenominator());
		newFraction = new BigFraction(true, totalNum, totalDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public Fraction subtract(Fraction other) {
		Fraction newFraction = null;
		BigInteger totalNum = this.numerator.multiply(other.getDenominator()).subtract(other.getNumerator().multiply(this.denominator));
		BigInteger totalDenom = this.denominator.multiply(other.getDenominator());
		newFraction = new BigFraction(true, totalNum, totalDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public Fraction multiply(Fraction other) {
		BigInteger totalNum = this.numerator.multiply(other.getNumerator());
		BigInteger setDenom = this.denominator.multiply(other.getDenominator());
		Fraction newFraction = new BigFraction(true, totalNum, setDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public Fraction divide(Fraction other) {
		BigInteger totalNum = this.numerator.multiply(other.getDenominator());
		BigInteger setDenom = this.denominator.multiply(other.getNumerator());
		Fraction newFraction = new BigFraction(true, totalNum, setDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public int compare(Fraction other) {
		return this.numerator.multiply(other.getDenominator()).compareTo(other.getNumerator().multiply(this.denominator));
	}
	
	public void simplify() {
		BigInteger factorNum = this.numerator.abs();
		BigInteger factorDenom = this.denominator;
		while(!factorNum.equals(BigInteger.ZERO)) {
			BigInteger temp;
			temp = factorNum;
			factorNum = factorDenom.mod(factorNum);
			factorDenom = temp;
		}
		this.numerator = this.numerator.divide(factorDenom);
		this.denominator = this.denominator.divide(factorDenom);
		if(!this.numerator.equals(this.numerator.abs()) && !this.denominator.equals(this.denominator.abs())) {
			this.numerator = this.numerator.negate();
			this.numerator = this.denominator.negate();
		}
		if(this.numerator.equals(this.numerator.abs()) && !this.denominator.equals(this.denominator.abs())) {
			this.numerator = this.numerator.negate();
			this.denominator = this.denominator.abs();
		}
	}
	
	public String toString() {
		String returnString = "";
		BigInteger wholePart = BigInteger.ZERO;
		BigInteger setNum = this.numerator.mod(this.denominator);
		if(this.numerator.abs().compareTo(this.denominator.abs()) >= 0)
<<<<<<< HEAD
			wholePart = this.numerator.subtract(setNum).divide(this.denominator);
		if(numerator.mod(denominator).equals(BigInteger.ZERO))
=======
			wholePart = this.numerator.subtract(setNum).divide(this.denominator);
		if(setNum.equals(BigInteger.ZERO)) {
			wholePart = this.numerator.divide(this.denominator);
>>>>>>> branch 'master' of https://github.com/QuantumPhi/FractionCalculator.git
			returnString += wholePart;
		}
		else {
			returnString = wholePart.equals(BigInteger.ZERO) ? returnString : returnString + wholePart + "_";
			returnString = wholePart.equals(BigInteger.ZERO) ? returnString + this.numerator + "/" : returnString + setNum.abs() + "/";
			returnString += this.denominator;
		}
		
		return returnString;
	}
}
