import scala.collection.mutable.MutableList

class CryptoClient(val address : Int) {
  private val transactions : MutableList[Transaction] = new MutableList[Transaction]
  private var latestBlock : Option[Block] = None

  def ReceiveTransaction(trans : Transaction) : Boolean = {
    transactions += trans
    return true
  }

  def GetTransactions(pub : BigInt, mod : BigInt) : List[Transaction] = {
    return List()
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
