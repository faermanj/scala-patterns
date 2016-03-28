package syntax.java;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class DOMLegacy {
	public static void main(String[] args) throws Exception {
		String xhtml = "<html>"
				+"<h1> Oi </h1>"
				+"<h1> Mundo </h1>" 
				+"</html>";
		InputStream is = new ByteArrayInputStream(xhtml.getBytes());
		Document doc = DocumentBuilderFactory
	    		.newInstance()
	    		.newDocumentBuilder()
	    		.parse(is);
	    NodeList nodes = doc.getElementsByTagName("h1");
	    //Se ao menos NodeList estendesse Iterable...
	    for (int i = 0; i < nodes.getLength() ; i++) {
			String content = nodes.item(i).getTextContent();
			System.out.println(content);
		}
	}
}
