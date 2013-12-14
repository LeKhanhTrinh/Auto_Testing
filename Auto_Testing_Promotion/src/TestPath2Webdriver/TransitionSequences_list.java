package TestPath2Webdriver;

import java.util.ArrayList;

public class TransitionSequences_list {
	ArrayList<TransitionSequences> arrTransq;
	
	public TransitionSequences_list(){
		arrTransq = new ArrayList<TransitionSequences>();
	}
	
	public void addTransitionsq(TransitionSequences _tsq){
		arrTransq.add(_tsq);
	}
	
	public TransitionSequences getTransitionByIndex(int i){
		return arrTransq.get(i);
	}
	
	
	public int getSize(){
		return arrTransq.size();
	}
	
	public void removeTransq(int i){
		arrTransq.remove(i);
	}
	
	
	public void removeTransq(TransitionSequences transq){
		arrTransq.remove(transq);
	}
	
	public String logElems(){
		String result = "";
		result += "Number of Transition Sequence: " + getSize() + "\n";
		
		for (int i=0 ; i<getSize() ; i++){
			System.out.println("Transition Sequence " + i + " - " + arrTransq.get(i).getSize() + ":");
			result += "Transition Sequence " + (i+1) + " - " + arrTransq.get(i).getSize() + ":\n";
			result += arrTransq.get(i).logTransq();
		}
		
		
		return result;
	}
	
	public void printElem(){
		System.out.println("Number of Transition Sequence: " + getSize());
		
		for (int i=0 ; i<getSize() ; i++){
			System.out.println("Transition Sequence " + i + " - " + arrTransq.get(i).getSize() + ":");
			arrTransq.get(i).printTransq();
		}
	}

}
