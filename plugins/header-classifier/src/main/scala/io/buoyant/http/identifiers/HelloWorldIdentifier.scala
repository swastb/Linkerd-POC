package io.buoyant.http.identifiers

import com.twitter.finagle.http.Request
import com.twitter.finagle.{Dtab, Path}
import com.twitter.util.Future
import io.buoyant.router.RoutingFactory
import io.buoyant.router.RoutingFactory.{RequestIdentification, UnidentifiedRequest}


case class HelloWorldIdentifier(
  prefix: Path,
  name: String,
  baseDtab: () => Dtab = () => Dtab.base
) extends RoutingFactory.Identifier[Request] {

  private[this] val MoveOn =
    Future.value(new UnidentifiedRequest[Request]("MoveOn to next identifier"))

  def apply(req: Request): Future[RequestIdentification[Request]] = {


    val userId:String = {
      req.headerMap.get("userid").getOrElse("NOT_FOUND")
    }
    println("--------------  User Id Received As ------------------------"+userId)
    val token = generateToken(userId);
    req.headerMap.set ("token", token)
    if (!userId.equals("NOT_FOUND"))
      MoveOn
    else
      throw new Exception("userid not found")
  }

  def generateToken(username:String): String ={
    val url = "http://localhost:9999/token/"+username
    val result = scala.io.Source.fromURL(url).mkString
    println("Received Response from Service as "+result)
    return result;

  }

  def validateToken(): Unit ={
    val url = "<URL to validate token>"
    val result = scala.io.Source.fromURL(url).mkString
    println(result)

  }


  /* def getRestContent(url:String): String = {
     val httpClient = new DefaultHttpClient()
     val httpResponse = httpClient.execute(new HttpGet(url))
     val entity = httpResponse.getEntity()
     var content = ""
     if (entity != null) {
       val inputStream = entity.getContent()
       content = io.Source.fromInputStream(inputStream).getLines.mkString
       inputStream.close
     }
     httpClient.getConnectionManager().shutdown()
     return content
   }*/
}
