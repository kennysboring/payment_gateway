package src;
import java.math.BigDecimal;

import src.exceptions.IllegalValueException;
import src.exceptions.InsufficientBalanceException;

public class Usuario {
    public enum TipoUsuario {
        COMUM,
        LOJISTA,
    }

    private long id;
    private String nome;
    private String cpf;
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

        
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.saldo = saldo;
        this.tipoUsuario = tipoUsuario;
        
    }

    public void debitar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalValueException("ERROR to debit: value is not accept ");
        }
        
        if (this.saldo.compareTo(valor) < 0) {
            throw new InsufficientBalanceException(valor, this.saldo);
        }

        this.saldo = this.saldo.subtract(valor);
    }

    public void creditar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalValueException("ERROR to credit: value is not accept ");
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