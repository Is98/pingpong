import java.net.*;
import java.util.*;

import common.*;

class Client
{
  public static void main( String args[] )
  {
    System.out.println( "Client" );
    final String host = "localhost";           // Host name 
    final int    port = 50000;                 // Port used 

    try
    {
      NetStringWriter out;                     // String output
      NetStringReader in;                      // String input

      Socket socket = new Socket( host, port );// Socket 

      out = new NetTCPWriter( socket );        // Output 
      in  = new NetTCPReader( socket );        // Input 

      for (int i= 0; i<args.length; i++)       // Send messages 
      {
        out.put( args[i] );                    //   to Server 
        String response = in.get();            //   Response 
        if ( response == null ) break;         // Failure 
        System.out.printf("Length of [%s] is %s\n",
                           args[i], response );
      }
      out.close();                             // Close stream
    }
    catch ( Exception e )
    {
      DEBUG.error("Error:\n%s", e.getMessage() );
    }
  }
}
