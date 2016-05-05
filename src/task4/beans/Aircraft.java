package task4.Beans;

public class Aircraft {
    
    private final String model;
    
    private final String number;
    
    private final int numberSeats;
    
    public Aircraft(String name, String number, String numberSeats) {
        this.model = name;
        this.number = number;
        this.numberSeats = Integer.parseInt(numberSeats);
    }

    public String getModel() {
        return model;
    }

    public String getNumber() {
        return number;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    @Override
    public String toString() {
        return "Aircraft{" + "model=" + model + ", number=" + number + ", numberSeats=" + numberSeats + '}';
    }  
    
}
