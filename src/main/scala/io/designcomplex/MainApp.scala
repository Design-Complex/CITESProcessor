/**
 * main entry point for application
 * based on com.cscie88a.MainApp from HES CSCI E88A Spring 2020
 */

package io.designcomplex

import io.designcomplex.config.{ConfigUtils, CookieSettings}
import pureconfig.generic.auto._
import com.typesafe.scalalogging.{LazyLogging}

object MainApp extends LazyLogging {

  val COOKIE_CONFIG_PATH="io.designcomplex.cookie"

  def hello(name: String): String = s"Hello ${name}"

  def main(args: Array[String]): Unit = {
    val cookie = ConfigUtils.loadAppConfig[CookieSettings](COOKIE_CONFIG_PATH)
    logger.info(s"running application version with ttl: ${cookie.ttl}")

    val message = args.length match {
      case 0 => hello("Anonymous")
      case _ => hello(args(0))
    }
    println(message)
  }
}
