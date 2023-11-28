import model.Funcionario;
import controller.FuncionarioController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        usei contantes para evitar duplicação
        final String OPERADOR = "Operador";
        final String GERENTE = "Gerente";


        List<String> nomes = List.of("Maria", "João", "Caio", "Miguel", "Alice", "Heitor", "Arthur", "Laura", "Heloísa", "Helena");
        List<String> dataNascimentos = List.of("2000-10-18", "1990-05-12", "1961-05-02", "1988-10-14", "1995-01-05", "1999-11-19", "1993-03-31", "1994-07-08", "2003-05-24", "1996-09-02");
        List<BigDecimal> salarios = List.of(BigDecimal.valueOf(2009.44), BigDecimal.valueOf(2284.38), BigDecimal.valueOf(9836.14), BigDecimal.valueOf(19119.88), BigDecimal.valueOf(2234.68), BigDecimal.valueOf(1582.72), BigDecimal.valueOf(4071.84), BigDecimal.valueOf(3017.45), BigDecimal.valueOf(1606.85), BigDecimal.valueOf(2799.93));
        List<String> funcoes = List.of(OPERADOR, OPERADOR, "Coordenador", "Diretor", "Recepcionista", OPERADOR, "Contador", GERENTE, "Eletricista", GERENTE);

        FuncionarioController func = new FuncionarioController();

        List<Funcionario> todosFuncionarios = new ArrayList<>();

        for (int i = 0; i < nomes.size(); i++) {
            LocalDate dataNascimento = LocalDate.parse(dataNascimentos.get(i));
            Funcionario funcionariosAgrupados = new Funcionario(nomes.get(i), dataNascimento, salarios.get(i), funcoes.get(i));
            todosFuncionarios.add(funcionariosAgrupados);
        }

//      3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
        func.addFuncionario(todosFuncionarios);

//      3.2 – Remover o funcionário “João” da lista.
        func.removerFuncionarioPorNome("joão");

//      3.3   formato dd/mm/aaaa
        func.imprimirListaFuncionarios();

//        3.4 – Os funcionários receberam 10% de aumento de salário
        func.addAumento();
        System.out.println("Imprimindo lista com aumento de 10% do salário de todos os funcionários.");
        func.imprimirListaFuncionarios();

//        3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
//        3.6 – Imprimir os funcionários, agrupados por função.
        func.imprimirAgrupadosFuncao();


//        3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        func.imprimirAniversarioOutDez();


//        3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
        func.imprimirMaisVelho();

//        3.10 – Imprimir a lista de funcionários por ordem alfabética.
        func.imprimirListaOrdemAlfabetica();

//        3.11 – Imprimir o total dos salários dos funcionários.
        func.imprimirSomaTotalSalarios();

//        3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
        func.imprimirQtdSalariosMinimos();
    }
}