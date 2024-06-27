package com.pgcalixto.tabela_fipe.runner;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.pgcalixto.tabela_fipe.model.Ano;
import com.pgcalixto.tabela_fipe.model.Marca;
import com.pgcalixto.tabela_fipe.model.Modelo;
import com.pgcalixto.tabela_fipe.model.TipoVeiculo;
import com.pgcalixto.tabela_fipe.model.Veiculo;
import com.pgcalixto.tabela_fipe.service.FipeApiService;

@Component
public class TabelaFipeCommandLineRunner implements CommandLineRunner {

    @Autowired
    private FipeApiService fipeApiService;

    @Override
    public void run(String... args) throws Exception {

        // TIPO DO VEÍCULO

        final Scanner scanner = new Scanner(System.in);

        final TipoVeiculo tipoVeiculo = requisitaTipoVeiculo(scanner);

        final List<Marca> marcas = fipeApiService.getMarcas(tipoVeiculo);

        // MARCA

        final int codigoMarca = requisitaCodigoMarca(scanner, marcas);

        final List<Modelo> modelos = fipeApiService.getModelos(tipoVeiculo, codigoMarca);

        // MODELO - ANO

        int codigoModelo = requisitaCodigoModelo(scanner, modelos);

        final List<Ano> anos = fipeApiService.getAnos(tipoVeiculo, codigoMarca, codigoModelo);

        // VEICULO

        final List<Veiculo> veiculos = anos.stream()
                .map(ano ->
                    fipeApiService.getVeiculos(tipoVeiculo, codigoMarca, codigoModelo, ano.codigo()))
                .collect(Collectors.toList());

        System.out.println("Todos os veículos com os valores por ano:");

        veiculos.stream().forEach(System.out::println);

        scanner.close();
    }

    public TipoVeiculo requisitaTipoVeiculo(final Scanner scanner) {
        System.out.printf("Digite o tipo de veículo desejado %s:\n", Arrays.asList(TipoVeiculo.values()));

        final String entradaTipoVeiculo = scanner.nextLine().toUpperCase();

        TipoVeiculo tipoVeiculo = null;

        try {
            tipoVeiculo = TipoVeiculo.valueOf(entradaTipoVeiculo);
        } catch (IllegalArgumentException e) {
            scanner.close();
            throw new RuntimeException("Tipo de veículo inválido!");
        }

        return tipoVeiculo;
    }

    public int requisitaCodigoMarca(final Scanner scanner, final List<Marca> marcas) {

        marcas.stream().forEach(System.out::println);

        System.out.println("Digite o código da marca desejada:");

        int codigoMarca;

        try {
            codigoMarca = scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.close();
            throw new RuntimeException("Código da marca inválido!");
        }

        return codigoMarca;
    }

    private int requisitaCodigoModelo(final Scanner scanner, final List<Modelo> modelos) {

        modelos.stream().forEach(System.out::println);

        System.out.println("Digite o código do modelo desejado:");

        int codigoModelo;

        try {
            codigoModelo = scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.close();
            throw new RuntimeException("Código do modelo inválido!");
        }

        return codigoModelo;
    }
}
