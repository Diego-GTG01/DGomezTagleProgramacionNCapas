package com.risosuit.DGomezTagleProgramacionNCapasMaven.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class Usuario {

    private int IdUsuario;
    @NotEmpty(message = "Este campo no Puede estar vacio")
    @Size(min = 3, max = 50, message = "Debe tener más de 3 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$",message = "Ingrese Solo letras, números y espacios")
    private String UserName;
    @NotEmpty(message = "Este campo no Puede estar vacio")
    @Size(min = 3, max = 50, message = "Debe tener más de 3 caracteres")
    private String Nombre;
    @NotEmpty(message = "Este campo no Puede estar vacio")
    @Size(min = 3, max = 50, message = "Debe tener más de 3 caracteres")
    private String ApellidoPaterno;
    @NotEmpty(message = "Este campo no Puede estar vacio")
    @Size(min = 3, max = 50, message = "Debe tener más de 3 caracteres")
    private String ApellidoMaterno;
    @NotEmpty(message = "Este campo no Puede estar vacio")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Ingrese un correo valido")
    private String Email;
    @NotEmpty(message = "Este campo no Puede estar vacio")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,16}$", message = "Debe agregar una contraseña con 1 mayuscula, 1 minuscula,1 número, 1 caracter especial")
    @Size(min = 8, max = 16, message = "Debe tener entre 8-16 caracteres")
    private String Password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Debes seleccionar una fecha")
    private LocalDate FechaNacimiento;
    @Size(min = 1, max = 2, message = "Debe tener más de 3 caracteres")
    @NotEmpty(message = "Debe Seleccionar una Opción")
    private String Sexo;
    @NotEmpty(message = "Este campo no Puede estar vacio")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Ingrese solo Digitos")
    @Size(min = 10, max = 10, message = "Debe ser maximo de 10 digitos")
    private String Telefono;
    @NotEmpty(message = "Este campo no Puede estar vacio")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Ingrese solo Digitos")
    @Size(min = 10, max = 10, message = "Debe ser maximo de 10 digitos")
    private String Celular;
    @NotEmpty(message = "Este campo no Puede estar vacio")
    @Size(max = 18, message = "Debe tener más de 3 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Ingrese una CURP valida")
    private String CURP;
    private LocalDateTime UltimoAcceso;
    @Valid
    public Rol Rol;
    @Valid
    public List<Direccion> Direcciones;

    public Usuario() {
        this.Rol = new Rol();
    }

    public Rol getRol() {
        return Rol;
    }

    public void setRol(Rol rol) {
        this.Rol = rol;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public LocalDate getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public LocalDateTime getUltimoAcceso() {
        return UltimoAcceso;
    }

    public void setUltimoAcceso(LocalDateTime UltimoAcceso) {
        this.UltimoAcceso = UltimoAcceso;
    }

    @Override
    public String toString() {
        String Cadena = "";
        Cadena += "\n===========================================";
        Cadena += "\nUsuario";
        Cadena += "\nIdUsuario = " + IdUsuario;
        Cadena += "\nNombre = " + Nombre;
        Cadena += "\nApellidoPaterno = " + ApellidoPaterno;
        Cadena += "\nApellidoMaterno = " + ApellidoMaterno;
        Cadena += "\nEmail = " + Email;
        Cadena += "\nFechaNacimiento = " + FechaNacimiento;
        Cadena += "\nSexo = " + Sexo;
        Cadena += "\nTelefono = " + Telefono;
        Cadena += "\nCelular = " + Celular;
        Cadena += "\nUltimoAcceso = " + UltimoAcceso;
        Cadena += "\nrol = " + Rol.getNombre();
        if (Direcciones.size() > 0) {
            int i = 1;
            for (Direccion Direccione : Direcciones) {
                Cadena += "\n*******" + "Direccion: " + i + "*******";
                Cadena += Direccione.toString();
                i++;
            }
            Cadena += "\n****************************";
        }

        Cadena += "\n===========================================";

        return Cadena;
    }

}
