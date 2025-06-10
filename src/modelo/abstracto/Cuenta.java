package modelo.abstracto;

public abstract class Cuenta {
    
    // Atributos encapsulados
    protected int numeroCuenta;
    protected double saldo;
    
    // Constructor    
    public Cuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0.0;
    }
    
    // Constructor, sobrecarga
    public Cuenta(int numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }

    // Getters y Setters
    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    // Métodos
    public void depositar(double monto) {
        if (monto <= 0) {
            System.out.println("No se permite el ingreso de montos menores o iguales a cero.");
        } else {
            this.saldo += monto;
            System.out.println("¡Depósito realizado de manera exitosa!");
            System.out.println("Usted tiene un saldo actual de $" + this.saldo + " CLP");
        }
    }

    public void girar(double monto) {
        if (this.saldo <= 0) {
            System.out.println("Para realizar un giro, cada cliente debe tener un saldo mayor que cero.");
        } else if (monto <= 0) {
            System.out.println("No se permite el ingreso de montos menores o iguales a cero.");
        } else if (monto > this.saldo) {
            System.out.println("No se permite realizar giros que excedan o superen el saldo de la cuenta.");
        } else {
            this.saldo -= monto;
            System.out.println("Giro realizado con éxito.");
        }
    }

    public void consultarSaldo() {
        System.out.println("Saldo actual: $" + this.saldo + " CLP");
    }

    // Métodos abstractos para que cada subclase lo implemente específicamente
    public abstract void visualizarDatosCuenta();
    
    public abstract double calcularInteres();
    
}