// vars/dockerBuildImage.groovy
#!/usr/bin/env groovy

/**
 * Builds a Docker image with versioning and verification
 *
 * @param userName The user name part of the image name
 * @param appName The application name part of the image name
 * @param newVersion The version to tag the image with
 * @param dockerfilePath Path to the Dockerfile (default: ".")
 * @param buildArgs Additional build arguments (optional)
 * @param credentialsId The Jenkins credentials ID for Docker Hub (optional)
 * @return Boolean indicating if build was successful
 */
def call(Map config = [:]) {
    def userName = config.userName ?: error("userName parameter is required")
    def appName = config.appName ?: error("appName parameter is required")
    def newVersion = config.newVersion ?: error("newVersion parameter is required")
    def dockerfilePath = config.dockerfilePath ?: "."
    def buildArgs = config.buildArgs ?: ""
    def credentialsId = config.credentialsId ?: "dockerhubCredentials"
    def buildSuccess = false
    
    withCredentials([usernamePassword(credentialsId: credentialsId, 
                                       passwordVariable: 'dockerhubCredentials_Passwd', 
                                       usernameVariable: 'dockerhubCredentials_User')]) {
        
        // Logout first to ensure clean state
        sh 'docker logout'
        
        // Build the Docker image with version tag
        def imageNameWithVersion = "${env.dockerhubCredentials_User}/${userName}-${appName}-img:${newVersion}"
        def imageNameLatest = "${env.dockerhubCredentials_User}/${userName}-${appName}-img:latest"
        
        // Build image with version tag
        sh """docker build -t ${imageNameWithVersion} ${buildArgs} ${dockerfilePath}"""
        
        // Tag as latest
        sh """docker tag ${imageNameWithVersion} ${imageNameLatest}"""
        
        // List images for debugging
        sh 'docker images'
        
        // Verify the image was built successfully
        def latestImgExists = sh(script: """docker images -q ${imageNameLatest}""", returnStdout: true).trim()
        
        if (latestImgExists) {
            echo '*********Docker Image Build SUCCESSFUL*********'
            buildSuccess = true
        } else {
            error '*********Docker Image Build FAILED*********'
        }
    }
    
    return buildSuccess
}
