

# Building a Java App: From Source Code to Windows Installer

Building this application is a "vertical slice" of software engineering. You aren't just writing code; you are managing the entire **Lifecycle** of a product.

Here are the core concepts you mastered in this tutorial, organized by stage:

---

### 1. The Java Transformation Pipeline

Java is unique because it doesn't run directly on your hardware. It goes through a multi-step transformation:

* **Source Code (`.java`):** Human-readable text.
* **Bytecode (`.class`):** A "middle-man" language. It isn't quite human-readable, but it isn't "machine code" (ones and zeros) that your CPU understands yet. This allows Java to be "Write Once, Run Anywhere."
* **The JAR (Java ARchive):** A package (like a `.zip` file) that bundles all your `.class` files together so you don't have to move dozens of tiny files around.

---

### 2. The JRE vs. JDK Distinction

This is a common hurdle for new developers.

* **JDK (Java Development Kit):** The "Toolbox." It contains the compiler (`javac`) and the packager (`jpackage`). Only the **Developer** needs this.
* **JRE (Java Runtime Environment):** The "Engine." It’s the part of the JDK that actually runs the code. Every **User** needs this to play the app.
* **Concept Learned:** By "bundling" the JRE, you ensure the user doesn't have to go find and install the "Engine" themselves.

---

### 3. Native Packaging (The "Bridge")

Computers don't naturally know what to do with a `.jar` file. They *do* know what to do with an `.exe`.

* **jpackage:** This tool acts as a bridge. It takes your platform-independent Java code and wraps it in a platform-specific "wrapper."
* **WiX Toolset:** This handles the "Windows-specific" parts, like creating a shortcut on your Desktop and adding the app to your "Add/Remove Programs" list.

---

### 4. Swing and GUI Basics

The code you pasted uses a library called **Swing**. You learned three fundamental UI concepts:

* **Containers (`JFrame`, `JPanel`):** The "boxes" that hold your elements.
* **Layout Managers (`BorderLayout`, `GridLayout`):** The rules that tell the buttons where to stay when you resize the window.
* **Event Listeners:** The logic that says "When *X* happens (button click), do *Y* (show popup)."

---

### 5. Summary Table

| Concept | What it solves |
| --- | --- |
| **Bytecode** | Allows your app to run on Windows, Mac, or Linux without changing code. |
| **Artifacts** | Organizes your mess of code into a single, professional file. |
| **Bundling** | Eliminates the "It works on my machine!" problem by bringing the environment with the app. |
| **JIT Compilation** | Makes Java fast by converting bytecode to CPU-specific instructions while the app is running. |

---

### Using IntelliJ IDEA Community Edition

## 🎯 What You Will Learn

In this guide, you will play **TWO roles** on the same Windows machine:

* **DEVELOPER:** Write code, compile it, build a JAR, and create an installer.
* **USER:** Install and run the finished app, just like your end-users would.

By the end, you will understand how Java source code becomes a working, standalone Windows application.

---

## 📋 Prerequisites

Before you start, ensure you have the following installed:

### 1. Java Development Kit (JDK) 21

You need a full JDK, not just a JRE. Run these commands in PowerShell to verify:

```powershell
java -version
javac -version
jar --version
jpackage --version

```

> 💡 **TIP:** If `jpackage` is not found, you likely have a JRE only. Download the full JDK 21 from [Adoptium](https://adoptium.net).

### 2. WiX Toolset

Needed to build `.exe` files. Install it via PowerShell:

```powershell
dotnet tool install wix --global

```

> ⚠️ **WARNING:** You must **restart your computer** after installing WiX before `jpackage` will recognize it.

### 3. IntelliJ IDEA Community Edition

Download for free from [JetBrains](https://www.jetbrains.com/idea/download).

---

## 🧑‍💻 Part 1 — Developer Role: Create the Project

### 1. Create a New Project

1. Open IntelliJ IDEA and click **New Project**.
2. Use the following settings:

| Setting | Value |
| --- | --- |
| **Language** | Java |
| **Build system** | IntelliJ (NOT Maven or Gradle) |
| **JDK** | 21 (Click "Add JDK" if not listed) |
| **Project name** | `HelloWorldApp` |
| **Location** | `C:\JavaProjects\HelloWorldApp` |

### 2. Write the Java Source Code

1. In the Project panel, expand `src`.
2. Right-click `src` → **New** → **Java Class**. Name it `HelloWorldApp`.
3. Paste the following code:

```java
import javax.swing.*;
import java.awt.*;

public class HelloWorldApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello World App v1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 250));

        JLabel titleLabel = new JLabel("Hello World!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 42));
        titleLabel.setForeground(new Color(102, 126, 234));

        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        infoPanel.setBackground(new Color(240, 240, 250));

        JLabel javaVersion = new JLabel("Java: " + System.getProperty("java.version"), SwingConstants.CENTER);
        JLabel osName     = new JLabel("OS: "   + System.getProperty("os.name"),    SwingConstants.CENTER);
        JLabel userName   = new JLabel("User: " + System.getProperty("user.name"),  SwingConstants.CENTER);
        JLabel javaHome   = new JLabel("Home: " + System.getProperty("java.home"),  SwingConstants.CENTER);

        infoPanel.add(javaVersion);
        infoPanel.add(osName);
        infoPanel.add(userName);
        infoPanel.add(javaHome);

        JButton button = new JButton("Click Me!");
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(102, 126, 234));
        button.setForeground(Color.WHITE);
        button.addActionListener(e ->
            JOptionPane.showMessageDialog(frame, "Hello from Java!", "Success!", JOptionPane.INFORMATION_MESSAGE)
        );

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(infoPanel,  BorderLayout.CENTER);
        mainPanel.add(button,      BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

```

### 3. Compile and Run

* Click the green **Run** button (▶) or press `Shift + F10`.
* **Result:** A window titled "Hello World App v1.0" appears.
<img width="533" height="486" alt="image" src="https://github.com/user-attachments/assets/0a068c20-2e17-4dd6-933e-20338f7cbe80" />

---

## 📦 Part 2 — Developer Role: Package into a JAR

### 4. Create a JAR Artifact

1. Go to **File** → **Project Structure** (`Ctrl + Alt + Shift + S`).
2. Click **Artifacts** → **+** → **JAR** → **From modules with dependencies**.
3. Select `HelloWorldApp` as the Main Class. Click **OK**.
4. Build it: **Build** → **Build Artifacts** → **HelloWorldApp:jar** → **Build**.

> ✅ **Location:** `out\artifacts\HelloWorldApp_jar\HelloWorldApp.jar`

---

## 🎁 Part 3 — Developer Role: Create the Installer

### 5. Prepare the Input Folder

Open the IntelliJ Terminal (`Alt + F12`) and run:

```powershell
mkdir input
copy out\artifacts\HelloWorldApp_jar\HelloWorldApp.jar input\

```

### 6. Run jpackage

Run the following command to bundle your app with its own JRE:

```powershell
jpackage `
  --input input `
  --name HelloWorldApp `
  --main-jar HelloWorldApp.jar `
  --main-class HelloWorldApp `
  --type exe `
  --app-version 1.0 `
  --description "Hello World Java Application" `
  --vendor "Your Name" `
  --win-menu `
  --win-shortcut `
  --win-dir-chooser

```

> ✅ **Result:** `HelloWorldApp-1.0.exe` (~52 MB) is created in your project root.

---

## 👤 Part 4 — User Role: Install and Run

1. **Double-click** `HelloWorldApp-1.0.exe`.
2. Follow the Setup Wizard (Click Next, Install, Finish).
3. **Verify:** The app window shows its Java Home as `C:\Program Files\HelloWorldApp\runtime\`. This proves it is using its own bundled Java!

---

## 🔁 Part 5 — Understanding the Process

### Why Bundle the JRE?

| Feature | Without JRE (JAR only) | With JRE (EXE Installer) |
| --- | --- | --- |
| **Ease of Use** | User must install Java manually | One-click install |
| **Reliability** | Version conflicts possible | Always uses the correct version |
| **Portability** | Requires external setup | Works out-of-the-box |

---

## 🔧 Part 6 — Troubleshooting

* **`javac` not recognized:** Reinstall JDK 21 and check "Add to PATH".
* **WiX not found:** Ensure you installed the WiX toolset and **restarted your PC**.
* **Red errors in code:** Ensure your filename is exactly `HelloWorldApp.java` (case sensitive).

## 🧹 Part 7 — Uninstall

To clean up, go to **Windows Settings** → **Apps** → **Installed Apps**, search for `HelloWorldApp`, and click **Uninstall**.

---
