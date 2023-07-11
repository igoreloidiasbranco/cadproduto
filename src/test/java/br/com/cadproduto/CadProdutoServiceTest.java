package br.com.cadproduto;


import br.com.cadproduto.model.ProdutoModel;
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
        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome("produto teste").setQuantidade(7);

        ProdutoModel produtoModelSalvo = produtoService.salvar(produtoModel);

        Assertions.assertEquals(produtoModel.getNome() ,produtoModelSalvo.getNome());
        Assertions.assertEquals(produtoModel.getQuantidade(), produtoModelSalvo.getQuantidade());

    }

    @Test
    public void criar_com_nome_nulo() {
        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome(null).setQuantidade(1);


        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            ProdutoModel produtoModelTeste = produtoService.salvar(produtoModel);
        });

        Assertions.assertEquals("Nome não pode ser nulo" , exception.getMessage());

    }

    @Test
    public void criar_com_quantidade_nulo() {
        ProdutoModel produtoModel = new ProdutoModel().setNome("Produto teste").setQuantidade(null);

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            ProdutoModel produtoModelTeste = produtoService.salvar(produtoModel);
        });

        Assertions.assertEquals("Quantidade não pode ser nulo" , exception.getMessage());
    }

    @Test
    public void deletar_produto() {
        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome("produto teste").setQuantidade(7);
        ProdutoModel produtoModelSalvo = produtoService.salvar(produtoModel);

        Long idSalvo = produtoModelSalvo.getId();

        produtoService.deletar(idSalvo);

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            ProdutoModel produtoModelDeletado = produtoService.getById(idSalvo);
        });

        Assertions.assertEquals("ID não encontrado" , exception.getMessage());

    }

    @Test
    public void testar_GetbyId_nulo() {
        Exception expeption = Assertions.assertThrows(Exception.class, () -> {

            ProdutoModel produtoModel = produtoService.getById(20L);
        });

        Assertions.assertEquals("ID não encontrado" , expeption.getMessage());
    }

    @Test
    public void testar_GetbyId_valido() {
        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome("produto teste").setQuantidade(2);

        ProdutoModel produtoModelSalvo = produtoService.salvar(produtoModel);

        Long idValido = produtoModelSalvo.getId();

        ProdutoModel produtoModelTesteGet = produtoService.getById(idValido);
        Assertions.assertEquals((produtoModelSalvo.getId()), produtoModelTesteGet.getId());
        Assertions.assertEquals(produtoModelSalvo.getNome(), produtoModelTesteGet.getNome());
        Assertions.assertEquals(produtoModelSalvo.getQuantidade(), produtoModelTesteGet.getQuantidade());

    }

}
