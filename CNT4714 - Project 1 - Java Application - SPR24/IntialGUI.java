/* 
 * Name: Luis Munoz
 Course: CNT 4714 – Spring 2024
 Assignment title: Project 1 – An Event-driven Enterprise Simulation
 Date: Sunday January 28, 2024
*/
package pa01;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;



//*********************************************************************
public class IntialGUI extends JFrame 
{
	//
	private static final int WIDTH = 700;
	//
	private static final int HEIGHT = 600;
	
	private JLabel blankLabel, controlsLabel, idLabel, qtyLabel, itemLabel, cartLabel, currentTotalLabel;
	private JButton blankButton, processB, confirmB, viewB, finishB, newB, exitB; //variable newB was newb
	private JTextField blankTextField, idTextField, qtyTextField, itemTextField, totalTextField, cartLineArray,
	firstItemTextField, secondItemTextField, thirdItemTextField, fourthItemTextField, fifthItemTextField;
	
	//declare reference variables for event handlers - one for each handler - six in this case 
	private ProcessButtonHandler procbHandler;
	private ConfirmButtonHandler confbHandler;
	private ViewButtonHandler viewbHandler;
	private FinishButtonHandler finbHandler;
	private NewButtonHandler newbHandler;
	private ExitButtonHandler exitbHandler;
	
	private static int itemCount = 0;
	private static String[] userItems = new String[5];
	private static double orderSubTotal = 0.00;
	
	//*****************************************************************
	public IntialGUI() 
	{
		setTitle("Nile.com - Spring 2024");
		setSize(WIDTH,HEIGHT);
		
		blankButton = new JButton("	");
		blankLabel = new JLabel("",SwingConstants.RIGHT);
		
		idLabel = new JLabel("Enter Item ID for Item #" + (itemCount+1) + ":", SwingConstants.RIGHT);
		qtyLabel = new JLabel("Enter Quantity for Item #" + (itemCount+1) + ":", SwingConstants.RIGHT);
		itemLabel = new JLabel("Details for Item #" + (itemCount+1) + ":", SwingConstants.RIGHT);
		currentTotalLabel = new JLabel("Current Subtotal for "+ (itemCount) + " item(s):", SwingConstants.CENTER);
		 
		controlsLabel = new JLabel(" USER CONTROLS ", SwingConstants.RIGHT);
		cartLabel = new JLabel("Your Cart is Currently Empty", SwingConstants.CENTER);

		
		blankTextField = new JTextField();
		idTextField = new JTextField();
		qtyTextField = new JTextField();
		itemTextField = new JTextField();
		totalTextField = new JTextField();
		cartLineArray = new JTextField();
		
		firstItemTextField = new JTextField();
		secondItemTextField = new JTextField();
		thirdItemTextField = new JTextField();
		fourthItemTextField = new JTextField();
		fifthItemTextField = new JTextField();

		processB 	 = new JButton("Find Item #" + (itemCount+1));
		procbHandler = new ProcessButtonHandler();
		processB.addActionListener(procbHandler);
		
		confirmB 	 = new JButton("Add Item #" + (itemCount+1) + " To Cart");
		confbHandler = new ConfirmButtonHandler();
		confirmB.addActionListener(confbHandler);
		
		viewB 		 = new JButton("View Cart");
		viewbHandler = new ViewButtonHandler();
		viewB.addActionListener(viewbHandler);
		
		finishB 	 = new JButton("Check Out");
		finbHandler = new FinishButtonHandler();
		finishB.addActionListener(finbHandler);
		
		newB = new JButton("Empty Cart - Start A New Order");
		newbHandler = new NewButtonHandler();
		newB.addActionListener(newbHandler);
		
		exitB = new JButton("Exit (Close App)");
		exitbHandler = new ExitButtonHandler();
		exitB.addActionListener(exitbHandler);
				
				
		//button settings		
		confirmB.setEnabled			(false);//disable confirm until calculation complete
		viewB.setEnabled			(false);
		finishB.setEnabled			(false);//disable finish until confirm complete
		itemTextField.setEditable (false);
		totalTextField.setEditable(false);
		blankButton.setBackground(Color.DARK_GRAY);
		blankButton.setVisible(false);
	
		Container pane = getContentPane();

		GridLayout grid6by2 = new GridLayout(6,2,8,4);
		//GridLayout grid5by1 = new GridLayout(5,1,2,2); //for cart
		GridLayout grid7by2 = new GridLayout(7,2,8,4);

			
		//
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel southPanel = new JPanel();
		
		//
		northPanel.setLayout(grid6by2);
		centerPanel.setLayout(grid6by2);
		southPanel.setLayout(grid6by2);
		
		//
		pane.add(northPanel, BorderLayout.NORTH);
		pane.add(centerPanel, BorderLayout.CENTER);
		pane.add(southPanel, BorderLayout.SOUTH);

		
		//
		pane.setBackground(Color.CYAN);
		northPanel.setBackground(getForeground());
		//
		//
		centerPanel.setBackground(Color.LIGHT_GRAY);
		southPanel.setBackground(Color.GREEN);
		
		centerFrame(WIDTH,HEIGHT);
		

		northPanel.add(idLabel);
		idLabel.setHorizontalAlignment(JLabel.CENTER);
		northPanel.add(idTextField);
		idTextField.setHorizontalAlignment(JLabel.RIGHT);
		
		northPanel.add(qtyLabel);
		qtyLabel.setHorizontalAlignment(JLabel.CENTER);
		northPanel.add(qtyTextField);
		qtyTextField.setHorizontalAlignment(JLabel.RIGHT);
		
		northPanel.add(itemLabel);
		itemLabel.setHorizontalAlignment(JLabel.CENTER);
		northPanel.add(itemTextField);
		itemTextField.setHorizontalAlignment(JLabel.RIGHT);
		
		northPanel.add(currentTotalLabel);
		currentTotalLabel.setHorizontalAlignment(JLabel.CENTER);
		northPanel.add(totalTextField);
		totalTextField.setHorizontalAlignment(JLabel.RIGHT);
		
		
		centerPanel.add(cartLabel);
		cartLabel.setHorizontalAlignment(JLabel.CENTER);
		centerPanel.add(firstItemTextField);
		firstItemTextField.setText(null);
		centerPanel.add(secondItemTextField);
		secondItemTextField.setText(null);
		centerPanel.add(thirdItemTextField);
		thirdItemTextField.setText(null);
		centerPanel.add(fourthItemTextField);
		fourthItemTextField.setText(null);
		centerPanel.add(fifthItemTextField);
		fifthItemTextField.setText(null);
		

		southPanel.add(controlsLabel);
		controlsLabel.setHorizontalAlignment(JLabel.CENTER);
		southPanel.add(blankButton);
		
		
		southPanel.add(processB);
		southPanel.add(confirmB);
		southPanel.add(viewB);
		southPanel.add(finishB);
		southPanel.add(newB);
		southPanel.add(exitB);
		//	
	}//end constructor

	//**************************************************************
	public void centerFrame(int frameWidth, int frameHeight) 
	{
		//
		Toolkit aToolkit = Toolkit.getDefaultToolkit();
		
		//
		Dimension screen = aToolkit.getScreenSize();
		
		//
		int xPositionOfFrame = (screen.width - frameWidth) / 2;
		int yPositionOfFrame = (screen.height - frameHeight) / 2;
		
		//
		setBounds(xPositionOfFrame, yPositionOfFrame, frameWidth, frameHeight);
				
	}// end method
	
	public String itemsPurchased(String[] userItems) {
		String itemsPurchased = "1. " + userItems[0];
		for (int a = 1;a<itemCount;a++) {
			itemsPurchased += "\n"+(a+1)+". " + userItems[a];
		}

		return itemsPurchased;
	}
	
	
	public String transactions(String[] userItems) {
		DateFormat recieptID = new SimpleDateFormat("DDMMYYYYHHMMSS");
		Calendar obj = Calendar.getInstance();
		String receiptNumber = recieptID.format(obj.getTime());
		String transactions = receiptNumber + " " + userItems[0];
		for (int b = 1;b<itemCount;b++) {
			transactions += receiptNumber + " " + userItems[b]+ "\n\n";
		}
		return transactions;
	}
	
	
//*********************************************************************
	private class ProcessButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e ) 
		{
			String userItem = idTextField.getText();
			int userQty = Integer.parseInt(qtyTextField.getText());
			
			File inputFile = new File("inventory.csv");
			FileReader inputFileReader = null;
			BufferedReader inputBufReader = null;
			Scanner aScanner;
			try {
				aScanner = new Scanner(inputFile);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} //scanner object
			String inventoryLine;
			String searchID;
			String itemID;
			String itemDesc;
			String[] itemInfo = null;
			boolean isItemAvail = false;
			boolean isItemFound = false;
			int itemQty = 0;
			double itemPrice = 0.00;

			try {
				inputFileReader = new FileReader(inputFile);
				inputBufReader = new BufferedReader(inputFileReader);
				
				System.out.println("Search Item: " + userItem);
				
				inventoryLine = inputBufReader.readLine(); // read from file
				
				whileloop:while(inventoryLine != null) {
					
					aScanner =  new Scanner(inventoryLine).useDelimiter("\\s*,\\s*");
					searchID = aScanner.next();
					if(searchID.equals(userItem)) 
					{
						isItemFound = true;
						
						itemInfo = inventoryLine.split("\\s*,\\s*");
						itemID = itemInfo[0];				//item ID
						itemDesc = itemInfo[1];								//item Description
						isItemAvail = Boolean.parseBoolean(itemInfo[2]);	//item stock status
						itemQty = Integer.parseInt(itemInfo[3]);			//item quantity
						itemPrice = Double.parseDouble(itemInfo[4]);		//item price
						
						int discount = 0;
						if (userQty>=5 && userQty<=9) 
						{
							discount = 10;
						}else if (userQty>= 10 && userQty<=14) 
						{
							discount = 15;
						}else if (userQty>=15) 
						{
							discount = 20;
						}
						
						double subTotal = (itemPrice - ((discount * 0.01)* itemPrice)) * userQty;//the discounted price of the user requested qty
						orderSubTotal += subTotal;

						
						if (isItemAvail != true) {
							JOptionPane.showMessageDialog(null, "Sorry...that item is out of stock","Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
							idTextField.setText("");
							qtyTextField.setText("");
						}
						else if (itemQty < userQty) {
							JOptionPane.showMessageDialog(null, "Insufficient stock. Only "+itemQty+" on hand. Please reduce the quantity","Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
							qtyTextField.setText("");
						}else {
							//JOptionPane.showMessageDialog(null, "Item added!","Nile Dot Com", JOptionPane.INFORMATION_MESSAGE);
							itemTextField.setText(itemID+" "+itemDesc+" $"+itemPrice+" "+userQty+" "+discount+"% $"+subTotal);
							confirmB.setEnabled			(true);
							viewB.setEnabled			(true);
							finishB.setEnabled			(true);
						}
						
						break whileloop;
					}
					else {
						inventoryLine = inputBufReader.readLine();//read next line from file
					}
				}//end while
				
				if (isItemFound != true) {
					JOptionPane.showMessageDialog(null, "Item ID "+userItem+" is not on file","Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
					idTextField.setText("");
					qtyTextField.setText("");
				}
						

			}
			catch(FileNotFoundException fileNotFoundException) {
				JOptionPane.showMessageDialog(null, "Error: File not found","Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
			}
			catch(IOException ioException) {
				JOptionPane.showMessageDialog(null, "Error: Problem reading file ","Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);

			}

		}// end method
		
	}// end class

//************************************************************************************
	private class ConfirmButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e ) 
		{
			String addedItem = itemTextField.getText();
			userItems[itemCount]=addedItem;
			System.out.println(userItems[itemCount]);
			itemCount++;

			
			idLabel.setText("Enter Item ID for Item #" + String.valueOf(itemCount+1) + ":");
			qtyLabel.setText("Enter Quantity for Item #" + (itemCount+1) + ":");
			itemLabel.setText("Details for Item #" + (itemCount+1) + ":");
			cartLabel.setText("Current Subtotal for "+ (itemCount) + " item(s):");

			processB.setText("Find Item #" + (itemCount+1));
			confirmB.setText("Add Item #" + (itemCount+1) + " To Cart");
			
			
			idTextField.setText(null);
			qtyTextField.setText(null);
			itemTextField.setText(null);
			totalTextField.setText("$"+orderSubTotal);
			
			for (int i = 0; i < itemCount;i++) {
				if (i==0) {
					firstItemTextField.setText("Items 1 -" + userItems[i]);
				}
				if (i==1) {
					secondItemTextField.setText("Items 2 -" + userItems[i]);
				}
				if (i==2) {
					thirdItemTextField.setText("Items 3 -" + userItems[i]);
				}
				if (i==3) {
					fourthItemTextField.setText("Items 4 -" + userItems[i]);
				}
				if (i==4) {
					fifthItemTextField.setText("Items 5 -" + userItems[i]);
				}
			}

			
			confirmB.setEnabled			(false);

		} // end method
	
	} // end class

//************************************************************************************
	private class ViewButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e ) 
		{
			String viewCart = itemsPurchased(userItems);
			JOptionPane.showMessageDialog(null, viewCart, "Nile Dot Com - Current Shopping Cart Status", JOptionPane.INFORMATION_MESSAGE);
						
		} // end method
		
	} // end class
//************************************************************************************
		private class FinishButtonHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e ) 
			{
				double taxRate = 0.06;
				double taxAmount = orderSubTotal * taxRate;
				double orderTotal = orderSubTotal + taxAmount;
				DateFormat dfor = new SimpleDateFormat("MMMM dd, yy, hh:mm:ssa z");
				Calendar obj = Calendar.getInstance();
				JOptionPane.showMessageDialog(null, "Date:"+dfor.format(obj.getTime())
				+"\n\nNumber of line items:"+itemCount
				+"\n\nItem# / ID / Title / Price / Qty / Disc% / Subtotal\n"+itemsPurchased(userItems)
				+"\n\nOrderSubtotal: 	$"+orderSubTotal
				+"\nTax rate:			%6"
				+ "\nTax amount:		$"+taxAmount
				+"\nOrder total:		$"+orderTotal
				+"\nThanks for shopping at Nile Dot Com!"
				,"Nile Dot Com - FINAL INVOICE", JOptionPane.INFORMATION_MESSAGE);
				
			     try {
			   	     FileWriter outputFile = new FileWriter("transactions.csv",true);
			    	 BufferedWriter writer = new BufferedWriter(outputFile);
			 		DateFormat recieptID = new SimpleDateFormat("DDMMYYYYHHMMSS");
					Calendar cal = Calendar.getInstance();
					String receiptNumber = recieptID.format(cal.getTime());
					writer.write(receiptNumber + " " + userItems[0]);
					writer.newLine();
					for (int b = 1;b<itemCount;b++) {
						writer.write(receiptNumber + " " + userItems[b]);
						writer.newLine();
					}
					writer.newLine();
					writer.newLine();    
			    	writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			     
					Arrays.fill(userItems,null);
					confirmB.setEnabled			(false);
					viewB.setEnabled			(false);
					finishB.setEnabled			(false);
					
					itemCount=0;
					idLabel.setText("Enter item ID for Item #" + String.valueOf(itemCount+1) + ":");
					qtyLabel.setText("Enter quantity for item" + (itemCount+1) + ":");
					itemLabel.setText("Details for Item #" + (itemCount+1) + ":");
					processB.setText("Find Item #" + (itemCount+1));
					confirmB.setText("Add Item #" + (itemCount+1) + " To Cart");
					
					idTextField.setText(null);
					qtyTextField.setText(null);
					itemTextField.setText(null);
					totalTextField.setText(null);

					firstItemTextField.setText(null);
					secondItemTextField.setText(null);
					thirdItemTextField.setText(null);
					fourthItemTextField.setText(null);
					fifthItemTextField.setText(null);


				
			} // end method
		
		} // end class

//********************************************************************************
		private class NewButtonHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e ) 
			{
					System.out.println("The Empty Cart Button Was Clicked...");
					Arrays.fill(userItems,null);
					confirmB.setEnabled			(false);
					viewB.setEnabled			(false);
					finishB.setEnabled			(false);
					
					itemCount=0;
					idLabel.setText("Enter item ID for Item #" + String.valueOf(itemCount+1) + ":");
					qtyLabel.setText("Enter quantity for item" + (itemCount+1) + ":");
					itemLabel.setText("Details for Item #" + (itemCount+1) + ":");
					processB.setText("Find Item #" + (itemCount+1));
					confirmB.setText("Add Item #" + (itemCount+1) + " To Cart");
					
					idTextField.setText(null);
					qtyTextField.setText(null);
					itemTextField.setText(null);
					totalTextField.setText(null);

					firstItemTextField.setText(null);
					secondItemTextField.setText(null);
					thirdItemTextField.setText(null);
					fourthItemTextField.setText(null);
					fifthItemTextField.setText(null);	
					
			} // end method
			
			
		} // end class

//**************************************************************************************
		private class ExitButtonHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e ) 
			{
					System.out.println("The Exit Item To Cart Button Was Clicked...");
					System.exit(0);		
			} // end method
			
		} // end class
//**************************************	
	public static void main(String [] args) 
	{
		JFrame aNewStore = new IntialGUI();
		aNewStore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aNewStore.setVisible(true);
	}//end main
	
	
}
