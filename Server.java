import java.net.*;
import java.io.*;

import org.json.simple.*; 

public class Server
{
	
    public static void main( String args[] ) throws IOException
    {
        ServerSocket serv_sock;
        Socket acp_sock = null;
        DataInputStream is;
        DataOutputStream os;
        String recv_str = null;
        
        String result = "";
 
        try
        {
            serv_sock = new ServerSocket( 20000, 10 );
            while( true )
            {
                System.out.println( "Waiting for connections." );
                acp_sock = serv_sock.accept(); 
 
                is = new DataInputStream( acp_sock.getInputStream() ); 
                os = new DataOutputStream( acp_sock.getOutputStream() ); 
                System.out.println( "Client Connected: " + acp_sock.getInetAddress().getHostName() );
                System.out.println( "Waiting for response from client." );
 
                while( true )
                {
                    // 클라이언트로부터 STRING형 정보를 받는과정
                    try
                    {
                        recv_str = is.readUTF();
                    }
                    catch( Exception e )
                    {
                        // recv_str 값이 null일때 접속 종료
                        System.out.println( "======== Disconnected =======" );
                        break;
                    }
 
                    System.out.println( "Message from client : " + recv_str );
                    // /////////////////////
                    String name = new String(JSONnameParser(recv_str));
                    String method = new String(JSONmethodParser(recv_str));
                    result = ServiceCall(name, method);
                    // /////////////////////
 
                    try
                    {
                        os.writeUTF( result );
                    }
                    catch( Exception e )
                    {
 
                    }
                }
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            acp_sock.close();
        }
    }

	private static String ServiceCall(String name, String method) {

		switch(name){
		case "mkdir":
			System.out.println(OperationHandler.mkDir(method));
			return "mkdir makes complete";
		}
		return "Nothing to call";
	}
	private static String JSONmethodParser(String recv_str) {
		JSONObject obj = (JSONObject)JSONValue.parse(recv_str);
		return (String)obj.get("method");
	}

	private static String JSONnameParser(String recv_str) {
		JSONObject obj = (JSONObject)JSONValue.parse(recv_str);
		return (String)obj.get("name");
	}
}
