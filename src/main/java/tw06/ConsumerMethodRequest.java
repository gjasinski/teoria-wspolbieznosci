package tw06;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConsumerMethodRequest implements MethodRequest, Future<String> {
    private Servant servant;
    private int sizeToGet;
    private String result;

    ConsumerMethodRequest(Servant servant, int sizeToGet){
        this.servant = servant;
        this.sizeToGet = sizeToGet;
    }

    @Override
    public void execute() {
        this.result = this.servant.getFromBuffer(this.sizeToGet);
    }

    @Override
    public boolean guard() {
        return this.servant.canITakeData(this.sizeToGet);
    }

    @Override
    public boolean cancel(boolean b) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return this.result != null;
    }

    @Override
    public String get() throws InterruptedException, ExecutionException {
        return this.result;
    }

    @Override
    public String get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return get();
    }
}
