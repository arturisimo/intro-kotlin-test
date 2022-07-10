# Test de integración con kotlin

**Mock de servicios web**

Spring Boot ([SpringMockK](https://github.com/Ninja-Squad/springmockk)) ofrece herramientas de testing, se integra con diversas librerías y ofrece algunas funcionalidades propias

* Gestión de mocks con [MockK](https://mockk.io/) mocking library for Kotlin
* Mocks del servidor web con MockMvc
* Tests de acceso a bases de datos
* Tests de integración

Se pueden simular peticiones web sin ejecutar el servidor web, existe un cliente Http mock **MockMvc** que se conecta directamente al servidor web mock, Se utilizan **plugins** de JUnit para controlar el ciclo de vida de estos elementos de forma muy sencilla

Mock de servicios La librería de testing de Spring facilita la inyección de dobles de esas dependencias **@MockBean**

	@MockkBean
    private lateinit var service: MessageService


**Uso de TestRestTemplate**

Uso de cliente web con TestRestTemplate

    val entity: ResponseEntity<String> = template.getForEntity("/hello")


**H2**

[h2](https://www.h2database.com/html/main.html) Java SQL database. Para activar esa consola:

    spring.h2.console.enabled=true

http://127.0.0.1:8080/h2-console

Configuración para H2 con persitencia en un fichero data/testdb.mv.db

    spring.datasource.driver-class-name=org.h2.Driver
    spring.datasource.url=jdbc:h2:file:./data/testdb
    spring.datasource.username=sa
    spring.datasource.password=password


**+ info**

[the second episode of Spring Time in Kotlin series](https://www.youtube.com/watch?v=0jWo3o7r-W4) at the official [Kotlin YouTube channel](https://www.youtube.com/channel/UCP7uiEZIqci43m22KDl0sNw)
