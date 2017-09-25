import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;

public class ChatThread implements Runnable {
    private Socket socket;
    private ServerSocket s_socket;
    private int thread_number;

    public ChatThread(ServerSocket ss)
    {
        s_socket = ss;
        thread_number=++ServerFrame.thread_count;
    }

    @Override
    public void run() 
    {
        try {
            socket=s_socket.accept();
            //ServerFrame.sockets.add(socket);
            
            
            Scanner in = new Scanner(socket.getInputStream()); //From Client
            PrintWriter out = new PrintWriter(socket.getOutputStream()); //To Client
            ServerFrame.list[thread_number-1]=out;
            ServerFrame.running_threads++;
            
            while (true) {//While ServerFrame is running
                if (in.hasNext()) {
                    String input = in.nextLine(); 
                    if (input.equals("FetchMsgHistory667")){
                    	//input="";
                    	ServerFrame.Send(thread_number-1,input);
                    }
                    else{
                    //if (input)
                    // System.out.println("Client at " +socket.toString()+" Said: " + input);
                    parseSocketData(s_socket,input); 
                    //ServerFrame.list[thread_number-1].println("Thread "+thread_number+" Heard you Say: " + input);
                    ServerFrame.msgHist+=input;
                    ServerFrame.sync(thread_number-1);
                    out.flush();
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
    public void parseSocketData(ServerSocket s,String inp){
        String[] thedata=socket.toString().split(",");
        System.out.println("Client at:");
        System.out.println(thedata[0].substring(7));
        System.out.println(thedata[1]);
        System.out.println(thedata[2].substring(0,(thedata[2].length()-1)));
        System.out.println("Said "+inp);
    }

}