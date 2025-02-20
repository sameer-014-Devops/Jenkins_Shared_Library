// vars/Docker_Cleanup.groovy
#!/usr/bin/env groovy

/**
 * Checks for existing Docker images and removes them if found
 *
 * @param userName The user name part of the image name
 * @param appName The application name part of the image name
 * @param version The version to check for and clean up
 * @param cleanLatest Whether to check and clean up the latest tag (default: true)
 * @param cleanVersioned Whether to check and clean up the versioned tag (default: true)
 * @param credentialsId The Jenkins credentials ID for Docker Hub (optional)
 */
def call(Map config = [:]) {
    def userName = config.userName ?: error("userName parameter is required")
    def appName = config.appName ?: error("appName parameter is required")
    def version = config.defaultVersion ?: error("version parameter is required")
    def cleanLatest = config.cleanLatest != null ? config.cleanLatest : true
    def cleanVersioned = config.cleanVersioned != null ? config.cleanVersioned : true
    def credentialsId = config.credentialsId ?: "dockerhubCredentials"
    
    withCredentials([usernamePassword(credentialsId: credentialsId, 
                                       passwordVariable: 'dockerhubCredentials_Passwd', 
                                       usernameVariable: 'dockerhubCredentials_User')]) {
        
        def baseImageName = "${env.dockerhubCredentials_User}/${userName}-${appName}-img"
        
        // Clean latest tag if requested
        if (cleanLatest) {
            def latestImageExists = sh(
                script: """docker images -q ${baseImageName}:latest""", 
                returnStdout: true
            ).trim()
            
            if (latestImageExists) {
                echo 'Docker Image with the latest tag already exists. Removing the existing image...'
                sh """docker rmi -f ${baseImageName}:latest"""
            } else {
                echo 'Docker Image with the latest tag does not exist...'
            }
        }
        
        // Clean versioned tag if requested
        if (cleanVersioned) {
            def versionedImageExists = sh(
                script: """docker images -q ${baseImageName}:${version}""", 
                returnStdout: true
            ).trim()
            
            if (versionedImageExists) {
                echo "Docker Image with the specified version ${version} already exists. Removing the existing image..."
                sh """docker rmi -f ${baseImageName}:${version}"""
            } else {
                echo "Docker Image with the specified version ${version} does not exist..."
            }
        }
    }
}
