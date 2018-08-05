import java.security.KeyFactory
import java.security.spec.RSAPrivateKeySpec
import java.security.spec.RSAPublicKeySpec
import java.security.Signature
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey
import java.security.interfaces.RSAPrivateKey

import java.math.BigInteger

import java.util.Base64

import scala.util.Try

case class Address(val Pub : BigInt, val Mod : BigInt, val Prv : BigInt)
case class FullKeys(val pubKey : RSAPublicKey, val prvKey : RSAPrivateKey) {
  def Pub : BigInt = {
    return pubKey.getPublicExponent()
  }

  def Prv : BigInt = {
    return prvKey.getPrivateExponent()
  }

  def Mod : BigInt = {
    return pubKey.getModulus()
  }
}

object KeyGenerator {
  def GenerateKeyPair() : Address = {
    val kpGen = KeyPairGenerator.getInstance("RSA");
    kpGen.initialize(512);
    val kp = kpGen.generateKeyPair();
    val pubKey : RSAPublicKey = kp.getPublic().asInstanceOf[RSAPublicKey]
    val prvKey : RSAPrivateKey = kp.getPrivate().asInstanceOf[RSAPrivateKey]
    return Address(pubKey.getPublicExponent(), pubKey.getModulus(), prvKey.getPrivateExponent())
  }

  def GenerateFullKeys() : FullKeys = {
    val kpGen = KeyPairGenerator.getInstance("RSA");
    kpGen.initialize(512);
    val kp = kpGen.generateKeyPair();
    val pubKey : RSAPublicKey = kp.getPublic().asInstanceOf[RSAPublicKey]
    val prvKey : RSAPrivateKey = kp.getPrivate().asInstanceOf[RSAPrivateKey]
    return FullKeys(pubKey, prvKey)
  }

  def GenerateUsers(numUsers : Int) : Array[FullKeys] = {
    var users = new Array[FullKeys](numUsers)
    for(i <- 0 to numUsers - 1)
    {
      val kp = KeyGenerator.GenerateFullKeys();
      users(i) = kp
    }
    return users
  }

  def Sign(toSign : BigInt, prvKey : RSAPrivateKey) : BigInt  = {
    val rsa = Signature.getInstance("SHA1withRSA")
    rsa.initSign(prvKey)
    return new BigInt(new BigInteger(rsa.sign()))
  }

  def Verify(toVerify : BigInt, pubKey : RSAPublicKey) : Boolean = {
    val rsa = Signature.getInstance("SHA1withRSA")
    rsa.initVerify(pubKey)
    return rsa.verify(toVerify.toByteArray)
  }

  def Verify(toVerify : BigInt, pubKeyInt : BigInt, mod : BigInt) : Try[Boolean] = {
    val pubKeySpec : RSAPublicKeySpec = new RSAPublicKeySpec(mod.bigInteger, pubKeyInt.bigInteger)
    val kf = KeyFactory.getInstance("RSA")
    val pubKey = kf.generatePublic(pubKeySpec)
    val rsa = Signature.getInstance("SHA1withRSA")
    rsa.initVerify(pubKey)
    return rsa.verify(toVerify.toByteArray)
  }
}
