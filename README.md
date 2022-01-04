## RICKY AND MORTY APP

![](https://github.com/txoksue/rickyandmorty-app/blob/master/screenshots/rick_and_morty_background.png)

La aplicación se compone de 4 pantallas:

- Splash

- Lista de personajes

- Listado de favoritos

- Detalle de personaje

![](https://github.com/txoksue/rickyandmorty-app/blob/master/screenshots/splash.png)
![](https://github.com/txoksue/rickyandmorty-app/blob/master/screenshots/characters_list.png)
![](https://github.com/txoksue/rickyandmorty-app/blob/master/screenshots/favorites_list.png)
![](https://github.com/txoksue/rickyandmorty-app/blob/master/screenshots/character_detail.png)

## Arquitectura

* **MVVM, Single Activity, Android Architecture Components**: Activity/Fragment -> ViewModel -> Repository.

* **Patrón Repository con Clean Architecture** para acceder a la API de Marvel.

![](https://github.com/txoksue/rickyandmorty-app/blob/master/screenshots/repository_architecture.png)
![](https://github.com/txoksue/rickyandmorty-app/blob/master/screenshots/clean-architecture-own-layers.png)

* **ROOM** para gestión de favoritos en una base de datos local.

* **Hilt** para la inyección de dependencias.

* **Retofit + Gson** para hacer llamadas a servicios, usando **Sealed Classes** para manejar las distintas respuestas.
    
* **Gidle** para manejar imágenes.

* **Lottie** para la carga de animaciones.
    
* **Unit test y UI test con Espresso**




