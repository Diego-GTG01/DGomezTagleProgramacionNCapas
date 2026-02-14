/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosuit.DGomezTagleProgramacionNCapasMaven.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("demo")
public class DemoController {
    @GetMapping("Saludo")
    public String Saludo(){
        return "HolaMundo";
    }
    
    @GetMapping("SaludoPersonalizado/{nombre}")
    public String SaludoPersonalizado(@PathVariable("nombre") String nombre,Model model){
        model.addAttribute("nombre", nombre);
        return "SaludoPersonalizado";
    }
    

       
    
    @GetMapping("Suma/{numero1}/{numero2}")
    public String Suma(@PathVariable("numero1") int numero1,@PathVariable("numero2") int numero2,Model model){
        float Resultado=numero1+numero2;
        model.addAttribute("Resultado", Resultado);
        
        return "ResultadoOperacion";
    }
    @GetMapping("Resta/{numero1}/{numero2}")
    public String Resta(@PathVariable("numero1") int numero1,@PathVariable("numero2") int numero2,Model model){
        float Resultado=numero1-numero2;
        model.addAttribute("Resultado", Resultado);
        
        return "ResultadoOperacion";
    }
    @GetMapping("Multiplicacion/{numero1}/{numero2}")
    public String Multiplicacion (@PathVariable("numero1") int numero1,@PathVariable("numero2") int numero2,Model model){
        float Resultado=numero1*numero2;
        model.addAttribute("Resultado", Resultado);
        
        return "ResultadoOperacion";
    }
    @GetMapping("Division/{numero1}/{numero2}")
    public String Division (@PathVariable("numero1") int numero1,@PathVariable("numero2") int numero2,Model model){
        float Resultado=0f ;
        Resultado=numero1/numero2 ;
        model.addAttribute("Resultado", Resultado);
        
        return "ResultadoOperacion";
    }
    
    @GetMapping("Factorial/{numero1}")
    public String Factorial (@PathVariable("numero1") int numero1,Model model){
        int resultado = 1; 
        for(int i = 1;i<=numero1-1; i++){
            resultado = resultado*(i+1);
        }
        model.addAttribute("Resultado", resultado);
        
        return "ResultadoOperacion";
    }
    
    
  
}
