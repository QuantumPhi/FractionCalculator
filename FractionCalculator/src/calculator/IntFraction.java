package calculator;

public class IntFraction {
	private boolean positive;
	private int wholePart;
	private int numerator;
	private int denominator;
	
	public boolean isPositive() {
		return positive;
	}
	public void setPositive(boolean positive) {
		this.positive = positive;
	}

	public int getWholePart() {
		return wholePart;
	}
	public void setWholePart(int wholePart) {
		this.wholePart = wholePart;
	}

	public int getNumerator() {
		return numerator;
	}
	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}

	public int getDenominator() {
		return denominator;
	}
	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}

	public IntFraction(boolean positive, int numerator, int denominator) {
		this.positive = positive;
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	public IntFraction(boolean positive, int wholePart, int numerator, int denominator) {
		this.positive = positive;
		this.wholePart = wholePart;
		this.numerator = numerator;
		this.denominator = denominator;
	}

	
	public IntFraction add(IntFraction other) {
		IntFraction newFraction = null;
		boolean totalPositive = true;
		int thisNum = this.numerator + this.wholePart * this.denominator;
		if(this.positive == false)
			thisNum *= -1;
		int otherNum = other.getNumerator() + other.getWholePart() * other.getDenominator();
		if(other.isPositive() == false)
			otherNum *= -1;
		int totalNum = 0;
		int setDenom = 0;
		if(this.denominator == other.getDenominator()) {
			totalNum = thisNum + otherNum;
			setDenom = this.denominator;
			if(totalNum < 0)
				totalPositive = false;
			newFraction = new IntFraction(totalPositive, totalNum, setDenom);
		}
		else {
			totalNum += thisNum * other.getDenominator();
			totalNum += otherNum * this.denominator;
			setDenom = this.denominator * other.getDenominator();
			if(totalNum < 0)
				totalPositive = false;
			newFraction = new IntFraction(totalPositive, totalNum, setDenom);
		}
		
		newFraction.simplify();
		return newFraction;
	}
	
	public IntFraction subtract(IntFraction other) {
		IntFraction newFraction = null;
		boolean totalPositive = true;
		int thisNum = this.numerator + this.wholePart * this.denominator;
		if(this.positive == false)
			thisNum *= -1;
		int otherNum = other.getNumerator() + other.getWholePart() * other.getDenominator();
		if(other.isPositive() == false)
			otherNum *= -1;
		int totalNum = 0;
		int setDenom = 0;
		if(this.denominator == other.getDenominator()) {
			totalNum = thisNum - otherNum;
			setDenom = this.denominator;
			if(totalNum < 0)
				totalPositive = false;
			newFraction = new IntFraction(totalPositive, totalNum, setDenom);
		}
		else {
			totalNum += thisNum * other.getDenominator();
			totalNum -= other.getNumerator() * this.denominator;
			setDenom = this.denominator * other.getDenominator();
			if(totalNum < 0)
				totalPositive = false;
			newFraction = new IntFraction(totalPositive, totalNum, setDenom);
		}
		
		newFraction.simplify();
		return newFraction;
	}
	
	public IntFraction multiply(IntFraction other) {
		boolean totalPositive = this.positive == other.isPositive();
		int totalNum = (this.wholePart * this.denominator + this.numerator) * other.getNumerator();
		int setDenom = this.denominator * other.getDenominator();
		IntFraction newFraction = new IntFraction(totalPositive, totalNum, setDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public IntFraction divide(IntFraction other) {
		boolean totalPositive = this.positive == other.isPositive();
		int totalNum = (this.wholePart * this.denominator + this.numerator) * other.getDenominator();
		int setDenom = this.denominator * (other.getWholePart() * other.getDenominator() + other.getNumerator());
		IntFraction newFraction = new IntFraction(totalPositive, totalNum, setDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public void simplify() {
		while(this.numerator >= this.denominator) {
			this.setNumerator(this.numerator - this.denominator);
			this.setWholePart(this.wholePart + 1);
		}
		int factorNum = this.numerator;
		int factorDenom = this.denominator;
		while(factorNum != 0) {
			int temp;
			temp = factorNum;
			factorNum = factorDenom % factorNum;
			factorDenom = temp;
		}
		this.setNumerator(this.numerator / factorDenom);
		this.setDenominator(this.denominator / factorDenom);
	}
	
	public String toString() {
		String returnString = "";
		if(!this.positive)
			returnString += "-";
		if(this.wholePart != 0)
			returnString += this.wholePart + "_";
		returnString += this.numerator + "/";
		returnString += this.denominator;
		
		return returnString;
	}
}
