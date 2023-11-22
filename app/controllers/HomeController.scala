package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {


  def onPageLoad(): Action[AnyContent] = Action {
    Ok(views.html.loginView())
  }

  def submit(country: String): Action[AnyContent] = Action {
      Redirect(controllers.routes.CountryController.getCapital(country))
  }
}
