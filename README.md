# Bluetooth Debug
Provides a logging wrapper around both `BluetoothGattCallback` and `ScanCallback`, in order to figure out what is going on.

This happens by creating either `DebugBluetoothGattCallback` or a `DebugScanCallback` which initially log events and passes then to their enclosed instances.

## Warning
Both debug wrappers implement all methods up to API 27.
If your wrapped instances are implementing a method that the debug wrapper does not, and that method is critical for your business logic, then your app will probably not work properly.

When a either wrapper is initialised, it will print in log a list of all methods that are declared in the passed callback class and any parents and are NOT overridden.

If your app does not need any of the listed, non-overridden, methods, then there won't be a problem.

## Getting the Library

<b>gradle</b>
```groovy
	repositories {
		maven {
			url "https://dl.bintray.com/alt236/maven"
		}
	}

	dependencies {
		compile 'uk.co.alt236:bluetoothdebug:1.0.0'
	}
```

## Usage

### Controlling output
Both `DebugBluetoothGattCallback` and `DebugScanCallback` implement `LogControl` which contains the following signatures:

* `isLoggingEnabled()`: Check if logging is globally enabled
* `setLoggingEnabled(boolean)`: Enable or disable logging

Note: By default, logging in disabled when a debug wrapper is instantiated.

The default logcat tags are the following:
* `BTDebug-SCAN` for `DebugScanCallback`
* `BTDebug-GATT` for `DebugBluetoothGattCallback`

They can be changed at construction time.

### Debugging a ScanCallback
Wrap your own callback with a `DebugScanCallback`.
Don't forget to keep and instance of this wrapped callback to use for stopping.

```java
ScanCallback yourOwnCallback = {your own code goes here};
DebugScanCallback debugScanCallback = new DebugScanCallback(yourOwnCallback); // You can pass your own log tag here

debugScanCallback.setLoggingEnabled(true); // Logging is disabled by default
bluetoothLeScanner.startScan(debugScanCallback);

[...]

bluetoothLeScanner.stopScan(debugScanCallback);
```

### Debugging a GattCallback
Wrap your own callback with a `DebugGattCallback`.

```java
BluetoothGattCallback yourOwnCallback = {your own code goes here};
DebugBluetoothGattCallback debugGattCallback = new DebugBluetoothGattCallback(yourOwnCallback); // you can set your own tag, and select a byte array handling method here

debugGattCallback.setLoggingEnabled(true); // Logging is disabled by default
yourBluetoothDevice.connectGatt(context, false, debugGattCallback);

```
#### Displaying byte arrays
A lot of the information returned in GATT callbacks is encoded as a byte array.

This library provides a number of different `ByteFormat`s to display them, with the default being `ByteFormat.INDIVIDUALLY_AS_UNSIGNED_BYTE`

##### 1. ByteFormat.INDIVIDUALLY_AS_ASCII
Will try and parse each byte as an ASCII character and pass it raw to log.
Unprintable characters will show up however the log would normally handle them.

For example:
`[255, 0, 1, 65, 97, 127]` would be displayed as `[￿,  , , A, a, ]`

##### 2. ByteFormat.INDIVIDUALLY_AS_ASCII_WITH_UNICODE_SYMBOLS
Will try and parse each byte as an ASCII character and pass it to log.
Unprintable (C0) characters will be converted to their Unicode representations as per the Unicode control pictures (https://en.wikipedia.org/wiki/Unicode_control_characters).

For example:
`[255, 0, 1, 65, 97, 127]` would be displayed as `[￿, ␀, ␁, A, a, ␡]`

Note: Displaying this depends on the font used for rendering, so if the second array above has more than one unknown character block (the first entry), then your browser font is missing the control pictures.

##### 3. ByteFormat.INDIVIDUALLY_AS_UNSIGNED_BYTE
Will display the array as you'd expect it to be displayed if Java had unsigned data types.

For example:
`[255, 0, 1, 65, 97, 127]` would be displayed as `[255, 0, 1, 65, 97, 127]`
##### 4. ByteFormat.INDIVIDUALLY_AS_SIGNED_BYTE
Will display the array the way Java prints byte arrays.

For example:
`[255, 0, 1, 65, 97, 127]` would be displayed as `[-1, 0, 1, 65, 97, 127]`

## License
    Copyright (C) 2018 Alexandros Schillings

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
