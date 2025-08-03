# Jenkins CI/CD Pipeline Demo

## 🚀 Use Case: Automating Continuous Integration and Deployment for a Web Application

This repository contains a complete Maven-based Java project with Jenkins CI/CD pipeline configuration for demonstration purposes.

## 📋 Prerequisites

Before starting, ensure you have the following installed:

- **Java 11 or higher** - Required for running the Maven project
- **Maven 3.6+** - For building and testing the Java application
- **Git** - For version control
- **Jenkins** - For CI/CD pipeline execution
- **GitHub Account** - For repository hosting (free account)

## 🏗️ Project Structure

```
jenkins-pipeline-demo/
├── src/
│   ├── main/java/com/example/
│   │   └── Application.java          # Main Java application
│   └── test/java/com/example/
│       └── ApplicationTest.java      # Unit tests
├── pom.xml                          # Maven configuration
├── Jenkinsfile                      # Jenkins pipeline definition
├── sample-data.txt                  # Sample file for modifications
├── config.properties               # Configuration file
└── README.md                       # This documentation
```

## 🎯 Step-by-Step Implementation Guide

### Phase 1: Git Repository Setup

#### 1. Create Git Account (Free account)
- Visit [GitHub.com](https://github.com)
- Sign up for a free account
- Verify your email address

#### 2. Create Git Repository
- Click "New repository" in GitHub
- Repository name: `jenkins-pipeline-demo`
- Description: `Sample Maven project for Jenkins CI/CD pipeline demonstration`
- Set to **Public** (for free account)
- Initialize with README: **No** (we have our own)
- Click "Create repository"

#### 3. Initial Code Push to Git Repository

```bash
# Navigate to your project directory
cd /path/to/your/project

# Initialize Git repository
git init

# Add all files
git add .

# Make initial commit
git commit -m "Initial commit: Maven project with Jenkins pipeline"

# Add remote origin (replace YOUR_USERNAME with your GitHub username)
git remote add origin https://github.com/YOUR_USERNAME/jenkins-pipeline-demo.git

# Push to main branch
git branch -M main
git push -u origin main
```

### Phase 2: Testing Git Workflow

#### 4. Modify Files in Local Folder
Make simple changes to test the Git workflow:

```bash
# Edit the sample-data.txt file
echo "Modified on $(date)" >> sample-data.txt

# Or edit config.properties
sed -i 's/build.number=1/build.number=2/' config.properties
```

#### 5. Commit and Push Changes

```bash
# Add modified files
git add .

# Commit changes
git commit -m "Update sample data and build number"

# Push changes
git push origin main
```

#### 6. Pull Code from Git Repository

To simulate pulling changes (useful when working in teams):

```bash
# Pull latest changes
git pull origin main
```

### Phase 3: Jenkins Pipeline Setup

#### 7. Jenkins Installation and Configuration

**Install Jenkins:**
- Download Jenkins from [jenkins.io](https://www.jenkins.io/download/)
- Follow installation instructions for your operating system
- Complete the initial setup wizard
- Install suggested plugins

**Configure Global Tools:**
1. Navigate to: Manage Jenkins → Global Tool Configuration
2. Configure Maven:
   - Name: `Maven`
   - Install automatically: ✅
   - Version: Latest available
3. Configure JDK:
   - Name: `JDK11`
   - Install automatically: ✅
   - Version: Java 11

#### 8. Create Jenkins Pipeline Job

1. **Create New Item:**
   - Click "New Item" in Jenkins dashboard
   - Enter name: `jenkins-pipeline-demo`
   - Select "Pipeline" type
   - Click "OK"

2. **Configure Pipeline:**
   - **General Tab:**
     - Description: `CI/CD pipeline for Maven project demonstration`
     - GitHub project URL: `https://github.com/YOUR_USERNAME/jenkins-pipeline-demo`
   
   - **Build Triggers:**
     - ✅ GitHub hook trigger for GITScm polling
     - ✅ Poll SCM: `H/15 * * * *` (every 15 minutes)
   
   - **Pipeline Configuration:**
     - Definition: `Pipeline script from SCM`
     - SCM: `Git`
     - Repository URL: `https://github.com/YOUR_USERNAME/jenkins-pipeline-demo.git`
     - Branch: `*/main`
     - Script Path: `Jenkinsfile`

3. **Save Configuration**

#### 9. Jenkinsfile Features

Our `Jenkinsfile` includes:

- **📅 Periodic Schedule**: Runs every 7 days (Sundays at 2 AM)
- **🔄 Pipeline Stages**:
  - Checkout: Get source code from Git
  - Validate: Check project structure
  - Compile: Build the Java application
  - Test: Run unit tests with reporting
  - Package: Create JAR artifact
  - Code Quality: Analyze code metrics
  - Integration Tests: Verify application runs
  - Deploy to Staging: Deploy to staging environment
  - Smoke Tests: Verify staging deployment
  - Deploy to Production: Manual approval for production

- **📊 Reporting**:
  - Test results publishing
  - Artifact archiving
  - Build duration tracking

- **🚨 Failure Handling**:
  - Stage-specific error handling
  - Notification system
  - Workspace cleanup

### Phase 4: Pipeline Execution and Testing

#### 10. First Pipeline Run (Successful)

```bash
# Trigger pipeline manually
# In Jenkins: Go to your pipeline → "Build Now"

# Expected Output: "Successful Run"
```

**Monitor the pipeline:**
- Check console output for each stage
- Verify test results are published
- Confirm artifacts are archived

#### 11. Implement Failure Scenario

To test failure handling, intentionally break something:

**Option 1: Break Tests**
```java
// Edit ApplicationTest.java - change an assertion to make it fail
assertEquals("Wrong Expected Value", result);
```

**Option 2: Break Build (Change branch name)**
```bash
# Create and switch to 'master' branch (instead of 'main')
git checkout -b master
git push origin master

# Update Jenkins job to look for 'master' branch but keep code in 'main'
```

**Option 3: Break Jenkinsfile**
```groovy
// Add invalid syntax to Jenkinsfile
stages {
    stage('Broken Stage') {
        steps {
            sh 'invalid-command-that-does-not-exist'
        }
    }
}
```

#### 12. Push Updated Jenkins Files and Test

```bash
# After making failure changes
git add .
git commit -m "Test failure scenario - intentional errors"
git push origin main
```

#### 13. Restart Jenkins and Run Pipeline

```bash
# Restart Jenkins (method depends on installation)
# Linux/Mac: sudo systemctl restart jenkins
# Windows: Restart Jenkins service

# Run pipeline again - Expected: "Failed"
```

#### 14. Fix Issues and Generate Success Report

```bash
# Fix the intentional errors
# Restore working code
git add .
git commit -m "Fix pipeline issues - restore working state"
git push origin main

# Run pipeline again
# Expected: "Successful Run"
```

## 📈 Pipeline Features and Benefits

### Automated Testing
- Unit tests run on every commit
- Test results published and tracked
- Code coverage reporting

### Continuous Integration
- Automatic builds on code changes
- Integration with Git webhooks
- Parallel execution where possible

### Deployment Automation
- Staged deployment (Dev → Staging → Production)
- Manual approval for production deployments
- Rollback capabilities

### Monitoring and Reporting
- Build status notifications
- Performance metrics tracking
- Detailed execution logs

## 🔧 Customization Options

### Modify Schedule
```groovy
// In Jenkinsfile, change the cron expression:
triggers {
    cron('0 2 * * 0')  // Weekly on Sunday at 2 AM
    // cron('H */4 * * *')  // Every 4 hours
    // cron('0 9 * * 1-5')  // Weekdays at 9 AM
}
```

### Add More Stages
```groovy
stage('Security Scan') {
    steps {
        echo 'Running security analysis...'
        // Add security scanning tools
    }
}

stage('Performance Tests') {
    steps {
        echo 'Running performance tests...'
        // Add performance testing
    }
}
```

### Configure Notifications
```groovy
post {
    failure {
        emailext (
            subject: "Build Failed: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
            body: "Check console output at ${env.BUILD_URL}",
            to: "team@example.com"
        )
    }
}
```

## 🐛 Troubleshooting

### Common Issues

1. **Maven not found**
   - Ensure Maven is configured in Global Tool Configuration
   - Check PATH environment variable

2. **Git authentication issues**
   - Use personal access tokens for HTTPS
   - Configure SSH keys for SSH access

3. **Pipeline fails at checkout**
   - Verify repository URL is correct
   - Check Jenkins has access to the repository

4. **Tests not running**
   - Ensure test classes end with `Test`
   - Check Maven Surefire plugin configuration

### Debug Commands

```bash
# Test Maven build locally
mvn clean compile test package

# Check Git configuration
git config --list

# Verify Java and Maven versions
java -version
mvn -version
```

## 📚 Learning Outcomes

After completing this tutorial, you will understand:

- Git workflow and repository management
- Maven project structure and build lifecycle
- Jenkins pipeline configuration and execution
- CI/CD best practices and automation
- Failure handling and recovery procedures
- Scheduling and periodic execution
- Testing integration and reporting

## 🎓 Next Steps

1. **Advanced Pipeline Features:**
   - Multi-branch pipelines
   - Pipeline libraries
   - Parallel execution

2. **Integration with Other Tools:**
   - SonarQube for code quality
   - Docker for containerization
   - Kubernetes for orchestration

3. **Security and Compliance:**
   - Secret management
   - Security scanning
   - Compliance reporting

## 📞 Support

If you encounter issues:
1. Check Jenkins console output for detailed error messages
2. Verify all prerequisites are installed and configured
3. Review the troubleshooting section above
4. Consult Jenkins documentation: [jenkins.io/doc](https://www.jenkins.io/doc/)

---

**Happy Building! 🚀**

*This project demonstrates the power of automated CI/CD pipelines for modern software development.* 