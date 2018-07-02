object MainSimulator extends App {
    var initialCurrencyCount = 10;
    var initialUsers = 5;

    var coins = new Array[Coin](initialCurrencyCount);
    var i:Int = 0;
    while(i < initialCurrencyCount)
    {
      coins(i) = new Coin(i);
      i = i + 1;
    }
    coinPrinter.apply(coins);
}

object coinPrinter {
  def apply(coins : Array[Coin]) : Unit = {
    if(!coins.isEmpty)
    {
      Console.println(coins.head.ownerId);
      coinPrinter.apply(coins.tail);
    }
  }
}
