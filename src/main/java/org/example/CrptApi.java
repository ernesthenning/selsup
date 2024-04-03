package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class CrptApi {

    private static final String BASE_URL = "https://ismp.crpt.ru/api/v3/documents/create";
    private final TimeUnit timeUnit;
    private int requestLimit;
    private AtomicInteger counter = new AtomicInteger();
    private ReentrantLock lock = new ReentrantLock();

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.timeUnit = timeUnit;
        this.requestLimit = requestLimit;
    }

    private void makeRequest(Root document) {
        try {
            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            URL url = new URL(BASE_URL);
            ObjectMapper om = new ObjectMapper();
            String jsonDocument = om.writeValueAsString(document);
            HttpsURLConnection conn = getHttpsURLConnection(url, sslSocketFactory, jsonDocument);
            conn.connect();
        } catch (IOException i){
            i.printStackTrace();
        }
        counter.incrementAndGet();
    }

    private static HttpsURLConnection getHttpsURLConnection(URL url, SSLSocketFactory sslSocketFactory, String jsonDocument) throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setSSLSocketFactory(sslSocketFactory);
        conn.setDoOutput(true);
        try (OutputStream os = conn.getOutputStream()){
            byte[] input = jsonDocument.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return conn;
    }

    public void makeRequestIfLimitNotExceeded(Root document) {
        if (counter.get() < requestLimit) {
            makeRequest(document);
        } else {
            try {
                lock.wait(timeUnit.toMillis(1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public class Description {

        public Description() {

        }

        @JsonProperty("participantInn")
        private String participantInn;
        @JsonProperty("participantInn")
        public String getParticipantInn() {
            return this.participantInn;
        }
        @JsonProperty("participantInn")
        public void setParticipantInn(String participantInn) {
            this.participantInn = participantInn; }

    }

    public class Product {

        public Product() {

        }

        @JsonProperty("certificate_document")
        private String certificate_document;
        @JsonProperty("certificate_document")
        public String getCertificate_document() {
            return this.certificate_document;
        }
        @JsonProperty("certificate_document")
        public void setCertificate_document(String certificate_document) {
            this.certificate_document = certificate_document; }

        @JsonProperty("certificate_document_date")
        private String certificate_document_date;
        @JsonProperty("certificate_document_date")
        public String getCertificate_document_date() {
            return this.certificate_document_date;
        }
        @JsonProperty("certificate_document_date")
        public void setCertificate_document_date(String certificate_document_date) {
            this.certificate_document_date = certificate_document_date; }

        @JsonProperty("certificate_document_number")
        public String getCertificate_document_number() {
            return this.certificate_document_number; }
        @JsonProperty("certificate_document_number")
        public void setCertificate_document_number(String certificate_document_number) {
            this.certificate_document_number = certificate_document_number; }
        @JsonProperty("certificate_document_number")
        String certificate_document_number;
        @JsonProperty("owner_inn")
        public String getOwner_inn() {
            return this.owner_inn; }
        @JsonProperty("owner_inn")
        public void setOwner_inn(String owner_inn) {
            this.owner_inn = owner_inn; }
        @JsonProperty("owner_inn")
        String owner_inn;
        @JsonProperty("producer_inn")
        public String getProducer_inn() {
            return this.producer_inn; }
        @JsonProperty("producer_inn")
        public void setProducer_inn(String producer_inn) {
            this.producer_inn = producer_inn; }
        @JsonProperty("producer_inn")
        String producer_inn;
        @JsonProperty("production_date")
        public String getProduction_date() {
            return this.production_date; }
        @JsonProperty("production_date")
        public void setProduction_date(String production_date) {
            this.production_date = production_date; }
        @JsonProperty("production_date")
        String production_date;
        @JsonProperty("tnved_code")
        public String getTnved_code() {
            return this.tnved_code; }
        @JsonProperty("tnved_code")
        public void setTnved_code(String tnved_code) {
            this.tnved_code = tnved_code; }
        @JsonProperty("tnved_code")
        String tnved_code;
        @JsonProperty("uit_code")
        public String getUit_code() {
            return this.uit_code; }
        @JsonProperty("uit_code")
        public void setUit_code(String uit_code) {
            this.uit_code = uit_code; }
        @JsonProperty("uit_code")
        String uit_code;
        @JsonProperty("uitu_code")
        public String getUitu_code() {
            return this.uitu_code; }
        @JsonProperty("uitu_code")
        public void setUitu_code(String uitu_code) {
            this.uitu_code = uitu_code; }
        @JsonProperty("uitu_code")
        String uitu_code;
    }

    public class Root {

        public Root() {
        }
        @JsonProperty("description")
        public Description getDescription() {
            return this.description;
        }
        @JsonProperty("description")
        public void setDescription(Description description) {
            this.description = description;
        }
        @JsonProperty("description")
        Description description;
        @JsonProperty("doc_id")
        public String getDoc_id() {
            return this.doc_id;
        }
        @JsonProperty("doc_id")
        public void setDoc_id(String doc_id) {
            this.doc_id = doc_id; }
        @JsonProperty("doc_id")
        String doc_id;
        @JsonProperty("doc_status")
        public String getDoc_status() {
            return this.doc_status;
        }
        @JsonProperty("doc_status")
        public void setDoc_status(String doc_status) {
            this.doc_status = doc_status; }
        @JsonProperty("doc_status")
        String doc_status;
        @JsonProperty("doc_type")
        public String getDoc_type() {
            return this.doc_type;
        }
        @JsonProperty("doc_type")
        public void setDoc_type(String doc_type) {
            this.doc_type = doc_type; }
        @JsonProperty("doc_type")
        String doc_type;
        @JsonProperty("importRequest")
        public String getImportRequest() {
            return this.importRequest;
        }
        @JsonProperty("importRequest")
        public void setImportRequest(String importRequest) {
            this.importRequest = importRequest; }
        @JsonProperty("importRequest")
        String importRequest;
        @JsonProperty("owner_inn")
        public String getOwner_inn() {
            return this.owner_inn;
        }
        @JsonProperty("owner_inn")
        public void setOwner_inn(String owner_inn) {
            this.owner_inn = owner_inn; }
        @JsonProperty("owner_inn")
        String owner_inn;
        @JsonProperty("participant_inn")
        public String getParticipant_inn() {
            return this.participant_inn;
        }
        @JsonProperty("participant_inn")
        public void setParticipant_inn(String participant_inn) {
            this.participant_inn = participant_inn;
        }
        @JsonProperty("participant_inn")
        String participant_inn;
        @JsonProperty("producer_inn")
        public String getProducer_inn() {
            return this.producer_inn; }
        @JsonProperty("producer_inn")
        public void setProducer_inn(String producer_inn) {
            this.producer_inn = producer_inn; }
        @JsonProperty("producer_inn")
        String producer_inn;
        @JsonProperty("production_date")
        public String getProduction_date() {
            return this.production_date; }
        @JsonProperty("production_date")
        public void setProduction_date(String production_date) {
            this.production_date = production_date; }
        @JsonProperty("production_date")
        String production_date;
        @JsonProperty("production_type")
        public String getProduction_type() {
            return this.production_type; }
        @JsonProperty("production_type")
        public void setProduction_type(String production_type) {
            this.production_type = production_type; }
        @JsonProperty("production_type")
        String production_type;
        @JsonProperty("products")
        public ArrayList<Product> getProducts() {
            return this.products; }
        @JsonProperty("products")
        public void setProducts(ArrayList<Product> products) {
            this.products = products; }
        @JsonProperty("products")
        ArrayList<Product> products;
        @JsonProperty("reg_date")
        public String getReg_date() {
            return this.reg_date; }
        @JsonProperty("reg_date")
        public void setReg_date(String reg_date) {
            this.reg_date = reg_date; }
        @JsonProperty("reg_date")
        String reg_date;
        @JsonProperty("reg_number")
        public String getReg_number() {
            return this.reg_number; }
        @JsonProperty("reg_number")
        public void setReg_number(String reg_number) {
            this.reg_number = reg_number; }
        @JsonProperty("reg_number")
        String reg_number;
    }

}
