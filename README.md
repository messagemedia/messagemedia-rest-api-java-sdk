MessageMedia REST API SDK 

## Examples
We have provided a collection of examples for all features in the example project

## CLI Examples

The CLI provides an interface for testing the functionality provided by this SDK.

It provides the following actions:

* SendMessage --help
* CancelMessage (String message_id)
* CheckDeliveryReports 
* CheckReplies
* ConfirmDeliveryReports (String message_id1, String message_id2 ... String message_idN) 
* ConfirmReplies (String message_id1, String message_id2 ... String message_idN) 
* GetMessageStatus (String message_id)

For example, to send a message with the CLI, after building it: 
 
```
java -Dcom.messagemedia.restapi.examples.api_key=<api_key> \
     -Dcom.messagemedia.restapi.examples.secret_key=<secret_key> \
     -jar rest-api-java-sdk-cli/target/rest-api-java-sdk-cli-1.0.0-SNAPSHOT-jar-with-dependencies.jar \
     SendMessage <destination_number> "Hello world"
```

