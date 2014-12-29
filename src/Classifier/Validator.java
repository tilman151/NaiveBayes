package Classifier;

public class Validator {

	private IClassifier classifier;
	
	public Validator(IClassifier c){
		this.classifier = c;
	}
	
	public double validateOnTestSet(Trainingset testSet){
		
		int wronglyClassified = 0;
		
		for(int i = 0; i < testSet.size(); i++){
			if(classifier.classify(testSet.getInstance(i)).compareTo(testSet.getInstance(i).getClassification()) != 0)
				wronglyClassified++;
		}
		
		return 1 - ((double) wronglyClassified  / (double) testSet.size());
	}
	
}
