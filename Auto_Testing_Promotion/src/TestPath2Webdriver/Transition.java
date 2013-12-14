package TestPath2Webdriver;

import org.openqa.selenium.WebDriver;

import HTML_Element.Elem_html_list;
import HTML_Element.ElementStatus;
import HTML_Element.ElementStatus_list;
import HTML_Element.Element_html;
import arg.HTML_StatusValue;


//import CellString.SubCompo_list;

public class Transition {
	Event event;
	State beginState;
	State endState;
	Condition_list conditionList;
	
	public Transition(Event _e, State s1, State s2){
		event=_e;
		beginState=s1;
		endState=s2;
	}
	
	public Transition(Event _e, State s1, State s2, String input){
		event=_e;
		beginState=s1;
		endState=s2;
		conditionList = new Condition_list(input);
	}
	
	
	
	public boolean changeTrans(WebDriver driver, int test_case){
		try{
			boolean test = true;
			Elem_html_list  elem_html_list = beginState.elem_html_list;
			ElementStatus_list elem_st_list = beginState.elem_st_list;
			
			
			for (int i=0; i<elem_st_list.getSize(); i++){
				ElementStatus e = elem_st_list.getElementByIndex(i);
				if (e.getStatus().compareTo(HTML_StatusValue.IGNORE)==0){
					continue;
				}
				
				Element_html eh = elem_html_list.getElementById(e.getId());
				if (eh.getValueAt(test_case).compareTo(HTML_StatusValue.IGNORE)==0){
					continue;
				}
				//if (!checkId(eh)){
				//	break;
				//}
				//System.out.println("LIST: "  + conditionList.getSize());
				for (int j=0 ; j<conditionList.getSize() ; j++){
					
					Condition cd = conditionList.getConditionByIndex(j);
					//System.out.println("\n\tCONDITION: " + beginState.getName() + "\t" + cd.event);
					if (cd.event !=null){
						//System.out.println("RANGE: " + cd.elem_id.size());
						//test = true;
						for (int k=0 ; k<cd.elem_id.size() ; k++){
							test=true;
							String value_test_case = eh.getValueAt(cd.getIdAt(k),test_case);
							if (value_test_case != null){
								//System.out.println("\n\tVALUE: " + cd.getValueAt(k) + "\t" + value_test_case + "\t" + cd.getEndStateAt(k) + "\t" + endState.getName());
								//System.out.println("\n\tVALUE: " + cd.getValueAt(k) + "\t" + value_test_case + "\t" + cd.getEndStateAt(k) + "\t" + endState.getName());
								if (cd.event.equals(event.getName()) && cd.getIdAt(k).equals(eh.getHtml_id())){
									if(!cd.getValueAt(k).equals(value_test_case) || !cd.getEndStateAt(k).equals(endState.getName())){
										//System.out.println("\n\tVALUE1: " + cd.getValueAt(k) + "\t" + value_test_case);
										//System.out.println("FAIL HERE");
										test = false;
									}else{
										test = true;
										break;
									}
									
									//break;
								}
							}
						}
					}
					//System.out.println(test);
					//break;
				}
				//System.out.println(test);
				break;
				
			}		
			//System.out.println(test);
			return test;
		}catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
	
	public boolean checkId(Element_html eh){
		try{
			for (int i=0 ; i<conditionList.getSize() ; i++){
				Condition cd = conditionList.getConditionByIndex(i);
				for (int j=0 ; j<cd.elem_id.size() ; j++){
					if (!cd.getIdAt(j).equals(eh.getHtml_id())){
						return false;
					}
				}
			}
			return true;
		}catch (Exception e) {
			System.out.println("error");
			return false;
		}
	}
	
	public String getName(){
		return event.getName();
	}
	
	public State getBeginState(){
		return beginState;
	}
	public State getEndState(){
		return endState;
	}
	public Event getEvent(){
		return event;
	}
	
	public void setNameEndState(String name){
		endState.setName(name);
	}
	
	public void setNameEvent(String name){
		event.setName(name);
	}
	
	
	public void removeTrans(){
		beginState = null;
		event = null;
		endState = null;
		conditionList = null;
	}
	
	public String logTrans(){
		String trans = "\t"+beginState.getName() + "---->" + endState.getName() + "\n";
		System.out.println("\t"+beginState.getName() + "---->" + endState.getName());
		return trans;
	}
	
	public void printTrans(){
		//System.out.println("\t"+beginState.getName() + "--" + event.getName() + "-->" + endState.getName() + "\n\tCondition:");
		System.out.println("\t"+beginState.getName() + "---->" + endState.getName());
		//System.out.println("\t"+beginState.getName() + "---->" + endState.getName());
		//for (int i=0 ; i<conditionList.getSize() ; i++){
		//	conditionList.arr_compo.get(i).printEL();
		//}
	}
	
	
	
}
