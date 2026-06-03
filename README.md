# Fog Carport

![Java Version](https://img.shields.io/badge/Java-21-blue.svg)
![Framework](https://img.shields.io/badge/Javalin-6.7.0-orange.svg)
![License](https://img.shields.io/badge/License-GPL--3.0-green.svg)

En webapplikation til online bestilling, beregning og administration af carporte for Fog Byggecenter.

## Beskrivelse
Dette system er udviklet som et eksamensprojekt på DAT2. Applikationen gør det muligt for eksterne kunder at konfigurere en carport ud fra specifikke dimensioner, 
uden redskabsskur, og modtage en kvittering. For Fogs interne salgsmedarbejdere fungerer systemet som et administrationsværktøj med liste over forespørgsler, tilbud, en stykliste og en dynamisk SVG-tegning.

---

## Indholdsfortegnelse
1. [Hvad gør applikationen](#hvad-gør-applikationen)
2. [Teknologier](#teknologier)
3. [Installation (Lokal kørsel)](#installation-lokal-kørsel)
4. [Instruktioner og Brug](#instruktioner-og-brug)
5. [Udfordringer undervejs](#udfordringer-undervejs)
6. [Gruppemedlemmer og Inspiration](#gruppemedlemmer-og-inspiration)
7. [Licens](#licens)

---

## Hvad gør applikationen

### Kundevendt flow (Ekstern)
* **Dynamisk konfiguration:** Kunden indtaster ønsket længde og bredde.
* **Kundeoprettelse:** Systemet gemmer kunden og knytter forespørgslen til kundens unikke ID i databasen.
* **Automatiseret mailbekræftelse:** Systemet sender en bekræftelsesmail til kunden via SMTP (Mailtrap).
* **PDF-generering:** Kunden kan downloade en PDF-kvittering med specifikationerne direkte fra sessionen.

### Salgsmedarbejder flow (Internt)
* **Sælger-dashboard:** Sælgerens oversigt.
* **Forespørgselshåndtering:** Sælgere kan gennemse ubehandlede forespørgsler og konvertere dem til tilbud.
* **Priskalkulation:** Systemet beregner automatisk en totalpris baseret på materialepriser i databasen.
* **Dynamisk SVG-tegning:** Genererer en præcis, skalerbar konstruktionstegning set oppefra baseret på de valgte mål.
* **Stykliste (BOM):** Udregner den nøjagtige mængde remme, stolper og spær, der skal bruges til styklisten.
## Teknologier
Applikationen er bygget ud fra principperne om objektorienteret programmering (OOP) som er det, vi arbejder med på skolen

* **Backend:** Java 21 & Javalin 6.7.0 (Webframework)
* **Frontend:** HTML5, CSS3, & Thymeleaf (Template Engine)
* **Database:** PostgreSQL & HikariCP (Connection Pooling)
* **Testværktøjer:** JUnit 5 & Hamcrest
* **Øvrige Services:** Mailtrap (SMTP-test) & OpenHTMLtoPDF (PDF-bytestream)

---

## Installation (Lokal kørsel)

Følg disse trin for at downloade, konfigurere og afvikle projektet lokalt på din egen maskine:

### 1. Forudsætninger
Sørg for at have følgende installeret på dit system:
* **Java 21 JDK**
* **PostgreSQL** 
* **Maven** 

### 2. Kloning af repository
Klon projektet til din lokale maskine via terminalen:

git clone [https://github.com/stinetorndal/DAT2_Fog](https://github.com/stinetorndal/DAT2_Fog)

### 3. Database opsætning
Opret to tomme databaser i PostgreSQL (f.eks. via pgAdmin):

fog_app (Til selve applikationen under kørsel)

fog_test (Dedikeret testdatabase til afvikling af JUnit-integrationstests)

Kør det medfølgende SQL-opsætningsscript på begge databaser for at oprette tabeller, foreign keys og indlæse de nødvendige basisdata for postnumre og materialepriser.

### 4. Miljøvariabler (.env)
Opret en fil med navnet .env i projektets rodmappe og konfigurer dine lokale database- og Mailtrap-adgange:

# Database-konfiguration
DB_USER=din_postgres_bruger<br>
DB_PASS=din_postgres_adgangskode<br>
DB_URL=jdbc:postgresql://localhost:5432/%s<br>
DB_NAME=fog_app<br>

# Mailtrap-konfiguration (Til SMTP-test af bekræftelsesmails)
MAILTRAP_HOST=sandbox.smtp.mailtrap.io<br>
MAILTRAP_PORT=2525<br>
MAILTRAP_USER=dit_mailtrap_id<br>
MAILTRAP_PASS=dit_mailtrap_password<br>

### 5. Byg og kør applikationen
Kør følgende kommando i terminalen for at downloade afhængigheder og compile projektet:

Bash
mvn clean package

Find derefter Main.java i din IDE (f.eks. IntelliJ). Applikationen vil nu køre lokalt på:

👉 http://localhost:7070

**Instruktioner og Brug** <br>
Oprettelse af forespørgsel (Kundens perspektiv) <br>
Naviger til http://localhost:7070/ på din maskine.

Indtast de ønskede dimensioner for din drømme-carport samt eventuelle mål på et redskabsskur. Klik på "Send forespørgsel".

Du føres til en kvitteringsside, hvor du kan klikke på "Download PDF" for at hente dine specifikationer ned som en officiel PDF-kvittering.<br>

*Behandling og beregning (Salgsmedarbejderens perspektiv)<br>
Gå til http://localhost:7070/ og vælg sælger.

Log ind med en gyldig medarbejderkonto (kan oprettes/findes i tabellen salespersons).

Gå til dashboard og vælg, hvad du vil se - tilbud eller forespørgsel. Du kan du ud fra en liste vælge et specifikt tilbud eller en forespørgsel.
I forespørgsel kan du konvertere til tilbud. 
I tilbud kan du se stykliste eller generere en dynamisk SVG-tegning


**Udfordringer undervejs**
Fremmednøgler og sletterækkefølge i JUnit: 
I vores integrationstests er databasetabellerne bundet hårdt sammen af Foreign Keys. 
Vi lærte, at man skal slette data i den helt rigtige rækkefølge (først quotes, så inquiries, og til sidst customers), så Foreign Key-restriktioner ikke blokerer for testafviklingen.

Isolering af JUnit mod miljøvariabler: 
JUnit-tests indlæser ikke automatisk lokale .env-filer i IntelliJ. 
Dette løste vi ved at konfigurere Environment Variables inde i IntelliJs testkonfigurationer samt sikre, at instansieringen af vores ConnectionPool i testklasserne
modtager de korrekte systemvariabler dynamisk via .getInstance().

Kontrolleret redundans: 
For at sikre, at priser og dimensioner på et historisk tilbud ikke ændrer sig, hvis en kunde efterfølgende ændrer sin oprindelige forespørgsel, 
valgte vi arkitektonisk at implementere kontrolleret redundans ved at gemme længde og bredde direkte på tilbuddet i databasen.

**Gruppemedlemmer og Inspiration**
Vi er en projektgruppe på to studerende, der har samarbejdet tæt om at designe og kode denne fuld-stack applikation:

* **Stine:** [stinetorndal](https://github.com/stinetorndal)
* **Katarina:** [KatarinaKN](https://github.com/KatarinaKN)

Tutorials og inspirationskilder
Undervejs i udviklingen har vi søgt inspiration fra:

W3Schools
https://www.freecodecamp.org/news/how-to-write-a-good-readme-file/

Licens
Dette projekt er licenseret under GPL-3.0 License - se den officielle side på https://choosealicense.com/licenses/gpl-3.0/
 for fuldstændige detaljer.

Dette betyder, at koden er open-source; andre må meget gerne inspicere, modificere og lære af koden i uddannelsesøjemed, men koden må ikke lukkes inde i et kommercielt, proprietært system.
---

