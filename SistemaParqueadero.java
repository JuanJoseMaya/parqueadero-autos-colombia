import java.util.Date;

/* ========================================================= */
/* CONTROLADOR PRINCIPAL DEL SISTEMA                       */
/* Integra: Módulo de Acceso y Módulo de Administración    */
/* ========================================================= */

public class SistemaParqueadero {

    // ---------------------------------------------------------
    // MÓDULO 1: GESTIÓN DE ACCESO (Rol: VIGILANTE)
    // ---------------------------------------------------------

    /**
     * RF-02 y RF-03: Registra la entrada validando el pago.
     */
    public String registrarEntrada(String placa) {
        // 1. Buscar vehículo en la BD
        Vehiculo vehiculo = repositorioVehiculos.buscarPorPlaca(placa);
        
        if (vehiculo == null) {
            return "ERROR: Vehículo no registrado en el sistema.";
        }

        // 2. Validar si el usuario tiene mensualidad activa (RF-03)
        Usuario usuario = vehiculo.getUsuario();
        boolean estaAlDia = usuario.verificarPagoVigente(new Date());

        if (estaAlDia) {
            // 3. Crear registro de entrada (RF-02)
            RegistroMovimiento entrada = new RegistroMovimiento(vehiculo, new Date());
            repositorioMovimientos.guardar(entrada);
            return "ACCESO AUTORIZADO para: " + usuario.getNombre();
        } else {
            // 4. Denegar acceso (RF-04)
            return "ACCESO DENEGADO: El usuario presenta mora en su pago.";
        }
    }

    /**
     * RF-05 y RF-06: Registra la salida y verifica novedades.
     */
    public String registrarSalida(String placa) {
        Vehiculo vehiculo = repositorioVehiculos.buscarPorPlaca(placa);
        
        // 1. Verificar Novedades (RF-06)
        if (repositorioNovedades.tieneNovedades(vehiculo)) {
            System.out.println("ALERTA: El vehículo tiene novedades reportadas.");
        }

        // 2. Cerrar el movimiento (poner hora de salida)
        repositorioMovimientos.marcarSalida(vehiculo, new Date());
        return "SALIDA EXITOSA.";
    }


    // ---------------------------------------------------------
    // MÓDULO 2: ADMINISTRACIÓN (Rol: ADMINISTRADOR)
    // ---------------------------------------------------------

    /**
     * RF-07: Crear un nuevo usuario en el sistema.
     */
    public void crearUsuario(String cedula, String nombre, String telefono, String correo) {
        // 1. Validar duplicados
        if (repositorioUsuarios.existeCedula(cedula)) {
            throw new Exception("Error: El usuario ya existe.");
        }

        // 2. Crear y guardar
        Usuario nuevoUsuario = new Usuario(cedula, nombre, telefono, correo);
        repositorioUsuarios.guardar(nuevoUsuario);
        System.out.println("Usuario creado exitosamente: " + nombre);
    }

    /**
     * RF-10: Configurar nuevas celdas físicas.
     */
    public void crearCelda(String codigo, String tipo) {
        Celda nuevaCelda = new Celda(codigo, tipo, "Disponible");
        repositorioCeldas.guardar(nuevaCelda);
    }

    /**
     * RF-11: Asignar una celda a un vehículo (Gestión de Cupos).
     */
    public void asignarCelda(String placa, String codigoCelda) {
        // 1. Buscar entidades
        Vehiculo vehiculo = repositorioVehiculos.buscarPorPlaca(placa);
        Celda celda = repositorioCeldas.buscarPorCodigo(codigoCelda);

        // 2. Validar disponibilidad (RNF-05)
        if (celda.getEstado().equals("Ocupada")) {
            throw new Exception("Error: La celda seleccionada ya está ocupada.");
        }

        // 3. Realizar asignación
        vehiculo.setCelda(celda);
        celda.setEstado("Ocupada"); // Actualizar estado visual

        // 4. Guardar cambios
        repositorioVehiculos.actualizar(vehiculo);
        repositorioCeldas.actualizar(celda);
        
        System.out.println("Celda " + codigoCelda + " asignada al vehículo " + placa);
    }
}
