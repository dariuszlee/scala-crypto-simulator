import java.security.MessageDigest
import java.nio.ByteBuffer

abstract class TransactionBase(val modulus: BigInt, val key: BigInt) {
  private val id : BigInt = TransactionUtilities.CreateTransactionId(modulus, key)

  def Id : BigInt = {
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
