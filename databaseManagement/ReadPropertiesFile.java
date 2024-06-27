package databaseManagement;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class ReadPropertiesFile {
	private String propertyString;
	private String credentialsOne;
	private String credentialsTwo;
	private String credentialsThree;
	
	public ReadPropertiesFile() {
		propertyString = "";
		credentialsOne = "";
		credentialsTwo = "";
		credentialsThree = "";
	}
	
	public void readPropertiesFile() {
		File myObj = new File("properties.txt");
		try {
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()){
				String data = myReader.nextLine();
				propertyString = data;
			}
			myReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("An Error Has occured");
			e.printStackTrace();
		}
		String[] arrayOfStr = propertyString.split(",", 3);	
		
		credentialsOne = arrayOfStr[0];
		credentialsTwo = arrayOfStr[1];
		credentialsThree = arrayOfStr[2];
	}
	
	public String getProperties() {
		return propertyString;
	}
	
	public String getCredentialsOne() {
		return credentialsOne;
	}
	
	public String getCredentialsTwo() {
		return credentialsTwo;
	}
	
	public String getCredentialsThree() {
		return credentialsThree;
	}
}
