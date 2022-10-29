# Arakne Value [![javadoc](https://javadoc.io/badge2/fr.arakne/arakne-value/javadoc.svg)](https://javadoc.io/doc/fr.arakne/arakne-value) [![Maven Central](https://img.shields.io/maven-central/v/fr.arakne/arakne-value)](https://search.maven.org/artifact/fr.arakne/arakne-value)

Common data structure, constants and helpers for Dofus 1.29.

## Installation

For installing using maven, add this dependency into the `pom.xml` :

```xml
<dependency>
    <groupId>fr.arakne</groupId>
    <artifactId>arakne-value</artifactId>
    <version>0.8-alpha</version>
</dependency>
```

## Structures

- [Colors](src/main/java/fr/arakne/utils/value/Colors.java) : Store the creatures / players colors
- [Dimensions](src/main/java/fr/arakne/utils/value/Dimensions.java) : Store 2D dimensions (with and height)
- [Interval](src/main/java/fr/arakne/utils/value/Interval.java) : Store an integer interval, with min and max

## Constants

- [Gender](src/main/java/fr/arakne/utils/value/constant/Gender.java) : The character gender (male or female)
- [Race](src/main/java/fr/arakne/utils/value/constant/Race.java) : The character races / classes enum (only dofus 1.29 races)

## Helpers

### [RandomUtils](src/main/java/fr/arakne/utils/value/helper/RandomUtil.java)

Helper class for generate random values in replacement of the native Random class.
Also useful for create predictable random values during unit tests.

```java
// Create the random instance
RandomUtil random = new RandomUtil();

// Create a shared (static) instance of the RandomUtil
// This is necessary for enable testing mode on static fields
RandomUtil shared = RandomUtil.createShared(); 

random.bool(35); // 35% of chance to be true
random.rand(new Interval(4, 10)); // Random number between 4 and 10
```

Enable testing mode on unit test :

```java
public class MyTestCase {
    @BeforeEach
    public void setUp() {
        // Enable testing mode and reset the random seed
        // Execute before each tests, so the generated value is always independent
        RandomUtil.enableTestingMode();
    }
}
```
