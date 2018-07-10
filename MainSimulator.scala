// import java.security.KeyPair;
import java.util.Base64
import java.security.interfaces.RSAPublicKey
import java.security.interfaces.RSAPrivateKey

object MainSimulator extends App {
    // Step 1: Create Coins
    var initialCurrencyCount = 10;
    var initialUsers = 5;

    var coins = new Array[Coin](initialCurrencyCount);
    var i:Int = 0;
    while(i < initialCurrencyCount)
    {
      coins(i) = new Coin(i);
      i = i + 1;
    }

    val kp = KeyGenerator.GenerateKeyPair();
    val encoder : Base64.Encoder = Base64.getEncoder()

    val pubKey : RSAPublicKey = kp.getPublic().asInstanceOf[RSAPublicKey]
    Console.println(pubKey.getModulus())
    Console.println(pubKey.getPublicExponent())
    val prvKey : RSAPrivateKey = kp.getPrivate().asInstanceOf[RSAPrivateKey]
    Console.println(prvKey.getPrivateExponent())
}

object CoinUtils {
  def apply(coins : Array[Coin]) : Unit = {
    if(!coins.isEmpty)
    {
      Console.println(coins.head.ownerId);
      CoinUtils.apply(coins.tail);
    }
  }

  def GenerateAddress() : Array[Char] = {
    return new Array[Char](32);
  }
}
