### My socket server
Make small web server with socket API  

- Handle basic http request and send requested files or do job   
   - Socket API
   - Servlet / Default servlet / Servlet Container
- Support multiple concurrent requests 
   - Multi-process socket or Multi-thread socket   
   - Nonblocking server socket accept   
- Make dynamic response with programming interface   
   - In-Memory db / count user

### Testing / K6
[Installation](https://k6.io/docs/getting-started/installation/)
[Running](https://k6.io/docs/getting-started/running-k6/)

```
docker pull grafana/k6
docker run --rm -i grafana/k6 run --vus 10 --duration 30s - <script.js
```

Experimental factors : The number of thread, Backlog, Blocking/Non-blocking IO, Execution time to response

### Step1
Make simple web server with socket API

### Step2
Make server multi-thread to handle concurrent request

### Step3
Testing and make thread pool

### Step4
Feature nonblocking socket IO and compare with blocking/multi-thread server

### socket
- socket() : create socket
- bind()   : 지정된 포트 번호를 사용할 것이라는 것을 운영체제에 요청, 이미 사용 중이라면 에러 리턴
           : backlog로 연결 대기열이 증가할 수 있는 최대 길이를 지정
- listen() : 클라이언트에 의한 연결 요청이 수신될 때까지 대기
           : 요청이 수신 시 또는 에러 발생 시 대기 종료
- accept() : 연결 요청을 받아들여 소켓 간 연결을 수립
           : 이때 클라이언트 소켓과 통신할 새로운 소켓 인스턴스 반환
- send() / recv()
- close()
