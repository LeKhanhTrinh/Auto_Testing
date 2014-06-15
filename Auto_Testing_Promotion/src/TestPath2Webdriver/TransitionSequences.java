package TestPath2Webdriver;

import java.util.ArrayList;

public class TransitionSequences {
	ArrayList<Transition> arrTran;
	
	public TransitionSequences(){
		arrTran = new ArrayList<Transition>();
	}
	
	
	public void addTransition(Transition _t){
		arrTran.add(_t);
	}
	
	public Transition getTransitionByIndex(int i){
		return arrTran.get(i);
	}
	
	
	public int getSize(){
		return arrTran.size();
	}
	
	public void removeTran(Transition tr){
		arrTran.remove(tr);
	}
	
	
	
	
	public String logTransq(){
		String transqs = "";
		for (int i=0 ; i<getSize(); i++){
			transqs += arrTran.get(i).logTrans();
		}
		
		return transqs;
	}
	
	
	public void printTransq(){
		for (int i=0 ; i<getSize(); i++){
			arrTran.get(i).printTrans();
		}
	}

}
