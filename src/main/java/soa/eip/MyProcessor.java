package soa.eip;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;


/*Processor is a simple Java interface which is used to add custom integration logic to a route.
 * It contains a single process method used to preform custom business logic on a message received by a consumer
 */
public class MyProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {

        String request= exchange.getIn().getBody(String.class);

        String body = "";
        String maximus = "";

        for(String i : request.split(" ")) {
            // Check the n value of max
            if(i.matches("max:[0-9]+")) {

                maximus = i.split(":")[1];
            }
            // Get the rest of the request
            else{
                body += i + " ";
            }
        }
        exchange.getIn().setBody(body);
        // Set the value of count
        exchange.getIn().setHeader("count", maximus);
        //twitter-search:body?count=MAX
    }
}