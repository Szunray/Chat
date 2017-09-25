import java.awt.*;
import java.awt.font.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.TimerTask;

public class ClientFrame {
    static Socket this_socket;
    static  PrintWriter    out;
    static BufferedReader  in;
    public static void main(String args[]) {
        try {
            JTextField theMessage=new JTextField();
            Font the_font = new Font("SansSerif", Font.BOLD, 20);
            theMessage.setFont(the_font);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int ChatPort=Integer.parseInt(args[0]);
            InetAddress ip = InetAddress.getByName("localhost"); 
            //127.0.0.1 ,the server's loopback IP
            //byte[] ipAddr = new byte[] { 127, 0, 0, 1 };
            //InetAddress ip = InetAddress.getByAddress(ipAddr);
            
            try {
                this_socket = new Socket(ip, ChatPort);
            } catch (Exception e1) {
                System.out.println("Port "+ChatPort+ " not Available");
                return;
            }
            out =new PrintWriter(this_socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(this_socket.getInputStream()));
            //
           /* String line;
            if((line=in.readLine())!=null)
            	theMessage.setText(line);*/
            //--------------------------
            System.out.println("The Inet address is "+ip+
                               "\n listening on port "+this_socket.getLocalPort()+
                               "\n sending on port "+this_socket.getPort()+"\n\n");
            JFrame frame = new JFrame("Chat Frame");
            JButton button = new JButton("Ask Chat Server");
            button.addActionListener((ev)->{send_receive(theMessage);});
            Container contentPane = frame.getContentPane();
            contentPane.add(theMessage, BorderLayout.CENTER);
            contentPane.add(button, BorderLayout.SOUTH);
            frame.setSize(500, 200);
            frame.setVisible(true);
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); ;
            
            frame.addWindowListener(new WindowAdapter() {
                                        public void windowClosing(WindowEvent e) {
                                            try {
                                                this_socket.close();
                                                System.out.println("Client Closing");
                                                System.exit(0);
                                            } catch (IOException ioe) {
                                                System.out.println("Socket did not close properly.");
                                            }
                                        }
                                    });    
           
            	//hear(theMessage);
            update(theMessage);
            java.util.Timer t = new java.util.Timer();
            t.schedule(new TimerTask() {

                        @Override
                        public void run() {
                        	
                            System.out.println("This will run every 5 seconds");
                            update(theMessage);
                            
                        }
                    }, 10000, 10000);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    public static void send_receive(JTextField the_message){
        ClientFrame.out.println(the_message.getText());
        ClientFrame.out.flush();
        String the_response="";
        try {
            while ((the_response=ClientFrame.in.readLine())==null) {
            	
                Thread.sleep(500);
            }
            System.out.println(the_response);
            the_message.setText(the_response);
            the_message.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void update(JTextField the_message){
    	
        ClientFrame.out.println("FetchMsgHistory667");
        ClientFrame.out.flush();
        String the_response="";
        
        try {
            while ((the_response=ClientFrame.in.readLine())==null) {
            
                Thread.sleep(500);
            }
           
            
            System.out.println(the_response);
            the_message.setText(the_response);
            the_message.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

