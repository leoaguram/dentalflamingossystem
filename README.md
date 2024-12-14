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

---

# 📖 Manual de Uso: Dental Flamingos

Este documento explica el flujo básico para utilizar el sistema **Dental Flamingos**, desde el inicio de sesión hasta la generación de reportes de ingresos. Sigue estas instrucciones para navegar por el sistema y aprovechar sus funcionalidades.

---

## **1. Inicio de Sesión**

1. Accede a la URL principal: [http://localhost:8080](http://localhost:8080).
2. Ingresa tus credenciales de dentista:
     - **Dentista 1:**
    - Usuario: noemident10
    - Contraseña: noemident10
  - **Dentista 2:**
    - Usuario: aradent10
    - Contraseña: aradent10
3. Haz clic en el botón **Iniciar Sesión**.
4. Si las credenciales son correctas, serás redirigido al panel principal del sistema.

---

## **2. Gestión de Citas**

### **Consultar Citas**
1. En el menú superior, selecciona la opción **Citas**.
2. Se mostrará una tabla con todas las citas registradas para el dentista logueado.
3. Usa los filtros disponibles para buscar citas:
   - **Por estado**: ACTIVA, CANCELADA, FINALIZADA.
   - **Por paciente**: Busca por nombre del paciente.
   - **Por tratamiento**: Filtra por el tipo de tratamiento asignado.
   - **Por Fecha**: Selecciona un rango de fechas para filtrar los resultados.

### **Registrar una Cita**
1. Haz clic en el botón **Registrar Cita**.
2. Completa el formulario:
   - **Paciente**: Selecciona el paciente de la lista.
   - **Fecha y hora**: Ingresa la fecha y hora de la cita.
   - **Tratamiento**: Selecciona el tratamiento a realizar.
   - **Status**: El status inicial para la cita es ACTIVO.
3. Haz clic en **Guardar** para registrar la cita.

### **Modificar o Cancelar Citas**
1. En la tabla de citas, selecciona la opción **Editar** para modificar una cita.
2. Para cancelar una cita, selecciona la opción **Cancelar** en el menú de acciones.

### **Finalizar una cita**
1. En el Index, seleccióna la cita activa y presiona el botón **Registrar Pago**.
2. En la pantalla de pago, llena la información requerida.
3. Haz clic en **Guardar** para registrar el monto cobrado, el monto pagado y para finalizar la cita.

---

## **3. Gestión de Pacientes**

### **Consultar Pacientes**
1. Selecciona **Pacientes** y después **Consultar** en el menú.
2. Se mostrará una lista de todos los pacientes registrados.
3. Usa el campo de búsqueda para encontrar pacientes por su nombre.

### **Registrar un Nuevo Paciente**
1. Haz clic en el botón **Registrar Paciente**.
2. Completa el formulario con los datos del paciente:
   - Nombre, teléfono, correo electrónico, dirección, etc.
3. Haz clic en **Guardar** para agregar el paciente.

### **Modificar o Eliminar Pacientes**
1. En la lista de pacientes, selecciona la opción **Editar** para actualizar la información.
2. Para eliminar un paciente, selecciona **Eliminar**. Confirma la acción en el modal que aparece.

### **Contactar Pacientes**
1. Desde la lista de pacientes, selecciona el ícono de llamada para contactar al paciente por teléfono.
2. Usa el ícono de WhatsApp para enviarle un mensaje directamente desde el sistema.

---

## **4. Gestión de Pagos**

### **Consultar Pagos**
1. Selecciona **Pagos** en el menú.
2. En la pantalla de filtros, selecciona un paciente y una cita asociada.
3. Haz clic en **Filtrar** para ver los movimientos de pago asociados.

### **Registrar un Pago**
1. Selecciona **Pagos** en el menú.
2. En la pantalla de filtros, selecciona un paciente y una cita asociada.
2. En la pantala de movimientos de pagos, selecciona **Registrar Abono**.
3. Completa el formulario:
   - **Monto pagado**.
   - **Método de pago**: Efectivo, tarjeta, transferencia, etc.
   - **Comentarios**: Opcional.
4. Haz clic en **Guardar** para registrar el movimiento.

### **Generar Recibo en PDF**
1. Si el estado del pago es **LIQUIDADO**, aparecerá un botón **Descargar Recibo**.
2. Haz clic en el botón para generar un PDF con el detalle del tratamiento y los pagos realizados.

---

## **5. Generación de Reportes**

1. Selecciona **Reportes** en el de configuración.
2. Ingresa un rango de fechas:
   - **Fecha inicial** y **Fecha final**.
3. Haz clic en **Generar Reporte**.
4. El sistema descargará un archivo PDF que incluye:
   - La lista de ingresos registrados en el rango seleccionado.
   - El total de ganancias generadas.

---

## **6. Configuración de Usuario**

1. En el menú superior, selecciona el ícono de configuración.
2. Desde esta pantalla puedes:
   - Cambiar tu contraseña.
   - Actualizar tu información personal (nombre, número de contacto, etc.).
3. Haz clic en **Guardar** para confirmar los cambios.

---

## **📌 Notas Importantes**

- **Permisos del Usuario**: Solo los dentistas registrados tienen acceso al sistema. No es posible registrar nuevos dentistas desde la interfaz.
- **Citas y Pagos**: Una cita debe estar registrada antes de asociar pagos o generar recibos.
- **Validaciones**: El sistema no permite eliminar pacientes, citas o pagos si están vinculados a registros importantes.
- **Seguridad**: Las contraseñas se almacenan encriptadas y el acceso al sistema está protegido con autenticación JWT.

---

Este manual cubre los pasos básicos para usar el sistema **Dental Flamingos** de manera eficiente. Si necesitas agregar más detalles o ajustes, indícalo y lo integro. 🚀

---

## 🖋️ Autor

**Leonardo Aguirre Ramírez.**
