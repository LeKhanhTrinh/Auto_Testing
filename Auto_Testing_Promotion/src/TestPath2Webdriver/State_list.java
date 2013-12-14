package TestPath2Webdriver;

import java.util.ArrayList;

public class State_list {
	ArrayList<State> arrState;
	
	public State_list(){
		arrState = new ArrayList<State>();
	}
	
	public void addState(State _state){
		arrState.add(_state);
	}
	
	public State getStateByIndex(int index){
		return arrState.get(index);
	}
	
	public boolean checkExist(String _name){
		for (int i=0 ; i<arrState.size() ; i++){
			if (arrState.get(i).getName().equals(_name)){
				return true;
			}
		}
		return false;
	}
	
	public State getStateByName(String _name){
		for (int i=0; i<arrState.size(); i++){
			if (arrState.get(i).getName().compareTo(_name)==0){
				return arrState.get(i);
			}
		}
		return null;
	}
	
	
	public int getSize(){
		return arrState.size();
	}
	
	public void removeStateByName(String name){
		State temp = getStateByName(name);
		if (temp != null){
			arrState.remove(temp);
		}
	}
	
	public void printStateDetail(){
		for (int i=0 ; i<arrState.size() ; i++){
			arrState.get(i).printState();
		}
	}
	
	public void printStateList(){
		for (int i=0 ; i<arrState.size() ; i++){
			arrState.get(i).setId(i);
			System.out.println(arrState.get(i).id + " / " + arrState.get(i).name);
		}
	}
}
