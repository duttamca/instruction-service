package com.sanjiv.awsmessaging.instructionservice.controller;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.sanjiv.awsmessaging.instructionservice.common.MessageHelper;
import com.sanjiv.awsmessaging.instructionservice.listener.NotificationListener;
import com.sanjiv.awsmessaging.instructionservice.service.InstructionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class InstructionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstructionController.class);
    @Autowired
    NotificationListener notificationListener;
    @Autowired
    InstructionService instructionService;

    public NotificationListener getNotificationListener() {
        return notificationListener;
    }

    public void setNotificationListener(NotificationListener notificationListener) {
        this.notificationListener = notificationListener;
    }

    public InstructionService getInstructionService() {
        return instructionService;
    }

    public void setInstructionService(InstructionService instructionService) {
        this.instructionService = instructionService;
    }

    @PostMapping("/sendMessageQueue")
    public @ResponseBody
    void write(@RequestBody String notificationData){
        LOGGER.info("START PROCESSING THE MESSAGE");
        getInstructionService().processMessage(notificationData);
        //this is a hack, need to find neater solution TODO
        getNotificationListener().getMessage();
    }
    @GetMapping("/hello")
    public String sayHello() {
        return "hello world!!!";
    }
}
