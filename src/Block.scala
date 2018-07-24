case class Block(val transactions : List[TransactionBase], val previousBlock : Option[Block])
{
  def Find(id : BigInt) : Option[TransactionBase] = {
    return Find(id, Some(this))
  }

  private def Find(id : BigInt, block : Option[Block]) : Option[TransactionBase] = {
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

  def FindByPubKey(mod : BigInt, pub : BigInt) : List[TransactionBase] = {
    return FindByPubKey(mod, pub, Some(this))
  }

  def FindByPubKey(mod : BigInt, pub : BigInt, block : Option[Block]) : List[TransactionBase]  = block match {
    case None => return List()
    case Some(Block(trans, nextBlock)) => {
      return FindByPubKey(mod, pub, nextBlock) ++ trans.filter((x) => x.key == pub && x.modulus == mod) 
    }
  }
}

object BlockUtilities {
}
