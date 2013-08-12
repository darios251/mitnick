Instrucciones Instalador:

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
Armar los datos de instalacion del cliente.

La aplicacion requiere:
JDK 7
PostgreSQL 8.4
Si el SO es superior a Windows XP se debe instalar el servicio de impresion fiscal en una maquina virtual con WXP.
Si los reportes no funcionan agregar los fonts necesarios a Windows/fonts