# QuestDB Extension Sample Project
This projects shows how to create a QuestDB extension. It is a simple extension that adds a new function to QuestDB that counts words in a string. It's meant to be a starting point for your own extensions.

## Prerequisites
* Java 11
* Internet connection

## Building
To build the extension on Linux and MacoOS, run the following command:
```shell
./mvnw clean package
```
If you are on Windows, run the following command:
```shell
mvnw.cmd clean package
```

This will produce a JAR file in the `target` directory. This JAR file is the extension that you have to load into QuestDB. The exact process differs depending on QuestDB distribution.

This is how you install the extension to QuestDB installed on MacOS using Homebrew:
1. Copy the JAR file to the QuestDB with the main JAR:
    ```shell
    cp target/questdb-extension-sample-1.0-SNAPSHOT.jar /opt/homebrew/opt/questdb/libexec/
    ```
2. Open `/opt/homebrew/opt/questdb/libexec/questdb.sh`, find this line:
    ```shell
    JAVA_LIB="$BASE/questdb.jar"
    ```
    and change it to this:
    ```shell
    JAVA_LIB="$BASE/questdb.jar:$BASE/questdb_ext_tutorial-1.0-SNAPSHOT.jar"
    ```
3. Restart QuestDB:
    ```shell
    questdb start
    questdb stop
    ```
4. Open QuestDB web console and run the following query:
    ```sql
    select wordcount('hello world') from long_sequence(1)
    ```
    You should see `2` in the result set.

If you use a different QuestDB distribution then the paths will be different, but the basic principle is still the same: You need to copy the JAR file to the QuestDB installation directory and add it to the module path.

## Further Steps
If this tutorial is working then you can try to modify the code and see how it affects the extension. Pop in the [QuestDB Slack](https://questdb.io/slack) if you have any questions!

## Disclaimer
Java API for functions is not officially support QuestDB. It is subject to change in the future. Always test your custom functions before upgrading QuestDB.