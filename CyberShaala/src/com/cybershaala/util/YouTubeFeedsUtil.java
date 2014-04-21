package com.cybershaala.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cybershaala.CyberShaalaDAL;
import com.cybershaala.data.model.Materials;

public class YouTubeFeedsUtil {
	XPathFactory xFactory = XPathFactory.newInstance();
	XPath xpath = xFactory.newXPath();
	XPathExpression expr = null;
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	String tags="";
	
	public void fetchAndLoadYouTubeFeeds() throws IOException{
		// standard for reading an XML file
		String tokens[];
		HttpClient client = new DefaultHttpClient();
		HttpGet request;
		HttpResponse response;
		BufferedReader tagBuffRdr = null;
		try {
			tagBuffRdr = new BufferedReader(new FileReader("src/tags.csv"));
			while((tags = tagBuffRdr.readLine()) != null) {
				tokens = tags.split(",");
				String urlSuffix="";
				
				for (String token : tokens)
					urlSuffix = urlSuffix + "/" + token;//urlSuffix contains tags
				
				System.out.println("**************Loading Tags*************");
				System.out.println("Fetching feeds from URL - "+"https://gdata.youtube.com/feeds/api/videos/-"+urlSuffix);
				//Execute HTTP GET on this URL
				request = new HttpGet("https://gdata.youtube.com/feeds/api/videos/-"+urlSuffix);
				response = client.execute(request);
				parseFeedXml(response.getEntity().getContent());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			tagBuffRdr.close();
		}
	}

	public void parseFeedXml(InputStream is)
	{
		List<Materials> materialsList = new ArrayList<Materials>();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			factory.setNamespaceAware(true);
			Document doc = builder.parse(is);
			
			// new XPath expression to get the number of people with name Lars
			expr = xpath.compile("count(//*[local-name()='entry'])");
			// run the query and get the number of nodes
			Double number = (Double) expr.evaluate(doc, XPathConstants.NUMBER);
			int numEntryNodes = number.intValue();
			System.out.println("Number of YouTube videos " +numEntryNodes);
			if(numEntryNodes>25)
				numEntryNodes = 25;
			/*Run through the XML and build Material objects from info in <entry> tags*/ 
			for(int i=1;i<=numEntryNodes;i++)
			{
				Materials objMat = new Materials();
				
				System.out.println("Video Link---------:");
				objMat.setMaterialUrl(getXMLNodeValue(doc, "attr", "//*[local-name()='entry']["+i+"]/*[local-name()='link'][1]/@href"));
				System.out.println("Title---------:");				
				objMat.setMaterialName(getXMLNodeValue(doc, "element", "//*[local-name()='entry']["+i+"]/*[local-name()='title']"));
				System.out.println("Description---------:");
				objMat.setMaterialDesc(getXMLNodeValue(doc, "element", "//*[local-name()='entry']["+i+"]/*[local-name()='content']"));
				objMat.setType("youtube");
				objMat.setDateTime(new Timestamp((new Date()).getTime()));
				objMat.setUserId("YouTubeFeeds");
				objMat.setTags(tags);
				
				CyberShaalaDAL.createConnectionAndStatement();
				CyberShaalaDAL.insertIntoMaterials(objMat);
				CyberShaalaDAL.close();
				
//				materialsList.add(objMat);
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getXMLNodeValue(Document doc, String nodeType, String xpathExpr)
	{
		String value="";
		try {
				expr = xpath.compile(xpathExpr);
				NodeList node = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
				System.out.println("Node:"+node.item(0));
				if (nodeType=="attr")
					value = node.item(0).getNodeValue().trim();
				else
					value = node.item(0).getTextContent().trim();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		System.out.println("Value:"+value);
		return value;
	}
	
	public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		YouTubeFeedsUtil process = new YouTubeFeedsUtil();
		process.fetchAndLoadYouTubeFeeds();
	}
}