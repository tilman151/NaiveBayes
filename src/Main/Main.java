package Main;

import java.util.ArrayList;

import DataTransfer.InstanceReader;
import DataTransfer.MetaDataReader;
import Classifier.AllAboutThatBayes;
import Classifier.Domain;
import Classifier.Trainingset;
import Classifier.Validator;

public class Main {

	public static void main(String args[]) {
		InstanceReader input = new InstanceReader("./car.data");
		Trainingset t = input.readInstances();

		MetaDataReader meta = new MetaDataReader("./car.c45-names");
		ArrayList<Domain> d = meta.readDomains();
		ArrayList<String> c = meta.readClasses();

		t.setClasses(c);
		t.setDomains(d);

		Trainingset testSet = null;
		double mean = 0;
		Validator v = null;
		
		for (int i = 0; i < 100; i++) {

			AllAboutThatBayes classifier = new AllAboutThatBayes();

			testSet = t.splitUpTestSet(33);

			classifier.learn(t);

			v = new Validator(classifier);
			mean += v.validateOnTestSet(testSet);
			
			t = input.readInstances();
			t.setClasses(c);
			t.setDomains(d);
		}
		System.out.println("Mean error over 100 samples: "
				+ (1-(mean/100.0)));
		
		int[][] confusion = v.computeConfusionMatrix(testSet);

		printConfusion(confusion, testSet.getClasses());

	}

	private static void printConfusion(int[][] confusion,
			ArrayList<String> classes) {
		System.out.print("\t | \t");
		for (int j = 0; j < confusion.length; j++)
			System.out.print(classes.get(j) + "\t | \t");
		System.out.println("");
		for (int i = 0; i < confusion.length; i++) {
			System.out.print(classes.get(i) + "  \t | \t");
			for (int j = 0; j < confusion.length; j++) {
				System.out.print(confusion[i][j] + "\t | \t");
			}
			System.out.println("");
		}
	}

}
