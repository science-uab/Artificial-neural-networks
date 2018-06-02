//
//  main.swift
//  Ann
//
//  Created by Alexandru Cristian Donea on 22/05/2018.
//  Copyright © 2018 University Projects. All rights reserved.
//

import Foundation

let ann = Ann()
var trainingData=[[Double]]()
var target=[[Double]]()
var numData=Int()
var numOutputs=Int()
var numVariables=Int()
numVariables=3
numOutputs=4
//no needs to be allocated. That progres is automatically
//func allocateTrainingData(data:[[Double]], target:[[Double]], numTrainingData:Int, numVariables:Int, numOptputs:Int){
//    target.reserveCapacity(numTrainingData)
//    data.reserveCapacity(numTrainingData)
//    for i in 0...numTrainingData{
//        data[i].reserveCapacity(numVariables)
//        target[i].reserveCapacity(numOptputs)
//    }
//}


func readFile(){
    
    var file:String=FileReader.read(fileName: "Data/mnist_test.txt")
    let stdin = FileHandle.standardInput
    let inputString = NSString(data: stdin.availableData, encoding: String.Encoding.utf8.rawValue)
    for i in 0...numData{
        for j in 0...numVariables{
           // trainingData[i][j]=
            //I will update it
        }
        var classIndex:Int=0
        for j in 0...numOutputs{
            target[i][j]=0
            target[i][classIndex]=0
        }
        
    }
}


func f(){
    print("epoch=\(ann.getEpoch()) error=\(ann.getError())")
}


    print("Reading data....")
     print("Done")
    ann.setNumLayers(numLayers_: 3)
    ann.setNumNeurons(layerIndex: 0, numNeurons:numVariables)
    ann.setNumNeurons(layerIndex: 1, numNeurons: 4)
    ann.setNumNeurons(layerIndex: 2, numNeurons: numOutputs)
    ann.setLearningRate(newLearningRate: 0.1)
    ann.setNumIterations(numIterations_: 100000)
    ann.train(trainingData: trainingData, target: target, numData: numData)
    print("Error:\(ann.getError())")

    //delete data
    trainingData.removeAll()
    target.removeAll()
    numData=0





