/**
 * 
 */
package uk.ac.york.modules.testing;

/**
 * This class represents a first order equation (f(x)=ax+b)
 * 
 * @author Manuel Oriol (manuel@cs.york.ac.uk)
 * @date Feb 14, 2010
 *
 */
public class SecondOrderEquation extends Equation {

	double a;
	double b;
	double c;
	
	/**
	 * Creates a first-order equation.
	 * 
	 * @param a a in ax+b
	 * @param b b in ax+b
	 */
	public SecondOrderEquation(double a, double b, double c) {
		this.a=a;
		this.b=b;
		this.c=c;
	}
	
	/* (non-Javadoc)
	 * @see uk.ac.york.modules.testing.Equation#toString()
	 */
	@Override
	public String toString() {
		return a + "x^2+" + b +"x+" +c;
	}

	/* (non-Javadoc)
	 * @see uk.ac.york.modules.testing.Equation#of(double)
	 */
	@Override
	public double of(double x) {
		return Math.pow(a*x, 2) + (b * x) + c;
	}
	
}
