package databaseManagement;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
/**
 * ReadPropertiesFile is a class that reads a properties file that contains sensitive information
 * @author Matthew-Bustamante
 *
 */
public class ReadPropertiesFile {
	private String propertyString;
	private String credentialsOne;
	private String credentialsTwo;
	private String credentialsThree;
	
	/**
	 * Constructor intilalizing variables
	 */
	public ReadPropertiesFile() {
		propertyString = "";
		credentialsOne = "";
		credentialsTwo = "";
		credentialsThree = "";
	}
	
	/**
	 * readPropertiesFile method reads a file in project directory called "properties.txt"
	 * The method will then read that file and store the data of that file into variables
	 */
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
	
	/**
	 * returns first credentials
	 * @return string object
	 */
	public String getCredentialsOne() {
		return credentialsOne;
	}
	
	/**
	 * returns second credentials
	 * @return string object
	 */
	public String getCredentialsTwo() {
		return credentialsTwo;
	}
	
	/**
	 * returns third credentials
	 * @return string object
	 */
	public String getCredentialsThree() {
		return credentialsThree;
	}
}
