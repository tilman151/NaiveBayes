package Classifier;

import java.util.ArrayList;

/**
 * A Naive Bayes classifier.
 * 
 * Implements the IClassifier interface.
 * 
 * @author Tim & Tilman
 *
 */
public class AllAboutThatBayes implements IClassifier {
	
	//contains P(a_i = v_i | C = c_j) as the number of occurrence
	private ArrayList<ProbabilityDomain> aPriori;
	//contains class names
	private ArrayList<String> classes;
	//contains P(C = c_j) as number of occurrence
	private ArrayList<Double> classProbability;
	
	
	//The final probabilities are obtained by dividing the value in aPriori/classProbability by classProability/instancesLearned
	
	public AllAboutThatBayes(){
		aPriori = new ArrayList<>();
		classProbability = new ArrayList<>();
		classes = new ArrayList<>();
	}
	
	public AllAboutThatBayes(ArrayList<String> classes){
		this.classes = classes;
		aPriori = new ArrayList<>();
		classProbability = new ArrayList<>();
	}
	
	public void setClasses(ArrayList<String> classes){
		this.classes = classes;
	}	
	
	/**
	 * Learns from a training set with Naive Bayes method 
	 * 
	 * @param training Training set to be learned from
	 */
	public void learn(Trainingset training){
		
		this.classes = training.getClasses();
		for(int i = 0; i < classes.size(); i++)
			classProbability.add(0.0);
		
		//initialize probabilities
		for(int i = 0; i < training.getDomains().size(); i++){
			aPriori.add(new ProbabilityDomain(training.getDomain(i),training.getClassCount()));
		}
		
		//for all instances
		for(int i = 0; i < training.size(); i++){
			learn(training.getInstance(i));
		}
	}

	/**
	 * Learns from a single Instance
	 * 
	 * @param instance Instance to be learned from
	 */
	private void learn(Instance instance) {
		//for all features
		for(int i = 0; i < instance.getDimension(); i++){
			int indexOfValue = aPriori.get(i).indexOf(instance.getFeature(i));
			if(indexOfValue > -1)
				// Increment the counter for the respective class for the given value of a feature
				aPriori.get(i).upOneProbability(indexOfValue, classes.indexOf(instance.getClassification()));
			else
				System.err.println("Unknown value for feature " + aPriori.get(i).getName() + " found!");
		}
		
		//add classification count
		int indexOfClass = classes.indexOf(instance.getClassification());
		double preProb = classProbability.get(indexOfClass);
		classProbability.set(indexOfClass, preProb+1);
	}
	
	public String classify(Instance i){
		
		// Initialize probability of class membership
		double[] membershipProbability = new double[classes.size()];
		
		// for each feature
		for(int j = 0; j < i.getDimension(); j++){
			// for each probable class
			for(int c = 0; c < membershipProbability.length; c++){
				// get the value
				int indexOfValue = aPriori.get(j).indexOf(i.getFeature(j));
				
				// accumulate probabilities by multiplication
				if (j == 0) 
					membershipProbability[c] = aPriori.get(j).getProbability(indexOfValue, c)/classProbability.get(c);
				else 
					membershipProbability[c] *= aPriori.get(j).getProbability(indexOfValue, c)/classProbability.get(c);
				
			}
		}
		
		// multiply with probability of class itself
		for (int c = 0; c < membershipProbability.length; c++){
			membershipProbability[c] *= classProbability.get(c);
		}
		
		int maxIndex = 0;
		for(int c = 0; c < membershipProbability.length; c++){
			if(membershipProbability[maxIndex] < membershipProbability[c])
				maxIndex = c;
		}
		
		// return class with maximal probability
		return classes.get(maxIndex);
	}

}
