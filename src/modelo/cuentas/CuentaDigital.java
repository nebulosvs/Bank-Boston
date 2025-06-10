package modelo.cuentas;

import modelo.abstracto.Cuenta;
import java.time.LocalDate;

// Aplicación de herencia
public class CuentaDigital extends Cuenta {
    
    // Atributos
    private static final double LIMITE_DIARIO_GIRO = 100000.0;
    private double montoGiradoHoy;
    private LocalDate ultimaFechaGiro;
    
    // Constructor
    public CuentaDigital(int numeroCuenta,double saldo) {
        super(numeroCuenta, saldo);
        this.montoGiradoHoy = 0;
        this.ultimaFechaGiro = LocalDate.now(); // Iniciamos con la fecha actual
    }
    
        // Getters y Setters 
    public double getMontoGiradoHoy() {
        return montoGiradoHoy;
    }

    public void setMontoGiradoHoy(double montoGiradoHoy) {
        this.montoGiradoHoy = montoGiradoHoy;
    }
    
    
    // Sobreescrituras
    @Override
    public void girar(double monto) {
        // Verificación de día
        if (!LocalDate.now().equals(ultimaFechaGiro)) {
            montoGiradoHoy = 0;
            ultimaFechaGiro = LocalDate.now();
        }
        
        
        if (monto <= 0) {
            System.out.println("Favor ingresar un monto válido.");
            return;
        }
        
        if (monto > getSaldo()) {
            System.out.println("Usted tiene fondos insuficientes.");
            return;
        }
        
        if (montoGiradoHoy + monto > LIMITE_DIARIO_GIRO) {
            System.out.println("Usted ha superado su monto límite (" + LIMITE_DIARIO_GIRO + ") para girar el día de hoy.");
            return;
        }
        
        // Autorización del giro
        setSaldo(getSaldo() - monto);
        montoGiradoHoy += monto;
        ultimaFechaGiro = LocalDate.now(); // Actualizar fecha por si es el primer giro del día
        System.out.println("Giro realizado con éxito.");
    }
    
    @Override
    public double calcularInteres() {
        return 0.0;
    }
    
    @Override
    public void visualizarDatosCuenta() {
        System.out.println("--- Datos de la Cuenta Digital ---");
        System.out.println("Número de Cuenta: " + this.getNumeroCuenta());
        System.out.println("Saldo Actual: $" + String.format("%.2f", this.getSaldo()) + " CLP");
        System.out.println("Monto límite diario para giros: $" + LIMITE_DIARIO_GIRO);
        System.out.println("Monto que se ha girado hoy: $" + montoGiradoHoy);
    }
    
    
    
}
