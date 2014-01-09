package FSM_GRAPH;

import java.util.ArrayList;


public class Search_FSM {
	
	int [][] overTransition;
	//int [][] countTransition;
	String [][] mapTransition;
	ArrayTransList[][] arrTranLists;
	int N;
	int [] overState;
	ArrayList<Integer> arrEndSt;
	ArrayList<Integer> addMore;
	ArrayList<Integer> arr;
	
	public Search_FSM(FSM_G fsm){
		arr = new ArrayList<Integer>();
		arrEndSt = new ArrayList<Integer>();
		addMore = new ArrayList<Integer>();
		N = fsm.getNumberOfState();
		
		System.out.println("N= " + N);
		
		//Khoi tao overState = 0
		overState = new int[N];
		for (int i=0; i<N; i++) overState[i]=0;
		
		//Khoi tao overTransition = 0
		overTransition = new int[N][];
		//countTransition = new int[N][];
		for (int i=0; i<N; i++){ 
			overTransition[i]=new int[N];
			//countTransition[i] = new int[N];
			for (int j=0; j<N; j++){
				overTransition[i][j]=0;
				//countTransition[i][j] = 0;
			}
		}
		
		//arrTransition = null
		arrTranLists = new ArrayTransList[N][];
		for (int i=0; i<N; i++){ 
			arrTranLists[i] = new ArrayTransList[N];
			for (int j=0; j<N; j++){
				arrTranLists[i][j] = new ArrayTransList();
			}
		}
				
		//mapTransition = null
		mapTransition = new String[N][];
		for (int i=0; i<N; i++){ 
			mapTransition[i]=new String[N];
			for (int j=0; j<N; j++){
				mapTransition[i][j]="";
			}
		}
		
		//Lay cac gia tri cua endStateList
		for (int i=0 ; i<fsm.getSizeOfEndStateList() ; i++){
			
		}
		
		//System.out.println("fsm.getNumberOfTransition()"+fsm.getNumberOfTransition());
		//System.out.println("FSM_G number of transition: " + fsm.getNumberOfTransition());
		for (int i=0; i<fsm.getNumberOfTransition(); i++){
			
			int a = fsm.getIndexBeginStateOfTransition(i);
			int b = fsm.getIndexEndStateOfTransition(i);
			//System.out.println("a="+a+"_b="+b);
			mapTransition[a][b] = fsm.getNameOfTransitioin(i);
			overTransition[a][b] = 2;
			//countTransition[a][b]++;
			arrTranLists[a][b].Add(fsm.getNameOfTransitioin(i));
			//System.out.println("Vi tri: [" + a + ", " + b + "] = " + mapTransition[a][b]);
			//System.out.println(i + "." + fsm.getNameBeginStateOfTransition(i) + "--" + fsm.getNameOfTransitioin(i) + "-->" + fsm.getNameEndStateOfTransition(i));
		}
		
		for (int i=0 ; i<fsm.getSizeOfEndStateList() ; i++){
			/*for (int j=0 ; j<N ; j++){
				
				if (!arrTranLists[i][j].IsEmpty())
					System.out.println("Test: [" + i + ", " + j + "] = " + arrTranLists[i][j].printAll());
			}
			*/
			String tempName = fsm.getNameEndStateByIndex(i);
			for (int j=0 ; j<N ; j++){
				if (tempName.equals(fsm.getNameStateByIndex(j))){
					arrEndSt.add(j);
				}
			}
		}
		
		System.out.println("END STATE LIST:");
		for (int i=0 ; i<arrEndSt.size() ; i++){
			System.out.println(arrEndSt.get(i));
		}
		//System.out.println("``````");
		
	}
	
	//Xoa phan sort va add
	public path DFS(){
		path PATH;
		PATH = new path();
		arr.clear();
		arr.add(0);
		DFS(0, PATH);
		addToPath(PATH);
		PATH.sort();
		//addPath(PATH);
		//PATH.sort();
		printCheck();
		PATH.printPath();
		//PATH.printSomeThingsOfPath();
		//addToPathAfter(18);
		printList();
		return PATH;
	}
	
	
	//Xoa phan danh dau dinh
	private void DFS(int i, path PATH){
		int t=0;
		//System.out.println("?");
		//if (overState[i]==0) {
		//t++;
		
		for (int j=0; j<N; j++){
			
			if (arrTranLists[i][j].getSize()>0 && overState[j]!=1 && overTransition[i][j]==2){
				t++;
				
				//overState[j]=1;
				arrTranLists[i][j].RemoveHead();
				if (arrTranLists[i][j].IsEmpty()){
					overTransition[i][j]=1;
				}
				arr.add(j);
		
				DFS(j, PATH);
				arr.remove(arr.size()-1);
				
			}
		}
		//}
		if (t==0){
			System.out.println();
			PATH.Add(arr);
			//arr = new ArrayList();
		}
		
	}
	
	//Kiem tra
	public void printCheck(){
		System.out.println("CHECK DUYET");
		for (int i=0 ; i<N ; i++){
			for (int j=0 ; j<N ; j++){
				if (overTransition[i][j] != 0)
					System.out.println("["+ i + "," + j +"]" + overTransition[i][j]);
			}
		}
	}
	
	//In chuoi bat dau tu mot vi tri i
	public void printList(){
		System.out.println("Chuoi sau khi them");
		
		for (int j=0 ; j<addMore.size() ; j++){
			System.out.print(addMore.get(j) + " ");
		}
		System.out.println();
	}
	
	
	public void addToPath(path PATH){
		for (int i=0 ; i<PATH.getSize() ; i++){
			if (!inEndStateList(PATH.getLastElemFromPathNumber(i), arrEndSt)){
				addToPathAfter(PATH.getLastElemFromPathNumber(i));
				for (int j=0 ; j<addMore.size() ; j++){
					PATH.getListByIndex(i).add(addMore.get(j));
				}
				while (!addMore.isEmpty()){
					addMore.remove(0);
				}
			}
		}
	}
	
	// Them state vao path
	public void addToPathAfter(int i){
		//int i = PATH.getLastElemFromPathNumber(pathNumb);
		

		//System.out.println("check T/F: " + i + " / " + inEndStateList(i, arrEndSt));
		if (inEndStateList(i, arrEndSt) == true){
			return ;
		}
		
		for (int j=0; j<N; j++){
			if (mapTransition[i][j].length() > 0 && overTransition[i][j] == 1){
				overTransition[i][j] = 3;
				addMore.add(j);
				System.out.println("IAM HERE: " + j);
				addToPathAfter(j);
				break;
			}
		}
		
		
	}
	
	public boolean inEndStateList(int gt, ArrayList<Integer> listInt){
		for (int i=0 ; i<listInt.size() ; i++){
			if (gt == listInt.get(i)){
				return true;
			}
		}
		return false;
	}
	
	public void addPath(path PATH){
		
		for (int i=0; i<N; i++){
			for (int j=0; j<N; j++){
				if (mapTransition[i][j].length()>0 && overTransition[i][j]==2){
					//System.out.println(""+i+"_"+j);
					ArrayList<Integer> t = PATH.getIfEndBy(i);
					t.add(j);
					PATH.Add(t);
				}
				
			}
				
		}
	}
	
	public static ArrayList<String> getTransition(String input){
		ArrayList<String> result = new ArrayList<String>();
		while (input != null){
			int indexOfCom = input.indexOf(",");
			String buff1 = "";
			String buff2 = "";
			
			if (indexOfCom >= 0){
				buff1 = input.substring(0, indexOfCom);
				buff2 = input.substring(indexOfCom + 1, input.length());
				result.add(buff1);
				input = buff2;
			}else{			
				buff1 = input;
				result.add(buff1);
				input = null;
			}
		}
		return result;
	}
}

class ArrayTransList{
	ArrayList<String> arrList;
	
	public ArrayTransList() {
		// TODO Auto-generated constructor stub
		arrList = new ArrayList<String>();
	}
	
	public ArrayTransList(ArrayList<String> arrList){
		this.arrList = arrList;
	}
	
	public void Add(String element){
		arrList.add(element);
	}
	
	public void RemoveHead(){
		arrList.remove(0);
	}
	
	public boolean IsEmpty(){
		return arrList.isEmpty();
	}
	
	public String printAll(){
		String rs = "{";
		if (arrList.size() > 0){
			for (int i=0 ; i<arrList.size()-1 ; i++){
				rs += arrList.get(i) + ", ";
			}
			rs += arrList.get(arrList.size()-1);
		}
		rs += "}";
		return rs;
	}
	
	public int getSize(){
		return arrList.size();
	}
	
	public String getByIndex(int i){
		return arrList.get(i);
	}
}