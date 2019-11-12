import org.knowm.xchart.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class NeuralNetwork2 {
    Matrix2d [] data;
    Matrix2d [] labels;
    Matrix2d [] weights;
    double lR;
    boolean bias;
    double [][] lossHistory;
    public NeuralNetwork2(int [] arr, double learningRate, boolean bias, String dataDirectory, String labelDirectory) throws IOException {
        /*
        Initializes the neural network and imports its learning data and labels from a local directory. This Initializer only works
        reliably for the MNIST dataset. Could work for other datasets if encoded in the same manner.

        Parameters: arr[] -> array describing the desired dimension of the input layer, hidden layers and output layer.
                    learningRate -> a double describing the rate of gradient descent.
                    bias -> boolean that specify's whether to add a bias to each layer of the neural network.
                    dataDirectory -> String referencing location of training data.
                    labelDirectory -> String referencing location of training labels.
        Other info: random number generator has been set to a specific seed. comment out or remove the "r.setSeed()" method when
                    not debugging.
        */
        data = MNISTDataReader2.loadData(dataDirectory);
        labels = MNISTDataReader2.loadLabels(labelDirectory);
        weights = new Matrix2d[arr.length-1];
        Random r = new Random();
        r.setSeed(1217);
        this.bias = bias;
        for(int i = 0; i < arr.length-2; i++){
            if(this.bias){
                weights[i] = new Matrix2d(arr[i]+1,arr[i+1]+1);
                weights[i].gausianPopulate(r);
            }
            else{
                weights[i] = new Matrix2d(arr[i],arr[i+1]);
                weights[i].gausianPopulate(r);
            }

        }
        if(this.bias){
            weights[arr.length-2] = new Matrix2d(arr[arr.length-2]+1,arr[arr.length-1]);
        }
        else{
            weights[arr.length-2] = new Matrix2d(arr[arr.length-2],arr[arr.length-1]);
        }
        weights[arr.length-2].gausianPopulate(r);
        lR = learningRate;
    }

    public NeuralNetwork2(Matrix2d [] arr, double learningRate, boolean bias, Matrix2d[] data1, Matrix2d [] label) throws IOException {
        /*
        Initializes the neural network. Imports its learning data and labels as Matrix2d arrays. This initializer isn't restricted
        to the MNIST dataset. Should work fine as long as the data and labels are arrays of Matrix2d objects.

        Parameters: arr[] -> array specifying the weights for all edges connecting the neural network. Each index specifies the weights
                    between corresponding consecutive layers using a Matrix2d Object.
                    learningRate -> a double describing the rate of gradient descent.
                    bias -> boolean that specify's whether to add a bias to each layer of the neural network.
                    data1 -> Array containing the training data as Matrix2d matrices.
                    label -> Array containing the labels as Matrix2d matrices.
        Other info: there is a chance the bias might get properly integrated. ****check to make sure.
        */
        data = data1;
        labels = label;
        weights = arr;
        lR = learningRate;
        this.bias = bias;
    }

    public NeuralNetwork2(int [] arr, double learningRate, boolean bias, Matrix2d[] data1, Matrix2d [] label) throws IOException {
        /*
        Initializes the neural network. Imports its learning data and labels as Matrix2d arrays. This initializer isn't restricted
        to the MNIST dataset. Should work fine as long as the data and labels are arrays of Matrix2d objects.

        Parameters: arr[] -> array describing the desired dimension of the input layer, hidden layers and output layer.
                    learningRate -> a double describing the rate of gradient descent.
                    bias -> boolean that specify's whether to add a bias to each layer of the neural network.
                    data1 -> Array containing the training data as Matrix2d matrices.
                    data1 -> Array containing the labels as Matrix2d matrices.
        Other info: random number generator has been set to a specific seed. comment out or remove the "r.setSeed()" method when
                    not debugging.
        */
        data = data1;
        labels = label;
        this.bias = bias;
        weights = new Matrix2d[arr.length-1];
        Random r = new Random();
        //r.setSeed(200);
        for(int i = 0; i < arr.length-2; i++){
            r.setSeed(100+i);
            if(this.bias){
                weights[i] = new Matrix2d(arr[i]+1,arr[i+1]+1);
                weights[i].gausianPopulate(r);
            }
            else{
                weights[i] = new Matrix2d(arr[i],arr[i+1]);
                weights[i].gausianPopulate(r);
            }

        }
        if(this.bias){
            weights[arr.length-2] = new Matrix2d(arr[arr.length-2]+1,arr[arr.length-1]);
        }
        else{
            weights[arr.length-2] = new Matrix2d(arr[arr.length-2],arr[arr.length-1]);
        }
        weights[arr.length-2].gausianPopulate(r);
        lR = learningRate;

    }

    public void forwardPass(Matrix2d [] activations){
        /*
        Takes in a an array the keeps track the layer activations. The "activations" array starts with only the
        input layers activated the remaining layers start off as empty. This function then propagates the input activations
        forward all the way to the output layer based on the weights that the network was initialized with.
         */
        if(bias) {
            //Appends a node to the input layer that will be used as a bias node. Temporarily stores the new copy of the input layer as
//          //a variable called image(bad name) and replaces the original.
            Matrix2d image; //bad naming practice. remember to change
            image = new Matrix2d(1, activations[0].xSpan + 1);
            for (int j = 0; j < activations[0].xSpan; j++) {
                image.set(0, j, activations[0].get(0, j));
            }
            activations[0] = image;
        }
        for(int i = 0; i < activations.length-1; i++){
            if(bias){
                //if the network has a bias it will set the last node of the input layer to 1. An empty extra node will have already been
                // added to the end of input layer by the if statement above.
                activations[i].set(0,activations[i].xSpan-1,1);
            }
            // a sigmoid activation function is used for propagating the activations forward.
            activations[i+1] = sigmoid(MatricOpr.cross(activations[i],weights[i]));
        }
    }

    public void backPropagate(Matrix2d [] activations, Matrix2d error){
        /*
        Takes in an array of activations along with the prediction error as input and performs a single step of gradient descent on all the
        weights such that the network can minimize its loss. The extent of the descent is scaled by the learning rate. Lower learning rates
        prevent the algorithm from overshooting it's "global minima" but it could also get stuck at a "local minima" if the learning rate is
        too low.
         */
        Matrix2d [] change = new Matrix2d[weights.length]; // an array of matrices that keeps track of the loss minimizing change for each weight
        Matrix2d [] layers = new Matrix2d[weights.length]; // an array of matrices that keeps track of the derivative of the loss with respect to te input to an activation layer
        layers [layers.length-1] = MatricOpr.Multiply(sigDir(activations[activations.length-1]),error);
        for(int i = layers.length-2; i>= 0; i--){
            layers[i] = MatricOpr.Multiply(sigDir(activations[i+1]),MatricOpr.cross(layers[i+1],MatricOpr.transpose(weights[i+1])));
        }
        for(int i = change.length-1; i>= 0; i--){
            change[i] = MatricOpr.cross(MatricOpr.transpose(activations[i]),layers[i]);
        }
        for(int i = 0; i<weights.length; i++){
            weights[i] = MatricOpr.subtract(weights[i],MatricOpr.scale(change[i],lR));
            //scales the loss minimizing changes by the learning rate and updates the neural networks weights
        }
    }

    public void fit(double ratio, int epoch, int freq){
        /*
            This function, when called tries to fit the model to the data one data point at a time. It will train the model on the same data for some number of repetitions
            specified by the variable "epoch".
            ratio -> ratio of training data to testing data
            epoch -> number of times to train the model on the same data
            freq -> Integer specifying frequency of feedback
         */
        lossHistory = new double[2][epoch];
        int limit = data.length - (int)(data.length/(1+ratio));//represent the boundary for the portion of the data dedicated to training.
        for(int j = 0; j<epoch; j++) {
            if (j % freq == 0) { // gives feedback on the models loss from at the beginning of some epoch. The frequency of the feedback is determined by the variable "freq"
                System.out.println("epoch = " + j + " || loss = " + report(limit));
            }
            lossHistory[0][j] = report(limit);
            lossHistory[1][j] = j;
            for (int i = 0; i < limit; i++) {
//            for (int i = 10000; i < 60000; i++) {
                fitPartial(data[i], labels[i]); //fits the model to a single data point from our training data.
            }

        }
    }
    public double report(int limit){ // name of function could use an improvement. "report" is too general
        /*
            This function, when called gives feedback on the average loss of a model over 50 randomly selected data points in the testing set.
            limit -> integer representing the lower boundary of the testing set
         */
        Random rand = new Random();
        Matrix2d loss;
        int r;
        double sum = 0;
        for(int i = 0; i<50   ;i++){
            r = rand.nextInt(data.length - limit - 1) + limit; // generates a random index within the boundaries of the testing set.
            loss = calcLoss(predict(data[i]), labels[i]); // calculates the loss for the "rth" data point in training data
            sum += MatricOpr.sumAll(MatricOpr.abs(loss))/loss.xSpan; // calculating the average loss over each node in the output layer and adding it to an accumulator.
        }
        sum = sum/50; // finds the average loss over the 50 random data points.
        return sum; // returns the average loss as a string
    }

    public void fitPartial(Matrix2d digit, Matrix2d label){ // Name the parameter "digit" differently. Its too specific to numbers.
        /*
            This function when called fits the model to a single data point from our training data.
            digit -> data point over which to fit the model
            label -> label corresponding to the data point
         */
        Matrix2d [] activations = new Matrix2d[weights.length+1]; // initializes an array of activations over which to feed forward activations
        activations[0] = digit; // assigns the first index to the input layer
        forwardPass(activations); // propagates activations forth over consecutive layers to generate an output
        backPropagate(activations,calcLoss(activations[activations.length-1],label)); //performs a single step of gradient descent
    }

    public Matrix2d predict(Matrix2d input){
        /*
            This function, when called, makes a prediction using the current state of the model and outputs a matrix of predictions.
            input -> input layer.
         */
        Matrix2d [] activations = new Matrix2d[weights.length+1];
        activations[0] = input;
        forwardPass(activations);
        Matrix2d output = activations[activations.length-1];
        return output;
    }

    public Matrix2d calcLoss(Matrix2d prediction, Matrix2d label){
        /*
            Takes a prediction and a target output and calculates the loss.
         */
        Matrix2d output = MatricOpr.scale(MatricOpr.subtract(label,prediction),-1);
        return output;
    }

    public Matrix2d sigmoid(Matrix2d a){
        /*
            Applies the sigmoid activation function to every element within a matrix.
            a -> The matrix for which the sigmoid activation is being calculated.
         */
        Matrix2d output = new Matrix2d(a.ySpan,a.xSpan);
        for(int i = 0; i<a.ySpan; i++){
            for(int j = 0; j<a.xSpan; j++){
                double num = a.get(i,j);
                output.set(i,j,1/(1+Math.exp(-num))); // "1/(1+Math.exp(-num))" is the sigmoid activation function
            }
        }
        return output;
    }

    public Matrix2d sigDir(Matrix2d a){
        /*
            Finds the derivative to a sigmoid activation for every element in a matrix.
            a -> The matrix for which the derivative is being calculated.
         */
        Matrix2d output = new Matrix2d(a.ySpan,a.xSpan);
        for(int i = 0; i<a.ySpan; i++){
            for(int j = 0; j<a.xSpan; j++){
                double num = a.get(i,j);
                output.set(i,j,num*(1-num)); // "num*(1-num)" is the sigmoid activation function
            }
        }
        return output;
    }

    public static double simplify(Matrix2d a){ // find a better name to this function. Consider porting this to Matrix2d as "maxIndex".
        /*
            Finds the index with maximum element in a matrix.
            a -> The matrix for which the function finds the max index.
         */
        int maxIndex = 0;
        for(int i = 1; i < a.xSpan; i++){
            if(a.get(0,i)>a.get(0,maxIndex)){
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public void saveModel(String dir) throws IOException{
        /*
            Saves the current model to a file named after the variable "dir".
            dir-> name of file for the model
         */
        BufferedWriter wr = new BufferedWriter(new FileWriter(dir));
        String output = Integer.toString(weights.length)+"\n";
        for(int i = 0; i < weights.length; i++){
            output += "* "+weights[i].ySpan+" "+weights[i].xSpan+"\n"+weights[i].toString();
        }
        wr.write(output);
        wr.close();
    }

    public void loadModel(String dir) throws IOException{
        /*

         */
        BufferedReader re = new BufferedReader(new FileReader(dir));
        int length = Integer.parseInt(re.readLine());
        Matrix2d [] output = new Matrix2d[length];
        String line = re.readLine();
        int index = 0;
        int y = 0;
        Matrix2d in = null;
        while(line!=null){
            if(line.charAt(0) == '*'){
                in = new Matrix2d(Integer.parseInt("" +line.charAt(2)),Integer.parseInt("" +line.charAt(4)));
                y = 0;
            }
            else{
                String [] arr = line.split(" ");
                for(int x = 0; x<arr.length; x++){
                    in.set(y,x,Double.parseDouble(arr[x]));
                }
                if(y == in.ySpan-1){
                    output[index] = in;
                    index+=1;
                }
                y+=1;
            }
            line = re.readLine();
        }
        re.close();
        weights = output;
    }
    public double pA; //predictionAccuracy
    public double aL; //averageLoss;
    public String ctw; //correctTOwrong;
    public ArrayList<Integer> wp = new ArrayList<>(); //wrongPredictions; indexes for wrong predictions
    public String ttaTable = ""; //target To actual table
    public double ofi; //over-fitting index
    public void performanceAnalysis(double ratio){ // need to be more consistent with naming
        int limit = data.length - (int)(data.length/(1+ratio));
        int countErr= 0;
        int countAcc = 0;
        double totloss = 0;
        for(int i = limit; i < data.length; i++){
//        for(int i = 0; i < 10000; i++){
            Matrix2d prediction = this.predict(data[i]);
            Matrix2d actual = labels[i];
            Matrix2d loss = calcLoss(prediction,actual);
            totloss += MatricOpr.sumAll(MatricOpr.abs(loss))/loss.xSpan;// loss representation may need improvement. consider multidimensional distance (think fancy pythagorean theorem)
            //System.out.println(MatricOpr.sumAll(MatricOpr.abs(loss))/loss.xSpan);
            if(simplify(prediction)==simplify(actual)){
                countAcc += 1;
                ttaTable += "Correctness : True | target: " + simplify(actual) + " | prediction: " + simplify(prediction) + " | confidence: " + prediction.get(0,(int)simplify(prediction)) + "\n"; // confidence index is probably wrong but this should be enough for simple testing.
            }
            else{
                wp.add(i);
                countErr += 1;
                ttaTable += "Correctness : False | target: " + simplify(actual) + " | prediction: " + simplify(prediction) + " | confidence: " + prediction.get(0,(int)simplify(prediction))+ "\n";
            }
        }
        ctw = "correct: " + countAcc + " | wrong: " + countErr + " | total: " + (countAcc+countErr);
        pA = countAcc/(0.0+countAcc+countErr);
        aL = totloss/(0.0+countAcc+countErr);
        int countAcc2=0;
        int countErr2=0;

        for(int i = limit- (int)(data.length/(1+ratio)); i < limit; i++){
//        for(int i = 10000; i < 20000; i++){
            Matrix2d prediction = this.predict(data[i]);
            Matrix2d actual = labels[i];
            if(simplify(prediction)==simplify(actual)){
                countAcc2 += 1;
            }
            else{
                countErr2 += 1;
            }
        }
        double pA2 = countAcc2/(0.0+countAcc2+countErr2);
        System.out.println("unseen "+pA);
        System.out.println("seen "+pA2);
        ofi = pA2-pA;
    }
    public String getAccuracy(double ratio){
        int limit = data.length - (int)(data.length/(1+ratio));
        int countErr= 0;
        for(int i = limit; i < data.length; i++){

        }
        return null;
    }

    public double[][] getLossHistory() {
        return lossHistory;
    }

    public static void main(String[] args) throws IOException{
        String resDir = "/Users/bgeleto/Documents/IdeaProjects/MachineLearning2/res";
        String dataFilePath = resDir + "/train-images-idx3-ubyte (1)";
        String labelFilePath = resDir + "/train-labels-idx1-ubyte";
        Matrix2d[] data = MNISTDataReader2.loadData(dataFilePath);
        MNISTDataReader2.resizeArray(data,15,15);
        MNISTDataReader2.linearizeArray(data);

        Matrix2d[] labels = MNISTDataReader2.loadLabels(labelFilePath);
        int [] arr = {225,20,20,10};
        NeuralNetwork2 brain = new NeuralNetwork2(arr,0.01,true, data, labels);
        double ratio  = 10./2.;
        //brain.loadModel("001");
        brain.fit(ratio,100,1);
        //brain.saveModel("1");
        brain.performanceAnalysis(ratio);
        System.out.println("*********************************************************************************************************************");
        System.out.println("average loss \n"+brain.aL);
        System.out.println("*********************************************************************************************************************");
        System.out.println("prediction accuracy \n"+brain.pA);
        System.out.println("*********************************************************************************************************************");
        System.out.println("correct to wrong \n"+brain.ctw);
        System.out.println("*********************************************************************************************************************");
        System.out.println("over-fitting index \n"+brain.ofi);
        System.out.println("*********************************************************************************************************************");
        //System.out.println("target to actual Table \n"+brain.ttaTable);
        System.out.println("*********************************************************************************************************************");

        final XYChart chart = QuickChart.getChart("Simple XChart Real-time Demo", "Epoch", "Loss", "loss", brain.getLossHistory()[1], brain.getLossHistory()[0]);
        // Show it
        final SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);
        sw.displayChart();
    }
}
