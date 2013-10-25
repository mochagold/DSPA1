import java.io.*;
import java.net.*;

import org.json.simple.JSONObject; 

public class Client
{
    public static void main( String args[] ) throws IOException
    {
    	BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
    	Socket sock = null; //Socket
        DataInputStream is; //read
        DataOutputStream os; //write
 
        String input_str = "", recv_str = ""; //Text message from client
        JSONObject obj = new JSONObject();
 
        try
        {
            sock = new Socket( "127.0.0.1", 20000 );
            is = new DataInputStream( sock.getInputStream() );
            os = new DataOutputStream( sock.getOutputStream() );
                    
            System.out.println( "=========Connection Complete=========" );
            
            while( true )
            {
                System.out.printf( "%s> ", System.getProperty("user.dir") );
                input_str = in.readLine();
                //사용자로부터 입력받은 명령어를 JSON으로 패킹
                String[] objstr = input_str.split(" ");
                obj.put("name", objstr[0]);
                obj.put("method", objstr[1]);
                
                try
                {
                    os.writeUTF( obj.toString() );
                }
                catch( Exception e )
                {
                }
                try
                {
                    recv_str = is.readUTF();
                }
                catch( Exception e )
                { 
                }
                System.out.println( "Response: " + recv_str );
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }
}
