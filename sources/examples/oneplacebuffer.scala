package examples;

object oneplacebuffer {

  import scala.concurrent._;

  class OnePlaceBuffer {
    private val m = new MailBox();             // An internal mailbox
    private case class Empty(), Full(x: Int);  // Types of messages we deal with

    m send Empty();                            // Initialization

    def write(x: Int): Unit = m receive {
      case Empty() =>
        Console.println("put " + x);
        m send Full(x)
    }

    def read: Int = m receive {
      case Full(x) =>
        Console.println("get " + x);
        m send Empty() ; x
    }
  }

  def main(args: Array[String]) = {
    val buf = new OnePlaceBuffer;
    val random = new java.util.Random();

    def producer(n: int): unit = {
      Thread.sleep(random.nextInt(1000));
      buf.write(n);
      producer(n + 1)
    }

    def consumer: unit = {
      Thread.sleep(random.nextInt(1000));
      val n = buf.read;
      consumer
    }

    ops.spawn(producer(0));
    ops.spawn(consumer)
  }

}
