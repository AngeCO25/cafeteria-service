# Cafetería service

## Grupo: 12

Es un proyecto con microservicio de pedidos para una cafeteria. Permite consultar el menú de productos y crear pedidos calculando el total de la compra automaticamente.

## Equipo:
1. Ángela Carrasco - en GitHub:AngeCO25 
2. Iván Huentemilla - en GitHub:Naglock

## Convenciones de Commits
Usamos conventional commits que quiere decir que cada mensaje empieza con el tipo de cambio que se realizó.

- **feat:** Se usa para agregar algo nuevo al proyecto(en este caso el endpoint de productos disponibles y módulo ventas).
- **fix:** Se usa cuando se corrige un error.
- **chore:** Se usa para tareas que no son una funcionalidad del microservicio, si no que mantenimiento del proyecto(configuracion del workflow en este caso)

## Ramas y flujos de merge
1. main: rama principal, es el codigo final.
2. develop: rama donde se reaalizan los cambios antes de pasar a main.
3. feature/ModuloDisponibles: Agrega el endpoint que muestra solo los productos disponibles para pedir.
4. feature/ModuloVenta: Agrega el modelo de datos para resgitrar las ventas.
5. hotfix/CorregirCalculo: Corrige el error en el cálculo del total de un pedido. 

## Naming de ramas
- **Formato:** PascalCase
- **Ejemplo:** feature/ModuloVenta

## Estructura de Carpetas
El proyecto sigue la arquitectura estándar de Spring Boot para mantener el código ordenado y separado por responsabilidades:
- `src/main/java/.../model/`: Entidades y representación de datos (ej. Producto, Pedido, Venta).
- `src/main/java/.../repository/`: Interfaces para la persistencia de datos en la base de datos.
- `src/main/java/.../service/`: Lógica de negocio y validaciones.
- `src/main/java/.../controller/`: Endpoints REST que exponen la API.
- `.github/workflows/`: Archivos YAML de configuración para la automatización CI/CD con GitHub Actions.

## Justificación de elección modelo de ramificación: GitFlow
Elegimos Gitflow porque el enunciado pide crear ramas main, develop, feature y hotfix que son ramas de GitFlow.
Además, la rama develop nos permite revisar el código que modificó alguno de los integrantes antes de que llegue a main.Con hotfix dejamos en el historial que fue una corrección urgente.
No elegimos TBD porque ese modelo integra todos los cambios directamente a main, sin pasar por una rama intermedia antes para su revisión. Eso funcionaria si el proyecto tiene muchas pruebas automaticas que avisan de inmediato si se rompió algo. El nuestro solo tiene 3 pruebas(las dos de PedidosServiceTest.java y la de CafeteriaApplicationTests.java), asi que preferimos usar rama develop como filtro antes de que llegue a main.

## Estrategia de revisión 
Cada Pull request lo revisa el otro integrante del equipo antes de aprobarlo. Si falta algo, se usa 'Request changes' con un comentario explicando que falta. Cuando esta listo, se aprueba y se mezcla con develop o main segun corresponda.

## GitHub Actions(Workflows)
Automatizar la ejecucion de los test nos permite corroborar si hay errores al instante que se sube un cambio, sin depender que alguien lo haga de forma manual.

Cada integrante del equipo configuro un workflow
- **Ángela:** Corre los test automaticos cada vez que hace push a develop.
- **Iván:** Compila y ejecuta los test automaticos cada vez que se abre un Pull Request hacia la rama main.

## Uso de IA
Usamos Claude de las siguientes formas:
-Generó la base del código del microservicio a partir de la idea de negocio que elegimos con sus atributos. Nosotros probamos, ejecutamos y ajustamos.
-Nos guió en el uso de Git y GitHub.
-Nos aclaro conceptos de lo visto en clases.

## Conclusiones
Iván: Al estar acostumbrado a trabajar de forma individual mis proyecto utilizando Trunk-based Development, pasar a GitFlow en este proyecto me mostro el valor de separar los entornos al trabajar en equipo. La dinámica de los Pull Requests fue muy efectiva, recibir comentarios y solicitudes de cambio antes de integrar el código me permitió ver en la práctica cómo este flujo actúa como un filtro de calidad. Respecto al CI/CD, aunque ya tenía un acercamiento previo, reafirmé la utilidad de automatizar la integración continua para agilizar procesos. Finalmente la simulación del hotfix me ayudó a comprender la importancia de tener un mecanismo de emergencia para corregir errores críticos directamente en producción, sin interrumpir el desarrollo de las funcionaledes que siguen avanzando en paralelo.

Ángela: Esta experiencia de aprendizaje me ayudó a comprender como se trabaja en equipo en el desarrollo de software de forma efectiva. Esta es mi primera interacción con github ya que antes solo lo habia instalado pero no sabia usarlo.
Al principio me costó recordar los conceptos y los pasos: qué comando ejecutar primero y por qué. Me parece que con práctica y repetición se va a volver algo sencillo.
Es fuera de lo normal para mi trabajar con un compañero que no conozco ni veo, pero creo que tuvimos un buen resultado. Me parece interesante como el orden permite que los demás del equipo entiendan en qué y cómo estamos trabajando, y si no se siguen los pasos de estas estructuras, al final github terminaria viendose como algo enredado. Depende del equipo querer hacer las cosas bien y usar las herramientas de forma adecuada. 
La automatización de tareas con workflows es fundamental para hacer el trabajo más rápido y menos tedioso.
A futuro esto va a ser muy necesario para trabajar en quipo sin perderse entre las versiones, y para tener respaldo de todo lo que se ha hecho y de quien lo hizo. Me gustaría aprender más detalles de las herramientas para aprovecharlas al máximo y obetener resultados óptimos.
