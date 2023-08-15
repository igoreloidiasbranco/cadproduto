package br.com.cadproduto.repository;

import br.com.cadproduto.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
}
