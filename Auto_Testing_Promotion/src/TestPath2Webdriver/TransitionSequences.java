package TestPath2Webdriver;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public class TransitionSequences {
	ArrayList<Transition> arrTran;
	
	public TransitionSequences(){
		arrTran = new ArrayList<Transition>();
	}
	
	
	public void addTransition(Transition _t){
		arrTran.add(_t);
	}
	
	public Transition getTransitionByIndex(int i){
		return arrTran.get(i);
	}
	
	
	public int getSize(){
		return arrTran.size();
	}
	
	public void removeTran(Transition tr){
		arrTran.remove(tr);
	}
	
	public void printIndex(int i){
		arrTran.get(i).printTrans();
	}
	
	
	public String logTransq(){
		String transqs = "";
		for (int i=0 ; i<getSize(); i++){
			transqs += arrTran.get(i).logTrans();
		}
		
		return transqs;
	}
	
	
	public void printTransq(){
		for (int i=0 ; i<getSize(); i++){
			arrTran.get(i).printTrans();
		}
	}
	
	public boolean runTransq(String url, int nsleep, boolean log, int test_c, WebDriver driver){
		boolean next = false;
		boolean res = true;
		driver.get(url);
		//index = i;
		int index=0;
		for (int j=0 ; j<getSize() ; j++){
			Transition tran = getTransitionByIndex(j);
			if(!tran.changeTrans(driver, test_c)) {
				next=true;
				System.err.println("\n\nFAIL HERE");
				break;
			}
		}
		//int index = i;
		//if (next){
			//index--;
			//continue;
		//}
		System.err.println("So index = " + index);
		if (log){
			if (next){
				//System.err.println("So index = " + index);
				System.err.print("Test path "+(index+1)+": ");
				//textRS += "Test path "+(index+1)+": "+transq.getTransitionByIndex(0).getBeginState().getName();
				System.err.print(getTransitionByIndex(0).getBeginState().getName());
				
				for (int j=0; j<getSize(); j++){
					Transition tran = getTransitionByIndex(j);
					//tran.changeTrans(driver, test_c);
					
					Event e = tran.getEvent();
					//State s1 = tran.getBeginState();
					State s2 = tran.getEndState();
					
					System.err.print("*"+e.name+"="+s2.name);
					//textRS += "*"+e.name+"="+s2.name;
				}
				System.out.println();
				//textRS += "\n";
			}else{
				//System.out.println("So index = " + index);
				System.out.print("Test path "+(index+1)+": ");
				//textRS += "Test path "+(index+1)+": ";
				System.out.print(getTransitionByIndex(0).getBeginState().getName());
				//textRS += transq.getTransitionByIndex(0).getBeginState().getName();
				for (int j=0; j<getSize(); j++){
					Transition tran = getTransitionByIndex(j);
					//tran.changeTrans(driver, test_c);
					
					Event e = tran.getEvent();
					//State s1 = tran.getBeginState();
					State s2 = tran.getEndState();
					
					System.out.print("*"+e.name+"="+s2.name);
					//textRS += "*"+e.name+"="+s2.name;
				}
				System.out.println();
				//textRS += "\n";
			}

			index++;
		}
		
		if (!getTransitionByIndex(0).getBeginState().checkState(driver, test_c)){
			return false;
		}
		
		boolean passone = true;
		for (int j=0; j<getSize(); j++){
			Transition tran = getTransitionByIndex(j);
			if (tran.changeTrans(driver, test_c) == false){
				
				Event e = tran.getEvent();
				State s1 = tran.getBeginState();
				State s2 = tran.getEndState();
				//System.out.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
				System.err.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
				//System.out.println("\tFAIL TEST PATH: " + (j+1) + "\n");
				//textRS += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
				System.err.println("\tFAIL");
				//textRS += "\tFAIL\n";
				//String a="";
				//AnsiConsole.systemInstall();
				//AnsiConsole.out.println("\033[31m\tFAIL TEST PATH: " + (j+1) + "\n");
				
			}else{
				Event e = tran.getEvent();
				State s1 = tran.getBeginState();
				State s2 = tran.getEndState();
				if (next){
					System.err.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
					//extRS += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
				}else{
					System.out.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
					//textRS += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
				}
				
				
				
				//tran.printTrans();
				// thuc thi event
				try{
					e.doEvent(driver, test_c);
				} catch (Exception err){
					passone = false;
					System.out.println("FAIL EVENT");
					break;
				}
				// check state sau do
				if (!s2.checkState(driver, test_c)){
					passone = false;
					System.out.println("FAIL STATE");
					break;
				}
				if (next){
					System.err.println("\tOK");
					//textRS += "\tOK\n";
				}else{
					System.out.println("\tOK");
					//textRS += "\tOK\n";
				}
				//System.out.println("\tOK");
				try{
					Thread.sleep(nsleep);
				} catch (Exception ex){
					ex.printStackTrace();
				}
			}
		}
		
		if (passone == false){
			res = false;
			System.out.println("FAILT HERE:");
			System.out.print("Test path "+(0+1)+": ");
			
			System.out.print(getTransitionByIndex(0).getBeginState().getName());
			//textRS += "FAILT HERE:\n" + "Test path "+(0+1)+": " + transq.getTransitionByIndex(0).getBeginState().getName();
			for (int j=0; j<getSize(); j++){
				Transition tran = getTransitionByIndex(j);
				Event e = tran.getEvent();
				//State s1 = tran.getBeginState();
				State s2 = tran.getEndState();
				
				System.out.print("*"+e.name+"="+s2.name);
				//textRS += "*"+e.name+"="+s2.name;
			}
			
			System.out.println();
			//textRS += "\n";
		}
		return res;
	}
	
	
	
	
	
}
