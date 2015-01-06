package Classifier;

import java.util.ArrayList;

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

	private void learn(Instance instance) {
		//for all features
		for(int i = 0; i < instance.getDimension(); i++){
			int indexOfValue = aPriori.get(i).indexOf(instance.getFeature(i));
			if(indexOfValue > -1)
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
		
		double[] membershipProbability = new double[classes.size()];
		
		
		for(int j = 0; j < i.getDimension(); j++){
			for(int c = 0; c < membershipProbability.length; c++){
				int indexOfValue = aPriori.get(j).indexOf(i.getFeature(j));
				
				if (j == 0) 
					membershipProbability[c] = aPriori.get(j).getProbability(indexOfValue, c)/classProbability.get(c);
				else 
					membershipProbability[c] *= aPriori.get(j).getProbability(indexOfValue, c)/classProbability.get(c);
				
			}
		}
		
		for (int c = 0; c < membershipProbability.length; c++){
			membershipProbability[c] *= classProbability.get(c);
		}
		
		int maxIndex = 0;
		for(int c = 0; c < membershipProbability.length; c++){
			if(membershipProbability[maxIndex] < membershipProbability[c])
				maxIndex = c;
		}
		
		return classes.get(maxIndex);
	}

}
