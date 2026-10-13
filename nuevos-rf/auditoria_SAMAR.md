# Auditoría: diagramas SAMAR vs. requisitos funcionales

Se revisaron la versión 2 del md (requisitos_funcionales_SAMAR.md) y la nueva versión de los diagramas (Casos de uso, Diagrama de clases, Diagrama Conceptual DB y Diagrama Entidad Relación). Los diagramas de paquetes y de despliegue están vacíos. El resultado es requisitos_funcionales_SAMAR_v3.md, con 100 requisitos únicos.

## 1. Resumen

La versión 2 tenía 103 entradas, pero varios números aparecían dos veces con contenidos distintos. Por ejemplo, RF027 era "confirmar o rechazar" en el grupo de reservas y "historial de reservas" entre los pendientes. Se encontraron 32 números repetidos. Además, había tres estados vacíos (RF003 a RF005 sin texto) y dos casos de uso del diagrama no tenían requisito o no coincidían con él.

Después de la auditoría, se eliminaron o fusionaron 16 entradas y se agregaron 13 requisitos que faltaban según el diagrama. Así se llegó a 100. En la tabla de la sección 4 aparecen los cambios que modifican contenido.

## 2. Lo que coincide

Los siete casos de uso de nivel 1 del diagrama (Gestionar cuenta, Gestionar oferta turística, Gestionar reserva, Gestionar recomendaciones, Visualizar oferta turística, Gestionar reportes y Administrar usuarios) tienen grupo propio en el md. Todos los casos de uso de nivel 2 también tienen su requisito. El cambio de nombre de "Consultar recomendaciones" a "Gestionar recomendaciones" ya quedó aplicado, y "Editar Recomendación" pasó a "Ajustar criterios", igual que en RF069.

Los atributos del diagrama de clases también coinciden en lo esencial: los datos de documento, nacionalidad, país de residencia, fecha de nacimiento y discapacidad del Turista; los estados ACTIVO, INACTIVO y BLOQUEADO; las categorías de servicio; los atributos de hotel, restaurante y actividad; la disponibilidad con cupos; los criterios de búsqueda; el paquete con ítems; la solicitud y la ejecución del algoritmo; el reporte y el registro de flujo.

## 3. Lo que no coincide

### 3.1 Diagramas sin requisito por escrito (se agregaron)

El diagrama de clases tiene algunos atributos y relaciones que el md no cubría. Se agregaron como RF013 (último acceso), RF032 (activar o desactivar servicio, tomado del atributo Activa de Disponibilidad), RF035 (cupos disponibles calculados), RF047 (precio fijado al reservar, tomado de DetallesReserva), RF064 y RF065 (capacidad del hospedaje y edad mínima de la actividad), RF066 (fechas válidas, tomado de Disponibilidad), RF072 (configuración del algoritmo, tomado de ConfiguracionAlgoritmo), RF096 y RF099 (notificaciones y área responsable del Administrador, tomados de Administrador.areaResponsable), RF100 (notificación de desactivación, tomado del caso de uso "Desactivar Usuario"), RF037 (notificación al prestador, porque el diagrama de clases tiene una Notificacion con destinatario) y RF085 (ocultar ofertas no validadas, derivado de la validación de RF095).

### 3.2 Requisitos sin modelar en los diagramas

Estos requisitos están en el md, pero no existen en ningún diagrama. Se recomienda agregarlos a clases y a la base de datos antes de la entrega. Los más importantes son:

- Pagos: no hay clase ni tabla para pago o transacción (RF043, RF055), comprobante (RF052), cupón o código promocional (RF054), liquidación a prestadores (RF051), bitácora de reserva (RF057) ni lista de espera (RF058).
- Ubicación: RF016 y RF017 piden ubicación por servicio. En el diagrama de clases la ubicación está solo en PrestadorTuristico, y ServicioTuristico no tiene campo de ubicación.
- Actor de IA: en el caso de uso, el Turista aparece como quien genera la recomendación, y no hay un actor "Módulo de IA". La nota de la propia diagrama ("hace falta el módulo de IA") sigue pendiente. Conviene agregar ese actor.
- Actores de guía: RF016 menciona a los guías como un tipo de prestador por separado, pero RF022 dice que el guía pertenece a una agencia. Se dejó la versión de RF022, que coincide con el diagrama de clases (idiomasGuia está dentro de AgenciaTuristica). La redacción de RF016 se corrigió.

### 3.3 Contradicciones entre diagramas y md

- EstadoReserva no incluye RECHAZADA, pero RF038 y RF048 lo usan. Hay que agregarlo al enum del diagrama de clases y a la base de datos.
- TipoNotificacion incluye RESERVA_CERRADA, RESERVA_CANCELADA y RESERVA_PAUSADA, pero no CONFIRMADA ni RECHAZADA, que son las notificaciones que pide RF039. RESERVA_PAUSADA no aparece en ningún requisito.
- Recomendacion tiene el atributo esEditada, que contradice RF069: el Turista no puede modificar el contenido generado por el modelo. Conviene eliminar ese atributo o dejarlo fijo en falso.
- Calificacion no tiene relación con Reserva en el diagrama de clases, ni id_reserva en la tabla calificacion del ER. RF076 exige que la calificación venga de una reserva COMPLETADA, por lo que el modelo debe tener esa relación.
- TipoDieta no incluye kosher ni halal, pero RF079 los pide. Tampoco incluye la certificación de cocina segura.
- Disponibilidad tiene el campo fecha en el ER, pero no en el diagrama de clases. RF034 y RF066 dependen de esa fecha.
- Usuario no tiene campos para intentos fallidos ni bloqueo temporal (RF005), ni foto de perfil (RF008), ni en el ER ni en el diagrama de clases.
- Errores de tipo y de nombre: horaCheckIn, horaCheckOut y horaApertura están como LocalDateTime en la clase, pero como TIME en el ER. El atributo "paisRecidencia" tiene un error de ortografía (es paisResidencia). En el ER la moneda es CHAR, mientras que en la clase es String.
- El nombre del caso de uso "Notificar Novedad En Reserva" sigue sin cambiar. RF038 es "confirmar o rechazar" y RF039 es "notificar estado", así que conviene separar los dos casos de uso. Tampoco existe en el diagrama un caso de uso para "Notificar estado", que sí está en RF039.

## 4. Cambios aplicados al md

La tabla resume los cambios que modifican el contenido. Las demás entradas solo cambiaron de número.

| Entrada v2 | Acción | Resultado en v3 | Motivo |
|---|---|---|---|
| RF003, RF004, RF005 (registro hotel, restaurante, agencia) | Fusionadas | RF003 | Eran estados vacíos. Se completaron en una sola entrada de registro de prestador. |
| RF022 pendiente (ubicación exacta) | Eliminada | Cubierta por RF016 y RF017 | Repetía la ubicación de registro y edición. |
| RF048 pendiente (cross-selling) | Eliminada | — | Marcada como "posible descarte" y no aparece en el diagrama. |
| RF019 pendiente (generar reportes) | Fusionada | RF090 | Repetía generar y visualizar reportes. |
| RF023 pendiente (filtros de itinerarios) | Fusionada | RF068 | Repetía la solicitud con parámetros. |
| RF027 pendiente (historial de reservas) | Fusionada | RF041 | Repetía la consulta del estado de reserva. |
| RF032 pendiente (avisos en tiempo real) | Fusionada | RF062 | Se unió con el pronóstico del clima. |
| RF033 pendiente (itinerarios) | Fusionada | RF050 | Se unió con el paquete turístico. |
| RF035 pendiente (promociones baja temporada) y RF045 pendiente (cumpleaños) | Fusionadas | RF054 | Ambas son códigos de descuento. |
| RF036 pendiente (comprobantes de pago) | Fusionada | RF044 | Es la alternativa manual de validación. |
| RF049 pendiente (precios dinámicos) | Fusionada | RF027 | Es una regla de tarifas. |
| RF053 y RF054 pendientes (cero residuos y bienestar animal) | Fusionadas | RF033 | Son certificaciones de sostenibilidad, junto con el sello de RF034 de la v2. |
| RF068 pendiente (motivo de cancelación) | Fusionada | RF040 | Es un paso de la cancelación. |
| RF030 pendiente (condiciones de cancelación) | Modificada | RF042 | Se separó de la consulta de estado para que RF045 la pueda referenciar. |
| RF063 (editar usuario, con fotos) | Modificada | RF094 | Se aclaró que incluye datos del usuario y moderación de contenido. |
| RF024 pendiente (bitácora de reserva, número duplicado con RF024 de categorías) | Renumerada | RF057 | Número repetido. |
| RF066 pendiente (historial de búsquedas) | Modificada | RF083 | Se quitó la nota sobre autoguardado, que describía otro requisito. |
| RF001 | Modificada | RF001 | Se incluyó la validación de correo único y la selección de tipo de cuenta. |
| RF016 (servicio turístico) | Modificada | RF016 | Se quitó "Guías" como tipo de prestador independiente. |
| RF044 "Gestonar" (typo) | Corregida | RF069 | Se usa el nombre "Ajustar criterios" del diagrama. |
| RF046 (generar recomendación) | Modificada | RF068 | Se agregaron los parámetros de la clase SolicitudRecomendacion. |
| RF029 de reserva (cancelar) y RF034 (sellos) | Modificadas | RF040 y RF033 | Se agregaron el motivo de cancelación y las certificaciones. |

Las notas entre paréntesis del md (por ejemplo, "¿Usamos eso?", "Posible descarte" o "Aprobado") se quitaron de los requisitos. Esas decisiones deben quedar en la documentación del equipo y no en el requisito.

## 5. Decisiones pendientes

Algunos requisitos siguen sin aparecer en el diagrama y el equipo debe decidir si se quedan. Son la mensajería previa (RF059), la lista de espera (RF058), la donación por huella de carbono (RF060), los contactos de emergencia (RF063), el parqueo (RF028), el pronóstico del clima (RF062), el filtro de estrellas en reseñas (RF081), el historial de búsquedas (RF083) y la búsqueda por voz (RF084). Si se decide eliminar alguno, el total baja a menos de 100 y hay que reemplazarlo por otro requisito que sí esté en el diagrama.
