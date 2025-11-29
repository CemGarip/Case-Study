import http from 'k6/http';
import { check, sleep } from 'k6';

// Ana URL'i tanımla
const BASE_URL = 'https://www.enuygun.com';

// Yapılandırma: 1 Sanal Kullanıcı (VU) ve 60 saniye süre
export const options = {
  vus: 1,
  duration: '60s',

  // Başarı Kriterleri (Thresholds)
  thresholds: {
    // Hata oranı %1'in altında olmalı
    http_req_failed: ['rate<0.01'],
    // İsteklerin %90'ı 3 saniyenin altında yanıt vermeli
    http_req_duration: ['p(90)<3000'],
  },
};

export default function () {
  // Simülasyon: Ana sayfaya basit bir GET isteği
  const url = `${BASE_URL}/`;

  const res = http.get(url);

  // Doğrulama: Response Status ve Yanıt Süresi
  check(res, {
    'Status is 200 OK': (r) => r.status === 200,
    'Response time (P90) is under 3s': (r) => r.timings.duration < 3000,
  });

  // Kullanıcı, bir sonraki aksiyondan önce 5 saniye bekler.
  sleep(5);
}