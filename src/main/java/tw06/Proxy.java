package tw06;

import java.util.concurrent.Future;

class Proxy {
    static final int BUFFER_SIZE = 50;
    private Scheduler scheduler;
    private Servant servant;

    Proxy(){
        this.scheduler = new Scheduler();
        (new Thread(this.scheduler)).start();
        this.servant = new Servant(BUFFER_SIZE);
    }

    Future<Integer> putData(String data) {
        ProducerMethodRequestImpl methodRequest = new ProducerMethodRequestImpl(servant, data);
        this.scheduler.scheduleTask(methodRequest);
        return methodRequest;

    }

    Future<String> getData(int sizeToGet) {
        ConsumerMethodRequest methodRequest = new ConsumerMethodRequest(servant, sizeToGet);
        this.scheduler.scheduleTask(methodRequest);
        return methodRequest;
    }
}
