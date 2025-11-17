/* ---- CÓDIGO DE LÓGICA (Pseudocódigo en Java) ---- */
// (Se asume que existen clases Repositorio para hablar con la BD)

public class ControladorAcceso {

    // (El controlador se conecta a los repositorios de la base de datos)
    // private VehiculoRepository vehiculoRepo;
    // private PagoRepository pagoRepo;
    // private MovimientoRepository movimientoRepo;

    /**
     * Lógica principal para RF-01, RF-02, RF-03, RF-04.
     * Implementa los métodos del Diagrama de Clases.
     */
    public String registrarEntrada(String placa) {
        
        // 1. Buscar vehículo (RF-01)
        // Vehiculo vehiculo = vehiculoRepo.findByPlaca(placa);
        // if (vehiculo == null) {
        //     return "ERROR: Vehículo no registrado.";
        // }

        // 2. Obtener el usuario
        // Usuario usuario = vehiculo.getUsuario();

        // 3. Validar pago (RF-03)
        // Se llama al método del objeto Usuario
        // boolean estaAlDia = usuario.estaAlDia(); // (Este método internamente consultaría los pagos)

        // if (estaAlDia) {
            // 4. Registrar Entrada (RF-02)
            // RegistroMovimiento mov = new RegistroMovimiento(vehiculo, new Date());
            // movimientoRepo.save(mov);
            // return "ACCESO AUTORIZADO";
        // } else {
            // 5. Denegar Entrada (RF-04)
            // return "ACCESO DENEGADO - PAGO VENCIDO";
        // }
        
        // Versión simplificada para el pseudocódigo:
        if (placa.equals("ABC123")) {
             return "ACCESO AUTORIZADO"; // Simula HU-01
        } else {
             return "ACCESO DENEGADO - PAGO VENCIDO"; // Simula HU-02
        }
    }

    /**
     * Lógica para RF-05 y RF-06
     */
    public String registrarSalida(String placa) {
        // 1. Buscar el último movimiento abierto de esa placa
        // 2. Ponerle la fechaHoraSalida actual
        // 3. Verificar si tiene novedades (RF-06)
        // 4. Guardar y retornar
        return "SALIDA REGISTRADA PARA " + placa;
    }
}
