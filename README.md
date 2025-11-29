# ✈️ Enuygun QA Automation Engineer Technical Assessment Project

Bu proje, Enuygun Teknik Değerlendirme Süreci kapsamında geliştirilmiş bir otomasyon projesidir. Proje, hem **Web UI Otomasyonu** hem de **API Otomasyonu** görevlerini kapsamaktadır.

## 🛠️ Proje Teknolojileri

* **Programlama Dili:** Java
* **Test Çatısı:** Cucumber (BDD)
* **Web Otomasyonu:** Selenium WebDriver
* **API Otomasyonu:** Rest Assured
* **Bağımlılık Yönetimi:** Maven
* **Raporlama:** Allure
* **Diğer Yardımcılar:** Lombok, Log4j

## 🎯 Proje Kapsamı

### Bölüm 1: Web UI Uçuş Arama ve Veri Analizi

**Senaryo:** Belirlenen bir rota için uçuş araması yapılır, gelen uçuş listesi sayfasındaki temel uçuş verileri (Havayolu, Fiyat, Kalkış Saati vb.) alınır.

**Yapılan İşlemler:**
1.  Uçuş listesi verileri toplanır.
2.  Toplanan veriler **CSV dosyasına** kaydedilir.
3.  `FlightAnalyzer` sınıfı kullanılarak uçuş fiyatları üzerinde **Min, Max, Ortalama** analizleri yapılır.
4.  Görselleştirme simülasyonu ile en ucuz ve en pahalı havayolları belirlenir.

### Bölüm 2: API Testleri

Bu bölüm, temel REST API otomasyon yeteneklerini göstermektedir.

**Senaryolar:**
* **PetStore API:** Mevcut bir pet'in sorgulanması (`GET /pet/{petId}`) ve API yanıtının doğrulanması.
* **Flight Search API (Simülasyon):** Uçuş arama servisine istek atılması ve temel yanıt doğrulama adımları.

## 🚀 Projeyi Çalıştırma

Proje, Maven tabanlı olduğu için aşağıdaki komut ile doğrudan çalıştırılabilir:

```bash
mvn clean test
