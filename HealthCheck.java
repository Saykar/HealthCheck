import java.io.*;
import java.net.*;
import java.util.Scanner;


public class HealthCheck {

	public static void main(String[] args) {
		
		//int ResponseCode = checkHTTPHealth("http://127.0.0.1:9696/ServerProcesses/GetBooksByAuthorEndpoint");
		//System.out.println(ResponseCode);
		
		int ret = contentVerification("https://www.google.com/finance", "Markets");
		System.out.println(ret);
		
		int r = sampleRequest("http://127.0.0.1:9696/ServerProcesses/GetBooksByAuthorEndpoint", "C:\\Users\\gsaykar\\Desktop\\dump\\SampleRequest.txt");
		System.out.println(r);
	}
	
	 public static int checkHTTPHealth(String strURL) {

		 try {
				  int code = 0;
				 
				 URL url = new URL (strURL);
				 URLConnection connection = url.openConnection();
				 
				 HttpURLConnection httpConnection = (HttpURLConnection) connection;
				 httpConnection.connect();
				 code = httpConnection.getResponseCode();	

				  return code;

		 } catch (RuntimeException e) {
			 throw e;
		 } 
		 catch (Exception e) {
			 throw new RuntimeException(e);
		 }

	 }
  
  public static int checkTCPecho(String host, int port) {
	  try
		{
			Socket socket = new Socket(host, port);
			BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
			String line = "TCP_test_msg";
			
				socketWriter.println(line);
				line = socketReader.readLine();
				if ( line != null )
				{
					if(line.endsWith("TCP_test_msg"))
					{
						return 1;
					}
					else{
						return 0;
					}
				}
				
			return 0;
		}
		catch (RuntimeException e) {
			 throw e;
		} 
		catch (Exception e) {
			 throw new RuntimeException(e);
		}
	
  }
	//checks if keyword is present in response
	public static int contentVerification(String strURL, String keyword) {
		
		String response = executeGet(strURL);
		
		if (response.toLowerCase().contains(keyword.toLowerCase())){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	public static int sampleRequest(String strURL, String filepath) {
		try{
			//String data = fileToString(filepath);
			String data = new Scanner(new File(filepath)).useDelimiter("\\Z").next();
			//System.out.println(data);
			int ResponseCode = executePost(strURL, data);
			
			return ResponseCode;
		}
		catch (RuntimeException e) {
			 throw e;
		} 
		catch (Exception e) {
			 throw new RuntimeException(e);
		}
	}

	
	public static String executeGet(String targetURL) {
	    URL url;
	    HttpURLConnection connection = null;
	    //int ret = 0;
	    try {
	      //Create connection
	      url = new URL(targetURL);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("GET");
	      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");				 				
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
	      wr.flush ();
	      wr.close ();

	      //Get Response
	      
	     // ret = connection.getResponseCode();  	 
	     // System.out.println(connection.getContentType());

	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      //System.out.println(response.toString());
	      return response.toString();

	    } 
	    catch (RuntimeException e) {
			 throw e;
		} 
		catch (Exception e) {
			 throw new RuntimeException(e);
		}	    
	    finally {
	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	  }

	public static int executePost(String targetURL, String data) {
	    URL url;
	    HttpURLConnection connection = null;
	    int ret;
	    
	    try {
	      //Create connection
	      url = new URL(targetURL);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");				 				
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);
	      connection.setRequestProperty("Content-Length", "" + Integer.toString(data.getBytes().length));
	      connection.setRequestProperty("SOAPAction", "/GetBooksByAuthorEndpoint");
	          
	      //Send request
	      DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
	      wr.writeBytes (data);
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      ret = connection.getResponseCode();  	      
	      return ret;
	    } 
	    catch (RuntimeException e) {
			 throw e;
		} 
		catch (Exception e) {
			 throw new RuntimeException(e);
		}
	    finally {

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	  }
}
