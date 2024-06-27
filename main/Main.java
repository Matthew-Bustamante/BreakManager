package main;
import databaseManagement. * ;
public class Main {
	// Main Method
	public static void main(String[] args) {
		System.out.println("Test");
		DatabaseConnection dbConnect = new DatabaseConnection();
		ReadPropertiesFile properties = new ReadPropertiesFile();
		
		properties.readPropertiesFile();
		
		String url = properties.getCredentialsOne();
		String username = properties.getCredentialsTwo();
		String password = properties.getCredentialsThree();
		dbConnect.setConnection(url,username,password);
		
		dbConnect.closeConnection();
	}
	
}
