package modelo.cuentas;

import modelo.abstracto.Cuenta;

// Aplicación de herencia
public class CuentaCorriente extends Cuenta {
    // Atributos
    private double lineaCredito;
    private double deudaLineaCredito;
    
    // Constructor
    public CuentaCorriente(int numeroCuenta, double lineaCredito) {
        super(numeroCuenta);
        this.lineaCredito = lineaCredito;
        this.deudaLineaCredito = 0.0;
    }
    
    // Getters y Setters
    public double getLineaCredito() {
        return lineaCredito;
    }

    public void setLineaCredito(double lineaCredito) {
        this.lineaCredito = lineaCredito;
    }

    public double getDeudaLineaCredito() {
        return deudaLineaCredito;
    }

    public void setDeudaLineaCredito(double deudaLineaCredito) {
        this.deudaLineaCredito = deudaLineaCredito;
    }
    
    
    // Sobreescrituras
    @Override
    public void depositar(double monto) {
        if (monto <= 0) {
            System.out.println("No se permite el ingreso de montos menores o iguales a cero.");
            return;
        }
        double montoRestante = monto;
        if (this.deudaLineaCredito > 0) {
            double pagoDeuda = Math.min(montoRestante, this.deudaLineaCredito);
            this.deudaLineaCredito -= pagoDeuda;
            montoRestante -= pagoDeuda;
            System.out.println("Se aplicaron $" + String.format("%.2f", pagoDeuda) + " CLP al pago de la línea de crédito. Deuda restante: $" + String.format("%.2f", this.deudaLineaCredito) + " CLP.");
            if (this.deudaLineaCredito == 0) {
                System.out.println("¡Deuda de línea de crédito saldada completamente!");
            }
        }
        if (montoRestante > 0) {
            super.depositar(montoRestante);
        } else {
            System.out.println("Depósito procesado. Saldo actual de la cuenta: $" + String.format("%.2f", this.saldo) + " CLP.");
        }
    }
    
    @Override
    public void girar(double monto) {
        if (monto <= 0) {
            System.out.println("Error: No se permite el ingreso de montos menores o iguales a cero.");
            return;
        }
        if (this.deudaLineaCredito > 0) {
            System.out.println("No se puede realizar el giro. Primero debe saldar su deuda de línea de crédito de $" + String.format("%.2f", this.deudaLineaCredito) + " CLP.");
            return;
        }
        double saldoDisponibleParaGiro = this.saldo + (this.saldo * 0.50); // Saldo + 50% del saldo

        if (monto <= this.saldo) {
            super.girar(monto);
        } else if (monto <= saldoDisponibleParaGiro) {
            double montoDeLineaCredito = monto - this.saldo;
            this.saldo = 0;
            this.deudaLineaCredito += montoDeLineaCredito; // Se genera deuda
            System.out.println("Giro realizado parcialmente del saldo y se utilizaron $" + String.format("%.2f", montoDeLineaCredito) + " CLP de la línea de crédito.");
            System.out.println("Saldo actual de la cuenta: $" + String.format("%.2f", this.saldo) + " CLP. Deuda de línea de crédito: $" + String.format("%.2f", this.deudaLineaCredito) + " CLP.");
        } else {
            System.out.println("Fondos insuficientes. No se puede girar $" + String.format("%.2f", monto) + " CLP.");
            System.out.println("Saldo disponible para giro (incluyendo línea de crédito del 50% del saldo): $" + String.format("%.2f", saldoDisponibleParaGiro) + " CLP.");
        }
    }
    
    @Override
    public double calcularInteres() {
        return 0.0;
    }
    
    @Override
    public void visualizarDatosCuenta() {
        System.out.println("--- Datos de la Cuenta Corriente ---");
        System.out.println("Número de Cuenta: " + this.getNumeroCuenta());
        System.out.println("Saldo Actual: $" + String.format("%.2f", this.getSaldo()) + " CLP");
        System.out.println("Línea de Crédito Aprobada: $" + String.format("%.2f", this.lineaCredito) + " CLP");
        System.out.println("Deuda de Línea de Crédito: $" + String.format("%.2f", this.deudaLineaCredito) + " CLP");
        System.out.println("Crédito Disponible de Línea: $" + String.format("%.2f", (this.lineaCredito - this.deudaLineaCredito)) + " CLP");
    }

}