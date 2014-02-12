package backup;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import TestPath2Webdriver.WebdriverCommand;

@SuppressWarnings("serial")
public class TestMain extends JPanel implements ActionListener {

	//static private final String newline = "\n";
    JButton openButton, webButton, exitButton;
    public static JTextArea log;
    JFileChooser fc, fc1;
    public static String txtRS = "a";
    static JFrame frame = new JFrame("Auto Testing Web Application");
    public static String result="";
    public static String resultF2="";
    public static boolean chosenXls1 = false;
    public static boolean chosenJsp = false;
   //public static String link = "http://localhost:8080/web_demo/";
    public static String link = "http://localhost:8080/";
    
    public TestMain() {
        super(new BorderLayout());
 
        //Create the log first
        log = new JTextArea(15,50);
        log.setMargin(new Insets(5,5,5,5));
        //log.append("Choose .xls file to testing\n");
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
 
        //Create a file chooser
        fc = new JFileChooser(new File("D:\\Dropbox\\NCKH\\Testcase"));
        //fc1 = new JFileChooser(new File("D:\\xampp\\tomcat\\webapps\\web_demo"));
        fc1 = new JFileChooser(new File("D:\\Java Server Face\\WebDemo\\web"));
        
        fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
        openButton = new JButton("Open .xls FOLDER");
        openButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int returnVal = fc.showOpenDialog(TestMain.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					//log.append("Opening: " + file.getPath() + "." + "\n");
					result = file.getPath();
				}else{
					log.append("ERROR: Open .xls file command cancelled by user.");
				}
				chosenXls1 = true;
				
				if (chosenJsp){
		        	try{
		        		process();
		        		openButton.setVisible(false);
		        		webButton.setVisible(false);
		        	}catch (Exception ex) {
						// TODO: handle exception
					}
		        }
			}
		});
        
        webButton = new JButton("Open web file");
        webButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int returnVal = fc1.showOpenDialog(TestMain.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc1.getSelectedFile();
					//link += file.getName();
					String testName = file.getParentFile().getParentFile().getName() + "/" + "faces" + "/" + file.getName();
					//log.append("Opening: " + link + "." + "\n");
					//System.out.println("link: " + testName);
					link += testName;
					//result = file.getPath();
				}else{
					log.append("ERROR: Open .jsp file command cancelled by user.");
				}
				chosenJsp = true;
				//log.append("TEST: " + chosenXls);
				if (chosenXls1){
		        	try{
		        		process();
		        		openButton.setVisible(false);
		        		webButton.setVisible(false);
		        	}catch (Exception ex) {
						// TODO: handle exception
					}
		        }
			}
		});
        
        
        
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(webButton);
 
        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
   	 
        int returnVal = fc.showOpenDialog(TestMain.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            log.append("Opening: " + file.getPath() + "." + "\n");
            //log.append("here" + txtRS);
            result = file.getPath();
            int returnVal1 = fc.showOpenDialog(TestMain.this);
            if (returnVal1 == JFileChooser.APPROVE_OPTION){
            	file = fc.getSelectedFile();
                log.append("Opening: " + file.getPath() + "." + "\n");
            }
            
        } else {
            //log.append("Open command cancelled by user." + newline);
        }
        //frame.setVisible(false);
        openButton.setVisible(false);
        
        
        try{
        	process();
        }catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new TestMain());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void process() throws Exception{
    	String resultF1 = editString(result);
    	//resultF2 = editString(resultF2);
    	//System.out.println(result + "\n" + result1);
    	
    	boolean b1 = true;
		
		//b1 = runTestCase(resultF1, resultF2, link, 00, true);
		b1 = runTestCase(resultF1, link, 00, true);
		
		if (b1){
			System.out.println("PASS");
			log.append("PASS");
		} else {
			System.out.println("FAIL");
			log.append("FAIL");
		}
    }
    
    public static String editString(String input){
    	String escaped = input.replace("\\", "\\\\");
    	return escaped;
    }
    
  //------------------------------------------------------------------------------------
    public static boolean runTestCase(String folder, String url, int delay, boolean islogging){
		boolean b1 = false;
		
		try{
			
			//WebdriverCommand webcom = new WebdriverCommand("abc",  new File(filename));
			
			WebdriverCommand webcom = new WebdriverCommand("abc",  folder);
			
			System.out.println("FILE OK");
			log.append("FILE OK");
			
			b1 = webcom.runTestCaseWithUrl(url, delay, islogging);
			System.out.println("Da chay xong");
			//txtRS =  webcom.getTxt();
			log.append(webcom.textRS);
			
			webcom.quitDriver();
			
		} catch (Exception e){
			
		}
		
		return b1;
	}
    
    
    //------------------------------------------------------------------------------------
    public static boolean runTestCase(String filename, String filename1, String url, int delay, boolean islogging){
		boolean b1 = false;
		
		try{
			
			//WebdriverCommand webcom = new WebdriverCommand("abc",  new File(filename));
			
			WebdriverCommand webcom = new WebdriverCommand("abc",  new File(filename), new File(filename1));
			
			System.out.println("FILE OK");
			log.append("FILE OK");
			
			b1 = webcom.runTestCaseWithUrl(url, delay, islogging);
			System.out.println("chay qua day");
			//txtRS =  webcom.getTxt();
			log.append(webcom.textRS);
			
			webcom.quitDriver();
			
		} catch (Exception e){
			
		}
		
		return b1;
	}
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
        
    }

    public void printlnText(String msg){
    	log.append(msg + "\n");
    }
}
