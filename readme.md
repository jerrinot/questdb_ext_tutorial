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

## Modifications
If this tutorial is working then you can try to modify the code and see how it affects the extension. If you do not have Java IDE then Intellij [IDEA Community Edition](https://www.jetbrains.com/idea/download/) is a good choice. It's free and has a good support for Java development. VS.Code is also a good choice, but it does require a bit of tweaking to get it working with Java.

Pop in at the [QuestDB Slack](https://questdb.io/slack) if you have any questions!

## Going Deeper
This tutorials shows how to create a simple pure function: It maps a string column from a single row to integer.

The next step could be a custom aggregation/GroupBy function: A function which receives data from multiple rows and produces a single value. This is a bit more involved, but it's a good way to learn more about QuestDB internals.

## Disclaimer
Java API for functions is not officially supported by QuestDB. It is subject to change in the future. Always test your custom functions before upgrading QuestDB.