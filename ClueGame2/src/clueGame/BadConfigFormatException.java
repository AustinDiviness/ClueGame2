package clueGame;

import java.io.FileWriter;
import java.io.IOException;

public class BadConfigFormatException extends RuntimeException {
	
	public BadConfigFormatException(String message) { 
		super(message);
		createLog(message);
	}

	public void createLog(String message) { 
		// Appends the error to the errorLog.txt
		try {
			FileWriter fw = new FileWriter("ErrorLog.txt", true);
			fw.write(message + "\n");
			fw.close();
		} 
		catch (IOException e) {
			System.err.println("IOException cannot open file: ErrorLog.txt");
		}
	}
} // end class bracket
