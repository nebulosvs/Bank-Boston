// Paquete principal donde va el main
package bank.main;

// Importar paquetes
import modelo.abstracto.Cliente;
import modelo.cuentas.CuentaCorriente;
import modelo.cuentas.CuentaAhorro;
import modelo.cuentas.CuentaDigital;
import modelo.abstracto.Cuenta;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BankBoston {

    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Cliente> clientes = new ArrayList<>(); 

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenuPrincipal();
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número para la opción.");
                scanner.nextLine();
                opcion = 0; 
                continue;
            }
            switch (opcion) {
                case 1:
                    registrarOAnadirCuentaACliente();
                    break;
                case 2:
                    verDatosCliente();
                    break;
                case 3:
                    realizarOperacionEnCuenta("Depositar");
                    break;
                case 4:
                    realizarOperacionEnCuenta("Girar");
                    break;
                case 5:
                    realizarOperacionEnCuenta("Consultar Saldo");
                    break;
                case 6:
                    System.out.println("Saliendo de la aplicación. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        } while (opcion != 6);
        scanner.close();
    }

    // Menú Principal
    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- Menú Principal Bank Boston ---");
        System.out.println("1. Registrar cliente o Abrir nueva cuenta");
        System.out.println("2. Ver datos de cliente");
        System.out.println("3. Depositar dinero");
        System.out.println("4. Girar dinero");
        System.out.println("5. Consultar saldo de cuenta");
        System.out.println("6. Salir");
        System.out.print("Ingrese una opción: ");
    }
    
    // Busqueda por rut, permite continuar con el registro de nuevo cliente.
    // Si el cliente ya tiene una cuenta, puede registrar otro tipo de cuenta y quedarán ambas asociadas al rut
    private static void registrarOAnadirCuentaACliente() {
        System.out.println("\n--- Registrar Cliente o Abrir Nueva Cuenta ---");
        int rutBuscar = obtenerEnteroValido("Ingrese el RUT del cliente (solo números, sin puntos ni guion): ");
        Cliente clienteEncontrado = buscarClientePorRut(rutBuscar);
        if (clienteEncontrado == null) {
            System.out.println("Cliente no encontrado. Procediendo a registrar un nuevo cliente.");
            registrarNuevoCliente(rutBuscar);
        } else {
            System.out.println("Cliente '" + clienteEncontrado.getNombre() + " " + clienteEncontrado.getApellidoPaterno() + "' encontrado.");
            abrirNuevaCuenta(clienteEncontrado);
        }
    }
    
    // Formulario para crear el cliente en el sistema, con sus respectivas validaciones
    private static void registrarNuevoCliente(int rutExistente) {
        System.out.println("\n--- Formulario de Registro de Nuevo Cliente ---");
        String nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, dvCaracter;
        int telefonoNumerico;
        nombre = obtenerStringNoVacio("Ingrese nombre: ");
        apellidoPaterno = obtenerStringNoVacio("Ingrese apellido paterno: ");
        apellidoMaterno = obtenerStringNoVacio("Ingrese apellido materno: ");
        domicilio = obtenerStringNoVacio("Ingrese domicilio: ");
        comuna = obtenerStringNoVacio("Ingrese comuna: ");
        boolean telefonoValido = false;
        do {
            telefonoNumerico = obtenerEnteroValido("Ingrese teléfono (9 dígitos, ej: 912345678): ");
            if (String.valueOf(telefonoNumerico).length() != 9 || !String.valueOf(telefonoNumerico).startsWith("9")) {
                System.out.println("El teléfono debe tener exactamente 9 dígitos y comenzar con 9.");
            } else if (telefonoNumerico <= 0) {
                System.out.println("El teléfono debe ser un número positivo.");
            } else {
                telefonoValido = true;
            }
        } while (!telefonoValido);
        boolean dvValido = false;
        do {
            System.out.print("Ingrese Dígito Verificador del RUT (número 0-9 o 'K'): ");
            dvCaracter = scanner.nextLine().trim().toUpperCase();
            if (dvCaracter.isEmpty()) {
                System.out.println("El Dígito Verificador no puede estar vacío.");
            } else if (!dvCaracter.matches("[0-9K]")) {
                System.out.println("Dígito Verificador inválido. Debe ser un número (0-9) o la letra 'K'.");
            } else {
                dvValido = true;
            }
        } while (!dvValido);
        Cliente cliente = new Cliente(rutExistente, dvCaracter, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefonoNumerico);
        clientes.add(cliente);
        System.out.println("Cliente '" + cliente.getNombre() + " " + cliente.getApellidoPaterno() + "' registrado exitosamente.");
        abrirNuevaCuenta(cliente);
    }
    
    // Formulario para seleccionar que tipo de cuenta se quiere crear
    private static void abrirNuevaCuenta(Cliente cliente) {
        System.out.println("\n--- Apertura de Nueva Cuenta para " + cliente.getNombre() + " ---");
        Cuenta nuevaCuenta = null;
        int tipoCuenta;
        boolean tipoCuentaValido = false;
        do {
            System.out.println("\nSeleccione el tipo de cuenta a abrir:");
            System.out.println("1. Cuenta Corriente");
            System.out.println("2. Cuenta de Ahorro");
            System.out.println("3. Cuenta Digital");
            System.out.print("Ingrese una opción: ");
            tipoCuenta = obtenerEnteroValido(""); 
            boolean yaTieneCuentaDeTipo = false;
            
            // Validacion de que se cree una cuenta de un tipo de cuenta que no tenga
            for (Cuenta c : cliente.getCuentas()) {
                if (tipoCuenta == 1 && c instanceof CuentaCorriente) {
                    yaTieneCuentaDeTipo = true;
                    System.out.println("El cliente ya tiene una Cuenta Corriente.");
                    break;
                } else if (tipoCuenta == 2 && c instanceof CuentaAhorro) {
                    yaTieneCuentaDeTipo = true;
                    System.out.println("El cliente ya tiene una Cuenta de Ahorro.");
                    break;
                } else if (tipoCuenta == 3 && c instanceof CuentaDigital) {
                    yaTieneCuentaDeTipo = true;
                    System.out.println("El cliente ya tiene una Cuenta Digital.");
                    break;
                }
            }
            if (yaTieneCuentaDeTipo) {
                continue; 
            }
            if (tipoCuenta == 3) {
                boolean tieneCuentaBase = false;
                for (Cuenta c : cliente.getCuentas()) {
                    if (c instanceof CuentaCorriente || c instanceof CuentaAhorro) {
                        tieneCuentaBase = true;
                        break;
                    }
                }
                if (!tieneCuentaBase) {
                    System.out.println("Para abrir una Cuenta Digital, el cliente debe tener previamente una Cuenta Corriente o de Ahorro.");
                    continue; 
                }
            }
            int numeroCuenta = generarNumeroCuentaUnico(); 
            switch (tipoCuenta) {
                case 1: 
                    System.out.print("Ingrese la línea de crédito aprobada para la Cuenta Corriente: ");
                    double lineaCreditoCC = obtenerDoubleValido("");
                    nuevaCuenta = new CuentaCorriente(numeroCuenta, lineaCreditoCC);
                    tipoCuentaValido = true;
                    break;
                case 2:
                    double tasaInteresCA = 0.0020;
                    nuevaCuenta = new CuentaAhorro(numeroCuenta, tasaInteresCA);
                    tipoCuentaValido = true;
                    break;
                case 3: 
                    double saldoInicial = 0;
                    nuevaCuenta = new CuentaDigital(numeroCuenta, saldoInicial);
                    tipoCuentaValido = true;
                    break;
                default:
                    System.out.println("Opción de tipo de cuenta no válida. Por favor, intente de nuevo.");
                    break;
            }
        } while (!tipoCuentaValido);

        if (nuevaCuenta != null) {
            cliente.agregarCuenta(nuevaCuenta);
            System.out.println("Cuenta " + nuevaCuenta.getClass().getSimpleName() + " (Número: " + nuevaCuenta.getNumeroCuenta() + ") abierta exitosamente para " + cliente.getNombre() + ".");
        }
    }
    
    // Muestra la informacion actual de las cuentas del cliente, luego de validarlo
    private static void verDatosCliente() {
        System.out.println("\n--- Ver Datos de Cliente ---");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados aún. Por favor, registre un cliente primero.");
            return;
        }
        int rutBuscar = obtenerEnteroValido("Ingrese el RUT del cliente (solo números, sin puntos ni guion): ");

        Cliente clienteEncontrado = buscarClientePorRut(rutBuscar);
        if (clienteEncontrado != null) {
            clienteEncontrado.mostrarInformacion();
        } else {
            System.out.println("Cliente con RUT " + rutBuscar + " no encontrado.");
        }
    }
    
    // Operaciones bancarias disponibles luego de validar
    private static void realizarOperacionEnCuenta(String tipoOperacion) {
        System.out.println("\n--- " + tipoOperacion + " en Cuenta ---");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados aún. Por favor, registre un cliente primero.");
            return;
        }
        int rutBuscar = obtenerEnteroValido("Ingrese el RUT del cliente: ");
        Cliente clienteEncontrado = buscarClientePorRut(rutBuscar);
        if (clienteEncontrado == null) {
            System.out.println("Cliente con RUT " + rutBuscar + " no encontrado.");
            return;
        }
        if (clienteEncontrado.getCuentas().isEmpty()) {
            System.out.println("El cliente '" + clienteEncontrado.getNombre() + "' no tiene cuentas registradas para realizar esta operación.");
            return;
        }
        Cuenta cuentaSeleccionada = seleccionarCuenta(clienteEncontrado, tipoOperacion.toLowerCase());
        if (cuentaSeleccionada == null) {
            return;
        }
        if (tipoOperacion.equals("Consultar Saldo")) {
            cuentaSeleccionada.consultarSaldo();
        } else {
            double monto = obtenerDoubleValido("Ingrese el monto a " + tipoOperacion.toLowerCase() + ": ");
            if (tipoOperacion.equals("Depositar")) {
                cuentaSeleccionada.depositar(monto);
            } else if (tipoOperacion.equals("Girar")) {
                cuentaSeleccionada.girar(monto);
            }
        }
    }
    
    // Buscador de clientes por Rut
    private static Cliente buscarClientePorRut(int rut) {
        for (Cliente c : clientes) {
            if (c.getRut() == rut) {
                return c;
            }
        }
        return null;
    }
    
    // Validacion de que exista la cuenta con la que se quier trabajar 
    private static Cuenta seleccionarCuenta(Cliente cliente, String proposito) {
        List<Cuenta> cuentasDelCliente = cliente.getCuentas();
        if (cuentasDelCliente.isEmpty()) {
            System.out.println("El cliente no tiene cuentas para " + proposito + ".");
            return null;
        }
        System.out.println("\n--- Cuentas disponibles para " + proposito + " de " + cliente.getNombre() + " ---");
        for (int i = 0; i < cuentasDelCliente.size(); i++) {
            Cuenta c = cuentasDelCliente.get(i);
            String tipo = c.getClass().getSimpleName();
            System.out.println((i + 1) + ". " + tipo + " (Número: " + c.getNumeroCuenta() + ")");
        }
        int seleccion = obtenerEnteroValido("Seleccione el número de la cuenta para " + proposito + ": ");
        if (seleccion > 0 && seleccion <= cuentasDelCliente.size()) {
            return cuentasDelCliente.get(seleccion - 1);
        } else {
            System.out.println("Selección de cuenta inválida. Operación cancelada.");
            return null;
        }
    }
    
    // Generador de numeros de cuenta 
    private static int generarNumeroCuentaUnico() {
        int nuevoNumero;
        boolean esUnico;
        do {
            nuevoNumero = (int) (100000000 + Math.random() * 900000000);
            esUnico = true;
            for (Cliente cliente : clientes) {
                for (Cuenta cuenta : cliente.getCuentas()) {
                    if (cuenta.getNumeroCuenta() == nuevoNumero) {
                        esUnico = false;
                        break;
                    }
                }
                if (!esUnico) break;
            }
        } while (!esUnico);
        return nuevoNumero;
    }
    
    
    // VALIDACIONES
    
    // Validación de texto no vacio
    private static String obtenerStringNoVacio(String mensaje) {
        String input;
        do {
            System.out.print(mensaje);
            input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("El campo no puede estar vacío. Por favor, intente de nuevo.");
            }
        } while (input.trim().isEmpty());
        return input;
    }
    
    // Validación de enteros
    private static int obtenerEnteroValido(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese solo números enteros.");
                scanner.nextLine();
            }
        }
    }
    
    // Validación de doubles
    private static double obtenerDoubleValido(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                double valor = scanner.nextDouble();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número decimal o entero válido.");
                scanner.nextLine();
            }
        }
    }
}