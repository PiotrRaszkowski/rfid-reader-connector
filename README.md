# reader-connector

============================================================

# Description
Library/API to connect to RFID Readers (eg. ACM812A).

# Motivation
No free/public and well-documented API for RFID Readers.

# Installation
```xml
<dependency>
    <groupId>io.codingpassion.rfid</groupId>
    <artifactId>rfid-connector</artifactId>
    <version>1.0</version>
</dependency>
```

# Usage

## Create ReaderConnector

### Factory method
```java
ReaderConnector readerConnector = ReaderConnectorFactory.getReaderConnector(ReaderModel.ACM812A_SOCKET);
```

## Usage ACM812A with TCP/IP (Socket)

This chapter describes how to use the ACM812A reader.

The full documentation of the reader communication protocol: [comunication_protocol.pdf](documentation/acm812a/ACM_Reader communication protocol-ALL.pdf)  
Additional documentation/instruction of the demo application: [demo_instructions.pdf](documentation/acm812a/EPC-Demo operation instructions.pdf)

### Create ReaderConnector object

The first step is to create the ReaderConnector object which allows to send commands to the reader and gives the ability to read tags from the reader.

The reader gives several interfaces to establish connections, this driver has been developed to use TCP/IP interface to operate with the reader. 
By default, the device connects with the IP address 192.168.1.2 and allows to connect via Socket on port 12345.
In addition, the reader allows to connect via web browser by accessing http://192.168.1.2:80 with login and password admin:admin.

```java
ReaderConnector readerConnector = ReaderConnectorFactory.getReaderConnector(ReaderModel.ACM812A_SOCKET);
```

### Connect to the reader

The second important step is to connect to the reader using the created ReaderConnector object. 
There are two necessary parameters:

Parameter name | Default value
--- | ---
HOST | 192.168.1.2
PORT | 12345

```java
Map<String, String> connectionParameters = new HashMap<>();
connectionParameters.put(ACM812ASocketReaderConnector.HOST_PARAMETER, "192.168.1.2");
connectionParameters.put(ACM812ASocketReaderConnector.PORT_PARAMETER, "12345");

ReaderConnector readerConnector = ReaderConnectorFactory.getReaderConnector(ReaderModel.ACM812A_SOCKET);

readerConnector.connect(connectionParameters);
```

### Start and stop reading

By default, when the device is turned on, it works in continuous reading mode, so no start method is necessary to establish reading mode.

If you want to stop reading or start reading again, use stopReading() or startReading(TagType tagType) method from the ReaderConnector.

```java
readerConnector.stopReading();

readerConnector.startReading(TagType.ACM);
```

Start reading takes additional parameter TagType, you can choose the type of a tag which you want to read, for example ACM (EPC).

### Adding and removing listeners

If a device is in continuous reading mode you can register a reading listener to receive events. You need to implement the ReadingListener interface and register or unregister a listener using methods:
```java
ReadingListener readingListener = new ReadingListener() {
    public void onTagRead(String epc) {
        System.out.println("Tag = " + epc);
    }
};

readerConnector.addReadingListener(readingListener);

readerConnector.removeReadingListener(readingListener);
```

You don't need to remove readingListener before calling stopReading() method.

### Restart the reader

You must restart the reader to apply parameters.

```java
readerConnector.restart();
```

### Identify tags on demand

When you decided do stop the continuous reading mode you can manually identify tags by calling the method identifyTags(TagType tagType). This method takes one additional parameter which allow you to choose the type of a tag you want to read.

```java
readerConnector.identifyTags(TagType.ACM);
```

### Applying parameters

The ReaderConnector object allows you to set several parameters, to do that you need to obtain the ParametersApplier and use one of the methods.

```java
ParametersApplier parametersApplier = readerConnector.getParametersApplier();

parametersApplier.applyAntennaPower("100");
```

#### Parameters description

Parameter name | Allowed values | Interpretation
--- | --- | ---
User code | 0 - 255 |
Antenna power | 0 - 150 | 
Reading time interval | 10 - 100 | (N*10)ms
Adjacent discrimination | 1, 2 | 1 - active, 2 - inactive
Adjacent discrimination time |  1 - 255 | (N*1)s
Tags identification mode | 0 - 3 | 0：ACM single tag identification, 1：ACM multiple tags identification, 2：18000_6B single tag identification, 3：18000_6B not support multiple tags identification temporarily

### Examples

#### Multi-tag reading with adjacent discrimination

```java
Map<String, String> connectionParameters = new HashMap<>();
connectionParameters.put(ACM812ASocketReaderConnector.HOST_PARAMETER, "192.168.1.2");
connectionParameters.put(ACM812ASocketReaderConnector.PORT_PARAMETER, "12345");

ReaderConnector readerConnector = ReaderConnectorFactory.getReaderConnector(ReaderModel.ACM812A_SOCKET);

readerConnector.connect(connectionParameters);
readerConnector.getParametersApplier().applyUserCode("255");
readerConnector.getParametersApplier().applyReadingTimeInterval("100"); // 1 second
readerConnector.getParametersApplier().applyAntennaPower("150"); // maximum power
readerConnector.getParametersApplier().applyAdjacentDiscrimination("1"); // set the adjacent discrimination as active
readerConnector.getParametersApplier().applyAdjacentDiscriminationTime("10"); // 10 seconds
readerConnector.getParametersApplier().applyTagsIdentificationMode("1"); // multiple tags identification

readerConnector.restart(); //reader needs to be restarted to apply parameters
```

#### Single-tag reading without adjacent discrimination

```java
Map<String, String> connectionParameters = new HashMap<>();
connectionParameters.put(ACM812ASocketReaderConnector.HOST_PARAMETER, "192.168.1.2");
connectionParameters.put(ACM812ASocketReaderConnector.PORT_PARAMETER, "12345");

ReaderConnector readerConnector = ReaderConnectorFactory.getReaderConnector(ReaderModel.ACM812A_SOCKET);

readerConnector.connect(connectionParameters);
readerConnector.getParametersApplier().applyUserCode("255");
readerConnector.getParametersApplier().applyReadingTimeInterval("100"); // 1 second
readerConnector.getParametersApplier().applyAntennaPower("150"); // maximum power
readerConnector.getParametersApplier().applyAdjacentDiscrimination("2"); // set the adjacent discrimination as inactive
readerConnector.getParametersApplier().applyTagsIdentificationMode("10); // single tag identification

readerConnector.restart(); //reader needs to be restarted to apply parameters
```