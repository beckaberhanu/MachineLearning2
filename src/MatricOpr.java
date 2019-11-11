
public class MatricOpr {
    public static Matrix2d cross(Matrix2d a, Matrix2d b){
        if(a.xSpan != b.ySpan) {
            System.out.println("Error(function: cross) -> a.xSpan != b.ySpan");
            System.out.println(a.xSpan);
            System.out.println(b.ySpan);
            assert a.xSpan == b.ySpan;
        }
        Matrix2d output = new Matrix2d(a.ySpan,b.xSpan);
        for(int i = 0; i<b.xSpan; i++){
            for(int j = 0; j<a.ySpan; j++){
                double out = 0;
                for(int k = 0; k<a.xSpan; k++){
                    out += a.get(j,k)*b.get(k,i);
                }
                output.set(j,i,out);
            }
        }
        return output;
    }
    public static Matrix2d transpose(Matrix2d a){
        Matrix2d output = new Matrix2d(a.xSpan,a.ySpan);
        for(int i = 0; i < a.ySpan; i++){
            for(int j = 0; j < a.xSpan; j++){
                output.set(j,i,a.get(i,j));
            }
        }
        return output;
    }
    public static Matrix2d normalize(Matrix2d a){
        double max = a.findMax();
        if(max == 0){
            return a;
        }
        else {
            Matrix2d output = new Matrix2d(a.ySpan, a.xSpan);
            for (int i = 0; i < a.ySpan; i++) {
                for (int j = 0; j < a.xSpan; j++) {
                    output.set(i, j, (a.get(i, j) / max));
                }
            }
            return output;
        }
    }
    public static Matrix2d Multiply(Matrix2d a, Matrix2d b){
        if(a.xSpan != b.xSpan || a.ySpan != b.ySpan) {
            System.out.println("Error(function: Multiply) -> dimensions of matrices is not equal");
            assert a.ySpan == b.ySpan && a.xSpan == b.xSpan;
        }
        Matrix2d output = new Matrix2d(a.ySpan,a.xSpan);
        for(int i = 0; i < a.ySpan; i++){
            for(int j = 0; j< b.xSpan; j++){
                output.set(i,j,a.get(i,j)*b.get(i,j));
            }
        }
        return output;
    }
    public static Matrix2d add(Matrix2d a, Matrix2d b){
        if(a.xSpan != b.xSpan || a.ySpan != b.ySpan) {
            System.out.println("Error(function: add) -> dimensions of matrices is not equal");
            assert a.ySpan == b.ySpan && a.xSpan == b.xSpan;
        }
        Matrix2d output = new Matrix2d(a.ySpan,a.xSpan);
        for(int i = 0; i < a.ySpan; i++){
            for(int j = 0; j< b.xSpan; j++){
                output.set(i,j,a.get(i,j)+b.get(i,j));
            }
        }
        return output;
    }
    public static Matrix2d subtract(Matrix2d a, Matrix2d b){
        if(a.xSpan != b.xSpan || a.ySpan != b.ySpan) {
            System.out.println("Error(function: subtract) -> dimensions of matrices is not equal");
            assert a.ySpan == b.ySpan && a.xSpan == b.xSpan;
        }
        Matrix2d output = new Matrix2d(a.ySpan,a.xSpan);
        for(int i = 0; i < a.ySpan; i++){
            for(int j = 0; j< b.xSpan; j++){
                output.set(i,j,a.get(i,j)-b.get(i,j));
            }
        }
        return output;
    }
    public static double dot(Matrix2d a, Matrix2d b){
        if(a.xSpan != b.xSpan || a.ySpan != b.ySpan) {
            System.out.println("Error(function: dot) -> dimensions of matrices is not equal");
            assert a.ySpan == b.ySpan && a.xSpan == b.xSpan;
        }
        double output = 0;
        for(int i = 0; i<a.ySpan; i++){
            for(int j = 0; j<a.xSpan; j++){
                output += a.get(i,j)*b.get(i,j);
            }
        }
        return output;
    }
    public static Matrix2d scale (Matrix2d a, double b){
        Matrix2d output = new Matrix2d(a.ySpan,a.xSpan);
        for(int i = 0; i<a.ySpan; i++){
            for(int j = 0; j<a.xSpan; j++){
                output.set(i,j,a.get(i,j)*b);
            }
        }
        return output;
    }
    public static double sumAll (Matrix2d a){
        double sum = 0;
        for(int i = 0; i<a.ySpan; i++){
            for(int j = 0; j<a.xSpan; j++){
                sum += a.get(i,j);
            }
        }
        return sum;
    }
    public static Matrix2d abs (Matrix2d a){
        Matrix2d output = new Matrix2d(a.ySpan,a.xSpan);
        for(int i = 0; i<a.ySpan; i++){
            for(int j = 0; j<a.xSpan; j++){
                output.set(i,j,Math.abs(a.get(i,j)));
            }
        }
        return output;
    }


}
