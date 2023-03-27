# Test de integración con kotlin. Test Containers

**Acceso a [postgreSQL](https://www.postgresql.org/)**

Añadir dependencia 

    implementation("org.postgresql:postgresql:42.2.14")

Configuración

    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
    spring.datasource.username=postgres
    spring.datasource.password=root

Docker

    $ docker-compose up -d

**[TestContainers](https://www.testcontainers.org/)**

    @Testcontainers
    class TestContainerTest(

        val container = PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:13-alpine"))
                        .apply({
                            withDatabaseName("db")
                            withUsername("postgres")
                            withPassword("password")
                            withInitScript("sql/schema.sql")
                        })


**+info**

[the 5th episode of Spring Time in Kotlin series](https://www.youtube.com/watch?v=0jWo3o7r-W4) at the official [Kotlin YouTube channel](https://www.youtube.com/kotlin)

