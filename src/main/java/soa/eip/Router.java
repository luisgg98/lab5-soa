package soa.eip;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

// Tutorials
/*
* https://www.baeldung.com/apache-camel-intro
* https://camel.apache.org/components/latest/eips/choice-eip.html
* https://camel.apache.org/components/latest/eips/from-eip.html
* https://stackoverflow.com/questions/9636651/apache-camel-directstart-endpoint-what-does-it-mean
*
 */

@Component
public class Router extends RouteBuilder {

  public static final String DIRECT_URI = "direct:twitter";

  Processor myProcessor = new MyProcessor();

  @Override
  public void configure() {
    from(DIRECT_URI)
      .log("Body contains \"${body}\"")
      .log("Searching twitter for \"${body}\"!")
      .choice()
            .when(body().regex(".*max:[0-9]+.*")) // Check if there is a max
              .process(myProcessor)
              .toD("twitter-search:${body}?count=${header.count}")
              .endChoice()
            .otherwise() // Otherwise just works as usually
              .toD("twitter-search:${body}")
              .endChoice()
      .end()
      .log("Body now contains the response from twitter:\n${body}");
  }
}
