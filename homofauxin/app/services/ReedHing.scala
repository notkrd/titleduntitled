package services

import scala.io.Source
import javax.inject._
import play.api.Logger

@Singleton
class ReedHing @Inject() () {
  val logging: Logger = Logger("ReedHing")

  def readDict(dict_path: String): List[String] = {
    var dict_lines = List.empty[String]
    val dict_src = Source.fromFile(dict_path)
    try {
      dict_src.getLines.foldRight(List.empty[String])(_ :: _)
    }
    catch {
      case e: Exception =>
        println(s"Error reading file $dict_path with error $e")
        List.empty[String]
    }
    finally {
      dict_src.close()
    }
  }

  def processLine(a_line: String): Option[Pronounciation] = {
    val split_line: List[String] = a_line.split(Array(' ')).toList
    split_line match {
      case Nil => None
      case fst :: rst => Some(fst.stripMargin.toLowerCase, rst.filter(_ != ""))
    }
  }

  def fromLines(some_lines: List[String]): PronounceDict = {
    some_lines.foldLeft(PronounceDict.empty)(
      (d, l) => processLine(l) match {
        case None => d
        case Some(p) => p @: d
    })
  }

  def processDict(dict_path: String): PronounceDict = {
    logging.info(s"Opening dict at $dict_path")
    try {
      val the_lines = readDict(dict_path)
      logging.info("Read lines in file")
      fromLines(the_lines)
    }
    catch {
      case e: Exception =>
        println(s"Failed to read dict at $dict_path with error $e")
        services.PronounceDict.empty
    }
  }

  val test_dict_file = "resources/test_dict.txt"
  val cmu_dict_file = "resources/pronounce_dict.txt"
  val dict10000_file = "resources/10000_dict.txt"

  val full_dict: PronounceDict = processDict(cmu_dict_file)
  logging.info("Processed pronounciation dictionary")
}
