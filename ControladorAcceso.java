// Pseudocódigo en Java para ControladorAcceso.java

// Esto implementa la lógica de HU-01 y HU-02



public class ControladorAcceso {



    // (El controlador se conecta a los repositorios de la base de datos)

    private VehiculoRepository vehiculoRepo;

    private PagoRepository pagoRepo;

    private MovimientoRepository movimientoRepo;



    /**

     * Lógica principal para RF-01, RF-02, RF-03, RF-04

     */

    public String registrarEntrada(String placa) {

        

        // 1. Buscar vehículo (RF-01)

        Vehiculo vehiculo = vehiculoRepo.findByPlaca(placa);

        if (vehiculo == null) {

            return "ERROR: Vehículo no registrado.";

        }



        // 2. Obtener el usuario

        Usuario usuario = vehiculo.getUsuario();



        // 3. Validar pago (RF-03)

        // Se asume que el pagoRepo busca el último pago de ese usuario

        // y comprueba si la fecha actual está en el rango.

        boolean estaAlDia = pagoRepo.usuarioEstaAlDia(usuario.getIdUsuario());



        if (estaAlDia) {

            // 4. Registrar Entrada (RF-02)

            RegistroMovimiento mov = new RegistroMovimiento(vehiculo, new Date());

            movimientoRepo.save(mov);

            return "ACCESO AUTORIZADO";

        } else {

            // 5. Denegar Entrada (RF-04)

            return "ACCESO DENEGADO - PAGO VENCIDO";

        }

    }

}
