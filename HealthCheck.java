/**
 *@author gauri saykar
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class HealthCheck {

	public static void main(String[] args) {

		int ResponseCode = checkHTTPHealth("http://blogsearch.google.co.id/ping/RPC2");
		System.out.println(ResponseCode);

		int ret = contentVerification("https://www.google.com/finance", "Markets");
		System.out.println("contentVerification check: " + ret);

		int r = sampleRequest("http://localhost:7909/Products/Operations/", "C:\\Users\\gsaykar\\Desktop\\dump\\SampleRequest.txt");
		System.out.println("sampleRequest return code: " + r);
	}

	public static int sampleSOAPRequest(String strURL, String filepath, String SOAPaction) {
		try{
			//String data = fileToString(filepath);
			String data = new Scanner(new File(filepath)).useDelimiter("\\Z").next();
			int ResponseCode = executeSOAP_Post(strURL, data, SOAPaction);

			return ResponseCode;
		}
		catch (RuntimeException e) {
			 throw e;
		}
		catch (Exception e) {
			 throw new RuntimeException(e);
		}
	}

	public static int executeSOAP_Post(String targetURL, String data, String SOAPaction) {
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
		      connection.setRequestProperty("SOAPAction", SOAPaction);

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

	 public static int checkHTTPHealth(String strURL) {

		 try {
			 int code = 0;

			 URL url = new URL (strURL);
			 URLConnection connection = url.openConnection();

			 HttpURLConnection httpConnection = (HttpURLConnection) connection;
			 httpConnection.setConnectTimeout(5000);
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
			System.out.println(ResponseCode);
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
	    //URLConnection connection = null;
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
	     // DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
	      //wr.flush ();
	      //wr.close ();

	      //Get Response

	     // ret = connection.getResponseCode();
	      //System.out.println(connection.getContentType());

	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer();
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      return response.toString();

	    }
	    catch(FileNotFoundException e){
	    	throw new RuntimeException(e.getMessage());
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

	      //Send request
	      DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
	      wr.writeBytes (data);
	      wr.flush ();
	      wr.close ();

	      //Get Response
	      ret = connection.getResponseCode();
	      return ret;
	    }
	    catch(FileNotFoundException e){
	    	throw new RuntimeException(e.getMessage());
	    }
	    catch (RuntimeException e) {
			 throw e;
		}
		catch (Exception e) {
			 throw new RuntimeException(e.getMessage());
		}
	    finally {

	      if(connection != null) {
	        connection.disconnect();
	      }
	    }
	  }
}
