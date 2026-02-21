
Building a Java App
From Source Code to Windows Installer
Using IntelliJ IDEA Community Edition
<img width="533" height="486" alt="image" src="https://github.com/user-attachments/assets/959143a9-eed0-4f28-82c9-6422c9c2c8c6" />


🎯  What You Will Learn
In this guide you will play TWO roles on the same Windows machine:
  •  DEVELOPER — Write code, compile it, build a JAR, and create an installer
  •  USER — Install and run the finished app, just like your end users would

By the end you will understand how Java source code becomes a working Windows application.


📋  Prerequisites — What You Need Before You Start

Java Development Kit (JDK) 21
You need a full JDK installed — not just a JRE. Open PowerShell and run all four of these commands to check:

java -version
javac -version
jar --version
jpackage --version

💡  TIP
If all four commands show version 21.x — you are ready!
If jpackage is not found, you have a JRE only. Download the full JDK 21 from:  https://adoptium.net


WiX Toolset (needed to build .exe files)
Run this in PowerShell to install it automatically:
dotnet tool install wix --global

⚠️  WARNING
You must restart your computer after installing WiX before jpackage will find it.


IntelliJ IDEA Community Edition
Download free from:  https://www.jetbrains.com/idea/download  — choose Community (not Ultimate).


🧑‍💻  Part 1 — Developer Role: Create the Project

1
Create a New Project in IntelliJ


Open IntelliJ IDEA. On the Welcome screen, click New Project.

Setting
Value you should choose
Language
Java
Build system
IntelliJ  (NOT Maven or Gradle)
JDK
21  — click Add JDK if not listed
Project name
HelloWorldApp
Location
C:\JavaProjects\HelloWorldApp


Click Create.
IntelliJ opens and shows the project panel on the left side.

💡  TIP
If JDK 21 does not appear in the dropdown, click 'Add JDK' and browse to your JDK folder.
The default install path is usually:  C:\Program Files\Eclipse Adoptium\jdk-21.x.x-hotspot


2
Write the Java Source Code


In the Project panel on the left, expand src.
Right-click on src  →  New  →  Java Class
Type the name:  HelloWorldApp  and press Enter.
A new file opens in the editor. Delete everything inside it.
Copy and paste the full code below into the file.

⚠️  WARNING
Make sure the class name in the code exactly matches the filename.
Both must be:  HelloWorldApp   (capital H, capital W, capital A — no spaces)


Complete Source Code — paste this into HelloWorldApp.java
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
        mainPanel.add(button,     BorderLayout.SOUTH);


        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

Press  Ctrl + S  to save.

💡  TIP
IntelliJ will underline errors in red as you type — this is normal until the file is complete.
If you see a red underline after pasting, check that the opening and closing braces { } are balanced.


3
Compile and Run — The First Compilation


Click the green ▶ Run button at the top of IntelliJ, or press Shift + F10.

IntelliJ automatically:
Compiles HelloWorldApp.java into bytecode (HelloWorldApp.class)
Runs the bytecode using the JVM
Opens a Hello World window on your screen


💡  TIP
Where is the .class file?  Look in:  out\production\HelloWorldApp\HelloWorldApp.class
You can see it in the Project panel on the left — expand the 'out' folder.


✅  SUCCESS
A window titled 'Hello World App v1.0' should appear on screen.
The app shows your Java version, OS name, username, and Java home path.
Click the 'Click Me!' button — a popup should appear.
Close the window when you are done.



📦  Part 2 — Developer Role: Package into a JAR

4
Create a JAR File Using IntelliJ Artifacts


A JAR file bundles your compiled bytecode into one portable file. Here is how to create one using IntelliJ's built-in tool:

Open Project Structure
Go to the menu:  File  →  Project Structure  (or press Ctrl + Alt + Shift + S)
In the left panel of the dialog, click Artifacts.
Click the  +  (plus) button at the top.
Choose:  JAR  →  From modules with dependencies

Configure the Artifact
Field
What to enter
Module
HelloWorldApp
Main Class
Click the ... button and select HelloWorldApp
JAR file
Leave as default — IntelliJ fills this in automatically


Click OK to close the inner dialog.
You should now see HelloWorldApp:jar listed as an artifact.
Note the Output directory path shown — you will need it in the next step.
Click OK to close Project Structure.

Build the JAR
Go to the menu:  Build  →  Build Artifacts
Click:  HelloWorldApp:jar  →  Build
Wait a few seconds. IntelliJ creates your JAR file.

✅  SUCCESS
Your JAR file now exists at:
out\artifacts\HelloWorldApp_jar\HelloWorldApp.jar


Test the JAR from the IntelliJ Terminal
Open the Terminal inside IntelliJ:  View  →  Tool Windows  →  Terminal   (or press Alt + F12)

Type this command and press Enter:
java -jar out\artifacts\HelloWorldApp_jar\HelloWorldApp.jar

✅  SUCCESS
The Hello World window appears again — this time launched from the JAR.
Close the window before continuing.



🎁  Part 3 — Developer Role: Bundle and Create the Installer

This is the key step. You will use jpackage to wrap your JAR together with a complete Java runtime (JRE) into a single Windows installer (.exe). Users who receive this installer do NOT need Java installed.

5
Prepare the Input Folder


jpackage expects your JAR in a dedicated folder called input. Run these commands in the IntelliJ Terminal:

mkdir input
copy out\artifacts\HelloWorldApp_jar\HelloWorldApp.jar input\

✅  SUCCESS
Verify it worked — you should see HelloWorldApp.jar inside the input folder.


6
Run jpackage to Create the .exe Installer


In the IntelliJ Terminal, paste and run the following command. You can copy it all at once — the backtick character (`) continues the command across multiple lines in PowerShell:

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

⚠️  WARNING
This command takes 2–3 minutes to complete — this is normal! jpackage is:
  •  Bundling your JAR file (3 KB)
  •  Bundling a complete Java runtime (~49 MB)
  •  Compressing everything into one installer
Do not close the terminal while it is running.


✅  SUCCESS
When finished you will see:  HelloWorldApp-1.0.exe  in your project folder.
The file is approximately 52 MB — that is normal because it includes a full JRE.


What Is Inside the Installer?
Component
Size / Description
HelloWorldApp.jar
~3 KB  — your compiled bytecode
Bundled JRE
~49 MB — complete Java runtime, no install needed
HelloWorldApp-1.0.exe
~52 MB — the final installer combining both above



👤  Part 4 — User Role: Install and Run the App

Now switch roles. Pretend you are a user who received this installer. You will install and launch the app just as a real user would.

7
Copy the Installer to Downloads (Simulating a Download)


In the IntelliJ Terminal, run:
copy HelloWorldApp-1.0.exe "$env:USERPROFILE\Downloads\"

Then open File Explorer and navigate to your Downloads folder. You should see HelloWorldApp-1.0.exe there.

8
Run the Installer


Double-click  HelloWorldApp-1.0.exe  in File Explorer.
If Windows shows a 'Windows protected your PC' warning, click  More info  →  Run anyway.
The Setup wizard opens. Follow these steps:

Welcome screen  →  click Next
Choose install location  →  keep the default  (C:\Program Files\HelloWorldApp)  →  click Next
Select options  →  leave both checkboxes ticked  →  click Install
Wait for the progress bar to complete
Completion screen  →  leave 'Launch HelloWorldApp' ticked  →  click Finish

✅  SUCCESS
The Hello World app opens automatically after installation!
You did not need to install Java separately — the app brought its own.


9
Verify the App Uses Its Own Bundled Java


Look at the Java Home value shown in the app window:

Java Home: C:\Program Files\HelloWorldApp\runtime\

Compare this to your system Java:
java -version   →   C:\Program Files\Eclipse Adoptium\jdk-21.x.x...

✅  SUCCESS
They are DIFFERENT paths — the app is using its own private, bundled Java runtime.
This means the app would work even on a computer with no Java installed at all.


Launch the App from Different Locations
Desktop shortcut — double-click the HelloWorldApp icon
Start Menu — press Windows key, type Hello, click HelloWorldApp
Direct path — browse to C:\Program Files\HelloWorldApp\ and double-click HelloWorldApp.exe


🔁  Part 5 — Understanding What Happened

The Two Compilations Explained

First Compilation (javac)
Second Compilation (JIT)
Done by YOU, the developer
Done automatically at runtime
Happens once when you build
Happens every time the app starts
javac converts .java → .class
JVM converts .class → native CPU code
Produces platform-independent bytecode
Produces code optimised for THIS specific CPU
Stored in your project's out/ folder
Lives only in memory while app runs


Why Bundle the JRE?

Without JRE Bundle (JAR only)
With JRE Bundle (EXE installer)
User must find and install Java
User just double-clicks and clicks Next
User must have the correct version
Correct version is always included
Many users fail or give up
Works for 100% of users instantly
Download: your JAR (3 KB) + Java (100 MB)
Download: one installer (~52 MB)


Where All the Files Live

Your Developer Files  (C:\JavaProjects\HelloWorldApp\)
File
What it is
HelloWorldApp.java
Your source code — human-readable text
out\...\HelloWorldApp.class
Compiled bytecode — platform independent
out\...\HelloWorldApp.jar
Packaged bytecode — portable archive
HelloWorldApp-1.0.exe
Final installer — bundles JAR + JRE


The Installed App  (C:\Program Files\HelloWorldApp\)
File / Folder
What it does
HelloWorldApp.exe
Native Windows launcher — starts the app
app\HelloWorldApp.jar
Your bytecode (~3 KB)
runtime\bin\javaw.exe
The bundled Java runtime
runtime\lib\
Java libraries required to run the app



🔧  Part 6 — Troubleshooting Common Problems

Problem: javac is not recognised
⚠️  WARNING
Symptom:  'javac' is not recognized as an internal or external command
Cause:  JDK is not installed, or its bin folder is not on the PATH
Fix:  Reinstall JDK 21 from adoptium.net and make sure 'Add to PATH' is checked during install.
Then close and reopen IntelliJ.


Problem: jpackage cannot find WiX
⚠️  WARNING
Symptom:  Error: WiX toolset not found, or similar message
Cause:  WiX Toolset is not installed
Fix:  Run in PowerShell:    winget install WiXToolset.WiX
Then RESTART your computer and try the jpackage command again.


Problem: The app shows the wrong Java Home
⚠️  WARNING
Symptom:  Java Home in the app window shows your system JDK path, not the runtime\ folder
Cause:  You ran the JAR directly (java -jar ...) instead of launching the installed app
Fix:  Always launch from the desktop shortcut, Start Menu, or the installed .exe
Do NOT use:    java -jar HelloWorldApp.jar    to test the installed version


Problem: Red errors in IntelliJ after pasting code
⚠️  WARNING
Symptom:  Red underlines on multiple lines immediately after pasting
Cause 1:  The class name does not match the file name — check both are HelloWorldApp
Cause 2:  A brace { or } is missing — count opening and closing braces
Cause 3:  The JDK is not set for the project — go to File → Project Structure → SDKs



🧹  Part 7 — Uninstall the Application

When you are done testing, you can uninstall the app cleanly:

Open Windows Settings  →  Apps  →  Installed apps
Search for:  HelloWorldApp
Click the three dots ...  →  Uninstall
Confirm the uninstall

✅  SUCCESS
The uninstaller removes everything:
  •  C:\Program Files\HelloWorldApp\  (entire folder)
  •  Desktop shortcut
  •  Start Menu shortcut
  •  Registry entries
Your original project files in C:\JavaProjects\ are NOT affected.



✅  Student Checklist — Tick Off Each Step

Use this checklist as you work through the guide. You can print this page and tick each box by hand, or ask your instructor to sign off each step.

Part 1 — Setup & Create Project
☐
Verified JDK 21 is installed (java, javac, jar, jpackage all work)
☐
Installed WiX Toolset
☐
Created new IntelliJ project:  HelloWorldApp
☐
Created HelloWorldApp.java in the src folder
☐
Pasted the source code and saved the file
Part 2 — Compile & Run
☐
Ran the app with Shift+F10 and saw the Hello World window
☐
Located the HelloWorldApp.class file in the out\ folder
Part 3 — Create the JAR
☐
Opened Project Structure and created a JAR artifact
☐
Built the artifact:  HelloWorldApp.jar created in out\artifacts\
☐
Tested JAR from terminal:  java -jar ... opened the window
Part 4 — Bundle and Create Installer
☐
Created the input\ folder and copied JAR into it
☐
Ran the jpackage command successfully
☐
HelloWorldApp-1.0.exe created (~52 MB)
Part 5 — Install and Test as a User
☐
Copied installer to Downloads folder
☐
Ran the installer and completed the setup wizard
☐
App launched after installation
☐
Verified Java Home shows the runtime\ path, not the system JDK
☐
Launched the app from the desktop shortcut
☐
Launched the app from the Start Menu
Part 6 — Wrap Up
☐
Can explain the difference between the first and second compilations
☐
Can explain why bundling the JRE improves the user experience
☐
Successfully uninstalled the app



⚡  Quick Reference — All Key Commands

IntelliJ Keyboard Shortcuts
Action
Shortcut
Run the app
Shift + F10
Open Project Structure
Ctrl + Alt + Shift + S
Open Terminal
Alt + F12
Build Artifacts
Build menu  →  Build Artifacts
Save file
Ctrl + S
Find anything
Double-tap Shift


PowerShell / Terminal Commands
java -jar out\artifacts\HelloWorldApp_jar\HelloWorldApp.jar   (test JAR)
mkdir input                                                       (create input folder)
copy out\artifacts\HelloWorldApp_jar\HelloWorldApp.jar input\ (copy JAR)
jpackage --input input --name HelloWorldApp ...                   (create installer)

Important File Locations
File
Location
Source code
src\HelloWorldApp.java
Compiled bytecode
out\production\HelloWorldApp\HelloWorldApp.class
JAR file
out\artifacts\HelloWorldApp_jar\HelloWorldApp.jar
Windows installer
HelloWorldApp-1.0.exe  (project root)
Installed app
C:\Program Files\HelloWorldApp\
Bundled Java runtime
C:\Program Files\HelloWorldApp\runtime\


End of Guide  •  Good luck!
