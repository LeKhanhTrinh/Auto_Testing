package TestPath2Webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import arg.Webdriver_actionType;

import HTML_Element.Elem_html_list;
import HTML_Element.Element_html;

public class Event {
	String name;
	int element_id;
	String element_html_id;
	String action;
	Elem_html_list elem_html_list;
	
	public Event(String _name, String _e, String _a, Elem_html_list _elem_html_list){
		name=_name;
		element_html_id =_e;
		action=_a;
		elem_html_list=_elem_html_list;
	}
	
	public Event(String _name, int _e, String _a, Elem_html_list _elem_html_list){
		name=_name;
		element_id=_e;
		action=_a;
		elem_html_list=_elem_html_list;
	}
	
	public String getName(){
		return name;
	}
	
	public int getElemId(){
		return element_id;
	}
	
	public String getAction(){
		return action;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	
	public void doEvent(WebDriver driver, int test_current) {
		//System.out.println("doing event called");
		try{
			Element_html elem = elem_html_list.getElementById(element_id);
			//Element_html elem = elem_html_list.getElementByName(element_html_id);
			WebElement webelem = driver.findElement(By.id(elem.getHtml_id()));
			
			if (this.action.compareTo(Webdriver_actionType.ADDTEXT)==0){
				webelem.sendKeys(elem.getValueAt(test_current));
			} else
			if (this.action.compareTo(Webdriver_actionType.DELTEXT)==0){
				webelem.clear();
			} else
			if (this.action.compareTo(Webdriver_actionType.CLICK)==0){
				webelem.click();
			}
			if (this.action.compareTo(Webdriver_actionType.SELECT)==0){
				webelem.click();
			}
			
		} catch (Exception e){
			e.printStackTrace();
			
		}

	}
	
	
	
	
	
	
}
