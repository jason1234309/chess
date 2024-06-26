# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
url for sequence diagram:
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZxDAaQMiVpgOZQIAV2wwAxGmBUAnjABKKWyRVMCg5JAg0NwB3AAskMDFEVFIAWgA+ckoaKAAuGABtAAUAeTIAFQBdGAB6RysAHTQAbzrKDwBbFAAaGFx1KOgOHpR24CQEAF9MYWyYdNZ2Lko8lqsO7r7VAaghkbHJzDZOblh5mdEc-0DgygAKVp3tAEpprNE5jNl5JRV1PNsUGAAKpWe5rYCdF5fRTKNSqD5GHR5ABiSE4MAeWnkMB0Pge60w0J+cLmZzeKjyaEcCAQr2shnmRNhfxgICgKDkKBBd0xz0J2mJ6gRxjyCg4HAxVixwDpVHejIFzNUeTZHISCkcYBit2Ampi0qhit+8PmIpgYolGq10P53yVpIy5wpMF11u0svl8yOS1y-lU2AiBitMUOixODsy1GWMFWbQh3RderKEAA1ugpjNKBGkugwHkAEwABkLjVjUHWPVdMWTabQU3QHDsDmcbmg7ABMAAMhBApFXLF4olkLmEZncgUSuUqtUDOpwmhS-j4z1NtsOBmslmvWHo2WK719FtBlNveGyfS8t3e7d2QBHRxqYFWaEvJ0Mz5GuH-QHcqBguOQraMLGsKSIwKi6K8tiuKSgBYhMiBKTnnKzoPDab6juSKAqjEKAgCmhSHmuN4oPej6-tCPRoXyGEKnaxo5KqnLBjqeovkBgomhkZoWuabHughJJIY6WE5FW6FYRGp7Rn6AZoEGeqhscW7IbuS6dJWSapumsoqRkOYYAWxaLuCGmJlqNY6Q2TZOC4rj2Cg6Bdj2TjMP2cQJJgBnMKp475NIACinYBWUAXTrOqjzo0VaWWgumnBk0njk0MXaXWSk+phF7ObYrmsW68ivpJdHAV+MAAmALGpbWhr0UJ3FIgAZmiEridoMCNQ47TmdWaUwBx9rCZGKHYW18geioMAIoJLIcCg3Dqlptb5b1NUDYhDV5HNC2GGNwAdV1PWxetQnIRcMBUjSE0MtuynjpdtJJRGY6UtStJjtmw6GTARYls0D31pwNktq47ISp2QTMAA4vG8LuYOXlfT5IlRn5UMhdOtjxtFS3oPFCJJSs1U6U9Z3OhDwQw50qgrextFpDNyrlYCVW42gtWlUKppNS1PXQgdEDdcTkSmIzz2iUdaXXekjM5MglOw7cHOcekIrsmAjjli6NLlfG-MQDoABWeFgBoYtDW+-x69oFNYLRt0+jkVMoLbsmBmISUW5uuRNFjnTQrbG6o59yR5j9xnNH7KAB5DgONpg9i2W42Ca9g3DwOynIwM70QeUOodZdkeRFKUlQ1FHOMWWlpZRwAcvGQezA7JxE2zPR1w3GVnijI15HAmcJM7tPaO38b14B9Oy8zlV6itsXK4Nm3Neie0C0LbP9SdXNk9hku1tLDOfiyTGD-GtxRwAktIo+dOPKALxtiJieKuv+wJR8mjvVudFf11STuuRnb9zVPON28kPYAPFqjb+KBf4fSGt5Iyf1fbxivnHYGdkvDzQQBAKIMAABSEA0TZ3jG4HQCBQApkRgXXyxdChAjLtUCuHg2alhAEjOAEAcFQBvrA6QjctyJQAa3KutYejsNDpw7hvC0Fd0EcNc6hC0RD2FuIjhXDoAyOkEVekHwp4VVZqI9AD96pP2Xq1fi2JOqCz3k5LeqhhKW1sZEA2xtwB-xKpxHIFUh6X20fY0CORzGv2ju1ax3U-EBK9tlKOEldFf1ZAPQwaAUB4Nie1KI8QYisiRh4j8dUWTOCxCgXxqDr6shiO2FAHBnbsXNptGA7I06gEMOkqxh1InmwSQ9DxzdlgBSgA4KAYCbCk1oRdN6+MEFIyQaWAGmBrIJ2bHZewwAfCIA5LAYA2A06EDCBEXOCNvKF2jP5IKIUwo1GMFMoRd1XpXVJj3C47DNnQj0LOJWeTZYvLwLU7QAAhfQKBPmM1VkibagJdo6zaftVxJsNCTw-oxbgeBgzQkBQYEFH9AkQoSNrBAfN2pwvAAiyS00kU-KgBRAFQKsUFK4k-XFUKCXUWxMS02UTumTPtrcx2AyhkjIgXdKBRcJlXXgfMRB4dkHzOskAA
