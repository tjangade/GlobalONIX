
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

//import org.apache.log4j.Logger;

import com.sun.xml.tree.XmlDocument;


public class FileReaderAndWriter {

	/**
	 * @param args
	 */
	private static final Logger LOGGER = Logger.getLogger(FileReaderAndWriter.class);
	
	public  Set<String> getIsbnsFromFile() throws IOException 
	{
		
		Set<String> isbns =new HashSet<String>();
		BufferedReader reader;
	
			reader = new BufferedReader(new FileReader(
					"C:/Users/tjangade/develop/workspace1/GlobalONIX/globalONIX.txt"));
			
			String line = "";
			while (line != null) {
				line = reader.readLine(); // read next line
				if(line != null)
				isbns.add(line.trim());
			}
			reader.close();
			isbns.remove(null);
		
		return isbns;
	
	}
	
	public void onixFileWriter() throws IOException
	{
		String name ="14.xml";
		File f = new File(name);
		
      	FileWriter to = new FileWriter(f);
      	XmlDocument   xmlDoc = new XmlDocument ();
      	XmlCreator xmlc=new XmlCreator();
      	Writer  out = new OutputStreamWriter (System.out);
      	
      	try{
            System.out.println("working");
            
           }catch (Exception e) {
               System .out.println("Couldn't read file2");
           }
          	int i = xmlDoc.getLength();
          	System.out.println("name =  "+name);
          	out.write ("No tree exists yet\n");
          	xmlDoc.write (to);
          	to.write ("\n");
          	to.close();
           }
	        	  

}
