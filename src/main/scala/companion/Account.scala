package companion

class Account {
  val id = Account.newUniqueNumber
  private var balance = 0.0

  def deposit(amount: Double): Unit ={
    balance += amount
  }
}

object Account{
  private var lastNumber = 0

  private def newUniqueNumber() = {
    lastNumber += 1
    lastNumber
  }

  def main(args: Array[String]): Unit = {
    val account = new Account()
    val account1 = new Account()
    println(account.id)
    println(account1.id)
  }
}