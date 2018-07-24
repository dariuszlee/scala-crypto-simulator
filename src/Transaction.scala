import java.security.MessageDigest
import java.nio.ByteBuffer

import java.math.BigInteger

import java.security.Signature
import java.security.spec.RSAPrivateKeySpec
import java.security.spec.RSAPublicKeySpec
import java.security.KeyFactory
import java.security.PublicKey

abstract class TransactionBase(val modulus: BigInt, val key: BigInt) {
  private val id : BigInt = TransactionUtilities.CreateTransactionId(modulus, key)

  def GetId() : BigInt = {
    return id
  }
}

case class TransactionFirst(mod: BigInt, k : BigInt) extends TransactionBase(mod, k)
case class Transaction(mod: BigInt, k: BigInt, val prevTx : BigInt, val sig : BigInt) extends TransactionBase(mod, k)

object TransactionUtilities {
  val bigZero = BigInt(0)
  val hasher = MessageDigest.getInstance("SHA-256")
  def CreateTransactionId(mod : BigInt, key : BigInt) : BigInt = {
    val byteArry = ToByteArray(mod, key)
    val hash = hasher.digest(byteArry)
    return ToBigInt(hash)
  }

  def GetSignature(id : BigInt, prv : BigInt, mod : BigInt) : BigInt = {
    return ModExp(id, prv - 1, mod)
  }

  private def ModExp(id : BigInt, n : BigInt, mod : BigInt) : BigInt = {
    val base = BigInt(1)
    n match {
      case `base` => {
        val exp = id * id % mod
        return exp
      }
      case _ => { 
        val exp = ModExp(id, n-1, mod) * id % mod
        return exp
      }
    }
  }

  def Sign(id : BigInt, prv : Array[Byte], mod : Array[Byte]) : BigInt = {
    val spec = new RSAPrivateKeySpec(new BigInteger(mod), new BigInteger(prv))
    val kf = KeyFactory.getInstance("RSA")
    val pk = kf.generatePrivate(spec)

    val rsa = Signature.getInstance("SHA1withRSA")
    rsa.initSign(pk)
    rsa.update(id.toByteArray)
    return new BigInt(new BigInteger(rsa.sign()))
  }

  def Verify(sig : BigInt, pub : Array[Byte], mod : Array[Byte]) : Boolean = {
    val spec = new RSAPublicKeySpec(new BigInteger(mod), new BigInteger(pub))
    val kf = KeyFactory.getInstance("RSA")
    val pubKey : PublicKey = kf.generatePublic(spec)

    val rsa = Signature.getInstance("SHA1withRSA")
    rsa.initVerify(pubKey)
    return rsa.verify(sig.toByteArray)
  }

  private def ToByteArray(mod : BigInt, key : BigInt) : Array[Byte] = {
    val modByte = mod.toByteArray
    val keyByte = key.toByteArray
    val byteBuffer = ByteBuffer.allocate(modByte.size + keyByte.size).put(modByte).put(keyByte)
    return byteBuffer.array
  }

  private def ToBigInt(array : Array[Byte]) : BigInt = {
    val byteBuffer = ByteBuffer.allocate(32).put(array)
    return BigInt(byteBuffer.getLong(16))
  }
}

object TransactionSimulator extends App {
  val key = KeyGenerator.GenerateKeyPair()
  val sig = TransactionUtilities.Sign(35, key.Prv.toByteArray, key.Mod.toByteArray)
  val verify = TransactionUtilities.Verify(sig, key.Pub.toByteArray, key.Mod.toByteArray)
  Console.println(sig)
  Console.println(verify)
}
