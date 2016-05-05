package task4.Beans;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class OriginDestination {

    private final Date departureDateTime;

    private final Date arrivalDateTime;

    private String departureAirportLocation;

    private String arrivalAirportLocation;

    private List<Flight> flights;

    public OriginDestination() {
        this.departureDateTime = Calendar.getInstance().getTime();
        this.arrivalDateTime = Calendar.getInstance().getTime();
    }

    public OriginDestination(String departureDateTime, String arrivalDateTime) {
        this.departureDateTime = conversionDateTime(departureDateTime);
        this.arrivalDateTime = conversionDateTime(arrivalDateTime);
    }

    public String departureDateTimeToString() {
        return DateToXMLGregorianCalendar(departureDateTime);                            
    }

    public String arrivalDateTimeToString() {
        return DateToXMLGregorianCalendar(arrivalDateTime);
    }

    private String DateToXMLGregorianCalendar(Date date) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            DatatypeFactory df = DatatypeFactory.newInstance();
            XMLGregorianCalendar dateTime = df.newXMLGregorianCalendar(calendar);
            return dateTime.normalize().toString();
        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException(ex);
        }
    }    

    public Date getDepartureDateTime() {
        return departureDateTime;
    }

    public Date getArrivalDateTime() {
        return arrivalDateTime;
    }

    private Date conversionDateTime(String dateTime) {
        Calendar calendar = DatatypeConverter.parseDateTime(dateTime);
        return calendar.getTime();
    }

    public String getDepartureAirportLocation() {
        return departureAirportLocation;
    }

    public void setDepartureAirportLocation(String departureAirportLocation) {
        this.departureAirportLocation = departureAirportLocation;
    }

    public String getArrivalAirportLocation() {
        return arrivalAirportLocation;
    }

    public void setArrivalAirportLocation(String arrivalAirportLocation) {
        this.arrivalAirportLocation = arrivalAirportLocation;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    private String printFlights() {
        StringBuilder sb = new StringBuilder();
        for (Flight flight : flights) {
            sb.append("\n");
            sb.append("\t");
            sb.append("\t");
            sb.append(flight);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Shedule: " + "\n\tdepartureDateTime = " + departureDateTime
                + "\n\tarrivalDateTime = " + arrivalDateTime
                + "\n\tdepartureAirportLocation = " + departureAirportLocation
                + "\n\tarrivalAirportLocation = " + arrivalAirportLocation
                + "\n\tflights: " + printFlights();
    }

}
