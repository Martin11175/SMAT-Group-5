package uk.ac.york.modules.testing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

@SuppressWarnings({ "serial", "unchecked" })
public class EquationPanel extends JPanel {

	
	/**
	 * The equation.
	 */
	public Equation equation;
	
	
	/**
	 * Takes an equation and assigns it to the class member.
	 * 
	 * @param equation
	 */
	public EquationPanel(Equation equation) {
		super();
		this.equation = equation;
		this.setBackground(Color.white);
		series[0] = new ArrayList<Double>();
		series[1] = new ArrayList<Double>();

		this.populate(10);
		
		// Add space to max and min
		maxX *= 1.1;
		minX *= 1.1;
		maxY *= 1.1;
		minY *= 1.1;
	}
	/**
	 * The list of points.
	 */
	public ArrayList<Double> []series = new ArrayList[2];

	/**
	 * The size of all bs
	 */
	int b = 25;
	
	/**
	 * The maximum and minimum represented values of x and y.
	 */
	double maxX = 5;
	double maxY = 5;
	double minX = -5;
	double minY = -5;
	

	/**
	 * The number of digits of the scales of X and Y.
	 */
	int nDigitsX = 1;
	int nDigitsY = 1;
	
	
	/**
	 * Popup menu on this graph.
	 */
	JPopupMenu popup=null;

	
	/**
	 * Populates the series of numbers.
	 * 
	 * @param max the maximum of the series.
	 */
	public void populate(double max) {
		double step = max/1000;
		for (int i = -1000; i<1000; i=i+1) {
			try{
				this.addValue(i*step, equation.of(i*step));
			} catch (ArithmeticException e) {
				this.addValue(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
			}
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// we get the graph component
		Graphics2D g2 = (Graphics2D)g;
		// we get the size of the area to paint 
		double w = getWidth();
		double h = getHeight();
		
		// the step between points
		int pointStep = 1;
		if ((w!=0)&&(series[0]!=null)&&(series[0].size()>w))
			pointStep = (int) (series[0].size()/w);

		// Calculate position of x and y axis
		double xAxisHeight = h - b - ((h - 2*b) * (-minY / (maxY - minY)));
		double yAxisWidth = b + ((w - 2*b) * (-minX / (maxX - minX)));
		
		// Draw y-axis.
		g2.draw(new Line2D.Double(yAxisWidth, b, yAxisWidth, h-b));
		// Draw x-axis.
		g2.draw(new Line2D.Double(b, xAxisHeight, w-b, xAxisHeight));
		g2.drawString("y="+equation.toString(), b , b-5);
		
		// we draw the y-labels
		double ystep = Math.pow(10, nDigitsY);
		if (ystep*2 > maxY && -ystep*2 < minY) 
			ystep = Math.pow(10, nDigitsY-1)*2;
		for (int i = -10; i < 10; i++) {
			int grade = (int)(i*ystep);
			if (grade > maxY) {
				break;
			} else if (grade < minY) {
				continue;
			} else if (grade == 0) {
				continue;
			}
			double y1 = h - b - ((h - 2*b)*((grade - minY) / (maxY - minY)));
			g2.draw(new Line2D.Double(yAxisWidth - 1, y1, yAxisWidth + 1, y1));
			g2.drawString(""+grade, (int)((yAxisWidth) + (7*nDigitsY)), (int)y1+4);
		}

		// we draw the x-labels
		double xstep = Math.pow(10, nDigitsX);
		if (xstep*2 > maxX && -xstep*2 < minX) 
			xstep = Math.pow(10, nDigitsX-1)*2;
		for (int i = -10; i < 10; i++) {
			int grade = (int)(i*xstep);
			if (grade > maxX) {
				break;
			} else if (grade < minX) {
				continue;
			} else if (grade == 0) {
				continue;
			}
			double x1 = b + ((w - 2*b)*((grade - minX) / (maxX - minX)));
			g2.draw(new Line2D.Double(x1, xAxisHeight - 1, x1, xAxisHeight + 1));
			g2.drawString(""+grade, (int)(x1-(nDigitsX*4)-2), (int) (xAxisHeight+17));
		}

		// we draw data points.
		try {
			g2.setPaint(Color.red);
			int size = series[0].size();
			for(int i = 0; i < size; i+=pointStep) {
				// Skip infinity points
				if(series[0].get(i) == Double.POSITIVE_INFINITY
						|| series[1].get(i) == Double.POSITIVE_INFINITY)
					continue;
				double x = b + (w - 2*b)*((series[0].get(i) - minX) / (maxX - minX));
				double y =  h - b - (h - 2*b)*((series[1].get(i) - minY) / (maxY - minY));
				g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
			}
		} catch (NullPointerException e) {
			System.out.println("Ouch");
		}
	}


	
	/**
	 * Adds a value to draw.
	 * 
	 * @param x the x value.
	 * @param y the y value
	 */
	public void addValue(double x, double y) {
		// if the maximum or minimum of x and y are too low we extend them.
		if (x != Double.POSITIVE_INFINITY && y != Double.POSITIVE_INFINITY) {
			if (maxX<x) {
				maxX=x;
				int digits = (int)Math.floor(Math.log10(maxX));
				nDigitsX = digits > nDigitsX ? digits : nDigitsX;
			}
			if (minX>x) {
				minX=x;
				int digits = (int)Math.floor(Math.log10(-minX));
				nDigitsX = digits > nDigitsX ? digits : nDigitsX;
			}
			if (maxY<y) {
				maxY=y;
				int digits = (int)Math.floor(Math.log10(maxY));
				nDigitsY = digits > nDigitsY ? digits : nDigitsY;
			}
			if (minY>y) {
				minY=y;
				int digits = (int)Math.floor(Math.log10(-minY));
				nDigitsY = digits > nDigitsY ? digits : nDigitsY;
			}
		}
		// we add the point to the graph
		series[0].add(x);
		series[1].add(y);
	}
}
