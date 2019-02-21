package controllers

import javax.inject._
import play.api.mvc._
import services.ReedHing
import play.api.Logger

@Singleton
class HomoFauxController @Inject()(cc: ControllerComponents, reedhing: ReedHing) extends AbstractController(cc) {

  val logging = Logger("HomoFauxIn translator")

  def getHomoFauxIn(start_phr: String) = Action {
    val home_phones = reedhing.full_dict.homeOhFauxCent(start_phr)
    val faux_ins_str = home_phones.mkString(" ")
    logging.info(s"Translated phrase [$start_phr] into [$faux_ins_str]")
    Ok(faux_ins_str)
  }
}
