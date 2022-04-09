### My socket server
Make small web server with socket API  

- Handle basic http request and send requested files or do job   
   - Socket API
   - Handling http request and response with http format
   - Servlet / Default servlet / Servlet Container
- Support multiple concurrent requests 
   - Multi-process socket or Multi-thread socket   
   - Nonblocking server socket accept   
- Make dynamic response with programming interface   
   - count user

### Step1
Make simple web server with socket API

### Step2
Make server multi-thread to handle concurrent request

### Step3
Feature nonblocking socket IO and compare with blocking/multi-thread server

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
