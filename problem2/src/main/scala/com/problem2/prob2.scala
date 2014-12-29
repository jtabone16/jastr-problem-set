//**************************************************
// prob2.scala
// You are given a large set of log files (k > 100) and each line of the file has a
// timestamp and an error message. Each file has (n > 10000) lines. The lines in
// each file are sorted by timestamp in ascending order, naturally. I've provided
// 10 test log files and each file has 10000 lines. 
//
// The API for interacting with this data is the following:
// class LogLine {
//   long timestamp;
//   String message;
//  }
//  
//
// Created by John Tabone 
//**************************************************

import scala.collection
import scala.io.Source._
import java.io._

class LogLine (msg:String, time:Long) {
  var timestamp:Long = time
  var message:String = msg
}

object prob2 {

    var postLogList = scala.collection.mutable.ArrayBuffer[LogLine]()
    var preLogList = Array[String]()
    var totalNumOfLogLines:Int = 0
    var totalNumOfLogs:Int = 0

  def main(args: Array[String]) {

    appendLogLinesToOutput(postLogList)
    println("Total number of log files: " + totalNumOfLogs)
    println("Total number of log lines in finalLog.txt: " + totalNumOfLogLines)

  }


  def getTotalNumberOfLogFiles(): Int = {
    var input = scala.io.StdIn.readLine("Enter the directory where the log files are located (as .txt file)")
    var files = new java.io.File(input).listFiles.filter(_.getName.endsWith(".txt"))
    files.foreach {
      file =>
        preLogList = preLogList:+file.toString
    }
    return preLogList.length
  }

  def getTotalNumberOfLinesInFile(k:Int) : Int = {
    var log:String = preLogList(k)
    var numLines:Int = 0

    for(line <- fromFile(log, "UTF-8").getLines()) {
        numLines = numLines + 1
      }

    return numLines
  }

  def readAllLogLinesFromFile(k:Int){
    var log:String = preLogList(k)

    for(line <- fromFile(log, "UTF-8").getLines()) {
      var numsInLogLine = line.split("[^0-9]").filter(_.length>0).map(_.toLong)
      var timestamp = numsInLogLine.max
      var error = line.replace(timestamp.toString, "")
      var logLine = new LogLine(error, timestamp)
      postLogList+=logLine
    }

  }

  def appendLogLinesToOutput(lines: scala.collection.mutable.ArrayBuffer[LogLine]) {
    totalNumOfLogs = getTotalNumberOfLogFiles()
    preLogList.foreach{
      log =>
        var numofLinesInLog = getTotalNumberOfLinesInFile(preLogList.indexOf(log))
        totalNumOfLogLines+=numofLinesInLog
        readAllLogLinesFromFile(preLogList.indexOf(log))
    }
  
    lines sortBy (_.timestamp) 

    val pw = new PrintWriter(new File("finalLog.txt" ))

    lines.foreach{
      logLine =>
        var logString = logLine.timestamp.toString + logLine.message + '\n'
        pw.write(logString)
    }

    pw.close

  }

}