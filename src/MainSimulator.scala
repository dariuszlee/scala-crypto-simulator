import scala.math.random

object MainSimulator extends App {
    // Step 1: Create Coins
    val client = new CryptoClient(0)

    var initialCurrencyCount = 1
    var initialUsers = 5;

    val users = KeyGenerator.GenerateUsers(initialUsers);

    for(i <- 0 to initialCurrencyCount - 1)
    {
      // Randomly assign to start
      val userNum : Int = (random() * initialUsers).asInstanceOf[Int]
      val userKp = users(userNum)
      
      client.ReceiveTransaction(new TransactionFirst(userKp.Mod, userKp.Pub))
    }

    client.GenerateBlock()

    val userTx = users.find((x) => client.GetTransactions(x.Pub, x.Mod) match {
      case List() => false
      case x :: _ => true
    }) match {
      case Some(t) => new Tuple2(t, client.GetTransactions(t.Pub, t.Mod).head)
      case None => null
    }
    val user = userTx._1
    val prevTx = userTx._2

    val sig = KeyGenerator.Sign(prevTx.Id, user.prvKey)
    val newOwner = users.find((t) => user.Mod != t.Mod) match {
      case Some(t) => t
      case None => throw new Exception("New owner not found")
    }
    val newTx = Transaction(newOwner.Mod, newOwner.Pub, prevTx.Id, sig)
    val verify = KeyGenerator.Verify(newTx.sig, prevTx.key, prevTx.modulus)
}
