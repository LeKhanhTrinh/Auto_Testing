package FSM_GRAPH;

import java.util.ArrayList;


public class Search_FSM {
	
	int [][] overTransition;
	String [][] mapTransition;
	int N;
	int [] overState;
	
	ArrayList<Integer> arr;
	
	public Search_FSM(FSM_G fsm){
		arr = new ArrayList<Integer>();
		
		
		N = fsm.getNumberOfState();
		
		System.out.println("N="+N);
		
		overState = new int[N];
		for (int i=0; i<N; i++) overState[i]=0;
		
		
		overTransition = new int[N][];
		for (int i=0; i<N; i++){ 
			overTransition[i]=new int[N];
			for (int j=0; j<N; j++){
				overTransition[i][j]=0;
			}
		}
		
		
		
		mapTransition = new String[N][];
		for (int i=0; i<N; i++){ 
			mapTransition[i]=new String[N];
			for (int j=0; j<N; j++){
				mapTransition[i][j]="";
			}
		}
		
		
		//System.out.println("fsm.getNumberOfTransition()"+fsm.getNumberOfTransition());
		//System.out.println("FSM_G number of transition: " + fsm.getNumberOfTransition());
		for (int i=0; i<fsm.getNumberOfTransition(); i++){
			
			int a = fsm.getIndexBeginStateOfTransition(i);
			int b = fsm.getIndexEndStateOfTransition(i);
			//System.out.println("a="+a+"_b="+b);
			mapTransition[a][b] = fsm.getNameOfTransitioin(i);
			overTransition[a][b] = 2;
			//System.out.println(i + "." + fsm.getNameBeginStateOfTransition(i) + "--" + fsm.getNameOfTransitioin(i) + "-->" + fsm.getNameEndStateOfTransition(i));
		}
		//System.out.println("``````");
		
	}
	
	public path DFS(){
		path PATH;
		PATH = new path();
		arr.clear();
		arr.add(0);
		DFS(0, PATH);
		PATH.sort();
		addPath(PATH);
		PATH.sort();
		PATH.printPath();
		return PATH;
	}
	
	private void DFS(int i, path PATH){
		int t=0;
		//System.out.println("?");
		//if (overState[i]==0) {
		//t++;
		
		for (int j=0; j<N; j++){
			
			if (mapTransition[i][j].length()>0 && overState[j]!=1 && overTransition[i][j]==2){
				t++;
				overTransition[i][j]=1;
				overState[j]=1;
				
				arr.add(j);
		
				DFS(j, PATH);
				arr.remove(arr.size()-1);
			}
		}
		//}
		if (t==0){
			PATH.Add(arr);
			//arr = new ArrayList();
		}
		
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
	
	


}
