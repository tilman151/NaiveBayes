package Main;

import java.util.ArrayList;

import DataTransfer.InstanceReader;
import DataTransfer.MetaDataReader;
import Classifier.AllAboutThatBayes;
import Classifier.Domain;
import Classifier.Trainingset;

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
		
		classifier.learn(t);
	}
	
}
