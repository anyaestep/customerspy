package com.sonicnf.customerspy.util

import concurrent.ops._
import scala.runtime.VolatileBooleanRef

trait Spawnable {
  def run
}

/**
 * Thread that starts itself on init
 * @agrebneva
 */
trait Threader extends Spawnable with Logging {
  // we don't care about synchronization of this flag too much since it's a one-off, o volatile will do
  @volatile
  private var stop: Boolean = false
  // start an anonymous thread
  def init = spawn { 
    try
    {
      this.run
    } catch {
      case e: Exception => error("An exception has occurred and the thread is exiting", e);
    }
  }
  
  def destroy = stop = true
  
  def isStopSet: Boolean = stop
}