package com.youtan.leilao.controller;

import com.youtan.leilao.DTO.VeiculoDTO;
import com.youtan.leilao.service.VeiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<VeiculoDTO> listVeiculo() {
        return veiculoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO> getVeiculo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(veiculoService.getVeiculo(id));
    }

    @PostMapping
    public ResponseEntity<VeiculoDTO> createVeiculo(@RequestBody VeiculoDTO VeiculoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.createVeiculo(VeiculoDTO));
    }

    @PutMapping
    public ResponseEntity<VeiculoDTO> updateVeiculo(@RequestBody VeiculoDTO VeiculoDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(veiculoService.updateVeiculo(VeiculoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVeiculo(@PathVariable Long id) {
        veiculoService.deleteVeiculo(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
