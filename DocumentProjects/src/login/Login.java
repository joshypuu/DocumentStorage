package login;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import home.Home;


public class Login {

	public JFrame frame;
	private JTextField username;
	private JPasswordField password;
	public static String driver = "com.mysql.cj.jdbc.Driver";
	public static String url = "jdbc:mysql://localhost:3306/USERS?autoReconnect=true&useSSL=false";
    public String uname = "root";
    public String pword = "Gears114??";
    

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try 
	    {

			EventQueue.invokeLater(new Runnable() {
			
			/**
			 * shows the login in frame
			 */
			public void run() {
				try {
					//Initialize Login window
					Login window = new Login();
					
					//Set login window to visible
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			});
	    }catch (Exception e) {
	    	//print out that connection to database has failed
	    	System.out.println("Connection to database failed\n");
	        e.printStackTrace();
	    }		
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize(url, uname, pword);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String ul, String ur, String pwd) {
		
		//Initialize JFrame
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Login label
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(198, 24, 61, 16);
		frame.getContentPane().add(lblLogin);
		
		//Username label
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(89, 116, 73, 16);
		frame.getContentPane().add(lblUsername);
		
		//Username variable
		username = new JTextField();
		username.setBounds(161, 111, 130, 26);
		frame.getContentPane().add(username);
		username.setColumns(10);
		
		//Password label
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(89, 167, 73, 21);
		frame.getContentPane().add(lblPassword);
	    
		//Password variable
		password = new JPasswordField();
		password.setBounds(161, 164, 130, 26);
		frame.getContentPane().add(password);
		password.setColumns(10);
		
		//Login button
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener(){
			
			/**
			 * Action performed for login button
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//variables to check the text from both username and password textfields
				String n = username.getText();
				String p = password.getText();
				
				System.out.println("Login button was pressed");
				
				try {
					
					System.out.println(n);
					//SQL statement that searches through databases to check both username and password
					String sql = "SELECT * FROM USERS WHERE userName =? AND passWord =?";
					
					//connection to database
					Connection connection = DriverManager.getConnection(ul, ur, pwd);
					
					//prepared statement
					PreparedStatement statement = connection.prepareStatement(sql);
					
					//set username
					statement.setString(1, n);
					
					//set password
					statement.setString(2, p);
					
					//ResultSet that executes SQL statement
					ResultSet rs = statement.executeQuery();
			
					//if username space is empty
					if( username.getText().isEmpty() || password.getText().isEmpty()) {
					
						//display message
						JOptionPane.showMessageDialog(null, "Please enter username and password");
				
					//if username and password match
					}else if(rs.next()){
		
						Class.forName(driver);
				    	System.out.println();
						System.out.println("Connection to database successful\n");
						
						//username
						String name1 = username.getText();	
					
						//display welcome message for user
						JOptionPane.showMessageDialog(null, "Welcome " + name1 );
					
						//initialize Home object
						Home home = new Home(name1,connection);
					
						//set Home window to visible 
						home.setVisible(true);
					
						//close login window
						frame.dispose();

					}else {
						
						//display login failure message
						JOptionPane.showMessageDialog(null, "login failed. Retry");
					
					}
				}catch(Exception a){}	
			}
  
			private void setVisble(boolean b) {} 
		
		});
		
		//set bounds 
		btnLogin.setBounds(89, 221, 117, 29);
		frame.getContentPane().add(btnLogin);
	
		//create button for creating a new user
		JButton btnCreateUser = new JButton("Create User");
		btnCreateUser.addActionListener(new ActionListener(){
       
			/**
			 * actionPerformed for create user button
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				 
				//username
				String name = username.getText();
				
				//password
				String pass = password.getText();

				try {
					
					//calls insert user method
					insertUser(ul, ur, pwd, name, pass);
				
				}catch(ClassNotFoundException | SQLException e1) {
					
					e1.printStackTrace();
				
				}
			}
			});
		
		//set bounds for create user button
		btnCreateUser.setBounds(235, 221, 117, 29);
		frame.getContentPane().add(btnCreateUser);
	}
	
	/**
	 * insert user method used to insert new user to USER database
	 */
	public static void insertUser(String ul, String ur, String  pwd, String n, String p) throws ClassNotFoundException, SQLException {
		
		System.out.println("initiate user input into database");
		
		//driver
		Class.forName(driver);
		
		//connection
		Connection connection = DriverManager.getConnection(ul, ur, pwd);
		
		//sql statement
		String sql = "INSERT INTO USERS(userName, passWord) VALUES (?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		//set username
		statement.setString(1, n); 
		
		//set user password
		statement.setString(2, p); 

		//initialize rows to 1 id statement was successfully executed
		int rows = statement.executeUpdate();
		
		//if rows is greater than 0, print row was inserted 
		if(rows > 0) { 
			System.out.println("A user has been inserted");
		}
		
		System.out.println("insert successful");
	}

	/**
	 * set visable method
	 * @param b
	 */
	public void setVisible(boolean b) {}
}
