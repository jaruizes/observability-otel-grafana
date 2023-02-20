# Observabilidad todo en uno
Este repositorio contiene un proyecto de ejemplo para jugar con herramientas de observabilidad y OpenTelemetry.



# Caso de uso

El caso de uso es el siguiente:

![caso_de_uso](doc/pictures/caso_de_uso.png)

En la imagen, podemos ver que el servicio A es el punto de entrada al sistema. Expone un API Rest, con una operación POST que recibe un valor inicial. A partir de ese valor inicial, cada servicio suma 10. Por lo tanto, si empezamos con un valor inicial de 10, al finalizar las ejecuciones, en el topic “topic-d” veremos un valor de 40.

Como vemos, hay cuatro servicios, heterogéneos, que interaccionan entre sí. Podemos encontrar un servicio implementado con NodeJS, otro con Spring Boot y otros dos con Quarkus. Además, vemos que existen tanto llamadas síncronas como llamadas asíncronas. Saber lo que está pasando, en este tipo de sistemas, es muy complicado 
si no se dispone de herramientas como OpenTelemetry y Grafana



# Jugando con OpenTelemetry

Para levantar el entorno se debe ejecutar el siguiente comando:

```bash
sh build.sh
```

El script ya lanza una serie de peticiones al API del servicio A para ya disponer de información en el dashboard. De todas formas, si queremos lanzar más carga, ejecutamos:

```bash
sh load.sh <numero de peticiones>
```

reemplazando <numero de peticiones> por el valor que queramos. 

Para acceder al dashboard de grafana, accedemos a http://localhost:3000/d/metrics/dashboard-post?orgId=1&refresh=10s



# Apagando el entorno

Para apagar el entorno, simplemente tendremos que ejecutar:

```bash
sh destroy.sh
```



