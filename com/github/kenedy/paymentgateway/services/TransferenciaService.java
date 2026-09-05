package com.github.kenedy.paymentgateway.services;

import com.github.kenedy.paymentgateway.Transferencia;
import com.github.kenedy.paymentgateway.Usuario.TipoUsuario;
import com.github.kenedy.paymentgateway.exceptions.DomainException;
import com.github.kenedy.paymentgateway.exceptions.MerchantCannotPayException;
import com.github.kenedy.paymentgateway.exceptions.SelfTransferException;

public class TransferenciaService {
    public void executar(Transferencia t) {
        try{
            if (t.getPagador().getTipoUsuario() == TipoUsuario.LOJISTA) {
                throw new MerchantCannotPayException();
            }
            
            if (t.getPagador().getId() == t.getRecebedor().getId()) {
                throw new SelfTransferException();
            }

            t.getPagador().debitar(t.getValor());
            t.getRecebedor().creditar(t.getValor());
            t.setStatusAsCompleted();

        } catch (DomainException e) {
            t.setStatusAsFailed();
            throw e;
        }
    }
}
