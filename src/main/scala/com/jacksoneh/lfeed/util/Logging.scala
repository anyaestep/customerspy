package com.jacksoneh.lfeed.util

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A convenience trait that provides a logger. Developer can either use logger directly or use provided 
 * convenience methods
 * @agrebneva
 */
trait Logging {
  protected lazy val logger: Logger = LoggerFactory.getLogger(getClass.getName)
  
  def info(msg: String, args: AnyRef*) = logger.info(_fm(msg,args: _*))
  def warn(msg: String, args: AnyRef*) = logger.warn(_fm(msg,args: _*))
  def error(e: Exception, msg: String, args: AnyRef*) = logger.error(_fm(msg,args: _*), e)
  def error(msg: String, args: AnyRef*) = logger.error(_fm(msg,args: _*))
  def debug(msg: String, args: AnyRef*) = logger.debug(_fm(msg,args: _*))
  
  def _fm(msg: String, args: AnyRef*) = msg.format(args.toArray : _*)
}