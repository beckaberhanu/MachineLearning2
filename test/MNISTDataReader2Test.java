import java.io.IOException;

public class MNISTDataReader2Test {
    static int labelCheck(Matrix2d a){
        int value = 0;
        for(int i = 0; i < a.xSpan; i++){
            if(a.get(0,i) == 1){
                value = i;
                break;
            }
        }
        return value;
    }
    public static void main(String[] args) throws IOException {
        String resDir = "/Users/beckaberhanu/Desktop/Academic/College/Activities/Personal Projects/Machine Learning 2/res";
        String image = resDir + "/train-images-idx3-ubyte (1)";
        String label = resDir + "/train-labels-idx1-ubyte";
        Matrix2d[] data = MNISTDataReader2.loadData(image);
        MNISTDataReader2.resizeArray(data,28,28);
        System.out.println(data[50481]);
        int[][][] pic = data[50481].to3dArray();
        //ImageAs3DArray.displayImage(ImageAs3DArray.convertToImage(ImageAs3DArray.arrayResize(pic,400,400)));
        Matrix2d[] labels = MNISTDataReader2.loadLabels(label);
        System.out.println(labelCheck(labels[50481]));
    }
}



