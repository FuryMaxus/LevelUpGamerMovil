# Aplicacion movil de Level-Up Gamer
*por Vicente East y Jose Valenzuela*
## Funcionalidades principales
* Navegacion intuitiva
* Catalogo de Productos
* Sesiones (registro/login/perfil)
* Carrito de compra
## Endpoints utilizados (API externa y microservicio)
* La app usa los puertos 8080 y 8081 de la ip que se use
* En este projecto se utilizaron los siguientes microservicios
  * https://github.com/FuryMaxus/ServicioProductos.git
  * https://github.com/FuryMaxus/ServicioUsuarios.git
### Endpoints de auth y control de usuarios (puerto 8081)
* POST "api/v1/auth/ingreso" : login
* POST "api/v1/auth/registro" : registro
* GET "api/v1/usuarios/me" : datos de perfil
### Endpoints de productos (puerto 8080)
* GET "api/v1/productos" : obtener todos los productos
* PUT "api/v1/productos/{id}" : modifica un producto dependiendo de la id
* DELETE "api/v1/productos/{id}" : elimina un producto dependiendo de la id

## Como Ejecutar?
* Crear las bases de datos db_productos y db_usuarios para produccion (o db_productos_dev y db_usuarios_dev para desarrollo) en mysql
* Prender ambos microservicios de la manera que se desee (localmente o en la nube)
* en el archivo local.propeties del projecto rellenar o crear el campo  "MY_API_IP = **AQUI_REMPLAZAR_CON_LA_IP**" sin comillas
* si no se ingresa un valor se trabajara automaticamente con el servidor local del pc en el que se este trabajando
* buildear
* listo
## Captura de apk firmado y .jks
<img width="341" height="192" alt="Captura desde 2025-11-29 22-12-01" src="https://github.com/user-attachments/assets/ecfa4b8a-83c3-4a87-b101-7e6f1c35f65c" />

  
