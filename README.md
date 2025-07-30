# Members Project
Ett REST API byggt i Spring Boot för hantering av medlemmar med både API-endpoints och webbgränssnitt via Thymeleaf.

## Tekniker
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- H2 in-memory database
- Thymeleaf
- REST API

## Funktioner
- Skapa, läsa, uppdatera och ta bort medlemmar (CRUD)
- Anslutning till H2 in-memory databas
- Thymeleaf-baserat webbgränssnitt (delvis)
- API-endpoints för integration

## Starta projektet
1. Klona repo:t
2. Kör applikationen via `mvn spring-boot:run`
3. API finns på `http://localhost:8080`
4. Webbgränssnitt tillgängligt på `http://localhost:8080/members`
5. H2 Console finns på `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)
