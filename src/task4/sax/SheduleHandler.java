package task4.sax;

import task4.Beans.OriginDestination;
import task4.Beans.Flight;
import task4.Beans.Cost;
import task4.Beans.Aircraft;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.helpers.DefaultHandler;

public class SheduleHandler extends DefaultHandler {

    private final String DELIMITER = ":";
    private final int ATTR_ZERO = 0;
    private final int ATTR_ONE = 1;
    private final int ATTR_TWO = 2;

    private enum Tag {
        SHEDULE, ORIGINDESTINATION, DEPARTUREAIRPORT, ARRIVALAIRPORT, FLIGHTS,
        FLIGHT, COST, FIRSTCLASS, BUSINESSCLASS, ECONOMYCLASS, AIRCRAFT;
    }

    private Aircraft aircraft = null;
    private Cost cost = null;
    private Flight flight = null;
    private OriginDestination originDestination = null;
    private List<Flight> flightList = null;
    private final List<OriginDestination> sheduleList = new ArrayList<OriginDestination>();
    private Tag tag = null;

    public List<OriginDestination> getShedule() {
        return sheduleList;
    }

    @Override
    public void startDocument() throws org.xml.sax.SAXException {

    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, org.xml.sax.Attributes attr) throws org.xml.sax.SAXException {

        tag = Tag.valueOf(cut(qName).toUpperCase());

        switch (tag) {
            case ORIGINDESTINATION:                
                String departureDateTime = attr.getValue(ATTR_ZERO);
                String arrivalDateTime = attr.getValue(ATTR_ONE);
                originDestination = new OriginDestination(departureDateTime, arrivalDateTime);
                break;
            case FLIGHTS:    
                flightList = new ArrayList<Flight>();
                break;
            case FLIGHT:
                String numberFlight = attr.getValue(ATTR_ZERO);
                flight = new Flight(numberFlight);
                break;
            case COST:
                cost = new Cost();
                break;
            case AIRCRAFT:
                String model = attr.getValue(ATTR_ZERO);
                String number = attr.getValue(ATTR_ONE);
                String numberSeats = attr.getValue(ATTR_TWO);
                aircraft = new Aircraft(model, number, numberSeats);
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws org.xml.sax.SAXException {
        String data = new String(ch, start, length).trim();
        
        if (tag == null) {
            return;
        }
        switch (tag) {
            case DEPARTUREAIRPORT:
                originDestination.setDepartureAirportLocation(data);
                tag = null;
                break;
            case ARRIVALAIRPORT:
                originDestination.setArrivalAirportLocation(data);
                tag = null;
                break;
            case FIRSTCLASS:
                cost.setFirst(data);
                tag = null;
                break;
            case BUSINESSCLASS:
                cost.setBusiness(data);
                tag = null;
                break;
            case ECONOMYCLASS:
                cost.setEconomy(data);
                tag = null;
                break;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws org.xml.sax.SAXException {

        Tag endTag = Tag.valueOf(cut(qName).toUpperCase());

        switch (endTag) {
            case FLIGHT:
                flight.setCost(cost);                
                flight.setAircraft(aircraft);
                flightList.add(flight);                
                cost = null;
                aircraft = null;
                flight = null;                
                break;
            case FLIGHTS:
                originDestination.setFlights(flightList);
                flightList = null;
                break;
            case ORIGINDESTINATION:               
                sheduleList.add(originDestination);
                originDestination = null;
                break;
        }
    }

    @Override
    public void endDocument() {

    }

    // air:shedule --> shedule
    private String cut(String tagName) {
        if (tagName.contains(DELIMITER)) {
            int ind = tagName.indexOf(DELIMITER);
            tagName = tagName.substring(ind + 1);
        }
        return tagName;
    }

}
