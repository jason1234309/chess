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
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWOZVYSnfoccKQCLAwwAIIgQKAM4TMAE0HAARsAkoYMhZk4MepFdXaaA5lAgBXbDADEaYFQCeMAEopDSCWCiCkENFYDuABZIYByIqKQAtAB8LNSUAFwwANoACgDyZAAqALowAPSmegA6aADehZQ2ALYoADQwuFJ+0DL1KFXASAgAvpgUcbDRsWziic6u7pQAFBVQ1XUNyhLNUK0w7Z0IAJR9rOwwQwJCouJSiYYoYACqejN68ztHImKSEgcx6olkAKIAMt9wTIwWbzGAAMUcaQAssC9JgnidXgchv0RihEmhTAgELt9OJ3jAES8zjAQFAUIIUDdpiDgDV6o1li02h0uo95IipATPjAAJIAOR+jiBtPpiyazPWrIQfP5mTSsNoqP2hw5xIkiTJFJCwlMYACd0qdJQ7OO6u5CkSAqFIvuxvqwD1AUyEAA1uhZfKYI79fC1ac3hEUXtRt6nS73Wh6qKOMr8UGYqiEmH9RH0NG7TVcTRBkMwugwIkAEwABhLJVKPudbvTirmxt66BkRhM5is0F4Fxgvwgrl8lkCwVCyALBKTUESqQyOXyKik3jQFZjDKWKxkvXHY5D6O7vaQaEN9bFjLXOzjqlVZoD50u1Kgh4efqvSKGPJ+-0BddBEOhdafzwDZFE23eJZhgCAFAAKxQcBsxVYC8R3EAAhg10UlXFpD1qE8WjPbcCSJa8tUpCh5x8B97RTAIAH1IEjU0AJfD5LVlG0v0oqs018AUvSrf9OUDYNEMSTiayjdis3PIDhmTUTIwzI1JNYShpPzDBizLCsqK4hSjxQRs0GbTBjDMCxLGMFAPR7QwzGYAcghCTA1OYISc0nPg-m+TJvlyPI5wkBcSjk9BsxU1z2ESazbKmYK0DwxCCP9V4bzAXV9Ri8MxIYgSLS+TzP1i8FIRhPjCJfcLQ0KiDoNgqTEufEkZBQBBLhQNLq0jDLUyy-jzVfFiPP+bztLEorf3a3rAITYYIpgTFsTg+NXOTeacU3abnIxLEcSbFtTKsckZG7NxmAAcWNN57KHJyR0iBC3OSU6vN8wxjSCzLI1CwZ7tm34TvOmoJC6mi6PQeK0XeMqSQuVKnWBrjsr65i8o-IFCp-EqnU0KHBJ+yqPpC89ohxlKAckKZEYDaI33yoFXpqNIoJg5gMZgenVCmAAzaAKWQtnjUp8q8Z3dnGZqsA-vcRaL2Wid+YZpnwElrB1rzW7CxgUtyzKUXFYlk6DKMky20sbBTCgbAWvgclKRgMn-Ac4dwhc+7kynLIXremwCcXHXjX5Bsvq3YTre1FAyeB2ixPqdmA5qcH4JJtnLna+Gepx3LyFpkbIzGzHfUmoWZvx7rPqJqIk+IkII9jgXC65fqrUFb5hXllBeT4GP-eNT0FXZ+vceLkXjQ76XpPHc4R74IONvVjTtdKdmO8NvaTbsZqEAgPwYAAKQgfc7Z7ywFAQUBXRu53g4e1IrhnPJ2fe0v0ArZy4AgTeoC7mpl5n4XEj3-cEdYoMlPuvKAb8P5f3bnwBO8YK5JWhinOGsVBYN2RlnVGOcPSs1KggiQCZzwiR9uBPWY9LyMRJJBfeaAUigMoC6IBPsQHADARA6AUCO6oMDMja0Lc0ZMIaHQ8B792Ftw7r3NuA9x4gTmttMhsstoLVVjETasiFq7WMq2MyxgWHwBangb02BLaEC8D4B211nJXzdoNLyPl8jqF-kPRIfBmqtSeEoFQFMyHwIahqNQriQjsz4AAIS8TjamA1PLDV1uLPObcYBcx5qAAIbdYEXh8RQvxTUWo6idCEsJeDM6Eiid8LBvgcFOgSdzG2fNQZxTHvVTJiRsmtTvPkrhmcbHDTAqzMCiSakpNmB06ahC1FrXwiM5SctVozzVs7RRO1DKYCAA
