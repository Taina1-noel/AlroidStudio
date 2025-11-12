# ✏️ Activity 2 & 3: StudySync Product Specification and Wireframes

## 1. App Overview
**App Name:** StudySync  
**Purpose:** StudySync is designed to help students stay focused, organized, and motivated during study sessions. It provides tools like a customizable timer, flashcards, motivational quotes, and study music integrations to make studying more productive and enjoyable.

---

## 2. Problem Statement
Students often struggle with time management, motivation, and access to effective study tools. StudySync solves this by combining multiple study resources into one interactive, easy-to-use app.

---

## 3. Target Audience
- College students  
- High school students  
- Independent learners  
- Anyone preparing for exams or certifications

---

## 4. Core Features
1. **Pomodoro Timer:** Helps students structure study and break intervals.  
2. **Motivational Quotes:** Daily quotes pulled from the ZenQuotes API to boost focus and morale.  
3. **Flashcards:** Users can create, edit, and view flashcards using a RecyclerView list.  
4. **Study Music Integration:** Connects to Spotify or YouTube Music API for curated study playlists.  
5. **Progress Tracker:** Displays total hours studied, streaks, and earned badges.

---

## 5. App Requirements

| **Requirement** | **Description** |
|------------------|------------------|
| **Internet Usage** | Fetches quotes and playlists from APIs |
| **API Used** | ZenQuotes API (and optional Spotify API) |
| **RecyclerView** | Displays list of flashcards or study sessions |
| **User Interactions** | Clicks, long-press edits/deletes, text input for flashcards, navigation between screens |
| **Custom Styling** | Consistent green and blue theme, rounded buttons, card-style layouts |

---

## 6. App Navigation Structure
- **Home Screen:** Start Study Session button, motivational quote, navigation bar.  
- **Timer Screen:** Circular timer, start/pause/reset buttons, progress bar.  
- **Flashcards Screen:** Scrollable RecyclerView with Add/Edit functions.  
- **Music Screen:** Displays study playlists fetched via API.  
- **Profile Screen:** Shows total hours, streaks, and badges.

---

## 7. Technical Implementation
- **Language:** Kotlin  
- **Framework:** Android Studio  
- **APIs:** ZenQuotes.io (Quotes) and Spotify Web API  
- **UI Components:** RecyclerView, CardView, Navigation Component, SharedPreferences  
- **Data Storage:** Firebase or Room Database for user-created flashcards

---

## 8. Visual Design Guidelines
- **Primary Colors:** #16A34A (Green) and #2563EB (Blue)  
- **Typography:** Sans-serif (bold headers, regular body)  
- **Layout:** Minimal, accessible, and card-based interface

---

## 9. Team Roles
| **Member** | **Role** | **Responsibility** |
|-------------|----------|--------------------|
| Taina Noel | Project Manager | Organize milestones, oversee documentation |
| Jade Cook | Developer | Implements RecyclerView and APIs |
| Ariana Montgomery | UI/UX Designer | Creates app layout and styling |

---

## 10. Wireframes
Below are the low-fidelity wireframes for **StudySync**.

![StudySync Wireframe](./IMG_6619.jpg)

---

**Submission Date:** November 13, 2025  
**Course:** Android Development (Unit 8 – Milestone 1)  
**App Name:** StudySync  
**Team:** AlroidStudio  
