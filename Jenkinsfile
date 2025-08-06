pipeline {
    agent any
    
    // Schedule to run every 7 days (weekly on Sunday at 2 AM)
    triggers {
        cron('0 2 * * 0')
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
                
                // Display Git information
                script {
                    def gitCommit = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
                    def gitBranch = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()
                    echo "Git Commit: ${gitCommit}"
                    echo "Git Branch: ${gitBranch}"
                }
            }
        }
        
        stage('Validate') {
            steps {
                echo 'Validating project structure...'
                
                // Check if required files exist
                script {
                    if (!fileExists('pom.xml')) {
                        error('pom.xml not found!')
                    }
                    if (!fileExists('src/main/java')) {
                        error('src/main/java directory not found!')
                    }
                    echo 'Project structure validation passed!'
                }
            }
        }
        
        stage('Compile') {
            steps {
                echo 'Compiling the project...'
                sh 'mvn clean compile'
            }
            post {
                failure {
                    echo 'Compilation failed! Check the logs above.'
                }
            }
        }
        
        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh 'mvn test'
            }
            post {
                always {
                    // Publish test results
                    publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
                    
                    // Archive test reports
                    archiveArtifacts artifacts: 'target/surefire-reports/*', allowEmptyArchive: true
                }
                failure {
                    echo 'Tests failed! Please check the test reports.'
                }
                success {
                    echo 'All tests passed successfully!'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo 'Packaging the application...'
                sh 'mvn package -DskipTests'
            }
            post {
                success {
                    // Archive the JAR file
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                    echo 'Application packaged successfully!'
                }
                failure {
                    echo 'Packaging failed!'
                }
            }
        }
        
        stage('Code Quality Analysis') {
            steps {
                echo 'Running code quality checks...'
                
                // Run Maven site for reports (if configured)
                sh 'mvn site -DskipTests || true'
                
                // Custom code quality checks
                script {
                    def javaFiles = sh(returnStdout: true, script: 'find src -name "*.java" | wc -l').trim()
                    echo "Number of Java files: ${javaFiles}"
                    
                    def testFiles = sh(returnStdout: true, script: 'find src/test -name "*Test.java" | wc -l || echo 0').trim()
                    echo "Number of test files: ${testFiles}"
                }
            }
        }
        
        stage('Integration Tests') {
            steps {
                echo 'Running integration tests...'
                
                script {
                    try {
                        // Run the application to ensure it works
                        sh 'java -cp target/classes com.example.Application'
                        echo 'Integration test passed - Application runs successfully!'
                    } catch (Exception e) {
                        echo "Integration test failed: ${e.getMessage()}"
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }
        
        stage('Deploy to Staging') {
            when {
                branch 'main'
            }
            steps {
                echo 'Deploying to staging environment...'
                
                script {
                    // Simulate deployment
                    echo 'Copying artifacts to staging server...'
                    sh 'mkdir -p staging'
                    sh 'cp target/*.jar staging/ || echo "No JAR files to copy"'
                    echo 'Deployment to staging completed!'
                }
            }
        }
        
        stage('Smoke Tests') {
            when {
                branch 'main'
            }
            steps {
                echo 'Running smoke tests on staging...'
                
                script {
                    // Simulate smoke tests
                    sleep(2) // Simulate test execution time
                    echo 'Smoke tests passed!'
                }
            }
        }
        
        stage('Deploy to Production') {
            when {
                allOf {
                    branch 'main'
                    not { triggeredBy 'TimerTrigger' } // Don't auto-deploy on scheduled builds
                }
            }
            input {
                message "Deploy to production?"
                ok "Deploy"
                parameters {
                    choice(
                        name: 'DEPLOYMENT_TYPE',
                        choices: ['Blue-Green', 'Rolling', 'Canary'],
                        description: 'Choose deployment strategy'
                    )
                }
            }
            steps {
                echo "Deploying to production using ${DEPLOYMENT_TYPE} strategy..."
                
                script {
                    // Simulate production deployment
                    echo 'Production deployment started...'
                    sleep(3) // Simulate deployment time
                    echo 'Production deployment completed successfully!'
                }
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline execution completed!'
            
            // Clean workspace
            cleanWs()
        }
        success {
            echo 'Pipeline executed successfully! ✅'
            
            // Send success notification (configure as needed)
            script {
                def buildDuration = currentBuild.duration ?: 0
                echo "Build completed in ${buildDuration}ms"
            }
        }
        failure {
            echo 'Pipeline failed! ❌'
            
            // Send failure notification
            script {
                def failedStage = env.STAGE_NAME ?: 'Unknown'
                echo "Pipeline failed at stage: ${failedStage}"
                
                // In a real scenario, you would send email/Slack notifications here
                echo 'Failure notifications would be sent to the development team'
            }
        }
        unstable {
            echo 'Pipeline completed with warnings! ⚠️'
        }
        aborted {
            echo 'Pipeline was aborted! ��'
        }
    }
} 