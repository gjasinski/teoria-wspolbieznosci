package tw06;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ProducerMethodRequestImpl implements MethodRequest, Future<Integer>{
    private Servant servant;
    private String data;
    private Integer result;

    ProducerMethodRequestImpl(Servant servant, String data) {
        this.servant = servant;
        this.data = data;
    }

    public boolean guard() {
        return servant.canIPutData(data.length());
    }

    public void execute() {
        this.servant.putToBuffer(data);
        this.result = data.length();
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
        return result != null;
    }

    @Override
    public Integer get() throws InterruptedException, ExecutionException {
        return result;
    }

    @Override
    public Integer get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return get();
    }
}
