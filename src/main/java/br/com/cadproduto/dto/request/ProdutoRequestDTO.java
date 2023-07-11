package br.com.cadproduto.dto.request;

public class ProdutoRequestDTO {
    private String nome;
    private Integer quantidade;

    public String getNome() {
        return nome;
    }

    public ProdutoRequestDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public ProdutoRequestDTO setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }
}
