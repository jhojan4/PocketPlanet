PocketPlanet
PocketPlanet es una aplicacion Android desarrollada con Kotlin y Jetpack Compose para apoyar el cuidado de plantas. La app combina gestion personalizada de plantas del usuario, fichas informativas de especies comunes, configuracion de recordatorios y opciones basicas de perfil.

Objetivo
La aplicacion busca servir como un asistente de bolsillo para personas que quieren:

registrar sus propias plantas;
consultar informacion de cuidado;
recibir alertas de riego, fertilizacion y poda;
administrar preferencias como tema oscuro y notificaciones.
Tecnologias utilizadas
Kotlin
Android Studio
Jetpack Compose
Navigation Compose
Material 3
Firebase Firestore
Firebase Analytics
Firebase Authentication
Firebase Cloud Messaging
Android DataStore
AlarmManager para alertas locales
Requisitos tecnicos
Android SDK 35
Min SDK 33
Java 11
Gradle con Android Gradle Plugin 8.8.0
Kotlin 2.0.0
Estructura general del proyecto
El proyecto contiene un unico modulo:

:app: aplicacion principal Android.
Paquetes principales dentro de app/src/main/java/edu/unicauca/example/pocketplanet:

Presentacion: pantalla inicial de bienvenida.
Inicio_Sesion: inicio de sesion.
Registro: registro de usuarios.
InicioAplicacion: pantalla principal con busqueda y listado de plantas del usuario.
Agregar_Planta: formulario para registrar plantas personalizadas.
Informacion_Planta: detalle de plantas registradas por el usuario.
Consejos: catalogo de plantas predefinidas y consejos de jardineria.
PerfilConfiguraciones: perfil, tema, idioma y preferencias.
Notificaciones: configuracion de alertas y disparo de notificaciones.
ui/theme: tema visual de Compose.
Flujo funcional de la app
1. Pantalla de presentacion
La aplicacion inicia en una pantalla de bienvenida con un carrusel visual y dos accesos:

Inicio: abre el login.
Registrar: abre el formulario de registro.
2. Registro de usuarios
El registro solicita:

nombre de usuario;
correo electronico;
pais;
numero de celular;
contrasena;
confirmacion de contrasena.
El registro valida:

campos obligatorios;
formato de correo;
que el telefono solo tenga numeros;
coincidencia de contrasenas;
que no exista otro usuario con el mismo correo.
Los usuarios se almacenan en Firestore en la coleccion users. La contrasena se guarda usando hash SHA-256.

3. Inicio de sesion
El login se realiza consultando la coleccion users en Firestore. El sistema:

busca el usuario por correo;
calcula el hash SHA-256 de la contrasena ingresada;
compara el hash con el almacenado;
recupera el userId del documento para usarlo en la navegacion posterior.
4. Inicio de la aplicacion
Despues de iniciar sesion, el usuario entra a la pantalla principal, donde puede:

buscar plantas registradas;
ver el listado de sus plantas;
abrir el detalle de una planta;
ir a agregar una nueva planta;
navegar a alertas, consejos y perfil.
Las plantas del usuario se consultan desde Firestore en la coleccion dataplants, filtrando por userId.

5. Registro de plantas personalizadas
La pantalla AgregarPlanta permite guardar:

nombre;
tipo;
horas de sol;
cantidad de agua;
tipo de fertilizacion;
informacion adicional.
Cada planta queda asociada al userId del usuario autenticado. El ViewModel intenta asignar un identificador secuencial basado en el ultimo registro guardado en Firestore.

6. Detalle de plantas del usuario
La pantalla Informacion_Planta consulta Firestore por nombre de planta y muestra:

nombre;
tipo;
horas de sol;
cantidad de agua;
tipo de fertilizacion;
informacion adicional.
7. Catalogo de consejos de jardineria
La app incluye una pantalla de consejos con una lista predefinida de plantas ornamentales, aromaticas y suculentas. Entre ellas:

Aloe Vera
Albahaca
Agave
Cactus
Calatea
Ficus
Lavanda
Orquidea
Romero
Yuca
Zinnia
Cada planta abre una ficha de detalle con:

nombre cientifico;
tipo de planta;
ubicacion recomendada;
frecuencia de riego;
luz;
tipo de maceta;
sustrato;
poda;
plagas comunes.
Esta informacion esta definida principalmente en strings.xml y en recursos drawable.

8. Perfil y configuracion
Desde la pantalla de perfil el usuario puede:

ver nombre, correo, pais y celular;
editar esos datos;
activar o desactivar notificaciones;
cambiar entre modo claro y oscuro;
alternar idioma guardado localmente;
lanzar una notificacion de prueba;
cerrar sesion.
La informacion de perfil se carga y actualiza en Firestore, mientras que algunas preferencias locales se almacenan con DataStore y SharedPreferences.

9. Alertas y recordatorios
La aplicacion permite configurar alertas para:

riego;
fertilizacion;
poda.
Cada alerta puede activarse con una hora en formato hh:mm. Internamente se usa:

AlarmManager para programar eventos diarios;
BroadcastReceiver (NotificationReceiver) para recibir el evento;
NotificationManagerCompat para mostrar la notificacion.
Persistencia de datos
Firebase / Firestore
Colecciones identificadas en el codigo:

users: datos de usuario.
dataplants: plantas creadas por los usuarios.
configuracion: documento usado para seguimiento del id de plantas.
El proyecto tambien incluye app/google-services.json, por lo que ya existe una configuracion enlazada a Firebase.

Persistencia local
DataStore: estado de notificaciones y tema oscuro.
SharedPreferences: idioma actual.
Navegacion
La navegacion se define en AppNavigation.kt. Las rutas principales son:

PresentacionScreen
InicioSesionScreen
RegistroScreen
InicioAplicacion/{userId}
ConsejosScreen/{userId}
ConfiguracionesScreen/{userId}
AgregarPlantaScreen/{userId}
AlertConfigScreen/{userId}
informacion_planta/{nombre}/{userId}
detalle_planta/{nombre}
Recursos destacados
logo e iconos de la aplicacion;
imagenes de especies vegetales para la seccion de consejos;
cadenas de texto extensas para fichas informativas de plantas;
tema visual personalizable con soporte de modo oscuro.
Como ejecutar el proyecto
Abrir el proyecto en Android Studio.
Verificar que el SDK de Android 35 este instalado.
Sincronizar Gradle.
Confirmar que google-services.json este presente en app/.
Ejecutar la app en un emulador o dispositivo con Android 13 o superior.
Posibles mejoras tecnicas
Durante la lectura del codigo se observan oportunidades claras de mejora:

unificar el uso de Firebase Authentication, ya que hoy el login principal consulta Firestore manualmente;
fortalecer la seguridad del manejo de contrasenas;
centralizar modelos de datos para evitar clases Planta duplicadas en distintos paquetes;
persistir las horas de alertas para no perderlas al reiniciar la app;
internacionalizar realmente la interfaz, ya que el idioma cambia un estado local pero no toda la capa de recursos;
agregar pruebas unitarias y de interfaz;
limpiar dependencias repetidas o con versiones mezcladas en build.gradle.kts.
Estado actual del proyecto
PocketPlanet ya tiene una base funcional importante:

autenticacion basica;
registro y consulta de plantas;
seccion educativa de consejos;
perfil editable;
tema oscuro;
alertas locales.
Es un buen punto de partida para evolucionar hacia una aplicacion de cuidado de plantas mas robusta, con mejor arquitectura, autenticacion mas consistente y una experiencia de usuario mas pulida.
