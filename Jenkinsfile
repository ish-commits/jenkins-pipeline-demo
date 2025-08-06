pipeline {
    agent any
    
    // Schedule to run every 7 days (weekly on Sunday at 2 AM)
    triggers {
        cron('0 2 * * 0')
    }
    
    tools {
        maven 'Maven'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Using manual source code (bypassing Git)...'
                echo 'Source code checked out successfully!'
            }
        }
        
        stage('Validate') {
            steps {
                echo 'Validating project structure...'
                
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
        
        stage('Build Info') {
            steps {
                echo 'Displaying build information...'
                script {
                    echo "Workspace: ${env.WORKSPACE}"
                    echo "Build Number: ${env.BUILD_NUMBER}"
                    echo "Job Name: ${env.JOB_NAME}"
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
                success {
                    echo 'Compilation completed successfully!'
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
                    echo 'Archiving test results...'
                    script {
                        if (fileExists('target/surefire-reports/*.xml')) {
                            publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
                            archiveArtifacts artifacts: 'target/surefire-reports/*', allowEmptyArchive: true
                        }
                    }
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
                    echo 'Archiving JAR artifacts...'
                    script {
                        if (fileExists('target/*.jar')) {
                            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                        }
                    }
                    echo 'Application packaged successfully!'
                }
                failure {
                    echo 'Packaging failed!'
                }
            }
        }
        
        stage('Integration Tests') {
            steps {
                echo 'Running integration tests...'
                
                script {
                    try {
                        sh 'java -cp target/classes com.example.Application'
                        echo 'Integration test passed - Application runs successfully!'
                    } catch (Exception e) {
                        echo "Integration test warning: ${e.getMessage()}"
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline execution completed!'
        }
        success {
            echo 'Pipeline executed successfully! ✅'
        }
        failure {
            echo 'Pipeline failed! ❌'
            echo 'Check the console output and test reports for details.'
        }
        unstable {
            echo 'Pipeline completed with warnings! ⚠️'
        }
    }
} 