package clueGame;

import java.io.FileWriter;
import java.io.IOException;

/* DESCRIPTION
 * This File is for the Bad Formation on the input Files
 * Writes all errors to the ErrorLog.txt file
 */
public class BadConfigFormatException extends RuntimeException {
	
	public BadConfigFormatException(String message) 
	{ // Constructor 
		super(message);
		createLog(message);
	}

	public void createLog(String message) 
	{ // Appends the error to the errorLog.txt
		try {
			FileWriter fw = new FileWriter("ErrorLog.txt", true);
			fw.write(message + "\n");
			fw.close();
		} catch (IOException e) {
			System.err.println("IOException cannot open file: ErrorLog.txt");
		}
	}

}
