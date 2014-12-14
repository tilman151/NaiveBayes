package Classifier;

public class PropabilityDomain extends Domain {

	double[][] propabilities;
	
	public PropabilityDomain(String name, String[] values, int classCount) {
		super(name, values);
		propabilities = new double[values.length][classCount];
	}
	
	public PropabilityDomain(Domain org, int classCount){
		super(org.name,org.values);
		propabilities = new double[values.length][classCount];
	}

	public double getPropability(int index, int classNum){
		return propabilities[index][classNum];
	}
	
	public void setPropability(int index, int classNum, double val){
		propabilities[index][classNum] = val;
	}
	
	public void upOnePropability(int index, int classNum){
		propabilities[index][classNum]++;
	}
}
