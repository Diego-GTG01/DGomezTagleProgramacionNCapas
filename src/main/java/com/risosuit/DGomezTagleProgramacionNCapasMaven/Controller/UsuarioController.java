package com.risosuit.DGomezTagleProgramacionNCapasMaven.Controller;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.ColoniaDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.DireccionDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.EstadoDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.MunicipioDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.PaisDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.RolDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.UsuarioDAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Direccion;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.ErroresArchivo;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.Service.ValidationService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
    private ValidationService validationservice;

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

    // @GetMapping("{idUsuario}")
    // public String GetById(@PathVariable("idUsuario") int idUsuario, Model model)
    // {
    // Result result = usuarioDAOImplementation.GetById(idUsuario);
    // model.addAttribute("usuarios", result.Object);
    // return "GetAll";
    //
    // }
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
        model.addAttribute("Usuario", usuario);
        model.addAttribute("Roles", rolDAOImplementation.GetAll().Objects);

        return "GetAll";

    }

    @GetMapping("cargamasiva")
    public String CargaMasiva() {
        
        return "CargaMasiva";
    }

    @PostMapping("cargamasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile archivo,
            RedirectAttributes redirectAttributes, HttpSession session) {
        
        try {
            if (archivo != null) {
                String rutaBase = System.getProperty("user.dir");
                String rutaCarpeta = "src/main/resources/archivosCM";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
                String NombreArchivo = fecha + archivo.getOriginalFilename();
                String rutaArchivo = rutaBase + "/" + rutaCarpeta + "/" + NombreArchivo;
                String extension = archivo.getOriginalFilename().split("\\.")[1];
                List<Usuario> Usuarios = null;
                if (extension.contains("txt")) {
                    archivo.transferTo(new File(rutaArchivo));
                    Usuarios = LecturaArchivoTxt(new File(rutaArchivo));

                } else if (extension.contains("xlsx")) {
                    archivo.transferTo(new File(rutaArchivo));
                    Usuarios = LecturaArchivoXLSX(new File(rutaArchivo));

                } else {

                    System.out.println("Extensión Erronea");
                }

                List<ErroresArchivo> errores = ValidarDatos(Usuarios);
                if (errores.isEmpty()) {
                    System.out.println("Sin errores ");
                    redirectAttributes.addFlashAttribute("Success", "El archivo fue leido Correctamente");
                    String idArchivo = fecha;
                    session.setAttribute("idArchivoActual", idArchivo);
                    session.setAttribute("ruta_" + idArchivo, rutaArchivo);
                    return "redirect:cargamasiva";
                } else {
                    System.out.println(errores);
                    List<String> listaStrings = new ArrayList<>();
                    for (ErroresArchivo error : errores) {
                        listaStrings.add(error.toString());
                    }
                    for (Usuario usuario : Usuarios) {
                        System.out.println(usuario.toString());

                    }

                    redirectAttributes.addFlashAttribute("errores", listaStrings);
                    return "redirect:cargamasiva";
                }

            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return "CargaMasiva";
    }

    public List<Usuario> LecturaArchivoTxt(File archivo) {
        List<Usuario> Usuarios = new ArrayList<>();
        try (BufferedReader bufferedreader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int NumeroLinea = 0;

            while ((linea = bufferedreader.readLine()) != null) {
                NumeroLinea++;
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] Datos = linea.split("\\|");
                if (Datos.length < 16) {
                    System.out.println("La Linea " + NumeroLinea + " NO TIENE EL FORMATO CORRECTO");
                    System.out.println("Campos encontrados: " + Datos.length);
                    continue;
                }
                try {
                    Usuario Usuario = new Usuario();
                    Usuario.setUserName(Datos[0]);
                    Usuario.setNombre(Datos[1]);
                    Usuario.setApellidoPaterno(Datos[2]);
                    Usuario.setApellidoMaterno(Datos[3]);
                    Usuario.setEmail(Datos[4]);
                    Usuario.setPassword(Datos[5]);
                    if (Datos.length > 6 && !Datos[6].trim().isEmpty()) {
                        Usuario.setFechaNacimiento(LocalDate.parse(Datos[6].trim()));
                    }
                    Usuario.setSexo(limpiarCampo(Datos[7]));
                    Usuario.setTelefono(limpiarCampo(Datos[8]));
                    Usuario.setCelular(limpiarCampo(Datos[9]));
                    Usuario.setCURP(limpiarCampo(Datos[10]));
                    Usuario.Rol.setIdRol(Integer.parseInt(Datos[11].trim()));

                    Direccion direccion = new Direccion();
                    direccion.setCalle(limpiarCampo(Datos[12]));
                    direccion.setNumeroInterior(limpiarCampo(Datos[13]));
                    direccion.setNumeroExterior(limpiarCampo(Datos[14]));

                    if (Datos.length > 15 && !Datos[15].trim().isEmpty()) {
                        direccion.Colonia.setIdColonia(Integer.parseInt(Datos[15].trim()));
                        direccion.Colonia.Municipio.setIdMunicipio(1);
                        direccion.Colonia.Municipio.Estado.setIdEstado(1);
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(1);
                    }

                    Usuario.getDirecciones().add(direccion);
                    Usuarios.add(Usuario);

                } catch (Exception e) {

                    System.out.println("Error procesando línea " + NumeroLinea + ": " + e.getMessage());
                    e.printStackTrace();
                }

            }
            System.out.println("Total de usuarios leídos: " + Usuarios.size());

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return Usuarios;
    }

    public List<Usuario> LecturaArchivoXLSX(File archivo) {
        List<Usuario> Usuarios = null;
        try (InputStream inputstream = new FileInputStream(archivo);
                XSSFWorkbook workbook = new XSSFWorkbook(inputstream)) {

            Usuarios = new ArrayList<>();
            XSSFSheet sheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();
            for (Row row : sheet) {
                Usuario Usuario = new Usuario();
                Usuario.setUserName(row.getCell(0).toString());
                Usuario.setNombre(row.getCell(1).toString());
                Usuario.setApellidoPaterno(row.getCell(2).toString());
                Usuario.setApellidoMaterno(row.getCell(3).toString());
                Usuario.setEmail(row.getCell(4).toString());
                Usuario.setPassword(row.getCell(5).toString());

                if (row.getCell(6) != null && row.getCell(6).getCellType() != CellType.BLANK) {

                    Usuario.setFechaNacimiento(LocalDate.parse(row.getCell(6).getLocalDateTimeCellValue()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                }
                Usuario.setSexo(limpiarCampo(row.getCell(7).toString()));
                Usuario.setTelefono(limpiarCampo(fmt.formatCellValue(row.getCell(8))));
                Usuario.setCelular(limpiarCampo(fmt.formatCellValue(row.getCell(9))));
                Usuario.setCURP(limpiarCampo(row.getCell(10).toString()));
                Usuario.Rol.setIdRol((int) row.getCell(11).getNumericCellValue());

                Direccion direccion = new Direccion();
                direccion.setCalle(limpiarCampo(row.getCell(12).toString()));

                direccion.setNumeroInterior(fmt.formatCellValue(row.getCell(13)));
                direccion.setNumeroExterior(fmt.formatCellValue(row.getCell(14)));

                direccion.Colonia.setIdColonia((int) (row.getCell(15).getNumericCellValue()));
                direccion.Colonia.Municipio.setIdMunicipio(1);
                direccion.Colonia.Municipio.Estado.setIdEstado(1);
                direccion.Colonia.Municipio.Estado.Pais.setIdPais(1);

                Usuarios.add(Usuario);

            }

        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());

        }

        return Usuarios;
    }

    public List<ErroresArchivo> ValidarDatos(List<Usuario> Usuarios) {
        List<ErroresArchivo> errores = new ArrayList<>();
        int fila = 0;

        for (Usuario usuario : Usuarios) {
            fila++;

            BindingResult bindingResult = validationservice.ValidateObject(usuario);

            if (bindingResult.hasErrors()) {
                ErroresArchivo errorArchivo = new ErroresArchivo();
                errorArchivo.fila = fila;
                errorArchivo.dato = "";
                errorArchivo.descripcion = "";

                for (ObjectError objectError : bindingResult.getAllErrors()) {
                    if (objectError instanceof FieldError) {
                        FieldError fieldError = (FieldError) objectError;
                        errorArchivo.dato += fieldError.getField() + " ";
                        errorArchivo.descripcion += fieldError.getDefaultMessage() + " ";
                    }
                }

                errores.add(errorArchivo);
            }
        }

        return errores;
    }

    private String limpiarCampo(String campo) {
        if (campo == null || campo.trim().isEmpty() || campo.trim().equals("null")) {
            return "";
        }
        return campo.trim();
    }

    @GetMapping("CargaMasivaProcesar")
    public String procesarArchivo(HttpSession session, RedirectAttributes redirectAttributes) {
        String archivoActual = (String) session.getAttribute("idArchivoActual");
        String rutaReal = (String) session.getAttribute("ruta_" + archivoActual);

        if (rutaReal == null) {
            redirectAttributes.addFlashAttribute("Error", "No hay ningún archivo pendiente de procesar.");

            session.removeAttribute("ruta_" + archivoActual);
            return "redirect:/Usuario";
        }

        System.out.println("Procesando archivo desde sesión: " + rutaReal);

        redirectAttributes.addFlashAttribute("Success", "Archivo Procesado correctamente");
        return "redirect:/Usuario";
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
                if (Result.Correct) {

                } else {

                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        return "redirect:/Usuario/" + usuario.getIdUsuario();
    }

    @GetMapping("Delete/{idUsuario}")
    public String DeleteUsuario(@PathVariable("idUsuario") int idUsuario, Model model,
            RedirectAttributes redirectAttributes) {
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
    public String UpdateUsuario(@PathVariable("idUsuario") int idUsuario,
            @Valid @ModelAttribute("Usuario") Usuario usuario,
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
            redirectAttributes.addFlashAttribute("ErrorAddDireccion",
                    "Direccion No Fue Agregado correctamente, Verifique los datos");
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
