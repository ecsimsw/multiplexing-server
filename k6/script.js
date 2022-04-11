import http from 'k6/http';
import {sleep} from 'k6';

const ROOT_PATH = 'http://host.docker.internal:8080/'
const SUB_PATH = 'userCount';

export let options = {
    vus: 1000,
    duration: '10s',
};

export default function () {
    http.get(ROOT_PATH + SUB_PATH);
    sleep(1);
}
