package com.sanjiv.awsmessaging.instructionservice.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.sanjiv.awsmessaging.instructionservice.common.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InstructionService {
    private static final Logger log = LoggerFactory.getLogger(InstructionService.class);
    @Value("${sqs.url}")
    private String sqsURL;
    public void processMessage(String requestMessage) {
        try {

            final AmazonSQS sqs = MessageHelper.createAmazonSQS();
            log.info("===============================================");
            log.info("Getting Started with Amazon SQS Standard Queues");
            log.info("===============================================");

            log.info("Sending a message to MyQueue.");
            /*
            * we can insert the data here in the NOSQL db
            * For time being i am inserting it in the sqs
            * */
            sqs.sendMessage(new SendMessageRequest(sqsURL, requestMessage));
            log.info("Message Sent.");

        }catch (final AmazonServiceException ase) {
            log.error("Caught an AmazonServiceException, which means " +
                    "your request made it to Amazon SQS, but was " +
                    "rejected with an error response for some reason.");
            log.error("Error Message:    " + ase.getMessage());
            log.error("HTTP Status Code: " + ase.getStatusCode());
            log.error("AWS Error Code:   " + ase.getErrorCode());
            log.error("Error Type:       " + ase.getErrorType());
            log.error("Request ID:       " + ase.getRequestId());
        } catch (final AmazonClientException ace) {
            log.error("Caught an AmazonClientException, which means " +
                    "the client encountered a serious internal problem while " +
                    "trying to communicate with Amazon SQS, such as not " +
                    "being able to access the network.");
            log.error("Error Message: " + ace.getMessage());
        }
    }
}
