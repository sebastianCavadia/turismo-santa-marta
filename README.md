# Samar
Plataforma Digital para la Gestión Integrada y Sostenible del Turismo en Santa Marta.

* **Programa:** Ingeniería de Sistemas
* **Curso:** Arquitectura de Software (07011438) - 7mo Semestre
* **Periodo académico:** 2026-2
* **Duración del proyecto:** 6 meses / 24 semanas

## Equipo de Desarrollo (Samar)

| Estudiante                        | Código     | Rol Asignado                        |
|:----------------------------------|:-----------|:------------------------------------|
| Julio Alexander Amador Calderón   | 2022214024 | Arquitecto de software              |
| Valentina Bonilla Nigrinis        | 2022214021 | Responsable de validación y calidad |
| Iván Camilo Alarcón Moreno        | 2021214036 | Analista de requisitos              |
| Sergio Andrés Mosquera Molina     | 2022214001 | Diseñador de datos                  |
| Sebastián Mateo Cavadía Maldonado | 2022214068 | Desarrollador principal             |

---

## Descripción del Proyecto

Santa Marta es un destino turístico principal en el Caribe colombiano. Sin embargo, el crecimiento del turismo ha evidenciado problemáticas en la gestión de la información, la cual se encuentra actualmente fragmentada en múltiples plataformas independientes.

Este proyecto busca centralizar la información mediante una plataforma digital interoperable y escalable que resuelva problemáticas como:
* Concentración excesiva de turistas y congestión en atractivos sensibles.
* Baja visibilidad de pequeños y medianos prestadores de servicios.
* Falta de mecanismos integrados para consultar disponibilidad y realizar reservas.
* Baja confiabilidad del servicio durante temporadas altas.

## Condiciones de Desarrollo y Tecnologías

El ecosistema tecnológico ha sido seleccionado considerando el presupuesto y la necesidad de un sistema robusto:

* **Metodología:** **RUP (Rational Unified Process)**. Elegido por su enfoque iterativo, ideal para organizar el trabajo en 6 meses e identificar riesgos tempranos.
* **Lenguaje:** **Java**. Seleccionado por su estabilidad, escalabilidad y amplia documentación.
* **Framework:** **Spring**. Simplifica el desarrollo, la seguridad y la creación de servicios web.
* **Base de Datos:** **PostgreSQL**. Confiable, gratuita, relacional y flexible para manejar la variedad de datos de los distintos prestadores de servicios.

---

##  Atributos de Calidad

La arquitectura del sistema está orientada a satisfacer los siguientes atributos de calidad clave:

1. **Disponibilidad:** Uptime crítico del 99.99%, garantizando el servicio en temporadas de alta concurrencia.
2. **Escalabilidad:** Capacidad de soportar el crecimiento de usuarios, prestadores e integraciones futuras.
3. **Interoperabilidad:** Integración fluida con múltiples fuentes heterogéneas (Sistemas hoteleros, INDETUR, PNN, etc.).
4. **Seguridad:** Cumplimiento de la Ley 1581 de 2012 para el manejo de datos personales y comerciales.
5. **Rendimiento:** Tiempos de respuesta rápidos para búsquedas y reservas, incluso bajo estrés.
6. **Usabilidad:** Interfaz intuitiva y bilingüe, accesible para turistas y prestadores con poca experiencia tecnológica.

---

## Restricciones del Proyecto

* **Técnicas:** Dependencia de la disposición e infraestructura tecnológica de terceros para las integraciones.
* **Económicas:** Presupuesto inicial limitado de infraestructura de USD 20.000 y alcance amplio para 6 meses.
* **Socioambientales:** Obligación de promover el turismo sostenible, respetando los límites de carga del Parque Tayrona y la Sierra Nevada.
* **Normativas:** Alineación estricta con PNN, Corpamag, ICANH, Migración Colombia y directrices de las autoridades indígenas.
* **Éticas:** Los módulos de recomendación (IA) deben ser explicables, transparentes y no favorecer injustamente a ciertos operadores.

---

## Coordinación y Comunicación

El equipo se articula mediante los siguientes mecanismos:
* **WhatsApp:** Canal principal para delegación de roles, toma de decisiones y reporte de avances.
* **Teams:** Canal secundario destinado a la revisión conjunta de avances, almacenamiento centralizado de documentos formales y discusiones de planificación que requerían la participación sincrónica de todo el equipo.
* **Reuniones Presenciales:** Canal terciario para bloqueos rápidos y planificación a corto plazo.

*(Para más detalles sobre los requerimientos y el diseño de la base de datos, consulte la documentación adjunta del proyecto).*
