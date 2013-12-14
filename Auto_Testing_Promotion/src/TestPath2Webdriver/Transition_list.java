package TestPath2Webdriver;

import java.util.ArrayList;

public class Transition_list {
	
	ArrayList<Transition> arrTransition;
	
	public Transition_list(){
		arrTransition = new ArrayList<Transition>();
	}
	
	public int getSize(){
		return arrTransition.size();
	}
	
	public void addTransition(Transition _t){
		arrTransition.add(_t);
	}
	
	public Transition getTransitionByIndex(int index){
		return arrTransition.get(index);
	}
	
	public Transition findBy2S(State s1, State s2){
		
		for (int i=0; i<arrTransition.size(); i++){
			if (s1==arrTransition.get(i).getBeginState() && s2==arrTransition.get(i).getEndState()){
				return arrTransition.get(i);
			}
		}
		
		return null;
	}
	
}
