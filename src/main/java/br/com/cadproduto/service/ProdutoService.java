package br.com.cadproduto.service;

import br.com.cadproduto.exception.NotFoundException;
import br.com.cadproduto.exception.ValidationException;
import br.com.cadproduto.entity.ProdutoEntity;
import br.com.cadproduto.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProdutoService {
    final ProdutoRepository produtoRepository;


    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoEntity salvar(ProdutoEntity produtoEntity) {
        if (produtoEntity.getNome() == null) {
            throw new ValidationException("Nome não pode ser nulo");
        }

        if (produtoEntity.getQuantidade() == null) {
            throw new ValidationException("Quantidade não pode ser nulo");
        }

        return produtoRepository.save(produtoEntity);
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    public ProdutoEntity getById(Long id) {
        ProdutoEntity produtoEntity = produtoRepository.findById(id).orElse(null);
        if (produtoEntity == null) {
            throw new NotFoundException("ID não encontrado");
        }
        return produtoEntity;
    }

    public List<ProdutoEntity> produtoModelList() {
        return produtoRepository.findAll();
    }


}
