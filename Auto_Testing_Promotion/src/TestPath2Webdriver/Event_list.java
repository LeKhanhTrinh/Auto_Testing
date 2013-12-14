package TestPath2Webdriver;

import java.util.ArrayList;

public class Event_list {
	ArrayList<Event> arrEvent;
	
	public Event_list(){
		arrEvent=new ArrayList<Event>();
	}
	
	public void addEvent(Event e){
		arrEvent.add(e);
	}
	
	public Event getEventByIndex(int index){
		return arrEvent.get(index);
	}
	
	public Event getEventByName(String _name){
		for (int i=0; i<arrEvent.size(); i++){
			if (arrEvent.get(i).getName().compareTo(_name)==0){
				return arrEvent.get(i);
			}
		}
		return null;
	}
	
	
	public int getSize(){
		return arrEvent.size();
	}
	

}
