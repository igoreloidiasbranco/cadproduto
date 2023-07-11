package br.com.cadproduto.dto.response;

public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private Integer quantidade;


    public Long getId() {
        return id;
    }

    public ProdutoResponseDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public ProdutoResponseDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public ProdutoResponseDTO setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }
}
