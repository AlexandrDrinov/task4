package task4.sax;

public class Cost {

    private final String EXCEPTION_NOT_VALID_VALUE = "Value < 0. It's bad. Value must be > 0";
    
    private int first;

    private int business;

    private int economy;

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public void setFirst(String first) {
        int value = Integer.parseInt(first);
        validation(value);
        setFirst(value);
    }

    private void validation(int testValue) {
        if (testValue < 0) {
            throw new RuntimeException(EXCEPTION_NOT_VALID_VALUE);
        }
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public void setBusiness(String business) {
        int value = Integer.parseInt(business);
        validation(value);
        setBusiness(value);
    }

    public int getEconomy() {
        return economy;
    }

    public void setEconomy(int economy) {
        this.economy = economy;
    }

    public void setEconomy(String economy) {
        int value = Integer.parseInt(economy);
        validation(value);
        setEconomy(value);
    }

    @Override
    public String toString() {
        return "Cost{" + "first=" + first + ", business=" + business + ", economy=" + economy + '}';
    }

}
