# ✈️ Enuygun QA Otomasyon Teknik Değerlendirme Projesi

Bu proje, Enuygun.com uçuş arama modülü ve Petstore API servisleri için geliştirilmiş kapsamlı bir test otomasyon çerçevesidir. Proje; Java 17, Selenium 4, Cucumber BDD ve Rest Assured teknolojileri kullanılarak, modüler, sürdürülebilir ve ölçeklenebilir bir yapıda tasarlanmıştır.


## 🎯 Proje Kapsamı ve Hedefler
Proje, teknik değerlendirme gereksinimlerini karşılamak üzere 4 ana bölümde kurgulanmıştır:

* **Part 1: UI Otomasyonu: Uçuş arama, filtreleme (saat/havayolu) ve fiyat sıralama doğrulama testleri.

* **Part 2: API Testleri: Petstore API üzerinde uçtan uca CRUD (Create, Read, Update, Delete) işlemleri ve JSON Şema doğrulaması.

* **Part 3: Performans Testi: Kritik arama modülü için k6 kullanılarak oluşturulmuş yük testi simülasyonu.

* **Part 4: Veri Analizi: UI üzerinden çekilen gerçek zamanlı uçuş verilerinin (CSV) analizi ve "En Uygun Maliyetli Uçuş" algoritması.

## 🛠️ Kurulum Ön Şartları (Prerequisites)
Projeyi yerel ortamınızda çalıştırmadan önce aşağıdaki araçların kurulu olduğundan emin olun:

Java Development Kit (JDK) 17+

Apache Maven 3.6+

Google Chrome (Sürüm yönetimi WebDriverManager ile otomatiktir)

k6 (Sadece Performans testlerini çalıştırmak için)

Allure Commandline (Raporları görüntülemek için opsiyonel)

## 🏃 Projeyi Kurma ve Hazırlık
Terminali açın ve aşağıdaki adımları izleyerek projeyi kurun:

1. Projeyi Klonlayın
    ```bash
    git clone [GITHUB REPO ADRESİNİZ]
    cd FlightProjectUI
    ```
2. Bağımlılıkları Yükleyin
Projenin derlenmesi ve gerekli kütüphanelerin (Selenium, Cucumber, RestAssured vb.) indirilmesi için:

    ```bash
    mvn clean install -DskipTests
    ```
3. Yapılandırma
Test parametreleri (Base URL, Timeout süreleri vb.) src/main/resources/config.properties dosyasında yönetilmektedir.

## 🧪 Testleri Çalıştırma Komutları
Test senaryoları, Cucumber etiketleri (tags) kullanılarak kategorize edilmiştir. Aşağıdaki Maven komutlarını kullanarak testleri başlatabilirsiniz:
1.  TÜM UI TESTLERİ -- mvn test -Dcucumber.filter.tags="@UI_Execution"
2.  KRİTİK YOL (CASE 3) -- mvn test -Dcucumber.filter.tags="@UI_CriticalPath"
3.  API TESTLERİ (CRUD) -- mvn test -Dsurefire.includes=runners.ApiTestRunner
4.  VERİ ANALİZİ (PART 4) -- mvn test -Dcucumber.filter.tags="@Part4_Analysis"

## 📊 Veri Analizi ve Raporlama Sonuçları
I. Veri Analizi Çıktıları (Part 4)
Part 4 senaryosu çalıştırıldığında, sistem flight_data_*.csv dosyası oluşturur ve konsola aşağıdaki analitik sonuçları basar:

1.  Fiyat Ölçeklendirme -- Ham veri (1.23) analiz için gerçek TL değerine (1230.00 TL) dönüştürülür.
2.  İstatistikler -- Her havayolu için Min/Max/Ortalama fiyatlar hesaplanır.
3.  Algoritma -- Fiyat ve aktarma durumuna göre en maliyet etkin uçuşu seçer.

II. Test Raporları (Allure)
Testler tamamlandıktan sonra detaylı HTML raporunu görüntülemek için:
mvn allure:serve

## ⚡ Performans Testi (Part 3)
Uçuş arama modülünün yük altındaki davranışını simüle etmek için k6 kullanılmıştır.
Senaryo: 1 Sanal Kullanıcı (VU), 60 saniye boyunca sürekli istek gönderir.
Başarı Kriteri: Hata Oranı < %1; P(90) Yanıt Süresi < 3000ms.

Çalıştırma Komutu:
    ```bash
    cd performance-tests
    k6 run enuygun_search.js
    ```

## 🏗️ Teknik Mimari Özellikleri
Page Object Model (POM): [@FindBy anotasyonları ve Page Factory ile modern, bakımı kolay yapı.]

WaitUtils: Thread.sleep yerine Explicit Waits kullanan gelişmiş bekleme yönetimi.

Scenario Outline: Farklı test verileri (Ankara/Lefkoşa) için tek bir feature dosyası üzerinden veri odaklı (Data-Driven) test yaklaşımı.

Robust Locator Strategy: Dinamik elementler ve iframe/popup yönetimi için güçlendirilmiş XPath ve CSS seçiciler.

Rest Assured Framework: Request/Response Spec Builder kullanımı ve JSON Schema Validation entegrasyonu.
