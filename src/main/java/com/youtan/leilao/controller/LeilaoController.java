package com.youtan.leilao.controller;

import com.youtan.leilao.DTO.ItemLeilaoDTO;
import com.youtan.leilao.DTO.LanceDTO;
import com.youtan.leilao.DTO.LeilaoDTO;
import com.youtan.leilao.service.LeilaoService;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<List<LeilaoDTO>> listLeilao() {

        return ResponseEntity.status(HttpStatus.OK).body(leilaoService.findAll());
    }

    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<LeilaoDTO> getLeilao(@PathVariable Long id) {
         return ResponseEntity.status(HttpStatus.OK).body(leilaoService.getLeilao(id));
    }

    @GetMapping("/itens/{id}")
    @PermitAll
    public ResponseEntity<List<ItemLeilaoDTO>> getLeilaoItens(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(leilaoService.getLeilaoItens(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LeilaoDTO> createLeilao(@RequestBody LeilaoDTO leilaoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leilaoService.createLeilao(leilaoDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LeilaoDTO> updateLeilao(@PathVariable Long id,@RequestBody LeilaoDTO leilaoDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(leilaoService.updateLeilao(id, leilaoDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteLeilao(@PathVariable Long id) {
        leilaoService.deleteLeilao(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addItensLeilao(@PathVariable Long id, @PathVariable String tipo, @RequestBody  List<ItemLeilaoDTO> novosItens){
        leilaoService.addItensAoLeilao(id,novosItens,tipo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/lance")
    public ResponseEntity novoLance(@RequestBody LanceDTO lance){
        leilaoService.novoLance(lance);
        return ResponseEntity.status(HttpStatus.OK).body("entrou");
    }

    @GetMapping("historico/{id}/{tipo}")
    public ResponseEntity lanceHistoricoItem(@PathVariable Long id,@PathVariable String tipo){
        return ResponseEntity.status(HttpStatus.OK).body(leilaoService.getHistorico(id,tipo));
    }
    
    
}
