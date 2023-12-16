created for version tag test 1.0.0

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