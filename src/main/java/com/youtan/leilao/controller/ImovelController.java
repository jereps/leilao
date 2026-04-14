package com.youtan.leilao.controller;

import com.youtan.leilao.DTO.ImovelDTO;
import com.youtan.leilao.service.ImovelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imovel")
public class ImovelController {

    private final ImovelService imovelService;

    public ImovelController(ImovelService ImovelService) {
        this.imovelService = ImovelService;
    }

    @GetMapping
    public ResponseEntity<List<ImovelDTO>> listImoveis() {

        return ResponseEntity.status(HttpStatus.OK).body(imovelService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImovelDTO> getImovel(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(imovelService.getImovel(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImovelDTO> createImovel(@RequestBody ImovelDTO imovelDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(imovelService.createImovel(imovelDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImovelDTO> updateImovel(@PathVariable Long id,@RequestBody ImovelDTO imovelDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(imovelService.updateImovel(id, imovelDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteImovel(@PathVariable Long id) {
        imovelService.deleteImovel(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
