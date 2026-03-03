package com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int IdUsuario;
    @Lob
    @Column(name = "imagen")
    private String ImagenFile;

    @Column(name = "username")
    private String UserName;

    @Column(name = "nombre")
    private String Nombre;

    @Column(name = "apellidopaterno")
    private String ApellidoPaterno;

    @Column(name = "apellidomaterno")
    private String ApellidoMaterno;

    @Column(name = "email")
    private String Email;

    @Column(name = "password")
    private String Password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fechanacimiento")
    private LocalDate FechaNacimiento;

    @Column(name = "sexo")
    private String Sexo;

    @Column(name = "telefono")
    private String Telefono;

    @Column(name = "celular")
    private String Celular;

    @Column(name = "curp")
    private String CURP;

    @Column(name = "activo")
    private int Activo;

    @Column(name = "ultimoacceso")
    private LocalDateTime UltimoAcceso;
    @ManyToOne
    @JoinColumn(name = "idrol")
    public Rol Rol;
    
    @OneToMany(mappedBy = "Usuario")
    public List<Direccion> Direcciones;

    public Usuario() {
        this.Rol = new Rol();
        this.Direcciones = new ArrayList<>();
    }

    public int getActivo() {
        return Activo;
    }

    public void setActivo(int activo) {
        Activo = activo;
    }

    public String getImagenFile() {
        return ImagenFile;
    }

    public void setImagenFile(String ImagenFile) {
        this.ImagenFile = ImagenFile;
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
        Cadena += "\nNombreUsuario = " + UserName;
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
