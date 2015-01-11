package Classifier;

public interface IClassifier {

	/**
	 * Classifies an instance
	 * 
	 * @param i Instance to be classified
	 * @return classification
	 */
	public String classify(Instance i);	
}
