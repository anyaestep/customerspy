Scala Spring Maven html5
====================================
A demo project - live feed via websockets generated from tailing a mongoDB collection 

Application
====================================
The application consists of several modules:
- a server-side sample generator that populates a capped MongoDB collection 
- a server-side feeder which tails the collection and usess messaging to notify of additions. ActiveMQ is used as the broker and it
  is configured for websockets transport. 
- a client that subscribes to the queue and produces live feed on the screen

Prerequisites
====================================
- The application expects mongoDB running on localhost with the default parameters.

- src/main/webapp/img is empty and needs some sample images

Installation
====================================

To install
'mvn clean install'

To run with jetty
'mvn jetty:run'
