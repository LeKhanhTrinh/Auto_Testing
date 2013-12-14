package TestPath2Webdriver;

import java.util.ArrayList;

public class Condition {
	//Moi o bieu dien state:id/value-endState
	String event;
	ArrayList<String> elem_id;
	ArrayList<String> elem_val;
	ArrayList<String> end_state;
	
	public Condition(String event, ArrayList<String> elem_id, ArrayList<String> elem_cal, ArrayList<String> end_state) {
		this.event = event;
		this.elem_id = elem_id;
		this.elem_val = elem_cal;
		this.end_state = end_state;
	}
	
	//------------------------------------------------------------------------------------------------
	public void addElemId(String id){
		elem_id.add(id);
	}
	
	public void addElemVal(String val){
		elem_val.add(val);
	}
	
	
	//------------------------------------------------------------------------------------------------
	
	public String getIdAt(int i){
		return elem_id.get(i);
	}
	
	public String getValueAt(int i){
		return elem_val.get(i);
	}
	
	public String getEndStateAt(int i){
		return end_state.get(i);
	}
	
	public ArrayList<String> getElem_id() {
		return elem_id;
	}
	
	public String getEvent() {
		return event;
	}
	
	public ArrayList<String> getElem_val() {
		return elem_val;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	
	//------------------------------------------------------------------------------------------------
	
	public void setElem_id(ArrayList<String> elem_id) {
		this.elem_id = elem_id;
	}
	
	public void setElem_val(ArrayList<String> elem_val) {
		this.elem_val = elem_val;
	}
	
	public void printEL(){
		System.out.println("\t" + event + ":");
		System.out.println("\t" + elem_id + " : " + elem_val + ":" + end_state);		
	}
}
