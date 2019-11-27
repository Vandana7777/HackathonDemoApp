# HackathonDemoApp

**Traditional Automation Test**
- Maven build project with TestNG framework have been used to implement traditional way of automoation
- To run the project: Run command: mvn clean test -PTraditionalTests
- Once you run the tests, it will generate html report at ProjectDir\target\surefire-reports\emailable-report.html
- Defects will be shown with BUG prefix in html report

**Visual AI Test**
- VisualAITests.java class has been created for Visual AI Test using Applitools
- In order to run the tests, Run command: mvn clean test -PVisualAITests
- This will create single batch called "Hackathon" and all tests will be stored inside this
- I have highlighted all the possible defects with bug region
- I have not added same bug on diff screen. I have added bug in single screen. if other screen has same issue, I have not highlighted that


