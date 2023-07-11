package br.com.cadproduto.repository;

import br.com.cadproduto.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
}
