package connectors

import play.api.libs.json.JsValue

import javax.inject.Inject
import play.api.libs.ws.WSClient


import scala.concurrent.{ExecutionContext, Future}


class CountryConnector @Inject()(ws: WSClient)(implicit ec: ExecutionContext) {


  def getCapital(country: String): Future[String] = {
    val apiUrl = s"https://restcountries.com/v3.1/name/$country"
    ws.url(apiUrl).get().map { response =>
      if (response.status == 200) {
        val data: JsValue = response.json
        val capital: JsValue = (data \ 0 \ "capital").get
        capital.toString().filter(c => c.isLetter || c.isWhitespace)
      } else {
        throw new RuntimeException(s"Failed to fetch capital. Status: ${response.status}")
      }
    }
  }

}
