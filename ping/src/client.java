import java.net.*;
import java.util.*;

import common.*;

@SuppressWarnings("unused")
class Client
{
	public static void main( String args[] )
	{
		System.out.println( "Client" );
		final String host = "localhost";           // Host name 
		final int    port = 1987;                 // Port used
    
		NetStringWriter out;                     // String output
		NetStringReader in;                      // String input
		double delay = 0;
    
		while (true) 
		{
			try
			{
				Socket socket = new Socket( host, port );// Socket
				long start = System.nanoTime();
				out = new NetTCPWriter( socket );        // Output 
				in  = new NetTCPReader( socket );        // Input 
				long stop = System.nanoTime();
				
				if (delay < 0) {delay = 0;}
				Thread.sleep((long) delay);
				
				double myPing = ( double ) (stop-start)/2000000 ; //ms
    		
    		
				out.put( "" + myPing );                  //   to Server 
				String response = in.get();              //   Response 
				if ( response == null ) 
				{
					DEBUG.trace("NullResponse! [%s]", response);
				}
				System.out.printf("Server Ping is [%s]. My Ping  is [%s] \n",
									response, myPing );
    		
				delay =  Double.parseDouble(response) - myPing;
				System.out.printf("Ping Difference is [%s]. \n", delay );
				in.close();                              // Close stream
				out.close();                              // Close stream
				Thread.sleep(5000);
    		
			}
    
			catch ( Exception e )
			{
				DEBUG.error("Error:\n%s", e.getMessage() );
			}
		}  
  }

}

