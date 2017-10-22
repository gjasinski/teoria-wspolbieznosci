package tw02.task2;

class Customer implements Runnable{
    private final int id;
    private final HypermarketSimulator hypermarketSimulator;

    Customer(int id, HypermarketSimulator hypermarketSimulator){
        this.id = id;
        this.hypermarketSimulator = hypermarketSimulator;
    }

    private void takeTrolley() throws InterruptedException {
        int trolleys = this.hypermarketSimulator.takeTrolley();
        System.out.println(this.toString() + "has taken a trolley, trolleys left: " + trolleys);
    }


    private void giveBackTrolley() throws InterruptedException{
        int trolleys = this.hypermarketSimulator.giveBackTrolley();
        System.out.println(this.toString() + "give back trolley, trolleys left:" + trolleys);
    }

    @Override
    public void run() {
        try {
            takeTrolley();
            Thread.sleep(1000);
            giveBackTrolley();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                '}';
    }
}
