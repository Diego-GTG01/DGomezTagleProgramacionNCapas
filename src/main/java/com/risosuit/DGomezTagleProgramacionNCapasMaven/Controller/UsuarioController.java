package com.risosuit.DGomezTagleProgramacionNCapasMaven.Controller;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.ColoniaDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.EstadoDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.MunicipioDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.PaisDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.RolDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.UsuarioDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Pais;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Rol;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @Autowired
    private PaisDAOImplementation paisDAOImplementation;

    @Autowired
    private RolDAOImplementation rolDAOImplementation;
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;

    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;

    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;

    @GetMapping("")
    public String GetAll(Model model) {
        Result result = usuarioDAOImplementation.GetAll();
        model.addAttribute("usuarios", result.Objects);
        return "GetAll";

    }

//    @GetMapping("{idUsuario}")
//    public String GetById(@PathVariable("idUsuario") int idUsuario, Model model) {
//        Result result = usuarioDAOImplementation.GetById(idUsuario);
//        model.addAttribute("usuarios", result.Object);
//        return "GetAll";
//
//    }
    @GetMapping("{idUsuario}")
    public String GetByIdDetalle(@PathVariable("idUsuario") int idUsuario, Model model) {
        Result result = usuarioDAOImplementation.GetById(idUsuario);
        model.addAttribute("usuario", result.Object);
        return "DetalleUsuario";

    }

    @GetMapping("Form")
    public String Formulario(Model model) {
        model.addAttribute("Usuario", new Usuario());
        model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
        model.addAttribute("Roles", rolDAOImplementation.GetAll().Objects);

        return "Formulario";
    }

    @PostMapping("Form")
    public String Formulario(@Valid @ModelAttribute("Usuario") Usuario usuario, BindingResult bindingresult, Model model) {
        if (bindingresult.hasErrors()) {
            model.addAttribute("Usuario", usuario);
            model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
            model.addAttribute("Roles", rolDAOImplementation.GetAll().Objects);
            return "Formulario";
        } else {
            Result Result = usuarioDAOImplementation.Add(usuario);
            return "redirect:/Usuario";
        }

    }

    @GetMapping("getEstadosByPais/{IdPais}")
    @ResponseBody
    public Result getEstadoByPais(@PathVariable("IdPais") int IdPais) {
        Result result = estadoDAOImplementation.getEstadoByPais(IdPais);
        return result;
    }

    @GetMapping("getMunicipiosByEstado/{IdEstado}")
    @ResponseBody
    public Result getMunicipioByEstado(@PathVariable("IdEstado") int IdEstado) {
        Result result = municipioDAOImplementation.getMunicipioByEstado(IdEstado);
        return result;
    }

    @GetMapping("getColoniasByMunicipio/{IdMunicipio}")
    @ResponseBody
    public Result getColoniaByMunicipio(@PathVariable("IdMunicipio") int IdMunicipio) {
        Result result = coloniaDAOImplementation.getColoniaByMunicipio(IdMunicipio);
        return result;
    }

}
