package task4;

import task4.sax.SheduleHandler;
import task4.beans.OriginDestination;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import task4.beans.Flight;

public class Runner {

    public static void main(String[] args) {

        final String FILENAME = "src/shedule.xml";
        final String FILENAME_RESULT ="src/shedule_result.xml";

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            SheduleHandler sheduleSAX = new SheduleHandler();
            parser.parse(new File(FILENAME), sheduleSAX);
            List<OriginDestination> sheduleList = sheduleSAX.getShedule();
            exportStructureToXML(FILENAME_RESULT, sheduleList);
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            System.out.println(ex);
        }
    }

    public static void exportStructureToXML(String fileName, List<OriginDestination> sheduleList) {
        try {     
            File file = new File(fileName);
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element shedule = doc.createElement("air:shedule");
            shedule.setAttribute("xmlns:air", "http://www.dr.org/Shedule");
            shedule.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            shedule.setAttribute("xsi:schemaLocation", "http://www.dr.org/Shedule shedule.xsd");
            doc.appendChild(shedule);

            for (OriginDestination od : sheduleList) {
                Element originDestination = doc.createElement("originDestination");
                originDestination.setAttribute("departureDateTime", od.departureDateTimeToString());
                originDestination.setAttribute("arrivalDateTime", od.arrivalDateTimeToString());
                shedule.appendChild(originDestination);

                Element departureAirport = doc.createElement("departureAirport");
                originDestination.appendChild(departureAirport);

                Text textDepartureAirport = doc.createTextNode(od.getDepartureAirportLocation());
                departureAirport.appendChild(textDepartureAirport);

                Element arrivalAirport = doc.createElement("arrivalAirport");
                originDestination.appendChild(arrivalAirport);

                Text textArrivalAirport = doc.createTextNode(od.getArrivalAirportLocation());
                arrivalAirport.appendChild(textArrivalAirport);

                Element flights = doc.createElement("flights");                
                originDestination.appendChild(flights);

                for (Flight fl : od.getFlights()) {
                    Element flight = doc.createElement("flight");
                    flight.setAttribute("number", fl.getNumber());
                    flights.appendChild(flight);

                    Element cost = doc.createElement("cost");
                    flight.appendChild(cost);

                    Element firstClass = doc.createElement("firstClass");
                    cost.appendChild(firstClass);

                    Text textFirstClass = doc.createTextNode(String.valueOf(fl.getCost().getFirst()));
                    firstClass.appendChild(textFirstClass);

                    Element businessClass = doc.createElement("businessClass");
                    cost.appendChild(businessClass);

                    Text textBusinessClass = doc.createTextNode(String.valueOf(fl.getCost().getBusiness()));
                    businessClass.appendChild(textBusinessClass);

                    Element economyClass = doc.createElement("economyClass");
                    cost.appendChild(economyClass);

                    Text textEconomyClass = doc.createTextNode(String.valueOf(fl.getCost().getEconomy()));
                    economyClass.appendChild(textEconomyClass);
                    
                    Element aircraft = doc.createElement("aircraft");
                    aircraft.setAttribute("model", fl.getAircraft().getModel());
                    aircraft.setAttribute("number", fl.getAircraft().getNumber());
                    aircraft.setAttribute("numberSeats", String.valueOf(fl.getAircraft().getNumberSeats()));
                    flight.appendChild(aircraft);
                }
            }

            TransformerFactory transfactory = TransformerFactory.newInstance();
            Transformer trans = transfactory.newTransformer();
            trans.setOutputProperty(OutputKeys.METHOD, "xml");
            trans.setOutputProperty(OutputKeys.ENCODING, "utf-8");            
            trans.setOutputProperty(OutputKeys.INDENT, "yes");            
            trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");            

            StreamResult result = new StreamResult(file);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);            
        } catch (TransformerException | ParserConfigurationException ex) {
            System.out.println(ex);
        }

    }

}
