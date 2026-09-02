package com.github.kenedy.paymentgateway;

import java.math.BigDecimal;
import com.github.kenedy.paymentgateway.exceptions.IllegalValueException;
import com.github.kenedy.paymentgateway.exceptions.InsufficientBalanceException;

public class Usuario {
    public enum TipoUsuario {
        COMUM,
        LOJISTA,
    }

    private final long id;
    private String nome;
    private final String cpf;
    private String email;
    private BigDecimal saldo;
    private TipoUsuario tipoUsuario;

    public Usuario(long id, 
        String cpf,
        String nome, 
        String email, 
        BigDecimal saldo, 
        TipoUsuario tipoUsuario) 
    {
        if (id <= 0) {
            throw new IllegalValueException("ERROR: id cannot be less than 0(zero)");
        }

        if (cpf == null || cpf.isBlank()) {
            throw new IllegalValueException("ERROR: cpf is null");
        }

        if (nome == null || nome.isBlank()) {
            throw new IllegalValueException("ERROR: name cannot be blank");
        }

        if (email == null || !email.contains("@")) {
            throw new IllegalValueException("ERROR: invalid email");
        }

        if (saldo == null || saldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalValueException("ERROR: saldo cannot be less than 0(zero)");
        }

        if (tipoUsuario == null) {
            throw new IllegalValueException("ERROR: select a user valid type");
        }
        
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.saldo = saldo;
        this.tipoUsuario = tipoUsuario;
        
    }

    public void debitar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalValueException("ERROR to debit: value is not accepted");
        }
        
        if (this.saldo.compareTo(valor) < 0) {
            throw new InsufficientBalanceException(valor, this.saldo);
        }

        this.saldo = this.saldo.subtract(valor);
    }

    public void creditar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalValueException("ERROR to credit: value is not accepted");
        }
        
        this.saldo = this.saldo.add(valor);
    }

    //all getters
    public long getId(){ return id; }
    public String getCpf(){ return cpf; }
    public String getNome(){ return nome; }
    public String getEmail(){ return email; }
    public BigDecimal getSaldo(){ return saldo; }
    public TipoUsuario getTipoUsuario(){ return tipoUsuario; }
}