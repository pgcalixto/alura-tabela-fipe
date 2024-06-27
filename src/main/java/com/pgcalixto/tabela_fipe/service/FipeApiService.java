package com.pgcalixto.tabela_fipe.service;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pgcalixto.tabela_fipe.model.Ano;
import com.pgcalixto.tabela_fipe.model.Marca;
import com.pgcalixto.tabela_fipe.model.Modelo;
import com.pgcalixto.tabela_fipe.model.Modelos;
import com.pgcalixto.tabela_fipe.model.TipoVeiculo;
import com.pgcalixto.tabela_fipe.model.Veiculo;

@Service
public class FipeApiService {

    private static final RestTemplate REST_TEMPLATE = new RestTemplateBuilder()
            .rootUri("https://parallelum.com.br/fipe/api/v1")
            .build();

    private static final Map<TipoVeiculo, String> URL_POR_TIPO_VEICULO = new EnumMap<>(Map.ofEntries(
            Map.entry(TipoVeiculo.CAMINHAO, "caminhoes"),
            Map.entry(TipoVeiculo.CARRO, "carros"),
            Map.entry(TipoVeiculo.MOTO, "motos")));

    private String buildUrl(final TipoVeiculo tipoVeiculo, final String url) {
        return "/" + URL_POR_TIPO_VEICULO.get(tipoVeiculo) + url;
    }

    public List<Marca> getMarcas(final TipoVeiculo tipoVeiculo) {
        final String url = buildUrl(tipoVeiculo, "/marcas");

        final Marca[] marcasResponse = REST_TEMPLATE.getForObject(url, Marca[].class);

        return Arrays.asList(marcasResponse);
    }

    public List<Modelo> getModelos(final TipoVeiculo tipoVeiculo, final int codigoMarca) {
        final String url = buildUrl(tipoVeiculo, "/marcas/" + codigoMarca + "/modelos");

        final Modelos modelosResponse = REST_TEMPLATE.getForObject(url, Modelos.class);

        List<Modelo> modelos = modelosResponse != null
                ? modelosResponse.modelos()
                : null;

        return modelos;
    }

    public List<Ano> getAnos(
            final TipoVeiculo tipoVeiculo,
            final int codigoMarca,
            final int codigoModelo) {

        final String url = buildUrl(
                tipoVeiculo,
                "/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos");

        final Ano[] anosResponse = REST_TEMPLATE.getForObject(url, Ano[].class);

        return Arrays.asList(anosResponse);
    }

    public Veiculo getVeiculos(
            final TipoVeiculo tipoVeiculo,
            final int codigoMarca,
            final int codigoModelo,
            final String codigoAno) {

        final String url = buildUrl(
                tipoVeiculo,
                "/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos/" + codigoAno);

        final Veiculo veiculo = REST_TEMPLATE.getForObject(url, Veiculo.class);

        return veiculo;
    }
}
