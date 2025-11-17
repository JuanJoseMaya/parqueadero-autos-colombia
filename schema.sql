/* ---- MODELO FÍSICO (MySQL/MariaDB) ---- */

/* Guarda los clientes que pagan la mensualidad */
CREATE TABLE Usuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nombreCompleto VARCHAR(150) NOT NULL,
    cedula VARCHAR(20) NOT NULL UNIQUE,
    telefono VARCHAR(20)
);

/* Guarda los vehículos asociados a un cliente */
CREATE TABLE Vehiculo (
    idVehiculo INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(7) NOT NULL UNIQUE, /* Ej: 'ABC123' */
    marca VARCHAR(50),
    color VARCHAR(30),
    idUsuario_FK INT NOT NULL,
    FOREIGN KEY (idUsuario_FK) REFERENCES Usuario(idUsuario)
);

/* Guarda el historial de pagos (CLAVE para RF-03) */
CREATE TABLE PagoMensual (
    idPago INT AUTO_INCREMENT PRIMARY KEY,
    fechaInicio DATE NOT NULL,  /* Fecha en que inicia la cobertura */
    fechaFin DATE NOT NULL,      /* Fecha en que termina la cobertura */
    monto DECIMAL(10, 2) NOT NULL,
    idUsuario_FK INT NOT NULL,
    FOREIGN KEY (idUsuario_FK) REFERENCES Usuario(idUsuario)
);

/* Guarda las entradas y salidas (CLAVE para Iteración 1) */
CREATE TABLE RegistroMovimiento (
    idMovimiento INT AUTO_INCREMENT PRIMARY KEY,
    fechaHoraEntrada DATETIME NOT NULL,
    fechaHoraSalida DATETIME NULL, /* NULO si el vehículo está DENTRO */
    idVehiculo_FK INT NOT NULL,
    FOREIGN KEY (idVehiculo_FK) REFERENCES Vehiculo(idVehiculo)
);

/* Guarda reportes sobre un vehículo (para RF-06) */
CREATE TABLE Novedad (
    idNovedad INT AUTO_INCREMENT PRIMARY KEY,
    descripcion TEXT NOT NULL,
    fecha DATETIME NOT NULL,
    idVehiculo_FK INT NOT NULL,
    FOREIGN KEY (idVehiculo_FK) REFERENCES Vehiculo(idVehiculo)
);
