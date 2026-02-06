# Changelog

Todos los cambios entre versiones del proyecto se mostrán aquí


## [1.0.0] - 2025-12-22

### Added

- Estructura Base: Actividad principal con la TopAppBar que incluye un icono para las acciones de añadir.

- Menú Overflow: Implementación de menú desplegable con la opción About us.

- Vistas de Entidad: Creación de las ventanas iniciales para añadir elementos y mostrar el listado de la entidad asignada.

- Identificación Visual: Inclusión imágenes o iconos para identificar elementos en el listado.

- State Hoisting: Aplicación de elevación de estado en cada ventana, separando la lógica de la vista.

- Componentes Reutilizables: Diseño de componentes con propiedades similares para su reutilización en diversas partes de la interfaz.

- Personalización Global: Creación y aplicación de un CompositionLocal personalizado para modificar aspectos específicos de la interfaz de usuario.

## [2.0.0] - 2026-01-05

### Added

- Patrón MVVM: Implementación de estados de UI y sus correspondientes ViewModels para las vistas de añadir, editar y listar la entidad.

- Inyección de Dependencias: Integración completa de Hilt en toda la aplicación.

- Repositorio Estático: Creación de datos estáticaos que proporciona información a los ViewModels.

- Flujos de Datos: Simulación de base de datos en la operación de listado mediante el uso de Flow o StateFlow.

- Animaciones de Navegación: Implementación de transiciones de entrada y salida entre pantallas utilizando animaciones de navegación.

- Gestión de Borrado: Cuadro de diálogo para la confirmación de eliminación de elementos del listado.

- Notificaciones UI: Uso de SnackBar para informar sobre el resultado de la eliminación de datos.

- Acceso Rápido: Incorporación de un FloatingActionButton en el Scaffold para navegar a la ventana de añadir.

- AboutScreen Avanzada: Pantalla cn información del desarrollador.

- Previsualizaciones: Implementación de un preview para cada ventana de la aplicación.

- Documentación: Creación de los archivos README.md y CHANGELOG.md.

## [3.0.0] - 2026-02-06

### Added

- Icono: Personalización del icono de la app

- About us: Ahora se incluye logo de la app, nombre, Datos de forma vistosa, dos botones con enlaces externos a plataformas del dev

- Drawer :Inclusión del drawer desde el MainActivity de la app para que se superponga a todos los elementos con control de habilitación de apertura por gestos

- Colores: Mayor personalización de la interfaz con colores diferentes según el tema

- Listar: Mejora estética, pulido de las cards que listan los elementos y adición de una cabecera dinámica que cambia según el tema claro/oscuro

- Botón eliminar: Adición de botón eliminar en las cards del listado manteniendo a su vez la función de eliminar dejando pulsada la card

- Barra de búsqueda: Nueva en la screen de listar, busca películas según su título, actualiza el listado según el usuario escribe, no necesita el titulo completo

- FAB: En las Screen de add/edit, se ha sustituido el botón inferior de guardado por un floating action button

- Checkboxes: Adición de campo de tipo checkbox en las screen add/edit

- Control de fechas: Adición de campo de tipo fecha en las screen add/edit que despliegan el teclado numérico y tienen validación de formato de fecha

- Control de duplicados: Validación de títulos duplicados, mostrando al usuario un AlertDialog en caso de duplicidad

- Header add/edit: Header dinámico que aparece si el título es distinto de cadena vacía y se va actualizando según el usuario escribe, cambia con el tema

- Control de permisos: La app ahora pedirá permisos al usuario cuando los necesite

- Notificaciones: Se emitirán al añadir una nueva película exitosamente

- Loading Screen: Pantalla de carga que se mostrará mientras la app carga datos del repositorio

- Documentación: Mejoras en la documentación del código

- CompositionLocal: Uso para dimensiones universales en todos los componentes y también para los campos de texto de los formularios

- Repositorio: Ahora se hace uso de room para la persistencia real en una base de datos

- Navhost: Ahora la navegación funciona por grafos

- Nuevos iconos e imágenes usados en diferentes componentes
