package controller;

import model.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FuncionarioController {
    private List<Funcionario> listaFuncionarios;

    public FuncionarioController() {
        this.listaFuncionarios = new ArrayList<>();
    }

//    método para o caso de passar uma instância do Funcionario, ao invés de uma lista (para o caso de inserir manualmente 1 a 1)
    public void addFuncionario(Funcionario func) {
        listaFuncionarios.add(func);
    }

    public void addFuncionario(List<Funcionario> funcs) {
        listaFuncionarios.addAll(funcs);
    }

    public void imprimirListaFuncionarios() {
        listaFuncionarios.forEach(this::formatacao);
    }

    private void formatacao(Funcionario func) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        System.out.println("Nome: " + func.getNome());
        System.out.println("Função: " + func.getFuncao());
        System.out.println("Salário: " + decimalFormat.format(func.getSalario()));
        System.out.println("Data Nascimento: " + dateFormat.format(func.getDataNascimento()));
        System.out.println("-----------------------------");
    }

    public void removerFuncionarioPorNome(Funcionario func) {
        if (listaFuncionarios.contains(func)) {
            listaFuncionarios.remove(func);
            System.out.println("Funcionário (" + func.getNome() + ") removido.");
        } else {
            System.out.println("Funcionário não encontrado na lista.");
        }
    }

    public void removerFuncionarioPorNome(String nome) {
        // Verifica se o usuário existe antes de procurar,
        // Essa abordagem só é possível usando a API java a partir do java 8, qualquer versão anterior, terá erro
        // Vou usar esse método para remover a primeira ocorrência do nome na lista e então remover.
        listaFuncionarios.stream()
                .filter(funcionario -> funcionario.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .ifPresentOrElse(
                        funcionarioEncontrado -> {
                            listaFuncionarios.remove(funcionarioEncontrado);
                            System.out.println("Funcionário (" + nome + ") removido.");
                        },
                        () -> System.out.println("Funcionário com o nome '" + nome + "' não encontrado.")
                );
    }

    public void addAumento() {
        System.out.println("Aplicando aumento de salário: \n");
        listaFuncionarios.forEach(funcionario -> {
            BigDecimal novoSalario = funcionario.getSalario().multiply(BigDecimal.valueOf(1.10));
            funcionario.setSalario(novoSalario);
        });
    }

    public Map<String, List<Funcionario>> agruparFuncao() {
        System.out.println("Chamando funcão agruparFuncionariosPorFuncao");
        return listaFuncionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    public void imprimirAgrupadosFuncao() {
        System.out.println("Chamando funcão imprimirFuncionariosAgrupadosPorFuncao");

        Map<String, List<Funcionario>> funcionariosAgrupados = agruparFuncao();

        funcionariosAgrupados.forEach((funcao, funcionariosListados) -> {
            System.out.println("Funcionários da função '" + funcao + "':");
            funcionariosListados.forEach(this::formatacao);
            System.out.println();
        });
    }

    public void imprimirMaisVelho() {
        // Encontrar o funcionário com a maior idade usando Streams e Comparator
        Funcionario funcionarioMaisVelho = listaFuncionarios.stream()
                .max(Comparator.comparingInt(f -> calcularIdade(f.getDataNascimento())))
                .orElse(null);

        // Imprimir os atributos do funcionário com a maior idade
        if (funcionarioMaisVelho != null) {
            System.out.println("Funcionário com a maior idade:");
            System.out.println("Nome: " + funcionarioMaisVelho.getNome());
            System.out.println("Idade: " + calcularIdade(funcionarioMaisVelho.getDataNascimento()));
        } else {
            System.out.println("Não há funcionários na lista.");
        }
    }

    // Método para calcular a idade com base na data de nascimento
    private int calcularIdade(LocalDate dataNascimento) {
        LocalDate hoje = LocalDate.now();
        return Period.between(dataNascimento, hoje).getYears();
    }

    public void imprimirListaOrdemAlfabetica() {
        // Ordenar a lista de funcionários por nome
        System.out.println("Imprimindo lista em ordem alfabética");
        listaFuncionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(this::formatacao);
    }

//    3.11 – Imprimir o total dos salários dos funcionários.
    public void imprimirSomaTotalSalarios() {
        System.out.println("Imprimindo a soma total dos salários\n");
        BigDecimal totalSalarios = listaFuncionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Imprimir o total dos salários
        System.out.println("Soma total dos salários: " + NumberFormat.getCurrencyInstance().format(totalSalarios) + "\n");
    }

    public void imprimirQtdSalariosMinimos() {
        BigDecimal salMinimo = new BigDecimal("1212.00");
        listaFuncionarios.forEach(funcionario -> {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salMinimo, 2, RoundingMode.HALF_UP);
            System.out.println("Nome : " + funcionario.getNome());
            System.out.println("Quantidade Salários Mínimos: " + salariosMinimos);
            System.out.println("-----------------------------");
        });
    }

    public void imprimirAniversarioOutDez() {
        listaFuncionarios.stream()
                .filter(funcionario -> aniversarioEm(funcionario, Month.OCTOBER) || aniversarioEm(funcionario, Month.DECEMBER))
//                .filter(funcionario -> funcionario.getDataNascimento().getMonth() == Month.OCTOBER || funcionario.getDataNascimento().getMonth() == Month.DECEMBER)
                .forEach(this::formatacao);
    }

//    Método auxiliar para o usar "qualquer" mês
    private boolean aniversarioEm(Funcionario funcionario, Month mesDesejado) {
        return funcionario.getDataNascimento().getMonth() == mesDesejado;
    }
}
