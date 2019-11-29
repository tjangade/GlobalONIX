/**
 * 
 */

import java.sql.Connection;

import com.sun.xml.tree.ElementNode;
import com.sun.xml.tree.XmlDocument;

/**
 * @author tjangade
 *
 */
public class XmlCreator {
	
	private static Connection con=null;
	
	public XmlDocument xmlCreator(String isbn)
	{
		 
		XmlDocument xmlDoc = new XmlDocument ();
		DatabaseConnector dc = new DatabaseConnector();
		 
		
		ElementNode product = (ElementNode) xmlDoc.createElement("product");
		xmlDoc.appendChild(product);
		//String isbn10 = dc.selectIsbn10forIsbn13(isbn13);
		
		//P.6 Starts //
		
		String[] titleSubTitle = dc.getTitleSubTitle(isbn);
		 
		ElementNode title = (ElementNode) xmlDoc.createElement("titledetail");
		
		ElementNode titletype = (ElementNode) xmlDoc.createElement("b202");
		titletype.appendChild(xmlDoc.createTextNode("01"));
		title.appendChild(titletype);
		
		ElementNode titleelement = (ElementNode) xmlDoc.createElement("titleelement");
		
		if (!titleSubTitle[0].equals("null") && !titleSubTitle[0].isEmpty()){
			
			ElementNode titletext = (ElementNode) xmlDoc.createElement("b203");
			titletext.appendChild(xmlDoc.createTextNode(titleSubTitle[0]));
			titleelement.appendChild(titletext);
		}
		
		if (!titleSubTitle[1].equals("null") && !titleSubTitle[1].isEmpty()){
			
			ElementNode subtitletext = (ElementNode) xmlDoc.createElement("b209");
			subtitletext.appendChild(xmlDoc.createTextNode(titleSubTitle[1]));
			titleelement.appendChild(subtitletext);
		}
		
		title.appendChild(titleelement);
		product.appendChild(title);
		 
		//P.6 End //
		 
		 
		 try {
			 
			 String rISBN = dc.getrelatedISBN(isbn);
			 System.out.println("Related ISBN is  -->" + rISBN);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return xmlDoc;
	}
	
	

}
