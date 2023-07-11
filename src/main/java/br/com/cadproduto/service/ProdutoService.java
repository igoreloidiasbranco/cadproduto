package br.com.cadproduto.service;

import br.com.cadproduto.exception.NotFoundException;
import br.com.cadproduto.exception.ValidationException;
import br.com.cadproduto.model.ProdutoModel;
import br.com.cadproduto.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProdutoService {
    final ProdutoRepository produtoRepository;


    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoModel salvar(ProdutoModel produtoModel) {
        if (produtoModel.getNome() == null) {
            throw new ValidationException("Nome não pode ser nulo");
        }

        if (produtoModel.getQuantidade() == null) {
            throw new ValidationException("Quantidade não pode ser nulo");
        }

        return produtoRepository.save(produtoModel);
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    public ProdutoModel getById(Long id) {
        ProdutoModel produtoModel = produtoRepository.findById(id).orElse(null);
        if (produtoModel == null) {
            throw new NotFoundException("ID não encontrado");
        }
        return produtoModel;
    }

    public List<ProdutoModel> produtoModelList() {
        return produtoRepository.findAll();
    }


}
