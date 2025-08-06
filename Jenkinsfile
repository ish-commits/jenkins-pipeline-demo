pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Code checked out from Git repository successfully!'
                echo "Repository: https://github.com/ish-commits/jenkins-pipeline-demo.git"
                echo "Workspace: ${env.WORKSPACE}"
            }
        }
        
        stage('Build Info') {
            steps {
                echo 'Displaying build information...'
                echo "Build Number: ${env.BUILD_NUMBER}"
                echo "Job Name: ${env.JOB_NAME}"
                echo "Git integration working successfully!"
            }
        }
        
        stage('Simple Test') {
            steps {
                echo 'Running simple validation...'
                script {
                    def result = 10 + 20
                    if (result == 30) {
                        echo 'Test PASSED! ✅'
                    } else {
                        error('Test failed')
                    }
                }
            }
        }
    }
    
    post {
        success {
            echo 'Jenkins pipeline completed successfully! ✅'
            echo 'Git to Jenkins integration demonstrated!'
        }
        failure {
            echo 'Pipeline failed! ❌'
        }
    }
} 