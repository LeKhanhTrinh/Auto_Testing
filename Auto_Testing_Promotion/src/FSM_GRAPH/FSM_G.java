package FSM_GRAPH;

import java.util.ArrayList;

import TestPath2Webdriver.State;
import TestPath2Webdriver.State_list;
import TestPath2Webdriver.Transition;
import TestPath2Webdriver.TransitionSequences;
import TestPath2Webdriver.TransitionSequences_list;
import TestPath2Webdriver.Transition_list;

public class FSM_G {
	
	private String name;
	private int numOfTest;
	public State_list stateList;
	private Transition_list transitionList;
	private State beginState;
	private State_list endStateList;
	
	public FSM_G(String _name, State_list _stateList, Transition_list _transitionList){
		name = _name;
		stateList=_stateList;
		transitionList=_transitionList;
	}
	
	public FSM_G(int _num, String _name, State_list _stateList, Transition_list _transitionList, State _beginState, State_list _endStateList){
		name = _name;
		numOfTest = _num;
		stateList=_stateList;
		transitionList=_transitionList;
		beginState = _beginState;
		endStateList = _endStateList;
	}
	
	
	//------------------------------------------------------------------------------------------
	//Ham GET
	//------------------------------------------------------------------------------------------
	
	public int getNumOfTest(){
		return numOfTest;
	}
	
	//State Number
	public int getNumberOfState(){
		return stateList.getSize();
	}
	
	// tim tat ca even mot state
	public ArrayList<String> getAllPathOfState(String name){
		ArrayList<String> ret = new ArrayList<String>();
		
		for (int i=0; i<transitionList.getSize(); i++){
			if (transitionList.getTransitionByIndex(i).getBeginState().getName().compareTo(name)==0){
				ret.add(transitionList.getTransitionByIndex(i).getName());
			}
		}
		
		return ret;
	}
	
	//Lay tat ca cac transition den 1 state
	public Transition_list getTransByEndState(String name){
		Transition_list transList = new Transition_list();
		
		for (int i=0; i<transitionList.getSize(); i++){
			if (transitionList.getTransitionByIndex(i).getEndState().getName().equals(name)){
				transList.addTransition(transitionList.getTransitionByIndex(i));
			}
		}
		
		return transList;
	}
	
	//Get State Index
	public int getIndexOfState(String name) {
		for (int i=0; i<stateList.getSize(); i++){
			if (name.compareTo(stateList.getStateByIndex(i).getName())==0){
				return i;
			}
		}
		return -1;
	}
	
	//Get name by index
	public String getNameStateByIndex(int index){
		return stateList.getStateByIndex(index).getName();
	}
	
	//Get name by index
	public String getNameEndStateByIndex(int index){
		return endStateList.getStateByIndex(index).getName();
	}
		
	//Get index Begin State
	public int getIndexBeginStateOfTransition(int i){
		return getIndexOfState(transitionList.getTransitionByIndex(i).getBeginState().getName());
	}
	
	//Get index End State
	public int getIndexEndStateOfTransition(int i){
		return getIndexOfState(transitionList.getTransitionByIndex(i).getEndState().getName());
	}
	
	//Get name Begin State
	public String getNameBeginStateOfTransition(int i){
		//return getIndexOfState(transitionList.getTransitionByIndex(i).getBeginState().getName());
		return transitionList.getTransitionByIndex(i).getBeginState().getName();
	}
	
	//Get name End State
	public String getNameEndStateOfTransition(int i){
		return transitionList.getTransitionByIndex(i).getEndState().getName();
	}
		
	//Transition Name
	public String getNameOfTransitioin(int i){
		return transitionList.getTransitionByIndex(i).getName();
	}
	
	//Transition number
	public int getNumberOfTransition(){
		return transitionList.getSize();
	}
	
	
	public String getName(){
		return name;
	}
	
	public Transition_list getTransList(){
		return transitionList;
	}
	
	public State_list getStateList(){
		return stateList;
	}
	
	public State getBeginState(){
		return beginState;
	}
	
	public State_list getEndListState(){
		return endStateList;
	}
	
	public int getSizeOfEndStateList(){
		return endStateList.getSize();
	}
	//------------------------------------------------------------------------------------------
	//Ham SET
	//------------------------------------------------------------------------------------------
	public void addEndState(State endst){
		endStateList.addState(endst);
	}
	
	public void addBeginState(State begin){
		beginState = begin;
	}
	
	///////////////////////////////////////
	public TransitionSequences_list conVertFromPath(path PATH){
		TransitionSequences_list transqlist = new TransitionSequences_list();
		
		for (int i=0; i<PATH.getSize(); i++){
			TransitionSequences transq = new TransitionSequences();
			
			ArrayList<Integer> arr1 = PATH.getListByIndex(i);

			//System.out.println("Size = " + arr1.size());
			for (int j=0; j<arr1.size()-1; j++){
				//System.out.println(stateList.getStateByIndex(j).getId() + " / " + stateList.getStateByIndex(j+1).getId());
				Transition tran1 = transitionList.findBy2S(
						stateList.getStateByIndex(arr1.get(j)), 
						stateList.getStateByIndex(arr1.get(j+1)));
				//System.out.println("Transition: " + tran1.getName() + " / " + stateList.getStateByIndex(j).getName() + " / " + stateList.getStateByIndex(j+1).getName());
				//System.out.println(tran1.getName());
				transq.addTransition(tran1);
				
			}
			
			transqlist.addTransitionsq(transq);
		}
		
		return transqlist;
	}
	
	
	public TransitionSequences_list getPath_DFS(){
		//System.out.println("chua bao search");
		Search_FSM searcher = new Search_FSM(this);
		//System.out.println("Da khai bao search");
		path PATH = searcher.DFS();
		//System.out.println("Path khoi tao: ");
		return conVertFromPath(PATH);
	}
	
	public void printTransition(){
		System.out.println("Transition: ");
		for (int i=0 ; i<transitionList.getSize() ; i++){
			System.out.println(i + ":");
			transitionList.getTransitionByIndex(i).printTrans();
		}
	}
	
	public void printAll(){
		System.out.println("G - State number:" + stateList.getSize());
		System.out.println("G - Transition number:" + transitionList.getSize());
	}
	
	
	public void printBeginState(){
		System.out.println("BEGIN STATE:\t" + beginState.getName() + "\n");
	}
	
	public void printEndState(){
		System.out.print("End state:\t");
		for (int i=0 ; i<endStateList.getSize() ; i++){
			System.out.print(endStateList.getStateByIndex(i).getName() + "\t");
		}
		System.out.println();
	}
	
	public void printState(){
		stateList.printStateList();
	}
	
	//public void prin
}
