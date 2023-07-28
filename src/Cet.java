import java.math.BigDecimal;
import java.math.RoundingMode;

public class Cet {

    // Método para calcular o Custo Efetivo Total (CET)
    public static BigDecimal calculateCET(BigDecimal valorEmprestimo, BigDecimal taxaJurosMensal, int prazoMeses, BigDecimal despesasAdicionais) {
        BigDecimal valorFinal = valorEmprestimo.add(despesasAdicionais);

        BigDecimal jurosAcumulados = BigDecimal.ZERO;
        BigDecimal parcela = calculateParcela(valorFinal, taxaJurosMensal, prazoMeses);

        for (int i = 0; i < prazoMeses; i++) {
            BigDecimal jurosMes = valorFinal.multiply(taxaJurosMensal);
            jurosAcumulados = jurosAcumulados.add(jurosMes);
            valorFinal = valorFinal.add(jurosMes).subtract(parcela);
        }

        BigDecimal CET = (jurosAcumulados.divide(valorEmprestimo, 4, RoundingMode.HALF_EVEN)).multiply(new BigDecimal("100"));
        return CET;
    }

    // Método auxiliar para calcular o valor da parcela mensal
    private static BigDecimal calculateParcela(BigDecimal valorEmprestimo, BigDecimal taxaJurosMensal, int prazoMeses) {
        BigDecimal taxaMensal = taxaJurosMensal.add(BigDecimal.ONE).pow(prazoMeses).subtract(BigDecimal.ONE);
        return valorEmprestimo.multiply(taxaJurosMensal).divide(taxaMensal, 2, RoundingMode.HALF_EVEN);
    }

    public static void main(String[] args) {
        // Exemplo de utilização do método de cálculo do CET
        BigDecimal valorEmprestimo = new BigDecimal("10000.00");
        BigDecimal taxaJurosMensal = new BigDecimal("0.02"); // 2% ao mês
        int prazoMeses = 12;
        BigDecimal despesasAdicionais = new BigDecimal("150.00");

        BigDecimal CET = calculateCET(valorEmprestimo, taxaJurosMensal, prazoMeses, despesasAdicionais);
        System.out.println("CET: " + CET + "%");
    }
}