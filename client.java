package sk_program;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame implements ActionListener{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public JTextField userText;
public JTextArea chatWindow;
private JLabel sc1;
private JLabel sc2;
private JLabel index;
public ObjectOutputStream output;
public ObjectInputStream input;
public String message = "";
public String serverIP="192.168.194.140";
private int score =0;
private int edge=-1;
private boolean yep =false;
public Socket connection;
private JLabel[] jl = new JLabel[65];
private JButton[] jb = new JButton[112];
public Client(String host){
	super("BoX these Dots");
	setBackground(Color.white);
	for(int i=0;i<=56;i+=8)
	{
		for(int j=i;j<(8+i);j++)
		{
		jl[j] = new JLabel("O");
		jl[j].setSize(40, 40);
		jl[j].setLocation(60+(j%8)*40,60+(j/8)*40);
		jl[j].setVisible(true);
		add(jl[j]);
		if(j<56)
		 {   
			 jb[56+j] = new JButton();
			 jb[56+j].setBackground(Color.white);
			 jb[56+j].setBorderPainted(false);
			 jb[56+j].setSize(20,30);
			 jb[56+j].addActionListener(this);
			 jb[56+j].setLocation(55+(j%8)*40,85+(j/8)*40 );
			 add(jb[56+j]);
		 }
		if(j!=(8+i-1))
		{
			 jb[j-i/8] = new JButton();
			 jb[j-i/8].setBackground(Color.white);
			 jb[j-i/8].addActionListener(this);
			 jb[j-i/8].setBorderPainted(false);
			 jb[j-i/8].setSize(25,20);
			 jb[j-i/8].setLocation(75+(j%8)*40,70+(j/8)*40 );
			 add(jb[j-i/8]);
		}
		if(j==63)
		{
			jl[64] = new JLabel();
			jl[64].setSize(40, 40);
			jl[64].setLocation(60+(64%8)*40,60+(64/8)*40);
			jl[64].setVisible(true);
			add(jl[64]);
		}
		}
	}
	
    sc1 = new JLabel("PLAYER 1: 0");
    sc1.setBackground(Color.white);
    sc1.setSize(200, 90);
    sc1.setLocation(0,-30);
    sc1.setVisible(true);
    add(sc1);
    sc2 = new JLabel("PLAYER 2: 0");
    sc2.setBackground(Color.white);
    sc2.setSize(200, 90);
    sc2.setLocation(300, -30);
    sc2.setVisible(true);
    add(sc2);
    index = new JLabel(" PLAYER 1 ");
    index.setBackground(Color.white);
    index.setSize(200, 90);
    index.setLocation(180, 0);
    index.setVisible(true);
    add(index);
	    chatWindow=new JTextArea();
	    chatWindow.setEditable(false);
	    add(new JScrollPane(chatWindow), BorderLayout.CENTER);
	    setSize(455,435);
	    setVisible(true);
		
}


public void startRunning(){
	try{
		connectToServer();
		setupStreams();
		whilePlaying();
	}catch(EOFException eofException){

	}catch(IOException ioException){
		ioException.printStackTrace();
	}finally{
		closeCrap();
	}
}

public void connectToServer() throws IOException{
	connection = new Socket(InetAddress.getByName(serverIP),6789);
}

public void setupStreams() throws IOException{
	output = new ObjectOutputStream(connection.getOutputStream());
	output.flush();
	input= new ObjectInputStream(connection.getInputStream());
}

public void whilePlaying() throws IOException{
	edge=-1;
	int j=-1;
	while(true)
	{
		try
		{
			j = (int) input.readObject();
			sc2.setText("PLAYER 2: "+String.valueOf(j/1000));
			j=j%1000;
			jb[j].setBackground(Color.red);
				 jb[j].setBorderPainted(false);
				 Dimension d= jb[j].getSize();
				Point p=new Point();
				 if(d.width==20)
				 {
				 jb[j].setSize(1,30);
				 p=jb[j].getLocation();
				 jb[j].setLocation(p.x+10,p.y);
				 yep=true;
				 }
				 
				 if(d.height==20)
				 {
					jb[j].setSize(25,1);
					p = jb[j].getLocation();
					jb[j].setLocation(p.x,p.y+10);
					yep=true;
				 }
				 if(j>56)
				 {
					 g.addEdge(j-56,j-56+8);
				 }
				 if(j<56)
				 {
					 g.addEdge(j+j/7, j+j/7+1);
				 }
				 ableToType(yep);

		}
		catch(ClassNotFoundException classNotfoundException){
			}
	}
		
}

public void closeCrap(){
	ableToType(false);
	try{
		output.close();
		input.close();
		connection.close();
	}catch(IOException ioException){
		ioException.printStackTrace();
	}
}

public void sendMessage(int message){
	try{
		output.writeObject(message);
		output.flush();
	}catch(IOException ioException){
		chatWindow.append("\n something messed up!");
	}
}
 public void showMessages(final String m){
 	SwingUtilities.invokeLater(
 		new Runnable(){
 			public void run(){
 				chatWindow.append(m);
 			}
 		}
 		);
 }
  
 public void ableToType(final boolean tof){
 	SwingUtilities.invokeLater(
 		new Runnable(){
 			public void run(){
 				edge =0;
 				for(int i=0;i<112;i++)
 					jb[i].setEnabled(tof);
 			}
 		}
 		);

 }
 
 public static void main(String args[])
 {
	 Client c=new Client("me");
	 c.setResizable(true);
	 c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 c.startRunning();
 }

 Graph g = new Graph(64);
 
@Override
public void actionPerformed(ActionEvent e) {
	for(int j=0;j<112;j++)
	{
    		if(e.getSource()==jb[j])
    		{
    			jb[j].setBackground(Color.black);
   			 jb[j].setBorderPainted(false);
   			 Dimension d= jb[j].getSize();
   			Point p=new Point();
   			 if(d.width==20)
   			 {
   			 jb[j].setSize(1,30);
   			 p=jb[j].getLocation();
   			 jb[j].setLocation(p.x+10,p.y);
   			 }
   			 
   			 if(d.height==20)
   			 {
   				jb[j].setSize(25,1);
   				p = jb[j].getLocation();
   				jb[j].setLocation(p.x,p.y+10);
   			 }
   			 if(j>56)
   			 {
   				 g.addEdge(j-56,j-56+8);
   				score += (g.answer(j-56, j-56+8)).intValue();
   				System.out.println(String.valueOf(j-56)+" "+String.valueOf(j-56+8));
   			 }
   			 if(j<56)
   			 {
   				 g.addEdge(j+j/7, j+j/7+1);
   				score += (g.answer(j+j/7, j+j/7+1)).intValue();
   				System.out.println(String.valueOf(j+j/7)+" "+String.valueOf(j+j/7+1));
   			 }
   			 edge = j;
   			 sc1.setText("PLAYER 1: "+String.valueOf(score));
   			 sendMessage(score*1000+edge);
   			 yep=false;
   			 ableToType(yep);
    		}
    	}
    }
	
}