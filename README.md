# AI Caption Generator 

An AI-powered image captioning system for real and anime images, designed for dataset creation and fine-tuning workflows.

This project combines:

* Spring Boot backend
* FastAPI AI microservice
* MySQL database
* Florence-2 for real image captions
* WD Tagger for anime image captions

---

# Features

➤ Real image captioning <br>
➤ Anime image tagging <br>
➤ Batch image upload <br>
➤ Individual caption export for fine-tuning <br>
➤ Database storage <br>
➤ REST API architecture <br>

---

# Project Structure

```text
AI-Caption-Generator/
├── spring-backend/
└── ai-server/
```

---

# Tech Stack

## Backend

* Java
* Spring Boot
* Spring MVC
* Hibernate
* JPA
* Gradle

## AI Server

* Python
* FastAPI
* Florence-2
* WD Tagger
* ONNX Runtime

## Database

* MySQL

---

# Installation

## 1. Clone Repository

```bash
git clone https://github.com/Rudra973592/AI-Caption-Generator.git
cd AI-Caption-Generator
```

---

## 2. Setup MySQL

Create database:

```sql
CREATE DATABASE caption_db;
```

Then open:

```text
spring-backend/src/main/resources/application.properties
```

Enter your username and password:

```properties
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

---

## 3. Start AI Server

Open terminal:

```bash
cd ai-server
```

Install dependencies:

```bash
pip install fastapi uvicorn torch pillow onnxruntime huggingface_hub
pip uninstall transformers -y
pip install transformers==4.46.3 accelerate sentencepiece -U
```

Important:

```text
Florence-2 requires Transformers version 4.46.3 or newer.
```

Run server:

```bash
python -m uvicorn ai_server:app --reload
```

AI server runs at:

```text
http://127.0.0.1:8000
```

---

## 4. Start Spring Boot Backend

Open second terminal:

```bash
cd spring-backend
```

Run:

```bash
gradlew bootRun
```

Backend runs at:

```text
http://localhost:8080
```

---

## IntelliJ Users

Important:

```text
Open ONLY the "spring-backend" folder as a Gradle project.
Do NOT open the root repository folder.
```

---

# Usage

Open browser:

```text
http://localhost:8080/index.html
```

Then:

1. Upload images
2. Select mode:

   * Real Mode
   * Anime Mode
3. Click Generate Captions
4. Download captions for fine-tuning datasets

---

# API Endpoints

## Generate Captions

```http
POST /upload-multiple
```

## View Stored Captions

```http
GET /captions
```

Example:

```text
http://localhost:8080/captions
```

---

# Architecture

```text
User → Frontend → Spring Boot → FastAPI → AI Models → MySQL
```

---

# Future Scope

* User authentication
* Caption history
* Cloud deployment
* Identity-aware caption generation

---

# Author

**Rudra Jani**

---
