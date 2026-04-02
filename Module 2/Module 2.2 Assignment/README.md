# Baldree Module 2 CSD420

This Maven project contains two console programs for the assignment:

- `WriteRandomDataApp` writes five random integers and five random doubles to `Baldree datafile.dat`
- `ReadRandomDataApp` reads the file and displays all saved records

## Run the writer

```bash
mvn exec:java -Dexec.mainClass="com.baldree.mod2.WriteRandomDataApp"
```

## Run the reader

```bash
mvn exec:java -Dexec.mainClass="com.baldree.mod2.ReadRandomDataApp"
```

## Run tests

```bash
mvn test
```
