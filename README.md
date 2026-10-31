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
