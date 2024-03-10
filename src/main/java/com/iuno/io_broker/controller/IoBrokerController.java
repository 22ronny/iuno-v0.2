package com.iuno.io_broker.controller;

import com.iuno.io_broker.entity.Switch2;
import com.iuno.io_broker.entity.WindowContact;
import com.iuno.io_broker.service.IoBrokerService;
import com.iuno.telegramBot.service.TelegramBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ioBrokerDataPoint")
public class IoBrokerController {

    private final IoBrokerService service;
    private final TelegramBot telegramBot;

    public IoBrokerController(IoBrokerService service, TelegramBot telegramBot) {
        this.service = service;
        this.telegramBot = telegramBot;
    }

    @PostMapping("/windowContact")
    void windowContact(@RequestBody WindowContact windowContact) {
        if (windowContact.getState()) {
            telegramBot.sendMassage("Tür offen");
        }
    }

    @PostMapping("/switch2")
    void receiveDataPointSwitch2(@RequestBody Switch2 switch2) {
        service.setBrightness(switch2);
    }
}
