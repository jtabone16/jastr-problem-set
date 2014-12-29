//**************************************************
// prob1.scala
//
// findMostConsecutivelyRepeatingValue (numbers: Array[Int]) : Int
//   A function where the input is an array of integers and the output is the value that
//   appears the most times consecutively (in a row). Ex: [1, 2, 2, 5, 5, 5, 2, 2] => 5
//
// findMostFrequentValue (numbers: Array[Int]) : Int
//   A function where the input is an array of integers and the output is the value that
//   appears most frequently. Ex: [1, 2, 2, 5, 5, 5, 2, 2] => 2
//
// Created by John Tabone 
//**************************************************

import scala.collection
import scala.io.Source._

object prob1 {

  def main(args: Array[String]) {

    var input = readLine("Enter a list of integers(separated by whitespace)")
    val numbers = input.split(" ").map(_.toInt)

    println("Most Consecutively Repeating Value: \n")
    var a = findMostConsecutivelyRepeatingValue(numbers)
    println(a)

    println("Most Frequent Value: \n")
    var b = findMostFrequentValue(numbers)
    println(b)


  }

  def findMostConsecutivelyRepeatingValue (numbers: Array[Int]) : Int = {
    var max = 0
    var maxCount = 0
    var currentCount = 1
    var previous = None : Option[Int]

    numbers.foreach{
      num =>
        previous match {
          case Some(value) => {
            if (value == num){
              currentCount = currentCount + 1
            }
            else {
              currentCount = 1
            }
          }
          case None => 
        }

        if (currentCount > maxCount){
          maxCount = currentCount
          max = num
        }
        previous = Some(num)
    }

    return max

  }

  def findMostFrequentValue (numbers: Array[Int]) : Int = {

    var maxVal = 0
    var maxKey = 0
    val map = scala.collection.mutable.Map[Int, Int]()

    numbers.foreach {
      num =>
        if (map.contains(num)) {
          map(num) = map(num) + 1
        }
        else{
          map(num) = 0
        }
    }

    map.foreach{case (k,v) =>
      if (v > maxVal){
          maxVal = v
          maxKey = k
        }
    }

    return maxKey
  }
}
    
