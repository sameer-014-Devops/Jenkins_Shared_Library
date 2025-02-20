// vars/dockerHubLogin.groovy
#!/usr/bin/env groovy

/**
 * Performs Docker Hub login and verifies the success
 *
 * @param credentialsId The Jenkins credentials ID containing Docker Hub username/password (optional)
 * @param verifyLogin Whether to verify the login success (default: true)
 * @return Boolean indicating if login was successful
 */
def call(Map config = [:]) {
    def credentialsId = config.credentialsId ?: 'DOCKERHUB_CREDENTIALS'
    def verifyLogin = config.verifyLogin != null ? config.verifyLogin : true
    def loginSuccess = false
    
    echo '**********Login to Docker Hub**********'
    
    withCredentials([usernamePassword(credentialsId: credentialsId, 
                                       passwordVariable: 'DOCKERHUB_CREDENTIALS_PSW', 
                                       usernameVariable: 'DOCKERHUB_CREDENTIALS_USR')]) {
        sh "echo \$DOCKERHUB_CREDENTIALS_PSW | docker login -u \$DOCKERHUB_CREDENTIALS_USR --password-stdin"
        
        if (verifyLogin) {
            def loginStatus = sh(script: "docker info | grep -i \"Username: \$DOCKERHUB_CREDENTIALS_USR\"", 
                                 returnStatus: true)
            
            if (loginStatus == 0) {
                echo '*********Docker Hub Login SUCCESSFUL*********'
                loginSuccess = true
            } else {
                error '*********Docker Hub Login FAILED*********'
            }
        } else {
            // If verification is disabled, assume success based on login command
            loginSuccess = true
        }
    }
    
    return loginSuccess
}
