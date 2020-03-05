import java.io.*;
import java.net.*;
import java.util.Scanner;

class TaylorA1TCPClient {

    public static void main(String argv[]) throws Exception
    {
        String inputLine;
        String connection;

        // Makes a TCP connection to Server listening on port 20120
        Socket clientSocket = new Socket("localhost", 20120);

        // Creates output to stream to send to Server
        DataOutputStream outToServer =
                new DataOutputStream(clientSocket.getOutputStream());

        // Creates input stream to listen to messages from the Server
        BufferedReader inFromServer =
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // Sends the message "request" to Server
        outToServer.writeBytes("request" + '\n');

        // Checks whether Client made a connection with the Server
        connection = inFromServer.readLine();
        if(connection.equals("connected"))
            System.out.println("local host connected");
        else
            System.out.println("Error establishing a connection. Please try again.");

        // Asks user to enter a web server name as a String
        System.out.println("Enter a web server name (e.g. 'www.lbl.gov'): ");
        Scanner scan = new Scanner(System.in);
        String obj = scan.nextLine();
        URL inFromUser = new URL("https://" + obj);

        // Starts timer
        long t1 = System.currentTimeMillis();

        // Prints the IP address of the web server
        InetAddress ip = InetAddress.getByName((inFromUser).getHost());
        System.out.println("Public IP Address of: " + ip);

        // Prints each line of page text received form the web server
        HttpURLConnection con = (HttpURLConnection) inFromUser.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent","Mozilla/5.0");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);

        // Ends timer
        long t2 = System.currentTimeMillis();

        // Calculates the value of T(elapsed) = t2 - t1
        long elapsed = t2 - t1;
        System.out.println("delay= " + elapsed);

        // Sends the web server's IP address to the Server
        outToServer.writeBytes(ip.toString() + '\n');

        // Sends the time elapsed to the Server
        outToServer.writeBytes(Long.toString(elapsed) + '\n');

        // Prints out the message done
        System.out.println("done");

        // Closes the connection
        clientSocket.close();
    }
}