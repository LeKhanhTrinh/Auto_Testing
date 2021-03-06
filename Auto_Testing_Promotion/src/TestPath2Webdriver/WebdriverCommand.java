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
		fsm.printTransition();
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
		transqlist.printElem();
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
        		//String cellString = sheet.getCell(2, i+2+nevent+2+nelem+2+nstate+2).getContents().trim();
        		//System.out.println("Sheet Condition:" + j + "\t" + cellString);
        		String eventNConds = sheet.getCell(j+2,i+2+nevent+2+nelem+2+nstate+2).getContents().trim();	//Dich lai 1 cot
        		String s1name = sheet.getCell(1,i+2+nevent+2+nelem+2+nstate+2).getContents().trim();
        		String s2name = sheet.getCell(j+2,1+nevent+2+nelem+2+nstate+2).getContents().trim();	//Dich lai 1 cot
        		
        		/*
        		if (enames.length() == 0){
        			enames = "_";
        		}
        		
        		if (cellString.length() == 0){
        			cellString = "_";
        		}
        		
        		*/
        		
        		if (eventNConds.length() == 0){
        			eventNConds = "_";
        		}
        		
        		ArrayList<String> ECnames = new ArrayList<String>();
        		ECnames = subEvents(eventNConds); //List cac event va condition
        		
        		//System.out.println(s1name + "--" + ename + "-->" + s2name + " :\t" + cellString);
        		for (int k=0 ; k<ECnames.size() ; k++){
        			if (ECnames.get(k).length()==0 || ECnames.get(k).compareTo("_")==0){
            			continue;
            		}
        			
        			String eName = getNameEvent(ECnames.get(k));
        			Condition cond = getCond(ECnames.get(k));
        			System.out.println("Name Event: " + eName);
        			transitionList.addTransition(new Transition(eventList.getEventByName(eName), 
            				stateList.getStateByName(s1name), 
            				stateList.getStateByName(s2name), cond));
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
				Label rs = new Label(5, i, theResult.get(i));
				Label dt = new Label(8, i, detail.get(i));
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

	public String getNameEvent(String input){
		String temp = "";
		if (input.indexOf("]") >= 0){
			temp = input.substring(input.indexOf("]")+1, input.length());
		}else{
			temp = input;
		}
		
		return temp;
	}
	
	public Condition getCond(String input){
		int moNgoac = input.indexOf("[");
		int dongNgoac = input.indexOf("]");
		String conds = "";
		Condition conditions;
		if (moNgoac >= 0 && dongNgoac > moNgoac){
			conds = input.substring(moNgoac+1, dongNgoac);
			int gachCheo = conds.indexOf("/");
			String id = conds.substring(0, gachCheo);
			String values = conds.substring(gachCheo+1, conds.length());
			conditions = new Condition(id, values);
		}else{
			conditions = new Condition();
		}
		return conditions;
	}
}
