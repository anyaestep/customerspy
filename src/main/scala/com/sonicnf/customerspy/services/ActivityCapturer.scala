package com.sonicnf.customerspy.services

import com.sonicnf.customerspy.db.SimpleMongoClient
import com.sonicnf.customerspy.util.Threader
import com.mongodb.DBCollection
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.MongoDB
import java.util.UUID
import java.io.File
import util.Random.nextInt

/**
 * Capturer places user events to the capped collection. In this POC, those are random
 */
class ActivityCapturer(collectionName: String, mongoClient: SimpleMongoClient) extends Threader {
  // db collection to operate on
  val collection = _ensureGetCollection
  val imgNum = 19
  
  /**
   * Keep inserting docs to the capped collection
   */
  override def run: Unit = {
    // go until destory is initiated and insert random objects to the capped collection
    while (!isStopSet) {
      val obj = MongoDBObject("_id" -> UUID.randomUUID, "imgsrc" -> this._getRandomImg)
      info("Inserting %s", obj.toString)
      try {
         this.collection.insert(obj)
         // make sure we have no errors
         this.mongoClient.getLastError.throwOnError()
      } catch {
        // in real world, it would something more specific but just exception will do for this POC
        case e: Exception => info("Error inserting %s", obj.toString, e);
      }
      // too much output otherwise ..
      Thread.sleep(5000)
    }
  }
  
  def _getRandomImg = nextInt(this.imgNum)
  
  /**
   * Check if capped collection exists and create otherwise
   */
  def _ensureGetCollection: DBCollection = {
    val db: MongoDB = this.mongoClient.getDb()
    if (!db.collectionExists(this.collectionName)) 
      // create a capped collection of size 100
      db.createCollection(this.collectionName, MongoDBObject("capped" -> true, "size" ->100))
    else {
      db.getCollection(this.collectionName)
    }
  }
}