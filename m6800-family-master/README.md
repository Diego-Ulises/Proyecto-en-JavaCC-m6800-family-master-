# M6800 Family

Programmer’s Reference Manual in Java

## Compiler .jar

```bash
$ mkdir -p bin
# $ javac -d bin src/**/*.java -> no funciona, se pone la de abajo.
$ javac -d bin $(find src/ -name *.java)
$ cd bin/ && jar cfe ../M6800.jar com.compilador.m6800.Main * && cd ..
$ java -jar M6800.jar prueba.txt
```

o se puede descargar el `M6800.jar` de los [release](https://gitlab.com/mvochoa/m6800-family/releases).
