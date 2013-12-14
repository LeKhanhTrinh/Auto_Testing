package FSM_GRAPH;

import java.util.ArrayList;

public class path {
	ArrayList<ArrayList<Integer>> arrPath;
	
	public int getSize(){
		return arrPath.size();
	}
	
	public path(){
		arrPath = new ArrayList<ArrayList<Integer>>();
	}
	
	
	public void Add(ArrayList<Integer> arr){
		ArrayList<Integer> t = new ArrayList<Integer>();
		for (int i=0; i<arr.size(); i++){
			t.add(arr.get(i));
		}
		arrPath.add(t);
		
	}
	
	public ArrayList<Integer> getListByIndex(int index){
		return arrPath.get(index);
	}
	
	public ArrayList<Integer> getIfEndBy(int e){
		int ai=0;
		int al=0;
		for (int i=0; i<arrPath.size(); i++){
			ArrayList<Integer> t = arrPath.get(i);
			for (int k=0; k<t.size(); k++){
				if (e==t.get(k).intValue()){
					ai = i;
					al = k;
					break;
				}
			}
		}
		
		
		
		ArrayList<Integer> t = arrPath.get(ai);
			
		ArrayList<Integer> t1 = new ArrayList<Integer>();
		for (int k=0; k<=al; k++){
			t1.add(t.get(k));
		}
		return t1;

	}
	
	
	public void sort(){
		for (int i=0; i< arrPath.size()-1; i++){
			for (int j=i+1; j<arrPath.size(); j++){
				if (arrPath.get(i).size()>arrPath.get(j).size()){
					ArrayList<Integer> t = arrPath.get(i);
					
					arrPath.set(i, arrPath.get(j));
					arrPath.set(j, t);
					
				}
			}
		}
		
		for (int i=0; i< arrPath.size()-1; i++){
			for (int j=i+1; j<arrPath.size(); j++){
				if (laCon(arrPath.get(i), arrPath.get(j))){
					arrPath.remove(i);
					i=0;
					j=1;
				}
			}
		}
	}
	
	public boolean laCon(ArrayList<Integer> arr1, ArrayList<Integer> arr2){
		if (arr1.size()>arr2.size()) return false;
		for (int i=0; i<arr1.size(); i++){
			if (arr1.get(i).intValue()!=arr2.get(i).intValue()){
				return false;
			}
		}
		
		return true;
	}
	
	public void printPath(){
		for (int i=0; i<arrPath.size(); i++){
			System.out.print(""+(i+1)+". ");
			ArrayList<Integer> t = arrPath.get(i);
			for (int k=0; k<t.size(); k++){
				System.out.print(t.get(k) + " ");
				
			}
			System.out.println();
			
		}
	}
	
	
	
}
