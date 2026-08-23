import java.math.BigDecimal;

public class Usuario {
    public enum TipoUsuario {
        COMUM,
        LOJISTA,
    }

    private long id;
    private String nome;
    private String email;
    private BigDecimal saldo;
    private TipoUsuario tipoUsuario;

    public Usuario(long id, 
        String nome, 
        String email, 
        BigDecimal saldo, 
        TipoUsuario tipoUsuario) 
    {
        this.id = id;
        this.email = email;
        this.saldo = saldo;
        this.tipoUsuario = tipoUsuario;
        
    }

    //all getters
    public long GetId(){ return id; }
    public String GetNome(){ return nome; }
    public String GetEmail(){ return email; }
    public BigDecimal GetSaldo(){ return saldo; }
    public TipoUsuario getTipoUsuario(){ return tipoUsuario; }

    //all setters
    public void SetSaldo(BigDecimal saldo) {
        this.saldo = saldo; 
    }
}