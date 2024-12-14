# Dental Flamingos System - Sistema de Gestión de Consultorio Dental 🦷

**Dental Flamingos** es un sistema desarrollado para la administración de un consultorio dental con enfoque en la gestión de citas, pagos y pacientes, el sistema sólo tiene dos dentistas registrados, por lógica de negocio no pueden registrarse más dentistas ni pueden eliminarse, cada dentista cuenta con sus claves de acceso, que son usuario y contraseña.

---

## 🚀 Características Principales

1. **Gestión de Citas**
   - Agendar, modificar, y cancelar citas.
   - Filtrado de citas por paciente, tratamiento, y estado (ACTIVA, CANCELADA, FINALIZADA).

2. **Gestión de Pacientes**
   - Registro de nuevos pacientes.
   - Actualización y eliminación de información.
   - Contacto directo con los pacientes mediante llamadas o WhatsApp desde el sistema.

3. **Gestión de Pagos**
   - Registro de movimientos de pago por cita.
   - Generación de recibos en PDF para tratamientos pagados.
   - Cálculo y registro automático del monto restante y el estatus del pago (PENDIENTE o LIQUIDADO).

4. **Reportes**
   - Generación de reportes de ingresos por rango de fechas en formato PDF.
   - Resumen de ganancias totales.

5. **Seguridad**
   - Sistema de login con autenticación mediante Spring Security.
   - Contraseñas encriptadas con BCrypt.
   - Gestión de sesiones mediante JWT.

---

## 🛠️ Tecnologías Utilizadas

- **Backend**: Spring Boot, Spring Security, Spring Data JPA.
- **Frontend**: Thymeleaf, HTML, CSS, Bootstrap, JavaScript.
- **Base de Datos**: MySQL.
- **Librerías adicionales**:
  - iText 5.5.13.4 (Generación de PDFs).

---

## 📋 Requisitos Previos

- Java 17+ (Compatible con versiones superiores).
- MySQL (con un esquema inicial proporcionado).
- Maven (para la gestión de dependencias).

---

## ⚙️ Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/leoaguram/dentalflamingossystem.git

2. Importa el proyecto en tu IDE preferido (IntelliJ, Eclipse, etc.).
3. Configura las propiedades de conexión en application.properties:
   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/dentalflamingos_bkp
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña

4. Crea la base de datos y ejecuta los scripts de inicialización (DDL y datos de prueba).
5. Construye y ejecuta la aplicación
6. Accede al sistema:
   URL: http://localhost:8080/dentalflamingos/
  - Dentista 1:
    - Usuario: noemident10
    - Contraseña: noemident10
  - Dentista 2:
    - Usuario: aradent10
    - Contraseña: aradent10

---

## 🖋️ Autor

**Leonardo Aguirre Ramírez.**
