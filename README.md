# Algorithm Visualizer: A Professional Learning Platform

An interactive, modern, and feature-rich application designed to help users learn and understand a wide variety of algorithms through dynamic, state-aware visualizations. This project has been architected for stability, scalability, and a professional user experience.

![Screenshot](https://i.imgur.com/your-screenshot.png) <!-- TODO: Replace with a real screenshot URL -->

## ✨ Core Features

- **Robust, Menu-Driven Interface**: A clean, intuitive main menu to select algorithm categories, ensuring a focused and stable user experience.
- **Multiple, Dedicated Visualizers**:
  - **Sorting & Searching**: Visualize classic array-based algorithms with smooth, customizable bar charts.
  - **Graph Algorithms**: Watch graph traversals (BFS, DFS) and pathfinding algorithms (Dijkstra's) with interactive, draggable nodes.
  - **Tree Traversals**: See how In-Order, Pre-Order, and Post-Order traversals navigate a binary search tree.
- **Comprehensive Learning Suite**:
  - **Information Panel**: Detailed documentation for every algorithm, including time/space complexity and links to further reading.
  - **Live Code Highlighting**: A syntax-highlighted code panel that shows the algorithm's source code and highlights the exact line being executed at each step.
  - **Code Translation**: Translate algorithm implementations from Java to Python, JavaScript, and C++.
- **Learning Management System (LMS)**:
  - A centralized **Quiz Center** with a rich question bank for every implemented algorithm.
- **Rich Customization & Polish**:
  - **Global Theme Manager**: A robust, singleton-based theme manager that ensures consistent, high-contrast themes across all windows. Includes **Terminal**, **Dark**, and **Light** modes.
  - **State-Aware Controls**: A full suite of playback controls (`Play`, `Pause`, `Step`, `Reset`) that intelligently enable and disable based on the visualizer's state.
  - **Live Speed Adjustment**: Change the visualization speed in real-time without pausing the animation.
  - **Custom Data Input**: Provide your own data for arrays and trees to visualize specific scenarios.
- **Singleton-Managed Background Music**: A stable, globally managed music player with multiple tracks and playback modes (loop, shuffle) that will not cause overlapping audio bugs.

## 🚀 Getting Started

1.  **Prerequisites**: Java 21 (or later) and Maven.
2.  **Clone**: `git clone https://github.com/your-username/algorithm-visualizer.git`
3.  **Build**: `cd algorithm-visualizer && mvn clean install`
4.  **Run**: The main entry point is `org.algovisualizer.Main`. Run this from your IDE or use the generated JAR: `java -jar target/algorithm-visualizer-1.0.0.jar`

## 🛠️ How to Use

1.  Launch the application to open the **Main Menu**.
2.  Select a category (e.g., "Graph"). A dedicated **Visualizer Window** will open.
3.  Use the dropdown to select an algorithm (e.g., "Dijkstra's Algorithm").
4.  Press **Generate** for a random dataset or input your own.
5.  Press **Play** to watch the visualization, or use the step controls for a manual walkthrough.
6.  Adjust the **Speed** slider in real-time.
7.  Explore the tabs on the right to view algorithm **Information**, live **Code**, and language **Translations**.
8.  From the main menu, launch the **LMS & Quiz Center** to test your knowledge.

## Dev Journey & Critical Bug Fixes

This project underwent a significant refactoring to achieve a professional, stable, and scalable architecture. As a senior developer, I believe in transparency and documenting the engineering process.

- **Initial State & Challenges**: The initial versions of the application suffered from critical architectural flaws, leading to instability, a poor user experience, and numerous bugs, including:
    - Non-functional UI controls (playback, generation).
    - A broken theme system with poor font contrast and no global consistency.
    - A flawed music player implementation that caused overlapping audio.
    - A state management system that was prone to corruption, leading to crashes and visualizer failures.

- **The Final Overhaul**: To fix these issues, the project was re-architected from the ground up with a focus on professional software design patterns:
    - **Stateful Architecture**: The `AlgorithmController` and `ControlPanel` were rewritten to be fully state-aware, ensuring controls are always responsive and correct.
    - **Singleton Managers**: A singleton `ThemeManager` and `MusicManager` were implemented to provide robust, global control over themes and audio, eliminating all related bugs.
    - **Menu-Driven UI**: The application was refactored into a menu-driven system with dedicated, category-specific windows, improving stability and user focus.
    - **Robust Error Handling & Testing**: All components were rewritten with a focus on correctness, eliminating race conditions and `NullPointerException`s that plagued earlier versions.

This journey from a broken prototype to a stable, feature-rich platform reflects a commitment to quality and professional engineering standards.

---

_This project is now ready for deployment._
