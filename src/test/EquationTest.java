package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.ac.york.modules.testing.Equation;
import uk.ac.york.modules.testing.FirstOrderEquation;
import uk.ac.york.modules.testing.TestEquation;

public class EquationTest {
	
	private static Equation eq = null;
	
	@Before
	public void setUp() {
		EquationTest.eq = null;
	}
	
	@Test 
	public void createWithNullClass() {
		Equation e = Equation.createEquationFromType(null);
	}

	@Test
	public void creatingFirstOrderEquation() {
		
		Thread t = new Thread() {
			public void run() {
				EquationTest.eq = Equation.createEquationFromType(FirstOrderEquation.class);
			}
		};
		t.start();
				
		GUIHelper.inputToJOptionPane(GUIHelper.standardDelay, "1");
		GUIHelper.inputToJOptionPane(GUIHelper.standardDelay, "2");
		
		assertNotNull(EquationTest.eq);
		
		assertEquals("1.0x+2.0", EquationTest.eq.toString());
		
		//assertEquals(10, e.of(3.5), 0);
	}

}
