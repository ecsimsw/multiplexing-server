### My socket server
Make small web server with socket API, from Single thread to Multi thread, Multiplexing server

Report : `https://ecsimsw.tistory.com/entry/Web-server-with-socket-API`

### Steps
- Handle basic http request and send requested files or do job   
   - Using Socket API
   - Handling http request and response with http format
   - Servlet / Default servlet / Servlet Container
   - Make dynamic response with Jsoup
- Support multiple concurrent requests with Multi-threading
- `Multiplexing` server with `Selector - java NIO`
- Server test with `K6`

### How to use, What you can code
1. Determine which requests to handle and how to handle by extending Servlet class
- Extends servlet abstract class
  [ex, `http/servlet/UserCountServlet`]
- Map in servlet mappings in `config/ServerConfig`
  [ex, SERVLET_MAPPINGS.put("/{requestUrlPath}", {SERVLET_CLASS});]

2. Change implementation of WebServer with `SingleThreadWebServer`, `MultiThreadWebServer`, `MultiplexingWebServer`
- Default webServer implementation is MultiplexingWebServer
- Instance what you want, in ServerApplication
``` java
try (WebServer webServer = new SingleThreadWebServer()) {}
try (WebServer webServer = new MultiThreadWebServer()) {}
try (WebServer webServer = new MultiplexingWebServer()) {}
```

3. Run sample page or sample API
- [GET] http:://localhost:8080/index.html
- [GET] http:://localhost:8080
- [GET] http:://localhost:8080/userCount
- [PUT] http:://localhost:8080/userCount?number=5
- [DELETE] http:://localhost:8080/userCount

### Quick start
```
git clone https://github.com/ecsimsw/socket-server.git ecsimsw-webserver
cd ecsimsw-webserver
./gradlew run
docker pull grafana/k6
docker run --rm -i grafana/k6 run --vus 10 --duration 30s - <k6/script.js
```
