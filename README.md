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

✅ Real image captioning
✅ Anime image tagging
✅ Batch image upload
✅ Individual caption export for fine-tuning
✅ Database storage
✅ REST API architecture

---

# Project Structure

AI-Caption-Generator/

├── spring-backend/

└── ai-server/

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

## 1. Clone repository

git clone [https://github.com/Rudra973592/AI-Caption-Generator.git](https://github.com/Rudra973592/AI-Caption-Generator.git)

cd AI-Caption-Generator

---

## 2. Start MySQL

Create database:

CREATE DATABASE caption_db;

Update:

spring-backend/src/main/resources/application.properties

with your MySQL username and password.

---

## 3. Start AI Server

Open terminal:

cd ai-server

Install dependencies:

pip install fastapi uvicorn transformers torch pillow onnxruntime huggingface_hub

Run:

python -m uvicorn ai_server:app --reload

AI server runs at:

[http://127.0.0.1:8000](http://127.0.0.1:8000)

---

## 4. Start Spring Boot Backend

Open second terminal:

cd spring-backend

Run (Windows):

gradlew bootRun

Backend runs at:

[http://localhost:8080](http://localhost:8080)

---

# Usage

Open browser:

http://localhost:8080/index.html

Then:

1. Upload images
2. Select mode:

   * Real Mode
   * Anime Mode
3. Click Generate Captions
4. Download captions for fine-tuning datasets

---

# API Endpoints

## Generate captions

POST /upload-multiple

## View stored captions

GET /captions

Example:

[http://localhost:8080/captions](http://localhost:8080/captions)

---

# Architecture

User → Frontend → Spring Boot → FastAPI → AI Models → MySQL

---

# Future Scope

* User authentication
* Caption history
* Cloud deployment
* Identity-aware caption generation

---

# Author

Rudra Jani

