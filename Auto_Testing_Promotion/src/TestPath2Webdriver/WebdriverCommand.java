package TestPath2Webdriver;

import java.io.File;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.*;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.firefox.FirefoxDriver;


import FSM_GRAPH.FSM_G;
import HTML_Element.Elem_html_list;
import HTML_Element.ElementStatus;
import HTML_Element.ElementStatus_list;
import HTML_Element.Element_html;

public class WebdriverCommand {

	FSM_G fsm; // do thi bieu dien state machine
	WebDriver driver; // webdriver object
	Elem_html_list elemHtmlList = new Elem_html_list(); // danh sach cac html element
	State_list stateList = new State_list(); // danh sach cac sate
	Event_list eventList = new Event_list(); // danh sach cac event
	Transition_list transitionList = new Transition_list(); // danh sach cac transition
	State beginState = new State();	//trang thai bat dau cua mot SM
	State_list endStateList = new State_list();	//danh sach cac trang thai ket thuc cua mot SM
	ArrayList<String> testPath = new ArrayList<String>();
	ArrayList<String> theResult = new ArrayList<String>();
	ArrayList<String> detail = new ArrayList<String>();
	
	String testPathS = "";
	String theResultS = "";
	String detailS = "";
	
	int numOfTest;
	
	String name;
	public String textRS = "";
	public String textFail = "";
	
	public WebdriverCommand(String _name, File _file) throws Exception{
		
		name = _name;
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
		
		//FSM_G fsm1 = inputData(_file);
		//fsm = fsm1;
		//fsm.printState();
		inputData(_file);

	}
	
	public WebdriverCommand(String _name, File _file, File _file1) throws Exception{
		
		name = _name;
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
		
		//Nhap du lieu cua fsm1 va fsm2, tao fsm_g la compo cua ca 2 fsm
		
		//fsm = fsm1;
		
		inputData(_file);
		inputData(_file1);
		
	}
	
	public WebdriverCommand(String _name, String _folder) throws Exception{
		
		name = _name;
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
		
		
		File directory = new File(_folder);
		File[] contents = directory.listFiles();
		for ( File f : contents) {
		  inputData(f);
		  System.out.println(f.getName());
		}
	    fsm.printState();
		
	}
	
	
	//Noi tai day
	public FSM_G composFSMs(FSM_G fsm1, FSM_G fsm2){
		FSM_G fsm_g = fsm1;
		//Xoa endState o fsm1
		for (int i=0 ; i<fsm_g.getEndListState().getSize() ; i++){
			if (fsm_g.getEndListState().getStateByIndex(i).getName().equals(fsm2.getBeginState().getName())){
				fsm_g.getEndListState().removeStateByName(fsm2.getBeginState().getName());
				//fsm_g.getStateList().removeStateByName(fsm2.getBeginState().getName());
			}
		}
		
		
		//Them cac State va cac Transition tu fsm2 vao fsm 1
		for (int i=0 ; i<fsm2.getStateList().getSize() ; i++){
			if (!fsm2.getStateList().getStateByIndex(i).getName().equals(fsm2.getBeginState().getName())){
				fsm_g.getStateList().addState(fsm2.getStateList().getStateByIndex(i));
			}
			
		}
		
		for (int i=0 ; i<fsm2.getEndListState().getSize() ; i++){
			fsm_g.getEndListState().addState(fsm2.getEndListState().getStateByIndex(i));
		}
		
		
		for (int i=0 ; i<fsm2.getTransList().getSize() ; i++){
			fsm_g.getTransList().addTransition(fsm2.getTransList().getTransitionByIndex(i));
		}
		return fsm_g;
	}
	
	public boolean runTestCaseWithUrl(String url, int nSleep, boolean log){
		System.out.println("Number of Test: " + fsm.getNumOfTest());
		for (int i=0; i<fsm.getNumOfTest(); i++){
			System.out.println("\n\nStart run test case with values: "+(i+1));
			textRS += "\n\nStart run test case with values: "+(i+1) + "\n";
			try{
				if (!my_run_withURL(url, nSleep, log, i)){
					return false;
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			System.out.print("PASS testing with values: "+(i+1)+"\n");
			textRS += "PASS testing with values: "+(i+1)+"\n";
			textRS += "\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n";
		}
		//System.out.println("co o day");
		return true;
	}
	
	//Check xem co xoa hay ko
	public boolean checkRemove(TransitionSequences transq, String url, int test_c){
		driver.get(url);
		for (int j=0 ; j<transq.getSize() ; j++){
			Transition tran = transq.getTransitionByIndex(j);
			if(!tran.changeTrans(driver, test_c)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	//Dang dung
	public boolean my_run_withURL(String url, int nsleep, boolean log, int test_c){
		boolean res = true;
		int index = 0;
		
		
		System.out.println("runing:");
		textRS += "runing:\n";
		TransitionSequences_list transqlist = fsm.getPath_DFS();	//FSM SAU KHI NOI

		//transqlist = transqlistTemp;
		System.out.println("==============================================================================================");
		textRS += "==========================================================================================================================\n";
		//transqlist.printElem();
		textRS += transqlist.logElems();
		textRS += "========================================================================================================================\n\n";
		System.out.println("==============================================================================================\n\n");
		
		for (int i=0; i<transqlist.getSize(); i++){
			TransitionSequences transq =transqlist.getTransitionByIndex(i);
			
			testPathS = "";
			theResultS = "";
			detailS = "";
			
			boolean next = false;
			driver.get(url);
			//index = i;
			next = checkRemove(transq, url, test_c);
			if (next){
				System.err.println("\n\nFAIL HERE");
				textRS += "\n\nFAIL HERE!!\n";
				textFail += "\n\nFAIL HERE!!\n";
				theResultS += "IGNORE\n";
				detailS += "Ignore this test path.\n";
			}
			
			
			
			//In chi tiet tung chuyen trang thai
			//Kiem tra IGNORE nhung test path khong can thiet
			if (log){
				if (next){
					//System.err.println("So index = " + index);
					System.err.print("Test path "+(index+1)+": ");
					textRS += "Test path "+(index+1)+": "+transq.getTransitionByIndex(0).getBeginState().getName();
					textFail += "Test path "+(index+1)+": "+transq.getTransitionByIndex(0).getBeginState().getName();
					testPathS += "Test path "+(index+1)+": "+transq.getTransitionByIndex(0).getBeginState().getName();
					System.err.print(transq.getTransitionByIndex(0).getBeginState().getName());
					
					for (int j=0; j<transq.getSize(); j++){
						Transition tran = transq.getTransitionByIndex(j);
						//tran.changeTrans(driver, test_c);
						
						Event e = tran.getEvent();
						//State s1 = tran.getBeginState();
						State s2 = tran.getEndState();
						
						System.err.print("*"+e.name+"="+s2.name);
						textRS += "*"+e.name+"="+s2.name;
						textFail += "*"+e.name+"="+s2.name;
						testPathS += "*"+e.name+"="+s2.name;
					}
					System.out.println();
					textRS += "\n";
					textFail += "\n";
					testPathS += "\n";
					//index++;
					//continue;
				}else{
					//System.out.println("So index = " + index);
					System.out.print("Test path "+(index+1)+": ");
					textRS += "Test path "+(index+1)+": ";
					testPathS += "Test path "+(index+1)+": ";
					System.out.print(transq.getTransitionByIndex(0).getBeginState().getName());
					textRS += transq.getTransitionByIndex(0).getBeginState().getName();
					testPathS += transq.getTransitionByIndex(0).getBeginState().getName();
					
					for (int j=0; j<transq.getSize(); j++){
						Transition tran = transq.getTransitionByIndex(j);
						//tran.changeTrans(driver, test_c);
						
						Event e = tran.getEvent();
						//State s1 = tran.getBeginState();
						State s2 = tran.getEndState();
						
						System.out.print("*"+e.name+"="+s2.name);
						textRS += "*"+e.name+"="+s2.name;
						testPathS += "*"+e.name+"="+s2.name;
					}
					System.out.println();
					textRS += "\n";
					testPathS += "\n";
				}

				index++;
			}
			//System.out.println("check run: " + !transq.getTransitionByIndex(0).getBeginState().checkState(driver, test_c));
			if (!transq.getTransitionByIndex(0).getBeginState().checkState(driver, test_c)){
				res = false;
			}
			
			boolean passone = true;
			for (int j=0; j<transq.getSize(); j++){
				Transition tran = transq.getTransitionByIndex(j);
				Event e = tran.getEvent();
				State s1 = tran.getBeginState();
				State s2 = tran.getEndState();
				//System.out.println("check run: " + !tran.changeTrans(driver, test_c));
				if (!tran.changeTrans(driver, test_c)){
					//System.out.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
					System.err.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
					//System.out.println("\tFAIL TEST PATH: " + (j+1) + "\n");
					textRS += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
					textFail += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
					detailS += "FAIL:\n\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
					System.err.println("\tFAIL");
					textRS += "\tFAIL\n\n\n";
					textFail += "\tFAIL\n\n\n";
					theResultS = "FAIL\n";
					
					break;

					
				}else{
					if (next){
						System.err.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
						textRS += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
					}else{
						System.out.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
						textRS += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
					}
					
					
					
					//tran.printTrans();
					// thuc thi event
					try{
						e.doEvent(driver, test_c);
					} catch (Exception err){
						passone = false;
						System.out.println("FAIL EVENT");
						theResultS = "FAIL\n";
						detailS += "FAIL EVENT:	Event couldn't do.\n";
						break;
					}
					// check state sau do
					if (!s2.checkState(driver, test_c)){
						passone = false;
						textFail += "\n\nFAIL HERE!!\n";
						textFail += "FAIL STATE: \"" + s2.getName() + "\" (We can't found this state)\n";
						theResultS = "FAIL\n";
						detailS += "FAIL STATE: \"" + s2.getName() + "\" (We can't found this state)\n";
						System.out.println("FAIL STATE");
						break;
					}
					if (next){
						System.err.println("\tOK");
						textRS += "\tOK\n";
					}else{
						System.out.println("\tOK");
						textRS += "\tOK\n";
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
				System.out.print("Test path "+(i+1)+": ");
				
				System.out.print(transq.getTransitionByIndex(0).getBeginState().getName());
				textFail += "Test path "+(i+1)+": " + transq.getTransitionByIndex(0).getBeginState().getName();
				textRS += "FAILT HERE:\n" + "Test path "+(i+1)+": " + transq.getTransitionByIndex(0).getBeginState().getName();
				for (int j=0; j<transq.getSize(); j++){
					Transition tran = transq.getTransitionByIndex(j);
					Event e = tran.getEvent();
					//State s1 = tran.getBeginState();
					State s2 = tran.getEndState();
					
					System.out.print("*"+e.name+"="+s2.name);
					textFail += "*"+e.name+"="+s2.name;
					textRS += "*"+e.name+"="+s2.name;
				}
				
				System.out.println();
				textFail += "\n";
				textRS += "\n";
			}
			
			testPath.add(testPathS);
			if (!theResultS.equals("")){
				theResult.add(theResultS);
			}else{
				theResult.add("PASS\n");
			}
			detail.add(detailS);
		}
		
		textFail += "--------------------------------------------------------------------------";
		
		return res;
	}
	
	
	
	
	
	
	
	public boolean run_withURL(String url, int nsleep, boolean log, int test_c){
		boolean res = true;
		int index = 0;
		
		System.out.println("runing:");
		textRS += "runing:\n";
		TransitionSequences_list transqlist = fsm.getPath_DFS();	//FSM SAU KHI NOI
		//TransitionSequences temp = 
		//TransitionSequences_list transqlistTemp = new TransitionSequences_list();
		
		//System.out.println("Size of Transition Sequence list: " + transqlist.getSize());
		
		for (int i=0 ; i<transqlist.getSize() ; ){
			TransitionSequences transq =transqlist.getTransitionByIndex(i);
			//System.out.println(i + ". " + checkRemove(transq, url, test_c));
			boolean check = checkRemove(transq, url, test_c);
			if (check){
				transqlist.removeTransq(transq);
				continue;
			}
			i++;
		}
		
		//transqlist = transqlistTemp;
		System.out.println("==============================================================================================");
		textRS += "==========================================================================================================================\n";
		//transqlist.printElem();
		textRS += transqlist.logElems();
		textRS += "========================================================================================================================\n\n";
		System.out.println("==============================================================================================\n\n");
		
		for (int i=0; i<transqlist.getSize(); i++){
			TransitionSequences transq =transqlist.getTransitionByIndex(i);
			
			boolean next = false;
			driver.get(url);
			//index = i;
			for (int j=0 ; j<transq.getSize() ; j++){
				Transition tran = transq.getTransitionByIndex(j);
				if(!tran.changeTrans(driver, test_c)) {
					next=true;
					System.err.println("\n\nFAIL HERE");
					textRS += "\n\nFAIL HERE!!\n";
					break;
				}
			}
			//int index = i;
			//if (next){
				//index--;
				//continue;
			//}
			//System.err.println("So index = " + index);
			if (log){
				if (next){
					//System.err.println("So index = " + index);
					System.err.print("Test path "+(index+1)+": ");
					textRS += "Test path "+(index+1)+": "+transq.getTransitionByIndex(0).getBeginState().getName();
					System.err.print(transq.getTransitionByIndex(0).getBeginState().getName());
					
					for (int j=0; j<transq.getSize(); j++){
						Transition tran = transq.getTransitionByIndex(j);
						//tran.changeTrans(driver, test_c);
						
						Event e = tran.getEvent();
						//State s1 = tran.getBeginState();
						State s2 = tran.getEndState();
						
						System.err.print("*"+e.name+"="+s2.name);
						textRS += "*"+e.name+"="+s2.name;
					}
					System.out.println();
					textRS += "\n";
				}else{
					//System.out.println("So index = " + index);
					System.out.print("Test path "+(index+1)+": ");
					textRS += "Test path "+(index+1)+": ";
					System.out.print(transq.getTransitionByIndex(0).getBeginState().getName());
					textRS += transq.getTransitionByIndex(0).getBeginState().getName();
					for (int j=0; j<transq.getSize(); j++){
						Transition tran = transq.getTransitionByIndex(j);
						//tran.changeTrans(driver, test_c);
						
						Event e = tran.getEvent();
						//State s1 = tran.getBeginState();
						State s2 = tran.getEndState();
						
						System.out.print("*"+e.name+"="+s2.name);
						textRS += "*"+e.name+"="+s2.name;
					}
					System.out.println();
					textRS += "\n";
				}

				index++;
			}
			
			if (!transq.getTransitionByIndex(0).getBeginState().checkState(driver, test_c)){
				return false;
			}
			
			boolean passone = true;
			for (int j=0; j<transq.getSize(); j++){
				Transition tran = transq.getTransitionByIndex(j);
				if (tran.changeTrans(driver, test_c) == false){
					
					Event e = tran.getEvent();
					State s1 = tran.getBeginState();
					State s2 = tran.getEndState();
					//System.out.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
					System.err.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
					//System.out.println("\tFAIL TEST PATH: " + (j+1) + "\n");
					textRS += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
					System.err.println("\tFAIL");
					textRS += "\tFAIL\n";
					//String a="";
					//AnsiConsole.systemInstall();
					//AnsiConsole.out.println("\033[31m\tFAIL TEST PATH: " + (j+1) + "\n");
					
				}else{
					Event e = tran.getEvent();
					State s1 = tran.getBeginState();
					State s2 = tran.getEndState();
					if (next){
						System.err.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
						textRS += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
					}else{
						System.out.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
						textRS += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
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
						textRS += "\tOK\n";
					}else{
						System.out.println("\tOK");
						textRS += "\tOK\n";
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
				System.out.print("Test path "+(i+1)+": ");
				
				System.out.print(transq.getTransitionByIndex(0).getBeginState().getName());
				textRS += "FAILT HERE:\n" + "Test path "+(i+1)+": " + transq.getTransitionByIndex(0).getBeginState().getName();
				for (int j=0; j<transq.getSize(); j++){
					Transition tran = transq.getTransitionByIndex(j);
					Event e = tran.getEvent();
					//State s1 = tran.getBeginState();
					State s2 = tran.getEndState();
					
					System.out.print("*"+e.name+"="+s2.name);
					textRS += "*"+e.name+"="+s2.name;
				}
				
				System.out.println();
				textRS += "\n";
			}
			
		}
		
		
		return res;
	}
	
	
	public void test_DFS(){
		TransitionSequences_list transqlist = fsm.getPath_DFS();	//FSM SAU KHI NOI
		System.out.println(transqlist.getSize());
		
		for (int i=0; i<transqlist.getSize(); i++){
			TransitionSequences transq =transqlist.getTransitionByIndex(i);
			for (int j=0; j<transq.getSize(); j++){
				Transition tran = transq.getTransitionByIndex(j);
				Event e = tran.getEvent();
				State s1 = tran.getBeginState();
				State s2 = tran.getEndState();
				
				System.out.println(s1.name+"*"+e.name+"="+s2.name);
			}
			System.out.println("---------------------------------");
		}
		
		
	}
	
	
	// ham input du lieu tu file excel
	public void inputData(File file) throws Exception{

		int numOfElem = elemHtmlList.getSize();
		int numOfState = stateList.getSize();
		System.out.println("Num Of Element: " + numOfElem);
		System.out.println("Num Of State: " + numOfState);
		
		
		
		System.out.println("Input data: ");
		Workbook workbook = Workbook.getWorkbook(file);
        Sheet sheet = workbook.getSheet(0);
        
        // so cac html element
        int nelem = Integer.valueOf(sheet.getCell(0, 0).getContents().trim()).intValue();
        numOfTest = Integer.valueOf(sheet.getCell(4, 0).getContents().trim()).intValue();
       
        for (int i=0; i<nelem; i++){
        	
        	int id = Integer.valueOf(sheet.getCell(1, i+2).getContents().trim()).intValue();
        	String html_id = sheet.getCell(2, i+2).getContents().trim();
        	String type = sheet.getCell(3, i+2).getContents().trim();
        	
        	//String defaultV = sheet.getCell(4, i+2).getContents().trim();
        	
        	ArrayList<String> values = new ArrayList<String>();
        	for (int j=0; j<numOfTest; j++){
        		String tvalue = sheet.getCell(4+j, i+2).getContents().trim();
        		//if (tvalue.length()==0){ Exception e = new Exception(); throw e;} // kiem tra chuan du lieu
        		if (tvalue.length()==0){
        			tvalue = "_";
        		}
        		values.add(tvalue);
        	}
        	
        	//if(elemHtmlList.checkExist(html_id)){
        	//	elemHtmlList.getElementByName(html_id).addMoreElem(html_id, type, values);
        	//}else{
        		elemHtmlList.addElement(new Element_html(numOfElem + id, html_id, type, values));
        	//}
        }
        
        System.out.println("Number of html element: "+elemHtmlList.getSize());
        System.out.println("Number of testing values: "+numOfTest);
        
        int nstate = Integer.valueOf(sheet.getCell(0, nelem+2).getContents().trim()).intValue();
        
        //System.out.println(nstate);
        
        for (int i=0; i<nstate; i++){
        	String name = sheet.getCell(1, i+2+nelem+2).getContents().trim();
        	//Integer.valueOf(sheet.getCell(0, i+2+nelem+2).getContents().trim());
        	int beginEnd = Integer.valueOf(sheet.getCell(0, i+2+nelem+2).getContents().trim());
        	//System.out.println(name);
        	ElementStatus_list elemStList = new ElementStatus_list();
        	for (int j=0; j<nelem; j++){
        		String id = sheet.getCell(j+2, nelem+2+1).getContents().trim();
        		String st = sheet.getCell(j+2, i+2+nelem+2).getContents().trim();
        		if (st.length() == 0){
        			st = "_";
        		}
        		//System.out.println(id+"-"+st);
        		elemStList.addElement(new ElementStatus(Integer.valueOf(id).intValue() + numOfElem, st));
        	}
        	
    		stateList.addState(new State(name, elemStList, elemHtmlList));
        	
        	
        	
        	//-------------------------------
        	// Gan cac gia tri cho state bat dau va ket thuc
        	// Co the co nhieu endstate
        	//-------------------------------
        	
        	if (beginEnd == 0 && !stateList.checkExist(name)){
        		beginState = new State(name, elemStList, elemHtmlList);
        	}else if (beginEnd == 1){
        		endStateList.addState(new State(name, elemStList, elemHtmlList));
        	}
        	
        }
        System.out.println("Number of states: "+stateList.getSize());
        
        int nevent = Integer.valueOf(sheet.getCell(0, nelem+2+nstate+2).getContents().trim()).intValue();
        
        //System.out.println(nevent);
        
        for (int i=0; i<nevent; i++){
        	
        	String name = sheet.getCell(1, i+2+nelem+2+nstate+2).getContents().trim();
        	String elem_id = sheet.getCell(2, i+2+nelem+2+nstate+2).getContents().trim();
        	String action = sheet.getCell(3, i+2+nelem+2+nstate+2).getContents().trim();
        	
        	//eventList.addEvent(new Event(name, Integer.valueOf(elem_id).intValue(), action, elemHtmlList));
        	eventList.addEvent(new Event(name, elem_id, action, elemHtmlList));
        }
        
        System.out.println("Number of events: "+eventList.getSize());
        //Them thong so ve so dau vao
        int ntrans = Integer.valueOf(sheet.getCell(0, nelem+2+nstate+2+nevent+2).getContents().trim()).intValue();
        
        for (int i=0; i<ntrans; i++){
        	//cellstring
        	for (int j=0; j<nstate; j++){
        		String cellString = sheet.getCell(2, i+2+nevent+2+nelem+2+nstate+2).getContents().trim();
        		//System.out.println("Sheet Condition:" + j + "\t" + cellString);
        		String enames = sheet.getCell(j+3,i+2+nevent+2+nelem+2+nstate+2).getContents().trim();	//Dich lui 1 cot
        		String s1name = sheet.getCell(1,i+2+nevent+2+nelem+2+nstate+2).getContents().trim();
        		String s2name = sheet.getCell(j+3,1+nevent+2+nelem+2+nstate+2).getContents().trim();	//Dich lui 1 cot
        		
        		if (enames.length() == 0){
        			enames = "_";
        		}
        		
        		if (cellString.length() == 0){
        			cellString = "_";
        		}
        		
        		ArrayList<String> ename = new ArrayList<String>();
        		ename = subEvents(enames);
        		//System.out.println(s1name + "--" + ename + "-->" + s2name + " :\t" + cellString);
        		for (int k=0 ; k<ename.size() ; k++){
        			if (ename.get(k).length()==0 || ename.get(k).compareTo("_")==0){
            			continue;
            		}
        			transitionList.addTransition(new Transition(eventList.getEventByName(ename.get(k)), 
            				stateList.getStateByName(s1name), 
            				stateList.getStateByName(s2name), cellString));
        		}
        	}
        	
        }
        System.out.println("Number of transitions: "+transitionList.getSize());   
        fsm = new FSM_G(numOfTest, this.name, stateList, transitionList, beginState, endStateList);
        //return fsm1;
	}
	
	public void exportData(File file) throws Exception{
		
		try{
			WritableWorkbook workbook1 = Workbook.createWorkbook(file);
			WritableSheet sheet1 = workbook1.createSheet("First Sheet", 0); 
			
			for (int i=0 ; i<testPath.size() ; i++){
				Label tp = new Label(0, i, testPath.get(i));
				Label rs = new Label(1, i, theResult.get(i));
				Label dt = new Label(2, i, detail.get(i));
				sheet1.addCell(tp);
				sheet1.addCell(rs);
				sheet1.addCell(dt);
			}
			workbook1.write();
            workbook1.close();
		}catch(Exception ex){
			
		}
	}
	
	public ArrayList<String> subEvents(String events){
		String tempEvent = events;
		ArrayList<String> result = new ArrayList<String>();
		
		if (!events.equals("_")){
			
			while (tempEvent != null){
				
				String buff1 = "";
				String buff2 = "";
				
				int charac = tempEvent.indexOf(",");
				if (charac >= 0){
					
					buff1 = tempEvent.substring(0, charac);
					buff2 = tempEvent.substring(charac + 1, tempEvent.length());
					result.add(buff1);
					tempEvent = buff2;
				}else{
					
					buff1 = tempEvent;
					result.add(buff1);
					tempEvent = null;
				}
			}
		}else{
			
			result.add("_");
		}
		
		return result;
	}
		
	public String getTxt(){
		return textRS;
	}
		
	public void quitDriver(){
		driver.quit();
	}

}
