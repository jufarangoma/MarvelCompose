# MarvelWiki

Esta es una app de android desarrollada con compose para visualizar una wiki de personajes de marvel, esta app perimite:

- Buscar personajes de marvel
- Ver los comics en los que ha participado un personaje
- ver un detalle del comic

## Estructura del proyecto

- data: Todas las implementaciones a los repositorios, objetos DTO y endpoints
- domain: Entidades, repositorios y excepciones de dominio
- presentation: Actividad, fragmentos, adaptadores y viewmodels
- di: Modules de inyección

## Librerias utilizadas

- Navigation Compose
- Coroutines
- Retrofit2
- Hilt
- Mockk
- Gson
- Coil

## Arquitectura utilizada

<img src="https://github.com/jufarangoma/CropBitmapFromShape/blob/master/MarvelWiki.png"/>

Arriba se muestra el modelo que use para la arquitectura de la app, los componentes que se
encuentran con linea cortada no fueron incluidos en la prueba dado que no fueron necesarios, sin
embargo los incluí en el diseño ya que son componentes recurrentes con los que he trabajado.

- La capa de Presentación contiene los elementos visuales que se usaron en la app, además de los
  viewmodels
  encargados de mantener los datos que se requieran. En esta capa use el patron de arquitectura
  MVVM.

- La capa de Dominio contiene toda la logica de negocio, para esta app no fue necesario implementar
  casos de uso ya que la mayoria de logica fue implementada en las entidades

- La capa de Datos contiene todas las llamadas al Api de Mercado Libre, los modelos DTO y
  las implementaciones del repositorio además contiene el archivo local de 4 superheroes

## Flujo de datos

Para el manejo asincrono de las peticiones use corrutinas y flow