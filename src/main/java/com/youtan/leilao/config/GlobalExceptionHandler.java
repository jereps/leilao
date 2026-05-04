package com.youtan.leilao.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public static ResponseEntity<?> handleErroSQL(SQLException ex){

        Map<String,Object> response = new HashMap<>();
        response.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("timestamp", LocalDateTime.now());
        response.put("mensagem","Erro ao tentar criar o SQL, gravar no Banco de Dados");
        response.put("erros",ex.getMessage());
        response.put("erro",ex.getNextException());


        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(NullPointerException.class)
    public static ResponseEntity<?> handleException(NullPointerException ex){

        Map<String,Object> response = new HashMap<>();
        response.put("status",ex.getCause());
        response.put("timestamp", LocalDateTime.now());
        response.put("mensagem","Erro geral");
        response.put("erros",ex.getMessage());
        response.put("erro",ex.getSuppressed());

        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
