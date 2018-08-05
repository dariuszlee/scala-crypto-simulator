import scala.collection.mutable.MutableList
import scala.util.{ Success, Failure }

class CryptoClient(val address : Int) {
  private val transactions : MutableList[TransactionBase] = new MutableList[TransactionBase]
  private var latestBlock : Option[Block] = None

  def ReceiveTransaction(trans : TransactionBase) : Boolean = trans match {
    case TransactionFirst(mod, pub) => {
      transactions += trans
      return true
    }
    case Transaction(mod, pub, prev, sig) => 
      return latestBlock match {
        case None => {
          return false
        }
        case Some(b) => b.Find(prev) match {
          case Some(t) => {
            TransactionUtilities.Verify(sig, t.key, t.modulus) match {
              case Success(v) => {
                if(v)
                  transactions += trans
                return v
              }
              case Failure(f) => false
            }
          }
          case None => false
      }
    }
  }

  def GetTransactions(pub : BigInt, mod : BigInt) : List[TransactionBase] = {
    return latestBlock match {
      case None => List()
      case Some(t : Block) => t.FindByPubKey(mod, pub)
    }
  }

  def GenerateBlock() : Boolean = {
    latestBlock match {
      case None => latestBlock = Some(Block(transactions.toList, None))
      case Some(block) => latestBlock = Some(Block(transactions.toList, Some(block)))
    }
    transactions.clear()
    return true
  }

  def PrintTransactions() : Unit = {
    Console.println("Print transactions")
    transactions.foreach((x) => Console.println(x))
  }

  def PrintBlocks() : Unit = {
    Console.println("Print blocks")
    return PrintBlocks(latestBlock)
  }

  def PrintBlocks(block : Option[Block]) : Unit = block match {
    case None => return
    case Some(block) => {
      Console.println(block)
      PrintBlocks(block.previousBlock)
    }
  }
}
