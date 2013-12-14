package TestPath2Webdriver;

import java.util.ArrayList;



public class Condition_list {
	public ArrayList<Condition> arr_compo;
	public ArrayList<String> subStateValue = new ArrayList<String>();	//(state:{id/value-main})
	public ArrayList<String> subStateCompValue = new ArrayList<String>();	//(id/value)
	
	public Condition_list(String input){
		arr_compo = new ArrayList<Condition>();
		process(input);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	/*
	 * 
	 */
	public void process(String input){
		findSubCom(input, ";");
		//printAL(tempSubComp);
		//System.out.println(subStateValue.size());
		for (int i=0 ; i<subStateValue.size() ; i++){
			//System.out.println(subStateValue.get(i));
			findCompo(subStateValue.get(i));
			//System.out.println(subStateValue.get(i));
		}
	}
	
	//tach hoan chinh thanh cac SubCompo
	public void findCompo(String input){
		String state="";
		String compo="";
		ArrayList<String> tempId = new ArrayList<String>();
		ArrayList<String> tempValue = new ArrayList<String>();
		ArrayList<String> endSt = new ArrayList<String>();
		
		if (!input.equals("_")){
			int dau = input.indexOf(":");
			state = input.substring(0,dau);
			compo = input.substring(dau+1,input.length());
			
			for (int i = subStateCompValue.size()-1; i >= 0; i--)
			{
				subStateCompValue.remove(i);
			}
			
			findSubCom(compo, ",");
			
			for (int i=0 ; i<subStateCompValue.size() ; i++){
				int vitri = subStateCompValue.get(i).indexOf("/");
				int vitri1 = subStateCompValue.get(i).indexOf("-");
				tempId.add(subStateCompValue.get(i).substring(0, vitri));
				tempValue.add(subStateCompValue.get(i).substring(vitri+1,vitri1));
				endSt.add(subStateCompValue.get(i).substring(vitri1+1,subStateCompValue.get(i).length()));
			}
			
			//addSubCompo(new SubCompo(state, tempId, tempValue,endSt));
			arr_compo.add(new Condition(state, tempId, tempValue,endSt));
		}else{
			arr_compo.add(new Condition(null, null, null, null));
		}
	}
		
	//Tach string ; va ,
	public void findSubCom(String input, String dau){
		//tempSubComp = new ArrayList<String>();
		String input1=input;
		if (!input1.equals("_")){
			while(input1 != null){
				String buff1 = "";
				String buff2 = "";
				int champhay = input1.indexOf(dau);
				if(champhay >= 0){
					buff1 = input1.substring(0, champhay);
					//System.out.println(buff1);
					buff2 = input1.substring(champhay+1, input1.length());
					//System.out.println(buff2);
					if (dau.equals(";")){
						subStateValue.add(buff1);
					}else if(dau.equals(",")){
						subStateCompValue.add(buff1);
					}
					input1 = buff2;
				}else{
					buff1=input1;
					//System.out.println(buff1);
					if (dau.equals(";")){
						subStateValue.add(buff1);
					}else if(dau.equals(",")){
						subStateCompValue.add(buff1);
					}
					input1=null;
				}
			}
		}else{
			subStateCompValue.add("_");
			subStateValue.add("_");
		}
	}
	
	//-----------------------------------------------------------------------------------------------------
	/*
	 * 
	 */
	public void removeAllCond(){
		for (int i=0 ; i<arr_compo.size() ; i++){
			arr_compo.remove(i);
		}
	}
	
	public int getSize(){
		return arr_compo.size();
	}
	
	public void addCondition(Condition _c){
		arr_compo.add(_c);
	}
	
	public Condition getConditionByIndex(int i){
		return arr_compo.get(i);
	}
	
	public String getNameEventByIndex(int index){
		return arr_compo.get(index).getEvent();
	}
	
	public void setNameEventByIndex(int i, String name){
		arr_compo.get(i).setEvent(name);
	}
}
