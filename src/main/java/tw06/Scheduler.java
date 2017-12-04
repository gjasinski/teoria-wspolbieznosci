package tw06;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

class Scheduler implements Runnable{
    private Queue<MethodRequest> queue;

    Scheduler(){
        this.queue = new ConcurrentLinkedDeque<>();
    }


    void scheduleTask(MethodRequest methodRequest){
        queue.add(methodRequest);
    }

    @Override
    public void run() {
        while(true){
            MethodRequest request = queue.poll();
            if(request != null){
                if(request.guard()){
                    request.execute();
                }
                else {
                    queue.add(request);
                }
            }
        }
    }
}
