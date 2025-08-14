# Steadfast Preview - Focus Session Manager

![Version](https://img.shields.io/badge/version-preview-orange)
![Platform](https://img.shields.io/badge/platform-Windows-blue)
![Java](https://img.shields.io/badge/Java-17+-green)
![License](https://img.shields.io/badge/license-MIT-brightgreen)

> **Note: This is the preview version of Steadfast.** A feature-rich productivity application designed to help users maintain focus by blocking distracting programs during work sessions.

## 🎯 Overview

Steadfast Preview is a console-based focus management tool that allows users to create profiles with specific banned programs and run timed focus sessions. During these sessions, the selected programs are automatically minimized to help maintain productivity.

## ✨ Current Features

### 📋 Profile Management
- **Create Profiles**: Set up custom focus profiles with names and descriptions
- **Load Profiles**: Access previously created profiles from the database
- **Program Association**: Link specific programs to each profile

### 🚫 Program Blocking
- **Real-time Scanning**: Detect currently running programs using PowerShell
- **Smart Selection**: Choose from existing banned programs or add new ones
- **Dynamic Blocking**: Programs are minimized every 2 seconds during focus sessions

### ⏱️ Session Management
- **Custom Duration**: Set focus time in minutes (default: 30 minutes)
- **Live Progress**: Real-time countdown and session status updates
- **Session Recording**: All sessions are automatically saved to the database
- **Automatic Termination**: Sessions end automatically with notification

### 💾 Database Integration
- **SQLite Backend**: Persistent storage for profiles, programs, and sessions
- **Relational Design**: Efficient many-to-many relationships between profiles and programs
- **Session History**: Complete tracking of focus session data

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Windows operating system
- PowerShell (for program scanning)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/andrepontde/Steadfast-Java-pilot.git
   cd java-steadfast-preview
   ```

2. Compile the application:
   ```bash
   javac -cp "." src/main/java/io/github/andrepontde/*.java
   ```

3. Run the application:
   ```bash
   java -cp "src/main/java" io.github.andrepontde.Main
   ```

### Usage
1. **Choose Action**: Create a new profile (c) or load existing (l)
2. **Setup Profile**: Name your profile and select programs to block
3. **Set Duration**: Enter focus time in minutes
4. **Start Session**: The application will begin blocking selected programs
5. **Complete Session**: Session ends automatically and data is saved

## 🛠️ Technical Architecture

### Core Components
- **Main.java**: Console interface and session management
- **DbConnection.java**: Database operations and SQLite integration
- **CommandRunner.java**: System interaction and program control using NirCmd
- **View.java**: JavaFX GUI (planned for future implementation)

### Database Schema
```sql
- profiles (id, name, description)
- bannedProgs (id, name, description)  
- profile_programs (profile_id, program_id) -- Junction table
- sessions (id, profile_id, dateStart, dateFinish, description)
```

### Dependencies
- **NirCmd**: Used for advanced Windows system interactions and program control

## 🔮 Future Roadmap

### 📱 Mobile Version
- **Cross-platform mobile app** for iOS and Android
- **Sync capabilities** with desktop version
- **Mobile-specific productivity features**

### 🤖 AI-Enhanced Desktop Version
- **Dynamic Procrastination Tracking**: AI-powered analysis of user behavior patterns
- **Performance Analytics**: Non-intrusive monitoring with intelligent insights
- **Adaptive Reward System**: Personalized rewards and consequences based on performance
- **Event-Driven Architecture**: Seamless integration with Windows API for real-time responsiveness
- **Smart Recommendations**: AI-suggested focus strategies based on usage patterns

### 🌐 Cross-Platform Support
- **Linux Support**: Native compatibility with major Linux distributions
- **macOS Support**: Full macOS integration with system-level controls
- **Unified Experience**: Consistent functionality across all platforms

### 🎨 Enhanced User Interface
- **Modern GUI**: Sleek, intuitive graphical interface (coming soon!)
- **Dark/Light Themes**: Customizable appearance options
- **Dashboard Analytics**: Visual progress tracking and statistics
- **Customizable Layouts**: Personalized workspace organization

### ⚡ Performance Improvements
- **Windows API Integration**: Replace scheduled threads with efficient event-driven system
- **Reduced Resource Usage**: Optimized background processing
- **Instant Response**: Real-time program detection and blocking

## 🤝 Contributing

We welcome contributions to Steadfast! Please feel free to:
- Report bugs and suggest features
- Submit pull requests
- Improve documentation
- Share feedback on the preview version

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For questions, suggestions, or support:
- Open an issue on GitHub
- Contact: [your-email@example.com]

---

**Steadfast Preview** - Building better focus habits, one session at a time. 🎯

> *This preview version represents the foundation of a comprehensive productivity ecosystem. Stay tuned for exciting updates and new features!*