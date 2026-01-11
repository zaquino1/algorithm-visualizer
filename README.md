# Algorithm Visualizer: A Modern Learning Platform

An interactive, modern, and feature-rich application designed to help users learn and understand a wide variety of algorithms through dynamic, state-aware visualizations.

![Screenshot](https://i.imgur.com/your-screenshot.png) <!-- TODO: Replace with a real screenshot URL -->

## ✨ Core Features

- **Menu-Driven Interface**: A clean main menu to select algorithm categories for a focused learning experience.
- **Multiple Visualizer Categories**:
  - **Sorting & Searching**: Visualize classic array-based algorithms with smooth, customizable bar charts.
  - **Graph Algorithms**: Watch graph traversals (BFS, DFS) and pathfinding algorithms (Dijkstra's) with interactive, draggable nodes.
  - **Tree Traversals**: See how In-Order, Pre-Order, and Post-Order traversals navigate a binary search tree.
- **Comprehensive Learning Suite**:
  - **Information Panel**: Detailed documentation for every algorithm, including time/space complexity and links to further reading.
  - **Live Code Highlighting**: A syntax-highlighted code panel that shows the algorithm's source code and highlights the currently executing line.
  - **Code Translation**: Translate algorithm implementations from Java to Python, JavaScript, and C++.
- **Learning Management System (LMS)**:
  - A centralized **Quiz Center** with a rich question bank for every implemented algorithm.
- **Rich Customization & Polish**:
  - **Global Theme Manager**: Switch between **Terminal**, **Dark**, and **Light** themes, applied consistently across all windows.
  - **State-Aware Controls**: Full playback controls (`Play`, `Pause`, `Step`, `Reset`) that intelligently enable and disable based on the visualizer's state.
  - **Live Speed Adjustment**: Change the visualization speed in real-time.
  - **Custom Data Input**: Provide your own data for arrays and trees to visualize specific scenarios.
- **Background Music**: A globally managed music player with multiple tracks and playback modes to enhance your learning session.

## 🚀 Getting Started

1.  **Prerequisites**: Java 21 (or later) and Maven.
2.  **Clone**: `git clone https://github.com/your-username/algorithm-visualizer.git`
3.  **Build**: `cd algorithm-visualizer && mvn clean install`
4.  **Run**: The main entry point is `org.algovisualizer.Main`. Run this from your IDE or use the generated JAR: `java -jar target/algorithm-visualizer-1.0.0.jar`

## 🛠️ Current Status & Future Development

This project is a robust, feature-rich learning tool, but it is still under active development. There are known areas for improvement and exciting features planned for the future.

### Known Bugs & Areas for Improvement:

- **Custom Graph Input**: The text field for custom graph data is not yet implemented.
- **String Algorithm Visualizer**: The visualizer for string algorithms is currently a placeholder and needs to be designed and built.
- **Theme Persistence**: The selected theme is not currently saved between application launches.

### Future Features:

- **More Algorithms**: Adding more complex graph algorithms (A*, Prim's, Kruskal's), advanced sorting algorithms, and dynamic programming visualizations.
- **Enhanced Interactivity**: Implementing the ability to add, remove, and edit nodes and edges directly within the graph and tree visualizers.
- **More Translation Languages**: Expanding the code translation feature to include more languages.
- **Advanced LMS**: Building out the LMS to track user progress, scores, and provide more comprehensive learning paths.

## 🤝 Contributing

This project is open to assistance! Contributions, issues, and feature requests are welcome. If you'd like to contribute, please feel free to fork the repository, make your changes, and submit a pull request.

Please check the [issues page](https://github.com/your-username/algorithm-visualizer/issues) for current tasks and bugs to work on.

---

_This project aims to be a top-tier educational tool for students and developers alike, and your help is appreciated to get it there!_
