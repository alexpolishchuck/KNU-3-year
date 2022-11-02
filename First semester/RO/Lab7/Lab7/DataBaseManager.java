package Lab7;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



public class DataBaseManager {

    public void printFile(){

    }

    public ArrayList<vegetable> readFile(){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        DocumentBuilder db = null;
        ArrayList<vegetable> vegetables =new ArrayList<>();
        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(fileName));
            NodeList list = doc.getElementsByTagName("Lab7.vegetable");

            int len = list.getLength();

            for(int i=0; i<len; i++)
            {
                Node node = list.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    vegetables.add(new vegetable(element.getElementsByTagName("category").item(0).getTextContent()
                            ,element.getElementsByTagName("name").item(0).getTextContent()));
                }
                
            }


        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e);
        }
        return vegetables;
    }
    public void addObject(String category, String name){
        ArrayList<vegetable> vegetables = readFile();
        //sort
        for(vegetable i:vegetables)
        {
            if(i.getCategory().compareTo(category)==0 && i.getName().compareTo(name)==0)
                return;
        }
        DocumentBuilder db = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
        db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(fileName));
        Element root_vegetables = doc.getDocumentElement();
        Element vegetable = doc.createElement("Lab7.vegetable");
        vegetable.appendChild(doc.createElement("category"));
        vegetable.getElementsByTagName("category").item(0).appendChild(doc.createTextNode(category));
        vegetable.appendChild(doc.createElement("name"));
        vegetable.getElementsByTagName("name").item(0).appendChild(doc.createTextNode(name));
        root_vegetables.appendChild(vegetable);


            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"vegetables.dtd");
            DOMSource ds = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(fileName);
            transformer.transform(ds,streamResult);


        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public boolean deleteObject(String category, String name)
    {

        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(new File(fileName));
            NodeList list = doc.getElementsByTagName("Lab7.vegetable");
            int len = list.getLength();
            for(int i=0; i<len; i++)
            {
                Element element = (Element) list.item(i);
                    if (element.getElementsByTagName("category").item(0).getTextContent().compareTo(category) == 0 &&
                            element.getElementsByTagName("name").item(0).getTextContent().compareTo(name) == 0)
                    {
                        element.getParentNode().removeChild(element);
                        Transformer transformer = TransformerFactory.newInstance().newTransformer();
                        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
                        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"vegetables.dtd");
                        DOMSource ds = new DOMSource(doc);
                        StreamResult streamResult = new StreamResult(fileName);
                        transformer.transform(ds,streamResult);
                        return true;
                    }

            }
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }
    public void editObject(String category, String name, PARAMETER parameter_to_change, String new_string)
    {
        if(deleteObject(category,name))
        {
            switch (parameter_to_change){
                case CATEGORY:
                    addObject(new_string,name);
                    break;
                case NAME:
                    addObject(category,new_string);
                    break;
            }
        }


    }
    public enum PARAMETER{CATEGORY,NAME};
    private final String fileName ="vegetables.xml";

}
