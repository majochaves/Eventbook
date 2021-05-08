## Datamodeler DB
En la carpeta "Datamodeler DB" se encuentra todo el proyecto de la base de datos realizado en datamodeler.

## Scripts SQL
En esta carpeta se encuentra el script para la creación de las tablas de la base de datos Derby.  
  
__Nota importante:__ Si habías ejecutado la versión antigua del script, deberás: 
- Ejecutar únicamente el script __ActualizarBD.sql__.
- Posteriormente, deberás cambiar el tipo de dato en el paquete __dao__ -> __Campoanalisis.java__ -> el atributo "__Integer__ valor"  cambiarlo a __Double__
- Por último cambiar tambien el get y set de dicho atributo (el código marcará el error automáticamente).

## Imágenes DB
### Entidad/Relación
<img src="https://github.com/majochaves/Eventbook/blob/database/Imagen%20BD/Imagen%20Entidad-Relacion.JPG" alt="Aprende Ayudando" width="2000">

### Modelo Relacional
<img src="https://github.com/majochaves/Eventbook/blob/database/Imagen%20BD/Imagen%20Modelo%20Relacional.JPG" alt="Aprende Ayudando" width="2000">
