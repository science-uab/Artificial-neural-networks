package com.artificial.neuronal.network;

import java.text.*;

public class TestNeuronalNetwork {
 public static void main(String args[])
 {
  double xorInput[][] =
  {
   {0.0,0.0},
   {1.0,0.0},
   {0.0,1.0},
   {1.0,1.0}};

  double xorIdeal[][] =
  { {0.0},{1.0},{1.0},{0.0}};

  System.out.println("Learn:");

  Ann network = new Ann(2,3,1,0.7,0.9);

  NumberFormat percentFormat = NumberFormat.getPercentInstance();
  percentFormat.setMinimumFractionDigits(4);


  for (int i=0;i<23;i++) {
   for (int j=0;j<xorInput.length;j++) {
    network.computeOutputs(xorInput[j]);
    network.calcError(xorIdeal[j]);
    network.learn();
   }
   System.out.println( "Incercarea #" + i + ",Eroarea de estimare:" +
             percentFormat .format(network.getError(xorInput.length)) );
  }

  System.out.println("Rezultat:");

  for (int i=0;i<xorInput.length;i++) {

   for (int j=0;j<xorInput[0].length;j++) {
    System.out.print( xorInput[i][j] +":" );
   }

   double out[] = network.computeOutputs(xorInput[i]);
   System.out.println("="+out[0]);
  }
 }
}