CREATE DATABASE dentalflamingos_bkp;
USE dentalflamingos_bkp;

DROP TABLE IF EXISTS dentistas;
CREATE TABLE dentistas(
    idDentista INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    numero_celular VARCHAR(10),
    usuario VARCHAR(15) NOT NULL,
    dent_password VARCHAR(150) NOT NULL,
    UNIQUE (nombre, numero_celular)
);

DROP TABLE IF EXISTS pacientes;
CREATE TABLE pacientes (
    idPaciente INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idDentista INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    fecha_nac DATE NOT NULL,
    numero_celular VARCHAR(10) NOT NULL UNIQUE,
    email VARCHAR(100),
    direccion VARCHAR(100),
    FOREIGN KEY (idDentista) REFERENCES dentistas(idDentista)
);

DROP TABLE IF EXISTS catalogostatus;
CREATE TABLE catalogostatus (
    idStatus INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS catalogotratamientos;
CREATE TABLE catalogotratamientos(
    idTratamiento TINYINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(50)
);

DROP TABLE IF EXISTS citas;
CREATE TABLE citas(
    idCita INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    fecha_hora_cita DATETIME NOT NULL,
    observaciones TEXT,
    -- idDentista INT NOT NULL,
    idTratamiento TINYINT NOT NULL,
    idPaciente INT NOT NULL,
    idStatus INT NOT NULL,
    UNIQUE(idPaciente, fecha_hora_cita),
    -- FOREIGN KEY (idDentista) REFERENCES dentistas(idDentista),
    FOREIGN KEY (idTratamiento) REFERENCES catalogotratamientos(idTratamiento),
    FOREIGN KEY (idPaciente) REFERENCES pacientes(idPaciente),
    FOREIGN KEY (idStatus) REFERENCES catalogostatus(idStatus)
);

CREATE TABLE pagos_movimientos (
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
    id_cita INT NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    fecha_pago DATETIME NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    comentarios TEXT,
    CONSTRAINT fk_movimiento_cita FOREIGN KEY (id_cita) REFERENCES citas(idCita) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE pagos_resumen (
    id_resumen INT AUTO_INCREMENT PRIMARY KEY,
    id_cita INT NOT NULL,
    monto_total DECIMAL(10, 2) NOT NULL,
    monto_pagado DECIMAL(10, 2) DEFAULT 0.00 NOT NULL,
    monto_restante DECIMAL(10, 2) GENERATED ALWAYS AS (monto_total - monto_pagado) STORED,
    id_status INT NOT NULL,
    CONSTRAINT fk_resumen_cita FOREIGN KEY (id_cita) REFERENCES citas(idCita) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_resumen_status FOREIGN KEY (id_status) REFERENCES catalogostatus(idStatus) ON DELETE RESTRICT ON UPDATE CASCADE
);

DROP TRIGGER IF EXISTS trg_resumen_before_insert;
DELIMITER $$
CREATE TRIGGER trg_resumen_before_insert
BEFORE INSERT ON pagos_resumen
FOR EACH ROW
BEGIN
    IF NEW.monto_restante = 0.00 THEN
        SET NEW.id_status = (SELECT idStatus FROM catalogostatus WHERE descripcion = 'LIQUIDADO');
    ELSE
        SET NEW.id_status = (SELECT idStatus FROM catalogostatus WHERE descripcion = 'PENDIENTE');
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS trg_resumen_before_update;
DELIMITER $$
CREATE TRIGGER trg_resumen_before_update
BEFORE UPDATE ON pagos_resumen
FOR EACH ROW
BEGIN
    SET NEW.monto_pagado = OLD.monto_pagado + NEW.monto_pagado;
    
    SET NEW.monto_restante = NEW.monto_total - NEW.monto_pagado;
    
    IF NEW.monto_restante = 0.00 THEN
        SET NEW.id_status = (SELECT idStatus FROM catalogostatus WHERE descripcion = 'LIQUIDADO');
    ELSE
        SET NEW.id_status = (SELECT idStatus FROM catalogostatus WHERE descripcion = 'PENDIENTE');
    END IF;
END$$
DELIMITER ;

INSERT INTO catalogostatus (descripcion)
VALUES ('ACTIVA'), ('CANCELADA'), ('FINALIZADA'), ('PENDIENTE'), ('LIQUIDADO');

INSERT INTO catalogotratamientos (descripcion) VALUES
('Endodoncia'),
('Extracción'),
('Limpieza'),
('Revisión general'),
('Blanqueamiento'),
('Remoción de sarro');

INSERT INTO dentistas (nombre, numero_celular, usuario, dent_password) VALUES
('Noemi Esther Ramírez Silva', '5575444640', 'noemident10', '$2y$11$ttK2DB7qJYR0LqlzoQLXC.izc/Jue54PoFwG/jwlnqH0jY/uzJgyu'),
('Aranza Esther Aguirre Ramírez', '5533470090', 'aradent10', '$2y$11$bEU8YKQiimKxlwdhP.bVm.ilbQEMVpQviNanvlsCOEFzGD4NHFwdK');

INSERT INTO pacientes (idDentista, nombre, fecha_nac, numero_celular, email, direccion) VALUES
(1, 'Juan Pérez', '1985-04-23', '5551234567', 'juan.perez@example.com', 'Av. Siempre Viva 123'),
(1, 'María García', '1990-06-15', '5552345678', 'maria.garcia@example.com', 'Calle Falsa 456'),
(1, 'Carlos López', '1978-09-10', '5553456789', 'carlos.lopez@example.com', 'Boulevard Principal 789'),
(1, 'Ana Rodríguez', '1982-12-25', '5554567890', 'ana.rodriguez@example.com', 'Plaza Central 101'),
(2, 'Luis Fernández', '1995-01-30', '5555678901', 'luis.fernandez@example.com', 'Callejón del Beso 202'),
(2, 'Sofía Martínez', '2000-03-14', '5556789012', 'sofia.martinez@example.com', 'Avenida Libertad 303'),
(2, 'Miguel Torres', '1987-07-19', '5557890123', 'miguel.torres@example.com', 'Paseo de la Reforma 404'),
(2, 'Laura González', '1992-11-05', '5558901234', 'laura.gonzalez@example.com', 'Camino Real 505'),
(1, 'Jorge Hernández', '1980-08-22', '5559012345', 'jorge.hernandez@example.com', 'Ruta del Sol 606'),
(2, 'Lucía Ramírez', '1998-05-09', '5550123456', 'lucia.ramirez@example.com', 'Carretera Nacional 707');

INSERT INTO citas (idPaciente, idTratamiento, fecha_hora_cita, observaciones, idStatus) VALUES
(1, 1, '2024-07-17T15:30', 'El paciente notificó llegar con 10 min de retraso', 3),
(2, 3, '2024-07-17T15:30', 'NA', 3),
(8, 4, '2024-07-17T17:00', 'Nuevo paciente recomendado por señora Florencia', 3),
(3, 6, '2024-07-19T18:00', 'El paciente cuenta con sensibilidad en encías', 3),
(10, 5,'2024-07-19T15:45', 'El paciente tiene los dientes en un grado de manchado nivel 4', 3),
(3, 1, '2024-07-21T16:00', 'Primera cita de endodoncia', 3),
(3, 1, '2024-07-22T09:30', 'Segunda cita de endodoncia, colocación de coronas temporales', 3),
(3, 1, '2024-07-24T10:00', 'Cita final de endodoncia, restauración con amalgama', 3),
(5, 2, '2024-07-20T09:00', 'Paciente requiere extracción de la pieza 14', 1),
(6, 3, '2024-07-20T10:00', 'Limpieza de rutina', 1),
(7, 5, '2024-07-20T11:00', 'Paciente quiere blanqueamiento dental', 1),
(1, 4, '2024-07-20T12:00', 'Revisión general post-endodoncia', 2),
(8, 2, '2024-07-20T13:00', 'Paciente solicita extracción de muela del juicio', 3),
(9, 6, '2024-07-21T14:00', 'Remoción de sarro por acumulación', 3),
(4, 5, '2024-07-21T15:30', 'Blanqueamiento programado', 3),
(10, 4, '2024-07-22T17:00', 'Revisión general anual', 3),
(2, 3, '2024-07-22T18:30', 'Limpieza profunda', 1);

INSERT INTO pagos_movimientos (id_cita, monto, fecha_pago, metodo_pago, comentarios)
VALUES
(1, 500.00, '2024-11-01 10:00:00', 'Efectivo', 'Abono inicial'),
(1, 1000.00, '2024-11-15 14:30:00', 'Tarjeta', 'Abono final'),
(2, 700.00, '2024-11-02 09:00:00', 'Transferencia', 'Primer pago'),
(2, 800.00, '2024-11-16 12:00:00', 'Efectivo', 'Segundo abono'),
(3, 1500.00, '2024-11-05 16:00:00', 'Efectivo', 'Pago completo'),
(4, 300.00, '2024-11-06 10:15:00', 'Tarjeta', 'Primer abono'),
(4, 200.00, '2024-11-20 11:30:00', 'Transferencia', 'Segundo abono'),
(5, 1000.00, '2024-11-10 13:00:00', 'Efectivo', 'Abono inicial'),
(5, 2000.00, '2024-11-25 15:00:00', 'Tarjeta', 'Pago final'),
(1, 500.00, '2024-11-30 10:00:00', 'Transferencia', 'Pago adicional');

INSERT INTO pagos_resumen (id_cita, monto_total, monto_pagado)
VALUES
(1, 2500.00, 500.00),
(2, 1000.00, 500.00),
(3, 2800.00, 200.00),
(4, 1000.00, 500.00),
(5, 3000.00, 3000.00);