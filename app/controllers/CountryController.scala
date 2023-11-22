package controllers

import connectors.CountryConnector
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import play.api.data.Form
import play.api.data.Forms._

class CountryController @Inject()(
                                   countryConnector: CountryConnector,
                                   cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  val countryForm = Form("country" -> nonEmptyText)

  def getCapitalForm(): Action[AnyContent] = Action {
    implicit request =>
      countryForm.bindFromRequest.fold(
        formWithErrors =>
          BadRequest("Invalid form submission"),
        country =>
          Redirect(routes.CountryController.showCapital(country))
      )
  }

  def getCapital(): Action[AnyContent] = Action {
    implicit request =>
      Ok(views.html.loginView())
  }

  def showCapital(country: String): Action[AnyContent] = Action.async {
    implicit request =>
      countryConnector.getCapital(country).map {
        capital =>
          Ok(views.html.secondPage(country, capital))
      }.recover {
        case ex =>
          InternalServerError(s"An error has occured: ${ex.getMessage}")
      }

  }

}
