package Classifier;

import java.util.ArrayList;

public class AllAboutThatBayes {
	
	//contains P(a_i = v_i | C = c_i) as the number of occurrence
	private ArrayList<PropabilityDomain> aPriori;
	//contains class names
	private ArrayList<String> classes;
	//contains P(C = c_i) as number of occurrence
	private ArrayList<Double> classPropability;
	//contains the number of learned instances
	private int instancesLearned;
	
	//The final probabilities are obtained by dividing the value in aPriori/classPropability by classPropability/instancesLearned
	
	public AllAboutThatBayes(){
		instancesLearned = 0;
		aPriori = new ArrayList<>();
		classPropability = new ArrayList<>();
		classes = new ArrayList<>();
	}
	
	public AllAboutThatBayes(ArrayList<String> classes){
		this.classes = classes;
		instancesLearned = 0;
		aPriori = new ArrayList<>();
		classPropability = new ArrayList<>();
	}
	
	public void setClasses(ArrayList<String> classes){
		this.classes = classes;
	}
	
	public void learn(Trainingset training){
		
		this.classes = training.getClasses();
		for(int i = 0; i < classes.size(); i++)
			classPropability.add(0.0);
		
		//initialize probabilities
		for(int i = 0; i < training.getDomains().size(); i++){
			aPriori.add(new PropabilityDomain(training.getDomain(i),training.getClassCount()));
		}
		
		//for all instances
		for(int i = 0; i < training.size(); i++){
			learn(training.getInstance(i));
			instancesLearned++;
		}
		
		//Debug output
		System.out.println(instancesLearned);
		for(int i = 0; i < classes.size(); i++){
			System.out.println(classes.get(i) + ": " + classPropability.get(i));
			for(int j = 0; j < aPriori.size(); j++){
				for(int k = 0; k < aPriori.get(j).size(); k++)
					System.out.println("\t" + aPriori.get(j).getValue(k) + ": " + aPriori.get(j).getPropability(k, i));
				System.out.println("\n");
			}
		}
	}

	private void learn(Instance instance) {
		//for all features
		for(int i = 0; i < instance.getDimension(); i++){
			int indexOfValue = aPriori.get(i).indexOf(instance.getFeature(i));
			if(indexOfValue > -1)
				aPriori.get(i).upOnePropability(indexOfValue, classes.indexOf(instance.getClassification()));
			else
				System.err.println("Unknown value for feature" + i + "found!");
		}
		
		//add classification count
		int indexOfClass = classes.indexOf(instance.getClassification());
		double preProb = classPropability.get(indexOfClass);
		classPropability.set(indexOfClass, preProb+1);
	}

}
