//
//  FileReader.swift
//  Ann
//
//  Created by Alexandru Cristian Donea on 22/05/2018.
//  Copyright © 2018 University Projects. All rights reserved.
//

import Foundation


class FileReader{
    
    
    class func read(fileName:String)->String{
        let dir = try? FileManager.default.url(for: .documentDirectory,
                                               in: .userDomainMask, appropriateFor: nil, create: true)
     
        if let fileURL = dir?.appendingPathComponent(fileName).appendingPathExtension("txt") {
            

//            let outString = "Write this text to the file"
//            do {
//                try outString.write(to: fileURL, atomically: true, encoding: .utf8)
//            } catch {
//                print("Failed writing to URL: \(fileURL), Error: " + error.localizedDescription)
//            }
//

            var inString = ""
            do {
                inString = try String(contentsOf: fileURL)
            } catch {
                print("Failed reading from URL: \(fileURL), Error: " + error.localizedDescription)
            }
            return inString
        }
        else{
            return "Error file"
        }
    }
    
    
    
}
