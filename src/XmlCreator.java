/**
 * 
 */

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		
		//P.1 Starts //   Country Codes [AU-036 ,CA 124 ,SN 702 ,UK 826 ,US 840]
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String TodaysDate = dateFormat.format(date);
		
		
		ISBNVO isbnvo = dc.getisbn10andisbn13(isbn);
		
		if (isbnvo.getCounntryCode().equals("036")){
			ElementNode recordReference = (ElementNode) xmlDoc.createElement("a001");
			recordReference.appendChild(xmlDoc.createTextNode(isbnvo.getIsbn13()));
			product.appendChild(recordReference);
		}
		if (isbnvo.getCounntryCode().equals("124")){
			ElementNode recordReference = (ElementNode) xmlDoc.createElement("a001");
			recordReference.appendChild(xmlDoc.createTextNode(isbnvo.getIsbn13()));
			product.appendChild(recordReference);
		}
		if (isbnvo.getCounntryCode().equals("702")){
			ElementNode recordReference = (ElementNode) xmlDoc.createElement("a001");
			recordReference.appendChild(xmlDoc.createTextNode(TodaysDate + isbnvo.getIsbn()));
			product.appendChild(recordReference);
		}
		if (isbnvo.getCounntryCode().equals("826")){
			ElementNode recordReference = (ElementNode) xmlDoc.createElement("a001");
			recordReference.appendChild(xmlDoc.createTextNode(TodaysDate + isbnvo.getIsbn()));
			product.appendChild(recordReference);
		}
		if (isbnvo.getCounntryCode().equals("840")){
			ElementNode recordReference = (ElementNode) xmlDoc.createElement("a001");
			recordReference.appendChild(xmlDoc.createTextNode(isbnvo.getIsbn13()));
			product.appendChild(recordReference);
		}
		
		if(isbnvo.getStatusCode().equals("E") || isbnvo.getStatusCode().equals("F") || isbnvo.getStatusCode().equals("I"))
		{
			ElementNode notificationType = (ElementNode) xmlDoc.createElement("a002");
			notificationType.appendChild(xmlDoc.createTextNode("02"));
			product.appendChild(notificationType);
		}
		else{
			ElementNode notificationType = (ElementNode) xmlDoc.createElement("a002");
			notificationType.appendChild(xmlDoc.createTextNode("03"));
			product.appendChild(notificationType);
		}
		
		//P.1 End //
		
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
			 //System.out.println("Related ISBN is  -->" + rISBN);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return xmlDoc;
	}
	
	

}
