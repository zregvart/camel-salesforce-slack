# Salesforce Example

This is the example demo from the Apache Camel & Salesforce talk given
at the Berlin Salesforce Developer Group Meetup by Zoran Regvart.

Link to the slides is [here](https://docs.google.com/presentation/d/1pmRcj7qJY3gHPTk5mt_KOcU5zfijvXyUFAkBxpwZH10/edit?usp=sharing).

## How to run the example

**NOTE:** Make sure that you have performed the needed configuration
outlined below.

**NOTE:** Make sure that you have enabled one of the routes either in
`src/main/java/com/regvart/talks/salesforce/SalesforceToSlack.java`
or in `src/main/resources/camel/salesforce-to-slack.xml` - see the
comments provieded there on instructions.

**NOTE:** You need an Java 8 to run this example, install it if you
haven't already.

To run set the environment variables:

| Variable name                           | Value                      |
|-----------------------------------------|----------------------------|
| CAMEL_COMPONENT_SALESFORCE_CLIENTID     | OAuth Consumer Key         |
| CAMEL_COMPONENT_SALESFORCE_CLIENTSECRET | OAuth Consumer Secret      |
| CAMEL_COMPONENT_SALESFORCE_REFRESHTOKEN | OAuth Refresh Token        |
| CAMEL_COMPONENT_SLACK_WEBHOOKURL        | Slack Incoming WebHook URL |

And run:

    $ ./mvnw

Create a Lead in Salesforce and observe the message that arrives at the
configured Slack channel.

To stop the example press <kbd>ctrl</kbd>+<kbd>c</kbd>.

## Configuring Salesforce

To run the example you need access to a Salesforce developer account,
you can sign up for your own Developer account at
<https://developer.salesforce.com/>. After you have done that, you'll
need to create a Connected Application for your integration.

To do this after logging in to your Salesforce Developer account,
navigate to _Apps_ located under _Build_ and then _Create_, there you
should see _Connected Apps_ table in the heading click on _New_ and fill
in the indicated required fields and enable the _OAuth Settings_, for
_Callback URL_ you can use <https://login.salesforce.com/services/oauth2/success>.

In the _Available OAuth Scopes_ add _Access and manage your data (api)_
and _Perform requests on your behalf at any time (refresh_token,
offline_access)_.

After clicking _Save_ click on _Manage_ on the top of the page and then
click on  _Edit Policies_. Change the _IP Relaxation_ to
_Relax IP restrictions_ and click on _Save_.

**NOTE:** This will get you started quicker, but production you should
re-evaluate to comply with your security needs.

Next gather your _Consumer Key_ (_clientId_ property), _Consumer Secret_
(clientSecret) and either use username and password of the developer
account; or get the refresh token from Salesforce (more on this below).

## Configuring Slack

To run the example you need to have the access to a Slack instance and
the ability to configure incoming WebHooks. Once logged in open
_Apps & Integrations_ menu and search for `Incoming WebHooks`. On the
_Incoming WebHooks_ screen select _Add Configuration_ and pick a channel
and continue by clicking on _Add Incoming WebHooks integration_ and make
note of the _Webhook URL_ provided.

## Additional Salesforce Tips and Tricks

### How to get the OAuth refresh token

In your browser go to the URL change the `__YOUR_CLIENT_ID_HERE__`
with your connected application _Consumer Key_:

<https://login.salesforce.com/services/oauth2/authorize?response_type=token&client_id=__YOUR_CLIENT_ID_HERE__&redirect_uri=https://login.salesforce.com/services/oauth2/success&display=touch>

Allow access to the application, and you'll end up on a page with
`refresh_token` after the `#`, something like:

`https://login.salesforce.com/services/oauth2/success#access_token=..&refresh_token=`**<refresh_token>**`&instance_url=...&issued_at=...&signature=...&scope=...&token_type=Bearer`

### How to generate Salesforce Data Transfer Objects (DTOs)

You can regenerate the generated DTOs in `src/generated/java` by
running the `camel-salesforce-maven-plugin`:

    $ ./mvnw org.apache.camel.maven:camel-salesforce-maven-plugin:generate \
      -DcamelSalesforce.clientId=<OAuth consumer key> \
      -DcamelSalesforce.clientSecret=<OAuth consumer secret> \
      -DcamelSalesforce.userName=<Salesforce username> \
      -DcamelSalesforce.password=<Salesforce password> \
      -DcamelSalesforce.includePattern=Lead

**NOTE:** Remember that if you're using Salesforce security token you
need to append it to your Salesforce password.
