package Classifier;

public class ProbabilityDomain extends Domain {

	double[][] probabilities;
	
	public ProbabilityDomain(String name, String[] values, int classCount) {
		super(name, values);
		probabilities = new double[values.length][classCount];
	}
	
	public ProbabilityDomain(Domain org, int classCount){
		super(org.name,org.values);
		probabilities = new double[values.length][classCount];
	}

	public double getProbability(int index, int classNum){
		return probabilities[index][classNum];
	}
	
	public void setProbability(int index, int classNum, double val){
		probabilities[index][classNum] = val;
	}
	
	public void upOneProbability(int index, int classNum){
		probabilities[index][classNum]++;
	}
}
