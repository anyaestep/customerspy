package com.jacksoneh.lfeed.services

import com.jacksoneh.lfeed.db.SimpleMongoClient
import com.jacksoneh.lfeed.util._
import com.mongodb.casbah._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.Bytes
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.core.MessageCreator
import javax.jms._

/**
 * Feeder will tail the capped collection for new entires and notify of additions
 */
class LiveFeeder(collectionName: String, mongoClient: SimpleMongoClient, jmsTemplate: JmsTemplate, topicName: String) extends Threader {
  
  override def run: Unit = {
    val collection = this.mongoClient.getCollection(this.collectionName)
    info("Starting tailing cursor: '%s'", this.collectionName)
    // open up and sort in natural order, which capped collection should be in already
    val cursor = collection.find()
     // and add options to make cursor tailable
    cursor.option = Bytes.QUERYOPTION_TAILABLE
    cursor.option = Bytes.QUERYOPTION_AWAITDATA
    // tail away
    while(!isStopSet && cursor.hasNext) {
      this._notify(cursor.next.toString)
    }
  }
  
  def _notify(json: String) {
    debug("message is being sent to %s: %s", this.topicName, json)
    this.jmsTemplate.send(this.topicName, new MessageCreator() {
           override def createMessage(session: Session): Message = session.createTextMessage(json)
     });
  }

}