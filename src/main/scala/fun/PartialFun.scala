package fun

object PartialFun extends App{
    type Named = PartialFunction[Int,String]
    
	val douglas:Named = {case 42 => "The Answer"}
	val sheldon:Named = {case 73 => "The Best"}
	val beast:Named = {case 666 => "The Beast"}
	
	val cool = douglas orElse sheldon
	
	1 to 1000 collect cool foreach println
}