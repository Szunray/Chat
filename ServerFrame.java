import java.awt.*;
import java.awt.font.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerFrame {
    public static int thread_count=0;
    public static int running_threads=0;
    public static String dialog="";
    public static ArrayList<Socket>sockets;
    public static ArrayList<ConnectionToClient>clients;
    public static String msgHist="Start :";
    static ArrayList<PrintWriter>outStreams;
    static PrintWriter[] list = new PrintWriter [300];
    public static void main(String args[]) {
         
        try {
            JTextArea theTextArea=new JTextArea(5,40); 
            Font the_font = new Font("SansSerif", Font.BOLD, 20);
            theTextArea.setFont(the_font);
            JFrame frame = new JFrame("Chat Server Frame");
            JButton button = new JButton("Request Port");
            button.addActionListener((ev)->{try_port(theTextArea);});
            Container contentPane = frame.getContentPane();
            contentPane.add(theTextArea, BorderLayout.CENTER);
            contentPane.add(button, BorderLayout.SOUTH);
            frame.setSize(500, 200);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); ;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public static synchronized void sync(int threadNum){
    	list[threadNum].flush();
    	list[threadNum].println(msgHist);
    }
    public static synchronized void Send(int threadNum, String msg){
    	//msgHist+=msg;
    	//list[threadNum].flush();
    	list[threadNum].println(msgHist);
    	list[threadNum].flush();
    	//list[threadNum].println("Thread "+threadNum+" Heard you Say: " + msg);
    }
    public static synchronized void sendAll(String msg){
    	for(int x=0; x<running_threads;x++)
    		Send(x,msg);
    	System.out.println("msgHist : " + msgHist);
    }
    public static void try_port(JTextArea aTextArea){
        ServerSocket server;
        int portnumber=Integer.parseInt(aTextArea.getText().trim());
        try {
            server = new ServerSocket(portnumber); 
        } catch (Exception e) {
            ServerFrame.dialog=ServerFrame.dialog+"\nPort "+aTextArea.getText().trim()+ " may already be in use.";
            aTextArea.setText(ServerFrame.dialog);
            aTextArea.repaint();
            return;
        }
        ChatThread chat = new ChatThread(server); //one per port
        Thread t = new Thread(chat); 
        t.start(); 
        ServerFrame.dialog=ServerFrame.dialog+"\nPort "+aTextArea.getText().trim()+ " is open and listening.";
        aTextArea.setText(ServerFrame.dialog);
        aTextArea.repaint();
    }
}




