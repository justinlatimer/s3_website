package s3.website

import scala.util.Try

class Logger(val verboseOutput: Boolean) {
  def debug(msg: String) = if (verboseOutput) log(Debug, msg)
  def info(msg: String) = log(Info, msg)
  def fail(msg: String) = log(Failure, msg)

  def info(report: SuccessReport) = log(Success, report.reportMessage)
  def info(report: FailureReport) = fail(report.reportMessage)

  def pending(msg: String) = log(Wait, msg)

  private def log(logType: LogType, msgRaw: String): Try[Unit] = {
    val msg = msgRaw.replaceAll("\\n", "\n       ") // Indent new lines, so that they arrange nicely with other log lines
    Try(println(s"[$logType] $msg"))
  }

  sealed trait LogType {
    val prefix: String
    override def toString = prefix
  }
  case object Debug extends LogType {
    val prefix = "debg".cyan
  }
  case object Info extends LogType {
    val prefix = "info".blue
  }
  case object Success extends LogType {
    val prefix = "succ".green
  }
  case object Failure extends LogType {
    val prefix = "fail".red
  }
  case object Wait extends LogType {
    val prefix = "wait".yellow
  }

  /**
   * Idea copied from https://github.com/ktoso/scala-rainbow.
   */
  implicit class RainbowString(val s: String) {
    import Console._

    /** Colorize the given string foreground to ANSI black */
    def black = BLACK + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def red = RED + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def green = GREEN + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def yellow = YELLOW + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def blue = BLUE + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def magenta = MAGENTA + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def cyan = CYAN + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def white = WHITE + s + RESET
  }
}