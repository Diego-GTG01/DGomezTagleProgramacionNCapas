/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosuit.DGomezTagleProgramacionNCapasMaven.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.UsuarioJPADAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario;

import oracle.jdbc.proxy.annotation.Post;

/**
 *
 * @author ALIEN62
 */
@RestController
@RequestMapping("api/demo")
public class DemoRestController {
    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;

    @GetMapping
    public String HolaMundo() {
        return "HolaMundo";
    }

    @GetMapping("saludo/{nombre}")
    public String Saludo(@PathVariable("nombre") String nombre) {
        return "Hola " + nombre;
    }

    @GetMapping("saludo")
    public String Saludo2(@RequestParam("nombre") String nombre) {
        return "Hola " + nombre;
    }

    @GetMapping("datos/{status}")
    public ResponseEntity ObtenerDatos(@PathVariable("status") int status) {

        return ResponseEntity.status(status).body("");
    }

    @GetMapping("Suma/{numero1}/{numero2}")
    public ResponseEntity Suma(@PathVariable("numero1") int numero1, @PathVariable("numero2") int numero2) {
        return ResponseEntity.ok(numero1 + numero2);
    }

    @PostMapping("Suma")
    public ResponseEntity SumaPost(@RequestBody List<Integer> numeros) {
        int Resultado = 0;
        for (Integer integer : numeros) {
            Resultado += integer;
        }
        return ResponseEntity.ok(Resultado);
    }

    @GetMapping("ListaUsuarios")
    public ResponseEntity ListaUsuarios() {
        Result Result = usuarioJPADAOImplementation.GetAll();
        
        return ResponseEntity.ok(Result.Objects);
    }

}
