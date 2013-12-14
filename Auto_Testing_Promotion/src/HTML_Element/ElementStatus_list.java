package HTML_Element;

import java.util.ArrayList;

public class ElementStatus_list {
	ArrayList<ElementStatus> arrElementStatus;
	
	public ElementStatus_list() {
		arrElementStatus = new ArrayList<ElementStatus>();
	}
	
	public void addElement(ElementStatus _e){
		arrElementStatus.add(_e);
	}
	
	public ElementStatus getElementById(int id){
		for (int i=0; i<arrElementStatus.size(); i++){
			if (arrElementStatus.get(i).getId()==id){
				return arrElementStatus.get(i);
			}
		}
		return null;
	}
	
	public ElementStatus getElementByName(String _status){
		for (int i=0; i<arrElementStatus.size(); i++){
			if (arrElementStatus.get(i).getStatus().equals(_status)){
				return arrElementStatus.get(i);
			}
		}
		return null;
	}
	
	public ElementStatus getElementByIndex(int index){
		return arrElementStatus.get(index);
	}
	
	public int getSize(){
		return arrElementStatus.size();
	}
}
