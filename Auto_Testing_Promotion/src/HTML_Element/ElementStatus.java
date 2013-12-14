package HTML_Element;

public class ElementStatus {
	int id;
	String status;
	String html_id;
	
	public ElementStatus(String _id, String _status){
		//id=_id;
		html_id = _id;
		status=_status;
	}
	
	public ElementStatus(int _id, String _status){
		id=_id;
		status=_status;
	}
	
	public String getStatus(){
		return status;
	}
	
	public String getStatusByHTMLId(String _id){
		if (html_id.equals(_id)){
			return status;
		}
		return null;
	}
	
	/*
	public String getStatusById(int id){
		if (this.id == id){
			return status;
		}
		return null;
	}
	*/
	
	public int getId(){
		return id;
	}
	
	public String getHTMLId(){
		return html_id;
	}
}
