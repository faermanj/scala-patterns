package fun

object ComposeFun extends App{
	def twist(s:String) = s.reverse
	def shout(s:String) = s.toUpperCase + "!"
	
	def twistAndShout = twist _ andThen shout _
	def shoutedTwist  = twist _ compose shout _
	
	println(twistAndShout("Shaking"))
	println(shoutedTwist("Oh Baby"))
}