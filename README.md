## Web server with socket API
Socket API 이용하여 Http 형식의 요청을 처리할 수 있는 웹 서버를 제작하고, Java NIO로 Multiplexing server 로 개선한다.

### Steps
1. Socket API로 Socket Server를 제작한다.
2. Multi-Threading을 이용한 비동기 요청 처리를 구현한다.
3. NIO, Selector를 이용한 Multiplexing server를 구현한다.

### Backlog

Backlog는 연결 대기할 수 있는 큐의 사이즈이다. 사용자와 연결이 완료되었지만 애플리케이션에서 처리하지 못하는 상황인 경우 (ex, 동기 처리 또는 사용 가능한 스레드 부족)에 연결을 큐에 담아두는데, 그 사이즈를 말한다.    

서버는 클라이언트로부터 전달받은 SYN을 syn_queue에 저장해 두고, SYN+ACK 패킷을 클라이언트에 전달하게 된다. 이때 지정한 시간 동안 클라이언트에서 ACK 패킷이 제대로 오지 않는다면, 이 syn_queue 안에 연결을 확인하여 클라이언트에 다시 지정된 시간 간격으로, 지정된 횟수 재시도하는 것이다.

그리고 이렇게 ACK 패킷을 전달받은 요청이 완료된 연결을 accept_queue에 저장하고, 서버에서 accept가 가능해질 경우, accept_queue에서 연결을 꺼내와 전달하는 것이다.

즉 이 두 큐의 사이즈가 작고 트래픽이 몰려 큐가 가득 찬다면, 그 이후의 연결들은 소실되게 된다. 반대로 큐의 사이즈가 트래픽에 비해 너무 크면 사용되는 큐에 비해 메모리만 차지하는 꼴이 된다. 이런 syn_queue와 accept_queue의 사이즈를 socket API의 listen() 함수 backlog 파라미터로 지정하는 것이다.

 
<img width="843" alt="image" src="https://github.com/ecsimsw/multiplexing-server-with-socketAPI/assets/46060746/96e4a360-1935-47f2-b757-b33882c9c7e9">

 
### SYN Flood attack

backlog의 syn_queue 특성을 이용해서 서버를 공격하는 것이 가능하다. 클라이언트 측에서 SYN 패킷을 전송하고, ACK 패킷을 전송하지 않는 것을 반복하면, 서버의 syn_queue에는 공격 자의 syn 연결 정보로만 가득 찰 것이고, 그 외 다른 사용자의 요청을 수립하지 못하게 된다. 이런 공격 방식을 SYN-Flood라고 한다.

대표적인 대응 방식으로는 SYN_Cookie를 이용할 수 있다. 앞선 그림에서 SYN 패킷을 syn_queue에 저장하는 것이 아니라, SYN_ACK에 연결 수립에 필요한 데이터를 포함하고, 클라이언트의 ACK 요청에서 해당 정보를 확인하는 것이다. 또는 동일 클라이언트의 연결 요청의 수를 제한하는 방화벽을 두는 것도 SYN-Flood를 대응할 수 있는 방법이 된다. 

### Non-blocking IO 와 IO 다중화(Multiplexing)

Non blocking IO는 IO 작업이 진행되는 동안 사용자 흐름이 끊기지 않는다. 사용자가 커널에 read를 호출하면, Kernel은 데이터 준비 여부에 상관없이 응답하는 것에 차이가 있다. 아래 그림에서처럼 준비가 되어있지 않다면 준비가 아직 안됐음을 반환하고, 준비가 되었다면 해당 데이터를 반환한다. 응답이 바로 오기 때문에 메인 흐름에서는 데이터를 기다릴(block) 필요 없이 다른 작업을 이어 갈 수 있게 되는 것이다.

<img width="971" alt="image" src="https://github.com/ecsimsw/multiplexing-server-with-socketAPI/assets/46060746/e605a5a6-5202-48ed-bb7d-9dcf2140802c">

### Non-blocking IO의 한계

앞손 Non-blocking 방식이라면 데이터 준비 상태를 확인해야하기 때문에 그 자체도 비용이 되면서, 그 확인의 주기가 너무 길면 data read 후 처리되는 시간이 너무 느려진다는 문제가 생기고, 반대로 그 확인 주기가 너무 짧으면 kernel 은 준비되지 않는 데이터에 매번 에러를 응답하며 무의미한 자원 사용, I/O 처리 지연을 야기하게 된다. 그래서 Non-blocking I/O로 여러 개의 Socket connection을 다룬다고 하면 결국 Multi-process 또는 Multi-thread의 비동기 방식을 고려해야 한다.

<img width="1068" alt="image" src="https://github.com/ecsimsw/multiplexing-server-with-socketAPI/assets/46060746/bd1925fd-2a43-47ab-83ac-8a8056c8538e">

### IO 다중화(Multiplexing)

Multiplexing은 하나의 흐름()으로 여러 파일(I/O)을 관리하는 기법이다. 다중 IO 작업들의 파일 디스크립터 변화를 확인하고 read 준비 완료된 작업이 생기면 이를 반환하여 요청을 처리하는 방식이다. 즉 한 스레드 (혹은 프로세스) 안에서 연결들을 돌아가면 확인하는 것이 아닌, Kernel이 연결 소켓들의 파일 디스크립터를 확인하고 완료된 소켓을 반환해주면 이를 처리하겠다는 것이다. Kernel은 이런 다중 파일 디스크립터를 모니터링하는 System call을 제공하고 있고, Select, Poll, Epoll 등이 있다.

Java 1.4부터는 NIO(new I/O) 가 도입되면서 Non-blocking IO 와 Selector를 사용할 수 있다. 이를 다음과 같이 이용하여 Multiplexing server를 구현할 수 있었다. 

``` java
public void run() throws IOException {
    final ByteBuffer buffer = ByteBuffer.allocate(256);

    while (true) {
        selector.select();
        
        final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        
        while (iterator.hasNext()) {
            final SelectionKey key = iterator.next();
            iterator.remove();

            if (key.isAcceptable()) {
                final SocketChannel client = serverSocket.accept();
                client.configureBlocking(false);
                client.register(selector, SelectionKey.OP_READ);
            }

            if (key.isReadable()) {
                try (SocketChannel client = (SocketChannel) key.channel()) {
                    client.write(RESPONSE_MSG);
                    buffer.clear();
                }
            }
        }
    }
}
``` 
