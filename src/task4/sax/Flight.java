package task4.sax;

public class Flight {

    private final String number;

    private Cost cost;

    private Aircraft aircraft;

    public Flight() {
        this.number = "";
    }

    public Flight(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    @Override
    public String toString() {
        return "Flight: " + "number=" + number + ", cost=" + cost + ", aircraft=" + aircraft + '}';
    }

}
