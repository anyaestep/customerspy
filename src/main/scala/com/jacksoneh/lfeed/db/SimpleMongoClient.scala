package com.jacksoneh.lfeed.db

import com.mongodb.casbah._

/**
 * MongoClient implementation - mongo client wrapper with convenience methods which wraps MongoClient object. 
 * Spring does not work well with scala objects yet so this helps with proper wiring of MongoClient.
 */
class SimpleMongoClient(mongoHost: String, mongoPort: Integer, defaultDbName: String = "spy") {
  val _client = MongoClient(this.mongoHost, this.mongoPort)
  
  def getDb(name : Option[String] = None) : MongoDB = {
    name match {
      case Some(s: String) => this._client(s) 
      case None => this._client(this.defaultDbName)
    }
  }
  /**
   * get collection object in the default db 
   */
  def getCollection(name: String):MongoCollection = this.getDb()(name)
  
  /**
   * return last error on DB
   */
  def getLastError = getDb().getLastError
}