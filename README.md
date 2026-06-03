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
```

## 🛠️ Getting Started

### Prerequisites
* **Java 21** or higher
* **Maven** (included via wrapper)
* **Chrome / ChromeDriver** installed locally

### Building from Source

To compile the source code (including all 300+ actions) and skip tests:
```bash
.\mvnw.cmd clean compile
```

To run the agent locally using Spring Boot:
```bash
.\mvnw.cmd spring-boot:run
```

### Building the Windows Installer (.msi)

To package the agent into a native Windows Installer (`.msi`), you need the [WiX Toolset](https://wixtoolset.org/) installed. Run the native profile:

```bash
.\mvnw.cmd clean package -Pnative-installer "-Dmaven.test.skip=true"
```
The resulting `.msi` file will be generated in the root of the project.

## ⚡ How It Works

1. **Agent Setup:** The user installs the MSI and enters their Organization token via the Java Swing Applet.
2. **Polling:** `AgentPollingService` reaches out to the cloud backend every 10 seconds to request pending test runs.
3. **Execution:** When a test is received, `ExecutionService` spins up a WebDriver.
4. **Action Routing:** Each step in the test case is matched via the `ActionRegistry` and routed to its dedicated class (e.g., `ClickAction`, `TouchSwipeLeftAction`) for execution.
5. **Reporting:** Results, logs, and screenshots are securely zipped and posted back to the AutoPropel Cloud Dashboard.
