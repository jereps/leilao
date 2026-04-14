package com.youtan.leilao.controller;

import com.youtan.leilao.DTO.VeiculoDTO;
import com.youtan.leilao.service.VeiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/veiculo")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping
    public ResponseEntity<List<VeiculoDTO>> listVeiculo() {
        return ResponseEntity.status(HttpStatus.OK).body(veiculoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO> getVeiculo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(veiculoService.getVeiculo(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VeiculoDTO> createVeiculo(@RequestBody VeiculoDTO VeiculoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.createVeiculo(VeiculoDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VeiculoDTO> updateVeiculo(@PathVariable Long id, @RequestBody VeiculoDTO VeiculoDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(veiculoService.updateVeiculo(id,VeiculoDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteVeiculo(@PathVariable Long id) {
        veiculoService.deleteVeiculo(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
