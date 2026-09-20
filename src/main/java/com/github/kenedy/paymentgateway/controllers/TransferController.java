package com.github.kenedy.paymentgateway.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kenedy.paymentgateway.domain.Transfer;
import com.github.kenedy.paymentgateway.domain.User;
import com.github.kenedy.paymentgateway.dto.CreateTransferRequest;
import com.github.kenedy.paymentgateway.dto.TransferResponse;
import com.github.kenedy.paymentgateway.exceptions.UserNotFoundException;
import com.github.kenedy.paymentgateway.repositories.TransactionRepository;
import com.github.kenedy.paymentgateway.repositories.UserRepository;
import com.github.kenedy.paymentgateway.services.TransferService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/transfers")
public class TransferController {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TransferService service;

    public TransferController(UserRepository userRepository, TransferService service, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.service = service;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping
    public TransferResponse create(@Valid @RequestBody CreateTransferRequest request) {
        User payer = userRepository.findById(request.getPayerId())
            .orElseThrow(() -> new UserNotFoundException("ERROR: payer not found"));
        User payee = userRepository.findById(request.getPayeeId())
            .orElseThrow(() -> new UserNotFoundException("ERROR: payee not found"));

        Transfer transfer = new Transfer(payer, request.getAmount(), payee);
        service.execute(transfer);
        return new TransferResponse(transfer);
    }

    @GetMapping
    public List<TransferResponse> findAll() {
        return transactionRepository.findAll().stream()
            .map(TransferResponse::new)
            .toList();
    }
    
}
