/* ========================================================= */
/* SISTEMA DE INFORMACIÓN - PARQUEADERO "AUTOS COLOMBIA"   */
/* Script de Base de Datos Completo (Iteración 1 + 2)      */
/* ========================================================= */

/* --- 1. TABLA DE USUARIOS (CLIENTES) --- */
/* Permite registrar la información personal y de contacto */
CREATE TABLE Usuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nombreCompleto VARCHAR(150) NOT NULL,
    cedula VARCHAR(20) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(100),
    estado VARCHAR(20) DEFAULT 'Activo' /* Opciones: 'Activo', 'Inactivo' */
);

/* --- 2. TABLA DE CELDAS (ESPACIOS FÍSICOS) --- */
/* Define el inventario del parqueadero. Ej: A-01, B-05 */
CREATE TABLE Celda (
    idCelda INT AUTO_INCREMENT PRIMARY KEY,
    numeroCelda VARCHAR(10) NOT NULL UNIQUE,
    tipo VARCHAR(20) NOT NULL, /* Opciones: 'Carro', 'Moto' */
    estado VARCHAR(20) DEFAULT 'Disponible' /* Opciones: 'Disponible', 'Ocupada' */
);

/* --- 3. TABLA DE VEHÍCULOS --- */
/* Relaciona el vehículo con un usuario y con una celda asignada */
CREATE TABLE Vehiculo (
    idVehiculo INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(7) NOT NULL UNIQUE, /* Ej: 'ABC-123' */
    marca VARCHAR(50),
    color VARCHAR(30),
    idUsuario_FK INT NOT NULL,
    idCelda_FK INT NULL, /* Puede ser NULL si aún no tiene celda asignada */
    
    /* Relaciones (Llaves Foráneas) */
    FOREIGN KEY (idUsuario_FK) REFERENCES Usuario(idUsuario),
    FOREIGN KEY (idCelda_FK) REFERENCES Celda(idCelda)
);

/* --- 4. TABLA DE PAGOS MENSUALES --- */
/* Controla la vigencia del servicio para permitir el acceso */
CREATE TABLE PagoMensual (
    idPago INT AUTO_INCREMENT PRIMARY KEY,
    fechaInicio DATE NOT NULL,
    fechaFin DATE NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    idUsuario_FK INT NOT NULL,
    FOREIGN KEY (idUsuario_FK) REFERENCES Usuario(idUsuario)
);

/* --- 5. TABLA DE REGISTRO DE MOVIMIENTOS --- */
/* Bitácora de entradas y salidas operada por el Vigilante */
CREATE TABLE RegistroMovimiento (
    idMovimiento INT AUTO_INCREMENT PRIMARY KEY,
    fechaHoraEntrada DATETIME NOT NULL,
    fechaHoraSalida DATETIME NULL, /* Si es NULL, el vehículo está adentro */
    idVehiculo_FK INT NOT NULL,
    FOREIGN KEY (idVehiculo_FK) REFERENCES Vehiculo(idVehiculo)
);

/* --- 6. TABLA DE NOVEDADES --- */
/* Reportes de incidentes asociados a un vehículo */
CREATE TABLE Novedad (
    idNovedad INT AUTO_INCREMENT PRIMARY KEY,
    descripcion TEXT NOT NULL,
    fecha DATETIME NOT NULL,
    idVehiculo_FK INT NOT NULL,
    FOREIGN KEY (idVehiculo_FK) REFERENCES Vehiculo(idVehiculo)
);
