public class Matrix2dTest {

    public static void main(String[] args) {
        double [][] arr1 = {{0,0,0,0},{0,0,0,0}};
        double [][] arr2 = {{1,1,1,1},{1,1,1,1}};
        double [][] arr3 = {{1,1,1},{1,1,1},{1,1,1}};
        double [][] arr4 = {{1,10,9},{11,1,2},{5,9,10}};
        double [][] arr5 = {{1,2},{2,1}};
        double [][] arr6 = {{0.5,10,9},{1,1,0},{5,9,10},{1,.2,0}};
        double [][] arr7 = {{1,2},{2,1},{1,2},{2,1}};
        Matrix2d mat1 = new Matrix2d(arr1);
        Matrix2d mat2 = new Matrix2d(arr2);
        Matrix2d mat3 = new Matrix2d(arr3);
        Matrix2d mat4 = new Matrix2d(arr4);
        Matrix2d mat5 = new Matrix2d(arr5);
        Matrix2d mat6 = new Matrix2d(arr6);
        Matrix2d mat7 = new Matrix2d(arr7);

        System.out.println("testCross:-> *******************************************");
        System.out.println(MatricOpr.cross(mat1,mat7));
        System.out.println(MatricOpr.cross(mat2,mat7));
        System.out.println(MatricOpr.cross(mat3,mat4));
        System.out.println(MatricOpr.cross(mat7,mat5));

        System.out.println("testTranspose:-> *******************************************");
        System.out.println(MatricOpr.transpose(mat1));
        System.out.println(MatricOpr.transpose(mat3));
        System.out.println(MatricOpr.transpose(mat7));
        System.out.println(MatricOpr.transpose(mat2));

        System.out.println("testNormalize:-> *******************************************");
        System.out.println(MatricOpr.normalize(mat2).findMax());
        System.out.println(MatricOpr.normalize(mat4).findMax());
        System.out.println(MatricOpr.normalize(mat1).findMax());
        System.out.println(MatricOpr.normalize(mat3).findMax());

        System.out.println("testMultiply:-> *******************************************");
        System.out.println(MatricOpr.Multiply(mat1,mat2));
        System.out.println(MatricOpr.Multiply(mat3,mat4));
        System.out.println(MatricOpr.Multiply(mat4,mat3));
        System.out.println(MatricOpr.Multiply(mat2,mat1));

        System.out.println("testAdd:-> *******************************************");
        System.out.println(MatricOpr.add(mat1,mat2));
        System.out.println(MatricOpr.add(mat3,mat4));
        System.out.println(MatricOpr.add(mat4,mat3));
        System.out.println(MatricOpr.add(mat2,mat1));

        System.out.println("testSubtract:-> *******************************************");
        System.out.println(MatricOpr.subtract(mat1,mat2));
        System.out.println(MatricOpr.subtract(mat3,mat4));
        System.out.println(MatricOpr.subtract(mat4,mat3));
        System.out.println(MatricOpr.subtract(mat2,mat1));

        System.out.println("testDot:-> *******************************************");
        System.out.println(MatricOpr.dot(mat1,mat2));
        System.out.println(MatricOpr.dot(mat3,mat4));
        System.out.println(MatricOpr.dot(mat4,mat3));
        System.out.println(MatricOpr.dot(mat2,mat1));

        System.out.println("testScale:-> *******************************************");
        System.out.println(MatricOpr.scale(mat1,2));
        System.out.println(MatricOpr.scale(mat3,10));
        System.out.println(MatricOpr.scale(mat4,5));
        System.out.println(MatricOpr.scale(mat2,0));

        System.out.println("testGausianPopulate:-> *******************************************");
        Matrix2d m = new Matrix2d(200,200);
        m.gausianPopulate();
        System.out.println(m.get(100,100));
        System.out.println(MatricOpr.sumAll(m));
        System.out.println(m.average());
        System.out.println(m.returnself());
    }
}
