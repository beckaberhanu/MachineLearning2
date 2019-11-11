import java.text.DecimalFormat;
import java.util.Random;

public class Matrix2d {
    public double label;
    public int xSpan;
    public int ySpan;
    private double[][] matrix;
    public Matrix2d(int ySpan, int xSpan){
        this.xSpan = xSpan;
        this.ySpan = ySpan;
        matrix = new double[ySpan][xSpan];
    }
    public Matrix2d(int ySpan, int xSpan, double label){
        this.xSpan = xSpan;
        this.ySpan = ySpan;
        matrix = new double[ySpan][xSpan];
        this.label = label;
    }
    public Matrix2d(double arr[][]){
        this.ySpan = arr.length;
        this.xSpan = arr[0].length;
        matrix = arr;
    }
    public void set(int y, int x, double num){
        matrix[y][x] = num;
    }
    public double get(int y, int x){
        return matrix[y][x];
    }
    public double findMax(){
        double max = this.get(0,0);
        for(int i = 0; i< ySpan; i++){
            for(int j = 0; j< xSpan; j++){
                if(matrix[i][j]>max){
                    max = matrix[i][j];
                }

            }
        }
        return max;
    }
    public void gausianPopulate(){
        Random r = new Random();
        gausianPopulate(r);
    }
    public void gausianPopulate(Random r){
        for(int i = 0; i<ySpan; i++){
            for(int j = 0; j<xSpan; j++){
                matrix[i][j] = r.nextGaussian();
            }
        }
    }
    public double average(){
        double out = 0;
        for(int i = 0; i<ySpan; i++){
            for(int j = 0; j<xSpan; j++){
                out+=this.get(i,j);
            }
        }
        out=out/ (this.xSpan+this.ySpan);
        return out;
    }
    public int[][][] to3dArray(){
        int[][][] output = new int[xSpan][ySpan][4];
        double max = findMax();
        for(int i = 0; i < xSpan; i++){
            for(int j = 0; j < ySpan; j++){
                output[i][j][0] = (int)(this.get(j,i)*255/max);
                output[i][j][1] = (int)(this.get(j,i)*255/max);
                output[i][j][2] = (int)(this.get(j,i)*255/max);
                output[i][j][3] = (int)(this.get(j,i)*255/max);
            }
        }
        return output;
    }

    @Override
    public String toString(){
        String output = "";
        DecimalFormat df = new DecimalFormat("###.#####");
        for(int i = 0; i< ySpan; i++){
            for(int j = 0; j< xSpan; j++){
                output+= df.format(matrix[i][j]) + " ";
            }
            output+= "\n";
        }
        return output;
    }

    public String dimensions(){
        return "[y,x] : [" +ySpan+","+xSpan+"]";
    }
    public Matrix2d returnself(){
        return this;
    }
}
