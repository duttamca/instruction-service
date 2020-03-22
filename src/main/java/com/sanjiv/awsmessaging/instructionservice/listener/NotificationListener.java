package com.sanjiv.awsmessaging.instructionservice.listener;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.sanjiv.awsmessaging.instructionservice.common.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationListener{

    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);

    @Value("${rest.endpoint.notification}")
    private String notificationUrl;

    @Value("${sqs.url}")
    private String sqsURL;

    @Scheduled(fixedRate = 1000)
    @SqsListener("MySQSQueue")
    public void getMessage() {
        final AmazonSQS sqs = MessageHelper.createAmazonSQS();
        while(true) {
            log.info("Receiving messages from MyQueue.");
            List<Message> messages = MessageHelper.receiveMessagesFromSQS(sqs, sqsURL);
            for (final com.amazonaws.services.sqs.model.Message message : messages) {
                log.info("Message");
                log.info("  MessageId:     " + message.getMessageId());
                log.info("  ReceiptHandle: " + message.getReceiptHandle());
                log.info("  MD5OfBody:     " + message.getMD5OfBody());
                log.info("  Body:          " + message.getBody());
                if(!"".equals(message.getBody())) {
                    log.info("Calling POST /notification to insert records into database");
                    /*
                    * CALL DATA SERVICE FROM HERE TO INSERT THE RECORD IN THE DATABASE
                    * CALL TO DATA SERVICE SEEMS WILL BE SYNCHRONOUS
                    * */
                    //RestTemplate rest = new RestTemplate();
                    //ResponseEntity<String> companyInfoResponse = rest.postForEntity(notificationUrl, message.getBody(), String.class);
                    //String s = companyInfoResponse.getBody();
                    //delete message from the queue once processing is done
                    MessageHelper.deleteMessageFromSQS(sqs,message,sqsURL);
                }
            }
        }
    }

}
