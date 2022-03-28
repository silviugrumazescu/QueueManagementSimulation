public class Client {
    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int arrivalTime, serviceTime, id;

    public Client(int id, int arrivalTime, int serviceTime){
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.id = id;
    }


}
