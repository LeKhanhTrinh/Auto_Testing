package TestPath2Webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import arg.HTML_StatusValue;
import arg.HTML_Type;
import arg.Webdriver_getType;

import HTML_Element.Elem_html_list;
import HTML_Element.ElementStatus;
import HTML_Element.ElementStatus_list;
import HTML_Element.Element_html;


public class State {
	int id;
	String name;
	ElementStatus_list elem_st_list; // for this state
	Elem_html_list elem_html_list;
	
	public State(){
		
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public State(int _id, String _name, ElementStatus_list _elem_st_list, Elem_html_list _elem_html_list){
		id = _id;
		name=_name;
		elem_st_list=_elem_st_list;
		elem_html_list=_elem_html_list;
	}
	
	public State(String _name, ElementStatus_list _elem_st_list, Elem_html_list _elem_html_list){
		name=_name;
		elem_st_list=_elem_st_list;
		elem_html_list=_elem_html_list;
	}
	
	public String getName(){
		return name;
	}
	
	public void printState(){
		System.out.println("PRINT STATE: " + name);
		for (int i=0 ; i<elem_html_list.getSize() ; i++){
			System.out.println(elem_html_list.getElementByIndex(i).getHtml_id() + " / " + elem_st_list.getElementByIndex(i).getStatus());
		}
	}
	
	public void addMoreInfo(String _name, ElementStatus_list _elem_st_list, Elem_html_list _elem_html_list){
		if (name.equals(_name)){
			for (int i=0 ; i<_elem_html_list.getSize() ; i++){
				elem_html_list.addElement(_elem_html_list.getElementByIndex(i));
				elem_st_list.addElement(_elem_st_list.getElementByIndex(i));
			}
			
			//for (int i=0 ; i<_elem_st_list.getSize() ; i++){
			//	elem_st_list.addElement(_elem_st_list.getElementByIndex(i));
			//}
		}
	}
	
	public String getStringFromHtmlById(WebElement webelem, Element_html eh, int test_current){
		
		try{
			String value="";
			if (eh.getType().compareTo(HTML_Type.TEXTBOX)==0){
				//System.out.println("is textbox");
				value=webelem.getAttribute(Webdriver_getType.ATT_VALUE);
				//System.out.println("\tWEB VaLue: " + value + " - " + eh.getHtml_id());
				//value=webelem.getAttribute("");
			} else if (eh.getType().compareTo(HTML_Type.CHECKBOX)==0){
				//System.out.println("is checkbox");
				if (webelem.isSelected()){
					value = "1";
				} else {
					value = "0";
				}
				//System.out.println(value);
			}else if (eh.getType().compareTo(HTML_Type.RADIO) == 0){
				if (webelem.isSelected()){
					value = "1";
				} else {
					value = "0";
				}
			}else if (eh.getType().compareTo(HTML_Type.LISTBOX) == 0){
				Select clickThis = new Select(webelem);			
				clickThis.selectByValue(eh.getValueAt(test_current));
				value = eh.getValueAt(test_current);
			}
			else {
				//System.out.println("not other");
				value=webelem.getText();
			}
			//System.out.println("\tWEB VaLue: " + value + " - " + eh.getHtml_id());
			return value;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	// test_current la bien chi xem test lan thu may, tuong ung voi bo gia tri nao
	public boolean checkState(WebDriver driver, int test_current){
		try{
			
			for (int i=0; i<elem_st_list.getSize(); i++){
				ElementStatus e = elem_st_list.getElementByIndex(i);
				if (e.getStatus().compareTo(HTML_StatusValue.IGNORE)==0){
					continue;
				}
				Element_html eh = elem_html_list.getElementById(e.getId());
				//Element_html eh = elem_html_list.getElementByName(e.getHTMLId());
				if (eh.getValueAt(test_current).compareTo(HTML_StatusValue.IGNORE)==0){
					continue;
				}
				WebElement webelem = driver.findElement(By.id(eh.getHtml_id()));
				
				//System.out.println("\tCheck State");
				String value = getStringFromHtmlById(webelem, eh, test_current);
				//String id = getIdFromTxtB(webelem, eh);
				if (e.getStatus().compareTo(HTML_StatusValue.EMPTY)==0){
					if (value.length()!=0){
						System.out.println("f1");
						return false;
					}
				} else if (e.getStatus().compareTo(HTML_StatusValue.DEFAULT)==0){ 
					if (value.compareTo(eh.getValueAt(test_current))!=0){
						
						System.out.println("f2");
						System.out.println(value+":\t"+eh.getValueAt(test_current));
						if (eh.getValueAt(test_current).compareTo(HTML_StatusValue.IGNORE)==0){
							continue;
						}
						return false;
					}
				} else if (value.compareTo(e.getStatus())!=0){
					System.out.println("Strings not compare("+value+"):"+eh.getHtml_id()+"_"+e.getStatus());
					System.out.println("f3");
					return false;
				}
			
			}
			
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	public int getCheckState(WebDriver driver, int test_current){
		try{
			
			for (int i=0; i<elem_st_list.getSize(); i++){
				ElementStatus e = elem_st_list.getElementByIndex(i);
				if (e.getStatus().compareTo(HTML_StatusValue.IGNORE)==0){
					continue;
				}
				Element_html eh = elem_html_list.getElementById(e.getId());
				//Element_html eh = elem_html_list.getElementByName(e.getHTMLId());
				if (eh.getValueAt(test_current).compareTo(HTML_StatusValue.IGNORE)==0){
					continue;
				}
				WebElement webelem = driver.findElement(By.id(eh.getHtml_id()));
				
				//System.out.println("\tCheck State");
				String value = getStringFromHtmlById(webelem, eh, test_current);
				//String id = getIdFromTxtB(webelem, eh);
				if (e.getStatus().compareTo(HTML_StatusValue.EMPTY)==0){
					if (value.length()!=0){
						System.out.println("f1");
						return 1;
					}
				} else if (e.getStatus().compareTo(HTML_StatusValue.DEFAULT)==0){ 
					if (value.compareTo(eh.getValueAt(test_current))!=0){
						
						System.out.println("f2");
						System.out.println(value+":\t"+eh.getValueAt(test_current));
						if (eh.getValueAt(test_current).compareTo(HTML_StatusValue.IGNORE)==0){
							continue;
						}
						return 2;
					}
				} else if (value.compareTo(e.getStatus())!=0){
					System.out.println("Strings not compare("+value+"):"+eh.getHtml_id()+"_"+e.getStatus());
					System.out.println("f3");
					return 3;
				}
			
			}
			
			return 0;
		} catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}
	
	
	
	public void setName(String name){
		this.name = name;
	}
	
	
	
	
}
