import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey
import java.security.interfaces.RSAPrivateKey

import java.util.Base64

case class Address(val Pub : BigInt, val Mod : BigInt, val Prv : BigInt)

object KeyGenerator {
  def GenerateKeyPair() : Address = {
    val kpGen = KeyPairGenerator.getInstance("RSA");
    kpGen.initialize(512);
    val kp = kpGen.generateKeyPair();
    val pubKey : RSAPublicKey = kp.getPublic().asInstanceOf[RSAPublicKey]
    val prvKey : RSAPrivateKey = kp.getPrivate().asInstanceOf[RSAPrivateKey]
    return Address(pubKey.getPublicExponent(), pubKey.getModulus(), prvKey.getPrivateExponent())
  }

  def GenerateUsers(numUsers : Int) : Array[Address] = {
    var users = new Array[Address](numUsers)
    for(i <- 0 to numUsers - 1)
    {
      val kp = KeyGenerator.GenerateKeyPair();
      val encoder : Base64.Encoder = Base64.getEncoder()

      users(i) = kp
    }
    return users
  }
}
