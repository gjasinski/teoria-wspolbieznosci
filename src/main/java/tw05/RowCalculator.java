package tw05;

import java.util.concurrent.Callable;

public class RowCalculator implements Callable<int[]>{
    int y, width, maxIter;
    double zoom;

    public RowCalculator(int y, int width, double zoom, int maxIter) {
        this.y = y;
        this.width = width;
        this.zoom = zoom;
        this.maxIter = maxIter;
    }

    private int[] calculateRow() {
        double zx,zy, cX, cY, tmp;
        int[] result = new int[width];
        for (int x = 0; x < width; x++) {
            zx = zy = 0;
            cX = (x - 400) / zoom;
            cY = (y - 300) / zoom;
            int iter = maxIter;
            while (zx * zx + zy * zy < 4 && iter > 0) {
                tmp = zx * zx - zy * zy + cX;
                zy = 2.0 * zx * zy + cY;
                zx = tmp;
                iter--;
            }
            result[x] =  iter | (iter << 8);
        }
        return result;
    }

    @Override
    public int[] call() throws Exception {
        return calculateRow();
    }
}
