import http from 'k6/http';
  import { sleep } from 'k6';
	
   export default function () {
   http.get('http://host.docker.internal:8080/userCount');
   sleep(1);
}
