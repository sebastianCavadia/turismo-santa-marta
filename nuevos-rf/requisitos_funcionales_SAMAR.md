# Requisitos funcionales – SAMAR (versión revisada)

Este documento reorganiza los requisitos del borrador por caso de uso de nivel 1
## Gestionar cuenta

- RF001 - Gestionar cuenta (registrarse): El sistema debe permitir a los usuarios registrarse indicando que tipo de usuario es (turista, restaurante, agencia turistica u hotel) para que este realice un formulario indicando datos distintos dependiendo del tipo de usuario y su posterior validacion de parte del sistema.
- RF002 - Gestionar cuenta (registro turista): Al registrarse, el Turista debe indicar tipo de documento (CC, CE o pasaporte), número de documento, nacionalidad, país de residencia y fecha de nacimiento, y puede indicar si tiene alguna discapacidad. El sistema debe validar que el número de documento no esté repetido.
- RF003 - Gestionar cuenta (registro hotel):
- RF004 - Gestionar cuenta (registro restaurante):
- RF005 - Gestionar cuenta (registro agencia turistica):
- RF006 - Gestionar cuenta (iniciar de sesión): El sistema debe permitir a los usuarios autenticarse mediante su correo electrónico y contraseña registrados, habilitando el acceso a las funciones específicas correspondientes a su rol.
- RF007 - Gestionar cuenta (recuperar contraseña): El sistema debe permitir a los usuarios recuperar el acceso a su cuenta mediante el envío de un enlace con token temporal al correo electrónico registrado para el restablecimiento de la contraseña.
- RF008 - Gestionar cuenta (editar perfil): El sistema debe permitir a los usuarios autenticados consultar y actualizar la información de su perfil tanto personal como empresarial, incluyendo su nombre, teléfono de contacto, ubicacion (si este es empresarial) y foto de perfil.
- RF009 - Gestionar cuenta (eliminar cuenta): El sistema debe permitir al usuario solicitar la eliminación permanente de su cuenta y anonimización de sus datos en bases de datos históricas.
- RF010 - Gestionar cuenta (bloqueo temporal por intentos fallidos): El sistema debe bloquear temporalmente una cuenta (5 minutos) después de 5 intentos fallidos consecutivos de inicio de sesión.
- RF011 - Gestionar cuenta (estados de la cuenta): El sistema debe manejar los estados ACTIVO, INACTIVO y BLOQUEADO. Solo las cuentas ACTIVAS pueden iniciar sesión, y las BLOQUEADAS deben mostrar un mensaje que indique contactar a la administración.
- RF012 - Gestionar cuenta (idioma de la interfaz): El sistema debe permitir al usuario seleccionar el idioma de la interfaz y guardar esa preferencia en su perfil.
- RF013 - Gestionar cuenta (cerrar sesión): El sistema debe permitir cerrar sesión, invalidando el token de acceso activo del dispositivo.

## Gestionar oferta turística

- RF014 - Gestionar oferta turística (registrar servicio turístico): El sistema debe permitir a los Prestadores de Servicio (Hoteles, Restaurantes, Guías y Agencias Turísticas) registrar sus servicios turísticos seleccionando el tipo de servicio, indicando la ubicación geográfica (dirección o coordenadas), adjuntando imágenes representativas y especificando la modalidad de atención del establecimiento: con reserva previa (gestión de cupos por fechas y horarios) o atención directa / walk-in (para establecimientos de atención inmediata por flujo peatonal).
- RF015 - Gestionar oferta turística (editar servicio turístico): El sistema debe permitir a los Prestadores de Servicio actualizar la información general, la ubicación y la galería de imágenes de los servicios turísticos que tengan previamente registrados.
- RF016 - Gestionar oferta turística (actualizar disponibilidad): El sistema debe permitir a los Prestadores de Servicio modificar la disponibilidad de sus servicios, ajustando cupos máximos, horarios de atención y fechas de operación.
- RF017 - Gestionar oferta turística (cargar imágenes): El sistema debe permitir a los Prestadores de Servicio cargar y eliminar imágenes en formatos estándar (JPEG, PNG) para la galería visual de sus publicaciones, validando un tamaño máximo por archivo.
- RF018 - Gestionar oferta turística (límite de aforo): El sistema debe permitir definir y verificar límites de aforo máximo diario por servicio o prestador, bloqueando automáticamente la creación de nuevas reservas cuando se alcance la capacidad máxima configurada.
- RF019 - Gestionar oferta turística (accesibilidad de imágenes): El sistema debe asegurar que todas las imágenes subidas por Prestadores incluyan atributos "alt" obligatorios y etiquetas ARIA para lectores de pantalla.
- RF020 - Gestionar oferta turística (registrar guía turístico en oferta): El sistema debe permitir registrar guías turísticos siendo parte de las agencias turisticas, indicando los idiomas en los que presta el servicio.
- RF021 - Gestionar oferta turística (atributos por tipo de servicio): Según el tipo de servicio, el sistema debe solicitar campos específicos: en hoteles, categoría en estrellas, número de habitaciones, hora de check-in y hora de check-out; en restaurantes, tipo de cocina, aforo, horario de apertura y cierre, y opciones dietéticas; en actividades, duración, nivel de dificultad, edad mínima y qué incluye.
- RF022 - Gestionar oferta turística (categorías): El prestador debe asignar una o más categorías al servicio (playa, naturaleza, cultura, aventura, gastronomía o descanso), que se usarán como filtro de búsqueda.
- RF023 - Gestionar oferta turística (precio base y moneda): Cada servicio debe tener un precio base y una moneda definidos por el prestador, que sirven como referencia para las reservas y para los filtros de precio.
- RF024 - Gestionar oferta turística (marca de accesibilidad): El prestador debe poder indicar si el servicio es accesible para personas con movilidad reducida. Este dato se usa en búsquedas y recomendaciones.
   
## Gestionar reserva

- RF025 - Gestionar reserva (consultar disponibilidad): El sistema debe verificar y mostrar al Turista la disponibilidad en tiempo real de cupos, fechas y horarios para un servicio turístico antes de proceder con el registro de la reserva.
- RF026 - Gestionar reserva (crear reserva): El sistema debe permitir al Turista realizar la reserva de un servicio turístico seleccionando fecha, hora y número de personas, generando una solicitud en estado "Pendiente".
- RF027 - Gestionar reserva (confirmar o rechazar solicitud): El sistema debe permitir al Prestador de Servicio gestionar las solicitudes de reserva recibidas, cambiando su estado a "Confirmada" o "Rechazada".
- RF028 - Gestionar reserva (notificar estado de reserva): El sistema debe enviar notificaciones automáticas al Turista (vía correo electrónico o alertas internas) informando sobre las actualizaciones en el estado de su reserva (confirmada, rechazada o cancelada), incluyendo las novedades originadas por acciones del Prestador de Servicio.
- RF029 - Gestionar reserva (cancelar reserva): El sistema debe permitir tanto al Turista como al Prestador de Servicio cancelar una reserva activa, actualizando inmediatamente la disponibilidad de cupos en la plataforma.
- RF030 - Gestionar reserva (consultar estado de reserva): El sistema debe permitir al Turista consultar el estado actual (pendiente, confirmada, rechazada o cancelada) de cada una de sus reservas.
- RF031 - Gestionar reserva (pasarela de pagos): El sistema debe permitir pagar la reserva en línea mediante una pasarela de pagos externa certificada. La plataforma no debe almacenar el número completo de la tarjeta.
- RF032 - Gestionar reserva (confirmación por pago): Una reserva solo puede pasar a CONFIRMADA cuando el pago haya sido aprobado por la pasarela o validado manualmente por el prestador, según la modalidad de pago configurada.
- RF033 - Gestionar reserva (reembolso): Al cancelar una reserva pagada dentro del plazo sin penalización definido en RF030, el sistema debe generar un reembolso al Turista y registrar su estado.
- RF034 - Gestionar reserva (cálculo del precio total): El sistema debe calcular el total de la reserva sumando los subtotales de cada servicio, los impuestos aplicables y la comisión de la plataforma, y mostrar el desglose antes del pago.
- RF035 - Gestionar reserva (estados de la reserva): El sistema debe manejar los estados PENDIENTE, CONFIRMADA, RECHAZADA, CANCELADA y COMPLETADA. El estado RECHAZADA debe agregarse al enum EstadoReserva del diagrama de clases.
- RF036 - Gestionar reserva (completar reserva): El sistema debe cambiar automáticamente a COMPLETADA las reservas CONFIRMADAS cuya fecha de uso ya haya pasado.
- RF037 - Gestionar reserva (paquete turístico): El sistema debe permitir combinar varios servicios en un paquete con día y orden de ejecución, calcular el precio total del paquete y registrar cada servicio como un ítem del paquete.
- RF038 - Gestionar reserva (liquidación a prestadores): El sistema debe calcular, para cada prestador, el monto a pagar por las reservas COMPLETADAS descontando la comisión de la plataforma, y registrar el estado de cada liquidación.
- RF039 - Gestionar reserva (comprobante electrónico): Al confirmarse un pago, el sistema debe generar un comprobante en PDF con los datos de la reserva, el desglose del precio y el número de transacción, disponible en el perfil del Turista.
- RF040 - Gestionar reserva (recordatorio previo): El sistema debe enviar al Turista un recordatorio por correo electrónico 24 horas antes de la fecha de una reserva confirmada.
- RF041 - Gestionar reserva (cupón en el pago): El sistema debe permitir aplicar un código promocional durante el pago, validando que esté vigente, que no haya superado su límite de usos y que sea aplicable al servicio.
- RF042 - Gestionar reserva (registro de transacciones): El sistema debe registrar cada intento de pago con fecha, monto, estado (aprobado, rechazado o pendiente) y referencia de la pasarela, para trazabilidad y conciliación.
- RF043 - Gestionar reserva (expiración sin pago): Si una reserva pendiente de pago no se completa dentro del plazo configurado (por ejemplo, 15 minutos), el sistema debe cancelarla automáticamente y liberar los cupos reservados.

## Consultar recomendaciones

- RF044 - Consultar recomendaciones (personalizar preferencias): El sistema debe permitir al Turista ajustar los criterios o parámetros de una recomendación generada para que el módulo de IA la genere nuevamente. El Turista no puede modificar directamente el contenido producido por el modelo, a fin de preservar su transparencia e imparcialidad.
- RF045 - Consultar recomendaciones (listar recomendaciones): El sistema debe permitir al Turista consultar el listado de las recomendaciones generadas para él, junto con los criterios tomados en cuenta para cada sugerencia.
- RF046 - Consultar recomendaciones (generar recomendacion): Antes de generar recomendaciones, el sistema debe registrar la solicitud del Turista con número de personas, rango de fechas, presupuesto, categorías de interés, restricciones dietéticas y preferencia de accesibilidad.
- RF047 - Consultar recomendaciones (registro de ejecuciones): Cada ejecución del algoritmo debe registrar versión, fecha y hora, tiempo de procesamiento, cantidad de candidatos evaluados y si fue exitosa, para auditar el módulo.

## Visualizar oferta turística

- RF048 - Visualizar oferta turística (buscar servicio turístico): El sistema debe permitir a los Turistas buscar servicios turísticos mediante palabras clave y filtrar los resultados por tipo de servicio, ubicación, precio y calificación. La búsqueda debe ser insensible a mayúsculas y minúsculas y presentar los resultados paginados de 10 en 10.
- RF049 - Visualizar oferta turística (ver detalles de oferta): El sistema debe presentar al Turista la información completa de un servicio seleccionado, incluyendo descripción, galería de fotos, ubicación, horarios disponibles y valoraciones recibidas.
- RF050 - Visualizar oferta turística (calificar oferta turística): El sistema debe permitir a los Turistas asignar una puntuación de 1 a 5 estrellas y redactar una reseña o comentario sobre los servicios turísticos consultados.
- RF051 - Visualizar oferta turística (listar oferta turística): El sistema debe permitir al Turista visualizar el listado de las ofertas turísticas publicadas sin necesidad de ingresar criterios de búsqueda, con resultados paginados de 10 en 10.
- RF052 - Visualizar oferta turística (filtrar por lengua de señas): El sistema debe permitir a los Turistas buscar guías turísticos o establecimientos que cuenten con personal capacitado en Lengua de Señas.
- RF053 - Visualizar oferta turística (restricciones dietéticas estrictas): El sistema debe permitir filtrar restaurantes certificadamente aptos para celíacos, veganos estrictos, kosher y halal, evitando contaminación cruzada.
- RF054 - Visualizar oferta turística (calificación promedio): El sistema debe calcular y mostrar la calificación promedio de cada servicio a partir de todas sus valoraciones, y actualizarla cada vez que se registre una nueva.
- RF055 - Visualizar oferta turística (calificar solo con reserva completada): Solo el Turista que tenga una reserva COMPLETADA de un servicio puede calificarlo, y solo puede hacerlo una vez por reserva.

## Gestionar reportes

- RF056 - Gestionar reportes (exportar reportes): El sistema debe permitir al Administrador exportar los reportes de demanda, reservas e indicadores estadísticos en formatos estándar (PDF y CSV) para su análisis externo.
- RF057 - Gestionar reportes (monitorear flujo): El sistema debe permitir al Administrador monitorear el flujo de reservas y los niveles de concurrencia por servicio, zona o fecha.
- RF058 - Gestionar reportes (monitorear algoritmo de recomendación): El sistema debe permitir al Administrador monitorear el comportamiento del módulo de IA, verificando que las recomendaciones generadas sean explicables e imparciales, sin favorecer sistemáticamente a ningún operador por razones comerciales. 
- RF059 - Gestionar reportes (registro de flujo diario): El sistema debe registrar cada día, por zona y fecha, el número de visitantes y de reservas, para alimentar los indicadores.
- RF060 - Gestionar reportes (metadatos del reporte): Cada reporte generado debe guardar su tipo (estadísticas, flujo o algoritmo), el período analizado, el Administrador que lo generó y la fecha de generación.

## Administrar usuarios

- RF061 - Administrar usuarios (buscar usuario): El sistema debe permitir al Administrador buscar usuarios registrados ingresando criterios como nombre, correo electrónico o rol. Los resultados deben mostrarse en una lista paginada con la información básica y el estado actual de la cuenta.
- RF062 - Administrar usuarios (asignar rol y estado): El sistema debe permitir al Administrador editar la información de los usuarios, modificar los roles asignados (Turista, Prestador de Servicio, Administrador) y activar o desactivar cuentas dentro de la plataforma.
- RF063 - Administrar usuarios (editar usuario): El sistema debe permitir al Administrador eliminar fotografías o cualquier otro tipo de contenido específico si detecta que infringen las normas de la plataforma. 
- RF064 - Administrar usuarios (bitácora administrativa): El sistema debe registrar cada acción de un Administrador sobre las cuentas (cambio de rol, activación, desactivación o edición), con fecha, hora y motivo, para fines de auditoría.

## Pendientes de revisión

Este grupo reúne los requisitos que tienen signos de interrogación o que aún no están en el diagrama. Se dejaron con su redacción original para que se decidan antes de integrarlos.

- RF018 - Consultar recomendaciones (???): El sistema debe integrarse de forma desacoplada con un módulo de Inteligencia Artificial que genere recomendaciones personalizadas de atractivos y servicios turísticos de menor afluencia en Santa Marta, orientadas a descentralizar la demanda en temporadas altas. El modelo de IA debe ser explicable (informando al usuario los criterios o variables tomadas en cuenta para la sugerencia) e imparcial, garantizando un trato equitativo sin favorecer sistemáticamente a ningún operador por razones comerciales.
- RF019 - Gestionar reportes (generar y visualizar ???): El sistema debe permitir al Administrador generar y visualizar reportes con indicadores estadísticos sobre demanda turística, servicios más cotizados, flujo de reservas y tendencias de concurrencia.
- RF020 - Administrar usuarios (buscar usuario, ver información): El sistema debe permitir al Administrador consultar el perfil detallado y el historial de actividad de cualquier usuario antes de editar sus datos, asignarle un nuevo rol o proceder con la desactivación de su cuenta. (REVISAR O COMPLETAR EN DIAGRAMA)
- RF022 - Gestionar oferta turística (???): El sistema debe permitir al Prestador de Servicio fijar la ubicación exacta de su servicio mediante coordenadas o mapas interactivos, permitiendo que el Turista visualice la ruta o mapa de llegada. (REVISAR O COMPLETAR EN DIAGRAMA)
- RF023 - Consultar recomendaciones (personalizar preferencias??): El sistema debe permitir al Turista filtrar sugerencias de itinerarios y actividades según sus intereses específicos (gastronomía, ecoturismo, cultura) y restricciones particulares (presupuesto, accesibilidad reducida, horarios disponibles).
- RF024 - Gestionar reserva (???): El sistema debe guardar una bitácora del historial de cada reserva, registrando la fecha, hora y usuario responsable de cada cambio de estado (creación, confirmación, rechazo o cancelación).
- RF027 - Gestionar reserva (???): El sistema debe permitir al Turista consultar el historial completo de sus reservas realizadas, clasificándolas en estados: activas, completadas, rechazadas y canceladas.
- RF028 - Gestionar usuario (???): El sistema debe permitir al Administrador verificar y validar la documentación o registro legal de los prestadores de servicio antes de que sus ofertas sean públicas en la plataforma.
- RF029 - Gestionar ??? (???): El sistema debe permitir a los Prestadores de Servicio emitir una respuesta pública a las calificaciones y reseñas recibidas en sus respectivos servicios.
- RF030 - Gestionar reserva (???): El sistema debe permitir a los Prestadores de Servicio establecer y mostrar las condiciones y tiempos límites bajo los cuales un Turista puede cancelar una reserva sin penalización.
- RF031 - Gestionar (???): El sistema debe permitir a los Prestadores de Servicio configurar diferentes esquemas de precios y tarifas según la temporada (alta, media, baja) o días específicos de la semana.
- RF032 - Gestionar reportes (???): El sistema debe permitir al Administrador emitir avisos en tiempo real sobre cierres preventivos de zonas naturales (como el Parque Tayrona), alertas meteorológicas o restricciones temporales en playas. (Posible descarte, aunque no está mal la idea)
- RF033 - NO EXISTE Creación de itinerarios personalizados: El sistema debe permitir a los Turistas organizar y agrupar diferentes servicios reservados (alojamiento, restaurante y guía) en una agenda o itinerario diario dentro de su perfil.
- RF034 - NO EXISTE Identificación de sellos de sostenibilidad: El sistema debe permitir a los Prestadores de Servicio registrar sus prácticas de turismo responsable (como uso de energías limpias o guías locales), asignándoles una insignia sostenible que los Turistas puedan usar como criterio de filtrado.
- RF035 - NO EXISTE Gestión de promociones en temporada baja: El sistema debe permitir a los Prestadores de Servicio generar códigos de descuento o tarifas promocionales para incentivar la llegada de visitantes durante fechas de menor concurrencia.
- RF036 - NO EXISTE Carga y verificación de comprobantes de pago: El sistema debe permitir al Turista adjuntar comprobantes de pago o transferencia para que el Prestador de Servicio valide manualmente la transacción antes de confirmar la reserva. (Pensar sobre una pasarela de pagos)
- RF037 - NO EXISTE Canal de mensajería o consulta previa: El sistema debe permitir al Turista enviar preguntas directas al Prestador de Servicio sobre detalles específicos de la oferta antes de formalizar una reserva.
- RF038 - Gestionar cuenta (cambiar contraseña): El sistema debe permitir a los usuarios autenticados cambiar su contraseña desde la edición de su perfil, solicitando la contraseña actual y la nueva contraseña, la cual se almacenará cifrada mediante BCrypt. (¿Usamos eso?)
- RF045 - Recompensas de cumpleaños (??? no está en casos de uso): El sistema debe enviar automáticamente un cupón de descuento o promoción especial al Turista durante el mes de su cumpleaños. (Cupón de cumpleaños / Para reajuste)
- RF048 - Gestionar reserva (cross-selling) (???): El sistema debe permitir a los Prestadores ofrecer servicios adicionales opcionales (ej. desayuno, transporte al aeropuerto) durante el proceso de reserva del Turista. (Posible descarte)
- RF049 - Reglas de precios dinámicos (???): El sistema debe permitir al Prestador configurar reglas para que los precios aumenten o disminuyan automáticamente según el porcentaje de ocupación o cercanía a la fecha. (Buena idea / Para reajuste)
- RF050 - Listas de espera (waitlists) (NO ESTÁ): El sistema debe permitir a los Turistas anotarse en una lista de espera para fechas agotadas, notificándoles automáticamente si se libera un cupo.
- RF051 - Personalización de metaetiquetas SEO (NO ESTÁ): El sistema debe permitir al Prestador editar las descripciones cortas y palabras clave (SEO) de su página pública para mejorar su posicionamiento en buscadores.
- RF052 - Compensación voluntaria de huella (NO ESTÁ): El sistema debe permitir al Turista agregar una donación económica opcional al total de su pago, destinada a ONGs locales para compensar su huella de carbono. (Para revisión / ¿Se puede tomar como impuesto?)
- RF053 - Filtro de cero residuos (NO ESTÁ): El sistema debe incluir un filtro de búsqueda específico para encontrar Prestadores certificados en políticas de cero plásticos y reciclaje integral.
- RF054 - Compromiso de bienestar animal (NO ESTÁ): El sistema debe requerir que los Prestadores de servicios con fauna (ej. avistamiento de aves, acuarios) firmen digitalmente y muestren una política de no maltrato animal.
- RF055 - Pronóstico del clima integrado (NO ESTÁ): El sistema debe mostrar el pronóstico meteorológico actualizado para las fechas y ubicación específica de la reserva en el panel del Turista. (Dudable, mas no descartable)
- RF056 - Gestionar oferta turística (parqueo) (???): El sistema debe requerir al Prestador especificar las opciones de parqueo (propio, público cercano, costo) y permitir filtrar servicios por esta característica.
- RF057 - Contactos de emergencia locales (NO ESTÁ EN CASOS DE USO): El sistema debe mostrar un botón persistente durante las fechas de viaje con los números de policía, turismo y hospitales cercanos al área de reserva. (No es mala idea / Para reajuste)
- RF058 - Modo de alto contraste (NO ESTÁ EN CASOS DE USO): El sistema debe incluir un interruptor (toggle) en la interfaz para activar colores de alto contraste que faciliten la lectura a personas con discapacidad visual.
- RF059 - Búsqueda por voz (NO ESTÁ): El sistema debe integrar un botón de micrófono en la barra de búsqueda para que los Turistas dicten sus consultas en lugar de escribirlas. (Aprobado)
- RF063 - Sistema de tickets de soporte PQRS (NO ESTÁ): El sistema debe permitir a Turistas y Prestadores abrir tickets formales de Peticiones, Quejas, Reclamos o Sugerencias dirigidos a la Administración. (Aprobado / No está mala la idea)
- RF065 - Gestión de preguntas frecuentes FAQ (NO ESTÁ): El sistema debe permitir al Administrador crear, editar y eliminar preguntas y respuestas frecuentes en la sección de Ayuda de la plataforma.
- RF066 - Historial de búsquedas recientes (NO ESTÁ, más bien sería autoguardado): El sistema debe guardar temporalmente, en el almacenamiento local del navegador, las últimas 3 búsquedas del Turista para sugerirlas al hacer clic en la barra de búsqueda.
- RF067 - Indicador de "Nuevo Servicio" (NO ESTÁ): El sistema debe mostrar una etiqueta visual de "Nuevo" en la tarjeta de aquellos servicios publicados en los últimos 30 días.
- RF068 - Motivo de cancelación de reserva (NO ESTÁ): El sistema debe solicitar al Turista o Prestador que seleccione un motivo de una lista desplegable cuando decida cancelar una reserva activa.
- RF069 - Vista previa del servicio (NO ESTÁ): El sistema debe permitir al Prestador visualizar cómo verá el Turista su servicio publicado antes de guardarlo definitivamente.
- RF070 - Filtro de estrellas en reseñas (NO ESTÁ): El sistema debe permitir al Turista hacer clic en una puntuación (ej. 5 estrellas) para ver únicamente las reseñas con esa calificación.
- RF071 - Consejos de viaje sostenible (NO ESTÁ): El sistema debe adjuntar automáticamente un bloque de texto estático con 3 consejos básicos de turismo responsable (ej. no arrojar basura, cuidar el agua) al final de la información de la reserva.
