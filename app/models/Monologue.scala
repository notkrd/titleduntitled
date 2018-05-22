package models


/**
  * This class represents monologues I failed to avoid including.
  *
  * @param text The monologue itself
  * @param title An identifier
  */
class Monologue(val title: String = "", val text: String) {
  override implicit def toString: String = text
}

object Monologue {
  val intro = new Monologue("Welcome to this Room", "I write to write my absence. That is, I write as an extension of other practices of trying not to be anyone. I persist and consume (images, tater-tots, tea) in this room, without any particular intent, in disingenuous avoidance of the subject position. The walls are solid and the water (only a few sips of it left at the bottom of a plastic cup) is wet. <br><br> I say this and yet continue to write, here, not like a machine. I know I might as well, ought to, speak like a machine, but writing code here the urge to intervene with something to say itches until I scratch it, as well as my knee, now, remembering I have nothing different to say about the things here except that they are, and return to a better inertness, under the dirty blanket, ceasing to scratch my knee.")

  val color = new Monologue("Lyric is a dry throat", "If this place is or was there might be a color of it. One light in the corner above the bed scalds, casts purple after-images on, idle eyes. The blind’s reflection and a hint of the rotten fruit underline a threat of something changing irresistibly. I sneeze with the dust, but it’s too cold out to friction the window open - if I do I’ll need to surround myself in the printed cotton blanket again, sweat-and-rust scented, scattered with crumbs to be indefinitely pinched away - better to sneeze. Where, rather, metaphor is is steeped in frost and footfalls. Somehow the body continues: lyric is a dry throat.")

  val metaphor = new Monologue("On stomach acid", "It seems that this narrates. The weight of a stomach acidic, dust endangering the beautiful bright screen. Between bile and circulation an agent selects one world among others to pursue. But the system works itself out currently, diodes activated then fading in the dust-welcoming gaps beneath keys. Writers are fired for picking the wrong adjectives, after all. The spongy ground sweats orange.")

  val tutorial = new Monologue("Instructions for Use","INSTRUCTIONS: Combine words to make phrases, and sentences. A word is to be clicked on, unless it is red, in which case it is not permitted. A sentence is true or false, or it does something more complicated, but if it is more complicated, you can work out what it does by figuring out whether some other sentence is true or false. Mostly, in this room, you compose sentences and see if they are true now, but you may also rarely be able to ask a question or issue a command. You can do the same things in that room. Combine phrases with phrases to make phrases, until you end up with something false or true. And then make another sentence, over again.")

  val montague = new Monologue("This Dead Logician","We like our mathematicians illegally gay, cruising clubs for a soiree of strangers (<q>some kind of soiree</q> write the Fefermans) after the conference, out of mind wandering the reflective streets to their advisor's house. Two novels will refigure this mathematician's death: Delaney's deconstructive <q>pornotropic fantasy</q> <em>The Mad Men</em> and <q>a murder mystery by pop-science author and creationist hack</q> David Berlinski <em>Less Than Meets the Eye</em>. A mathematician's murder proves their theory sufficient; the silence of the work its beauty, some handful of facts in his advisor's student's biography oddly conducive to porn and hack thrillers. ")

  val genius = new Monologue("On genius worship", "Unfortunately, the Genius myth is such a hard habit to shake for mathematicians, the argument made in their unexpected bridge championship, oddly expert organ playing, wealth made as an afterthought. I couldn't idly harness exponentials into a few Beverly Hills mansions, maintain a speed habit (Tarski, not Montague), publish teach & change the discourse's terms (one thinks, offered the mathematician's biography, embedding corresponding structures; <q>I did work it out when I was 13<q> quoth Tarski).<br><br>The half-century late professional gossip isn't in the textbook, though it makes the journalistic summary paragraph, and the professor mentions it nonetheless, with such a tone of bemused revelation. Yet one wishes to think in other tropes, other myths, other terms.")

  val montague_sem = new Monologue(montague.title ++ ": semanticists", montague.text.replaceAll("mathematician(s?)", "semanticist$1"))

  val montague_org = new Monologue(montague.title ++ ": organists", montague.text.replaceAll("mathematician(s?)", "organist$1"))

  val montague_inv = new Monologue(montague.title ++ ": successful real estate investor", montague.text.replaceAll("mathematician(s?)", "successful real estate investor$1"))

  val blankets = new Monologue("On blankets", "This room establishes leaf-shadowed temporalities. The intention to act is insufficient to act, even when accompanied by the ability to act. Some might respond by redefining intention to only occur when some physiological sign of action occurs. ")

  val blood = new Monologue("Bloody water", "The body's water drains into the hole in the floor, after filling the towel round Montague's neck. This blood is imagined; the book left it out. But the water is wet and a body must leak.")

  val thx = new Monologue("Thank you very much", "I'm alive, thank you very much, and I left math 17 months ago.")

  val glows = new Monologue("A Glowing Screen", "It reads: [**]")

  val wet = new Monologue("The mathematician is wet", "How wetness too can be of different kinds; the wetware implements certain inexplicable diversions. The Fefermans, in their biography of Tarski - a cumulative page and a half of which is the primary public biographical resource on Montague - indicate somewhere, well after Montague's death in 1971, near Tarski's death in 1983, Tarski calling Montague one of only two mathematicians Tarski enjoyed spending time with. The Fefermans, both Tarski's students, characterize this a polite lie. Tarski happened to board the last ship to leave Poland for the US, in September 1939, for a positivism-inspired Unity of Science conference, on which boat he happened to meet a student of Quine's. Quine, among less famous others, arranged for Tarski to remain in Harvard, by the River Charles. Indianapolis is the only large city in the US not on a body of water.")

  val knots = new Monologue("Various Knots", "I imagine various knots. This is a dark turn of mind. One must consider the semantics of holes. It is possible that the strangling towel made only an unknot.")
}