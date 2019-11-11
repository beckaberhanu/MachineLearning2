import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ImageAs3DArray {

//    JFrame frame = new JFrame();
//    public ImageAs3DArray(){
//        frame.setVisible(true);
//        frame.setSize(450,450);
//    }

    public static BufferedImage importImage(String directory){
        BufferedImage output = null;
        try {
            output = ImageIO.read(new File(directory));
        }catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public void displayImage(BufferedImage image){
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(image.getWidth(),image.getHeight());
        JLabel jLabel = new JLabel(new ImageIcon(image));
        JPanel jPanel = new JPanel();
        jPanel.add(jLabel);
        frame.add(jPanel);
        Scanner scan = new Scanner(System.in);
        String x = scan.nextLine();
        frame.remove(jPanel);
        frame.setVisible(false);
    }

    public static int [][][] imageConvertor(BufferedImage image){
        int [][][] output = new int[image.getWidth()][image.getHeight()][4];
        for(int i = 0; i < image.getWidth(); i++){
            for(int j = 0; j < image.getHeight(); j++){

                output[i][j][3] = (image.getRGB(i,j)>>24&0xff);
                //System.out.println((image.getRGB(i,j)>>24&0xff));
                output[i][j][0] = (image.getRGB(i,j)>>16&0xff);
                output[i][j][1] = (image.getRGB(i,j)>>8&0xff);
                output[i][j][2] = (image.getRGB(i,j)&0xff);
            }
        }
        return output;
    }

    public static int[][][] arrayResize(int[][][] arr, int width,int height){
        int[][][] output = new int[width][height][4];
        System.out.println(output[0].length);
        for(int i = 0; i<width-1; i++){
            for(int j = 0; j<height-1; j++){
                for(int k = 0; k<4;k++) {
                    int i1 = (int)((arr.length-1)*i*1./width);
                    int j1 = (int)((arr[0].length-1)*j*1./height);
                    output[i][j][k] = arr[i1][(j1)][k];
                }
            }
        }
        return output;
    }

    public static BufferedImage convertToImage(int [][][] input){
        BufferedImage output = new BufferedImage(input.length,input[0].length,4);
        for(int i = 0; i < input.length; i++){
            for(int j = 0; j<input[i].length;j++){
                output.setRGB(i,j,(input[i][j][3]<<24)|(input[i][j][0]<<16)|input[i][j][1]<<8|input[i][j][2]);
            }
        }
        return  output;
    }

    public static int findMax(int[][][] arr){
        int max = 0;
        for(int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if(arr[i][j][1]>max){
                    max = arr[i][j][1];
                }
            }
        }
        return max;
    }

    public static void print2dArray(double [][] arr){
        for(int i = 0; i<arr.length; i++){
            for(int j = 0; j<arr[i].length; j++){
                if(arr[i][j]<10){
                    System.out.print("      " + arr[i][j]);
                }
                else if(arr[i][j]<100){
                    System.out.print("     " + arr[i][j]);
                }
                else if(arr[i][j]<1000){
                    System.out.print("    " + arr[i][j]);
                }
                else if(arr[i][j]<10000){
                    System.out.print("   " + arr[i][j]);
                }
                else if(arr[i][j]<100000){
                    System.out.print("  " + arr[i][j]);
                }
                else{
                    System.out.print(" " +arr[i][j]);
                }
                //System.out.print(arr[i][j] + " ");
            }
            System.out.print("\n");
        }

    }

    public static void print3dArray(int [][][] arr){
        print3dArray(arr,255);
    }

    public static void print3dArray(int [][][] arr, int threshold){
        for(int i = 0; i < arr[0].length; i++){
            for(int j = 0; j < arr.length; j++){
                int sum = 0;
                for(int k = 0; k < 3; k++){
                    sum+=arr[j][i][k];
                }
                //*9/1020
                if(sum*threshold/(3*255)<10){
                    System.out.print(" " + sum*threshold/(3*255) + " ");
                }
                else if(sum*threshold/(3*255)<100){
                    System.out.print(" " + sum*threshold/(3*255));
                }
                else{
                    System.out.print(sum*threshold/(3*255));
                }
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    public static int[][][] grayScale(int[][][] arr){
        for(int i = 0; i<arr.length; i++){
            for(int j = 0; j<arr[i].length; j++){
//                double value = 0;
//                for(int k = 0; k<3; k++){
//                    value = Math.pow(arr[i][j][k],2);
//                }
//                for(int k = 0; k<3; k++){
//                    arr[i][j][k] = (int) Math.pow(value/3,1/2.);
//                }
//                arr[i][j][3] = 255;
                int value = (int) (0.2126*arr[i][j][0] + 0.7152*arr[i][j][1] + 0.0722*arr[i][j][2]);
                arr[i][j][0] = value;
                arr[i][j][1] = value;
                arr[i][j][2] = value;
            }
        }
        return arr;
    }

    public static int[][][] kernelOverlay(int[][][] arr, double[][] kernel, int tarX, int tarY){
        int[][][] output = new int[arr.length][arr[0].length][arr[0][0].length];
        for(int i = tarX; i<arr.length-kernel.length+tarX; i++){
            for(int j = tarY; j<arr[i].length-kernel[0].length+tarY; j++){
                //----------------------------------------------------------------
                double div = 0;
                int sum;
                for(int k = 0; k<3; k++){
                    sum = 0;
                    div = 0;
                    for(int x = 0; x<kernel.length; x++){
                        for(int y = 0; y<kernel[0].length;y++){
                            sum+= kernel[x][y]*arr[i-tarX+x][j-tarY+y][k];
                            div+= Math.abs(kernel[x][y]);
                            //System.out.println(kernel[x][y]);
                        }
                    }

                    //System.out.println(kernel[0][0]);

                    output[i][j][k] = (int)(sum/div);
                    //System.out.println(sum/div);
                    if(output[i][j][1]<0){
                        System.out.println("waaaaaa");
                    }
                }
                //System.out.println(div);
                //----------------------------------------------------------------
            }

        }
        //System.out.println(kernel[0][0]);
        System.out.println("----------------------------------------------------------------");
        return output;
    }

    public static int[][][] kernelConvolve(int[][][] arr, double[][] kernel, int tarX, int tarY){
        int[][][] output = new int[arr.length][arr[0].length][arr[0][0].length];
        for(int i = tarX; i<arr.length-kernel.length+tarX; i++){
            for(int j = tarY; j<arr[i].length-kernel[0].length+tarY; j++){
                //----------------------------------------------------------------
                double div = 0;
                int sum;
                for(int k = 0; k<3; k++){
                    sum = 0;
                    div = 0;
                    for(int x = 0; x<kernel.length; x++){
                        for(int y = 0; y<kernel[0].length;y++){
                            sum+= kernel[x][y]*arr[i-tarX+x][j-tarY+y][k];
                            div+= Math.abs(kernel[x][y]);
                            //System.out.println(kernel[x][y]);
                        }
                    }
                    //System.out.println(kernel[0][0]);

                    output[i][j][k] = (int)Math.abs(2*sum/div);
                    //System.out.println(sum/div);
                }
                //System.out.println(div);
                //----------------------------------------------------------------
            }

        }
        //System.out.println(kernel[0][0]);
        System.out.println("----------------------------------------------------------------");
        return output;
    }
    public static int[][][] brightOutliner(int[][][] arr){
        for(int i = 0; i<arr.length; i++){
            for(int j = 0; j<arr[i].length; j++){
                if(arr[i][j][1]>0){
                    arr[i][j][0] = 255;
                    arr[i][j][1] = 255;
                    arr[i][j][2] = 255;
                }
            }
        }
        return arr;
    }

    public static int[][][] descretize(int[][][] arr, int variations){
        for(int i = 0; i<arr.length; i++){
            for(int j = 0; j<arr[i].length;j++){
                for(int k = 0; k < 3; k++){
                    arr[i][j][k] = 255/variations *(int) (variations * arr[i][j][k]/(255.));
                }

            }
        }
        return arr;
    }

    public static double[][] gausianGenerator(int x, double sigma){
        double[][] output = new double[x][x];
        int k = x/2;
        double peak = 15;//2/(Math.exp((Math.pow(-k,2)+Math.pow(-k,2))/(-2*Math.pow(sigma,2))));
        //System.out.println(peak);
        for(int i = 0; i<output.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                double factor = 1/(Math.PI*2*Math.pow(sigma,2));
                double exp = Math.exp((Math.pow(i-k,2)+Math.pow(j-k,2))/(-2*Math.pow(sigma,2)));
                double out = peak*exp;
                output[i][j] = Math.round(out);
            }
        }
        return output;
    }


    public static void main(String[] args) {
       // BufferedImage img = importImage("/Users/beckaberhanu/Desktop/Academic/College/Activities/Personal Projects/ImageProcessing/DeulanayTriangulator/res/randpic.jpg");
        //displayImage(img);
        //displayImage(convertToImage(arrayResize(imageConvertor(img),1000,600)));
        //print3dArray(imageConvertor(img));
        //System.out.println("waaa"+(Color.green.getRGB()));//>>6&0xff));
        //print3dArray(arrayResize(imageConvertor(img),100,100),10);
        print2dArray(gausianGenerator(15,7));
    }
}
