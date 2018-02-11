import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static void main(String[] args)
    {
        Server server = new Server(); 
        try(ServerSocket serverSocket = new ServerSocket(5000))
        {
            while(true)
            {
                server.addClient(serverSocket.accept());
            }
        }
        catch (IOException e)
        {
            System.out.println("Server ERROR : "+e.getMessage());
        }

    }
    public void addClient(Socket socket)
    {
        new Thread(new ClientThread(socket)).start();
    }

    class ClientThread implements Runnable
    {
        private Socket socket;

        public  ClientThread(Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run()
        {
            String name,clientMessage;
            try
            {
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                name = inputStream.readLine();
                System.out.println(name+" CONNECTED");
                while (true)
                {
                    clientMessage = inputStream.readLine();
                    if (clientMessage.equals("exit"))
                    {
                        break;
                    }
                    System.out.println(name+" : "+clientMessage);

                }
                System.out.println(name+" DISCONNECTED");
                inputStream.close();
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());

            }
            finally
            {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
    }


}
