import java.math.BigDecimal;

public class Transacoes{
    public void debitar(Usuario usuario, BigDecimal valor) {
        BigDecimal saldo = usuario.GetSaldo();

        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0){
            return /* ERRO */;
        }
        
        if (saldo.compareTo(valor) < 0) {
            return /* ERRO */;
        }

        saldo = saldo.subtract(valor);
        usuario.SetSaldo(saldo);
    }

    public void creditar(Usuario usuario, BigDecimal valor) {
        BigDecimal saldo = usuario.GetSaldo();

        saldo = saldo.add(valor);
        usuario.SetSaldo(saldo);
    }
}
