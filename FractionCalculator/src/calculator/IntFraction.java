package calculator;

public class IntFraction {
	private int wholePart;
	private int numerator;
	private int denominator;

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
		if(positive)
			this.numerator = numerator;
		else
			this.numerator = -numerator;
		this.denominator = denominator;
	}
	
	public IntFraction(boolean positive, int wholePart, int numerator, int denominator) {
		if(positive) {
			this.wholePart = wholePart;
			this.numerator = numerator;
		}
		else {
			this.wholePart = -wholePart;
			this.numerator = -numerator;
		}
		this.denominator = denominator;
	}

	
	public IntFraction add(IntFraction other) {
		IntFraction newFraction = null;
		int totalWhole = this.wholePart + other.getWholePart();
		int totalNum = this.numerator * other.getDenominator() + other.getNumerator() * this.denominator;
		int totalDenom = this.denominator * other.getDenominator();
		newFraction = new IntFraction(true, totalWhole, totalNum, totalDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public IntFraction subtract(IntFraction other) {
		IntFraction newFraction = null;
		int totalWhole = this.wholePart - other.getWholePart();
		int totalNum = this.numerator * other.getDenominator() - other.getNumerator() * this.denominator;
		int totalDenom = this.denominator * other.getDenominator();
		newFraction = new IntFraction(true, totalWhole, totalNum, totalDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public IntFraction multiply(IntFraction other) {
		int totalNum = (this.wholePart * this.denominator + this.numerator) * (other.getWholePart() * other.getDenominator() + other.getNumerator());
		int setDenom = this.denominator * other.getDenominator();
		IntFraction newFraction = new IntFraction(true, totalNum, setDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public IntFraction divide(IntFraction other) {
		int totalNum = (this.wholePart * this.denominator + this.numerator) * other.getDenominator();
		int setDenom = this.denominator * (other.getWholePart() * other.getDenominator() + other.getNumerator());
		IntFraction newFraction = new IntFraction(true, totalNum, setDenom);
		newFraction.simplify();
		return newFraction;
	}
	
	public boolean compare(IntFraction other) {
		return this.numerator + this.wholePart * this.denominator > other.getNumerator()
				+ other.getWholePart() * other.getDenominator();
	}
	
	public void simplify() {
		while(Math.abs(this.numerator) >= this.denominator) {
			if(this.numerator >= 0) {
				this.setNumerator(this.numerator - this.denominator);
				this.setWholePart(this.wholePart + 1);
			}
			else {
				this.setNumerator(this.numerator + this.denominator);
				this.setWholePart(this.wholePart - 1);
			}
		}
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
		if(this.wholePart != 0)
			returnString += this.wholePart + "_";
		returnString += this.numerator + "/";
		returnString += this.denominator;
		
		return returnString;
	}
}
