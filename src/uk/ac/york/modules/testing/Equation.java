/**
 * 
 */
package uk.ac.york.modules.testing;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;

/**
 * This class represents an equation.
 * Note that there should be only one constructor for a given equation type with only double arguments.
 * 
 * @author Manuel Oriol (manuel@cs.york.ac.uk)
 * @date Feb 14, 2010
 *
 */
public abstract class Equation {

	
	/**
	 * Static method creating an instance of a given Equation type. 
	 * The method asks values of parameters through option panes.
	 * 
	 * @param equationType the class of the equation 
	 * @return the Equation
	 * @throws cancelException 
	 */
	@SuppressWarnings("unchecked")
	public static Equation createEquationFromType(Class equationType) throws cancelException {
		Constructor c =  equationType.getConstructors()[0];
		int n_arguments = c.getParameterTypes().length;
		Object[] arguments = new Double [n_arguments];
		for (int i=0; i<n_arguments;i++) {
			
			boolean validInput = false;
			while (!validInput) {
				//ask for values
				String s = JOptionPane.showInputDialog(null, ((char)(((byte)'a')+i))+" =", 
						"Enter argument", JOptionPane.QUESTION_MESSAGE);
				if (s == null) {
					throw new cancelException();
				} else if (s.length() == 0) {
					JOptionPane.showMessageDialog(null, "Please enter a value");
				} else {
					try {
						arguments[i] = Double.parseDouble(s);
						validInput = true;
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Please enter a valid number");
					}
				}
			}
		}
		
		try {
			// we return the new instance
			return (Equation)c.newInstance(arguments);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "alert", "alert", JOptionPane.ERROR_MESSAGE);
			return null;
		}		
	}
	
	@Override
	public String toString() {
		return "A "+this.getClass().getName();
	}
	
	/**
	 * Calculates the value of this equation for x.
	 * 
	 * @param x the x to use with f(x)
	 * @return the result for this equation given x.
	 */
	public abstract double of(double x);
	
}
