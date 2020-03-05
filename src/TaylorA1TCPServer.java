import java.io.*;
import java.net.*;

class TaylorA1TCPServer {

    public static void main(String argv[]) throws Exception
    {
        String req;
        String ipaddress;
        long delay;

        ServerSocket welcomeSocket = new ServerSocket(20120);

        while(true) {

            Socket connectionSocket = welcomeSocket.accept();

            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream  outToClient =
                    new DataOutputStream(connectionSocket.getOutputStream());

            // Sends the message connected to Client when received request
            req = inFromClient.readLine();
            if(req.equals("request")) {
                outToClient.writeBytes("connected" + '\n');
                System.out.println("connected");
            }

            // Listens for the next two messages from the client
            ipaddress = inFromClient.readLine();
            delay = Long.parseLong(inFromClient.readLine());

            // Prints out the collected information.
            System.out.println("IP address: " + ipaddress + '\t' + "delay: " + delay);
        }
    }
}