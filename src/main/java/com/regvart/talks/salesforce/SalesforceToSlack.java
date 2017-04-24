/**
 * Copyright 2017 Zoran Regvart
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.regvart.talks.salesforce;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Simple Spring Boot application that demonstrates the usage of Apache Camel
 * Salesforce component with Slack.
 *
 * There are two ways of running this example, either by uncommenting the
 * {@code @Component} below to use the Java Domain Specific Language or to
 * enable {@code autoStartup} in the
 * {@code src/main/resources/camel/salesforce-to-slack.xml} XML document to use
 * XML to define the route.
 *
 * Consult the README.md file on how to setup the Salesforce and Slack
 * instances.
 */
@SpringBootApplication
public class SalesforceToSlack {

    // Uncomment if you wish to use Java to configure the route
    // don't forget to add `import org.springframework.stereotype.Component;`
    // @Component
    class SalesforceToSlackRoute extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            from("salesforce:leads" // use the Salesforce component auto
                                    // configured from camel-salesforce-starter
                + "?notifyForOperationCreate=true" // we wish to be notified
                                                   // only for creation of new
                                                   // Leads
                + "&notifyForOperationDelete=false"//
                + "&notifyForOperationUpdate=false"//
                + "&sObjectName=Lead" // the SObject that we stream events on
                + "&updateTopic=true" // we will update the Topic
                + "&sObjectQuery=SELECT Id, Name FROM Lead") // SOQL the
                                                             // streaming is
                                                             // based upon

                    // use the Simple language to transform the received Data
                    // Transfer Object to Slack message format
                    .transform(simple("A new Lead created <https://eu11.salesforce.com/${body.id}|${body.name}>"))

                    .to("slack" // use the Slack component auto configured from
                                // camel-slack-starter
                        + "?username=Salesforce" // set the username
                        + "&iconEmoji=%3Acamel%3A"); // set the icon
        }

    }

    public static void main(final String[] args) {
        SpringApplication.run(SalesforceToSlack.class, args);
    }

}
