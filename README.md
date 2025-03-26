# 🚀 Email Intel

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
📹 [Video Demo](https://github.com/ewfx/gaied-ai-mavericks/tree/main/artifacts/demo)  
🖼️ Screenshots:
1. **Recieved Support Emails**
![Recieved Support Emails](artifacts/screenshots/Received%20Support%20Emails.png)

2. **Categorize and Create JIRAs**
![Categorize and Create JIRAs](artifacts/screenshots/Respective%20JIRAs%20created%20with%20categorization.png)

3. **Reply back to user with JIRA ticket**
![Reply back to user with JIRA ticket](artifacts/screenshots/Reply%20to%20reporter%20with%20ticket%20details.png)
![Detailed view of replied email](artifacts/screenshots/Detailed%20email.png)

4. **Detailed JIRA Story**
![Detailed JIRA story](artifacts/screenshots/Detailed%20JIRA%20story.png)

![Screenshot 1](link-to-image)

## 💡 Inspiration
1. **Automating Email-Based Support**  
   - Reduce manual effort in reading emails, classifying requests, and creating Jira tickets.  

2. **AI-Powered Email Classification**  
   - Use OpenAI to intelligently categorize emails instead of relying on basic keyword matching.  

3. **Seamless Jira Integration**  
   - Automate ticket creation, categorization, and attachment handling in Jira for faster issue resolution.  

4. **Improving Response Time**  
   - Send instant auto-replies with Jira ticket details to enhance user experience.  

5. **Scalability & Performance**  
   - Handle a large volume of emails efficiently with automated workflows.  

## ⚙️ What It Does
	Emails are classified into one of the categories using OpenAI.
	•	The appropriate category & subcategory are assigned to Jira tickets.
	•	Labels are added in Jira based on the classification.

📌 Example Classification:
- 🔹 Email Content: “I’m getting a 401 error when calling the API.”
- 🔹 Classified As: Category: API Access Issues | Subcategory: 401 Unauthorized
- 🔹 Jira Labels: API_Access_Issues, 401_Unauthorized

A Gen AI model will automate email classification by:
	- 1.	Reading and understanding the email content and attachments using NLP and AI models.
	- 2.	Identifying intent and categorizing emails into Request Type & Sub Request Type.
	- 3.	Extracting key attributes required for processing.
	- 4.	Auto-assigning the request to the appropriate team/individual.
	- 5.	Integrating with the loan servicing platform to generate service requests.

## 🛠️ How We Built It
	•	Gen AI & NLP Models (for understanding emails)
	•	Machine Learning Classification Models (for request classification)
	•	OCR (for reading text from attachments)
	•	Spring Boot 3, Java 17 (for backend integration)
	•	Jira API (for ticket creation & management)

## 🚧 Challenges We Faced

#### 🔹 1. Gmail IMAP Email Processing  
**Challenge:** Extracting text correctly from plain text, HTML, and multipart emails.  
**Solution:** Used `JavaMail` with `Jsoup` to clean HTML and parse multipart emails.  

#### 🔹 2. OpenAI API Integration  
**Challenge:** API requires `messages` format instead of `prompt`.  
**Solution:** Used `v1/chat/completions` and `ObjectMapper` for proper JSON formatting.  

#### 🔹 3. Jira API Authentication  
**Challenge:** Jira requires `accountId`, and GitHub removed password authentication.  
**Solution:** Used Jira API Token (PAT) and retrieved `accountId` via `/myself` API.  

#### 🔹 4. Handling Attachments in Emails  
**Challenge:** Extracting and uploading email attachments to Jira.  
**Solution:** Saved attachments locally and used `X-Atlassian-Token: no-check` for Jira uploads.   

#### 🔹 5. Performance Optimization  
**Challenge:** Frequent polling could cause performance issues.  
**Solution:** Optimized polling to run every 60 seconds and used async processing for Jira. 

## 🏃 How to Run
1. Clone the repository  
   ```sh
   git clone https://github.com/ewfx/gaied-ai-mavericks.git
   ```
2. Install dependencies  
   ```sh
   mvn clean install
   ```
3. Run the project  
   ```sh
   ./mvnw spring-boot:run
   ```

## 🏗️ Tech Stack
- 🔹 Backend: Java 17 / Spring Boot
- 🔹 Other: OpenAI API / Jira API / GMAIL IMAP API

## 👥 Team
- **Spallya Omar** 	  - [GitHub](https://github.com/spallya) | [LinkedIn](https://in.linkedin.com/in/spallya-omar)
- **Srinivas Dingari**    - [GitHub](https://github.com/srinivasd9) | [LinkedIn](https://www.linkedin.com/in/srinivasdingari)
- **Harshitha Marupaka**  - [GitHub](https://github.com/harshitha27) | [LinkedIn](https://in.linkedin.com/in/harshitha-alka-51871916a)
- **Rana AnveshReddy Yeramareddy**  - [GitHub](#) | [LinkedIn](#)
- **Shivakishore Cherupally**  - [GitHub](#) | [LinkedIn](#)

