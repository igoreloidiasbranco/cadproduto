package br.com.cadproduto;


import br.com.cadproduto.entity.ProdutoEntity;
import br.com.cadproduto.service.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadProdutoServiceTest {

    @Autowired
    private ProdutoService produtoService;

    @Test
    public void criar_produto() {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setNome("produto teste").setQuantidade(7);

        ProdutoEntity produtoEntitySalvo = produtoService.salvar(produtoEntity);

        Assertions.assertEquals(produtoEntity.getNome() , produtoEntitySalvo.getNome());
        Assertions.assertEquals(produtoEntity.getQuantidade(), produtoEntitySalvo.getQuantidade());

    }

    @Test
    public void criar_com_nome_nulo() {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setNome(null).setQuantidade(1);


        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            ProdutoEntity produtoEntityTeste = produtoService.salvar(produtoEntity);
        });

        Assertions.assertEquals("Nome não pode ser nulo" , exception.getMessage());

    }

    @Test
    public void criar_com_quantidade_nulo() {
        ProdutoEntity produtoEntity = new ProdutoEntity().setNome("Produto teste").setQuantidade(null);

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            ProdutoEntity produtoEntityTeste = produtoService.salvar(produtoEntity);
        });

        Assertions.assertEquals("Quantidade não pode ser nulo" , exception.getMessage());
    }

    @Test
    public void deletar_produto() {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setNome("produto teste").setQuantidade(7);
        ProdutoEntity produtoEntitySalvo = produtoService.salvar(produtoEntity);

        Long idSalvo = produtoEntitySalvo.getId();

        produtoService.deletar(idSalvo);

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            ProdutoEntity produtoEntityDeletado = produtoService.getById(idSalvo);
        });

        Assertions.assertEquals("ID não encontrado" , exception.getMessage());

    }

    @Test
    public void testar_GetbyId_nulo() {
        Exception expeption = Assertions.assertThrows(Exception.class, () -> {

            ProdutoEntity produtoEntity = produtoService.getById(20L);
        });

        Assertions.assertEquals("ID não encontrado" , expeption.getMessage());
    }

    @Test
    public void testar_GetbyId_valido() {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setNome("produto teste").setQuantidade(2);

        ProdutoEntity produtoEntitySalvo = produtoService.salvar(produtoEntity);

        Long idValido = produtoEntitySalvo.getId();

        ProdutoEntity produtoEntityTesteGet = produtoService.getById(idValido);
        Assertions.assertEquals((produtoEntitySalvo.getId()), produtoEntityTesteGet.getId());
        Assertions.assertEquals(produtoEntitySalvo.getNome(), produtoEntityTesteGet.getNome());
        Assertions.assertEquals(produtoEntitySalvo.getQuantidade(), produtoEntityTesteGet.getQuantidade());

    }

}
