public class Cabin {
    private String mainName;
    private Passenger[] passengers = new Passenger[3];


    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public Passenger[] getPassengers() {
        return passengers;
    }

    public void setPassengers(Passenger[] passengers) {
        this.passengers = passengers;
    }

    public String getName() { return mainName; }
}
