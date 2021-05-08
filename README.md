## Datamodeler DB
En la carpeta "Datamodeler DB" se encuentra todo el proyecto de la base de datos realizado en datamodeler.

## Imagen DB
En esta carpeta se encuentran imágenes del modelo relacional y el modelo entidad/relación.

## Scripts SQL
En esta carpeta se encuentra el script para la creación de las tablas de la base de datos Derby.  
  
__Nota importante:__ Si habías ejecutado la versión antigua del script, deberás: 
- Ejecutar únicamente el script __ActualizarBD.sql__.
- Posteriormente, deberás cambiar el tipo de dato en el paquete __dao__ -> __Campoanalisis.java__ -> el atributo "__Integer__ valor"  cambiarlo a __Double__
- Por último cambiar tambien el get y set de dicho atributo (el código marcará el error automáticamente).
