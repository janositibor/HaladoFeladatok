## Fényképgyűjtemény

Ebben a feladatban a Fortepan fénykép adatbázist modellezzük le egy
alkalmazás segítségével.

A feladatban egy már működő, de még fejlesztésre szoruló projektet találsz.
A `pom.xml`-ben is már fel van véve minden, amire szükséged lesz.
Adatbázis gyanánt most még a repository osztályban tárolt lista szolgál.
A következőket valósítsd meg:

### Validáció és hibakezelés

* Létrehozáskor a leírás nem lehet üres vagy null.
* Létrehozáskor az évszám nem lehet kisebb 1800-nál.
* Módosításkor a beérkező plusz információ nem lehet üres vagy null.
* Ha elbukik a validáció, a HTTP-válaszban a 400-as státuszkód szerepeljen!
* A létrehozó metódusok egy új fénykép sikeres létrehozásakor térjenek vissza
  201-es státuszkóddal!
* Az `id` alapján keresést, módosítást és törlést végző metódusok sikertelen
  keresés esetén térjenek vissza 404-es státuszkóddal!
* A törlést végző metódus sikeres törlés esetén térjen vissza 204-es státuszkóddal!

Mindezek helyes létrehozását a `ValidationIT` osztályban lévő tesztesetekkel
ellenőrizheted.

### Integrációs tesztelés

* Írd meg a `FortepanControllerIT` osztály teszteseteit az ott található leírások alapján!