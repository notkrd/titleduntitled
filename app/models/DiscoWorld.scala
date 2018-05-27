package models

import scala.collection.immutable._
import play.api.Logger

/** Object for a "room" in the fiction, to be described.
  *
  * @param entities a partial function from names to individuals
  * @param relations1 a partial function from words to predicates in the model, where a predicate is a from entitie to truth values
  * @param relations2 a partial function from words to binary relations in the model
  * @param lexicon the words in the model, found by categories, useable to speak something
  * @param name naming it something lets us bundle it up, choose among alternatives
  * @param m_triggers phrases inciting monologues, with the monologues incited
  * @param m_syntax phrase-structure style composition rules. Not currently used for parsing, but currently just to highlight permissible categories
  */
class DiscoWorld(entities: Map[KeyPhrase, Entity],
                 relations1: Map[KeyPhrase, PredSing],
                 relations2: Map[KeyPhrase, Entity => Entity => Boolean],
                 val lexicon: Map[KeyPhrase, Set[KeyPhrase]] = Map(),
                 val name: String = "unknown world",
                 val m_triggers: Map[String, Monologue] = Map(),
                 val m_syntax: Map[(String, String), String] = shit_syntax
                ) extends World(entities, relations1, relations2){

  def possSuccs(a_cat: String): Set[String] = {
    m_syntax.keySet.filter( lr => lr._1 == a_cat).map( lr => lr._2)
  }

  /** Message for parse error
    *
    * @param phr_in phrase to parse
    * @return the message
    */
  def failure_for(phr_in: String): String = "This language does not determine whether or not <strong>" ++ phr_in ++ "</strong>. You must be speaking some other language, if you are speaking language at all. "

  /** Message for key error
    *
    * @param phr_in phrase to parse
    * @return the message
    */
  def key_error_for(phr_in: String): String =  "In saying <strong>" + phr_in + "</strong>, you or the programmer seem to have invented words, which is forbidden in the strongest terms. "

  /** Message for successful true parse
    *
    * @param phr_in phrase to parse
    * @return the message
    */
  def truth_for(phr_in: String): String = "<strong>" + phr_in.capitalize + "</strong>. "

  /** Message for successful false parse
    *
    * @param phr_in phrase to parse
    * @return the message
    */
  def falsity_for(phr_in: String): String = "It is not the case that <strong>" + phr_in + "</strong>. "

  /** A really bad, case-by-case parser.
    *
    * @param parsed a list of phrases: each must have a syntactic category under the key "cat" and a phrase under the key "phrase"
    * @return A string (not an actual semantic object!) commenting on the semantics of the sentence
    */
  def shitParse(parsed: List[Map[String, String]]): String = {
    val full_phrase: String = parsed.foldLeft("")((s, p) => s + p("phrase") + " ").toLowerCase.trim
    val failure_msg: String = failure_for(full_phrase)
    val key_error_msg: String = key_error_for(full_phrase)
    val its_true_msg: String = truth_for(full_phrase)
    val its_false_msg: String = falsity_for(full_phrase)

    parsed match {
      case Nil => "There is nothing here, unuttered. "
      case head :: Nil => head("cat") match {
          case "Sentence" => "It seems we're done here, too late for meaning"
          case _ => failure_msg
        }
      case fst :: snd :: Nil => {
        (fst("cat"), snd("cat")) match {
          case ("Entity", "Intransitive Verb") =>
            if ((entities.keySet contains fst("phrase")) &&
              (relations1.keySet contains snd("phrase")))
            {
              if(relations1(snd("phrase"))(entities(fst("phrase")))) { its_true_msg }
              else { its_false_msg }
            }
            else { key_error_msg }
          case _ => failure_msg
        }
      }
      case fst :: snd :: thd :: Nil =>
        (fst("cat"), snd("cat"), thd("cat")) match {
          case ("Entity", "Auxiliary Verb", "Adjective") =>
            if((entities.keySet contains fst("phrase")) &&
              (Set("is","are") contains snd("phrase")) &&
              (relations1.keySet contains thd("phrase")))
            {
              if(relations1(thd("phrase"))(entities(fst("phrase")))) { its_true_msg }
              else { its_false_msg }
            }
            else { key_error_msg }
          case ("Determiner", "Noun", "Intransitive Verb") =>
            if((relations1.keySet contains snd("phrase")) &&
              (relations1.keySet contains thd("phrase")))
            {
              if(relations1(thd("phrase"))(entities("the " + snd("phrase")))) { its_true_msg }
              else { its_false_msg }
            }
            else { key_error_msg }
          case ("Entity", "Transitive Verb", "Entity") =>
            if((entities.keySet contains fst("phrase")) &&
              (relations2.keySet contains snd("phrase")) &&
              (entities.keySet contains thd("phrase")))
            {
              if(relations2(snd("phrase"))(entities(fst("phrase")))(entities(thd("phrase")))) { its_true_msg }
              else { its_false_msg }
            }
            else { key_error_msg }
          case ("Entity", "Auxiliary Verb", "Entity") =>
            if((entities.keySet contains fst("phrase")) &&
              (Set("is", "are") contains snd("phrase")) &&
              (entities.keySet contains thd("phrase")))
            {
              if(entities(fst("phrase")) == entities(thd("phrase"))) { its_true_msg }
              else { its_false_msg }
            }
            else { key_error_msg }
          case _ => failure_msg
          }
      case fst :: snd :: rest => {
        (fst("cat"), snd("cat")) match {
          case ("Determiner", "Noun") => {
            if(relations1.keySet contains snd("phrase")) {
              val new_parse = Map("cat" -> "Entity", "phrase" -> ("the " ++ snd("phrase"))) :: rest
              shitParse(new_parse)
            }
            else { key_error_msg }
          }
          case _ => { failure_msg }
        }
      }
      case _ => {
        failure_msg
      }
    }
  }

  /** Evaluate a sentence to provide a response to give the reader
    *
    * @param parsed phrase to evaluate, as a sequence of tagged phrases
    * @return Message corresponding to the parse
    */
  def evalSent(parsed: List[Map[String, String]]): String = {
    val full_phrase: String = parsed.foldLeft("")((s, p) => s + p("phrase") + " ").toLowerCase.trim
    val response: String = if(m_triggers.keySet contains full_phrase) {
      shitParse(parsed) ++ "<br><br>" ++ m_triggers(full_phrase).text
    }
    else {
      shitParse(parsed)
    }
      response + "</br></br>"
  }

}
