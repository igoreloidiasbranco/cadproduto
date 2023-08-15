package br.com.cadproduto.controller;

import br.com.cadproduto.dto.request.ProdutoRequestDTO;
import br.com.cadproduto.dto.response.ProdutoResponseDTO;
import br.com.cadproduto.entity.ProdutoEntity;
import br.com.cadproduto.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponseDTO salvar(@RequestBody ProdutoRequestDTO requestDTO) {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setNome(requestDTO.getNome()).setQuantidade(requestDTO.getQuantidade());
        produtoEntity = produtoService.salvar(produtoEntity);

        ProdutoResponseDTO responseDTO = new ProdutoResponseDTO();
        responseDTO.setId(produtoEntity.getId()).setNome(produtoEntity.getNome())
                .setQuantidade(produtoEntity.getQuantidade());

        return responseDTO;

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoResponseDTO> listar () {
        List <ProdutoResponseDTO> listaResponseDTO = new ArrayList<>();
        List <ProdutoEntity> listaModel = produtoService.produtoModelList();

        listaModel.forEach(produtoModel -> {
            ProdutoResponseDTO produtoResponseDTO = new ProdutoResponseDTO()
                    .setId(produtoModel.getId())
                    .setNome(produtoModel.getNome())
                    .setQuantidade(produtoModel.getQuantidade());
                    listaResponseDTO.add(produtoResponseDTO);
        });

        return listaResponseDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoResponseDTO consultar(@PathVariable Long id) {
        ProdutoEntity produtoEntity = produtoService.getById(id);

        ProdutoResponseDTO produtoResponseDTO = new ProdutoResponseDTO()
                .setId(produtoEntity.getId())
                .setNome(produtoEntity.getNome())
                .setQuantidade(produtoEntity.getQuantidade());

        return produtoResponseDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoResponseDTO editar(@PathVariable Long id, @RequestBody ProdutoRequestDTO produtorequestDTO) {
        ProdutoEntity produtoEntityEditado = new ProdutoEntity();
        produtoEntityEditado
                .setId(id)
                .setNome(produtorequestDTO.getNome())
                .setQuantidade(produtorequestDTO.getQuantidade());

        produtoEntityEditado = produtoService.salvar(produtoEntityEditado);

        ProdutoResponseDTO produtoResponseDTOeditado = new ProdutoResponseDTO();
        produtoResponseDTOeditado.setId(produtoEntityEditado.getId())
                .setNome(produtoEntityEditado.getNome())
                .setQuantidade(produtoEntityEditado.getQuantidade());

        return produtoResponseDTOeditado;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        produtoService.deletar(id);
    }
}
