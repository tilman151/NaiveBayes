package Classifier;

/**
 * Domain of a feature
 * 
 * @author Tilman & Tim
 *
 */
public class Domain {

	protected String name;
	protected String[] values;
	
	public Domain(String name, String[] values){
		this.name = name;
		this.values = values;
	}
	
	public String getValue(int index){
		return values[index];
	}
	
	public String getName(){
		return name;
	}
	
	public int size(){
		return values.length;
	}
	
	public String toString(){
		String res = name + "(";
		for(String s : values){
			res += s + ",";
		}
		return res + ")";
	}
	
	public int indexOf(String value){
		for(int i = 0; i < values.length; i++){
			if(values[i].compareTo(value) == 0)
				return i;
		}
		return -1;
	}
	
}
