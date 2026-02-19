package com.risosuit.DGomezTagleProgramacionNCapasMaven.Controller;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.ColoniaDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.DireccionDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.EstadoDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.MunicipioDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.PaisDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.RolDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.UsuarioDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Direccion;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Pais;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Rol;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario;
import jakarta.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.web.multipart.MultipartFile;

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
    private DireccionDAOImplementation direccionDAOImplementation;
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
        model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
        model.addAttribute("Roles", rolDAOImplementation.GetAll().Objects);
        model.addAttribute("Direccion", new Direccion());
        return "DetalleUsuario";

    }

    @PostMapping("guardarImagen")
    public String guardarImagen(
            @RequestParam("idUsuario") int idUsuario,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            Model model) {

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);

        if (!imagenFile.isEmpty()) {
            try {
                String base64 = Base64.getEncoder().encodeToString(imagenFile.getBytes());
                usuario.setImagenFile(base64);
                System.out.println(usuario.getImagenFile());
                System.out.println(usuario.getIdUsuario());
                Result Result = usuarioDAOImplementation.UpdateImagen(usuario);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/Usuario/" + usuario.getIdUsuario();
    }

    @PostMapping("Delete/{idUsuario}")
    public String Delete(@PathVariable("idUsuario") int idUsuario, Model model) {
        Result result = usuarioDAOImplementation.Delete(idUsuario);
        return "redirect:/Usuario";

    }

    @GetMapping("Form")
    public String Formulario(Model model) {
        model.addAttribute("Usuario", new Usuario());
        model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
        model.addAttribute("Roles", rolDAOImplementation.GetAll().Objects);

        return "Formulario";
    }

    @PostMapping("Form")
    public String Formulario(@Valid @ModelAttribute("Usuario") Usuario usuario,
            BindingResult bindingresult,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            Model model) {

        if (imagenFile != null && !imagenFile.isEmpty()) {

            String nombreArchivo = imagenFile.getOriginalFilename();
            String[] cadena = nombreArchivo.split("\\.");

            if (cadena.length > 1
                    && (cadena[1].equalsIgnoreCase("jpg") || cadena[1].equalsIgnoreCase("png"))) {

                try {
                    byte[] bytes = imagenFile.getBytes();
                    String base64 = Base64.getEncoder().encodeToString(bytes);

                    usuario.setImagenFile(base64);

                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                }

            } else {
                usuario.setImagenFile("");
            }
        }

        if (bindingresult.hasErrors()) {
            model.addAttribute("Usuario", usuario);
            model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
            model.addAttribute("Roles", rolDAOImplementation.GetAll().Objects);
            return "Formulario";
        } else {
            Result result = usuarioDAOImplementation.Add(usuario);
            return "redirect:/Usuario";
        }
    }

    @PostMapping("/agregarDireccion/{idUsuario}")
    public String EditarUsuario(@PathVariable("idUsuario") int idUsuario, @Valid @ModelAttribute("Usuario") Usuario usuario,
            BindingResult bindingresult,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            Model model) {

        if (bindingresult.hasErrors()) {
            model.addAttribute("Usuario", usuario);
            model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
            model.addAttribute("Roles", rolDAOImplementation.GetAll().Objects);
            return "redirect:/Usuario" + idUsuario;
        } else {
            Result result = usuarioDAOImplementation.Update(usuario);
            return "redirect:/Usuario" + idUsuario;
        }

    }

    @GetMapping("DeleteDireccion/{idDireccion}/{idUsuario}")

    public String DeleteDireccion(@PathVariable("idDireccion") int idDireccion,
            @PathVariable("idUsuario") int idUsuario) {
        Result Result = direccionDAOImplementation.Delete(idDireccion);
        return "redirect:/Usuario/" + idUsuario;
    }

    @PostMapping("/agregarDireccion/{idUsuario}")
    public String agregarDireccion(@PathVariable("idUsuario") int idUsuario,
            @Valid @ModelAttribute("direccion") Direccion direccion,
            BindingResult bindingResult,
            Model model) {

        Result Result = usuarioDAOImplementation.GetById(idUsuario);
        model.addAttribute("usuario", Result.Object);

        if (bindingResult.hasErrors()) {

            model.addAttribute("direccion", direccion);
            model.addAttribute("paises", paisDAOImplementation.GetAll().Objects);

            model.addAttribute("mostrarModalDireccion", true);
            model.addAttribute("modalId", "AddDireccionModal");

            if (direccion.getColonia() != null
                    && direccion.getColonia().getMunicipio() != null
                    && direccion.getColonia().getMunicipio().getEstado() != null
                    && direccion.getColonia().getMunicipio().getEstado().getPais() != null) {

                int paisId = direccion.getColonia().getMunicipio().getEstado().getPais().getIdPais();
                int estadoId = direccion.getColonia().getMunicipio().getEstado().getIdEstado();
                int municipioId = direccion.getColonia().getMunicipio().getIdMunicipio();

                model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
                return "redirect:/Usuario/" + idUsuario;
            }

        }
        Result result = direccionDAOImplementation.Add(direccion, idUsuario);
        return "redirect:/Usuario/" + idUsuario;
    }

    @PostMapping("/agregarDireccion/{idUsuario}")
    public String modificarDireccion(@PathVariable("idUsuario") int idUsuario,
            @Valid @ModelAttribute("direccion") Direccion direccion,
            BindingResult bindingResult,
            Model model) {

        Result Result = usuarioDAOImplementation.GetById(idUsuario);
        model.addAttribute("usuario", Result.Object);

        if (bindingResult.hasErrors()) {

            model.addAttribute("direccion", direccion);
            model.addAttribute("paises", paisDAOImplementation.GetAll().Objects);

            model.addAttribute("mostrarModalDireccion", true);
            model.addAttribute("modalId", "AddDireccionModal");

            if (direccion.getColonia() != null
                    && direccion.getColonia().getMunicipio() != null
                    && direccion.getColonia().getMunicipio().getEstado() != null
                    && direccion.getColonia().getMunicipio().getEstado().getPais() != null) {

                int paisId = direccion.getColonia().getMunicipio().getEstado().getPais().getIdPais();
                int estadoId = direccion.getColonia().getMunicipio().getEstado().getIdEstado();
                int municipioId = direccion.getColonia().getMunicipio().getIdMunicipio();

                model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
                return "redirect:/Usuario/" + idUsuario;
            }

        }
        Result result = direccionDAOImplementation.Update(direccion);
        return "redirect:/Usuario/" + idUsuario;
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

    @GetMapping("getDireccionById/{IdDireccion}")
    @ResponseBody
    public Result getDireccionById(@PathVariable("IdDireccion") int IdDireccion) {
        Result result = direccionDAOImplementation.GetByID(IdDireccion);
        return result;
    }

}
