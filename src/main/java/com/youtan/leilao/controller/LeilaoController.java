package com.youtan.leilao.controller;

import com.youtan.leilao.DTO.ItemLeilaoDTO;
import com.youtan.leilao.DTO.LeilaoDTO;
import com.youtan.leilao.service.LeilaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leilao")
public class LeilaoController {

    private final LeilaoService leilaoService;

    public LeilaoController(LeilaoService leilaoService) {
        this.leilaoService = leilaoService;
    }

    @GetMapping
    public ResponseEntity<List<LeilaoDTO>> listImoveis() {

        return ResponseEntity.status(HttpStatus.OK).body(leilaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeilaoDTO> getLeilao(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(leilaoService.getLeilao(id));
    }

    @PostMapping
    public ResponseEntity<LeilaoDTO> createLeilao(@RequestBody LeilaoDTO leilaoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leilaoService.createLeilao(leilaoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeilaoDTO> updateLeilao(@PathVariable Long id,@RequestBody LeilaoDTO leilaoDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(leilaoService.updateLeilao(id, leilaoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLeilao(@PathVariable Long id) {
        leilaoService.deleteLeilao(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/{tipo}")
    public ResponseEntity addItensLeilao(@PathVariable Long id, @PathVariable String tipo, @RequestBody  List<ItemLeilaoDTO> novosItens){
        leilaoService.addItensAoLeilao(id,novosItens,tipo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    
}
