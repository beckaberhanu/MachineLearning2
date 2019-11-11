import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class MNISTDataReader2 {
    public static Matrix2d[] loadData (String dataFilePath) throws IOException {
        /*
            This function loads data from an MNIST dataset file (".ubyte") and outputs individual digits as an array of matrices.
            The matrices are added to the array as an object called Matrix2d.
         */
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFilePath)));
        int magicNumber = dataInputStream.readInt();
        int numberOfItems = dataInputStream.readInt();
        int nRows = dataInputStream.readInt();
        int nCols = dataInputStream.readInt();

//        System.out.println("magic number is " + magicNumber);
//        System.out.println("number of items is " + numberOfItems);
//        System.out.println("number of rows is: " + nRows);
//        System.out.println("number of cols is: " + nCols);

        Matrix2d [] output = new Matrix2d[numberOfItems];

        for(int i = 0; i < numberOfItems; i++) {
            Matrix2d Matrix= new Matrix2d(nRows, nCols);
            for (int r = 0; r < nRows; r++) {
                for (int c = 0; c < nCols; c++) {
                    Matrix.set(r, c, dataInputStream.readUnsignedByte());
                }
            }
            output[i] = Matrix;
        }
        dataInputStream.close();
        return output;
    }

    public static Matrix2d [] loadLabels (String labelFilePath) throws IOException {
        /*
            This function loads labels from an MNIST dataset file (".ubyte") and outputs individual digits as an array of matrices.
            The matrices are added to the array as an object called Matrix2d. Every index in the matrix is set to 0, except the index
            equal to the label.
         */
        DataInputStream labelInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(labelFilePath)));
        int labelMagicNumber = labelInputStream.readInt();
        int numberOfLabels = labelInputStream.readInt();

        //System.out.println("labels magic number is: " + labelMagicNumber);
        //System.out.println("number of labels is: " + numberOfLabels);

        Matrix2d [] outputs = new Matrix2d[numberOfLabels];
        for(int i = 0; i<numberOfLabels; i++){
            int a = labelInputStream.readUnsignedByte();
            outputs[i] = new Matrix2d(1,10);
            outputs[i].set(0,a,1);
        }
        labelInputStream.close();

        return outputs;
    }

    public static void resizeArray(Matrix2d [] info, int y, int x){ //bad naming. find something else for "info". also consider renaming "resizeArray" its too vague
        /*
            This function resizes each Matrix in an array to a new dimension of "(y,x)"
         */
        for(int i = 0; i<info.length; i++){
            info[i] = resize(info[i],y,x);
        }
    }

    public static Matrix2d resize(Matrix2d a,int y, int x){ // consider making this a matrix2d method
        /*
            This function resizes an input matrix to a new dimension of "(y,x)". The output of this is not guaranteed to retain
            all the information from original matrix. It works the same way you would resize an image.
         */
        Matrix2d output = new Matrix2d(y,x);
        for(int i = 0; i<y; i++){
            for(int j = 0; j<x; j++){
                output.set(i,j,a.get(i*a.ySpan/y,j*a.xSpan/x));
            }
        }
        return output;
    }

    public static void linearizeArray(Matrix2d [] info){ // find better name for function
        for(int i = 0; i<info.length; i++){
            info[i] = linearize(info[i]);
        }
    }
    public static Matrix2d linearize(Matrix2d a){
        Matrix2d output = new Matrix2d(1,a.xSpan*a.ySpan);
        int index = 0;
        for(int i = 0; i < a.ySpan; i++){
            for(int j = 0; j < a.xSpan; j++){
                output.set(0,index,a.get(i,j));
                index+=1;
            }
        }
        //output.set(0,output.ySpan,1);
        return output;
    }


}
