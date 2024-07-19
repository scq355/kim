package chapter06

object L16_TraitSelfType {
  def main(args: Array[String]): Unit = {
    new RegisterUser("scq", "sadsfa").insert()
  }
}

class User(val name:String, val password: String)

trait UserDao {

  _: User =>

  def insert(): Unit ={
    println(s"insert into db: ${this.name}")
  }
}

class RegisterUser(name: String, password: String) extends User(name, password) with UserDao