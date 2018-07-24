import scala.math.random

object MainSimulator extends App {
    // Step 1: Create Coins
    val client = new CryptoClient(0)

    var initialCurrencyCount = 10;
    var initialUsers = 5;

    val users = KeyGenerator.GenerateUsers(initialUsers);

    for(i <- 0 to initialCurrencyCount - 1)
    {
      // Randomly assign to start
      val userNum : Int = (random() * initialUsers).asInstanceOf[Int]
      val userKp = users(userNum)
      
      client.ReceiveTransaction(new TransactionFirst(userKp.Mod, userKp.Pub))
    }

    client.PrintTransactions()
    client.GenerateBlock()
    client.PrintBlocks()

    val user = users.find((x) => client.GetTransactions(x.Pub, x.Mod) match {
      case List() => false
      case x :: _ => true
    }) match {
      case Some(t) => new Tuple4(t.Prv, t.Pub, t.Mod, client.GetTransactions(t.Pub, t.Mod).head.GetId())
    }

    val sign = TransactionUtilities.Sign(user._4, user._1.toByteArray, user._3.toByteArray)
    val verify = TransactionUtilities.Verify(sign, user._2.toByteArray, user._3.toByteArray)
    Console.println("Sign: " + sign)
    Console.println("Verify: " + verify)
}
