package calculator;

import java.math.BigInteger;

public interface Fraction {
	BigInteger getNumerator();
	BigInteger getDenominator();
	
	Fraction add(Fraction other);
	Fraction subtract(Fraction other);
	Fraction multiply(Fraction other);
	Fraction divide(Fraction other);
	
	void simplify();
	boolean compare(Fraction other);
	
	String toString();
}
