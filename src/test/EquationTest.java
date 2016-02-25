package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.ac.york.modules.testing.Equation;
import uk.ac.york.modules.testing.FirstOrderEquation;
import uk.ac.york.modules.testing.FractionEquation;
import uk.ac.york.modules.testing.cancelException;


public class EquationTest {
	
	private static Equation eq = null;
	
	@Before
	public void setUp() {
		EquationTest.eq = null;
	}
	
	@Test 
	public void createWithNullClass() {
		try {
			Equation e = Equation.createEquationFromType(null);
		} catch (cancelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void creatingFirstOrderEquation() {
		
		Thread t = new Thread() {
			public void run() {
				try {
					EquationTest.eq = Equation.createEquationFromType(FirstOrderEquation.class);
				} catch (cancelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		t.start();				
		GUIHelper.inputToJOptionPane(600, "2");
		GUIHelper.inputToJOptionPane(600, "4");		
		assertNotNull(EquationTest.eq);
		assertEquals("2.0x+4.0", EquationTest.eq.toString());		
		assertEquals(12, EquationTest.eq.of(4.0), 0);
	}
	
	@Test
	public void creatingFractionEquation() {
		
		Thread t = new Thread() {
			public void run() {
				try {
					EquationTest.eq = Equation.createEquationFromType(FractionEquation.class);
				} catch (cancelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		t.start();				
		GUIHelper.inputToJOptionPane(GUIHelper.standardDelay, "1");
		GUIHelper.inputToJOptionPane(GUIHelper.standardDelay, "2");
			
		assertNotNull(EquationTest.eq);
		assertEquals("1.0/(x+2.0)", EquationTest.eq.toString());
		assertEquals(1, EquationTest.eq.of(-1), 0);
	}

}
