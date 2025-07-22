package com.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.service.ChatbotService;

@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

	@Autowired
    private ChatbotService chatbotService;

    @GetMapping("/ask")
    public String responder(@RequestParam String question) {
        return chatbotService.responder(question);
    }
}
