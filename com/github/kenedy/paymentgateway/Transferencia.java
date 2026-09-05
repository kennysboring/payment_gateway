package com.github.kenedy.paymentgateway;

import java.math.BigDecimal;

import com.github.kenedy.paymentgateway.exceptions.IllegalValueException;

public class Transferencia {
    private enum StatusTransaction {
        PENDING,
        COMPLETED,
        FAILED,
    }

    private Usuario pagador; 
    private Usuario recebedor;
    private BigDecimal valor; 
    private StatusTransaction status;

    public Transferencia(Usuario pagador, BigDecimal valor, Usuario recebedor) {
        if (pagador == null || recebedor == null) {
            throw new IllegalValueException("ERROR: pagador or recebedor not defined");
        }

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalValueException("ERROR: value is not accepted");
        }

        this.pagador = pagador;
        this.recebedor = recebedor;
        this.valor = valor;
        this.status = StatusTransaction.PENDING;
        }
    

    public Usuario getPagador() { return pagador; }
    public Usuario getRecebedor() { return recebedor; }
    public BigDecimal getValor() { return valor; }

    public void setStatusAsCompleted() { this.status = StatusTransaction.COMPLETED; }
    public void setStatusAsFailed() { this.status = StatusTransaction.FAILED; }
    }

