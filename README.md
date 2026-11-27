# Evaluación Parcial 2: Pipeline CI/CD

**Grupo 12**
Esta evaluación fue desarrollada de forma individual por **Ángela Carrasco** (GitHub: AngeCO25).

En esta evaluación automaticé el ciclo de vida del microservicio de la EP1 con un pipeline CI/CD en GitHub Actions. Cada push a main ejecuta pruebas, análisis de seguridad y calidad, construye la imagen Docker y despliega el microservicio en AWS EC2.

## Estructura del proyecto
```
cafeteria-service/
├── .github/
│   ├── workflows/pipeline.yml   # Pipeline CI/CD
│   └── dependabot.yml           # Revision de dependencias
├── nginx/nginx.conf             # Balanceador de carga
├── src/                         # Codigo y pruebas JUnit
├── .snyk                        # Riesgo aceptado documentado
├── Dockerfile                   # Imagen multi stage
├── docker-compose.yml           # Orquestacion de contenedores
└── pom.xml                      # Dependencias Maven
```


## Pipeline
Se ejecuta con cada push a main. Si una etapa falla, las siguientes no se ejecutan.

| Etapa | Qué hace |
|---|---|
| 1. Pruebas | Ejecuta las pruebas unitarias JUnit |
| 2. Snyk | Revisa vulnerabilidades en las dependencias y bloquea si son altas o críticas |
| 3. SonarCloud | Analiza la calidad del código |
| 4. Docker | Construye la imagen etiquetada con el commit |
| 5. Despliegue | Despliega en AWS EC2 por SSH con Docker Compose y prueba que responda |

## Contenedores y orquestación
- **Dockerfile multi stage:** descarga dependencias, ejecuta pruebas, compila y deja una imagen final liviana que corre sin privilegios de administrador.
- **Docker Compose:** 2 réplicas del microservicio con límites de CPU y memoria, healthcheck y Nginx como balanceador en el puerto 8080.

## Seguridad
- **Dependabot** revisa las dependencias cada semana y propone actualizaciones.
- **Snyk** detectó 66 vulnerabilidades en Spring Boot 3.2.5 y bloqueó el despliegue. Lo corregí aceptando el pull request de Dependabot (Spring Boot 4.1.2) y actualizando Jackson a 3.1.8. Una vulnerabilidad sin arreglo disponible quedó documentada en `.snyk`, porque no afecta al microservicio.
- **Secretos** de GitHub para los tokens y el acceso a AWS. No se escriben en el código.

## Trazabilidad y calidad
- Cada despliegue queda registrado en Actions con su commit y autor.
- La imagen Docker se etiqueta con el commit que la originó.
- Las pruebas, Snyk y SonarCloud se ejecutan en cada cambio antes de desplegar.

## Cómo probarlo
1. Cambiar un precio en `src/main/resources/data.sql` y hacer push a main.
2. Esperar que el pipeline termine en verde en Actions.
3. Abrir `http://IP_EC2:8080/api/productos` y ver el cambio.

## Uso de IA
Usé Claude para entender los conceptos e interpretar errores del pipeline. 

## Reflexión individual
Esta evaluación me ayudó a comprender la importancia de utilizar pipelines CI/CD en el desarrollo de software, especialmente cuando se trabaja de manera colaborativa. Su objetivo es establecer una ruta de automatización de procesos ordenados donde cada etapa cumple una función antes de pasar a la siguiente, permitiendo reducir errores humanos, detectar problemas a tiempo y hacer que las entregas sean más rápidas y confiables.
También comprendí que herramientas como Github Actions, Docker, Snyk y SonarCloud no solo facilitan el trabajo, sino que ayudan a mantener un mayor control sobre la calidad y seguridad del software. Por ejemplo, el uso de secretos permite proteger información sensible, como contraseñas, tokens y accesos a servidores, evitando que queden expuestos en el código frente a posibles amenazas.
Otro aprendizaje fue entender que automatizar el despliegue no significa solamente publicar una aplicación si no que asegurarse de que haya pasado por pruebas y revisiones que permitan entregar un producto mas estable. 

---------------------------------------------------------------------------------

# Evaluación Parcial 3: Observalidad y entornos reales en DevOps

## Observabilidad y cumplimiento normativo

Para esta evaluación se extendió el pipeline agregando monitoreo de infraestructura y reforzando los controles de calidad y seguridad que ya existían.

### Monitoreo con CloudWatch
Se levantó una instancia EC2 dedicada al monitoreo y se instaló el CloudWatch Agent, que recolecta el uso de memoria RAM y almacenamiento (métricas que no vienen por defecto en EC2). El uso de CPU se obtiene directamente de las métricas estándar de EC2.

| Métrica | Origen | Qué mide |
|---|---|---|
| CPUUtilization | EC2 (estándar) | Uso de CPU |
| mem_used_percent | CloudWatch Agent | Uso de memoria RAM |
| disk_used_percent | CloudWatch Agent | Uso de almacenamiento |

Con estas métricas se armó un dashboard en CloudWatch con los tres gráficos, para revisar el estado del servidor en cualquier momento.

### Refuerzo del cumplimiento normativo
El pipeline ya contaba con Snyk y SonarCloud desde la EP2. Se revisó que ambos realmente detuvieran el pipeline ante una falla crítica:
- Snyk ya bloqueaba el despliegue ante vulnerabilidades altas o críticas.
- A SonarCloud se le agregó el parámetro `sonar.qualitygate.wait=true`, para que también falle si no se pasa el Quality Gate (antes solo reportaba el resultado, sin bloquear nada).

Como el build y el despliegue dependen de que ambos pasen, cualquier falla crítica de seguridad o calidad detiene el pipeline antes de llegar a producción.

## Reflexión individual
Con esta evaluación entendí que no basta con que el pipeline despliegue la aplicación, también hay que poder ver cómo está funcionando el servidor mientras está en producción. Para eso usé Amazon CloudWatch, que es un servicio de AWS que recolecta datos de la máquina (como el uso de CPU, memoria y disco) y los muestra en gráficos. Antes no tenía forma de saber si el servidor se estaba quedando sin recursos, y ahora puedo revisarlo en cualquier momento con el dashboard que armé.

También aprendí sobre el Quality Gate de SonarCloud, que es básicamente un filtro de calidad mínima que debe cumplir el código (por ejemplo, que no tenga muchos errores o código duplicado). Me di cuenta que SonarCloud estaba analizando el código, pero el pipeline seguía avanzando igual aunque el código no cumpliera esos mínimos. Tuve que agregar una configuración para que, si el Quality Gate falla, el pipeline se detenga ahí y no siga hasta el despliegue.

En general esta evaluación me sirvió para entender que no es solo tener las herramientas instaladas, si no que hay que asegurarse de que realmente estén haciendo su trabajo de frenar el proceso cuando algo anda mal.

