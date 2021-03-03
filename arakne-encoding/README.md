# Arakne Encoding [![javadoc](https://javadoc.io/badge2/fr.arakne/arakne-encoding/javadoc.svg)](https://javadoc.io/doc/fr.arakne/arakne-encoding)  [![Maven Central](https://img.shields.io/maven-central/v/fr.arakne/arakne-encoding)](https://search.maven.org/artifact/fr.arakne/arakne-encoding) 

Common encoding algorithms used by Dofus 1.29 protocol.

## Installation

For installing using maven, add this dependency into the `pom.xml` :

```xml
<dependency>
    <groupId>fr.arakne</groupId>
    <artifactId>arakne-encoding</artifactId>
    <version>0.5-alpha</version>
</dependency>
```

## Usage

### [Base64](src/main/java/fr/arakne/utils/encoding/Base64.java)

Implementation of the Dofus pseudo Base 64 format.

To encode and decode a single character :

```java
int value = Base64.ord('G'); // Get the integer value of the 'G' character
char c = Base64.chr(value); // Get the Base 64 character for the given value ('G' here)
char d = Base64.chrMod(value); // Same of above, but handle the overflow using a modulo (if value > 64) 
```

To encode and decode a string (ex: a cell id for fight position) :

```java
String strCellId = "zS";

int cellId = Base64.decode(strCellId); // Decode the cell id
String encoded = Base64.encode(cellId, 2); // Encode an integer into a Base64 string of length 2
```

Handle also byte array (ex: for map data) :

```java
byte[] data = Base64.toBytes("aazDf"); // Decode each character into the corresponding array element
String encoded = Base64.encode(new byte[] {45, 7, 14, 22}); // Encode a byte array
```

### [PasswordEncoder](src/main/java/fr/arakne/utils/encoding/PasswordEncoder.java)

Implementation of password cipher for the authentication process.

```java
// Create the encoder. The parameter must be the connection key (HC packet)
PasswordEncoder encoder = new PasswordEncoder(key);

// Client (bot) side : encode the password and send to the server
socket.send(username + "\n#1" + encoder.encode(password));

// Server side : decode the password
// Note: the parameter must not contains the "#1" prefix
account.verifyPassword(encoder.decode(encodedPassword));
```

### [XorCipher](src/main/java/fr/arakne/utils/encoding/XorCipher.java)

Implementation of the cipher used by Dofus protocol and encrypted maps.

Note: It's better to use higher level utilities like `EncryptedMapDataSerializer` instead of using manually the `XorCipher`.

```java
// Create the cipher with the parsed key
XorCipher cipher = new XorCipher("my key");

// Encrypt the data using the key and a key offset of 6
String encrypted = cipher.encrypt("my data", 6);

// Decrypt the received data. The key offset must be the same as the used on encrypted method
String decrypted = cipher.decrypt(encrypted, 6); // "my data"
```

### [Key](src/main/java/fr/arakne/utils/encoding/Key.java)

Handle the Dofus cipher key.

```java
// Server: generate a key and send to the client
Key myKey = Key.generate();
client.send("AK1" + myKey.encode());

// Client : parse a received key
Key myKey = Key.parse(ak.key());

// Use cipher to encrypt and decrypt data
myKey.cipher().decrypt(receivedData, offset);
myKey.cipher().encrypt(dataToSend, offset);
```
