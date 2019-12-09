/**
 * 
 */

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.xml.tree.ElementNode;
import com.sun.xml.tree.XmlDocument;

import oracle.jdbc.driver.DBConversion;

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
		
		
		
		 try {
			 

				 
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
			
			//P.5 Starts //
			
			ISBNVO collectionIsbnvo = new ISBNVO(); 
			collectionIsbnvo = dc.getseriesSeq(isbn);
			String seriesSeq = collectionIsbnvo.getSeriesSeq();
			String seriesCode = collectionIsbnvo.getSeriesCode();
			String seriesDescription = "" + collectionIsbnvo.getSeriesDescription();
			
			workVO workvo = new workVO();
			workvo = dc.getparentID(isbn);
			String parentId = "" + workvo.getParentId();
			String editionVol = "" + workvo.getEditionVolume();
			String collectiontitle = "" + workvo.getTitle();
			
			if((!seriesSeq.equals("null") && !seriesSeq.isEmpty())  || (!parentId.equals("null") && !parentId.isEmpty()))
			{
				
				ElementNode collection = (ElementNode) xmlDoc.createElement("collection");
				
				ElementNode collectionType = (ElementNode) xmlDoc.createElement("x329");
				collectionType.appendChild(xmlDoc.createTextNode("01"));
				collection.appendChild(collectionType);
				
				
					
				ElementNode collectionIdentifier = (ElementNode) xmlDoc.createElement("collectionidentifier");
				collection.appendChild(collectionIdentifier);
				
				ElementNode collectionIdType = (ElementNode) xmlDoc.createElement("x344");
				collectionIdType.appendChild(xmlDoc.createTextNode("01"));
				collectionIdentifier.appendChild(collectionIdType);
				ElementNode collectionIdTypeName = (ElementNode) xmlDoc.createElement("b233");
				collectionIdTypeName.appendChild(xmlDoc.createTextNode("WileySeriesID"));
				collectionIdentifier.appendChild(collectionIdTypeName);
				
				if (!seriesCode.equals("null") && !seriesCode.isEmpty()){
					ElementNode collectionIdTypeValue = (ElementNode) xmlDoc.createElement("b244");
					collectionIdTypeValue.appendChild(xmlDoc.createTextNode(seriesCode));
					collectionIdentifier.appendChild(collectionIdTypeValue);
					
				}
					
				
				if (!seriesSeq.equals("null") && !seriesSeq.isEmpty()){
					
					ElementNode collectionSeq = (ElementNode) xmlDoc.createElement("collectionsequence");
					collection.appendChild(collectionSeq);
					
					ElementNode collectionSeqType = (ElementNode) xmlDoc.createElement("x479");
					collectionSeqType.appendChild(xmlDoc.createTextNode("01"));
					collectionSeq.appendChild(collectionSeqType);
					ElementNode collectionSeqTypeNum = (ElementNode) xmlDoc.createElement("x480");
					collectionSeqTypeNum.appendChild(xmlDoc.createTextNode("WileySeriesSequence"));
					collectionSeq.appendChild(collectionSeqTypeNum);
					ElementNode collectionSeqNum = (ElementNode) xmlDoc.createElement("x481");
					collectionSeqNum.appendChild(xmlDoc.createTextNode(seriesSeq));
					collectionSeq.appendChild(collectionSeqNum);
				}
					
					ElementNode collectionTitleDetail = (ElementNode) xmlDoc.createElement("titledetail");
					collection.appendChild(collectionTitleDetail);
					
					ElementNode collectionTitleType = (ElementNode) xmlDoc.createElement("b202");
					collectionTitleType.appendChild(xmlDoc.createTextNode("01"));
					collectionTitleDetail.appendChild(collectionTitleType);
					ElementNode collectionTitleElement = (ElementNode) xmlDoc.createElement("titleelement");
					collectionTitleDetail.appendChild(collectionTitleElement);
					ElementNode collectionTitleElementLevel = (ElementNode) xmlDoc.createElement("x409");
					collectionTitleElementLevel.appendChild(xmlDoc.createTextNode("02"));
					collectionTitleElement.appendChild(collectionTitleElementLevel);
					
					if (!seriesDescription.equals("null") && !seriesDescription.isEmpty()){
						
						ElementNode collectionTitleText = (ElementNode) xmlDoc.createElement("b203");
						collectionTitleText.appendChild(xmlDoc.createTextNode(seriesDescription));
						collectionTitleElement.appendChild(collectionTitleText);
					}
					
				
				
					
				ElementNode setTitleDetail = (ElementNode) xmlDoc.createElement("titledetail");
				collection.appendChild(setTitleDetail);
				
				ElementNode setTitleType = (ElementNode) xmlDoc.createElement("b202");
				setTitleType.appendChild(xmlDoc.createTextNode("01"));
				setTitleDetail.appendChild(setTitleType);
				ElementNode setTitleElement = (ElementNode) xmlDoc.createElement("titleelement");
				setTitleDetail.appendChild(setTitleElement);
				ElementNode setTitleElementLevel = (ElementNode) xmlDoc.createElement("x409");
				setTitleElementLevel.appendChild(xmlDoc.createTextNode("02"));
				setTitleElement.appendChild(setTitleElementLevel);
				
				if (!editionVol.equals("null") && !editionVol.isEmpty()){
					
					ElementNode setpartNumber = (ElementNode) xmlDoc.createElement("x410");
					setpartNumber.appendChild(xmlDoc.createTextNode(editionVol));
					setTitleElement.appendChild(setpartNumber);
				}
				if (!collectiontitle.equals("null") && !collectiontitle.isEmpty()){
					
					ElementNode setTitleText = (ElementNode) xmlDoc.createElement("b203");
					setTitleText.appendChild(xmlDoc.createTextNode(collectiontitle));
					setTitleElement.appendChild(setTitleText);
				}
				
			product.appendChild(collection);
			
			}else{
				ElementNode noCollection = (ElementNode) xmlDoc.createElement("x411");
				product.appendChild(noCollection);
				
			}
			
			//P.5 End //
			
			//P.6 Starts //
			
			String[] titleSubTitle = dc.getTitleSubTitle(isbn);
			 
			ElementNode title = (ElementNode) xmlDoc.createElement("titledetail");
			
			ElementNode titletype = (ElementNode) xmlDoc.createElement("b202");
			titletype.appendChild(xmlDoc.createTextNode("01"));
			title.appendChild(titletype);
			
			if ((!titleSubTitle[0].equals("null") && !titleSubTitle[0].isEmpty()) || (!titleSubTitle[1].equals("null") && !titleSubTitle[1].isEmpty())){
				
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
			}
			
			product.appendChild(title);
			 
			//P.6 End //
			 
			 
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return xmlDoc;
	}
	
	

}
