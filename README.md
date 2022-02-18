# MIARMA

#### Repositorio del proyecto **MIARMA** del módulo de Acceso a Datos y Programación de Servicios y Procesos 2021-22. ####

</br>

### Descargar JDK 17 de la página de Oracle, y también descargar IntelliJ IDEA Community Edition. Para poner en marcha el proyecto, una vez se ha abierto en *IntelliJ IDEA*, es ir a la pestaña de Maven, a la derecha de la ventana, abrir la carpeta de Plugins, abrir el apartado de *spring-boot* y pulsar donde diga *spring-boot:run*
### La aplicación consiste en el manejo ficheros de usuarios y sus publicaciones de una red social. Esto nos permite manejar funcionalidades distintas según el tipo de visualización del usuario o publicación(Público o Privado). 
</br>


## Entidades de la aplicación:
* ### Usuario.
* ### Publicacion.

  
</br>

## <p>Funcionalidades de Usuario:</p> ##
#### Asociación @OneToMany con Publicacion.
* Crear un nuevo Usuario.
* Crear un login.
* Mostrar el perfil del usuario logueado.
* Mostrar el perfil de un usuario, si su perfil es público o si lo sigues.
* Editar tu perfil.
* Mandar una petición de seguimiento.
* Aceptar una petición de seguimiento.
* Rechazar una petición de seguimiento.
* Listar todas las solicitudes de seguimiento.
</br>

## <p>Funcionalidades de Publicacion:</p> ##
#### Asociación @ManyToOne hacia Usuario


* Crear una nueva publicación.
* Editar una publicación.
* Borrar una publicación.
* Mostrar una lista de todas las publicaciones públicas.
* Mostrar una publicación en concreto.
* Mostrar una lista de todas las publicaciones de un usuario.
* Mostrar una lista de todas mis publicaciones.

</br>

## Recomendaciones a la hora de probar en Postman:
### Para las consultas en las que se requiran el uso de ficheros, estos se encuentran el directorio uploads dentro del mismo proyecto. Utilice los ficheros que se llamen igual que la consulta.

## Realizado por:
* ### Daniel Oliva
