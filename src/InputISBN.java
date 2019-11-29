/**
 * 
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;

import com.sun.xml.tree.XmlDocument;

/**
 * @author tjangade
 *
 */
public class InputISBN {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws TransformerConfigurationException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, TransformerException {
		
		XmlDocument   xmlDoc = new XmlDocument ();
		Document doc = null;
		DatabaseConnector dc = new DatabaseConnector();
		
		FileReaderAndWriter frw = new FileReaderAndWriter();
		DOMSource source = null;
		
		File f = new File("14.xml");
       	FileWriter to = new FileWriter(f);
    	Writer  out = new OutputStreamWriter (System.out);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
		
		XmlCreator xmlc=new XmlCreator();
		//xmlc.createHeader(new File("header.txt"));
		
		Set<String> isbn=frw.getIsbnsFromFile();
		List<String> finalIsbns = new ArrayList<String>();
		int count=0;
		
		for(String i:isbn){
			
			String refinedIsbn=i.replaceAll("-", "").trim();
			if(i.contains("P"))
				continue;
			String finalIsbn=((refinedIsbn.length()==10)?dc.selectIsbn10(refinedIsbn):(refinedIsbn.length()==13)?dc.selectIsbn10forIsbn13(refinedIsbn):null);
			if(finalIsbn!=null)
			{
				finalIsbns.add(finalIsbn);
	 			count++;
				out.write (count+"\n");
				System.out.println(i+" is a valid ISBN");
			}else
			{
				System.out.println(i+" is invalid ISBN or does not have adequate data");
			}
		}
		
		dc.closeConnection();
		
		for(String s:finalIsbns)
		{
			try {
				xmlDoc=xmlc.xmlCreator(s);
				xmlDoc.write (to);
		       	to.write ("\n");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
	}

}
