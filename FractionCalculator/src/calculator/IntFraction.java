package calculator;

public class IntFraction {
	private int numerator;
	private int denominator;

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
		this.numerator = positive ? numerator : -numerator;
		this.denominator = denominator;
	}
	
	public IntFraction(boolean positive, int wholePart, int numerator, int denominator) {
		this.numerator = positive ? numerator + wholePart * denominator : -(numerator + wholePart * denominator);
		this.denominator = denominator;
	}

	
	public IntFraction add(IntFraction other) {
		IntFraction newFraction = null;
		int totalNum = this.numerator * other.getDenominator() + other.getNumerator() * this.denominator;
		int totalDenom = this.denominator * other.getDenominator();
		newFraction = new IntFraction(true, totalNum, totalDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public IntFraction subtract(IntFraction other) {
		IntFraction newFraction = null;
		int totalNum = this.numerator * other.getDenominator() - other.getNumerator() * this.denominator;
		int totalDenom = this.denominator * other.getDenominator();
		newFraction = new IntFraction(true, totalNum, totalDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public IntFraction multiply(IntFraction other) {
		int totalNum = this.numerator * other.getNumerator();
		int setDenom = this.denominator * other.getDenominator();
		IntFraction newFraction = new IntFraction(true, totalNum, setDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public IntFraction divide(IntFraction other) {
		int totalNum = this.numerator * other.getDenominator();
		int setDenom = this.denominator * other.getNumerator();
		IntFraction newFraction = new IntFraction(true, totalNum, setDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public boolean compare(IntFraction other) {
		return this.numerator * other.getDenominator() > other.getNumerator() * this.numerator;
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
		this.setNumerator(this.numerator / factorDenom);
		this.setDenominator(this.denominator / factorDenom);
	}
	
	public String toString() {
		String returnString = "";
		int wholePart = 0;
		while(Math.abs(this.numerator) >= Math.abs(this.denominator)) {
			this.numerator = this.numerator > 0 ? this.numerator - this.denominator : this.numerator + this.denominator;
			wholePart = this.numerator > 0 ? wholePart + 1 : wholePart - 1;
		}
		returnString = wholePart == 0 ? returnString : returnString + wholePart + "_";
		returnString += this.numerator + "/";
		returnString += this.denominator;
		
		return returnString;
	}
}
