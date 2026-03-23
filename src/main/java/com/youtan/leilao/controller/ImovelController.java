package com.youtan.leilao.controller;

import com.youtan.leilao.DTO.ImovelDTO;
import com.youtan.leilao.service.ImovelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imovel")
public class ImovelController {

    private final ImovelService imovelService;

    public ImovelController(ImovelService leilaoService) {
        this.imovelService = leilaoService;
    }

    @GetMapping
    public List<ImovelDTO> listImoveis() {
        return imovelService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImovelDTO> getImovel(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(imovelService.getImovel(id));
    }

    @PostMapping
    public ResponseEntity<ImovelDTO> createImovel(@RequestBody ImovelDTO imovelDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(imovelService.createImóvel(imovelDTO));
    }

    @PutMapping
    public ResponseEntity<ImovelDTO> updateImovel(@RequestBody ImovelDTO imovelDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(imovelService.updateImovel(imovelDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteImovel(@PathVariable Long id) {
        imovelService.deleteImovel(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
