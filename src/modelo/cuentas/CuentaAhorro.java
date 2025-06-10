package modelo.cuentas;

import modelo.abstracto.Cuenta;


// Aplicación de herencia
public class CuentaAhorro extends Cuenta {
    // Atributo
    private double tasaInteres;
    
    
    // Constructor
    public CuentaAhorro(int numeroCuenta, double tasaInteres) {
        super(numeroCuenta);
        this.tasaInteres = tasaInteres;
    }
    
    // Constructor, sobrecarga
    public CuentaAhorro(int numeroCuenta, double saldoInicial, double tasaInteres) {
        super(numeroCuenta, saldoInicial);
        this.tasaInteres = tasaInteres;
    }
    
    // Getters y Setters para la tasa de interés
    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }
    
    // Sobreescritura
    @Override
    public double calcularInteres() {
        return this.saldo * tasaInteres;
    }
    
    // Sobreescritura, Sobrecarga
    // Podemos calcular el saldo tras hacer una proyeccion en años
    public double calcularInteres(int años) {
        double saldoProyectado = this.saldo * Math.pow((1 + tasaInteres), años);
        return saldoProyectado;
    }
    
    // Sobreescritura
    // Resumen de la cuenta, muestra la información
    @Override
    public void visualizarDatosCuenta() {
        System.out.println("--- Datos de la Cuenta de Ahorro ---");
        System.out.println("Número de Cuenta: " + this.getNumeroCuenta());
        System.out.println("Saldo Actual: $" + String.format("%.2f", this.getSaldo()) + " CLP");
        System.out.println("Tasa de Interés Anual: " + String.format("%.2f", (this.tasaInteres * 100)) + "%");
        System.out.println("\n--- Proyección de Saldo (interés compuesto) ---");
        double saldoProyectado3Años = calcularInteres(3);
        System.out.println("Saldo en 3 años: $" + String.format("%.2f", saldoProyectado3Años) + " CLP");
        double saldoProyectado5Años = calcularInteres(5);
        System.out.println("Saldo en 5 años: $" + String.format("%.2f", saldoProyectado5Años) + " CLP");
    }
}