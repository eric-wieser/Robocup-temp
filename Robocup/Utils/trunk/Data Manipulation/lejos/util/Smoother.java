package lejos.util;
public class Smoother extends BasicSmoother
{
	private BasicSmoother	NaNSmoother;
	protected double average = Double.NaN;
	private double NaNProb  = 0;
	public Smoother(double t)
	{
		super(t);
		NaNSmoother = new BasicSmoother(t);
	}
	
	public double getOutput()
	{
		return average;
	}
	
	public void setInput(double input)
	{
		super.setInput(input);
		
		boolean isNaN = Double.isNaN(input);
		NaNProb = NaNSmoother.getOutput(isNaN ? 1 : 0);
		if(NaNProb > 0.5)
			average = Double.NaN;
		else
			average = super.getOutput();
		
	}
}