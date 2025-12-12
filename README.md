# quarkus-camel-fop-img

Project to reproduce a bug with images when generating PDF files using FOP with Apache Camel run by Quarkus.

## JAR mode

### Build & run

```sh
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

### Test

```sh
curl -s http://localhost:8081/pdf -o jar.pdf
curl -s http://localhost:8081/pdf/img -o jar-img.pdf
```

## Native mode

### Build

```sh
./mvnw package -Dnative
./target/quarkus-camel-fop-img-0.0.0-SNAPSHOT-runner
```

### Run

```sh
curl -s http://localhost:8081/pdf -o native.pdf
curl -s http://localhost:8081/pdf/img -o native-img.pdf
```
