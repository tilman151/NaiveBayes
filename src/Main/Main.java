package Main;

import java.util.ArrayList;

import DataTransfer.InstanceReader;
import DataTransfer.MetaDataReader;
import Classifier.AllAboutThatBayes;
import Classifier.Domain;
import Classifier.Instance;
import Classifier.Trainingset;
import Classifier.Validator;

public class Main {

	public static void main(String args[]){
		InstanceReader input = new InstanceReader("./car.data");
		Trainingset t = input.readInstances();
		
		MetaDataReader meta = new MetaDataReader("./car.c45-names");
		ArrayList<Domain> d = meta.readDomains();
		ArrayList<String> c = meta.readClasses();
		
		t.setClasses(c);
		t.setDomains(d);
		
		AllAboutThatBayes classifier = new AllAboutThatBayes();
		
		Trainingset testSet = t.splitUpTestSet(33);
		
		classifier.learn(t);
		
		ArrayList<String> features = new ArrayList<>();
		features.add("vhigh");
		features.add("vhigh");
		features.add("2");
		features.add("4");
		features.add("small");
		features.add("med");
		Instance i = new Instance(features,"unacc");
		
		System.out.println(classifier.classify(i));
		
//		Validator v = new Validator(classifier);
//		System.out.println(v.validateOnTestSet(testSet));
		
	}
	
}
