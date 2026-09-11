package com.github.kenedy.paymentgateway.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kenedy.paymentgateway.domain.Transfer;
import com.github.kenedy.paymentgateway.dto.CreateTransferRequest;
import com.github.kenedy.paymentgateway.exceptions.UserNotFoundException;
import com.github.kenedy.paymentgateway.repositories.TransactionRepository;
import com.github.kenedy.paymentgateway.repositories.UserRepository;
import com.github.kenedy.paymentgateway.services.TransferService;

@RestController 
@RequestMapping("/transfers")
public class TransferController {
    private final TransactionRepository transferRepository;
    private final UserRepository userRepository;
    private final TransferService service;

    public TransferController(UserRepository userRepository, TransferService service, TransactionRepository transferRepository) {
        this.userRepository = userRepository;
        this.service = service;
        this.transferRepository = transferRepository;
    }

    @PostMapping 
    public Transfer create(@RequestBody CreateTransferRequest request) {
        Transfer transfer = new Transfer(
            userRepository.findById(request.getPayerId()).orElseThrow(() -> new UserNotFoundException("ERROR: payer not found")),
            request.getAmount(),
            userRepository.findById(request.getPayeeId()).orElseThrow(() -> new UserNotFoundException("ERROR: payee not found")));

        service.execute(transfer);
        return transfer;
    }

    @GetMapping
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }
    
}
