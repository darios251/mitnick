Instrucciones de instalaciones:
---------------------------------------------
*Instalación Aplicación:

La aplicacion requiere:
JDK 7
PostgreSQL 8.4

En dependencies/report copiar:
detalleFactura.jasper
detallePagos.jasper
En dependencies/img copiar:
logoComprobante.png
logoReporte.png

en dependencies:
Agregar la libreria del proyecto
Editar el archivo demo.bat y reemplazar demo-1.0-SNAPSHOT.jar por la libreria del proyecto agregada.

Datos:
Armar los datos de instalacion del cliente. Crear base de datos con datos iniciales.

Finalizada la instalación, si los reportes no funcionan agregar los fonts necesarios a Windows/fonts

---------------------------------------------

*Instalación de Impresora Fiscal (MitnickPrinterService):
La apliación requiere sistema operativo Windows XP (Si el SO es superior a Windows XP se debe instalar el servicio de impresion fiscal en una maquina virtual con Windows XP)
Instalar los driver de la impresora fiscal, ejecutando el archivo EpsonFPHostControlX.exe
Ejecutar en una ventana de comando:
C:\WINDOWS\Microsoft.NET\Framework\v2.0.50727\installUtil.exe MitnickPrinterService.exe
(para desinstalar: C:\WINDOWS\Microsoft.NET\Framework\v2.0.50727\installUtil.exe /u MitnickPrinterService.exe)
Luego ir a Servicios del sistema y chequear que exista el servicio MitnickPrinterService, iniciarlo.
Configurarlo para que reinicie el servicio siempre, con tiempo 0.0, esto es para que reinicie automaticamente e inmediatamente despues de cualquier error.

---------------------------------------------

Notas Adicionales:
El archivo datos BKP.bat se debe utilizar para que el cliente realice backups diarios de su base de datos.
Editarlo y copiarlo en el escritorio del cliente.
El cliente debe ejecutar este archivo todos los dias, luego de hacer el cierre Z y cerrar la apliación.
Para iniciar le VM de la printer automaticamente al iniciar el equipo:
startPrinterService.vbs - agregar un acceso directo a este archivo en el menú de inicio