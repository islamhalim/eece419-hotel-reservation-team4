package com.c7b6.eece411.A1;
import java.io.*;
import java.net.*;


import java.net.Socket;


public class Assignment1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	
		
		
////		 TODO Auto-generated method stub
		int port = Integer.parseInt(args[1]);
		int studentNumber = Integer.parseInt(args[2]);
		String server = args[0];
		
//		int port = 5627;
//		int studentNumber = 22083059;
//		String server = "reala.ece.ubc.ca";

		try{
			InetAddress Address = InetAddress.getByName(server);

	
			String sIP = Address.getHostAddress();
			
			System.out.println("Connecting To: " + server +" == " + sIP + "...");

			Socket s = new Socket(sIP, port);
			System.out.println("Connection Successful...");
			System.out.println("Sending ID:" + studentNumber);
			

			InputStream in = s.getInputStream();
			OutputStream out = s.getOutputStream();
			ByteOrder.int2leb(studentNumber, out);
			
			int temptotal = 0;
			int total = 0;
			byte[] response = new byte[70];
			
			while((temptotal = in.read(response, total, response.length - total)) != -1)
				{
					total += temptotal;
				}	
			
			int messLength = (int)response[0];
			int codeLength = (int)response[8];
			
			System.out.println("Message Length: " + messLength);
			System.out.println("Code Length: " + codeLength);
			
			byte[] secretCode = new byte[codeLength];
			
			for(int i=0; i<codeLength; i++)
			{
				secretCode[i] = response[messLength-codeLength+i];
			}
			
			System.out.println();
//			System.out.println("Top Secret Response: " + StringUtils.byteArrayToHexString(response) + "\n");
			System.out.println("Top Secret Response: " + StringUtils.byteArrayToHexString(secretCode) + "\n");
			s.close();
			System.out.println("Disconnected From Server...Bye!");
		
		}
		catch(UnknownHostException UHex)
		{
			System.out.println("The Host "+server+" is unknown");
		}
		
		catch(IOException IOex)
		{
			System.out.println("There was an exception creating the connection");
		}
		
		
	}


}

