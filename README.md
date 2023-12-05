# FrgDado

Proyecto de ejemplo Android + Java que ejemplifica el uso de un Fragment tratado como un componente independiente, reutilizable y configurable.

## Objetivo

El objetivo de este proyecto es ejemplificar el uso de un Fragment como un componente independiente, reutilizable y que puede ser utilizado en diferentes actividades.

## Descripción

El proyecto consiste en el desarrollo de un fragmento que represente un dado de las caras que se desee. El fragmento debe ser configurable en cuanto al número de caras que se desee, y debe permitir ser reutilizado en cualquier aplicación que lo necesite.

El dado puede ser lanzado mediante un botón y debe tener un modo debug que nos permita ver el resultado del lanzamiento desde el propio fragmento, así como seleccionar el resultado desde un spinner para hacer pruebas.

En este proyecto se utiliza este Fragmento para crear un juego de dados en el que se lanzan dos dados de un mismo número de caras y se gana la partida cuándo tras el lanzamiento del primero obtenemos el mismo resultado en el segundo.

En la actividad principal se puede reiniciar la partida, lanzar los dados y ver el resultado del lanzamiento. El modo debug está activado por defecto.

Al finalizar la partida existe otra actividad que nos permite ver un historial de partidas ordenado por las que menos intentos llevaron a las que más para esa dificultad concreta (que se define según el número de caras de los dados).

Es posible también borrar dicho historial en la actividad de historial, todos estos datos se almancenan en una pequeña base de datos SQLite.

Temas tratados en el ejemplo:

- Fragments como componentes independientes y reutilizables.
- Comunicación entre Fragments y Activities.
- Fragments y su ciclo de vida.
- Fragments y su estado.
- Base de datos SQLite.
- Uso de un Spinner.
- Listeners.
- ListView.
- Adapters.