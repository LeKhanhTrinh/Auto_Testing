package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import TestPath2Webdriver.WebdriverCommand;


@SuppressWarnings("serial")
public class TestMain1 extends JFrame {

    /** Creates new form NewJFrame */
    public TestMain1() {
        initComponents();
        setTitle("Auto Testing Web Application");
    }

    /** This method is called   from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel2 = new JLabel();
        fileButton = new JButton();
        linkWeb = new JTextField();
        jScrollPane1 = new JScrollPane();
        log = new JTextArea();
        jFileChooser = new JFileChooser(new File("D:\\Dropbox\\NCKH\\Testcase"));
        jFileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Web");

        fileButton.setText("Open .xls folder");
        fileButton.setToolTipText("");
        fileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int returnVal = jFileChooser.showOpenDialog(TestMain1.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = jFileChooser.getSelectedFile();
					//log.append("Opening: " + file.getPath() + "." + "\n");
					result = file.getPath();
				}else{
					log.append("ERROR: Open .xls file command cancelled by user.");
				}
				
				try{
	        		process();
	        		fileButton.setVisible(false);
	        		linkWeb.setVisible(false);
	        		jLabel2.setVisible(false);
	        	}catch (Exception ex) {
					// TODO: handle exception
				}
			}
		});

        //-------------------------------------------------------------------------------
        //Web Address
        //-------------------------------------------------------------------------------
        linkWeb.setText("http://localhost:8080/ConsultingAgency/");
        linkWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        log.setColumns(20);
        log.setRows(5);
        log.setEditable(false);
        jScrollPane1.setViewportView(log);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10)
                        .addComponent(linkWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(fileButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(linkWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileButton)
                    .addComponent(jLabel2))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>

	private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
	// TODO add your handling code here:
	}
	
	/**
	 * 
	 */
	
	public static void process() throws Exception{
    	String resultF1 = editString(result);
    	//resultF2 = editString(resultF2);
    	//System.out.println(result + "\n" + result1);
    	link = linkWeb.getText();
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
    
	/**
	 *	 runTest case
	 */
	
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
			log.append(webcom.textFail);
			log.append(webcom.textRS);
			webcom.exportData(new File("result.xls"));
			webcom.quitDriver();
			
		} catch (Exception e){
			
		}
		
		return b1;
	}
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new TestMain1().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    private static JButton fileButton;
    private JLabel jLabel2;
    private static JScrollPane jScrollPane1;
    private static JTextArea log;
    private static JTextField linkWeb;
    private static JFileChooser jFileChooser;
    private static String link="";
    private static String result="";
    // End of variables declaration
}
