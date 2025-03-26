# 🚀 Project Name

## 📌 Table of Contents
- [Introduction](#introduction)
- [Demo](#demo)
- [Inspiration](#inspiration)
- [What It Does](#what-it-does)
- [How We Built It](#how-we-built-it)
- [Challenges We Faced](#challenges-we-faced)
- [How to Run](#how-to-run)
- [Tech Stack](#tech-stack)
- [Team](#team)

---x

## 🎯 Introduction
Designing a Gen AI-Based Email Classification System to automate the processing of servicing requests received via email. Emails will be classified to below category and sub categories

Categories & Subcategories Mapping:
| **Category**                | **Subcategories** |
|-----------------------------|------------------|
| **Authentication Issues**    | Invalid Credentials, Two-Factor Authentication (2FA) Issues, Password Reset, Account Lockout |
| **API Access Issues**        | 401 Unauthorized, 403 Forbidden, API Key Not Working, Rate Limit Exceeded, Missing Permissions |
| **Payment & Billing**        | Payment Failure, Subscription Issues, Invoice Requests, Refund Requests |
| **Technical Issues**         | Server Downtime, Slow Response Time, Database Errors, Integration Issues |
| **Product Support**          | Feature Requests, Bug Reports, Performance Optimization, Usage Guidelines |
| **General Inquiries**        | Account Information, Company Policies, Partnership Opportunities |


## 🎥 Demo
🔗 [Live Demo](#) (if applicable)  
📹 [Video Demo](#) (if applicable)  
🖼️ Screenshots:

![Screenshot 1](link-to-image)

## 💡 Inspiration
What inspired you to create this project? Describe the problem you're solving.

## ⚙️ What It Does
	Emails are classified into one of the categories using OpenAI.
	•	The appropriate category & subcategory are assigned to Jira tickets.
	•	Labels are added in Jira based on the classification.

📌 Example Classification:
🔹 Email Content: “I’m getting a 401 error when calling the API.”
🔹 Classified As: Category: API Access Issues | Subcategory: 401 Unauthorized
🔹 Jira Labels: API_Access_Issues, 401_Unauthorized

A Gen AI model will automate email classification by:
	1.	Reading and understanding the email content and attachments using NLP and AI models.
	2.	Identifying intent and categorizing emails into Request Type & Sub Request Type.
	3.	Extracting key attributes required for processing.
	4.	Auto-assigning the request to the appropriate team/individual.
	5.	Integrating with the loan servicing platform to generate service requests.

## 🛠️ How We Built It
	•	Gen AI & NLP Models (for understanding emails)
	•	Machine Learning Classification Models (for request classification)
	•	OCR (for reading text from attachments)
	•	Spring Boot 3, Java 17 (for backend integration)
	•	Jira API (for ticket creation & management)

## 🚧 Challenges We Faced
Describe the major technical or non-technical challenges your team encountered.

## 🏃 How to Run
1. Clone the repository  
   ```sh
   git clone https://github.com/your-repo.git
   ```
2. Install dependencies  
   ```sh
   npm install  # or pip install -r requirements.txt (for Python)
   ```
3. Run the project  
   ```sh
   npm start  # or python app.py
   ```

## 🏗️ Tech Stack
- 🔹 Frontend: React / Vue / Angular
- 🔹 Backend: Node.js / FastAPI / Django
- 🔹 Database: PostgreSQL / Firebase
- 🔹 Other: OpenAI API / Twilio / Stripe

## 👥 Team
- **Your Name** - [GitHub](#) | [LinkedIn](#)
- **Teammate 2** - [GitHub](#) | [LinkedIn](#)
