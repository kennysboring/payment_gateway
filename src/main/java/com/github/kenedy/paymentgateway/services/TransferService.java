package com.github.kenedy.paymentgateway.services;

import org.springframework.stereotype.Service;

import com.github.kenedy.paymentgateway.domain.Transfer;
import com.github.kenedy.paymentgateway.domain.User.UserType;
import com.github.kenedy.paymentgateway.exceptions.DomainException;
import com.github.kenedy.paymentgateway.exceptions.MerchantCannotPayException;
import com.github.kenedy.paymentgateway.exceptions.SelfTransferException;
import com.github.kenedy.paymentgateway.exceptions.IllegalValueException;
import com.github.kenedy.paymentgateway.repositories.TransactionRepository;

@Service 
public class TransferService {
    private final TransactionRepository repository;

    public TransferService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void execute(Transfer t) {
        if (t == null) {
            throw new IllegalValueException("ERROR: transfer cannot be null");
        }

        try{
            if (t.getPayer().getUserType() == UserType.MERCHANT) {
                throw new MerchantCannotPayException();
            }
            
            if (t.getPayer().getId().equals(t.getPayee().getId())) {
                throw new SelfTransferException();
            }

            t.getPayer().debit(t.getAmount());
            t.getPayee().credit(t.getAmount());
            t.setStatusAsCompleted();

        } catch (DomainException e) {
            t.setStatusAsFailed();
            throw e;

        } finally {
            repository.save(t);
        }
    }
}
