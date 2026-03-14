import http from 'k6/http';
import { check } from 'k6';

export let options = {
    stages: [
        { duration: '20s', target: 500 },
        { duration: '30s', target: 1000 },
        { duration: '30s', target: 1500 },
    ],
};

export default function () {
    let res = http.get('http://localhost:8080/api/products');
    check(res, {
        'status was 200': (r) => r.status == 200,
    });
}