# projekt-zaliczeniowy-bz-pb
projekt-zaliczeniowy-bz-pb created by GitHub Classroom

Przedmiotem niniejszego projektu jest aplikacja mająca podobne funkcjonalności do popularnego serwisu lubimyczytac.pl. Aplikacja pozwala użytkownikom oceniać i komentować znajdujące się w bazie danych książki.

Każda ocena wystawiona użytkownika książce może zawierać ocenę w postaci wartości liczbowej i komentarza. Dodatkowo każda ocena posiada datę wystawienia. Ocena jest przypisywana zarówno do książki jak i do konta użytkownika, który ma później dostęp do wystawionych przez siebie ocen i może je przeglądać.

Użytkownik o uprawnieniach administratora może dodawać nowe książki do bazy danych, a także w razie potrzeby usuwać komentarze użytkowników i ich konta.

Projekt wykonany został w języku Java, z wykorzystaniem spring boot’a. 

Schemat bazy danych:


<img src="./src/main/resources/db-schema.svg">.

Celem zapewnienia bezpieczeństwa zastosowano spring-boot-security i skonfigurowano mechanizm JWT tokena. Token był zwracany w ciasteczkach użytkownikowi po poprawnym zalogowaniu i był ważny przez 1h.

W aplikacji zastosowano logowanie poprzez bibliotekę logback. Dokumentację w postaci pliku .yaml wygenerowano za pomocą biblioteki springdoc wykorzystującej openAPI Swaggera. Wykonano testy integracyjne wybranych metod kontrolerów restowych.
