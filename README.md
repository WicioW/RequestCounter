# Polish 
(English version below)

## Treść zadania

Zadanie rekrutacyjne backend

Stwórz prosty RESTowy serwis, który zwróci informacje:  
• Identyfikator  
• Login  
• Nazwa  
• Typ  
• Url do avatara  
• Data stworzenia  
• Obliczenia  
API serwisu powinno wyglądać jak poniżej:

`GET /users/{login}`

```json

{
"id": "...",
"login": "...",
"name": "…",
"type": "...",
"avatarUrl": "...",
"createdAt": "...",
"calculations": "..."
}
```

Serwis powinien pobrać dane z https://api.github.com/users/{login} (np.
https://api.github.com/users/octocat) i przekazać je w API. W polu calculations powinien być
zwrócony wynik działania `6 / liczba_followers * (2 + liczba_public_repos)`.

Serwis powinien zapisywać w bazie danych liczbę wywołań API dla każdego loginu.
Baza danych powinna zawierać dwie kolumny: LOGIN oraz REQUEST_COUNT. Dla każdego wywołania
usługi wartość REQUEST_COUNT powinna być zwiększana o jeden.

Serwis powinien być zaimplementowany w Java. Projekt powinien być możliwy do zbudowania za
pomocą Maven lub Gradle. Możesz wspierać się dowolnymi, łatwo dostępnymi technologiami (silniki
BD, biblioteki, frameworki).
Pamiętaj o zastosowaniu dobrych praktyk programowania.
Projekt umieść na dowolnym repozytorium i udostępnij nam link.

## Przyjęte założenia:

- Jeżeli api github zwróci nam błąd, to zwracamy ten sam błąd w naszym api. Chcemy przekazać dalej
  jak najdokładniejsze informacje, o tym co poszło nie tak
- Zakładam, że "calculations" oblicza jakiś score użytkownika, więc jeżeli nie ma followersów to nie
  ma sensu dzielić przez 0. Zwracam wartość `0` dla calculations

## Proponowane dalsze usprawnienia:

- dodanie retry i/lub circuit breaker do wywołań api githuba
- dodanie cache do wywołań api githuba
- aktualizacja wartości `REQUEST_COUNT` w bazie danych zrobiona asynchronicznie. np eventem

# English

## Task Description

Recruitment Backend Task

Create a simple RESTful service that will return information about:  
• Identifier  
• Login  
• Name  
• Type  
• Avatar URL  
• Creation date  
• Calculations  

The API of the service should look as follows:

`GET /users/{login}`

```json
{
"id": "...",
"login": "...",
"name": "...",
"type": "...",
"avatarUrl": "...",
"createdAt": "...",
"calculations": "..."
}
```

The service should retrieve data from https://api.github.com/users/{login} (
e.g., https://api.github.com/users/octocat) and provide it in the API response. The "calculations"
field should return the result of the operation `6 / number_of_followers * (2 +
number_of_public_repos)`.

The service should also record the number of API calls for each login in the database. The database
should have two columns: LOGIN and REQUEST_COUNT. For each service call, the REQUEST_COUNT value
should be incremented by one.

The service should be implemented in Java. The project should be buildable using Maven or Gradle.
You can use any readily available technologies (database engines, libraries, frameworks). Make sure
to follow good programming practices. Place the project in any repository and provide us with a
link.

## Assumptions Made:

- If the GitHub API returns an error, we should return the same error in our API. We want to convey
  as accurate information as possible about what went wrong.
- I assume that "calculations" calculate some user's score, so if there are no followers, it doesn't
  make sense to divide by 0. I return the value 0 for calculations in this case.

## Proposed Future Improvements:

- Add retry and/or circuit breaker for GitHub API calls.
- Implement caching for GitHub API calls.
- Update the value of REQUEST_COUNT in the database asynchronously, for example, using events.