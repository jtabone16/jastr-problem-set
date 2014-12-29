//**************************************************
// prob3.scala
// Write a function where the input is a binary search tree of integers and the
// output is the Nth-greatest value in the tree. The BST is defined by a root node
// and the property: every node on the right subtree has to be larger than the
// current node and every node on the left subtree has to be smaller (or equal) than
// the current node.
//
// Created by John Tabone 
//**************************************************

import scala.collection
import scala.io.Source._

case class Tree(value: Int, left: Option[Tree] = None, right: Option[Tree] = None){
    def inorder(t:Tree => Unit){
      left.map(_.inorder(t))
      t(this)
      right.map(_.inorder(t))
    }
}

object prob3 {

  var inOrderList = scala.collection.immutable.List[Int]()
  var nthNum:Int = 0

  def main(args: Array[String]) {

    var n = readLine("Which nth value would you like to find? \n")
    val tree1 = new Tree(30, 
        Some(Tree(20, 
          Some(Tree(10)))),
        Some(Tree(40,
          Some(Tree(35)))))


    nthNum = findNthGreatestValueInBST(tree1, n.toInt)

    if (nthNum < 0 || nthNum+1 > inOrderList.length){
      println ("Nth value is out of range!")
      return
    }

    println ("The nth value is: " + inOrderList(nthNum))

  }

  def findNthGreatestValueInBST(tree: Tree, n: Int) : Int = {

    List("Inorder traversal results in: " -> tree.inorder _) foreach {
      case (inorder, f) =>
        val s = new StringBuilder(inorder)
        f(t => s ++= t.value.toString + " ")
        f(t => inOrderList = inOrderList:+ t.value)
        println(s)
    }

    return n-1 //return n-1 rather than the nth number since we're dealing 
               //with a list (index starts at 0) and so that I can see if 
               //user input is out of range
  }
}