// vars/dockerPushImage.groovy
#!/usr/bin/env groovy

/**
 * Pushes Docker images to Docker Hub with version and latest tags
 *
 * @param userName The user name part of the image name
 * @param appName The application name part of the image name
 * @param version The version of the image to push
 * @param pushLatest Whether to also push the latest tag (default: true)
 * @param credentialsId The Jenkins credentials ID for Docker Hub (optional)
 * @return Boolean indicating if push was successful
 */
def call(Map config = [:]) {
    def userName = config.userName ?: error("userName parameter is required")
    def appName = config.appName ?: error("appName parameter is required")
    def version = config.newVersion ?: error("newVersion parameter is required")
    def pushLatest = config.pushLatest != null ? config.pushLatest : true
    def credentialsId = config.credentialsId ?: "dockerhubCredentials"
    def pushSuccess = false
    
    withCredentials([usernamePassword(credentialsId: credentialsId, 
                                       passwordVariable: 'dockerhubCredentials_Passwd', 
                                       usernameVariable: 'dockerhubCredentials_User')]) {
        
        def baseImageName = "${env.dockerhubCredentials_User}/${userName}-${appName}-img"
        
        // Push versioned image
        echo "********Pushing Docker Image With The Version ${version}********"
        sh """docker push ${baseImageName}:${version}"""
        
        // Push latest tag if requested
        if (pushLatest) {
            echo "********Pushing Docker Image With The Tag 'latest'********"
            sh """docker push ${baseImageName}:latest"""
        }
        
        // Verify the push was successful
        // Note: This is a simple verification that the image still exists locally
        // A more robust solution would check the Docker Hub API
        def imagePushed = sh(
            script: """docker images -q ${baseImageName}:${version}""", 
            returnStdout: true
        ).trim()
        
        if (imagePushed) {
            echo '*********Docker Image Push SUCCESSFUL*********'
            pushSuccess = true
        } else {
            error '*********Docker Image Push FAILED*********'
        }
    }
    
    return pushSuccess
}
