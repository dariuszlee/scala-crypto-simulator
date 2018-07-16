case class Block(val transactions : List[Transaction], val previousBlock : Option[Block])
{
  def Find(id : BigInt) : Option[Transaction] = {
    return Find(id, Some(this))
  }

  private def Find(id : BigInt, block : Option[Block]) : Option[Transaction] = {
    block match {
      case None => return None
      case Some(Block(trans, nextBlock)) => {
        val findResult = trans.find((x) => x.GetId() == id) 
        return findResult match {
          case None => return Find(id, nextBlock)
          case Some(_) => findResult
        }
      }
    }
  }

}

object BlockUtilities {
}
