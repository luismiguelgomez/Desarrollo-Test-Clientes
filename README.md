## Recomendaciones antes de iniciar

Para ejecutar correctamente este proyecto, se recomienda utilizar **IntelliJ IDEA Community 2024.3.3** o la versi�n **Profesional**. Esto garantizar� compatibilidad con las herramientas y configuraciones necesarias para el desarrollo.

Para ejecutar la aplicaci�n, dir�gete a la siguiente ruta en tu proyecto:

```
prueba_tecnica\applications\app-service\src\main\java\co\com\bancolombia\MainApplication.java
```

Una vez all�, haz clic en el bot�n verde que aparece en `MainApplication` para ejecutar la aplicaci�n.

![Ejecuci�n en IntelliJ IDEA](\ejecucionproyecto.png)

# Proyecto Base Implementando Clean Architecture

## Antes de Iniciar

Empezaremos por explicar los diferentes componentes del proyecto y partiremos de los componentes externos, continuando con los componentes core de negocio (dominio) y por �ltimo el inicio y configuraci�n de la aplicaci�n.

Lee el art�culo [Clean Architecture � Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Es el m�dulo m�s interno de la arquitectura, pertenece a la capa del dominio y encapsula la l�gica y reglas del negocio mediante modelos y entidades del dominio.

## Usecases

Este m�dulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define l�gica de aplicaci�n y reacciona a las invocaciones desde el m�dulo de entry points, orquestando los flujos hacia el m�dulo de entities.

## Infrastructure

### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no est�n arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos
gen�ricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan
basadas en el patr�n de dise�o [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

Estas clases no pueden existir solas y deben heredarse sus comportamientos en los **Driven Adapters**.

### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios REST,
SOAP, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicaci�n o el inicio de los flujos de negocio.

## Application

Este m�dulo es el m�s externo de la arquitectura, es el encargado de ensamblar los distintos m�dulos, resolver las dependencias y crear los beans de los casos de uso (**UseCases**) de forma autom�tica, inyectando en �stos instancias concretas de las dependencias declaradas. Adem�s, inicia la aplicaci�n (es el �nico m�dulo del proyecto donde encontraremos la funci�n `public static void main(String[] args)`).

**Los beans de los casos de uso se disponibilizan autom�ticamente gracias a un `@ComponentScan` ubicado en esta capa.**

