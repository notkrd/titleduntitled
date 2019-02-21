package services

import scala.annotation.tailrec
import scala.util.Random

case class PronounceDict(leaves: Set[String], phonemes: Map[String, PronounceDict]) {

  def this(leaf: String) = this(Set(leaf), Map.empty[String, PronounceDict])
  def this(phonemes: Map[String, PronounceDict]) = this(Set.empty[String], phonemes)
  def this() = this(Set.empty[String], Map.empty[String, PronounceDict])

  def pronounce_words(wrds: List[String]): Vector[String] = {
    val d_map = this.words
    wrds.foldLeft(Vector.empty[String])((v, w) => {
      if(d_map contains w){
        v ++ d_map(w).toVector
      }
      else {
        v
      }
    })
  }

  def pronounce_sent(sent: String): Vector[String] = {
    pronounce_words(sent.split(Array(' ')).map(_.toLowerCase).toList)
  }

  def homeOhFauxCent(sent: String): Vector[String] = {
    homeOhFauxNIEs(pronounce_sent(sent))(Vector.empty[String])
  }

  def addLeaf(new_leaf: String): PronounceDict =
    PronounceDict(leaves + new_leaf, phonemes)

  def updatePhon(a_phon: String)(new_dic: PronounceDict): PronounceDict =
    PronounceDict(leaves, phonemes.updated(a_phon, new_dic))

  final def findFaux(phones: Vector[String])(prospect: List[String]): Option[(String, List[String])] = phones match {
    case fst +: rst =>
      leaves.toVector match {
        case f +: _ => Some(f, prospect)
        case _ =>
          if (phonemes contains fst) {
            phonemes(fst).findFaux(rst)(prospect ++ List(fst))
          }
          else {
            leaves.toList match {
              case f :: _ => Some(f, prospect)
              case Nil => None
            }
          }
      }
    case _ => None
  }

  @tailrec
  final def homeOhFauxNIEs(phones: Vector[String])(prev: Vector[String]): Vector[String] = phones match {
    case fst +: rst =>
      findFaux(phones)(List.empty[String]) match {
        case Some((s, phs)) =>
          homeOhFauxNIEs(phones.drop(phs.size))(prev :+ s)
        case None => homeOhFauxNIEs(rst)(prev :+ fst)
      }
    case _ =>
      prev
  }

  @tailrec
  final def dictAt(a_path: Pronounce): Option[PronounceDict] = a_path match {
    case Nil => Some(this)
    case fst :: rst =>
      if(phonemes contains fst) {
        phonemes(fst) dictAt rst
      }
      else {
        None
      }
  }

  @tailrec
  final def hasPath(a_path: Pronounce): Boolean = a_path match {
    case Nil => true
    case fst :: rst =>
      if (phonemes contains fst) {
        phonemes(fst) hasPath rst
      }
      else {
        false
      }
  }

  def words: Pronounciations = {
    val leaf_words: Pronounciations = leaves.map((_, List.empty[String])).toMap
    val branch_words: Pronounciations = phonemes flatMap (spd => {
      spd._2.words mapValues (spd._1 :: _)
    })
    Map.empty[String, Pronounce] ++ leaf_words ++ branch_words
  }

  def @:(pronounce: (String, Pronounce)): PronounceDict =
    PronounceDict.addPronounce(this)(pronounce)(List.empty[String])

  def ++ (that: PronounceDict): PronounceDict =
    PronounceDict(this.leaves ++ that.leaves, this.phonemes ++ that.phonemes)

  override def toString: String = phonemes.foldLeft(leaves.foldLeft("{ ")((s, l) =>
    s ++ "\"" ++ l ++ "\" " ))((s, spd) => s ++ spd._1 ++ " -> " ++ spd._2.toString ++ " ") ++ "}"
}

object PronounceDict {
  def empty: PronounceDict = {
    PronounceDict(Set.empty[String], Map.empty[String, PronounceDict])
  }

  @tailrec
  final def propagateUpdate(new_dict: PronounceDict)(ancestors: Stream[(String, PronounceDict)]): PronounceDict =
    ancestors match {
      case Stream.Empty => new_dict
      case (s, pd) #:: rst =>
        propagateUpdate(pd.updatePhon(s)(new_dict))(rst)
  }

  @tailrec
  private def updateAtHelper(a_dict: PronounceDict)(a_path: Pronounce)(a_val: String)(ancestors: Stream[(String, PronounceDict)]): PronounceDict = a_path match {
    case Nil => propagateUpdate(a_dict.addLeaf(a_val))(ancestors)
    case fst :: rst =>
      updateAtHelper(a_dict.phonemes(fst))(rst)(a_val)((fst, a_dict) #:: ancestors)
  }

  def updateAt(a_dict: PronounceDict)(a_path: Pronounce)(a_val: String): PronounceDict = updateAtHelper(a_dict)(a_path)(a_val)(Stream.empty[(String, PronounceDict)])


  @tailrec
  private def addKeyHelper(a_dict: PronounceDict)(a_path: Pronounce)(a_key: String)(ancestors: Stream[(String, PronounceDict)]): PronounceDict = a_path match {
    case Nil => propagateUpdate(a_dict.updatePhon(a_key)(new PronounceDict()))(ancestors)
    case fst :: rst =>
      addKeyHelper(a_dict.phonemes(fst))(rst)(a_key)((fst, a_dict) #:: ancestors)
  }

  def addKey(a_dict: PronounceDict)(a_path: Pronounce)(a_val: String): PronounceDict = addKeyHelper(a_dict)(a_path)(a_val)(Stream.empty[(String, PronounceDict)])

  @tailrec
  final def addPronounce(a_dict: PronounceDict)(pronounce: (String, Pronounce))(curr_path: Pronounce): PronounceDict = a_dict.dictAt(curr_path) match {
    case Some(d) =>
      pronounce._2 match {
        case Nil => updateAt(a_dict)(curr_path)(pronounce._1)
        case fst :: rst => if (d.phonemes contains fst) {
          addPronounce(a_dict)(pronounce._1, rst)(curr_path ++ List(fst))
        }
        else {
          addPronounce(addKey(a_dict)(curr_path)(fst))(pronounce._1, rst)(curr_path ++ List(fst))
        }
      }
    case None =>
      throw new IllegalArgumentException(s"Invalid current path $curr_path in $a_dict with pronounce $pronounce")
  }

  def from_pronounciations(prns: Pronounciations): PronounceDict = prns.foldRight(PronounceDict.empty)(_ @: _)
}