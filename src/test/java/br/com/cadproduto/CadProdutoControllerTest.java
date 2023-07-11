package br.com.cadproduto;

import br.com.cadproduto.dto.request.ProdutoRequestDTO;
import br.com.cadproduto.dto.response.ProdutoResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadProdutoControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void criar_produto_rest() {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome("Produto teste").setQuantidade(1);

        ResponseEntity<ProdutoResponseDTO> responseDTO =
                restTemplate.exchange(
                        "/produtos", HttpMethod.POST,
                        new HttpEntity<>(requestDTO), ProdutoResponseDTO.class);

        Assertions.assertEquals(201, responseDTO.getStatusCode().value());
        Assertions.assertNotNull(responseDTO.getBody());
        Assertions.assertNotNull(responseDTO.getBody().getId());
        Assertions.assertEquals(requestDTO.getNome(), responseDTO.getBody().getNome());
        Assertions.assertEquals(requestDTO.getQuantidade(), responseDTO.getBody().getQuantidade());
    }

    @Test
    public void listar_produtos() {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome("Produto1 teste listar").setQuantidade(1);

        ResponseEntity<ProdutoResponseDTO> responseDTO =
                restTemplate.exchange(
                        "/produtos",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDTO),
                        ProdutoResponseDTO.class);

        Long id1 = responseDTO.getBody().getId();

        requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome("Produto2 teste listar")
                .setQuantidade(2);

        responseDTO =
                restTemplate.exchange(
                        "/produtos", HttpMethod.POST,
                        new HttpEntity<>(requestDTO), ProdutoResponseDTO.class);
        Long id2 = responseDTO.getBody().getId();

        ResponseEntity<List<ProdutoResponseDTO>> responseGetDTO =
                restTemplate.exchange(
                        "/produtos",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ProdutoResponseDTO>>() {});


        List<ProdutoResponseDTO> produtosLista = responseGetDTO.getBody();

        Assertions.assertFalse(produtosLista.isEmpty());

        boolean existeProduto1 = false;
        boolean existeProduto2 = false;

        for (ProdutoResponseDTO produtoResponseDTO : produtosLista) {
            if (produtoResponseDTO.getId().equals(id1)){
                existeProduto1 = true;
            } else if (produtoResponseDTO.getId().equals(id2)) {
                existeProduto2 = true;
            }
        }

        Assertions.assertTrue(existeProduto1);

        Assertions.assertTrue(existeProduto2);

        Assertions.assertTrue(
                produtosLista.stream().anyMatch(produtoResponseDTO -> produtoResponseDTO.getId()
                        .equals(id2)));

    }

    @Test
    public void get_produto_by_id() {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome("Produto teste").setQuantidade(1);

        ResponseEntity<ProdutoResponseDTO> responseDTO =
                restTemplate.exchange(
                        "/produtos", HttpMethod.POST,
                        new HttpEntity<>(requestDTO),
                        ProdutoResponseDTO.class);

        Long id = responseDTO.getBody().getId();

        ResponseEntity<ProdutoResponseDTO> responseGetDTO =
                restTemplate.exchange(
                        "/produtos/" + id,
                        HttpMethod.GET,
                        null,
                        ProdutoResponseDTO.class);

        ProdutoResponseDTO responseBody = responseGetDTO.getBody();

        Assertions.assertEquals(200, responseGetDTO.getStatusCode().value());
        Assertions.assertEquals(id, responseBody.getId());
        Assertions.assertEquals(requestDTO.getNome(), responseBody.getNome());
        Assertions.assertEquals(requestDTO.getQuantidade(), responseBody.getQuantidade());

    }

    @Test
    public void editar_produto() {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome(" produto teste editar").setQuantidade(3);

        ResponseEntity<ProdutoResponseDTO> responseDTO =
                restTemplate.exchange(
                        "/produtos",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDTO),
                        ProdutoResponseDTO.class);

        Long id = responseDTO.getBody().getId();

        requestDTO.setNome("produto teste editado").setQuantidade(4);

        ResponseEntity<ProdutoResponseDTO> responseDTOeditar =
                restTemplate.exchange(
                        "/produtos/" + id,
                        HttpMethod.PUT,
                        new HttpEntity<>(requestDTO),
                        ProdutoResponseDTO.class);

        Assertions.assertEquals(200, responseDTOeditar.getStatusCode().value());
        Assertions.assertEquals(id, responseDTOeditar.getBody().getId());
        Assertions.assertEquals(requestDTO.getNome(), responseDTOeditar.getBody().getNome());
        Assertions.assertEquals(requestDTO.getQuantidade(), responseDTOeditar.getBody().getQuantidade());
    }

    @Test
    public void deletarProduto() {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome(" produto teste excluir").setQuantidade(3);

        ResponseEntity<ProdutoResponseDTO> responseDTO =
                restTemplate.exchange(
                        "/produtos",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDTO),
                        ProdutoResponseDTO.class);

        Long id = responseDTO.getBody().getId();

        ResponseEntity<?> responseDTOdeletar =
                restTemplate.exchange(
                        "/produtos/" + id,
                        HttpMethod.DELETE,
                        null,
                        Object.class);

        Assertions.assertEquals(204, responseDTOdeletar.getStatusCode().value());

        ResponseEntity<ProdutoResponseDTO> responseDTOconsultar =
                restTemplate.exchange(
                        "/produtos/" + id,
                        HttpMethod.GET,
                        null,
                        ProdutoResponseDTO.class);

        Assertions.assertEquals(NOT_FOUND, responseDTOconsultar.getStatusCode());
    }
}