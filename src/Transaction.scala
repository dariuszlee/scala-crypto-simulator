class Transaction(val modulus: BigInt, val key: BigInt)  {
  def send(toId : Int) : Boolean = {
    return true;
  }
}
