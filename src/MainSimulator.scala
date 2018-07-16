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
      Console.println("Rand val: " + userNum)
      val userKp = users(userNum)
      
      client.ReceiveTransaction(new Transaction(userKp.Mod, userKp.Pub))
    }

    client.PrintTransactions()
    client.GenerateBlock()
    client.PrintBlocks()

    val tk = new Transaction(0, 0)
}
