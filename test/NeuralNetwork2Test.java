import java.io.IOException;

public class NeuralNetwork2Test {
    static void generateXORTrainingSet(int length, Matrix2d[] data, Matrix2d[] label){
        for(int i = 0; i < data.length; i++){
            int a,b,c;
            if(Math.random()>0.5){
                a = 1;
            }
            else{
                a = 0;
            }
            if(Math.random()>0.5){
                b = 1;
            }
            else{
                b = 0;
            }
            c = a ^ b;
            double arr1[][] = {{a,b}};
            double arr2[][] = {{c}};
            data[i] = new Matrix2d(arr1);
            label[i] = new Matrix2d(arr2);
        }
    }

    public static void main(String[] args) throws IOException {
//        int [] arr = {2,2,1};
//        Matrix2d [] data = new Matrix2d[10000];
//        Matrix2d [] label = new Matrix2d[10000];
//        generateXORTrainingSet(10000,data,label);
//        NeuralNetwork2 nn = new NeuralNetwork2(arr,0.01,true,data,label);
//        int x = 1201;
//        System.out.println(data[x]);
//        System.out.println(label[x]);
//        //nn.fit(new Point2D.Double(1000,10000),300);
//        //nn.saveModel("firsttry");
//        nn.loadModel("firsttry");
//        System.out.println(nn.predict(data[x]));
//        int count = 0;
//        for(int i = 0; i<10000; i++){
//            if(Math.round(nn.predict(data[i]).get(0,0))==label[i].get(0,0)){
//                count +=1;
//            }
//            else{
//                //count -=1;
//            }
//        }
//        System.out.println(count);
//        //nn.saveModel("firsttry");
        int [] arr = {1,1,1,1};
        double [][] aw1 = new double [1][1];
        aw1[0][0] = 0.3;
        double [][] aw2 = new double [1][1];
        aw2[0][0] = 0.7;
        double [][] aw3 = new double [1][1];
        aw3[0][0] = 0.4;
        double [][] aw4 = new double [1][1];
        aw4[0][0] = 0.9;
        Matrix2d w1 = new Matrix2d(aw1);
        Matrix2d w2 = new Matrix2d(aw2);
        Matrix2d w3 = new Matrix2d(aw3);
        Matrix2d w4 = new Matrix2d(aw4);
        System.out.print("weight list:" +w1 + " " + w2 + " " +w3+ " " +w4+ "\n ");
        Matrix2d [] weight = {w1,w2,w3,w4};


        double [][] in = new double[1][1];
        in[0][0] = 0.8;
        Matrix2d [] data = new Matrix2d[1];
        data[0] = new Matrix2d(in);
        System.out.println("data " + data[0]);
        Matrix2d [] label = new Matrix2d[1];
        double [][] lb = new double[1][1];
        lb[0][0] = 1;
        label[0] = new Matrix2d(lb);
        System.out.println("label " + label[0]);
        NeuralNetwork2 nn = new NeuralNetwork2(weight,0.01,false,data,label);
        nn.fit(1,1,1);

    }

}
