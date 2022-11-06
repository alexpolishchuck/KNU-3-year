package FileManager;

import FileManager.SAXHandler.SAXHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.crypto.NodeSetData;
import javax.xml.namespace.QName;
import javax.xml.parsers.*;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FileManager
{

    public ArrayList<BankDeposit> parseFileDOM()
    {
        ArrayList<BankDeposit> res = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(true);
        documentBuilderFactory.setNamespaceAware(true);
        documentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                "http://www.w3.org/2001/XMLSchema");
        if(!validateFile())
            throw new RuntimeException("XML file is invalid");
        try
        {
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(filePath));
            doc.getDocumentElement().normalize();
            NodeList deposits = doc.getElementsByTagName("deposit");
            int size = deposits.getLength();
            for(int i=0; i<size; i++)
            {
                Node node = deposits.item(i);
                Element element = (Element) node;
                res.add(new BankDeposit(
                        element.getAttribute("name"),
                        element.getElementsByTagName("Country").item(0).getTextContent(),
                        element.getElementsByTagName("Type").item(0).getTextContent(),
                        element.getElementsByTagName("Depositor").item(0).getTextContent(),
                        ((Element)element.getElementsByTagName("Depositor").item(0)).getAttribute("account_id"),
                        element.getElementsByTagName("Amount").item(0).getTextContent(),
                        element.getElementsByTagName("Profitability").item(0).getTextContent(),
                        element.getElementsByTagName("Time_constraints").item(0).getTextContent()
                ));
            }

        } catch (Exception e)
        {
            System.out.println(e);
        }



        return res;
    }
    public ArrayList<BankDeposit> parseFileStAX()  {
        ArrayList<BankDeposit> res = new ArrayList<>();
        if(!validateFile())
            return res;
        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(filePath));
            BankDeposit deposit = null;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case "deposit":
                            deposit = new BankDeposit();
                            deposit.setParameter(BankDeposit.FIELD.NAME,startElement.getAttributeByName(
                                    new QName("name")).getValue());
                            break;
                        case "Country":
                            event = reader.nextEvent();
                            deposit.setParameter(BankDeposit.FIELD.COUNTRY,event.asCharacters().getData());
                            break;
                        case "Type":
                            event = reader.nextEvent();
                            deposit.setParameter(BankDeposit.FIELD.TYPE,event.asCharacters().getData());
                            break;
                        case "Depositor":
                            event = reader.nextEvent();
                            deposit.setParameter(BankDeposit.FIELD.DEPOSITOR,event.asCharacters().getData());
                            deposit.setParameter(BankDeposit.FIELD.ID,startElement.getAttributeByName(
                                    new QName("account_id")).getValue());
                            break;
                        case "Amount":
                            event = reader.nextEvent();
                            deposit.setParameter(BankDeposit.FIELD.AMOUNT,event.asCharacters().getData());
                            break;
                        case "Profitability":
                            event = reader.nextEvent();
                            deposit.setParameter(BankDeposit.FIELD.PROF,event.asCharacters().getData());
                            break;
                        case "Time_constraints":
                            event = reader.nextEvent();
                            deposit.setParameter(BankDeposit.FIELD.TIME,event.asCharacters().getData());
                            break;
                    }
                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals("deposit")) {
                        res.add(deposit);
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return res;
    }
    public ArrayList<BankDeposit> parseFileSAX()
    {
        if(!validateFile())
            return null;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        ArrayList<BankDeposit> res = null;
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SAXHandler handler = new SAXHandler();
            saxParser.parse(new File(filePath), handler);
            //Get Employees list
             res = handler.getDeposits();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    private boolean validateFile()
    {
        try
        {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(filePath)));
        } catch (Exception e)
        {
            return false;
        }
        return true;
    }

    private String filePath = "src\\FileManager\\Bankdeposits.xml";
    private String xsdPath = "src\\FileManager\\Bankdeposits.xsd";
}
