package com.risosuit.DGomezTagleProgramacionNCapasMaven.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            HttpServletRequest request,
            Model model) {

        

        if (error != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String loginError = (String) session.getAttribute("loginError");
                if (loginError != null) {
                    model.addAttribute("error", loginError);
                    session.removeAttribute("loginError");
                } else {
                    model.addAttribute("error", "Credenciales inválidas");
                }
            }
        }

        if (logout != null) {
            model.addAttribute("logout", "Has cerrado sesión exitosamente");
            logout= null;
        }

        return "login";
    }

}
