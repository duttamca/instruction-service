package com.sanjiv.awsmessaging.instructionservice.common;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

public class MessageHelper {

    public static AmazonSQS createAmazonSQS() {
        AmazonSQS sqs = AmazonSQSClientBuilder.standard()
                .withCredentials(getCredentialsProvider())
                .withRegion(Regions.US_EAST_1)
                .build();

        return  sqs;
    }

    public static ProfileCredentialsProvider getCredentialsProvider() {
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }
        return  credentialsProvider;
    }

    public static List<Message> receiveMessagesFromSQS(AmazonSQS sqs, String sqsURL) {
        final ReceiveMessageRequest receiveMessageRequest =
                new ReceiveMessageRequest(sqsURL)
                        .withMaxNumberOfMessages(1)
                        .withWaitTimeSeconds(3);
        final List<Message> messages = sqs.receiveMessage(receiveMessageRequest)
                .getMessages();

        return  messages;
    }

    public static void deleteMessageFromSQS(AmazonSQS amazonSQS, Message message, String queueURL) {
        String messageReceiptHandle = message.getReceiptHandle();
        amazonSQS.deleteMessage(new DeleteMessageRequest(queueURL,messageReceiptHandle));
    }
}
