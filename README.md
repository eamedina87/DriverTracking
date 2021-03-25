

# DriverTracking App

An app that can help you manage your deliveries.
Runs a service that tracks your position during an active delivery, saves to database and then syncs with an API.

## Libraries


- [Retrofit](https://square.github.io/retrofit/)
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Fused Location Provider](https://developer.android.com/training/location) 
- [Live Data](https://developer.android.com/topic/libraries/architecture/livedata)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodela)
- [Room Database](https://developer.android.com/jetpack/androidx/releases/room)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [MockK](https://mockk.io/)

## Architecture

MVVM + Clean

Dependency Injection with Hilt

**Data**
- Repository
- DataSources
   - Database
   - Api

**Domain**
- Use Cases
- Managers

**Presentation**
- Activity/Fragment
- Service
- ViewModel

## Testing

With MockK library. Due to the time constraint only unit tests were developed for datasources and repositories.

## Environments

API urls are configured in the gradle.properties file

**Local**

Using Mockoon server and testing with a physical device

`API_BASE_URL_LOCAL="http://SERVER_URL:3001/api/"`

**Remote**

Using MyJsonServer with a physical device or emulator

`API_BASE_URL_REMOTE="https://my-json-server.typicode.com/eamedina87/drivertracking-backend/"`

*Note*: during the development of this app there were ocassions in which the server was out. So it is possible that during the testing of the app it can happen again.

*Important*: If any of these urls are changed in the gradle.properties, due to security configurations, they must also be updated in `network_security_config.xml` 

## Background Service

The Service in charge of acquiring the location and battery information, and saving to local and then sending to the API is the Tracking Service.

There are several log messages put in place so that when executing the app we could see how everything is working in the background.

It is recommended that in the Android Studio's Logcat we filter the info with the text found in the LOG_TAG_APP constant.

`const val LOG_TAG_APP = "drivertracking.log"`

Another way of testing what's happening in the background is by using the Android Studio's Database Inspector with live updates.

## Extras

The app also can work in Tablets with a differentiated UI.

## License

MIT License

Copyright (c) [year] [fullname]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
