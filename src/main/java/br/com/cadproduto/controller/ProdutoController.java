package br.com.cadproduto.controller;

import br.com.cadproduto.dto.request.ProdutoRequestDTO;
import br.com.cadproduto.dto.response.ProdutoResponseDTO;
import br.com.cadproduto.model.ProdutoModel;
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
        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome(requestDTO.getNome()).setQuantidade(requestDTO.getQuantidade());
        produtoModel = produtoService.salvar(produtoModel);

        ProdutoResponseDTO responseDTO = new ProdutoResponseDTO();
        responseDTO.setId(produtoModel.getId()).setNome(produtoModel.getNome())
                .setQuantidade(produtoModel.getQuantidade());

        return responseDTO;

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoResponseDTO> listar () {
        List <ProdutoResponseDTO> listaResponseDTO = new ArrayList<>();
        List <ProdutoModel> listaModel = produtoService.produtoModelList();

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
        ProdutoModel produtoModel = produtoService.getById(id);

        ProdutoResponseDTO produtoResponseDTO = new ProdutoResponseDTO()
                .setId(produtoModel.getId())
                .setNome(produtoModel.getNome())
                .setQuantidade(produtoModel.getQuantidade());

        return produtoResponseDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoResponseDTO editar(@PathVariable Long id, @RequestBody ProdutoRequestDTO produtorequestDTO) {
        ProdutoModel produtoModelEditado = new ProdutoModel();
        produtoModelEditado
                .setId(id)
                .setNome(produtorequestDTO.getNome())
                .setQuantidade(produtorequestDTO.getQuantidade());

        produtoModelEditado = produtoService.salvar(produtoModelEditado);

        ProdutoResponseDTO produtoResponseDTOeditado = new ProdutoResponseDTO();
        produtoResponseDTOeditado.setId(produtoModelEditado.getId())
                .setNome(produtoModelEditado.getNome())
                .setQuantidade(produtoModelEditado.getQuantidade());

        return produtoResponseDTOeditado;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        produtoService.deletar(id);
    }
}
