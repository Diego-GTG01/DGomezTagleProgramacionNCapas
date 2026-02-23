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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("Usuario", new Usuario());
        model.addAttribute("Roles", rolDAOImplementation.GetAll().Objects);
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

    @PostMapping("")
    public String Busqueda(@ModelAttribute("Usuario") Usuario usuario, Model model) {

        Result result = usuarioDAOImplementation.Busqueda(usuario);
        model.addAttribute("usuarios", result.Objects);
        model.addAttribute("Usuario", new Usuario());
        model.addAttribute("Roles", rolDAOImplementation.GetAll().Objects);

        return "GetAll";

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
                System.out.println("base64 = " + base64);
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

    @GetMapping("Delete/{idUsuario}")
    public String DeleteUsuario(@PathVariable("idUsuario") int idUsuario, Model model, RedirectAttributes redirectAttributes) {
        Result result = usuarioDAOImplementation.Delete(idUsuario);
        if (result.Correct) {
            redirectAttributes.addFlashAttribute("SuccessDeleteUsuario", "El usuario fue eliminado correctamente");
        } else {
            redirectAttributes.addFlashAttribute("ErrorDeleteUsuario", "El usuario No fue eliminado correctamente");
        }
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
            @RequestParam("imagen") MultipartFile imagenFile,
            Model model, RedirectAttributes redirectAttributes) {

        if (imagenFile != null && !imagenFile.isEmpty()) {

            String nombreArchivo = imagenFile.getOriginalFilename();
            String[] cadena = nombreArchivo.split("\\.");

            if (cadena.length > 1
                    && (cadena[1].equals("jpg") || cadena[1].equals("png"))) {

                try {
                    byte[] bytes = imagenFile.getBytes();
                    String base64 = Base64.getEncoder().encodeToString(bytes);

                    usuario.setImagenFile(base64);
                    System.out.println(usuario.getImagenFile());
                    System.out.println(usuario.getIdUsuario());

                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                }

            }
        }

        if (bindingresult.hasErrors()) {
            for (FieldError error : bindingresult.getFieldErrors()) {
                System.out.println("Campo: " + error.getField());
                System.out.println("Error: " + error.getDefaultMessage());
                System.out.println("Valor rechazado: " + error.getRejectedValue());
            }

            if (usuario.Direcciones.get(0).getColonia() != null
                    && usuario.Direcciones.get(0).Colonia.Municipio != null
                    && usuario.Direcciones.get(0).Colonia.Municipio.Estado != null
                    && usuario.Direcciones.get(0).Colonia.Municipio.Estado.Pais != null) {

            }
            model.addAttribute("Usuario", usuario);
            model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
            model.addAttribute("Roles", rolDAOImplementation.GetAll().Objects);
            model.addAttribute("Failed", "Usuario No Fue Agregado correctamente, Verifique los datos");
            return "Formulario";
        } else {
            Result result = usuarioDAOImplementation.Add(usuario);
            if (result.Correct) {
                redirectAttributes.addFlashAttribute("Success", "Usuario No Fue Editado correctamente");
            } else {
                redirectAttributes.addFlashAttribute("Failed", "Algo salió Mal :(");
            }
            return "redirect:/Usuario";
        }
    }

    @PostMapping("/UpdateUser/{idUsuario}")
    public String UpdateUsuario(@PathVariable("idUsuario") int idUsuario, @Valid @ModelAttribute("Usuario") Usuario usuario,
            BindingResult bindingresult,
            Model model, RedirectAttributes redirectAttributes) {

        if (bindingresult.hasErrors()) {
            for (FieldError error : bindingresult.getFieldErrors()) {
                System.out.println("Campo: " + error.getField());
                System.out.println("Error: " + error.getDefaultMessage());
                System.out.println("Valor rechazado: " + error.getRejectedValue());
            }
            model.addAttribute("Usuario", usuario);
            model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
            model.addAttribute("Roles", rolDAOImplementation.GetAll().Objects);
            redirectAttributes.addFlashAttribute("FailedEdicion", "Usuario No Fue Editado correctamente");

            return "redirect:/Usuario/" + idUsuario;
        } else {
            Result result = usuarioDAOImplementation.Update(usuario);
            redirectAttributes.addFlashAttribute("SuccessEdicion", "Usuario Editado correctamente");
            return "redirect:/Usuario/" + idUsuario;
        }

    }

    @GetMapping("DeleteDireccion/{idDireccion}/{idUsuario}")

    public String DeleteDireccion(@PathVariable("idDireccion") int idDireccion,
            @PathVariable("idUsuario") int idUsuario, RedirectAttributes redirectAttributes) {
        Result Result = direccionDAOImplementation.Delete(idDireccion);
        if (Result.Correct) {
            redirectAttributes.addFlashAttribute("SuccessDeleteDireccion", "La Direccion fue eliminada correctamente");
        } else {
            redirectAttributes.addFlashAttribute("ErrorDeleteDireccion", "La Direccion No fue eliminada correctamente");
        }
        return "redirect:/Usuario/" + idUsuario;
    }

    @PostMapping("/agregarDireccion/{idUsuario}")
    public String AddDireccion(@PathVariable("idUsuario") int idUsuario,
            @Valid @ModelAttribute("direccion") Direccion direccion,
            BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes) {

        Result Result = usuarioDAOImplementation.GetById(idUsuario);
        model.addAttribute("usuario", Result.Object);

        if (bindingResult.hasErrors()) {

            model.addAttribute("paises", paisDAOImplementation.GetAll().Objects);
            redirectAttributes.addFlashAttribute("direccion", direccion);
            redirectAttributes.addFlashAttribute("ErrorAddDireccion", "Direccion No Fue Agregado correctamente, Verifique los datos");
            return "redirect:/Usuario/" + idUsuario;

        }

        Result result = direccionDAOImplementation.Add(direccion, idUsuario);
        if (result.Correct) {
            redirectAttributes.addFlashAttribute("SuccessAddDireccion", "Direccion Agregada correctamente");
        } else {
            redirectAttributes.addFlashAttribute("ErrorAddDireccion", "Algo Salió Mal");
        }
        return "redirect:/Usuario/" + idUsuario;
    }

    @PostMapping("/UpdateDireccion/{idUsuario}")
    public String modificarDireccion(@PathVariable("idUsuario") int idUsuario,
            @Valid @ModelAttribute("direccion") Direccion direccion,
            BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes) {

        Result Result = usuarioDAOImplementation.GetById(idUsuario);
        model.addAttribute("usuario", Result.Object);

        if (bindingResult.hasErrors()) {

            model.addAttribute("direccion", direccion);
            model.addAttribute("paises", paisDAOImplementation.GetAll().Objects);

            if (direccion.getColonia() != null
                    && direccion.getColonia().getMunicipio() != null
                    && direccion.getColonia().getMunicipio().getEstado() != null
                    && direccion.getColonia().getMunicipio().getEstado().getPais() != null) {

                int paisId = direccion.getColonia().getMunicipio().getEstado().getPais().getIdPais();
                int estadoId = direccion.getColonia().getMunicipio().getEstado().getIdEstado();
                int municipioId = direccion.getColonia().getMunicipio().getIdMunicipio();

                model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
                redirectAttributes.addFlashAttribute("ErrorEdicionDireccion", "La dirección no pudo ser editada");
                redirectAttributes.addFlashAttribute("IdDireccion", direccion.getIdDireccion());

                return "redirect:/Usuario/" + idUsuario;
            }

        }
        Result result = direccionDAOImplementation.Update(direccion);
        redirectAttributes.addFlashAttribute("SuccessEdicionDireccion", "Direccion Editado correctamente");
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

    @GetMapping("getColoniaByCodigoPostal/{IdDireccion}")
    @ResponseBody
    public Result getDirecionByCodigoPostal(@PathVariable("IdDireccion") String IdDireccion) {
        Result result = coloniaDAOImplementation.getColoniaByCodigoPostal(IdDireccion);
        return result;
    }

}
