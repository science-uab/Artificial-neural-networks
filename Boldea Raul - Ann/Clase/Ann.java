package com.artificial.neuronal.network;

/**
 * @author RBelze
 */

public class Ann {

 /**
  * Declararea erorii de antrenare
  */
 protected double globalError;

 /**
  * Numarul de neuroni de intrare.
  */
 protected int inputCount;

 /**
  * Numarul de neuroni ascunsi.
  */
 protected int hiddenCount;

 /**
  * NUmarul neuronilor de iesire
  */
 protected int outputCount;

 /**
  * Numarul total de neuroni din retea.
  */
 protected int neuronCount;

 /**
  * Numarul legaturilor din retea
  */
 protected int weightCount;

 /**
  * Rata de invatare.
  */
 protected double learnRate;

 /**
  * Output
  */
 protected double fire[];

 /**
  * Matricea legaturilor 
  */
 protected double matrix[];

 /**
  * Eroarea ultimului calcul.
  */
 protected double error[];

 /**
  * Matricea de acumulare
  */
 protected double accMatrixDelta[];

 /**
  * Pragul, aceasta valoare, impreuna cu matriceade acumulare poate fi invatata de neuron
  */
 protected double thresholds[];

 /**
  * Valoarea ce va fi aplicata in calculul rezultatului matricei
  */
 protected double matrixDelta[];

 protected double accThresholdDelta[];

 protected double thresholdDelta[];

 /**
  * Momentul de invatare.
  */
 protected double momentum;

 /**
  * Schimbarea in erori.
  */
 protected double errorDelta[];


 /**
  * Construirea retelei neuronale
  *
  * @param inputCount Numar neuroni de intrare.
  * @param hiddenCount Numarul neuronilor ascunsi
  * @param outputCount Numarul neuronilor de iesire
  * @param learnRate Rata de invatare.
  */
 public Ann(int inputCount,
         int hiddenCount,
         int outputCount,
         double learnRate,
         double momentum) {

  this.learnRate = learnRate;
  this.momentum = momentum;

  this.inputCount = inputCount;
  this.hiddenCount = hiddenCount;
  this.outputCount = outputCount;
  neuronCount = inputCount + hiddenCount + outputCount;
  weightCount = (inputCount * hiddenCount) + (hiddenCount * outputCount);

  fire    = new double[neuronCount];
  matrix   = new double[weightCount];
  matrixDelta = new double[weightCount];
  thresholds = new double[neuronCount];
  errorDelta = new double[neuronCount];
  error    = new double[neuronCount];
  accThresholdDelta = new double[neuronCount];
  accMatrixDelta = new double[weightCount];
  thresholdDelta = new double[neuronCount];

  reset();
 }



 /**
  * Returneaza radacina patrata a erorilor pentru un set complet de training
  * @param lungimea unui set complet de training.
  * @return Eroarea curenta pentru reteaua neuronala.
  */
 public double getError(int len) {
  double err = Math.sqrt(globalError / (len * outputCount));
  globalError = 0; // clear the accumulator
  return err;

 }

 public double threshold(double sum) {
  return 1.0 / (1 + Math.exp(-1.0 * sum));
 }

 public double []computeOutputs(double input[]) {
  int i, j;
  final int hiddenIndex = inputCount;
  final int outIndex = inputCount + hiddenCount;

  for (i = 0; i < inputCount; i++) {
   fire[i] = input[i];
  }

  // first layer
  int inx = 0;

  for (i = hiddenIndex; i < outIndex; i++) {
   double sum = thresholds[i];

   for (j = 0; j < inputCount; j++) {
    sum += fire[j] * matrix[inx++];
   }
   fire[i] = threshold(sum);
  }

  // hidden layer

  double result[] = new double[outputCount];

  for (i = outIndex; i < neuronCount; i++) {
   double sum = thresholds[i];

   for (j = hiddenIndex; j < outIndex; j++) {
    sum += fire[j] * matrix[inx++];
   }
   fire[i] = threshold(sum);
   result[i-outIndex] = fire[i];
  }

  return result;
 }

 public void calcError(double ideal[]) {
  int i, j;
  final int hiddenIndex = inputCount;
  final int outputIndex = inputCount + hiddenCount;

  // clear hidden layer errors
  for (i = inputCount; i < neuronCount; i++) {
   error[i] = 0;
  }

  // layer errors and deltas for output layer
  for (i = outputIndex; i < neuronCount; i++) {
   error[i] = ideal[i - outputIndex] - fire[i];
   globalError += error[i] * error[i];
   errorDelta[i] = error[i] * fire[i] * (1 - fire[i]);
  }

  // hidden layer errors
  int winx = inputCount * hiddenCount;

  for (i = outputIndex; i < neuronCount; i++) {
   for (j = hiddenIndex; j < outputIndex; j++) {
    accMatrixDelta[winx] += errorDelta[i] * fire[j];
    error[j] += matrix[winx] * errorDelta[i];
    winx++;
   }
   accThresholdDelta[i] += errorDelta[i];
  }

  // hidden layer deltas
  for (i = hiddenIndex; i < outputIndex; i++) {
   errorDelta[i] = error[i] * fire[i] * (1 - fire[i]);
  }

  // input layer errors
  winx = 0; // offset into weight array
  for (i = hiddenIndex; i < outputIndex; i++) {
   for (j = 0; j < hiddenIndex; j++) {
    accMatrixDelta[winx] += errorDelta[i] * fire[j];
    error[j] += matrix[winx] * errorDelta[i];
    winx++;
   }
   accThresholdDelta[i] += errorDelta[i];
  }
 }

 public void learn() {
  int i;

  // process the matrix
  for (i = 0; i < matrix.length; i++) {
   matrixDelta[i] = (learnRate * accMatrixDelta[i]) + (momentum * matrixDelta[i]);
   matrix[i] += matrixDelta[i];
   accMatrixDelta[i] = 0;
  }

  // process the thresholds
  for (i = inputCount; i < neuronCount; i++) {
   thresholdDelta[i] = learnRate * accThresholdDelta[i] + (momentum * thresholdDelta[i]);
   thresholds[i] += thresholdDelta[i];
   accThresholdDelta[i] = 0;
  }
 }

 public void reset() {
  int i;

  for (i = 0; i < neuronCount; i++) {
   thresholds[i] = 0.5 - (Math.random());
   thresholdDelta[i] = 0;
   accThresholdDelta[i] = 0;
  }
  for (i = 0; i < matrix.length; i++) {
   matrix[i] = 0.5 - (Math.random());
   matrixDelta[i] = 0;
   accMatrixDelta[i] = 0;
  }
 }
}