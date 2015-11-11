package controllers

import java.sql.Timestamp
import java.util.UUID
import java.util.concurrent.TimeoutException
import javax.inject.{Inject, Singleton}

import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import forms.TraineeForm
import models._
import models.daos._
import org.joda.time.DateTime
import play.api.Logger
import play.api.i18n.{Messages, MessagesApi}
import play.api.libs.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


@Singleton
class SubscriptionController @Inject()(
                                   val messagesApi: MessagesApi,
                                   val env: Environment[Trainee, JWTAuthenticator],
                                   subscriptionDAO: SubscriptionDAO)
  extends Silhouette[Trainee, JWTAuthenticator] {


  def delete(idSubscription: UUID) = SecuredAction.async { implicit request =>
    subscriptionDAO.cancel(idSubscription, new Timestamp(request.identity.subscription.get.period.end.getTimeInMillis), new Timestamp(System.currentTimeMillis())).flatMap { ret =>
      Future.successful(Ok(Json.obj("message" -> Messages("my.subscription.cancel.success"))))
    }.recover {
      case ex: TimeoutException =>
        Logger.error("Problem delete subscription for "+idSubscription)
        InternalServerError(ex.getMessage)
      case t: Throwable =>
        Logger.error("Problem retrieve subscription for "+idSubscription)
        BadRequest
      case _ => BadRequest(Json.obj("message" -> Messages("my.subscription.cancel.fail")))
    }
  }

  def retrieve(idSubscription: UUID) = SecuredAction.async { implicit request =>
    subscriptionDAO.retrieve(idSubscription).flatMap { ret =>
      Future.successful(Ok(Json.toJson(ret)))
    }.recover {
      case ex: TimeoutException =>
        Logger.error("Problem retrieve subscription for "+idSubscription)
        InternalServerError(ex.getMessage)
      case ex: NoSuchElementException => NotFound
      case t: Throwable =>
        Logger.error("Problem retrieve subscription for "+idSubscription)
        BadRequest
      case _ => BadRequest
    }
  }
}
