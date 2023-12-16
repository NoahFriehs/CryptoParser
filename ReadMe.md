# CryptoParser

Dieses Repository enthält den Quellcode für das CryptoParser-Projekt.

## Projektübersicht

CryptoParser ist eine Java-Anwendung zur Analyse und Verarbeitung von Crypto-Transactions csv's. Es wird mit Maven zum builden verwendet und enthält GitHub-Actions für automatisierte Veröffentlichungen und Testung.

## Inhaltsverzeichnis

- Projektstruktur
- Build-Prozess
- Abhängigkeiten
- Verwendung
- Testumgebung und Automatisierung

## Projektstruktur

Die Hauptkomponenten des Projekts sind:

- Quellcode: Der gesamte Quellcode befindet sich im Verzeichnis src.
- Tests: Unit-Tests sind im Verzeichnis src/test verfügbar.
- GitHub-Aktionen: Der Release-workflow ist in der Datei .github/workflows/release.yml definiert.

## Build-Prozess

Stellen Sie sicher, dass JDK 17 installiert und Maven konfiguriert ist. Dann führen Sie folgenden Befehl aus:

```bash
mvn clean package
```

## Abhängigkeiten

Das Projekt verwendet folgende Abhängigkeiten:

- TestNG für Tests
- Mockito für das Mocking in Tests
- Für genauere Informationen siehe pom.xml


## Verwendung

Um den CryptoParser zu verwenden, gibt es zwei Möglichkeiten:

1. Erstellen Sie das Projekt mit dem bereitgestellten Maven-Befehl und führen Sie es aus.

```bash
mvn clean package
java -jar target/crypto_parser-1.0-SNAPSHOT.jar
```

2. Führen Sie das Docker-Image von Docker Hub aus und geben Sie den Tag für das entsprechende GitHub-Release an.

```bash
docker run -it stefanbicha/crypto_parser:<tag>
```

Ersetzen Sie <tag> durch die gewünschte Versionsnummer.

## Testumgebung und Automatisierung

### Tests:
Unittests werden mit testNG durchgeführt.

Konfiguration der Testumgebung im pom.xml:

```xml
<project>
        ...
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>RELEASE</version>
        <scope>test</scope>
    </dependency>
    
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <version>3.12.4</version> 
        <scope>test</scope>
    </dependency>
    ...
</project>
```

### Testabdeckung:
Die Testabdeckung wird mit der "Run with Coverage"-Funktion von IntelliJ IDEA gemessen.

![Run with Coverage](pictures/runwithcoverage.png)

[Link zum HTML-Report](htmlReport/index.html)

### Testautomatisierung:
tests.yml für GitHub Actions-Workflow 

```
name: Run Tests

on: push

jobs:
build-and-test:
runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v2

      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          java-version: '17'
          distribution: 'adopt'

      - name: Build with Maven
        run: mvn -B package --file pom.xml

      - name: Test with Maven
        run: mvn test
        
```