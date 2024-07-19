package object chapter06 {
  val commonValue = "包对象"
  def commonMethod(): Unit = {
    println(s"当前是${commonValue}")
  }
}
