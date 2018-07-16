import java.security.MessageDigest
import java.nio.ByteBuffer

case class Transaction(val modulus: BigInt, val key: BigInt)  {
  private val id : BigInt = TransactionUtilities.CreateTransactionId(modulus, key)

  def GetId() : BigInt = {
    return id
  }
}

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
  val sig = TransactionUtilities.GetSignature(35, 29, 91)
  val verify = TransactionUtilities.GetSignature(sig, 5, 91)
  Console.println(sig)
  Console.println(verify)
}
