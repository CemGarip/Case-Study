# ⚡ Enuygun.com Yük Testi Dokümantasyonu (Part 3)

Bu test, Enuygun.com'un kritik uçuş arama modülünün davranışını tek bir kullanıcı yükü altında incelemek için k6 kullanılarak oluşturulmuştur.

## 1. Test Senaryosu: Uçuş Arama Yükü

* **Hedef Modül:** Uçuş Arama (Homepage GET Request Simülasyonu)
* **Amaç:** Enuygun ana sayfasının tek bir kullanıcıdan gelen sürekli yüke nasıl yanıt verdiğini ölçmek.
* **Yük Profili:** 1 Sanal Kullanıcı (VU), 60 saniye boyunca sürekli talep gönderir.
* **Başarı Kriterleri (Thresholds):**
    * Hata Oranı (`http_req_failed`): %1'in altında.
    * P(90) Yanıt Süresi (`http_req_duration`): 3000 milisaniyenin (3 saniye) altında.

## 2. Test Yürütme Adımları

1.  **Kurulum Doğrulaması:** K6'nın sistem yolunda (`k6 run`) kurulu olduğundan emin olun.
2.  **Dizin Değiştirme:** Terminali projenin ana dizininde açın.
    ```bash
    cd performance-tests
    ```
3.  **Testi Çalıştırma:** Senaryo dosyasını çalıştırın.
    ```bash
    k6 run enuygun_search.js
    ```

## 3. Örnek Test Raporu (Terminal Çıktısı)

Aşağıdaki metrikler, testin sonunda terminalde görüntülenecektir:

| Metrik | Anlamı | Örnek Sonuç | Başarı/Hata Durumu |
| :--- | :--- | :--- | :--- |
| **`http_req_duration`** | Yanıt Süreleri (ms) | `avg=1250.00ms, p(90)=1800.00ms` | Başarı (Eşik 3000ms altı) |
| **`http_req_failed`** | Hata Oranı (%) | `rate=0.0000 (0%)` | Başarı (Eşik %1 altı) |
| **`vus`** | Kullanıcı Sayısı | `1` | Başarı (Sabit) |
```markdown
// Örnek Terminal Çıktısı Formatı
// ----------------------------------------------------
// ✓ Status is 200 OK
// ✓ Response time (P90) is under 3s (Threshold)
//
// checks.....................: 100.00% ✓ 120    ✗ 0
// http_req_duration..........: 1.25s  min=900ms avg=1250ms med=1100ms max=2500ms p(90)=1800ms p(95)=2000ms
// http_req_failed............: 0.00%  ✓ 0      ✗ 120
//
// VUs........................: 1      min=1    max=1
// iterations.................: 24     min=24   avg=0.4
// ----------------------------------------------------