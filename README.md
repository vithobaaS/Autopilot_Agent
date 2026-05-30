# Autopilot Local Agent 🚀

The **Autopilot Local Agent** is an enterprise-grade, lightweight Java execution engine built with Spring Boot 3.4 and Selenium. It securely polls the AutoPropel Cloud Dashboard for automated test execution instructions and runs them flawlessly on your local machine or dedicated test server.

This repository contains the completely refactored, layered architecture for the execution engine, featuring a highly optimized **Action Framework** supporting over **300+ automation capabilities**.

---

## 🌟 Key Features

* **300+ Action Framework:** Supports everything from basic interactions (Click, Type) to highly advanced domains including Shadow DOM, iFrames, API Mocking, Mobile Emulation, Visual Testing, and SQL Databases.
* **O(1) Command Execution:** The monolithic `if-else` execution block was entirely replaced by an `ActionRegistry` that dynamically routes test steps to specialized `ActionHandler` implementations in micro-seconds.
* **Layered Architecture:** Clean code separation across `controller`, `service`, `action`, `ui`, and `config` layers.
* **Smart Polling:** Lightweight API polling keeps your local machine synchronized with cloud-scheduled executions.
* **Windows Native Installer:** Shipped with a pre-configured WiX Toolset integration to build seamless `.msi` Windows installers (`AutopilotAgent-1.0.0.msi`).

## 📁 Project Structure

```text
localagent-java/
├── src/main/java/com/autopropel/localagent_java/
│   ├── action/         # Core Action Framework (ActionHandler, ActionRegistry)
│   │   └── impl/       # Contains 300+ isolated execution classes
│   ├── config/         # WebDriver and Agent configuration
│   ├── controller/     # REST Endpoints (Status, Health)
│   ├── exception/      # Global error handling
│   ├── service/        # Business Logic (ExecutionService, AgentPollingService)
│   └── ui/             # Native Java Swing UI for Agent Configuration
└── pom.xml             # Maven Configuration
