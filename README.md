### My socket server
Make small web server with socket API  

- Handle basic http request and send requested files or do job   
   - Socket API
   - Handling http request and response with http format
   - Servlet / Default servlet / Servlet Container
- Make dynamic response with programming interface (Jsoup)
- Support multiple concurrent requests with multi-threading
   - Multi-process socket or Multi-thread socket
- Multiplexing server with java NIO 
- Server test with K6

### Testing / K6
[Installation](https://k6.io/docs/getting-started/installation/)
[Running](https://k6.io/docs/getting-started/running-k6/)

```
docker pull grafana/k6
docker run --rm -i grafana/k6 run --vus 10 --duration 30s - <script.js
```

Experimental factors
- The number of thread
- Backlog
- Blocking/Non-blocking IO
- Execution time to response
