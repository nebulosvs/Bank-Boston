package modelo.abstracto;

import interfaces.usuarios.infoCliente;
import modelo.abstracto.Cuenta;
import java.util.ArrayList;
import java.util.List;


// Uso de la interface
public class Cliente implements infoCliente {
	
    private int rut;
    private String dv; // Digito verificador
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String domicilio;
    private String comuna;
    private int telefono;
    private List<Cuenta> cuentas;
    
    public Cliente(int rut, String dv, String nombre, String apellidoPaterno, String apellidoMaterno,
                   String domicilio, String comuna, int telefono) {
        this.rut = rut;
        this.dv = dv;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.domicilio = domicilio;
        this.comuna = comuna;
        this.telefono = telefono;
        this.cuentas = new ArrayList<>();
    }
    // GETTERS Y SETTERS
    
    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }
    
    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }
    
    //MÉTODOS
    @Override
    public void mostrarInformacion() {
        System.out.println("\n--- Datos Personales del Cliente ---");
        System.out.println("Rut: " + this.rut + "-" + this.dv);
        System.out.println("Nombre Completo: " + this.nombre + " " + this.apellidoPaterno + " " + this.apellidoMaterno);
        System.out.println("Domicilio: " + this.domicilio + ", " + this.comuna);
        System.out.println("Teléfono: " + this.telefono);

        System.out.println("\n--- Cuentas del Cliente ---");
        if (cuentas.isEmpty()) {
            System.out.println("El cliente no tiene cuentas registradas.");
        } else {
            for (Cuenta c : cuentas) {
                c.visualizarDatosCuenta();
                System.out.println("--------------------");
            }
        }
    }
    
    public void agregarCuenta(Cuenta nuevaCuenta) {
        this.cuentas.add(nuevaCuenta);
    }
    
}