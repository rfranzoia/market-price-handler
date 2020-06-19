# market-price-handler

This is a simple price handler that should be able to read prices from a queue, apply some changes on prices then post the new prices into a client REST API

The code is presented using the following technologies:

* Maven
* Spring-boot (version 2.1)
*-* Java (8 or 11)

You're going to need a developer machine with Java and Maven installed.
The code uses  a ConcurrentLinkedQueue and a ScheduledExecutorService to 
simulate the reception of the messages from an external 
Message provider (such as Kafka or SQS from AWS).

There's also an implementation of a partial REST API that will be running
at http://localhost:8080/client/prices, to simulate the actual price update
sending to a client.

There are unit tests included where some use JMockit and other use
SpringBootRunner. And JavaDoc on the most relevant part of the code.